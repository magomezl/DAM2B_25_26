package main;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import modelo.dao.AlumnoDAO;
import modelo.dao.AlumnoDAOImpl;
import modelo.dao.EmpresaDAO;
import modelo.dao.EmpresaDAOImpl;
import modelo.dto.AlumnoDTO;
import modelo.dto.EmpresaDTO;

public class MainApp {
	
	private static EmpresaDAO empDAO = new EmpresaDAOImpl();
	private static AlumnoDAO aluDAO = new AlumnoDAOImpl();
	private static final String RUTA_DOC_OUT = System.getProperty("user.dir") + System.getProperty("file.separator") 
	+ "src" + System.getProperty("file.separator") + "main" + System.getProperty("file.separator")
	+ "resources" + System.getProperty("file.separator") + "documento.xml";
	
	public static void main(String[] args) {
		
		for(EmpresaDTO emp: empDAO.leerEmpresas()) {
			empDAO.anadirEmpresa(emp);
		}
		
		for(AlumnoDTO alu: aluDAO.leeAlumno()) {
			aluDAO.anadirAlumno(alu);
		}
		
		System.out.println("DESPUES DE AÑADIR EMPRESAS");
		System.out.println("Empresas en la db");
		System.out.println(empDAO.listarEmpresas());
		System.out.println("Alumnos en la db");
		System.out.println(aluDAO.listarAlumnos());
		
		creaDocumentoDOM();

	}

	private static void creaDocumentoDOM() {
		DocumentBuilder dB;
		try {
			dB = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = dB.newDocument();
			
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			
			Element elementoRaiz = doc.createElement("agenda");
			doc.appendChild(elementoRaiz);
			
			Element eleEmpWrap = doc.createElement("empresas");
			elementoRaiz.appendChild(eleEmpWrap);
			
			Element eleAluSinWrap = doc.createElement("alumnos_sin_asignar");
			elementoRaiz.appendChild(eleAluSinWrap);
			
			for (EmpresaDTO emp: empDAO.listarEmpresas()) {
				Element eleEmp = empDAO.creaElementoEmpresa(doc, emp); 
				Element eleEmpAluWrap = doc.createElement("alumnos_asignados");
				for (AlumnoDTO alu: aluDAO.listarAlumnos(emp.getId_empresa())) {
					eleEmpAluWrap.appendChild(aluDAO.creaElementoAlumno(doc, alu));
				}
				eleEmp.appendChild(eleEmpAluWrap);
				eleEmpWrap.appendChild(eleEmp);
			}
			
			for (AlumnoDTO alu: aluDAO.listarAlumnos(0)) {
				eleAluSinWrap.appendChild(aluDAO.creaElementoAlumno(doc, alu));
			}
			
			t.transform(new DOMSource(doc), new StreamResult(new File(RUTA_DOC_OUT)));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
