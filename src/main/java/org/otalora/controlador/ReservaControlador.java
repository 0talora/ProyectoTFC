package org.otalora.controlador;

import java.util.List;

import org.otalora.dao.impl.EventoDAO;
import org.otalora.dao.impl.ReservaDAO;
import org.otalora.modelo.Evento;
import org.otalora.modelo.Reserva;
import org.otalora.modelo.Usuario;

public class ReservaControlador {
	public boolean reservar(Evento evento, int numPersonas) {
		
		ReservaDAO reservaDAO = new ReservaDAO();
		EventoDAO eventoDAO = new EventoDAO();
		
		Reserva reserva = crearReserva(evento, numPersonas);
		boolean reservaCreada=reservaDAO.insertarReserva(reserva);
		if(reservaCreada) {
			return eventoDAO.descontarPlazas(evento.getId(),numPersonas);
		}
		return false;
		
	}
	
	private Reserva crearReserva(Evento evento,int numPersonas) {
		Usuario usuarioActual = UsuarioSesion.getInstancia().getUsuarioActual();
		
		Reserva reserva = new Reserva();
		reserva.setEvento(evento);
		reserva.setUsuario(usuarioActual);
		reserva.setNumeroPersonas(numPersonas);
		return reserva;
	}
	public List<Reserva> verReservas(){
		
		ReservaDAO reservaDAO = new ReservaDAO();
		return reservaDAO.obtenerReservas();
		
	}
	
	public boolean cancelarReserva(Reserva reserva) {
		
		ReservaDAO reservaDAO = new ReservaDAO();
		EventoDAO eventoDAO = new EventoDAO();

		boolean reservaCancelada= reservaDAO.cancelarReserva(reserva);
		if(reservaCancelada) {
			return eventoDAO.devolverPlazas(reserva.getEvento().getId(),reserva.getNumeroPersonas());
		}
		return false;
	}
	
}
