package com.lgcns.ikep4.support.partner.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.mail.MailConstant;
import com.lgcns.ikep4.support.mail.model.Mail;
import com.lgcns.ikep4.support.partner.dao.PartnerQualityClaimSellDao;
import com.lgcns.ikep4.support.partner.dao.PartnerQualityClaimSellMoreDao;
import com.lgcns.ikep4.support.partner.model.PartnerClaimSellHistory;
import com.lgcns.ikep4.support.partner.model.PartnerQualitySub;
import com.lgcns.ikep4.support.partner.search.PartnerQualityClaimSellMoreSearchCondition;
import com.lgcns.ikep4.support.partner.service.PartnerQualityClaimSellMoreService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.support.mail.service.MailSendService;

@Service
@Transactional
public class PartnerQualityClaimSellMoreServiceImpl extends GenericServiceImpl<PartnerQualitySub, String>implements PartnerQualityClaimSellMoreService {

    @Autowired
    private  IdgenService idgenService;
    
    @Autowired
    private PartnerQualityClaimSellMoreDao partnerQualityClaimSellMoreDao;
    
    @Autowired
    private PartnerQualityClaimSellDao partnerQualityClaimSellDao;
    
    private Properties prop = null;		
	private String serverIp = null;
	private String serverPort = null;
	
	@Autowired
	private MailSendService mailSendService;

    public String createQualityClaimSellMore( PartnerQualitySub partnerQualitySub ) {
        
        final String generatedId = this.idgenService.getNextId();
        partnerQualitySub.setQualityClaimSellMoreId( generatedId );
        partnerQualitySub.setQualityClaimSellMoreGroupId( generatedId );
        
        final String insertedId = this.partnerQualityClaimSellMoreDao.create( partnerQualitySub );
        
       /* if(partnerQualitySub.getItemType().equals( "CL" )){
            //this.partnerQualityClaimSellDao.updateQualityClaimSellMoreCount(partnerQualitySub.getItemId());
        	PartnerClaimSellHistory qualityClaimSell = new PartnerClaimSellHistory();
        	qualityClaimSell.setSeqNum(Integer.parseInt(partnerQualitySub.getItemId()));
        	qualityClaimSell.setItemId(partnerQualitySub.getItemId());
        	qualityClaimSell.setSubId(partnerQualitySub.getLinereplyId());
        	this.partnerQualityClaimSellDao.updateQualityClaimSellMoreInfo(qualityClaimSell);
        }
        */
        return insertedId;
      
    }

    public SearchResult<PartnerQualitySub> getPartnerQualityClaimSellMore(PartnerQualityClaimSellMoreSearchCondition searchCondition ){
        Integer count = this.partnerQualityClaimSellMoreDao.countBySearchCondition(searchCondition);
        
        searchCondition.terminateSearchCondition( count );
        
        SearchResult<PartnerQualitySub> result = null;
        
        if(searchCondition.isEmptyRecord()){
            result = new SearchResult<PartnerQualitySub>( searchCondition );
        }else{
            List<PartnerQualitySub> partnerQualityClaimSellMoreList = this.partnerQualityClaimSellMoreDao.listBySearchCondition(searchCondition);
            result = new SearchResult<PartnerQualitySub>( partnerQualityClaimSellMoreList , searchCondition );
        }
        
        return result;
        
    }
    
    public SearchResult<PartnerQualitySub> getPartnerQualityClaimSellMorePrint( int itemId ){
    	PartnerQualityClaimSellMoreSearchCondition searchCondition = new PartnerQualityClaimSellMoreSearchCondition();
    	searchCondition.setItemId(Integer.toString(itemId));
    	searchCondition.setItemType("CL");
        Integer count = this.partnerQualityClaimSellMoreDao.countBySearchCondition(searchCondition);
        
        searchCondition.terminateSearchCondition( count );
        
        SearchResult<PartnerQualitySub> result = null;
        
        if(searchCondition.isEmptyRecord()){
            result = new SearchResult<PartnerQualitySub>( searchCondition );
        }else{
            List<PartnerQualitySub> partnerQualityClaimSellMoreList = this.partnerQualityClaimSellMoreDao.listBySearchCondition(searchCondition);
            result = new SearchResult<PartnerQualitySub>( partnerQualityClaimSellMoreList , searchCondition );
        }
        
        return result;
        
    }
    
    
    public String createReplyQualityClaimSellMore( PartnerQualitySub partnerQualitySub ){
        
    	PartnerQualitySub parent = this.partnerQualityClaimSellMoreDao.get( partnerQualitySub.getQualityClaimSellMoreParentId() );
        
    	partnerQualitySub.setItemId( parent.getItemId() );
    	partnerQualitySub.setQualityClaimSellMoreGroupId( parent.getQualityClaimSellMoreGroupId() );
    	partnerQualitySub.setIndentation( parent.getIndentation() + 1 );
    	partnerQualitySub.setQualityClaimSellMoreDelete( 0 );
    	partnerQualitySub.setStep( parent.getStep()+1 );

        final String generatedId = this.idgenService.getNextId();
        
        partnerQualitySub.setQualityClaimSellMoreId( generatedId );
        
        //스텝값을 업데이트한다.(기존에 있던 데이터)
        this.partnerQualityClaimSellMoreDao.updateStep(partnerQualitySub);
        
        final String insertedId = this.partnerQualityClaimSellMoreDao.create( partnerQualitySub );
        
        if(partnerQualitySub.getItemType().equals( "CL" )){
            this.partnerQualityClaimSellDao.updateQualityClaimSellMoreCount(partnerQualitySub.getItemId());
        }
        
        return insertedId;
    }
    
    public void delete( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public PartnerQualitySub read( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void update( PartnerQualitySub arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public PartnerQualitySub readPartnerQualityClaimSellMore( String linereplyId ) {
    	PartnerQualitySub partnerQualitySub = this.partnerQualityClaimSellMoreDao.get( linereplyId );
        return partnerQualitySub;
    }

    public void updatePartnerQualityClaimSellMore( PartnerQualitySub readPartnerQualityClaimSellMore ) {
        this.partnerQualityClaimSellMoreDao.update( readPartnerQualityClaimSellMore );
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void sendMail(User user, PartnerQualitySub readPartnerQualityClaimSellMore ) {
    	List<User> recipientList = new ArrayList<User>();
		
		int i=0;
		
		recipientList = partnerQualityClaimSellMoreDao.getUserInfoList(user.getUserId());
		
		int len = recipientList.size();
		if(len < 1) {
			return;
		}
		
		/*
		for (Iterator iterator = participiantList.iterator(); iterator.hasNext();) {
			Participant participant = (Participant) iterator.next();
			userIds[i++] = participant.getTargetUserId();
		}
	
		recipientList = alarmDao.getUserInfoList(userIds);
		
		int len = recipientList.size();
		if(len < 1) {
			return;
		}
		
		String senderId = schedule.getRegisterId();
		List<User> senderList = alarmDao.getUserInfoList(new String[]{senderId});*/
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("partnerMailTemplate.vm");
		

		// 발신자
		//User sender = senderList.get(0);

		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		for (i = 0; i < len; i++) {
			User rm = (User) recipientList.get(i);
			recip = new HashMap<String, String>();

			recip.put("email", rm.getMail());
			recip.put("name", rm.getUserName());

			recipients.add(recip);
		}
		mail.setToEmailList(recipients);
		
		Map dataMap = new HashMap();
		
		mail.setTitle("거래처 상담내역이 등록되었습니다");
		
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
        String baseUrl =commonprop.getProperty("ikep4.baseUrl");
		String url = "";
		url = baseUrl+"/support/partner/partnerQualityClaimSell/directDetailQualityClaimSell.do?seqNum="+readPartnerQualityClaimSellMore.getItemId()+"&linereplyId="+readPartnerQualityClaimSellMore.getLinereplyId();
		
		dataMap.put("partner", readPartnerQualityClaimSellMore.getPartnerName());
		dataMap.put("regdate", readPartnerQualityClaimSellMore.getCounselDate());
		dataMap.put("register", readPartnerQualityClaimSellMore.getCounselor());
		dataMap.put("title", readPartnerQualityClaimSellMore.getCounselTitle());
		dataMap.put("url", url);
		
		User sender = user;

		mailSendService.sendMail(mail, dataMap, sender);
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void sendCommentMail(User user, PartnerQualitySub readPartnerQualityClaimSellMore ) {
    	List<User> recipientList = new ArrayList<User>();
		
		int i=0;
		
		recipientList = partnerQualityClaimSellMoreDao.getUserInfoList2(readPartnerQualityClaimSellMore.getLinereplyId());
		
		PartnerQualitySub tempPartnerQualitySub = this.partnerQualityClaimSellMoreDao.get( readPartnerQualityClaimSellMore.getLinereplyId() );
		
		int len = recipientList.size();
		if(len < 1) {
			return;
		}
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("partnerMailTemplate.vm");
		

		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		for (i = 0; i < len; i++) {
			User rm = (User) recipientList.get(i);
			recip = new HashMap<String, String>();

			recip.put("email", rm.getMail());
			recip.put("name", rm.getUserName());

			recipients.add(recip);
		}
		mail.setToEmailList(recipients);
		
		Map dataMap = new HashMap();
		
		mail.setTitle("거래처 상담내역에 Comment가 등록되었습니다");
		
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
        String baseUrl =commonprop.getProperty("ikep4.baseUrl");
		String url = "";
		url = baseUrl+"/support/partner/partnerQualityClaimSell/directDetailQualityClaimSell.do?seqNum="+readPartnerQualityClaimSellMore.getItemId()+"&linereplyId="+readPartnerQualityClaimSellMore.getLinereplyId();
		
		dataMap.put("partner", tempPartnerQualitySub.getPartnerName());
		dataMap.put("regdate", tempPartnerQualitySub.getCounselDate());
		dataMap.put("register", tempPartnerQualitySub.getCounselor());
		dataMap.put("title", tempPartnerQualitySub.getCounselTitle());
		dataMap.put("url", url);
		
		User sender = user;

		mailSendService.sendMail(mail, dataMap, sender);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void sendCommentMailSub(User user, PartnerQualitySub readPartnerQualityClaimSellMore ) {
    	List<User> recipientList = new ArrayList<User>();
		
		int i=0;
		
		recipientList = partnerQualityClaimSellMoreDao.getUserInfoList3(readPartnerQualityClaimSellMore.getLinereplyId());
		
		PartnerQualitySub tempPartnerQualitySub = this.partnerQualityClaimSellMoreDao.get( readPartnerQualityClaimSellMore.getLinereplyId() );
		
		int len = recipientList.size();
		if(len < 1) {
			return;
		}
		
		Mail mail = new Mail();

		mail.setMailType(MailConstant.MAIL_TYPE_TEPLATE);
		mail.setMailTemplatePath("partnerMailTemplate.vm");
		

		// 수신자
		List recipients = new ArrayList();
		HashMap<String, String> recip;
		for (i = 0; i < len; i++) {
			User rm = (User) recipientList.get(i);
			recip = new HashMap<String, String>();

			recip.put("email", rm.getMail());
			recip.put("name", rm.getUserName());

			recipients.add(recip);
		}
		mail.setToEmailList(recipients);
		
		Map dataMap = new HashMap();
		
		mail.setTitle("거래처 상담내역에 Comment가 등록되었습니다");
		
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
        String baseUrl =commonprop.getProperty("ikep4.baseUrl");
		String url = "";
		url = baseUrl+"/support/partner/partnerQualityClaimSell/directDetailQualityClaimSell.do?seqNum="+readPartnerQualityClaimSellMore.getItemId()+"&linereplyId="+readPartnerQualityClaimSellMore.getLinereplyId();
		
		dataMap.put("partner", tempPartnerQualitySub.getPartnerName());
		dataMap.put("regdate", tempPartnerQualitySub.getCounselDate());
		dataMap.put("register", tempPartnerQualitySub.getCounselor());
		dataMap.put("title", tempPartnerQualitySub.getCounselTitle());
		dataMap.put("url", url);
		
		User sender = user;

		mailSendService.sendMail(mail, dataMap, sender);
    }
    
    public void updatePartnerTlComment( PartnerQualitySub readPartnerQualityClaimSellMore ) {
        this.partnerQualityClaimSellMoreDao.updateTlComment( readPartnerQualityClaimSellMore );
    }
    
    public void updatePartnerOfComment( PartnerQualitySub readPartnerQualityClaimSellMore ) {
        this.partnerQualityClaimSellMoreDao.updateOfComment( readPartnerQualityClaimSellMore );
    }
    
    public void updatePartnerQualityClaimSellMaster( PartnerQualitySub readPartnerQualityClaimSellMore ) {
        this.partnerQualityClaimSellMoreDao.updateSubMaster( readPartnerQualityClaimSellMore );
    }

    public void userDeleteQualityClaimSellMore( PartnerQualitySub partnerQualitySub ){
        //하위 댓글 개수를 구함
        Integer count = this.partnerQualityClaimSellMoreDao.countChildren(partnerQualitySub.getQualityClaimSellMoreId());
        
        //하위 댓글이 존재하지 않으면
        if(count == 0 ){
            this.adminDeleteQualityClaimSellMore(partnerQualitySub.getItemId(), partnerQualitySub.getQualityClaimSellMoreId(),partnerQualitySub.getItemType() );
        }else{ //하위 댓글이 존재하면
            //삭제 여부 필드를 "1"로 업데이트 한다.
            this.partnerQualityClaimSellMoreDao.logicalDelete(partnerQualitySub);
        }  
    }
    
    /*
     * (non-Javadoc)
     * @see com.lgcns.ikep4.lightpack.board.service.BoardQualityClaimSellMoreService#
     * adminDeleteBoardQualityClaimSellMore(java.lang.String, java.lang.String)
     */
    public void adminDeleteQualityClaimSellMore(String itemId, String linereplyId, String itemType) {
        

        Integer childrenCount = this.partnerQualityClaimSellMoreDao.countChildren(linereplyId);

        if (childrenCount == 0) {
            this.partnerQualityClaimSellMoreDao.physicalDelete(linereplyId);

        } else {
            this.partnerQualityClaimSellMoreDao.physicalDeleteThreadByQualityClaimSellMoreId(linereplyId);
        }

        

    }

    
}
