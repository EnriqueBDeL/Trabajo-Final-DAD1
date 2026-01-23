package edu.ucam.servidor.data;

import java.util.ArrayList;
import java.util.Hashtable;

public class DataRepository<T> {

    private Hashtable<String, T> data = new Hashtable<>();

    private static int sesionesActivas = 0;


    public void add(T obj) {
        try {
            String id = (String) obj.getClass()
                                    .getMethod("getId")
                                    .invoke(obj);
            data.put(id, obj);
        } catch (Exception e) {
            throw new RuntimeException("El objeto no tiene método getId()");
        }
    }

    public T get(String id) {
        return data.get(id);
    }

    public boolean existe(String id) {
        return data.containsKey(id);
    }

    public void remove(String id) {
        data.remove(id);
    }

    public ArrayList<T> getTodos() {
        return new ArrayList<>(data.values());
    }

    public int getSize() {
        return data.size();
    }

    public static synchronized void incrementarSesiones() {
        sesionesActivas++;
    }

    public static synchronized void decrementarSesiones() {
        sesionesActivas--;
    }

    public static synchronized int getSesionesActivas() {
        return sesionesActivas;
    }
}
