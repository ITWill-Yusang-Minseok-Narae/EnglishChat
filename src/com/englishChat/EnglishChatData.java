package com.englishChat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

public class EnglishChatData {
	
	public static final int port = 5555;
	public static final int tajaUserLimit = 2;
	public static final int tajaQuestion = 3;	
	private TreeMap<String, LinkedList<EnglishChatWord>> dictionary;
	private TreeMap<String, String> minidictionary;
	private ArrayList<EnglishChatUser> clients = new ArrayList<EnglishChatUser>();

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
	public ArrayList<EnglishChatUser> getClients() {
		return clients;
	}
	public void setClients(ArrayList<EnglishChatUser> clients) {
		this.clients = clients;
	}
}
