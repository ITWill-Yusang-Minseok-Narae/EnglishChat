package com.englishChat.Dictionary;
//20111108
//Kim Yusang
//bakkus@daum.net
import java.util.LinkedList;
import java.util.TreeMap;

import com.englishChat.EnglishChatWord;

public class EnglishDictionaryData {

	private TreeMap<String, LinkedList<EnglishChatWord>> dictionary;
	private TreeMap<String, String> minidictionary;

	public TreeMap<String, LinkedList<EnglishChatWord>> getDictionary() {
		return dictionary;
	}

	public void setDictionary(
			TreeMap<String, LinkedList<EnglishChatWord>> dictionary) {
		this.dictionary = dictionary;
	}

	public TreeMap<String, String> getMinidictionary() {
		return minidictionary;
	}

	public void setMinidictionary(TreeMap<String, String> minidictionary) {
		this.minidictionary = minidictionary;
	}
}
