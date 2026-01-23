package edu.ucam.cliente.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import edu.ucam.cliente.interfaces.IChannelData;

public class ChannelData implements IChannelData {

    @Override
    public void sendObject(String ip, int port, Object obj) {
        try (Socket socket = new Socket(ip, port);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            
            oos.writeObject(obj);
            oos.flush();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object receiveObject(String ip, int port) {
        try (Socket socket = new Socket(ip, port);
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            
            return ois.readObject();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}