
package com.lgcns.ikep4.portal.moorimess.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.portal.moorimess.dao.OldRecordDao;
import com.lgcns.ikep4.portal.moorimess.model.OldRecordHistory;
import com.lgcns.ikep4.portal.moorimess.search.OldRecordSearchCondition;
import com.lgcns.ikep4.portal.moorimess.service.OldRecordService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;

import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.tagfree.util.MimeUtil;

/**
 * 
 * 고객정보 품질/클레임 상담이력 구현체 클래스 
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Service
@Transactional
public class OldRecordServiceImpl implements OldRecordService {

    @Autowired
    private OldRecordDao oldRecordDao;

    @Autowired
    private FileService fileService;

    public SearchResult<OldRecordHistory> listOldRecordBySearchCondition(
            OldRecordSearchCondition searchCondition ) {

        Integer count = this.oldRecordDao.countBySearchCondition( searchCondition );

        searchCondition.terminateSearchCondition( count );

        SearchResult<OldRecordHistory> searchResult = null;
        if ( searchCondition.isEmptyRecord() ) {
            searchResult = new SearchResult<OldRecordHistory>( searchCondition );
        } else {
            List<OldRecordHistory> oldRecordHistoryList = this.oldRecordDao
                    .listOldRecordBySearchCondition( searchCondition );
            
            for(int i=0; i<oldRecordHistoryList.size(); i++){
                if(oldRecordHistoryList.get( i ) != null){
                	if(!StringUtils.isEmpty(oldRecordHistoryList.get( i ).getFactory())){
                		if(oldRecordHistoryList.get( i ).getFactory().equals( "S" )){
                            oldRecordHistoryList.get( i ).setFactory( "S" );
                        }else if(oldRecordHistoryList.get( i ).getFactory().equals( "A" )){
                            oldRecordHistoryList.get( i ).setFactory( "A" );
                        }else if(oldRecordHistoryList.get( i ).getFactory().equals( "B" )){
                            oldRecordHistoryList.get( i ).setFactory( "B" );
                        }else if(oldRecordHistoryList.get( i ).getFactory().equals( "C" )){
                            oldRecordHistoryList.get( i ).setFactory( "C" );
                        }else if(oldRecordHistoryList.get( i ).getFactory().equals( "D" )){
                            oldRecordHistoryList.get( i ).setFactory( "D" );
                        }else if(oldRecordHistoryList.get( i ).getFactory().equals( "E" )){
                            oldRecordHistoryList.get( i ).setFactory( "E" );
                        }else if(oldRecordHistoryList.get( i ).getFactory().equals( "F" )){
                            oldRecordHistoryList.get( i ).setFactory( "F" );
                        }
                	}
                	if(!StringUtils.isEmpty(oldRecordHistoryList.get( i ).getClaimGubun())){
	                    if(oldRecordHistoryList.get( i ).getClaimGubun().equals( "1" )){
	                        oldRecordHistoryList.get( i ).setClaimGubun( "신용보험" );
	                    }else if(oldRecordHistoryList.get( i ).getClaimGubun().equals( "2" )){
	                        oldRecordHistoryList.get( i ).setClaimGubun( "담보" );
	                    }else if(oldRecordHistoryList.get( i ).getClaimGubun().equals( "3" )){
	                        oldRecordHistoryList.get( i ).setClaimGubun( "여신한도" );
	                    }else if(oldRecordHistoryList.get( i ).getClaimGubun().equals( "4" )){
	                        oldRecordHistoryList.get( i ).setClaimGubun( "Sub거래선" );
	                    }else if(oldRecordHistoryList.get( i ).getClaimGubun().equals( "5" )){
	                        oldRecordHistoryList.get( i ).setClaimGubun( "기타" );
	                    }else if(oldRecordHistoryList.get( i ).getClaimGubun().equals( "6" )){
	                    	oldRecordHistoryList.get( i ).setClaimGubun( "배서인" );
	                    }
                	}
                }
            }

            searchResult = new SearchResult<OldRecordHistory>( oldRecordHistoryList, searchCondition );

        }

        return searchResult;
    }

    public OldRecordHistory getOldRecord( int itemId ) {

        OldRecordHistory oldRecordHistory = oldRecordDao.getOldRecord( itemId );

        //파일 조회
        //List<FileData> fileDataList = fileService.getItemFile( Integer.toString( itemId ), "N" );
        List<FileData> fileDataList = fileService.getItemFileList( Integer.toString( itemId ), "BW", "N" );
        oldRecordHistory.setFileDataList( fileDataList );

        return oldRecordHistory;
    }

    public int createOldRecord( OldRecordHistory oldRecord, User user, List<MultipartFile> fileList ) {

        int seqNum = 0;
        try {
            oldRecord.setLinereplyCount( 0 );

            String registerName = user.getUserName();
            String updaterName = user.getUserName();

            oldRecord.setRegisterName( registerName );
            oldRecord.setUpdaterName( updaterName );

            
            
          //ActiveX editor 사용여부 확인
    		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
    		String useActiveX = "N";
    		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
    		
    		Portal portal = (Portal)RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portal", RequestAttributes.SCOPE_SESSION);
    		
    		//ActiveX Editor 사용 여부 확인
    		if("Y".equals(useActiveX)) {
    			//사용자 브라우저가 IE인 경우
    			if(oldRecord.getMsie() == 1){
    				try {	
    					//현재 포탈 도메인 가져오기

    					//현재 포탈 포트 가져오기
    					String port = commonprop.getProperty("ikep4.activex.editor.port");
    					//Tagfree ActiveX Editor Util => FileService, domain, port 생성자로 넘김 
    					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
    					util.setMimeValue(oldRecord.getContents());
    					//Mime 데이타 decoding
    					util.processDecoding();
    					//editor 첨부된 이미지 확인
    					if(util.getFileLinkList() != null && util.getFileLinkList().size()>0){
    						oldRecord.setEditorFileLinkList(util.getFileLinkList());
    					}
    					//내용 가져오기
    					String content = util.getDecodedHtml(false);		
    					content = content.trim();
    					//내용세팅
    					oldRecord.setContents(content);
    					
    				} catch (Exception e) {
    					throw new IKEP4ApplicationException("", e);
    				}
    			}
    		}
    		
    		seqNum = oldRecordDao.createOldRecord( oldRecord );

            if ( oldRecord.getFileLinkList() != null ) {
                fileService.saveFileLink( oldRecord.getFileLinkList(), Integer.toString( seqNum ),"BW", user );
            }

        } catch ( Exception e ) {

            throw new IKEP4ApplicationException( "DB UPDATE 실패", e );

        }

        return seqNum;
    }

    public void updateOldRecord( OldRecordHistory oldRecord, List<MultipartFile> fileList, User user,
            int seqNum ) {
    	
    	//ActiveX editor 사용여부 확인
		Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		String useActiveX = "N";
		useActiveX = commonprop.getProperty("ikep4.activex.editor.use");
		Portal portal = (Portal)RequestContextHolder.currentRequestAttributes().getAttribute("ikep.portal", RequestAttributes.SCOPE_SESSION);
		
		//ActiveX Editor 사용 여부 확인
		if("Y".equals(useActiveX)) {
			//사용자 브라우저가 IE인 경우
			if(oldRecord.getMsie() == 1){
				try {		
					//현재 포탈 도메인 가져오기

					//현재 포탈 포트 가져오기
					String port = commonprop.getProperty("ikep4.activex.editor.port");
					//Tagfree ActiveX Editor Util => FileService, domain, port 생성자로 넘김 
					MimeUtil util = new MimeUtil(fileService, portal.getPortalHost(), port);
					util.setMimeValue(oldRecord.getContents());
					//Mime 데이타 decoding
					util.processDecoding();
					//editor 첨부된 이미지 확인
					if(util.getFileLinkList() != null && util.getFileLinkList().size()>0){
						List<FileLink> newFileLinkList = new ArrayList<FileLink>();
						//기존 등록된 파일 처리
						if(oldRecord.getEditorFileLinkList() != null){
							for (int i = 0; i < oldRecord.getEditorFileLinkList().size(); i++) {
								FileLink fLink = (FileLink) oldRecord.getEditorFileLinkList().get(i);
								newFileLinkList.add(fLink);
							}
						}
						//새로 등록된 파일 처리
						for (int i = 0; i < util.getFileLinkList().size(); i++) {
							FileLink fileLink = (FileLink)util.getFileLinkList().get(i);							
							newFileLinkList.add(fileLink);
						}
						
						oldRecord.setEditorFileLinkList(newFileLinkList);
					}
					//내용 가져오기
					String content = util.getDecodedHtml(false);		
					content = content.trim();
					//내용세팅
					oldRecord.setContents(content);
					
				} catch (Exception e) {
					throw new IKEP4ApplicationException("", e);
				}
			}
		}

        try {
            //파일 등록 
            if ( oldRecord.getFileLinkList() != null ) {
                fileService.saveFileLink( oldRecord.getFileLinkList(), Integer.toString( seqNum ),"BW", user );
            }

            String updaterName = user.getUserName();

            oldRecord.setUpdaterName( updaterName );
            oldRecord.setUpdaterId( user.getUserId() );
            oldRecord.setSeqNum( seqNum );

            this.oldRecordDao.update( oldRecord );

        } catch ( Exception e ) {
            throw new IKEP4ApplicationException( "DB UPDATE 실패", e );
        }

    }
    
    public void updateOldRecordMaster( OldRecordHistory oldRecord, User user,
            int seqNum ) {

        try {
            String updaterName = user.getUserName();

            oldRecord.setUpdaterName( updaterName );
            oldRecord.setUpdaterId( user.getUserId() );
            oldRecord.setSeqNum( seqNum );

            this.oldRecordDao.updateMaster( oldRecord );

        } catch ( Exception e ) {
            throw new IKEP4ApplicationException( "DB UPDATE 실패", e );
        }

    }
    
    public void deleteOldRecord( OldRecordHistory oldRecord  ){
        oldRecordDao.deleteOldRecord( oldRecord );
    }
    


}
