package edu.ucam.cliente.service;

public class ResponseParser {

    private String type;     // OK | FAILED | PREOK
    private int numero;      // número de secuencia
    private int code;        // código de estado
    private String message;  // mensaje opcional
    private String ip;       // solo PREOK
    private int port;        // solo PREOK

    public ResponseParser(String respuesta) {

        if (respuesta == null || respuesta.isBlank()) {
            this.message = "";
            return;
        }

        String[] chunks = respuesta.split(" ");

        if (chunks.length < 3) {
            this.message = respuesta;
            return;
        }

        try {
            this.type = chunks[0];
            this.numero = Integer.parseInt(chunks[1]);
            this.code = Integer.parseInt(chunks[2]);

            if ("PREOK".equals(type) && chunks.length >= 5) {
                this.ip = chunks[3];
                this.port = Integer.parseInt(chunks[4]);
                this.message = "";
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 3; i < chunks.length; i++) {
                    sb.append(chunks[i]).append(" ");
                }
                this.message = sb.toString().trim();
            }

        } catch (Exception e) {
            this.message = respuesta;
        }
    }

    // =================== GETTERS ===================

    public boolean isSuccess() {
        return "OK".equals(type);
    }

    public boolean isPREOK() {
        return "PREOK".equals(type);
    }

    public boolean isFailed() {
        return "FAILED".equals(type);
    }

    public int getCode() {
        return code;
    }

    public int getNumero() {
        return numero;
    }

    public String getMessage() {
        return message;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
