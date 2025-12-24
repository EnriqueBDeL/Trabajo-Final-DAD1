package edu.ucam.cliente.service;

import java.io.IOException;
import java.util.List;
import edu.ucam.cliente.interfaces.*;

public abstract class BaseRepository<T> implements IRepository<T> {

    protected final IComunicationServer comunication;
    protected final IChannelData channelData;
    protected final String insertCommand;
    protected final String getCommand;

    
////////////////////////////////////////////////////////////////////////////////////////////|     
    public BaseRepository(IComunicationServer comunication, IChannelData channelData,
            String insertCommand, String deleteCommand, String getCommand, 
            String listCommand, String countCommand, String updateCommand) {
        this.comunication = comunication;
        this.channelData = channelData;
        this.insertCommand = insertCommand;
        this.getCommand = getCommand;
    }
////////////////////////////////////////////////////////////////////////////////////////////|  
    
    
//----------------------------------------------------------------------------------------------|  

    @Override
    public void add(T model) throws IOException, ClassNotFoundException {
     
    	String response = comunication.sendCommand(insertCommand);
        
    	ResponseParser parser = new ResponseParser(response);

       
    	if (parser.isPREOK()) {
        	
            System.out.println("Servidor listo en " + parser.getIp() + ":" + parser.getPort());
            channelData.sendObject(parser.getIp(), parser.getPort(), model);
            
            String confirmacion = comunication.readLine(); 
            
        } else {
            throw new IOException("Error esperando PREOK: " + parser.getMessage());
        }
    }

//----------------------------------------------------------------------------------------------|  

    
    @SuppressWarnings("unchecked") //Silencia la queja del compilador sobre el "T objeto = (T) channelData.receiveObject(...);"
    @Override
    public T getModel(String id) throws IOException, ClassNotFoundException {
        
    	String response = comunication.sendCommand(getCommand + " " + id);
        
    	ResponseParser parser = new ResponseParser(response);

        
    	
    	if (parser.isPREOK()) {
        
    		try { 
    			
    			Thread.sleep(50); 
    			
    			} catch (InterruptedException e) {
    				
    				
    			} 

            T objeto = (T) channelData.receiveObject(parser.getIp(), parser.getPort());
            
            String confirmacion = comunication.readLine(); 
            
            return objeto;
        }
        return null;
    }
    
//----------------------------------------------------------------------------------------------|  
    
    public void delete(String id) throws IOException {}
    public List<T> list() throws IOException, ClassNotFoundException { return null; }
    public void update(String id, T model) throws IOException, ClassNotFoundException {}
    public int modelSize() { return 0; }
}
