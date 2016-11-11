package com.englishChat;
//20161108
import java.net.ServerSocket;
import java.net.Socket;

public class EnglishTajaServer {
	

	
	private Socket sc;
//	EnglishChatData ecd = new EnglishChatData();
	
	public void serverStart(EnglishChatData ecd){
		new EnglishChatDictionaryIO().dictionaryFileInputToTreeMap(ecd);
		try {
			ServerSocket ss = new ServerSocket(EnglishChatData.port);
			System.out.println("서버시작...");
			Thread manager = new Thread(new EnglishTajaManager(ecd));
			manager.start();
			while (true) {
				if (ecd.getClients().size()<=EnglishChatData.tajaUserLimit){
					setSc(ss.accept());
					Thread userAccept = new Thread(new EnglishTajaUserAccept(ecd,sc));
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
		EnglishChatData ecd = new EnglishChatData();
		ob.serverStart(ecd);
	}
	
}
