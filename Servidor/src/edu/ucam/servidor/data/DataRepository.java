package edu.ucam.servidor.data;

import java.util.Hashtable;
import java.lang.reflect.Method;

public class DataRepository<T> {

    private Hashtable<String, T> datos = new Hashtable<>();

    public void add(T obj) {
        datos.put(getId(obj), obj);
    }

    public T get(String id) {
        return datos.get(id);
    }

    public boolean existe(String id) {
        return datos.containsKey(id);
    }
}
