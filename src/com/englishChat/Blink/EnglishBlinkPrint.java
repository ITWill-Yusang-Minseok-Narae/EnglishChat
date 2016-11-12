package com.englishChat.Blink;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.englishChat.Dictionary.EnglishDictionaryData;

public class EnglishBlinkPrint {

	private TreeMap<String, String> tm;

	EnglishBlinkData ecd = new EnglishBlinkData();

	public void print(EnglishDictionaryData edd) {// ���
	
		
		Iterator<String> it = edd.getMinidictionary().keySet().iterator();
				
//		while (it.hasNext()) {
//
//			String key = it.next();// Ű�� ã�°��� value�� �ȳ���
//			String value = ecd.getMinidictionary().get(key);// Ű�� value�� ã������
//			
//			System.out.println(key + " : " + value);
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			for (int i = 0; i < 40; i++) {
//				
//				System.out.println();
//				try {
//					Thread.sleep(200);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
	}	
	public void blink(EnglishDictionaryData edd) {// ���

		Iterator<String> it = edd.getMinidictionary().keySet().iterator();

		while (it.hasNext()) {

			String key = it.next();// Ű�� ã�°��� value�� �ȳ���
			String value = edd.getMinidictionary().get(key);// Ű�� value�� ã������
			System.out.println("\t\t"+ key + " : " + value);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int i = 0; i < 50; i++) {
				
				System.out.println();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
