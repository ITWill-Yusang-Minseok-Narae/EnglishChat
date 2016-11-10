package com.englishChat;
//20161108
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientTest{

	private static final long serialVersionUID = -8029413848722483074L;
	private Socket sc = null;
	private final int port = 5555;
	private String host = "192.168.16.22";

	public ClientTest(){
		System.out.println("채팅클라이언트");
	}
	
	public static void main(String[] args) {
		new ClientTest().connect();
	}

	
	public void connect(){
		try {
			sc = new Socket(host, port);
		} catch (Exception e) {
			System.out.println("서버가 죽어있다...");
			return;
		}
		Thread readerThread = new Thread(new Reader(sc));
		Thread writerThread = new Thread(new Writer(sc));
		readerThread.start();//Reader호출
		writerThread.start();//Writer호출
	}
}

class Reader implements Runnable{
	private Socket sc;

	public Reader(Socket sc) {
		super();
		this.sc = sc;
	}
	
	@Override
	public void run() {
		//서버가 보낸 데이터를 받는곳
		String str;
		try {
			if(sc==null)//server접속안함
				return;
			InputStream is = sc.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			//서버가 보낸 데이터를 출력
			while ((str=br.readLine())!=null)
					System.out.println(str);
		} catch (Exception e) {
			System.out.println("\r\n 서버 연결 종료...");
			sc = null;//재연결시 쓰레기값 생기므로 미리제거
		}
	}
}

class Writer implements Runnable{
	private Socket sc;
	
	public Writer(Socket sc) {
		this.sc = sc;
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
				str = scn.next();
//				if(str.trim().equals(""))
//					return;
				PrintWriter pw = new PrintWriter(os,true);//true는 flush
				pw.println("김유상]" + str);
				System.out.println("김유상]" + str);
			}
		} catch (Exception e) {
			System.out.println("\r\n 서버 연결 종료...");
			sc = null;
		}
	}
}