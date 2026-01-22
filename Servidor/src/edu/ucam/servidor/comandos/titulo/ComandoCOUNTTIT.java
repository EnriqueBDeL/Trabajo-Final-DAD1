package edu.ucam.servidor.comandos.titulo;

import java.io.PrintWriter;
import java.net.Socket;
import edu.ucam.servidor.comandos.Comando;
import edu.ucam.servidor.ServidorRepository;

public class ComandoCOUNTTIT extends Comando {

	
	
    @Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {
    	
        int total = ServidorRepository.contarTitulos();
        
        out.println("OK " + partes[0] + " 200 " + total);
        
    }
}