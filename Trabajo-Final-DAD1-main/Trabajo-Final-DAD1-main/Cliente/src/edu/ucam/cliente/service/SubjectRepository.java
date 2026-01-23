package edu.ucam.cliente.service;

import java.io.IOException;
import java.util.List;

import edu.ucam.cliente.interfaces.IChannelData;
import edu.ucam.cliente.interfaces.IComunicationServer;
import edu.ucam.domain.Asignatura;

public class SubjectRepository extends BaseRepository<Asignatura> {

    // -------------------------------------------------------------
    public SubjectRepository(IComunicationServer comunication,
                             IChannelData channelData) {

        super(
            comunication,
            channelData,
            "ADDASIG",
            "REMOVEASIG",
            "GETASIG",
            "LISTASIG",
            "COUNTASIG",
            "UPDATEASIG"
        );
    }
    // -------------------------------------------------------------

  
    public int sumaCreditos() {

        try {
            String response = comunication.sendCommand("SUMCREDITS");
            ResponseParser parser = new ResponseParser(response);

            if (parser.isSuccess()) {
                return Integer.parseInt(parser.getMessage().trim());
            }
        } catch (Exception e) {
            System.err.println("Error SUMCREDITS: " + e.getMessage());
        }

        return 0;
    }

    public void addAsignaturaToTitulo(String idAsig, String idTit)
            throws IOException {

        String response =
                comunication.sendCommand("ADDASIG2TIT " + idAsig + " " + idTit);

        ResponseParser parser = new ResponseParser(response);

        if (!parser.isSuccess()) {
            throw new IOException(parser.getMessage());
        }
    }

    // -------------------------------------------------------------

    @SuppressWarnings("unchecked")
    public List<Asignatura> listFromTitulo(String idTit)
            throws IOException, ClassNotFoundException {

        String response =
                comunication.sendCommand("LISTASIGFROMTIT " + idTit);

        ResponseParser parser = new ResponseParser(response);

        if (!parser.isPREOK()) {
            return null;
        }

        Object obj =
                channelData.receiveObject(parser.getIp(), parser.getPort());

        String confirmacion = comunication.readLine();
        ResponseParser parserFinal = new ResponseParser(confirmacion);

        if (!parserFinal.isSuccess()) {
            throw new IOException("Servidor no confirmó LISTASIGFROMTIT");
        }

        return (List<Asignatura>) obj;
    }

    // -------------------------------------------------------------

    public void removeAsignaturaFromTitulo(String idAsig, String idTit)
            throws IOException {

        String response =
                comunication.sendCommand("REMOVEASIGFROMTIT " + idAsig + " " + idTit);

        ResponseParser parser = new ResponseParser(response);

        if (!parser.isSuccess()) {
            throw new IOException(parser.getMessage());
        }
    }
}
 