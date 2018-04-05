package com.lgcns.ikep4.support.search.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author wonchu
 * @version $Id: FormItem.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class BoardHandle extends BaseObject {

	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7589104766586205130L;

	/**
	 * 모듈 - 게시판:BD, 협업게시판:WS, 아이디어:ID, 포럼:FR, Q&A:QA, 개인블러그:SB
	 */
	private String module;
	
	/**
	 * item id
	 */
	private String itemId;
	
	/**
	 * contents
	 */
	private String contents;
	
	/**
	 * html 제거한 contents
	 */
	private String textContents;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getTextContents() {
		return textContents;
	}

	public void setTextContents(String textContents) {
		this.textContents = textContents;
	}
	
	
    
}
