package edu.ucam.servidor;

import java.net.ServerSocket;
import java.net.Socket;

import edu.ucam.servidor.data.DataRepository;
import edu.ucam.servidor.hilo.HiloServidor;

public class ServidorPrincipal {

    public static void main(String[] args) {

        System.out.println("Servidor iniciado en puerto 5000...");

        try (ServerSocket server = new ServerSocket(5000)) {

            while (true) {

                Socket cliente = server.accept();

                System.out.println("Cliente conectado desde: "
                        + cliente.getInetAddress());

                DataRepository.incrementarSesiones();

                new Thread(new HiloServidor(cliente)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
