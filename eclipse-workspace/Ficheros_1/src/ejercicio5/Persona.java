package ejercicio5;

import java.io.Serializable;
import java.time.LocalDate;

public class Persona implements Serializable{
	
	private static final long serialVersionUID = -2099173592037393849L;
	
	private String nombre;
	private String apellido1;
	private String apellido2;
	private LocalDate nacimiento;
	 
	public Persona() {
	}

	public Persona(String nombre, String apellido1, String apellido2, LocalDate nacimiento) {
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.nacimiento = nacimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public LocalDate getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(LocalDate nacimiento) {
		this.nacimiento = nacimiento;
	}
}
