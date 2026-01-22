package edu.ucam.servidor;

import java.util.ArrayList;
import java.util.Hashtable;

import edu.ucam.domain.Asignatura;
import edu.ucam.domain.Matricula;
import edu.ucam.domain.Titulacion;
import edu.ucam.servidor.data.DataRepository;


public class ServidorRepository {

    public static DataRepository<Titulacion> repoTitulos = new DataRepository<>();
    public static DataRepository<Asignatura> repoAsignaturas = new DataRepository<>();
    public static DataRepository<Matricula> repoMatriculas = new DataRepository<>();

    
    private static Hashtable<String, ArrayList<String>> asignaturasPorTitulo = new Hashtable<>();

    
    
    public static void addTitulo(Titulacion t) { repoTitulos.add(t); }
    public static Titulacion getTitulo(String id) { return repoTitulos.get(id); }
    public static boolean existeTitulo(String id) { return repoTitulos.existe(id); }
    public static void removeTitulo(String id) { repoTitulos.remove(id); }
    public static ArrayList<Titulacion> getListaTitulos() { return repoTitulos.getTodos(); }

    
    
    public static void addAsignatura(Asignatura a) { repoAsignaturas.add(a); }
    public static Asignatura getAsignatura(String id) { return repoAsignaturas.get(id); }
    public static boolean existeAsignatura(String id) { return repoAsignaturas.existe(id); }
    public static void removeAsignatura(String id) { repoAsignaturas.remove(id); }
    public static ArrayList<Asignatura> getListaAsignaturas() { return repoAsignaturas.getTodos(); }


    
    public static boolean agregarAsignaturaATitulo(String idAsignatura, String idTitulo) {
        
    	if (!repoAsignaturas.existe(idAsignatura) || !repoTitulos.existe(idTitulo)) {
            return false;
        }

        if (!asignaturasPorTitulo.containsKey(idTitulo)) {
            asignaturasPorTitulo.put(idTitulo, new ArrayList<>());
        }

        ArrayList<String> lista = asignaturasPorTitulo.get(idTitulo);
        
        if (!lista.contains(idAsignatura)) {
            lista.add(idAsignatura);
            return true;
        }
        return false; 
    }
    
    public static ArrayList<Asignatura> getAsignaturasDeTitulo(String idTitulo) {
        ArrayList<Asignatura> resultado = new ArrayList<>();
        
        if (asignaturasPorTitulo.containsKey(idTitulo)) {
            ArrayList<String> listaIDs = asignaturasPorTitulo.get(idTitulo);
            
            for (String id : listaIDs) {
                Asignatura asig = repoAsignaturas.get(id);
                if (asig != null) {
                    resultado.add(asig);
                }
            }
        }
        return resultado;
    }
    
    
    
    public static void addMatricula(Matricula m) { repoMatriculas.add(m); }
    public static Matricula getMatricula(String id) { return repoMatriculas.get(id); }
    public static void removeMatricula(String id) { repoMatriculas.remove(id); }
    
    
    
}