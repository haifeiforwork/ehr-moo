/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.collpack.expertnetwork.model.ExpertNetworkList;
import com.lgcns.ikep4.collpack.expertnetwork.service.ExpertNetworkListService;
import com.lgcns.ikep4.collpack.qna.constants.QnaConstants;
import com.lgcns.ikep4.collpack.qna.dao.QnaDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaExpertDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaLinereplyDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaRecommendDao;
import com.lgcns.ikep4.collpack.qna.dao.QnaReferenceDao;
import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaExpert;
import com.lgcns.ikep4.collpack.qna.model.QnaReference;
import com.lgcns.ikep4.collpack.qna.service.QnaExpertService;
import com.lgcns.ikep4.collpack.qna.service.QnaService;
import com.lgcns.ikep4.support.activitystream.service.ActivityStreamService;
import com.lgcns.ikep4.support.favorite.service.PortalFavoriteService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.mail.service.MailSendService;
import com.lgcns.ikep4.support.message.model.Message;
import com.lgcns.ikep4.support.message.service.MessageOutsideService;
import com.lgcns.ikep4.support.sms.model.Sms;
import com.lgcns.ikep4.support.sms.service.SmsService;
import com.lgcns.ikep4.support.tagging.constants.TagConstants;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.tagging.service.TagService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.tagfree.util.MimeUtil;


/**
 * BoardService 구현 클래스
 * 
 * @author 이동희(loverfairy@gmail.com)
 * @version $Id: QnaServiceImpl.java 16582 2011-09-21 05:30:29Z giljae $
 */
@Service("qnaService")
public class QnaServiceImpl extends GenericServiceImpl<Qna, String> implements QnaService {

	@Autowired
	private QnaDao qnaDao;
	
	@Autowired
	private QnaExpertDao qnaExpertDao;
	
	@Autowired
	private QnaLinereplyDao qnaLinereplyDao;
	
	@Autowired
	private QnaRecommendDao qnaRecommendDao;
	
	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private QnaExpertService qnaExpertService;
	
	@Autowired
	private FileService fileService; 
	
	@Autowired
	private TagService tagService; 
	
	@Autowired
	private ExpertNetworkListService expertItemService;
	
	@Autowired
	protected MessageSource messageSource = null;
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private MessageOutsideService messageOutsideService;
	
	@Autowired
	private MailSendService mailSendService;
	
	@Autowired
	private QnaReferenceDao qnaReferenceDao;
	
	@Autowired
	private ActivityStreamService activityStreamService;
	
	@Autowired
	private PortalFavoriteService portalfavoriteService;

	

	public String create(Qna qna, User user) {
		
		String id = idgenService.getNextId();
		
		qna.setQnaId(id);
		
		//질문이면
		if(qna.getQnaGroupId()== null){
			qna.setQnaGroupId(id);
		} 
		
		
		//첨부파일 업데이트
		if(qna.getFileLinkList() != null) {
			qna.setAttachFileCount(qna.getFileLinkList().size());
			
			fileService.saveFileLink(qna.getFileLinkList(), qna.getQnaId(), IKepConstant.ITEM_TYPE_CODE_QA, user);
		} 
		
		qnaDao.create(qna);
		
		
		//전문가 의뢰 있으면 전문가 등록
		qnaExpertService.createByQna(qna, user);
		
		//답변이면
		if(qna.getQnaType() == QnaConstants.IS_REPLY){
			
			//질문 답변수 업데이트
			qnaDao.updateReplyCount(qna.getQnaGroupId());
			
			//자식 채택된 상태에 따라  질문 상태값 변경
			updateSatus(qna.getQnaGroupId());
			
			
			//답변 시간 체크
			Qna parentQna = qnaDao.get(qna.getQnaGroupId());
			
			if(parentQna.getAnswerNecessaryTime() == 0){	//첫 답변만 시간타임 넣기
				
				Qna childQna = qnaDao.get(qna.getQnaId());
				
				Date parendDate = parentQna.getRegistDate();
				Date childDate = childQna.getRegistDate();

				long diffDate = (childDate.getTime() - parendDate.getTime())/(QnaConstants.MILLION_SECOND * QnaConstants.TIME_SECOND);
				
				double diff = Integer.parseInt(String.valueOf(diffDate))/QnaConstants.TIME_SECOND_LONG;
				
				diff = Double.parseDouble(String.format("%.1f", diff));
				
				qnaDao.updateAnswerNecessaryTime(qna.getQnaGroupId(), diff);
			}
			
			//알람 하기 - 답변확정 받지 않으면 알림 계속 하기
			if(!parentQna.getStatus().equals(QnaConstants.STATUS_COMPLETE)){
				
				//메세지 보내기 내용
				String urgentMsg = "";
				if(qna.getUrgent() == 1){
					urgentMsg = getMessage("message.collpack.qna.alarm.mail.urgent")+ " ";
				}
				
				String contentsTitle = "Q&A 제목:"+urgentMsg + parentQna.getTitle()+"<br/>";
				String contentsBody = "답변 등록 시간: "+DateUtil.getTodayDateTime("yyyy.MM.dd HH:mm:ss")+ "<br/><br/>" +getMessage("message.collpack.qna.alarm.mail.contents",new String[]{qna.getRegisterName()})+"<br/><br/>답변을 확인하시길 바랍니다.";
				String contentsQnaTitle = " "+qna.getTitle();
				
				StringBuffer massage = new StringBuffer();
				
				massage.append(contentsTitle);
				massage.append(contentsBody);
				massage.append(contentsQnaTitle);
				
				StringBuffer mailMassage = new StringBuffer();
				
				mailMassage.append(contentsTitle);
				String contensBody1 = "<br/>"+contentsBody;
				mailMassage.append(contensBody1);
				String contensBody2 = "<br/><br/><a href=\""+qna.getQnaPathUrl()+"/getQna.do?qnaId="+parentQna.getQnaId()+"\">"+contentsQnaTitle+"</a>";
				mailMassage.append(contensBody2);
				
				String massageTitle = getMessage("message.collpack.qna.alarm.mail.title");
				
				char[] channelCheck = parentQna.getAlarmChannel().toCharArray();
				char mailChannel = 0;
				char smsChannel = 0;
				char messageChannel = 0;
				
				
				try{mailChannel = channelCheck[0];}catch(Exception e){}
				try{smsChannel = channelCheck[1];}catch(Exception e){}
				try{messageChannel = channelCheck[2];}catch(Exception e){}
				
				
				//SMS보내기
				if (smsChannel=='1' && !StringUtil.isEmpty(parentQna.getMobile())) {
					
					Sms sms = new Sms();

					sms.setReceiverId(parentQna.getRegisterId());
					sms.setReceiverPhoneno(parentQna.getMobile());
					sms.setContents(massage.toString());
					sms.setRegisterId(qna.getRegisterId());
					sms.setRegisterName(qna.getRegisterName());	

					smsService.create(sms);
				}
				
				//메일보내기
				if (mailChannel == '1' && !StringUtil.isEmpty(parentQna.getMail())) {
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("email", parentQna.getMail());
					map.put("name", parentQna.getRegisterName());
					
					List mailList = new ArrayList();	//메일은 
					
					mailList.add(map);
					
					Mail mail = new Mail();
					
					Properties prop = PropertyLoader.loadProperties("/configuration/mail-conf.properties");
					String mailDomain = prop.getProperty("ikep4.support.mail.domain");
					
                    mail.setFromEmail("admin@"+mailDomain);
					
					mail.setToEmailList(mailList);

					mail.setMailType(MailConstant.MAIL_TYPE_HTML);

					mail.setTitle(massageTitle);
					mail.setContent(mailMassage.toString());

					Map dataMap = new HashMap();
					
					mailSendService.sendMail(mail, dataMap, user);
					

					//mailSendService.sendMail(mail, dataMap, user);
				}
				
				
				//쪽지보내기
				if (messageChannel=='1') {
					
					Message message = new Message();
					message.setSenderId(qna.getRegisterId());
					message.setSenderName(qna.getRegisterName());
					message.setContents(mailMassage.toString());
					
					message.setReceiverList(parentQna.getRegisterId());
					
					//messageOutsideService.sendMessageOutside(message, user);
				}
				
			}
		} 
		
		//태그 등록	- 태그 있을때 등록
		createQnaTag(qna);
		
		
		//파일업로드
		if(qna.getEditorFileLinkList() != null) {

			fileService.saveFileLinkForEditor(qna.getEditorFileLinkList(), qna.getQnaId(), IKepConstant.ITEM_TYPE_CODE_QA, user);
	
		}
		
		
		//게시물(방명록,댓글,블로그) 등록/수정/삭제,  멤버가입/탈퇴, 프로파일수정 등의 ActivityStream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_QA, IKepConstant.ACTIVITY_CODE_DOC_POST, qna.getQnaGroupId(), qna.getTitle());

		return id;
	}
	


	public Qna read(String qnaId) {
		
		Qna qna = qnaDao.get(qnaId);
		
		if(qna != null){
			
			List<FileData> fileDataList = fileService.getItemFile(qnaId,"N");
			qna.setFileDataList(fileDataList);
			
			List<Tag> tagList = tagService.listTagByItemId(qnaId,  TagConstants.ITEM_TYPE_QNA);
			qna.setTagList(tagList); 
			
		}
		
		return qna;
	}
	
	

	public List<Qna> readGroup(String groupId, String registerId) {
		
		List<Qna> list = qnaDao.selectByGroup(groupId);
		
		List<FileData> fileDataList = null; 
		List<Tag> tagList = null; 
		
		for(Qna qna : list) {
			
			fileDataList = fileService.getItemFile(qna.getQnaId(),"N"); 
			qna.setFileDataList(fileDataList); 
			
			tagList = tagService.listTagByItemId(qna.getQnaId(),  TagConstants.ITEM_TYPE_QNA);
			qna.setTagList(tagList); 
			
			//hit업데이트 - 전체 그룹 업데이트
			int count = qnaReferenceDao.getCountByRegisterId(qna.getQnaId(), registerId);
			
			if(count == 0){
				
				QnaReference qnaReference = new QnaReference();
				qnaReference.setQnaId(qna.getQnaId());
				qnaReference.setRegisterId(registerId);
				
				qnaReferenceDao.create(qnaReference);
				
				qnaDao.updateHit(qna.getQnaId());
				
				//부모 조회수 더하기
				qna.setHitCount(qna.getHitCount()+1);
				
			}
			
		}
		
		return list;
	}
	

	/**
	 * 공통 베스트 질문, 답변 가져오기
	 * @param line
	 * @return
	 */
	public String readBestQnaId(int type, String portalId, String newDate, int line ) {
		
		List<String> qnaIdList = qnaDao.selectBestQnaId(type, portalId, newDate, line);
		
		String qnaId = "";
		
		if(qnaIdList.size() > 0){
			
			Random random = new Random();
			int ran = random.nextInt(qnaIdList.size());
			
			qnaId =  qnaIdList.get(ran);
			
		}
		
		return qnaId;
		
	}
	
	
	public List<QnaExpert> listMainExpert(String partalId) {
		
		List<QnaExpert> qnaExpertList = new ArrayList<QnaExpert>();
		
		Properties prop = PropertyLoader.loadProperties("/configuration/qna-conf.properties");
		String pagePer = prop.getProperty("qna.main.expert.qnaList.pagePer");	
		int expertUserPer = Integer.parseInt(prop.getProperty("qna.main.expert.user.pagePer"));	
		
		List<ExpertNetworkList> expertNetworkList = expertItemService.listRandom(expertUserPer, partalId);
		
		for(ExpertNetworkList expertItem : expertNetworkList){
			
			if(expertItem != null){
				QnaExpert qnaExpert = new QnaExpert();
				qnaExpert.setExpertId(expertItem.getUserId());
				qnaExpert.setUserName(expertItem.getUserName());
				qnaExpert.setUserEnglishName(expertItem.getUserEnglishName());
				qnaExpert.setTeamName(expertItem.getTeamName());
				qnaExpert.setTeamEnglishName(expertItem.getTeamEnglishName());
				qnaExpert.setJobTitleName(expertItem.getJobTitleName());
				qnaExpert.setJobTitleEnglishName(expertItem.getJobTitleEnglishName());
				qnaExpert.setMail(expertItem.getMail());
				qnaExpert.setMobile(expertItem.getMobile());
				qnaExpert.setPicturePath(expertItem.getPicturePath());
				qnaExpert.setProfilePicturePath(expertItem.getProfilePicturePath());
				
				Qna qnaSearch = new Qna();
				qnaSearch.setRegisterId(expertItem.getUserId());
				qnaSearch.setStartRowIndex(0);
				qnaSearch.setEndRowIndex(Integer.parseInt(pagePer));
				qnaSearch.setPortalId(partalId);
				qnaSearch.setQnaType(QnaConstants.IS_REPLY);
				
				qnaExpert.setQnaList(qnaDao.listAll(qnaSearch));
				
				qnaExpertList.add(qnaExpert);
			}
		}
		
		return qnaExpertList;
	}


	public List<Qna> listRelation(Qna qnaSearch) {
		
		List<Qna> list = qnaDao.listRelation(qnaSearch);
		
		return list;
	}
	

	public int readCountRelation(Qna qnaSearch) {
		return qnaDao.selectCountRelation(qnaSearch);
	}


	public void update(Qna qna, User user) {
		
		//첨부파일 업데이트
		if(qna.getFileLinkList() != null) {
			
			int fileCount = 0;
			for(FileLink fileLink :qna.getFileLinkList()){
				if(!fileLink.getFlag().equals("del")){
					++fileCount;
				}
			}
			
			qna.setAttachFileCount(fileCount);
			
			fileService.saveFileLink(qna.getFileLinkList(), qna.getQnaId(), IKepConstant.ITEM_TYPE_CODE_QA, user);
		} else {
			qna.setAttachFileCount(0);
		}
		
		
		//태그 등록	- 태그 있을때 등록
		createQnaTag(qna);
		
		qnaDao.update(qna);
		
		//전문가 의뢰 있으면 전문가 등록
		qnaExpertService.createByQna(qna, user);
		
		
		//게시물(방명록,댓글,블로그) 등록/수정/삭제,  멤버가입/탈퇴, 프로파일수정 등의 ActivityStream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_QA, IKepConstant.ACTIVITY_CODE_DOC_EDIT, qna.getQnaGroupId(), qna.getTitle());

	}
	
	
	public List<Qna> list(Qna qnaSearch) {
		return qnaDao.listAll(qnaSearch);
	}
	
	public int listCount(Qna qnaSearch) {
		
		return qnaDao.selectCount(qnaSearch);
	}

	public void approveAdopt(String qnaId) {
		updateAdopt(qnaId, QnaConstants.ADOPT_OK);
	}
	
	public void cancelAdopt(String  qnaId) {
		updateAdopt(qnaId, QnaConstants.ADOPT_NO);
	}

	public void updateAdopt(String qnaId, int answerAdopt) {
		
		Qna qna =  qnaDao.get(qnaId);
		
		qnaDao.updateAnswerAdopt(qnaId, answerAdopt);
		
		//자식 채택된 상태에 따라 질문 상태값 변경
		updateSatus(qna.getQnaGroupId());
		
	}
	
	
	/**
	 * 삭제 플래그
	 */
	public void approveDeleteItem(String qnaId) {
		
		//자식 글이 있는지 검사
		Qna qna = qnaDao.get(qnaId);
		
		if(qna.getReplyCount() > 0){	//자식글 있으면 삭제플래그 업데이트
			updateItemDelte(qnaId, QnaConstants.ITEM_DELETE_OK);
		
		} else {		//자식글 없으면 리얼삭제
			delete(qnaId);
		}
		
	}
	
	/**
	 * 삭제 플래그 삭제
	 */
	public void cancleDeleteItem(String qnaId) {
		
		updateItemDelte(qnaId, QnaConstants.ITEM_DELETE_NO);
		
	}
	
	/**
	 * 임시 삭제 공통 함수
	 */
	private void updateItemDelte(String qnaId, int itemDelete) {
		
		qnaDao.updateItemDelete(qnaId, itemDelete);
		
		Qna qna = qnaDao.get(qnaId);
		
		//답변이면 질문 변경
		if(qna.getQnaType() == QnaConstants.IS_REPLY){		
			//답글 수 변경
			qnaDao.updateReplyCount(qna.getQnaGroupId());	
			
			//질문 상태 변경
			updateSatus(qna.getQnaGroupId());
		}
		
	}
	
	public void delete(String qnaId) {

		Qna qna = qnaDao.get(qnaId);
		
		
		//답변이면 
		if(qna.getQnaType() == QnaConstants.IS_REPLY){	
			
			deleteQna(qnaId);
			
			//답글 수 변경
			qnaDao.updateReplyCount(qna.getQnaGroupId());
			
			//질문 상태 변경
			updateSatus(qna.getQnaGroupId());
			
		} else {	//질문이면 그룹 삭제
			
			//전문가 삭제
			qnaExpertDao.removeByQnaId(qnaId);
			
			//답변 qna 도 모두 삭제
			List<String> childIdList = qnaDao.listChildId(qnaId);
			
			for(String repltyId: childIdList){
				
				deleteQna(repltyId);
			
			}
			
			deleteQna(qnaId);
		}
		
		//게시물(방명록,댓글,블로그) 등록/수정/삭제,  멤버가입/탈퇴, 프로파일수정 등의 ActivityStream 등록
		activityStreamService.create(IKepConstant.ITEM_TYPE_CODE_QA, IKepConstant.ACTIVITY_CODE_DOC_DELETE, qna.getQnaGroupId(), qna.getTitle());

		
		//즐겨찾기 삭제
		portalfavoriteService.delete(qnaId, qna.getRegisterId());

		//파일 삭제
		fileService.removeItemFile(qnaId);
	}
	
	private void deleteQna(String qnaId){
		//한줄리플 삭제
		qnaLinereplyDao.removeByQnaId(qnaId);
	
		//추천 삭제
		qnaRecommendDao.removeByQnaId(qnaId);
	
		//태그 삭제
		tagService.delete(qnaId, TagConstants.ITEM_TYPE_QNA);
		
		//조회목록 삭제
		qnaReferenceDao.removeQna(qnaId);
		
		//전체 파일 삭제
		fileService.removeItemFile(qnaId);
		
		//qna 삭제
		qnaDao.remove(qnaId);
	}
	
	/**
	 * 상태변경 공통 함수
	 * @param qnaGroupId TODO
	 */
	private void updateSatus(String qnaGroupId){
		
		//자식 채택된 상태에 따라 질문 상태값 변경
		int groupAdoptStatus = qnaDao.selectGroupAdoptStatus(qnaGroupId);
		String status = "";
		
		if(groupAdoptStatus == QnaConstants.ADOPT_OK){
			status = QnaConstants.STATUS_COMPLETE;
		} else if(groupAdoptStatus == QnaConstants.ADOPT_NO){
			status = QnaConstants.STATUS_REPLY;
		} else {
			status = QnaConstants.STATUS_NOREPLY;
		}
		
		qnaDao.updateStatus(qnaGroupId, status);
	}


	/**
	 * 즐겨찾기 업데이트 공통 함수
	 */
	public void updateFavorite(String qnaGroupId, int favoriteCount) {
		
		qnaDao.updateGroupFavoriteCount(qnaGroupId, favoriteCount);
		
	}
	
	
	public void updateMailCount(String qnaId) {
		qnaDao.updateMailCount(qnaId);
	}


	public void updateMblogCount(String qnaId) {
		qnaDao.updateMblogCount(qnaId);
	}


	/**
	 * 태그 등록 - qna 등록, 수정시 사용
	 * @param qna
	 */
	private void createQnaTag(Qna qna){
		
		//태그 등록	- 태그 있을때 등록
		if(!StringUtil.isEmpty(qna.getTag())){
			Tag tag = new Tag();
			String tagSubType = "";
			
			if(qna.getQnaType() == QnaConstants.IS_QNA){
				tagSubType = QnaConstants.ITEM_SUB_TYPE_QNA;
			} else {
				tagSubType = QnaConstants.ITEM_SUB_TYPE_ANSWER;
			}
			
			tag.setTagName(qna.getTag());										//사용자가 작성한 tag
			tag.setTagItemId(qna.getQnaId());									//게시물 ID
			tag.setTagItemType(TagConstants.ITEM_TYPE_QNA);						//모듈 타입 TagConstants에 정의되어 있음.
			tag.setTagItemSubType(tagSubType);									//모듈 서브 타입  - 있을때만 넣기
			tag.setTagItemName(qna.getTitle());									//게시물 제목
			tag.setTagItemContents(qna.getContents());  						//게시물 내용
			tag.setTagItemUrl("/collpack/qna/getQna.do?docPopup=true&qnaId="+qna.getQnaGroupId());	//게시물 팝업창 url
			tag.setRegisterId(qna.getRegisterId());
			tag.setRegisterName(qna.getRegisterName());
			tag.setPortalId(qna.getPortalId());
			
			tagService.create(tag);
		}
	}

	/**
	 * 리소스번들에서 메시지 값 조회
	 * 
	 * @param messageCode 리소스번들의 코드값
	 * @param messageParameters array of arguments that will be filled in for
	 *            params within the message (params look like "{0}", "{1,date}",
	 *            "{2,time}" within a message)
	 * @param defaultMessage 리소스번들 조회 실패시 기본 제공 메시지
	 * @return 코드값에 해당하는 리소스 값
	 */
	public String getMessage(String messageCode, Object[] messageParameters, String defaultMessage) {
		Locale locale = Locale.getDefault();
		return messageSource.getMessage(messageCode, messageParameters, defaultMessage, locale);
	}
	
	public String getMessage(String messageCode) {
		return getMessage(messageCode, null, null);
	}
	
	public String getMessage(String messageCode, Object[] messageParameters) {
		return getMessage(messageCode, messageParameters, null);
	}
	
}
