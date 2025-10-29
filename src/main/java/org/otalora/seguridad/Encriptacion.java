package org.otalora.seguridad;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encriptacion {
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public static String encriptarClave(String clave){

        String claveEncriptada = encoder.encode(clave);
        return claveEncriptada;
    }

    public static boolean verificarClave(String clave, String claveEncriptada){
        return encoder.matches(clave, claveEncriptada);
    }

}
