package edu.ucam.cliente.service;

import java.io.IOException;
import java.util.List;
import edu.ucam.cliente.interfaces.*;

public abstract class BaseRepository<T> implements IRepository<T> {

    protected IComunicationServer comunication;
    protected IChannelData channelData;
    
    protected String addCommand;
    protected String updateCommand;
    protected String removeCommand;
    protected String getCommand;
    protected String listCommand;
    protected String countCommand;

    public BaseRepository(IComunicationServer comunication, IChannelData channelData,
            String insertCommand, String deleteCommand, String getCommand, 
            String listCommand, String countCommand, String updateCommand) {
        this.comunication = comunication;
        this.channelData = channelData;
        this.addCommand = insertCommand;
        this.removeCommand = deleteCommand;
        this.getCommand = getCommand;
        this.listCommand = listCommand;
        this.countCommand = countCommand;
        this.updateCommand = updateCommand;
    }

    @Override
    public void add(T model) throws IOException, ClassNotFoundException {
        String response = comunication.sendCommand(addCommand);
        ResponseParser parser = new ResponseParser(response);
        
        if (parser.isPREOK()) {
            try { Thread.sleep(50); } catch (Exception e) {}
            channelData.sendObject(parser.getIp(), parser.getPort(), model);
            comunication.readLine(); 
        }
    }


    
    @Override
    public void update(String id, T model) throws IOException, ClassNotFoundException {
        String response = comunication.sendCommand(updateCommand + " " + id);
        ResponseParser parser = new ResponseParser(response);

        if (parser.isPREOK()) {
            try { Thread.sleep(50); } catch (Exception e) {}
            channelData.sendObject(parser.getIp(), parser.getPort(), model);
            comunication.readLine();
        }
    }


    
    @Override
    public void delete(String id) throws IOException {
        comunication.sendCommand(removeCommand + " " + id);
    }
    
    @Override
    public void remove(String id) throws IOException {
        delete(id); 
    }

    
    
    @Override
    public T get(String id) throws IOException, ClassNotFoundException {
        String response = comunication.sendCommand(getCommand + " " + id);
        ResponseParser parser = new ResponseParser(response);

        if (parser.isPREOK()) {
            try { Thread.sleep(50); } catch (Exception e) {}
            Object obj = channelData.receiveObject(parser.getIp(), parser.getPort());
            comunication.readLine();
            return (T) obj;
        }
        return null;
    }

    @Override
    public T getModel(String id) throws IOException, ClassNotFoundException {
        return get(id);
    }


    @Override

    public List<T> list() throws IOException, ClassNotFoundException {
        String response = comunication.sendCommand(listCommand);
        ResponseParser parser = new ResponseParser(response);

        if (parser.isPREOK()) {
            try { Thread.sleep(50); } catch (Exception e) {}
            Object obj = channelData.receiveObject(parser.getIp(), parser.getPort());
            comunication.readLine(); 
            return (List<T>) obj;
        }
        return null;
    }

    
    
    @Override
    public List<T> getAll() throws IOException, ClassNotFoundException {
        return list(); 
    }
    

    @Override
    public int modelSize() {
        if (countCommand == null) return 0;
        try {
            String respuesta = comunication.sendCommand(countCommand);
            ResponseParser parser = new ResponseParser(respuesta);
            if (parser.isSuccess()) {
                return Integer.parseInt(parser.getMessage().trim());
            }
        } catch (Exception e) {}
        return 0;
    }
}