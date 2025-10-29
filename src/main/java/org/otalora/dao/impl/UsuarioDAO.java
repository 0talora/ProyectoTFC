package org.otalora.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.otalora.dao.IUsuarioDAO;
import org.otalora.modelo.Usuario;
import org.otalora.seguridad.VerificacionCorreo;
import org.otalora.util.GestorConexionHibernate;

public class UsuarioDAO implements IUsuarioDAO{

	VerificacionCorreo verificarCorreo=new VerificacionCorreo();
	@Override
	public Usuario obtenerUsuario(String nick) {
		Usuario usuario=null;
		try (SessionFactory sessionFactory = new GestorConexionHibernate().getSessionFactory();Session ss = sessionFactory.openSession()){
			String hql="from Usuario where nick= :nick";
			Query<Usuario> query=ss.createQuery(hql,Usuario.class);
            query.setParameter("nick", nick);
            usuario=query.uniqueResult();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		System.out.println(usuario);
		return usuario;

    }
	@Override
	public void crearEntidad(Usuario usuario) {
		// TODO Auto-generated method stub
		try (SessionFactory sessionFactory = new GestorConexionHibernate().getSessionFactory();Session ss = sessionFactory.openSession()){
			Transaction transaction = ss.beginTransaction();
			ss.save(usuario);
			transaction.commit();
			System.out.println("Usuario "+usuario.getNick()+" creado");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@Override
	public boolean verificarActivo(Usuario usuario){
		if (usuario.isActivo()) {
			return true;
		}else{
			return false;
		}
	}


//	public void restablecerClave(String correo) {
//		if(verificarCorreo.verificarCorreo(correo)){
//			System.out.println("eres el dueño de la cuenta");
//		}else{
//			System.out.println("No eres el dueño de la cuenta");
//		}
//	}
	@Override
	public boolean verificarNick(String nick) {
		try (SessionFactory sessionFactory = new GestorConexionHibernate().getSessionFactory();Session ss = sessionFactory.openSession()){
			String hql="from Usuario where nick= :nick";
			Query<Usuario> query=ss.createQuery(hql,Usuario.class);
            query.setParameter("nick", nick);
            Usuario usuario = query.uniqueResult();  // Obtiene el primer usuario o null si no existe

            return usuario != null;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	//verifica si el correo esxiste o no en la aplicación
	@Override
	public boolean verificarExistenciaCorreo(String correo) {
			// TODO Auto-generated method stub
			
			try (SessionFactory sessionFactory = new GestorConexionHibernate().getSessionFactory();Session ss = sessionFactory.openSession()){
				String hql="from Usuario where correo= :correo";
				Query<Usuario> query=ss.createQuery(hql,Usuario.class);
	            query.setParameter("correo", correo);
	            Usuario usuario = query.uniqueResult();  // Obtiene el primer usuario o null si no existe

	            return usuario != null;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return false;
			}
		}

}
