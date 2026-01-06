package edu.ucam.servidor;

import java.util.Hashtable;
import edu.ucam.domain.Titulacion;

public class ServidorRepository {

    private static Hashtable<String, Titulacion> titulos = new Hashtable<>();

    public static void addTitulo(Titulacion t) {
        titulos.put(t.getId(), t);
    }

    public static Titulacion getTitulo(String id) {
        return titulos.get(id);
    }

    public static boolean existeTitulo(String id) {
        return titulos.containsKey(id);
    }
}
