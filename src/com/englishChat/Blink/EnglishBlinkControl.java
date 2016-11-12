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
	// String key = it.next();// Ű�� ã�°��� value�� �ȳ���
	// String value = tm.get(key);// Ű�� value�� ã������
	// System.out.println(key + " : " + value);
	// }
	//
	// }

	public void next() { // �ܾ�����

		String txt = "";
		String fileName = "d:\\work\\test1.txt";

		try {

			// BufferedWriter �� FileWriter�� �����Ͽ� ��� (�ӵ� ���)
			BufferedWriter fw = new BufferedWriter(new FileWriter(fileName,
					true));

			// ���Ͼȿ� ���ڿ� ����
			fw.write(txt);
			fw.flush();
			// ��ü �ݱ�
			fw.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void test() { //�ܾ������

		Scanner s = new Scanner(System.in);
		System.out.println("����ܾ �Է��ϼ��� : [�ܾ�/������ �������ּ��䤷^_^��]");
		String word = s.next();

	}

	public void save() {//�ܾ�����Է�������
		Iterator<String> it = ebd.getWordbook().keySet().iterator();
		Scanner s = new Scanner(System.in);
		System.out.println("����ܾ� : [��� �Է��ϼ���.]");
		String word = s.next();
		System.out.println("�� : [�ѱ۸� �Է��ϼ���.]");
		String mean = s.next();
		String str = (word + ":" + mean);

		if (!word.equals(":")) {
			FileWriter fw;

			try {
				fw = new FileWriter("d:\\work\\test111.txt");

				fw.write(str);
				
				System.out.println("�ܾ ����Ǿ����ϴ�.");
				fw.close();
			} catch (IOException e) {
				System.out.println("�ٽ� �Է����ּ���."); // ������ �ִٸ� �޽��� ���
				System.exit(0);
			}
		}
	}

	public void delete() {//�ܾ�������

		Iterator<String> it = ebd.getWordbook().keySet().iterator();
		Scanner sc = new Scanner(System.in);
		System.out.println("������ ����ܾ�: [��� �Է��ϼ���]");
		String word = sc.next();
		while (it.hasNext()) {

			String key = it.next();// Ű�� ã�°��� value�� �ȳ���

			if (ebd.getWordbook().get(key).equals(word)) {
				ebd.getWordbook().remove(key);
				System.out.println("�����Ǿ����ϴ�.");
			} else {
				System.out.println("�߸��Է��ϼ̽��ϴ�.");
				break;
			}

		}

		FileWriter fw;

		try {
			fw = new FileWriter("d:\\work\\test111.txt");
			fw.write(word);
			fw.close();

		} catch (IOException e) {
			System.out.println("�ٽ� �Է����ּ���."); // ������ �ִٸ� �޽��� ���
			System.exit(0);
		}

	}

	public void look() {//�ܾ��庸����

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

	public void except() { //�ܾ��� ��ºκ�

	}

	public void controllor() {

		ArrayList<String> lists = new ArrayList<String>();
		Scanner sc = new Scanner(System.in);
		int ch, std;
		input();
		ebp.print(edd);
		System.out.println("    \t\t< �ü���Ż ����ϱ��� >   ");
		System.out.println(" \t���� ��� ���ܾ �����ұ��??? ");
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
						.println("1.�ܾ�ٽú���\n2.�𸣴´ܾ� �� ����ܾ��忡 ����\n3.�ܿ����� �ܾ� ���� �Է��ؼ� �ܾ��忡 ����\n"
								+ "4.�ܿ�ܾ� �� �ܾ��忡�� ����\n"
								+ "5.�� ���� �ܾ��庸��\n6.����ܾ�Test\n7.����\n------------------------\n");
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
				System.out.println("�����մϴ�..");
				System.exit(0);
			}
		}
	}
}
