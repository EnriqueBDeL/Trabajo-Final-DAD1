package edu.ucam.cliente.service;

import java.io.IOException;

import edu.ucam.cliente.interfaces.IAutentication;
import edu.ucam.cliente.interfaces.IComunicationServer;

public class AutenticationService implements IAutentication{
	
	private final IComunicationServer comunication;
	
	
	
/////////////////////////////////////////////////////////////////////|	
	public AutenticationService(IComunicationServer comunication) {
		
		this.comunication = comunication;
		
	}
/////////////////////////////////////////////////////////////////////|
	
	
	
	
//-----------------------------------------------------------------------------------------------|	
	
	@Override
	public boolean autenticar(String usuario, String password) throws IOException {
		
		String response = comunication.sendCommand("USER " + usuario);
		
		ResponseParser parser = new ResponseParser(response);
		
		
		if(parser.isSuccess()) {
			
			response = comunication.sendCommand("PASS " + password);
			parser = new ResponseParser(response);
            return parser.isSuccess();
            
		}
		
		return false;
	}

//-----------------------------------------------------------------------------------------------|	
	
	@Override
	public void closeSession() throws IOException {
		
		comunication.sendCommand("EXIT");
		
		comunication.disconnect();
		
	}

}