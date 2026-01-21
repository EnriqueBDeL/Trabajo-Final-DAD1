package edu.ucam.servidor.comandos;

import java.io.PrintWriter;
import java.net.Socket;

public class ComandoEXIT extends Comando {

    @Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {
        out.println(partes[0] + " OK Conexión cerrada correctamente");
    }
}