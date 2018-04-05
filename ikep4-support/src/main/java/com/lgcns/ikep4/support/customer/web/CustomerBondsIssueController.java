
package com.lgcns.ikep4.support.customer.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.model.BasicInfo;
import com.lgcns.ikep4.support.customer.model.BondsIssueHistory;
import com.lgcns.ikep4.support.customer.model.CustomerBondsIssueSub;
import com.lgcns.ikep4.support.customer.search.CustomerBondsIssueSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerBasicInfoService;
import com.lgcns.ikep4.support.customer.service.CustomerBondsIssueService;
import com.lgcns.ikep4.support.customer.service.CustomerBondsIssueSubService;
import com.lgcns.ikep4.support.customer.service.CustomerService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 
 * 고객정보 채권 관련 이슈 상담이력 컨트롤러
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Controller
@RequestMapping( value = "/support/customer/customerBondsIssue" )
public class CustomerBondsIssueController extends BaseController {

	@Autowired
    private CustomerBondsIssueSubService customerBondsIssueSubService;
	
    @Autowired
    private CustomerBondsIssueService customerBondsIssueService;
    
    @Autowired
    CustomerBasicInfoService customerBasicInfoService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private UserDao userDao;

    /**
     * 채권 관련 이슈 상담내역 리스트 불러오기 
     * @return
     */
    @RequestMapping( value = "/bondsIssueList.do" )
    public ModelAndView bondsIssueList( CustomerBondsIssueSearchCondition searchCondition,
            @RequestParam( value = "searchConditionString", required = false )
            String searchConditionString ) {

    	User user = (User) getRequestAttribute("ikep.user");
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

        SearchResult<BondsIssueHistory> searchResult = this.customerBondsIssueService
                .listBondsIssueBySearchCondition( searchCondition );

        boolean birole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "ACL11");
		int bondsIssueRole = userDao.getUserRoleCheck(map);
		if(bondsIssueRole > 0){
			birole = true;
		}
		
        ModelAndView modelAndView = new ModelAndView().addObject( "searchResult", searchResult )
                .addObject( "searchCondition", searchResult.getSearchCondition() )
                .addObject( "searchConditionString", tempSearchConditionString )
                .addObject( "boardCode", new BoardCode() )
                .addObject("birole", birole);

        return modelAndView;
    }

    /**
     * 채권 관련 이슈 상세화면 조회
     * @param itemId
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping( value = "/detailBondsIssue.do" )
    public ModelAndView detailBondsIssue( @RequestParam( "seqNum" )
    int itemId ) throws JsonGenerationException, JsonMappingException, IOException {

        User user = (User) getRequestAttribute("ikep.user");
        
        //로그인한 사용자가 admin인지 확인
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        if(!isAdmin){
            
            boolean result = false;
    		Map<String, String> map = new HashMap<String, String>();
    		map.put("userId", user.getUserId());
    		map.put("roleName", "ACL10");
    		int bondsIssueRole = userDao.getUserRoleCheck(map);
    		if(bondsIssueRole > 0){
    			result = true;
    		}
    		
            if(!result){
                throw new IKEP4AuthorizedException();
            }
        }
        
        
        BondsIssueHistory bondsIssueHistory = customerBondsIssueService.getBondsIssue( itemId );
        
        
        if(bondsIssueHistory != null){
            if(bondsIssueHistory.getFactory().equals( "S" )){
                bondsIssueHistory.setFactory( "S" );
            }else if(bondsIssueHistory.getFactory().equals( "A" )){
                bondsIssueHistory.setFactory( "A" );
            }else if(bondsIssueHistory.getFactory().equals( "B" )){
                bondsIssueHistory.setFactory( "B" );
            }else if(bondsIssueHistory.getFactory().equals( "C" )){
                bondsIssueHistory.setFactory( "C" );
            }else if(bondsIssueHistory.getFactory().equals( "D" )){
                bondsIssueHistory.setFactory( "D" );
            }else if(bondsIssueHistory.getFactory().equals( "E" )){
                bondsIssueHistory.setFactory( "E" );
            }else if(bondsIssueHistory.getFactory().equals( "F" )){
                bondsIssueHistory.setFactory( "F" );
            }
            
            if(bondsIssueHistory.getClaimGubun().equals( "1" )){
                bondsIssueHistory.setClaimGubun( "신용보험" );
            }else if(bondsIssueHistory.getClaimGubun().equals( "2" )){
                bondsIssueHistory.setClaimGubun( "담보" );
            }else if(bondsIssueHistory.getClaimGubun().equals( "3" )){
                bondsIssueHistory.setClaimGubun( "여신한도" );
            }else if(bondsIssueHistory.getClaimGubun().equals( "4" )){
                bondsIssueHistory.setClaimGubun( "Sub거래선" );
            }else if(bondsIssueHistory.getClaimGubun().equals( "5" )){
                bondsIssueHistory.setClaimGubun( "기타" );
            }else if(bondsIssueHistory.getClaimGubun().equals( "6" )){
                bondsIssueHistory.setClaimGubun( "배서인" );
            }
            
           
       }
        
        boolean birole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "ACL11");
		int bondsIssueRole = userDao.getUserRoleCheck(map);
		if(bondsIssueRole > 0){
			birole = true;
		}

        //첨부파일 가져오기
        ObjectMapper mapper = new ObjectMapper();
        String fileDataListJson = mapper.writeValueAsString(bondsIssueHistory.getFileDataList());
        
        BasicInfo info = new BasicInfo();
        String tmptemId = Integer.toString( itemId );
        info.setId(tmptemId);
        info.setDivCode("BH");
        info.setRegisterId(user.getUserId());
        customerBasicInfoService.updateHitCount(info);
        
        
        ModelAndView modelAndView = new ModelAndView().addObject( "bondsIssueHistory", bondsIssueHistory )
        .addObject("fileDataListJson", fileDataListJson).addObject( "isAdmin", isAdmin ).addObject("birole", birole);
        return modelAndView;
    }
    
    @RequestMapping( value = "/detailBondsIssuePrint.do" )
    public ModelAndView detailBondsIssuePrint( @RequestParam( "seqNum" )
    int itemId ) throws JsonGenerationException, JsonMappingException, IOException {

        User user = (User) getRequestAttribute("ikep.user");
        
        //로그인한 사용자가 admin인지 확인
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        if(!isAdmin){
            
            boolean result = false;
    		Map<String, String> map = new HashMap<String, String>();
    		map.put("userId", user.getUserId());
    		map.put("roleName", "ACL10");
    		int bondsIssueRole = userDao.getUserRoleCheck(map);
    		if(bondsIssueRole > 0){
    			result = true;
    		}
    		
            if(!result){
                throw new IKEP4AuthorizedException();
            }
        }
        
        
        BondsIssueHistory bondsIssueHistory = customerBondsIssueService.getBondsIssue( itemId );
        
        
        if(bondsIssueHistory != null){
            if(bondsIssueHistory.getFactory().equals( "S" )){
                bondsIssueHistory.setFactory( "S" );
            }else if(bondsIssueHistory.getFactory().equals( "A" )){
                bondsIssueHistory.setFactory( "A" );
            }else if(bondsIssueHistory.getFactory().equals( "B" )){
                bondsIssueHistory.setFactory( "B" );
            }else if(bondsIssueHistory.getFactory().equals( "C" )){
                bondsIssueHistory.setFactory( "C" );
            }else if(bondsIssueHistory.getFactory().equals( "D" )){
                bondsIssueHistory.setFactory( "D" );
            }else if(bondsIssueHistory.getFactory().equals( "E" )){
                bondsIssueHistory.setFactory( "E" );
            }else if(bondsIssueHistory.getFactory().equals( "F" )){
                bondsIssueHistory.setFactory( "F" );
            }
            
            if(bondsIssueHistory.getClaimGubun().equals( "1" )){
                bondsIssueHistory.setClaimGubun( "신용보험" );
            }else if(bondsIssueHistory.getClaimGubun().equals( "2" )){
                bondsIssueHistory.setClaimGubun( "담보" );
            }else if(bondsIssueHistory.getClaimGubun().equals( "3" )){
                bondsIssueHistory.setClaimGubun( "여신한도" );
            }else if(bondsIssueHistory.getClaimGubun().equals( "4" )){
                bondsIssueHistory.setClaimGubun( "Sub거래선" );
            }else if(bondsIssueHistory.getClaimGubun().equals( "5" )){
                bondsIssueHistory.setClaimGubun( "기타" );
            }else if(bondsIssueHistory.getClaimGubun().equals( "6" )){
                bondsIssueHistory.setClaimGubun( "배서인" );
            }
            
           
       }
        SearchResult<CustomerBondsIssueSub> searchResult = this.customerBondsIssueSubService.getCustomerBondsIssueSubPrint(itemId);

        //첨부파일 가져오기
        ObjectMapper mapper = new ObjectMapper();
        String fileDataListJson = mapper.writeValueAsString(bondsIssueHistory.getFileDataList());
        
        
        ModelAndView modelAndView = new ModelAndView().addObject( "bondsIssueHistory", bondsIssueHistory )
        .addObject("fileDataListJson", fileDataListJson).addObject( "isAdmin", isAdmin ).addObject( "searchResult", searchResult );
        return modelAndView;
    }

    /**
     * 채권 관련 이슈 등록 화면 불러오기
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping( value = "/createBondsIssueView.do" )
    public ModelAndView createBondsIssueView() throws JsonGenerationException, JsonMappingException, IOException {

        BondsIssueHistory bondsIssue = new BondsIssueHistory();

        return this.bindResult( new ModelAndView().addObject( "bondsIssue", bondsIssue ) );

    }

    /**
     * 채권 관련 이슈 등록 프로세스 
     * @param bondsIssue
     * @param request
     * @return
     */
    @RequestMapping( value = "/createBondsIssue.do" )
    public String createBondsIssue( BondsIssueHistory bondsIssue, HttpServletRequest request ) {

        User user = (User)getRequestAttribute( "ikep.user" );

        MultipartRequest multipartRequest = (MultipartRequest)request;
        List<MultipartFile> fileList = multipartRequest.getFiles( "file" );

        bondsIssue.setDeleteFlag( "N" );
        bondsIssue.setRegisterId( user.getUserId() );
        bondsIssue.setUpdaterId( user.getUserId() );

        int bondsIssueId = customerBondsIssueService.createBondsIssue( bondsIssue, user, fileList );
        CustomerBondsIssueSub customerBondsIssueSub = new CustomerBondsIssueSub();
        customerBondsIssueSub.setItemType( "BI" );
        customerBondsIssueSub.setClaimGubunSub(bondsIssue.getClaimGubun());
        customerBondsIssueSub.setClaimGubunSubName(bondsIssue.getClaimGubunName());
        customerBondsIssueSub.setSubjectSub(bondsIssue.getSubject());
        customerBondsIssueSub.setItemId(Integer.toString(bondsIssue.getSeqNum()));
        customerBondsIssueSub.setFactorySub(bondsIssue.getFactory());
        customerBondsIssueSub.setCounselDateSub(bondsIssue.getCounselDate());
        customerBondsIssueSub.setCounselorSub(bondsIssue.getCounselor());
        customerBondsIssueSub.setContents(bondsIssue.getContent());
        customerBondsIssueSub.setMasterSub("Y");
        ModelBeanUtil.bindRegisterInfo( customerBondsIssueSub, user.getUserId(), user.getUserName() );
        this.customerBondsIssueSubService.createBondsIssueSub( customerBondsIssueSub );

        return "redirect:/support/customer/customerBondsIssue/detailBondsIssue.do?seqNum=" + bondsIssueId;
    }
    
    
    /**
     * 채권 관련 이슈 수정화면 불러오기 
     * @return
     */
    @RequestMapping( value = "/modifyBondsIssueView.do")
    public ModelAndView  modifyBondsIssueView (@RequestParam( "seqNum" )int seqNum)throws JsonGenerationException,
            JsonMappingException, IOException{
        
        BondsIssueHistory bondsIssue = customerBondsIssueService.getBondsIssue( seqNum );
        
        if(bondsIssue != null){
            
            if(bondsIssue.getFactory().equals( "S" )){
                bondsIssue.setFactory( "S" );
            }else if(bondsIssue.getFactory().equals( "A" )){
                bondsIssue.setFactory( "A" );
            }else if(bondsIssue.getFactory().equals( "B" )){
                bondsIssue.setFactory( "B" );
            }else if(bondsIssue.getFactory().equals( "C" )){
                bondsIssue.setFactory( "C" );
            }else if(bondsIssue.getFactory().equals( "D" )){
                bondsIssue.setFactory( "D" );
            }else if(bondsIssue.getFactory().equals( "E" )){
                bondsIssue.setFactory( "E" );
            }else if(bondsIssue.getFactory().equals( "F" )){
                bondsIssue.setFactory( "F" );
            }
            
            if(bondsIssue.getClaimGubun().equals( "1" )){
                bondsIssue.setClaimGubun( "신용보험" );
            }else if(bondsIssue.getClaimGubun().equals( "2" )){
                bondsIssue.setClaimGubun( "담보" );
            }else if(bondsIssue.getClaimGubun().equals( "3" )){
                bondsIssue.setClaimGubun( "여신한도" );
            }else if(bondsIssue.getClaimGubun().equals( "4" )){
                bondsIssue.setClaimGubun( "Sub거래선" );
            }else if(bondsIssue.getClaimGubun().equals( "5" )){
                bondsIssue.setClaimGubun( "기타" );
            }else if(bondsIssue.getClaimGubun().equals( "6" )){
            	bondsIssue.setClaimGubun( "배서인" );
            }
           
       }
        
        
        String selectedFactory = bondsIssue.getFactory();
        String selectedClaimGubun = bondsIssue.getClaimGubun();
        
        //첨부파일 가져오기
        ObjectMapper mapper = new ObjectMapper();
        String fileDataListJson = mapper.writeValueAsString(bondsIssue.getFileDataList());
        
        
        ModelAndView mav = new ModelAndView().addObject( "bondsIssue", bondsIssue );
        mav.addObject("fileDataListJson", fileDataListJson)
           .addObject( "selectedFactory", selectedFactory )
           .addObject( "selectedClaimGubun",selectedClaimGubun );
        
        return mav;
    }
    
    /**
     * 채권 관련 이슈 수정 처리 프로세스
     * @return
     */
    @RequestMapping( value = "/modifyBondsIssue.do")
    public String modifyBondsIssue (@RequestParam("seqNum")int seqNum,
            BondsIssueHistory bondsIssue, HttpServletRequest request)throws JsonGenerationException, JsonMappingException, IOException{
        
        User user = (User)getRequestAttribute( "ikep.user" );
        
        MultipartRequest multipartRequest = (MultipartRequest)request;
        List<MultipartFile> fileList = multipartRequest.getFiles( "file" );
        
        //관리자 확인
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        
      
       customerBondsIssueService.updateBondsIssue(bondsIssue,fileList,user,seqNum);
       
       CustomerBondsIssueSub customerBondsIssueSub = new CustomerBondsIssueSub();
       customerBondsIssueSub.setItemType( "BI" );
       customerBondsIssueSub.setClaimGubunSub(bondsIssue.getClaimGubun());
       customerBondsIssueSub.setClaimGubunSubName(bondsIssue.getClaimGubunName());
       customerBondsIssueSub.setSubjectSub(bondsIssue.getSubject());
       customerBondsIssueSub.setItemId(Integer.toString(bondsIssue.getSeqNum()));
       customerBondsIssueSub.setFactorySub(bondsIssue.getFactory());
       customerBondsIssueSub.setCounselDateSub(bondsIssue.getCounselDate());
       customerBondsIssueSub.setCounselorSub(bondsIssue.getCounselor());
       customerBondsIssueSub.setContents(bondsIssue.getContent());
       ModelBeanUtil.bindRegisterInfo( customerBondsIssueSub, user.getUserId(), user.getUserName() );
       this.customerBondsIssueSubService.updateCustomerBondsIssueSubMaster(customerBondsIssueSub);
        
        
        
        return "redirect:/support/customer/customerBondsIssue/detailBondsIssue.do?seqNum="+seqNum;
    }
    
    /**
     * 채권 관련 이슈 삭제
     *
     */
    @RequestMapping(value = "/deleteBondsIssue.do")
    public String deleteBondsIssue(@RequestParam("seqNum")int seqNum, @RequestParam("registerId")String registerId){
        
        User user = (User)getRequestAttribute( "ikep.user" );
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        BondsIssueHistory bondsIssue = new BondsIssueHistory();
        bondsIssue.setSeqNum( seqNum );
        bondsIssue.setUpdaterId( user.getUserId() );
        bondsIssue.setUpdaterName( user.getUserName() );
        
        
        if(isAdmin || user.getUserId().equals( registerId) ){
            customerBondsIssueService.deleteBondsIssue(bondsIssue);
        }
        
        return "redirect:/support/customer/customerBondsIssue/bondsIssueList.do";
        
        
    }
    

}
