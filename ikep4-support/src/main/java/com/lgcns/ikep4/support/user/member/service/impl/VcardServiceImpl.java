package com.lgcns.ikep4.support.user.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.dao.VcardDao;
import com.lgcns.ikep4.support.user.restful.model.Vcard;
import com.lgcns.ikep4.support.user.restful.model.VcardSearchCondition;
import com.lgcns.ikep4.support.user.member.service.VcardService;

/**
 * 
 * 명함관리 service 구현객체
 *
 * @version $Id$
 */
@Service
public class VcardServiceImpl extends GenericServiceImpl<Vcard, String> implements VcardService {
	
	@Autowired
	private VcardDao vcardDao;
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 사용자가 폴더에 등록한 명함 목록(전체)+폴더에 등록되지 않은 명함 목록
	 * @return
	 */
	public List<Vcard> listUserVcardCheckFolder(VcardSearchCondition vcardSearchCondition) {
		List<Vcard> vcardList = this.vcardDao.listUserVcardCheckFolder(vcardSearchCondition); 
		
		for (Vcard vcard : vcardList) {
			List<FileData> fileData = fileService.getItemFile(vcard.getVisitingcardGroupId(), "0");
			
			if(fileData != null && fileData.size() > 0) {
				vcard.setFileId(fileData.get(0).getFileid());
			}
		}
		
		return vcardList;
	}
	
	
}