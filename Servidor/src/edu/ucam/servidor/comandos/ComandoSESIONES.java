package edu.ucam.servidor.comandos; 

import java.io.PrintWriter;
import java.net.Socket;
import edu.ucam.servidor.hilo.HiloServidor; 

public class ComandoSESIONES extends Comando {

	
	@Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {
        int num = HiloServidor.clientesConectados;
        out.println("OK " + partes[0] + " 200 " + num); 
    }
	
}