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

// He corregido el error que nos daba que era de permisos, pero mañana, si puedo, depuraré alguna cosa que no aún se puede pulir
public class Importar_XStream_MySQL {

	public static void main(String[] args) {

		try (Connection con = getConexion()) {

			// Cargar XML con XStream
			XStream xs = new XStream(new DomDriver("UTF-8"));
			
			// Esta línea faltaba y por eso nos daba error. Se trata de dar permisos para que XStream pueda acceder a las clases que hemos creado
			xs.addPermission(AnyTypePermission.ANY);
			xs.alias("academia", Academia.class);
			//Esto ya que no tenemos un objeto que agrupe a los alumnos
			xs.addImplicitCollection(Academia.class, "alumnos");
			xs.alias("alumno", Alumno.class);
			xs.alias("domiciliacion", Domiciliacion.class);
			xs.alias("pago", Pago.class);
			// No habiamos puesto que id era atributo
			xs.useAttributeFor(Alumno.class, "id");
			InputStream is = Importar_XStream_MySQL.class
					.getClassLoader()
					.getResourceAsStream("resources/Files/academia.xml");

			Academia academia = (Academia) xs.fromXML(is);

			// Procesar alumnos
			for (Alumno a : academia.getAlumno()) {
				
				// Insertar alumno
				PreparedStatement psAlumno = con.prepareStatement(
						"INSERT INTO alumnos (id, nombre, iban, banco) VALUES (?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);

				psAlumno.setString(1, a.getId());
				psAlumno.setString(2, a.getNombre());
				psAlumno.setString(3, a.getDomiciliacion().getIban());
				psAlumno.setString(4, a.getDomiciliacion().getBanco());
				psAlumno.executeUpdate();

				ResultSet rs = psAlumno.getGeneratedKeys();
				rs.next();
				int alumnoId = rs.getInt(1);

				// Insertar curso (si no existe). Con INSERT IGNORE evitamos duplicados y no lanza ERROR simplemente ignora la sentencia
				PreparedStatement psCurso = con.prepareStatement(
						"INSERT IGNORE INTO cursos (denominacion, cuota_mensual) VALUES (?, ?)");

				psCurso.setString(1, a.getCurso());
				psCurso.setDouble(2, a.getCuota());
				psCurso.executeUpdate();

				// Buscamos y Obtenemos id del curso porque puede que ya exista
				PreparedStatement psCursoId = con.prepareStatement(
						"SELECT id FROM cursos WHERE denominacion = ?");
				psCursoId.setString(1, a.getCurso());
				ResultSet rsCurso = psCursoId.executeQuery();
				rsCurso.next();
				int cursoId = rsCurso.getInt("id");

				// Insertar matrícula
				PreparedStatement psMatricula = con.prepareStatement(
						"INSERT INTO matriculas (alumno_id, curso_id) VALUES (?, ?)");
				psMatricula.setInt(1, alumnoId);
				psMatricula.setInt(2, cursoId);
				psMatricula.executeUpdate();

				// Insertar pagos. Repasar esta parte
				if (a.getPagos() != null) {
					for (Pago p : a.getPagos()) {
						PreparedStatement psPago = con.prepareStatement(
								"INSERT INTO pagos (alumno_id, curso_id, mes, estado, importe) " +
								"VALUES (?, ?, ?, ?, ?)");

						psPago.setInt(1, alumnoId);
						psPago.setInt(2, cursoId);
						psPago.setString(3, "desconocido");
						psPago.setBoolean(4, true);
						psPago.setDouble(5, p.getImporte());
						psPago.executeUpdate();
					}
				}
			}

			System.out.println("Datos importados correctamente en la base de datos");

		} catch (Exception e) {
			e.printStackTrace();
		}


	}


	public static Connection getConexion() throws Exception {
		String url = "jdbc:mysql://10.196.55.168:3306/academia";
		String user = "root";
		String password = "toor"; 

		return DriverManager.getConnection(url, user, password);
	}

	
}
