package com.lgcns.ikep4.support.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.dao.CustomerCommonDao;
import com.lgcns.ikep4.support.customer.dao.CustomerDao;
import com.lgcns.ikep4.support.customer.model.Customer;
import com.lgcns.ikep4.support.customer.model.ManCareer;
import com.lgcns.ikep4.support.customer.model.ManFamily;
import com.lgcns.ikep4.support.customer.model.ManInfoItem;
import com.lgcns.ikep4.support.customer.search.ManInfoItemSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * CustomerService 구현체 클래스 
 *
 * @author SongHeeJung
 * @version $Id$
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	
	@Autowired
	private CustomerDao customerDao;
	
   @Autowired
    private FileService fileService;
   
   @Autowired
    private ACLService aclService;
   
   @Autowired
    private CustomerCommonDao customerCommonDao;

   
   public boolean checkAdmin(String userId){
       
       List<User> admins = aclService.listSystemAdminAsUser( "customer" );
       boolean isAdmin = false;
       
       for(int i=0; i<admins.size(); i++){
           
           if(userId.equals( admins.get( i ).getUserId())){
               isAdmin = true;              
           }           
       }
       
       return isAdmin;   
   }

   
	
	public SearchResult<ManInfoItem> listManInfoItemBySearchCondition(
			ManInfoItemSearchCondition searchCondition) {
		
		Integer count = this.customerDao.countBySearchCondition(searchCondition);
		
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<ManInfoItem> searchResult = null;
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<ManInfoItem>(searchCondition);
		}else{
			List<ManInfoItem> manInfoItemList = this.customerDao.listManInfoItemBySearchCondition(searchCondition);
			
			
			searchResult = new SearchResult<ManInfoItem>(manInfoItemList,searchCondition);
			
		}
		
		return searchResult;
	}
	
	
	public ManInfoItem readManInfoItem(int itemId){
		
		ManInfoItem manInfoItem = this.customerDao.readManInfoItem(itemId);
		return manInfoItem;
	}
	
    public List<ManInfoItem> readManFamily(String itemId){
        
        List<ManInfoItem> manFamily = this.customerDao.listManFamily( itemId );
        return manFamily;
    }
    
    public List<ManInfoItem> readManCareer(String itemId){
        
        List<ManInfoItem> manCareer = this.customerDao.listManCareer( itemId );
        return manCareer;
    }
    
    public void updateManInfo(ManInfoItem manInfoItem , int familyCnt, int careerCnt, List<MultipartFile> fileList, User user){
        
        
        try{
            
            String fileId = "";
            
            if(fileList.size() > 0 && !fileList.get(0).isEmpty()) {
                List<FileData> uploadList = fileService.uploadFile(fileList, "", "", "0", user);
                
                fileId = uploadList.get(0).getFileId();
                fileService.createFileLink(fileId, "", IKepConstant.ITEM_TYPE_CODE_PORTAL, user);
            }
            
            String updaterName = customerDao.getUserName( user.getUserId() );
            
            manInfoItem.setSajinSys( fileId );
            manInfoItem.setUpdaterId( user.getUserId() );
            manInfoItem.setUpdaterName( updaterName );
            
            this.customerDao.updateManInfo( manInfoItem );
                 
            
            //가족정보 업데이트  (기존정보 삭제 후 다시 저장)
            this.customerDao.deleteManInfoFamily( Integer.toString( manInfoItem.getSeqNum()) );
            
            saveManInfoFamily( manInfoItem, familyCnt, user );
    
            //경력정보 업데이트  (기존정보 삭제 후 다시 저장)
            this.customerDao.deleteManInfoCareer( Integer.toString( manInfoItem.getSeqNum()) );  
   
            saveManInfoCareer( manInfoItem, careerCnt, user );
      
        }catch(Exception e){
            throw new IKEP4ApplicationException("DB UPDATE 실패", e);
        }
        
    }



    private void saveManInfoCareer( ManInfoItem manInfoItem, int careerCnt, User user ) {
        if(careerCnt != 0){
            for(int i=0; i<careerCnt ;i++){
                String itemId = Integer.toString( manInfoItem.getSeqNum() );

                String[] mainCareer = new String[careerCnt];
                String[] preCareer = new String[careerCnt];
                String[] mainBusiness = new String[careerCnt];
                
                String[] mainCareerTemp = manInfoItem.getMainCareer().split( "," );
                for(int j=0; j<mainCareerTemp.length;j++){
                    mainCareer[j]=mainCareerTemp[j];
                }
                
                String[] preCareerTemp = manInfoItem.getPreCareer().split( "," );
                for(int j=0; j<preCareerTemp.length;j++){
                    preCareer[j]=preCareerTemp[j];
                }
                
                String[] mainBusinessTemp = manInfoItem.getMainBusiness().split( "," );
                for(int j=0; j<mainBusinessTemp.length;j++){
                    mainBusiness[j]=mainBusinessTemp[j];
                }
  
                ManCareer manCareer = new ManCareer();
                manCareer.setItemId( itemId );
                manCareer.setCareerNum(Integer.toString( i ));
                manCareer.setMainCareer( StringUtil.nvl(mainCareer[i],"") );
                manCareer.setPreCareer( StringUtil.nvl(preCareer[i],"") );
                manCareer.setMainBusiness( StringUtil.nvl(mainBusiness[i],"") );
                manCareer.setRegisterId( user.getUserId());
                manCareer.setUpdaterId( user.getUserId());
                
                if(manCareer.getMainCareer().equals( "" ) && manCareer.getPreCareer().equals( "" ) && manCareer.getMainBusiness().equals( "" )){
                 //TODO  어떻게 처리해야하나..
                }else{
                    this.customerDao.createManInfoCareer( manCareer );
                }
                
            }
        }
    }



    private void saveManInfoFamily( ManInfoItem manInfoItem, int familyCnt, User user ) {
        if(familyCnt != 0){
            for(int i=0; i<familyCnt ;i++){
               
                String[] familyName = new String[familyCnt];
                String[] familyRelation = new String[familyCnt];
                
                String[] familyNameTemp = manInfoItem.getFamilyName().split( "," );
                for(int j=0; j<familyNameTemp.length;j++){
                    familyName[j]=familyNameTemp[j];
                }
                
                String[] familyRelationTemp = manInfoItem.getFamilyRelation().split( "," );
                for(int j=0; j<familyRelationTemp.length;j++){
                    familyRelation[j]=familyRelationTemp[j];
                }
  
                ManFamily manFamily = new ManFamily();
                manFamily.setItemId( Integer.toString(manInfoItem.getSeqNum()));
                manFamily.setFamilyNum(Integer.toString( i ));
                manFamily.setFamilyName( StringUtil.nvl(familyName[i],"") );
                manFamily.setFamilyRelation( StringUtil.nvl(familyRelation[i],"") );
                manFamily.setRegisterId( user.getUserId() );
                manFamily.setUpdaterId( user.getUserId() );
                
                if(manFamily.getFamilyRelation().equals( "" ) && manFamily.getFamilyName().equals( "" )){
                    //TODO  어떻게 처리해야하나..
                }else{
                    this.customerDao.createManInfoFamily( manFamily );
                }
            }
        }
    }
    
    
    
    public int createManInfoItem(ManInfoItem manInfoItem, int familyCnt , int careerCnt , List<MultipartFile> fileList, User user){

         int manId ; //새로 생성된 인물 id
         
      try{
          
          String fileId = "";
          
          //파일 첨부
          if(fileList.size() > 0 && !fileList.get(0).isEmpty()) {
              List<FileData> uploadList = fileService.uploadFile(fileList, "", "", "0", user);
              
              fileId = uploadList.get(0).getFileId();
              fileService.createFileLink(fileId, "", IKepConstant.ITEM_TYPE_CODE_PORTAL, user);
          }
          
          manInfoItem.setSajinSys( fileId );
          
          String registerName = customerDao.getUserName( manInfoItem.getRegisterId() );
          String updaterName = customerDao.getUserName( manInfoItem.getUpdaterId() );
          
          manInfoItem.setRegisterName( registerName );
          manInfoItem.setUpdaterName( updaterName );
          
          customerDao.createManInfoItem( manInfoItem ); 
          
          int seqNum = customerDao.getSeqNum(manInfoItem);
          manId = seqNum;
          
          if(familyCnt != 0){

    
              if(familyCnt != 0){
                  for(int i=0; i<familyCnt;i++){
                     
                      String[] familyName = new String[familyCnt];
                      String[] familyRelation = new String[familyCnt];
                      
                      String[] familyNameTemp = manInfoItem.getFamilyName().split( "," );//뒤가 없을때는 length 가 짧아진다.
                      for(int j=0; j<familyNameTemp.length;j++){
                          familyName[j]=familyNameTemp[j];
                      }
                      
                      String[] familyRelationTemp = manInfoItem.getFamilyRelation().split( "," );//뒤가 없을때는 length 가 짧아진다.
                      for(int j=0; j<familyRelationTemp.length;j++){
                          familyRelation[j]=familyRelationTemp[j];
                      }
    
                      ManFamily manFamily = new ManFamily();
                      manFamily.setItemId( Integer.toString( seqNum) );
                      manFamily.setFamilyNum(Integer.toString( i ));
                      manFamily.setFamilyName( StringUtil.nvl(familyName[i],"") );
                      manFamily.setFamilyRelation( StringUtil.nvl(familyRelation[i],"") );
                      manFamily.setRegisterId( manInfoItem.getRegisterId() );
                      manFamily.setUpdaterId( manInfoItem.getUpdaterId() );
                    
                 
                      
                      if(manFamily.getFamilyRelation().equals( "" ) && manFamily.getFamilyName().equals( "" )){
                         //여기엔??
                      }else{
                          this.customerDao.createManInfoFamily( manFamily );
                      }
                  }
              }
             }
          
          
          //경력정보 관련
          
          
          if(careerCnt != 0){
              for(int i=0; i<careerCnt;i++){
                  
                  
              
                  String[] mainCareer = new String[careerCnt];
                  String[] preCareer = new String[careerCnt];
                  String[] mainBusiness = new String[careerCnt];
                  
                  String[] mainCareerTemp = manInfoItem.getMainCareer().split( "," );//뒤가 없을때는 length 가 짧아진다.
                  for(int j=0; j<mainCareerTemp.length;j++){
                      mainCareer[j]=mainCareerTemp[j];
                  }
                  
                  String[] preCareerTemp = manInfoItem.getPreCareer().split( "," );//뒤가 없을때는 length 가 짧아진다.
                  for(int j=0; j<preCareerTemp.length;j++){
                      preCareer[j]=preCareerTemp[j];
                  }
                  
                  String[] mainBusinessTemp = manInfoItem.getMainBusiness().split( "," );//뒤가 없을때는 length 가 짧아진다.
                  for(int j=0; j<mainBusinessTemp.length;j++){
                      mainBusiness[j]=mainBusinessTemp[j];
                  }

                  ManCareer manCareer = new ManCareer();
                  manCareer.setItemId( Integer.toString( seqNum) );
                  manCareer.setCareerNum( Integer.toString( i ) );
                  manCareer.setMainCareer( StringUtil.nvl(mainCareer[i],"") );
                  manCareer.setPreCareer( StringUtil.nvl(preCareer[i],"") );
                  manCareer.setMainBusiness( StringUtil.nvl(mainBusiness[i],"") );
                  manCareer.setRegisterId( manInfoItem.getRegisterId() );
                  manCareer.setUpdaterId( manInfoItem.getUpdaterId() );
                  
                  if(manCareer.getMainCareer().equals( "" ) && manCareer.getPreCareer().equals( "" ) && manCareer.getMainBusiness().equals( "" )){
                     
                  }else{
                      this.customerDao.createManInfoCareer( manCareer );
                  }
                  
              }
          }
   
        
          
      }catch(Exception e){
          throw new IKEP4ApplicationException("DB UPDATE 실패", e);
      }
      
      
      return manId;
        
    }
    
    
  


    public void deleteManInfo( ManInfoItem manInfoItem){
        
         customerDao.deleteManInfo( manInfoItem );
        
    }



    public List<Customer> getCustomerId( String customer ) {
        
        return customerCommonDao.getCustomerId( customer );
    }



    public void syncCustomerName( ManInfoItem manInfoItem ) {
        customerDao.syncCustomerName(manInfoItem);
        
    }



    public boolean checkAccess( String userId ) {
       
      
        boolean isOK = false;
        
        Integer result = customerDao.checkAccess(userId);
        
        if(result != 0 ){
            isOK = true;
        }
        
        return isOK;
    }
   

}
