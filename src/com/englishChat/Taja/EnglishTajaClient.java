package com.englishChat.Taja;
//20161108
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.englishChat.AppLogin.EnglishAppLoginMember;

public class EnglishTajaClient{

	private static final long serialVersionUID = -8029413848722483074L;
	private Socket sk = null;
	private final int tajaServerPort = 5555;
	private String tajaServerIP = "192.168.16.22";
	private String name = "";
	

	public EnglishTajaClient(){
		System.out.println("타자클라이언트");
		Scanner sc = new Scanner(System.in);
		System.out.print("이름?");
		name = sc.next();
//		sc.close();
		connect();
	}

	public EnglishTajaClient(EnglishAppLoginMember ealm){
		this.name=ealm.getName();
		System.out.println("타자클라이언트");
		connect();
	}
	
	public void connect(){
		OutputStream os = null;
		ObjectOutputStream oos = null;
		
		
		try {
			sk = new Socket(tajaServerIP, tajaServerPort);
		} catch (Exception e) {
			System.out.println("서버가 죽어있다...");
			return;
		}
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
		try {
			oos.writeObject(name);
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
//		try {
//			oos.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Thread readerThread = new Thread(new Reader(sk));
		Thread writerThread = new Thread(new Writer(sk,name));
		readerThread.start();//Reader호출
		writerThread.start();//Writer호출
		try {
			readerThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writerThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			System.out.println("\r\n 서버 연결 종료(ClientReader)...");
			sk = null;//재연결시 쓰레기값 생기므로 미리제거
		}
	}
}

class Writer implements Runnable{
	private Socket sk;
	private String name;
	
	public Writer(Socket sk, String name) {
		this.sk = sk;
		this.name = name;
	}

	@Override
	public void run() {
		//서버에게 데이터를 보내는곳
		Scanner sc = new Scanner(System.in);
		String str = null;
		OutputStream os = null;
		
		if(sk==null)
			return;
		try {
			while((os=sk.getOutputStream())!=null){
				str = sc.nextLine();
				if (str.equals("exit")){
					sc.close();
					sk.close();
					return;
				}
//				if(str.trim().equals(""))
//					return;
				PrintWriter pw = new PrintWriter(os,true);//true는 flush
				pw.println(name + "]" + str);
				System.out.println(name + "]" + str);
			}
		} catch (Exception e) {
			System.out.println("\r\n 서버 연결 종료(ClientWriter)...");
			try {
				sk.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}