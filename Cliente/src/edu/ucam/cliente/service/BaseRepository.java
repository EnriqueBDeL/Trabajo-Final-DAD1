package edu.ucam.cliente.service;

import java.io.IOException;
import java.util.List;
import edu.ucam.cliente.interfaces.*;

public abstract class BaseRepository<T> implements IRepository<T> {

    protected final IComunicationServer comunication;
    protected final IChannelData channelData;
    protected final String insertCommand;
    protected final String getCommand;
    // ... otros comandos se pueden añadir aquí ...

    public BaseRepository(IComunicationServer comunication, IChannelData channelData,
            String insertCommand, String deleteCommand, String getCommand, 
            String listCommand, String countCommand, String updateCommand) {
        this.comunication = comunication;
        this.channelData = channelData;
        this.insertCommand = insertCommand;
        this.getCommand = getCommand;
    }

    @Override
    public void add(T model) throws IOException, ClassNotFoundException {
        // 1. Enviar comando (Ej: ADDTIT) [cite: 39]
        String response = comunication.sendCommand(insertCommand);
        ResponseParser parser = new ResponseParser(response);

        // 2. Si Servidor dice PREOK, enviamos datos [cite: 87]
        if (parser.isPREOK()) {
            System.out.println("Servidor listo en " + parser.getIp() + ":" + parser.getPort());
            
            // 3. Enviar objeto por el canal de datos (puerto indicado) [cite: 119]
            channelData.sendObject(parser.getIp(), parser.getPort(), model);
            
            // 4. Esperar confirmación final (OK) por el canal de comandos [cite: 39]
            // Nota: En sockets bloqueantes, a veces es necesario leer la confirmación extra
            // pero para esta entrega, con el envío suele bastar si el server no manda nada más.
        } else {
            throw new IOException("Error esperando PREOK: " + parser.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getModel(String id) throws IOException, ClassNotFoundException {
        // 1. Enviar comando con ID (Ej: GETTIT 123) [cite: 43]
        String response = comunication.sendCommand(getCommand + " " + id);
        ResponseParser parser = new ResponseParser(response);

        // 2. Si es PREOK, recibimos datos
        if (parser.isPREOK()) {
            // 3. Recibir objeto del puerto indicado [cite: 108]
            return (T) channelData.receiveObject(parser.getIp(), parser.getPort());
        }
        return null; // O lanzar excepción si falla
    }
    
    // Dejar el resto de métodos con "throw new IOException" o vacíos para la parcial
    public void delete(String id) throws IOException {}
    public List<T> list() throws IOException, ClassNotFoundException { return null; }
    public void update(String id, T model) throws IOException, ClassNotFoundException {}
    public int modelSize() { return 0; }
}
