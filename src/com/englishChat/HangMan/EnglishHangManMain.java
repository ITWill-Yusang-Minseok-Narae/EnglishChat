package com.englishChat.HangMan;

public class EnglishHangManMain {

	public static void main(String[] args) {
		
		
		EnglishHangManControl ed = new EnglishHangManControl(null, null);
		ed.input();
		//ed.print();
		ed.random();
		
		
		
		ed.play();
	}

}
