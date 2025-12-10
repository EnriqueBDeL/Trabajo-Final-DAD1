package edu.ucam.cliente.service;

import java.io.IOException;
import java.util.List;

import edu.ucam.cliente.interfaces.IChannelData;
import edu.ucam.cliente.interfaces.IComunicationServer;
import edu.ucam.cliente.interfaces.IRepository;

abstract class  BaseRepository<T> implements IRepository<T>{
	
	protected final IComunicationServer comunication;
	protected final IChannelData channelData;
	protected final String insertCommand;
	protected final String deleteCommand;
	protected final String getCommand;
	protected final String listCommand;
	protected final String countCommand;
	protected final String updateCommand;
	
	
	public BaseRepository(IComunicationServer comunication, IChannelData channelData, 
			String insertCommand, String deleteCommand, String getCommand, String listCommand, String countCommand, String updateCommand) {
				this.comunication = comunication;
				this.channelData = channelData;
				this.insertCommand = insertCommand;
				this.deleteCommand = deleteCommand;
				this.getCommand = getCommand;
				this.listCommand = listCommand;
				this.countCommand = countCommand;
				this.updateCommand = updateCommand;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public T getModel(String id) throws IOException, ClassNotFoundException {
		String response = comunication.sendCommand(getCommand);
		ResponseParser parser = new ResponseParser(response);
		
		if(parser.isPREOK()) {
			T responseModel = (T)channelData.receiveObject(parser.getIp(),  parser.getPort());
			return responseModel;
		}
		
		return null;
	}
	
	
	public void add(T model) throws IOException, ClassNotFoundException 
	{
		throw new IOException("NO IMPLEMENTADO");
	}
	
	
	public void delete(String id) throws IOException{
		throw new IOException("NO IMPLEMENTADO");
	}
	
	public List<T> list() throws IOException, ClassNotFoundException {
		throw new IOException("NO IMPLEMENTADO");
	}
	
	
	public void update(String id, T model) throws IOException, ClassNotFoundException {
		throw new IOException("NO IMPLEMENTADO");
	}
	
	public int modelSize() {
		return 0;
	}
	
	
	
	
	
	

}
