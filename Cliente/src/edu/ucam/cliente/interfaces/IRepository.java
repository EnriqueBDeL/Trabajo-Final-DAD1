package edu.ucam.cliente.interfaces;

import java.io.IOException;
import java.util.List;

public interface IRepository <T>{
	
	public void add(T model) throws IOException, ClassNotFoundException;
	public void delete(String id) throws IOException;
	public List<T> list() throws IOException, ClassNotFoundException;
	public void update(String id, T model) throws IOException, ClassNotFoundException;
	public T getModel(String id) throws IOException, ClassNotFoundException;
	public int modelSize();
	void remove(String id) throws IOException;
	T get(String id) throws IOException, ClassNotFoundException;
	List<T> getAll() throws IOException, ClassNotFoundException;
}