
package com.lgcns.ikep4.support.customer.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.dao.CustomerDao;
import com.lgcns.ikep4.support.customer.dao.CustomerQualityClaimSellDao;
import com.lgcns.ikep4.support.customer.model.QualityClaimSellHistory;
import com.lgcns.ikep4.support.customer.search.CustomerQualityClaimSellSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerQualityClaimSellService;
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
public class CustomerQualityClaimSellServiceImpl implements CustomerQualityClaimSellService {

    @Autowired
    private CustomerQualityClaimSellDao customerQualityClaimSellDao;

    @Autowired
    private FileService fileService;


    @Autowired
    private CustomerDao customerDao;

    public SearchResult<QualityClaimSellHistory> listQualityClaimSellBySearchCondition(
            CustomerQualityClaimSellSearchCondition searchCondition ) {

        Integer count = this.customerQualityClaimSellDao.countBySearchCondition( searchCondition );

        searchCondition.terminateSearchCondition( count );

        SearchResult<QualityClaimSellHistory> searchResult = null;
        if ( searchCondition.isEmptyRecord() ) {
            searchResult = new SearchResult<QualityClaimSellHistory>( searchCondition );
        } else {
            List<QualityClaimSellHistory> qualityClaimSellHistoryList = this.customerQualityClaimSellDao
                    .listQualityClaimSellBySearchCondition( searchCondition );
            
            for(int i=0; i<qualityClaimSellHistoryList.size(); i++){
                if(qualityClaimSellHistoryList.get( i ) != null){
                	if(!StringUtils.isEmpty(qualityClaimSellHistoryList.get( i ).getFactory())){
	                    if(qualityClaimSellHistoryList.get( i ).getFactory().equals( "1" )){
	                        qualityClaimSellHistoryList.get( i ).setFactory( "진주" );
	                    }else if(qualityClaimSellHistoryList.get( i ).getFactory().equals( "2" )){
	                        qualityClaimSellHistoryList.get( i ).setFactory( "울산" );
	                    }else if(qualityClaimSellHistoryList.get( i ).getFactory().equals( "3" )){
	                        qualityClaimSellHistoryList.get( i ).setFactory( "대구" );
	                    }
                	}
                	if(!StringUtils.isEmpty(qualityClaimSellHistoryList.get( i ).getClaimGubun())){
	                    if(qualityClaimSellHistoryList.get( i ).getClaimGubun().equals( "1" )){
	                        qualityClaimSellHistoryList.get( i ).setClaimGubun( "A/S" );
	                    }else if(qualityClaimSellHistoryList.get( i ).getClaimGubun().equals( "2" )){
	                        qualityClaimSellHistoryList.get( i ).setClaimGubun( "B/S" );
	                    }else if(qualityClaimSellHistoryList.get( i ).getClaimGubun().equals( "3" )){
	                        qualityClaimSellHistoryList.get( i ).setClaimGubun( "컴플레인" );
	                    }
                	}
                }
            }

            searchResult = new SearchResult<QualityClaimSellHistory>( qualityClaimSellHistoryList, searchCondition );

        }

        return searchResult;
    }

    public QualityClaimSellHistory getQualityClaimSell( int itemId ) {

        QualityClaimSellHistory qualityClaimSellHistory = customerQualityClaimSellDao.getQualityClaimSell( itemId );

        //파일 조회
        List<FileData> fileDataList = fileService.getItemFile( Integer.toString( itemId ), "N" );
        qualityClaimSellHistory.setFileDataList( fileDataList );

        return qualityClaimSellHistory;
    }

    public int createQualityClaimSell( QualityClaimSellHistory qualityClaimSell, User user, List<MultipartFile> fileList ) {

        int seqNum = 0;
        try {
            qualityClaimSell.setLinereplyCount( 0 );

            String registerName = customerDao.getUserName( qualityClaimSell.getRegisterId() );
            String updaterName = customerDao.getUserName( qualityClaimSell.getUpdaterId() );

            qualityClaimSell.setRegisterName( registerName );
            qualityClaimSell.setUpdaterName( updaterName );

            seqNum = customerQualityClaimSellDao.createQualityClaimSell( qualityClaimSell );

            if ( qualityClaimSell.getFileLinkList() != null ) {
                fileService.saveFileLink( qualityClaimSell.getFileLinkList(), Integer.toString( seqNum ),
                        IKepConstant.ITEM_TYPE_CODE_PORTAL, user );
            }

        } catch ( Exception e ) {

            throw new IKEP4ApplicationException( "DB UPDATE 실패", e );

        }

        return seqNum;
    }

    public void updateQualityClaimSell( QualityClaimSellHistory qualityClaimSell, List<MultipartFile> fileList, User user,
            int seqNum ) {

        try {
            //파일 등록 
            if ( qualityClaimSell.getFileLinkList() != null ) {
                fileService.saveFileLink( qualityClaimSell.getFileLinkList(), Integer.toString( seqNum ),
                        IKepConstant.ITEM_TYPE_CODE_PORTAL, user );
            }

            String updaterName = customerDao.getUserName( user.getUserId() );

            qualityClaimSell.setUpdaterName( updaterName );
            qualityClaimSell.setUpdaterId( user.getUserId() );
            qualityClaimSell.setSeqNum( seqNum );

            this.customerQualityClaimSellDao.update( qualityClaimSell );

        } catch ( Exception e ) {
            throw new IKEP4ApplicationException( "DB UPDATE 실패", e );
        }

    }
    
    public void updateQualityClaimSellMaster( QualityClaimSellHistory qualityClaimSell, User user,
            int seqNum ) {

        try {

            String updaterName = customerDao.getUserName( user.getUserId() );

            qualityClaimSell.setUpdaterName( updaterName );
            qualityClaimSell.setUpdaterId( user.getUserId() );
            qualityClaimSell.setSeqNum( seqNum );

            this.customerQualityClaimSellDao.updateMaster( qualityClaimSell );

        } catch ( Exception e ) {
            throw new IKEP4ApplicationException( "DB UPDATE 실패", e );
        }

    }
    
    public void deleteQualityClaimSell( QualityClaimSellHistory qualityClaimSell  ){
        customerQualityClaimSellDao.deleteQualityClaimSell( qualityClaimSell );
    }
    


}
