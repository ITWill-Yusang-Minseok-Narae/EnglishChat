package com.englishChat;

import java.net.Socket;

public class EnglishChatUser {
	private Socket sc;
	private String ip;
	private String answer;
	private boolean gameMode;
	private int gamePoint;
	
	public EnglishChatUser() {
		this.sc = null;
		this.ip = "";
		this.answer = "";
		this.gameMode = false;
		this.gamePoint = 0;
	}
	public Socket getSc() {
		return sc;
	}
	public void setSc(Socket sc) {
		this.sc = sc;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public boolean isGameMode() {
		return gameMode;
	}
	public void setGameMode(boolean gameMode) {
		this.gameMode = gameMode;
	}
	public int getGamePoint() {
		return gamePoint;
	}
	public void setGamePoint(int gamePoint) {
		this.gamePoint = gamePoint;
	}
}
