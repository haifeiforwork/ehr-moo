/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.memo.service;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.memo.model.Memo;
import com.lgcns.ikep4.support.memo.search.MemoSearchCondition;

/**
 *
 * @author 배성훤(sunghwonbae@gmail.com)
 * @version $Id: MemoDao.java 6218 2011-05-14 02:03:19Z combinet $
 */
public interface MemoService extends GenericService<Memo, String> {


	public SearchResult<Memo> getMemoList(MemoSearchCondition memoSearchCondition);

	public Memo get(String id);

	public boolean exists(String id);

	public String create(Memo object);

	public void update(Memo object);

	public void remove(String id);
	
	public void multiDelete(String[] itemIds);
}
