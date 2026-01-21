package edu.ucam.servidor.comandos;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import edu.ucam.domain.Titulacion;
import edu.ucam.servidor.ServidorRepository; 

public class ComandoADDTIT extends Comando {

	
	
    public ComandoADDTIT(Socket socket, BufferedReader in, PrintWriter out, String[] partes) {
        super(socket, in, out, partes);
    }

    
    
    @Override
    public void ejecutar() {
        if (partes.length < 4) {
            out.println(partes[0] + " FAILED 400 Faltan datos de la titulacion");
            return;
        }

        try {
            String idTitulo = partes[2];
            String nombreTitulo = partes[3];

            Titulacion t = new Titulacion();
            t.setId(idTitulo);
            t.setNombre(nombreTitulo);

            ServidorRepository.addTitulo(t);

            System.out.println("Añadida titulación: " + nombreTitulo);
            out.println(partes[0] + " OK Titulacion guardada correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            out.println(partes[0] + " FAILED 500 Error interno al guardar");
        }
    }
}
