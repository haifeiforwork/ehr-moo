/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.workmanual.dao.LineReplyDao;
import com.lgcns.ikep4.collpack.workmanual.dao.ManualDao;
import com.lgcns.ikep4.collpack.workmanual.model.LineReply;
import com.lgcns.ikep4.collpack.workmanual.model.ManualPk;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.collpack.workmanual.service.LineReplyService;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * Service 구현 클래스
 * 
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: LineReplyServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service 
@Transactional
public class LineReplyServiceImpl extends GenericServiceImpl<LineReply, String> implements LineReplyService {
	@Autowired
	private LineReplyDao lineReplyDao;
	@Autowired
	private ManualDao manualDao;
	
	@Autowired
	private IdgenService idgenService;
	@Autowired
	private ActivityStreamService activityStreamService;

	@Deprecated
	public String create(LineReply lineReply) {
		return lineReplyDao.create(lineReply);
	}

	@Deprecated
	public boolean exists(String lineReplyId) {
		return lineReplyDao.exists(lineReplyId);
	}

	public LineReply read(String lineReplyId) {
		return lineReplyDao.get(lineReplyId);
	}

	@Deprecated
	public void delete(String lineReplyId) {
		lineReplyDao.remove(lineReplyId);
	}

	public void update(LineReply lineReply) {
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, IKepConstant.ACTIVITY_CODE_LINE_REPLY_EDIT, lineReply.getLinereplyId(), StringUtil.cutString(lineReply.getLinereplyContents(), 100, ""));

		lineReplyDao.update(lineReply);
	}
	////////////////////////////////////

	//댓글 정보 조회
	public SearchResult<LineReply> listLineReply(ManualSearchCondition manualSearchCondition) {
		Integer count = lineReplyDao.countLineReply(manualSearchCondition.getManualId());
		manualSearchCondition.terminateSearchCondition(count);  
		
		SearchResult<LineReply> searchResult = null; 
		if(manualSearchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<LineReply>(manualSearchCondition);
		} else {
			List<LineReply> lineReplyList = lineReplyDao.listLineReply(manualSearchCondition); 
			searchResult = new SearchResult<LineReply>(lineReplyList, manualSearchCondition);  			
		}  
		
		return searchResult;
	}
	//댓글 저장
	public String createLineReply(LineReply lineReply, String portalId) {
		String linereplyId = idgenService.getNextId();
		lineReply.setLinereplyId(linereplyId);
		lineReply.setLinereplyGroupId(linereplyId);
		lineReplyDao.create(lineReply);
		
		ManualPk manualPk = new ManualPk();
		manualPk.setManualId(lineReply.getManualId());
		manualPk.setPortalId(portalId);
		manualDao.updateLinereplyCount(manualPk);
		
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, IKepConstant.ACTIVITY_CODE_LINE_REPLY_POST, linereplyId, StringUtil.cutString(lineReply.getLinereplyContents(), 100, ""));
		
		return linereplyId;
	}
	//댓글의 답글 저장
	public String createReplyLineReply(LineReply lineReply, String portalId) {
		lineReplyDao.updateStep(lineReply);
		
		String linereplyId = idgenService.getNextId(); 
		lineReply.setLinereplyId(linereplyId);
		linereplyId = lineReplyDao.create(lineReply);

		ManualPk manualPk = new ManualPk();
		manualPk.setManualId(lineReply.getManualId());
		manualPk.setPortalId(portalId);
		manualDao.updateLinereplyCount(manualPk);

		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, IKepConstant.ACTIVITY_CODE_LINE_REPLY_POST, linereplyId, StringUtil.cutString(lineReply.getLinereplyContents(), 100, ""));
		
		return linereplyId;
	}
	//작성자 모드로 글 삭제 
	public void deleteLinereplyByUser(String manualId, String linereplyId, String updaterId, String updaterName, String portalId) {
		LineReply lineReply = lineReplyDao.get(linereplyId);
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, IKepConstant.ACTIVITY_CODE_LINE_REPLY_DELETE, linereplyId, StringUtil.cutString(lineReply.getLinereplyContents(), 100, ""));
		
		Integer count = lineReplyDao.countChildren(linereplyId);
		if(count == 0) {
			lineReplyDao.remove(linereplyId);
		} else {
			lineReply.setIsDelete(1);
			lineReply.setUpdaterId(updaterId);
			lineReply.setUpdaterName(updaterName);
			lineReplyDao.update(lineReply);
		}
		
		ManualPk manualPk = new ManualPk();
		manualPk.setManualId(manualId);
		manualPk.setPortalId(portalId);
		manualDao.updateLinereplyCount(manualPk);
	}
	//관리자 모드로 글 삭제 
	public void deleteLinereplyByAdmin(String manualId, String linereplyId, String portalId) {
		LineReply lineReply = lineReplyDao.get(linereplyId);
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_WORK_MANUAL, IKepConstant.ACTIVITY_CODE_LINE_REPLY_DELETE, linereplyId, StringUtil.cutString(lineReply.getLinereplyContents(), 100, ""));
				
		lineReplyDao.removeAll(linereplyId); 
		
		ManualPk manualPk = new ManualPk();
		manualPk.setManualId(manualId);
		manualPk.setPortalId(portalId);
		manualDao.updateLinereplyCount(manualPk);
	}
}
