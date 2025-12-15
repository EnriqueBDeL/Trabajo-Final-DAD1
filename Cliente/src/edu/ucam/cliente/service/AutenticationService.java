package edu.ucam.cliente.service;

import java.io.IOException;
import edu.ucam.cliente.interfaces.IAutentication;
import edu.ucam.cliente.interfaces.IComunicationServer;

public class AutenticationService implements IAutentication {

    private IComunicationServer comServer;

    public AutenticationService(IComunicationServer comServer) {
        this.comServer = comServer;
    }

<<<<<<< HEAD
<<<<<<< Updated upstream
=======
    @Override
    public boolean autenticar(String usuario, String password) throws IOException {

    	String respuestaUser = comServer.sendCommand("USER " + usuario);
        System.out.println("Servidor dice: " + respuestaUser);

        if (respuestaUser != null && respuestaUser.startsWith("OK")) {
            
            String respuestaPass = comServer.sendCommand("PASS " + password);
            System.out.println("Servidor dice: " + respuestaPass);
            
            if (respuestaPass != null && respuestaPass.startsWith("OK")) {
                return true; // Login correcto
            }
        }
        return false;
    }

    @Override
    public void closeSession() throws IOException {
        comServer.sendCommand("EXIT"); // 
        comServer.disconnect();
    }
>>>>>>> Stashed changes
=======
>>>>>>> origin/main
}