package org.otalora.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.otalora.dao.ICampoDAO;
import org.otalora.modelo.Campo;
import org.otalora.modelo.Usuario;
import org.otalora.util.GestorConexionHibernate;

public class CampoDAO implements ICampoDAO{

	@Override
	public List<Campo> obtenerCampos() {
		// TODO Auto-generated method stub
	    try (SessionFactory sessionFactory = new GestorConexionHibernate().getSessionFactory();
		         Session ss = sessionFactory.openSession()) {
		        
		        String hql = "FROM Campo";
		        Query<Campo> query = ss.createQuery(hql, Campo.class);
		        List<Campo> campos = query.list();
		        
		        return campos;
		        
		    } catch (Exception e) {
		        e.printStackTrace();
		        System.err.println("error al cargar los Campos");
		        return null;
		    }
	}
	
	@Override
	public Long contarCamposAdministrador(Usuario admin) {
	    try (SessionFactory sessionFactory = new GestorConexionHibernate().getSessionFactory();
		         Session ss = sessionFactory.openSession()) {
	        Long count = ss.createQuery(
	            "SELECT COUNT(c) FROM Campo c WHERE c.usuarioAdmin = :admin", Long.class).setParameter("admin", admin).uniqueResult();
	        return count != null ? count : 0L;
	    }
	}
	
	@Override
	public List<Campo> obtenerCamposAdministrador(Usuario admin) {
		// TODO Auto-generated method stub
	    try (SessionFactory sessionFactory = new GestorConexionHibernate().getSessionFactory();
		         Session ss = sessionFactory.openSession()) {
		        
		        String hql = "FROM Campo c WHERE c.usuarioAdmin = :admin";
		        Query<Campo> query = ss.createQuery(hql, Campo.class).setParameter("admin", admin);
		        List<Campo> campos = query.list();
		        
		        return campos;
		        
		    } catch (Exception e) {
		        e.printStackTrace();
		        System.err.println("error al cargar los Campos");
		        return null;
		    }
	}


}