package org.otalora.main;

import org.otalora.vista.comun.LogInVista;
import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
    	
        EventQueue.invokeLater(() -> {
            LogInVista login = new LogInVista();
            login.setVisible(true);
        });
    }
}