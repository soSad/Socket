package com.manyClient.sock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CreatServerTread extends Thread{
	private Socket client;
	private BufferedReader bufferedReader;
	private PrintWriter printWriter;
	
	public CreatServerTread(Socket sock) throws IOException{
		client = sock;
		bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		printWriter = new PrintWriter(client.getOutputStream());
		System.out.println("client"+getName()+"come");
		start();
	}
	
	public void run(){
		try {
			String line = bufferedReader.readLine();
			while(!line.equals("bye")){
				printWriter.println("continue,client"+getName());
				line= bufferedReader.readLine();
				System.out.println(getName());
			}
			printWriter.print("bye"+getName());
			printWriter.close();
			bufferedReader.close();
			client.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
