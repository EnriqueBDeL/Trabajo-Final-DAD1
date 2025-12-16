package edu.ucam.cliente;

import java.io.IOException;
import java.util.Scanner; // Importante
import edu.ucam.domain.Titulacion; // Asegúrate de importar esto

public class ClientePrincipal {
    
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            ClienteERP cliente = new ClienteERP();
            System.out.println("Cliente conectado. Introduce credenciales.");
            
            System.out.print("Usuario: ");
            String usuario = scanner.nextLine();
            
            System.out.print("Password: ");
            String password = scanner.nextLine();
            
            if(cliente.autenticar(usuario, password)) {
                System.out.println("¡Login correcto!");
                
                // --- AQUÍ ES DONDE PROBAMOS TU CÓDIGO ---
                // Mantenemos el programa vivo preguntando qué hacer
                boolean salir = false;
                while(!salir) {
                    System.out.println("\n--- MENÚ ---");
                    System.out.println("1. Añadir Título (ADDTIT)");
                    System.out.println("2. Salir (EXIT)");
                    System.out.print("Elige: ");
                    String opcion = scanner.nextLine();
                    
                    if(opcion.equals("1")) {
                        // Creamos un título de prueba
                        Titulacion t = new Titulacion("Ingeniería Informática", opcion, null); // Asegúrate del constructor
                        // t.setId("1"); // Si necesitas setear ID manualmente
                        // Llamamos a tu repositorio (necesitarás getters en ClienteERP o hacerlo público)
                        // Para la prueba rápida, asumo que puedes acceder o modificar ClienteERP:
                        // cliente.getTituRepository().add(t); 
                        System.out.println("Comando enviado (Simulado hasta que conectes el repo)");
                    } else if (opcion.equals("2")) {
                        cliente.cerrarSesion();
                        salir = true;
                    }
                }
                
            } else {
                System.out.println("Autenticación incorrecta");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
            System.out.println("FIN DE LA APLICACIÓN");
        }
    }
}