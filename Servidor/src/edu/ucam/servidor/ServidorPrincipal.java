package edu.ucam.servidor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import edu.ucam.domain.Titulacion;
import edu.ucam.servidor.hilo.HiloServidor;

public class ServidorPrincipal {
    // "Base de datos" en memoria para la práctica parcial
    public static Hashtable<String, Titulacion> titulos = new Hashtable<>();

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5000)) { // Puerto 5000 [cite: 27]
            System.out.println("Servidor iniciado en puerto 5000...");
            while (true) {
                Socket cliente = server.accept();
                new Thread(new HiloServidor(cliente)).start();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}