package com.lgcns.ikep4.portal.portlet.service.impl;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.portlet.dao.PortletWiseSayingDao;
import com.lgcns.ikep4.portal.portlet.model.PortletWiseSaying;
import com.lgcns.ikep4.portal.portlet.service.PortletWiseSayingService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * 포틀릿 오늘의 명언 Service 구현 클래스
 *
 * @author 박철종
 * @version $Id: PortletWiseSayingServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("portletWiseSayingService")
public class PortletWiseSayingServiceImpl extends GenericServiceImpl<PortletWiseSaying, String> implements PortletWiseSayingService {

	/**
	 * 오늘의 명언 포틀릿 DAO
	 */
	@Autowired
	private PortletWiseSayingDao portletWiseSayingDao;
	
	/**
	 * 첨부파일 관리 service
	 */
	@Autowired
	private FileService fileService;
	
	/**
	 * 오늘의 명언 포틀릿 설정 팝업 페이지의 페이지 별 오늘의 명언 리스트
	 * @param searchCondition SearchCondition
	 * @return SearchResult<PortletWiseSaying> 오늘의 명언 포틀릿 설정 팝업 페이지의 페이지 별 오늘의 명언 리스트
	 */
	public SearchResult<PortletWiseSaying> listBySearchCondition(SearchCondition searchCondition) {
		
		Integer count = portletWiseSayingDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<PortletWiseSaying> searchResult = null;
		
		if (searchCondition.isEmptyRecord()) {
			
			searchResult = new SearchResult<PortletWiseSaying>(searchCondition);
		} else {

			List<PortletWiseSaying> wiseSayingList = portletWiseSayingDao.listBySearchCondition(searchCondition);

			searchResult = new SearchResult<PortletWiseSaying>(wiseSayingList, searchCondition);
		}
		
		return searchResult;
	}
	
	/**
	 * 오늘의 명언 정보
	 * @param id 오늘의 명언 아이디
	 * @return PortletWiseSaying 오늘의 명언 정보
	 */
	public PortletWiseSaying get(String id) {
		
		PortletWiseSaying portletWiseSaying = portletWiseSayingDao.get(id);
		
		if(portletWiseSaying != null && !StringUtil.isEmpty(portletWiseSaying.getImageFileId())) {
			FileData fileData = new FileData();
			fileData = fileService.read(portletWiseSaying.getImageFileId());
			
			portletWiseSaying.setFileData(fileData);
		}
		
		return portletWiseSaying;
	}
	
	/**
	 * 오늘의 명언 무작위 선택
	 * @return PortletWiseSaying 오늘의 명언 무작위 선택
	 */
	public PortletWiseSaying getRandomWiseSaying() {
		
		Random random = new Random();
		
		Integer count = portletWiseSayingDao.countByList();
		Integer randomCount = random.nextInt(count) + 1;
		
		PortletWiseSaying portletWiseSaying = portletWiseSayingDao.getRandomWiseSaying(randomCount);
		
		String imageFileYn = "N";
		
		if(portletWiseSaying != null && !StringUtil.isEmpty(portletWiseSaying.getImageFileId())) {
			FileData fileData = new FileData();
			fileData = fileService.read(portletWiseSaying.getImageFileId());
			
			if(fileData != null) {
				Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
				String uploadRoot = prop.getProperty("ikep4.support.fileupload.upload_root");
			
				File file = new File(uploadRoot + fileData.getFilePath(), fileData.getFileName());
				
				if (file.exists()) {
					imageFileYn = "Y"; 
				}
			}
			
			portletWiseSaying.setFileData(fileData);
			portletWiseSaying.setImageFileYn(imageFileYn);
			
		}
		
		return portletWiseSaying;
	}
	
	/**
	 * 오늘의 명언 등록
	 * @param obj 오늘의 명언 정보
	 * @return 등록된 오늘의 명언 아이디
	 * @throws Exception
	 */
	public String create(PortletWiseSaying portletWiseSaying) {
		
		String id = portletWiseSaying.getWiseSayingId();
		
		portletWiseSayingDao.create(portletWiseSaying);
		
		return id;
	}
	
	/**
	 * 오늘의 명언 등록 확인
	 * @param obj 오늘의 명언 정보
	 * @return boolean 오늘의 명언 등록 확인(존재:true, 미존재:false)
	 */
	public boolean existsWiseSaying(PortletWiseSaying portletWiseSaying) {
		
		boolean exist = portletWiseSayingDao.existsWiseSaying(portletWiseSaying);

		return exist;
		
	}
	
	/**
	 * 오늘의 명언 등록
	 * @param obj 오늘의 명언 정보
	 * @return 등록된 오늘의 명언 아이디
	 * @throws Exception
	 */
	public String createWiseSaying(PortletWiseSaying portletWiseSaying) {
		
		String id = portletWiseSaying.getWiseSayingId();
		
		portletWiseSayingDao.create(portletWiseSaying);
		
		return id;
	}
	
	/**
	 * 오늘의 명언 수정
	 * @param obj 오늘의 명언 정보
	 * @throws Exception
	 */
	public void updateWiseSaying(PortletWiseSaying portletWiseSaying) {
		
		//boolean attachCheck = portletWiseSaying.getAttachCheck();
		
		
			portletWiseSayingDao.update(portletWiseSaying);
	
		
	}
	
	/**
	 * 오늘의 명언 삭제
	 * @param ids 오늘의 명언 아이디
	 */
	public void delete(String id) {
		
		PortletWiseSaying portletWiseSaying = new PortletWiseSaying();
		portletWiseSaying = portletWiseSayingDao.get(id);
		
		if(!StringUtil.isEmpty(portletWiseSaying.getImageFileId())) {
			fileService.removeFile(portletWiseSaying.getImageFileId());
		}
		
		portletWiseSayingDao.delete(id);
	}
	
	/**
	 * 오늘의 명언 멀티 삭제
	 * @param ids 오늘의 명언 아이디
	 */
	public void multiDeleteWiseSaying(String[] ids) {
		
		for(String wiseSayingId:ids) {
			PortletWiseSaying portletWiseSaying = new PortletWiseSaying();
			portletWiseSaying = portletWiseSayingDao.get(wiseSayingId);
			
			if(!StringUtil.isEmpty(portletWiseSaying.getImageFileId())) {
				fileService.removeFile(portletWiseSaying.getImageFileId());
			}
			
			portletWiseSayingDao.delete(wiseSayingId);
		}
	}

}