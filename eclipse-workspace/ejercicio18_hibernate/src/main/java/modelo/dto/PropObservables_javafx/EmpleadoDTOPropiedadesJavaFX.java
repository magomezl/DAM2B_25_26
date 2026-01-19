package modelo.dto.PropObservables_javafx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmpleadoDTOPropiedadesJavaFX {
	private IntegerProperty empNum;
	private StringProperty empNombre;
	private StringProperty empApellido1;
	private StringProperty empApellido2;
	private IntegerProperty empDpto;
	
	public EmpleadoDTOPropiedadesJavaFX() {
	}

	public EmpleadoDTOPropiedadesJavaFX(int empNum, String empNombre, String empApellido1, String empApellido2, int empDpto) {
		this.empNum = new SimpleIntegerProperty(empNum);
		this.empNombre = new SimpleStringProperty(empNombre);
		this.empApellido1 = new SimpleStringProperty(empApellido1);
		this.empApellido2 = new SimpleStringProperty(empApellido2);
		this.empDpto = new SimpleIntegerProperty(empDpto);
	}

	public int getEmpNum() {
		return empNum.get();
	}

	public void setEmpNum(int empNum) {
		this.empNum.set(empNum);
	}

	public String getEmpNombre() {
		return empNombre.get();
	}

	public void setEmpNombre(String empNombre) {
		this.empNombre.set(empNombre);
	}

	public String getEmpApellido1() {
		return empApellido1.get();
	}

	public void setEmpApellido1(String empApellido1) {
		this.empApellido1.set(empApellido1);
	}

	public String getEmpApellido2() {
		return empApellido2.get();
	}

	public void setEmpApellido2(String empApellido2) {
		this.empApellido2.set(empApellido2);
	}

	public int getEmpDpto() {
		return empDpto.get();
	}

	public void setEmpDpto(int empDpto) {
		this.empDpto.set(empDpto);
	}
}
