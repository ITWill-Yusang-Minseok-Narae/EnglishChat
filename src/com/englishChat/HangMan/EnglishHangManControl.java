package com.englishChat.HangMan;

import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

import com.englishChat.AppLogin.EnglishAppLoginControl;
import com.englishChat.Dictionary.EnglishDictionaryData;

 public class EnglishHangManControl {

	private static final String[] Data = { "abdomen", "bequest", "eversion",
			"iceberg", "invoke", "kindle", "recurvate", "slush", "stock",
			"unsystematic" };
	// private static final String[] Data2 = { "n.", "n.", "n.", "n.", "vt.",
	// "vt.", "adj.", "n.", "n.", "adj." };
	private static final String[] Data3 = { "복부", "유증", "밖으로 뒤집음", "빙산",
			"기원하다", "태우다", "휘어진", "진창길", "줄기", "비체계적인" };
	int returnint=0;
	String dash, value, mean, alphabet;
	EnglishDictionaryData edd = new EnglishDictionaryData();
	Random rd = new Random();
	Scanner sc = new Scanner(System.in);
	private TreeMap<String, String> tm;

	public EnglishHangManControl() {
		input();
		random();
		play();
	}

	public void input() {// 단어장 데이터 넣기

		TreeMap<String, String> tm = new TreeMap<String, String>();

		for (int i = 0; i < Data.length; i++) {

			tm.put(Data[i], Data3[i]);

		}
		edd.setMinidictionary(tm);
		// return tm;
	}

	void print() {// 출력

		tm = edd.getMinidictionary();
		Iterator<String> it = tm.keySet().iterator();

		while (it.hasNext()) {

			String key = it.next();// 키를 찾는거임 value는 안나옴
			value = tm.get(key);// 키로 value를 찾은거임

			System.out.println(key + " : " + value);
		}

	}

	// value값에 대한 키값을 가져옴
	public static Object getKeyFromValue(TreeMap tm, Object value) {

		for (Object o : tm.keySet()) {
			if (tm.get(o).equals(value)) {
				return o;
			}
		}
		return null;

	}

	void random() {// 퀴즈 낼 단어를 랜덤으로 고름
		tm = edd.getMinidictionary();
		Iterator<String> it = tm.keySet().iterator();

		Object[] values = tm.values().toArray();
		Object randomValue = values[rd.nextInt(values.length)];
		// value를 랜덤으로 돌림

		// System.out.println("답은");
		// System.out.println("---------------");
		// System.out.println(randomValue);

		// System.out.println(tm.containsValue(randomValue));

		// System.out.println(getKeyFromValue(tm, randomValue));
		// 랜덤 value값에 대한 키 값을 프린드함

		mean = (String) randomValue;
		dash = (String) getKeyFromValue(tm, randomValue);
		// System.out.println(dash);
		System.out.println("---------------");
		// System.out.println("입니다!^^");

	}

	// 행맨을 플레이 함
	void play() {

		
		
		Alphabet al = new Alphabet();

		String[] dash2 = dash.split("");
		String[] dash3 = new String[dash2.length];
		int count = 0, heart = 0, hangCount = 0;
		System.out.print("\n행맨 게임을 시작합니다");
		System.out.print("\n\n");
		System.out.println("-------------------------------------------");
		System.out.println("-------------------------------------------");
		System.out.println("                START!!!");
		System.out.println("-------------------------------------------");
		System.out.println("-------------------------------------------");
		System.out.printf("\n\n\n문제:");
		for (int i = 0; i < dash2.length; i++) {

			dash3[i] = "ㅡ ";
			System.out.print(dash3[i]);
		}

		while (true) {

			try {
				do {
					alphabet = "";
					heart = 0;
					System.out.println();
					System.out.println("유추할 알파벳을 입력하세요");

					alphabet = sc.nextLine();
					al.inputFormat(alphabet);

					for (int i = 0; i < dash2.length; i++) {
						if (dash2[i].equals(alphabet)) {
							// System.out.println(dash.substring(i, i + 1));

							System.out.println("♪♪맞췄습니다♪♪");
							System.out.println("입력하신 " + alphabet + " 은 존재합니다");

							dash3[i] = alphabet;// 해당하는 _를 입력한 알파벳으로 바꿈
							heart++;
							count++;
						}
						if (heart > 1) {// 만약에 맞춘것이 한번에 2자리면 -1해야함
							heart--;
							count--;
						}

					}
					if (0 == heart) {
						hangCount++;
						System.out.println("!!!틀렸습니다!!!");
						System.out.println("틀린횟수 : " + hangCount);
						// 행맨 그려지는 메소드 추가하기
						EnglishHangManPrint.main(hangCount);

					}
					for (int i = 0; i < dash2.length; i++) {

						System.out.print(dash3[i]);
					}
					System.out.println();

					// 행맨이 죽거나, 알파벳을 다맞추면 끝내기
					if (hangCount > 6 || count == dash2.length) {

						if (hangCount > 6) {
							System.out.println();
							System.out.println("행맨이 죽어버렸습니다 ㅠㅠㅠㅠ");
							System.out.println("이번 게임의 승자는 존재하지 않습니다");
							System.out.printf("정답은 %s : %s 입니다", dash, mean);
							System.out.println();
							replay();
							if(returnint==10){
								return;
							}

						}
						if (count == dash2.length) {
							System.out.println();
							System.out.println("게임이 종료되었습니다");
							System.out.println("축하합니다 정답을 맞추셨습니다^^");
							System.out.printf("정답은 %s : %s 입니다", dash, mean);
							System.out.println();
							replay();
							if(returnint==10){
								return;
							}

						}

					}

				} while (hangCount > 6 || heart == dash2.length);
				

			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				System.out.println("잘못된 동작을 하셨습니다1");
//				e.printStackTrace();
			}
		}
	}

	public int replay() {
		returnint=0;

		try {
			char ch;
			System.out.println("게임을 계속 하시겠습니까? (y/n)");
			ch = (char) System.in.read();

			if (ch == 'Y' || ch == 'y') {
				//return;
				
				EnglishHangManMain.main(null);

			}

			if (ch == 'N' || ch == 'n') {
				System.out.println("행맨 게임을 종료합니다");
				returnint=10;
				
				
			} else
				System.out.println("y키나 n키를 눌러주십시오");

		} catch (Exception e) {
			System.out.println("잘못된 동작을 하셨습니다2");
			
			
		}
		return returnint;
		
		
	

	}

	// ------------------------ 예외처리------------------------------

	class Alphabet {
		public void inputFormat(String alphabet) throws Exception {

			if (alphabet.length() > 1) {
				throw new Exception("1개의 알파벳만 쓰십시오");
			}

			int eng = 0;

			for (int i = 0; i < alphabet.length(); i++) {
				char ch = alphabet.charAt(i);
				if (ch >= 'a' && ch <= 'z') {

					eng++;

				}
			}
			if (eng == 0)
				throw new Exception("영문자 소문자로 입력해주세요");

		}

	}

}
