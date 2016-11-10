package com.englishChat;
//20161108

public class EnglishChatMain {
	
	public static void main(String[] args) {
		EnglishChatData ecd = new EnglishChatData();
		EnglishChatTajaServer ects = new EnglishChatTajaServer();
		
		//ects.serverStart(ecd);
		new EnglishChatDictionaryIO().dictionaryAllInput(ecd);

	}
}
