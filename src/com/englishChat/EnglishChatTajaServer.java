package com.englishChat;
//20161108
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class EnglishChatTajaServer {
	private List<Socket> clients = new ArrayList<Socket>();
	private final int port = 5555;
	
	public void serverStart(EnglishChatData ecd){
		new EnglishChatDictionaryIO().dictionaryAllInput(ecd);
		try {
			ServerSocket ss = new ServerSocket(port);
			System.out.println("��������...");
			while (true) {
				Socket sc = ss.accept();
				WorkThread wt = new WorkThread(sc);
				wt.start();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	
	class WorkThread extends Thread{
		private Socket sk;
		
		public WorkThread(Socket sk) {
			this.sk = sk;
		}
		
		@Override
		public void run() {
			String ip = sk.getInetAddress().getHostAddress();
			String msg = null;
			BufferedReader br;
			
			try {
				br = new BufferedReader(new InputStreamReader(sk.getInputStream()));
			} catch (IOException e) {
				memberExitProcess(ip);
				return;
			}
			
			//������ client(sk)�� ����
			clients.add(sk);
			
			//�ٸ� client���� ���ӻ���� �˸�
			msg = ip + "]�� �����߽��ϴ�.";
			chatBroadcast(msg);
			try {
				while ((msg=br.readLine())!=null) 
					chatBroadcast(msg);
			} catch (IOException e) {
				memberExitProcess(ip);
				return;
			}
		}
		
		private void memberExitProcess(String ip){
			String msg = ip + "]�� �����߽��ϴ�.";
			chatBroadcast(msg);
				sk = null;
		}
		
		private void chatBroadcast(String msg){
			//���� ������ ���
			for (Socket s : clients) {
				if(s==sk)
					continue;//pass
				PrintWriter pw = null;
				try {
					pw = new PrintWriter(s.getOutputStream(),true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pw.println(msg);
			}
			System.out.println(msg);
		}
	}
	
//	public static void main(String[] args) {
//		EnglishChatTajaServer ob = new EnglishChatTajaServer();
//		ob.serverStart();
//	}
}
