package com.lgcns.ikep4.support.partner.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.constant.IKepConstant;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.partner.dao.PartnerDao;
import com.lgcns.ikep4.support.partner.model.Partner;
import com.lgcns.ikep4.support.partner.model.PartnerClaimSellHistory;
import com.lgcns.ikep4.support.partner.model.PartnerManCareer;
import com.lgcns.ikep4.support.partner.model.PartnerManFamily;
import com.lgcns.ikep4.support.partner.model.PartnerManInfoItem;
import com.lgcns.ikep4.support.partner.search.PartnerManInfoItemSearchCondition;
import com.lgcns.ikep4.support.partner.service.PartnerService;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 
 * PartnerService 구현체 클래스 
 *
 * @author SongHeeJung
 * @version $Id$
 */
@Service
@Transactional
public class PartnerServiceImpl implements PartnerService {

	
	@Autowired
	private PartnerDao partnerDao;
	
   @Autowired
    private ACLService aclService;
   
   @Autowired
   private FileService fileService;
   
   

   
   public boolean checkAdmin(String userId){
       
       List<User> admins = aclService.listSystemAdminAsUser( "Partner" );
       boolean isAdmin = false;
       
       for(int i=0; i<admins.size(); i++){
           
           if(userId.equals( admins.get( i ).getUserId())){
               isAdmin = true;              
           }           
       }
       
       return isAdmin;   
   }
   
   public List<PartnerManInfoItem> getPartnerId( String partner ) {
       
       return partnerDao.getPartnerId( partner );
   }
   
    public Partner readPortalDefault() {
		return partnerDao.getPortalDefault();
	}



    public boolean checkAccess( String userId ) {
       
      
        boolean isOK = false;
        
        Integer result = partnerDao.checkAccess(userId);
        
        if(result != 0 ){
            isOK = true;
        }
        
        return isOK;
    }
    
    
    public boolean checkRegInfo( PartnerClaimSellHistory qualityClaimSell ) {
        
        
        boolean isOK = false;
        
        Integer result = partnerDao.checkRegInfo(qualityClaimSell);
        
        if(result != 0 ){
            isOK = true;
        }
        
        return isOK;
    }
    
    
    public SearchResult<PartnerManInfoItem> listManInfoItemBySearchCondition(
    		PartnerManInfoItemSearchCondition searchCondition) {
		
		Integer count = this.partnerDao.countBySearchCondition(searchCondition);
		
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<PartnerManInfoItem> searchResult = null;
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<PartnerManInfoItem>(searchCondition);
		}else{
			List<PartnerManInfoItem> manInfoItemList = this.partnerDao.listManInfoItemBySearchCondition(searchCondition);
			
			
			searchResult = new SearchResult<PartnerManInfoItem>(manInfoItemList,searchCondition);
			
		}
		
		return searchResult;
	}
    
    public SearchResult<PartnerManInfoItem> listRegInfoItemBySearchCondition(
    		PartnerManInfoItemSearchCondition searchCondition) {
		
		Integer count = this.partnerDao.countRegBySearchCondition(searchCondition);
		
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<PartnerManInfoItem> searchResult = null;
		if(searchCondition.isEmptyRecord()){
			searchResult = new SearchResult<PartnerManInfoItem>(searchCondition);
		}else{
			List<PartnerManInfoItem> manInfoItemList = this.partnerDao.listRegInfoItemBySearchCondition(searchCondition);
			
			
			searchResult = new SearchResult<PartnerManInfoItem>(manInfoItemList,searchCondition);
			
		}
		
		return searchResult;
	}
    
    public List<PartnerManInfoItem> listItemBySearchCondition(PartnerManInfoItemSearchCondition searchCondition) {
		
			List<PartnerManInfoItem> manInfoItemList = this.partnerDao.listItemBySearchCondition(searchCondition);
			
		return manInfoItemList;
	}
	
	
	public PartnerManInfoItem readManInfoItem(int itemId){
		
		PartnerManInfoItem manInfoItem = this.partnerDao.readManInfoItem(itemId);
		return manInfoItem;
	}
	
    public List<PartnerManInfoItem> readManFamily(String itemId){
        
        List<PartnerManInfoItem> manFamily = this.partnerDao.listManFamily( itemId );
        return manFamily;
    }
    
    public List<PartnerManInfoItem> readManCareer(String itemId){
        
        List<PartnerManInfoItem> manCareer = this.partnerDao.listManCareer( itemId );
        return manCareer;
    }
    
    public void updateManInfo(PartnerManInfoItem manInfoItem , int familyCnt, int careerCnt, List<MultipartFile> fileList, User user){
        
        
        try{
            
            String fileId = "";
            
            if(fileList.size() > 0 && !fileList.get(0).isEmpty()) {
                List<FileData> uploadList = fileService.uploadFile(fileList, "", "", "0", user);
                
                fileId = uploadList.get(0).getFileId();
                fileService.createFileLink(fileId, "", IKepConstant.ITEM_TYPE_CODE_PORTAL, user);
            }
            
            String updaterName = partnerDao.getUserName( user.getUserId() );
            
            manInfoItem.setSajinSys( fileId );
            manInfoItem.setUpdaterId( user.getUserId() );
            manInfoItem.setUpdaterName( updaterName );
            
            this.partnerDao.updateManInfo( manInfoItem );
                 
            
            //가족정보 업데이트  (기존정보 삭제 후 다시 저장)
            this.partnerDao.deleteManInfoFamily( Integer.toString( manInfoItem.getSeqNum()) );
            
            saveManInfoFamily( manInfoItem, familyCnt, user );
    
            //경력정보 업데이트  (기존정보 삭제 후 다시 저장)
            this.partnerDao.deleteManInfoCareer( Integer.toString( manInfoItem.getSeqNum()) );  
   
            saveManInfoCareer( manInfoItem, careerCnt, user );
      
        }catch(Exception e){
            throw new IKEP4ApplicationException("DB UPDATE 실패", e);
        }
        
    }
    
public void updateManInfoMaster(PartnerManInfoItem manInfoItem , User user){
        
        
        try{
            
            
            String updaterName = partnerDao.getUserName( user.getUserId() );
            
            manInfoItem.setUpdaterId( user.getUserId() );
            manInfoItem.setUpdaterName( updaterName );
            
            this.partnerDao.updateManInfoMaster( manInfoItem );
                 
        }catch(Exception e){
            throw new IKEP4ApplicationException("DB UPDATE 실패", e);
        }
        
    }



    private void saveManInfoCareer( PartnerManInfoItem manInfoItem, int careerCnt, User user ) {
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
  
                PartnerManCareer manCareer = new PartnerManCareer();
                manCareer.setItemId( itemId );
                manCareer.setCareerNum(Integer.toString( i ));
                manCareer.setMainCareer( StringUtil.nvl(mainCareer[i],"") );
                manCareer.setPreCareer( StringUtil.nvl(preCareer[i],"") );
                manCareer.setMainBusiness( StringUtil.nvl(mainBusiness[i],"") );
                manCareer.setRegisterId( user.getUserId());
                manCareer.setUpdaterId( user.getUserId());
                manCareer.setJoinDate(manInfoItem.getJoinDate());
                manCareer.setLeaveDate(manInfoItem.getLeaveDate());
                manCareer.setMoveDate(manInfoItem.getMoveDate());
                
                if(manCareer.getMainCareer().equals( "" ) && manCareer.getPreCareer().equals( "" ) && manCareer.getMainBusiness().equals( "" )){
                 //TODO  어떻게 처리해야하나..
                }else{
                    this.partnerDao.createManInfoCareer( manCareer );
                }
                
            }
        }
    }



    private void saveManInfoFamily( PartnerManInfoItem manInfoItem, int familyCnt, User user ) {
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
  
                PartnerManFamily manFamily = new PartnerManFamily();
                manFamily.setItemId( Integer.toString(manInfoItem.getSeqNum()));
                manFamily.setFamilyNum(Integer.toString( i ));
                manFamily.setFamilyName( StringUtil.nvl(familyName[i],"") );
                manFamily.setFamilyRelation( StringUtil.nvl(familyRelation[i],"") );
                manFamily.setRegisterId( user.getUserId() );
                manFamily.setUpdaterId( user.getUserId() );
                
                if(manFamily.getFamilyRelation().equals( "" ) && manFamily.getFamilyName().equals( "" )){
                    //TODO  어떻게 처리해야하나..
                }else{
                    this.partnerDao.createManInfoFamily( manFamily );
                }
            }
        }
    }
    
    public int createManInfoMaster(PartnerManInfoItem manInfoItem, User user){

        int manId ; 
        
     try{
         
         
         String registerName = partnerDao.getUserName( manInfoItem.getRegisterId() );
         String updaterName = partnerDao.getUserName( manInfoItem.getUpdaterId() );
         
         manInfoItem.setRegisterName( registerName );
         manInfoItem.setUpdaterName( updaterName );
         
         manId = partnerDao.createManInfoMaster( manInfoItem ); 
         
     }catch(Exception e){
         throw new IKEP4ApplicationException("DB UPDATE 실패", e);
     }
     
     return manId;
       
   }
    
    
    public void createManInfoItem(PartnerManInfoItem manInfoItem, int familyCnt , int careerCnt , List<MultipartFile> fileList, User user){

      try{
          
          String fileId = "";
          
          //파일 첨부
          if(fileList.size() > 0 && !fileList.get(0).isEmpty()) {
              List<FileData> uploadList = fileService.uploadFile(fileList, "", "", "0", user);
              
              fileId = uploadList.get(0).getFileId();
              fileService.createFileLink(fileId, "", "PN", user);
          }
          
          manInfoItem.setSajinSys( fileId );
          
          String registerName = partnerDao.getUserName( manInfoItem.getRegisterId() );
          String updaterName = partnerDao.getUserName( manInfoItem.getUpdaterId() );
          
          manInfoItem.setRegisterName( registerName );
          manInfoItem.setUpdaterName( updaterName );
          
          partnerDao.createManInfoItem( manInfoItem ); 
          
          int seqNum = manInfoItem.getSeqNum();
          
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
    
                      PartnerManFamily manFamily = new PartnerManFamily();
                      manFamily.setItemId( Integer.toString( seqNum) );
                      manFamily.setFamilyNum(Integer.toString( i ));
                      manFamily.setFamilyName( StringUtil.nvl(familyName[i],"") );
                      manFamily.setFamilyRelation( StringUtil.nvl(familyRelation[i],"") );
                      manFamily.setRegisterId( manInfoItem.getRegisterId() );
                      manFamily.setUpdaterId( manInfoItem.getUpdaterId() );
                    
                 
                      
                      if(manFamily.getFamilyRelation().equals( "" ) && manFamily.getFamilyName().equals( "" )){
                         //여기엔??
                      }else{
                          this.partnerDao.createManInfoFamily( manFamily );
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

                  PartnerManCareer manCareer = new PartnerManCareer();
                  manCareer.setItemId( Integer.toString( seqNum) );
                  manCareer.setCareerNum( Integer.toString( i ) );
                  manCareer.setMainCareer( StringUtil.nvl(mainCareer[i],"") );
                  manCareer.setPreCareer( StringUtil.nvl(preCareer[i],"") );
                  manCareer.setMainBusiness( StringUtil.nvl(mainBusiness[i],"") );
                  manCareer.setRegisterId( manInfoItem.getRegisterId() );
                  manCareer.setUpdaterId( manInfoItem.getUpdaterId() );
                  manCareer.setJoinDate(manInfoItem.getJoinDate());
                  manCareer.setLeaveDate(manInfoItem.getLeaveDate());
                  manCareer.setMoveDate(manInfoItem.getMoveDate());
                  
                  if(manCareer.getMainCareer().equals( "" ) && manCareer.getPreCareer().equals( "" ) && manCareer.getMainBusiness().equals( "" )){
                     
                  }else{
                      this.partnerDao.createManInfoCareer( manCareer );
                  }
                  
              }
          }
   
          
      }catch(Exception e){
          throw new IKEP4ApplicationException("DB UPDATE 실패", e);
      }
      
    }
    
    
  


    public void deleteManInfo( PartnerManInfoItem manInfoItem){
    	 partnerDao.deleteManInfoMaster( manInfoItem );
         partnerDao.deleteManInfo( manInfoItem );
    }
}
