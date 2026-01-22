package edu.ucam.servidor;

import java.util.ArrayList;

import edu.ucam.domain.Asignatura;
import edu.ucam.domain.Matricula;
import edu.ucam.domain.Titulacion;
import edu.ucam.servidor.data.DataRepository;


public class ServidorRepository {

    public static DataRepository<Titulacion> repoTitulos = new DataRepository<>();
    public static DataRepository<Asignatura> repoAsignaturas = new DataRepository<>();
    public static DataRepository<Matricula> repoMatriculas = new DataRepository<>();


    public static void addTitulo(Titulacion t) { repoTitulos.add(t); }
    public static Titulacion getTitulo(String id) { return repoTitulos.get(id); }
    public static boolean existeTitulo(String id) { return repoTitulos.existe(id); }
    public static void removeTitulo(String id) { repoTitulos.remove(id); }

    
    
    public static void addAsignatura(Asignatura a) { repoAsignaturas.add(a); }
    public static Asignatura getAsignatura(String id) { return repoAsignaturas.get(id); }
    public static boolean existeAsignatura(String id) { return repoAsignaturas.existe(id); }
    public static void removeAsignatura(String id) { repoAsignaturas.remove(id); }


    public static void addMatricula(Matricula m) { repoMatriculas.add(m); }
    public static Matricula getMatricula(String id) { return repoMatriculas.get(id); }
    public static void removeMatricula(String id) { repoMatriculas.remove(id); }
    
    public static ArrayList<Titulacion> getListaTitulos() { return repoTitulos.getTodos(); }
    public static ArrayList<Asignatura> getListaAsignaturas() { return repoAsignaturas.getTodos(); }
    
}