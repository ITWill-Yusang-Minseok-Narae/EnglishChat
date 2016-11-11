package com.englishChat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
//예외처리---------------------------------------

class ExceptionId{//id예외처리
	
	public void inputFormat(String id) throws Exception{
		
		if(id.length()<6 || id.length()>15)
			throw new Exception("id는 최소 6글자 ~ 최대15글자여야 합니다");
		
		int eng =0;
		int num=0;
		
		for(int i=0; i<id.length();i++){
			char ch = id.charAt(i);
			if((ch>='a' && ch<='z')|| (ch>='A'&&ch<='Z')){
				eng++;
			}else if(ch>='0' && ch<='9')
				num++;
		}
		if(eng==0 || num==0){
			throw new Exception("id는 영문과 숫자를 혼용해야 합니다");
		}
		
	}
	
}

class ExceptionPw{//비번예외처리
	public void inputFormat(String pw1,String pw2) throws Exception{
		
		if(!pw1.equals(pw2))
			throw new Exception("비밀번호와 비밀번호 확인이 맞지않습니다");
		
		if(pw1.length()<8 || pw2.length()>15)
			throw new Exception("비밀번호는 최소 8글자~ 최대 15글자로 설정해야 합니다");	
	}
}

class ExceptionName{//이름예외처리
	public void inputFormat(String name)throws Exception{
		
		
		int eng=0;
		int num=0;
		
		if(name.length()<2 ||name.length()>5)
			throw new Exception("이름은(성포함) 최소 2글자~5글자로 입력해야 합니다");
			
		for(int i=0;i<name.length();i++){
			char ch = name.charAt(i);
			if((ch>='a' &&ch<='z') || (ch>='A'&&ch<='Z')){
				eng++;
			}else if(ch>='0' && ch<='9')
				num++;
		}
		if(eng>0 || num >0)
			throw new Exception("한글이름으로 입력해야 합니다");
		
		
	}
	
}
class ExceptionEmail{//이메일 예외
	public void inputFormat(String email)throws Exception{
	
		String temp[]=email.split(".");
		int num=0;
		
		for(int i=0;i<email.length();i++){
			char ch = email.charAt(i);
			if(ch=='@' ||ch =='.'){
				
				num++;
				
			}
			
		}if(num<2){
			throw new Exception("이메일 형식이 아닙니다");
		}
		/*
		if(temp[0].length()<8){
			throw new Exception("이메일 형식이 아닙니다");
		}
		if(temp[1].length()<2){
			throw new Exception("이메일 형식이 아닙니다");
		}*/
		
		
	}
	
	
}

public class EnglishAppLoginControl implements EnglishAppLoginInterface{


	public EnglishAppLoginControl() {//기본생ㅅ성자
	
		this.list = new ArrayList<EnglishAppLoginVo>();
		
	}
	
	
	Scanner sc = new Scanner(System.in);
	Calendar now = Calendar.getInstance(); //생년월일검수용
	Calendar yyyy = Calendar.getInstance(); //나이용
	
	private ArrayList<EnglishAppLoginVo> list;
	
	

	@Override
	public void input() {
		
		ExceptionId EId = new ExceptionId(); //아이디 예외처리
		ExceptionPw EPw = new ExceptionPw(); //패스워드 예외처리
		ExceptionName EN = new ExceptionName(); //이름 예외처리
		ExceptionEmail EE = new ExceptionEmail();//이메일 예외처리
		
		EnglishAppLoginVo vo = new EnglishAppLoginVo();
		
		int year,month,day;
		String id,pw1,pw2,name,email,name2;
		
		/*while(true){
			
			System.out.println("아이디?");
			vo.setId(sc.next());
			id = vo.getId();
			try {
				
				EId.inputFormat(id);
				
				break;
				
			} catch (Exception e1) {
				System.out.println(e1.toString());
				
			}
			
		
		}
		
		
		
		while(true){
			System.out.println("비밀번호를 입력하십시오");
			vo.setPw1(sc.next());
			pw1=vo.getPw1();
			
			System.out.println("비밀번호 확인 다시한번 입력하십시오");
			vo.setPw2(sc.next());
			pw2=vo.getPw2();
			try {
				
				EPw.inputFormat(pw1,pw2);
				break;
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
		}
		*/
		
		/*
		while(true){
			System.out.println("이름?");
			
			//name2=sc.next();
			//System.out.println(name2);
			
			vo.setName(sc.next());
			//System.out.println(vo.getName());
			
			
			try {
				EN.inputFormat(vo.getName());
				break;
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			
		}
		while(true){
			System.out.println("이메일?");
			vo.setEmail(sc.next());
			email=vo.getEmail();
			
			try {
				EE.inputFormat(email);
				break;
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
		}
		*/
		try {
			
			do{
				System.out.println("생년?0000");
				year=sc.nextInt();
			}while(year<1900);
			do{
				System.out.println("생월?00");
				month = sc.nextInt();
				System.out.println(month);
				
			}while(month <1 || month >12);
			now.set(year, month - 1, 1);
			
			
			do{
				System.out.println("생일?");
				day = sc.nextInt();
			}while(day<1||day>now.getActualMaximum(Calendar.DATE));
//			System.out.println(month+"1");
			now.set(year, month - 1,day);

//			System.out.println(month+"2");
			System.out.printf("%4d - %d - %2d *\n",
					now.get(Calendar.YEAR),now.get(Calendar.MONTH)+1,now.get(Calendar.DATE));
			vo.setBirth(now);
			
			
			
			//System.out.println("나이?");
			int y = yyyy.get(Calendar.YEAR);
			vo.setAge((y-year)+1);
			System.out.println("나이 : "+vo.getAge());
			
		
			System.out.printf("\n========================\n축하합니다!\n\n");
			System.out.println("가입이 완료되었습니다");
			
			if(list==null)
				list = new ArrayList<EnglishAppLoginVo>();
			
			System.out.println(vo.toString());
			System.out.println("========================");
			
			
			list.add(vo);
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public void login() {
	}

	@Override
	public void update() {
	}

	@Override
	public void delete() {
	}	

}













