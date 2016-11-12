package com.englishChat.AppLogin;

public interface EnglishAppLoginInterface {

	public void input();//가입
	public void login();//로그인
	public void update();//수정
	public void delete();//탈퇴
	public void save();// 회원아이디 저장하기(직렬화)
	public void load(); //회원아이디 가져오기(역직렬화)
	public void adimlogin();//관리자 로그인
	
	
}
