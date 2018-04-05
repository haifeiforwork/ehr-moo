/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.moorimmss.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.moorimmss.dao.MssTeamTreeSpecialDao;
import com.lgcns.ikep4.portal.moorimmss.model.MssTeamTreeSpecial;
import com.lgcns.ikep4.portal.moorimmss.service.MssTeamTreeSpecialService;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.service.MessageOutsideService;
import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.service.SmsService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 */
@Service
public class MssTeamTreeSpecialServiceImpl  implements MssTeamTreeSpecialService {
	

	
	@Autowired
	private MssTeamTreeSpecialDao mssTeamTreeSpecialDao;
	
	@Autowired
	private IdgenService idgenService;

	public List<MssTeamTreeSpecial> getList() {
		// TODO Auto-generated method stub
		return this.mssTeamTreeSpecialDao.getList();
	}
	public List<MssTeamTreeSpecial> getList(String userId) {
		// TODO Auto-generated method stub
		return this.mssTeamTreeSpecialDao.getList(userId);
	}
	public void deleteMssTeamTreeSpecialList(String[] cid) {
		this.mssTeamTreeSpecialDao.deleteMssTeamTreeSpecialList(cid);

	}



	public String create(MssTeamTreeSpecial mssTeamTreeSpecial) {
		
		mssTeamTreeSpecial.setItemId(this.idgenService.getNextId());
		return  this.mssTeamTreeSpecialDao.create(mssTeamTreeSpecial);
	}



}
