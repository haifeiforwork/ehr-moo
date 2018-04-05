package com.lgcns.ikep4.approval.admin.model;


import com.lgcns.ikep4.framework.core.model.BaseObject;

public class Code<T> extends BaseObject {
	
	static final long serialVersionUID = 7824506174606342333L;

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
