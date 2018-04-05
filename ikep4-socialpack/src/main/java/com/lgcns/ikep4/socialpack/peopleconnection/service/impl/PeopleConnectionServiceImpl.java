/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.socialpack.peopleconnection.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.socialpack.peopleconnection.dao.PeopleConnectionDao;
import com.lgcns.ikep4.socialpack.peopleconnection.model.ExternalExpert;
import com.lgcns.ikep4.socialpack.peopleconnection.service.PeopleConnectionService;

/**
 * 
 * PeopleConnectionService 구현 클래스
 *
 * @author 최성우
 * @version $Id: PeopleConnectionServiceImpl.java 16246 2011-08-18 04:48:28Z giljae $
 */
@Service("peopleConnectionService")
public class PeopleConnectionServiceImpl extends GenericServiceImpl<ExternalExpert, String> implements PeopleConnectionService {
	@Autowired
	private PeopleConnectionDao peopleConnectionDao;

	/**
	 * 파일 서비스
	 */
	@Autowired
	private FileService fileService;

    public static final String EDITOR_YN_N					= "N";
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.socialpack.peopleconnection.service.PeopleConnectionService#listByProfileIdList(java.util.List)
	 */
	public List<ExternalExpert> listByProfileIdList(List<String> externalExpertProfileIdList) {
		
		// ExternalExpert객체 List
		List<ExternalExpert> recommendExternalExpertList = new ArrayList<ExternalExpert>();
		
		Iterator<String> profileIdListIt = externalExpertProfileIdList.iterator();

		String fileId = "";
		
		while(profileIdListIt.hasNext()){
			String profileId = (String) profileIdListIt.next();
			ExternalExpert externalExpert = peopleConnectionDao.get(profileId);
			
			fileId = "";
			// profileI에 해당하는 사진 이미지 조회
			List<FileData> fileDataList = fileService.getItemFile(profileId, EDITOR_YN_N);
			if(null != fileDataList && 0 < fileDataList.size()){
				fileId = fileDataList.get(0).getFileId();
			}
			 
			externalExpert.setFileId(fileId);
			recommendExternalExpertList.add(externalExpert);
		}

		return recommendExternalExpertList;
	}

	
}
