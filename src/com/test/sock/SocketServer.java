package com.test.sock;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
	public static void main(String[] args){
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(2013);
			while(true){
				Socket socket = serverSocket.accept();
				
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String result = bufferedReader.readLine();
				System.out.println("client say"+result);
				
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				printWriter.println("hello client ,i am server");
				printWriter.flush();
				
				printWriter.close();
				bufferedReader.close();
				socket.close();
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//serverSocket
		}
	}
}
