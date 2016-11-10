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

public class EnglishChatServer {
	private List<Socket> clients = new ArrayList<Socket>();
	private final int port = 5555;
	
	public void serverStart(){
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
		private Socket sc;
		
		public WorkThread(Socket sc) {
			this.sc = sc;
		}
		
		@Override
		public void run() {
			String ip = sc.getInetAddress().getHostAddress();
			String msg = null;
			BufferedReader br;
			
//			try {
				try {
					br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
				} catch (IOException e) {
					memberExitProcess(ip);
					return;
				}
				
				//������ client(sc)�� ����
				clients.add(sc);
				
				//�ٸ� client���� ���ӻ���� �˸�
				msg = ip + "]�� �����߽��ϴ�.";
				
				for (Socket s : clients) {
					if(s==sc)
						continue;//pass
					PrintWriter pw;
					try {
						pw = new PrintWriter(s.getOutputStream(),true);
					} catch (IOException e) {
						memberExitProcess(ip);
						return;
					}
					pw.println(msg);
				}
				System.out.println(msg);

				try {
					while ((msg=br.readLine())!=null) {
						//���� ������ ���
						for (Socket s : clients) {
							if(s==sc)
								continue;//pass
							PrintWriter pw;
							try {
								pw = new PrintWriter(s.getOutputStream(),true);
							} catch (IOException e) {
								memberExitProcess(ip);
								return;
							}
							pw.println(msg);
						}
						System.out.println(msg);
					}
				} catch (IOException e) {
					memberExitProcess(ip);
					return;
				}
//			} catch (Exception e) {
//				memberExitProcess(ip);
//			}
		}
		
		private void memberExitProcess(String ip){
			String msg = ip + "]�� �����߽��ϴ�.";
			try {
				//���� ������ ���
				for (Socket s : clients) {
					if(s==sc)
						continue;//pass
					PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
					pw.println(msg);
				}
				System.out.println(msg);
				sc = null;
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		private PrintWriter chatBroadcast(Socket s){
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(s.getOutputStream(),true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return pw;
		}
	}
	
	public static void main(String[] args) {
		EnglishChatServer ob = new EnglishChatServer();
		ob.serverStart();
	}
}
