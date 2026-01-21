package edu.ucam.servidor.hilo;

import java.io.*;
import java.net.Socket;
import edu.ucam.servidor.comandos.*;

public class HiloServidor implements Runnable {

    private Socket socket;

    public HiloServidor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String linea;
            while ((linea = in.readLine()) != null) {
                

            	String[] partes = linea.split(" ");
                if (partes.length < 2) continue; 
                
                String nombreComando = partes[1]; 

                Comando comando = null;

                switch (nombreComando) {
                    case "USER":
                        comando = new ComandoUSER(socket, in, out, partes);
                        break;
                    case "PASS":
                        comando = new ComandoPASS(socket, in, out, partes);
                        break;
                    case "ADDTIT":
                        comando = new ComandoADDTIT(socket, in, out, partes);
                        break;
                    case "GETTIT":
                        comando = new ComandoGETTIT(socket, in, out, partes);
                        break;
                    case "EXIT":
                        comando = new ComandoEXIT(socket, in, out, partes);
                        break;
                    default:
                        out.println(partes[0] + " FAILED 404 Comando no encontrado");
                        break;
                }


                if (comando != null) {
                    comando.ejecutar();
                    
                    if (comando instanceof ComandoEXIT) {
                        break; 
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}