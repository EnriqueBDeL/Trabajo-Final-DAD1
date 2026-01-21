package edu.ucam.servidor.comandos;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import edu.ucam.domain.Matricula;
import edu.ucam.servidor.ServidorRepository;

public class ComandoGETMATRICULA extends Comando {

    @Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {
        
        if (partes.length < 3) {
            out.println("FAILED " + partes[0] + " 400 Falta el ID de la matricula");
            return;
        }

        String idMatricula = partes[2];

        Matricula m = ServidorRepository.getMatricula(idMatricula);
        
        if (m == null) {
            out.println("FAILED " + partes[0] + " 404 Matricula no encontrada");
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
            oos.writeObject(m);
            oos.flush();

            System.out.println("Enviada matrícula: " + idMatricula);

            out.println("OK " + partes[0] + " 200 Transferencia finalizada");
            
            oos.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("FAILED " + partes[0] + " 500 Error en envio");
        } finally {
            try {
                if (socketDatos != null) socketDatos.close();
                if (serverSocketDatos != null) serverSocketDatos.close();
            } catch (Exception ex) {}
        }
    }
}