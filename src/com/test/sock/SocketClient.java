package com.test.sock;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
	public static void main(String[] args){
		try {
			Socket socket = new Socket("127.0.0.1", 2016);
			socket.setSoTimeout(60000);
			//发送客户端准备传输的信息
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader sysBuff = new BufferedReader(new InputStreamReader(System.in));
			printWriter.println(sysBuff.readLine());
			printWriter.flush();
			
			//获取服务端传输来的信息
			BufferedReader buffererReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String result = buffererReader.readLine();
			System.out.println("Server say"+result);
			
			printWriter.close();
			buffererReader.close();
			socket.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
