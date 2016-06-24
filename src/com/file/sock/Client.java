package com.file.sock;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Socket{
	
	private static final String SERVER_IP = "111.9.6.204";
	private static final int SERVER_PORT = 2001;
	
	private Socket sock;
	private File file;
	private FileInputStream finput;
	private DataOutputStream data;
	
	public Client() throws IOException{
		try {
			sock = new Socket(SERVER_IP, SERVER_PORT);
			file = new File("C:/test.doc");
			finput = new FileInputStream(file);
			data = new DataOutputStream(sock.getOutputStream());
			
			data.writeUTF(file.getName());
			data.flush();
			data.writeLong(file.length());
			data.flush();
			
			byte[] sendBytes = new byte[1024];
			int  length = 0;
			while((length = finput.read(sendBytes, 0, sendBytes.length))>0){
				data.write(sendBytes);
				data.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(finput!=null)
				finput.close();
			if(data!=null)
				data.close();
			sock.close();
		}
	}
	public static void main(String[] args) throws IOException{
		new Client();
	}
}
