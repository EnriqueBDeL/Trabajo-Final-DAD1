package edu.ucam.cliente;

import java.util.Scanner;
import edu.ucam.domain.Asignatura;
import edu.ucam.domain.Matricula;
import edu.ucam.domain.Titulacion;

public class ClientePrincipal {
    
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            ClienteERP cliente = new ClienteERP();
            System.out.println("--- CONECTADO AL SERVIDOR ---");
            
            System.out.print("Usuario: ");
            String usuario = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();
            
            if(cliente.autenticar(usuario, password)) {
                System.out.println("¡Login correcto! Bienvenido " + usuario);

                boolean salir = false;
               
                while(!salir) {
                    System.out.println("\n--- MENÚ GESTIÓN UNIVERSIDAD ---");
                    System.out.println("1. Añadir Título (ADDTIT)");
                    System.out.println("2. Consultar Título (GETTIT)");
                    System.out.println("3. Añadir Asignatura (ADDASIG)");
                    System.out.println("4. Consultar Asignatura (GETASIG)");
                    System.out.println("5. Añadir Matrícula (ADDMATRICULA)");
                    System.out.println("6. Consultar Matrícula (GETMATRICULA)");
                    System.out.println("0. Salir (EXIT)");
                    System.out.print("Seleccione opción: ");
                    String opcion = scanner.nextLine();
                    
                    switch (opcion) {
                        case "1": 
                            System.out.println("\n--- NUEVO TÍTULO ---");
                            System.out.print("ID: "); String idT = scanner.nextLine();
                            System.out.print("Nombre: "); String nomT = scanner.nextLine();
                            Titulacion t = new Titulacion();
                            t.setId(idT); t.setNombre(nomT);
                            cliente.insertarTitulo(t);
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|                                                           
                        case "2": 
                            System.out.print("ID a buscar: ");
                            Titulacion tr = cliente.obtenerTitulo(scanner.nextLine());
                            if(tr!=null) System.out.println("RECUPERADO: " + tr.getNombre());
                            else System.out.println("No encontrado.");
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|                               
                        case "3": 
                            System.out.println("\n--- NUEVA ASIGNATURA ---");
                            System.out.print("ID: "); String idA = scanner.nextLine();
                            System.out.print("Nombre: "); String nomA = scanner.nextLine();
                            Asignatura a = new Asignatura();
                            a.setId(idA); a.setNombre(nomA);
                            cliente.insertarAsignatura(a);
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|                               
                        case "4": 
                            System.out.print("ID a buscar: ");
                            Asignatura ar = cliente.obtenerAsignatura(scanner.nextLine());
                            if(ar!=null) System.out.println("RECUPERADO: " + ar.getNombre());
                            else System.out.println("No encontrada.");
                            break;

//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|                               
                        case "5": 
                            System.out.println("\n--- NUEVA MATRÍCULA ---");
                            System.out.print("ID Matrícula: "); String idM = scanner.nextLine();
                            Matricula m = new Matricula();
                            m.setId(idM); 
                            cliente.insertarMatricula(m);
                            
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|                   
                        case "6": 
                            System.out.print("ID Matrícula a buscar: ");
                            Matricula mr = cliente.obtenerMatricula(scanner.nextLine());
                            if(mr!=null) System.out.println("RECUPERADO ID: " + mr.getId());
                            else System.out.println("No encontrada.");
                            break;

//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|                        
                        case "7":
                            System.out.print("ID Título a borrar: ");
                            cliente.borrarTitulo(scanner.nextLine());
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|                        
                        case "8":
                            System.out.print("ID Asignatura a borrar: ");
                            cliente.borrarAsignatura(scanner.nextLine());
                            break;              
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|                        
                        case "0":
                            cliente.cerrarSesion();
                            salir = true;
                            System.out.println("Adiós.");
                            break;
                            
                        default: System.out.println("Opción no válida.");
                    }
                }
            } else {
                System.out.println("Error de autenticación. (Recuerda: admin / admin)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}