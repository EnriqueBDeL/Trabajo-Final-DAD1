package edu.ucam.servidor.comandos;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ComandoUSER extends Comando {

    public ComandoUSER(Socket socket, BufferedReader in, PrintWriter out, String[] partes) {
        super(socket, in, out, partes);
    }

    @Override
    public void ejecutar() {

    	if (partes.length < 3) {
            out.println(partes[0] + " FAILED 400 Falta el nombre de usuario");
            return;
        }

        String usuario = partes[2];
        System.out.println("Recibido usuario: " + usuario);
        

        out.println(partes[0] + " OK Usuario recibido");
        
    }
}