/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.socialblog.model;

import java.util.Arrays;
import java.util.List;

/**
 * 블로그 내부 사용하는 Select box 리스트 VO
 *
 * @author 이형운
 * @version $Id: SocialBoardCode.java 16246 2011-08-18 04:48:28Z giljae $
 */
public class SocialBoardCode {
	
	/**
	 * 블로그 페이징 정보 객체
	 */
	@SuppressWarnings("unchecked")
	private static final List<SocialCode<Integer>> PAGE_NUM_LIST = Arrays.asList(
			new SocialCode<Integer>(1, "1"),
			new SocialCode<Integer>(3, "3"),
			new SocialCode<Integer>(5, "5"),
			new SocialCode<Integer>(10, "10")
	);
	
	/**
	 * 블로그 File 사이즈  정보 객체
	 */
	@SuppressWarnings("unchecked")
	private static final List<SocialCode<Integer>> FILE_SIZE_LIST = Arrays.asList(
			new SocialCode<Integer>(1, "1"),
			new SocialCode<Integer>(2, "2"),
			new SocialCode<Integer>(5, "5"),
			new SocialCode<Integer>(10, "10"),
			new SocialCode<Integer>(20, "20") 
	);
	
	/**
	 * 블로그 이미지 파일 사이즈 정보 객체
	 */
	@SuppressWarnings("unchecked")
	private static final List<SocialCode<Integer>> IMAGE_FILE_SIZE_LIST = Arrays.asList(
			new SocialCode<Integer>(1000, "1000"),
			new SocialCode<Integer>(2000, "2000"),
			new SocialCode<Integer>(3000, "3000"),
			new SocialCode<Integer>(4000, "4000"),
			new SocialCode<Integer>(5000, "5000") 
	);
	
	/**
	 * 블로그 이미지 넓이 정보 객체
	 */
	@SuppressWarnings("unchecked")
	private static final List<SocialCode<Integer>> IMAGE_WIDTH_LIST = Arrays.asList(
			new SocialCode<Integer>(300, "300"),
			new SocialCode<Integer>(500, "500"),
			new SocialCode<Integer>(800, "800"),
			new SocialCode<Integer>(1024, "1024"),
			new SocialCode<Integer>(2048, "2048") 
	);
	
	/**
	 * 블로그 Yes / No 콤보 객체
	 */
	@SuppressWarnings("unchecked")
	private static final List<SocialCode<Integer>> YES_NO_LIST = Arrays.asList(
			new SocialCode<Integer>(1, "ui.socialpack.socialblog.commoon.code.yes"),
			new SocialCode<Integer>(0, "ui.socialpack.socialblog.commoon.code.no")
	);
	
	/**
	 * 블로그 승인 관련 정보 객체
	 */
	@SuppressWarnings("unchecked")
	private static final List<SocialCode<Integer>> APPROVAL_REJECT_LIST = Arrays.asList(
			new SocialCode<Integer>(1, "ui.socialpack.socialblog.commoon.code.approval"),
			new SocialCode<Integer>(0, "ui.socialpack.socialblog.commoon.code.reject")
	); 
	
	/**
	 * 블로그 사용 비사용 유무 정보 객체
	 */
	@SuppressWarnings("unchecked")
	private static final List<SocialCode<Integer>> USE_UNUSE_LIST = Arrays.asList(
			new SocialCode<Integer>(1, "ui.socialpack.socialblog.commoon.code.use"),
			new SocialCode<Integer>(0, "ui.socialpack.socialblog.commoon.code.unuse")
	);

	/**
	 * 블로그 페이징 정보 객체 조회
	 * @return 블로그 페이징 정보 객체
	 */
	public List<SocialCode<Integer>> getPageNumList() {
		return PAGE_NUM_LIST;
	}

	/**
	 * 블로그 File 사이즈  정보 객체 조회
	 * @return 블로그 File 사이즈  정보 객체
	 */
	public List<SocialCode<Integer>> getFileSizeList() {
		return FILE_SIZE_LIST;
	}

	/**
	 *  블로그 이미지 파일 사이즈 정보 객체 조회
	 * @return 블로그 이미지 파일 사이즈 정보 객체
	 */
	public List<SocialCode<Integer>> getImageFileSizeList() {
		return IMAGE_FILE_SIZE_LIST;
	}
	
	/**
	 * 블로그 이미지 넓이 정보 객체 조회
	 * @return 블로그 이미지 넓이 정보 객체
	 */
	public List<SocialCode<Integer>> getImageWidthList() {
		return IMAGE_WIDTH_LIST;
	}

	/**
	 * 블로그 Yes / No 콤보 객체 조회
	 * @return 블로그 Yes / No 콤보 객체
	 */
	public List<SocialCode<Integer>> getYesNoList() {
		return YES_NO_LIST;
	}  
	
	/**
	 * 블로그 승인 관련 정보 객체 조회
	 * @return 블로그 승인 관련 정보 객체
	 */
	public List<SocialCode<Integer>> getUseUnuseList() {
		return USE_UNUSE_LIST;
	}  
	
	/**
	 * 블로그 승인 관련 정보 객체 조회
	 * @return 블로그 승인 관련 정보 객체
	 */
	public List<SocialCode<Integer>> getApprovalRejectList() {
		return APPROVAL_REJECT_LIST;
	}  
}
