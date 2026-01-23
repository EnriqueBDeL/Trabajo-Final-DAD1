package edu.ucam.servidor.comandos.titulo;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import edu.ucam.domain.Titulacion;
import edu.ucam.servidor.ServidorRepository;
import edu.ucam.servidor.comandos.Comando;

public class ComandoADDTIT extends Comando {

    @Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {
        try (ServerSocket serverDatos = new ServerSocket(0)) {
            String ip = socket.getLocalAddress().getHostAddress();
            int puerto = serverDatos.getLocalPort();
            
            out.println("PREOK " + partes[0] + " 200 " + ip + " " + puerto);

            Socket socketDatos = serverDatos.accept();
            ObjectInputStream ois = new ObjectInputStream(socketDatos.getInputStream());
            
            Titulacion titulo = (Titulacion) ois.readObject();
            
            // GUARDAMOS EN EL REPOSITORIO
            ServidorRepository.addTitulo(titulo);
            
            out.println("OK " + partes[0] + " 200 Titulo guardado");
            
            ois.close();
            socketDatos.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("FAILED " + partes[0] + " 500 Error al añadir");
        }
    }
}