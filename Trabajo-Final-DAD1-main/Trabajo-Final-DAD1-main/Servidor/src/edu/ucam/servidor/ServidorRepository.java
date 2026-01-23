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

    
    private static Hashtable<String, ArrayList<String>> asignaturasPorTitulo =
            new Hashtable<>();

    // ---------------- TITULOS ----------------
    public static void addTitulo(Titulacion t) {
        repoTitulos.add(t);
    }

    public static Titulacion getTitulo(String id) {
        return repoTitulos.get(id);
    }

    public static boolean existeTitulo(String id) {
        return repoTitulos.existe(id);
    }

    public static void removeTitulo(String id) {
        repoTitulos.remove(id);
        asignaturasPorTitulo.remove(id);
    }

    public static ArrayList<Titulacion> getListaTitulos() {
        return repoTitulos.getTodos();
    }

    public static int contarTitulos() {
        return repoTitulos.getSize();
    }

    // ---------------- ASIGNATURAS ----------------
    public static void addAsignatura(Asignatura a) {
        repoAsignaturas.add(a);
    }

    public static Asignatura getAsignatura(String id) {
        return repoAsignaturas.get(id);
    }

    public static boolean existeAsignatura(String id) {
        return repoAsignaturas.existe(id);
    }

    public static void removeAsignatura(String id) {
        repoAsignaturas.remove(id);
        for (ArrayList<String> lista : asignaturasPorTitulo.values()) {
            lista.remove(id);
        }
    }

    public static ArrayList<Asignatura> getListaAsignaturas() {
        return repoAsignaturas.getTodos();
    }

    // ---------------- RELACIÓN ----------------
    public static boolean agregarAsignaturaATitulo(String idAsig, String idTit) {

        if (!existeAsignatura(idAsig) || !existeTitulo(idTit)) {
            return false;
        }

        asignaturasPorTitulo.putIfAbsent(idTit, new ArrayList<>());

        ArrayList<String> lista = asignaturasPorTitulo.get(idTit);
        if (lista.contains(idAsig)) return false;

        lista.add(idAsig);
        return true;
    }

    public static ArrayList<Asignatura> getAsignaturasDeTitulo(String idTit) {

        ArrayList<Asignatura> resultado = new ArrayList<>();
        if (!asignaturasPorTitulo.containsKey(idTit)) return resultado;

        for (String idAsig : asignaturasPorTitulo.get(idTit)) {
            Asignatura a = repoAsignaturas.get(idAsig);
            if (a != null) resultado.add(a);
        }
        return resultado;
    }

    public static boolean eliminarAsignaturaDeTitulo(String idAsig, String idTit) {

        if (!asignaturasPorTitulo.containsKey(idTit)) return false;
        return asignaturasPorTitulo.get(idTit).remove(idAsig);
    }

    // ---------------- MATRÍCULAS ----------------
    public static boolean existeMatricula(String id) {
        return repoMatriculas.existe(id);
    }

    public static void addMatricula(Matricula m) {
        repoMatriculas.add(m);
    }

    public static Matricula getMatricula(String id) {
        return repoMatriculas.get(id);
    }

    public static void removeMatricula(String id) {
        repoMatriculas.remove(id);
    }

    public static ArrayList<Matricula> getListaMatriculas() {
        return repoMatriculas.getTodos();
    }
    
    
}
