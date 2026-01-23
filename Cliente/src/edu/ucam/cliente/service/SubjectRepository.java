package edu.ucam.cliente.service;

import java.io.IOException;
import java.util.List;
import edu.ucam.cliente.interfaces.IChannelData;
import edu.ucam.cliente.interfaces.IComunicationServer;
import edu.ucam.domain.Asignatura;

public class SubjectRepository extends BaseRepository<Asignatura> {

    public SubjectRepository(IComunicationServer comunication, IChannelData channelData) {
        super(comunication, channelData, "ADDASIG", "REMOVEASIG", "GETASIG", "LISTASIG", "COUNTASIG", "UPDATEASIG");
    }


    public int sumaCreditos() {
        try {
            String response = comunication.sendCommand("SUMCREDITS");
            ResponseParser parser = new ResponseParser(response);
            if(parser.isPREOK()) {
                return Integer.parseInt(parser.getMessage());
            }
        } catch (IOException e) { e.printStackTrace(); }
        return 0;
    }

    public List<Asignatura> listFromTitulo(String idTit) throws IOException, ClassNotFoundException {
        String respuesta = comunication.sendCommand("LISTASIGFROMTIT " + idTit);
        ResponseParser parser = new ResponseParser(respuesta);

        if (parser.isPREOK()) {
            try { Thread.sleep(50); } catch (Exception e) {}
            Object obj = channelData.receiveObject(parser.getIp(), parser.getPort());
            comunication.readLine();
            return (List<Asignatura>) obj;
        }
        return null;
    }

    public void addAsignaturaToTitulo(String idAsig, String idTit) throws IOException {
        String respuesta = comunication.sendCommand("ADDASIG2TIT " + idAsig + " " + idTit);
        ResponseParser parser = new ResponseParser(respuesta);
        if (parser.isSuccess()) System.out.println("-> Vinculada correctamente.");
        else System.out.println("-> Error: " + parser.getMessage());
    }

    public void removeAsignaturaFromTitulo(String idAsig, String idTit) throws IOException {
        String respuesta = comunication.sendCommand("REMOVEASIGFROMTIT " + idAsig + " " + idTit);
        ResponseParser parser = new ResponseParser(respuesta);
        if (parser.isSuccess()) System.out.println("-> Desvinculada correctamente.");
        else System.out.println("-> Error: " + parser.getMessage());
    }
}
