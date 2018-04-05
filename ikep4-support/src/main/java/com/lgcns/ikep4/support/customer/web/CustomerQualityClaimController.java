
package com.lgcns.ikep4.support.customer.web;

import java.io.IOException;
import java.util.List;

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
import com.lgcns.ikep4.support.customer.model.QualityClaimHistory;
import com.lgcns.ikep4.support.customer.search.CustomerQualityClaimSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerBasicInfoService;
import com.lgcns.ikep4.support.customer.service.CustomerQualityClaimService;
import com.lgcns.ikep4.support.customer.service.CustomerService;

import com.lgcns.ikep4.support.user.member.model.User;

import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 
 * 고객정보 품질/클레임 상담이력 컨트롤러
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Controller
@RequestMapping( value = "/support/customer/customerQualityClaim" )
public class CustomerQualityClaimController extends BaseController {

    
    @Autowired
    private CustomerQualityClaimService customerQualityClaimService;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    CustomerBasicInfoService customerBasicInfoService;

    /**
     * 품질/클레임 상담내역 리스트 불러오기 
     * @return
     */
    @RequestMapping( value = "/qualityClaimList.do" )
    public ModelAndView qualityClaimList( CustomerQualityClaimSearchCondition searchCondition,
            @RequestParam( value = "searchConditionString", required = false )
            String searchConditionString ) {

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

        SearchResult<QualityClaimHistory> searchResult = this.customerQualityClaimService
                .listQualityClaimBySearchCondition( searchCondition );

        ModelAndView modelAndView = new ModelAndView().addObject( "searchResult", searchResult )
                .addObject( "searchCondition", searchResult.getSearchCondition() )
                .addObject( "searchConditionString", tempSearchConditionString )
                .addObject( "boardCode", new BoardCode() );

        return modelAndView;
    }

    /**
     * 품질/클레임 상세화면 조회
     * @param itemId
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping( value = "/detailQualityClaim.do" )
    public ModelAndView detailQualityClaim( @RequestParam( "seqNum" )
    int itemId ) throws JsonGenerationException, JsonMappingException, IOException {

        User user = (User) getRequestAttribute("ikep.user");
        
        //로그인한 사용자가 admin인지 확인
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        if(!isAdmin){
            
            boolean result = customerService.checkAccess(user.getUserId());
            
            if(!result){
                throw new IKEP4AuthorizedException();
            }
        }
        
        
        QualityClaimHistory qualityClaimHistory = customerQualityClaimService.getQualityClaim( itemId );
        
        
        if(qualityClaimHistory != null){
            if(qualityClaimHistory.getFactory().equals( "1" )){
                qualityClaimHistory.setFactory( "진주" );
            }else if(qualityClaimHistory.getFactory().equals( "2" )){
                qualityClaimHistory.setFactory( "울산" );
            }else if(qualityClaimHistory.getFactory().equals( "3" )){
                qualityClaimHistory.setFactory( "대구" );
            }
            
            if(qualityClaimHistory.getClaimGubun().equals( "1" )){
                qualityClaimHistory.setClaimGubun( "A/S" );
            }else if(qualityClaimHistory.getClaimGubun().equals( "2" )){
                qualityClaimHistory.setClaimGubun( "B/S" );
            }else if(qualityClaimHistory.getClaimGubun().equals( "3" )){
                qualityClaimHistory.setClaimGubun( "컴플레인" );
            }
           
       }
        

        //첨부파일 가져오기
        ObjectMapper mapper = new ObjectMapper();
        String fileDataListJson = mapper.writeValueAsString(qualityClaimHistory.getFileDataList());
        
        BasicInfo info = new BasicInfo();
        String tmptemId = Integer.toString( itemId );
        info.setId(tmptemId);
        info.setDivCode("QH");
        info.setRegisterId(user.getUserId());
        customerBasicInfoService.updateHitCount(info);
        
        ModelAndView modelAndView = new ModelAndView().addObject( "qualityClaimHistory", qualityClaimHistory )
        .addObject("fileDataListJson", fileDataListJson).addObject( "isAdmin", isAdmin );
        return modelAndView;
    }

    /**
     * 품질/클레임 등록 화면 불러오기
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping( value = "/createQualityClaimView.do" )
    public ModelAndView createQualityClaimView() throws JsonGenerationException, JsonMappingException, IOException {

        QualityClaimHistory qualityClaim = new QualityClaimHistory();

        return this.bindResult( new ModelAndView().addObject( "qualityClaim", qualityClaim ) );

    }

    /**
     * 품질/클레임 등록 프로세스 
     * @param qualityClaim
     * @param request
     * @return
     */
    @RequestMapping( value = "/createQualityClaim.do" )
    public String createQualityClaim( QualityClaimHistory qualityClaim, HttpServletRequest request ) {

        User user = (User)getRequestAttribute( "ikep.user" );

        MultipartRequest multipartRequest = (MultipartRequest)request;
        List<MultipartFile> fileList = multipartRequest.getFiles( "file" );

        qualityClaim.setDeleteFlag( "N" );
        qualityClaim.setRegisterId( user.getUserId() );
        qualityClaim.setUpdaterId( user.getUserId() );

        int qualityClaimId = customerQualityClaimService.createQualityClaim( qualityClaim, user, fileList );

        return "redirect:/support/customer/customerQualityClaim/detailQualityClaim.do?seqNum=" + qualityClaimId;
    }
    
    
    /**
     * 품질/클레임 수정화면 불러오기 
     * @return
     */
    @RequestMapping( value = "/modifyQualityClaimView.do")
    public ModelAndView  modifyQualityClaimView (@RequestParam( "seqNum" )int seqNum)throws JsonGenerationException,
            JsonMappingException, IOException{
        
        QualityClaimHistory qualityClaim = customerQualityClaimService.getQualityClaim( seqNum );
        
        if(qualityClaim != null){
            if(qualityClaim.getFactory().equals( "1" )){
                qualityClaim.setFactory( "진주" );
            }else if(qualityClaim.getFactory().equals( "2" )){
                qualityClaim.setFactory( "울산" );
            }else if(qualityClaim.getFactory().equals( "3" )){
                qualityClaim.setFactory( "대구" );
            }
            
            if(qualityClaim.getClaimGubun().equals( "1" )){
                qualityClaim.setClaimGubun( "A/S" );
            }else if(qualityClaim.getClaimGubun().equals( "2" )){
                qualityClaim.setClaimGubun( "B/S" );
            }else if(qualityClaim.getClaimGubun().equals( "3" )){
                qualityClaim.setClaimGubun( "컴플레인" );
            }
           
       }
        
        
        String selectedFactory = qualityClaim.getFactory();
        String selectedClaimGubun = qualityClaim.getClaimGubun();
        
        //첨부파일 가져오기
        ObjectMapper mapper = new ObjectMapper();
        String fileDataListJson = mapper.writeValueAsString(qualityClaim.getFileDataList());
        
        
        ModelAndView mav = new ModelAndView().addObject( "qualityClaim", qualityClaim );
        mav.addObject("fileDataListJson", fileDataListJson)
           .addObject( "selectedFactory", selectedFactory )
           .addObject( "selectedClaimGubun",selectedClaimGubun );
        
        return mav;
    }
    
    /**
     * 품질/클레임 수정 처리 프로세스
     * @return
     */
    @RequestMapping( value = "/modifyQualityClaim.do")
    public String modifyQualityClaim (@RequestParam("seqNum")int seqNum,
            QualityClaimHistory qualityClaim, HttpServletRequest request)throws JsonGenerationException, JsonMappingException, IOException{
        
        User user = (User)getRequestAttribute( "ikep.user" );
        
        MultipartRequest multipartRequest = (MultipartRequest)request;
        List<MultipartFile> fileList = multipartRequest.getFiles( "file" );
        
        //관리자 확인
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        
      
       customerQualityClaimService.updateQualityClaim(qualityClaim,fileList,user,seqNum);
        
        
        
        return "redirect:/support/customer/customerQualityClaim/detailQualityClaim.do?seqNum="+seqNum;
    }
    
    /**
     * 품질/클레임 삭제
     *
     */
    @RequestMapping(value = "/deleteQualityClaim.do")
    public String deleteQualityClaim(@RequestParam("seqNum")int seqNum, @RequestParam("registerId")String registerId){
        
        User user = (User)getRequestAttribute( "ikep.user" );
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        QualityClaimHistory qualityClaim = new QualityClaimHistory();
        qualityClaim.setSeqNum( seqNum );
        qualityClaim.setUpdaterId( user.getUserId() );
        qualityClaim.setUpdaterName( user.getUserName() );
        
        
        if(isAdmin || user.getUserId().equals( registerId) ){
            customerQualityClaimService.deleteQualityClaim(qualityClaim);
        }
        
        return "redirect:/support/customer/customerQualityClaim/qualityClaimList.do";
        
        
    }
    

}
