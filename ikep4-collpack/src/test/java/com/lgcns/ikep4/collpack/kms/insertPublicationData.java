package com.lgcns.ikep4.collpack.kms;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.lgcns.ikep4.support.publication.dao.PublicationDao;
import com.lgcns.ikep4.support.publication.model.Publication;
import com.lgcns.ikep4.support.publication.service.PublicationService;
import com.lgcns.ikep4.util.lang.StringUtil;


public class insertPublicationData extends BaseKmsTestCase {
    
    @Autowired
    PublicationDao publicationDao;
    

    
    @Autowired
    PublicationService publicationServiceImpl;
    
    
    
    @Test
    @Rollback(false)
    public void testCreate2(){
        
        List<HashMap<String,String>> listPublication = (List<HashMap<String,String>>)publicationDao.listPublicationForIamMoorim();
        
        Publication publication = null;
        
        for(HashMap resultData : listPublication){
            publication = setPublication(resultData);
            publicationServiceImpl.createPublication( publication );
        }
        
        List<HashMap<String,String>> listPublication2 = (List<HashMap<String,String>>)publicationDao.listPublicationForGoodPaper();
        
        Publication publication2 = null;
        
        for(HashMap resultData : listPublication2){
            publication2 = setPublication(resultData);
            publicationServiceImpl.createPublication( publication2 );
        }
        
        
    }
    
    /**
     * 
        id,
        type,
        company_name,
        name,
        address,
        tel_no,
        e_mail,
        group_id,
        group_name,
        zip1_no,
        zip2_no,
        count,
        updater_id,
        updater_name,
        update_date,
        register_id,
        register_name,
        regist_date
     */
    
    private Publication setPublication(HashMap<String, String> resultData){
        
        Publication publication = new Publication();
        
        publication.setType(StringUtil.nvl(resultData.get( "TYPE" ),""));
        publication.setCompanyName( StringUtil.nvl(resultData.get( "company_name" ),"") );
        publication.setName( StringUtil.nvl(resultData.get( "name" ),"") );
        publication.setAddress( StringUtil.nvl(resultData.get( "address" ),"") );
        publication.setTelNo( StringUtil.nvl(resultData.get( "tel_no" ),"") );
        publication.seteMail( StringUtil.nvl(resultData.get( "e_mail" ),"") );
        publication.setGroupId( StringUtil.nvl(resultData.get( "group_id" ),"") );
        publication.setGroupName( StringUtil.nvl(resultData.get( "group_name" ),"") );
        publication.setZip1No( StringUtil.nvl(resultData.get( "zip1_no" ),"") );
        publication.setZip2No( StringUtil.nvl(resultData.get( "zip2_no" ),"") );
        publication.setCount( StringUtil.nvl(resultData.get( "count" ),"") );
        publication.setRegisterId( "admin" );
        publication.setRegisterName( "관리자" );
        
        return publication;
  
        
    }
    
    
    
    
    

}
