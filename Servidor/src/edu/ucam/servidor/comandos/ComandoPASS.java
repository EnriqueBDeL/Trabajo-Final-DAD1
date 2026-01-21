package edu.ucam.servidor.comandos;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ComandoPASS extends Comando {

	
	
    public ComandoPASS(Socket socket, BufferedReader in, PrintWriter out, String[] partes) {
        super(socket, in, out, partes);
    }

    
    
    @Override
    public void ejecutar() {
        if (partes.length < 3) {
            out.println(partes[0] + " FAILED 400 Falta la password");
            return;
        }

        String pass = partes[2];
        

        if ("1234".equals(pass) || "secret".equals(pass)) {
            out.println(partes[0] + " OK Login correcto");
        } else {
            out.println(partes[0] + " FAILED 401 Password incorrecta");
        }
    }
}