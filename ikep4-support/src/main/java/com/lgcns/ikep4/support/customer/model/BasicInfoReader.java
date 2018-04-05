
package com.lgcns.ikep4.support.customer.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 고객 기본정보 
 *
 * @author SongHeeJung
 * @version $Id$
 */
public class BasicInfoReader extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = -8983561250357155660L;

	private String readerId;
	
	private String readerName;

	private String readerTeamName;
	
	private Date readDate;

	public String getReaderId() {
		return readerId;
	}

	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}

	public String getReaderName() {
		return readerName;
	}

	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}

	public String getReaderTeamName() {
		return readerTeamName;
	}

	public void setReaderTeamName(String readerTeamName) {
		this.readerTeamName = readerTeamName;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	

}
