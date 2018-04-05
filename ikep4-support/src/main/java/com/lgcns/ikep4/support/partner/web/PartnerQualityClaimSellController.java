
package com.lgcns.ikep4.support.partner.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.dao.CustomerBasicInfoDao;
import com.lgcns.ikep4.support.customer.model.BasicInfo;
import com.lgcns.ikep4.support.customer.search.BasicInfoSearchCondition;
import com.lgcns.ikep4.support.partner.model.Partner;
import com.lgcns.ikep4.support.partner.model.PartnerClaimSellHistory;
import com.lgcns.ikep4.support.partner.model.PartnerManInfoItem;
import com.lgcns.ikep4.support.partner.model.PartnerQualitySub;
import com.lgcns.ikep4.support.partner.search.PartnerManInfoItemSearchCondition;
import com.lgcns.ikep4.support.partner.search.PartnerQualityClaimSellSearchCondition;
import com.lgcns.ikep4.support.partner.service.PartnerBasicInfoService;
import com.lgcns.ikep4.support.partner.service.PartnerQualityClaimSellMoreService;
import com.lgcns.ikep4.support.partner.service.PartnerQualityClaimSellService;
import com.lgcns.ikep4.support.partner.service.PartnerService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.support.user.userTheme.model.UserTheme;
import com.lgcns.ikep4.support.user.userTheme.service.UserThemeService;
import com.lgcns.ikep4.util.excel.ExcelModelConstants;
import com.lgcns.ikep4.util.excel.ExcelViewModel;
import com.lgcns.ikep4.util.jco.EpVendorDataRcvRFC;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.lgcns.ikep4.util.model.VendorDetail;

/**
 * 
 * 고객정보 품질/클레임 상담이력 컨트롤러
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Controller
@RequestMapping( value = "/support/partner/partnerQualityClaimSell" )
public class PartnerQualityClaimSellController extends BaseController {

	@Autowired
    private PartnerQualityClaimSellMoreService partnerQualityClaimSellMoreService;
    
    @Autowired
    private PartnerQualityClaimSellService partnerQualityClaimSellService;
    
    @Autowired
    PartnerBasicInfoService partnerBasicInfoService;
    
    @Autowired
    private PartnerService partnerService;

    @Autowired
    private UserDao userDao;
    
    @Autowired
	private UserService userService;
    
    @Autowired
	private UserThemeService userThemeService;
    
    @Autowired
	private EpVendorDataRcvRFC epVendorRcv;
    
    @Autowired
    private CustomerBasicInfoDao customerBasicInfoDao;
	
    /**
     * 품질/클레임 상담내역 리스트 불러오기 
     * @return
     */
    @RequestMapping( value = "/qualityClaimSellList.do" )
    public ModelAndView qualityClaimSellList( PartnerQualityClaimSellSearchCondition searchCondition,
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
        List<String> tempRolePurposeList = new ArrayList<String>();
        boolean crRole1 = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "CR01");
		int tempCrRole1 = userDao.getUserRoleCheck(map);
		if(tempCrRole1 > 0){
			crRole1 = true;
			tempRolePurposeList.add("CR01");
		}
		
		boolean crRole2 = false;
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("userId", user.getUserId());
		map2.put("roleName", "CR02");
		int tempCrRole2 = userDao.getUserRoleCheck(map2);
		if(tempCrRole2 > 0){
			crRole2 = true;
			tempRolePurposeList.add("CR02");
		}
		
		boolean crRole3 = false;
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("userId", user.getUserId());
		map3.put("roleName", "CR03");
		int tempCrRole3 = userDao.getUserRoleCheck(map3);
		if(tempCrRole3 > 0){
			crRole3 = true;
			tempRolePurposeList.add("CR03");
		}
		
		boolean crRole4 = false;
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("userId", user.getUserId());
		map4.put("roleName", "CR04");
		int tempCrRole4 = userDao.getUserRoleCheck(map4);
		if(tempCrRole4 > 0){
			crRole4 = true;
			tempRolePurposeList.add("CR04");
		}
		
		boolean crRole5 = false;
		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("userId", user.getUserId());
		map5.put("roleName", "CR05");
		int tempCrRole5 = userDao.getUserRoleCheck(map5);
		if(tempCrRole5 > 0){
			crRole5 = true;
			tempRolePurposeList.add("CR05");
		}
		
		boolean crRole6 = false;
		Map<String, String> map6 = new HashMap<String, String>();
		map6.put("userId", user.getUserId());
		map6.put("roleName", "CR06");
		int tempCrRole6 = userDao.getUserRoleCheck(map6);
		if(tempCrRole6 > 0){
			crRole6 = true;
			tempRolePurposeList.add("CR06");
		}
		
		boolean crRole7 = false;
		Map<String, String> map7 = new HashMap<String, String>();
		map7.put("userId", user.getUserId());
		map7.put("roleName", "CR07");
		int tempCrRole7 = userDao.getUserRoleCheck(map7);
		if(tempCrRole7 > 0){
			crRole7 = true;
			tempRolePurposeList.add("CR07");
		}
		searchCondition.setRolePurposeList(tempRolePurposeList);

        SearchResult<PartnerClaimSellHistory> searchResult = this.partnerQualityClaimSellService
                .listQualityClaimSellBySearchCondition( searchCondition );

        ModelAndView modelAndView = new ModelAndView().addObject( "searchResult", searchResult )
                .addObject( "searchCondition", searchResult.getSearchCondition() )
                .addObject( "searchConditionString", tempSearchConditionString )
                .addObject( "crRole1", crRole1 )
                .addObject( "crRole2", crRole2 )
                .addObject( "crRole3", crRole3 )
                .addObject( "crRole4", crRole4 )
                .addObject( "crRole5", crRole5 )
                .addObject( "crRole6", crRole6 )
                .addObject( "crRole7", crRole7 )
                .addObject( "nowYear", DateUtil.getToday("yyyy"))
			    .addObject( "nowMonth", DateUtil.getToday("MM"))
                .addObject( "boardCode", new BoardCode() );

        return modelAndView;
    }
    
    @RequestMapping(value = "excelQualityClaimSellList.do")
	public ModelAndView excelQualityClaimSellList(PartnerQualityClaimSellSearchCondition searchCondition, HttpServletRequest request) {
    	
    	List<PartnerClaimSellHistory> list = partnerQualityClaimSellService.crStatisticsList(searchCondition);
    	ExcelViewModel excel = new ExcelViewModel();  
    	try {	
    		String fileName = "Contact_Report_"+ DateUtil.getTodayDateTime("yyyyMM")+ ".xls";

    		
    		if( list.size() > 0 ) {
    			
    			excel.setFileName(fileName);   
    		    excel.setSheetName("Sheet");   
    		    
    		    excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
    		    excel.addColumn("ID", "userId", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_CENTRE); 
    		    excel.addColumn("성명", "userName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_CENTRE); 
    		    excel.addColumn("부서", "teamName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_CENTRE); 
    		    excel.addColumn("직급", "jobTitleName", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_CENTRE); 
    		    excel.addColumn("등록수", "regCnt", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_RIGHT); 
    		    excel.addColumn("상담수", "regSubCnt", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_RIGHT); 
    		    excel.addColumn("팀장코멘트", "comCnt1", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_RIGHT); 
    		    excel.addColumn("임원코멘트", "comCnt2", 20,ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_RIGHT); 
    		    		    
    		    excel.setDataList(list);			
    		}
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel); 
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
        boolean isAdmin = partnerService.checkAdmin( user.getUserId() );
        
        /*if(!isAdmin){
            
            boolean result = partnerService.checkAccess(user.getUserId());
            
            if(!result){
                throw new IKEP4AuthorizedException();
            }
        }*/
        
        
        PartnerClaimSellHistory qualityClaimSellHistory = partnerQualityClaimSellService.getQualityClaimSell( itemId );
        
        
        /*if(qualityClaimSellHistory != null){
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
           
       }*/
        
        /*boolean clrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "ACL12");
		int claimRole = userDao.getUserRoleCheck(map);
		if(claimRole > 0){
			clrole = true;
		}*/

        //첨부파일 가져오기
        ObjectMapper mapper = new ObjectMapper();
        String fileDataListJson = mapper.writeValueAsString(qualityClaimSellHistory.getFileDataList());
        
        /*Partner info = new Partner();
        String tmptemId = Integer.toString( itemId );
        info.setId(tmptemId);
        info.setDivCode("CL");
        info.setRegisterId(user.getUserId());
        partnerBasicInfoService.updateHitCount(info);*/
        
        ModelAndView modelAndView = new ModelAndView().addObject( "qualityClaimSellHistory", qualityClaimSellHistory )
        .addObject("fileDataListJson", fileDataListJson).addObject( "isAdmin", isAdmin );
        //.addObject( "clrole", clrole );
        return modelAndView;
    }
    
    
    @RequestMapping( value = "/directDetailQualityClaimSell.do" )
    public ModelAndView directDetailQualityClaimSell( @RequestParam( "seqNum" ) int itemId,
    @RequestParam(value = "linereplyId", defaultValue = "false", required = false) String linereplyId,
    HttpServletRequest request
    ) throws JsonGenerationException, JsonMappingException, IOException {

		
    	User user = (User) getRequestAttribute("ikep.user");
        
        //로그인한 사용자가 admin인지 확인
        boolean isAdmin = partnerService.checkAdmin( user.getUserId() );
        
        /*if(!isAdmin){
            
            boolean result = partnerService.checkAccess(user.getUserId());
            
            if(!result){
                throw new IKEP4AuthorizedException();
            }
        }*/
        
        
        PartnerClaimSellHistory qualityClaimSellHistory = partnerQualityClaimSellService.getQualityClaimSell( itemId );
        
        

        //첨부파일 가져오기
        ObjectMapper mapper = new ObjectMapper();
        String fileDataListJson = mapper.writeValueAsString(qualityClaimSellHistory.getFileDataList());
        
        Partner info = new Partner();
        String tmptemId = Integer.toString( itemId );
        info.setId(tmptemId);
        info.setDivCode("CL");
        info.setRegisterId(user.getUserId());
        partnerBasicInfoService.updateHitCount(info);
        
        qualityClaimSellHistory.setDirectSubId(linereplyId);
        
        ModelAndView modelAndView = new ModelAndView().addObject( "qualityClaimSellHistory", qualityClaimSellHistory )
        .addObject("fileDataListJson", fileDataListJson).addObject( "isAdmin", isAdmin );
        return modelAndView;
    }
    
    @RequestMapping( value = "/detailQualityClaimSellPrint.do" )
    public ModelAndView detailQualityClaimSellPrint( @RequestParam( "seqNum" )
    int itemId ) throws JsonGenerationException, JsonMappingException, IOException {

        User user = (User) getRequestAttribute("ikep.user");
        
        //로그인한 사용자가 admin인지 확인
        boolean isAdmin = partnerService.checkAdmin( user.getUserId() );
        
        /*if(!isAdmin){
            
            boolean result = partnerService.checkAccess(user.getUserId());
            
            if(!result){
                throw new IKEP4AuthorizedException();
            }
        }*/
        
        
        PartnerClaimSellHistory qualityClaimSellHistory = partnerQualityClaimSellService.getQualityClaimSell( itemId );
        
        
        SearchResult<PartnerQualitySub> searchResult = this.partnerQualityClaimSellMoreService.getPartnerQualityClaimSellMorePrint( itemId );

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

        PartnerClaimSellHistory qualityClaimSell = new PartnerClaimSellHistory();
        User user = (User) getRequestAttribute("ikep.user");
        boolean crRole1 = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "CR01");
		int tempCrRole1 = userDao.getUserRoleCheck(map);
		if(tempCrRole1 > 0){
			crRole1 = true;
		}
		
		boolean crRole2 = false;
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("userId", user.getUserId());
		map2.put("roleName", "CR02");
		int tempCrRole2 = userDao.getUserRoleCheck(map2);
		if(tempCrRole2 > 0){
			crRole2 = true;
		}
		
		boolean crRole3 = false;
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("userId", user.getUserId());
		map3.put("roleName", "CR03");
		int tempCrRole3 = userDao.getUserRoleCheck(map3);
		if(tempCrRole3 > 0){
			crRole3 = true;
		}
		
		boolean crRole4 = false;
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("userId", user.getUserId());
		map4.put("roleName", "CR04");
		int tempCrRole4 = userDao.getUserRoleCheck(map4);
		if(tempCrRole4 > 0){
			crRole4 = true;
		}
		
		boolean crRole5 = false;
		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("userId", user.getUserId());
		map5.put("roleName", "CR05");
		int tempCrRole5 = userDao.getUserRoleCheck(map5);
		if(tempCrRole5 > 0){
			crRole5 = true;
		}
		
		boolean crRole6 = false;
		Map<String, String> map6 = new HashMap<String, String>();
		map6.put("userId", user.getUserId());
		map6.put("roleName", "CR06");
		int tempCrRole6 = userDao.getUserRoleCheck(map6);
		if(tempCrRole6 > 0){
			crRole6 = true;
		}
		
		boolean crRole7 = false;
		Map<String, String> map7 = new HashMap<String, String>();
		map7.put("userId", user.getUserId());
		map7.put("roleName", "CR07");
		int tempCrRole7 = userDao.getUserRoleCheck(map7);
		if(tempCrRole7 > 0){
			crRole7 = true;
		}

        return this.bindResult( new ModelAndView()
        		.addObject( "crRole1", crRole1 )
                .addObject( "crRole2", crRole2 )
                .addObject( "crRole3", crRole3 )
                .addObject( "crRole4", crRole4 )
                .addObject( "crRole5", crRole5 )
                .addObject( "crRole6", crRole6 )
                .addObject( "crRole7", crRole7 )
        		.addObject( "qualityClaimSell", qualityClaimSell ) );

    }

    /**
     * 품질/클레임 등록 프로세스 
     * @param qualityClaimSell
     * @param request
     * @return
     */
    @RequestMapping( value = "/createQualityClaimSell.do" )
    public String createQualityClaimSell( PartnerClaimSellHistory qualityClaimSell, HttpServletRequest request ) {

        User user = (User)getRequestAttribute( "ikep.user" );

        MultipartRequest multipartRequest = (MultipartRequest)request;
        List<MultipartFile> fileList = multipartRequest.getFiles( "file" );

        qualityClaimSell.setDeleteFlag( "N" );
        qualityClaimSell.setRegisterId( user.getUserId() );
        qualityClaimSell.setUpdaterId( user.getUserId() );

        int qualityClaimSellId = partnerQualityClaimSellService.createQualityClaimSell( qualityClaimSell, user, fileList );
        
        PartnerQualitySub partnerQualitySub = new PartnerQualitySub();
        ModelBeanUtil.bindRegisterInfo( partnerQualitySub, user.getUserId(), user.getUserName() );
        partnerQualitySub.setItemType( "CL" );
        partnerQualitySub.setCounselContents(qualityClaimSell.getCounselContent());
        partnerQualitySub.setCounselDate(qualityClaimSell.getCounselDate());
        partnerQualitySub.setCounselor(qualityClaimSell.getCounselor());
        partnerQualitySub.setRequestor(qualityClaimSell.getRequestor());
        partnerQualitySub.setCounselType(qualityClaimSell.getCounselType());
        partnerQualitySub.setCounselTitle(qualityClaimSell.getCounselTitle());
        partnerQualitySub.setItemId(Integer.toString(qualityClaimSell.getSeqNum()));
        partnerQualityClaimSellMoreService.createQualityClaimSellMore( partnerQualitySub );
        partnerQualitySub.setPartnerName(qualityClaimSell.getPartnerName());
        if(!StringUtils.isEmpty(qualityClaimSell.getCounselTitle())){
        	partnerQualityClaimSellMoreService.sendMail(user, partnerQualitySub);
        }

        return "redirect:/support/partner/partnerQualityClaimSell/detailQualityClaimSell.do?seqNum=" + qualityClaimSellId;
    }
    
    @RequestMapping(value="/popupSAPSync.do")
    public ModelAndView popupSAPSync (PartnerQualityClaimSellSearchCondition searchCondition, 
            @RequestParam(value="name",required=false)String sendName){
        
    	PartnerManInfoItemSearchCondition searchCondition1 = new PartnerManInfoItemSearchCondition();
    	searchCondition1.setSearchColumn("name");
    	searchCondition1.setSearchWord(sendName);
    	List<PartnerManInfoItem> basicInfoList = partnerService.listItemBySearchCondition( searchCondition1 );
    	
    	List<VendorDetail> vendorList = epVendorRcv.EpVendorDataRcvRFC(sendName, null);
        return new ModelAndView()
        .addObject( "vendorList",vendorList )
        .addObject( "basicInfoList",basicInfoList )
        .addObject( "searchCondition", searchCondition );
        
    }
    @RequestMapping(value="/checkRegInfo.do")
    public @ResponseBody String checkRegInfo (PartnerClaimSellHistory qualityClaimSell){
    	boolean result = partnerService.checkRegInfo(qualityClaimSell);
    	
    	if(result){
    		return "success";
    	}else{
    		return "fail";
    	}
        
        
    }
    
    /**
     * 품질/클레임 수정화면 불러오기 
     * @return
     */
    @RequestMapping( value = "/modifyQualityClaimSellView.do")
    public ModelAndView  modifyQualityClaimSellView (@RequestParam( "seqNum" )int seqNum)throws JsonGenerationException,
            JsonMappingException, IOException{
    	User user = (User) getRequestAttribute("ikep.user");
        PartnerClaimSellHistory qualityClaimSell = partnerQualityClaimSellService.getQualityClaimSell( seqNum );
        
        boolean crRole1 = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "CR01");
		int tempCrRole1 = userDao.getUserRoleCheck(map);
		if(tempCrRole1 > 0){
			crRole1 = true;
		}
		
		boolean crRole2 = false;
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("userId", user.getUserId());
		map2.put("roleName", "CR02");
		int tempCrRole2 = userDao.getUserRoleCheck(map2);
		if(tempCrRole2 > 0){
			crRole2 = true;
		}
		
		boolean crRole3 = false;
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("userId", user.getUserId());
		map3.put("roleName", "CR03");
		int tempCrRole3 = userDao.getUserRoleCheck(map3);
		if(tempCrRole3 > 0){
			crRole3 = true;
		}
		
		boolean crRole4 = false;
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("userId", user.getUserId());
		map4.put("roleName", "CR04");
		int tempCrRole4 = userDao.getUserRoleCheck(map4);
		if(tempCrRole4 > 0){
			crRole4 = true;
		}
		
		boolean crRole5 = false;
		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("userId", user.getUserId());
		map5.put("roleName", "CR05");
		int tempCrRole5 = userDao.getUserRoleCheck(map5);
		if(tempCrRole5 > 0){
			crRole5 = true;
		}
		
		boolean crRole6 = false;
		Map<String, String> map6 = new HashMap<String, String>();
		map6.put("userId", user.getUserId());
		map6.put("roleName", "CR06");
		int tempCrRole6 = userDao.getUserRoleCheck(map6);
		if(tempCrRole6 > 0){
			crRole6 = true;
		}
		
		boolean crRole7 = false;
		Map<String, String> map7 = new HashMap<String, String>();
		map7.put("userId", user.getUserId());
		map7.put("roleName", "CR07");
		int tempCrRole7 = userDao.getUserRoleCheck(map7);
		if(tempCrRole7 > 0){
			crRole7 = true;
		}
        
        
        //첨부파일 가져오기
        ObjectMapper mapper = new ObjectMapper();
        String fileDataListJson = mapper.writeValueAsString(qualityClaimSell.getFileDataList());
        
        
        ModelAndView mav = new ModelAndView().addObject( "qualityClaimSell", qualityClaimSell );
        mav.addObject("fileDataListJson", fileDataListJson)
        .addObject( "crRole1", crRole1 )
                .addObject( "crRole2", crRole2 )
                .addObject( "crRole3", crRole3 )
                .addObject( "crRole4", crRole4 )
                .addObject( "crRole5", crRole5 )
                .addObject( "crRole6", crRole6 )
                .addObject( "crRole7", crRole7 );
        
        return mav;
    }
    
    /**
     * 품질/클레임 수정 처리 프로세스
     * @return
     */
    @RequestMapping( value = "/modifyQualityClaimSell.do")
    public String modifyQualityClaimSell (@RequestParam("seqNum")int seqNum,
            PartnerClaimSellHistory qualityClaimSell, HttpServletRequest request)throws JsonGenerationException, JsonMappingException, IOException{
        
        User user = (User)getRequestAttribute( "ikep.user" );
        
        MultipartRequest multipartRequest = (MultipartRequest)request;
        List<MultipartFile> fileList = multipartRequest.getFiles( "file" );
        
        //관리자 확인
        boolean isAdmin = partnerService.checkAdmin( user.getUserId() );
        
        
      
       partnerQualityClaimSellService.updateQualityClaimSell(qualityClaimSell,fileList,user,seqNum);
        
       /*PartnerQualitySub partnerQualitySub = new PartnerQualitySub();
       partnerQualitySub.setItemType( "CL" );
       partnerQualitySub.setContents(qualityClaimSell.getContent());
       partnerQualitySub.setCounselDateSub(qualityClaimSell.getCounselDate());
       partnerQualitySub.setCounselorSub(qualityClaimSell.getCounselor());
       partnerQualitySub.setJijongSub(qualityClaimSell.getJijong());
       partnerQualitySub.setFactorySub(qualityClaimSell.getFactory());
       partnerQualitySub.setFactorySubName(qualityClaimSell.getFactoryName());
       partnerQualitySub.setClaimGubunSubName(qualityClaimSell.getClaimGubunName());
       partnerQualitySub.setClaimGubunSub(qualityClaimSell.getClaimGubun());
       partnerQualitySub.setWeightSub(qualityClaimSell.getWeight());
       partnerQualitySub.setSubjectSub(qualityClaimSell.getSubject());
       partnerQualitySub.setItemId(Integer.toString(qualityClaimSell.getSeqNum()));
       ModelBeanUtil.bindRegisterInfo( partnerQualitySub, user.getUserId(), user.getUserName() );
       this.partnerQualityClaimSellMoreService.updatePartnerQualityClaimSellMaster(partnerQualitySub);*/
        
        return "redirect:/support/partner/partnerQualityClaimSell/detailQualityClaimSell.do?seqNum="+seqNum;
    }
    
    /**
     * 품질/클레임 삭제
     *
     */
    @RequestMapping(value = "/deleteQualityClaimSell.do")
    public String deleteQualityClaimSell(@RequestParam("seqNum")int seqNum, @RequestParam("registerId")String registerId){
        
        User user = (User)getRequestAttribute( "ikep.user" );
        boolean isAdmin = partnerService.checkAdmin( user.getUserId() );
        
        PartnerClaimSellHistory qualityClaimSell = new PartnerClaimSellHistory();
        qualityClaimSell.setSeqNum( seqNum );
        qualityClaimSell.setUpdaterId( user.getUserId() );
        qualityClaimSell.setUpdaterName( user.getUserName() );
        
        partnerQualityClaimSellService.deleteQualityClaimSell(qualityClaimSell);
        
        return "redirect:/support/partner/partnerQualityClaimSell/qualityClaimSellList.do";
        
        
    }
    
    private void authenticationSuccess(HttpServletRequest request, String username) {
		HttpSession httpsession = request.getSession(true);
		String portalId = (String) httpsession.getAttribute("ikep.portalId");

		// 사용자 정보 조회
		User user = userService.read(username);

		// 사용자 테마 정보 조회
		UserTheme userThemeCheck = userThemeService.readUserTheme(user.getUserId());
		UserTheme userTheme = null;

		if (userThemeCheck == null) {
			// 사용자 테마 정보 없으면 기본 테마정보 조회
			userTheme = userThemeService.readDefaultTheme(portalId);
		} else {
			userTheme = userThemeService.readTheme(userThemeCheck.getThemeId(), portalId);
		}

		user.setUserTheme(userTheme);
		
		//세션 타임아웃 설정
		long loginTime=System.currentTimeMillis();
		httpsession.setAttribute("ikep.loginTime", loginTime);

		httpsession.setAttribute("ikep.user", user);
		httpsession.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
				new Locale(user.getLocaleCode()));

	}
}
