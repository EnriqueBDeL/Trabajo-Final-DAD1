package edu.ucam.servidor.comandos;

import java.io.PrintWriter;
import java.net.Socket;

public class ComandoUSER extends Comando {

    @Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {

    	if (partes.length < 3) {

    		out.println("FAILED " + partes[0] + " 400 Falta nombre de usuario");
            return;
        }

        String usuario = partes[2];
        

        if ("admin".equals(usuario)) {
             out.println("OK " + partes[0] + " 200 Usuario correcto, envie password");
        } else {
             out.println("FAILED " + partes[0] + " 401 Usuario no reconocido");
        }
    }
}