package modelo.dto;

public class EmpresaDTO {
	private int id_empresa;
	private String nombre;
	private String direccion;
	private String telefono;
	private String persona_contacto;
	private String email;
	
	public EmpresaDTO() {
	
	}
	
	public EmpresaDTO(String nombre) {
		this.nombre = nombre;
		this.direccion = "DESCONOCIDO";
		this.telefono = "DESCONOCIDO";
		this.persona_contacto = "DESCONOCIDO";
		this.email = "DESCONOCIDO";
	}
	
	

	public EmpresaDTO(int id_empresa, String nombre, String direccion, String telefono, String persona_contacto,
			String email) {
		this.id_empresa = id_empresa;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.persona_contacto = persona_contacto;
		this.email = email;
	}

	public EmpresaDTO(String nombre, String direccion, String telefono, String persona_contacto,
			String email) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.persona_contacto = persona_contacto;
		this.email = email;
	}

	public int getId_empresa() {
		return id_empresa;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getPersona_contacto() {
		return persona_contacto;
	}

	public void setPersona_contacto(String persona_contacto) {
		this.persona_contacto = persona_contacto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "EmpresaDTO [id_empresa=" + id_empresa + ", nombre=" + nombre + ", direccion=" + direccion
				+ ", telefono=" + telefono + ", persona_contacto=" + persona_contacto + ", email=" + email + "]\n";
	}
}
