package edu.ucam.servidor.comandos;

import java.io.PrintWriter;
import java.net.Socket;

public class ComandoPASS extends Comando {

    @Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {
        if (partes.length < 3) {
            out.println(partes[0] + " FAILED 400 Falta password");
            return;
        }
        out.println(partes[0] + " OK Login correcto");
    }
}