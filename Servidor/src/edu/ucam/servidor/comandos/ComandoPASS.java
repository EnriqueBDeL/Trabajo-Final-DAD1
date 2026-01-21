package edu.ucam.servidor.comandos;

import java.io.PrintWriter;
import java.net.Socket;

public class ComandoPASS extends Comando {

    @Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {

    	if (partes.length < 3) {
            out.println("FAILED " + partes[0] + " 400 Falta password");
            return;
        }

        String pass = partes[2];
        
        if ("admin".equals(pass)) {

        	out.println("OK " + partes[0] + " 200 Welcome admin");
        } else {
            out.println("FAILED " + partes[0] + " 401 Password incorrecta");
        }
    }
}