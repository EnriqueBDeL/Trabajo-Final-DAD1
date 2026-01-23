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
        // 1. Preparamos el socket de datos
        try (ServerSocket serverDatos = new ServerSocket(0)) { // El puerto 0 busca uno libre automático
            
            String ip = socket.getLocalAddress().getHostAddress();
            int puerto = serverDatos.getLocalPort();
            
            // 2. Avisamos al cliente: "Oye, conéctate a esta IP y Puerto que te voy a enviar datos"
            // PROTOCOLO: PREOK <ID> 200 <IP> <PUERTO>
            out.println("PREOK " + partes[0] + " 200 " + ip + " " + puerto);

            // 3. Esperamos a que el cliente se conecte
            Socket socketDatos = serverDatos.accept();
            ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
            
            // 4. Obtenemos la lista REAL del repositorio
            ArrayList<Titulacion> lista = ServidorRepository.getListaTitulos();
            
            // 5. Enviamos la lista
            oos.writeObject(lista);
            oos.flush(); // IMPORTANTE: Empujar los datos para que no se queden en el buffer
            
            // 6. Cerramos el canal de datos
            oos.close();
            socketDatos.close();
            
            // 7. IMPORTANTE: Confirmar por el canal de comando que todo ha terminado
            // Si falta esto, el cliente se queda congelado esperando el "OK"
            out.println("OK " + partes[0] + " 200 Listado enviado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("FAILED " + partes[0] + " 500 Error en el servidor al listar");
        }
    }
}