package com.lgcns.ikep4.support.customer.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.model.BasicInfo;
import com.lgcns.ikep4.support.customer.model.MainSelling;
import com.lgcns.ikep4.support.customer.search.MainSellingSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerBasicInfoService;
import com.lgcns.ikep4.support.customer.service.CustomerMainSellingService;
import com.lgcns.ikep4.support.customer.service.CustomerService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 
 * 고객들의 판매처 정보 Controller
 *
 * @author SongHeeJung
 * @version $Id$
 */
@Controller
@RequestMapping(value="/support/customer/customerMainSelling")
public class CustomerMainSellingController extends BaseController {

    
    @Autowired
    CustomerMainSellingService customerMainSellingService;
    
    @Autowired
    CustomerBasicInfoService customerBasicInfoService;
    
    @Autowired
    CustomerService customerService;
    
    /**
     * 고객별 주요판매처 정보 리스트 뿌리기 
     * @return
     */
    @RequestMapping(value="/mainSellingList.do")
    public ModelAndView mainSellingList(MainSellingSearchCondition searchCondition, 
            @RequestParam(value="searchConditionString",required=false)String searchConditionString){
        
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
        
        SearchResult<MainSelling> searchResult = this.customerMainSellingService.listMainSellingBySearchCondition(searchCondition);
        
        ModelAndView modelAndView = new ModelAndView().addObject( "searchResult", searchResult )
        .addObject( "searchCondition", searchResult.getSearchCondition() )
        .addObject( "searchConditionString", tempSearchConditionString )
        .addObject( "boardCode", new BoardCode() );
        
        return modelAndView;
    }
    
    /**
     * 주요 판매처 상세정보 조회
     * @param customerId
     * @param registerId
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value = "/detailMainSellingView.do")
    public ModelAndView detailMainSellingView(@RequestParam("customerId")String customerId,
                                        @RequestParam("customer")String customer)
                    throws JsonGenerationException,JsonMappingException,IOException{
        
        User user = (User) getRequestAttribute( "ikep.user" );
        //로그인한 사용자가 admin인지 확인 
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        if(!isAdmin){
            
            boolean result = customerService.checkAccess(user.getUserId());
            
            if(!result){
                throw new IKEP4AuthorizedException();
            }
        }
        
        
        List<MainSelling> mainSellingDetailList = new ArrayList<MainSelling>();
        
        mainSellingDetailList = customerMainSellingService.listMainSellingDetail( customerId );
        
        ModelAndView mav = new ModelAndView("support/customer/customerMainSelling/detailMainSellingView");
        
        String registerId2 = mainSellingDetailList.get( 0 ).getRegisterId();
        
        BasicInfo info = new BasicInfo();
        info.setId(customerId);
        info.setDivCode("MS");
        info.setRegisterId(user.getUserId());
        customerBasicInfoService.updateHitCount(info);
        
        return mav.addObject( "mainSellingDetailList", mainSellingDetailList )
                  .addObject( "isAdmin", isAdmin ).addObject( "customer", customer )
                  .addObject( "customerId", customerId ).addObject( "registerId", registerId2 );
    }
    
    /**
     * 기존에 저장된 고객사의 판매처가 있을 경우 (등록 화면 출력)
     * @param customer
     * @param customerId
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value="/createMainSellingView.do")
    public ModelAndView createMainSellingView(@RequestParam("customer")String customer,
                            @RequestParam("customerId")String customerId)throws JsonGenerationException, JsonMappingException,IOException{
        
        MainSelling mainSelling = new MainSelling();
        mainSelling.setCustomer( customer );
        mainSelling.setCustomerId( customerId );
        return this.bindResult( new ModelAndView().addObject( "mainSelling", mainSelling ) );    

    }
    /**
     * 기존에 저장된 고객사의 판매처가 있을 경우 추가 등록 프로세스
     * @param mainSelling
     * @return
     */
    @RequestMapping(value="/createMainSelling.do")
    public String createMainSelling (MainSelling mainSelling){
        
        User user = (User)getRequestAttribute( "ikep.user" );
        
        mainSelling.setRegisterId( user.getUserId() );
        mainSelling.setRegisterName( user.getUserName() );
        mainSelling.setUpdaterId( user.getUserId() );
        mainSelling.setUpdaterName( user.getUserName() );
        mainSelling.setDeleteFlag( "N" );
        
        
        String customer = mainSelling.getCustomer();
        try {
            
            customer = URLEncoder.encode( customer , "UTF-8");
            
        } catch ( Exception e ) {
          System.out.println(e);
        }
        
        customerMainSellingService.createMainSelling(mainSelling);
       
       
        return "redirect:/support/customer/customerMainSelling/detailMainSellingView.do?customerId="+mainSelling.getCustomerId()
        +"&customer="+customer+"&registerId="+user.getUserId();
    }
    
    /**
     * 등록 화면 출력 (기존에 등록되지 않은 고객사의 판매처를 등록할 경우) 
     * @return
     */
    @RequestMapping(value="/createMainSellingFirstView.do")
    public ModelAndView createMainSellingFirstView(){
        MainSelling mainSelling = new MainSelling();
        
        ModelAndView mav = new ModelAndView();
        mav.addObject( "mainSelling", mainSelling );
        return mav;
    }
    
    /**
     * 등록 처리 프로세스 (기존에 등록되지 않은 고객사의 판매처를 등록할 경우) 
     * @param mainSelling
     * @return
     */
    @RequestMapping(value="/createMainSellingFirst.do")
    public String createMainSellingFirst(MainSelling mainSelling){
        
        User user = (User)getRequestAttribute( "ikep.user" );
        
        mainSelling.setRegisterId( user.getUserId() );
        mainSelling.setRegisterName( user.getUserName() );
        mainSelling.setUpdaterId( user.getUserId() );
        mainSelling.setUpdaterName( user.getUserName() );
        mainSelling.setDeleteFlag( "N" );
        mainSelling.setInoutFlag( "D" );
        mainSelling.setSellingId( 1 );
        
        customerMainSellingService.createMainSelling(mainSelling);
        
        
        String customer = mainSelling.getCustomer();
        try {
            
            customer = URLEncoder.encode( customer , "UTF-8");
            
        } catch ( Exception e ) {
          System.out.println(e);
        }
        
        
        
        return "redirect:/support/customer/customerMainSelling/detailMainSellingView.do?customerId="+mainSelling.getCustomerId()
        +"&customer="+customer;
    }
    
    /**
     * 주요 판매처 수정 화면 출력 
     * @param mainSellingItem
     * @return
     */
    @RequestMapping(value="/modifyMainSellingView.do")
    public ModelAndView modifyMainSellingView(MainSelling mainSellingItem){

        
        ModelAndView mav = new ModelAndView();
      
        mav.addObject( "mainSellingItem", mainSellingItem );
        
        return mav;
    }
            
    /**
     * 주요 판매처 수정 처리 프로세스
     * @param mainSellingItem
     * @return
     */
    @RequestMapping(value="/modifyMainSelling.do")
    public String modifyMainSelling (MainSelling mainSellingItem){
        User user = (User)getRequestAttribute( "ikep.user" );
        mainSellingItem.setUpdaterId( user.getUserId() );
        mainSellingItem.setUpdaterName( user.getUserName() );
        customerMainSellingService.modifyMainSelling( mainSellingItem );
        
        String customer = mainSellingItem.getCustomer();
        
        try {
            
            customer = URLEncoder.encode( customer , "UTF-8");
            
        } catch ( Exception e ) {
          System.out.println(e);
        }
        
        
        return "redirect:/support/customer/customerMainSelling/detailMainSellingView.do?customerId="+mainSellingItem.getCustomerId()+"&customer="+customer;
    }
    
    /**
     * 주요판매처 삭제 처리 프로세스
     * @param seqNum
     * @param registerId
     * @param customerId
     * @param customer
     */
    @RequestMapping(value="/deleteMainSelling.do")
    public @ResponseBody int deleteMainSelling (@RequestParam("seqNum")int seqNum,@RequestParam("registerId")String registerId,
                                    @RequestParam("customerId")String customerId,@RequestParam("customer")String customer
                                   ){
        
        User user = (User) getRequestAttribute("ikep.user");
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        MainSelling mainSelling = new MainSelling();
        mainSelling.setSeqNum( seqNum );
        mainSelling.setUpdaterId( user.getUserId() );
        mainSelling.setUpdaterName( user.getUserName() );
        
        
        if(isAdmin || user.getUserId().equals( registerId) ){
           customerMainSellingService.deleteMainSelling(mainSelling);
        }
        
        int mainSellingCnt = customerMainSellingService.checkMainSelling( customerId );
      

        return mainSellingCnt;
       
    }
    
    /**
     * 기존에 고객사의 판매처가 등록되어있는지 유무
     * @param customerId
     * @return
     */
    @RequestMapping(value="/checkMainSelling.do")
    public @ResponseBody boolean checkMainSelling(String customerId){
       // boolean existMainSelling = false;
        
        boolean existMainSelling = false;
        int mainSellingCnt = 0;
        
        mainSellingCnt = customerMainSellingService.checkMainSelling(customerId);
      
        if(mainSellingCnt > 0 ){
            existMainSelling = true;
        }
        
        return existMainSelling;
    }
    
}
