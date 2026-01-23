package edu.ucam.cliente.service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.ucam.cliente.interfaces.IChannelData;
import edu.ucam.cliente.service.ResponseParser;

public class ChannelData implements IChannelData {

    @Override
    public void sendObject(String ip, int port, Object model)
            throws IOException {

        try (Socket socketDatos = new Socket(ip, port);
             ObjectOutputStream oos =
                 new ObjectOutputStream(socketDatos.getOutputStream())) {

            oos.writeObject(model);
            oos.flush();
        }
    }

    @Override
    public Object receiveObject(String ip, int port)
            throws IOException, ClassNotFoundException {

        try (Socket socketDatos = new Socket(ip, port);
             ObjectInputStream ois =
                 new ObjectInputStream(socketDatos.getInputStream())) {

            return ois.readObject();
        }
    }


    @Override
    public void sendObjectFromPreOk(String preOkResponse, Object model)
            throws IOException, ClassNotFoundException {

        ResponseParser parser = new ResponseParser(preOkResponse);

        if (!parser.isPREOK()) {
            throw new IOException("Respuesta no es PREOK");
        }

        sendObject(parser.getIp(), parser.getPort(), model);
    }

    @Override
    public Object receiveObjectFromPreOk(String preOkResponse)
            throws IOException, ClassNotFoundException {

        ResponseParser parser = new ResponseParser(preOkResponse);

        if (!parser.isPREOK()) {
            throw new IOException("Respuesta no es PREOK");
        }

        return receiveObject(parser.getIp(), parser.getPort());
    }
}
