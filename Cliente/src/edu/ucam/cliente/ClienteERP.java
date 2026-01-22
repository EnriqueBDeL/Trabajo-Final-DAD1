package edu.ucam.cliente;

import java.io.IOException;
import java.util.List;

import edu.ucam.cliente.interfaces.IAutentication;
import edu.ucam.cliente.interfaces.IComunicationServer;
import edu.ucam.cliente.interfaces.IRepository;
import edu.ucam.cliente.interfaces.IChannelData;
import edu.ucam.cliente.service.AutenticationService;
import edu.ucam.cliente.service.ChannelData;
import edu.ucam.cliente.service.CommunicationSocket;
import edu.ucam.domain.Asignatura;
import edu.ucam.domain.Titulacion;
import edu.ucam.domain.Matricula;
import edu.ucam.cliente.service.SubjectRepository;
import edu.ucam.cliente.service.TituRepository;
import edu.ucam.cliente.service.MatriculaRepository;
import edu.ucam.cliente.service.ResponseParser; 



public class ClienteERP {
	
	private final IComunicationServer comunication;
    private final IAutentication autentication;
    
    private final IRepository<Asignatura> subjectRepository; 
    private final IRepository<Titulacion> tituRepository;
    private final IRepository<Matricula> matriculaRepository;
    
/////////////////////////////////////////////////////////////////////////////////////////////|    
    public ClienteERP() throws IOException{
    	
    	this.comunication = new CommunicationSocket();
        this.comunication.connect();
        
        IChannelData channelData = new ChannelData();
       
        this.autentication = new AutenticationService(this.comunication);
        
        this.subjectRepository = new SubjectRepository(comunication, channelData);
        this.tituRepository = new TituRepository(comunication, channelData);
        this.matriculaRepository = new MatriculaRepository(comunication, channelData);
    } 
/////////////////////////////////////////////////////////////////////////////////////////////|
   
    public boolean autenticar(String usuario, String password) throws IOException {
        return autentication.autenticar(usuario, password);
    }
    
    
    
    public void cerrarSesion() throws IOException {
        autentication.closeSession();
    }
    
    
    
    public void insertarTitulo(Titulacion t) throws Exception {
        tituRepository.add(t);
    }
    
    
    
    public Titulacion obtenerTitulo(String id) throws Exception {
        return tituRepository.getModel(id);
    }

    
    
    
    public void borrarTitulo(String id) throws IOException {
        tituRepository.delete(id);
    }
    
    
    public int obtenerTotalTitulos() {
        return tituRepository.modelSize();
    }
    
    
    
    public void insertarAsignatura(Asignatura asig) throws Exception {
        subjectRepository.add(asig);
    }
    
    
    
    public Asignatura obtenerAsignatura(String id) throws Exception {
        return subjectRepository.getModel(id);
    }

    
    public void borrarAsignatura(String id) throws IOException {
        subjectRepository.delete(id);
    }
    
    
    public void insertarMatricula(Matricula m) throws Exception {
        matriculaRepository.add(m);
    }
    
    
    
    public Matricula obtenerMatricula(String id) throws Exception {
        return matriculaRepository.getModel(id);
    }
    
    
    public void borrarMatricula(String id) throws IOException {
        matriculaRepository.delete(id);
    }
    
    
    
    public List<Titulacion> listarTitulos() throws Exception {
        return tituRepository.list();
    }
    
    
    
    public List<Asignatura> listarAsignaturas() throws Exception {
        return subjectRepository.list();
    }
    
    
    
    
    public void vincularAsignatura(String idAsig, String idTit) throws IOException {
        ((SubjectRepository)subjectRepository).addAsignaturaToTitulo(idAsig, idTit);
    }

    
    
    public List<Asignatura> listarAsignaturasDeTitulo(String idTit) throws Exception {
        return ((SubjectRepository)subjectRepository).listFromTitulo(idTit);
    }
    
    
    
    public void desvincularAsignatura(String idAsig, String idTit) throws IOException {
        ((SubjectRepository)subjectRepository).removeAsignaturaFromTitulo(idAsig, idTit);
    }
    
    
    
    
    public int obtenerSesionesActivas() throws IOException {
    
    	String respuesta = comunication.sendCommand("SESIONES");
     
        ResponseParser parser = new ResponseParser(respuesta);
        if (parser.isSuccess()) {
            return Integer.parseInt(parser.getMessage().trim());
        }
        return 0;
    }
    
    
}