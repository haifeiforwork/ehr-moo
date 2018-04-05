package com.lgcns.ikep4.portal.portlet.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.portal.portlet.model.PortletInnovation;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 포틀릿 Innovation service
 *
 * @author 임종상
 * @version $Id: PortletInnovationService.java 17850 2012-04-03 07:56:31Z unshj $
 */
@Transactional
public interface PortletInnovationService extends GenericService<PortletInnovation, String> {
	
	/**
	 * Innovation 등록
	 * @param portletInnovation Portlet Innovation Model
	 * @param fileList 이미지 파일
	 * @param editorAttach 에디터 여부(1:에디터, 0:에디터 아님)
	 * @param user 유저 객체
	 */
	public void createPortletInnovation(PortletInnovation portletInnovation, String editorAttach, User user);
	
	/**
	 * Innovation 조회
	 * @param portletConfigId 포틀릿 config ID
	 * @param user 유저 객체
	 * @return PortletInnovation Innovation 정보
	 */
	public PortletInnovation readPortletInnovation(String portletConfigId);
}