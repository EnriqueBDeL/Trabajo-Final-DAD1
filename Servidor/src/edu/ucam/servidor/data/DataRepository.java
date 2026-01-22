package edu.ucam.servidor.data;

import java.util.ArrayList;
import java.util.Hashtable;
import java.lang.reflect.Method;

public class DataRepository<T> {

    private Hashtable<String, T> datos = new Hashtable<>();

    
    
    public void add(T obj) {
        String id = obtenerId(obj);
        
        if (id != null) {
            datos.put(id, obj);
        } else {
            System.out.println("Error: No se pudo obtener el ID del objeto.");
        }
    }

    
    
    public T get(String id) {
        return datos.get(id);
    }

    
    public boolean existe(String id) {
        return datos.containsKey(id);
    }

  
    private String obtenerId(T obj) {
    	
        try {

        	Method metodoGetId = obj.getClass().getMethod("getId");
            
            Object resultado = metodoGetId.invoke(obj);
            
            return (String) resultado;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void remove(String id) {
        datos.remove(id);
    }
    
    public ArrayList<T> getTodos() {
        return new ArrayList<>(datos.values());
    }
    
    
    
    public int getSize() { //Para contar usuarios. 
        return datos.size();
    }
    
    
    
}