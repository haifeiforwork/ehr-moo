package com.lgcns.ikep4.servicepack.survey.util;

import org.springframework.util.StringUtils;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.servicepack.survey.model.Survey;
import com.lgcns.ikep4.servicepack.survey.model.SurveyStatus;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 권한관리
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: AuthUtils.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class AuthUtils extends BaseObject {
	private static final long serialVersionUID = 7729418428934003660L;

	private User user;

	private Survey survey;

	private boolean end; // 설문마감

	private boolean mail; // 메일발송

	private boolean approve; // 승인요청

	private boolean approveEnd; // 승인완료

	private boolean surveyEdit; // 설문수정

	private boolean questionEdit; // 문항수정

	private boolean delete; // 삭제

	private boolean copy; // 복사

	private boolean read;// 읽기권한

	private boolean publish;// 진행상태로변경
	
	private boolean isAdmin;

	public AuthUtils(Survey survey, User user, boolean isAdmin) {
		this.user = user;
		this.survey = survey;
		this.end = false;
		this.mail = false;
		this.approve = false;
		this.surveyEdit = false;
		this.questionEdit = false;
		this.delete = false;
		this.copy = false;
		this.read = false;
		this.approveEnd = false;
		this.publish = false;
		this.isAdmin=isAdmin;
		this.setMake();

	}

	/**
	 * 초기 권한 설정
	 *
	 */
	private void setMake() {
		if (StringUtils.hasText(survey.getRegisterId())) {
			if (survey.getRegisterId().equals(user.getUserId()) || isAdmin) {
				reSetAuth(survey);
			}

			// 승인대기중
			if (StringUtils.hasText(survey.getApproverId()) && survey.getApproverId().equals(user.getUserId())
					&& survey.getSurveyStatus().intValue() == SurveyStatus.WAIT.getCode()) // 승인요청
			{
				this.approveEnd=true;
			}
		}
	}

	/**
	 * 궈한 제 설정
	 * @param survey
	 */
	private void reSetAuth(Survey survey) {
		this.copy = true;
		this.read = true;
		

		// 작성중일때 나 반려중일경우
		if (survey.getSurveyStatus().intValue() == SurveyStatus.WRITING.getCode()
				|| survey.getSurveyStatus().intValue() == SurveyStatus.REJECT.getCode()) {
			this.approve=true;
		    this.surveyEdit=true;
		    this.questionEdit=true;
		    this.delete=true;
		}

		if (survey.getSurveyStatus().intValue() == SurveyStatus.APPROVE.getCode()) {
			this.publish=true;
		}

		// 상태가 답변중일경우
		if (survey.getSurveyStatus().intValue() == SurveyStatus.ANSERING.getCode()) {
			this.end=true;
			this.mail=true;
		}

		// 종료
		if (survey.getSurveyStatus().intValue() == SurveyStatus.END.getCode()) {
			this.delete=true;
		}
	}

	public User getUser() {
		return user;
	}

	public Survey getSurvey() {
		return survey;
	}

	public boolean isEnd() {
		return end;
	}

	public boolean isMail() {
		return mail;
	}

	public boolean isApprove() {
		return approve;
	}

	public boolean isApproveEnd() {
		return approveEnd;
	}

	public boolean isSurveyEdit() {
		return surveyEdit;
	}

	public boolean isQuestionEdit() {
		return questionEdit;
	}

	public boolean isDelete() {
		return delete;
	}

	public boolean isCopy() {
		return copy;
	}

	public boolean isRead() {
		return read;
	}

	public boolean isPublish() {
		return publish;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public void setMail(boolean mail) {
		this.mail = mail;
	}

	public void setApprove(boolean approve) {
		this.approve = approve;
	}

	public void setApproveEnd(boolean approveEnd) {
		this.approveEnd = approveEnd;
	}

	public void setSurveyEdit(boolean surveyEdit) {
		this.surveyEdit = surveyEdit;
	}

	public void setQuestionEdit(boolean questionEdit) {
		this.questionEdit = questionEdit;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public void setCopy(boolean copy) {
		this.copy = copy;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public void setPublish(boolean publish) {
		this.publish = publish;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
