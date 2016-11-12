package com.englishChat.AppLogin;

import java.util.Scanner;


/*class ExceptionInt{//int값인지 예외처리
	
	public void inputFormat(int ch) throws Exception{
		
		System.out.println("숫자를 쓰시오");
		
	}
	
}*/

public class EnglishAppLoginMain {

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		EnglishAppLoginControl ob = new EnglishAppLoginControl();
		
		
		int ch=0;
		ob.load();
		
		try {
			while(true){
				
				do{
					System.out.println("1.회원가입\n2.로그인\n3.가입 정보 수정\n"
							+ "4.탈퇴\n5.종료\n6.관리자로그인");
					
					ch = sc.nextInt();
					
					
					
					
				}while(ch<1);
				
				switch(ch){
				
				case 1:
					ob.input();
					ob.save();
					break;
				case 2:
					ob.login();
					
					break;
				case 3:
					ob.update();
					ob.save();
					break;
				case 4:
					ob.delete();
					break;
				case 5:
					System.out.println("프로그램을 종료합니다");
					System.exit(0);
					break;
				default:
					ob.save();
					ob.adimlogin();
					break;
				
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("숫자만 입력해주십시오");
			EnglishAppLoginMain.main(args);
		}
		

	}

}
