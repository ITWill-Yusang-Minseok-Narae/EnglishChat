package com.englishChat.Blink;

import java.util.TreeMap;

public class EnglishBlinkData {

	private TreeMap<String, String> wordbook;
	private final String ew[] = { "apple", "banana", "car", "dog", "egg",
			"fire", "glass", "house", "ilke", "joke" };
	private final String ed[] = { "���", "�ٳ���", "��", "��", "�ް�", "��", "����", "��",
			"�����ϴ�", "���" };

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
