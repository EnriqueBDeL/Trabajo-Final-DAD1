package edu.ucam.servidor.comandos;

import java.io.*;
import java.net.Socket;

public class ComandoEXIT implements IComando {

    @Override
    public void ejecutar(Socket socket, BufferedReader in, PrintWriter out, String[] partes) {
        out.println("OK " + partes[0] + " 200 Bye");
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
