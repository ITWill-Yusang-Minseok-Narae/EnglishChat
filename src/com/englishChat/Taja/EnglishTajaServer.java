package com.englishChat.Taja;
//20111108
//Kim Yusang
//bakkus@daum.net
import java.net.ServerSocket;
import java.net.Socket;

import com.englishChat.Dictionary.EnglishDictionaryData;
import com.englishChat.Dictionary.EnglishDictionaryIO;

public class EnglishTajaServer {
	private EnglishTajaData etd = null;
	private EnglishDictionaryData edd = null;
	private Socket sc = null;
	private Thread tajaManager = null;
	
	public EnglishTajaServer() {
		this.etd = new EnglishTajaData();
		this.edd = new EnglishDictionaryData();
		serverStart();
	}

	public void serverStart(){
		new EnglishDictionaryIO().dictionaryFileInputToTreeMap(edd);
		try {
			ServerSocket ss = new ServerSocket(EnglishTajaData.tajaServerPort);
			System.out.println("서버시작...");
			tajaManagerStart();
			while (true) {
				if (etd.getClients().size()<=EnglishTajaData.tajaUserLimit){
					sc=ss.accept();
					Thread userAccept = new Thread(new EnglishTajaUserAccept(this,etd,sc));
					userAccept.start();	
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public boolean tajaManagerStart(){
		if(tajaManager==null){
			tajaManager = new Thread(new EnglishTajaManager(etd,edd));
			tajaManager.start();
			return true;
		}else if (tajaManager.isAlive())
			return false;
			else{
				tajaManager = new Thread(new EnglishTajaManager(etd,edd));
				tajaManager.start();
				return true;
			}
	}
}
