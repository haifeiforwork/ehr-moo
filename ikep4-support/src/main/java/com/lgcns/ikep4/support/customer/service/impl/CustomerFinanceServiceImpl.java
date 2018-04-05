package com.lgcns.ikep4.support.customer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.dao.CustomerFinanceDao;
import com.lgcns.ikep4.support.customer.model.FinanceCondition;
import com.lgcns.ikep4.support.customer.model.Finance;
import com.lgcns.ikep4.support.customer.model.BuyingInfo;
import com.lgcns.ikep4.support.customer.model.MainBusiness;
import com.lgcns.ikep4.support.customer.model.MainPerson;
import com.lgcns.ikep4.support.customer.model.PersonStatic;
import com.lgcns.ikep4.support.customer.model.RelationCompany;
import com.lgcns.ikep4.support.customer.search.FinanceSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerFinanceService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.CustomerBasicInfo;
import com.lgcns.ikep4.util.model.CustomerBuyingInfo;



@Service
@Transactional
public class CustomerFinanceServiceImpl extends GenericServiceImpl<Object, String> implements CustomerFinanceService {

    @Autowired
    private CustomerFinanceDao customerFinanceDao;
    
    @Autowired
    private IdgenService idgenService;
    
    public SearchResult<Finance> listFinanceBySearchCondition( FinanceSearchCondition searchCondition ) {
        Integer count = this.customerFinanceDao.countBySearchCondition( searchCondition );
        
        searchCondition.terminateSearchCondition( count );
        
        SearchResult<Finance> searchResult = null;
        
        if(searchCondition.isEmptyRecord()){
            searchResult = new SearchResult<Finance>( searchCondition );
        }else{
            List<Finance> financeList = this.customerFinanceDao.listFinanceBySearchCondition( searchCondition );
            
            searchResult = new SearchResult<Finance>( financeList , searchCondition );
        }
        return searchResult;
    }

    public Finance readFinance( String id ) {
        Finance finance = this.customerFinanceDao.readFinance(id);
        
        
            
        return finance;
    }

    public List readFinanceCondition(String id){
        List historyInfo = this.customerFinanceDao.readHistory(id);
        return historyInfo;
    }
    
    public List profitLossList(String id){
        List profitLossList = this.customerFinanceDao.profitLossList(id);
        return profitLossList;
    }
    
    public List conditionList(String id){
        List conditionList = this.customerFinanceDao.conditionList(id);
        return conditionList;
    }
    
    public List rateList(String id){
        List rateList = this.customerFinanceDao.rateList(id);
        return rateList;
    }

    public List readMainPerson( String id ) {
        List mainPerson = this.customerFinanceDao.readMainPerson( id );
        return mainPerson;
    }

    public PersonStatic readPersonStatic( String id ) {
        PersonStatic personStatic = new PersonStatic();
        personStatic = this.customerFinanceDao.readPersonStatic( id );
        if(personStatic == null){
            personStatic =  new PersonStatic();
            personStatic.setId( id );
            return personStatic;
        }else{
            return personStatic;
        }
    }

    public List readMainBusiness( String id ) {
        List mainBusiness = this.customerFinanceDao.readMainBusiness( id );
        return mainBusiness;
    }

    public List readRelationCompany( String id ) {
        List relationCompany = this.customerFinanceDao.readRelationCompany( id );
        return relationCompany;
    }

    public String saveFinance( Finance finance ) {
    	
    	String [] yearGroup1 = StringUtils.split(finance.getYearValue(), "_");
    	String [] mntGroup1 = null;
    	String [] mntGroup2 = new String[3];
    	String [] mntGroup3 = new String[17];
    	String tmpYear = "";
    	Integer count = 0;
    	for(int i=0; i<yearGroup1.length; i++){
    		mntGroup1 = StringUtils.split(yearGroup1[i], "^");
    		for(int j=0; j<mntGroup1.length; j++){
    			mntGroup2 = StringUtils.split(mntGroup1[j], "/");
    			if(mntGroup2.length < 3){
    				mntGroup3[j] = "0";
    			}else{
    				mntGroup3[j] = mntGroup2[2];
    			}
    			tmpYear = mntGroup2[0];
    		}
    		finance.setYearInfo(tmpYear);
    		finance.setMnt1(mntGroup3[0]);
    		finance.setMnt2(mntGroup3[1]);
    		finance.setMnt3(mntGroup3[2]);
    		finance.setMnt4(mntGroup3[3]);
    		finance.setMnt5(mntGroup3[4]);
    		finance.setMnt6(mntGroup3[5]);
    		finance.setMnt7(mntGroup3[6]);
    		finance.setMnt8(mntGroup3[7]);
    		finance.setMnt9(mntGroup3[8]);
    		finance.setMnt10(mntGroup3[9]);
    		finance.setMnt11(mntGroup3[10]);
    		finance.setMnt12(mntGroup3[11]);
    		finance.setMnt13(mntGroup3[12]);
    		finance.setMnt14(mntGroup3[13]);
    		finance.setMnt15(mntGroup3[14]);
    		finance.setMnt16(mntGroup3[15]);
    		finance.setMnt17(mntGroup3[16]);
    		finance.setId( idgenService.getNextId() );      
    		count = customerFinanceDao.countFinance(finance);
    		if(count > 0){
    			customerFinanceDao.saveFinance(finance);
    		}else{
    			customerFinanceDao.createFinance( finance ); 	
    		}
    		count = 0;
    	}
    	String [] mntGroup = StringUtils.split(finance.getMntValue(), "^");
    	String [] mntGroup5 = new String[3];
    	String [] mntGroup4 = new String[17];
    	for(int ii=0; ii<mntGroup.length; ii++){
    		mntGroup5 = StringUtils.split(mntGroup[ii], "/");
    		if(mntGroup5.length < 2){
				mntGroup4[ii] = "0";
			}else{
				mntGroup4[ii] = mntGroup5[1];
			}
    	}
    	finance.setDivInfo("mount");
    	finance.setMnt1(mntGroup4[0]);
    	finance.setMnt2(mntGroup4[1]);
    	finance.setMnt3(mntGroup4[2]);
    	finance.setMnt4(mntGroup4[3]);
    	finance.setMnt5(mntGroup4[4]);
    	finance.setMnt6(mntGroup4[5]);
    	finance.setMnt7(mntGroup4[6]);
    	finance.setMnt8(mntGroup4[7]);
    	finance.setMnt9(mntGroup4[8]);
    	finance.setMnt10(mntGroup4[9]);
    	finance.setMnt11(mntGroup4[10]);
    	finance.setMnt12(mntGroup4[11]);
    	finance.setMnt13(mntGroup4[12]);
    	finance.setMnt14(mntGroup4[13]);
    	finance.setMnt15(mntGroup4[14]);
    	finance.setMnt16(mntGroup4[15]);
    	finance.setMnt17(mntGroup4[16]);
		finance.setId( idgenService.getNextId() );       
		count = customerFinanceDao.countFinance(finance);
		if(count > 0){
			customerFinanceDao.saveFinance(finance);
		}else{
			customerFinanceDao.createFinance( finance ); 	
		}
		count = 0;
    	
    	String [] perGroup = StringUtils.split(finance.getPerValue(), "^");
    	String [] perGroup2 = new String[3];
    	String [] perGroup1 = new String[17];
    	for(int ij=0; ij<perGroup.length; ij++){
    		perGroup2 = StringUtils.split(perGroup[ij], "/");
    		if(perGroup2.length < 2){
    			perGroup1[ij] = "0";
			}else{
				perGroup1[ij] = perGroup2[1];
			}
    	}
    	finance.setDivInfo("rate");
    	finance.setMnt1(perGroup1[0]);
    	finance.setMnt2(perGroup1[1]);
    	finance.setMnt3(perGroup1[2]);
    	finance.setMnt4(perGroup1[3]);
    	finance.setMnt5(perGroup1[4]);
    	finance.setMnt6(perGroup1[5]);
    	finance.setMnt7(perGroup1[6]);
    	finance.setMnt8(perGroup1[7]);
    	finance.setMnt9(perGroup1[8]);
    	finance.setMnt10(perGroup1[9]);
    	finance.setMnt11(perGroup1[10]);
    	finance.setMnt12(perGroup1[11]);
    	finance.setMnt13(perGroup1[12]);
    	finance.setMnt14(perGroup1[13]);
    	finance.setMnt15(perGroup1[14]);
    	finance.setMnt16(perGroup1[15]);
    	finance.setMnt17(perGroup1[16]);
		finance.setId( idgenService.getNextId() );            
		count = customerFinanceDao.countFinance(finance);
		if(count > 0){
			customerFinanceDao.saveFinance(finance);
		}else{
			customerFinanceDao.createFinance( finance ); 	
		}
		count = 0;
    	
                
        /*if(finance.getId() == null || finance.getId() == ""){
                      
            finance.setId( idgenService.getNextId() );            
            customerFinanceDao.createFinance( finance ); 
        }else{
         customerFinanceDao.saveFinance(finance);
        }*/
        
        return finance.getId();
      
    }

    public void saveFinanceCondition( FinanceCondition financeCondition, String historyCnt ) {
        
        try{
        	String [] yearGroup1 = StringUtils.split(financeCondition.getYearValue(), "_");
        	String [] mntGroup1 = null;
        	String [] mntGroup2 = new String[3];
        	String [] mntGroup3 = new String[29];
        	String tmpYear = "";
        	Integer count = 0;
        	for(int i=0; i<yearGroup1.length; i++){
        		mntGroup1 = StringUtils.split(yearGroup1[i], "^");
        		for(int j=0; j<mntGroup1.length; j++){
        			mntGroup2 = StringUtils.split(mntGroup1[j], "/");
        			if(mntGroup2.length < 3){
        				mntGroup3[j] = "0";
        			}else{
        				mntGroup3[j] = mntGroup2[2];
        			}
        			tmpYear = mntGroup2[0];
        		}
        		financeCondition.setYearInfo(tmpYear);
        		financeCondition.setMnt1(mntGroup3[0]);
        		financeCondition.setMnt2(mntGroup3[1]);
        		financeCondition.setMnt3(mntGroup3[2]);
        		financeCondition.setMnt4(mntGroup3[3]);
        		financeCondition.setMnt5(mntGroup3[4]);
        		financeCondition.setMnt6(mntGroup3[5]);
        		financeCondition.setMnt7(mntGroup3[6]);
        		financeCondition.setMnt8(mntGroup3[7]);
        		financeCondition.setMnt9(mntGroup3[8]);
        		financeCondition.setMnt10(mntGroup3[9]);
        		financeCondition.setMnt11(mntGroup3[10]);
        		financeCondition.setMnt12(mntGroup3[11]);
        		financeCondition.setMnt13(mntGroup3[12]);
        		financeCondition.setMnt14(mntGroup3[13]);
        		financeCondition.setMnt15(mntGroup3[14]);
        		financeCondition.setMnt16(mntGroup3[15]);
        		financeCondition.setMnt17(mntGroup3[16]);
        		financeCondition.setMnt18(mntGroup3[17]);
        		financeCondition.setMnt19(mntGroup3[18]);
        		financeCondition.setMnt20(mntGroup3[19]);
        		financeCondition.setMnt21(mntGroup3[20]);
        		financeCondition.setMnt22(mntGroup3[21]);
        		financeCondition.setMnt23(mntGroup3[22]);
        		financeCondition.setMnt24(mntGroup3[23]);
        		financeCondition.setMnt25(mntGroup3[24]);
        		financeCondition.setMnt26(mntGroup3[25]);
        		financeCondition.setMnt27(mntGroup3[26]);
        		financeCondition.setMnt28(mntGroup3[27]);
        		financeCondition.setMnt29(mntGroup3[28]);
        		count = customerFinanceDao.countFinanceCondition(financeCondition);
        		if(count > 0){
        			customerFinanceDao.updateFinanceCondition(financeCondition);
        		}else{
        			customerFinanceDao.saveFinanceCondition(financeCondition);
        		}
        		count = 0;
        		
        	}
        	String [] mntGroup = StringUtils.split(financeCondition.getMntValue(), "^");
        	String [] mntGroup5 = new String[3];
        	String [] mntGroup4 = new String[29];
        	for(int ii=0; ii<mntGroup.length; ii++){
        		mntGroup5 = StringUtils.split(mntGroup[ii], "/");
        		if(mntGroup5.length < 2){
    				mntGroup4[ii] = "0";
    			}else{
    				mntGroup4[ii] = mntGroup5[1];
    			}
        	}
        	financeCondition.setDivInfo("mount");
        	financeCondition.setMnt1(mntGroup4[0]);
        	financeCondition.setMnt2(mntGroup4[1]);
        	financeCondition.setMnt3(mntGroup4[2]);
        	financeCondition.setMnt4(mntGroup4[3]);
        	financeCondition.setMnt5(mntGroup4[4]);
        	financeCondition.setMnt6(mntGroup4[5]);
        	financeCondition.setMnt7(mntGroup4[6]);
        	financeCondition.setMnt8(mntGroup4[7]);
        	financeCondition.setMnt9(mntGroup4[8]);
        	financeCondition.setMnt10(mntGroup4[9]);
        	financeCondition.setMnt11(mntGroup4[10]);
        	financeCondition.setMnt12(mntGroup4[11]);
        	financeCondition.setMnt13(mntGroup4[12]);
        	financeCondition.setMnt14(mntGroup4[13]);
        	financeCondition.setMnt15(mntGroup4[14]);
        	financeCondition.setMnt16(mntGroup4[15]);
        	financeCondition.setMnt17(mntGroup4[16]);
        	financeCondition.setMnt18(mntGroup4[17]);
    		financeCondition.setMnt19(mntGroup4[18]);
    		financeCondition.setMnt20(mntGroup4[19]);
    		financeCondition.setMnt21(mntGroup4[20]);
    		financeCondition.setMnt22(mntGroup4[21]);
    		financeCondition.setMnt23(mntGroup4[22]);
    		financeCondition.setMnt24(mntGroup4[23]);
    		financeCondition.setMnt25(mntGroup4[24]);
    		financeCondition.setMnt26(mntGroup4[25]);
    		financeCondition.setMnt27(mntGroup4[26]);
    		financeCondition.setMnt28(mntGroup4[27]);
    		financeCondition.setMnt29(mntGroup4[28]);
    		count = customerFinanceDao.countFinanceCondition(financeCondition);
    		if(count > 0){
    			customerFinanceDao.updateFinanceCondition(financeCondition);
    		}else{
    			customerFinanceDao.saveFinanceCondition(financeCondition);
    		}
    		count = 0;
        	
        	String [] perGroup = StringUtils.split(financeCondition.getPerValue(), "^");
        	String [] perGroup2 = new String[3];
        	String [] perGroup1 = new String[29];
        	for(int ij=0; ij<perGroup.length; ij++){
        		perGroup2 = StringUtils.split(perGroup[ij], "/");
        		if(perGroup2.length < 2){
        			perGroup1[ij] = "0";
    			}else{
    				perGroup1[ij] = perGroup2[1];
    			}
        	}
        	financeCondition.setDivInfo("rate");
        	financeCondition.setMnt1(perGroup1[0]);
        	financeCondition.setMnt2(perGroup1[1]);
        	financeCondition.setMnt3(perGroup1[2]);
        	financeCondition.setMnt4(perGroup1[3]);
        	financeCondition.setMnt5(perGroup1[4]);
        	financeCondition.setMnt6(perGroup1[5]);
        	financeCondition.setMnt7(perGroup1[6]);
        	financeCondition.setMnt8(perGroup1[7]);
        	financeCondition.setMnt9(perGroup1[8]);
        	financeCondition.setMnt10(perGroup1[9]);
        	financeCondition.setMnt11(perGroup1[10]);
        	financeCondition.setMnt12(perGroup1[11]);
        	financeCondition.setMnt13(perGroup1[12]);
        	financeCondition.setMnt14(perGroup1[13]);
        	financeCondition.setMnt15(perGroup1[14]);
        	financeCondition.setMnt16(perGroup1[15]);
        	financeCondition.setMnt17(perGroup1[16]);
        	financeCondition.setMnt18(perGroup1[17]);
    		financeCondition.setMnt19(perGroup1[18]);
    		financeCondition.setMnt20(perGroup1[19]);
    		financeCondition.setMnt21(perGroup1[20]);
    		financeCondition.setMnt22(perGroup1[21]);
    		financeCondition.setMnt23(perGroup1[22]);
    		financeCondition.setMnt24(perGroup1[23]);
    		financeCondition.setMnt25(perGroup1[24]);
    		financeCondition.setMnt26(perGroup1[25]);
    		financeCondition.setMnt27(perGroup1[26]);
    		financeCondition.setMnt28(perGroup1[27]);
    		financeCondition.setMnt29(perGroup1[28]);
    		count = customerFinanceDao.countFinanceCondition(financeCondition);
    		if(count > 0){
    			customerFinanceDao.updateFinanceCondition(financeCondition);
    		}else{
    			customerFinanceDao.saveFinanceCondition(financeCondition);
    		}
    		count = 0;
        	/*customerFinanceDao.deleteFinanceCondition(financeCondition.getId());
            
            int iCnt = Integer.parseInt( StringUtil.nvl( historyCnt, "0" )); 
            
            String yearDate[] = financeCondition.getYearDate().split( "," );
            String yearContent[] = financeCondition.getYearContent().split( "," );
            
            ArrayList<String> arYearDate = makeArrayList( yearDate, iCnt );
            ArrayList<String> arYearContent = makeArrayList( yearContent, iCnt );
            
            
            
            for(int i=0 ; i<iCnt ; i++){
                try {
                    
                    financeCondition.setYearDate( StringUtil.nvl( arYearDate.get(i), "") );
                    financeCondition.setYearContent( StringUtil.nvl( arYearContent.get(i), "") );
                    
                } catch ( Exception e ) {
                    throw new IKEP4ApplicationException( "code", e );
                }
                if(!(financeCondition.getYearDate() == "" && financeCondition.getYearContent() =="")){
                    customerFinanceDao.saveFinanceCondition(financeCondition);
                }
            }*/
            
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

    public void saveFinanceRate( Finance finance ) {
    	String [] yearGroup1 = StringUtils.split(finance.getYearValue(), "_");
    	String [] mntGroup1 = null;
    	String [] mntGroup2 = new String[3];
    	String [] mntGroup3 = new String[12];
    	String tmpYear = "";
    	Integer count = 0;
    	for(int i=0; i<yearGroup1.length; i++){
    		mntGroup1 = StringUtils.split(yearGroup1[i], "-");
    		for(int j=0; j<mntGroup1.length; j++){
    			mntGroup2 = StringUtils.split(mntGroup1[j], "/");
    			if(mntGroup2.length < 3){
    				mntGroup3[j] = "0";
    			}else{
    				mntGroup3[j] = mntGroup2[2];
    			}
    			tmpYear = mntGroup2[0];
    		}
    		finance.setYearInfo(tmpYear);
    		finance.setMnt1(mntGroup3[0]);
    		finance.setMnt2(mntGroup3[1]);
    		finance.setMnt3(mntGroup3[2]);
    		finance.setMnt4(mntGroup3[3]);
    		finance.setMnt5(mntGroup3[4]);
    		finance.setMnt6(mntGroup3[5]);
    		finance.setMnt7(mntGroup3[6]);
    		finance.setMnt8(mntGroup3[7]);
    		finance.setMnt9(mntGroup3[8]);
    		finance.setMnt10(mntGroup3[9]);
    		finance.setMnt11(mntGroup3[10]);
    		finance.setMnt12(mntGroup3[11]);
    		count = customerFinanceDao.countFinanceRate(finance);
    		if(count > 0){
    			customerFinanceDao.updateFinanceRate(finance);
    		}else{
    			customerFinanceDao.saveFinanceRate(finance); 	
    		}
    		count = 0;
    		
    	}
    	
    	String [] perGroup = StringUtils.split(finance.getPerValue(), "-");
    	String [] perGroup2 = new String[3];
    	String [] perGroup1 = new String[12];
    	for(int ij=0; ij<perGroup.length; ij++){
    		perGroup2 = StringUtils.split(perGroup[ij], "/");
    		if(perGroup2.length < 2){
    			perGroup1[ij] = "0";
			}else{
				perGroup1[ij] = perGroup2[1];
			}
    	}
    	finance.setDivInfo("rate");
    	finance.setMnt1(perGroup1[0]);
    	finance.setMnt2(perGroup1[1]);
    	finance.setMnt3(perGroup1[2]);
    	finance.setMnt4(perGroup1[3]);
    	finance.setMnt5(perGroup1[4]);
    	finance.setMnt6(perGroup1[5]);
    	finance.setMnt7(perGroup1[6]);
    	finance.setMnt8(perGroup1[7]);
    	finance.setMnt9(perGroup1[8]);
    	finance.setMnt10(perGroup1[9]);
    	finance.setMnt11(perGroup1[10]);
    	finance.setMnt12(perGroup1[11]);
    	count = customerFinanceDao.countFinanceRate(finance);
		if(count > 0){
			customerFinanceDao.updateFinanceRate(finance);
		}else{
			customerFinanceDao.saveFinanceRate(finance); 	
		}
		count = 0;
        
   
    }

    public void saveMainPerson1( MainPerson mainPerson, String personCnt ) {
       try{
           customerFinanceDao.deleteMainPerson1(mainPerson.getId());
          
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
                   customerFinanceDao.saveMainPerson1(mainPerson);
               }
               
              
           }
           
           
       }catch(Exception e){
           throw new IKEP4ApplicationException( "DB업데이트 실패", e );
       }
    }

    public void saveMainPerson2( MainPerson mainPerson, String personCnt ) {
       try{
           
           customerFinanceDao.deleteMainPerson2(mainPerson.getId());
           
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
                   customerFinanceDao.saveMainPerson2(mainPerson);
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
       
       int existStatic = customerFinanceDao.selectPersonStaticCnt( id );
       
       if(existStatic > 0){
           personStatic.setId( id );
           customerFinanceDao.savePersonStatic(personStatic);
       }else{
           customerFinanceDao.createPersonStatic(personStatic);
       }
       
        
    }

    public void saveRelationCompany( RelationCompany relationCompany ,String companyCnt) {
        try{

            
            customerFinanceDao.deleteRelationCompany(relationCompany.getId());
            
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
                    customerFinanceDao.saveRelationCompany(relationCompany);
                }
                
            }
            
        }catch(Exception e){
            throw new IKEP4ApplicationException( "DB업데이트 실패", e );
        }
        
    }

    public void saveMainBusiness( MainBusiness mainBusiness, String buyingCnt, String sellingCnt ) {
       
       try{
            customerFinanceDao.deleteMainBusiness(mainBusiness.getId());
            
            
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
                customerFinanceDao.saveMainBusinessBuying( mainBusiness);
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
                customerFinanceDao.saveMainBusinessSelling( mainBusiness);
                }
                
            }
            
        }catch(Exception e){
            throw new IKEP4ApplicationException( "DB업데이트 실패", e );
        }
         
        
    }

    public void deleteFinance( String id) {
        customerFinanceDao.deleteFinance(id);
        
    }
    
    public void deleteFinanceCondition( String id) {
        customerFinanceDao.deleteFinanceCondition(id);
        
    }
    
    public void deleteFinanceRate( String id) {
        customerFinanceDao.deleteFinanceRate(id);
        
    }

    public List getBuyingInfo( String sapId ) {
        List BuyingInfoList = customerFinanceDao.getBuyingInfo(sapId);
        return BuyingInfoList; 
    }

    public void updateSapCustomer( List<CustomerBasicInfo> customerFinanceList ) {
       for(CustomerBasicInfo customerFinance : customerFinanceList ){
           customerFinanceDao.updateSapCustomer(customerFinance);
       }
        
    }

    public void updateSapCustomerBuyingInfo( List<CustomerBuyingInfo> customerBuyingInfoList ) {
        for(CustomerBuyingInfo customerBuyingInfo : customerBuyingInfoList ){
            customerFinanceDao.updateSapCustomerBuyingInfo(customerBuyingInfo);
        }
        
    }
    
   public void financeSAPSync( List<CustomerBasicInfo> customerFinanceList ) {
       for(CustomerBasicInfo customerFinance : customerFinanceList ){
           String sapId = customerFinance.getSapId();
           customerFinanceDao.updateFinanceSAPSync(sapId);
           customerFinanceDao.updateManInfoSAPSync( sapId );
           customerFinanceDao.updateCounselHistorySAPSync( sapId );
           customerFinanceDao.updateMainSellingSAPSync( sapId );
       }
        
    }

    public void deleteSapCustomer() {
       customerFinanceDao.deleteSapCustomer();
        
    }

    public void deleteSapCustomerBuyingInfo() {
        customerFinanceDao.deleteSapCustomerBuyingInfo();
        
    }

    public SearchResult<Finance> listSAPFinancebySearchCondition( FinanceSearchCondition searchCondition ) {
        Integer count = this.customerFinanceDao.countBySearchConditionForSAP( searchCondition );
        
        searchCondition.terminateSearchCondition( count );
        
        SearchResult<Finance> searchResult = null;
        
        if(searchCondition.isEmptyRecord()){
            searchResult = new SearchResult<Finance>( searchCondition );
        }else{
            List<Finance> financeList = this.customerFinanceDao.listSAPFinancebySearchCondition(searchCondition);
            
            searchResult = new SearchResult<Finance>( financeList , searchCondition );
        }
        return searchResult;
       
    }

    public Finance readFinanceForSap( String sapId ) {
        
      return customerFinanceDao.readFinanceForSap(sapId);
      
    }
    
    public String yearInfo(String code){
    	return customerFinanceDao.yearInfo(code);
    }

    public void saveFinanceYearInfo( Finance finance ){
    	customerFinanceDao.saveFinanceYearInfo( finance );
    }
 



    

    
}
