/*
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */

package com.lgcns.ikep4.support.customer.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.model.BasicInfo;
import com.lgcns.ikep4.support.customer.model.Customer;
import com.lgcns.ikep4.support.customer.model.ManInfoItem;
import com.lgcns.ikep4.support.customer.search.ManInfoItemSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerBasicInfoService;
import com.lgcns.ikep4.support.customer.service.CustomerService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ModelBeanUtil;


/**
 * 
 * 인물 정보 컨트롤러
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Controller
@RequestMapping( value = "/support/customer/customerCommon" )

public class CustomerCommonController extends BaseController {

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    CustomerBasicInfoService customerBasicInfoService;
    
    @Autowired
    private UserDao userDao;

   
    
    /**
     * 고객정보 메뉴화면 불러오기 
     * @return
     */
    @RequestMapping( value = "/menuView.do" )
    public ModelAndView menuView() {
    	
    	try {
			
    		User user = (User) getRequestAttribute("ikep.user");
    		
    		String roleId = (String)userDao.getRoleId(user.getUserId());
    		boolean birole = false;
    		Map<String, String> map = new HashMap<String, String>();
    		map.put("userId", user.getUserId());
    		map.put("roleName", "ACL10");
    		int bondsIssueRole = userDao.getUserRoleCheck(map);
    		if(bondsIssueRole > 0){
    			birole = true;
    		}
    		
    		boolean firole = false;
    		Map<String, String> fmap = new HashMap<String, String>();
    		fmap.put("userId", user.getUserId());
    		fmap.put("roleName", "ACL14");
    		int financeRole = userDao.getUserRoleCheck(fmap);
    		if(financeRole > 0){
    			firole = true;
    		}
    		
    		ModelAndView modelAndView = new ModelAndView("support/customer/customerCommon/menuView");
    		modelAndView.addObject("roleId", roleId);
    		modelAndView.addObject("birole", birole);
    		modelAndView.addObject("firole", firole);
    		return modelAndView;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;

    }



    /**
     * 인물정보 리스트 보여주기
     * @return
     */
    @RequestMapping( value = "/manInfo.do" )
    public ModelAndView ManInfo( ManInfoItemSearchCondition searchCondition,
            @RequestParam( value = "searchConditionString", required = false )
            String searchConditionString,
            @RequestParam(value="id",required=false)String id,
            @RequestParam(value="name",required=false)String sendName ) {
        
        
        if(sendName !=null){
            searchCondition.setSearchWord( id );
            searchCondition.setSearchColumn( "id" );
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

        SearchResult<ManInfoItem> searchResult = this.customerService
                .listManInfoItemBySearchCondition( searchCondition );

        ModelAndView modelAndView = new ModelAndView().addObject( "searchResult", searchResult )
                .addObject( "searchCondition", searchResult.getSearchCondition() )
                .addObject( "searchConditionString", tempSearchConditionString )
                .addObject( "boardCode", new BoardCode() );

        return modelAndView;
    }

    /**
     * 인물정보 상세조회
     * 	 * @return
     */
    @RequestMapping( value = "/detailManInfo.do" )
    public ModelAndView DetailManInfo( @RequestParam( "seqNum" )int seqNum, 
    @RequestParam( value = "searchConditionString", required = false )String searchConditionString ) throws JsonGenerationException, JsonMappingException, IOException {
        
        User user = (User) getRequestAttribute("ikep.user");
        
        //로그인한 사용자가 admin인지 확인
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        if(!isAdmin){
        
            boolean result = customerService.checkAccess(user.getUserId());
            
            if(!result){
                throw new IKEP4AuthorizedException();
            }
        }
        
        //인물정보를 가져온다.

        //String itemId = seqNum.toString();
        ManInfoItem manInfoItem = customerService.readManInfoItem( seqNum );

        String itemId = Integer.toString( seqNum );
        List<ManInfoItem> manFamily = customerService.readManFamily( itemId );
        List<ManInfoItem> manCareer = customerService.readManCareer( itemId );
        
        BasicInfo info = new BasicInfo();
        info.setId(itemId);
        info.setDivCode("PI");
        info.setRegisterId(user.getUserId());
        customerBasicInfoService.updateHitCount(info);
        
        ModelAndView modelAndView = new ModelAndView().addObject( "manInfoItem", manInfoItem )
                .addObject( "manFamily", manFamily ).addObject( "manCareer", manCareer ).addObject( "isAdmin", isAdmin );
        return modelAndView;
    }
    
    /**
     * 인물정보 생성 화면 컨트롤 메서드
     *   * @return
     */
    @RequestMapping( value = "/createManInfoView.do" )
    public ModelAndView createManInfoView()throws JsonGenerationException, JsonMappingException, IOException {

        ManInfoItem manInfoItem = new ManInfoItem();
        
       
      
        return this.bindResult( new ModelAndView().addObject( "manInfoItem",manInfoItem ) );
    }
    
    /**
     * 인물정보 생성 처리 동기 컨트롤 메서드
     *   * @return
     */
    @RequestMapping( value = "/createManInfo.do" )
    public String createManInfo(String userId, ManInfoItem manInfoItem,int familyTrTemp, int careerTrTemp
            ,HttpServletRequest request )throws JsonGenerationException, JsonMappingException, IOException {
        
        User user = (User) getRequestAttribute("ikep.user");

        MultipartRequest multipartRequest = (MultipartRequest) request;
        List<MultipartFile> fileList = multipartRequest.getFiles("file");
        
        
        manInfoItem.setDeleteFlag( "N" );
        manInfoItem.setOversea( "" );
        manInfoItem.setRegisterId( userId );
        manInfoItem.setUpdaterId( userId );
       
        
        int manId = customerService.createManInfoItem( manInfoItem , familyTrTemp , careerTrTemp, fileList ,user);
        
        
        return "redirect:/support/customer/customerCommon/detailManInfo.do?seqNum=" + manId;
    }
    
 
    
    
    /**
     * 인물정보 수정 화면 컨트롤 메서드
     *   * @return
     */
    @RequestMapping( value = "/modifyManInfoView.do" )
    public ModelAndView modifyManInfoView(@RequestParam( "seqNum" )int seqNum ) throws JsonGenerationException, JsonMappingException, IOException {
   
        ManInfoItem manInfoItem = customerService.readManInfoItem( seqNum );

        String itemId = Integer.toString( seqNum );
        List<ManInfoItem> manFamily = customerService.readManFamily( itemId );
        List<ManInfoItem> manCareer = customerService.readManCareer( itemId );

        ModelAndView modelAndView = new ModelAndView().addObject( "manInfoItem", manInfoItem )
                .addObject( "manFamily", manFamily ).addObject( "manCareer", manCareer );
      
        return modelAndView;    
      
    }
    
    /**
     * 인물정보 수정 처리 동기 컨트롤 메서드
     * @return
     */
    @RequestMapping( value = "/modifyManInfo.do" )
    public String modifyManInfo(@RequestParam( "seqNum" )int seqNum,
            @RequestParam( "registerId" )String registerId, ManInfoItem manInfoItem, ManInfoItem manFamily,int familyTrTemp, int careerTrTemp
            ,HttpServletRequest request){
        
        User user = (User) getRequestAttribute("ikep.user");

        MultipartRequest multipartRequest = (MultipartRequest) request;
        List<MultipartFile> fileList = multipartRequest.getFiles("file");
        
       
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
       
        //System.out.println(user.getUserId().equals( registerId));
        if(isAdmin || user.getUserId().equals( registerId)){
            customerService.updateManInfo( manInfoItem , familyTrTemp , careerTrTemp,fileList ,user);
        }
        
        
        return "redirect:/support/customer/customerCommon/detailManInfo.do?seqNum=" + seqNum;
  
    }
    
    /**
     * 인물 정보 삭제 (deleteFlag = Y)
     * @param seqNum
     * @return
     */
    @RequestMapping( value = "/deleteManInfo.do" )
    public String deleteManInfo(@RequestParam("seqNum")int seqNum,@RequestParam("registerId")String registerId){
        
        User user = (User) getRequestAttribute("ikep.user");
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        ManInfoItem manInfoItem = new ManInfoItem();
        manInfoItem.setSeqNum( seqNum );
        manInfoItem.setUpdaterId( user.getUpdaterId() );
        manInfoItem.setUpdaterName( user.getUserName() );
        
        if(isAdmin || user.getUserId().equals( registerId) ){
        customerService.deleteManInfo(manInfoItem);
        }
                
        return "redirect:/support/customer/customerCommon/manInfo.do";
    }
    
    /**
     * 고객사의 ID를 검색한다.
     * @param customer
     * @return
     */
    @RequestMapping(value="/getCustomerId.do")
    public @ResponseBody ModelAndView getCustomerId(String customer){
       
        ModelAndView mav = new ModelAndView();
        List<Customer> customerItem = new ArrayList<Customer>();
        customerItem = customerService.getCustomerId( customer );
        
        return mav.addObject( "customer", customerItem );
    }
    
    
    /**
     * 고객사의 ID를 검색한다.
     * @param customer
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value="/popupCustomer.do")
    public ModelAndView popupCustomer(@RequestParam("customer") String customer){
  
        ModelAndView mav = new ModelAndView();
        List<Customer> customerItem = new ArrayList<Customer>();
        //customer = "교원";
        customerItem = customerService.getCustomerId(customer);
        mav.addObject( "customer", customerItem );
        
        return mav;
    }
   

}
