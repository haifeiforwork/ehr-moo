/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.board.board.service.impl;

import java.util.Properties;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardBatchItemService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.messenger.AtMessengerCommunicator;

/**
 * BoardItemService 구현체 클래스
 */
@Service
public class BoardBatchItemServiceImpl implements BoardBatchItemService {

	private Properties prop = null;		
	private String serverIp = null;
	private String serverPort = null;
	
	@Async
	public void messageSend(String sender, String[] receiver, String contents, String url, String title){
		prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
		serverIp = prop.getProperty("messenger.server.ip");
		serverPort = prop.getProperty("messenger.server.port");
		String tmpUrl = url;
		AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));
		for(int i=0;i<receiver.length;i++){
			tmpUrl = tmpUrl+receiver[i];
			System.out.println(tmpUrl);
			atmc.addMessage(receiver[i], sender, contents.toString(), tmpUrl, title,"smspop");
			tmpUrl = url;
			System.out.println("i="+i);
		}
		atmc.send();
		
	}

}
