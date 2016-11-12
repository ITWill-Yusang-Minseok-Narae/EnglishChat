package com.englishChat.Taja;
//20111108
//Kim Yusang
//bakkus@daum.net
import java.net.ServerSocket;
import java.net.Socket;

import com.englishChat.Dictionary.EnglishDictionaryData;
import com.englishChat.Dictionary.EnglishDictionaryIO;

public class EnglishTajaServer {
	private Socket sc;
	
	public void serverStart(EnglishTajaData etd, EnglishDictionaryData edd){
		new EnglishDictionaryIO().dictionaryFileInputToTreeMap(edd);
		try {
			ServerSocket ss = new ServerSocket(EnglishTajaData.port);
			System.out.println("서버시작...");
			Thread manager = new Thread(new EnglishTajaManager(etd,edd));
			manager.start();
			while (true) {
				if (etd.getClients().size()<=EnglishTajaData.tajaUserLimit){
					setSc(ss.accept());
					Thread userAccept = new Thread(new EnglishTajaUserAccept(etd,sc));
					userAccept.start();	
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public Socket getSc() {
		return sc;
	}

	public void setSc(Socket sc) {
		this.sc = sc;
	}
	
	
	
	public static void main(String[] args) {
		EnglishTajaServer ob = new EnglishTajaServer();
		EnglishTajaData etd = new EnglishTajaData();
		EnglishDictionaryData edd = new EnglishDictionaryData();
		ob.serverStart(etd,edd);
	}
	
}
