package edu.ucam.servidor.comandos.asignatura;

import java.io.PrintWriter;
import java.net.Socket;

import edu.ucam.servidor.ServidorRepository;
import edu.ucam.servidor.comandos.Comando;
import edu.ucam.servidor.hilo.HiloServidor;

public class ComandoREMOVEASIG implements Comando {

    @Override
    public void ejecutar(
            HiloServidor hilo,
            Socket socket,
            PrintWriter out,
            String[] partes) {

        if (partes.length < 3) {
            out.println("FAILED " + partes[0] + " 400 Falta ID");
            return;
        }

        String id = partes[2];

        if (ServidorRepository.existeAsignatura(id)) {
            ServidorRepository.removeAsignatura(id);
            out.println("OK " + partes[0] + " 200 Asignatura eliminada");
        } else {
            out.println("FAILED " + partes[0] + " 404 No existe la asignatura");
        }
    }
}
