package edu.ucam.servidor.comandos.matricula;

import java.io.PrintWriter;
import java.net.Socket;
import edu.ucam.servidor.comandos.Comando;
import edu.ucam.servidor.ServidorRepository;


public class ComandoREMOVEMATRICULA extends Comando {

	
    @Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {
  
    	if (partes.length < 3) {
    		
            out.println("FAILED " + partes[0] + " 400 Falta ID");
            return;
            
        }
        
        String id = partes[2];
        
        if (ServidorRepository.repoMatriculas.existe(id)) {
        	
            ServidorRepository.removeMatricula(id);
            out.println("OK " + partes[0] + " 200 Matricula eliminada");
            
        } else {
        	
            out.println("FAILED " + partes[0] + " 404 No existe la matricula");
            
        }
    }
}