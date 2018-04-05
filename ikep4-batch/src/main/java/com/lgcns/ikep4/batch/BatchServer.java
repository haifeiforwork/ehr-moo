/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.support.quartz.service.QuartzJobService;


/**
 * Quartz Batch Server (Standalone) <br/>
 * 
 * @author 주길재
 * @version $Id: BatchServer.java 17055 2011-12-08 03:40:30Z giljae $
 */
public class BatchServer {
	private static final String COMMAND_STOP = "stop";

	private static final String COMMAND_START = "start";

	private static int PORT;

	private static String IP;

	private QuartzJobService quartzJobService = null;
	
	private BatchServer() {
		//Property에서 ip, port 정보를 읽어온다.
		Properties prop = PropertyLoader.loadProperties("batch.properties");
		IP = prop.getProperty("batch.server.ip");
		PORT = Integer.parseInt(prop.getProperty("batch.server.port"));
	}

	private void start() {
		ApplicationContext pathXmlapplicationContext = new ClassPathXmlApplicationContext(new String[] {
				"context-batch-dao.xml", "context-batch-service.xml", "context-batch.xml" });

		this.quartzJobService = (QuartzJobService) pathXmlapplicationContext.getBean("quartzJobServiceImpl");
//		// 스케쥴러 구동 시작
//		this.quartzJobService.start();

		await();
	}

	private void shutdown() {
		// 스케쥴러 구동 중지
		this.quartzJobService.shutdown();
		// Batch Daemon 구동 중지
		System.exit(0);
	}

	protected void await() {
		BufferedReader br = null;
		Socket socket = null;

		try {
			ServerSocket serverSocket = new ServerSocket(PORT); // 소켓 서버를
																		// 준비한다.
			socket = serverSocket.accept(); // 클라이언트를 대기한다.

			// Client 메세지를 받기 위한 스트림
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String readMessage = br.readLine();
			
			//stop 메세지가 넘어오면 배치서버 구동을 중지함.
			if(COMMAND_STOP.equals(readMessage)) {
				shutdown();
			}

		} catch (IOException ie) {
			ie.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				
				if (socket != null) {
					socket.close();
				}
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			if (COMMAND_STOP.equals(args[0])) {
				//Property에서 ip, port 정보를 읽어온다.
				Properties prop = PropertyLoader.loadProperties("batch.properties");
				IP = prop.getProperty("batch.server.ip");
				PORT = Integer.parseInt(prop.getProperty("batch.server.port"));
				
				Socket socket = new Socket(IP, PORT);

				//Server로 shutdown 메시지 송신
				PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
				pw.println(COMMAND_STOP);
				
				pw.close();
				socket.close();
				
			} else if (COMMAND_START.equals(args[0])) {
				BatchServer batchServer = new BatchServer();
				batchServer.start();
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
