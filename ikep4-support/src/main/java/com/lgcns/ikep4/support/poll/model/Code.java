package com.lgcns.ikep4.support.poll.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;


public class Code<T> extends BaseObject {

	private static final long serialVersionUID = 2991056404888577830L;

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
