
package com.lgcns.ikep4.support.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.dao.CustomerDao;
import com.lgcns.ikep4.support.customer.dao.CustomerQualityClaimDao;
import com.lgcns.ikep4.support.customer.model.QualityClaimHistory;
import com.lgcns.ikep4.support.customer.search.CustomerQualityClaimSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerQualityClaimService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;

import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 고객정보 품질/클레임 상담이력 구현체 클래스 
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Service
@Transactional
public class CustomerQualityClaimServiceImpl implements CustomerQualityClaimService {

    @Autowired
    private CustomerQualityClaimDao customerQualityClaimDao;

    @Autowired
    private FileService fileService;


    @Autowired
    private CustomerDao customerDao;

    public SearchResult<QualityClaimHistory> listQualityClaimBySearchCondition(
            CustomerQualityClaimSearchCondition searchCondition ) {

        Integer count = this.customerQualityClaimDao.countBySearchCondition( searchCondition );

        searchCondition.terminateSearchCondition( count );

        SearchResult<QualityClaimHistory> searchResult = null;
        if ( searchCondition.isEmptyRecord() ) {
            searchResult = new SearchResult<QualityClaimHistory>( searchCondition );
        } else {
            List<QualityClaimHistory> qualityClaimHistoryList = this.customerQualityClaimDao
                    .listQualityClaimBySearchCondition( searchCondition );
            
            for(int i=0; i<qualityClaimHistoryList.size(); i++){
                if(qualityClaimHistoryList.get( i ) != null){
                	if(qualityClaimHistoryList.get( i ).getFactory() != null && qualityClaimHistoryList.get( i ).getFactory() != ""){
                		if(qualityClaimHistoryList.get( i ).getFactory().equals( "1" )){
                            qualityClaimHistoryList.get( i ).setFactory( "진주" );
                        }else if(qualityClaimHistoryList.get( i ).getFactory().equals( "2" )){
                            qualityClaimHistoryList.get( i ).setFactory( "울산" );
                        }else if(qualityClaimHistoryList.get( i ).getFactory().equals( "3" )){
                            qualityClaimHistoryList.get( i ).setFactory( "대구" );
                        }
                	}
                    
                	if(qualityClaimHistoryList.get( i ).getClaimGubun() != null && qualityClaimHistoryList.get( i ).getClaimGubun() != ""){
                		if(qualityClaimHistoryList.get( i ).getClaimGubun().equals( "1" )){
                            qualityClaimHistoryList.get( i ).setClaimGubun( "A/S" );
                        }else if(qualityClaimHistoryList.get( i ).getClaimGubun().equals( "2" )){
                            qualityClaimHistoryList.get( i ).setClaimGubun( "B/S" );
                        }else if(qualityClaimHistoryList.get( i ).getClaimGubun().equals( "3" )){
                            qualityClaimHistoryList.get( i ).setClaimGubun( "컴플레인" );
                        }
                	}
                }
            }

            searchResult = new SearchResult<QualityClaimHistory>( qualityClaimHistoryList, searchCondition );

        }

        return searchResult;
    }

    public QualityClaimHistory getQualityClaim( int itemId ) {

        QualityClaimHistory qualityClaimHistory = customerQualityClaimDao.getQualityClaim( itemId );

        //파일 조회
        List<FileData> fileDataList = fileService.getItemFile( Integer.toString( itemId ), "N" );
        qualityClaimHistory.setFileDataList( fileDataList );

        return qualityClaimHistory;
    }

    public int createQualityClaim( QualityClaimHistory qualityClaim, User user, List<MultipartFile> fileList ) {

        int seqNum = 0;
        try {
            qualityClaim.setLinereplyCount( 0 );

            String registerName = customerDao.getUserName( qualityClaim.getRegisterId() );
            String updaterName = customerDao.getUserName( qualityClaim.getUpdaterId() );

            qualityClaim.setRegisterName( registerName );
            qualityClaim.setUpdaterName( updaterName );

            seqNum = customerQualityClaimDao.createQualityClaim( qualityClaim );

            if ( qualityClaim.getFileLinkList() != null ) {
                fileService.saveFileLink( qualityClaim.getFileLinkList(), Integer.toString( seqNum ),
                        IKepConstant.ITEM_TYPE_CODE_PORTAL, user );
            }

        } catch ( Exception e ) {

            throw new IKEP4ApplicationException( "DB UPDATE 실패", e );

        }

        return seqNum;
    }

    public void updateQualityClaim( QualityClaimHistory qualityClaim, List<MultipartFile> fileList, User user,
            int seqNum ) {

        try {
            //파일 등록 
            if ( qualityClaim.getFileLinkList() != null ) {
                fileService.saveFileLink( qualityClaim.getFileLinkList(), Integer.toString( seqNum ),
                        IKepConstant.ITEM_TYPE_CODE_PORTAL, user );
            }

            String updaterName = customerDao.getUserName( user.getUserId() );

            qualityClaim.setUpdaterName( updaterName );
            qualityClaim.setUpdaterId( user.getUserId() );
            qualityClaim.setSeqNum( seqNum );

            this.customerQualityClaimDao.update( qualityClaim );

        } catch ( Exception e ) {
            throw new IKEP4ApplicationException( "DB UPDATE 실패", e );
        }

    }
    
    public void deleteQualityClaim( QualityClaimHistory qualityClaim  ){
        customerQualityClaimDao.deleteQualityClaim( qualityClaim );
    }
    


}
