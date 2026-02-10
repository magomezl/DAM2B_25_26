package modelo.dao.hibernate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import modelo.dto.hibernate.Autores;
import modelo.dto.hibernate.Generos;
import modelo.dto.hibernate.Libros;
import modelo.dto.hibernate.Nacionalidades;

public class HibernateDAOImpl implements HibernateDAO {
	private static SessionFactory sF = new Configuration().configure().buildSessionFactory();
	private static Session sesion; 
	

	@Override
	public boolean anadirGenero(Generos genero) {
		sesion = sF.openSession();
		Transaction t;
		if (sesion.createQuery("FROM Generos WHERE LOWER(nombre)=:genero", Generos.class)
			.setParameter("genero", genero.getNombre().toLowerCase())
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
	public int anadirAutores(List<Autores> autores) {
		int i = 0;
		for(Autores autor:  autores) {
			if (anadirAutor(autor)) i++;
		}
		return i;
	}
	@Override
	public boolean anadirAutor(Autores autor) {
		boolean retorno = false;
		sesion = sF.openSession();
		Transaction t = sesion.beginTransaction();
		try { 
			//comprobar que no existe el autor en la DB
			Autores autEnBD = sesion.createSelectionQuery("FROM Autores a WHERE LOWER(a.nombre) = :nombreA AND nacimiento = :anioNac", Autores.class)
					.setParameter("nombreA", autor.getNombre().toLowerCase())
					.setParameter("anioNac", autor.getNacimiento())
					.uniqueResult();
			if (autEnBD==null) { 
				Nacionalidades nacAutor = autor.getNacionalidades();
				if (nacAutor != null) {
					// Buscar la nacionalidad por su nombre
					Nacionalidades nacEnBD = sesion.createSelectionQuery("FROM Nacionalidades n WHERE LOWER(n.nombre) = :nombre", Nacionalidades.class)
							.setParameter("nombre", nacAutor.getNombre().toLowerCase())
							.uniqueResult();
					if (nacEnBD!=null) {
						// Existe -> lo usamos
						autor.setNacionalidades(nacEnBD);
					}else {
						// No Existe -> persistimos en la DB
						sesion.persist(nacAutor);
					}
					sesion.persist(autor);
					t.commit();
					retorno = true;
				}	
			}
		}catch( Exception e) {
			t.rollback();
			retorno = false;
			e.printStackTrace();
		}finally {
			sesion.close();
		}

		return retorno;
	}

	@Override
	public int anadirLibros(List<Libros> libros) {
		int i = 0;
		for(Libros libro:  libros) {
			if (anadirLibro(libro)) i++;
		}
		return i;
	}
	
	@Override
	public boolean anadirLibro(Libros libro) {
		boolean retorno = false;
		sesion = sF.openSession();
		Transaction t = sesion.beginTransaction();
		try { 
			//TODO que no se repita un libro que ya exista. Un libro se repie si tiene el mismo titulo y autores
			if (libro.getGeneros() != null) {
				Generos g = libro.getGeneros();
				// El libro tiene genero. Vamos a ver si ya existe en la DB
				Generos gBD = sesion.createSelectionQuery("FROM Generos g WHERE LOWER(g.nombre) = :nombre", Generos.class)
						.setParameter("nombre", g.getNombre().toLowerCase())
						.getSingleResultOrNull();
				if (gBD!=null) {
					// Existe -> lo usamos
					libro.setGeneros(gBD);
				}else {
					// No Existe -> persistimos en la DB
					sesion.persist(g);
				}
			}
			// Autores
			Set<Autores> autoresProcesados = new HashSet<>();
			if (libro.getAutoreses() != null) {
				for ( Object o: libro.getAutoreses()) {
					Autores autor = (Autores) o;
					//comprobar que no existe el autor en la DB
					Autores autEnBD = sesion.createSelectionQuery("FROM Autores a WHERE LOWER(a.nombre) = :nombreA AND a.nacimiento = :anioNac", Autores.class)
							.setParameter("nombreA", autor.getNombre().toLowerCase())
							.setParameter("anioNac", autor.getNacimiento())
							.uniqueResult();
					if (autEnBD==null) {
						// Autor no existe en DB. Miramos la nacionalidad y la gestionamos
						Nacionalidades nacAutor = autor.getNacionalidades();
						if (nacAutor != null) {
							// Buscar la nacionalidad por su nombre
							Nacionalidades nacEnBD = sesion.createSelectionQuery("FROM Nacionalidades n WHERE LOWER(n.nombre) = :nombre", Nacionalidades.class)
									.setParameter("nombre", nacAutor.getNombre().toLowerCase())
									.uniqueResult();
							if (nacEnBD!=null) {
								// Existe -> lo usamos
								autor.setNacionalidades(nacEnBD);
							}else {
								// No Existe -> persistimos en la DB
								sesion.persist(nacAutor);
							}
						}
						sesion.persist(autor);
						autoresProcesados.add(autor);
					}else {
						//Existe el autor
						autoresProcesados.add(autEnBD);
					}
				}
				libro.setAutoreses(autoresProcesados); 
			 }
			
			
			
			sesion.persist(libro);
			for (Autores au: autoresProcesados) {
				au.getLibroses().add(libro);
			}
			
			t.commit();
			
			// TODO falta inversa
			
			
			retorno = true;
			
		}catch( Exception e) {
			t.rollback();
			retorno = false;
			e.printStackTrace();
		}finally {
			sesion.close();
		}
		return retorno;
	}

	// <T> inicial indica que se trata de un método genérico. Dentro del método podemos usar un tipo variable llamado T
	@Override
	public <T> List<T> getAll(Class<T> entityClass) {
		try(Session sesion = sF.openSession()){
			String hql = "FROM " + entityClass.getSimpleName();
			return sesion.createSelectionQuery(hql, entityClass).getResultList();
		}
	}

	@Override
	public List<Autores> getAllAutoresConNacionalidad() {
		try(Session sesion = sF.openSession()){
			String hql = "FROM Autores LEFT JOIN FETCH nacionalidades"; 
			return sesion.createSelectionQuery(hql, Autores.class).getResultList();
		}
	}

	@Override
	public List<Autores> buscarAutoresDeLibro(String tituloLibro) {
		
		if (tituloLibro == null || tituloLibro.trim().isEmpty()) {
			return null;
		}
		try(Session sesion = sF.openSession()){
			String hql = "SELECT DISTINCT a FROM Libros l JOIN l.autoreses a"
					+ " LEFT JOIN FETCH a.nacionalidades "
					+ " WHERE LOWER(l.titulo) LIKE :patronTituloLibro";
					
			
			List<Autores> autores = sesion.createQuery(hql, Autores.class)
					.setParameter("patronTituloLibro", "%" + tituloLibro + "%")
					.getResultList();
			return autores;
		}
		
		
	}
}
