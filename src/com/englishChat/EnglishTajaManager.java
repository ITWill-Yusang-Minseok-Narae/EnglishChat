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
		// �ٸ� client���� ���ӻ���� �˸�
		msg = "�Ŵ����� �����߽��ϴ�.";
		chatBroadcast(msg);
		int userConnects = ecd.getClients().size();
		msg = String.format("�ʱ� userConnects : %d", userConnects);
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
					msg = String.format("%d�� ������ �����մϴ�",
							EnglishChatData.tajaUserLimit	- ecd.getClients().size());
					chatBroadcast(msg);
				} else {
					msg = "������ �����մϴ�";
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
										msg = String.format("%s�� ������ϴ�.",	ecu.getIp());
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
					msg = "���� ���";
					chatBroadcast(msg);
					Iterator<EnglishChatUser> it = ecd.getClients().listIterator();
					while (it.hasNext()) {
						EnglishChatUser ecu = it.next();
						msg = String.format("%s : %d ��", ecu.getIp(), ecu.getGamePoint());
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
		// ���� ������ ���
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
