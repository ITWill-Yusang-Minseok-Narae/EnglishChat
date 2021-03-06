package com.englishChat.Taja;

import java.net.Socket;

public class EnglishTajaUser {
	private Socket sc;
	private String ip;
	private String name;
	private String answer;
	private boolean gameMode;
	private int gamePoint;
	
	public EnglishTajaUser() {
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
