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
	private static final String[] Data3 = { "����", "����", "������ ������", "����",
			"����ϴ�", "�¿��", "�־���", "��â��", "�ٱ�", "��ü������" };
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

	public void input() {// �ܾ��� ������ �ֱ�

		TreeMap<String, String> tm = new TreeMap<String, String>();

		for (int i = 0; i < Data.length; i++) {

			tm.put(Data[i], Data3[i]);

		}
		edd.setMinidictionary(tm);
		// return tm;
	}

	void print() {// ���

		tm = edd.getMinidictionary();
		Iterator<String> it = tm.keySet().iterator();

		while (it.hasNext()) {

			String key = it.next();// Ű�� ã�°��� value�� �ȳ���
			value = tm.get(key);// Ű�� value�� ã������

			System.out.println(key + " : " + value);
		}

	}

	// value���� ���� Ű���� ������
	public static Object getKeyFromValue(TreeMap tm, Object value) {

		for (Object o : tm.keySet()) {
			if (tm.get(o).equals(value)) {
				return o;
			}
		}
		return null;

	}

	void random() {// ���� �� �ܾ �������� ��
		tm = edd.getMinidictionary();
		Iterator<String> it = tm.keySet().iterator();

		Object[] values = tm.values().toArray();
		Object randomValue = values[rd.nextInt(values.length)];
		// value�� �������� ����

		// System.out.println("����");
		// System.out.println("---------------");
		// System.out.println(randomValue);

		// System.out.println(tm.containsValue(randomValue));

		// System.out.println(getKeyFromValue(tm, randomValue));
		// ���� value���� ���� Ű ���� ��������

		mean = (String) randomValue;
		dash = (String) getKeyFromValue(tm, randomValue);
		// System.out.println(dash);
		System.out.println("---------------");
		// System.out.println("�Դϴ�!^^");

	}

	// ����� �÷��� ��
	void play() {

		
		
		Alphabet al = new Alphabet();

		String[] dash2 = dash.split("");
		String[] dash3 = new String[dash2.length];
		int count = 0, heart = 0, hangCount = 0;
		System.out.print("\n��� ������ �����մϴ�");
		System.out.print("\n\n");
		System.out.println("-------------------------------------------");
		System.out.println("-------------------------------------------");
		System.out.println("                START!!!");
		System.out.println("-------------------------------------------");
		System.out.println("-------------------------------------------");
		System.out.printf("\n\n\n����:");
		for (int i = 0; i < dash2.length; i++) {

			dash3[i] = "�� ";
			System.out.print(dash3[i]);
		}

		while (true) {

			try {
				do {
					alphabet = "";
					heart = 0;
					System.out.println();
					System.out.println("������ ���ĺ��� �Է��ϼ���");

					alphabet = sc.nextLine();
					al.inputFormat(alphabet);

					for (int i = 0; i < dash2.length; i++) {
						if (dash2[i].equals(alphabet)) {
							// System.out.println(dash.substring(i, i + 1));

							System.out.println("�ܢܸ�����ϴ٢ܢ�");
							System.out.println("�Է��Ͻ� " + alphabet + " �� �����մϴ�");

							dash3[i] = alphabet;// �ش��ϴ� _�� �Է��� ���ĺ����� �ٲ�
							heart++;
							count++;
						}
						if (heart > 1) {// ���࿡ ������� �ѹ��� 2�ڸ��� -1�ؾ���
							heart--;
							count--;
						}

					}
					if (0 == heart) {
						hangCount++;
						System.out.println("!!!Ʋ�Ƚ��ϴ�!!!");
						System.out.println("Ʋ��Ƚ�� : " + hangCount);
						// ��� �׷����� �޼ҵ� �߰��ϱ�
						EnglishHangManPrint.main(hangCount);

					}
					for (int i = 0; i < dash2.length; i++) {

						System.out.print(dash3[i]);
					}
					System.out.println();

					// ����� �װų�, ���ĺ��� �ٸ��߸� ������
					if (hangCount > 6 || count == dash2.length) {

						if (hangCount > 6) {
							System.out.println();
							System.out.println("����� �׾���Ƚ��ϴ� �ФФФ�");
							System.out.println("�̹� ������ ���ڴ� �������� �ʽ��ϴ�");
							System.out.printf("������ %s : %s �Դϴ�", dash, mean);
							System.out.println();
							replay();
							if(returnint==10){
								return;
							}

						}
						if (count == dash2.length) {
							System.out.println();
							System.out.println("������ ����Ǿ����ϴ�");
							System.out.println("�����մϴ� ������ ���߼̽��ϴ�^^");
							System.out.printf("������ %s : %s �Դϴ�", dash, mean);
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
//				System.out.println("�߸��� ������ �ϼ̽��ϴ�1");
//				e.printStackTrace();
			}
		}
	}

	public int replay() {
		returnint=0;

		try {
			char ch;
			System.out.println("������ ��� �Ͻðڽ��ϱ�? (y/n)");
			ch = (char) System.in.read();

			if (ch == 'Y' || ch == 'y') {
				//return;
				
				EnglishHangManMain.main(null);

			}

			if (ch == 'N' || ch == 'n') {
				System.out.println("��� ������ �����մϴ�");
				returnint=10;
				
				
			} else
				System.out.println("yŰ�� nŰ�� �����ֽʽÿ�");

		} catch (Exception e) {
			System.out.println("�߸��� ������ �ϼ̽��ϴ�2");
			
			
		}
		return returnint;
		
		
	

	}

	// ------------------------ ����ó��------------------------------

	class Alphabet {
		public void inputFormat(String alphabet) throws Exception {

			if (alphabet.length() > 1) {
				throw new Exception("1���� ���ĺ��� ���ʽÿ�");
			}

			int eng = 0;

			for (int i = 0; i < alphabet.length(); i++) {
				char ch = alphabet.charAt(i);
				if (ch >= 'a' && ch <= 'z') {

					eng++;

				}
			}
			if (eng == 0)
				throw new Exception("������ �ҹ��ڷ� �Է����ּ���");

		}

	}

}
