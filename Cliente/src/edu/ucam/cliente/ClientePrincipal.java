package edu.ucam.cliente;

import java.io.IOException;

import edu.ucam.domain.Alumno;

public class ClientePrincipal {
	
	public static void main(String args[]) throws IOException {
		//TODO hay que preguntar al usuario por usuario y password
		String usuario = null;
		String password = null;
		
		
		ClienteERP cliente = new ClienteERP();
		if(cliente.autenticar(usuario, password)) {
			
		} else {
			System.out.print("Autenticación incorrecta");
		}
		
		System.out.println("FIN DE LA APPLICACIÓN");
		
	}

}