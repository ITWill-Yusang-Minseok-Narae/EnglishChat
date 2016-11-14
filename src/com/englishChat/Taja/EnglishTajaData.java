package com.englishChat.Taja;
//20111108
//Kim Yusang
//bakkus@daum.net
import java.util.ArrayList;

public class EnglishTajaData {
	
	public static final int tajaServerPort = 5555;
	public static final int tajaUserLimit = 3;
	public static final int tajaQuestion = 3;	
	private ArrayList<EnglishTajaUser> clients = new ArrayList<EnglishTajaUser>();

	public ArrayList<EnglishTajaUser> getClients() {
		return clients;
	}
	public void setClients(ArrayList<EnglishTajaUser> clients) {
		this.clients = clients;
	}
}
