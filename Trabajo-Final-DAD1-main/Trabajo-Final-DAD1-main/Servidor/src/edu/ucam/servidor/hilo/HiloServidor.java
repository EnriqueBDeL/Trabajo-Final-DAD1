package edu.ucam.servidor.hilo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;

import edu.ucam.servidor.comandos.titulo.*;
import edu.ucam.servidor.comandos.asignatura.*;
import edu.ucam.servidor.comandos.matricula.*;


import edu.ucam.servidor.comandos.*;
import edu.ucam.servidor.data.DataRepository;

public class HiloServidor implements Runnable {

    private Socket socket;
    private Hashtable<String, Comando> comandos = new Hashtable<>();
    private boolean autenticado = false;

    public HiloServidor(Socket socket) {
        this.socket = socket;
        inicializarComandos();
    }

    private void inicializarComandos() {

        comandos.put("USER", new ComandoUSER());
        comandos.put("PASS", new ComandoPASS());
        comandos.put("SESIONES", new ComandoSESIONES());

        comandos.put("ADDTIT", new ComandoADDTIT());
        comandos.put("GETTIT", new ComandoGETTIT());
        comandos.put("REMOVETIT", new ComandoREMOVETIT());
        comandos.put("COUNTTIT", new ComandoCOUNTTIT());
        comandos.put("UPDATETIT", new ComandoUPDATETIT());

        comandos.put("ADDASIG", new ComandoADDASIG());
        comandos.put("GETASIG", new ComandoGETASIG());
        comandos.put("REMOVEASIG", new ComandoREMOVEASIG());
        comandos.put("ADDASIG2TIT", new ComandoADDASIG2TIT());
        comandos.put("LISTASIGFROMTIT", new ComandoLISTASIGFROMTIT());
        comandos.put("REMOVEASIGFROMTIT", new ComandoREMOVEASIGFROMTIT());

        comandos.put("ADDMATRICULA", new ComandoADDMATRICULA());
        comandos.put("GETMATRICULA", new ComandoGETMATRICULA());
        comandos.put("REMOVEMATRICULA", new ComandoREMOVEMATRICULA());
        comandos.put("UPDATEMATRICULA", new ComandoUPDATEMATRICULA());

        comandos.put("EXIT", new ComandoEXIT());
    }

    @Override
    public void run() {

        DataRepository.incrementarSesiones();

        try (
            BufferedReader in =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out =
                new PrintWriter(socket.getOutputStream(), true)
        ) {
            String linea;

            while ((linea = in.readLine()) != null) {

                String[] partes = linea.split(" ");
                if (partes.length < 2) continue;

                String nombreComando = partes[1];
                Comando comando = comandos.get(nombreComando);

                if (comando == null) {
                    out.println(partes[0] + " FAILED 404 Comando desconocido");
                    continue;
                }

               
                if (!autenticado &&
                    !nombreComando.equals("USER") &&
                    !nombreComando.equals("PASS") &&
                    !nombreComando.equals("EXIT")) {

                    out.println(partes[0] + " FAILED 401 No autenticado");
                    continue;
                }

                comando.ejecutar(this, socket, out, partes);

                if (nombreComando.equals("EXIT")) break;
            }

        } catch (IOException e) {
            System.out.println("Cliente desconectado.");
        } finally {

            DataRepository.decrementarSesiones();

            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }

    // ------------------ CONTROL DE SESIÓN ------------------

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }

    public boolean isAutenticado() {
        return autenticado;
    }
}
