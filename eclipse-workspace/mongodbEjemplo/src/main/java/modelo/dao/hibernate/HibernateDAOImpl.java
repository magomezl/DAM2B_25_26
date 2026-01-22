package modelo.dao.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import modelo.dto.hibernate.Autores;
import modelo.dto.hibernate.Generos;
import modelo.dto.hibernate.Libros;

public class HibernateDAOImpl implements HibernateDAO {
	private static SessionFactory sF = new Configuration().configure().buildSessionFactory();
	private static Session sesion; 
	

	@Override
	public boolean anadirGenero(Generos genero) {
		sesion = sF.openSession();
		Transaction t;
		if (sesion.createQuery("FROM Generos WHERE nombre=:genero", Generos.class)
			.setParameter("genero", genero.getNombre())
			.list()
			.isEmpty()) {
			t = sesion.beginTransaction();
			sesion.persist(genero);
			t.commit();
			sesion.close();
			return true;
		}
		sesion.close();
		return false;
	}

	@Override
	public int anadirGenero(List<String> generos) {
		int i = 0;
		for (String genero: generos) {
			Generos generoObj = new Generos(genero);
			if (anadirGenero(generoObj)) {
				i++;
			}
		}
		return i;
	}

	@Override
	public void anadirAutores(List<Autores> autores) {
		
		
	}

	@Override
	public void anadirLibros(List<Libros> libros) {
		// TODO Auto-generated method stub
		
	}
}
