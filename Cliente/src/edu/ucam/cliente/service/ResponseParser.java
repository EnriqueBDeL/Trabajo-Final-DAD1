package edu.ucam.cliente.service;

public class ResponseParser {
    private String type;
    private String numero;
    private String code;
    private String message;
    private String ip;
    private int port; // Cambiado a int para facilitar uso

    public ResponseParser(String respuesta) {
        if (respuesta == null || respuesta.isEmpty()) return;
        
        String[] chunks = respuesta.split(" ");
        // Estructura mínima: <TIPO> <NUM> <COD> <MSG...>
        if (chunks.length >= 3) { 
            type = chunks[0];
            numero = chunks[1];
            code = chunks[2];
            
            // Si es PREOK: PREOK <num> <cod> <ip> <puerto>
            if ("PREOK".equals(type) && chunks.length >= 5) {
                ip = chunks[3];
                port = Integer.parseInt(chunks[4]);
                message = "Conexión de datos establecida";
            } else {
                // Reconstruir mensaje si tiene espacios (ej: "Welcome admin")
                StringBuilder sb = new StringBuilder();
                for (int i = 3; i < chunks.length; i++) {
                    sb.append(chunks[i]).append(" ");
                }
                message = sb.toString().trim();
            }
        }
    }

    public boolean isSuccess() { return "OK".equals(type); }
    public boolean isPREOK() { return "PREOK".equals(type); }
    public String getMessage() { return message; }
    public int getPort() { return port; }
    public String getIp() { return ip; }
}