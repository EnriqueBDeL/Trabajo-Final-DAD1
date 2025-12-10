package edu.ucam.cliente.service;

import edu.ucam.domain.Titulacion;
import edu.ucam.cliente.interfaces.IChannelData;
import edu.ucam.cliente.interfaces.IComunicationServer;
import edu.ucam.cliente.service.BaseRepository;

public class TituRepository extends BaseRepository<Titulacion>{

	public TituRepository(IComunicationServer comunication, IChannelData channelData) {
		super(comunication, channelData, "ADDTIT", "", "", "", "", "");
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
	

}
