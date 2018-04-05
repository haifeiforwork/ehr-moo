package com.lgcns.ikep4.support.memo.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.memo.dao.MemoDao;
import com.lgcns.ikep4.support.memo.model.Memo;
import com.lgcns.ikep4.support.memo.search.MemoSearchCondition;
import com.lgcns.ikep4.support.memo.service.MemoService;


@Service("memoService")
public class MemoServiceImpl extends GenericServiceImpl<Memo, String> implements MemoService {

	protected final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	private MemoDao memoDao;


	public SearchResult<Memo> getMemoList(MemoSearchCondition memoSearchCondition) {

		SearchResult<Memo> searchResult = null;

		Integer count = this.memoDao.countBySearchCondition(memoSearchCondition);
		memoSearchCondition.terminateSearchCondition(count);

		if (memoSearchCondition.isEmptyRecord()) {

			searchResult = new SearchResult<Memo>(memoSearchCondition);
		} else {
			List<Memo> meetingRoomList = memoDao.getMemoList(memoSearchCondition);
			searchResult = new SearchResult<Memo>(meetingRoomList, memoSearchCondition);
		}
		return searchResult;

	}



	public Memo read(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	public Memo get(String id) {
		// TODO Auto-generated method stub
		return this.memoDao.get(id);
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public String create(Memo object) {
		// TODO Auto-generated method stub
		return this.memoDao.create(object);
	}

	public void update(Memo object) {
		 this.memoDao.update(object);
	}

	public void remove(String id) {
		this.memoDao.remove(id);
	}

	public void multiDelete(String[] itemIds) {
		for (String itemId : itemIds) {
			this.memoDao.remove(itemId);
		}
	}






}
