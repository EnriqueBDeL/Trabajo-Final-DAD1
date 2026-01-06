package edu.ucam.servidor.hilo;

import java.io.*;
import java.net.*;

import edu.ucam.domain.Titulacion;
import edu.ucam.servidor.ServidorPrincipal;

public class ServidorRepository {

    public void ejecutar(
            String linea,
            Socket socket,
            BufferedReader in,
            PrintWriter out
    ) {

        try {
            System.out.println("Recibido: " + linea);

            // <num> <comando> <args>
            String[] partes = linea.split(" ");
            String numEnvio = partes[0];
            String comando = partes[1];

            switch (comando) {

                case "USER":
                    out.println("OK " + numEnvio + " 201 Envie password");
                    break;

                case "PASS":
                    out.println("OK " + numEnvio + " 202 Welcome admin");
                    break;

                case "EXIT":
                    out.println("OK " + numEnvio + " 200 Bye");
                    socket.close();
                    break;

                case "ADDTIT":
                    try (ServerSocket dataServer = new ServerSocket(0)) {

                        int puertoDatos = dataServer.getLocalPort();
                        out.println("PREOK " + numEnvio + " 203 127.0.0.1 " + puertoDatos);

                        Socket socketDatos = dataServer.accept();
                        ObjectInputStream ois =
                                new ObjectInputStream(socketDatos.getInputStream());

                        Titulacion titulo = (Titulacion) ois.readObject();

                        ServidorPrincipal.titulos.put(
                                titulo.getId(),
                                titulo
                        );

                        System.out.println("Título guardado: " + titulo.getNombre());

                        out.println("OK " + numEnvio + " 204 Transferencia terminada");
                    }
                    break;

                case "GETTIT":
                    String idTitulo = partes[2];

                    if (ServidorPrincipal.titulos.containsKey(idTitulo)) {

                        try (ServerSocket dataServer = new ServerSocket(0)) {

                            int puertoDatos = dataServer.getLocalPort();
                            out.println("PREOK " + numEnvio + " 205 127.0.0.1 " + puertoDatos);

                            Socket socketDatos = dataServer.accept();
                            ObjectOutputStream oos =
                                    new ObjectOutputStream(socketDatos.getOutputStream());

                            oos.writeObject(
                                    ServidorPrincipal.titulos.get(idTitulo)
                            );

                            out.println("OK " + numEnvio + " 206 Transferencia terminada");
                        }

                    } else {
                        out.println("FAILED " + numEnvio + " 404 Titulo no encontrado");
                    }
                    break;

                default:
                    out.println("FAILED " + numEnvio + " 400 Comando desconocido");
                    break;
            }

        } catch (Exception e) {
            out.println("FAILED 0 500 Error servidor");
            e.printStackTrace();
        }
    }
}
