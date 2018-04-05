package com.lgcns.ikep4.support.fileupload.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.fileupload.dao.FileCheckDao;
import com.lgcns.ikep4.support.fileupload.model.FileCheck;


/**
 * 파일권한체크 저장 DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: FileCheckDaoImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Repository
public class FileCheckDaoImpl extends GenericDaoSqlmap<FileCheck, String> implements FileCheckDao {

	/**
	 * 파일권한체크 쿼리 네임스페이스
	 */
	private static final String NAMESPACE = "support.fileupload.filecheck.";

	public FileCheck get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public String create(FileCheck object) {
		// TODO Auto-generated method stub
		return null;
	}

	public void update(FileCheck object) {
		// TODO Auto-generated method stub

	}

	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	/**
	 * 파일권한체크 (Board)
	 */
	public FileCheck checkBoard(FileCheck fileCheck) {
		return (FileCheck) this.sqlSelectForObject(NAMESPACE + "checkBoard", fileCheck);
	}

	/**
	 * 파일권한체크 (Planner)
	 */
	public FileCheck checkPlanner(FileCheck fileCheck) {
		return (FileCheck) this.sqlSelectForObject(NAMESPACE + "checkPlanner", fileCheck);
	}

	/**
	 * 파일권한체크 (SocialBlog)
	 */
	public FileCheck checkSocialBlog(FileCheck fileCheck) {
		return (FileCheck) this.sqlSelectForObject(NAMESPACE + "checkSocialBlog", fileCheck);
	}

	/**
	 * 파일권한체크 (MicroBlog)
	 */
	public boolean checkMicroBlog(FileCheck fileCheck) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "checkMicroBlog", fileCheck);
		if (count > 0){
			return true;
		}
		return false;
	}

	/**
	 * 파일권한체크 (QA)
	 */
	public FileCheck checkQA(FileCheck fileCheck) {
		return (FileCheck) this.sqlSelectForObject(NAMESPACE + "checkQA", fileCheck);
	}

	/**
	 * 파일권한체크 (Forum)
	 */
	public FileCheck checkForum(FileCheck fileCheck) {
		return (FileCheck) this.sqlSelectForObject(NAMESPACE + "checkForum", fileCheck);
	}

	/**
	 * 파일권한체크 (Todo)
	 */
	public boolean checkTodo(FileCheck fileCheck) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "checkTodo", fileCheck);
		if (count > 0){
			return true;
		}
		return false;
	}

	/**
	 * 파일권한체크 (Message)
	 */
	public boolean checkMessage(FileCheck fileCheck) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "checkMessage", fileCheck);
		if (count > 0){
			return true;
		}
		return false;
	}

	/**
	 * 파일권한체크 (WorkManual)
	 */
	public FileCheck checkWorkManual(FileCheck fileCheck) {
		return (FileCheck) this.sqlSelectForObject(NAMESPACE + "checkWorkManual", fileCheck);
	}

	/**
	 * 파일권한체크 (WhosWho)
	 */
	public FileCheck checkWhosWho(FileCheck fileCheck) {
		return (FileCheck) this.sqlSelectForObject(NAMESPACE + "checkWhosWho", fileCheck);
	}

	/**
	 * 파일권한체크 (CorporateVocabulary)
	 */
	public FileCheck checkCorporateVocabulary(FileCheck fileCheck) {
		return (FileCheck) this.sqlSelectForObject(NAMESPACE + "checkCorporateVocabulary", fileCheck);
	}

	/**
	 * 파일권한체크 (Survey)
	 */
	public FileCheck checkSurvey(FileCheck fileCheck) {
		return (FileCheck) this.sqlSelectForObject(NAMESPACE + "checkSurvey", fileCheck);
	}

	/**
	 * 파일권한체크 (Ideation)
	 */
	public FileCheck checkIdeation(FileCheck fileCheck) {
		return (FileCheck) this.sqlSelectForObject(NAMESPACE + "checkIdeation", fileCheck);
	}

	/**
	 * 파일권한체크 (Workspace)
	 */
	public FileCheck checkWorkspace(FileCheck fileCheck) {
		return (FileCheck) this.sqlSelectForObject(NAMESPACE + "checkWorkspace", fileCheck);
	}

	public boolean checkWorkspaceBoard(FileCheck fileCheck) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "checkWorkspaceBoard", fileCheck);
		return count>0;
	}

	public boolean checkWorkspaceWeekly(FileCheck fileCheck) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "checkWorkspaceWeekly", fileCheck);
		return count>0;
	}

	public boolean checkWorkspaceAnnounce(FileCheck fileCheck) {
		Integer count = (Integer) this.sqlSelectForObject(NAMESPACE + "checkWorkspaceAnnounce", fileCheck);
		return count>0;
	}
	
	/**
	 * 파일권한체크 (Workspace)
	 */
	public FileCheck checkKms(FileCheck fileCheck) {
		return (FileCheck) this.sqlSelectForObject(NAMESPACE + "checkKms", fileCheck);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.fileupload.dao.FileCheckDao#getBoardId(java.lang.String)
	 */
	public String getBoardId(String itemId) {
		return String.valueOf(this.sqlSelectForObject(NAMESPACE + "getBoardId", itemId));
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.support.fileupload.dao.FileCheckDao#getItemAuthor()
	 */
	public String getItemAuthor(String itemId) {
		return String.valueOf(this.sqlSelectForObject(NAMESPACE + "getItemAuthor", itemId));
	}

}
