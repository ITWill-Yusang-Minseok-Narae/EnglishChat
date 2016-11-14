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
	Calendar now = Calendar.getInstance(); // ������ϰ˼���
	Calendar yyyy = Calendar.getInstance(); // ���̿�


	private final int appLoginServerPort = 5551;
	private final String appLoginServerIP = "192.168.16.22";
	
	EnglishAppLoginMemberList ealml = null;
	EnglishAppLoginMember ealm = null;

	public EnglishAppLoginControl() {// �⺻������
		ealml = new EnglishAppLoginMemberList();
		manager();
	}

	// ȸ������-----------------------------------------------------------------------------
	@Override
	public void input() {

		ExceptionId EId = new ExceptionId(); // ���̵� ����ó��
		ExceptionPw EPw = new ExceptionPw(); // �н����� ����ó��
		ExceptionName EN = new ExceptionName(); // �̸� ����ó��
		ExceptionEmail EE = new ExceptionEmail();// �̸��� ����ó��

		EnglishAppLoginMember vo = new EnglishAppLoginMember();
		Iterator<EnglishAppLoginMember> it = ealml.getMemberList().iterator();
		int year, month, day;
		String id, pw1, pw2, name, email;
		int number = 0;

		while (true) {

			// Iterator<EnglishAppLoginMember> it = memberList.iterator();
			while (it.hasNext()) {

				vo = it.next();
				System.out.println("���̵�?");
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
					System.out.println("�ߺ��Ǵ� ���̵� �����մϴ� " + "�ٸ����̵� �Է����ֽʽÿ�");

			}
			while (true) {
				System.out.println("��й�ȣ�� �Է��Ͻʽÿ�");
				vo.setPw1(sc.next());
				pw1 = vo.getPw1();

				System.out.println("��й�ȣ Ȯ�� �ٽ��ѹ� �Է��Ͻʽÿ�");
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
				System.out.println("�̸�?");

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
				System.out.println("�̸���?");
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
					System.out.println("����?0000");
					year = sc.nextInt();
				} while (year < 1900);
				do {
					System.out.println("����?00");
					month = sc.nextInt();

				} while (month < 1 || month > 12);
				now.set(year, month - 1, 1);

				do {
					System.out.println("����?");
					day = sc.nextInt();
				} while (day < 1 || day > now.getActualMaximum(Calendar.DATE));

				now.set(year, month - 1, day);

				vo.setBirth(now);

				// System.out.println("����?");
				int y = yyyy.get(Calendar.YEAR);
				vo.setAge((y - year) + 1);
				System.out.println("���� : " + vo.getAge());

				System.out.printf("\n========================\n�����մϴ�!\n\n");
				System.out.println("������ �Ϸ�Ǿ����ϴ�");

//				if (memberList == null)
//					memberList = new ArrayList<EnglishAppLoginMember>();
				System.out.println("ȯ���մϴ�!  " + vo.getId() + "  ��!");
				System.out.println(vo.toString());
				System.out.println("========================");
				System.out.println();

				ealml.getMemberList().add(vo);

			} catch (Exception e) {
				System.out.println(e.toString());
			}

		}

	}

	// �α���--------------------------------------------------------------------------------

	@Override
	public void login() {
		int number = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("�α��� �� id�� ġ�ÿ�");
		String id = sc.next();

		Iterator<EnglishAppLoginMember> it = ealml.getMemberList().iterator();

		while (it.hasNext()) {

			EnglishAppLoginMember ealm = it.next();

			if (ealm.getId().equals(id)) {
				number++;
			}

			if (number > 0) {
				System.out.println("�α����� id�� ��й�ȣ�� ġ�ÿ�");
				String pw = sc.next();

				if (ealm.getPw1().equals(pw)) {

					System.out.println("�α��� �Ϸ�");
					// ���⿡ �޴���� ���°�
					// 1������ �ܾ���
					// 2���
					// 3����߱� Ÿ�ڰ���
					// 4���� �˻�
					// 5(������ �ܾ���)���� ������ My �ܾ���
					gameMenu(ealm);
					return;

				} else {
					System.out.println("��й�ȣ�� �����ʽ��ϴ�");
					System.out.println();
					break;
				}
			}
		}
		if (number == 0) {
			System.out.println("�Է��Ͻ� ���̵�� ���������ʽ��ϴ�");
			System.out.println();

		}

	}

	// ȸ����������------------------------------------------------------------------------

	@Override
	public void update() {
		ExceptionId EId = new ExceptionId(); // ���̵� ����ó��
		ExceptionPw EPw = new ExceptionPw(); // �н����� ����ó��
		ExceptionName EN = new ExceptionName(); // �̸� ����ó��
		ExceptionEmail EE = new ExceptionEmail();// �̸��� ����ó��

		// EnglishAppLoginVo vo = new EnglishAppLoginVo();
		int number = 0;
		int year, month, day;
		String id1, pw1, pw2, name1, email;
		Iterator<EnglishAppLoginMember> it = ealml.getMemberList().iterator();
		System.out.println("�α��� �� id�� ġ�ÿ�");
		String str = sc.next();

		while (it.hasNext()) {
			EnglishAppLoginMember vo = it.next();

			if (vo.getId().equals(str)) {
				number++;
			}

			if (number > 0) {
				System.out.println("������ id�� ��й�ȣ�� ġ�ÿ�");
				String pw = sc.next();

				if (vo.getPw1().equals(pw)) {

					System.out.println("�α��� �Ϸ�");

					System.out.println();
					System.out.println("ȸ������ ������ �����մϴ�");
					System.out.println();

					// ���̵� �״�� ������
					id1 = vo.getId();
					vo.setId(id1);

					// ������ ��й�ȣ
					while (true) {
						System.out.println("�ٲٽ� ��й�ȣ�� �Է��Ͻʽÿ�");
						vo.setPw1(sc.next());
						pw1 = vo.getPw1();

						System.out.println("�ٲٽ� ��й�ȣ Ȯ�� �ٽ��ѹ� �Է��Ͻʽÿ�");
						vo.setPw2(sc.next());
						pw2 = vo.getPw2();
						try {

							EPw.inputFormat(pw1, pw2);
							break;
						} catch (Exception e) {
							System.out.println(e.toString());
						}

					}
					// �̸� �״�� ������
					name1 = vo.getName();
					vo.setName(name1);

					// ������ �̸���

					while (true) {
						System.out.println("�̸���?");
						vo.setEmail(sc.next());
						email = vo.getEmail();

						try {
							EE.inputFormat(email);
							break;
						} catch (Exception e) {
							System.out.println(e.toString());
						}

					}

					// ������ ����+����

					try {
						do {
							System.out.println("����?0000");
							year = sc.nextInt();
						} while (year < 1900);
						do {
							System.out.println("����?00");
							month = sc.nextInt();

						} while (month < 1 || month > 12);
						now.set(year, month - 1, 1);

						do {
							System.out.println("����?");
							day = sc.nextInt();
						} while (day < 1
								|| day > now.getActualMaximum(Calendar.DATE));

						now.set(year, month - 1, day);

						vo.setBirth(now);

						// System.out.println("����?");
						int y = yyyy.get(Calendar.YEAR);
						vo.setAge((y - year) + 1);
						System.out.println("���� : " + vo.getAge());

						System.out
								.printf("\n========================\n�����մϴ�!\n\n");
						System.out.println("ȸ������ ������ �����Ǿ����ϴ�");
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
					System.out.println("��й�ȣ�� ���� �ʽ��ϴ�");
				System.out.println();
				break;

			}

		}
		if (number == 0) {
			System.out.println("�Է��Ͻ� ���̵�� �������� �ʽ��ϴ�");
			System.out.println();
		}

	}

	// ȸ��Ż��-------------------------------------------------------------------------------
	@Override
	public void delete() {
		Iterator<EnglishAppLoginMember> it = ealml.getMemberList().iterator();
		int number = 0;

		System.out.println("���� id�� ġ�ÿ�");
		String str = sc.next();

		while (it.hasNext()) {

			EnglishAppLoginMember vo = it.next();

			System.out.println();

			if (vo.getId().equals(str)) {
				number++;
			}
			if (number > 0) {

				System.out.println("������ id�� ����� ġ�ÿ�");
				String pwstr = sc.next();
				if (vo.getPw1().equals(pwstr)) {

					try {
						FileInputStream fis = new FileInputStream(
								"d:\\EnglishChat\\join.txt");
						ObjectInputStream ois = new ObjectInputStream(fis);
						// text���� ����

						ealml.getMemberList().remove(vo);
						// �ش�迭����

					} catch (Exception e) {
						// TODO: handle exception
					}
					System.out.println("����(Ż��)�Ǿ����ϴ�");
					System.out.println();
					save();// text���Ϸ� ����
					break;

				}

			}

		}
		if (number == 0) {
			System.out.println("�ش� ���̵�� �������� �ʽ��ϴ�");
			System.out.println();
		}

	}

	// ȸ������ �ؽ�Ʈ���Ͽ��� �о��--------------------------------------------------------
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
			System.out.println("������ �׾��ִ�...");
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
		
		System.out.println("����Ʈ�б�Ϸ�");
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
	

	// ȸ������ �ؽ�Ʈ���Ϸ�
	// ����-------------------------------------------------------------

	public void save() {
		class save {
			private static final long serialVersionUID = -8029413848722483074L;
			private Socket sk = null;
			private final int port = 5555;
			private String serverIP = "127.0.0.1";

			public void save() {
				System.out.println("������ ����");
				connect();
			}

			public void connect() {
				OutputStream os = null;
				ObjectOutputStream oos = null;

				System.out.print("������ ������ ���ε� �մϴ�");

				try {
					sk = new Socket(serverIP, port);
				} catch (Exception e) {
					System.out.println("������ �׾��ִ�...");
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
				 * readerThread.start();//Readerȣ��
				 * writerThread.start();//Writerȣ��
				 */

			}

		}

	}

	// ���------------------------------------------------------------------------------
	@Override
	public void admlogin() {// �ӽ÷� �������ϴ°� ���⿡ ����

		Iterator<EnglishAppLoginMember> it = ealml.getMemberList().iterator();
		int number, ch = 0;
		String admin = "admin";
		String adminPw = "adminpw";

		System.out.println("������ id�� ġ�ÿ�");
		String str = sc.next();

		if (admin.equals(str)) {

			System.out.println("������ ��й�ȣ�� ġ�ÿ�");
			String strpw = sc.next();

			if (adminPw.equals(strpw)) {

//				try {
					boolean loop = true;
					while (loop) {

						do {
							System.out.println("1.ȸ�� ��ü ��ȸ \n2.ȸ������ ���� ����\n"
									+ "3.������Ʈ ���� Ż�� \n4.�����ڸ�� ����");

							ch = sc.nextInt();

						} while (ch < 1);

						switch (ch) {

						case 1: // ȸ�� ��ü ��ȸ

							load();
							System.out
									.println("==============================================================");
							System.out.printf("���̵�\t    ��й�ȣ\t\t "
									+ "�̸���\t\t�̸�\t "
									+ "���� - �� - ��  ����\n");

							while (it.hasNext()) {

								EnglishAppLoginMember vo = it.next();
								System.out.printf("%s   ��   %s   ��   %s   ��",
										vo.getId(), vo.getPw1(), vo.getEmail());
								System.out.print(vo.toString());// �̸� ������� ����
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
						case 2: // ȸ������ ���� ����

							ExceptionId EId = new ExceptionId(); // ���̵� ����ó��
							ExceptionPw EPw = new ExceptionPw(); // �н����� ����ó��
							ExceptionName EN = new ExceptionName(); // �̸� ����ó��
							ExceptionEmail EE = new ExceptionEmail();// �̸��� ����ó��

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
							System.out.println("���������� ȸ���� id�� ġ�ÿ�");
							String str1 = sc.next();

							while (it.hasNext()) {
								EnglishAppLoginMember vo = it.next();

								if (vo.getId().equals(str1)) {
									number1++;
								}

								if (number1 > 0) {

									System.out.println();
									System.out.println("ȸ������ ������ �����մϴ�");
									System.out.println();

									// ���̵� �״�� ������
									id1 = vo.getId();
									vo.setId(id1);

									// ������ ��й�ȣ
									while (true) {
										System.out.println("�ٲٽ� ��й�ȣ�� �Է��Ͻʽÿ�");
										vo.setPw1(sc.next());
										pw1 = vo.getPw1();

										System.out
												.println("�ٲٽ� ��й�ȣ Ȯ�� �ٽ��ѹ� �Է��Ͻʽÿ�");
										vo.setPw2(sc.next());
										pw2 = vo.getPw2();
										try {

											EPw.inputFormat(pw1, pw2);
											break;
										} catch (Exception e) {
											System.out.println(e.toString());
										}

									}
									// �̸� �״�� ������
									name1 = vo.getName();
									vo.setName(name1);

									// ������ �̸���

									while (true) {
										System.out.println("�̸���?");
										vo.setEmail(sc.next());
										email = vo.getEmail();

										try {
											EE.inputFormat(email);
											break;
										} catch (Exception e) {
											System.out.println(e.toString());
										}

									}

									// ������ ����+����

									try {
										do {
											System.out.println("����?0000");
											year = sc.nextInt();
										} while (year < 1900);
										do {
											System.out.println("����?00");
											month = sc.nextInt();

										} while (month < 1 || month > 12);
										now.set(year, month - 1, 1);

										do {
											System.out.println("����?");
											day = sc.nextInt();
										} while (day < 1
												|| day > now
														.getActualMaximum(Calendar.DATE));

										now.set(year, month - 1, day);

										vo.setBirth(now);

										// System.out.println("����?");
										int y = yyyy.get(Calendar.YEAR);
										vo.setAge((y - year) + 1);
										System.out.println("���� : "
												+ vo.getAge());

										System.out
												.printf("\n========================\n");
										System.out.println(vo.getId()
												+ "ȸ������ ������ �����Ǿ����ϴ�");
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
								System.out.println("�Է��Ͻ� ȸ�� ���̵�� �������� �ʽ��ϴ�");
								System.out.println();
							}

							break;
						case 3: // ������Ʈ ���� Ż��

							// Iterator<EnglishAppLoginMember> it =
							// memberList.iterator();
							int number2 = 0;

							System.out.println("������ų ȸ�� id�� ġ�ÿ�");
							String str2 = sc.next();

							while (it.hasNext()) {

								EnglishAppLoginMember vo = it.next();

								System.out.println();

								if (vo.getId().equals(str2)) {
									number2++;
								}
								if (number2 > 0) {
									char ch1;
									System.out.println("������" + vo.getId()
											+ " ȸ���� ���̵� Ż���Ű�ڽ��ϱ�?" + " [y/n]");
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
											// text���� ����

											ealml.getMemberList().remove(vo);
											// �ش�迭����

											System.out
													.println("�ش� ȸ���� id�� ����(Ż��)�Ǿ����ϴ�");
											System.out.println();
											save();// text���Ϸ� ����
											break;

										} catch (Exception e) {
											// TODO: handle exception
										}

									} else
										System.out.println(vo.getId()
												+ "���� �������� �ʽ��ϴ�");
								}

							}
							if (number2 == 0) {
								System.out.println("�ش� ���̵�� �������� �ʽ��ϴ�");
								System.out.println();
							}

							break;
						case 4:
							System.out.println("������ ��� ����");
							loop = false;
							break;
						}

					}
//				} catch (Exception e) {
//					// TODO: handle exception
//					System.out.println("���ڸ� �Է����ֽʽÿ�");
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
				System.out.println("1.�ܾ������\n2.���\n3.Ÿ�ڰ���\n"
						+ "4.������");
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
		load();//�������� ȸ����� �ٿ�
		while(true){
			Scanner sc = new Scanner(System.in);
			do{
				System.out.println("1.ȸ������\n2.�α���\n3.���� ���� ����\n4.Ż��\n5.����\n6.�����ڷα���");
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
				System.out.println("���α׷��� �����մϴ�");
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

//����ó��------------------------------------------------------------------------------

class ExceptionId {// id����ó��

	public void inputFormat(String id) throws Exception {

		if (id.length() < 6 || id.length() > 15)
			throw new Exception("id�� �ּ� 6���� ~ �ִ�15���ڿ��� �մϴ�");

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
			throw new Exception("id�� ������ ���ڸ� ȥ���ؾ� �մϴ�");
		}

	}

}

class ExceptionPw {// �������ó��
	public void inputFormat(String pw1, String pw2) throws Exception {

		if (!pw1.equals(pw2))
			throw new Exception("��й�ȣ�� ��й�ȣ Ȯ���� �����ʽ��ϴ�");

		if (pw1.length() < 8 || pw2.length() > 15)
			throw new Exception("��й�ȣ�� �ּ� 8����~ �ִ� 15���ڷ� �����ؾ� �մϴ�");
	}
}

class ExceptionName {// �̸�����ó��
	public void inputFormat(String name) throws Exception {

		int eng = 0;
		int num = 0;

		if (name.length() < 2 || name.length() > 5)
			throw new Exception("�̸���(������) �ּ� 2����~5���ڷ� �Է��ؾ� �մϴ�");

		for (int i = 0; i < name.length(); i++) {
			char ch = name.charAt(i);
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
				eng++;
			} else if (ch >= '0' && ch <= '9')
				num++;
		}
		if (eng > 0 || num > 0)
			throw new Exception("�ѱ��̸����� �Է��ؾ� �մϴ�");

	}

}

class ExceptionEmail {// �̸��� ����
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
			throw new Exception("�̸��� ������ �ƴմϴ�");
		}
		/*
		 * if(temp[0].length()<8){ throw new Exception("�̸��� ������ �ƴմϴ�"); }
		 * if(temp[1].length()<2){ throw new Exception("�̸��� ������ �ƴմϴ�"); }
		 */

	}

}

//����ó�� ��
//-------------------------------------------------------------------------------
