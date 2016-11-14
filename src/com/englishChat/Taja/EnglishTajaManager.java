package com.englishChat.Taja;
//20111108
//Kim Yusang
//bakkus@daum.net
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Random;

import com.englishChat.Dictionary.EnglishDictionaryData;
import com.englishChat.Dictionary.EnglishDictionaryWord;

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
		boolean loop = true;
		while (true) {
//			msg = String.format("ecd.getClients().size() : %d", etd.getClients().size());
//			chatBroadcast(msg);
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
							Iterator<EnglishTajaUser> it = etd.getClients().listIterator();
							while (it.hasNext()) {
								EnglishTajaUser ecu = it.next();
								Iterator<EnglishDictionaryWord> it2 = edd.getDictionary().get(randomKey).listIterator();
								while (it2.hasNext()) {
									EnglishDictionaryWord ecw = it2.next();
									if (ecw.getDdut().equals(ecu.getAnswer())){
										msg = String.format("%s가 맞췄습니다.",	ecu.getName());
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
					Iterator<EnglishTajaUser> it = etd.getClients().listIterator();
					while (it.hasNext()) {
						EnglishTajaUser ecu = it.next();
						msg = String.format("%s : %d 점", ecu.getName(), ecu.getGamePoint());
						chatBroadcast(msg);
					}
					loop = false;
				}
			}
		}
	}

	private void allSetGameMode(boolean gameMode) {
		Iterator<EnglishTajaUser> it = etd.getClients().listIterator();
		while (it.hasNext()) {
			EnglishTajaUser ecu = it.next();
			ecu.setGameMode(gameMode);
		}
	}

	private void chatBroadcast(String msg) {
		// 나를 제외한 모두
		for (EnglishTajaUser ecu : etd.getClients()) {
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
