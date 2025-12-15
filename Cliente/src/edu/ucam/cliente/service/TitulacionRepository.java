package edu.ucam.cliente.service;

import java.io.IOException;
import java.util.List;
import edu.ucam.cliente.interfaces.IChannelData;
import edu.ucam.cliente.interfaces.IComunicationServer;
import edu.ucam.cliente.interfaces.IRepository;
import edu.ucam.domain.Titulacion; 

public class TitulacionRepository implements IRepository<Titulacion> {

    private IComunicationServer comServer;
    private IChannelData channelData;

    public TitulacionRepository(IComunicationServer comServer, IChannelData channelData) {
        this.comServer = comServer;
        this.channelData = channelData;
    }

    @Override
    public void add(Titulacion model) throws IOException, ClassNotFoundException {
        // Enviar comando ADDTIT
        String respuesta = comServer.sendCommand("ADDTIT"); 
        
        // Esperar respuesta PREOK <id> <cod> <ip> <puerto>
        if (respuesta != null && respuesta.startsWith("PREOK")) {
            String[] partes = respuesta.split(" ");
            String ip = partes[3];
            int puerto = Integer.parseInt(partes[4]);
            
            // Enviar objeto por canal de datos
            channelData.sendObject(ip, puerto, model);
            System.out.println(">> Objeto enviado correctamente.");
        } else {
            System.out.println("Error del servidor: " + respuesta);
        }
    }

    @Override
    public Titulacion getModel(String id) throws IOException, ClassNotFoundException {
        String respuesta = comServer.sendCommand("GETTIT " + id);
        
        if (respuesta != null && respuesta.startsWith("PREOK")) {
            String[] partes = respuesta.split(" ");
            String ip = partes[3];
            int puerto = Integer.parseInt(partes[4]);
            
            return (Titulacion) channelData.receiveObject(ip, puerto);
        }
        return null;
    }

    // Métodos vacíos para cumplir la interfaz (no necesarios en parcial)
    public void delete(String id) throws IOException {}
    public List<Titulacion> list() throws IOException, ClassNotFoundException { return null; }
    public void update(String id, Titulacion model) throws IOException, ClassNotFoundException {}
    public int modelSize() { return 0; }
}