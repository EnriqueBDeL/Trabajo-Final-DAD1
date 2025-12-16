package edu.ucam.servidor.hilo;

import java.io.*;
import java.net.*;
import edu.ucam.domain.Titulacion;
import edu.ucam.servidor.ServidorPrincipal;

public class HiloServidor implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public HiloServidor(Socket s) { this.socket = s; }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // El 'true' activa el autoFlush, importante para que no se quede colgado
            out = new PrintWriter(socket.getOutputStream(), true);
            
            String linea;
            while ((linea = in.readLine()) != null) {
                System.out.println("Recibido: " + linea); // Log
                
                // 1. Separamos el mensaje: "<numero> <comando> <args>"
                String[] partes = linea.split(" ");
                String numEnvio = partes[0];
                String comando = partes[1];

                // 2. Evaluamos qué comando es
                if (comando.equals("USER")) {
                    // Simulación: Aceptamos cualquier usuario por ahora
                    out.println("OK " + numEnvio + " 201 Envie password");
                
                } else if (comando.equals("PASS")) {
                    // Simulación: Aceptamos cualquier contraseña y damos la bienvenida
                    out.println("OK " + numEnvio + " 202 Welcome admin");
                
                } else if (comando.equals("EXIT")) {
                    out.println("OK " + numEnvio + " 200 Bye");
                    break; // Salimos del bucle para cerrar la conexión
                
                } else if (comando.equals("ADDTIT")) {
                    // --- LÓGICA DE CANAL DE DATOS (SUBIDA) ---
                    try (ServerSocket dataServer = new ServerSocket(0)) { // 0 = puerto libre aleatorio
                        int puertoDatos = dataServer.getLocalPort();
                        
                        // Avisamos al cliente dónde conectarse (PREOK)
                        out.println("PREOK " + numEnvio + " 203 127.0.0.1 " + puertoDatos);
                        
                        // Esperamos que el cliente se conecte para enviar el objeto
                        Socket socketDatos = dataServer.accept();
                        ObjectInputStream ois = new ObjectInputStream(socketDatos.getInputStream());
                        
                        // Leemos el objeto Titulacion
                        Titulacion titulo = (Titulacion) ois.readObject();
                        
                        // Lo guardamos en el mapa del ServidorPrincipal
                        // Asegúrate de que ServidorPrincipal.titulos esté inicializado y sea public static
                        ServidorPrincipal.titulos.put(titulo.getId(), titulo);
                        
                        System.out.println("Título guardado: " + titulo.getNombre());
                        
                        // Confirmamos finalización (opcional según implementación cliente, pero recomendado)
                        out.println("OK " + numEnvio + " 204 Transferencia terminada");
                    } catch (Exception e) {
                        out.println("FAILED " + numEnvio + " 500 Error en datos");
                        e.printStackTrace();
                    }

                } else if (comando.equals("GETTIT")) {
                    // --- LÓGICA DE CANAL DE DATOS (BAJADA) ---
                    String idTitulo = partes[2]; // El ID viene en el tercer argumento
                    
                    if (ServidorPrincipal.titulos.containsKey(idTitulo)) {
                        try (ServerSocket dataServer = new ServerSocket(0)) {
                            int puertoDatos = dataServer.getLocalPort();
                            
                            out.println("PREOK " + numEnvio + " 205 127.0.0.1 " + puertoDatos);
                            
                            Socket socketDatos = dataServer.accept();
                            ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
                            
                            // Enviamos el objeto al cliente
                            oos.writeObject(ServidorPrincipal.titulos.get(idTitulo));
                            
                            out.println("OK " + numEnvio + " 206 Transferencia terminada");
                        }
                    } else {
                        out.println("FAILED " + numEnvio + " 404 Titulo no encontrado");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error o desconexión del cliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) { e.printStackTrace(); }
        }
    }
}