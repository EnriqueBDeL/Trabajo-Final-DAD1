package edu.ucam.servidor;

import java.io.*;
import java.net.*;
import edu.ucam.domain.Titulacion; 

public class Servidor { // El nombre de la clase debe coincidir con el archivo

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) { 
            System.out.println("SERVIDOR INICIADO EN PUERTO 5000...");
            
            while (true) {
                Socket cliente = serverSocket.accept();
                new Thread(new HiloCliente(cliente)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class HiloCliente implements Runnable {
    private Socket socket;
    
    public HiloCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println("Recibido: " + linea);
                String[] partes = linea.split(" ");
                String idEnvio = partes[0];
                String comando = partes[1];
                
                if (comando.equals("USER")) {
                    pw.println("OK " + idEnvio + " 200 Password_Required");
                } 
                else if (comando.equals("PASS")) {
                    pw.println("OK " + idEnvio + " 200 Welcome");
                }
                else if (comando.equals("ADDTIT")) {
                    // 1. Responder PREOK + IP + Puerto Datos (5001)
                    pw.println("PREOK " + idEnvio + " 200 127.0.0.1 5001");
                    
                    // 2. Recibir el objeto
                    try (ServerSocket dataServer = new ServerSocket(5001)) {
                        Socket dataSocket = dataServer.accept();
                        ObjectInputStream ois = new ObjectInputStream(dataSocket.getInputStream());
                        Object obj = ois.readObject();
                        System.out.println("SERVIDOR: Titulacion recibida -> " + obj);
                    }
                }
                else if (comando.equals("GETTIT")) {
                    // 1. Responder PREOK + IP + Puerto Datos (5001)
                    pw.println("PREOK " + idEnvio + " 200 127.0.0.1 5001");
                    
                    // 2. Enviar objeto dummy
                    try (ServerSocket dataServer = new ServerSocket(5001)) {
                        Socket dataSocket = dataServer.accept();
                        ObjectOutputStream oos = new ObjectOutputStream(dataSocket.getOutputStream());
                        oos.writeObject(new Titulacion()); // Enviamos una vacía de prueba
                    }
                }
                else if (comando.equals("EXIT")) {
                    pw.println("OK " + idEnvio + " 200 Bye");
                    break;
                }
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}