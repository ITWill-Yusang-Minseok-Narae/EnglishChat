package com.englishChat;
//20161108

public class EnglishChatMain {
	
	public static void main(String[] args) {
		EnglishChatData ecd = new EnglishChatData();
		EnglishTajaServer ects = new EnglishTajaServer();
		
		//ects.serverStart(ecd);
		//new EnglishChatDictionaryIO().dictionaryAllInput(ecd);
		new EnglishChatDictionaryIO().DictionaryFileInputToMiniTreeMap(ecd);

	}
}
