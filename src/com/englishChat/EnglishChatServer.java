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
			System.out.println("서버시작...");
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
				
				//접속한 client(sc)를 저장
				clients.add(sc);
				
				//다른 client에게 접속사실을 알림
				msg = ip + "]가 접속했습니다.";
				
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
						//나를 제외한 모두
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
			String msg = ip + "]가 퇴장했습니다.";
			try {
				//나를 제외한 모두
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
