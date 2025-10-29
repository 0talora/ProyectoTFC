package org.otalora.controlador;

import org.otalora.modelo.Usuario;

public class UsuarioSesion {
	private static UsuarioSesion instancia;
	private Usuario usuarioActual;
	
	private UsuarioSesion() {
		
	}
	public static synchronized UsuarioSesion getInstancia() {
		if(instancia==null) {
			instancia= new UsuarioSesion();
		}
		return instancia;
	}
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual=usuarioActual;
	}
	
	public void cerrarSesion() {
		usuarioActual=null;
	}
	
}