package com.englishChat;

import java.util.Scanner;

public class EnglishAppLoginMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		EnglishAppLoginInterface ob = new EnglishAppLoginControl();
		
		int ch;
		
		while(true){
			
			do{
				
				System.out.println("1.ȸ������\n2.�α���\n3.���� ���� ����\n"
						+ "4.Ż��\n5.����\n6.������(�Ұ��� ������");
				ch=sc.nextInt();
				
			}while(ch<1);
			
			switch(ch){
			
			case 1:
				ob.input();
				break;
			case 2:
				ob.login();
				break;
			case 3:
				ob.update();
				break;
			case 4:
				ob.delete();
				break;
			case 5:
				System.out.println("���α׷��� �����մϴ�");
				System.exit(0);
				break;
			default:
				break;
			
			}
			
		}
		

	}

}
