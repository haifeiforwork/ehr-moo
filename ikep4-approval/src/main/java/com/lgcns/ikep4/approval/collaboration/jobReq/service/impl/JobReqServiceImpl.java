/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.collaboration.jobReq.service.impl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.approval.collaboration.common.model.CbrConstants;
import com.lgcns.ikep4.approval.collaboration.common.service.CollaboCommonService;
import com.lgcns.ikep4.approval.collaboration.common.service.CommonCodeService;
import com.lgcns.ikep4.approval.collaboration.common.util.CollaboUtils;
import com.lgcns.ikep4.approval.collaboration.jobReq.dao.JobReqDao;
import com.lgcns.ikep4.approval.collaboration.jobReq.model.JobReq;
import com.lgcns.ikep4.approval.collaboration.jobReq.search.JobReqListSearchCondition;
import com.lgcns.ikep4.approval.collaboration.jobReq.service.JobReqService;
import com.lgcns.ikep4.approval.collaboration.legal.model.ApprLegal;
import com.lgcns.ikep4.approval.collaboration.npd.model.NewProductDev;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.fileupload.dao.FileDao;
import com.lgcns.ikep4.support.fileupload.dao.FileLinkDao;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.tagfree.util.MimeUtil;


/**
 * 위임 관리 Service 구현
 * 
 * @author jeehye
 * @version $Id: ApprNoticeServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service
@Transactional
public class JobReqServiceImpl implements JobReqService {
	/** The log. */
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private JobReqDao jobReqDao;
	
	/** The file service. */
	@Autowired
	private FileService fileService;
	
	@Autowired
	private FileLinkDao fileLinkDao;
	
	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private MailSendService mailSendService;
	
	@Autowired
	private CollaboUtils collaboService;
	
	@Autowired
	private CollaboCommonService collaboCommonService;
	
	@Autowired
	private IdgenService idgenService;
	
	
	/**
	 * 법무관리자 여부
	 * 
	 * @param userId
	 * @return Boolean
	 */
	public boolean userAuthMgntYn(String userId) {
		int result = jobReqDao.userAuthMgntYn(userId);

		// 결과 데이터가 없을 경우에는 접근 권한이 없음
		if (result > 0) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	
	/**
	 * 계약서 List 갯수
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public int jobReqCount(JobReqListSearchCondition searchCondition) {
		int count = jobReqDao.jobReqCount(searchCondition);
//		(List<ApprDefLine>)this.apprDefLinedao.listApprDefLine();  
		return count;
	}
	
	/**
	 * 계약서 List
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public SearchResult<JobReq> jobReqList(JobReqListSearchCondition searchCondition) {
        Integer count = jobReqDao.jobReqCount(searchCondition);
        
        searchCondition.terminateSearchCondition( count );
        
        SearchResult<JobReq> searchResult = null;
        
        if(searchCondition.isEmptyRecord()){
            searchResult = new SearchResult<JobReq>( searchCondition );
        }else{
            List<JobReq> list = this.jobReqDao.jobReqList(searchCondition);
            
            searchResult = new SearchResult<JobReq>( list , searchCondition );
        }
        return searchResult;
	}
	
	/**
	 * 업무 의뢰 요청서 신규등록
	 * 
	 * @param jobReq 문서 상세 내용
	 * @return
	 */
	public void jobReqSave(JobReq jobReq) {
	Map<String, Object> jobReqMgntNo = jobReqDao.jobReqMgntNoInfo(jobReq.getDateNo());
		
		jobReq.setJobReqMgntNo((String) jobReqMgntNo.get("jobReqMgntNo"));
		jobReq.setProcessStatus("10");
		jobReq.setApprStsCd("01");
		jobReqDao.jobReqSaveReview(jobReq); //요청서
		
		jobReq.setReqDraftStsCd("01");
		jobReqDao.jobReqSaveApprLine(jobReq); //결제선
	}
	
	/**
	 * 계약서 상세정보
	 * 
	 * @param contractMgntNo 문서관리 번호
	 * @return
	 */
	public Map<String, String> jobReqDetail(Map<String, String> paramMap) {
		Map<String, String> jobReqDetail = jobReqDao.jobReqDetail(paramMap);
		return jobReqDetail;
	}
	
	/**
	 * 계약서 수정
	 * 
	 * @param jobReq 문서 상세 내용
	 * @return
	 */
	public void jobReqModify(JobReq jobReq) {
		String reqDraftName = jobReq.getReqReviewEmpName();
		String rcvDraftName = jobReq.getRcvReviewEmpName();
		if(StringUtil.isEmpty(reqDraftName)) {
			if(StringUtil.isEmpty(rcvDraftName)) {
				jobReq.setReqReviewEmpNo(null);
			}
		} else if(StringUtil.isEmpty(rcvDraftName)) {
			jobReq.setRcvReviewEmpNo(null);
		}
		jobReqDao.jobReqModifyReview(jobReq); //요청서
		jobReqDao.jobReqModifyApprLine(jobReq); //결제선
	}
	
	/**
	 * 계약서 삭제
	 * 
	 * @param jobReq 문서 상세 내용
	 * @return
	 */
	public void jobReqDelete(String jobReqMgntNo) {
		jobReqDao.jobReqDeleteReview(jobReqMgntNo); //요청서
		jobReqDao.jobReqDeleteApprLine(jobReqMgntNo); //결제선
		jobReqDao.jobReqDeleteHistory(jobReqMgntNo); //검토이력
		jobReqDao.jobReqDeleteResult(jobReqMgntNo); //검토결과
	}
	
	/**
	 * 업무 의뢰 검토이력 List
	 * 
	 * @param searchCondition 검색 조건
	 * @return
	 */
	public List<JobReq> jobReqHistoryList(String jobReqMgntNo) {
		
		List<JobReq> resultList = this.jobReqDao.jobReqHistoryList(jobReqMgntNo);
			
		return resultList;
	}
	
	/**
	 * 검토이력 등록
	 * 
	 * @param jobReq 문서 상세 내용
	 * @return
	 */
	public void jobReqHistorySave(JobReq jobReq, User user) {
		jobReqDao.jobReqHistorySave(jobReq);
	}
	
	/**
	 * 업무 의뢰 승인, 종료
	 * 
	 * @param jobReq 문서 상세 내용
	 * @return
	 */
	public void jobReqAppr(JobReq jobReq) {
		String jobReqMgntNo =  jobReq.getJobReqMgntNo(); //문서관리번호 세팅
		String apprLv =  jobReq.getApprLv(); //결제선 단계 Lv
		String processStatus = jobReq.getProcessStatus(); //품의상태
		String processStatusOld = jobReq.getProcessStatusOld(); //현재품의상태값 
		
		if(processStatusOld.equals("10") || processStatusOld.equals("20")) {
			jobReqDao.jobReqModifyReview(jobReq); //요청서 저장
			jobReqDao.jobReqModifyApprLine(jobReq); //결제선 저장
		}

		int mailType = getMailType(apprLv, processStatus);
		
		if(processStatus.equals("99")) {
			if(apprLv.equals("2") || apprLv.equals("3")) {
				jobReq.setProcessStatus("13");
			} else if(apprLv.equals("5") || apprLv.equals("6")) {
				jobReq.setProcessStatus("23");
			} 
		}
		jobReqDao.jobReqApprReview(jobReq); //요청서
		
		
		//종결이 아닐경우에만
		if(!apprLv.equals("7")) {
			jobReqDao.jobReqApprApprLine(getApprStatus(jobReq)); //결제선
		}
	
		try {
			//메일 발송오류시 승인과 영향받지 않게함.
			sendApproveMail( mailType, jobReqMgntNo, jobReq.getRejectReason());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
	
	/**
	 * 계약서 기안부서 접수
	 * 
	 * @param jobReq 문서 상세 내용
	 * @return
	 */
	public void jobReqReqReceipt(JobReq jobReq) {
		
		jobReqDao.jobReqReqReceiptReview(jobReq); //요청서
		jobReqDao.jobReqReqReceiptLine(jobReq); //결제선
//		jobReqDao.jobReqDeleteHistory(jobReq.getJobReqMgntNo()); //검토이력
//		jobReqDao.jobReqDeleteResult(jobReq.getJobReqMgntNo()); //검토결과
		
	}
	
	/**
	 * 업무 의뢰 주관부서 접수
	 * 
	 * @param jobReq 문서 상세 내용
	 * @return
	 */
	public void jobReqRcvReceipt(JobReq jobReq) {
		
		jobReqDao.jobReqRcvReceiptReview(jobReq); //요청서
		
		jobReqDao.jobReqRcvReceiptLine(jobReq); //결제선
	}
	
	/**
	 * 검토결과 등록
	 * 
	 * @param jobReq 문서 상세 내용
	 * @return
	 */
	public void jobReqResultSave(JobReq jobReq) {
		
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		Portal portal = (Portal)RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portal", RequestAttributes.SCOPE_SESSION);
		String port = commonprop.getProperty("ikep4.activex.editor.port");
		
		
		MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
		util.setMimeValue(jobReq.getReviewOpinion());
		//Mime 데이타 decoding
		util.processDecoding();
		
		//내용 가져오기
		String content = util.getDecodedHtml(false);		
		content = content.trim();
		//내용세팅
		jobReq.setReviewOpinion(content);
		
		jobReqDao.jobReqResultSave(jobReq);
	}
	
	/**
	 * 검토이력 수정
	 * 
	 * @param jobReq 문서 상세 내용
	 * @return
	 */
	public void jobReqResultModify(JobReq jobReq) {
		
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		Portal portal = (Portal)RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portal", RequestAttributes.SCOPE_SESSION);
		String port = commonprop.getProperty("ikep4.activex.editor.port");
		
		
		MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
		util.setMimeValue(jobReq.getReviewOpinion());
		//Mime 데이타 decoding
		util.processDecoding();
		
		//내용 가져오기
		String content = util.getDecodedHtml(false);		
		content = content.trim();
		//내용세팅
		jobReq.setReviewOpinion(content);
		
		jobReqDao.jobReqResultModify(jobReq);
	}
	
	
	
	/**
	 * 계약서 승인 결제선 update시 품의 상태 코드 세팅
	 * 
	 * @param jobReq 문서 상세 내용
	 * @return
	 */
	public JobReq getApprStatus(JobReq jobReq) {
		// session
		try{
			String apprLv =  jobReq.getApprLv(); //결제선 단계 Lv
			String processStatus = jobReq.getProcessStatus(); //품의상태
			
			//반려시 결제선에 들어갈 품의상태세팅
			if(processStatus.equals("13") || processStatus.equals("23")) {
				if(apprLv.equals("1")) {
					jobReq.setReqDraftStsCd("04");
				} else if(apprLv.equals("2")) {
					jobReq.setReqReviewStsCd("04");
				} else if(apprLv.equals("3")) {
					jobReq.setReqApprStsCd("04");
				} else if(apprLv.equals("4")) {
					jobReq.setRcvDraftStsCd("04");
				} else if(apprLv.equals("5")) {
					jobReq.setRcvReviewStsCd("04");
				} else if(apprLv.equals("6")) {
					jobReq.setRcvApprStsCd("04");
				}
			} else {
				if(apprLv.equals("1")) {
					jobReq.setReqDraftStsCd("03");
				} else if(apprLv.equals("2")) {
					jobReq.setReqReviewStsCd("03");
				} else if(apprLv.equals("3")) {
					jobReq.setReqApprStsCd("03");
				} else if(apprLv.equals("4")) {
					jobReq.setRcvDraftStsCd("03");
				} else if(apprLv.equals("5")) {
					jobReq.setRcvReviewStsCd("03");
				} else if(apprLv.equals("6")) {
					jobReq.setRcvApprStsCd("03");
				} 
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return jobReq;
	}
	
	/**
	 * 메일 타입 세팅
	 * @param apprLv
	 * @param 
	 */
	
	public int getMailType(String apprLv, String processStatus) {
		//메일 타입 세팅
		int mailType = 0;
		if(apprLv.equals("1")) { 
			mailType = 1; //기안부서 기안자 상신
		} else if(apprLv.equals("2")) {
			mailType = 2; //기안부서 검토자/승인
		} else if(apprLv.equals("3")) { 
			mailType = 4; //기안부서 승인자 승인
		} else if(apprLv.equals("4")) {
			mailType = 6; //주관부서 기안자 상신
		} else if(apprLv.equals("5")) {
			mailType = 7; //주관부서 검토자 승인
		} else if(apprLv.equals("6")) {
			mailType = 8; //주관부서 승인자 승인
		} else {
			mailType = 0;
		}
		
		if(processStatus.equals("13")) {
			mailType = 2; //기안부서 반려
		} else if(processStatus.equals("23")) {
			mailType = 5; //법무부서 반려
		}
		return mailType;
	}
	
	
	/**
	 * 메일보내기
	 * @param mailType
	 * @param newProductDev
	 * @throws Exception
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendApproveMail( int mailType, String id, String rejectReason) throws Exception {
		if( mailType == 0) {
			return;
		}
		
		JobReq detailInfo = jobReqDao.getJobReqDetailInfo(id);
		String jobReqMgntNo = detailInfo.getJobReqMgntNo();
		String processStatus = detailInfo.getProcessStatus();
		
		detailInfo.setRejectReason(rejectReason); //반려사유 세팅
		
		String serverMode =  collaboService.getServerMode();
		
		String title = "";
		if( detailInfo != null) {
			
			title = detailInfo.getJobReqTitle() + " 업무 의뢰 요청서 ";
		}
		
		List<String> mailTargetEmpNoList = new ArrayList<String>();
		List<JobReq> mailTargetLegalEmpNoList = new ArrayList<JobReq>();
		
		switch ( mailType) {
		case CbrConstants.REQ_MAIL_TYPE_01:
			title += "승인 요청"; 
			if( StringUtils.isNotEmpty( detailInfo.getReqReviewEmpNo() )) {
				mailTargetEmpNoList.add( detailInfo.getReqReviewEmpNo());
				processStatus = "11";
			}else{
				mailTargetEmpNoList.add( detailInfo.getReqApprEmpNo());
				processStatus = "11";
			}
			break;
		case CbrConstants.REQ_MAIL_TYPE_02:
			title += "반려";
			mailTargetEmpNoList.add( detailInfo.getReqDraftEmpNo());
			processStatus = "13";
			break;
		case CbrConstants.REQ_MAIL_TYPE_03:
			title += "승인 요청"; 
			mailTargetEmpNoList.add( detailInfo.getReqApprEmpNo());
			processStatus = "11";
			break;
		case CbrConstants.REQ_MAIL_TYPE_04:
			title += "접수 요청"; 
			mailTargetLegalEmpNoList = jobReqDao.getLegalEmpNo("30");
			processStatus = "12";
			for(int i=0; i < mailTargetLegalEmpNoList.size(); i++) {
				mailTargetEmpNoList.add((String) mailTargetLegalEmpNoList.get(i).getEmailEmpNo());
			}
			break;
		case CbrConstants.REQ_MAIL_TYPE_05:
			title += "반려";
			processStatus = "23";
			mailTargetEmpNoList.add( detailInfo.getReqDraftEmpNo());
			break;
		case CbrConstants.REQ_MAIL_TYPE_06:
			title += "접수 승인 요청"; 
			if( StringUtils.isNotEmpty( detailInfo.getRcvReviewEmpNo() )) {
				mailTargetEmpNoList.add( detailInfo.getRcvReviewEmpNo());
				processStatus = "21";
			}else{
				mailTargetEmpNoList.add( detailInfo.getRcvApprEmpNo());
				processStatus = "21";
			}
			break;
		case CbrConstants.REQ_MAIL_TYPE_07:
			title += "접수 승인 요청";
			mailType = CbrConstants.REQ_MAIL_TYPE_07;
			mailTargetEmpNoList.add( detailInfo.getRcvApprEmpNo());
			processStatus = "21";
			break;
		case CbrConstants.REQ_MAIL_TYPE_08:
			title += "접수 승인"; 
			mailTargetEmpNoList.add( detailInfo.getReqDraftEmpNo());
			mailTargetEmpNoList.add( detailInfo.getRcvDraftEmpNo());
			processStatus = "22";
			break;
		default:
			break;
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List receiverList = new ArrayList();
		
		if( mailTargetEmpNoList.size() > 0) {
			
			paramMap.put( "mailTargetEmpNoList", mailTargetEmpNoList);
			List<Map<String, Object>> mailList = (List<Map<String, Object>>) collaboCommonService.getUserMailAddrList( paramMap);
			if( mailList != null) {
				
				for (Map<String, Object> map : mailList) {
					
					String mailAddr = MapUtils.getString( map, "mail", "");
					String userName = MapUtils.getString( map, "userName", "");
					if( StringUtils.isNotEmpty( mailAddr)) {
						HashMap<String, String> recieverMap = new HashMap<String, String>();
//						if( StringUtils.equals( serverMode, "real")) {
							
							recieverMap.put("email", mailAddr);
//						}else{
//							
//							recieverMap.put("email", "admin@eptest.co.kr");
//						}
						recieverMap.put("name", userName);
						receiverList.add(recieverMap);
					}
				}
			}
		}
		
		String mainFrameUrl = "/approval/collaboration/jobReq/jobReqDetail.do?id="+jobReqMgntNo+"&processStatus="+processStatus;
//		String mainFrameUrl = "/approval/collaboration/testreq/editTestRequestView.do?trMgntNo="+testRequest.getTrMgntNo() +"&viewMode=modify";
		
		Mail mail = new Mail();
		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("jobReqTemplate.vm");
		mail.setTitle( title);
		
		User sendUser = new User();
		sendUser.setMail("admin@eptest.co.kr");
		
		mail.setToEmailList( receiverList);
		
		Map dataMap = new HashMap();
		dataMap.put("detailInfo", detailInfo);
		dataMap.put("mailType", mailType);
		dataMap.put("url", collaboService.getServerURL( mainFrameUrl));
		
		mailSendService.sendMail(mail, dataMap, sendUser);
	}
	
	/**
	 * 이력 등록후 기안자에게 메일보내기
	 * @param mailType
	 * @param newProductDev
	 * @throws Exception
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void sendApproveHistoryMail( String id, User user) throws Exception {
		
		JobReq detailInfo = jobReqDao.getJobReqDetailInfo(id);
		String jobReqMgntNo = detailInfo.getJobReqMgntNo();
		String processStatus = detailInfo.getProcessStatus();
		
		String title = "";
		if( detailInfo != null) {
			title = detailInfo.getJobReqTitle() + " 업무 의뢰 요청서 ";
		}
		
		List<String> mailTargetEmpNoList = new ArrayList<String>();
		
		title += "이력 등록"; 
		
		//메일 보낼 대상자 세팅
		if(detailInfo.getReqDraftEmpNo().equals(user.getEmpNo()) ) { //접속자가 해당 내역 기안부서 기안자인경우
			if(StringUtils.isNotEmpty(detailInfo.getRcvDraftEmpNo())) { //해당 주관부서 기안자가 존재 하는경우
				mailTargetEmpNoList.add( detailInfo.getRcvDraftEmpNo()); //주관부서 기안자 세팅
			} 			
		} else if(StringUtils.isNotEmpty(detailInfo.getRcvDraftEmpNo()) && detailInfo.getRcvDraftEmpNo().equals(user.getEmpNo())) { //접속자가 해당 내역 주관부서 기안자인경우
				mailTargetEmpNoList.add( detailInfo.getReqDraftEmpNo());
		} else { 
			mailTargetEmpNoList.add( detailInfo.getReqDraftEmpNo()); //기안부서 기안자 기본 세팅
			if(StringUtils.isNotEmpty(detailInfo.getRcvDraftEmpNo())) { //주관부서 기안자가 존재 할경우
				mailTargetEmpNoList.add( detailInfo.getRcvDraftEmpNo()); //주관부서 기안자 세팅
			}
		}
		
		processStatus = detailInfo.getProcessStatus();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List receiverList = new ArrayList();
		
		if( mailTargetEmpNoList.size() > 0) {
			
			paramMap.put( "mailTargetEmpNoList", mailTargetEmpNoList);
			List<Map<String, Object>> mailList = (List<Map<String, Object>>) collaboCommonService.getUserMailAddrList( paramMap);
			if( mailList != null) {
				
				for (Map<String, Object> map : mailList) {
					
					String mailAddr = MapUtils.getString( map, "mail", "");
					String userName = MapUtils.getString( map, "userName", "");
					if( StringUtils.isNotEmpty( mailAddr)) {
						HashMap<String, String> recieverMap = new HashMap<String, String>();
						
						recieverMap.put("email", mailAddr);
						recieverMap.put("name", userName);
						receiverList.add(recieverMap);
					}
				}
			}
		}
		
		String mainFrameUrl = "/approval/collaboration/jobReq/jobReqDetail.do?id="+jobReqMgntNo+"&processStatus="+processStatus;
		
		Mail mail = new Mail();
		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("jobReqTemplate.vm");
		mail.setTitle( title);
		
		User sendUser = new User();
		sendUser.setMail("admin@eptest.co.kr");
		
		mail.setToEmailList( receiverList);
		
		Map dataMap = new HashMap();
		dataMap.put("detailInfo", detailInfo);
		dataMap.put("mailType", 9);
		dataMap.put("url", collaboService.getServerURL( mainFrameUrl));
		
		mailSendService.sendMail(mail, dataMap, sendUser);
	}
	
	/**
	 * 파일 저장후 갱신데이터 리턴
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String ajaxUdateFile( JobReq jobReq, User user) throws Exception {
		
		updateFile( jobReq, user);
		JobReq resultTestRequest = this.getFileObject( jobReq, user);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put( "fileItemId", resultTestRequest.getFileItemId());
		resultMap.put( "fileDataListJson", resultTestRequest.getFileDataList());
		resultMap.put( "historyTxt", jobReq.getHistoryTxt()); //이력등록시 내용 세팅
		
		return CollaboUtils.convertJsonString( "datas", resultMap);
	}
	
	/**
	 * 파일정보 조회
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public JobReq getFileObject( JobReq jobReq, User user) throws Exception {
		
		if( StringUtils.isNotEmpty( jobReq.getFileItemId()) ){
			
			List<FileData> fileDataList = new ArrayList<FileData>();
			fileDataList = this.fileService.getItemFile( jobReq.getFileItemId(), NewProductDev.ITEM_FILE_TYPE);
			jobReq.setFileDataList( fileDataList);
			
			List<FileData> ecmFileDataList = new ArrayList<FileData>();
			ecmFileDataList = this.fileService.getItemFileEcm( jobReq.getFileItemId(), NewProductDev.ITEM_FILE_TYPE);
			jobReq.setEcmFileDataList( ecmFileDataList);
		}
		
		return jobReq;
	}
	
	/**
	 * 파일저장
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public String updateFile( JobReq jobReq, User user) throws Exception {
		
		String historyYn = jobReq.getHistoryYn();
		if(StringUtil.isEmpty(historyYn)) { //null방지 기본 이력 여부 세팅
			historyYn = "N";
		}
		
		jobReq.setSessionEmpNo( user.getEmpNo());
		jobReq.setSessionGoupId( user.getGroupId());
		
		String fileItemId = jobReq.getFileItemId();
		String jobReqMgntNo = jobReq.getJobReqMgntNo();
		
		if(historyYn.equals("Y")) { //검토이력인 경우
			fileItemId = idgenService.getNextId();
			jobReq.setFileItemId( fileItemId); //파일ID세팅
			jobReq.setWriteEmpNo(user.getEmpNo()); //작성사번 세팅
			
			jobReqDao.jobReqHistorySave(jobReq);
			sendApproveHistoryMail(jobReq.getJobReqMgntNo(), user);
		} else {
			// 1. 게시물 정보 가져오고
			// 1-1. 기존문서이면
			if( StringUtils.isNotEmpty( jobReqMgntNo) && !jobReqMgntNo.equals("undefined")){
				
				jobReq.setJobReqMgntNo( jobReq.getJobReqMgntNo());
				fileItemId  = jobReqDao.getFileId( jobReqMgntNo);
			}
			// 1. 게시물 X 파일등록이 최초이면 DB x
			// 2. 게시물 X 파일이 있으면 DB X
			// 3. 게시물 O 파일이 최초이면 DB 업데이트 <-- 일관되게 업데이트하자
			// 4. 게시물 O 파일이 있으면 DB 업데이트  <--무조건 되고 있고
			if( StringUtils.isEmpty( fileItemId)) {
				fileItemId = idgenService.getNextId();
				jobReq.setFileItemId( fileItemId);
				
				// 파일이 x, 게시물존재 O fileId 업데이트
				if( StringUtils.isNotEmpty( jobReqMgntNo)){
					jobReqDao.updateFileId( jobReq);
				}
			}
			
		}
		
		int attachCnt = 0;

		List<FileData> ecmFileDataList = this.fileService.getItemFileEcm( fileItemId, NewProductDev.ITEM_FILE_TYPE);
		
		List<FileData> tempFileDataList = this.fileService.getItemFile( fileItemId, NewProductDev.ITEM_FILE_TYPE);
		// 게시물의 첨부파일 갯수를 업데이트 한다.
		if (jobReq.getFileLinkList() != null) {

			int fileCount = 0;
			for (FileLink fileLink : jobReq.getFileLinkList()) {
				if (!fileLink.getFlag().equals("del")) {
					++fileCount;
					attachCnt++;
				}
			}

			jobReq.setAttachFileCount(fileCount);

		} else {
			if (tempFileDataList != null) {
				int fileCount = tempFileDataList.size();
				jobReq.setAttachFileCount(fileCount);

			}else{
				//boardItem.setAttachFileCount(0);
				attachCnt = ecmFileDataList.size();
				jobReq.setAttachFileCount(attachCnt);
			}
		}
		
		// 파일링크 생성
		if ( jobReq.getFileLinkList() != null) {
			
			this.fileService.saveFileLink( jobReq.getFileLinkList(), fileItemId, NewProductDev.ITEM_FILE_TYPE, user);
		}
		
		String [] ecmFileIds = StringUtils.split( jobReq.getEcmFileId(), "|");
		String [] ecmFileNames = StringUtils.split( jobReq.getEcmFileName(), "|");
		String [] ecmFilePaths = StringUtils.split( jobReq.getEcmFilePath(), "|");
		String [] ecmFileUrl1s = StringUtils.split( jobReq.getEcmFileUrl1(), "|");
		String [] ecmFileUrl2s = StringUtils.split( jobReq.getEcmFileUrl2(), "|");
		String [] ecmOldFiles = new String[ecmFileDataList.size()];
		String [] uploadFlgs = new String[ecmFileIds.length];
		int ecmOldCnt = 0;
		 
		for(FileData ecmFiles0 : ecmFileDataList) {
			ecmOldFiles[ecmOldCnt] = ecmFiles0.getFileName();
			ecmOldCnt++;
		}
		
		//첨부파일명 갱신
		FileData tempFileData = new FileData();
		for(FileData tempFiles : ecmFileDataList) {
			for(int i=0;i<ecmFileIds.length;i++){
				if(tempFiles.getFileName().equals(ecmFileIds[i])){
					tempFileData.setFileId(tempFiles.getFileId());
					tempFileData.setFileRealName(ecmFileNames[i]);
					this.fileDao.updateEcmFileName(tempFileData);
					this.fileDao.updateFileName(tempFileData);
					attachCnt = i+1;
				}
			}
		}
		
		for(int j = 0 ; j < ecmOldFiles.length ; j++) {
			for( int i = 0 ; i < ecmFileIds.length ; i++ ){
				if(ecmOldFiles[j].equals(ecmFileIds[i])){
					ecmOldFiles[j] = "";
				}
			}
		}
		
		for( int i = 0 ; i < ecmFileIds.length ; i++ ) {
			for(FileData ecmFiles2 : ecmFileDataList){
				if(ecmFiles2.getFileName().equals(ecmFileIds[i])){
					uploadFlgs[i] = "N";
				}
			}
		}
		FileLink fileLink1 = new FileLink();
		
		for(FileData ecmFiles3 : ecmFileDataList){
			for( int i = 0 ; i < ecmOldFiles.length ; i++ ) {
				if(ecmFiles3.getFileName().equals(ecmOldFiles[i])){
					fileLink1.setFileId(ecmFiles3.getFileid());
					fileLink1.setItemId( fileItemId);
					this.fileLinkDao.removeFileLink(fileLink1);
					this.fileLinkDao.removeEcmFileLink(fileLink1);
				}
			}
		}
		
		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");

		String uploadRootForFile = prop.getProperty("ikep4.support.fileupload.upload_root");
		String uploadFolderForFile = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder"));
		String tempUploadRoot = uploadRootForFile+uploadFolderForFile;
		String uploadRootForImage = prop.getProperty("ikep4.support.fileupload.upload_root_image");
		String uploadFolderForImage = getFilePath(prop.getProperty("ikep4.support.fileupload.upload_folder_image"));
		String tempUploadRootImage = uploadRootForImage+uploadFolderForImage;
		
		if(ecmFileIds.length > 0){
			for(int i = 0 ; i < ecmFileIds.length ; i++) {
				String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
				int tmpEcmFileExtsSize = tmpEcmFileExts.length-1;
				if(uploadFlgs[i] != "N"){
					
					/*File folder = new File(tempUploadRoot);
					if (!folder.exists()) {
						folder.mkdirs();
					}*/
					
					attachCnt++;
					
					String fileId = StringUtil.replaceQuot(EncryptUtil.encryptText(idgenService.getNextId()));
					FileData fileData = new FileData();
					fileData.setFileId(fileId);
					fileData.setFilePath(ecmFilePaths[i]);
					fileData.setFileUrl1(ecmFileUrl1s[i]);
					fileData.setFileUrl2(ecmFileUrl2s[i]);
					fileData.setFileRealName(ecmFileNames[i]);
					fileData.setFileSize(0);
					fileData.setFileName(ecmFileIds[i]);
					fileData.setFileContentsType(tmpEcmFileExts[tmpEcmFileExtsSize]);
					fileData.setFileExtension(tmpEcmFileExts[tmpEcmFileExtsSize]);
					fileData.setEditorAttach(0);
					fileData.setRegisterId(user.getUserId());
					fileData.setRegisterName(user.getUserName());
					fileData.setUpdaterId(user.getUserId());
					fileData.setUpdaterName(user.getUserName());
					this.fileDao.createEcmFile(fileData);
					
					fileData.setFileId(fileId);
					if(checkImageFile(ecmFileNames[i])){
						fileData.setFilePath(uploadFolderForImage);
					}else{
						fileData.setFilePath(uploadFolderForFile);
					}
					fileData.setFileRealName(ecmFileNames[i]);
					fileData.setFileSize(0);
					fileData.setFileName(ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExtsSize]);
					fileData.setFileContentsType(tmpEcmFileExts[tmpEcmFileExtsSize]);
					fileData.setFileExtension(tmpEcmFileExts[tmpEcmFileExtsSize]);
					fileData.setEditorAttach(0);
					fileData.setRegisterId(user.getUserId());
					fileData.setRegisterName(user.getUserName());
					fileData.setUpdaterId(user.getUserId());
					fileData.setUpdaterName(user.getUserName());
					this.fileDao.create(fileData);
					
					//ECM 파일 링크 정보 넣기
					FileLink fileLink = new FileLink();
					String fileLinkId = idgenService.getNextId();
					fileLink.setFileId(fileId);
					fileLink.setItemId( fileItemId);
					fileLink.setItemTypeCode(NewProductDev.ITEM_FILE_TYPE);
					fileLink.setFileLinkId(fileLinkId);
					fileLink.setRegisterId(user.getUserId());
					fileLink.setRegisterName(user.getUserName());
					fileLink.setUpdaterId(user.getUserId());
					fileLink.setUpdaterName(user.getUserName());
	
					this.fileLinkDao.createEcmFileLink(fileLink);
					
					fileLink.setFileId(fileId);
					fileLink.setItemId( fileItemId);
					fileLink.setItemTypeCode(NewProductDev.ITEM_FILE_TYPE);
					fileLink.setFileLinkId(fileLinkId);
					fileLink.setRegisterId(user.getUserId());
					fileLink.setRegisterName(user.getUserName());
					fileLink.setUpdaterId(user.getUserId());
					fileLink.setUpdaterName(user.getUserName());
	
					this.fileLinkDao.create(fileLink);
					
				}
				if(checkImageFile(ecmFileNames[i])){
					File folder = new File(tempUploadRootImage);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					//String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
					URL url = new URL(ecmFileUrl2s[i]);
					File srcFile2 = new File(tempUploadRootImage+"/"+ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExts.length-1]);
					FileUtils.copyURLToFile(url, srcFile2);
				}else{
					File folder = new File(tempUploadRoot);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					//String [] tmpEcmFileExts = StringUtils.split(ecmFileNames[i], ".");
					URL url = new URL(ecmFileUrl2s[i]);
					File srcFile2 = new File(tempUploadRoot+"/"+ecmFileIds[i]+"."+tmpEcmFileExts[tmpEcmFileExts.length-1]);
					FileUtils.copyURLToFile(url, srcFile2);
				}
			}
		}
		
		return CollaboUtils.convertJsonString( "fileItemId", fileItemId);
	}
	
	private String getFilePath(String path) {

		String date = DateUtil.getToday("yyyy-MM-dd");
		String yyyy = date.substring(0, 4);
		String mm = date.substring(5, 7);

		return path + yyyy + "/" + mm + "/" + date;
	}
	
	private boolean checkImageFile(String fileName) {

		Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
		String keywordList = prop.getProperty("ikep4.support.fileupload.image_file");

		Pattern p = Pattern.compile(keywordList, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(fileName);

		return m.find();
	}
	
}
