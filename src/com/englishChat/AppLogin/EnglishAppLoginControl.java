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

// ����ó�� ��
// -------------------------------------------------------------------------------

public class EnglishAppLoginControl implements EnglishAppLoginInterface {

	public EnglishAppLoginControl() {// �⺻��������

		this.list = new ArrayList<EnglishAppLoginVo>();

	}

	Scanner sc = new Scanner(System.in);
	Calendar now = Calendar.getInstance(); // ������ϰ˼���
	Calendar yyyy = Calendar.getInstance(); // ���̿�

	private ArrayList<EnglishAppLoginVo> list;

	// ȸ������-----------------------------------------------------------------------------
	@Override
	public void input() {

		ExceptionId EId = new ExceptionId(); // ���̵� ����ó��
		ExceptionPw EPw = new ExceptionPw(); // �н����� ����ó��
		ExceptionName EN = new ExceptionName(); // �̸� ����ó��
		ExceptionEmail EE = new ExceptionEmail();// �̸��� ����ó��

		EnglishAppLoginVo vo = new EnglishAppLoginVo();

		int year, month, day;
		String id, pw1, pw2, name, email;

		while (true) {

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

			if (list == null)
				list = new ArrayList<EnglishAppLoginVo>();
			System.out.println("ȯ���մϴ�!  " + vo.getId() + "  ��!");
			System.out.println(vo.toString());
			System.out.println("========================");
			System.out.println();

			list.add(vo);

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// �α���--------------------------------------------------------------------------------

	@Override
	public void login() {

		System.out.println("�α��� �� id�� ġ�ÿ�");
		String str = sc.next();

		Iterator<EnglishAppLoginVo> it = list.iterator();

		while (it.hasNext()) {

			EnglishAppLoginVo vo = it.next();
			if (vo.getId().equals(str)) {
				System.out.println("��й�ȣ�� ġ�ÿ�");
				String pw = sc.next();

				if (vo.getPw1().equals(pw)) {

					System.out.println("�α��� �Ϸ�");

					// ���⿡ �޴���� ���°�
					// 1������ �ܾ���
					// 2���
					// 3����߱� Ÿ�ڰ���
					// 4���� �˻�
					// 5(������ �ܾ���)���� ������ My �ܾ���

				} else {
					System.out.println("��й�ȣ�� �����ʽ��ϴ�");
					System.out.println();
					break;
				}
			} else {
				System.out.println("�Է��Ͻ� ���̵�� ���������ʽ��ϴ�");
				System.out.println();
				break;
			}
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

		int year, month, day;
		String id1, pw1, pw2, name1, email;

		System.out.println("�α��� �� id�� ġ�ÿ�");
		String str = sc.next();

		Iterator<EnglishAppLoginVo> it = list.iterator();

		while (it.hasNext()) {

			EnglishAppLoginVo vo = it.next();
			if (vo.getId().equals(str)) {
				System.out.println("��й�ȣ�� ġ�ÿ�");
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
					System.out.println("��й�ȣ�� �����ʽ��ϴ�");
					System.out.println();
					break;
				}
			} else {
				System.out.println("�Է��Ͻ� ���̵�� ���������ʽ��ϴ�");
				System.out.println();
				break;
			}
		}

	}

	// ȸ��Ż��-------------------------------------------------------------------------------
	@Override
	public void delete() {
		Iterator<EnglishAppLoginVo> it = list.iterator();
		//EnglishAppLoginVo vo = it.next();

//		
//		while (it.hasNext()) {
//
//			EnglishAppLoginVo vo = it.next();
//			System.out.printf("%s   ��   %s   ��   %s   ��", vo.getId(),
//					vo.getPw1(), vo.getEmail());
//			System.out.print(vo.toString());// �̸� ������� ����
//			System.out.println();
//			
//		}
		System.out.println("���� id�� ġ�ÿ�");
		String str = sc.next();
		
		while(it.hasNext()){
			EnglishAppLoginVo vo = it.next();
			
			if(vo.getId() .equals(str)){
				
				
				System.out.println("dddddddd" );
				break;
			}else 
				System.out.println("�ش� ���̵�� �����ϴ�");
			break;
		}
		
		
		
		
		
		/*while (it.hasNext()) {

			EnglishAppLoginVo vo = it.next();
			System.out.println(vo.getId());
			
			

			if (vo.getId().equals(str)) {
				System.out.println("�ش� ���̵� �����մϴ�");
				System.out.println();

				System.out.println("��й�ȣ�� ġ�ÿ�");
				String pw = sc.next();

				if (vo.getPw1().equals(pw)) {

					try {
						FileInputStream fis = new FileInputStream(
								"d:\\EnglishChat\\join.txt");
						ObjectInputStream ois = new ObjectInputStream(fis);
						// text���� ����

						list.remove(vo);
						// �ش�迭����
						
						
					} catch (Exception e) {
						// TODO: handle exception
					}

					System.out.println("����(Ż��)�Ǿ����ϴ�");
					System.out.println();
					save();// text���Ϸ� ����
					break;

				} else {
					System.out.println("��й�ȣ�� �����ʽ��ϴ�");
					System.out.println();
					break;
				}

			} else {
				System.out.println("�Է��Ͻ� ���̵�� �������� �ʽ��ϴ�");
				break;
			}

		}*/

	}

	// ȸ������ �ؽ�Ʈ���Ϸ�
	// ����-------------------------------------------------------------

	@Override
	public void save() {// ����ȭ

		try {

			FileOutputStream fos = new FileOutputStream(
					"d:\\EnglishChat\\join.txt");

			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(list);

			oos.close();
			fos.close();// ����ȭ ��

			// ���---------------------------------------

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// ȸ������ �ؽ�Ʈ���Ͽ��� �о��--------------------------------------------------------

	@Override
	public void load() {// ������ȭ

		// private String path = System.getProperty("D:");
		// private File f = new File(path, "\\EnglishChat\\join.txt");

		try {
			FileInputStream fis = new FileInputStream(
					"d:\\EnglishChat\\join.txt");

			ObjectInputStream ois = new ObjectInputStream(fis);

			System.out.println("ȸ������ ������ �о�鿴���ϴ�.");
			System.out.println();
			try {
				list = (ArrayList<EnglishAppLoginVo>) ois.readObject();

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("ȸ������ ������ �������� �ʽ��ϴ�");
				System.out.println();
			}

			ois.close();
			fis.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void adimlogin() {// �ӽ÷� �������ϴ°� ���⿡ ����

		Iterator<EnglishAppLoginVo> it = list.iterator();

		System.out
				.println("==============================================================");
		System.out.printf("���̵�\t\t ��й�ȣ\t\t �̸���\t\t  �̸�\t "
				+ "���� - �� - ��  ����\n");

		while (it.hasNext()) {

			EnglishAppLoginVo vo = it.next();
			System.out.printf("%s   ��   %s   ��   %s   ��", vo.getId(),
					vo.getPw1(), vo.getEmail());
			System.out.print(vo.toString());// �̸� ������� ����
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
