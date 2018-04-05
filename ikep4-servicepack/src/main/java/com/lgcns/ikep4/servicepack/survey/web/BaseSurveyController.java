/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.servicepack.survey.web;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import javax.validation.metadata.ConstraintDescriptor;

import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.servicepack.survey.util.MagicNumUtils;
import com.lgcns.ikep4.servicepack.survey.util.SurveyConstance;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 설문 기본 mvc
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: BaseSurveyController.java 16860 2011-10-21 05:01:28Z giljae $
 */

public class BaseSurveyController extends BaseController {
	
	/**
	 * 권한 관리
	 */
	@Autowired
    ACLService aclService;
	
	
	
	private static final Set<String> internalAnnotationAttributes = new HashSet<String>(3);

	static {
		internalAnnotationAttributes.add("message");
		internalAnnotationAttributes.add("groups");
		internalAnnotationAttributes.add("payload");
	}
	
	/**
	 * 어드민 유저 인지
	 * @return
	 */
	@ModelAttribute("isAdmin")
	public boolean isAdmin(){
		User user = (User) getRequestAttribute("ikep.user");
		
        String userId = user.getUserId();
        try
        {
        	return  aclService.isSystemAdmin(SurveyConstance.ITEM_TYPE_CODE, userId);
        }
        catch(Exception e){
        	log.error(e.getMessage());
        }
        
        return false;
	}
	
	/**
	 * 설문 mail send시 url분석
	 * @param request
	 * @return
	 */
	public String serverURL(HttpServletRequest request){
		String protocol = request.getProtocol();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();
		
		StringBuffer url = new StringBuffer();
		
		if( protocol.toLowerCase().indexOf("https") >=0 ){
			url.append("https://");
		}else{
			url.append("http://");
		}
		
		url.append(serverName);
		
		if( serverPort != MagicNumUtils.ARRAY_SIZE_10*MagicNumUtils.ARRAY_SIZE_8){
			url.append(":").append(serverPort);
		}
		
		url.append(contextPath);
		
		return url.toString();
	}
	
	/**
	 * @valid 사용시 groups 사용가능하다록 설정하는부분
	 * @param result
	 * @param o
	 * @param classes
	 * @return
	 */
	protected boolean isValid( Errors errors, Object o, Class<?>... classes )
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
	    
		for (ConstraintViolation<Object> violation : violations) {
			String field = violation.getPropertyPath().toString();
			FieldError fieldError = errors.getFieldError(field);
			//생명과학에선 없는 부분?
            ConstraintDescriptor<?> constraintDescriptor = violation.getConstraintDescriptor();
			String constraintName = constraintDescriptor.getAnnotation().annotationType().getSimpleName();
			if (fieldError == null || !fieldError.isBindingFailure()) {
				try {
                     //violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName(),
					 //constraintName,
                     errors.rejectValue(field,
                           
							constraintName,
							getArgumentsForConstraint(errors.getObjectName(), field, violation.getConstraintDescriptor()),
							violation.getMessage());
				}
				catch (NotReadablePropertyException ex) {
					throw new IllegalStateException("JSR-303 validated property '" + field +
							"' does not have a corresponding accessor for Spring data binding - " +
							"check your DataBinder's configuration (bean property versus direct field access)", ex);
				}
			}
		}
		
	    return violations.size() == 0 && !errors.hasErrors();
	}
	
	/**
	 * valid에서 argment받을수 있도록 수정 message_xxx.propeties Size.survey.title = {0}을  최소{1}에서 최대{2} 글자수 사이만  입력해주세요 사용가능
	 * @param objectName
	 * @param field
	 * @param descriptor
	 * @return
	 */
	protected Object[] getArgumentsForConstraint(String objectName, String field, ConstraintDescriptor<?> descriptor) {
		List<Object> arguments = new LinkedList<Object>();
		String[] codes = new String[] {SurveyConstance.MESSAGE_PREFIX + Errors.NESTED_PATH_SEPARATOR + objectName + Errors.NESTED_PATH_SEPARATOR + field, field};
		//System.out.println(MESSAGE_PREFIX + Errors.NESTED_PATH_SEPARATOR + objectName + Errors.NESTED_PATH_SEPARATOR + field);
		arguments.add(new DefaultMessageSourceResolvable(codes, field));
		// Using a TreeMap for alphabetical ordering of attribute names
		//Map<String, Object> attributesToExpose = new TreeMap<String, Object>();
		for (Map.Entry<String, Object> entry : descriptor.getAttributes().entrySet()) {
			String attributeName = entry.getKey();
			Object attributeValue = entry.getValue();
			if (!internalAnnotationAttributes.contains(attributeName)) {
				//System.out.println(attributeName + ":" + attributeValue +"------------------------------------------");
				//attributesToExpose.put(attributeName, attributeValue);
				arguments.add(attributeValue);
			}
		}
		
		return arguments.toArray(new Object[arguments.size()]);
	}
}
