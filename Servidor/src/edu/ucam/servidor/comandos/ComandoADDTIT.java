package edu.ucam.servidor.comandos;

import java.io.PrintWriter;
import java.net.Socket;
import edu.ucam.domain.Titulacion;
import edu.ucam.servidor.ServidorRepository;

public class ComandoADDTIT extends Comando {

    @Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {
        if (partes.length < 4) {
            out.println(partes[0] + " FAILED 400 Faltan datos");
            return;
        }

        try {
            String id = partes[2];
            String nombre = partes[3];

            Titulacion t = new Titulacion();
            t.setId(id);
            t.setNombre(nombre);

            ServidorRepository.addTitulo(t);

            out.println(partes[0] + " OK Titulacion guardada");
        } catch (Exception e) {
            out.println(partes[0] + " FAILED 500 Error interno");
        }
    }
}