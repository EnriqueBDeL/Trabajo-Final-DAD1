package edu.ucam.cliente.service;

import java.io.IOException;

import edu.ucam.cliente.interfaces.IAutentication;
import edu.ucam.cliente.interfaces.IComunicationServer;

public class AutenticationService implements IAutentication {

    private final IComunicationServer comunication;

    // -------------------------------------------------
    public AutenticationService(IComunicationServer comunication) {
        this.comunication = comunication;
    }
    // -------------------------------------------------

    @Override
    public boolean autenticar(String usuario, String password) throws IOException {

        // USER
        String response = comunication.sendCommand("USER " + usuario);
        ResponseParser parser = new ResponseParser(response);

        if (!parser.isSuccess() || parser.getCode() != 201) {
            return false;
        }

        // PASS
        response = comunication.sendCommand("PASS " + password);
        parser = new ResponseParser(response);

        if (!parser.isSuccess() || parser.getCode() != 202) {
            return false;
        }

        return true;
    }

    @Override
    public void closeSession() throws IOException {

        String response = comunication.sendCommand("EXIT");
        ResponseParser parser = new ResponseParser(response);

        // Según el PDF, EXIT → 200
        if (parser.isSuccess() && parser.getCode() == 200) {
            comunication.disconnect();
        } else {
            // En caso de error, cerramos igualmente el socket
            comunication.disconnect();
        }
    }
}
