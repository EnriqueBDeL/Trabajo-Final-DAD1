package edu.ucam.servidor.comandos;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import edu.ucam.domain.Asignatura;
import edu.ucam.servidor.ServidorRepository;

public class ComandoGETASIG extends Comando {

    @Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {

    	
        if (partes.length < 3) {
            out.println("FAILED " + partes[0] + " 400 Falta ID asignatura");
            return;
        }

        String idBusqueda = partes[2];


        if (!ServidorRepository.existeAsignatura(idBusqueda)) {
            out.println("FAILED " + partes[0] + " 404 Asignatura no encontrada");
            return;
        }

        ServerSocket serverSocketDatos = null;
        Socket socketDatos = null;

        try {
            serverSocketDatos = new ServerSocket(0);
            int puertoDatos = serverSocketDatos.getLocalPort();
            String ip = socket.getLocalAddress().getHostAddress();

            out.println("PREOK " + partes[0] + " 200 " + ip + " " + puertoDatos);

            socketDatos = serverSocketDatos.accept();

            ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
            
            Asignatura asig = ServidorRepository.getAsignatura(idBusqueda);
            oos.writeObject(asig);
            oos.flush();

            System.out.println("Enviada asignatura: " + asig.getNombre());

            out.println("OK " + partes[0] + " 200 Transferencia finalizada");
            
            oos.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("FAILED " + partes[0] + " 500 Error al enviar datos");
        } finally {
            try {
                if (socketDatos != null) socketDatos.close();
                if (serverSocketDatos != null) serverSocketDatos.close();
            } catch (Exception ex) {}
        }
    }
}