package edu.ucam.servidor.comandos.titulo;

import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList; 
import edu.ucam.domain.Titulacion;
import edu.ucam.servidor.ServidorRepository;
import edu.ucam.servidor.comandos.Comando;

public class ComandoLISTTIT extends Comando {

    @Override
    public void ejecutar(Socket socket, PrintWriter out, String[] partes) {
        
        try {
        	
            ServerSocket servidorDatos = new ServerSocket(0);
            
            String ip = socket.getLocalAddress().getHostAddress();
            int puerto = servidorDatos.getLocalPort();
            
            out.println("PREOK " + partes[0] + " 200 " + ip + " " + puerto);

            Socket clienteDatos = servidorDatos.accept();
            
            ObjectOutputStream envioObjetos = new ObjectOutputStream(clienteDatos.getOutputStream());
            
            ArrayList<Titulacion> lista = ServidorRepository.getListaTitulos();
            envioObjetos.writeObject(lista);
            envioObjetos.flush();

            out.println("OK " + partes[0] + " 200 Listado enviado");
            
            envioObjetos.close();
            clienteDatos.close();
            servidorDatos.close();

        } catch (Exception e) {
            out.println("FAILED " + partes[0] + " 500 Error al listar");
        }
    }
}