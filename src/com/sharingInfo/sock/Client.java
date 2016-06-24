package com.sharingInfo.sock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Socket{
	private static final String SERVER_IP = "111.9.6.204";
	private static final int SERVER_PORT = 2001;
	
	private Socket socket;
	private PrintWriter printWriter;
	private BufferedReader bufferedReader;
	
	public Client() throws IOException{
		super(SERVER_IP,SERVER_PORT);
		socket = this;
		printWriter = new PrintWriter(this.getOutputStream(),true);
		bufferedReader = new BufferedReader(new InputStreamReader(this.getInputStream()));
		new readLineTread();
		
		while(true){
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			String input = bufferedReader.readLine();
			printWriter.println(input);
		}
	}
	
	 class readLineTread extends Thread{
		private BufferedReader buff;
		public readLineTread(){
			try {
				buff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				start();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		public void run(){
			while(true){
				try {
					String result = buff.readLine();
					if("byeClient".equals(result)){
						break;
					}else{
						System.out.println(result);
					}
					printWriter.close();
					buff.close();
					bufferedReader.close();
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args){
		try {
			new Client();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
