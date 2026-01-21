package edu.ucam.servidor.comandos;

import java.io.PrintWriter;
import java.net.Socket;
import edu.ucam.domain.Titulacion;
import edu.ucam.servidor.ServidorRepository;

public class ComandoGETTIT extends Comando {

    @Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {
        if (partes.length < 3) {
            out.println(partes[0] + " FAILED 400 Falta ID");
            return;
        }

        String id = partes[2];

        if (ServidorRepository.existeTitulo(id)) {
            Titulacion t = ServidorRepository.getTitulo(id);
            out.println(partes[0] + " OK " + t.getId() + "-" + t.getNombre());
        } else {
            out.println(partes[0] + " FAILED 404 No encontrado");
        }
    }
}