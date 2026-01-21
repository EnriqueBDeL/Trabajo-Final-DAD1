package edu.ucam.servidor.hilo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;
import edu.ucam.servidor.comandos.*;

public class HiloServidor implements Runnable {

    private Socket socket;
    private Hashtable<String, Comando> comandos = new Hashtable<>();

    public HiloServidor(Socket socket) {
        this.socket = socket;
        
        comandos.put("USER", new ComandoUSER());
        comandos.put("PASS", new ComandoPASS());
        comandos.put("ADDTIT", new ComandoADDTIT());
        comandos.put("GETTIT", new ComandoGETTIT());
        comandos.put("EXIT", new ComandoEXIT());
        comandos.put("ADDASIG", new ComandoADDASIG());
        comandos.put("GETASIG", new ComandoGETASIG());
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
                    Comando comando = comandos.get(nombreComando);

                    if (comando != null) {
                        comando.ejecutar(socket, out, partes);
                        if (nombreComando.equals("EXIT")) break;
                    } else {
                        out.println(partes[0] + " FAILED 404 Comando desconocido");
                    }
                }
            } catch (java.net.SocketException e) {
                System.out.println("El cliente se ha desconectado abruptamente.");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                 try { if(socket != null) socket.close(); } catch (IOException e) {}
           }
      }
    	
}
