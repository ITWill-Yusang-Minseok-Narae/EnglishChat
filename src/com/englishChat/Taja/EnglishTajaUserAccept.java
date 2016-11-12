package com.englishChat.Taja;
//20111108
//Kim Yusang
//bakkus@daum.net
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.englishChat.EnglishChatUser;

public class EnglishTajaUserAccept implements Runnable{
	private Socket sc;
	EnglishTajaData ecd;
	
	public EnglishTajaUserAccept(EnglishTajaData ecd,Socket sc) {
		this.ecd = ecd;
		this.sc = sc;
	}
	
	@Override
	public void run() {
		String ip = sc.getInetAddress().getHostAddress();
		String msg = null;
		BufferedReader br;
		
		try {
			br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
		} catch (IOException e) {
			memberExitProcess(ip);
			return;
		}
		
		//������ client(sc)�� ����
		EnglishChatUser ecu = new EnglishChatUser();
		ecu.setIp(ip);
		ecu.setSc(sc);
		ecd.getClients().add(ecu);
		msg = String.format("ecd.getClients().size() : %d", ecd.getClients().size());
		chatBroadcast(msg);				
		
		//�ٸ� client���� ���ӻ���� �˸�
		msg = ip + "]�� �����߽��ϴ�.";
		chatBroadcast(msg);
		try {
			while ((msg=br.readLine())!=null) {
				if (ecu.isGameMode()) 
					ecu.setAnswer(msg.substring(msg.indexOf(']')+1));
				chatBroadcast(msg);
			}
		} catch (IOException e) {
			memberExitProcess(ip);
			return;
		}
	}
	
	private void memberExitProcess(String ip){
		String msg = ip + "]�� �����߽��ϴ�.";
		chatBroadcast(msg);
			sc = null;
	}
	
	private void chatBroadcast(String msg){
		//���� ������ ���
		for (EnglishChatUser ecu : ecd.getClients()) {
			if(ecu.getSc()==sc)
				continue;//pass
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(ecu.getSc().getOutputStream(),true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pw.println(msg);
		}
		System.out.println(msg);
	}
}
