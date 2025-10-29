package org.otalora.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.otalora.dao.IEventoDAO;
import org.otalora.modelo.Evento;
import org.otalora.modelo.Usuario;
import org.otalora.util.GestorConexionHibernate;

public class EventoDAO implements IEventoDAO {

	@Override
	public boolean crearEvento(Evento evento) {
		try (SessionFactory sessionFactory = new GestorConexionHibernate().getSessionFactory();
				Session ss = sessionFactory.openSession()) {
			Transaction transaction = ss.beginTransaction();
			ss.save(evento);
			transaction.commit();
			System.out.println("Evento " + evento.getNombre() + " insertado");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Evento> obtenerEventos() {
		try (SessionFactory sessionFactory = new GestorConexionHibernate().getSessionFactory();
				Session ss = sessionFactory.openSession()) {

			String hql = "FROM Evento e WHERE e.fechaHora > CURRENT_TIMESTAMP AND e.cancelado = false ORDER BY e.fechaHora ASC";
			Query<Evento> query = ss.createQuery(hql, Evento.class);
			List<Evento> eventos = query.list();

			return eventos;

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("error al cargar los eventos");
			return null;
		}
	}

	@Override
	public List<Evento> obtenerEventosAdministrador(Usuario admin) {
		try (SessionFactory sessionFactory = new GestorConexionHibernate().getSessionFactory();
				Session ss = sessionFactory.openSession()) {

			String hql = "SELECT e FROM Evento e JOIN e.idCampo c WHERE c.usuarioAdmin.id = :adminId ORDER BY e.fechaHora ASC\r\n";
			Query<Evento> query = ss.createQuery(hql, Evento.class);
			query.setParameter("adminId", admin.getId());

			List<Evento> eventos = query.list();

			return eventos;

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("error al cargar los eventos");
			return null;
		}
	}

	@Override
	public boolean descontarPlazas(long eventoId, int numPersonas) {
		// TODO Auto-generated method stub
		try (SessionFactory sessionFactory = new GestorConexionHibernate().getSessionFactory();
				Session ss = sessionFactory.openSession()) {

			Transaction tx = ss.beginTransaction();

			String hql = "UPDATE Evento SET numeroPlazasRestantes = numeroPlazasRestantes-:numPersonas WHERE id =:eventoId";
			Query<?> query = ss.createQuery(hql);
			query.setParameter("numPersonas", numPersonas);
			query.setParameter("eventoId", eventoId);

			int filasActualizadas = query.executeUpdate();
			tx.commit();

			return filasActualizadas > 0;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean devolverPlazas(long eventoId, int numPersonas) {
		// TODO Auto-generated method stub
		try (SessionFactory sessionFactory = new GestorConexionHibernate().getSessionFactory();
				Session ss = sessionFactory.openSession()) {

			Transaction tx = ss.beginTransaction();

			String hql = "UPDATE Evento e SET e.numeroPlazasRestantes = e.numeroPlazasRestantes + :numPersonas WHERE e.id = :eventoId";
			Query query = ss.createQuery(hql);
			query.setParameter("numPersonas", numPersonas);
			query.setParameter("eventoId", eventoId);

			int filas = query.executeUpdate();

			tx.commit();

			return filas > 0;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean cancelarEvento(Evento evento) {
		try (SessionFactory sessionFactory = new GestorConexionHibernate().getSessionFactory();Session ss = sessionFactory.openSession()){
			Transaction transaction = ss.beginTransaction();
			
			Evento eventoActual = ss.get(Evento.class,evento.getId());
			eventoActual.setCancelado(true);
			ss.merge(eventoActual);
			transaction.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}

}
