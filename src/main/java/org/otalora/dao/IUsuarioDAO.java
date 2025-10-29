package org.otalora.dao;

import org.otalora.modelo.Usuario;

public interface IUsuarioDAO {
	public Usuario obtenerUsuario(String nick);

	public void crearEntidad(Usuario usuario);

	public boolean verificarActivo(Usuario usuario);
		
	public  boolean verificarNick(String nick);

	public boolean verificarExistenciaCorreo(String correo);
}
