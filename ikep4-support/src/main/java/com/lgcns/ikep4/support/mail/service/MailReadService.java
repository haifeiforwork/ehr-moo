package com.lgcns.ikep4.support.mail.service;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.mail.base.MailException;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 메일받기 처리 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: MailReadService.java 16258 2011-08-18 05:37:22Z giljae $
 */
public interface MailReadService {

	/**
	 * 메일 목록 읽기
	 * 
	 * @param folderName
	 * @param mailVO
	 * @return @
	 */
	public List<Mail> getMailList(String folderName, User user, int size);

	
	/** 매일 가져올 건수 
	 * @param folderName
	 * @param user
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws MailException
	 * @throws MessagingException
	 */
	public Integer getMailListNum(String folderName, User user, Date fromDate, Date toDate) throws MailException, MessagingException;
	

	/** 메일 목록 읽기(기간검색 추가)
	 * @param folderName
	 * @param user
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws MailException
	 * @throws MessagingException
	 */
	public List<Mail> getMailList(String folderName, User user, Date fromDate, Date toDate, int size) throws MailException, MessagingException;

	/**
	 * 메일 상세 보기
	 * 
	 * @param folderName
	 * @param mailId
	 * @param mailVO
	 * @return @
	 */
	public Mail getMailInfo(String folderName, String mailId, User user);

	/** 메일 첨부 파일 다운로드
	 * @param idType
	 * @param folderName
	 * @param mailId
	 * @param response
	 * @param multipartPath
	 * @param user
	 * @return
	 */
	public String downloadMailAttach(String idType, String folderName, String mailId, HttpServletResponse response,
			String multipartPath, User user);

	/** 첨부파일서버로 저장
	 * @param folderName
	 * @param mailId
	 * @param multipartPath
	 * @param user
	 * @return
	 */
	public FileData copyMailAttach(String folderName, String fileName, String mailId, String multipartPath, User user);
	
	/**
	 * 신규 메일 갯수
	 * 
	 * @param folderName
	 * @param mailVO
	 * @return @
	 */
	public int getNewMailCount(String folderName, User user);
	
	/**
	 * 신규 메일 갯수
	 * 
	 * @param folderName
	 * @param mailVO
	 * @return @
	 */
	public int getNewMailCountFromTerrace(String folderName, User user);

	/**
	 * 메일 폴더 목록
	 * 
	 * @param mailVO
	 * @return @
	 */
	public List<String> getFolderList(User user);

	/** 읽은 메일로 변경
	 * @param folderName
	 * @param mailId
	 * @param user
	 * @return
	 */
	public String changeRead(String folderName, String mailId, User user);
	
	/** 메일 삭제
	 * @param idType
	 * @param folderName
	 * @param mailId
	 * @param user
	 * @return
	 */
	public String deleteMail(String idType, String folderName, String mailId, User user);

	/** 메일 삭제(여러개 삭제)
	 * @param idType
	 * @param folderName
	 * @param mailIdList
	 * @param user
	 * @return
	 */
	public String deleteMail(String idType, String folderName, List<String> mailIdList, User user);


	/** 메일 이동
	 * @param folderName
	 * @param mailId
	 * @param targetFolderName
	 * @param user
	 * @return
	 */
	public String moveMail(String folderName, String mailId, String targetFolderName, User user);

	/**
	 * 메일 이동(여러개 이동)
	 * 
	 * @param folderName
	 * @param mailIdList
	 * @param targetFolderName
	 * @param mailVO
	 * @return @
	 */
	public String moveMail(String folderName, List<String> mailIdList, String targetFolderName, User user);
	
	public List<Mail> getRecentMailInfo(User user, int nSize);

}
