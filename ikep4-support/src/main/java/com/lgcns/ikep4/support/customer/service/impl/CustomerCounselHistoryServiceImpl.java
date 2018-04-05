package com.lgcns.ikep4.support.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.dao.CustomerCounselHistoryDao;
import com.lgcns.ikep4.support.customer.dao.CustomerDao;
import com.lgcns.ikep4.support.customer.model.CounselHistory;
import com.lgcns.ikep4.support.customer.search.CounselHistorySearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerCounselHistoryService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;


@Service
@Transactional
public class CustomerCounselHistoryServiceImpl extends GenericServiceImpl<CounselHistory, String> implements CustomerCounselHistoryService {

    
    @Autowired
    
    private CustomerCounselHistoryDao customerCounselHistoryDao;
    
    @Autowired
    private CustomerDao customerDao;
    
    @Autowired
    private FileService fileService;
    
    
    public SearchResult<CounselHistory> listCounselHistoryBySearchCondition(CounselHistorySearchCondition searchCondition){
        
        Integer count = this.customerCounselHistoryDao.countBySearchCondition( searchCondition );
        
        searchCondition.terminateSearchCondition( count );
        
        SearchResult<CounselHistory> searchResult = null;
        
        if(searchCondition.isEmptyRecord()){
            searchResult = new SearchResult<CounselHistory>( searchCondition );
        }else{
            List<CounselHistory> counselHistoryList = this.customerCounselHistoryDao.listCounselHistoryBySearchCondition( searchCondition );
            
            searchResult = new SearchResult<CounselHistory>( counselHistoryList , searchCondition );
        }
        return searchResult;
    }
    
    public CounselHistory getCounselHistory( int itemId ){
        
        CounselHistory counselHistory = customerCounselHistoryDao.getCounselHistory(itemId);
        
        //파일 조회
        List<FileData> fileDataList = fileService.getItemFile( Integer.toString(itemId)+"_CH", "N" );
        counselHistory.setFileDataList( fileDataList );
        
        return counselHistory;
    }
    
    
    public int createCounselHistory( CounselHistory counselHistory, User user, List<MultipartFile> fileList ){
        
        int seqNum = 0;
        try{
            
            seqNum = customerCounselHistoryDao.createCounselHistory(counselHistory);
            
            if(counselHistory.getFileLinkList() !=null){
                fileService.saveFileLink( counselHistory.getFileLinkList(), Integer.toString( seqNum )+"_CH", IKepConstant.ITEM_TYPE_CODE_PORTAL, user );
            }
            
            
        }catch(Exception e){
            throw new IKEP4ApplicationException( "DB 업데이트 실패",e );
        }
        
        return seqNum;
      
    }

    public void updateCounselHistory( CounselHistory counselHistory, List<MultipartFile> fileList, User user, int seqNum ) {
        
        try{
            
            //파일 등록 
            if ( counselHistory.getFileLinkList() != null ) {
                fileService.saveFileLink( counselHistory.getFileLinkList(), Integer.toString( seqNum )+"_CH",
                        IKepConstant.ITEM_TYPE_CODE_PORTAL, user );
            }
            
            String updaterName = customerDao.getUserName( user.getUserId() ); 
            
            counselHistory.setUpdaterName( updaterName );
            counselHistory.setUpdaterId( user.getUserId() );
            counselHistory.setSeqNum( seqNum );
            
            this.customerCounselHistoryDao.update( counselHistory );
            
            
        }catch(Exception e){
            throw new IKEP4ApplicationException( "DB UPDATE 실패", e );
        }
    }

    public void deleteCounselHistory( CounselHistory counselHistory ) {
       
        customerCounselHistoryDao.deleteCounselHistory(counselHistory);
    }

    public void syncCustomerName( CounselHistory counselHistory ) {
        customerCounselHistoryDao.syncCustomerName(counselHistory);
        
    }
    
    
    
    
}
