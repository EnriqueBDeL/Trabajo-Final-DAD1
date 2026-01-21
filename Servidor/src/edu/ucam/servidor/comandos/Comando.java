package edu.ucam.servidor.comandos;

import java.io.PrintWriter;
import java.net.Socket;

public abstract class Comando {
    public abstract void ejecutar(Socket socket, PrintWriter out, String[] partes);
}