package edu.ucam.servidor.comandos;

import java.io.*;
import java.net.*;

import edu.ucam.domain.Titulacion;
import edu.ucam.servidor.data.DataRepository;

public class ComandoADDTIT implements IComando {

    @Override
    public void ejecutar(Socket socket, BufferedReader in, PrintWriter out, String[] partes) {

        try (ServerSocket dataServer = new ServerSocket(0)) {

            int puerto = dataServer.getLocalPort();
            out.println("PREOK " + partes[0] + " 203 127.0.0.1 " + puerto);

            Socket socketDatos = dataServer.accept();
            ObjectInputStream ois = new ObjectInputStream(socketDatos.getInputStream());

            Titulacion t = (Titulacion) ois.readObject();
            DataRepository.addTitulo(t);

            out.println("OK " + partes[0] + " 204 Transferencia terminada");

        } catch (Exception e) {
            out.println("FAILED " + partes[0] + " 500 Error ADDTIT");
            e.printStackTrace();
        }
    }
}
