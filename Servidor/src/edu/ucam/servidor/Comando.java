package edu.ucam.servidor.comando;

import java.io.*;
import java.net.Socket;

public abstract class Comando {

    protected Socket socket;
    protected BufferedReader in;
    protected PrintWriter out;
    protected String[] partes;

    public Comando(Socket socket, BufferedReader in, PrintWriter out, String[] partes) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.partes = partes;
    }

    public abstract void ejecutar();
}
