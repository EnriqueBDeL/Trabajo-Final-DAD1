package edu.ucam.servidor.comandos.titulo;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import edu.ucam.domain.Titulacion;
import edu.ucam.servidor.ServidorRepository;
import edu.ucam.servidor.comandos.Comando;

public class ComandoUPDATETIT extends Comando {

    @Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {

    	if (partes.length < 3) {
            out.println("FAILED " + partes[0] + " 400 Falta ID");
            return;
        }

        String id = partes[2];
        

        if (!ServidorRepository.repoTitulos.existe(id)) {
            out.println("FAILED " + partes[0] + " 404 El titulo no existe");
            return;
        }

        ServerSocket serverDatos = null;
        try {
            serverDatos = new ServerSocket(0);
            String ip = socket.getLocalAddress().getHostAddress();
            int puerto = serverDatos.getLocalPort();

            out.println("PREOK " + partes[0] + " 200 " + ip + " " + puerto);

            Socket socketDatos = serverDatos.accept();
            ObjectInputStream ois = new ObjectInputStream(socketDatos.getInputStream());
            
            Titulacion tit = (Titulacion) ois.readObject();
            
            tit.setId(id); 
            
            ServidorRepository.repoTitulos.add(tit); 

            out.println("OK " + partes[0] + " 200 Actualizacion correcta");
            
            ois.close();
            socketDatos.close();
            serverDatos.close();

        } catch (Exception e) {
            out.println("FAILED " + partes[0] + " 500 Error al actualizar");
        }
    }
}