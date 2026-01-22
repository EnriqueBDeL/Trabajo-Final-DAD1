package edu.ucam.cliente.service;

import java.io.IOException;
import java.util.List;
import edu.ucam.cliente.interfaces.*;

public abstract class BaseRepository<T> implements IRepository<T> {

    protected final IComunicationServer comunication;
    protected final IChannelData channelData;
    protected final String insertCommand;
    protected final String getCommand;
    protected final String deleteCommand;
    protected final String listCommand;
    protected final String countCommand;


    
////////////////////////////////////////////////////////////////////////////////////////////|     
    public BaseRepository(IComunicationServer comunication, IChannelData channelData,
            String insertCommand, String deleteCommand, String getCommand, 
            String listCommand, String countCommand, String updateCommand) {
        this.comunication = comunication;
        this.channelData = channelData;
        this.insertCommand = insertCommand;
        this.getCommand = getCommand;
        this.deleteCommand = deleteCommand;
        this.listCommand = listCommand;
        this.countCommand = countCommand;
        
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
    
    @Override
    public void delete(String id) throws IOException {

    	String response = comunication.sendCommand(deleteCommand + " " + id);
        
        ResponseParser parser = new ResponseParser(response);
        
        
        if (parser.isSuccess()) {
        	
            System.out.println("-> Eliminado correctamente.");
            
        } else {
        	
            System.out.println("-> Error al eliminar: " + parser.getMessage());
       
        }
    }
    
//----------------------------------------------------------------------------------------------|  
   
public List<T> list() throws IOException, ClassNotFoundException {
        
        String respuesta = comunication.sendCommand(listCommand); 
        ResponseParser parser = new ResponseParser(respuesta);

        if (parser.isPREOK()) {
            
            try { 
                Thread.sleep(50);
            } catch (InterruptedException e) {}

            Object objetoRecibido = channelData.receiveObject(parser.getIp(), parser.getPort());
            
            comunication.readLine(); 
            
            return (List<T>) objetoRecibido;
        }
        
        return null;
    }

//----------------------------------------------------------------------------------------------|  


	@Override
	public int modelSize() {

		try {
	        String respuesta = comunication.sendCommand(countCommand);

	        ResponseParser parser = new ResponseParser(respuesta);
	        
	        if (parser.isSuccess()) {
	        
	            return Integer.parseInt(parser.getMessage().trim());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return 0; 
	}

	public void update(String id, T model) throws IOException, ClassNotFoundException {}

}
