package com.lgcns.ikep4.support.customer.web;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.model.BasicHistory;
import com.lgcns.ikep4.support.customer.model.BasicInfo;
import com.lgcns.ikep4.support.customer.model.BasicInfoReader;
import com.lgcns.ikep4.support.customer.model.BuyingInfo;
import com.lgcns.ikep4.support.customer.model.CounselHistory;
import com.lgcns.ikep4.support.customer.model.MainBusiness;
import com.lgcns.ikep4.support.customer.model.MainPerson;
import com.lgcns.ikep4.support.customer.model.MainSelling;
import com.lgcns.ikep4.support.customer.model.ManInfoItem;
import com.lgcns.ikep4.support.customer.model.PersonStatic;
import com.lgcns.ikep4.support.customer.model.RelationCompany;
import com.lgcns.ikep4.support.customer.search.BasicInfoReaderSearchCondition;
import com.lgcns.ikep4.support.customer.search.BasicInfoSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerBasicInfoService;
import com.lgcns.ikep4.support.customer.service.CustomerCounselHistoryService;
import com.lgcns.ikep4.support.customer.service.CustomerMainSellingService;
import com.lgcns.ikep4.support.customer.service.CustomerService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ModelBeanUtil;


/**
 * 
 * 고객 기본정보 컨트롤러 
 *
 * @author SongHeeJung
 * @version $Id$
 */


@Controller
@RequestMapping(value="/support/customer/customerBasicInfo")
public class CustomerBasicInfoController extends BaseController {
    
    
    @Autowired
    CustomerBasicInfoService customerBasicInfoService;
    
    @Autowired
    CustomerService customerService;
    
    @Autowired
    CustomerMainSellingService customerMainSellingService;
    
    @Autowired
    CustomerCounselHistoryService customerCounselHistoryService;
    
   
    /**
     * 고객정보 리스트 보여주기
     * @return
     */
    @RequestMapping( value = "/customerList.do" )
    public ModelAndView CustomerList(BasicInfoSearchCondition searchCondition,
            @RequestParam(value="searchConditionString",required = false)String searchConditionString) {

        //화면이 넘어갈때 마다 searchCondition 조건을 String으로 가져가기 위함
        String tempSearchConditionString = null;
        if ( StringUtils.isEmpty( searchConditionString ) ) {
            tempSearchConditionString = ModelBeanUtil.makeSearchConditionString( searchCondition, "pageIndex",
                    "searchColumn", "searchWord", "sortColumn", "sortType" );
        } else {
            ModelBeanUtil.makeSearchCondition( searchConditionString, searchCondition );
            tempSearchConditionString = searchConditionString;
        }
        
        //페이징할 row수 설정 (초기 로딩시에는 기본값으로 설정한다)

        if ( searchCondition.getPagePerRecord().equals( "" ) ) {
            searchCondition.setPagePerRecord( searchCondition.getDefaultPagePerRecord() );

        }
        
        SearchResult<BasicInfo> searchResult = this.customerBasicInfoService.listBasicInfoBySearchCondition( searchCondition );
        
        ModelAndView modelAndView = new ModelAndView().addObject( "searchResult",searchResult )
        .addObject( "searchCondition", searchCondition )
         .addObject( "searchConditionString", tempSearchConditionString )
        .addObject("boardCode",new BoardCode());
        return modelAndView;
    }

    /**
     * 고객 기본정보 상세 보기 
     * @param id
     * @return
     */
    @RequestMapping(value="/detailBasicInfo.do")
    public ModelAndView detailBasicInfo (@RequestParam("id")String id){
      
        //로그인 한 사용자가 admin인지 확인
        User user = (User) getRequestAttribute("ikep.user");
        
        
        
        //로그인한 사용자가 admin인지 확인
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        if(!isAdmin){
            
            boolean result = customerService.checkAccess(user.getUserId());
            
            if(!result){
                throw new IKEP4AuthorizedException();
            }
        }
        
        
        BasicInfo basicInfo = getBasicInfo( id );
        
        String regno = basicInfo.getRegno();
        if(!regno.equals( "" ) && !regno.equals( null )){
            String regno1 = regno.substring( 0, 3 );
            String regno2 = regno.substring( 3, 5 );
            String regno3 = regno.substring( 5, 10 );
            basicInfo.setRegno1( regno1 );
            basicInfo.setRegno2( regno2 );
            basicInfo.setRegno3( regno3 );
        }
        
        String corporationNo = basicInfo.getCorporationNo();
        if(!corporationNo.equals( "" ) && !corporationNo.equals( null )){
            String corporationNo1 = corporationNo.substring( 0,6 );
            String corporationNo2 = corporationNo.substring( 6, 13 );
            
            basicInfo.setCorporationNo1( corporationNo1 );
            basicInfo.setCorporationNo2( corporationNo2 );
        }
        
        List<BasicHistory> basicHistory = customerBasicInfoService.readBasicHistory( id );
    
        List<MainPerson> mainPerson = getMainPerson( id );
        
        PersonStatic personStatic = getPersonStatic( id );
        
        List<MainBusiness> mainBusiness = customerBasicInfoService.readMainBusiness( id );
        
        String buyingCondition = "";
        String sellingCondition = "";
        
        if(mainBusiness != null){
            for(int i=0 ; i<mainBusiness.size(); i++){
                if(mainBusiness.get( i ).getSellingBuyingFlag().equals( "1" )){
                  
                    buyingCondition = mainBusiness.get( i ).getCondition();
                }else if(mainBusiness.get( i ).getSellingBuyingFlag().equals( "2" )){
                   
                    sellingCondition = mainBusiness.get( i ).getCondition();
                  
                }
            }
         }
        
        int buyingCnt = 1;
        int sellingCnt = 1;

        
        if(mainBusiness != null){
           for(int i=0 ; i<mainBusiness.size(); i++){
               if(mainBusiness.get( i ).getSellingBuyingFlag().equals( "1" )){
                   buyingCnt ++;
                   
               }else if(mainBusiness.get( i ).getSellingBuyingFlag().equals( "2" )){
                   sellingCnt++;
                  
               }
           }
        }

        List<RelationCompany> relationCompany = customerBasicInfoService.readRelationCompany( id );
        
        List<BuyingInfo> buyingInfoList = null;
        
        if(basicInfo.getSapId() != "" && basicInfo.getSapId() != null){
           
            buyingInfoList = customerBasicInfoService.getBuyingInfo(basicInfo.getSapId());
                    
        }
        
        BasicInfo info = new BasicInfo();
        info.setId(id);
        info.setDivCode("BM");
        info.setRegisterId(user.getUserId());
        customerBasicInfoService.updateHitCount(info);

        ModelAndView modelAndView = new ModelAndView().addObject( "basicInfo", basicInfo )
        .addObject( "isAdmin", isAdmin )
        .addObject( "basicHistory", basicHistory )
        .addObject( "mainPerson", mainPerson )
        .addObject( "personStatic", personStatic)
        .addObject( "mainBusiness", mainBusiness)
        .addObject( "buyingCondition", buyingCondition )
        .addObject( "sellingCondition", sellingCondition )
        .addObject( "buyingCnt", buyingCnt )
        .addObject( "sellingCnt", sellingCnt )
        .addObject( "relationCompany", relationCompany )
        .addObject( "buyingInfoList", buyingInfoList );    
        return modelAndView;
    }

    /**
     * 고객기본정보 가져온다.
     * @param id
     * @return
     */
    private BasicInfo getBasicInfo( String id ) {
        BasicInfo basicInfo = new BasicInfo();        
        basicInfo = customerBasicInfoService.readBasicInfo( id );
   
        if(basicInfo != null){
            if(basicInfo.getCustomerGubun().equals( "1" )){
                basicInfo.setCustomerGubun( "유통사" );
            }else if(basicInfo.getCustomerGubun().equals( "2" )){
                basicInfo.setCustomerGubun( "인쇄/가공사" );
            }else if(basicInfo.getCustomerGubun().equals( "3" )){
                basicInfo.setCustomerGubun( "출판/기획사" );
            }else if(basicInfo.getCustomerGubun().equals( "4" )){
                basicInfo.setCustomerGubun( "일반고객" );
            }
            
            if(basicInfo.getSubdivisionGrade().equals( "1" )){
                basicInfo.setSubdivisionGrade( "Target고객" );
            }else if(basicInfo.getSubdivisionGrade().equals( "2" )){
                basicInfo.setSubdivisionGrade( "이익개선고객" );
            }else if(basicInfo.getSubdivisionGrade().equals( "3" )){
                basicInfo.setSubdivisionGrade( "매출개선고객" );
            }else if(basicInfo.getSubdivisionGrade().equals( "4" )){
                basicInfo.setSubdivisionGrade( "관리대상고객" );
            } 
       }
        
        if(basicInfo == null)
            basicInfo = new BasicInfo();
        return basicInfo;
    }

    /**
     * 고객상세정보(인원현황) 가져오기
     * @param id
     * @return
     */
    private PersonStatic getPersonStatic( String id ) {
  
        PersonStatic personStatic = customerBasicInfoService.readPersonStatic( id ); 
  
        return personStatic;
    }


    /**
     * 고객 상세정보 (주요임원) 가져오기 
     * @param id
     * @return
     */
    private List<MainPerson> getMainPerson( String id ) {
       
        if(id.length() > 1){
           String[] ids = id.split( "," );
           id = ids[0];
        }
        
        List<MainPerson> mainPerson = customerBasicInfoService.readMainPerson( id );
        
        if(mainPerson.size() >0){
            for(int i=0; i<mainPerson.size(); i++){
                if(mainPerson.get( i ).getOfficerFlag().equals( "N" )){
                    if(mainPerson.get( i ).getKeymanFlag().equals( "1" )){
                        mainPerson.get( i ).setKeymanFlag( "임원" );                      
                    }else if(mainPerson.get( i ).getKeymanFlag().equals( "2" )){
                        mainPerson.get( i ).setKeymanFlag( "영업" );       
                    }else if(mainPerson.get( i ).getKeymanFlag().equals( "3" )){
                        mainPerson.get( i ).setKeymanFlag( "구매" );       
                    }else if(mainPerson.get( i ).getKeymanFlag().equals( "4" )){
                        mainPerson.get( i ).setKeymanFlag( "기타" );       
                    }             
                }
            }
        }
        return mainPerson;
    }

    /**
     * 등록 팝업창 생성 및 고객 조회
     * @return
     */
    @RequestMapping(value="/popupBusinessNo.do")
    public ModelAndView popupBusinessNo (BasicInfoSearchCondition searchCondition,
            @RequestParam(value="searchConditionString",required = false)String searchConditionString){
     
        //화면이 넘어갈때 마다 searchCondition 조건을 String으로 가져가기 위함
        String tempSearchConditionString = null;
        if ( StringUtils.isEmpty( searchConditionString ) ) {
            tempSearchConditionString = ModelBeanUtil.makeSearchConditionString( searchCondition, "pageIndex",
                    "searchColumn", "searchWord", "sortColumn", "sortType" );
        } else {
            ModelBeanUtil.makeSearchCondition( searchConditionString, searchCondition );
            tempSearchConditionString = searchConditionString;
        }
        
        //페이징할 row수 설정 (초기 로딩시에는 기본값으로 설정한다)

        if ( searchCondition.getPagePerRecord().equals( "" ) ) {
            searchCondition.setPagePerRecord( searchCondition.getDefaultPagePerRecord() );

        }
        
        SearchResult<BasicInfo> searchResult = this.customerBasicInfoService.listBasicInfoBySearchCondition( searchCondition );
        
        return new ModelAndView().addObject( "searchResult",searchResult )
        .addObject( "searchCondition", searchCondition )
        .addObject( "searchConditionString", tempSearchConditionString )
       .addObject("boardCode",new BoardCode());
    }
    
    
    
    
    /**
     * 고객정보 저장,수정 화면 출력 (기본정보)
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyBasicInfo.do")
    public ModelAndView modifyBasicInfoView(@RequestParam(value = "id", required = false)String id){
     
        String selectedCustomerGubun = "";
        String selectedSubdivisionGrade = "";
        ModelAndView mav = new ModelAndView();
      
        
        if(id != null && id != ""){
            BasicInfo basicInfo = customerBasicInfoService.readBasicInfo( id );
            String regno = basicInfo.getRegno();
            if(!regno.equals( "" ) && !regno.equals( null )){
                String regno1 = regno.substring( 0, 3 );
                String regno2 = regno.substring( 3, 5 );
                String regno3 = regno.substring( 5, 10 );
                basicInfo.setRegno1( regno1 );
                basicInfo.setRegno2( regno2 );
                basicInfo.setRegno3( regno3 );
            }
            String corporationNo = basicInfo.getCorporationNo();
            if(!corporationNo.equals( "" ) && !corporationNo.equals( null )){
                String corporationNo1 = corporationNo.substring( 0,6 );
                String corporationNo2 = corporationNo.substring( 6, 13 );
                
                basicInfo.setCorporationNo1( corporationNo1 );
                basicInfo.setCorporationNo2( corporationNo2 );
            }
            
            selectedCustomerGubun = basicInfo.getCustomerGubun();
            selectedSubdivisionGrade = basicInfo.getSubdivisionGrade();
            
            return mav.addObject( "basicInfo", basicInfo )        
            .addObject( "selectedCustomerGubun", selectedCustomerGubun )
            .addObject( "selectedSubdivisionGrade", selectedSubdivisionGrade );  
            
         }else{
             BasicInfo basicInfo2 = new BasicInfo();
            
            return mav.addObject( "basicInfo", basicInfo2 )        
            .addObject( "selectedCustomerGubun", selectedCustomerGubun )
            .addObject( "selectedSubdivisionGrade", selectedSubdivisionGrade );  
         }
        
     
        
    }
    
    
    
    /**
     * 고객정보 저장,수정화면 출력 (연혁정보)
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyBasicHistory.do")
    public ModelAndView modifyBasicHistoryView(@RequestParam(value = "id", required=false) String id){
        
        List<BasicHistory> basicHistory = customerBasicInfoService.readBasicHistory( id );
        
        if(basicHistory.isEmpty()){
            basicHistory = null;
        }
        
        ModelAndView mav = new ModelAndView();
        
        return mav.addObject( "basicHistory", basicHistory );    
    }
    
    /**
     * 고객정보 수정화면 출력(설비현황)
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyBasicInfoEquipment.do")
    public ModelAndView modifyEquipmentView(@RequestParam("id")String id){
        ModelAndView mav = new ModelAndView();
        BasicInfo basicInfo = customerBasicInfoService.readBasicInfo( id );
        
        return mav.addObject( "basicInfo", basicInfo ).addObject( "id", id );   
    }
    
    /**
     * 고객정보 수정화면 출력 (주요임원)
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyMainPerson1.do")
    public ModelAndView modifyMainPerson1View(@RequestParam("id") String id){
        
        List<MainPerson> mainPerson = getMainPerson( id );
        
        if(mainPerson.isEmpty()){
            mainPerson = null;
        }
        
        ModelAndView mav = new ModelAndView();
        
        return mav.addObject( "mainPerson", mainPerson ).addObject( "id", id );   
       
    }
    
    /**
     * 고객정보 수정화면 출력(keyman)
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyMainPerson2.do")
    public ModelAndView modifyMainPerson2View(@RequestParam("id") String id){
        
        List<MainPerson> mainPerson = getMainPerson( id );
        
        if(mainPerson.isEmpty()){
            mainPerson = null;
        }
        
        ModelAndView mav = new ModelAndView();
        
        return mav.addObject( "mainPerson", mainPerson ).addObject( "id", id );   
    }
    
    /**
     * 고객정보 수정화면 출력(인원현황)
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyPersonStatic.do")
    public ModelAndView modifyPersonStaticView(@RequestParam("id") String id){
        //System.out.println("id:" +id);
        
        PersonStatic personStatic = getPersonStatic( id );
        
        ModelAndView mav = new ModelAndView();
        
        return mav.addObject( "personStatic", personStatic ).addObject( "id", id );
    }
    /**
     * 고객정보 수정화면 출력(주거래선)
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyMainBusiness.do")
    public ModelAndView modifyMainBusinessView(@RequestParam("id") String id){
        
        List<MainBusiness> mainBusiness = customerBasicInfoService.readMainBusiness( id);
        String buyingCondition = "";
        String sellingCondition = "";
        
        if(mainBusiness != null){
            for(int i=0 ; i<mainBusiness.size(); i++){
                if(mainBusiness.get( i ).getSellingBuyingFlag().equals( "1" )){
                  
                    buyingCondition = mainBusiness.get( i ).getCondition();
                }else if(mainBusiness.get( i ).getSellingBuyingFlag().equals( "2" )){
                   
                    sellingCondition = mainBusiness.get( i ).getCondition();
                  
                }
            }
         }
        
        if(mainBusiness.isEmpty()){
            mainBusiness = null;
        }
        ModelAndView mav = new ModelAndView();
        
        return mav.addObject( "mainBusiness", mainBusiness)
                  .addObject( "buyingCondition", buyingCondition )
                  .addObject( "sellingCondition", sellingCondition )
                  .addObject( "id", id );
    }
    
    /**
     * 고객정보 수정화면 출력 (관계사 현황)
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyBasicRelationCompany.do")
    public ModelAndView modifyBasicRelationCompanyView(@RequestParam("id") String id){
        List<RelationCompany> relationCompany = customerBasicInfoService.readRelationCompany( id );
         
        if(relationCompany.isEmpty()){
             relationCompany = null;            
        
        }
        
        ModelAndView mav = new ModelAndView();
        
        mav.addObject( "relationCompany", relationCompany );
        return mav;
    }
    
    
    /**
     * 고객사의 기본정보를 저장한다. (기존에 고객사가 저장되어있을 때) 
     * @param basicInfo
     * @param bindResult
     * @param status
     * @return
     */
    @RequestMapping(value="/saveBasicInfo.do")
    public String saveBasicInfo(BasicInfo basicInfo){
        User user = (User) getRequestAttribute( "ikep.user" );   
        String id = null;
     
        basicInfo.setRegno( basicInfo.getRegno1()+ basicInfo.getRegno2() + basicInfo.getRegno3());
        basicInfo.setCorporationNo( basicInfo.getCorporationNo1() + basicInfo.getCorporationNo2() );
        basicInfo.setUpdaterId( user.getUserId() );
        basicInfo.setUpdaterName( user.getUserName() );
        basicInfo.setRegisterId( user.getUserId() );
        basicInfo.setRegisterName(user.getUserName() );

        try{
           id=  customerBasicInfoService.saveBasicInfo(basicInfo);
           
           if(basicInfo.getSapId() !=null && basicInfo.getSapId() !=""){
               //sapId가 null이 아닐때, 주요판매처, 상담내역 DB name 동기화
               MainSelling mainSelling = new MainSelling();
               mainSelling.setCustomer( basicInfo.getName() );
               mainSelling.setCustomerId( basicInfo.getId() );
               mainSelling.setSapId( basicInfo.getSapId() );
               
               customerMainSellingService.syncCustomerName( mainSelling );
               
               
               CounselHistory counselHistory = new CounselHistory();
               counselHistory.setCustomer( basicInfo.getName() );
               counselHistory.setCustomerId( basicInfo.getId() );
               counselHistory.setSapId( basicInfo.getSapId() );
               
               customerCounselHistoryService.syncCustomerName( counselHistory );
               
               ManInfoItem manInfoItem = new ManInfoItem();
               manInfoItem.setCustomerId( basicInfo.getId() );
               manInfoItem.setCustomerName( basicInfo.getName() );
               manInfoItem.setSapId( basicInfo.getSapId() );
               customerService.syncCustomerName(manInfoItem);
               
               
           }
           
        }catch(Exception e){
            throw new IKEP4AjaxException( "code", e );
        }
        

        
        return "redirect:/support/customer/customerBasicInfo/modifyBasicInfo.do?id="+id;
        
    }
    
    /**
     * 고객사의 기본정보를 저장한다. (연혁정보)
     * @param basicHistory
     * @param id
     * @param historyCnt
     * @return
     */
    @RequestMapping(value="/saveBasicHistory.do")
    public String saveBasicHistory(BasicHistory basicHistory, @RequestParam(value="historyCnt") String historyCnt){
        
        customerBasicInfoService.saveBasicHistory( basicHistory, historyCnt );
        
        return "redirect:/support/customer/customerBasicInfo/modifyBasicHistory.do?id="+basicHistory.getId();
        
    }
    /**
     * 고객사의 기본정보를 저장한다.(설비현황)
     * @param basicInfo
     * @param id
     * @return
     */
    @RequestMapping(value="/saveBasicEquipment.do")
    public String saveBasicEquipment(BasicInfo basicInfo, String id){
        
        customerBasicInfoService.saveBasicEquipment(basicInfo);
        
        return "redirect:/support/customer/customerBasicInfo/modifyBasicInfoEquipment.do?id="+id;
        
    }
    /**
     * 고객사의 기본정보를 저장한다.(주요임원)
     * @param mainPerson
     * @param id
     * @param personCnt
     * @return
     */
    @RequestMapping(value="/saveMainPerson1.do")
    public String saveMainPerson1 (MainPerson mainPerson, String id,  @RequestParam("personCnt") String personCnt){
        
        customerBasicInfoService.saveMainPerson1( mainPerson , personCnt);
        
        return "redirect:/support/customer/customerBasicInfo/modifyMainPerson1.do?id="+id;
    }
    
    /**
     * 고객사의 기본정보를 저장한다. (keyman)
     * @param mainPerson
     * @param id
     * @param personCnt
     * @return
     */
    @RequestMapping(value="/saveMainPerson2.do")
    public String saveMainPerson2 (MainPerson mainPerson, String id,  @RequestParam("personCnt") String personCnt){
        
        customerBasicInfoService.saveMainPerson2( mainPerson , personCnt);
        
        return "redirect:/support/customer/customerBasicInfo/modifyMainPerson2.do?id="+id;
    }
    
    /**
     * 고객사의 기본정보를 저장한다.(인원현황)
     * @param personStatic
     * @param id
     * @return
     */
    @RequestMapping(value="/savePersonStatic.do")
    public String savePersonStatic (PersonStatic personStatic){
        
        customerBasicInfoService.savePersonStatic(personStatic, personStatic.getId());
        
        return "redirect:/support/customer/customerBasicInfo/modifyPersonStatic.do?id="+personStatic.getId();
    }
    
    /**
     * 고객사의 기본정보를 저장한다.( 주거래선)
     * @param mainBusiness
     * @param buyingCnt
     * @param sellingCnt
     * @return
     */
    @RequestMapping(value="/saveMainBusiness.do")
    public String saveMainBusiness (MainBusiness mainBusiness,@RequestParam("buyingCnt")String buyingCnt,
            @RequestParam("sellingCnt")String sellingCnt){
        
        customerBasicInfoService.saveMainBusiness(mainBusiness, buyingCnt, sellingCnt);
        
        
        return "redirect:/support/customer/customerBasicInfo/modifyMainBusiness.do?id="+mainBusiness.getId();
    }
    
    
    /**
     * 고객사의 기본정보를 저장한다.(관계사)
     * @param relationCompany
     * @param companyCnt
     * @return
     */
    @RequestMapping(value="/saveRelationCompany.do")
    public String saveRelationCompany (RelationCompany relationCompany, 
             @RequestParam("companyCnt") String companyCnt){
    
        customerBasicInfoService.saveRelationCompany(relationCompany , companyCnt);
                
        return "redirect:/support/customer/customerBasicInfo/modifyBasicRelationCompany.do?id="+relationCompany.getId();
        
    }
    
    /**
     * 고객 세분화 등급 참조 팝업창을 띄운다.
     *
     */
    @RequestMapping(value="/popupCustomerGrade.do")
    public void popupCustomerGrade (){
        
    }
    
    /**
     * 고객사의 기본정보 삭제 (delete_flag = Y)
     * @param id
     * @return
     */
    @RequestMapping(value="/deleteBasicInfo.do")
    public String deleteBasicInfo(@RequestParam("id")String id){
        
        User user = (User)getRequestAttribute( "ikep.user" );
        BasicInfo basicInfo = new BasicInfo();
        basicInfo.setId( id );
        basicInfo.setUpdaterId( user.getUserId() );
        basicInfo.setUpdaterName( user.getUserName() );
        
        customerBasicInfoService.deleteBasicInfo(basicInfo);
        
        return "redirect:/support/customer/customerBasicInfo/customerList.do";
        
    }
    
    @RequestMapping(value="/listReaderView.do")
    public ModelAndView listReaderView(@RequestParam("id")String id,
    		@RequestParam("divCode")String divCode,
    		BasicInfoReaderSearchCondition searchCondition){
        
        searchCondition.setDivCode(divCode);
        SearchResult<BasicInfoReader> searchResult = this.customerBasicInfoService.listBasicInfoReaderBySearchCondition( searchCondition );
        ModelAndView mav = new ModelAndView()
        .addObject("searchResult", searchResult)
        .addObject("searchCondition", searchResult.getSearchCondition())
        .addObject("boardCode", new BoardCode());;
        return mav;
        
    }
    
    
    @RequestMapping(value="/popupSAPSync.do")
    public ModelAndView popupSAPSync (BasicInfoSearchCondition searchCondition, 
            @RequestParam(value="searchConditionString",required = false)String searchConditionString
            ,@RequestParam(value="id",required=false)String id,
            @RequestParam(value="name",required=false)String sendName){
        
        if(sendName !=null){
            searchCondition.setSearchWord( sendName );
            searchCondition.setSearchColumn( "name" );
        }
        
        //화면이 넘어갈때 마다 searchCondition 조건을 String으로 가져가기 위함
        String tempSearchConditionString = null;
        if ( StringUtils.isEmpty( searchConditionString ) ) {
            tempSearchConditionString = ModelBeanUtil.makeSearchConditionString( searchCondition, "pageIndex",
                    "searchColumn", "searchWord", "sortColumn", "sortType" );
        } else {
            ModelBeanUtil.makeSearchCondition( searchConditionString, searchCondition );
            tempSearchConditionString = searchConditionString;
        }
        
        //페이징할 row수 설정 (초기 로딩시에는 기본값으로 설정한다)

        if ( searchCondition.getPagePerRecord().equals( "" ) ) {
            searchCondition.setPagePerRecord( searchCondition.getDefaultPagePerRecord() );

        }
        
        SearchResult<BasicInfo> searchResult = this.customerBasicInfoService.listSAPBasicInfobySearchCondition(searchCondition);
        
        return new ModelAndView().addObject( "searchResult",searchResult )
        .addObject( "searchCondition", searchCondition )
        .addObject( "searchConditionString", tempSearchConditionString )
        .addObject("boardCode",new BoardCode())
        .addObject( "id" , id );
        
    }

    
    @RequestMapping(value="/detailBasicInfoForSAP.do")
    public ModelAndView detailBasicInfoForSAP (@RequestParam("sapId")String sapId,@RequestParam(value="id",required=false)String id){
        
        String selectedCustomerGubun = "";
        String selectedSubdivisionGrade = "";
        ModelAndView mav = new ModelAndView();
        BasicInfo basicInfo = null;
        if(id !=null && id != ""){
            basicInfo = customerBasicInfoService.readBasicInfo( id );
            BasicInfo basicInfoSap = customerBasicInfoService.readBasicInfoForSap( sapId );
            
            basicInfo.setSapId( basicInfoSap.getSapId() );
            basicInfo.setName( basicInfoSap.getName() );
            basicInfo.setRegno( basicInfoSap.getRegno() );
            
            String regno = basicInfoSap.getRegno();
            if(!regno.equals( "" ) && !regno.equals( null )){
                String regno1 = regno.substring( 0, 3 );
                String regno2 = regno.substring( 3, 5 );
                String regno3 = regno.substring( 5, 10 );
                basicInfo.setRegno1( regno1 );
                basicInfo.setRegno2( regno2 );
                basicInfo.setRegno3( regno3 );
            }
            
            String corporationNo = basicInfoSap.getCorporationNo();
            if(!corporationNo.equals( "" ) && !corporationNo.equals( null )){
                String corporationNo1 = corporationNo.substring( 0,6 );
                String corporationNo2 = corporationNo.substring( 6, 13 );
                
                basicInfo.setCorporationNo1( corporationNo1 );
                basicInfo.setCorporationNo2( corporationNo2 );
            }
            
            
            basicInfo.setCorporationNo( basicInfoSap.getCorporationNo() );
            basicInfo.setDirector( basicInfoSap.getDirector() );
            basicInfo.setType( basicInfoSap.getType() );
            basicInfo.setJijongType( basicInfoSap.getJijongType() );
            basicInfo.setPostNo( basicInfoSap.getPostNo() );
            basicInfo.setAddress1( basicInfoSap.getAddress1() );
            basicInfo.setAddress2( basicInfoSap.getAddress2() );
            basicInfo.setTelNo( basicInfoSap.getTelNo() );
            basicInfo.setFax( basicInfoSap.getFax() );
            basicInfo.seteMail( basicInfoSap.geteMail() );
            
            
        }else{
            basicInfo  = customerBasicInfoService.readBasicInfoForSap(sapId);
        }
        
        return mav.addObject( "basicInfo", basicInfo )        
        .addObject( "selectedCustomerGubun", selectedCustomerGubun )
        .addObject( "selectedSubdivisionGrade", selectedSubdivisionGrade );  
       
    }
    
    /**
     * sap에도 정보없고, ep에도 정보없을 경우 저장 화면 출력
     * @param sapId
     * @param id
     * @return
     */
    @RequestMapping(value="/detailBasicInfoForSAP2.do")
    public ModelAndView detailBasicInfoForSAP (@RequestParam("name")String name){
        
        String selectedCustomerGubun = "";
        String selectedSubdivisionGrade = "";
        ModelAndView mav = new ModelAndView();
        BasicInfo basicInfo = new BasicInfo();
        
        basicInfo.setName( name );
        
        return mav.addObject( "basicInfo", basicInfo )        
        .addObject( "selectedCustomerGubun", selectedCustomerGubun )
        .addObject( "selectedSubdivisionGrade", selectedSubdivisionGrade );  
       
    }
    
    
    

}
