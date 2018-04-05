/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.util.file;

import java.io.File;


/**
 * 파일 삭제 처리 스레드
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: FileDeletor.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class FileDeletor extends Thread {
	
	private File file = null;
	
	public final int SECOND_SIZE = 1000;
	
	public final int SLEEP_SIZE = 10;

	public FileDeletor(File file) {
		super();

		this.file = file;
	}

	public void run() {
		//System.out.println("file(" + file.getAbsolutePath() + ") delete before");
		while (file.exists()) {
			if (file.delete()) {
				//System.out.println("file(" + file.getAbsolutePath() + ") delete after");
				break;
			} else {
				try {
					Thread.sleep((long) (SLEEP_SIZE * SECOND_SIZE));
				} catch (Exception e) {
				}
			}
		}
	}
}
