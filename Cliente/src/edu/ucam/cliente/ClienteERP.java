package edu.ucam.cliente;

import edu.ucam.cliente.interfaces.IAutentication;
import edu.ucam.cliente.interfaces.IComunicationServer;
import edu.ucam.cliente.interfaces.IRepository;
import edu.ucam.cliente.interfaces.IChannelData;
import edu.ucam.cliente.service.AutenticationService;
import edu.ucam.cliente.service.ChannelData;
import edu.ucam.cliente.service.CommunicationSocket;
import edu.ucam.domain.Asignatura;
import edu.ucam.domain.Titulacion;
import edu.ucam.cliente.service.SubjectRepository;
import edu.ucam.cliente.service.TituRepository;

public class ClienteERP {
	
	private final IComunicationServer comunication;
    private final IAutentication autentication;
    private final IRepository<Asignatura> subjectRespository;
    private final IRepository<Titulacion> tituRepository;
    
    
    public ClienteERP() throws IOException{
    	this.comunication = new CommunicationSocket();
        this.comunication.connect();
        
        IChannelData channelData = new ChannelData();
        this.autentication = new AutenticationService(this.comunication);
        this.subjectRespository = new SubjectRepository(comunication, channelData);
        this.tituRepository = new TituRepository(comunication, channelData);
        
    } 
    
    
    public boolean autenticar(String usuario, String password) throws IOException {
        return autentication.autenticar(usuario, password);
    }
    
    public void cerrarSesion() throws IOException {
        autentication.closeSession();
    }
    
    public boolean insertarAsignatura(Asignatura asig) {
    	return false;
    }
    
    
    
}