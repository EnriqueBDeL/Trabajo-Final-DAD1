package edu.ucam.cliente.service;

import java.io.IOException;
import java.util.List;

import edu.ucam.cliente.interfaces.IChannelData;
import edu.ucam.cliente.interfaces.IComunicationServer;
import edu.ucam.domain.Titulacion;

public class TituRepository extends BaseRepository<Titulacion> {

    public TituRepository(IComunicationServer comunication, IChannelData channelData) {
        super(comunication, channelData, 
              "ADDTIT",       // insert
              "REMOVETIT",    // delete
              "GETTIT",       // get
              "LISTTIT",      // list
              "COUNTTIT",     // count
              "UPDATETIT");   // update
    }

	@Override
	public void delete(String id) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Titulacion> list() throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(String id, Titulacion model) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int modelSize() {
		// TODO Auto-generated method stub
		return 0;
	}
}