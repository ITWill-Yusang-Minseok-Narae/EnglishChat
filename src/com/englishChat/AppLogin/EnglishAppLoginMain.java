package com.englishChat.AppLogin;

import java.util.Scanner;


/*class ExceptionInt{//int������ ����ó��
	
	public void inputFormat(int ch) throws Exception{
		
		System.out.println("���ڸ� ���ÿ�");
		
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
					System.out.println("1.ȸ������\n2.�α���\n3.���� ���� ����\n"
							+ "4.Ż��\n5.����\n6.�����ڷα���");
					
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
					System.out.println("���α׷��� �����մϴ�");
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
			System.out.println("���ڸ� �Է����ֽʽÿ�");
			EnglishAppLoginMain.main(args);
		}
		

	}

}
