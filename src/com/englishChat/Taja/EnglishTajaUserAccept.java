package com.englishChat.Taja;
//20111108
//Kim Yusang
//bakkus@daum.net
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;

public class EnglishTajaUserAccept implements Runnable{
	private Socket sk;
	EnglishTajaServer ets;
	EnglishTajaData etd;
	
	public EnglishTajaUserAccept(EnglishTajaServer ets, EnglishTajaData etd,Socket sk) {
		this.ets = ets;
		this.etd = etd;
		this.sk = sk;
	}
	
	@Override
	public void run() {
		String clientIp = sk.getInetAddress().getHostAddress();
		String msg = null;
		BufferedReader br = null;
		InputStream is = null;
		InputStreamReader isr = null;
		ObjectInputStream ois = null;
		EnglishTajaUser etu = null;
		String userName = null;
		
		try {
			is = sk.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ois = new ObjectInputStream(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			userName = (String) ois.readObject();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		try {
//			ois.close();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		etu = new EnglishTajaUser();
		etu.setName(userName);
		etu.setIp(clientIp);
		etu.setSc(sk);
		isr = new InputStreamReader(is);
		br = new BufferedReader(isr);
		//접속한 client(sk)를 저장
		etd.getClients().add(etu);
		msg = String.format("ecd.getClients().size() : %d", etd.getClients().size());
		chatBroadcast(msg);				
		
		//다른 client에게 접속사실을 알림
		msg = etu.getName() + "]가 접속했습니다. ip=" + etu.getIp();
		chatBroadcast(msg);
		try {
			while ((msg=br.readLine())!=null) {
				if (etu.isGameMode()) 
					etu.setAnswer(msg.substring(msg.indexOf(']')+1));
//				ets.tajaManagerStart();
//				if (msg.equals("tajamanager"))
//					if(ets.tajaManagerStart())
//						chatBroadcast("타자매니저시작");
//					else
//						chatBroadcast("타자매니저실행중입니다");
//				if (msg.equals("exit")){
//					chatBroadcast(msg+"*");
//					memberExitProcess(etu);
//				}
				chatBroadcast(msg);
			}
		} catch (IOException e) {
			memberExitProcess(etu);
			return;
		}
	}
	
	private void memberExitProcess(EnglishTajaUser etu){
		String msg = etu.getName() + "]가 퇴장했습니다. ip=" + etu.getIp();
		chatBroadcast(msg);
		Iterator<EnglishTajaUser> it = etd.getClients().listIterator();
		while (it.hasNext()) {
			EnglishTajaUser etu1 = (EnglishTajaUser) it.next();
			if(etu1.getName().equals(etu.getName()))
				etd.getClients().remove(etu1);
		}
		try {
			sk.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void chatBroadcast(String msg){
		//나를 제외한 모두
		for (EnglishTajaUser ecu : etd.getClients()) {
			if(ecu.getSc()==sk)
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
