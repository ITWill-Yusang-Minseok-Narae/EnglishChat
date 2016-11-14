package com.englishChat.AppLogin;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.englishChat.Blink.EnglishBlinkControl;
import com.englishChat.HangMan.EnglishHangManControl;
import com.englishChat.Taja.EnglishTajaClient;


public class EnglishAppLoginControl implements EnglishAppLoginInterface {

	Scanner sc = new Scanner(System.in);
	Calendar now = Calendar.getInstance(); // 생년월일검수용
	Calendar yyyy = Calendar.getInstance(); // 나이용


	private final int appLoginServerPort = 5551;
	private final String appLoginServerIP = "192.168.16.22";
	
	EnglishAppLoginMemberList ealml = null;
	EnglishAppLoginMember ealm = null;

	public EnglishAppLoginControl() {// 기본생성자
		ealml = new EnglishAppLoginMemberList();
		manager();
	}

	// 회원가입-----------------------------------------------------------------------------
	@Override
	public void input() {

		ExceptionId EId = new ExceptionId(); // 아이디 예외처리
		ExceptionPw EPw = new ExceptionPw(); // 패스워드 예외처리
		ExceptionName EN = new ExceptionName(); // 이름 예외처리
		ExceptionEmail EE = new ExceptionEmail();// 이메일 예외처리

		EnglishAppLoginMember vo = new EnglishAppLoginMember();
		Iterator<EnglishAppLoginMember> it = ealml.getMemberList().iterator();
		int year, month, day;
		String id, pw1, pw2, name, email;
		int number = 0;

		while (true) {

			// Iterator<EnglishAppLoginMember> it = memberList.iterator();
			while (it.hasNext()) {

				vo = it.next();
				System.out.println("아이디?");
				String idd = sc.next();

				if (vo.getId().equals(idd)) {
					number++;
				}

				if (number == 0) {

					vo.setId(idd);
					id = vo.getId();

					try {
						EId.inputFormat(id);
						break;
					} catch (Exception e1) {
						System.out.println(e1.toString());
					}
				} else
					System.out.println("중복되는 아이디가 존재합니다 " + "다른아이디를 입력해주십시오");

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

//				if (memberList == null)
//					memberList = new ArrayList<EnglishAppLoginMember>();
				System.out.println("환영합니다!  " + vo.getId() + "  님!");
				System.out.println(vo.toString());
				System.out.println("========================");
				System.out.println();

				ealml.getMemberList().add(vo);

			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}

	}

	// 로그인--------------------------------------------------------------------------------

	@Override
	public void login() {
		int number = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("로그인 할 id를 치시오");
		String id = sc.next();

		Iterator<EnglishAppLoginMember> it = ealml.getMemberList().iterator();

		while (it.hasNext()) {

			EnglishAppLoginMember ealm = it.next();

			if (ealm.getId().equals(id)) {
				number++;
			}

			if (number > 0) {
				System.out.println("로그인할 id의 비밀번호를 치시오");
				String pw = sc.next();

				if (ealm.getPw1().equals(pw)) {

					System.out.println("로그인 완료");
					// 여기에 메뉴들로 가는것
					// 1깜빡이 단어장
					// 2행맨
					// 3뜻맞추기 타자게임
					// 4사전 검색
					// 5(깜빡이 단어장)에서 저장한 My 단어장
					gameMenu(ealm);
					return;

				} else {
					System.out.println("비밀번호가 맞지않습니다");
					System.out.println();
					break;
				}
			}
		}
		if (number == 0) {
			System.out.println("입력하신 아이디는 존재하지않습니다");
			System.out.println();

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
		int number = 0;
		int year, month, day;
		String id1, pw1, pw2, name1, email;
		Iterator<EnglishAppLoginMember> it = ealml.getMemberList().iterator();
		System.out.println("로그인 할 id를 치시오");
		String str = sc.next();

		while (it.hasNext()) {
			EnglishAppLoginMember vo = it.next();

			if (vo.getId().equals(str)) {
				number++;
			}

			if (number > 0) {
				System.out.println("수정할 id의 비밀번호를 치시오");
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

//						if (memberList == null)
//							memberList = new ArrayList<EnglishAppLoginMember>();

						System.out.println(vo.toString());
						System.out.println("========================");

						save();
						break;
					} catch (Exception e) {
						// TODO: handle exception
					}

				} else
					System.out.println("비밀번호가 맞지 않습니다");
				System.out.println();
				break;

			}

		}
		if (number == 0) {
			System.out.println("입력하신 아이디는 존재하지 않습니다");
			System.out.println();
		}

	}

	// 회원탈퇴-------------------------------------------------------------------------------
	@Override
	public void delete() {
		Iterator<EnglishAppLoginMember> it = ealml.getMemberList().iterator();
		int number = 0;

		System.out.println("삭제 id를 치시오");
		String str = sc.next();

		while (it.hasNext()) {

			EnglishAppLoginMember vo = it.next();

			System.out.println();

			if (vo.getId().equals(str)) {
				number++;
			}
			if (number > 0) {

				System.out.println("삭제할 id의 비번을 치시오");
				String pwstr = sc.next();
				if (vo.getPw1().equals(pwstr)) {

					try {
						FileInputStream fis = new FileInputStream(
								"d:\\EnglishChat\\join.txt");
						ObjectInputStream ois = new ObjectInputStream(fis);
						// text파일 열기

						ealml.getMemberList().remove(vo);
						// 해당배열삭제

					} catch (Exception e) {
						// TODO: handle exception
					}
					System.out.println("삭제(탈퇴)되었습니다");
					System.out.println();
					save();// text파일로 저장
					break;

				}

			}

		}
		if (number == 0) {
			System.out.println("해당 아이디는 존재하지 않습니다");
			System.out.println();
		}

	}

	// 회원정보 텍스트파일에서 읽어옴--------------------------------------------------------
	public void load() {
		Socket sk;
		EnglishAppLoginMemberList ecd;
		BufferedReader br = null;
		InputStream is = null;
		InputStreamReader isr = null;
		ObjectInputStream ois = null;
		ArrayList<EnglishAppLoginMember> memberList = null;
		EnglishAppLoginMember member = null;
		String testString = null;
		Object ob = null;

		try {
			sk = new Socket(appLoginServerIP, appLoginServerPort);
		} catch (Exception e) {
			System.out.println("서버가 죽어있다...");
			return;
		}

		try {
			is = sk.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ois = new ObjectInputStream(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			ob = ois.readObject();
//			testString = (String) ois.readObject();
//			member = (EnglishAppLoginMember) ois.readObject();			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List list = (List) ob;
		memberList = (ArrayList<EnglishAppLoginMember>) list;
		
		System.out.println("리스트읽기완료");
//		System.out.println(member.getId());		
		
		// try {
		// ois.close();
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		
		ealml.setMemberList(memberList);
		
		// etu = new EnglishTajaUser();
		// etu.setName(userName);
		// etu.setIp(name);
		// etu.setSc(sk);
	}
	

	// 회원정보 텍스트파일로
	// 저장-------------------------------------------------------------

	public void save() {
		class save {
			private static final long serialVersionUID = -8029413848722483074L;
			private Socket sk = null;
			private final int port = 5555;
			private String serverIP = "127.0.0.1";

			public void save() {
				System.out.println("서버에 연결");
				connect();
			}

			public void connect() {
				OutputStream os = null;
				ObjectOutputStream oos = null;

				System.out.print("정보를 서버에 업로드 합니다");

				try {
					sk = new Socket(serverIP, port);
				} catch (Exception e) {
					System.out.println("서버가 죽어있다...");
					return;
				}
				try {
					os = sk.getOutputStream();// /
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					oos = new ObjectOutputStream(os);// /
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					oos.reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					oos.writeObject(ealml.getMemberList());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					oos.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// try {
				// oos.close();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				/*
				 * Thread readerThread = new Thread(new Reader(sk)); Thread
				 * writerThread = new Thread(new Writer(sk,name));
				 * readerThread.start();//Reader호출
				 * writerThread.start();//Writer호출
				 */

			}

		}

	}

	// 출력------------------------------------------------------------------------------
	@Override
	public void admlogin() {// 임시로 프린드하는거 여기에 넣음

		Iterator<EnglishAppLoginMember> it = ealml.getMemberList().iterator();
		int number, ch = 0;
		String admin = "admin";
		String adminPw = "adminpw";

		System.out.println("관리자 id를 치시오");
		String str = sc.next();

		if (admin.equals(str)) {

			System.out.println("관리자 비밀번호를 치시오");
			String strpw = sc.next();

			if (adminPw.equals(strpw)) {

//				try {
					boolean loop = true;
					while (loop) {

						do {
							System.out.println("1.회원 전체 조회 \n2.회원가입 정보 수정\n"
									+ "3.블랙리스트 강제 탈퇴 \n4.관리자모드 종료");

							ch = sc.nextInt();

						} while (ch < 1);

						switch (ch) {

						case 1: // 회원 전체 조회

							load();
							System.out
									.println("==============================================================");
							System.out.printf("아이디\t    비밀번호\t\t "
									+ "이메일\t\t이름\t "
									+ "생년 - 월 - 일  나이\n");

							while (it.hasNext()) {

								EnglishAppLoginMember vo = it.next();
								System.out.printf("%s   ㅣ   %s   ㅣ   %s   ㅣ",
										vo.getId(), vo.getPw1(), vo.getEmail());
								System.out.print(vo.toString());// 이름 생년월일 나이
								System.out.println();
								/*
								 * System.out.printf("%s\t %s\t %d\n",
								 * vo.getName(), vo.printBirth(), vo.getAge());
								 */
							}
							System.out
									.println("==============================================================");
							System.out.println();

							break;
						case 2: // 회원가입 정보 수정

							ExceptionId EId = new ExceptionId(); // 아이디 예외처리
							ExceptionPw EPw = new ExceptionPw(); // 패스워드 예외처리
							ExceptionName EN = new ExceptionName(); // 이름 예외처리
							ExceptionEmail EE = new ExceptionEmail();// 이메일 예외처리

							int number1 = 0;
							int year,
							month,
							day;
							String id1,
							pw1,
							pw2,
							name1,
							email;
							// Iterator<EnglishAppLoginMember> it =
							// memberList.iterator();
							System.out.println("정보수정할 회원의 id를 치시오");
							String str1 = sc.next();

							while (it.hasNext()) {
								EnglishAppLoginMember vo = it.next();

								if (vo.getId().equals(str1)) {
									number1++;
								}

								if (number1 > 0) {

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

										System.out
												.println("바꾸실 비밀번호 확인 다시한번 입력하십시오");
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
												|| day > now
														.getActualMaximum(Calendar.DATE));

										now.set(year, month - 1, day);

										vo.setBirth(now);

										// System.out.println("나이?");
										int y = yyyy.get(Calendar.YEAR);
										vo.setAge((y - year) + 1);
										System.out.println("나이 : "
												+ vo.getAge());

										System.out
												.printf("\n========================\n");
										System.out.println(vo.getId()
												+ "회원님의 정보가 수정되었습니다");
										System.out.println();

//										if (memberList == null)
//											memberList = new ArrayList<EnglishAppLoginMember>();

										System.out.println(vo.toString());
										System.out
												.println("========================");

										save();
										break;
									} catch (Exception e) {
										// TODO: handle exception
									}

								}

							}
							if (number1 == 0) {
								System.out.println("입력하신 회원 아이디는 존재하지 않습니다");
								System.out.println();
							}

							break;
						case 3: // 블랙리스트 강제 탈퇴

							// Iterator<EnglishAppLoginMember> it =
							// memberList.iterator();
							int number2 = 0;

							System.out.println("삭제시킬 회원 id를 치시오");
							String str2 = sc.next();

							while (it.hasNext()) {

								EnglishAppLoginMember vo = it.next();

								System.out.println();

								if (vo.getId().equals(str2)) {
									number2++;
								}
								if (number2 > 0) {
									char ch1;
									System.out.println("정말로" + vo.getId()
											+ " 회원의 아이디를 탈퇴시키겠습니까?" + " [y/n]");
									try {
										ch1 = (char) System.in.read();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

									if (ch == 'Y' && ch == 'y') {

										try {

											FileInputStream fis = new FileInputStream(
													"d:\\EnglishChat\\join.txt");
											ObjectInputStream ois = new ObjectInputStream(
													fis);
											// text파일 열기

											ealml.getMemberList().remove(vo);
											// 해당배열삭제

											System.out
													.println("해당 회원의 id는 삭제(탈퇴)되었습니다");
											System.out.println();
											save();// text파일로 저장
											break;

										} catch (Exception e) {
											// TODO: handle exception
										}

									} else
										System.out.println(vo.getId()
												+ "님을 삭제하지 않습니다");
								}

							}
							if (number2 == 0) {
								System.out.println("해당 아이디는 존재하지 않습니다");
								System.out.println();
							}

							break;
						case 4:
							System.out.println("관리자 모드 종료");
							loop = false;
							break;
						}

					}
//				} catch (Exception e) {
//					// TODO: handle exception
//					System.out.println("숫자만 입력해주십시오");
//
//				}

			}

		}

	}

	public void gameMenu(EnglishAppLoginMember ealm){
		int ch=0;
		boolean loop = true;

		while(loop){
			Scanner sc = new Scanner(System.in);
			do{
				System.out.println("1.단어깜박이\n2.행맨\n3.타자게임\n"
						+ "4.나가기");
				try {
					ch = sc.nextInt();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}while(ch<1);
			switch(ch){
				case 1:
					new EnglishBlinkControl();
					break;
				case 2:
					new EnglishHangManControl();
					break;
				case 3:
					new EnglishTajaClient(ealm);
					break;
				case 4:
					loop = false;
					break;
				default:
					;
			}
		}
	}
	
	public void manager(){
		
		int ch=0;
		load();//서버에서 회원목록 다운
		while(true){
			Scanner sc = new Scanner(System.in);
			do{
				System.out.println("1.회원가입\n2.로그인\n3.가입 정보 수정\n4.탈퇴\n5.종료\n6.관리자로그인");
				try {
					ch = sc.nextInt();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}while(ch<1);
			switch(ch){
			case 1:
				input();
				save();
				break;
			case 2:
				login();
				break;
			case 3:
				update();
				save();
				break;
			case 4:
				delete();
				break;
			case 5:
				System.out.println("프로그램을 종료합니다");
				sc.close();
				System.exit(0);
				break;
			case 6:				
				admlogin();
				break;
			default:
			}
		}
	}
}

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

//예외처리 끝
//-------------------------------------------------------------------------------
