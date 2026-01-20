package modelo.dao.hibernate;

import java.util.List;

import modelo.dto.hibernate.Generos;

public interface HibernateDAO {
	/**
	 * Añade un genero a la tabla generos de la DB MySQL Biblitocea usando Hibernate 
	 * @param genero 
	 * @return true si se añadió con éxito
	 */
	boolean anadirGenero(Generos genero);
	/**
	 * Añade una lista de generos a la tabla generos 
	 * @param generos
	 * @return número de generos añadidos
	 */
	int anadirGenero(List<String> generos);
	

}
