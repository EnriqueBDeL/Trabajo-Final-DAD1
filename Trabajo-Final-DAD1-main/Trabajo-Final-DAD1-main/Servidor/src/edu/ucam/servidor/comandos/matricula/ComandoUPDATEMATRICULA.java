package edu.ucam.servidor.comandos.matricula;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import edu.ucam.domain.Matricula;
import edu.ucam.servidor.ServidorRepository;
import edu.ucam.servidor.comandos.Comando;
import edu.ucam.servidor.hilo.HiloServidor;

public class ComandoUPDATEMATRICULA implements Comando {

    @Override
    public void ejecutar(
            HiloServidor hilo,
            Socket socket,
            PrintWriter out,
            String[] partes) {

        if (partes.length < 3) {
            out.println("FAILED " + partes[0] + " 400 Falta ID");
            return;
        }

        String id = partes[2];

        if (!ServidorRepository.repoMatriculas.existe(id)) {
            out.println("FAILED " + partes[0] + " 404 La matricula no existe");
            return;
        }

        try (ServerSocket serverDatos = new ServerSocket(0)) {

            out.println("PREOK " + partes[0] + " 200 " +
                    socket.getLocalAddress().getHostAddress() + " " +
                    serverDatos.getLocalPort());

            Socket socketDatos = serverDatos.accept();
            ObjectInputStream ois =
                    new ObjectInputStream(socketDatos.getInputStream());

            Matricula mat = (Matricula) ois.readObject();
            mat.setId(id);

            ServidorRepository.repoMatriculas.add(mat);

            out.println("OK " + partes[0] + " 200 Actualizacion correcta");

            ois.close();
            socketDatos.close();

        } catch (Exception e) {
            out.println("FAILED " + partes[0] + " 500 Error al actualizar");
        }
    }
}
