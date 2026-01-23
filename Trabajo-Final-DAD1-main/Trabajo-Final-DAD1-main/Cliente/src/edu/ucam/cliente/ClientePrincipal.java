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

                    case "1": {
                        System.out.print("ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();

                        Titulacion t = new Titulacion();
                        t.setId(id);
                        t.setNombre(nombre);

                        cliente.insertarTitulo(t);
                        break;
                    }

                    case "2": {
                        System.out.print("ID: ");
                        Titulacion t = cliente.obtenerTitulo(scanner.nextLine());
                        if (t != null) {
                            System.out.println("ID: " + t.getId());
                            System.out.println("Nombre: " + t.getNombre());
                        }
                        break;
                    }

                    case "3": {
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
                        break;
                    }

                    case "4": {
                        System.out.print("ID: ");
                        Asignatura a = cliente.obtenerAsignatura(scanner.nextLine());
                        if (a != null) {
                            System.out.println("ID: " + a.getId());
                            System.out.println("Nombre: " + a.getNombre());
                            System.out.println("Créditos: " + a.getCreditos());
                        }
                        break;
                    }

                    case "5": {
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
                        m.setAsignaturas(new Hashtable<String, Asignatura>());

                        cliente.insertarMatricula(m);
                        break;
                    }

                    case "6": {
                        System.out.print("ID Matrícula: ");
                        Matricula m = cliente.obtenerMatricula(scanner.nextLine());
                        if (m != null) {
                            System.out.println("ID: " + m.getId());
                            System.out.println("Alumno: " + m.getAlumno().getNombre());
                            for (Asignatura a : m.getAsignaturas()) {
                                System.out.println("- " + a.getNombre());
                            }
                        }
                        break;
                    }

                    case "17": {
                        System.out.print("ID Título: ");
                        String id = scanner.nextLine();
                        Titulacion t = cliente.obtenerTitulo(id);
                        if (t != null) {
                            System.out.print("Nuevo nombre: ");
                            t.setNombre(scanner.nextLine());
                            cliente.actualizarTitulo(id, t.getNombre());
                        }
                        break;
                    }

                    case "0":
                        cliente.cerrarSesion();
                        salir = true;
                        break;

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
