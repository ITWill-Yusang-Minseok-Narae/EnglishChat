package com.englishChat.Dictionary;
//20111108
//Kim Yusang
//bakkus@daum.net
public interface EnglishDictionaryInterface {
	public void dictionaryFileInputToTreeMap(EnglishDictionaryData edd);
	public void dictionaryWriteToFile(EnglishDictionaryData edd);
	public void DictionaryFileInputToMiniTreeMap(EnglishDictionaryData edd);
	public void miniDictionaryWriteToFile(EnglishDictionaryData edd);
}
