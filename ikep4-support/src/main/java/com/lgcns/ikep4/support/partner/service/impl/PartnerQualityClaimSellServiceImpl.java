
package com.lgcns.ikep4.support.partner.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.partner.dao.PartnerDao;
import com.lgcns.ikep4.support.partner.dao.PartnerQualityClaimSellDao;
import com.lgcns.ikep4.support.partner.model.PartnerClaimSellHistory;
import com.lgcns.ikep4.support.partner.search.PartnerQualityClaimSellSearchCondition;
import com.lgcns.ikep4.support.partner.service.PartnerQualityClaimSellService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;

import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 
 * 고객정보 품질/클레임 상담이력 구현체 클래스 
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Service
@Transactional
public class PartnerQualityClaimSellServiceImpl implements PartnerQualityClaimSellService {

    @Autowired
    private PartnerQualityClaimSellDao partnerQualityClaimSellDao;

    @Autowired
    private FileService fileService;


    @Autowired
    private PartnerDao partnerDao;
    
    @Autowired
    private IdgenService idgenService;

    public SearchResult<PartnerClaimSellHistory> listQualityClaimSellBySearchCondition(
            PartnerQualityClaimSellSearchCondition searchCondition ) {

        Integer count = this.partnerQualityClaimSellDao.countBySearchCondition( searchCondition );

        searchCondition.terminateSearchCondition( count );

        SearchResult<PartnerClaimSellHistory> searchResult = null;
        if ( searchCondition.isEmptyRecord() ) {
            searchResult = new SearchResult<PartnerClaimSellHistory>( searchCondition );
        } else {
            List<PartnerClaimSellHistory> qualityClaimSellHistoryList = this.partnerQualityClaimSellDao
                    .listQualityClaimSellBySearchCondition( searchCondition );
            

            searchResult = new SearchResult<PartnerClaimSellHistory>( qualityClaimSellHistoryList, searchCondition );

        }

        return searchResult;
    }
    
    public List<PartnerClaimSellHistory> crStatisticsList(PartnerQualityClaimSellSearchCondition searchCondition){
    	
    	List<PartnerClaimSellHistory> list = partnerQualityClaimSellDao.crStatisticsList(searchCondition);
    	
    	return list;
    }

    public PartnerClaimSellHistory getQualityClaimSell( int itemId ) {

        PartnerClaimSellHistory qualityClaimSellHistory = partnerQualityClaimSellDao.getQualityClaimSell( itemId );

        //파일 조회
        List<FileData> fileDataList = fileService.getItemFile( Integer.toString( itemId ), "N" );
        qualityClaimSellHistory.setFileDataList( fileDataList );

        return qualityClaimSellHistory;
    }
    
    public PartnerClaimSellHistory getQualityClaimSellOther( int itemId ) {

        PartnerClaimSellHistory qualityClaimSellHistory = partnerQualityClaimSellDao.getQualityClaimSellOther( itemId );

        //파일 조회
        List<FileData> fileDataList = fileService.getItemFile( Integer.toString( qualityClaimSellHistory.getSeqNum() ), "N" );
        qualityClaimSellHistory.setFileDataList( fileDataList );

        return qualityClaimSellHistory;
    }

    public int createQualityClaimSell( PartnerClaimSellHistory qualityClaimSell, User user, List<MultipartFile> fileList ) {

        int seqNum = 0;
        try {
            qualityClaimSell.setLinereplyCount( 0 );

            String registerName = partnerDao.getUserName( qualityClaimSell.getRegisterId() );
            String updaterName = partnerDao.getUserName( qualityClaimSell.getUpdaterId() );
            
            qualityClaimSell.setRegisterName( registerName );
            qualityClaimSell.setUpdaterName( updaterName );
            //if(StringUtils.isEmpty(qualityClaimSell.getSapId())){
            	qualityClaimSell.setPartnerId(idgenService.getNextId());
            //}else{
            //	qualityClaimSell.setPartnerId(qualityClaimSell.getSapId());
            //}

            seqNum = partnerQualityClaimSellDao.createQualityClaimSell( qualityClaimSell );

            if ( qualityClaimSell.getFileLinkList() != null ) {
                fileService.saveFileLink( qualityClaimSell.getFileLinkList(), Integer.toString( seqNum ),
                        IKepConstant.ITEM_TYPE_CODE_PORTAL, user );
            }

        } catch ( Exception e ) {

            throw new IKEP4ApplicationException( "DB UPDATE 실패", e );

        }

        return seqNum;
    }

    public void updateQualityClaimSell( PartnerClaimSellHistory qualityClaimSell, List<MultipartFile> fileList, User user,
            int seqNum ) {

        try {
            //파일 등록 
            if ( qualityClaimSell.getFileLinkList() != null ) {
                fileService.saveFileLink( qualityClaimSell.getFileLinkList(), Integer.toString( seqNum ),
                        IKepConstant.ITEM_TYPE_CODE_PORTAL, user );
            }

            String updaterName = partnerDao.getUserName( user.getUserId() );

            qualityClaimSell.setUpdaterName( updaterName );
            qualityClaimSell.setUpdaterId( user.getUserId() );
            qualityClaimSell.setSeqNum( seqNum );

            this.partnerQualityClaimSellDao.update( qualityClaimSell );

        } catch ( Exception e ) {
            throw new IKEP4ApplicationException( "DB UPDATE 실패", e );
        }

    }
    
    public void updateQualityClaimSellMaster( PartnerClaimSellHistory qualityClaimSell, User user,
            int seqNum ) {

        try {

            String updaterName = partnerDao.getUserName( user.getUserId() );

            qualityClaimSell.setUpdaterName( updaterName );
            qualityClaimSell.setUpdaterId( user.getUserId() );
            qualityClaimSell.setSeqNum( seqNum );

            this.partnerQualityClaimSellDao.updateMaster( qualityClaimSell );

        } catch ( Exception e ) {
            throw new IKEP4ApplicationException( "DB UPDATE 실패", e );
        }

    }
    
    public void deleteQualityClaimSell( PartnerClaimSellHistory qualityClaimSell  ){
        partnerQualityClaimSellDao.deleteQualityClaimSell( qualityClaimSell );
    }
    


}
