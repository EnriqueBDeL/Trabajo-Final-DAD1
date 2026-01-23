package edu.ucam.cliente;

import java.io.IOException;
import java.util.List;
import java.util.Hashtable;

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

    private boolean autenticado = false;

    // ---------------------------------------------------------
    public ClienteERP() throws IOException {

        this.comunication = new CommunicationSocket();
        this.comunication.connect();

        IChannelData channelData = new ChannelData();

        this.autentication = new AutenticationService(this.comunication);

        this.subjectRepository = new SubjectRepository(comunication, channelData);
        this.tituRepository = new TituRepository(comunication, channelData);
        this.matriculaRepository = new MatriculaRepository(comunication, channelData);
    }
    // ---------------------------------------------------------

    private void checkAuth() {
        if (!autenticado)
            throw new IllegalStateException("Operación no permitida: no autenticado");
    }

    public boolean autenticar(String usuario, String password) throws IOException {
        autenticado = autentication.autenticar(usuario, password);
        return autenticado;
    }

    public void cerrarSesion() throws IOException {
        autenticado = false;
        autentication.closeSession();
    }

    // ---------------- TITULACIONES ----------------

    public void insertarTitulo(Titulacion t) throws Exception {
        checkAuth();
        tituRepository.add(t);
    }

    public Titulacion obtenerTitulo(String id) throws Exception {
        checkAuth();
        return tituRepository.getModel(id);
    }

    public void borrarTitulo(String id) throws IOException {
        checkAuth();
        tituRepository.delete(id);
    }

    public int obtenerTotalTitulos() {
        checkAuth();
        return tituRepository.modelSize();
    }

    public List<Titulacion> listarTitulos() throws Exception {
        checkAuth();
        return tituRepository.list();
    }

    public void actualizarTitulo(String id, String nuevoNombre) throws Exception {
        checkAuth();
        Titulacion t = tituRepository.getModel(id);
        t.setNombre(nuevoNombre);
        tituRepository.update(id, t);
    }

    // ---------------- ASIGNATURAS ----------------

    public void insertarAsignatura(Asignatura asig) throws Exception {
        checkAuth();
        subjectRepository.add(asig);
    }

    public Asignatura obtenerAsignatura(String id) throws Exception {
        checkAuth();
        return subjectRepository.getModel(id);
    }

    public void borrarAsignatura(String id) throws IOException {
        checkAuth();
        subjectRepository.delete(id);
    }

    public List<Asignatura> listarAsignaturas() throws Exception {
        checkAuth();
        return subjectRepository.list();
    }

    public void vincularAsignatura(String idAsig, String idTit) throws IOException {
        checkAuth();
        ((SubjectRepository) subjectRepository).addAsignaturaToTitulo(idAsig, idTit);
    }

    public List<Asignatura> listarAsignaturasDeTitulo(String idTit) throws Exception {
        checkAuth();
        return ((SubjectRepository) subjectRepository).listFromTitulo(idTit);
    }

    public void desvincularAsignatura(String idAsig, String idTit) throws IOException {
        checkAuth();
        ((SubjectRepository) subjectRepository).removeAsignaturaFromTitulo(idAsig, idTit);
    }

    // ---------------- MATRÍCULAS ----------------

    public void insertarMatricula(Matricula m) throws Exception {
        checkAuth();
        matriculaRepository.add(m);
    }

    public Matricula obtenerMatricula(String id) throws Exception {
        checkAuth();
        return matriculaRepository.getModel(id);
    }

    public void borrarMatricula(String id) throws IOException {
        checkAuth();
        matriculaRepository.delete(id);
    }

    public void actualizarMatricula(String id, Matricula nuevaMatricula) throws Exception {
        checkAuth();
        Matricula actual = matriculaRepository.getModel(id);
        actual.setAlumno(nuevaMatricula.getAlumno());
        Hashtable<String, Asignatura> tabla = new Hashtable<>();
        for (Asignatura a : nuevaMatricula.getAsignaturas()) {
            tabla.put(a.getId(), a);
        }

        actual.setAsignaturas(tabla);

        matriculaRepository.update(id, actual);
    }

    // ---------------- SESIONES ----------------

    public int obtenerSesionesActivas() throws IOException {
        checkAuth();
        String respuesta = comunication.sendCommand("SESIONES");
        ResponseParser parser = new ResponseParser(respuesta);
        if (parser.isSuccess()) {
            return Integer.parseInt(parser.getMessage().trim());
        }
        return 0;
    }
}
