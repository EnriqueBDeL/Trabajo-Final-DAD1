package edu.ucam.servidor.comandos;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import edu.ucam.domain.Asignatura;
import edu.ucam.servidor.ServidorRepository;

public class ComandoADDASIG extends Comando {

	
	
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
            Asignatura asig = (Asignatura) ois.readObject();

            ServidorRepository.addAsignatura(asig);
            System.out.println("Asignatura guardada: " + asig.getNombre());

            out.println("OK " + partes[0] + " 201 Insertada correctamente");
            
            ois.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("FAILED " + partes[0] + " 500 Error al recibir asignatura");
        } finally {
            try {
                if (socketDatos != null) socketDatos.close();
                if (serverSocketDatos != null) serverSocketDatos.close();
            } catch (Exception ex) {}
        }
    }
}