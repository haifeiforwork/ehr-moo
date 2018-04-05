/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Path.Node;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import javax.validation.metadata.ConstraintDescriptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lgcns.ikep4.collpack.qna.constants.QnaConstants;
import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.collpack.qna.model.QnaCategory;
import com.lgcns.ikep4.collpack.qna.model.QnaStatistics;
import com.lgcns.ikep4.collpack.qna.service.QnaCategoryService;
import com.lgcns.ikep4.collpack.qna.service.QnaService;
import com.lgcns.ikep4.collpack.qna.service.QnaStatisticsService;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;


/**
 * 모두 상속받는 controller
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $$Id: QnaBaseController.java 16862 2011-10-21 05:01:40Z giljae $$
 */
@RequestMapping(value = "/collpack/qna")
public class QnaBaseController extends BaseController {

	@Autowired
	private QnaService qnaService;
	
	@Autowired
	private QnaCategoryService qnaCategoryService;
	
	@Autowired
	private QnaStatisticsService qnaStatisticsService;
	
	 @Autowired
	 ACLService aclService;

	/**
	 * 공통으로 가져감 카테고리 리스트
	 * @return
	 */
	@ModelAttribute("categoryList")
	public List<QnaCategory> getCategoryList(HttpServletRequest request) {
		
		String requestUri = request.getRequestURI();
		
		List<QnaCategory> list = new ArrayList<QnaCategory>();
		if(!requestUri.contains("Reply.do") && !requestUri.contains("Ajax.do")& !requestUri.contains("More.do")& !requestUri.contains("qnaTag.do")){
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			if(portal != null){
				list = qnaCategoryService.list(portal.getPortalId());
			}
			
		} 
		
		return list;
	}
	
	
	/**
	 * 공통으로 가져감 카테고리 리스트
	 * @return
	 */
	@ModelAttribute("myQnaCount")
	public int getCountMyQna(HttpServletRequest request) {
		int count = 0;
		
		String requestUri = request.getRequestURI();

		if(!requestUri.contains("Reply.do") && !requestUri.contains("Ajax.do")& !requestUri.contains("More.do")& !requestUri.contains("qnaTag.do")){
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			User user = (User) getRequestAttribute("ikep.user");
			
			Qna qnaSearch = new Qna();
			qnaSearch.setPortalId(portal.getPortalId());
			qnaSearch.setRegisterId(user.getUserId());
			qnaSearch.setQnaType(QnaConstants.IS_QNA);
			
			count = qnaService.listCount(qnaSearch);
		} 
		
		return count;
	}
	
	/**
	 * 공통으로 가져감 카테고리 리스트
	 * @return
	 */
	@ModelAttribute("myReplyCount")
	public int getCountMyReply(HttpServletRequest request) {
		
		int count = 0;
		
		String requestUri = request.getRequestURI();

		if(!requestUri.contains("Reply.do") && !requestUri.contains("Ajax.do")& !requestUri.contains("More.do")& !requestUri.contains("qnaTag.do")){
			
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			User user = (User) getRequestAttribute("ikep.user");
			
			Qna qnaSearch = new Qna();
			qnaSearch.setPortalId(portal.getPortalId());
			qnaSearch.setRegisterId(user.getUserId());
			qnaSearch.setQnaType(QnaConstants.IS_REPLY);
			
			count = qnaService.listCount(qnaSearch);
		}
		
		
		return count;
	}
	
	
	/**
	 * 공통 - 긴급 리스트
	 * @return
	 */
	@ModelAttribute("urgentList")
	public List<Qna> getUrgentList(HttpServletRequest request) {
		
		List<Qna> urgentList = new ArrayList<Qna>();
		String requestUri = request.getRequestURI();
		
		Pattern p = Pattern.compile("(list.*?Qna.do)");	//리스트 페이지 에서만
        Matcher m = p.matcher(requestUri);

		if(request.getMethod().equalsIgnoreCase("GET") && (requestUri.contains("main.do") ||  m.find())){
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
			Properties prop = PropertyLoader.loadProperties("/configuration/qna-conf.properties");
			
			String urgentPageLine = prop.getProperty("qna.main.urgentList.pagePer");	
			
			Qna urgentQnaSearch = new Qna();
			
			urgentQnaSearch.setQnaType(QnaConstants.IS_QNA);
			urgentQnaSearch.setUrgent(QnaConstants.URGENT_OK);
			
			urgentQnaSearch.setEndRowIndex(Integer.parseInt(urgentPageLine));
			urgentQnaSearch.setStartRowIndex(0);
			urgentQnaSearch.setPortalId(portal.getPortalId());
			urgentQnaSearch.setIsNotAdopt(QnaConstants.IS_NOT_ADOPT);		//채택된 답변 질문 제외
			urgentQnaSearch.setItemDelete(QnaConstants.ITEM_DELETE_NO);		//삭제되지 않은 자료만
			
			urgentList = qnaService.list(urgentQnaSearch);
			
		}
		
		return urgentList;
	}
	
	
	/**
	 * 공통 - 현황 통계
	 * @return
	 */
	@ModelAttribute("qnaStatistics")
	public QnaStatistics getQnaStatistics(HttpServletRequest request) {
		
		String requestUri = request.getRequestURI();
		
		QnaStatistics  qnaStatistics =  new QnaStatistics();
		
		Pattern p = Pattern.compile("(list.*?Qna.do)");	//리스트 페이지 에서만
        Matcher m = p.matcher(requestUri);

		if(request.getMethod().equalsIgnoreCase("GET") && (requestUri.contains("main.do") ||  m.find())){
			Portal portal = (Portal) getRequestAttribute("ikep.portal");
		
			qnaStatistics = qnaStatisticsService.read(portal.getPortalId());
		
		}
		
		
		return qnaStatistics;
	}
	
	
	
	
	/**
	 * 어드민 유저 인지
	 * @return
	 */
	@ModelAttribute("isAdmin")
	public boolean isAdmin(){
        
		User user = (User) getRequestAttribute("ikep.user");
		
		String sysName = QnaConstants.ITEM_TYPE_CODE_QNA;
        String userId = user.getUserId();
        boolean isSystemAdmin = aclService.isSystemAdmin(sysName, userId);
        
        return isSystemAdmin;
        
	}
	
	/**
	 * 권한체크 - 본인자신이 쓴것인지 관리자인지
	 * @param isAdmin
	 * @param qnaRegisterId
	 * @param qnaId
	 */
	public void accessCheck(boolean isAdmin, String registerId){

		User user = (User) getRequestAttribute("ikep.user");
		
		//권한체크
		if(!(registerId.equals(user.getUserId()) || isAdmin)){
			throw new IKEP4AuthorizedException();
		}
	}
	
	
	/**
	 * @valid 사용시 groups 사용가능하다록 설정하는부분 - valid 체크
	 * @param result
	 * @param o
	 * @param classes
	 * @return
	 */
	protected boolean isValid( Errors result, Object o, Class<?>... classes )
	{
		
		Class<?>[] clazz = null;
		
	    if ( classes == null || classes.length == 0 || classes[0] == null )
	    {
	    	clazz = new Class<?>[] { Default.class };
	    }
	    else
	    {	
	    	clazz  = classes;
	    }
	    
	    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	    Set<ConstraintViolation<Object>> violations = validator.validate( o, clazz );
	    for ( ConstraintViolation<Object> v : violations )
	    {
	        Path path = v.getPropertyPath();
	        String propertyName = "";
	        if ( path != null )
	        {
	            for ( Node n : path )
	            {
	                propertyName += n.getName() + ".";
	            }
	            propertyName = propertyName.substring( 0, propertyName.length()-1 );
	        }
	        ConstraintDescriptor<?> constraintDescriptor = v.getConstraintDescriptor();
			String constraintName = constraintDescriptor.getAnnotation().annotationType().getSimpleName();
	        if ( propertyName == null || "".equals(  propertyName  ))
	        {
	            result.reject( constraintName, v.getMessage());
	        }
	        else
	        {
	            result.rejectValue( propertyName, constraintName, v.getMessage() );
	        }
	    }
	    return violations.size() == 0;
	}

}
