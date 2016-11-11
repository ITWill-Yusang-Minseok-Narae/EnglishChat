package com.englishChat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
//����ó��---------------------------------------

class ExceptionId{//id����ó��
	
	public void inputFormat(String id) throws Exception{
		
		if(id.length()<6 || id.length()>15)
			throw new Exception("id�� �ּ� 6���� ~ �ִ�15���ڿ��� �մϴ�");
		
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
			throw new Exception("id�� ������ ���ڸ� ȥ���ؾ� �մϴ�");
		}
		
	}
	
}

class ExceptionPw{//�������ó��
	public void inputFormat(String pw1,String pw2) throws Exception{
		
		if(!pw1.equals(pw2))
			throw new Exception("��й�ȣ�� ��й�ȣ Ȯ���� �����ʽ��ϴ�");
		
		if(pw1.length()<8 || pw2.length()>15)
			throw new Exception("��й�ȣ�� �ּ� 8����~ �ִ� 15���ڷ� �����ؾ� �մϴ�");	
	}
}

class ExceptionName{//�̸�����ó��
	public void inputFormat(String name)throws Exception{
		
		
		int eng=0;
		int num=0;
		
		if(name.length()<2 ||name.length()>5)
			throw new Exception("�̸���(������) �ּ� 2����~5���ڷ� �Է��ؾ� �մϴ�");
			
		for(int i=0;i<name.length();i++){
			char ch = name.charAt(i);
			if((ch>='a' &&ch<='z') || (ch>='A'&&ch<='Z')){
				eng++;
			}else if(ch>='0' && ch<='9')
				num++;
		}
		if(eng>0 || num >0)
			throw new Exception("�ѱ��̸����� �Է��ؾ� �մϴ�");
		
		
	}
	
}
class ExceptionEmail{//�̸��� ����
	public void inputFormat(String email)throws Exception{
	
		String temp[]=email.split(".");
		int num=0;
		
		for(int i=0;i<email.length();i++){
			char ch = email.charAt(i);
			if(ch=='@' ||ch =='.'){
				
				num++;
				
			}
			
		}if(num<2){
			throw new Exception("�̸��� ������ �ƴմϴ�");
		}
		/*
		if(temp[0].length()<8){
			throw new Exception("�̸��� ������ �ƴմϴ�");
		}
		if(temp[1].length()<2){
			throw new Exception("�̸��� ������ �ƴմϴ�");
		}*/
		
		
	}
	
	
}

public class EnglishAppLoginControl implements EnglishAppLoginInterface{


	public EnglishAppLoginControl() {//�⺻��������
	
		this.list = new ArrayList<EnglishAppLoginVo>();
		
	}
	
	
	Scanner sc = new Scanner(System.in);
	Calendar now = Calendar.getInstance(); //������ϰ˼���
	Calendar yyyy = Calendar.getInstance(); //���̿�
	
	private ArrayList<EnglishAppLoginVo> list;
	
	

	@Override
	public void input() {
		
		ExceptionId EId = new ExceptionId(); //���̵� ����ó��
		ExceptionPw EPw = new ExceptionPw(); //�н����� ����ó��
		ExceptionName EN = new ExceptionName(); //�̸� ����ó��
		ExceptionEmail EE = new ExceptionEmail();//�̸��� ����ó��
		
		EnglishAppLoginVo vo = new EnglishAppLoginVo();
		
		int year,month,day;
		String id,pw1,pw2,name,email,name2;
		
		/*while(true){
			
			System.out.println("���̵�?");
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
			System.out.println("��й�ȣ�� �Է��Ͻʽÿ�");
			vo.setPw1(sc.next());
			pw1=vo.getPw1();
			
			System.out.println("��й�ȣ Ȯ�� �ٽ��ѹ� �Է��Ͻʽÿ�");
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
			System.out.println("�̸�?");
			
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
			System.out.println("�̸���?");
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
				System.out.println("����?0000");
				year=sc.nextInt();
			}while(year<1900);
			do{
				System.out.println("����?00");
				month = sc.nextInt();
				System.out.println(month);
				
			}while(month <1 || month >12);
			now.set(year, month - 1, 1);
			
			
			do{
				System.out.println("����?");
				day = sc.nextInt();
			}while(day<1||day>now.getActualMaximum(Calendar.DATE));
//			System.out.println(month+"1");
			now.set(year, month - 1,day);

//			System.out.println(month+"2");
			System.out.printf("%4d - %d - %2d *\n",
					now.get(Calendar.YEAR),now.get(Calendar.MONTH)+1,now.get(Calendar.DATE));
			vo.setBirth(now);
			
			
			
			//System.out.println("����?");
			int y = yyyy.get(Calendar.YEAR);
			vo.setAge((y-year)+1);
			System.out.println("���� : "+vo.getAge());
			
		
			System.out.printf("\n========================\n�����մϴ�!\n\n");
			System.out.println("������ �Ϸ�Ǿ����ϴ�");
			
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













