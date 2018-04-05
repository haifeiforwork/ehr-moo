package com.lgcns.ikep4.support.customer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.dao.CustomerBasicInfoDao;
import com.lgcns.ikep4.support.customer.model.BasicHistory;
import com.lgcns.ikep4.support.customer.model.BasicInfo;
import com.lgcns.ikep4.support.customer.model.BasicInfoReader;
import com.lgcns.ikep4.support.customer.model.MainBusiness;
import com.lgcns.ikep4.support.customer.model.MainPerson;
import com.lgcns.ikep4.support.customer.model.PersonStatic;
import com.lgcns.ikep4.support.customer.model.RelationCompany;
import com.lgcns.ikep4.support.customer.search.BasicInfoReaderSearchCondition;
import com.lgcns.ikep4.support.customer.search.BasicInfoSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerBasicInfoService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.CustomerBasicInfo;
import com.lgcns.ikep4.util.model.CustomerBuyingInfo;



@Service
@Transactional
public class CustomerBasicInfoServiceImpl extends GenericServiceImpl<Object, String> implements CustomerBasicInfoService {

    @Autowired
    private CustomerBasicInfoDao customerBasicInfoDao;
    
    @Autowired
    private IdgenService idgenService;
    
    public SearchResult<BasicInfo> listBasicInfoBySearchCondition( BasicInfoSearchCondition searchCondition ) {
        Integer count = this.customerBasicInfoDao.countBySearchCondition( searchCondition );
        
        searchCondition.terminateSearchCondition( count );
        
        SearchResult<BasicInfo> searchResult = null;
        
        if(searchCondition.isEmptyRecord()){
            searchResult = new SearchResult<BasicInfo>( searchCondition );
        }else{
            List<BasicInfo> basicInfoList = this.customerBasicInfoDao.listBasicInfoBySearchCondition( searchCondition );
            
            searchResult = new SearchResult<BasicInfo>( basicInfoList , searchCondition );
        }
        return searchResult;
    }
    
    public SearchResult<BasicInfoReader> listBasicInfoReaderBySearchCondition( BasicInfoReaderSearchCondition searchCondition ) {
        
        
        Integer count = this.customerBasicInfoDao.countReaderBySearchCondition(searchCondition);

        searchCondition.terminateSearchCondition(count);

		SearchResult<BasicInfoReader> searchResult = null;
		if(searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<BasicInfoReader>(searchCondition);

		} else {

			List<BasicInfoReader> basicInfoReaderList = this.customerBasicInfoDao.listReaderBySearchCondition(searchCondition);
			searchResult = new SearchResult<BasicInfoReader>(basicInfoReaderList, searchCondition);
		}

		return searchResult;
    }

    public BasicInfo readBasicInfo( String id ) {
        BasicInfo basicInfo = this.customerBasicInfoDao.readBasicInfo(id);
        
        
            
        return basicInfo;
    }

    public List readBasicHistory(String id){
        List historyInfo = this.customerBasicInfoDao.readHistory(id);
        return historyInfo;
    }

    public List readMainPerson( String id ) {
        List mainPerson = this.customerBasicInfoDao.readMainPerson( id );
        return mainPerson;
    }

    public PersonStatic readPersonStatic( String id ) {
        PersonStatic personStatic = new PersonStatic();
        personStatic = this.customerBasicInfoDao.readPersonStatic( id );
        if(personStatic == null){
            personStatic =  new PersonStatic();
            personStatic.setId( id );
            return personStatic;
        }else{
            return personStatic;
        }
    }

    public List readMainBusiness( String id ) {
        List mainBusiness = this.customerBasicInfoDao.readMainBusiness( id );
        return mainBusiness;
    }

    public List readRelationCompany( String id ) {
        List relationCompany = this.customerBasicInfoDao.readRelationCompany( id );
        return relationCompany;
    }

    public String saveBasicInfo( BasicInfo basicInfo ) {
                
        if(basicInfo.getId() == null || basicInfo.getId() == ""){
                      
            basicInfo.setId( idgenService.getNextId() );            
            customerBasicInfoDao.createBasicInfo( basicInfo ); 
        }else{
         customerBasicInfoDao.saveBasicInfo(basicInfo);
        }
        
        return basicInfo.getId();
      
    }

    public void saveBasicHistory( BasicHistory basicHistory, String historyCnt ) {
        
        try{
            customerBasicInfoDao.deleteBasicHistory(basicHistory.getId());
            
            int iCnt = Integer.parseInt( StringUtil.nvl( historyCnt, "0" )); 
            
            String yearDate[] = basicHistory.getYearDate().split( "," );
            String yearContent[] = basicHistory.getYearContent().split( "," );
            
            ArrayList<String> arYearDate = makeArrayList( yearDate, iCnt );
            ArrayList<String> arYearContent = makeArrayList( yearContent, iCnt );
            
            
            
            for(int i=0 ; i<iCnt ; i++){
                try {
                    
                    basicHistory.setYearDate( StringUtil.nvl( arYearDate.get(i), "") );
                    basicHistory.setYearContent( StringUtil.nvl( arYearContent.get(i), "") );
                    
                } catch ( Exception e ) {
                    throw new IKEP4ApplicationException( "code", e );
                }
                if(!(basicHistory.getYearDate() == "" && basicHistory.getYearContent() =="")){
                    customerBasicInfoDao.saveBasicHistory(basicHistory);
                }
            }
            
        }catch(Exception e){
            throw new IKEP4ApplicationException("DB 업데이트 실패",e);
        }
       
        
    }

    /**
     * 동적 행 추가시 마지막 값이 비었을 경우 ''로 셋팅 
     * @param tempData
     * @param iCnt
     * @return
     */
    private ArrayList<String> makeArrayList( String[] tempData, int iCnt ) {
        ArrayList<String> arrList = new ArrayList<String>();
        
        for(int i=0; i<tempData.length; i++){
            arrList.add( tempData[i] );
        }
        
        if(arrList.isEmpty()){
            arrList.add( null );
        }
        
        for(int i =0; i<iCnt ; i++){
         
            if(arrList.size() != iCnt){
                arrList.add("");
            }
        }
        
        return arrList;
    }

    public void saveBasicEquipment( BasicInfo basicInfo ) {
        customerBasicInfoDao.saveBasicEquipment(basicInfo);
   
    }

    public void saveMainPerson1( MainPerson mainPerson, String personCnt ) {
       try{
           customerBasicInfoDao.deleteMainPerson1(mainPerson.getId());
          
           int iCnt = Integer.parseInt( StringUtil.nvl( personCnt, "0" )); 
           
           String name[] = mainPerson.getName().split( "," );
           String jikwe[]  = mainPerson.getJikwe().split( "," );
           String tel[] = mainPerson.getTel().split( "," );
           String eMail[] = mainPerson.geteMail().split( "," );
           
           ArrayList<String> arName = makeArrayList( name, iCnt );
           ArrayList<String> arJikwe = makeArrayList( jikwe, iCnt );
           ArrayList<String> arTel = makeArrayList( tel, iCnt );
           ArrayList<String> arEMail = makeArrayList( eMail, iCnt );
           
           
           for(int i=0 ; i<iCnt ; i++){
               try {
                   
                   mainPerson.setName( StringUtil.nvl( arName.get( i ), "" ) );
                   mainPerson.setJikwe(  StringUtil.nvl( arJikwe.get( i ), "" ) );
                   mainPerson.setTel( StringUtil.nvl( arTel.get( i ), "" ) );
                   mainPerson.seteMail( StringUtil.nvl( arEMail.get( i ), "" ) );
               } catch ( Exception e ) {
                   throw new IKEP4ApplicationException( "code", e );
               }
               
               if(!(mainPerson.getName() == "" && mainPerson.getJikwe() == "" &&mainPerson.getTel() == "" && mainPerson.geteMail() == "" )){
                   customerBasicInfoDao.saveMainPerson1(mainPerson);
               }
               
              
           }
           
           
       }catch(Exception e){
           throw new IKEP4ApplicationException( "DB업데이트 실패", e );
       }
    }

    public void saveMainPerson2( MainPerson mainPerson, String personCnt ) {
       try{
           
           customerBasicInfoDao.deleteMainPerson2(mainPerson.getId());
           
           int iCnt = Integer.parseInt( StringUtil.nvl( personCnt, "0" ) );
           
           String keymanFlag[] = mainPerson.getKeymanFlag().split( "," );
           String jikwe[] = mainPerson.getJikwe().split( "," );
           String name[] = mainPerson.getName().split( "," );
           String eMail[] = mainPerson.geteMail().split( "," );
           
           
           ArrayList<String> arKeymanFlag = makeArrayList( keymanFlag, iCnt );
           ArrayList<String> arJikwe = makeArrayList( jikwe, iCnt );
           ArrayList<String> arName = makeArrayList( name, iCnt );
           ArrayList<String> arEMail = makeArrayList( eMail, iCnt );
           
           for(int i=0; i<iCnt;i++){
               try{
                   mainPerson.setKeymanFlag( StringUtil.nvl( arKeymanFlag.get( i ), "" ) );
                   mainPerson.setJikwe(  StringUtil.nvl( arJikwe.get( i ), "" ) );
                   mainPerson.setName( StringUtil.nvl( arName.get( i ), "" ) );
                   mainPerson.seteMail( StringUtil.nvl( arEMail.get( i ), "" ) );  
               }catch(Exception e){
                   throw new IKEP4ApplicationException( "code", e );
               }
               if(!(mainPerson.getKeymanFlag() == "" && mainPerson.getJikwe() == "" && mainPerson.getName() == "" && mainPerson.geteMail() == "" )){
                   customerBasicInfoDao.saveMainPerson2(mainPerson);
               }
               
           }
           
       }catch(Exception e){
           throw new IKEP4ApplicationException( "DB업데이트 실패", e );
       }
        
    }

    public void savePersonStatic( PersonStatic personStatic, String id ) {
        
       personStatic.setTotalEmployee1( personStatic.getManEmployee1() + personStatic.getWomanEmployee1() );
       personStatic.setTotalEmployee2( personStatic.getManEmployee2() + personStatic.getWomanEmployee2() );
       personStatic.setTotalEmployee3( personStatic.getManEmployee3() + personStatic.getWomanEmployee3() );
       personStatic.setTotalEmployee4( personStatic.getManEmployee4() + personStatic.getWomanEmployee4() );
       
       int existStatic = customerBasicInfoDao.selectPersonStaticCnt( id );
       
       if(existStatic > 0){
           personStatic.setId( id );
           customerBasicInfoDao.savePersonStatic(personStatic);
       }else{
           customerBasicInfoDao.createPersonStatic(personStatic);
       }
       
        
    }

    public void saveRelationCompany( RelationCompany relationCompany ,String companyCnt) {
        try{

            
            customerBasicInfoDao.deleteRelationCompany(relationCompany.getId());
            
            int iCnt = Integer.parseInt( StringUtil.nvl( companyCnt, "0" ) );
            
            
            String relationName[] = relationCompany.getRelationName().split( "," );
            String type[] = relationCompany.getType().split( "," );
            String note[] = relationCompany.getNote().split( "," );
            

            ArrayList<String> arRelationName = makeArrayList( relationName, iCnt );
            ArrayList<String> arType = makeArrayList( type, iCnt );
            ArrayList<String> arNote = makeArrayList( note, iCnt );
            
            for(int i=0; i<iCnt;i++){
                try{                 
                    relationCompany.setRelationName( StringUtil.nvl( arRelationName.get( i ), "" ) );
                    relationCompany.setType(  StringUtil.nvl( arType.get( i ), "" ) );
                    relationCompany.setNote( StringUtil.nvl( arNote.get( i ), "" ) );
                }catch(Exception e){
                    throw new IKEP4ApplicationException( "code", e );
                }
                if(!(relationCompany.getRelationName() == "" && relationCompany.getType() == "" && relationCompany.getNote() == "")){
                    customerBasicInfoDao.saveRelationCompany(relationCompany);
                }
                
            }
            
        }catch(Exception e){
            throw new IKEP4ApplicationException( "DB업데이트 실패", e );
        }
        
    }

    public void saveMainBusiness( MainBusiness mainBusiness, String buyingCnt, String sellingCnt ) {
       
       try{
            customerBasicInfoDao.deleteMainBusiness(mainBusiness.getId());
            
            
            int biCnt = Integer.parseInt( StringUtil.nvl( buyingCnt, "0" ) );   
            int siCnt = Integer.parseInt( StringUtil.nvl( sellingCnt, "0" ) );   
            
            String mainCustomer_b[] = mainBusiness.getMainCustomer_b().split( "," );
            String monthAvg_b[] = mainBusiness.getMonthAvg_b().split( "," );
            String chargeEmployee_b[] = mainBusiness.getChargeEmployee_b().split( "," );
            String mainProduct_b[] = mainBusiness.getMainProduct_b().split( "," );
            String sellingBuyingRate_b[] = mainBusiness.getSellingBuyingRate_b().split( "," );

            String mainCustomer_s[] = mainBusiness.getMainCustomer_s().split( "," );
            String monthAvg_s[] = mainBusiness.getMonthAvg_s().split( "," );
            String chargeEmployee_s[] = mainBusiness.getChargeEmployee_s().split( "," );
            String mainProduct_s[] = mainBusiness.getMainProduct_s().split( "," );
            String sellingBuyingRate_s[] = mainBusiness.getSellingBuyingRate_s().split( "," );

            ArrayList<String> arMainCustomer_b = makeArrayList( mainCustomer_b, biCnt );
            ArrayList<String> arMonthAvg_b = makeArrayList( monthAvg_b, biCnt );
            ArrayList<String> arChargeEmployee_b = makeArrayList( chargeEmployee_b, biCnt );
            ArrayList<String> arMainProduct_b = makeArrayList( mainProduct_b, biCnt );
            ArrayList<String> arSellingBuyingRate_b = makeArrayList( sellingBuyingRate_b, biCnt );
          
            for(int i=0; i<biCnt;i++){
                try{                 
                    mainBusiness.setMainCustomer_b( StringUtil.nvl( arMainCustomer_b.get( i ), "" ) );
                    mainBusiness.setMonthAvg_b( StringUtil.nvl( arMonthAvg_b.get( i ), "" ) );
                    mainBusiness.setChargeEmployee_b( StringUtil.nvl( arChargeEmployee_b.get( i ), "" ) );
                    mainBusiness.setMainProduct_b( StringUtil.nvl( arMainProduct_b.get( i ), "" ) );
                    mainBusiness.setSellingBuyingRate_b( StringUtil.nvl( arSellingBuyingRate_b.get( i ), "" ) );

                }catch(Exception e){
                    throw new IKEP4ApplicationException( "code", e );
                }
                if(!(mainBusiness.getMainCustomer_b() == "" && mainBusiness.getMonthAvg_b() == "" && mainBusiness.getChargeEmployee_b() == "" 
                    && mainBusiness.getMainProduct_b() == "" && mainBusiness.getSellingBuyingRate_b() == "" )){
                customerBasicInfoDao.saveMainBusinessBuying( mainBusiness);
                }
                
            }
            
            ArrayList<String> arMainCustomer_s = makeArrayList( mainCustomer_s, siCnt );
            ArrayList<String> arMonthAvg_s = makeArrayList( monthAvg_s, siCnt );
            ArrayList<String> arChargeEmployee_s = makeArrayList( chargeEmployee_s, siCnt );
            ArrayList<String> arMainProduct_s = makeArrayList( mainProduct_s, siCnt );
            ArrayList<String> arSellingBuyingRate_s = makeArrayList( sellingBuyingRate_s, siCnt );
            
            
            for(int i=0; i<siCnt;i++){
                try{                 
                    mainBusiness.setMainCustomer_s( StringUtil.nvl( arMainCustomer_s.get( i ), "" ) );
                    mainBusiness.setMonthAvg_s( StringUtil.nvl( arMonthAvg_s.get( i ), "" ) );
                    mainBusiness.setChargeEmployee_s( StringUtil.nvl( arChargeEmployee_s.get( i ), "" ) );
                    mainBusiness.setMainProduct_s( StringUtil.nvl( arMainProduct_s.get( i ), "" ) );
                    mainBusiness.setSellingBuyingRate_s( StringUtil.nvl( arSellingBuyingRate_s.get( i ), "" ) );

                }catch(Exception e){
                    throw new IKEP4ApplicationException( "code", e );
                }
                
                if(!(mainBusiness.getMainCustomer_s() == "" && mainBusiness.getMonthAvg_s() == "" && mainBusiness.getChargeEmployee_s() == "" 
                    && mainBusiness.getMainProduct_s() == "" && mainBusiness.getSellingBuyingRate_s() == "" )){
                customerBasicInfoDao.saveMainBusinessSelling( mainBusiness);
                }
                
            }
            
        }catch(Exception e){
            throw new IKEP4ApplicationException( "DB업데이트 실패", e );
        }
         
        
    }

    public void deleteBasicInfo( BasicInfo basicInfo ) {
        customerBasicInfoDao.deleteBasicInfo(basicInfo);
        
    }
    
    public void updateHitCount( BasicInfo basicInfo ) {
        if(customerBasicInfoDao.existsReference(basicInfo)) {
			customerBasicInfoDao.updateReference(basicInfo);
		} else {
			customerBasicInfoDao.updateHitCount(basicInfo);
			customerBasicInfoDao.createReference(basicInfo);
		}
    }

    public List getBuyingInfo( String sapId ) {
        List BuyingInfoList = customerBasicInfoDao.getBuyingInfo(sapId);
        return BuyingInfoList; 
    }

    public void updateSapCustomer( List<CustomerBasicInfo> customerBasicInfoList ) {
       for(CustomerBasicInfo customerBasicInfo : customerBasicInfoList ){
           customerBasicInfoDao.updateSapCustomer(customerBasicInfo);
       }
        
    }

    public void updateSapCustomerBuyingInfo( List<CustomerBuyingInfo> customerBuyingInfoList ) {
        for(CustomerBuyingInfo customerBuyingInfo : customerBuyingInfoList ){
            customerBasicInfoDao.updateSapCustomerBuyingInfo(customerBuyingInfo);
        }
        
    }
    
   public void basicInfoSAPSync( List<CustomerBasicInfo> customerBasicInfoList ) {
       for(CustomerBasicInfo customerBasicInfo : customerBasicInfoList ){
           String sapId = customerBasicInfo.getSapId();
           customerBasicInfoDao.updateBasicInfoSAPSync(sapId);
           customerBasicInfoDao.updateManInfoSAPSync( sapId );
           customerBasicInfoDao.updateCounselHistorySAPSync( sapId );
           customerBasicInfoDao.updateMainSellingSAPSync( sapId );
       }
        
    }

    public void deleteSapCustomer() {
       customerBasicInfoDao.deleteSapCustomer();
        
    }

    public void deleteSapCustomerBuyingInfo() {
        customerBasicInfoDao.deleteSapCustomerBuyingInfo();
        
    }

    public SearchResult<BasicInfo> listSAPBasicInfobySearchCondition( BasicInfoSearchCondition searchCondition ) {
        Integer count = this.customerBasicInfoDao.countBySearchConditionForSAP( searchCondition );
        
        searchCondition.terminateSearchCondition( count );
        
        SearchResult<BasicInfo> searchResult = null;
        
        if(searchCondition.isEmptyRecord()){
            searchResult = new SearchResult<BasicInfo>( searchCondition );
        }else{
            List<BasicInfo> basicInfoList = this.customerBasicInfoDao.listSAPBasicInfobySearchCondition(searchCondition);
            
            searchResult = new SearchResult<BasicInfo>( basicInfoList , searchCondition );
        }
        return searchResult;
       
    }

    public BasicInfo readBasicInfoForSap( String sapId ) {
        
      return customerBasicInfoDao.readBasicInfoForSap(sapId);
      
    }

 



    

    
}
