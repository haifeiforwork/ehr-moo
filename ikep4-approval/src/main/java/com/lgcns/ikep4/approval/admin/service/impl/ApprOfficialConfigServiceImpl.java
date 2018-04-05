package com.lgcns.ikep4.approval.admin.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.approval.admin.dao.ApprOfficialConfigDao;
import com.lgcns.ikep4.approval.admin.model.ApprOfficialConfig;
import com.lgcns.ikep4.approval.admin.service.ApprOfficialConfigService;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


/**
 * 공문 설정 서비스
 * 
 * @author handul
 * @version $Id$
 */
@Service
public class ApprOfficialConfigServiceImpl extends GenericServiceImpl<ApprOfficialConfig, String> implements
		ApprOfficialConfigService {

	@Autowired
	private ApprOfficialConfigDao apprOfficialConfigDao;

	@Autowired
	private IdgenService idgenService;

	@Autowired
	private FileService fileService;

	public String create(ApprOfficialConfig apprOfficialConfig) {

		User user = (User) getRequestAttribute("ikep.user");

		apprOfficialConfig.setConfigId(idgenService.getNextId());
		apprOfficialConfigDao.create(apprOfficialConfig);

		if (!StringUtil.isEmpty(apprOfficialConfig.getHeaderImgId()) && apprOfficialConfig.getHeaderType().equals("1")) {
			fileService.createFileLink(apprOfficialConfig.getHeaderImgId(), apprOfficialConfig.getConfigId(),
					IKepConstant.ITEM_TYPE_CODE_APPROVAL, user);
		}

		if (!StringUtil.isEmpty(apprOfficialConfig.getFooterImgId()) && apprOfficialConfig.getFooterType().equals("1")) {
			fileService.createFileLink(apprOfficialConfig.getFooterImgId(), apprOfficialConfig.getConfigId(),
					IKepConstant.ITEM_TYPE_CODE_APPROVAL, user);
		}
		if (!StringUtil.isEmpty(apprOfficialConfig.getSealId())) {
			fileService.createFileLink(apprOfficialConfig.getSealId(), apprOfficialConfig.getConfigId(),
					IKepConstant.ITEM_TYPE_CODE_APPROVAL, user);
		}

		return apprOfficialConfig.getConfigId();
	}

	public boolean exists(String signId) {
		// TODO Auto-generated method stub
		return false;
	}

	public void update(ApprOfficialConfig apprOfficialConfig) {

		User user = (User) getRequestAttribute("ikep.user");

		apprOfficialConfigDao.update(apprOfficialConfig);

		if (!StringUtil.isEmpty(apprOfficialConfig.getHeaderImgId()) && apprOfficialConfig.getHeaderType().equals("1")) {
			fileService.createFileLink(apprOfficialConfig.getHeaderImgId(), apprOfficialConfig.getConfigId(),
					IKepConstant.ITEM_TYPE_CODE_APPROVAL, user);
		}

		if (!StringUtil.isEmpty(apprOfficialConfig.getFooterImgId()) && apprOfficialConfig.getFooterType().equals("1")) {
			fileService.createFileLink(apprOfficialConfig.getFooterImgId(), apprOfficialConfig.getConfigId(),
					IKepConstant.ITEM_TYPE_CODE_APPROVAL, user);
		}
		if (!StringUtil.isEmpty(apprOfficialConfig.getSealId())) {
			fileService.createFileLink(apprOfficialConfig.getSealId(), apprOfficialConfig.getConfigId(),
					IKepConstant.ITEM_TYPE_CODE_APPROVAL, user);
		}
	}

	public void delete(String signId) {
		// TODO Auto-generated method stub
	}

	public ApprOfficialConfig read(String configId) {
		return (ApprOfficialConfig) apprOfficialConfigDao.get(configId);
	}

	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}

	public void deleteImgFile(Map map) {
		apprOfficialConfigDao.deleteImgFile(map);
	}

}
