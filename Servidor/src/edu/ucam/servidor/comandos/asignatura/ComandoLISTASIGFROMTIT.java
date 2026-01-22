package edu.ucam.servidor.comandos.asignatura;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList; // Import normal

import edu.ucam.domain.Asignatura;
import edu.ucam.servidor.ServidorRepository;
import edu.ucam.servidor.comandos.Comando;

public class ComandoLISTASIGFROMTIT extends Comando {

   
	
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {
        if (partes.length < 3) {
            out.println("FAILED " + partes[0] + " 400 Falta ID Titulo");
            return;
        }

        String idTitulo = partes[2];
        
        try {
            ServerSocket serverDatos = new ServerSocket(0);
            
            String ip = socket.getLocalAddress().getHostAddress();
            int puerto = serverDatos.getLocalPort();
            out.println("PREOK " + partes[0] + " 200 " + ip + " " + puerto);

            Socket socketDatos = serverDatos.accept();
            ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
            

            ArrayList<Asignatura> lista = ServidorRepository.getAsignaturasDeTitulo(idTitulo);
            
            oos.writeObject(lista);
            oos.flush();

            out.println("OK " + partes[0] + " 200 Envio finalizado");
            
            oos.close();
            socketDatos.close();
            serverDatos.close();

        } catch (Exception e) {
            out.println("FAILED " + partes[0] + " 500 Error");
        }
    }
}