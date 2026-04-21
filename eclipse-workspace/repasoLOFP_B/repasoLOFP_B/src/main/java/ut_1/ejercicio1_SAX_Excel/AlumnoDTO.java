package ut_1.ejercicio1_SAX_Excel;

public class AlumnoDTO {
	
	private String id;
	private String nombre;
	private String curso;
	private double cuota;
	private String iban;
	private String banco;

	private double totalPagado = 0;
	private double totalPendiente = 0;
	private int cuotasPendientes = 0;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCurso() {
		return curso;
	}
	public void setCurso(String curso) {
		this.curso = curso;
	}
	public double getCuota() {
		return cuota;
	}
	public void setCuota(double cuota) {
		this.cuota = cuota;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public double getTotalPagado() {
		return totalPagado;
	}
	public void setTotalPagado(double totalPagado) {
		this.totalPagado = totalPagado;
	}
	public double getTotalPendiente() {
		return totalPendiente;
	}
	public void setTotalPendiente(double totalPendiente) {
		this.totalPendiente = totalPendiente;
	}
	public int getCuotasPendientes() {
		return cuotasPendientes;
	}
	public void setCuotasPendientes(int cuotasPendientes) {
		this.cuotasPendientes = cuotasPendientes;
	}
	@Override
	public String toString() {
		return "\nAlumnoDTO [id=" + id + ", nombre=" + nombre + ", curso=" + curso + ", cuota=" + cuota + ", iban=" + iban
				+ ", banco=" + banco + ", totalPagado=" + totalPagado + ", totalPendiente=" + totalPendiente
				+ ", cuotasPendientes=" + cuotasPendientes + "]";
	}
	
}
