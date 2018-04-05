package com.lgcns.ikep4.collpack.collaboration.board.board.restful.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="returnData0")
public class CollListReturnData {
	
	/**
	 * Collaboration ID
	 */
	private String collaborationId = "";
	
	/**
	 * Collaboration 명
	 */
	private String collaborationName = "";
	
	/**
	 * Collaboration 유형 ID
	 */
	private String typeId = "";
	
	/**
	 * Collaboration 유형 명
	 */
	private String typeName = "";
	

	public String getCollaborationId() {
		return collaborationId;
	}

	public void setCollaborationId(String collaborationId) {
		this.collaborationId = collaborationId;
	}

	public String getCollaborationName() {
		return collaborationName;
	}

	public void setCollaborationName(String collaborationName) {
		this.collaborationName = collaborationName;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


}