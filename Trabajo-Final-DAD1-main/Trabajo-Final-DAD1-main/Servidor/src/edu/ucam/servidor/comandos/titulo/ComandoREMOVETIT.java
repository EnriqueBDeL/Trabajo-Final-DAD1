package edu.ucam.servidor.comandos.titulo;

import java.io.PrintWriter;
import java.net.Socket;

import edu.ucam.servidor.ServidorRepository;
import edu.ucam.servidor.comandos.Comando;
import edu.ucam.servidor.hilo.HiloServidor;

public class ComandoREMOVETIT implements Comando {

    @Override
    public void ejecutar(
            HiloServidor hilo,
            Socket socket,
            PrintWriter out,
            String[] partes) {

        String id = partes[2];

        if (ServidorRepository.repoTitulos.existe(id)) {
            ServidorRepository.repoTitulos.remove(id);
            out.println("OK " + partes[0] + " 200 Eliminado");
        } else {
            out.println("FAILED " + partes[0] + " 404 No existe");
        }
    }
}
