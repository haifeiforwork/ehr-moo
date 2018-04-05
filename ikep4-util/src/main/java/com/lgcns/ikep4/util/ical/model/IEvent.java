package com.lgcns.ikep4.util.ical.model;

import java.util.ArrayList;
import java.util.List;

import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * IEventVO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: IEvent.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class IEvent extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -5844145303599158819L;

	/**
	 * prodId
	 */
	private String prodId;

	/**
	 * id
	 */
	private String uid;

	/**
	 * version
	 */

	private String version;

	/**
	 * calScale
	 */

	private String calScale;

	/**
	 * seq
	 */

	private int seq = 0;

	/**
	 * priority
	 */
	private int priority = 0;

	/**
	 * 시작일
	 */
	private DateTime dtstart;

	/**
	 * 종료일
	 */
	private DateTime dtend;
	
	/**
	 * 시작일
	 */
	private Date dtstart2;

	/**
	 * 종료일
	 */
	private Date dtend2;

	/**
	 * 제목
	 */
	private String summary;

	/**
	 * 설명
	 */
	private String description;

	/**
	 * 장소
	 */
	private String location;

	/**
	 * ㅍ
	 */
	private String categories;

	/**
	 * 생성자
	 */
	private IAttendee organizer;

	/**
	 * 참여자
	 */
	private List<IAttendee> attendeeList;

	/**
	 * 공개범위(CONFIDENTIAL ,PRIVATE , PUBLIC)
	 */
	private String clazz;

	/**
	 * 공개여부(OPAQUE, TRANSPARENT)
	 */
	private String transp;

	/**
	 * 생성일
	 */
	private Date created;

	/**
	 * 최종수정일
	 */
	private Date lastmod;

	/**
	 * 생성일
	 */
	private Date dtstamp;

	/**
	 * 반복설정
	 */
	private IRecur irecur;

	/**
	 * 알람설정
	 */
	private List<IAlarm> ialarmList;

	/**
	 * 반복설정문자열
	 */
	private String rrule;
	
	
	private int wholeday;

	private String method;

	private String geo;

	private String status;

	private String recurid;

	private String duration;

	private String attach;

	private String comment;

	private String contact;

	private DateTime exdate;

	private String exrule;

	private String rstatus;

	private String related;

	private String resources;

	private DateTime rdate;

	private String xprop;
	
	

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTransp() {
		return transp;
	}

	public void setTransp(String transp) {
		this.transp = transp;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRecurid() {
		return recurid;
	}

	public void setRecurid(String recurid) {
		this.recurid = recurid;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getExrule() {
		return exrule;
	}

	public void setExrule(String exrule) {
		this.exrule = exrule;
	}

	public String getRstatus() {
		return rstatus;
	}

	public void setRstatus(String rstatus) {
		this.rstatus = rstatus;
	}

	public String getRelated() {
		return related;
	}

	public void setRelated(String related) {
		this.related = related;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public String getRrule() {
		return rrule;
	}

	public void setRrule(String rrule) {
		this.rrule = rrule;
	}

	public String getXprop() {
		return xprop;
	}

	public void setXprop(String xprop) {
		this.xprop = xprop;
	}

	public Date getDtstart() {
		return dtstart;
	}

	public void setDtstart(DateTime dtstart) {
		this.dtstart = dtstart;
	}

	public Date getDtend() {
		return dtend;
	}

	public void setDtend(DateTime dtend) {
		this.dtend = dtend;
	}

	public void setLastmod(DateTime lastmod) {
		this.lastmod = lastmod;
	}

	public void setDtstamp(DateTime dtstamp) {
		this.dtstamp = dtstamp;
	}

	public DateTime getExdate() {
		return exdate;
	}

	public void setExdate(DateTime exdate) {
		this.exdate = exdate;
	}

	public DateTime getRdate() {
		return rdate;
	}

	public void setRdate(DateTime rdate) {
		this.rdate = rdate;
	}

	public void setCreated(DateTime created) {
		this.created = created;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public IAttendee getOrganizer() {
		return organizer;
	}

	public void setOrganizer(IAttendee organizer) {
		this.organizer = organizer;
	}

	public IRecur getIrecur() {
		return irecur;
	}

	public void setIrecur(IRecur irecur) {
		this.irecur = irecur;
	}

	public List<IAlarm> getIalarmList() {
		return ialarmList;
	}

	public void setIalarmList(List<IAlarm> ialarmList) {
		this.ialarmList = ialarmList;
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

	public void addIAlarmList(IAlarm alarm) {

		if (ialarmList == null) {
			ialarmList = new ArrayList<IAlarm>();
		}

		ialarmList.add(alarm);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastmod() {
		return lastmod;
	}

	public void setLastmod(Date lastmod) {
		this.lastmod = lastmod;
	}

	public Date getDtstamp() {
		return dtstamp;
	}

	public void setDtstamp(Date dtstamp) {
		this.dtstamp = dtstamp;
	}

	public String getCalScale() {
		return calScale;
	}

	public void setCalScale(String calScale) {
		this.calScale = calScale;
	}

	public int getWholeday() {
		return wholeday;
	}

	public void setWholeday(int wholeday) {
		this.wholeday = wholeday;
	}

	public Date getDtstart2() {
		return dtstart2;
	}

	public void setDtstart2(Date dtstart2) {
		this.dtstart2 = dtstart2;
	}

	public Date getDtend2() {
		return dtend2;
	}

	public void setDtend2(Date dtend2) {
		this.dtend2 = dtend2;
	}
	
	

}
