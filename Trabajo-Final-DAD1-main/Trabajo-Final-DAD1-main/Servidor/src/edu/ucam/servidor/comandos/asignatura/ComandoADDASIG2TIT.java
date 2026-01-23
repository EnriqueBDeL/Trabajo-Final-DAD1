package edu.ucam.servidor.comandos.asignatura;

import java.io.PrintWriter;
import java.net.Socket;

import edu.ucam.servidor.ServidorRepository;
import edu.ucam.servidor.comandos.Comando;
import edu.ucam.servidor.hilo.HiloServidor;

public class ComandoADDASIG2TIT implements Comando {

    @Override
    public void ejecutar(
            HiloServidor hilo,
            Socket socket,
            PrintWriter out,
            String[] partes) {

        if (partes.length < 4) {
            out.println("FAILED " + partes[0] + " 400 Faltan datos");
            return;
        }

        String idAsig = partes[2];
        String idTit = partes[3];

        boolean exito =
                ServidorRepository.agregarAsignaturaATitulo(idAsig, idTit);

        if (exito) {
            out.println("OK " + partes[0] + " 200 Asignatura vinculada");
        } else {
            out.println("FAILED " + partes[0] + " 404 Error al vincular");
        }
    }
}
