package com.englishChat.Blink;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.englishChat.Dictionary.EnglishDictionaryData;

public class EnglishBlinkControl {
	TreeMap<String, String> tm;
	EnglishBlinkData ebd;
	EnglishBlinkPrint ebp;
	EnglishDictionaryData edd;

	public EnglishBlinkControl() {

	
		this.ebd = new EnglishBlinkData();
		this.ebp = new EnglishBlinkPrint();
		this.edd = new EnglishDictionaryData();

		controllor();

	}

	public void input() {

		TreeMap<String, String> tm = new TreeMap<String, String>();
		for (int i = 0; i < ebd.getEw().length; i++) {
			tm.put(ebd.getEw()[i], ebd.getEd()[i]);

		}
		edd.setMinidictionary(tm);

	}

	// class Replay implements Runnable {
	//
	// private Map<String, String> tm = new TreeMap<String, String>();
	//
	// public Replay(EnglishChatData ecd) {
	//
	// TreeMap<String, String> tm = new TreeMap<String, String>();
	// for (int i = 0; i < ecd.getEw().length; i++) {
	// tm.put(ecd.getEw()[i], ecd.getEd()[i]);
	//
	// }
	// ecd.setMinidictionary(tm);
	// }
	//
	// @Override
	// public void run() {
	//
	// try {
	// for (int i = 0; i < 70; i++) {
	// System.out.println("");
	// Thread.sleep(200);
	// }
	// } catch (Exception e) {
	//
	// }
	//
	// }
	//
	// }

	// public void input() {
	//
	// EnglishChatData ecd = new EnglishChatData();
	//
	// Map<String, String> tm = new TreeMap<String, String>();
	// Iterator<String> it = tm.keySet().iterator();
	// tm = ecd.getMinidictionary();
	//
	// while (it.hasNext()) {
	//
	// String key = it.next();// 키를 찾는거임 value는 안나옴
	// String value = tm.get(key);// 키로 value를 찾은거임
	// System.out.println(key + " : " + value);
	// }
	//
	// }

	public void next() { // 단어저장

		String txt = "";
		String fileName = "d:\\work\\test1.txt";

		try {

			// BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
			BufferedWriter fw = new BufferedWriter(new FileWriter(fileName,
					true));

			// 파일안에 문자열 쓰기
			fw.write(txt);
			fw.flush();
			// 객체 닫기
			fw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void test() { //단어시험기능

		Scanner s = new Scanner(System.in);
		System.out.println("영어단어를 입력하세요 : [단어/뜻으로 구분해주세요ㅇ^_^ㅇ]");
		String word = s.next();

	}

	public void save() {//단어수동입력저장기능
		Iterator<String> it = ebd.getWordbook().keySet().iterator();
		Scanner s = new Scanner(System.in);
		System.out.println("영어단어 : [영어만 입력하세요.]");
		String word = s.next();
		System.out.println("뜻 : [한글만 입력하세요.]");
		String mean = s.next();
		String str = (word + ":" + mean);

		if (!word.equals(":")) {
			FileWriter fw;

			try {
				fw = new FileWriter("d:\\work\\test111.txt");

				fw.write(str);
				
				System.out.println("단어가 저장되었습니다.");
				fw.close();
			} catch (IOException e) {
				System.out.println("다시 입력해주세요."); // 에러가 있다면 메시지 출력
				System.exit(0);
			}
		}
	}

	public void delete() {//단어삭제기능

		Iterator<String> it = ebd.getWordbook().keySet().iterator();
		Scanner sc = new Scanner(System.in);
		System.out.println("삭제할 영어단어: [영어만 입력하세요]");
		String word = sc.next();
		while (it.hasNext()) {

			String key = it.next();// 키를 찾는거임 value는 안나옴

			if (ebd.getWordbook().get(key).equals(word)) {
				ebd.getWordbook().remove(key);
				System.out.println("삭제되었습니다.");
			} else {
				System.out.println("잘못입력하셨습니다.");
				break;
			}

		}

		FileWriter fw;

		try {
			fw = new FileWriter("d:\\work\\test111.txt");
			fw.write(word);
			fw.close();

		} catch (IOException e) {
			System.out.println("다시 입력해주세요."); // 에러가 있다면 메시지 출력
			System.exit(0);
		}

	}

	public void look() {//단어장보기기능

		try {
			FileInputStream fis = new FileInputStream("d:\\work\\test111.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String str;
			while ((str = br.readLine()) != null) {
				System.out.println(str);
			}
			fis.close();

		} catch (Exception e) {

			System.out.println(e.toString());
			;

		}

	}

	public void except() { //단어장 출력부분

	}

	public void controllor() {

		ArrayList<String> lists = new ArrayList<String>();
		Scanner sc = new Scanner(System.in);
		int ch, std;
		input();
		ebp.print(edd);
		System.out.println("    \t\t< 시선강탈 영어암기장 >   ");
		System.out.println(" \t오늘 몇개의 영단어를 공부할까요??? ");
		std = sc.nextInt();
		TreeMap<String, String> tm = new TreeMap<String, String>();
		for (int i = 0; i < std; i++) {

			tm.put(ebd.getEw()[std], ebd.getEd()[std]);
			System.out.print("\t\t");
			System.out.println(ebd.getEw()[i]);
			System.out.print("\t\t");

			System.out.println(ebd.getEd()[i]);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int j = 0; j < 70; j++) {

				System.out.println();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		ebd.setWordbook(tm);

		while (true) {
			do {
				System.out
						.println("1.단어다시보기\n2.모르는단어 내 영어단어장에 저장\n3.외워야할 단어 직접 입력해서 단어장에 저장\n"
								+ "4.외운단어 내 단어장에서 삭제\n"
								+ "5.내 영어 단어장보기\n6.영어단어Test\n7.종료\n------------------------\n");
				ch = sc.nextInt();
			} while (ch < 1);
			switch (ch) {

			case 1:
				ebp.blink(edd);
				break;
			case 2:
				next();
				break;
			case 3:
				save();
				break;
			case 4:
				delete();
				break;
			case 5:
				look();
				break;
			case 6:
				test();
				break;
			default:
				System.out.println("종료합니다..");
				System.exit(0);
			}
		}
	}
}
