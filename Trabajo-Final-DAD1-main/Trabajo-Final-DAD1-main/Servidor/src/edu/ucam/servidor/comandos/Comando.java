package edu.ucam.servidor.comandos;

import java.io.PrintWriter;
import java.net.Socket;
import edu.ucam.servidor.hilo.HiloServidor;

public interface Comando {

    void ejecutar(
        HiloServidor hilo,
        Socket socket,
        PrintWriter out,
        String[] partes
    );
}
