package com.file.sock;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server extends ServerSocket {

	private static final int PORT = 2001;
	private Socket client;
	private FileOutputStream fout;
	private DataInputStream data;
	
	public Server() throws IOException {
		super();
		ServerSocket server = new ServerSocket(PORT);
		
		try {
			while(true){
				client = server.accept();
				data = new DataInputStream(client.getInputStream());
				
				String fileName = data.readUTF();
				long fileLength = data.readLong();
				fout = new FileOutputStream(new File("D:/"+fileName));
				
				byte[] sendBytes = new byte[1024];
				int transLen = 0;
				System.out.println("开始接受文件"+fileName+"文件大小"+fileLength);
				while(true){
					int read = 0;
					read = data.read(sendBytes);
					if(read == -1)
						break;
					transLen+=read;
					System.out.println("接收文件进度"+100*transLen/fileLength+"%");
					fout.write(sendBytes, 0, read);
					fout.flush();
				}
				System.out.println("success");
				fout.flush();
			}
		} finally {
			if(data!=null)
				data.close();
			if(fout!=null)
				fout.close();
			server.close();
		}
		
	}
	
	public static void main(String[] args) throws IOException{
		new Server();
	}
	
}
