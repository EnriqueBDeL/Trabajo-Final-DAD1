package edu.ucam.servidor.comandos;

import java.io.PrintWriter;
import java.net.Socket;

public class ComandoUSER extends Comando {

    @Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {
        if (partes.length < 3) {
            out.println(partes[0] + " FAILED 400 Falta usuario");
            return;
        }
        String usuario = partes[2];
        out.println(partes[0] + " OK Usuario " + usuario + " recibido");
    }
}