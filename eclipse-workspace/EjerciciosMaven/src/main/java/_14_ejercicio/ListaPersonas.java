package _14_ejercicio;

import java.util.ArrayList;

import ejercicio5.Persona;

public class ListaPersonas {
	private ArrayList<Persona> lista = new ArrayList<Persona>();

	public void anadir(Persona person) {
		lista.add(person);
	}

	public ArrayList<Persona> getLista() {
		return lista;
	}
}
