package com.englishChat.AppLogin;
//20161108
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class EnglishAppLoginServer {
	private List<Socket> clients = new ArrayList<Socket>();
	private final int port = 5551;
	private EnglishAppLoginMemberList ealml = null;
	private EnglishAppLoginMember member = null;
	private Calendar cal = Calendar.getInstance();
	
	public EnglishAppLoginServer() {
		serverStart();
	}

	public void serverStart(){
		ealml = new EnglishAppLoginMemberList();
		ArrayList<EnglishAppLoginMember> memberList = new ArrayList<EnglishAppLoginMember>();
		member = new EnglishAppLoginMember();
		member.setId("admin");
		member.setPw1("adminpw");
		member.setPw2("adminpw");
		member.setEmail("admin@itwill.com");
		member.setName("admin");
		member.setAge(30);
		cal.set(1980, 12 - 1, 12);
		member.setBirth(cal);
		member.setTel(0);
		memberList.add(member);
		member = new EnglishAppLoginMember();
		member.setId("test1");
		member.setPw1("test1pw");
		member.setPw2("test1pw");
		member.setEmail("test1@itwill.com");
		member.setName("test1");
		member.setAge(21);
		cal.set(1981, 1 - 1, 1);
		member.setBirth(cal);
		member.setTel(1);
		memberList.add(member);
		member = new EnglishAppLoginMember();
		member.setId("test2");
		member.setPw1("test2pw");
		member.setPw2("test2pw");
		member.setEmail("test2@itwill.com");
		member.setName("test2");
		member.setAge(22);
		cal.set(1982, 2 - 1, 2);
		member.setBirth(cal);
		member.setTel(2);
		memberList.add(member);
		member = new EnglishAppLoginMember();
		member.setId("test3");
		member.setPw1("test3pw");
		member.setPw2("test3pw");
		member.setEmail("test3@itwill.com");
		member.setName("test3");
		member.setAge(23);
		cal.set(1983, 3 - 1, 3);
		member.setBirth(cal);
		member.setTel(3);
		memberList.add(member);
		ealml.setMemberList(memberList);
		try {
			ServerSocket ss = new ServerSocket(port);
			System.out.println("서버시작...");
			while (true) {
				Socket sc = ss.accept();
				System.out.println("클라이언트접속");
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
			OutputStream os = null;
			ObjectOutputStream oos = null;
			String ip = sk.getInetAddress().getHostAddress();
			String msg = null;
			BufferedReader br;
			
			try {
				os=sk.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				oos = new ObjectOutputStream(os);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				oos.reset();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			List<EnglishAppLoginMember> synchronizedList = Collections.synchronizedList(ealml.getMemberList());
			try {
				oos.writeObject(ealml.getMemberList());
//				oos.writeObject(synchronizedList);
//				oos.writeObject(member);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				oos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//접속한 client(sk)를 저장
			clients.add(sk);
			
			//다른 client에게 접속사실을 알림
//			msg = ip + "]가 접속했습니다.";
//			chatBroadcast(msg);
//			try {
//				while ((msg=br.readLine())!=null) 
//					chatBroadcast(msg);
//			} catch (IOException e) {
//				memberExitProcess(ip);
//				return;
//			}
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
}
