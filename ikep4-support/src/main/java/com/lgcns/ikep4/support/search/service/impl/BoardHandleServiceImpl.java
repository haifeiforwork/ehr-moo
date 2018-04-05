/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.search.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.search.dao.BoardHandleDao;
import com.lgcns.ikep4.support.search.model.BoardHandle;
import com.lgcns.ikep4.support.search.service.BoardHandleService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * WorkPlace List Impl 
 * 
 * @author wonchu
 * @version $Id: BoardHandleServiceImpl.java 16245 2011-08-18 04:28:59Z giljae $
 */
@Service("boardHandleServiceImpl")
public class BoardHandleServiceImpl extends GenericServiceImpl<BoardHandle, String> implements BoardHandleService{
	
	@Autowired
	private BoardHandleDao boardHandleDao;

	/**
	 * 모듈 - 게시판:BD, 협업게시판:WS, 아이디어:ID, 포럼:FR, Q&A:QA, 개인블러그:SB, 전사공유지식:CK
	 */
	private String[] module = {"BD", "WS", "ID", "FR", "QA", "SB", "NO", "CK"};
	
	/**
	 * textContents update
	 * @param 	BoardHandle
	 * @return 	void
	 */
	public void updateTextContents(BoardHandle boardHandle) {
		boolean b = false;
		
		// module, itemId 체크
		if(!StringUtil.isEmpty(boardHandle.getModule()) && !StringUtil.isEmpty(boardHandle.getItemId())){
			for(int i=0;i<module.length;i++){
				if(boardHandle.getModule().equals(module[i])){
					b = true;
					break;
				}
			}
		}
		
		// contnets 체크및 textContnets 생성
		if(b && !StringUtil.isEmpty(boardHandle.getContents())){
			boardHandle.setTextContents(StringUtil.cutString(StringUtil.extractTextFormHTML(boardHandle.getContents()), 3500, ""));
			boardHandleDao.updateTextContents(boardHandle);
		}
	}
	
	public void updateTextContents(String module, String itemId, String contents){
		BoardHandle boardHandle = new BoardHandle();
		boardHandle.setModule(module);
		boardHandle.setItemId(itemId);
		boardHandle.setContents(contents);
		this.updateTextContents(boardHandle);
	}
	
}