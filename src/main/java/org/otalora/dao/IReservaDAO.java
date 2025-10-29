package org.otalora.dao;

import java.util.List;

import org.otalora.modelo.Reserva;

public interface IReservaDAO {
	public boolean insertarReserva(Reserva reserva);
	public List<Reserva> obtenerReservas();
	public boolean cancelarReserva(Reserva reserva);

}
