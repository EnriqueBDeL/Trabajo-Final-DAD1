package edu.ucam.servidor.comandos.matricula;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import edu.ucam.domain.Matricula;
import edu.ucam.servidor.ServidorRepository;
import edu.ucam.servidor.comandos.Comando;
import edu.ucam.servidor.hilo.HiloServidor;

public class ComandoGETMATRICULA implements Comando {

    @Override
    public void ejecutar(
            HiloServidor hilo,
            Socket socket,
            PrintWriter out,
            String[] partes) {

        if (partes.length < 3) {
            out.println("FAILED " + partes[0] + " 400 Falta ID");
            return;
        }

        String id = partes[2];
        Matricula m = ServidorRepository.getMatricula(id);

        if (m == null) {
            out.println("FAILED " + partes[0] + " 404 No encontrada");
            return;
        }

        ServerSocket serverSocketDatos = null;
        Socket socketDatos = null;

        try {
            serverSocketDatos = new ServerSocket(0);
            out.println("PREOK " + partes[0] + " 200 " +
                    socket.getLocalAddress().getHostAddress() + " " +
                    serverSocketDatos.getLocalPort());

            socketDatos = serverSocketDatos.accept();
            ObjectOutputStream oos =
                    new ObjectOutputStream(socketDatos.getOutputStream());

            oos.writeObject(m);
            oos.flush();

            out.println("OK " + partes[0] + " 200 Transferencia terminada");
            oos.close();

        } catch (Exception e) {
            out.println("FAILED " + partes[0] + " 500 Error");
        } finally {
            try {
                if (socketDatos != null) socketDatos.close();
                if (serverSocketDatos != null) serverSocketDatos.close();
            } catch (Exception ignored) {}
        }
    }
}
