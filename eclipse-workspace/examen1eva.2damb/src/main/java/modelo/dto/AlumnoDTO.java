package modelo.dto;

public class AlumnoDTO {
	private int id_alumno;
	private String nombre;
	private String apellidos;
	private String ciclo;
	private String curso;
	private int id_empresa;

	public AlumnoDTO() {
	}

	public AlumnoDTO(String nombre, String apellidos, String ciclo, String curso, int id_empresa) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.ciclo = ciclo;
		this.curso = curso;
		this.id_empresa = id_empresa;
	}

	
	public int getId_alumno() {
		return id_alumno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public int getId_empresa() {
		return id_empresa;
	}

	public void setId_empresa(int id_empresa) {
		this.id_empresa = id_empresa;
	}

	@Override
	public String toString() {
		return "AlumnoDTO [id_alumno=" + id_alumno + ", nombre=" + nombre + ", apellidos=" + apellidos + ", ciclo="
				+ ciclo + ", curso=" + curso + ", id_empresa=" + id_empresa + "]\n";
	}
	
}
