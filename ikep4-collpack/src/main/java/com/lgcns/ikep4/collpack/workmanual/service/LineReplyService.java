/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.workmanual.service;

import com.lgcns.ikep4.collpack.workmanual.model.LineReply;
import com.lgcns.ikep4.collpack.workmanual.search.ManualSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;

/**
 * Service 정의
 *
 * @author 박정욱(crabes@lycos.co.kr)
 * @version $Id: LineReplyService.java 16236 2011-08-18 02:48:22Z giljae $
 */
public interface LineReplyService extends GenericService<LineReply, String> {
	/**
	 *댓글 정보 조회
	 * @param manualSearchCondition
	 * @return
	 */
	public SearchResult<LineReply> listLineReply(ManualSearchCondition manualSearchCondition);
	/**
	 *댓글 저장
	 * @param lineReply
	 * @param portalId
	 * @return
	 */
	public String createLineReply(LineReply lineReply, String portalId);
	/**
	 *댓글의 답글 저장
	 * @param lineReply
	 * @param portalId
	 * @return
	 */
	public String createReplyLineReply(LineReply lineReply, String portalId);
	/**
	 *작성자 모드로 글 삭제 
	 * @param manualId
	 * @param linereplyId
	 * @param updaterId
	 * @param updaterName
	 * @param portalId
	 */
	public void deleteLinereplyByUser(String manualId, String linereplyId, String updaterId, String updaterName, String portalId);
	/**
	 *관리자 모드로 글 삭제 
	 * @param manualId
	 * @param linereplyId
	 * @param portalId
	 */
	public void deleteLinereplyByAdmin(String manualId, String linereplyId, String portalId);
	
}
