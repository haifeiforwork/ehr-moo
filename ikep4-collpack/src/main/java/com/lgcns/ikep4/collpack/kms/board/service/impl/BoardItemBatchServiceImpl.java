/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.kms.board.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.kms.board.service.BoardItemBatchService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.messenger.AtMessengerCommunicator;

/**
 * BoardItemService 구현체 클래스
 */
@Service
public class BoardItemBatchServiceImpl implements BoardItemBatchService {

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
			atmc.addMessage(receiver[i], sender, contents.toString(), tmpUrl, title,"smspop");
			tmpUrl = url;
		}
		atmc.send();
		
	}
	
	
	@Async
	public void messageSendKms(String sender, List<HashMap<String, String>> keywordList, String contents, String url, String title){
		prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
		serverIp = prop.getProperty("messenger.server.ip");
		serverPort = prop.getProperty("messenger.server.port");
		String tmpUrl = url;
		String tmpTitle = "";
		AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));
		for(int i = 0; i < keywordList.size(); i++){
			if(title.contains(keywordList.get(i).get("KEYWORD"))){//&&!title.contains("한솔")&&!title.contains("한국")&&!title.contains("무림")){
				
				tmpTitle 	= keywordList.get(i).get("KEYWORD")+"관련 관심지식이 등록되었습니다.";
				tmpUrl = tmpUrl+keywordList.get(i).get("USER_ID");
				atmc.addMessage(keywordList.get(i).get("USER_ID"), sender, contents.toString(), tmpUrl, tmpTitle,"smspop");
				
				tmpUrl = url;
			}
		}
		atmc.send();
		
	}

}
