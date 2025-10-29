package org.otalora.controlador;

import java.util.List;

import org.otalora.dao.impl.EventoDAO;
import org.otalora.modelo.Evento;
import org.otalora.modelo.Usuario;

public class EventoControlador {
	
	public List<Evento> obtenerEventos(){
        EventoDAO eventoDAO = new EventoDAO();
		return eventoDAO.obtenerEventos();
	}
	
	public List<Evento> obtenerEventosAdministrador(){
        EventoDAO eventoDAO = new EventoDAO();
		Usuario admin =UsuarioSesion.getInstancia().getUsuarioActual();

		return eventoDAO.obtenerEventosAdministrador(admin);
	}
	
	public boolean crearEvento(Evento Evento) {
        EventoDAO eventoDAO = new EventoDAO();
        return eventoDAO.crearEvento(Evento);    
	}
	public boolean cancelarEvento(Evento evento) {
		EventoDAO eventoDAO = new EventoDAO();
		boolean eventoCancelado = eventoDAO.cancelarEvento(evento);
		if(eventoCancelado) {
			return true;
		}
		return false;
		
	}
}
