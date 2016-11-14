package com.englishChat.Blink;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;


import com.englishChat.Dictionary.EnglishDictionaryData;
import com.englishChat.Dictionary.EnglishDictionaryWord;

public class EnglishBlinkControl {
	TreeMap<String, String> tm = new TreeMap<String, String>();
	Iterator<String> it = tm.keySet().iterator();
	EnglishBlinkData ebd;
	EnglishDictionaryData edd;

	public EnglishBlinkControl() {// 메뉴

		this.ebd = new EnglishBlinkData();
		this.edd = new EnglishDictionaryData();

		int ch, str;
		Scanner sc = new Scanner(System.in);
		System.out.println("--------<영어단어장에 오신걸 환영합니다.>--------- ㅇ^^ㅇ");
		System.out.println("");

		System.out.println("\t실행하실 프로그램을 선택해주세요.ㅇ^^ㅇ ");
		while (true) {
			do {
				System.out.println("---------------------------------------");
				System.out
						.println("\t1.단어암기\n"
								+ "\t2.외워야 할 단어 직접 입력한후 내 단어장에 저장\n"
								+ "\t3.외운단어 단어장에서 삭제\n\t4.내 영어단어장보기\n\t5.영어단어Test\n\t6.프로그램 종료\n---------------------------------------\n");
				System.out.println();
				ch = sc.nextInt();
			} while (ch < 1);
			switch (ch) {

			case 1:
				wordblink();
				break;
			case 2:
				save();
				break;
			case 3:
				delete();
				break;
			case 4:
				look();
				break;
			case 5:
				test();
				break;
			default:
				System.out.println("종료합니다.. 수고하셨습니다^^");
				return;
			}
		}
	}

	public void input() {

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

	// public void next() { // 단어저장
	//
	// String txt = "";
	// String fileName = "d:\\work\\test1.txt";
	//
	// try {
	//
	// // BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
	// BufferedWriter fw = new BufferedWriter(new FileWriter(fileName,
	// true));
	//
	// // 파일안에 문자열 쓰기
	// fw.write(txt);
	// fw.flush();
	// // 객체 닫기
	// fw.close();
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }

	public void test() { // 단어시험기능
		int score = 0;
		int test = 0;
		Scanner s = new Scanner(System.in);
		// edd.getMinidictionary();
		Random rd = new Random();

		System.out.println("몇개의 단어를 Test보시겠습니까?");
		test = s.nextInt();
		ebd.setWordbook2(tm);
		Object[] keys = edd.getDictionary().keySet().toArray();
		for (int i = 0; i < test; i++) {
			int testRandom = rd.nextInt(2);
			Object randomKey = keys[rd.nextInt(keys.length)];
			if (testRandom == 1) {
				Object randomValue = edd.getDictionary().get(randomKey);
				System.out.print("영어단어 뜻 : " + randomValue);
				System.out.print("영어단어 : [영문만 입력하세요.]\n");
				String mean = s.next();
				String answerWord = (String) randomKey;
				if (answerWord.equals(mean)) {
					score++;
					System.out.println("정답입니다. 현재: " + score);
				} else
					System.out.println("틀렸습니다.");
			} else {
				System.out.print("영어단어 : " + randomKey);
				System.out.print("뜻 : [한글만 입력하세요.]\n");
				String mean = s.next();
				TreeMap<String, LinkedList<EnglishDictionaryWord>> answerMean = edd
						.getDictionary();
				if (answerMean.equals(mean)) {
					score++;
					System.out.println("정답입니다. 현재점수: " + score);
				} else
					System.out.println("틀렸습니다.");
			}
		}
		System.out.println("점수 결과: " + score);
	}

	public void save() {// 단어수동입력저장기능
		ebd.setWordbook2(tm);
		Iterator<String> it = ebd.getWordbook().keySet().iterator();
		while (true) {
			Scanner s = new Scanner(System.in);

			System.out.print("영어단어 : [영어만 입력하세요.]");
			String word = s.next();
			System.out.print("뜻 : [한글만 입력하세요.]");
			String mean = s.next();

			ebd.getWordbook().put(word, mean);
			System.out.println("단어장에 추가 되었습니다.");
			System.out.print("단어장에 계속 추가하시겠습니까?[y/n]");

			String ans = s.next();
			if (ans.equals("n")) {
				System.out.println("추가를 종료합니다.");
				break;
			}

		}

		try {

			FileOutputStream fos = new FileOutputStream("d:\\test111.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(ebd.getWordbook());// 출력
			System.out.println("단어저장완료!!");
			oos.close();
			fos.close();
		} catch (Exception e) {
		}
	}

	public void delete() {// 단어삭제기능
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		char ch = 0;
		Scanner sc = new Scanner(System.in);
		int num = 0;
		System.out.print("삭제할 영어단어: [영어만 입력하세요]");
		String word = sc.next();
		while (it.hasNext()) {

			String key = it.next();// 키를 찾는거임 value는 안나옴
			System.out.println();
			if (tm.get(key).equals(word)) {
				num++;
			}
			if (num > 0) {
				
			try {
				System.out.println("진짜+" + tm.get(key) + "단어를 지우시겠습니까?[y/n]");
				ch = (char)System.in.read();
			} catch (Exception e) {
				// TODO: handle exception
			}
			if(ch == 'Y' || ch == 'y') {
				try {  

					 fis = new FileInputStream(	"d:\\test111.txt");
					 ois = new ObjectInputStream(fis);
					// text파일 열기

					
					// 해당배열삭제

					System.out.println(key + "가 삭제되었습니다");
					System.out.println();
					tm.remove(key);
					save();// text파일로 저장
					break;

				} catch (Exception e) {
					// TODO: handle exception
				}

			}
				
				System.out.println("추가를 종료합니다.");
				break;

			}
		}
	}

	public void look() {// 단어장보기기능
		FileReader fr = null;
		ObjectInputStream ois = null;
		FileInputStream fis = null;

		try {
			fis = new FileInputStream("d:\\test111.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {

			ois = new ObjectInputStream(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			tm = (TreeMap<String, String>) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 출력

		for (int i = 0; i < tm.size(); i++) {
			Iterator<String> it = tm.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();// 키를 찾는거임 value는 안나옴
				String value = tm.get(key);// 키로 value를 찾은거임
				System.out.println(key + " : " + value);
			}
		}
	}

	public void wordblink() { // 단어암기

		Scanner sc = new Scanner(System.in);
		int ch, std;

		input();
		System.out.println("    \t< 시선강탈 영어암기장 >   ");
		System.out.println(" \t오늘 몇개의 영단어를 공부할까요??? ");
		std = sc.nextInt();
		TreeMap<String, String> tm = new TreeMap<String, String>();
		while (true) {
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

				for (int j = 0; j < 50; j++) {

					System.out.println();
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			ebd.setWordbook1(tm);
			System.out.print("다시 보시겠습니까??[y/n]");
			String ans = sc.next();
			if (ans.equals("n")) {
				System.out.println("단어암기를 종료합니다.");
				break;
			}
		}
	}
}