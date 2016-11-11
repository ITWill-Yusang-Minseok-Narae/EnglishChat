package com.englishChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EnglishTajaUserAccept implements Runnable{
	private Socket sc;
	EnglishChatData ecd;
	
	public EnglishTajaUserAccept(EnglishChatData ecd,Socket sc) {
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
		
		//접속한 client(sc)를 저장
		EnglishChatUser ecu = new EnglishChatUser();
		ecu.setIp(ip);
		ecu.setSc(sc);
		ecd.getClients().add(ecu);
		msg = String.format("ecd.getClients().size() : %d", ecd.getClients().size());
		chatBroadcast(msg);				
		
		//다른 client에게 접속사실을 알림
		msg = ip + "]가 접속했습니다.";
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
		String msg = ip + "]가 퇴장했습니다.";
		chatBroadcast(msg);
			sc = null;
	}
	
	private void chatBroadcast(String msg){
		//나를 제외한 모두
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
