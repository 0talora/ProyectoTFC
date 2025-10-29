package org.otalora.dao;

import java.util.List;

import org.otalora.modelo.Campo;
import org.otalora.modelo.Usuario;

public interface ICampoDAO {
	
	public List<Campo> obtenerCampos();
	public Long contarCamposAdministrador(Usuario admin);
	public List<Campo> obtenerCamposAdministrador(Usuario admin);
	
}
