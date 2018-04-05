/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.lightpack.award.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.award.dao.AwardItemDao;
import com.lgcns.ikep4.lightpack.award.dao.AwardLinereplyDao;
import com.lgcns.ikep4.lightpack.award.model.AwardItem;
import com.lgcns.ikep4.lightpack.award.model.AwardLinereply;
import com.lgcns.ikep4.lightpack.award.search.AwardLinereplySearchCondition;
import com.lgcns.ikep4.lightpack.award.service.AwardItemService;
import com.lgcns.ikep4.lightpack.award.service.AwardLinereplyService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * AwardLinereplyService 구현체 클래스
 */
@Service
public class AwardLinereplyServiceImpl implements AwardLinereplyService {

	/** The log. */
	protected final Log log = LogFactory.getLog(this.getClass());

	/** The idgen service. */
	@Autowired
	private IdgenService idgenService;

	/** The award linereply dao. */
	@Autowired
	private AwardLinereplyDao awardLinereplyDao;

	/** The award item dao. */
	@Autowired
	private AwardItemDao awardItemDao;

	@Autowired
	private ActivityStreamService activityStreamService;
	
	@Autowired
	private AwardItemService awardItemService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private MailSendService mailSendService;
	
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardLinereplyService#
	 * listAwardLinereplyBySearchCondition
	 * (com.lgcns.ikep4.lightpack.award.search.AwardLinereplySearchCondition)
	 */
	public SearchResult<AwardLinereply> listAwardLinereplyBySearchCondition(AwardLinereplySearchCondition searchCondtion) {
		Integer count = this.awardLinereplyDao.countBySearchCondition(searchCondtion);

		searchCondtion.terminateSearchCondition(count);

		SearchResult<AwardLinereply> result = null;

		if (searchCondtion.isEmptyRecord()) {
			result = new SearchResult<AwardLinereply>(searchCondtion);

		} else {
			List<AwardLinereply> awardLinereplyList = this.awardLinereplyDao.listBySearchCondition(searchCondtion);

			result = new SearchResult<AwardLinereply>(awardLinereplyList, searchCondtion);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardLinereplyService#
	 * readAwardLinereply(java.lang.String)
	 */
	public AwardLinereply readAwardLinereply(String linereplyId) {
		AwardLinereply awardLinereply = this.awardLinereplyDao.get(linereplyId);

		return awardLinereply;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardLinereplyService#
	 * createAwardLinereply
	 * (com.lgcns.ikep4.lightpack.award.model.AwardLinereply)
	 */
	public String createAwardLinereply(AwardLinereply awardLinereply) {
		final String generatedId = this.idgenService.getNextId();

		awardLinereply.setLinereplyId(generatedId);
		awardLinereply.setLinereplyGroupId(generatedId);

		final String insertedId = this.awardLinereplyDao.create(awardLinereply);

		// Activity Stream 댓글 등록
		this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS, IKepConstant.ACTIVITY_CODE_LINE_REPLY_POST,
				awardLinereply.getItemId(), awardLinereply.getContents());

		// 게시글의 댓글 갯수를 업데이트한다.
		this.awardItemDao.updateLinereplyCount(awardLinereply.getItemId());

		AwardItem awardItem = awardItemService.readAwardItem(awardLinereply.getItemId());
		
		if((awardItem.getAwardId().equals("100010083357")||awardItem.getAwardId().equals("100010089350")||awardItem.getAwardId().equals("100010089362")) && !awardItem.getRegisterId().equals(awardLinereply.getRegisterId())){
			Mail mail = new Mail();
			User user = this.userService.read(awardItem.getRegisterId());
			mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
			mail.setMailTemplatePath("linereplyMailTemplate.vm");
			
			List recipients = new ArrayList();
			HashMap<String, String> recip;
			recip = new HashMap<String, String>();

			recip.put("email", user.getMail());
			recip.put("name", user.getUserName());

			recipients.add(recip);
			mail.setToEmailList(recipients);
			
			Map dataMap = new HashMap();
			
			
			
			Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
	        String baseUrl =commonprop.getProperty("ikep4.baseUrl");
			String url = "";
			url = baseUrl+"/lightpack/award/awardItem/readAwardItemView.do?&docPopup=true&awardId=" + awardItem.getAwardId()+ "&itemId="+awardItem.getItemId();
			
			dataMap.put("regdate", getToday("yyyy-MM-dd"));
			mail.setTitle(awardItem.getRegisterName()+"님이 작성한 게시글에 댓글이 등록되었습니다");
			dataMap.put("register", awardLinereply.getRegisterName());
			
			dataMap.put("content", awardLinereply.getContents());
			dataMap.put("url", url);
			
			User sender = user;
			
			mailSendService.sendMail(mail, dataMap, sender);
		}
		
		return insertedId;
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardLinereplyService#
	 * createReplyAwardLinereply
	 * (com.lgcns.ikep4.lightpack.award.model.AwardLinereply)
	 */
	public String createReplyAwardLinereply(AwardLinereply awardLinereply) {

		AwardLinereply parent = this.awardLinereplyDao.get(awardLinereply.getLinereplyParentId());

		awardLinereply.setItemId(parent.getItemId());
		awardLinereply.setLinereplyGroupId(parent.getLinereplyGroupId());
		awardLinereply.setIndentation(parent.getIndentation() + 1);
		awardLinereply.setLinereplyDelete(0);
		awardLinereply.setStep(parent.getStep() + 1);

		final String generatedId = this.idgenService.getNextId();

		awardLinereply.setLinereplyId(generatedId);
		
		// 스텝값을 업데이트한다.
		this.awardLinereplyDao.updateStep(awardLinereply);

		final String insertedId = this.awardLinereplyDao.create(awardLinereply);

		// Activity Stream 댓글 등록
		this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS, IKepConstant.ACTIVITY_CODE_LINE_REPLY_POST,
				awardLinereply.getItemId(), awardLinereply.getContents());

		

		// 게시글의 댓글 갯수를 업데이트한다.
		this.awardItemDao.updateLinereplyCount(awardLinereply.getItemId());
		
		

		return insertedId;
	}
	
	public String getToday(String formatStr) {

		String format = formatStr;
		if (format == null || format.equals("")) {
			format = "yyyy-MM-dd";
		}

		Date date = new Date();
		SimpleDateFormat sdate = new SimpleDateFormat(format);

		return sdate.format(date);
	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardLinereplyService#
	 * updateAwardLinereply
	 * (com.lgcns.ikep4.lightpack.award.model.AwardLinereply)
	 */
	public void updateAwardLinereply(AwardLinereply awardLinereply) {
		this.awardLinereplyDao.update(awardLinereply);

		// Activity Stream 댓글 등록
		this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS, IKepConstant.ACTIVITY_CODE_LINE_REPLY_EDIT,
				awardLinereply.getItemId(), awardLinereply.getContents());

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardLinereplyService#
	 * adminDeleteAwardLinereply(java.lang.String, java.lang.String)
	 */
	public void adminDeleteAwardLinereply(String itemId, String linereplyId) {
		// 댓글을 가져온다.
		AwardLinereply awardLinereply = this.awardLinereplyDao.get(linereplyId);

		Integer childrenCount = this.awardLinereplyDao.countChildren(linereplyId);

		// Activity Stream 댓글 등록
		this.activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_BBS,
				IKepConstant.ACTIVITY_CODE_LINE_REPLY_DELETE, awardLinereply.getItemId(), awardLinereply.getContents());

		if (childrenCount == 0) {
			this.awardLinereplyDao.physicalDelete(linereplyId);

		} else {
			this.awardLinereplyDao.physicalDeleteThreadByLinereplyId(linereplyId);
		}

		// 게시글의 댓글 갯수를 업데이트한다.
		this.awardItemDao.updateLinereplyCount(itemId);

	}

	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.lightpack.award.service.AwardLinereplyService#
	 * userDeleteAwardLinereply
	 * (com.lgcns.ikep4.lightpack.award.model.AwardLinereply)
	 */
	public void userDeleteAwardLinereply(AwardLinereply awardLinereply) {
		// 하위 댓글 개수를 구한다.
		Integer count = this.awardLinereplyDao.countChildren(awardLinereply.getLinereplyId());

		// 하위 댓글이 존재하지 않으면
		if (count == 0) {
			this.adminDeleteAwardLinereply(awardLinereply.getItemId(), awardLinereply.getLinereplyId());

			// 하위 댓글이 존재하면
		} else {
			// 삭제여부 필드를 "1"로 업데이트 한다.
			this.awardLinereplyDao.logicalDelete(awardLinereply);

		}
		// 게시글의 댓글 갯수를 업데이트한다.
		this.awardItemDao.updateLinereplyCount(awardLinereply.getItemId());

	}

}
