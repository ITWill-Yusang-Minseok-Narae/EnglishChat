package com.englishChat.Taja;
//20111108
//Kim Yusang
//bakkus@daum.net
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Random;

import com.englishChat.EnglishChatUser;
import com.englishChat.EnglishChatWord;
import com.englishChat.Dictionary.EnglishDictionaryData;

public class EnglishTajaManager implements Runnable {
	EnglishTajaData etd;
	EnglishDictionaryData edd;

	public EnglishTajaManager(EnglishTajaData etd, EnglishDictionaryData edd) {
		this.etd = etd;
		this.edd = edd;
	}

	@Override
	public void run() {
		String msg = null;
		// 다른 client에게 접속사실을 알림
		msg = "매니저가 접속했습니다.";
		chatBroadcast(msg);
		int userConnects = etd.getClients().size();
		msg = String.format("초기 userConnects : %d", userConnects);
		chatBroadcast(msg);				
		while (true) {
			msg = String.format("ecd.getClients().size() : %d", etd.getClients().size());
			chatBroadcast(msg);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (userConnects != etd.getClients().size()) {
				if (etd.getClients().size() < EnglishTajaData.tajaUserLimit) {
					userConnects = etd.getClients().size();
					msg = String.format("%d명 입장후 시작합니다",
							EnglishTajaData.tajaUserLimit	- etd.getClients().size());
					chatBroadcast(msg);
				} else {
					msg = "게임을 시작합니다";
					chatBroadcast(msg);
					allSetGameMode(true);
					for (int i = 0; i < EnglishTajaData.tajaQuestion; i++){
						Random rd = new Random();
						// Object[] values =
						// ecd.getDictionary().values().toArray();
						Object[] keys = edd.getDictionary().keySet().toArray();
						// Object randomValue =
						// values[rd.nextInt(values.length)];
						Object randomKey = keys[rd.nextInt(keys.length)];
						msg = (String) randomKey;
						chatBroadcast(msg);
						boolean userCheckStatus = true;
						while(userCheckStatus){
							Iterator<EnglishChatUser> it = etd.getClients().listIterator();
							while (it.hasNext()) {
								EnglishChatUser ecu = it.next();
								Iterator<EnglishChatWord> it2 = edd.getDictionary().get(randomKey).listIterator();
								while (it2.hasNext()) {
									EnglishChatWord ecw = it2.next();
									if (ecw.getDdut().equals(ecu.getAnswer())){
										msg = String.format("%s가 맞췄습니다.",	ecu.getIp());
										chatBroadcast(msg);
										ecu.setGamePoint(ecu.getGamePoint()+1);
										userCheckStatus = false;
										while (it.hasNext()) 
											it.next();
										break;
									}
									
								}
							}
						}
					}
					msg = "게임 결과";
					chatBroadcast(msg);
					Iterator<EnglishChatUser> it = etd.getClients().listIterator();
					while (it.hasNext()) {
						EnglishChatUser ecu = it.next();
						msg = String.format("%s : %d 점", ecu.getIp(), ecu.getGamePoint());
						chatBroadcast(msg);
					}
				}
			}
		}
	}

	private void allSetGameMode(boolean gameMode) {
		Iterator<EnglishChatUser> it = etd.getClients().listIterator();
		while (it.hasNext()) {
			EnglishChatUser ecu = it.next();
			ecu.setGameMode(gameMode);
		}
	}

	private void chatBroadcast(String msg) {
		// 나를 제외한 모두
		for (EnglishChatUser ecu : etd.getClients()) {
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(ecu.getSc().getOutputStream(), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pw.println(msg);
		}
		System.out.println(msg);
	}
}
