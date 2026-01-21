package edu.ucam.cliente.service;

import java.io.IOException;
import java.util.List;
import edu.ucam.cliente.interfaces.IChannelData;
import edu.ucam.cliente.interfaces.IComunicationServer;
import edu.ucam.domain.Matricula;



public class MatriculaRepository extends BaseRepository<Matricula> {

    public MatriculaRepository(IComunicationServer comunication, IChannelData channelData) {
    	super(comunication, channelData, "ADDMATRICULA", "REMOVEMATRICULA", "GETMATRICULA", "LISTMATRICULA", "COUNTMATRICULA", "UPDATEMATRICULA");
    }

    
    
    @Override
    public void delete(String id) throws IOException { }

    @Override
    public List<Matricula> list() throws IOException, ClassNotFoundException { return null; }

    @Override
    public void update(String id, Matricula model) throws IOException, ClassNotFoundException { }

    @Override
    public int modelSize() { return 0; }
}