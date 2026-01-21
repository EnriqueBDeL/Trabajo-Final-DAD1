package edu.ucam.servidor.comandos.asignatura;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import edu.ucam.domain.Asignatura;
import edu.ucam.servidor.ServidorRepository;
import edu.ucam.servidor.comandos.Comando;

public class ComandoGETASIG extends Comando {

    @Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {
        if (partes.length < 3) {
            out.println("FAILED " + partes[0] + " 400 Falta ID");
            return;
        }

        String id = partes[2];
        if (!ServidorRepository.existeAsignatura(id)) {
            out.println("FAILED " + partes[0] + " 404 No encontrada");
            return;
        }

        ServerSocket serverSocketDatos = null;
        Socket socketDatos = null;

        try {
            serverSocketDatos = new ServerSocket(0);
            out.println("PREOK " + partes[0] + " 200 " + socket.getLocalAddress().getHostAddress() + " " + serverSocketDatos.getLocalPort());

            socketDatos = serverSocketDatos.accept();
            ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
            
            oos.writeObject(ServidorRepository.getAsignatura(id));
            oos.flush();

            out.println("OK " + partes[0] + " 200 Transferencia terminada");
            oos.close();

        } catch (Exception e) {
            out.println("FAILED " + partes[0] + " 500 Error");
        } finally {
            try {
                if (socketDatos != null) socketDatos.close();
                if (serverSocketDatos != null) serverSocketDatos.close();
            } catch (Exception ex) {}
        }
    }
}