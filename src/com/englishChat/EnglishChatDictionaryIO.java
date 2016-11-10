package com.englishChat;

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

public class EnglishChatDictionaryIO implements EnglishChatInterface{

	@Override
	public void dictionaryAllInput(EnglishChatData ecd) {
		
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
			fis = new FileInputStream("d:\\java\\work\\EnglishChat\\한영사전20161109.txt");
		} catch (FileNotFoundException e) {
			System.out.println("파일을 못열었습니다");
			e.printStackTrace();
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String str;
		
		try {
			int num = 0;
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
//				System.out.println(++num + "줄 " + key + " 읽기 성공");
			}
		} catch (IOException e) {
			System.out.println("파일 읽기 실패");
			e.printStackTrace();
			return;
		}
		System.out.println("TreeMap 입력 끝");
		ecd.setDictionary(tm);
		dictionaryPrint(ecd);
	}

	@Override
	public void dictionaryPrint(EnglishChatData ecd) { //테스트용
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("d:\\result.txt");
		} catch (FileNotFoundException e) {
			System.out.println("파일 생성을 못했습니다");
			e.printStackTrace();
			return;
		}
		PrintStream ps = new PrintStream(fos);
		
		Iterator<String> it = ecd.getDictionary().keySet().iterator();
		int num = 0;
		while (it.hasNext()) {
			String key = it.next();//키
			ps.print(key);
			LinkedList<EnglishChatWord> ll =ecd.getDictionary().get(key);//value
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
//				System.out.println(++num + "줄 쓰기 성공");
		}
		ps.close();
		try {
			fos.close();
		} catch (IOException e) {
			System.out.println("파일을 닫지 못했습니다");
			e.printStackTrace();
			return;
		}
		System.out.println("파일 출력 끝");
	}
}
