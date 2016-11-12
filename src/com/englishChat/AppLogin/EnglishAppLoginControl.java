package com.englishChat.AppLogin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

//예외처리------------------------------------------------------------------------------

class ExceptionId {// id예외처리

	public void inputFormat(String id) throws Exception {

		if (id.length() < 6 || id.length() > 15)
			throw new Exception("id는 최소 6글자 ~ 최대15글자여야 합니다");

		int eng = 0;
		int num = 0;

		for (int i = 0; i < id.length(); i++) {
			char ch = id.charAt(i);
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
				eng++;
			} else if (ch >= '0' && ch <= '9')
				num++;
		}
		if (eng == 0 || num == 0) {
			throw new Exception("id는 영문과 숫자를 혼용해야 합니다");
		}

	}

}

class ExceptionPw {// 비번예외처리
	public void inputFormat(String pw1, String pw2) throws Exception {

		if (!pw1.equals(pw2))
			throw new Exception("비밀번호와 비밀번호 확인이 맞지않습니다");

		if (pw1.length() < 8 || pw2.length() > 15)
			throw new Exception("비밀번호는 최소 8글자~ 최대 15글자로 설정해야 합니다");
	}
}

class ExceptionName {// 이름예외처리
	public void inputFormat(String name) throws Exception {

		int eng = 0;
		int num = 0;

		if (name.length() < 2 || name.length() > 5)
			throw new Exception("이름은(성포함) 최소 2글자~5글자로 입력해야 합니다");

		for (int i = 0; i < name.length(); i++) {
			char ch = name.charAt(i);
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
				eng++;
			} else if (ch >= '0' && ch <= '9')
				num++;
		}
		if (eng > 0 || num > 0)
			throw new Exception("한글이름으로 입력해야 합니다");

	}

}

class ExceptionEmail {// 이메일 예외
	public void inputFormat(String email) throws Exception {

		String temp[] = email.split(".");
		int num = 0;

		for (int i = 0; i < email.length(); i++) {
			char ch = email.charAt(i);
			if (ch == '@' || ch == '.') {

				num++;

			}

		}
		if (num < 2) {
			throw new Exception("이메일 형식이 아닙니다");
		}
		/*
		 * if(temp[0].length()<8){ throw new Exception("이메일 형식이 아닙니다"); }
		 * if(temp[1].length()<2){ throw new Exception("이메일 형식이 아닙니다"); }
		 */

	}

}

// 예외처리 끝
// -------------------------------------------------------------------------------

public class EnglishAppLoginControl implements EnglishAppLoginInterface {

	public EnglishAppLoginControl() {// 기본생ㅅ성자

		this.list = new ArrayList<EnglishAppLoginVo>();

	}

	Scanner sc = new Scanner(System.in);
	Calendar now = Calendar.getInstance(); // 생년월일검수용
	Calendar yyyy = Calendar.getInstance(); // 나이용

	private ArrayList<EnglishAppLoginVo> list;

	// 회원가입-----------------------------------------------------------------------------
	@Override
	public void input() {

		ExceptionId EId = new ExceptionId(); // 아이디 예외처리
		ExceptionPw EPw = new ExceptionPw(); // 패스워드 예외처리
		ExceptionName EN = new ExceptionName(); // 이름 예외처리
		ExceptionEmail EE = new ExceptionEmail();// 이메일 예외처리

		EnglishAppLoginVo vo = new EnglishAppLoginVo();

		int year, month, day;
		String id, pw1, pw2, name, email;

		while (true) {

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

		while (true) {
			System.out.println("비밀번호를 입력하십시오");
			vo.setPw1(sc.next());
			pw1 = vo.getPw1();

			System.out.println("비밀번호 확인 다시한번 입력하십시오");
			vo.setPw2(sc.next());
			pw2 = vo.getPw2();
			try {

				EPw.inputFormat(pw1, pw2);
				break;
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}

		while (true) {
			System.out.println("이름?");

			// name2=sc.next();
			// System.out.println(name2);

			vo.setName(sc.next());
			name = vo.getName();

			try {
				EN.inputFormat(name);
				break;
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}
		while (true) {
			System.out.println("이메일?");
			vo.setEmail(sc.next());
			email = vo.getEmail();

			try {
				EE.inputFormat(email);
				break;
			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}

		try {

			do {
				System.out.println("생년?0000");
				year = sc.nextInt();
			} while (year < 1900);
			do {
				System.out.println("생월?00");
				month = sc.nextInt();

			} while (month < 1 || month > 12);
			now.set(year, month - 1, 1);

			do {
				System.out.println("생일?");
				day = sc.nextInt();
			} while (day < 1 || day > now.getActualMaximum(Calendar.DATE));

			now.set(year, month - 1, day);

			vo.setBirth(now);

			// System.out.println("나이?");
			int y = yyyy.get(Calendar.YEAR);
			vo.setAge((y - year) + 1);
			System.out.println("나이 : " + vo.getAge());

			System.out.printf("\n========================\n축하합니다!\n\n");
			System.out.println("가입이 완료되었습니다");

			if (list == null)
				list = new ArrayList<EnglishAppLoginVo>();
			System.out.println("환영합니다!  " + vo.getId() + "  님!");
			System.out.println(vo.toString());
			System.out.println("========================");
			System.out.println();

			list.add(vo);

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// 로그인--------------------------------------------------------------------------------

	@Override
	public void login() {

		System.out.println("로그인 할 id를 치시오");
		String str = sc.next();

		Iterator<EnglishAppLoginVo> it = list.iterator();

		while (it.hasNext()) {

			EnglishAppLoginVo vo = it.next();
			if (vo.getId().equals(str)) {
				System.out.println("비밀번호를 치시오");
				String pw = sc.next();

				if (vo.getPw1().equals(pw)) {

					System.out.println("로그인 완료");

					// 여기에 메뉴들로 가는것
					// 1깜빡이 단어장
					// 2행맨
					// 3뜻맞추기 타자게임
					// 4사전 검색
					// 5(깜빡이 단어장)에서 저장한 My 단어장

				} else {
					System.out.println("비밀번호가 맞지않습니다");
					System.out.println();
					break;
				}
			} else {
				System.out.println("입력하신 아이디는 존재하지않습니다");
				System.out.println();
				break;
			}
		}

	}

	// 회원정보수정------------------------------------------------------------------------

	@Override
	public void update() {
		ExceptionId EId = new ExceptionId(); // 아이디 예외처리
		ExceptionPw EPw = new ExceptionPw(); // 패스워드 예외처리
		ExceptionName EN = new ExceptionName(); // 이름 예외처리
		ExceptionEmail EE = new ExceptionEmail();// 이메일 예외처리

		// EnglishAppLoginVo vo = new EnglishAppLoginVo();

		int year, month, day;
		String id1, pw1, pw2, name1, email;

		System.out.println("로그인 할 id를 치시오");
		String str = sc.next();

		Iterator<EnglishAppLoginVo> it = list.iterator();

		while (it.hasNext()) {

			EnglishAppLoginVo vo = it.next();
			if (vo.getId().equals(str)) {
				System.out.println("비밀번호를 치시오");
				String pw = sc.next();

				if (vo.getPw1().equals(pw)) {

					System.out.println("로그인 완료");

					System.out.println();
					System.out.println("회원정보 수정을 시작합니다");
					System.out.println();

					// 아이디값 그대로 가져옴
					id1 = vo.getId();
					vo.setId(id1);

					// 수정할 비밀번호
					while (true) {
						System.out.println("바꾸실 비밀번호를 입력하십시오");
						vo.setPw1(sc.next());
						pw1 = vo.getPw1();

						System.out.println("바꾸실 비밀번호 확인 다시한번 입력하십시오");
						vo.setPw2(sc.next());
						pw2 = vo.getPw2();
						try {

							EPw.inputFormat(pw1, pw2);
							break;
						} catch (Exception e) {
							System.out.println(e.toString());
						}

					}
					// 이름 그대로 가져옴
					name1 = vo.getName();
					vo.setName(name1);

					// 수정할 이메일

					while (true) {
						System.out.println("이메일?");
						vo.setEmail(sc.next());
						email = vo.getEmail();

						try {
							EE.inputFormat(email);
							break;
						} catch (Exception e) {
							System.out.println(e.toString());
						}

					}

					// 수정할 생일+나이

					try {
						do {
							System.out.println("생년?0000");
							year = sc.nextInt();
						} while (year < 1900);
						do {
							System.out.println("생월?00");
							month = sc.nextInt();

						} while (month < 1 || month > 12);
						now.set(year, month - 1, 1);

						do {
							System.out.println("생일?");
							day = sc.nextInt();
						} while (day < 1
								|| day > now.getActualMaximum(Calendar.DATE));

						now.set(year, month - 1, day);

						vo.setBirth(now);

						// System.out.println("나이?");
						int y = yyyy.get(Calendar.YEAR);
						vo.setAge((y - year) + 1);
						System.out.println("나이 : " + vo.getAge());

						System.out
								.printf("\n========================\n축하합니다!\n\n");
						System.out.println("회원님의 정보가 수정되었습니다");
						System.out.println();

						if (list == null)
							list = new ArrayList<EnglishAppLoginVo>();

						System.out.println(vo.toString());
						System.out.println("========================");

						save();
						break;
					} catch (Exception e) {
						// TODO: handle exception
					}

				} else {
					System.out.println("비밀번호가 맞지않습니다");
					System.out.println();
					break;
				}
			} else {
				System.out.println("입력하신 아이디는 존재하지않습니다");
				System.out.println();
				break;
			}
		}

	}

	// 회원탈퇴-------------------------------------------------------------------------------
	@Override
	public void delete() {
		Iterator<EnglishAppLoginVo> it = list.iterator();
		//EnglishAppLoginVo vo = it.next();

//		
//		while (it.hasNext()) {
//
//			EnglishAppLoginVo vo = it.next();
//			System.out.printf("%s   ㅣ   %s   ㅣ   %s   ㅣ", vo.getId(),
//					vo.getPw1(), vo.getEmail());
//			System.out.print(vo.toString());// 이름 생년월일 나이
//			System.out.println();
//			
//		}
		System.out.println("삭제 id를 치시오");
		String str = sc.next();
		
		while(it.hasNext()){
			EnglishAppLoginVo vo = it.next();
			
			if(vo.getId() .equals(str)){
				
				
				System.out.println("dddddddd" );
				break;
			}else 
				System.out.println("해당 아이디는 없습니다");
			break;
		}
		
		
		
		
		
		/*while (it.hasNext()) {

			EnglishAppLoginVo vo = it.next();
			System.out.println(vo.getId());
			
			

			if (vo.getId().equals(str)) {
				System.out.println("해당 아이디가 존재합니다");
				System.out.println();

				System.out.println("비밀번호를 치시오");
				String pw = sc.next();

				if (vo.getPw1().equals(pw)) {

					try {
						FileInputStream fis = new FileInputStream(
								"d:\\EnglishChat\\join.txt");
						ObjectInputStream ois = new ObjectInputStream(fis);
						// text파일 열기

						list.remove(vo);
						// 해당배열삭제
						
						
					} catch (Exception e) {
						// TODO: handle exception
					}

					System.out.println("삭제(탈퇴)되었습니다");
					System.out.println();
					save();// text파일로 저장
					break;

				} else {
					System.out.println("비밀번호가 맞지않습니다");
					System.out.println();
					break;
				}

			} else {
				System.out.println("입력하신 아이디는 존재하지 않습니다");
				break;
			}

		}*/

	}

	// 회원정보 텍스트파일로
	// 저장-------------------------------------------------------------

	@Override
	public void save() {// 직렬화

		try {

			FileOutputStream fos = new FileOutputStream(
					"d:\\EnglishChat\\join.txt");

			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(list);

			oos.close();
			fos.close();// 직렬화 끝

			// 출력---------------------------------------

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// 회원정보 텍스트파일에서 읽어옴--------------------------------------------------------

	@Override
	public void load() {// 역직렬화

		// private String path = System.getProperty("D:");
		// private File f = new File(path, "\\EnglishChat\\join.txt");

		try {
			FileInputStream fis = new FileInputStream(
					"d:\\EnglishChat\\join.txt");

			ObjectInputStream ois = new ObjectInputStream(fis);

			System.out.println("회원정보 파일을 읽어들였습니다.");
			System.out.println();
			try {
				list = (ArrayList<EnglishAppLoginVo>) ois.readObject();

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("회원정보 파일이 존재하지 않습니다");
				System.out.println();
			}

			ois.close();
			fis.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void adimlogin() {// 임시로 프린드하는거 여기에 넣음

		Iterator<EnglishAppLoginVo> it = list.iterator();

		System.out
				.println("==============================================================");
		System.out.printf("아이디\t\t 비밀번호\t\t 이메일\t\t  이름\t "
				+ "생년 - 월 - 일  나이\n");

		while (it.hasNext()) {

			EnglishAppLoginVo vo = it.next();
			System.out.printf("%s   ㅣ   %s   ㅣ   %s   ㅣ", vo.getId(),
					vo.getPw1(), vo.getEmail());
			System.out.print(vo.toString());// 이름 생년월일 나이
			System.out.println();
			/*
			 * System.out.printf("%s\t %s\t %d\n", vo.getName(),
			 * vo.printBirth(), vo.getAge());
			 */
		}
		System.out
				.println("==============================================================");
		System.out.println();

	}
}
