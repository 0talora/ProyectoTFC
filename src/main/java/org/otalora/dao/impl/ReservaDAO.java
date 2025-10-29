package org.otalora.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.otalora.controlador.UsuarioSesion;
import org.otalora.dao.IReservaDAO;
import org.otalora.modelo.Reserva;
import org.otalora.modelo.Usuario;
import org.otalora.util.GestorConexionHibernate;

public class ReservaDAO implements IReservaDAO{

	@Override
	public boolean insertarReserva(Reserva reserva) {
		// TODO Auto-generated method stub
		try (SessionFactory sessionFactory = new GestorConexionHibernate().getSessionFactory();Session ss = sessionFactory.openSession()){
			Transaction transaction = ss.beginTransaction();
			ss.save(reserva);
			transaction.commit();
			System.out.println("reserva "+reserva+" creada");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Reserva> obtenerReservas() {
		// TODO Auto-generated method stub
		List<Reserva>reservas=new ArrayList<Reserva>();
		Usuario usuario=UsuarioSesion.getInstancia().getUsuarioActual();
		
		try (SessionFactory sessionFactory = new GestorConexionHibernate().getSessionFactory();Session ss = sessionFactory.openSession()){
			reservas=ss.createQuery("FROM Reserva r WHERE r.usuario.id = :usuarioId ORDER BY r.fechaHoraReserva DESC",Reserva.class).setParameter("usuarioId", usuario.getId()).getResultList();
		} catch (Exception e) {
			// TODO: handle exception
	        e.printStackTrace();

		}
		return reservas;
	}

	@Override
	public boolean cancelarReserva(Reserva reserva) {
		// TODO Auto-generated method stub
		try (SessionFactory sessionFactory = new GestorConexionHibernate().getSessionFactory();Session ss = sessionFactory.openSession()){
			Transaction transaction = ss.beginTransaction();
			Reserva reservaActual= ss.get(Reserva.class, reserva.getId());
			
			reservaActual.setEstado("cancelada");
			ss.merge(reservaActual);
			transaction.commit();
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		} 
	}

}
