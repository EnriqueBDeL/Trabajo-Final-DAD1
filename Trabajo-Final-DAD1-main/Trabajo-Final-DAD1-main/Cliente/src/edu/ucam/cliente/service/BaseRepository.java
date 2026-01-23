package edu.ucam.cliente.service;

import java.io.IOException;
import java.util.List;

import edu.ucam.cliente.interfaces.*;
import edu.ucam.cliente.service.ResponseParser;

public abstract class BaseRepository<T> implements IRepository<T> {

    protected final IComunicationServer comunication;
    protected final IChannelData channelData;

    protected final String insertCommand;
    protected final String getCommand;
    protected final String deleteCommand;
    protected final String listCommand;
    protected final String countCommand;
    protected final String updateCommand;

    // ------------------------------------------------------------
    public BaseRepository(IComunicationServer comunication,
                          IChannelData channelData,
                          String insertCommand,
                          String deleteCommand,
                          String getCommand,
                          String listCommand,
                          String countCommand,
                          String updateCommand) {

        this.comunication = comunication;
        this.channelData = channelData;
        this.insertCommand = insertCommand;
        this.getCommand = getCommand;
        this.deleteCommand = deleteCommand;
        this.listCommand = listCommand;
        this.countCommand = countCommand;
        this.updateCommand = updateCommand;
    }
    // ------------------------------------------------------------

    @Override
    public void add(T model) throws IOException, ClassNotFoundException {

        String response = comunication.sendCommand(insertCommand);
        ResponseParser parser = new ResponseParser(response);

        if (!parser.isPREOK()) {
            throw new IOException("Error ADD: " + parser.getMessage());
        }

        
        channelData.sendObject(parser.getIp(), parser.getPort(), model);

        String confirmacion = comunication.readLine();
        ResponseParser finalParser = new ResponseParser(confirmacion);

        if (!finalParser.isSuccess()) {
            throw new IOException("Servidor no confirmó ADD: " + finalParser.getMessage());
        }
    }

    @Override
    public T getModel(String id) throws IOException, ClassNotFoundException {

        String response = comunication.sendCommand(getCommand + " " + id);
        ResponseParser parser = new ResponseParser(response);

        if (!parser.isPREOK()) {
            return null;
        }

        T objeto = (T) channelData.receiveObject(parser.getIp(), parser.getPort());

        String confirmacion = comunication.readLine();
        ResponseParser finalParser = new ResponseParser(confirmacion);

        if (!finalParser.isSuccess()) {
            throw new IOException("Servidor no confirmó GET");
        }

        return objeto;
    }

    @Override
    public void delete(String id) throws IOException {

        String response = comunication.sendCommand(deleteCommand + " " + id);
        ResponseParser parser = new ResponseParser(response);

        if (!parser.isSuccess()) {
            throw new IOException("Error DELETE: " + parser.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> list() throws IOException, ClassNotFoundException {

        String response = comunication.sendCommand(listCommand);
        ResponseParser parser = new ResponseParser(response);

        if (!parser.isPREOK()) {
            return null;
        }

        Object recibido = channelData.receiveObject(parser.getIp(), parser.getPort());

        String confirmacion = comunication.readLine();
        ResponseParser finalParser = new ResponseParser(confirmacion);

        if (!finalParser.isSuccess()) {
            throw new IOException("Servidor no confirmó LIST");
        }

        return (List<T>) recibido;
    }

    @Override
    public int modelSize() {

        try {
            String response = comunication.sendCommand(countCommand);
            ResponseParser parser = new ResponseParser(response);

            if (parser.isSuccess()) {
                return Integer.parseInt(parser.getMessage().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void update(String id, T model) throws IOException, ClassNotFoundException {

        String response = comunication.sendCommand(updateCommand + " " + id);
        ResponseParser parser = new ResponseParser(response);

        if (!parser.isPREOK()) {
            throw new IOException("Error UPDATE: " + parser.getMessage());
        }

        channelData.sendObject(parser.getIp(), parser.getPort(), model);

        String confirmacion = comunication.readLine();
        ResponseParser finalParser = new ResponseParser(confirmacion);

        if (!finalParser.isSuccess()) {
            throw new IOException("Servidor no confirmó UPDATE");
        }
    }
}
