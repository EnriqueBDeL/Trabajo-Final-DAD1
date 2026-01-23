package edu.ucam.servidor.comandos;

import java.io.PrintWriter;
import java.net.Socket;
import edu.ucam.servidor.hilo.HiloServidor;

public class ComandoEXIT implements Comando {

    @Override
    public void ejecutar(
            HiloServidor hilo,
            Socket socket,
            PrintWriter out,
            String[] partes) {

        out.println(partes[0] + " OK Conexión cerrada correctamente");

        try {
            socket.close();
        } catch (Exception e) {
           
        }
    }
}
