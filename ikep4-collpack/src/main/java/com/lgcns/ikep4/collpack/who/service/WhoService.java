/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.who.service;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.who.model.Pro;
import com.lgcns.ikep4.collpack.who.model.Who;
import com.lgcns.ikep4.collpack.who.search.WhoSearchCondition;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.tagging.model.Tag;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * TODO Javadoc주석작성
 *
 * @author 서혜숙(shs0420@nate.com)
 * @version $Id: WhoService.java 16236 2011-08-18 02:48:22Z giljae $
 */
@Transactional
public interface WhoService extends GenericService<Who, String> {
	/**
	 * 인물 목록 가져오기
	 * @param whoSearchCondition
	 * @return
	 */	
	public SearchResult<Who> listWhoBySearchCondition(WhoSearchCondition whoSearchCondition);
	/**
	 * 인물 상세 보기
	 * @param who
	 * @return
	 */		
	public Who readDetail(Who who);
	/**
	 * 수정이력목록 가져오기
	 * @param profileGroupId
	 * @return
	 */		
	public List<Who> selectProfileHistoryList(String profileGroupId);
	/**
	 * 인물 등록
	 * @param profileGroupId
	 * @return
	 */			
	public String create(Who who,User user);	
	/**
	 * 메일 중복 확인
	 * @param who
	 * @return
	 */		
	public Integer checkAlreadyMailExists(Who who);
	/**
	 * 관련인물 검색
	 * @param searchTag
	 * @return
	 */		
	public List<Pro> selectProList(Tag searchTag);
	/**
	 * 관련외부인물 검색
	 * @param searchTag
	 * @return
	 */		
	public List<Pro> selectWhoProList(Tag searchTag);	
}
