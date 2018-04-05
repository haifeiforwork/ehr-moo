package com.lgcns.ikep4.support.customer.web;

import java.io.IOException;
import java.util.List;

import javax.mail.Multipart;
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
import com.lgcns.ikep4.support.customer.model.CounselHistory;
import com.lgcns.ikep4.support.customer.model.QualityClaimHistory;
import com.lgcns.ikep4.support.customer.search.CounselHistorySearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerBasicInfoService;
import com.lgcns.ikep4.support.customer.service.CustomerCounselHistoryService;
import com.lgcns.ikep4.support.customer.service.CustomerService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 
 * 고객별 상담내역 컨트롤러
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Controller
@RequestMapping(value="/support/customer/customerCounselHistory")
public class CustomerCounselHistoryController extends BaseController{

    @Autowired
    CustomerCounselHistoryService counselHistoryService;
    
    @Autowired
    CustomerBasicInfoService customerBasicInfoService;
    
    @Autowired
    CustomerService customerService;
    
    /**
     * 고객별 상담내역 리스트 
     *
     */
    @RequestMapping(value="/counselHistoryList.do")
    public ModelAndView counselHistoryList(CounselHistorySearchCondition searchCondition,
            @RequestParam(value="searchConditionString",required = false)String searchConditionString,
            @RequestParam(value="id",required=false)String id,
            @RequestParam(value="name",required=false)String sendName){
        
        if(sendName !=null){
            String tmpId = id.trim();
            searchCondition.setSearchWord( tmpId );
            
            searchCondition.setSearchColumn( "customerId" );
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

        SearchResult<CounselHistory> searchResult = this.counselHistoryService.listCounselHistoryBySearchCondition( searchCondition );
                

        ModelAndView modelAndView = new ModelAndView().addObject( "searchResult", searchResult )
                .addObject( "searchCondition", searchResult.getSearchCondition() )
                .addObject( "searchConditionString", tempSearchConditionString )
                .addObject( "boardCode", new BoardCode() );

        return modelAndView;
        
    
    }
    /**
     * 고객별 상담내역 상세조회 
     * @param itemId
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value="/detailCounselHistory.do")
    public ModelAndView detailCounselHistory (@RequestParam("seqNum")int itemId) 
    throws JsonGenerationException,JsonMappingException,IOException{
        
        User user = (User) getRequestAttribute( "ikep.user" );
        
        //로그인한 사용자가 관리자인지 확인 
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        if(!isAdmin){
            
            boolean result = customerService.checkAccess(user.getUserId());
            
            if(!result){
                throw new IKEP4AuthorizedException();
            }
        }
        
        
        CounselHistory counselHistory = counselHistoryService.getCounselHistory( itemId);
        
        //첨부파일 가져오기 
        
       ObjectMapper mapper = new ObjectMapper();
       String fileDataListJson = mapper.writeValueAsString( counselHistory.getFileDataList() );
       
       BasicInfo info = new BasicInfo();
       String tmptemId = Integer.toString( itemId );
       info.setId(tmptemId);
       info.setDivCode("CH");
       info.setRegisterId(user.getUserId());
       customerBasicInfoService.updateHitCount(info);
       
       ModelAndView modelAndView = new ModelAndView().addObject( "counselHistory", counselHistory )
       .addObject( "fileDataListJson", fileDataListJson ).addObject( "isAdmin", isAdmin );
       
       return modelAndView;
       
        
    }
    /**
     * 등록 화면 불러오기 
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value="/createCounselHistoryView.do")
    public ModelAndView createCounselHistoryView() throws JsonGenerationException, JsonMappingException,IOException{
       
        CounselHistory counselHistory = new CounselHistory();
        return this.bindResult( new ModelAndView().addObject( "counselHistory", counselHistory ) );
        
    }
    
    /**
     * 등록 프로세스 
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value="/createCounselHistory.do")
    public String createCounselHistory(@RequestParam("customerId")String customerId, CounselHistory counselHistory, HttpServletRequest request)
    throws JsonGenerationException,JsonMappingException,IOException{
        
        User user =(User)getRequestAttribute( "ikep.user");
        
        MultipartRequest multipartRequest = (MultipartRequest)request;
        List<MultipartFile> fileList = multipartRequest.getFiles( "file" );
        
        counselHistory.setCustomerId( customerId );
        counselHistory.setDeleteFlag( "N" );
        counselHistory.setRegisterId( user.getUserId() );
        counselHistory.setRegisterName( user.getUserName() );
        counselHistory.setUpdaterId( user.getUserId() );
        counselHistory.setUpdaterName( user.getUserName() );
        counselHistory.setLinereplyCount( 0 );
        
        int counselHistoryId = counselHistoryService.createCounselHistory(counselHistory, user, fileList);
        
        
        return "redirect:/support/customer/customerCounselHistory/detailCounselHistory.do?seqNum=" + counselHistoryId;
    }
    
    
    /**
     * 고객별 상담내역 수정화면 불러오기 
     * @param seqNum
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value="/modifyCounselHistoryView.do")
    public ModelAndView modifyCounselHistoryView (@RequestParam("seqNum")int seqNum )throws JsonGenerationException,
    JsonMappingException, IOException{
        
        CounselHistory counselHistory = counselHistoryService.getCounselHistory( seqNum );
        
        //첨부파일 가져오기
        ObjectMapper mapper = new ObjectMapper();
        String fileDataListJson = mapper.writeValueAsString( counselHistory.getFileDataList());
        
        ModelAndView mav = new ModelAndView().addObject( "counselHistory", counselHistory )
        .addObject( "fileDataListJson", fileDataListJson );
        
        
        return mav;
    }
    
    /**
     * 고객별 상담내역 수정 프로세스
     * @param seqNum
     * @param counselHistory
     * @param request
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value="/modifyCounselHistory.do")
    public String modifyCounselHistory(@RequestParam("seqNum")int seqNum,
            CounselHistory counselHistory, HttpServletRequest request)throws JsonGenerationException,
            JsonMappingException,IOException{
        
        
        User user = (User)getRequestAttribute( "ikep.user" );
      
        
        MultipartRequest multipartRequest = (MultipartRequest)request;
        List<MultipartFile> fileList = multipartRequest.getFiles( "file" );
        
        //관리자 확인
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
       
        counselHistoryService.updateCounselHistory( counselHistory, fileList, user, seqNum );        
        
        
        
        return "redirect:/support/customer/customerCounselHistory/detailCounselHistory.do?seqNum="+seqNum;
    }
    
    
    
    /**
     * 상담내역 삭제
     * @param seqNum
     * @param registerId
     * @return
     */
    @RequestMapping(value = "/deleteCounselHistory.do")
    public String deleteCounselHistory(@RequestParam("seqNum")int seqNum, @RequestParam("registerId")String registerId){
        
        User user = (User)getRequestAttribute( "ikep.user" );
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        CounselHistory counselHistory  = new CounselHistory();
        counselHistory.setSeqNum( seqNum );
        counselHistory.setUpdaterId( user.getUserId() );
        counselHistory.setUpdaterName( user.getUserName() );
        
        if(isAdmin || user.getUserId().equals( registerId )){
        
            counselHistoryService.deleteCounselHistory(counselHistory);
        }
        
        
        return "redirect:/support/customer/customerCounselHistory/counselHistoryList.do";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
