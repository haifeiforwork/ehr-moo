/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.lightpack.award.dao.AwardDao;
import com.lgcns.ikep4.lightpack.award.dao.AwardItemDao;
import com.lgcns.ikep4.lightpack.award.model.Award;
import com.lgcns.ikep4.lightpack.award.model.AwardItem;
import com.lgcns.ikep4.lightpack.award.model.AwardItemReader;
import com.lgcns.ikep4.lightpack.award.service.AwardBatchService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 게시판 배치 서비스 클래스
 *
 * @author 최현식
 * @version $Id: AwardBatchServiceImpl.java 16240 2011-08-18 04:08:15Z giljae $
 */
@Service
public class AwardBatchServiceImpl implements AwardBatchService {

	@Autowired
	private AwardDao awardDao;

	@Autowired
	private AwardItemDao awardItemDao;
	
	@Autowired
	private MailSendService mailSendService;

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardBatchService#adminDeleteAwardItemForBatch(java.lang.String)
	 */
	public void adminDeleteAwardItemForBatch(String itemId) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardBatchService#listDeletePassedAwardItem(java.lang.Integer)
	 */
	public List<AwardItem> listBatchDeletePassedAwardItem(Integer getCount) {
		return this.awardItemDao.listBatchDeletePassedAwardItem(getCount);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardBatchService#listDeleteStatusAwardItem(java.lang.Integer)
	 */
	public List<AwardItem> listBatchDeleteStatusAwardItem(Integer getCount) {
		return this.awardItemDao.listBatchDeleteStatusAwardItem(getCount);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardBatchService#listBatchAwardItemInDeletedAward(java.lang.Integer)
	 */
	public List<AwardItem> listByAwardItemOfDeletedAward(String awardId, Integer getCount) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("awardId", awardId);
		parameter.put("getCount", getCount);

		return this.awardItemDao.listByAwardItemOfDeletedAward(parameter);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardBatchService#nextDeletedAward()
	 */
	public Award nextDeletedAward() {
		return this.awardDao.nextDeletedAward();
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardBatchService#deleteAward(java.lang.String)
	 */
	public void physicalAwardDelete(String awardId) {
		this.awardDao.physicalDelete(awardId);
	}
	
	public void updateDisplayAwardParentItem(){
		this.awardDao.updateDisplayAwardParentItem();
	}
	
	public void updateDisplayAwardItem(){
		this.awardDao.updateDisplayAwardItem();
	}
	
	@Async
	public void sendUserMail(List<AwardItemReader>  awardItemReaderList,String contents,User user,String wordName){
		for (AwardItemReader awardItemReader : awardItemReaderList) {
	  	   if(StringUtil.isEmpty(awardItemReader.getReaderMail())){//메일주소 없는 사용자는 메일 보내지 않음.
			}else{
		
				if(!StringUtil.isEmpty(awardItemReader.getReaderPassword())){//패스워드 없는 자는 퇴사자 일것입니다.
					HashMap<String, String> recip= new HashMap<String, String>();
					recip.put("email", awardItemReader.getReaderMail());
					//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ awardItemReader.getReaderMail():"+awardItemReader.getReaderMail());
					
					
					Mail mail = new Mail();
					mail.setMailType(MailConstant.MAIL_TYPE_HTML);
					if(!StringUtil.isEmpty(wordName)){
						mail.setTitle("["+wordName+"] 공지사항이 등록되었습니다.");
					}else{
						mail.setTitle("[전사공지] 공지사항이 등록되었습니다.");
					}
					mail.setContent(contents);
					
					
					List recipients = new ArrayList();
					recipients.add(recip);
					
					/*
					Mail mail = new Mail();
					mail.setMailType(MailConstant.MAIL_TYPE_HTML);
					mail.setTitle("[전사공지] 공지사항이 등록되었습니다.");

					String linkUrl;
					try {
						linkUrl = commonprop.getProperty("ikep4.baseUrl")+"/messengerLogin.do?gubun=2&itemId="+awardItem.getAwardItemId()+"&j_username="+awardItemReader.getReaderId()+"&j_password="+URLEncoder.encode(awardItemReader.getReaderPassword(), "UTF-8");
						mail.setContent("<br><br>아래의 DocLink Icon을 Double Click하면 회람 문서로 이동합니다.<br><br><a href=\""+linkUrl+"\"><img src=\""+baseUrl+"/base/images/icon/ic_note_b.png\" border=\"0\"></a>");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					*/
					
					if(recipients.size() > 0) {
						mail.setToEmailList(recipients);
						Map dataMap = new HashMap();
						mailSendService.sendMailNotice(mail, dataMap, user);

					}
					
				}
			}
		}
	}
}
