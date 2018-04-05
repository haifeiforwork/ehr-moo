/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.fileupload.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.fileupload.model.FileCheck;


/**
 * 파일권한체크 Dao
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: FileCheckDao.java 16273 2011-08-18 07:07:47Z giljae $
 */
public interface FileCheckDao extends GenericDao<FileCheck, String> {

	/**
	 * 파일권한체크 (Board)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public FileCheck checkBoard(FileCheck fileCheck);

	/**
	 * 파일권한체크 (Planner)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public FileCheck checkPlanner(FileCheck fileCheck);

	/**
	 * 파일권한체크 (SocialBlog)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public FileCheck checkSocialBlog(FileCheck fileCheck);

	/**
	 * 파일권한체크 (MicroBlog)
	 * 
	 * @param fileCheck
	 * @return 권한이 있는지 여부
	 */
	public boolean checkMicroBlog(FileCheck fileCheck);

	/**
	 * 파일권한체크 (QA)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public FileCheck checkQA(FileCheck fileCheck);

	/**
	 * 파일권한체크 (Forum)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public FileCheck checkForum(FileCheck fileCheck);

	/**
	 * 파일권한체크 (QA)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public boolean checkTodo(FileCheck fileCheck);

	/**
	 * 파일권한체크 (Message)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public boolean checkMessage(FileCheck fileCheck);

	/**
	 * 파일권한체크 (WorkManual)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public FileCheck checkWorkManual(FileCheck fileCheck);

	/**
	 * 파일권한체크 (WhosWho)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public FileCheck checkWhosWho(FileCheck fileCheck);

	/**
	 * 파일권한체크 (CorporateVocabulary)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public FileCheck checkCorporateVocabulary(FileCheck fileCheck);

	/**
	 * 파일권한체크 (Survey)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public FileCheck checkSurvey(FileCheck fileCheck);

	/**
	 * 파일권한체크 (Ideation)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public FileCheck checkIdeation(FileCheck fileCheck);

	/**
	 * 파일권한체크 (Workspace)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public FileCheck checkWorkspace(FileCheck fileCheck);

	/**
	 * 파일권한체크 (Workspace board)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public boolean checkWorkspaceBoard(FileCheck fileCheck);

	/**
	 * 파일권한체크 (Workspace weekly)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public boolean checkWorkspaceWeekly(FileCheck fileCheck);

	/**
	 * 파일권한체크 (Workspace announce)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public boolean checkWorkspaceAnnounce(FileCheck fileCheck);
	
	/**
	 * 파일권한체크 (KMS)
	 * 
	 * @param fileCheck
	 * @return
	 */
	public FileCheck checkKms(FileCheck fileCheck);

	/**
	 * 게시물 ID로 게시판 ID로 가져오는
	 * 
	 * @param itemId
	 * @return
	 */
	public String getBoardId(String itemId);

	/**
	 * 게시물의 작성자를 가져오는
	 * 
	 * @return 작성자 ID
	 */
	public String getItemAuthor(String itemId);

}
