package edu.ucam.cliente.service;

import java.io.IOException;
import java.util.List;

import edu.ucam.cliente.interfaces.IChannelData;
import edu.ucam.cliente.interfaces.IComunicationServer;
import edu.ucam.domain.Titulacion;

public class TituRepository extends BaseRepository<Titulacion> {

	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////|    
    public TituRepository(IComunicationServer comunication, IChannelData channelData) {
        super(comunication, channelData, "ADDTIT", "REMOVETIT", "GETTIT", "LISTTIT", "COUNTTIT", "UPDATETIT");   
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////|    
	
    
    @Override
	public void delete(String id) throws IOException {
		
	}

//---------------------------------------------------------------------------------------|    
    
	@Override
	public List<Titulacion> list() throws IOException, ClassNotFoundException {
		return null;
	}

//---------------------------------------------------------------------------------------|    
	
	@Override
	public void update(String id, Titulacion model) throws IOException, ClassNotFoundException {
		
	}

//---------------------------------------------------------------------------------------|    
	
	
	@Override
	public int modelSize() {
		return 0;
	}
}