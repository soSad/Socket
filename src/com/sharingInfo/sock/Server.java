package com.sharingInfo.sock;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class Server extends ServerSocket{
	
	private static final int SERVER_PORT = 2001;
	
	private static boolean isPrint = false;
	private static ArrayList user_list = new ArrayList();
	private static ArrayList<ServerThread> thread_list = new ArrayList<ServerThread>();
	private static LinkedList<String> message_list = new LinkedList<String>();
	/**
	 * ���������socket��������ͻ��˷�����Ϣ�̣߳������ͻ���������
	 * @throws IOException
	 */
	public Server() throws IOException {
		super(SERVER_PORT);		//����serversocket
		new PrintOutThread();	//������ͻ��˷�����Ϣ�߳�
		
		try {
			while(true){		//�����ͻ�������
				Socket socket = accept();
				new ServerThread(socket);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			close();
		}
	}
	
	class ServerThread extends Thread{
		private Socket client;
		private PrintWriter out;
		private BufferedReader in;
		private String name = null;

		public ServerThread(Socket socket) throws IOException {
			client = socket;
			out =  new PrintWriter(client.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			in.readLine();
			out.println("�ɹ�����,�������������");
			start();
		}
		public void run(){
			try {
				int flag = 0;
				String line = in.readLine();
				while(!"bye".equals(line)){
					//�鿴�����û��б�
					if("showuser".equals(line)){
						out.println(this.listOnLineUsers());
						in.readLine();
					}
					//��һ�ν��룬��������
					if(flag++ == 0){
						name = line;
						user_list.add(name);
						thread_list.add(this);
						out.println(name+"��ã����Կ�ʼ������");
						this.pushMseesage("client"+name+"say:"+line);
					}else{
						this.pushMseesage("client"+name+"say:"+line);
					}
					line = in.readLine();
				}
				out.println("byeClient");	
			} catch (Exception e) {
				e.printStackTrace();
			}finally{//�û��˳�������
				try {
					client.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
				thread_list.remove(this);
				user_list.remove(name);
				pushMseesage(name+"�˳���������");
			}
		}
		private void pushMseesage(String msg) {
			message_list.addLast(msg);
			isPrint = true;
		}
		private void sendMessage(String msg){
			out.println(msg);
		}
	
		private String listOnLineUsers() {
			// TODO Auto-generated method stub
			String s = "�����û��б�";
			for(int i = 0;i < user_list.size();i++){
				s += "[" + user_list.get(i)+"]***";
			}
			return s;
		}
		
	}
	class PrintOutThread extends Thread{
		public PrintOutThread(){
			start();
		}
		public void run(){
			while(true){
				if(isPrint){//�������ڶ����е���Ϣ��˳���͵����ͻ��ˣ����Ӷ��������
					String message = message_list.getFirst();
					for(ServerThread s: thread_list){
						s.sendMessage(message);
					}
					message_list.removeFirst();
					isPrint = message_list.isEmpty()?true:false;
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		new Server();
	}
}
