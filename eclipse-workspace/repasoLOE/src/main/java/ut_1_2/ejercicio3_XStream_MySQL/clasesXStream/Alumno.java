package ut_1_2.ejercicio3_XStream_MySQL.clasesXStream;

import java.util.List;

public class Alumno {

	private String id;
    private String nombre;
    private String curso;
    private double cuota;
    private Domiciliacion domiciliacion;
    private List<Pago> pagos;

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCurso() { return curso; }
    public double getCuota() { return cuota; }
    public Domiciliacion getDomiciliacion() { return domiciliacion; }
    public List<Pago> getPagos() { return pagos; }
	@Override
	public String toString() {
		return "Alumno [id=" + id + ", nombre=" + nombre + ", curso=" + curso + ", cuota=" + cuota + ", domiciliacion="
				+ domiciliacion + ", pagos=" + pagos + "]";
	}
    
    

}
