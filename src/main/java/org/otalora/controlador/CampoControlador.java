package org.otalora.controlador;

import java.util.List;

import org.otalora.dao.impl.CampoDAO;
import org.otalora.modelo.Campo;
import org.otalora.modelo.Usuario;

public class CampoControlador {
	
	public List<Campo> obtenerCampos() {
		CampoDAO campoDAO=new CampoDAO();
		return campoDAO.obtenerCampos();
	}
	public int contarCamposAdministrador() {
		CampoDAO campoDAO=new CampoDAO();
		Usuario admin =UsuarioSesion.getInstancia().getUsuarioActual();
	    Long cantidad = campoDAO.contarCamposAdministrador(admin);
	    return cantidad.intValue();
	}
	public List<Campo> obtenerCamposAdministrador() {
		CampoDAO campoDAO=new CampoDAO();
		Usuario admin =UsuarioSesion.getInstancia().getUsuarioActual();
		
		return campoDAO.obtenerCamposAdministrador(admin);
	}
}
