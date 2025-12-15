package edu.ucam.cliente.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import edu.ucam.cliente.interfaces.IChannelData;

public class ChannelDataService implements IChannelData {

    @Override
    public void sendObject(String ip, int port, Object model) throws IOException {
        Socket socketDatos = new Socket(ip, port);
        ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
        
        oos.writeObject(model);
        oos.flush();
        
        
        oos.close();
        socketDatos.close();
    }

    @Override
    public Object receiveObject(String ip, int port) throws IOException {
        Socket socketDatos = new Socket(ip, port);
        ObjectInputStream ois = new ObjectInputStream(socketDatos.getInputStream());
        
        Object recibido = null;
        try {
            recibido = ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        ois.close();
        socketDatos.close();
        return recibido;
    }
}