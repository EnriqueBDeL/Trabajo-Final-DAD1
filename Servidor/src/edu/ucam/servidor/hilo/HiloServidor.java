package edu.ucam.servidor.hilo;

import java.io.*;
import java.net.*;

public class HiloServidor implements Runnable {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private ServidorRepository repository = new ServidorRepository();

    public HiloServidor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            out = new PrintWriter(
                    socket.getOutputStream(),
                    true
            );

            String linea;
            while ((linea = in.readLine()) != null) {
                repository.ejecutar(linea, socket, in, out);
            }

        } catch (Exception e) {
            System.out.println("Cliente desconectado");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
