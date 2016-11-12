package com.englishChat.Dictionary;
//20111108
//Kim Yusang
//bakkus@daum.net
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

import com.englishChat.EnglishChatWord;

public class EnglishDictionaryIO implements EnglishDictionaryInterface{

	@Override
	public void dictionaryFileInputToTreeMap(EnglishDictionaryData edd) {
		
		Comparator<String> comp = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if ((o1.toLowerCase()).compareTo(o2.toLowerCase())==0)
					if (o1.compareTo(o2)==0)
						return 0;
					else 
						return o1.compareTo(o2);
				else
					return (o1.toLowerCase()).compareTo(o2.toLowerCase());
			}
		};
		
		TreeMap<String, LinkedList<EnglishChatWord>> tm = new TreeMap<String, LinkedList<EnglishChatWord>>(comp);

		FileInputStream fis;
		try {
			fis = new FileInputStream("d:\\java\\work\\EnglishChat\\�ѿ�����20161109.txt");
		} catch (FileNotFoundException e) {
			System.out.println("������ ���������ϴ�");
			e.printStackTrace();
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String str;
		
		try {
			while ((str=br.readLine())!=null){
				String[] ss = str.split("/");
				String key = ss[0];
				LinkedList<EnglishChatWord> ll = new LinkedList<EnglishChatWord>();
				for (int i = 1; i < ss.length;) {
					EnglishChatWord ecw = new EnglishChatWord();
					if(ss[i].equals("a.")||ss[i].equals("ad.")||ss[i].equals("adj.")||ss[i].equals("b.")||ss[i].equals("int.")||ss[i].equals("n.")||ss[i].equals("pref.")||ss[i].equals("v.")||ss[i].equals("vi.")||ss[i].equals("vt."))
						ecw.setJosa(ss[i++]);
					else
						ecw.setJosa("");
					if(i==ss.length){
						ecw.setDdut("");
						ll.add(ecw);
						continue;
					}
					if(ss[i].equals("")){
						ecw.setDdut("");
						i++;
					}
					else if(ss[i].equals("a.")||ss[i].equals("ad.")||ss[i].equals("adj.")||ss[i].equals("b.")||ss[i].equals("int.")||ss[i].equals("n.")||ss[i].equals("pref.")||ss[i].equals("v.")||ss[i].equals("vi.")||ss[i].equals("vt."))
						ecw.setDdut("");
					else
						ecw.setDdut(ss[i++]);
					ll.add(ecw);
				}
				if (tm.get(key) != null){
					Iterator<EnglishChatWord> it = ll.listIterator();
					while (it.hasNext()) {
						EnglishChatWord ecw = it.next();
						tm.get(key).add(ecw);
					}
				}
				else
					tm.put(key, ll);
			}
		} catch (IOException e) {
			System.out.println("���� �б� ����");
			e.printStackTrace();
			return;
		}
		System.out.println("TreeMap �Է� ��");
		edd.setDictionary(tm);
		dictionaryWriteToFile(edd);
	}

	@Override
	public void dictionaryWriteToFile(EnglishDictionaryData edd) { //�׽�Ʈ��
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("d:\\result.txt");
		} catch (FileNotFoundException e) {
			System.out.println("���� ������ ���߽��ϴ�");
			e.printStackTrace();
			return;
		}
		PrintStream ps = new PrintStream(fos);
		
		Iterator<String> it = edd.getDictionary().keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();//Ű
			ps.print(key);
			LinkedList<EnglishChatWord> ll =edd.getDictionary().get(key);//value
			Iterator<EnglishChatWord> it2 = ll.listIterator();
			while (it2.hasNext()) {
				EnglishChatWord ecw = it2.next();
				if(!ecw.getJosa().equals(""))
					ps.print("/" + ecw.getJosa());
				if(!ecw.getDdut().equals(""))
					ps.print("/" + ecw.getDdut());
				else if(!it2.hasNext())
					ps.print("/");
			}
			ps.print("\r\n");
		}
		ps.close();
		try {
			fos.close();
		} catch (IOException e) {
			System.out.println("������ ���� ���߽��ϴ�");
			e.printStackTrace();
			return;
		}
		System.out.println("���� ��� ��");
	}

	@Override
	public void DictionaryFileInputToMiniTreeMap(EnglishDictionaryData edd) {

		Comparator<String> comp = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if ((o1.toLowerCase()).compareTo(o2.toLowerCase())==0)
					if (o1.compareTo(o2)==0)
						return 0;
					else 
						return o1.compareTo(o2);
				else
					return (o1.toLowerCase()).compareTo(o2.toLowerCase());
			}
		};
		
		TreeMap<String, String> tm = new TreeMap<String, String>(comp);

		FileInputStream fis;
		try {
			fis = new FileInputStream("d:\\java\\work\\EnglishChat\\�ѿ�����20161109.txt");
		} catch (FileNotFoundException e) {
			System.out.println("������ ���������ϴ�");
			e.printStackTrace();
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String str;
		
		try {
			while ((str=br.readLine())!=null){
				String[] ss = str.split("/");
				String key = ss[0];
				String ddut = "";
				for (int i = 1; i < ss.length;) {
					if(ss[i].equals("a.")||ss[i].equals("ad.")||ss[i].equals("adj.")||ss[i].equals("b.")||ss[i].equals("int.")||ss[i].equals("n.")||ss[i].equals("pref.")||ss[i].equals("v.")||ss[i].equals("vi.")||ss[i].equals("vt."))
						i++;
					else
						;
					if(i==ss.length){
						ddut = "";
						continue;
					}
					if(ss[i].equals("")){
						ddut = "";
						i++;
					}
					else if(ss[i].equals("a.")||ss[i].equals("ad.")||ss[i].equals("adj.")||ss[i].equals("b.")||ss[i].equals("int.")||ss[i].equals("n.")||ss[i].equals("pref.")||ss[i].equals("v.")||ss[i].equals("vi.")||ss[i].equals("vt."))
						ddut = "";
						else{
							ddut = ss[i++];
							break;
						}
				}
				if (tm.get(key) != null){
				} else
					tm.put(key,ddut);
			}
		} catch (IOException e) {
			System.out.println("���� �б� ����");
			e.printStackTrace();
			return;
		}
		System.out.println("TreeMap �Է� ��");
		edd.setMinidictionary(tm);
		miniDictionaryWriteToFile(edd);
	}

	@Override
	public void miniDictionaryWriteToFile(EnglishDictionaryData edd) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("d:\\result.txt");
		} catch (FileNotFoundException e) {
			System.out.println("���� ������ ���߽��ϴ�");
			e.printStackTrace();
			return;
		}
		PrintStream ps = new PrintStream(fos);
		
		Iterator<String> it = edd.getMinidictionary().keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();//Ű
			ps.print(key);
			String ddut =edd.getMinidictionary().get(key);//value			
				if(!ddut.equals(""))					
					ps.print("/" + ddut);
			ps.print("\r\n");
		}
		ps.close();
		try {
			fos.close();
		} catch (IOException e) {
			System.out.println("������ ���� ���߽��ϴ�");
			e.printStackTrace();
			return;
		}
		System.out.println("���� ��� ��");
	}
}