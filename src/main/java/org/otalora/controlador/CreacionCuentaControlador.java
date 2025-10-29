package org.otalora.controlador;

import java.awt.Component;

import org.otalora.dao.impl.UsuarioDAO;
import org.otalora.modelo.Usuario;
import org.otalora.seguridad.Encriptacion;
import org.otalora.seguridad.VerificacionCorreo;

public class CreacionCuentaControlador {
	
	public static Object crearCuenta(Usuario usuario, Component parent) {
		// TODO Auto-generated method stub
		UsuarioDAO usuarioM =new UsuarioDAO();
		StringBuilder errores = new StringBuilder();

        // Verificamos el formato del correo
        if (!VerificacionCorreo.verificarFormatoCorreo(usuario.getCorreo())) {
            errores.append("El formato del correo no es correcto.\n");
        }
        
        final boolean[] correoExiste=new boolean[1];
        final boolean[] nickExiste = new boolean[1];
        
        Thread correoHilo= new Thread(()->{
        	correoExiste[0] = usuarioM.verificarExistenciaCorreo(usuario.getCorreo());
        });
        
        Thread nickHilo= new Thread(()->{
        	nickExiste[0]=usuarioM.verificarNick(usuario.getNick());
        });
        
        correoHilo.start();
        nickHilo.start();
        
        try {
			correoHilo.join();
			nickHilo.join();
		} catch (Exception e) {
			// TODO: handle exception
	        e.printStackTrace();
	        return "Error al validar usuario";
		}
        
        if(usuarioM.verificarExistenciaCorreo(usuario.getCorreo())) {
            errores.append("El correo ya está asociado a una cuenta.\n");
        }

        // Verificamos si el nick está disponible
        if (usuarioM.verificarNick(usuario.getNick())) {
            errores.append("El nick ya existe, por lo que no lo puedes usar.\n");
        }

        // Si hay errores, los mostramos y retornamos false
        if (errores.length() > 0) {
            System.out.println(errores.toString());
            return errores.toString();
        }
        
        if(VerificacionCorreo.verificarCorreo(parent, usuario.getCorreo())) {
        	
        	usuario.setClave(Encriptacion.encriptarClave(usuario.getClave()));
        	usuarioM.crearEntidad(usuario);
        }else {
        	System.out.println("No se crea la cuenta");
        	return false;
        }
        
        return true;
	}
}
