package edu.ucam.cliente;

import java.util.Scanner;
import edu.ucam.domain.Asignatura;
import edu.ucam.domain.Matricula;
import edu.ucam.domain.Titulacion;

public class ClientePrincipal {
	
	private static boolean idValido(String id) {
        return id != null && !id.trim().isEmpty();
    }
    
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
                    
                    // --- GRUPO CREACIÓN ---
                    System.out.println("1. Añadir Título");
                    System.out.println("2. Añadir Asignatura");
                    System.out.println("3. Añadir Matrícula");
                    
                    // --- GRUPO CONSULTA INDIVIDUAL ---
                    System.out.println("4. Consultar Título");
                    System.out.println("5. Consultar Asignatura");
                    System.out.println("6. Consultar Matrícula");
                    
                    // --- GRUPO LISTADOS ---
                    System.out.println("7. Listar Títulos");
                    System.out.println("8. Listar Asignaturas");
                    
                    // --- GRUPO MODIFICACIÓN ---
                    System.out.println("9. Modificar Título");
                    System.out.println("10. Modificar Matrícula");
                    
                    // --- GRUPO BORRADO ---
                    System.out.println("11. Borrar Título");
                    System.out.println("12. Borrar Asignatura");
                    System.out.println("13. Borrar Matrícula");
                    
                    // --- GRUPO RELACIONES ---
                    System.out.println("14. Vincular Asignatura a un Título");
                    System.out.println("15. Desvincular Asignatura de un Título");
                    System.out.println("16. Ver Asignaturas de un Título");
                    
                    // --- GRUPO INFO/ESTADÍSTICAS ---
                    System.out.println("17. Total de Títulos");
                    System.out.println("18. Ver número de clientes conectados");
                    
                    System.out.println("0. Salir");
                    System.out.print("Seleccione opción: ");
                    String opcion = scanner.nextLine();
                    
                    switch (opcion) {
                  
                        case "1": 
                            System.out.println("\n--- NUEVO TÍTULO ---");
                            System.out.print("ID: "); String idT = scanner.nextLine();
                            System.out.print("Nombre: "); String nomT = scanner.nextLine();
                            if (!idValido(idT)) {
                                System.out.println("ID inválido.");
                                break;
                            }
                            Titulacion t = new Titulacion();
                            t.setId(idT); t.setNombre(nomT);
                            cliente.insertarTitulo(t);
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                        case "2": 
                            System.out.println("\n--- NUEVA ASIGNATURA ---");
                            System.out.print("ID: "); String idA = scanner.nextLine();
                            System.out.print("Nombre: "); String nomA = scanner.nextLine();
                            if (!idValido(idA)) {
                                System.out.println("ID inválido.");
                                break;
                            }
                            Asignatura a = new Asignatura();
                            a.setId(idA); a.setNombre(nomA);
                            cliente.insertarAsignatura(a);
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                        case "3": 
                            System.out.println("\n--- NUEVA MATRÍCULA ---");
                            System.out.print("ID Matrícula: "); String idM = scanner.nextLine();
                            if (!idValido(idM)) {
                                System.out.println("ID inválido.");
                                break;
                            }
                            Matricula m = new Matricula();
                            m.setId(idM); 
                            cliente.insertarMatricula(m);
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                        case "4": 
                            System.out.print("ID a buscar: ");
                            Titulacion tr = cliente.obtenerTitulo(scanner.nextLine());
                            if(tr!=null) System.out.println("RECUPERADO: " + tr.getNombre());
                            else System.out.println("No encontrado.");
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                        case "5": 
                            System.out.print("ID a buscar: ");
                            Asignatura ar = cliente.obtenerAsignatura(scanner.nextLine());
                            if(ar!=null) System.out.println("RECUPERADO: " + ar.getNombre());
                            else System.out.println("No encontrada.");
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
                            System.out.println("\n--- LISTADO DE TÍTULOS ---");
                            try {
                                var lista = cliente.listarTitulos();
                                if (lista != null && !lista.isEmpty()) {
                                    for (Titulacion tList : lista) {
                                        System.out.println("- [" + tList.getId() + "] " + tList.getNombre());
                                    }
                                } else {
                                    System.out.println("No hay títulos registrados.");
                                }
                            } catch (Exception e) { 
                                System.out.println("Error al listar: " + e.getMessage()); 
                            }
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                        case "8":
                            System.out.println("\n--- LISTADO DE ASIGNATURAS ---");
                            try {
                                var lista = cliente.listarAsignaturas();
                                if (lista != null && !lista.isEmpty()) {
                                    for (Asignatura aList : lista) {
                                        System.out.println("- [" + aList.getId() + "] " + aList.getNombre());
                                    }
                                } else {
                                    System.out.println("No hay asignaturas registradas.");
                                }
                            } catch (Exception e) { 
                                System.out.println("Error al listar: " + e.getMessage()); 
                            }
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                        case "9":
                            System.out.println("\n--- MODIFICAR TÍTULO ---");
                            System.out.print("ID del Título a modificar: "); 
                            String idTM = scanner.nextLine();
                            
                            Titulacion tExistente = cliente.obtenerTitulo(idTM);
                            if (tExistente != null) {
                                System.out.println("Nombre actual: " + tExistente.getNombre());
                                System.out.print("Nuevo Nombre: "); 
                                String nuevoNom = scanner.nextLine();
                                cliente.actualizarTitulo(idTM, nuevoNom);
                            } else {
                                System.out.println("El título no existe, no se puede modificar.");
                            }
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                        case "10":
                            System.out.println("\n--- MODIFICAR MATRÍCULA ---");
                            System.out.print("ID de Matrícula a modificar: "); 
                            String idMM = scanner.nextLine();
                            Matricula mNueva = new Matricula();
                            mNueva.setId(idMM); 
                            System.out.println("Enviando actualización...");
                            cliente.actualizarMatricula(idMM, mNueva);
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                        case "11":
                            System.out.print("ID Título a borrar: ");
                            cliente.borrarTitulo(scanner.nextLine());
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                        case "12":
                            System.out.print("ID Asignatura a borrar: ");
                            cliente.borrarAsignatura(scanner.nextLine());
                            break;              
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                        case "13":
                            System.out.print("ID Matrícula a borrar: ");
                            cliente.borrarMatricula(scanner.nextLine());
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                        case "14":
                            System.out.println("\n--- VINCULAR ASIGNATURA A TÍTULO ---");
                            System.out.print("ID Asignatura: "); String ida = scanner.nextLine();
                            System.out.print("ID Título: "); String idt = scanner.nextLine();
                            cliente.vincularAsignatura(ida, idt);
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                        case "15":
                            System.out.println("\n--- DESVINCULAR ASIGNATURA DE TÍTULO ---");
                            System.out.print("ID Asignatura a quitar: "); String idaq = scanner.nextLine();
                            System.out.print("ID Título origen: "); String idtq = scanner.nextLine();
                            cliente.desvincularAsignatura(idaq, idtq);
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                        case "16":
                            System.out.println("\n--- VER ASIGNATURAS DE UN TÍTULO ---");
                            System.out.print("ID Título: "); 
                            var listaRel = cliente.listarAsignaturasDeTitulo(scanner.nextLine());
                            
                            if (listaRel != null && !listaRel.isEmpty()) {
                                for (Asignatura aRel : listaRel) {
                                    System.out.println("- " + aRel.getNombre());
                                }
                            } else {
                                System.out.println("Este título no tiene asignaturas o no existe.");
                            }
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                        case "17": 
                            System.out.println("\n--- TOTAL DE TÍTULOS ---");
                            try {
                               int total = cliente.obtenerTotalTitulos();
                               System.out.println("-> Hay " + total + " títulos registrados en el sistema.");
                            } catch (Exception e) {
                                System.out.println("Error al contar títulos: " + e.getMessage());
                            }
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                        case "18":
                            System.out.println("\n--- USUARIOS CONECTADOS ---");
                            try {
                               int conectados = cliente.obtenerSesionesActivas();
                               System.out.println("-> Usuarios activos ahora mismo: " + conectados);
                            } catch (Exception e) {
                                System.out.println("Error al obtener sesiones: " + e.getMessage());
                            }
                            break;
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%|
                        case "0":
                            cliente.cerrarSesion();
                            salir = true;
                            System.out.println("Adiós.");
                            break;
                            
                        default: System.out.println("Opción no válida.");
                    }
                    
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                    	
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
