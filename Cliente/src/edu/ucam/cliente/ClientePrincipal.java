package edu.ucam.cliente;

import java.util.Scanner;
import edu.ucam.cliente.interfaces.IAutentication;
import edu.ucam.cliente.service.AutenticationService;
import edu.ucam.cliente.service.ChannelDataService;
import edu.ucam.cliente.service.CommunicationSocket;
import edu.ucam.cliente.service.TitulacionRepository;
import edu.ucam.domain.Titulacion;

public class ClientePrincipal {

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        
        // Inicializamos conexión y servicio de datos
        CommunicationSocket comSocket = new CommunicationSocket();
        ChannelDataService dataService = new ChannelDataService();
        
        try {
            System.out.println("Conectando al servidor...");
            comSocket.connect(); // Conecta al puerto 5000
            
            // Autenticación
            IAutentication auth = new AutenticationService(comSocket);
            System.out.print("Usuario: ");
            String user = scanner.nextLine();
            System.out.print("Password: ");
            String pass = scanner.nextLine();
            
            if (auth.autenticar(user, pass)) {
                System.out.println("\n--- LOGIN CORRECTO ---");
                
                boolean salir = false;
                TitulacionRepository repoTit = new TitulacionRepository(comSocket, dataService);
                
                while(!salir) {
                    System.out.println("\n--- MENÚ ---");
                    System.out.println("1. Añadir Título (ADDTIT)");
                    System.out.println("2. Ver Título (GETTIT)");
                    System.out.println("0. Salir");
                    System.out.print("Elige: ");
                    String op = scanner.nextLine();
                    
                    if (op.equals("1")) {
                        Titulacion t = new Titulacion();
                        // Ajusta estos setters si el JAR tiene nombres distintos
                        // t.setNombre("Ingeniería Informática"); 
                        System.out.println("Enviando Título...");
                        repoTit.add(t);
                    } 
                    else if (op.equals("2")) {
                        System.out.print("ID del título: ");
                        String id = scanner.nextLine();
                        Titulacion t = repoTit.getModel(id);
                        if(t != null) System.out.println("Recibido: " + t.toString());
                    }
                    else if (op.equals("0")) {
                        salir = true;
                    }
                }
                auth.closeSession();
            } else {
                System.out.println("Login incorrecto.");
                comSocket.disconnect();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}