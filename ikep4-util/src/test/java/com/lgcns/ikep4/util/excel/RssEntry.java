package com.lgcns.ikep4.util.excel;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;


/**
 * RssEntryVO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: RssEntry.java 16247 2011-08-18 04:54:29Z giljae $
 */
public class RssEntry extends BaseObject {
	/**
	 *
	 */
	private static final long serialVersionUID = 9129504227012245192L;

	/**
	 * 제목
	 */
	private String title;

	/**
	 * 링크
	 */
	private String link;

	/**
	 * 저작자
	 */
	private String author;

	/**
	 * 설명
	 */
	private String description;

	/**
	 * 발행일
	 */
	private Date publishedDate;

	/**
	 * 
	 */
	private String type = "text/plain";

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

}
