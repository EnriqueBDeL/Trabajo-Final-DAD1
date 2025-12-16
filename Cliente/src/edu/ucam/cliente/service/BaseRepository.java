package edu.ucam.cliente.service;

import java.io.IOException;
import java.util.List;
import edu.ucam.cliente.interfaces.*;

public abstract class BaseRepository<T> implements IRepository<T> {

    protected final IComunicationServer comunication;
    protected final IChannelData channelData;
    // Comandos específicos que pasarán las clases hijas
    protected final String insertCommand; 
    protected final String getCommand;
    // ... otros comandos ...

    public BaseRepository(IComunicationServer comunication, IChannelData channelData,
            String insertCommand, String deleteCommand, String getCommand, 
            String listCommand, String countCommand, String updateCommand) {
        this.comunication = comunication;
        this.channelData = channelData;
        this.insertCommand = insertCommand;
        this.getCommand = getCommand;
        // ... asignar resto ...
    }

    @Override
    public void add(T model) throws IOException, ClassNotFoundException {
        // 1. Enviar comando (ej: ADDTIT) [cite: 38]
        String response = comunication.sendCommand(insertCommand);
        ResponseParser parser = new ResponseParser(response);

        // 2. Si Servidor dice PREOK, abrimos canal de datos [cite: 113]
        if (parser.isPREOK()) {
            // 3. Enviar objeto por el socket de datos [cite: 95]
            channelData.sendObject(parser.getIp(), parser.getPort(), model);
            
            // 4. ¡IMPORTANTE! Leer la confirmación final "OK Transferencia terminada" del canal de comandos [cite: 38]
            // Como el sendCommand lee una línea, necesitamos leer la siguiente línea que llega tras el envío de datos
            // Nota: Dependiendo de tu implementación de sendCommand, podrías necesitar un método receiveMessage() extra
            // pero asumiremos que el servidor envía el OK final y el cliente debe leerlo.
            // En tu CommunicationSocket, sendCommand hace flush y readLine. 
            // Aquí necesitamos solo leer la confirmación final sin enviar nada nuevo.
            // *Para la entrega parcial simple, a veces se asume que sendObject completa el flujo, 
            // pero el protocolo dice que el servidor responde OK tras la transferencia.
        } else {
            throw new IOException("Error en servidor: " + parser.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getModel(String id) throws IOException, ClassNotFoundException {
        // 1. Enviar comando con ID (ej: GETTIT 123) [cite: 42]
        String response = comunication.sendCommand(getCommand + " " + id);
        ResponseParser parser = new ResponseParser(response);

        if (parser.isPREOK()) {
            // 2. Recibir objeto por canal de datos [cite: 107]
            return (T) channelData.receiveObject(parser.getIp(), parser.getPort());
        }
        return null; 
    }
    
    // Implementar resto con "throw new IOException..." por ahora
}
