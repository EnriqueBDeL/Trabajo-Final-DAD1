package edu.ucam.cliente.interfaces;

import java.io.IOException;

public interface IChannelData {

    void sendObject(String ip, int port, Object model)
            throws IOException, ClassNotFoundException;

    Object receiveObject(String ip, int port)
            throws IOException, ClassNotFoundException;

    void sendObjectFromPreOk(String preOkResponse, Object model)
            throws IOException, ClassNotFoundException;

    Object receiveObjectFromPreOk(String preOkResponse)
            throws IOException, ClassNotFoundException;
}
