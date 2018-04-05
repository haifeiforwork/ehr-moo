
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
import com.lgcns.ikep4.support.customer.model.CustomerQualitySub;
import com.lgcns.ikep4.support.customer.model.QualityClaimSellHistory;
import com.lgcns.ikep4.support.customer.search.CustomerQualityClaimSellSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerBasicInfoService;
import com.lgcns.ikep4.support.customer.service.CustomerQualityClaimSellMoreService;
import com.lgcns.ikep4.support.customer.service.CustomerQualityClaimSellService;
import com.lgcns.ikep4.support.customer.service.CustomerService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
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
@RequestMapping( value = "/support/customer/customerQualityClaimSell" )
public class CustomerQualityClaimSellController extends BaseController {

	@Autowired
    private CustomerQualityClaimSellMoreService customerQualityClaimSellMoreService;
    
    @Autowired
    private CustomerQualityClaimSellService customerQualityClaimSellService;
    
    @Autowired
    CustomerBasicInfoService customerBasicInfoService;
    
    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserDao userDao;
    
    /**
     * 품질/클레임 상담내역 리스트 불러오기 
     * @return
     */
    @RequestMapping( value = "/qualityClaimSellList.do" )
    public ModelAndView qualityClaimSellList( CustomerQualityClaimSellSearchCondition searchCondition,
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

        SearchResult<QualityClaimSellHistory> searchResult = this.customerQualityClaimSellService
                .listQualityClaimSellBySearchCondition( searchCondition );

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
    @RequestMapping( value = "/detailQualityClaimSell.do" )
    public ModelAndView detailQualityClaimSell( @RequestParam( "seqNum" )
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
        
        
        QualityClaimSellHistory qualityClaimSellHistory = customerQualityClaimSellService.getQualityClaimSell( itemId );
        
        
        if(qualityClaimSellHistory != null){
            if(qualityClaimSellHistory.getFactory().equals( "1" )){
                qualityClaimSellHistory.setFactory( "진주" );
            }else if(qualityClaimSellHistory.getFactory().equals( "2" )){
                qualityClaimSellHistory.setFactory( "울산" );
            }else if(qualityClaimSellHistory.getFactory().equals( "3" )){
                qualityClaimSellHistory.setFactory( "대구" );
            }
            
            if(qualityClaimSellHistory.getClaimGubun().equals( "1" )){
                qualityClaimSellHistory.setClaimGubun( "A/S" );
            }else if(qualityClaimSellHistory.getClaimGubun().equals( "2" )){
                qualityClaimSellHistory.setClaimGubun( "B/S" );
            }else if(qualityClaimSellHistory.getClaimGubun().equals( "3" )){
                qualityClaimSellHistory.setClaimGubun( "컴플레인" );
            }
           
       }
        
        boolean clrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "ACL12");
		int claimRole = userDao.getUserRoleCheck(map);
		if(claimRole > 0){
			clrole = true;
		}

        //첨부파일 가져오기
        ObjectMapper mapper = new ObjectMapper();
        String fileDataListJson = mapper.writeValueAsString(qualityClaimSellHistory.getFileDataList());
        
        BasicInfo info = new BasicInfo();
        String tmptemId = Integer.toString( itemId );
        info.setId(tmptemId);
        info.setDivCode("CL");
        info.setRegisterId(user.getUserId());
        customerBasicInfoService.updateHitCount(info);
        
        ModelAndView modelAndView = new ModelAndView().addObject( "qualityClaimSellHistory", qualityClaimSellHistory )
        .addObject("fileDataListJson", fileDataListJson).addObject( "isAdmin", isAdmin ).addObject( "clrole", clrole );
        return modelAndView;
    }
    
    @RequestMapping( value = "/detailQualityClaimSellPrint.do" )
    public ModelAndView detailQualityClaimSellPrint( @RequestParam( "seqNum" )
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
        
        
        QualityClaimSellHistory qualityClaimSellHistory = customerQualityClaimSellService.getQualityClaimSell( itemId );
        
        
        if(qualityClaimSellHistory != null){
            if(qualityClaimSellHistory.getFactory().equals( "1" )){
                qualityClaimSellHistory.setFactory( "진주" );
            }else if(qualityClaimSellHistory.getFactory().equals( "2" )){
                qualityClaimSellHistory.setFactory( "울산" );
            }else if(qualityClaimSellHistory.getFactory().equals( "3" )){
                qualityClaimSellHistory.setFactory( "대구" );
            }
            
            if(qualityClaimSellHistory.getClaimGubun().equals( "1" )){
                qualityClaimSellHistory.setClaimGubun( "A/S" );
            }else if(qualityClaimSellHistory.getClaimGubun().equals( "2" )){
                qualityClaimSellHistory.setClaimGubun( "B/S" );
            }else if(qualityClaimSellHistory.getClaimGubun().equals( "3" )){
                qualityClaimSellHistory.setClaimGubun( "컴플레인" );
            }
           
       }
        SearchResult<CustomerQualitySub> searchResult = this.customerQualityClaimSellMoreService.getCustomerQualityClaimSellMorePrint( itemId );

        //첨부파일 가져오기
        ObjectMapper mapper = new ObjectMapper();
        String fileDataListJson = mapper.writeValueAsString(qualityClaimSellHistory.getFileDataList());
        
        
        ModelAndView modelAndView = new ModelAndView().addObject( "qualityClaimSellHistory", qualityClaimSellHistory )
        .addObject("fileDataListJson", fileDataListJson).addObject( "isAdmin", isAdmin ).addObject( "searchResult", searchResult );
        return modelAndView;
    }

    /**
     * 품질/클레임 등록 화면 불러오기
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping( value = "/createQualityClaimSellView.do" )
    public ModelAndView createQualityClaimSellView() throws JsonGenerationException, JsonMappingException, IOException {

        QualityClaimSellHistory qualityClaimSell = new QualityClaimSellHistory();

        return this.bindResult( new ModelAndView().addObject( "qualityClaimSell", qualityClaimSell ) );

    }

    /**
     * 품질/클레임 등록 프로세스 
     * @param qualityClaimSell
     * @param request
     * @return
     */
    @RequestMapping( value = "/createQualityClaimSell.do" )
    public String createQualityClaimSell( QualityClaimSellHistory qualityClaimSell, HttpServletRequest request ) {

        User user = (User)getRequestAttribute( "ikep.user" );

        MultipartRequest multipartRequest = (MultipartRequest)request;
        List<MultipartFile> fileList = multipartRequest.getFiles( "file" );

        qualityClaimSell.setDeleteFlag( "N" );
        qualityClaimSell.setRegisterId( user.getUserId() );
        qualityClaimSell.setUpdaterId( user.getUserId() );

        int qualityClaimSellId = customerQualityClaimSellService.createQualityClaimSell( qualityClaimSell, user, fileList );
        
        CustomerQualitySub customerQualitySub = new CustomerQualitySub();
        ModelBeanUtil.bindRegisterInfo( customerQualitySub, user.getUserId(), user.getUserName() );
        customerQualitySub.setItemType( "CL" );
        customerQualitySub.setClaimGubunSub(qualityClaimSell.getClaimGubun());
        customerQualitySub.setClaimGubunSubName(qualityClaimSell.getClaimGubunName());
        customerQualitySub.setContents(qualityClaimSell.getContent());
        customerQualitySub.setCounselDateSub(qualityClaimSell.getCounselDate());
        customerQualitySub.setCounselorSub(qualityClaimSell.getCounselor());
        customerQualitySub.setFactorySub(qualityClaimSell.getFactory());
        customerQualitySub.setFactorySubName(qualityClaimSell.getFactoryName());
        customerQualitySub.setItemId(Integer.toString(qualityClaimSell.getSeqNum()));
        customerQualitySub.setJijongSub(qualityClaimSell.getJijong());
        customerQualitySub.setWeightSub(qualityClaimSell.getWeight());
        customerQualitySub.setSubjectSub(qualityClaimSell.getSubject());
        customerQualitySub.setMasterSub("Y");
        this.customerQualityClaimSellMoreService.createQualityClaimSellMore( customerQualitySub );

        return "redirect:/support/customer/customerQualityClaimSell/detailQualityClaimSell.do?seqNum=" + qualityClaimSellId;
    }
    
    
    /**
     * 품질/클레임 수정화면 불러오기 
     * @return
     */
    @RequestMapping( value = "/modifyQualityClaimSellView.do")
    public ModelAndView  modifyQualityClaimSellView (@RequestParam( "seqNum" )int seqNum)throws JsonGenerationException,
            JsonMappingException, IOException{
        
        QualityClaimSellHistory qualityClaimSell = customerQualityClaimSellService.getQualityClaimSell( seqNum );
        
        if(qualityClaimSell != null){
            if(qualityClaimSell.getFactory().equals( "1" )){
                qualityClaimSell.setFactory( "진주" );
            }else if(qualityClaimSell.getFactory().equals( "2" )){
                qualityClaimSell.setFactory( "울산" );
            }else if(qualityClaimSell.getFactory().equals( "3" )){
                qualityClaimSell.setFactory( "대구" );
            }
            
            if(qualityClaimSell.getClaimGubun().equals( "1" )){
                qualityClaimSell.setClaimGubun( "A/S" );
            }else if(qualityClaimSell.getClaimGubun().equals( "2" )){
                qualityClaimSell.setClaimGubun( "B/S" );
            }else if(qualityClaimSell.getClaimGubun().equals( "3" )){
                qualityClaimSell.setClaimGubun( "컴플레인" );
            }
           
       }
        
        
        String selectedFactory = qualityClaimSell.getFactory();
        String selectedClaimGubun = qualityClaimSell.getClaimGubun();
        
        //첨부파일 가져오기
        ObjectMapper mapper = new ObjectMapper();
        String fileDataListJson = mapper.writeValueAsString(qualityClaimSell.getFileDataList());
        
        
        ModelAndView mav = new ModelAndView().addObject( "qualityClaimSell", qualityClaimSell );
        mav.addObject("fileDataListJson", fileDataListJson)
           .addObject( "selectedFactory", selectedFactory )
           .addObject( "selectedClaimGubun",selectedClaimGubun );
        
        return mav;
    }
    
    /**
     * 품질/클레임 수정 처리 프로세스
     * @return
     */
    @RequestMapping( value = "/modifyQualityClaimSell.do")
    public String modifyQualityClaimSell (@RequestParam("seqNum")int seqNum,
            QualityClaimSellHistory qualityClaimSell, HttpServletRequest request)throws JsonGenerationException, JsonMappingException, IOException{
        
        User user = (User)getRequestAttribute( "ikep.user" );
        
        MultipartRequest multipartRequest = (MultipartRequest)request;
        List<MultipartFile> fileList = multipartRequest.getFiles( "file" );
        
        //관리자 확인
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        
      
       customerQualityClaimSellService.updateQualityClaimSell(qualityClaimSell,fileList,user,seqNum);
        
       CustomerQualitySub customerQualitySub = new CustomerQualitySub();
       customerQualitySub.setItemType( "CL" );
       customerQualitySub.setContents(qualityClaimSell.getContent());
       customerQualitySub.setCounselDateSub(qualityClaimSell.getCounselDate());
       customerQualitySub.setCounselorSub(qualityClaimSell.getCounselor());
       customerQualitySub.setJijongSub(qualityClaimSell.getJijong());
       customerQualitySub.setFactorySub(qualityClaimSell.getFactory());
       customerQualitySub.setFactorySubName(qualityClaimSell.getFactoryName());
       customerQualitySub.setClaimGubunSubName(qualityClaimSell.getClaimGubunName());
       customerQualitySub.setClaimGubunSub(qualityClaimSell.getClaimGubun());
       customerQualitySub.setWeightSub(qualityClaimSell.getWeight());
       customerQualitySub.setSubjectSub(qualityClaimSell.getSubject());
       customerQualitySub.setItemId(Integer.toString(qualityClaimSell.getSeqNum()));
       ModelBeanUtil.bindRegisterInfo( customerQualitySub, user.getUserId(), user.getUserName() );
       this.customerQualityClaimSellMoreService.updateCustomerQualityClaimSellMaster(customerQualitySub);
        
        return "redirect:/support/customer/customerQualityClaimSell/detailQualityClaimSell.do?seqNum="+seqNum;
    }
    
    /**
     * 품질/클레임 삭제
     *
     */
    @RequestMapping(value = "/deleteQualityClaimSell.do")
    public String deleteQualityClaimSell(@RequestParam("seqNum")int seqNum, @RequestParam("registerId")String registerId){
        
        User user = (User)getRequestAttribute( "ikep.user" );
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        QualityClaimSellHistory qualityClaimSell = new QualityClaimSellHistory();
        qualityClaimSell.setSeqNum( seqNum );
        qualityClaimSell.setUpdaterId( user.getUserId() );
        qualityClaimSell.setUpdaterName( user.getUserName() );
        
        
        if(isAdmin || user.getUserId().equals( registerId) ){
            customerQualityClaimSellService.deleteQualityClaimSell(qualityClaimSell);
        }
        
        return "redirect:/support/customer/customerQualityClaimSell/qualityClaimSellList.do";
        
        
    }
    

}
