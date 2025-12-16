package edu.ucam.cliente.service;

import java.io.IOException;
import java.util.List;

import edu.ucam.cliente.interfaces.IChannelData;
import edu.ucam.cliente.interfaces.IComunicationServer;
import edu.ucam.domain.Asignatura;

public class SubjectRepository extends BaseRepository<Asignatura> {

	public SubjectRepository(IComunicationServer comunication, IChannelData channelData) {
		super(comunication, channelData, "ADDASIG", "REMOVEASIG", "GETASIG", "LISTASIG", "COUNTASIG", "UPDATEASIG");
	}
	
	
	public int sumaCreditos() {
		String response;
		int value = 0;
		try {
			response = comunication.sendCommand("SUMCREDITS");
			ResponseParser parser = new ResponseParser(response);
			if(parser.isPREOK()) {
				value = Integer.parseInt(parser.getMessage());
			}
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
		
		return value;
		
	}


	@Override
	public void delete(String id) throws IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Asignatura> list() throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void update(String id, Asignatura model) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int modelSize() {
		// TODO Auto-generated method stub
		return 0;
	}
}
