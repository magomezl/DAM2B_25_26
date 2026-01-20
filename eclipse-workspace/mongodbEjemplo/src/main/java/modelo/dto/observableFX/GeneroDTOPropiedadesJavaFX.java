package modelo.dto.observableFX;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GeneroDTOPropiedadesJavaFX {
	private IntegerProperty generoNum;
	private StringProperty generoNombre;
	
	public GeneroDTOPropiedadesJavaFX() {
	}

	public GeneroDTOPropiedadesJavaFX(int generoNum, String generoNombre) {
		this.generoNum = new SimpleIntegerProperty(generoNum);
		this.generoNombre = new SimpleStringProperty(generoNombre);
	}
	
	public GeneroDTOPropiedadesJavaFX(String generoNombre) {
		this.generoNombre = new SimpleStringProperty(generoNombre);
	}

	public int getGeneroNum() {
		return generoNum.get();
	}

	public void setGeneroNum(int generoNum) {
		this.generoNum.set(generoNum);
	}

	public String getGeneroNombre() {
		return generoNombre.get();
	}

	public void setGeneroNombre(String generoNombre) {
		this.generoNombre.set(generoNombre);
	}
	
	
	
}
