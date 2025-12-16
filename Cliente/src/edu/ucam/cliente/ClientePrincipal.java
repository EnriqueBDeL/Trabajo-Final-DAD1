package edu.ucam.cliente;

import java.util.Scanner;
import edu.ucam.cliente.service.TituRepository; 
import edu.ucam.domain.Titulacion;

public class ClientePrincipal {
    
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            ClienteERP cliente = new ClienteERP();
            System.out.println("--- CONECTADO AL SERVIDOR ---");
            
            // 1. Autenticación (Requisito: USER y PASS) [cite: 39]
            System.out.print("Usuario: ");
            String usuario = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            
            if(cliente.autenticar(usuario, password)) {
                System.out.println("¡Login correcto! Bienvenido " + usuario);
                
                // Accedemos al repositorio (puedes hacer un getter en ClienteERP o usarlo directo si es public)
                // Asumo que añades: public TituRepository getTituRepository() { return (TituRepository) tituRepository; }
                // Ojo: Si tituRepository es privado en ClienteERP, necesitarás un método getter allí.
                
                boolean salir = false;
                while(!salir) {
                    System.out.println("\n--- MENÚ GESTIÓN UNIVERSIDAD ---");
                    System.out.println("1. Añadir Título (ADDTIT)");
                    System.out.println("2. Consultar Título (GETTIT)");
                    System.out.println("3. Salir (EXIT)");
                    System.out.print("Seleccione opción: ");
                    String opcion = scanner.nextLine();
                    
                    switch (opcion) {
                        case "1": // ADDTIT
                            System.out.println("\n--- NUEVO TÍTULO ---");
                            System.out.print("ID Título: ");
                            String id = scanner.nextLine();
                            System.out.print("Nombre: ");
                            String nombre = scanner.nextLine();
                            
                            // Creamos el objeto vacío
                            Titulacion nuevaTitulacion = new Titulacion();
                            
                            // INTENTA ESCRIBIR ESTAS DOS LÍNEAS:
                            // Si al escribir el punto (.) no te sale setId, avísame.
                            // Pero normalmente si hay getId, hay setId.
                            try {
                                nuevaTitulacion.setId(id);
                                nuevaTitulacion.setNombre(nombre); 
                            } catch (Error e) {
                                // Si fallan los setters, es que la clase es rara, pero pruébalo
                            }

                            cliente.insertarTitulo(nuevaTitulacion); 
                            System.out.println("-> Solicitud de creación enviada.");
                            break;

                        case "2": // GETTIT
                            System.out.println("\n--- CONSULTAR TÍTULO ---");
                            System.out.print("ID a buscar: ");
                            String idBusqueda = scanner.nextLine();
                            
                            Titulacion t = cliente.obtenerTitulo(idBusqueda);
                            if (t != null) {
                                // AQUÍ SOLO USAMOS LO QUE VIMOS EN LA FOTO
                                System.out.println("RECUPERADO: " + t.getId() + " - " + t.getNombre());
                            } else {
                                System.out.println("-> Error: No se pudo recuperar o no existe.");
                            }
                            break;

                        case "3": // EXIT
                            cliente.cerrarSesion();
                            salir = true;
                            System.out.println("Sesión cerrada. Adiós.");
                            break;
                            
                        default:
                            System.out.println("Opción no válida.");
                    }
                }
            } else {
                System.out.println("Error de autenticación.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}