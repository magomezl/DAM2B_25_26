package ut_1_2.ejercicio3_XStream_MySQL;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;

import ut_1_2.ejercicio3_XStream_MySQL.clasesXStream.Academia;
import ut_1_2.ejercicio3_XStream_MySQL.clasesXStream.Alumno;
import ut_1_2.ejercicio3_XStream_MySQL.clasesXStream.Domiciliacion;
import ut_1_2.ejercicio3_XStream_MySQL.clasesXStream.Pago;

public class Importar_XStream_MySQL {

	public static void main(String[] args) {
		try(Connection con = getConexion()){
			XStream xs = new XStream(new DomDriver("UTF-8"));
			
			// IMPORTANTE. Damos permiso a XStream para que acceda a las clases que hemos creado
			xs.addPermission(AnyTypePermission.ANY);
			xs.alias("academia", Academia.class);
			xs.alias("alumno", Alumno.class);
			xs.alias("domiciliacion", Domiciliacion.class);
			xs.alias("pago", Pago.class);
			
			xs.addImplicitCollection(Academia.class, "alumnos");
			
			xs.useAttributeFor(Alumno.class, "id");
			
			xs.useAttributeFor(Pago.class, "mes");
			xs.useAttributeFor(Pago.class, "estado");
			xs.useAttributeFor(Pago.class, "importe");
			
			InputStream is = Importar_XStream_MySQL.class
					.getClassLoader()
					.getResourceAsStream("resources/Files/academia.xml");
			
			Academia academia = (Academia) xs.fromXML(is);
			
			
			for(Alumno a: academia.getAlumno()) {
				// Hacemos referencia a los campos de la tabla alumnos 
				PreparedStatement psAlumno = con.prepareStatement(
						"INSERT INTO alumnos (id, nombre, iban, banco) VALUES (?, ?, ?, ?)", 
						Statement.RETURN_GENERATED_KEYS);
				
				psAlumno.setString(1, a.getId());
				psAlumno.setString(2, a.getNombre());
				psAlumno.setString(3, a.getDomiciliacion().getIban());
				psAlumno.setString(4, a.getDomiciliacion().getBanco());
				psAlumno.executeUpdate();
				
				// Para recuperar el valor del codigo autonumérico que se genera al insertar el alumno en la tabla alumnos
				ResultSet rs = psAlumno.getGeneratedKeys();
				rs.next();
				int alumnoCodigo = rs.getInt(1);
				
				// Insertamos el curso del alumno (si no está). Con INSERT IGNORE evitamos duplicados 
				PreparedStatement psCurso = con.prepareStatement(
						"INSERT IGNORE INTO cursos (denominacion, cuota_mensual) VALUES (?, ?)");
				psCurso.setString(1, a.getCurso());
				psCurso.setDouble(2, a.getCuota());
				psCurso.executeUpdate();
				
				//Buscamos y obtenemos el id del curso porque puede que ya exista y no se haya almacenado en la sentencia anterior
				PreparedStatement psCursoId = con.prepareStatement(
						"SELECT id FROM cursos WHERE denominacion = ?");
				psCursoId.setString(1, a.getCurso());
				ResultSet rsCurso = psCursoId.executeQuery();
				rsCurso.next();
				int cursoId = rsCurso.getInt(1);
				
				// Insertar matrícula
				PreparedStatement psMatricula = con.prepareStatement(
						"INSERT INTO matriculas (alumno_id, curso_id) VALUES (?, ?)");
				psMatricula.setInt(1, alumnoCodigo);
				psMatricula.setInt(2, cursoId);
				psMatricula.executeUpdate();
				
				if (a.getPagos()!= null) {
					for (Pago p: a.getPagos()) {
						PreparedStatement psPago = con.prepareStatement(
								"INSERT INTO pagos (alumno_id, curso_id, mes, estado, importe) " +
								"VALUES (?, ?, ?, ?, ?)");

						psPago.setInt(1, alumnoCodigo);
						psPago.setInt(2, cursoId);
						psPago.setString(3, p.getMes());
						psPago.setBoolean(4, p.isPagado());
						psPago.setDouble(5, p.getImporte());
						psPago.executeUpdate();
					}
				}
			}
			System.out.println("Datos Importados correctamente");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConexion() {
		try {
			return DriverManager.getConnection("jdbc:mysql://10.196.55.168:3306/academia", "root", "toor");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}