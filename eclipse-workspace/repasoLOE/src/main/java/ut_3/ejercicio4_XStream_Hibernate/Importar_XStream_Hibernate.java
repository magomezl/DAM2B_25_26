package ut_3.ejercicio4_XStream_Hibernate;

import java.io.InputStream;
import java.math.BigDecimal;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import clasesHibernate.Alumnos;
import clasesHibernate.Cursos;
import clasesHibernate.Matriculas;
import clasesHibernate.Pagos;
import ut_1_2.ejercicio3_XStream_MySQL.clasesXStream.Academia;
import ut_1_2.ejercicio3_XStream_MySQL.clasesXStream.Alumno;
import ut_1_2.ejercicio3_XStream_MySQL.clasesXStream.Domiciliacion;
import ut_1_2.ejercicio3_XStream_MySQL.clasesXStream.Pago;

// He corregido el error que nos daba que era de permisos, pero mañana, si puedo, depuraré alguna cosa que no aún se puede pulir
public class Importar_XStream_Hibernate {
	private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
	
	public static void main(String[] args) {
		// Cargar XML con XStream
		XStream xs = new XStream(new DomDriver("UTF-8"));
		// IMPORTANTE. Damos permiso a XStream para que acceda a las clases que hemos creado
		xs.addPermission(AnyTypePermission.ANY);
		// Definimos las reglas de correspondencia entre las clases XStream y el fichero xml 
		xs.alias("academia", Academia.class);
		xs.alias("alumno", Alumno.class);
		xs.alias("domiciliacion", Domiciliacion.class);
		xs.alias("pago", Pago.class);

		xs.addImplicitCollection(Academia.class, "alumnos");

		xs.useAttributeFor(Alumno.class, "id");

		xs.useAttributeFor(Pago.class, "mes");
		xs.useAttributeFor(Pago.class, "estado");
		xs.useAttributeFor(Pago.class, "importe");

		InputStream is = Importar_XStream_Hibernate.class
				.getClassLoader()
				.getResourceAsStream("resources/Files/academia.xml");

		Academia academia = (Academia) xs.fromXML(is);

		Session session = sessionFactory.openSession();
		// Procesar alumnos
		for (Alumno a : academia.getAlumno()) {
			// Insertar alumno
			Transaction tx = session.beginTransaction();
			Alumnos alumnoHibernate = new Alumnos();
			alumnoHibernate.setId(a.getId());
			alumnoHibernate.setNombre(a.getNombre());
			alumnoHibernate.setIban(a.getDomiciliacion().getIban());
			alumnoHibernate.setBanco(a.getDomiciliacion().getBanco());

			session.persist(alumnoHibernate);
			
			
			Cursos curso = (Cursos) session.createQuery(
					"FROM Cursos c WHERE c.denominacion = :curso")
					.setParameter("curso", a.getCurso())
					.uniqueResult();

			if (curso == null) {
				curso = new Cursos();
				curso.setDenominacion(a.getCurso());
				curso.setCuotaMensual(BigDecimal.valueOf(a.getCuota()));
				session.persist(curso);
			}

			Matriculas matricula = new Matriculas();
			matricula.setAlumnos(alumnoHibernate);
			matricula.setCursos(curso);
			session.persist(matricula);
			
			for (Pago p : a.getPagos()) {
				Pagos pagoHibernate = new Pagos();
				pagoHibernate.setAlumnos(alumnoHibernate);
				pagoHibernate.setCursos(curso);
				pagoHibernate.setMes(p.getMes());
				pagoHibernate.setEstado(p.isPagado());
				pagoHibernate.setImporte(BigDecimal.valueOf(p.getImporte()));
				session.persist(pagoHibernate);
			}
			tx.commit();
		}

		
		session.close();
		sessionFactory.close();

		System.out.println("Datos insertados correctamente usando Hibernate");

	}
}
