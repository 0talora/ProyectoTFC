package org.otalora.seguridad;



import java.awt.Component;
import java.awt.Frame;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.otalora.vista.comun.VerificacionCorreoVista;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class VerificacionCorreo {

    private static final String remitente="recon.reservas@gmail.com";
    private static final String claveRemitente="ytmk gaut qvrr wkti ";


    public static boolean verificarCorreo(Component parent,String correo){
        final int codigo=generarCodigoVerificacion();
        
        new Thread(()->{
        	enviarCodigoCorreo(correo, codigo);
        }).start();
        
        Frame owner = parent instanceof Frame ? (Frame) parent : null;
        VerificacionCorreoVista dialog = new VerificacionCorreoVista(owner, codigo);

        return dialog.fueVerificado();
    }

    public static void  enviarCorreoInicioSesion(String correoDestinatario) {

        Properties propsCorreo = new Properties();
        propsCorreo.put("mail.smtp.host", "smtp.gmail.com");
        propsCorreo.put("mail.smtp.port", "587");
        propsCorreo.put("mail.smtp.auth", "true");
        propsCorreo.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(propsCorreo, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, claveRemitente);
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestinatario));
            message.setSubject("Inicio de Sesión");
            message.setText("Se ha iniciado sesion en un dispositivo con tu cuenta de Recon Reservas");

            Transport.send(message);
            System.out.println("Correo enviado a " + correoDestinatario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void enviarCodigoCorreo(String correoDestinatario, int codigo) {

        Properties propsCorreo = new Properties();
        propsCorreo.put("mail.smtp.host", "smtp.gmail.com");
        propsCorreo.put("mail.smtp.port", "587");
        propsCorreo.put("mail.smtp.auth", "true");
        propsCorreo.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(propsCorreo, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, claveRemitente);
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestinatario));
            message.setSubject("Código de Verificación");
            message.setText("Recon Reservas.\nGracias por confíar en nosotros\nTu código de verificación es: " + codigo);

            Transport.send(message);
            System.out.println("Correo enviado a " + correoDestinatario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int generarCodigoVerificacion(){
        Random random = new Random();

        int codigo = 100000 + random.nextInt(900000);
        return codigo;

    }

    public static boolean verificarFormatoCorreo(String correo) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }
    
}
