/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.message.model;

import java.util.Arrays;
import java.util.List;

/**
 * TODO Javadoc주석작성
 *
 * @author 손정환(samsonic@dbays.com)
 * @version $Id: MessageCode.java 16258 2011-08-18 05:37:22Z giljae $
 */
public class MessageCode {
	
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> PAGE_NUM_LIST = Arrays.asList(
			new Code<Integer>(10, "10"),
			new Code<Integer>(15, "15"),
			new Code<Integer>(20, "20"),
			new Code<Integer>(30, "30"),
			new Code<Integer>(40, "40"),
			new Code<Integer>(50, "50")
	);
	
	public List<Code<Integer>> getPageNumList() {
		return PAGE_NUM_LIST;
	}
	
	/**
	 * 개인별 월별 최대 발송 용량 설정
	 */
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> MAX_MONTH_FILESIZE = Arrays.asList(
			new Code<Integer>(100, "100 MB"),
			new Code<Integer>(1024, "1 GB"),
			new Code<Integer>(10240, "10 GB"),
			new Code<Integer>(0, "etc")
	);
	
	public List<Code<Integer>> getMaxMonthFilesize() {
		return MAX_MONTH_FILESIZE;
	}
	/**
	 * 개인별 보관함 최대  용량 설정
	 */
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> MAX_STORED_FILESIZE = Arrays.asList(
			new Code<Integer>(100, "100 MB"),
			new Code<Integer>(1024, "1 GB"),
			new Code<Integer>(10240, "10 GB"),
			new Code<Integer>(0, "etc")
	);
	
	public List<Code<Integer>> getMaxStoredFilesize() {
		return MAX_STORED_FILESIZE;
	}
	
	/**
	 * 건별 첨부 최대 용량 설정
	 */
	@SuppressWarnings("unchecked")
	private static final List<Code<Integer>> MAX_ATTACH_FILESIZE = Arrays.asList(
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
			new Code<Integer>(50, "50"),
			new Code<Integer>(100, "100"),
			new Code<Integer>(500, "500"),
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
			new Code<Integer>(30, "30"),
			new Code<Integer>(60, "60"),
			new Code<Integer>(90, "90"),
			new Code<Integer>(0, "etc")
	);
	
	public List<Code<Integer>> getKeepDays() {
		return KEEP_DAYS;
	}
}
