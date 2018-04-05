/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.seamless.model;
import java.util.Arrays;
import java.util.List;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: SeamlessmessageCode.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class SeamlessmessageCode {
	
	/**
	 * 메세지 타입
	 */
	@SuppressWarnings("unchecked")
	private static final List<Code<String>> MESSAGE_TYPE = Arrays.asList(
			new Code<String>("Mail",    "mail"),
			new Code<String>("Message", "message"),
			new Code<String>("SMS",     "sms")
	);
	
	public List<Code<String>> getMessageType() {
		return MESSAGE_TYPE;
	}
	
	/**
	 * 건별 첨부 최대 용량 설정
	 */
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> MAX_ATTACH_FILESIZE = Arrays.asList(
			new Code<Integer>(null, "unlimited"),
			new Code<Integer>(10, "10 MB"),
			new Code<Integer>(100, "100 MB"),
			new Code<Integer>(1024, "1 GB"),
			new Code<Integer>(0, "etc")
	);
	
	public List<Code<Integer>> getMaxAttachFilesize() {
		return MAX_ATTACH_FILESIZE;
	}
	
	/**
	 * 건별 최대 수신자수 설정
	 */
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> MAX_RECEIVER_COUNT = Arrays.asList(
			new Code<Integer>(null, "unlimited"),
			new Code<Integer>(100, "100"),
			new Code<Integer>(1000, "1000"),
			new Code<Integer>(10000, "10000"),
			new Code<Integer>(0, "etc")
	);
	
	public List<Code<Integer>> getMaxReceiverCount() {
		return MAX_RECEIVER_COUNT;
	}
	
	/**
	 * 만료 기한 설정
	 */
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> KEEP_DAYS = Arrays.asList(
			new Code<Integer>(null, "unlimited"),
			new Code<Integer>(30, "30"),
			new Code<Integer>(60, "60"),
			new Code<Integer>(90, "90"),
			new Code<Integer>(0, "etc")
	);
	
	public List<Code<Integer>> getKeepDays() {
		return KEEP_DAYS;
	}
	
	/**
	 * imap mail 동기화 가져오기 최대 건수 설정
	 */
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> MAX_IMAP_COUNT = Arrays.asList(
			new Code<Integer>(null, "unlimited"),
			new Code<Integer>(100, "100"),
			new Code<Integer>(500, "500"),
			new Code<Integer>(1000, "1000"),
			new Code<Integer>(0, "etc")
	);
	
	public List<Code<Integer>> getMaxImapCount() {
		return MAX_IMAP_COUNT;
	}
}
