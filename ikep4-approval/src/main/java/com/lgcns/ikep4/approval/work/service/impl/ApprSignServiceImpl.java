package com.lgcns.ikep4.approval.work.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.approval.work.dao.ApprSignDao;
import com.lgcns.ikep4.approval.work.model.ApprSign;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.approval.work.service.ApprSignService;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * Sing 이미지 관리 서비스
 * 
 * @author 유승목
 * @version $Id$
 */
@Service
public class ApprSignServiceImpl extends GenericServiceImpl<ApprSign, String> implements ApprSignService {

	@Autowired
	private ApprSignDao apprSignDao;

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private FileService fileService;

	public String create(ApprSign apprSign) {

		User user = (User) getRequestAttribute("ikep.user");
		String portalId = (String) getRequestAttribute("ikep.portalId");

		apprSign.setSignId(idgenService.getNextId());
		apprSign.setIsDefault("1");
		apprSign.setPortalId(portalId);

		apprSignDao.initDefault(apprSign);
		apprSignDao.create(apprSign);

		fileService.createFileLink(apprSign.getSignFileId(), apprSign.getSignId(),
				IKepConstant.ITEM_TYPE_CODE_APPROVAL, user);

		return apprSign.getSignId();
	}

	public boolean exists(String signId) {
		// TODO Auto-generated method stub
		return false;
	}

	public void update(ApprSign apprSign) {
		// TODO Auto-generated method stub
	}

	public void delete(String signId) {
		apprSignDao.remove(signId);
		fileService.removeItemFile(signId);
	}

	public void delete(String[] signIds) {

		for (String signId : signIds) {
			apprSignDao.remove(signId);
			fileService.removeItemFile(signId);
		}
	}

	public ApprSign read(String signId) {
		return (ApprSign) apprSignDao.get(signId);
	}

	public List<ApprSign> select(ApprSign apprSign) {
		return apprSignDao.select(apprSign);
	}

	public void changeDefault(ApprSign apprSign) {

		apprSignDao.initDefault(apprSign);

		apprSignDao.setDefault(apprSign);
	}

	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

	public void changeApprPassword(User user) {
		apprSignDao.changeApprPassword(user);
	}

	public void clearDefault(ApprSign apprSign) {
		apprSignDao.initDefault(apprSign);
	}

	public ApprSign selectUserSign(String userId) {

		ApprSign apprSign = new ApprSign();
		apprSign.setUserId(userId);
		apprSign.setIsDefault("1");

		List<ApprSign> signList = apprSignDao.select(apprSign);

		if (signList != null && signList.size() > 0) {
			return signList.get(0);
		} else {
			return null;
		}

	}

	public SearchResult<ApprSign> listBySearchCondition(ApprListSearchCondition searchCondition) {

		Integer count = this.apprSignDao.countBySearchCondition(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<ApprSign> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprSign>(searchCondition);
		} else {
			List<ApprSign> apprList = (List<ApprSign>) this.apprSignDao.listBySearchCondition(searchCondition);
			searchResult = new SearchResult<ApprSign>(apprList, searchCondition);
		}

		return searchResult;
	}
}
