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
		System.out.println("ä��Ŭ���̾�Ʈ");
	}
	
	public static void main(String[] args) {
		new EnglishChatClient().connect();
	}

	
	public void connect(){
		
		Scanner sc = new Scanner(System.in);
		System.out.print("�̸�?");
		name = sc.next();
		
		try {
			sk = new Socket(serverIP, port);
		} catch (Exception e) {
			System.out.println("������ �׾��ִ�...");
			return;
		}
		Thread readerThread = new Thread(new Reader(sk));
		Thread writerThread = new Thread(new Writer(sk,name));
		readerThread.start();//Readerȣ��
		writerThread.start();//Writerȣ��
	}
}

class Reader implements Runnable{
	private Socket sk;

	public Reader(Socket sk) {
		this.sk = sk;
	}
	
	@Override
	public void run() {
		//������ ���� �����͸� �޴°�
		String str;
		try {
			if(sk==null)//server���Ӿ���
				return;
			InputStream is = sk.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			//������ ���� �����͸� ���
			while ((str=br.readLine())!=null)
					System.out.println(str);
		} catch (Exception e) {
			System.out.println("\r\n ���� ���� ����...");
			sk = null;//�翬��� �����Ⱚ ����Ƿ� �̸�����
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
		//�������� �����͸� �����°�
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
				PrintWriter pw = new PrintWriter(os,true);//true�� flush
				pw.println(name + "]" + str);
				System.out.println(name + "]" + str);
			}
		} catch (Exception e) {
			System.out.println("\r\n ���� ���� ����...");
			sc = null;
		}
	}
}