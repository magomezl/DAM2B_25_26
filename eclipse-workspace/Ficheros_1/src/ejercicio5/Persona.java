package ejercicio5;

import java.io.Serializable;
import java.time.LocalDate;

public class Persona implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7087622712910013542L;
	
	private StringBuilder nombre;
	private StringBuilder apellido1;
	private StringBuilder apellido2;
	private LocalDate nacimiento;
	 
	public Persona() {
	}

	public Persona(StringBuilder nombre, StringBuilder apellido1, StringBuilder apellido2, LocalDate nacimiento) {
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.nacimiento = nacimiento;
	}

	public StringBuilder getNombre() {
		return nombre;
	}

	public void setNombre(StringBuilder nombre) {
		this.nombre = nombre;
	}

	public StringBuilder getApellido1() {
		return apellido1;
	}

	public void setApellido1(StringBuilder apellido1) {
		this.apellido1 = apellido1;
	}

	public StringBuilder getApellido2() {
		return apellido2;
	}

	public void setApellido2(StringBuilder apellido2) {
		this.apellido2 = apellido2;
	}

	public LocalDate getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(LocalDate nacimiento) {
		this.nacimiento = nacimiento;
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", nacimiento="
				+ nacimiento + "]";
	}

	
	
	
}
