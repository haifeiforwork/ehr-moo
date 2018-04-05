/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.servicepack.survey.dao.ResponseDao;
import com.lgcns.ikep4.servicepack.survey.dao.ResponseDetailDao;
import com.lgcns.ikep4.servicepack.survey.model.Response;
import com.lgcns.ikep4.servicepack.survey.model.ResponseDetail;
import com.lgcns.ikep4.servicepack.survey.model.ResponseKey;
import com.lgcns.ikep4.servicepack.survey.service.ResponseService;

/**
 * 설문조사 참여 dao
 *
 * @author ihko11
 * @version $Id: ResponseServiceImpl.java 16244 2011-08-18 04:11:42Z giljae $
 */
@Service
@Transactional
public class ResponseServiceImpl extends GenericServiceImpl<Response,ResponseKey> implements ResponseService {
	protected final Log log = LogFactory.getLog(getClass());
	
	private ResponseDao responseDao;
	
	//답변
	@Autowired
	private ResponseDetailDao responseDetailDao;
	
	@Autowired
	public ResponseServiceImpl(ResponseDao dao) {
		super(dao);
		this.responseDao = dao;
	}
	
	/**
	 * 참여여부
	 */
	public boolean already(Response response) {
		return responseDao.already(response);
	}

	/**
	 * 참여자 총카운트
	 */
	public Integer responseAllCount(String surveyId) {
		return responseDao.responseAllCount(surveyId);
	}
	
	/**
	 * 참여 삭제
	 */
	@Override
	public void delete(ResponseKey id) {
		//답변삭제
		responseDetailDao.removeAllByResponseId( id.getResponseId() );
		
		//문항삭제
		responseDao.remove(id);
	}

	/**
	 * 설문 참여
	 */
	@Override
	public ResponseKey create(Response response) {
		ResponseKey responseKey = responseDao.create(response);
		
		if( response.getResponse().intValue() == 0 )
		{	
			for (ResponseDetail responseDetail : response.getResponseDetail() ) 
			{
				getMaxResponseSeq(responseDetail);
				responseDetailDao.create(responseDetail );
			}
		}
		
		return responseKey;
	}

/**
 * 설문 참여 수정
 */
	@Override
	public void update(Response response) {
		//답변은 모두삭제후 다시입력
		responseDetailDao.removeAllByResponseId(response.getResponseId());
		responseDao.update(response);
		
		for (ResponseDetail responseDetail : response.getResponseDetail() ) {
			getMaxResponseSeq(responseDetail);
			responseDetailDao.create(responseDetail );
		}
		
	}

/**
 * 설문 참여 max seq
 * @param responseDetail
 */
	private void getMaxResponseSeq(ResponseDetail responseDetail) {
		Integer maxResponseSeq = Integer.valueOf(0);
		
		if( responseDetail.getResponseSeq()==null )
		{
			maxResponseSeq = responseDetailDao.maxResponseSeq( responseDetail.getResponseId() );
		
			int order = maxResponseSeq.intValue();
			order++;
			
			responseDetail.setResponseSeq(order);
		}
	}
	
	/**
	 * 설문 참여 일일 분석
	 */
	public List<Response> getDayResultList(String surveyId){
		return responseDao.getDayResultList(surveyId);
	}
	
	/**
	 * 설문 참석 상세 내용
	 */
	public List<ResponseDetail> getAllByAnswerId(String answerId){
		return responseDetailDao.getAllByAnswerId(answerId);
	}
	
	/**
	 * excel download report
	 */
	public List<ResponseDetail> getReportDetailAllBySurveyId(String surveyId){
		return responseDetailDao.getReportDetailAllBySurveyId(surveyId);
	}
}
