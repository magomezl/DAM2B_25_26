package ut2_ejercicio17.modelo.dto;

public class EmpleadoDTO {
	private int empNum;
	private String empNombre;
	private String empApellido1;
	private String empApellido2;
	private int empDpto;
	
	public EmpleadoDTO() {
	}

	public EmpleadoDTO(int empNum, String empNombre, String empApellido1, String empApellido2, int empDpto) {
		this.empNum = empNum;
		this.empNombre = empNombre;
		this.empApellido1 = empApellido1;
		this.empApellido2 = empApellido2;
		this.empDpto = empDpto;
	}

	public int getEmpNum() {
		return empNum;
	}

	public void setEmpNum(int empNum) {
		this.empNum = empNum;
	}

	public String getEmpNombre() {
		return empNombre;
	}

	public void setEmpNombre(String empNombre) {
		this.empNombre = empNombre;
	}

	public String getEmpApellido1() {
		return empApellido1;
	}

	public void setEmpApellido1(String empApellido1) {
		this.empApellido1 = empApellido1;
	}

	public String getEmpApellido2() {
		return empApellido2;
	}

	public void setEmpApellido2(String empApellido2) {
		this.empApellido2 = empApellido2;
	}

	public int getEmpDpto() {
		return empDpto;
	}

	public void setEmpDpto(int empDpto) {
		this.empDpto = empDpto;
	}

	@Override
	public String toString() {
		return "EmpleadoDTO [empNum=" + empNum + ", empNombre=" + empNombre + ", empApellido1=" + empApellido1
				+ ", empApellido2=" + empApellido2 + ", empDpto=" + empDpto + "]";
	}
	
	
	

}
