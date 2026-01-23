package edu.ucam.cliente.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import edu.ucam.cliente.interfaces.IComunicationServer;
import edu.ucam.cliente.config.ClientConfig;

public class CommunicationSocket implements IComunicationServer {

    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    private int communicationId = 1;

    // ----------------------------------------------------
    @Override
    public void connect() throws IOException {

        socket = new Socket(ClientConfig.IP, ClientConfig.COMMUNICATION_PORT);

        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream()),
                true // autoflush
        );
    }
    // ----------------------------------------------------

    @Override
    public void disconnect() throws IOException {

        if (pw != null) pw.close();
        if (br != null) br.close();
        if (socket != null && !socket.isClosed()) socket.close();
    }

    // ----------------------------------------------------

    @Override
    public boolean isAlive() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    // ----------------------------------------------------

    @Override
    public String sendCommand(String command) throws IOException {

        if (!isAlive()) {
            throw new IOException("Socket no conectado");
        }

        pw.println(communicationId + " " + command);
        communicationId++;

        return br.readLine();
    }

    // ----------------------------------------------------

    @Override
    public String readLine() throws IOException {

        if (!isAlive()) {
            throw new IOException("Socket no conectado");
        }

        return br.readLine();
    }
}
