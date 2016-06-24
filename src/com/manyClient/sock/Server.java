package com.manyClient.sock;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends ServerSocket {
	private static final int SERVER_PORT = 2016;
	public Server() throws IOException {
		super(SERVER_PORT);
		try {
			while(true){
				Socket sock = accept();
				new CreatServerTread(sock);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			close();
		}
	}
	
	@SuppressWarnings("resource")
	public static void main() throws IOException{
		new Server();
	}

}
