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

	public void setWordbook(TreeMap<String, String> wordbook) {
		this.wordbook = wordbook;
	}

	public String[] getEw() {
		return ew;
	}

	public String[] getEd() {
		return ed;
	}

	
}
