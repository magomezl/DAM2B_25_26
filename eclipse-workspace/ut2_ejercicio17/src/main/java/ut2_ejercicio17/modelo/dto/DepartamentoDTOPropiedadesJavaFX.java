package ut2_ejercicio17.modelo.dto;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DepartamentoDTOPropiedadesJavaFX {
	private IntegerProperty depNum;
	private StringProperty depNombre;
	private StringProperty depLocalidad;
	
	public DepartamentoDTOPropiedadesJavaFX() {
	}

	public DepartamentoDTOPropiedadesJavaFX(int depNum, String depNombre, String depLocalidad) {
		this.depNum = new SimpleIntegerProperty(depNum);
		this.depNombre = new SimpleStringProperty(depNombre);
		this.depLocalidad = new SimpleStringProperty(depLocalidad);
	}

	public int getDepNum() {
		return depNum.get();
	}

	public void setDepNum(int depNum) {
		this.depNum.set(depNum);
	}

	public String getDepNombre() {
		return depNombre.get();
	}

	public void setDepNombre(String depNombre) {
		this.depNombre.set(depNombre);
	}

	public String getDepLocalidad() {
		return depLocalidad.get();
	}

	public void setDepLocalidad(String depLocalidad) {
		this.depLocalidad.set(depLocalidad); 
	}

	@Override
	public String toString() {
		return "DepartamentoDTO [depNum=" + depNum + ", depNombre=" + depNombre + ", depLocalidad=" + depLocalidad
				+ "]";
	}
	
	
	
	

}
