package org.otalora.dao;

import java.util.List;

import org.otalora.modelo.Evento;
import org.otalora.modelo.Usuario;

public interface IEventoDAO {

	public boolean crearEvento(Evento evento);
	public List<Evento> obtenerEventos();
	public List<Evento> obtenerEventosAdministrador(Usuario admin);
	public boolean descontarPlazas(long eventoId,int numPersonas);
	public boolean devolverPlazas(long eventoId, int numPersonas);
	public boolean cancelarEvento(Evento evento);
	
}
