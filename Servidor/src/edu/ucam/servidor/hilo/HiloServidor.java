package edu.ucam.servidor.hilo;

import java.io.*;
import java.net.Socket;
import java.util.Hashtable;

import edu.ucam.servidor.comando.*;

public class HiloServidor implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private Hashtable<String, Comando> comandos = new Hashtable<>();

    public HiloServidor(Socket socket) {
        this.socket = socket;

        // Registrar comandos
        comandos.put("USER", ComandoUSER.class);
        comandos.put("PASS", ComandoPASS.class);
        comandos.put("EXIT", ComandoEXIT.class);
        comandos.put("ADDTIT", ComandoADDTIT.class);
        comandos.put("GETTIT", ComandoGETTIT.class);
    }

    @Override
    public void run() {

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String linea;
            while ((linea = in.readLine()) != null) {

                String[] partes = linea.split(" ");
                String comandoStr = partes[1];

                Comando clase = comandos.get(comandoStr);

                if (clase == null) {
                    out.println("FAILED " + partes[0] + " 400 Comando desconocido");
                    continue;
                }

                Comando comando = clase
                        .getConstructor(Socket.class, BufferedReader.class, PrintWriter.class, String[].class)
                        .newInstance(socket, in, out, partes);

                comando.ejecutar();
            }

        } catch (Exception e) {
            System.out.println("Cliente desconectado");
        }
    }
}
