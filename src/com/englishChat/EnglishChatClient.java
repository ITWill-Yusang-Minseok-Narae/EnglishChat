package com.englishChat;
//20161108
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EnglishChatClient{

	private static final long serialVersionUID = -8029413848722483074L;
	private Socket sk = null;
	private final int port = 5555;
	private String serverIP = "192.168.16.22";
	private String name = "";

	public EnglishChatClient(){
		System.out.println("채팅클라이언트");
	}
	
	public static void main(String[] args) {
		new EnglishChatClient().connect();
	}

	
	public void connect(){
		
		Scanner sc = new Scanner(System.in);
		System.out.print("이름?");
		name = sc.next();
		
		try {
			sk = new Socket(serverIP, port);
		} catch (Exception e) {
			System.out.println("서버가 죽어있다...");
			return;
		}
		Thread readerThread = new Thread(new Reader(sk));
		Thread writerThread = new Thread(new Writer(sk,name));
		readerThread.start();//Reader호출
		writerThread.start();//Writer호출
	}
}

class Reader implements Runnable{
	private Socket sk;

	public Reader(Socket sk) {
		this.sk = sk;
	}
	
	@Override
	public void run() {
		//서버가 보낸 데이터를 받는곳
		String str;
		try {
			if(sk==null)//server접속안함
				return;
			InputStream is = sk.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			//서버가 보낸 데이터를 출력
			while ((str=br.readLine())!=null)
					System.out.println(str);
		} catch (Exception e) {
			System.out.println("\r\n 서버 연결 종료...");
			sk = null;//재연결시 쓰레기값 생기므로 미리제거
		}
	}
}

class Writer implements Runnable{
	private Socket sc;
	private String name;
	
	public Writer(Socket sc, String name) {
		this.sc = sc;
		this.name = name;
	}

	@Override
	public void run() {
		//서버에게 데이터를 보내는곳
		Scanner scn = new Scanner(System.in);
		String str = null;
		OutputStream os = null;
		
		if(sc==null)
			return;
		try {
			while((os=sc.getOutputStream())!=null){
				str = scn.nextLine();
//				if(str.trim().equals(""))
//					return;
				PrintWriter pw = new PrintWriter(os,true);//true는 flush
				pw.println(name + "]" + str);
				System.out.println(name + "]" + str);
			}
		} catch (Exception e) {
			System.out.println("\r\n 서버 연결 종료...");
			sc = null;
		}
	}
}