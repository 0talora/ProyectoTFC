package org.otalora.controlador;

import org.otalora.dao.impl.UsuarioDAO;
import org.otalora.modelo.Usuario;
import org.otalora.seguridad.Encriptacion;
import org.otalora.seguridad.VerificacionCorreo;

public class LogInControlador {

    public boolean logInUsuario(String nick,String clave){
        
        UsuarioDAO usuarioVerificar=new UsuarioDAO();
        Encriptacion encriptacion=new Encriptacion();
        VerificacionCorreo verificacionCorreo=new VerificacionCorreo();

        Usuario usuario= usuarioVerificar.obtenerUsuario(nick);
        if (usuario == null) {
            System.out.println("Usuario no encontrado");
            return false;
        }

        if (!Encriptacion.verificarClave(clave, usuario.getClave())) {
            System.out.println("La clave introducida es incorrecta");
            return false;
        }

        if (!usuarioVerificar.verificarActivo(usuario)) {
            System.out.println("El usuario " + usuario.getNick() + " no está activo");
            return false;
        }


        UsuarioSesion.getInstancia().setUsuarioActual(usuario);

        verificacionCorreo.enviarCorreoInicioSesion(UsuarioSesion.getInstancia().getUsuarioActual().getCorreo());

        return true;
        
    }

}