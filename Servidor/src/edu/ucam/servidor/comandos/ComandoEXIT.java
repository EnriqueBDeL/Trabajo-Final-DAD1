package edu.ucam.servidor.comandos;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ComandoEXIT extends Comando {

	
    public ComandoEXIT(Socket socket, BufferedReader in, PrintWriter out, String[] partes) {
        super(socket, in, out, partes);
    }

    
    
    @Override
    public void ejecutar() {
       
        System.out.println("Finalizando sesión con el cliente: " + socket.getInetAddress());
        
       
        out.println(partes[0] + " OK Conexión cerrada correctamente");
    }
}