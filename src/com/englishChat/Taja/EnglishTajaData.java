package com.englishChat.Taja;
//20111108
//Kim Yusang
//bakkus@daum.net
import java.util.ArrayList;

import com.englishChat.EnglishChatUser;

public class EnglishTajaData {
	
	public static final int port = 5555;
	public static final int tajaUserLimit = 2;
	public static final int tajaQuestion = 3;	
	private ArrayList<EnglishChatUser> clients = new ArrayList<EnglishChatUser>();

	public ArrayList<EnglishChatUser> getClients() {
		return clients;
	}
	public void setClients(ArrayList<EnglishChatUser> clients) {
		this.clients = clients;
	}
}
