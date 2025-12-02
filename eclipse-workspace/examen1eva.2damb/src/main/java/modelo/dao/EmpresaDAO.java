package modelo.dao;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import modelo.dto.AlumnoDTO;
import modelo.dto.EmpresaDTO;

public interface EmpresaDAO {
	int anadirEmpresa(EmpresaDTO empresa);
	ArrayList<EmpresaDTO> listarEmpresas();
	
	ArrayList<EmpresaDTO> leerEmpresas();
	Element creaElementoEmpresa(Document doc, EmpresaDTO emp);
}
