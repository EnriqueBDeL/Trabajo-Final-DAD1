package edu.ucam.cliente.service;

public class ResponseParser {
		private String type;
		private String numero;
		private String code;
		private String message;
		private String ip;
		private String port;
		
		
		
		
		public ResponseParser(String respuesta) {
			String[] chuncks = respuesta.split(" ");
			if(chuncks.length == 4) {
				type = chuncks[0];
				numero = chuncks[1];
				code = chuncks[2];
				message = chuncks[3];
				if("PREOK".equals(type) && chuncks.length >= 5) {
					ip = chuncks[3];
					port = chuncks[4];
				}
			}
		}
		
		
		public boolean isSuccess() {
			return "OK".equals(type);
		}
		
		public boolean isPREOK() {
			return "PREOK".equals(type);
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getPort() {
			return Integer.parseInt(port);
		}
		
		public String getIp() {
			return this.ip;
		}
}
