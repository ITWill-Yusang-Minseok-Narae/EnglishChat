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
	private Socket sc = null;
	private final int port = 5555;
	private String host = "192.168.16.22";
	private String name = "";

	public EnglishChatClient(){
		System.out.println("ä��Ŭ���̾�Ʈ");
	}
	
	public static void main(String[] args) {
		new EnglishChatClient().connect();
	}

	
	public void connect(){
		
		try {
			sc = new Socket(host, port);
		} catch (Exception e) {
			System.out.println("������ �׾��ִ�...");
			return;
		}
		Thread readerThread = new Thread(new Reader(sc));
		Thread writerThread = new Thread(new Writer(sc));
		readerThread.start();//Readerȣ��
		writerThread.start();//Writerȣ��
	}
}

class Reader implements Runnable{
	private Socket sc;

	public Reader(Socket sc) {
		this.sc = sc;
	}
	
	@Override
	public void run() {
		//������ ���� �����͸� �޴°�
		String str;
		try {
			if(sc==null)//server���Ӿ���
				return;
			InputStream is = sc.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			//������ ���� �����͸� ���
			while ((str=br.readLine())!=null)
					System.out.println(str);
		} catch (Exception e) {
			System.out.println("\r\n ���� ���� ����...");
			sc = null;//�翬��� �����Ⱚ ����Ƿ� �̸�����
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
		//�������� �����͸� �����°�
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
				PrintWriter pw = new PrintWriter(os,true);//true�� flush
				pw.println("������]" + str);
				System.out.println("������]" + str);
			}
		} catch (Exception e) {
			System.out.println("\r\n ���� ���� ����...");
			sc = null;
		}
	}
}