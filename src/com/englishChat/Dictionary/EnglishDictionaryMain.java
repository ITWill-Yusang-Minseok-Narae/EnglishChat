package com.englishChat.Dictionary;
//20111108
//Kim Yusang
//bakkus@daum.net
public class EnglishDictionaryMain {
	
	public static void main(String[] args) {
		EnglishDictionaryData edd = new EnglishDictionaryData();
//		EnglishTajaServer ets = new EnglishTajaServer();
		
//		ets.serverStart(ecd);
//		new EnglishDictionaryIO().dictionaryFileInputToTreeMap(edd);
		new EnglishDictionaryIO().DictionaryFileInputToMiniTreeMap(edd);

	}
}
