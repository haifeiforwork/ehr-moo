/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.collaboration.board.board.service;

import org.springframework.transaction.annotation.Transactional;


/**
 * 게시글 서비스 클래스
 */
@Transactional
public interface BoardBatchItemService {
	
	public void messageSend(String sender, String[] receiver, String contents, String url, String title);

}
