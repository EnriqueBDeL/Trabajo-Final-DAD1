package edu.ucam.servidor.comandos.titulo;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import edu.ucam.domain.Titulacion;
import edu.ucam.servidor.ServidorRepository;
import edu.ucam.servidor.comandos.Comando;
import edu.ucam.servidor.hilo.HiloServidor;

public class ComandoGETTIT implements Comando {

    @Override
    public void ejecutar(
            HiloServidor hilo,
            Socket socket,
            PrintWriter out,
            String[] partes) {

        String id = partes[2];

        if (!ServidorRepository.repoTitulos.existe(id)) {
            out.println("FAILED " + partes[0] + " 404 No existe");
            return;
        }

        try (ServerSocket serverDatos = new ServerSocket(0)) {

            out.println("PREOK " + partes[0] + " 200 " +
                    socket.getLocalAddress().getHostAddress() + " " +
                    serverDatos.getLocalPort());

            Socket socketDatos = serverDatos.accept();
            ObjectOutputStream oos =
                    new ObjectOutputStream(socketDatos.getOutputStream());

            oos.writeObject(ServidorRepository.repoTitulos.get(id));
            oos.flush();

            out.println("OK " + partes[0] + " 200 Transferencia finalizada");

            oos.close();
            socketDatos.close();

        } catch (Exception e) {
            out.println("FAILED " + partes[0] + " 500 Error");
        }
    }
}
