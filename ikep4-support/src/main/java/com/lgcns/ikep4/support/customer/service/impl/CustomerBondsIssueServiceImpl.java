
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
import com.lgcns.ikep4.support.customer.dao.CustomerBondsIssueDao;
import com.lgcns.ikep4.support.customer.model.BondsIssueHistory;
import com.lgcns.ikep4.support.customer.search.CustomerBondsIssueSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerBondsIssueService;
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
public class CustomerBondsIssueServiceImpl implements CustomerBondsIssueService {

    @Autowired
    private CustomerBondsIssueDao customerBondsIssueDao;

    @Autowired
    private FileService fileService;


    @Autowired
    private CustomerDao customerDao;

    public SearchResult<BondsIssueHistory> listBondsIssueBySearchCondition(
            CustomerBondsIssueSearchCondition searchCondition ) {

        Integer count = this.customerBondsIssueDao.countBySearchCondition( searchCondition );

        searchCondition.terminateSearchCondition( count );

        SearchResult<BondsIssueHistory> searchResult = null;
        if ( searchCondition.isEmptyRecord() ) {
            searchResult = new SearchResult<BondsIssueHistory>( searchCondition );
        } else {
            List<BondsIssueHistory> bondsIssueHistoryList = this.customerBondsIssueDao
                    .listBondsIssueBySearchCondition( searchCondition );
            
            for(int i=0; i<bondsIssueHistoryList.size(); i++){
                if(bondsIssueHistoryList.get( i ) != null){
                	if(!StringUtils.isEmpty(bondsIssueHistoryList.get( i ).getFactory())){
                		if(bondsIssueHistoryList.get( i ).getFactory().equals( "S" )){
                            bondsIssueHistoryList.get( i ).setFactory( "S" );
                        }else if(bondsIssueHistoryList.get( i ).getFactory().equals( "A" )){
                            bondsIssueHistoryList.get( i ).setFactory( "A" );
                        }else if(bondsIssueHistoryList.get( i ).getFactory().equals( "B" )){
                            bondsIssueHistoryList.get( i ).setFactory( "B" );
                        }else if(bondsIssueHistoryList.get( i ).getFactory().equals( "C" )){
                            bondsIssueHistoryList.get( i ).setFactory( "C" );
                        }else if(bondsIssueHistoryList.get( i ).getFactory().equals( "D" )){
                            bondsIssueHistoryList.get( i ).setFactory( "D" );
                        }else if(bondsIssueHistoryList.get( i ).getFactory().equals( "E" )){
                            bondsIssueHistoryList.get( i ).setFactory( "E" );
                        }else if(bondsIssueHistoryList.get( i ).getFactory().equals( "F" )){
                            bondsIssueHistoryList.get( i ).setFactory( "F" );
                        }
                	}
                	if(!StringUtils.isEmpty(bondsIssueHistoryList.get( i ).getClaimGubun())){
	                    if(bondsIssueHistoryList.get( i ).getClaimGubun().equals( "1" )){
	                        bondsIssueHistoryList.get( i ).setClaimGubun( "신용보험" );
	                    }else if(bondsIssueHistoryList.get( i ).getClaimGubun().equals( "2" )){
	                        bondsIssueHistoryList.get( i ).setClaimGubun( "담보" );
	                    }else if(bondsIssueHistoryList.get( i ).getClaimGubun().equals( "3" )){
	                        bondsIssueHistoryList.get( i ).setClaimGubun( "여신한도" );
	                    }else if(bondsIssueHistoryList.get( i ).getClaimGubun().equals( "4" )){
	                        bondsIssueHistoryList.get( i ).setClaimGubun( "Sub거래선" );
	                    }else if(bondsIssueHistoryList.get( i ).getClaimGubun().equals( "5" )){
	                        bondsIssueHistoryList.get( i ).setClaimGubun( "기타" );
	                    }else if(bondsIssueHistoryList.get( i ).getClaimGubun().equals( "6" )){
	                        bondsIssueHistoryList.get( i ).setClaimGubun( "배서인" );
	                    }
                	}
                }
            }

            searchResult = new SearchResult<BondsIssueHistory>( bondsIssueHistoryList, searchCondition );

        }

        return searchResult;
    }

    public BondsIssueHistory getBondsIssue( int itemId ) {

        BondsIssueHistory bondsIssueHistory = customerBondsIssueDao.getBondsIssue( itemId );

        //파일 조회
        List<FileData> fileDataList = fileService.getItemFile( Integer.toString( itemId ), "N" );
        bondsIssueHistory.setFileDataList( fileDataList );

        return bondsIssueHistory;
    }

    public int createBondsIssue( BondsIssueHistory bondsIssue, User user, List<MultipartFile> fileList ) {

        int seqNum = 0;
        try {
            bondsIssue.setLinereplyCount( 0 );

            String registerName = customerDao.getUserName( bondsIssue.getRegisterId() );
            String updaterName = customerDao.getUserName( bondsIssue.getUpdaterId() );

            bondsIssue.setRegisterName( registerName );
            bondsIssue.setUpdaterName( updaterName );

            seqNum = customerBondsIssueDao.createBondsIssue( bondsIssue );

            if ( bondsIssue.getFileLinkList() != null ) {
                fileService.saveFileLink( bondsIssue.getFileLinkList(), Integer.toString( seqNum ),
                        IKepConstant.ITEM_TYPE_CODE_PORTAL, user );
            }

        } catch ( Exception e ) {

            throw new IKEP4ApplicationException( "DB UPDATE 실패", e );

        }

        return seqNum;
    }

    public void updateBondsIssue( BondsIssueHistory bondsIssue, List<MultipartFile> fileList, User user,
            int seqNum ) {

        try {
            //파일 등록 
            if ( bondsIssue.getFileLinkList() != null ) {
                fileService.saveFileLink( bondsIssue.getFileLinkList(), Integer.toString( seqNum ),
                        IKepConstant.ITEM_TYPE_CODE_PORTAL, user );
            }

            String updaterName = customerDao.getUserName( user.getUserId() );

            bondsIssue.setUpdaterName( updaterName );
            bondsIssue.setUpdaterId( user.getUserId() );
            bondsIssue.setSeqNum( seqNum );

            this.customerBondsIssueDao.update( bondsIssue );

        } catch ( Exception e ) {
            throw new IKEP4ApplicationException( "DB UPDATE 실패", e );
        }

    }
    
    public void updateBondsIssueMaster( BondsIssueHistory bondsIssue, User user,
            int seqNum ) {

        try {
            String updaterName = customerDao.getUserName( user.getUserId() );

            bondsIssue.setUpdaterName( updaterName );
            bondsIssue.setUpdaterId( user.getUserId() );
            bondsIssue.setSeqNum( seqNum );

            this.customerBondsIssueDao.updateMaster( bondsIssue );

        } catch ( Exception e ) {
            throw new IKEP4ApplicationException( "DB UPDATE 실패", e );
        }

    }
    
    public void deleteBondsIssue( BondsIssueHistory bondsIssue  ){
        customerBondsIssueDao.deleteBondsIssue( bondsIssue );
    }
    


}
