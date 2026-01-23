package edu.ucam.servidor.hilo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;

import edu.ucam.servidor.comandos.Comando;
import edu.ucam.servidor.comandos.ComandoEXIT;
import edu.ucam.servidor.comandos.ComandoPASS;
import edu.ucam.servidor.comandos.ComandoSESIONES;
import edu.ucam.servidor.comandos.ComandoUSER;
import edu.ucam.servidor.comandos.asignatura.*;
import edu.ucam.servidor.comandos.matricula.*;
import edu.ucam.servidor.comandos.titulo.*;

public class HiloServidor implements Runnable {

    public static int clientesConectados = 0;
    private Socket socket;
    private Hashtable<String, Comando> comandos = new Hashtable<>();

    public HiloServidor(Socket socket) {
        this.socket = socket;
        
        comandos.put("USER", new ComandoUSER());
        comandos.put("PASS", new ComandoPASS());
        comandos.put("EXIT", new ComandoEXIT());
        comandos.put("SESIONES", new ComandoSESIONES());

        comandos.put("ADDTIT", new ComandoADDTIT());
        comandos.put("GETTIT", new ComandoGETTIT());
        comandos.put("REMOVETIT", new ComandoREMOVETIT());
        comandos.put("LISTTIT", new ComandoLISTTIT());
        comandos.put("COUNTTIT", new ComandoCOUNTTIT());
        comandos.put("UPDATETIT", new ComandoUPDATETIT());

        comandos.put("ADDASIG", new ComandoADDASIG());
        comandos.put("GETASIG", new ComandoGETASIG());
        comandos.put("REMOVEASIG", new ComandoREMOVEASIG());
        comandos.put("LISTASIG", new ComandoLISTASIG());
        
        comandos.put("ADDASIG2TIT", new ComandoADDASIG2TIT());
        comandos.put("LISTASIGFROMTIT", new ComandoLISTASIGFROMTIT());
        comandos.put("REMOVEASIGFROMTIT", new ComandoREMOVEASIGFROMTIT());

        comandos.put("ADDMATRICULA", new ComandoADDMATRICULA());
        comandos.put("GETMATRICULA", new ComandoGETMATRICULA());
        comandos.put("REMOVEMATRICULA", new ComandoREMOVEMATRICULA());
        comandos.put("UPDATEMATRICULA", new ComandoUPDATEMATRICULA());
        
    }

    @Override
    public void run() {
        clientesConectados++;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            String linea;
            while ((linea = in.readLine()) != null) {
                String[] partes = linea.split(" ");
                String comando = partes[1];
                
                if (comandos.containsKey(comando)) {
                    comandos.get(comando).ejecutar(socket, out, partes);
                } else {
                    out.println("FAILED " + partes[0] + " 404 Comando no encontrado");
                }
                
                if (comando.equals("EXIT")) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientesConectados--;
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
