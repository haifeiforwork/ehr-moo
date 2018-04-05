package com.lgcns.ikep4.portal.admin.screen.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class Code<T> extends BaseObject {
	
	private static final long serialVersionUID = 4374920724515116932L;

	private T key;
	
	private String value; 

	public T getKey() {
		return key;
	} 
	public String getValue() {
		return value;
	}  
	 
	public Code(T key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	 
}
