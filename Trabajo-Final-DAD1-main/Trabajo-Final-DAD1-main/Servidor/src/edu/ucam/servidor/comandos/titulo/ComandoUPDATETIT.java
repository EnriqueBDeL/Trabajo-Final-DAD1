package edu.ucam.servidor.comandos.titulo;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import edu.ucam.domain.Titulacion;
import edu.ucam.servidor.ServidorRepository;
import edu.ucam.servidor.comandos.Comando;
import edu.ucam.servidor.hilo.HiloServidor;

public class ComandoUPDATETIT implements Comando {

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
            ObjectInputStream ois =
                    new ObjectInputStream(socketDatos.getInputStream());

            Titulacion t = (Titulacion) ois.readObject();
            t.setId(id);

            ServidorRepository.repoTitulos.add(t);

            out.println("OK " + partes[0] + " 200 Actualizado");

            ois.close();
            socketDatos.close();

        } catch (Exception e) {
            out.println("FAILED " + partes[0] + " 500 Error");
        }
    }
}
