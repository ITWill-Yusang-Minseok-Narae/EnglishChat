package com.englishChat.Blink;

import java.util.TreeMap;

public class EnglishBlinkData {

	private TreeMap<String, String> wordbook;
	private final String ew[] = { "apple", "banana", "car", "dog", "egg",
			"fire", "glass", "house", "ilke", "joke" };
	private final String ed[] = { "사과", "바나나", "차", "개", "달걀", "불", "유리", "집",
			"좋아하다", "농담" };

	public TreeMap<String, String> getWordbook() {
		return wordbook;
	}

	public void setWordbook1(TreeMap<String, String> wordbook1) {
		
		this.wordbook = wordbook1;
		
	}
	public void setWordbook2(TreeMap<String, String> wordbook2) {
		
		this.wordbook = wordbook2;
		
	}
	public String[] getEw() {
		return ew;
	}

	public String[] getEd() {
		return ed;
	}

	
}
