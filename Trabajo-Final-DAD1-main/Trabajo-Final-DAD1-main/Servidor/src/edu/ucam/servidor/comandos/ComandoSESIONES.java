package edu.ucam.servidor.comandos;

import java.io.PrintWriter;
import java.net.Socket;

import edu.ucam.servidor.hilo.HiloServidor;
import edu.ucam.servidor.data.DataRepository;

public class ComandoSESIONES implements Comando {

    @Override
    public void ejecutar(
            HiloServidor hilo,
            Socket socket,
            PrintWriter out,
            String[] partes) {

        int num = DataRepository.getSesionesActivas();
        out.println("OK " + partes[0] + " 200 " + num);
    }
}
