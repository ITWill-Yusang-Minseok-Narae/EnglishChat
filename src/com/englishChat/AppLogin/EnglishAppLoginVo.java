package com.englishChat.AppLogin;

import java.io.Serializable;
import java.util.Calendar;

public class EnglishAppLoginVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String pw1;
	private String pw2;
	private String name;
	private Calendar birth;
	private int age;
	
	private String email;
	private int tel;
	
	
	
	public Calendar getBirth() {
		return birth;
	}
	public void setBirth(Calendar birth) {
		this.birth = birth;
	}
	public String printBirth() {
		return String.format("%4s - %2s - %2s",
				birth.get(Calendar.YEAR),birth.get(Calendar.MONTH)+1,birth.get(Calendar.DATE));
	}
	
	
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	
	
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getPw1() {
		return pw1;
	}
	public void setPw1(String pw1) {
		this.pw1 = pw1;
	}
	public String getPw2() {
		return pw2;
	}
	public void setPw2(String pw2) {
		this.pw2 = pw2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getTel() {
		return tel;
	}
	public void setTel(int tel) {
		this.tel = tel;
	}
	
	@Override
	public String toString() {

		return String.format("%s %s คำ%d", getName(), printBirth(),
					getAge());
	}
	

}
