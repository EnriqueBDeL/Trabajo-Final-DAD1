package edu.ucam.servidor.comandos;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import edu.ucam.domain.Titulacion;
import edu.ucam.servidor.ServidorRepository;

public class ComandoGETTIT extends Comando {

    public ComandoGETTIT(Socket socket, BufferedReader in, PrintWriter out, String[] partes) {
        super(socket, in, out, partes);
    }

    
    
    @Override
    public void ejecutar() {
        if (partes.length < 3) {
            out.println(partes[0] + " FAILED 400 Falta el ID de la titulacion");
            return;
        }

        String idBusqueda = partes[2];
        
        if (ServidorRepository.existeTitulo(idBusqueda)) {
            Titulacion t = ServidorRepository.getTitulo(idBusqueda);
            out.println(partes[0] + " OK " + t.getId() + "-" + t.getNombre());
        } else {
            out.println(partes[0] + " FAILED 404 Titulacion no encontrada");
        }
    }
}