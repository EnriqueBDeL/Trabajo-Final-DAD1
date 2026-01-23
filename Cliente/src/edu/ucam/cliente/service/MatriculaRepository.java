package edu.ucam.cliente.service;

import edu.ucam.cliente.interfaces.IChannelData;
import edu.ucam.cliente.interfaces.IComunicationServer;
import edu.ucam.domain.Matricula;

public class MatriculaRepository extends BaseRepository<Matricula> {

    public MatriculaRepository(IComunicationServer comunication, IChannelData channelData) {
        super(comunication, channelData, "ADDMATRICULA", "REMOVEMATRICULA", "GETMATRICULA", "LISTMATRICULA", "COUNTMATRICULA", "UPDATEMATRICULA");
    }
}