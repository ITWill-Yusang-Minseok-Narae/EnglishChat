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
			
			//접속한 client(sk)를 저장
			clients.add(sk);
			
			//다른 client에게 접속사실을 알림
			msg = ip + "]가 접속했습니다.";
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
			String msg = ip + "]가 퇴장했습니다.";
			chatBroadcast(msg);
				sk = null;
		}
		
		private void chatBroadcast(String msg){
			//나를 제외한 모두
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
