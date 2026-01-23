package edu.ucam.servidor.data;

import java.util.Hashtable;
import java.util.ArrayList;
import java.lang.reflect.Method;

public class DataRepository<T> {

    private Hashtable<String, T> datos = new Hashtable<>();

    private String getId(T obj) {
        try {
            Method m = obj.getClass().getMethod("getId");
            return (String) m.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException("El objeto no tiene método getId()");
        }
    }

    public void add(T obj) {
        datos.put(getId(obj), obj);
    }

    public T get(String id) {
        return datos.get(id);
    }

    public boolean existe(String id) {
        return datos.containsKey(id);
    }

    public void remove(String id) {
        datos.remove(id);
    }

    public ArrayList<T> getTodos() {
        return new ArrayList<>(datos.values());
    }

    public int getSize() {
        return datos.size();
    }
}