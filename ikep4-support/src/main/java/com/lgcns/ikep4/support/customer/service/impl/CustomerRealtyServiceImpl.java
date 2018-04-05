package com.lgcns.ikep4.support.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.dao.CustomerDao;
import com.lgcns.ikep4.support.customer.dao.CustomerRealtyDao;
import com.lgcns.ikep4.support.customer.model.Realty;
import com.lgcns.ikep4.support.customer.search.RealtySearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerRealtyService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;


@Service
@Transactional
public class CustomerRealtyServiceImpl extends GenericServiceImpl<Realty, String> implements CustomerRealtyService {

    
    @Autowired
    
    private CustomerRealtyDao customerRealtyDao;
    
    @Autowired
    private CustomerDao customerDao;
    
    @Autowired
    private FileService fileService;
    
    
    public SearchResult<Realty> listRealtyBySearchCondition(RealtySearchCondition searchCondition){
        
        Integer count = this.customerRealtyDao.countBySearchCondition( searchCondition );
        
        searchCondition.terminateSearchCondition( count );
        
        SearchResult<Realty> searchResult = null;
        
        if(searchCondition.isEmptyRecord()){
            searchResult = new SearchResult<Realty>( searchCondition );
        }else{
            List<Realty> realtyList = this.customerRealtyDao.listRealtyBySearchCondition( searchCondition );
            
            searchResult = new SearchResult<Realty>( realtyList , searchCondition );
        }
        return searchResult;
    }
    
    public List<Realty> selectRealty(){
    	
    	List<Realty> realtyList = customerRealtyDao.selectRealty();
    	return realtyList;
    }
    
    public Realty getRealty( int itemId ){
        
        Realty realty = customerRealtyDao.getRealty(itemId);
        
        //파일 조회
        List<FileData> fileDataList = fileService.getItemFile( Integer.toString(itemId)+"_CH", "N" );
        realty.setFileDataList( fileDataList );
        
        return realty;
    }
    
    
    public int createRealty( Realty realty, User user, List<MultipartFile> fileList ){
        
        int seqNum = 0;
        try{
            
            seqNum = customerRealtyDao.createRealty(realty);
            
        }catch(Exception e){
            throw new IKEP4ApplicationException( "DB 업데이트 실패",e );
        }
        
        return seqNum;
      
    }

    public void updateRealty( Realty realty, List<MultipartFile> fileList, User user, int seqNum ) {
        
        try{
            
            String updaterName = customerDao.getUserName( user.getUserId() ); 
            
            realty.setUpdaterName( updaterName );
            realty.setUpdaterId( user.getUserId() );
            realty.setSeqNum( seqNum );
            
            this.customerRealtyDao.update( realty );
            
            
        }catch(Exception e){
            throw new IKEP4ApplicationException( "DB UPDATE 실패", e );
        }
    }

    public void deleteRealty( Realty realty ) {
       
        customerRealtyDao.deleteRealty(realty);
    }

    public void syncCustomerName( Realty realty ) {
        customerRealtyDao.syncCustomerName(realty);
        
    }
    
    
    
    
}
