/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.dao;

import java.util.Map;

import com.lgcns.ikep4.approval.work.model.ApprNotice;
import com.lgcns.ikep4.framework.core.dao.GenericDao;


/**
 * TODO Javadoc주석작성
 * 
 * @author 서지혜
 * @version $Id$
 */

public interface ApprNoticeDao extends GenericDao<ApprNotice, String> {

	void noticeCreate(ApprNotice apprNotice);

	void noticeUpdate(ApprNotice apprNotice);

	void noticeUpdateUsage(ApprNotice apprNotice);

	ApprNotice noticeDetail(Map<String, String> map);

}
