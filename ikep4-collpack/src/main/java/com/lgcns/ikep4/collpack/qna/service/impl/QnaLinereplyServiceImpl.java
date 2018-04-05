/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.qna.constants.QnaConstants;
import com.lgcns.ikep4.collpack.qna.dao.QnaDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaLinereplyDao;
import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaLinereply;
import com.lgcns.ikep4.collpack.qna.service.QnaLinereplyService;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * BoardService 구현 클래스
 * 
 * @author 이주열(jooyoul.lee@lgcns.com)
 * @version $Id: QnaLinereplyServiceImpl.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Service("qnaLinereplyService")
public class QnaLinereplyServiceImpl extends GenericServiceImpl<QnaLinereply, String> implements QnaLinereplyService {


	@Autowired
	private QnaLinereplyDao qnaLinereplyDao;
	
	@Autowired
	private QnaDao qnaDao;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private ActivityStreamService activityStreamService;
	

	public String create(QnaLinereply qnaLinereply) {
		
		String id = idgenService.getNextId();
		
		qnaLinereply.setLinereplyId(id);
		
		if(qnaLinereply.getLinereplyGroupId()== null){
			qnaLinereply.setLinereplyGroupId(id);
		}
		
		//내용 <br>처리
		qnaLinereply.setContents(StringUtil.replaceHtmlString(qnaLinereply.getContents()));
		
		//순서 증가시키기 -- 등록보다 순서 증가 먼저
		qnaLinereplyDao.updateStep(qnaLinereply.getLinereplyGroupId(), qnaLinereply.getStep());
		
		qnaLinereplyDao.create(qnaLinereply);
		
		//질문에 한줄 댓글 수 변경
		qnaDao.updateLinereplyCount(qnaLinereply.getQnaId());
		
		//게시물(방명록,댓글,블로그) 등록/수정/삭제,  멤버가입/탈퇴, 프로파일수정 등의 ActivityStream 등록
		
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_QA, IKepConstant.ACTIVITY_CODE_LINE_REPLY_POST, qnaLinereply.getLinereplyId(), StringUtil.cutString(qnaLinereply.getContents(), QnaConstants.CONTENT_SUMMAR_COUNT, "..."));

		
		return id;
	}


	public QnaLinereply read(String id) {
		
		return qnaLinereplyDao.get(id);
	}
	
	public int getCount(Qna qnaSearch) {
		
		return qnaLinereplyDao.getCount(qnaSearch);
	}


	public List<QnaLinereply> list(Qna qnaSearch) {
		return qnaLinereplyDao.selectAll(qnaSearch);
	}

	
	public void update(QnaLinereply qnaLinereply) {
		
		//내용 <br>처리
		qnaLinereply.setContents(StringUtil.replaceHtmlString(qnaLinereply.getContents()));
		
		qnaLinereplyDao.update(qnaLinereply);
		
	}
	
	
	/**
	 * 삭제 플래그 설정
	 */
	public void deleteLinereplyFlag(String linereplyId) {
		
		//자식 글이 있는지 검사
		int childCount = qnaLinereplyDao.selectCountByParentId(linereplyId);
		
		if(childCount > 0){	//자식글 있으면 삭제플래그 업데이트
			updateLinereplyDelete(linereplyId, QnaConstants.ITEM_DELETE_OK);
		
		} else {		//자식글 없으면 리얼삭제
			delete(linereplyId);
		}
		
	}

	/**
	 * 삭제 플래그 삭제 - 되살리기
	 */
	public void aliveLinereplyDeleteFlay(String linereplyId) {
		
		updateLinereplyDelete(linereplyId, QnaConstants.ITEM_DELETE_NO);
		
	}
	
	/**
	 * 공통 함수 삭제 플래그 업데이트
	 * @param qnaLinereply
	 */
	private void updateLinereplyDelete(String linereplyId, int linereplyDelete) {
		
		qnaLinereplyDao.updateLinereplyDelete(linereplyId, linereplyDelete);

		//한줄 댓글 수 변경
		QnaLinereply qnaLinereply = qnaLinereplyDao.get(linereplyId);
		
		//질문에 한줄 댓글 수 변경
		qnaDao.updateLinereplyCount(qnaLinereply.getQnaId());
		
	}

	
	public void delete(String linereplyId) {
		
		QnaLinereply qnaLinereply = qnaLinereplyDao.get(linereplyId);
		
		qnaLinereplyDao.remove(linereplyId);
		
		//질문에 한줄 댓글 수 변경
		qnaDao.updateLinereplyCount(qnaLinereply.getQnaId());
		
		//게시물(방명록,댓글,블로그) 등록/수정/삭제,  멤버가입/탈퇴, 프로파일수정 등의 ActivityStream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_QA, IKepConstant.ACTIVITY_CODE_LINE_REPLY_DELETE, qnaLinereply.getLinereplyId(), qnaLinereply.getContents());

	}


}
