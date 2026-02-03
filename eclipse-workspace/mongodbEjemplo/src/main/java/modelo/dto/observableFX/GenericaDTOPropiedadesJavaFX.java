package modelo.dto.observableFX;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GenericaDTOPropiedadesJavaFX {
	private IntegerProperty genericaNum;
	private StringProperty genericaNombre;
	
	public GenericaDTOPropiedadesJavaFX() {
	}

	public GenericaDTOPropiedadesJavaFX(int genericaNum, String genericaNombre) {
		this.genericaNum = new SimpleIntegerProperty(genericaNum);
		this.genericaNombre = new SimpleStringProperty(genericaNombre);
	}
	
	public GenericaDTOPropiedadesJavaFX(String genericaNombre) {
		this.genericaNombre = new SimpleStringProperty(genericaNombre);
	}

	public Integer getGenericaNum() {
		return genericaNum.get();
	}

	public void setGenericaNum(Integer genericaNum) {
		this.genericaNum.set(genericaNum);
	}

	public String getGenericaNombre() {
		return genericaNombre.get();
	}

	public void setGenericaNombre(String genericaNombre) {
		this.genericaNombre.set(genericaNombre);
	}

		
}
