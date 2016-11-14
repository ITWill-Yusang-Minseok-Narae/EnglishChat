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

	public EnglishBlinkControl() {// �޴�

		this.ebd = new EnglishBlinkData();
		this.edd = new EnglishDictionaryData();

		int ch, str;
		Scanner sc = new Scanner(System.in);
		System.out.println("--------<����ܾ��忡 ���Ű� ȯ���մϴ�.>--------- ��^^��");
		System.out.println("");

		System.out.println("\t�����Ͻ� ���α׷��� �������ּ���.��^^�� ");
		while (true) {
			do {
				System.out.println("---------------------------------------");
				System.out
						.println("\t1.�ܾ�ϱ�\n"
								+ "\t2.�ܿ��� �� �ܾ� ���� �Է����� �� �ܾ��忡 ����\n"
								+ "\t3.�ܿ�ܾ� �ܾ��忡�� ����\n\t4.�� ����ܾ��庸��\n\t5.����ܾ�Test\n\t6.���α׷� ����\n---------------------------------------\n");
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
				System.out.println("�����մϴ�.. �����ϼ̽��ϴ�^^");
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
	// String key = it.next();// Ű�� ã�°��� value�� �ȳ���
	// String value = tm.get(key);// Ű�� value�� ã������
	// System.out.println(key + " : " + value);
	// }
	//
	// }

	// public void next() { // �ܾ�����
	//
	// String txt = "";
	// String fileName = "d:\\work\\test1.txt";
	//
	// try {
	//
	// // BufferedWriter �� FileWriter�� �����Ͽ� ��� (�ӵ� ���)
	// BufferedWriter fw = new BufferedWriter(new FileWriter(fileName,
	// true));
	//
	// // ���Ͼȿ� ���ڿ� ����
	// fw.write(txt);
	// fw.flush();
	// // ��ü �ݱ�
	// fw.close();
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }

	public void test() { // �ܾ������
		int score = 0;
		int test = 0;
		Scanner s = new Scanner(System.in);
		// edd.getMinidictionary();
		Random rd = new Random();

		System.out.println("��� �ܾ Test���ðڽ��ϱ�?");
		test = s.nextInt();
		ebd.setWordbook2(tm);
		Object[] keys = edd.getDictionary().keySet().toArray();
		for (int i = 0; i < test; i++) {
			int testRandom = rd.nextInt(2);
			Object randomKey = keys[rd.nextInt(keys.length)];
			if (testRandom == 1) {
				Object randomValue = edd.getDictionary().get(randomKey);
				System.out.print("����ܾ� �� : " + randomValue);
				System.out.print("����ܾ� : [������ �Է��ϼ���.]\n");
				String mean = s.next();
				String answerWord = (String) randomKey;
				if (answerWord.equals(mean)) {
					score++;
					System.out.println("�����Դϴ�. ����: " + score);
				} else
					System.out.println("Ʋ�Ƚ��ϴ�.");
			} else {
				System.out.print("����ܾ� : " + randomKey);
				System.out.print("�� : [�ѱ۸� �Է��ϼ���.]\n");
				String mean = s.next();
				TreeMap<String, LinkedList<EnglishDictionaryWord>> answerMean = edd
						.getDictionary();
				if (answerMean.equals(mean)) {
					score++;
					System.out.println("�����Դϴ�. ��������: " + score);
				} else
					System.out.println("Ʋ�Ƚ��ϴ�.");
			}
		}
		System.out.println("���� ���: " + score);
	}

	public void save() {// �ܾ�����Է�������
		ebd.setWordbook2(tm);
		Iterator<String> it = ebd.getWordbook().keySet().iterator();
		while (true) {
			Scanner s = new Scanner(System.in);

			System.out.print("����ܾ� : [��� �Է��ϼ���.]");
			String word = s.next();
			System.out.print("�� : [�ѱ۸� �Է��ϼ���.]");
			String mean = s.next();

			ebd.getWordbook().put(word, mean);
			System.out.println("�ܾ��忡 �߰� �Ǿ����ϴ�.");
			System.out.print("�ܾ��忡 ��� �߰��Ͻðڽ��ϱ�?[y/n]");

			String ans = s.next();
			if (ans.equals("n")) {
				System.out.println("�߰��� �����մϴ�.");
				break;
			}

		}

		try {

			FileOutputStream fos = new FileOutputStream("d:\\test111.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(ebd.getWordbook());// ���
			System.out.println("�ܾ�����Ϸ�!!");
			oos.close();
			fos.close();
		} catch (Exception e) {
		}
	}

	public void delete() {// �ܾ�������
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		char ch = 0;
		Scanner sc = new Scanner(System.in);
		int num = 0;
		System.out.print("������ ����ܾ�: [��� �Է��ϼ���]");
		String word = sc.next();
		while (it.hasNext()) {

			String key = it.next();// Ű�� ã�°��� value�� �ȳ���
			System.out.println();
			if (tm.get(key).equals(word)) {
				num++;
			}
			if (num > 0) {
				
			try {
				System.out.println("��¥+" + tm.get(key) + "�ܾ ����ðڽ��ϱ�?[y/n]");
				ch = (char)System.in.read();
			} catch (Exception e) {
				// TODO: handle exception
			}
			if(ch == 'Y' || ch == 'y') {
				try {  

					 fis = new FileInputStream(	"d:\\test111.txt");
					 ois = new ObjectInputStream(fis);
					// text���� ����

					
					// �ش�迭����

					System.out.println(key + "�� �����Ǿ����ϴ�");
					System.out.println();
					tm.remove(key);
					save();// text���Ϸ� ����
					break;

				} catch (Exception e) {
					// TODO: handle exception
				}

			}
				
				System.out.println("�߰��� �����մϴ�.");
				break;

			}
		}
	}

	public void look() {// �ܾ��庸����
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
		}// ���

		for (int i = 0; i < tm.size(); i++) {
			Iterator<String> it = tm.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();// Ű�� ã�°��� value�� �ȳ���
				String value = tm.get(key);// Ű�� value�� ã������
				System.out.println(key + " : " + value);
			}
		}
	}

	public void wordblink() { // �ܾ�ϱ�

		Scanner sc = new Scanner(System.in);
		int ch, std;

		input();
		System.out.println("    \t< �ü���Ż ����ϱ��� >   ");
		System.out.println(" \t���� ��� ���ܾ �����ұ��??? ");
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
			System.out.print("�ٽ� ���ðڽ��ϱ�??[y/n]");
			String ans = sc.next();
			if (ans.equals("n")) {
				System.out.println("�ܾ�ϱ⸦ �����մϴ�.");
				break;
			}
		}
	}
}