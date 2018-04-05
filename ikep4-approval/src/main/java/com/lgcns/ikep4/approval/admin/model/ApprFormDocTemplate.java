package com.lgcns.ikep4.approval.admin.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author wonchu
 * @version $Id: FormItem.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class ApprFormDocTemplate extends BaseObject {
	
	static final long serialVersionUID = -7396787722343650846L;
	
	/**
	 * CreateApprFormDocTemplate
	 */
	public interface CreateApprFormDocTemplate {
	}
	
	/**
	 * UpdateApprFormDocTemplate
	 */
	public interface UpdateApprFormDocTemplate {
	}
	
	/**
	 * DeleteApprFormDocTemplate
	 */
	public interface DeleteApprFormDocTemplate {
	}
	
	/**
	 * 포탈아이디
	 */
	private String portalId;
	
	/**
	 * 템플릿 id
	 */
	@NotEmpty(groups = { UpdateApprFormDocTemplate.class, DeleteApprFormDocTemplate.class })
	private String templateId;
	
	/**
	 * 템플릿 type
	 */
	private int templateType;
	
	/**
	 * 템플릿 명
	 */
	@NotEmpty(groups = { CreateApprFormDocTemplate.class, UpdateApprFormDocTemplate.class })
    private String templateName;
    
	/**
	 * 템플릿 내용
	 */
	@NotEmpty(groups = { CreateApprFormDocTemplate.class, UpdateApprFormDocTemplate.class })
    private String templateData;
    
	/**
	 * 등록자 id
	 */
    private String registerId;
    
    /**
	 * 등록자 이름
	 */
    private String registerName;
    
    /**
	 * 등록일시
	 */
    private Date registDate;
    
    /**
	 * 템플릿  배열
	 */
    private String [] templates;
    
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public int getTemplateType() {
		return templateType;
	}

	public void setTemplateType(int templateType) {
		this.templateType = templateType;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateData() {
		return templateData;
	}

	public void setTemplateData(String templateData) {
		this.templateData = templateData;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String[] getTemplates() {
		return templates;
	}

	public void setTemplates(String[] templates) {
		this.templates = templates;
	}
	
    
}
