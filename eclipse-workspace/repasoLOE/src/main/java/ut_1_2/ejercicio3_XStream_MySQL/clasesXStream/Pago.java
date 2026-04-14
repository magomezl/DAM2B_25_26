package ut_1_2.ejercicio3_XStream_MySQL.clasesXStream;

public class Pago {

	private String mes;
    private String estado;
    private double importe;

    public String getMes() { return mes; }
    public boolean isPagado() {
        return estado.equalsIgnoreCase("pagado");
    }
    public double getImporte() { return importe; }

}
