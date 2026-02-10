package modelo.dto.observableFX;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AutorDTOPropiedadesJavaFX {
	private IntegerProperty num;
	private StringProperty nombre;
	private StringProperty nacionalidad;
	private IntegerProperty nacimiento;
	private IntegerProperty muerte;
	
	public AutorDTOPropiedadesJavaFX() {
	}

	public AutorDTOPropiedadesJavaFX(int num, String nombre, String nacionalidad, int nacimiento, int muerte) {
		
		this.num = new SimpleIntegerProperty(num);
		this.nombre = new SimpleStringProperty(nombre);
		this.nacionalidad = new SimpleStringProperty(nacionalidad);
		this.nacimiento = new SimpleIntegerProperty(nacimiento);
		this.muerte = new SimpleIntegerProperty(muerte);
	}

	public int getNum() {
		return num.get();
	}

	public void setNum(int num) {
		this.num.set(num);
	}

	public String getNombre() {
		return nombre.get();
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public String getNacionalidad() {
		return nacionalidad.get();
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad.set(nacionalidad);
	}

	public int getNacimiento() {
		return nacimiento.get();
	}

	public void setNacimiento(int nacimiento) {
		this.nacimiento.set(nacimiento);
	}

	public int getMuerte() {
		return muerte.get();
	}

	public void setMuerte(int muerte) {
		this.muerte.set(muerte);
	}
}
