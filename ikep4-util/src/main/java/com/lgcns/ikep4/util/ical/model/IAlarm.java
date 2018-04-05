package com.lgcns.ikep4.util.ical.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * IAlarmVO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: IAlarm.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class IAlarm extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 3774625536663801203L;

	/**
	 * 알람타입(AUDIO, DISPLAY, EMAIL, PROCEDURE)
	 */
	private String action;

	/**
	 * 반복설정문자열(Dur)
	 */
	private String trigger;

	/**
	 * 제목
	 */
	private String summary;

	/**
	 * 설명
	 */
	private String description;

	/**
	 * 참여자 리스트
	 */
	private List<IAttendee> attendeeList;

	/**
	 * attach
	 */
	private String attach;

	/**
	 * v
	 */
	private String duration;

	/**
	 * 반복설정
	 */
	private IDur idur;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public IDur getIdur() {
		return idur;
	}

	public void setIdur(IDur idur) {
		this.idur = idur;
	}

	public List<IAttendee> getAttendeeList() {
		return attendeeList;
	}

	public void setAttendeeList(List<IAttendee> attendeeList) {
		this.attendeeList = attendeeList;
	}

	public void addAttendeeList(IAttendee attendee) {

		if (attendeeList == null) {
			attendeeList = new ArrayList<IAttendee>();
		}

		attendeeList.add(attendee);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
