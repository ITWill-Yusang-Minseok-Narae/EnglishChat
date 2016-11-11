package com.englishChat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Random;

public class EnglishTajaManager implements Runnable {
	EnglishChatData ecd;

	public EnglishTajaManager(EnglishChatData ecd) {
		this.ecd = ecd;
	}

	@Override
	public void run() {
		String msg = null;
		// 다른 client에게 접속사실을 알림
		msg = "매니저가 접속했습니다.";
		chatBroadcast(msg);
		int userConnects = ecd.getClients().size();
		msg = String.format("초기 userConnects : %d", userConnects);
		chatBroadcast(msg);				
		while (true) {
			msg = String.format("ecd.getClients().size() : %d", ecd.getClients().size());
			chatBroadcast(msg);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (userConnects != ecd.getClients().size()) {
				if (ecd.getClients().size() < EnglishChatData.tajaUserLimit) {
					userConnects = ecd.getClients().size();
					msg = String.format("%d명 입장후 시작합니다",
							EnglishChatData.tajaUserLimit	- ecd.getClients().size());
					chatBroadcast(msg);
				} else {
					msg = "게임을 시작합니다";
					chatBroadcast(msg);
					allSetGameMode(true);
					for (int i = 0; i < EnglishChatData.tajaQuestion; i++){
						Random rd = new Random();
						// Object[] values =
						// ecd.getDictionary().values().toArray();
						Object[] keys = ecd.getDictionary().keySet().toArray();
						// Object randomValue =
						// values[rd.nextInt(values.length)];
						Object randomKey = keys[rd.nextInt(keys.length)];
						msg = (String) randomKey;
						chatBroadcast(msg);
						boolean userCheckStatus = true;
						while(userCheckStatus){
							Iterator<EnglishChatUser> it = ecd.getClients().listIterator();
							while (it.hasNext()) {
								EnglishChatUser ecu = it.next();
								Iterator<EnglishChatWord> it2 = ecd.getDictionary().get(randomKey).listIterator();
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
					Iterator<EnglishChatUser> it = ecd.getClients().listIterator();
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
		Iterator<EnglishChatUser> it = ecd.getClients().listIterator();
		while (it.hasNext()) {
			EnglishChatUser ecu = it.next();
			ecu.setGameMode(gameMode);
		}
	}

	private void chatBroadcast(String msg) {
		// 나를 제외한 모두
		for (EnglishChatUser ecu : ecd.getClients()) {
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
