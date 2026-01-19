package modelo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import jakarta.persistence.Tuple;
import modelo.dto.hibernate.Departamentos;

public class DepartamentoDAOImpl implements DepartamentoDAO {
	private static final SessionFactory sF = new Configuration().configure().buildSessionFactory();
	private static Session sesion;

	@Override
	public void anadirDpto(Departamentos dpto) {
		//TODO no necesaria comprobación. Hacer con método ListarDptos
		sesion = sF.openSession();
		Transaction t; 
		if (sesion.createQuery("FROM Departamentos WHERE loc=:localidad AND dnombre=:nombre", Departamentos.class)
				.setParameter("localidad", dpto.getLoc())
				.setParameter("nombre", dpto.getDnombre())
				.list()
				.isEmpty()) {
				t = sesion.beginTransaction();
				sesion.persist(dpto);
				t.commit();
				
		}
		sesion.close();
	}

	@Override
	public void modificarDpto(Departamentos dptoOld, Departamentos dptoNew) {
		sesion = sF.openSession();
		Transaction t = sesion.beginTransaction();
		dptoOld.setDnombre(dptoNew.getDnombre());
		dptoOld.setLoc(dptoNew.getLoc());
		sesion.merge(dptoOld);
		t.commit();
		sesion.close();
	}

	@Override
	public void modificarDpto(int dptoOld, Departamentos dptoNew) {
		sesion = sF.openSession();
		Transaction t = sesion.beginTransaction();
		Departamentos dptoObj = sesion.get(Departamentos.class, dptoOld);
		dptoObj.setDnombre(dptoNew.getDnombre());
		dptoObj.setLoc(dptoNew.getLoc());
		sesion.merge(dptoObj);
		t.commit();
		sesion.close();
	}

	@Override
	public void eliminarDpto(int dpto) {
		sesion = sF.openSession();
		Transaction t = sesion.beginTransaction();
		Departamentos dptoObj = sesion.get(Departamentos.class, dpto);
		sesion.remove(dptoObj);
		t.commit();
		sesion.close();
	}

	@Override
	public void eliminarDpto(Departamentos dpto) {
		sesion = sF.openSession();
		Transaction t = sesion.beginTransaction();
		sesion.remove(dpto);
		t.commit();
		sesion.close();
	}
	
	
	
	@Override
	public List<Departamentos> listarDptos() {
		sesion = sF.openSession();
		List<Departamentos> listado = sesion.createQuery("FROM Departamentos", Departamentos.class).list();
		sesion.close();
		return listado;
	}

	@Override
	public List<Departamentos> listarDptos(String localidad) {
		sesion = sF.openSession();
		List<Departamentos> listado = sesion.createQuery("FROM Departamentos WHERE loc=:localidad", Departamentos.class)
				.setParameter("localidad", localidad)
				.list();
		sesion.close();
		return listado;
	}

	@Override
	public Departamentos listarDptos(String nombre, String localidad) {
		sesion = sF.openSession();
		List<Departamentos> listado = sesion.createQuery("FROM Departamentos WHERE loc=:localidad AND dnombre=:nombre", Departamentos.class)
				.setParameter("localidad", localidad)
				.setParameter("nombre", nombre)
				.list();
		sesion.close();
		return listado.get(0);
	}

	@Override
	public List<Departamentos> listarDptosNombre(String nombre) {
		sesion = sF.openSession();
		List<Departamentos> listado = sesion.createQuery("FROM Departamentos WHERE dnombre=:nombre", Departamentos.class)
				.setParameter("nombre", nombre)
				.list();
		sesion.close();
		return listado;
	}

	@Override
	public Map<Integer, String> obtenerMapaDptos() {
		
		Map<Integer, String> mapa = new HashMap<>();
		sesion = sF.openSession();
		List<Tuple> tuplas = sesion.createSelectionQuery("SELECT d.deptNo as id, concat(d.dnombre, ' (', d.loc, ')') as dpto FROM Departamentos d", Tuple.class) 
				.list();
		for (Tuple t : tuplas) {
			mapa.put((Integer)t.get("id"), (String)t.get("dpto"));
		}
		sesion.close();
		return mapa;
	}

	@Override
	public Departamentos listarDptos(int id) {
		sesion = sF.openSession();
		Departamentos dpto = sesion.get(Departamentos.class, id);
		sesion.close();
		return dpto;
	}
}