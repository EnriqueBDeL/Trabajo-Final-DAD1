package edu.ucam.cliente;

import java.util.*;

import edu.ucam.domain.Asignatura;
import edu.ucam.domain.Matricula;
import edu.ucam.domain.Titulacion;
import edu.ucam.domain.Alumno;

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

            if (!cliente.autenticar(usuario, password)) {
                System.out.println("Error de autenticación. (admin / admin)");
                return;
            }

            boolean salir = false;

            while (!salir) {

                System.out.println("\n--- MENÚ GESTIÓN UNIVERSIDAD ---");
                System.out.println("1. Añadir Título");
                System.out.println("2. Consultar Título");
                System.out.println("3. Añadir Asignatura");
                System.out.println("4. Consultar Asignatura");
                System.out.println("5. Añadir Matrícula");
                System.out.println("6. Consultar Matrícula");
                System.out.println("7. Borrar Título");
                System.out.println("8. Borrar Asignatura");
                System.out.println("9. Borrar Matrícula");
                System.out.println("10. Listar Títulos");
                System.out.println("11. Listar Asignaturas");
                System.out.println("12. Vincular Asignatura a un Título");
                System.out.println("13. Ver Asignaturas de un Título");
                System.out.println("14. Desvincular Asignatura de un Título");
                System.out.println("15. Total de Títulos");
                System.out.println("16. Ver clientes conectados");
                System.out.println("17. Modificar Título");
                System.out.println("18. Modificar Matrícula");
                System.out.println("0. Salir");
                System.out.print("Seleccione opción: ");

                String opcion = scanner.nextLine();

                switch (opcion) {

                case "0": { // Salir
                    cliente.cerrarSesion();
                    salir = true;
                    break;
                }

                case "1": { // Añadir Título
                    System.out.print("ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();

                    Titulacion t = new Titulacion();
                    t.setId(id);
                    t.setNombre(nombre);

                    cliente.insertarTitulo(t);
                    System.out.println("Título añadido.");
                    break;
                }

                case "2": { // Consultar Título
                    System.out.print("ID: ");
                    Titulacion t = cliente.obtenerTitulo(scanner.nextLine());
                    if (t != null) {
                        System.out.println("ID: " + t.getId());
                        System.out.println("Nombre: " + t.getNombre());
                    } else {
                        System.out.println("No encontrado.");
                    }
                    break;
                }

                case "3": { // Añadir Asignatura
                    System.out.print("ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Créditos: ");
                    int creditos = Integer.parseInt(scanner.nextLine());

                    Asignatura a = new Asignatura();
                    a.setId(id);
                    a.setNombre(nombre);
                    a.setCreditos(creditos);

                    cliente.insertarAsignatura(a);
                    System.out.println("Asignatura añadida.");
                    break;
                }

                case "4": { // Consultar Asignatura
                    System.out.print("ID: ");
                    Asignatura a = cliente.obtenerAsignatura(scanner.nextLine());
                    if (a != null) {
                        System.out.println("ID: " + a.getId());
                        System.out.println("Nombre: " + a.getNombre());
                        System.out.println("Créditos: " + a.getCreditos());
                    } else {
                        System.out.println("No encontrada.");
                    }
                    break;
                }

                case "5": { // Añadir Matrícula
                    System.out.print("ID Matrícula: ");
                    String idM = scanner.nextLine();

                    System.out.print("DNI Alumno: ");
                    String dni = scanner.nextLine();
                    System.out.print("Nombre Alumno: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Apellidos Alumno: ");
                    String apellidos = scanner.nextLine();

                    Alumno al = new Alumno();
                    al.setDni(dni);
                    al.setNombre(nombre);
                    al.setApellidos(apellidos);

                    Matricula m = new Matricula();
                    m.setId(idM);
                    m.setAlumno(al);
                    m.setAsignaturas(new Hashtable<>());

                    cliente.insertarMatricula(m);
                    System.out.println("Matrícula añadida.");
                    break;
                }

                case "6": { // Consultar Matrícula
                    System.out.print("ID Matrícula: ");
                    Matricula m = cliente.obtenerMatricula(scanner.nextLine());
                    if (m != null) {
                        System.out.println("ID: " + m.getId());
                        System.out.println("Alumno: " + m.getAlumno().getNombre());
                        for (Asignatura a : m.getAsignaturas()) {
                            System.out.println("- " + a.getNombre());
                        }
                    } else {
                        System.out.println("No encontrada.");
                    }
                    break;
                }

                case "7": { // Borrar Título
                    System.out.print("ID: ");
                    cliente.borrarTitulo(scanner.nextLine());
                    System.out.println("Título eliminado.");
                    break;
                }

                case "8": { // Borrar Asignatura
                    System.out.print("ID: ");
                    cliente.borrarAsignatura(scanner.nextLine());
                    System.out.println("Asignatura eliminada.");
                    break;
                }

                case "9": { // Borrar Matrícula
                    System.out.print("ID Matrícula: ");
                    cliente.borrarMatricula(scanner.nextLine());
                    System.out.println("Matrícula eliminada.");
                    break;
                }

                case "10": { // Listar Títulos
                    List<Titulacion> lista = cliente.listarTitulos();
                    if (lista.isEmpty()) {
                        System.out.println("No hay títulos.");
                    } else {
                        for (Titulacion t : lista) {
                            System.out.println("- " + t.getId() + " : " + t.getNombre());
                        }
                    }
                    break;
                }

                case "11": { // Listar Asignaturas
                    List<Asignatura> lista = cliente.listarAsignaturas();
                    if (lista.isEmpty()) {
                        System.out.println("No hay asignaturas.");
                    } else {
                        for (Asignatura a : lista) {
                            System.out.println("- " + a.getId() + " : " + a.getNombre());
                        }
                    }
                    break;
                }

                case "12": { // Vincular Asignatura a Título
                    System.out.print("ID Asignatura: ");
                    String idAsig = scanner.nextLine();
                    System.out.print("ID Título: ");
                    String idTit = scanner.nextLine();

                    cliente.vincularAsignatura(idAsig, idTit);
                    System.out.println("Asignatura vinculada.");
                    break;
                }

                case "13": { // Ver Asignaturas de un Título
                    System.out.print("ID Título: ");
                    List<Asignatura> lista = cliente.listarAsignaturasDeTitulo(scanner.nextLine());
                    if (lista.isEmpty()) {
                        System.out.println("No hay asignaturas asociadas.");
                    } else {
                        for (Asignatura a : lista) {
                            System.out.println("- " + a.getNombre());
                        }
                    }
                    break;
                }

                case "14": { // Desvincular Asignatura de un Título
                    System.out.print("ID Asignatura: ");
                    String idAsig = scanner.nextLine();
                    System.out.print("ID Título: ");
                    String idTit = scanner.nextLine();

                    cliente.desvincularAsignatura(idAsig, idTit);
                    System.out.println("Asignatura desvinculada.");
                    break;
                }

                case "15": { // Total de Títulos
                    int total = cliente.obtenerTotalTitulos();
                    System.out.println("Total de títulos: " + total);
                    break;
                }

                case "16": { // Ver clientes conectados
                    int sesiones = cliente.obtenerSesionesActivas();
                    System.out.println("Clientes conectados: " + sesiones);
                    break;
                }

                case "17": { // Modificar Título
                    System.out.print("ID: ");
                    String id = scanner.nextLine();
                    Titulacion t = cliente.obtenerTitulo(id);
                    if (t != null) {
                        System.out.print("Nuevo nombre: ");
                        t.setNombre(scanner.nextLine());
                        cliente.actualizarTitulo(id, t.getNombre());
                        System.out.println("Título actualizado.");
                    } else {
                        System.out.println("No existe el título.");
                    }
                    break;
                }

                case "18": { // Modificar Matrícula
                    System.out.print("ID Matrícula: ");
                    String id = scanner.nextLine();
                    Matricula m = cliente.obtenerMatricula(id);
                    if (m != null) {
                        System.out.print("Nuevo nombre del alumno: ");
                        m.getAlumno().setNombre(scanner.nextLine());
                        cliente.actualizarMatricula(id, m);
                        System.out.println("Matrícula actualizada.");
                    } else {
                        System.out.println("No existe la matrícula.");
                    }
                    break;
                }

                default:
                    System.out.println("Opción no válida.");
            }
          }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
