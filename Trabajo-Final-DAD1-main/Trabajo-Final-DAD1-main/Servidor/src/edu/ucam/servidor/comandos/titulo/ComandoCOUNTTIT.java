package edu.ucam.servidor.comandos.titulo;

import java.io.PrintWriter;
import java.net.Socket;

import edu.ucam.servidor.ServidorRepository;
import edu.ucam.servidor.comandos.Comando;
import edu.ucam.servidor.hilo.HiloServidor;

public class ComandoCOUNTTIT implements Comando {

    @Override
    public void ejecutar(
            HiloServidor hilo,
            Socket socket,
            PrintWriter out,
            String[] partes) {

        int total = ServidorRepository.repoTitulos.getSize();
        out.println("OK " + partes[0] + " 200 " + total);
    }
}
