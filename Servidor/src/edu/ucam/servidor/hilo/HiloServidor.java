package edu.ucam.servidor.hilo;

import java.io.*;
import java.net.*;
import edu.ucam.domain.Titulacion;
import edu.ucam.servidor.ServidorPrincipal;

public class HiloServidor implements Runnable {

	
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    
    
///////////////////////////////////////|    
    public HiloServidor(Socket s) { 
        this.socket = s; 
    }
///////////////////////////////////////|

    
    
    @Override
    public void run() {
        
        try {
           
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream())); 
            
            String linea;
            
//-----------------------------------------------------------------------------------------| 
            while ((linea = in.readLine()) != null) {
            
                System.out.println("Recibido: " + linea);
                
                String partes[] = linea.split(" ");
                String numEnvio = partes[0];
                String comando = partes[1];

                switch (comando) {
                
                    case "USER":
                        out.println("OK " + numEnvio + " 201 Envie password");
                        out.flush();
                        break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                    case "PASS":
                        out.println("OK " + numEnvio + " 202 Welcome admin");
                        out.flush();
                        break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                    case "EXIT":
                        out.println("OK " + numEnvio + " 200 Bye");
                        out.flush();
                        return; // Usamos return para salir del método run() y matar el hilo
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                    case "ADDTIT":
                        try (ServerSocket dataServer = new ServerSocket(0)) {
                            int puertoDatos = dataServer.getLocalPort();
                            
                            out.println("PREOK " + numEnvio + " 203 127.0.0.1 " + puertoDatos);
                            out.flush();

                            Socket socketDatos = dataServer.accept();
                            
                            BufferedReader brDatos = new BufferedReader(new InputStreamReader(socketDatos.getInputStream()));
                            
                            String id = brDatos.readLine();
                            String nombre = brDatos.readLine();
                            
                            Titulacion titulo = new Titulacion();
                            titulo.setId(id);
                            titulo.setNombre(nombre);
                        
                            ServidorPrincipal.titulos.put(titulo.getId(), titulo);
                            
                            System.out.println("Título guardado: " + titulo.getNombre());
                            
                            brDatos.close();
                            socketDatos.close();
                            
                            out.println("OK " + numEnvio + " 204 Transferencia terminada");
                            out.flush();
                        } catch (Exception e) {
                            out.println("FAILED " + numEnvio + " 500 Error en datos");
                            out.flush();
                            e.printStackTrace();
                        }
                        break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                    case "GETTIT":
                        String idTitulo = partes[2]; 
                        
                        if (ServidorPrincipal.titulos.containsKey(idTitulo)) {
                            try (ServerSocket dataServer = new ServerSocket(0)) {
                                int puertoDatos = dataServer.getLocalPort();
                                
                                out.println("PREOK " + numEnvio + " 205 127.0.0.1 " + puertoDatos);
                                out.flush();
                                
                                Socket socketDatos = dataServer.accept();
                                
                                PrintWriter pwDatos = new PrintWriter(new OutputStreamWriter(socketDatos.getOutputStream())); // Sin true
                                
                                Titulacion t = ServidorPrincipal.titulos.get(idTitulo);
                                
                                pwDatos.println(t.getId());
                                pwDatos.flush();
                                
                                pwDatos.println(t.getNombre());
                                pwDatos.flush();
                                
                                pwDatos.close();
                                socketDatos.close();
                                
                                out.println("OK " + numEnvio + " 206 Transferencia terminada");
                                out.flush();
                            }
                        } else {
                            out.println("FAILED " + numEnvio + " 404 Titulo no encontrado");
                            out.flush();
                        }
                        break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|   
                    default:
                        out.println("FAILED " + numEnvio + " 400 Comando no reconocido");
                        out.flush();
                        break;
                }
            }
//-----------------------------------------------------------------------------------------| 
      
        } catch (Exception e) {
            System.out.println("Error o desconexión del cliente: " + e.getMessage());
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) { 
                e.printStackTrace(); 
            }
        }
    }
}