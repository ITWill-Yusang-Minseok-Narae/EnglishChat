package com.englishChat;

import java.util.Scanner;

public class EnglishAppLoginMain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		EnglishAppLoginInterface ob = new EnglishAppLoginControl();
		
		int ch;
		
		while(true){
			
			do{
				
				System.out.println("1.회원가입\n2.로그인\n3.가입 정보 수정\n"
						+ "4.탈퇴\n5.종료\n6.관리자(할건지 말건지");
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
				System.out.println("프로그램을 종료합니다");
				System.exit(0);
				break;
			default:
				break;
			
			}
			
		}
		

	}

}
