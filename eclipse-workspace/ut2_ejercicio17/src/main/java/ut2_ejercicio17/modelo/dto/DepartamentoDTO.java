package ut2_ejercicio17.modelo.dto;

public class DepartamentoDTO {
	private int depNum;
	private String depNombre;
	private String depLocalidad;
	
	public DepartamentoDTO() {
		super();
	}

	public DepartamentoDTO(int depNum, String depNombre, String depLocalidad) {
		super();
		this.depNum = depNum;
		this.depNombre = depNombre;
		this.depLocalidad = depLocalidad;
	}

	public int getDepNum() {
		return depNum;
	}

	public void setDepNum(int depNum) {
		this.depNum = depNum;
	}

	public String getDepNombre() {
		return depNombre;
	}

	public void setDepNombre(String depNombre) {
		this.depNombre = depNombre;
	}

	public String getDepLocalidad() {
		return depLocalidad;
	}

	public void setDepLocalidad(String depLocalidad) {
		this.depLocalidad = depLocalidad;
	}

	@Override
	public String toString() {
		return "DepartamentoDTO [depNum=" + depNum + ", depNombre=" + depNombre + ", depLocalidad=" + depLocalidad
				+ "]\n";
	}
	
	
	
	

}
