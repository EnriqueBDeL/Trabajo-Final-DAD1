package edu.ucam.servidor.comandos;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import edu.ucam.domain.Matricula;
import edu.ucam.servidor.ServidorRepository;

public class ComandoADDMATRICULA extends Comando {

    @Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {
        
        ServerSocket serverSocketDatos = null;
        Socket socketDatos = null;

        try {

        	serverSocketDatos = new ServerSocket(0);
            int puertoDatos = serverSocketDatos.getLocalPort();
            String ip = socket.getLocalAddress().getHostAddress();

       
            out.println("PREOK " + partes[0] + " 200 " + ip + " " + puertoDatos);

            socketDatos = serverSocketDatos.accept();

            ObjectInputStream ois = new ObjectInputStream(socketDatos.getInputStream());
            Matricula matricula = (Matricula) ois.readObject();

            ServidorRepository.addMatricula(matricula);
            System.out.println("Matrícula registrada con ID: " + matricula.getId());

            out.println("OK " + partes[0] + " 201 Matricula guardada correctamente");
            
            ois.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("FAILED " + partes[0] + " 500 Error al guardar matrícula");
        } finally {
            try {
                if (socketDatos != null) socketDatos.close();
                if (serverSocketDatos != null) serverSocketDatos.close();
            } catch (Exception ex) {}
        }
    }
}