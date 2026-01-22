package edu.ucam.cliente.service;

import java.io.IOException;
import java.util.List;

import edu.ucam.cliente.interfaces.IChannelData;
import edu.ucam.cliente.interfaces.IComunicationServer;
import edu.ucam.domain.Asignatura;

public class SubjectRepository extends BaseRepository<Asignatura> {

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////|	
	public SubjectRepository(IComunicationServer comunication, IChannelData channelData) {
		super(comunication, channelData, "ADDASIG", "REMOVEASIG", "GETASIG", "LISTASIG", "COUNTASIG", "UPDATEASIG");
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////|	
	
	
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

//----------------------------------------------------------------------------|

	@Override
	public void delete(String id) throws IOException {
		
	}

//----------------------------------------------------------------------------|

	@Override
	public List<Asignatura> list() throws IOException, ClassNotFoundException {
		return null;
	}

//----------------------------------------------------------------------------|


	@Override
	public void update(String id, Asignatura model) throws IOException, ClassNotFoundException {
		
	}

//----------------------------------------------------------------------------|
	
	@Override
	public int modelSize() {
		return 0;
	}
	
//----------------------------------------------------------------------------|

	public void addAsignaturaToTitulo(String idAsig, String idTit) throws IOException {

		String respuesta = comunication.sendCommand("ADDASIG2TIT " + idAsig + " " + idTit);
        ResponseParser parser = new ResponseParser(respuesta);
        
        if (parser.isSuccess()) {
            System.out.println("-> ¡Asignatura vinculada correctamente!");
        } else {
            System.out.println("-> Error: " + parser.getMessage());
        }
    }
	
//----------------------------------------------------------------------------|

	public List<Asignatura> listFromTitulo(String idTit) throws IOException, ClassNotFoundException {
        String respuesta = comunication.sendCommand("LISTASIGFROMTIT " + idTit);
        ResponseParser parser = new ResponseParser(respuesta);

        if (parser.isPREOK()) {
            try { Thread.sleep(50); } catch (Exception e) {}
            
            Object obj = channelData.receiveObject(parser.getIp(), parser.getPort());
            comunication.readLine();
            
            return (List<Asignatura>) obj;
        }
        return null;
    }
	
	
	
	
	
	
	
}
