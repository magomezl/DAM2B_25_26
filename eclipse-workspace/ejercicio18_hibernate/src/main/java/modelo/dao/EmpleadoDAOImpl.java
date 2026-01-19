package modelo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import modelo.dto.hibernate.Empleados;

public class EmpleadoDAOImpl implements EmpleadoDAO {
	private static final SessionFactory sF = new Configuration().configure().buildSessionFactory();
	private static Session sesion;


	@Override
	public void anadirEmpleado(Empleados emp) {
		sesion = sF.openSession();
		Transaction t; 
		if (sesion.createQuery("FROM Empleados WHERE nombre=:nombreE AND apellido1=:apellido1E AND apellido2=:apellido2E", Empleados.class)
				.setParameter("nombreE", emp.getNombre())
				.setParameter("apellido1E", emp.getApellido1())
				.setParameter("apellido2E", emp.getApellido2())
				.list()
				.isEmpty()) {
			t = sesion.beginTransaction();
			sesion.persist(emp);
			t.commit();
		}
		sesion.close();
	}

	@Override
	public void modificarEmpleado(int emp, Empleados empNew) {
		sesion = sF.openSession();
		Transaction t = sesion.beginTransaction();
		Empleados empObj = sesion.get(Empleados.class, emp);
		empObj.setNombre(empNew.getNombre());
		empObj.setApellido1(empNew.getApellido1());
		empObj.setApellido2(empNew.getApellido2());
		empObj.setDepartamentos(empNew.getDepartamentos());
		sesion.merge(empObj);
		t.commit();
		sesion.close();
	}

	@Override
	public void eliminarEmpleado(int emp) {
		sesion = sF.openSession();
		Transaction t = sesion.beginTransaction();
		Empleados empObj = sesion.get(Empleados.class, emp);
		sesion.remove(empObj);
		t.commit();
		sesion.close();
	}

	@Override
	public List<Empleados> listarEmpleados() {
		sesion = sF.openSession();
		List<Empleados> listado = sesion.createQuery("FROM Empleados", Empleados.class).list();
		sesion.close();
		return listado;
	}

}
