package com.lgcns.ikep4.support.customer.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.model.BasicInfo;
import com.lgcns.ikep4.support.customer.model.Realty;
import com.lgcns.ikep4.support.customer.search.RealtySearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerBasicInfoService;
import com.lgcns.ikep4.support.customer.service.CustomerRealtyService;
import com.lgcns.ikep4.support.customer.service.CustomerService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.excel.ExcelUtil;
import com.lgcns.ikep4.util.http.HttpUtil;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

/**
 * 
 * 고객별 상담내역 컨트롤러
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Controller
@RequestMapping(value="/support/customer/customerRealty")
public class CustomerRealtyController extends BaseController{

    @Autowired
    CustomerRealtyService realtyService;
    
    @Autowired
    CustomerService customerService;
    
    @Autowired
    CustomerBasicInfoService customerBasicInfoService;
    
    /**
     * 고객별 상담내역 리스트 
     *
     */
    @RequestMapping(value="/realtyList.do")
    public ModelAndView realtyList(RealtySearchCondition searchCondition,
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

        SearchResult<Realty> searchResult = this.realtyService.listRealtyBySearchCondition( searchCondition );
                

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
    @RequestMapping(value="/detailRealty.do")
    public ModelAndView detailRealty (@RequestParam("seqNum")int itemId) 
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
        
        
        Realty realty = realtyService.getRealty( itemId);
        
        BasicInfo info = new BasicInfo();
        String tmptemId = Integer.toString( itemId );
        info.setId(tmptemId);
        info.setDivCode("RT");
        info.setRegisterId(user.getUserId());
        customerBasicInfoService.updateHitCount(info);
        
       
       ModelAndView modelAndView = new ModelAndView().addObject( "realty", realty )
       .addObject( "isAdmin", isAdmin );
       
       return modelAndView;
       
        
    }
    
    @RequestMapping(value="/detailRealtyPrint.do")
    public ModelAndView detailRealtyPring (@RequestParam("seqNum")int itemId) 
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
        
        
        Realty realty = realtyService.getRealty( itemId);
        
        
       
       ModelAndView modelAndView = new ModelAndView().addObject( "realty", realty )
       .addObject( "isAdmin", isAdmin );
       
       return modelAndView;
       
        
    }
    /**
     * 등록 화면 불러오기 
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value="/createRealtyView.do")
    public ModelAndView createRealtyView() throws JsonGenerationException, JsonMappingException,IOException{
       
        Realty realty = new Realty();
        return this.bindResult( new ModelAndView().addObject( "realty", realty ) );
        
    }
    
    @RequestMapping("/realtyExcelForm")
	public ModelAndView excelForm(HttpServletRequest req) {

		// 더블 서밋방지 Token 셋팅
		String token = HttpUtil.setToken(req);

		return this.bindResult( new ModelAndView().addObject("token", token));
	}
    
    @RequestMapping(value = "/downloadExcelRealty.do")
	public void downloadExcelCareer( HttpServletResponse response) {
		
		List<Realty> realtyList = realtyService.selectRealty();
		
		String fileName = "고객_목록_" + DateUtil.getTodayDateTime("yyyyMMdd")+ ".xlsx";

		if( realtyList.size() > 0 ) {

			List<Object> excelDownloadList = new ArrayList<Object>();
			for (Realty excelRealty : realtyList) {

				excelDownloadList.add(excelRealty);
			}

			LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();

			titleMap.put("customerName", "customerName");
			titleMap.put("customerId", "customerId");

			// 파일 저장
			ExcelUtil.saveExcel(titleMap, excelDownloadList, fileName, response, "고객_목록_" + DateUtil.getTodayDateTime("yyyyMMdd"));
			
		}

	}	
    
    public void readExcelMod(String className, InputStream inp, int sheetIndex, User user) {

		try {

			// Excel 파일 열기
			Workbook workbook = WorkbookFactory.create(inp);

			Sheet sheet = workbook.getSheetAt(sheetIndex);

			int rownum = 0;
			int column = 0;

			// 타이틀 정보 읽기(타이틀은 객체의필드명과 동일하여야 함)
			Map titleMap = new HashMap();
			Map<String, String> description = new HashMap<String, String>();
			if (sheet.getRow(0) != null) {

				Row row = sheet.getRow(0);
				column = 0;
				for (Cell cell : row) {
					titleMap.put(column, cell.getStringCellValue());
					description.put(cell.getStringCellValue(), "java.lang.String");
					column++;
				}
			}
			
			// 객체 필드 타입 정보 저장
			Map<String, String> typeMap = description;
			String tmpcustomerId = "";
			String tmpcustomerName = "";
			String tmpno = "";  
			String tmpdivision = ""; 
			String tmpaddress = "";  
			String tmpowner = "";    
			String tmprelation = ""; 
			String tmppyeongSpace = "";   
			String tmppyeongBuilding = "";
			String tmpmeterSpace = "";    
			String tmpmeterBuilding = ""; 
			String tmpmeterSum = ""; 
			String tmptotalSum = ""; 
			String tmprightDate = "";
			String tmpcollateral = "";    
			String tmprightSum = ""; 
			String tmpdebt = "";
			String tmpnote = "";
			String chkcustomerId = "";
			String chkcustomerName = "";
			int chkCnt = 0;

			// Excel Row 정보를, 객체 리스트 정보에 담기
			for (Row row : sheet) {
				try {
					if (rownum > 0){// && row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK) {
						chkCnt++;

						// Excel Cell 정보를, 객체 정보에 담기

						for (int col = 0; col < titleMap.size(); col++) {

							try {
								Cell cell = row.getCell(col);
								if (typeMap.get(titleMap.get(col)) != null) {
									if(chkcustomerId == tmpcustomerId || tmpcustomerId == "" || chkcustomerId == ""){
										if(col == 0){
											tmpcustomerName = cell.getStringCellValue();
										}
										else if(col == 1){
											tmpcustomerId = cell.getStringCellValue();
										}
										else if(col == 2){
											if(tmpno == ""){
												if(cell == null){
													tmpno = "";
												}else{
													tmpno = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpno = tmpno+"|";
												}else{
													tmpno = tmpno+"|"+cell.getStringCellValue();
												}
												
											}
										}else if(col == 3){
											if(tmpdivision == ""){
												if(cell == null){
													tmpdivision = "";
												}else{
													tmpdivision = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpdivision = tmpdivision+"|";
												}else{
													tmpdivision = tmpdivision+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 4){
											if(tmpaddress == ""){
												if(cell == null){
													tmpaddress = "";
												}else{
													tmpaddress = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpaddress = tmpaddress+"|";
												}else{
													tmpaddress = tmpaddress+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 5){
											if(tmpowner == ""){
												if(cell == null){
													tmpowner = "";
												}else{
													tmpowner = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpowner = tmpowner+"|";
												}else{
													tmpowner = tmpowner+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 6){
											if(tmprelation == ""){
												if(cell == null){
													tmprelation = "";
												}else{
													tmprelation = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmprelation = tmprelation+"|";
												}else{
													tmprelation = tmprelation+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 7){
											if(tmppyeongSpace == ""){
												if(cell == null){
													tmppyeongSpace = "";
												}else{
													tmppyeongSpace = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmppyeongSpace = tmppyeongSpace+"|";
												}else{
													tmppyeongSpace = tmppyeongSpace+"|"+cell.getStringCellValue();
												}
												
											}
										}else if(col == 8){
											if(tmppyeongBuilding == ""){
												if(cell == null){
													tmppyeongBuilding = "";
												}else{
													tmppyeongBuilding = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmppyeongBuilding = tmppyeongBuilding+"|";
												}else{
													tmppyeongBuilding = tmppyeongBuilding+"|"+cell.getStringCellValue();
												}
												
											}
										}else if(col == 9){
											if(tmpmeterSpace == ""){
												if(cell == null){
													tmpmeterSpace = "";
												}else{
													tmpmeterSpace = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpmeterSpace = tmpmeterSpace+"|";
												}else{
													tmpmeterSpace = tmpmeterSpace+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 10){
											if(tmpmeterBuilding == ""){
												if(cell == null){
													tmpmeterBuilding = "";
												}else{
													tmpmeterBuilding = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpmeterBuilding = tmpmeterBuilding+"|";
												}else{
													tmpmeterBuilding = tmpmeterBuilding+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 11){
											if(tmpmeterSum == ""){
												if(cell == null){
													tmpmeterSum = "";
												}else{
													tmpmeterSum = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpmeterSum = tmpmeterSum+"|";
												}else{
													tmpmeterSum = tmpmeterSum+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 12){
											if(tmptotalSum == ""){
												if(cell == null){
													tmptotalSum = "";
												}else{
													tmptotalSum = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmptotalSum = tmptotalSum+"|";
												}else{
													tmptotalSum = tmptotalSum+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 13){
											if(tmprightDate == ""){
												if(cell == null){
													tmprightDate = "";
												}else{
													tmprightDate = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmprightDate = tmprightDate+"|";
												}else{
													tmprightDate = tmprightDate+"|"+cell.getStringCellValue();
												}
												
											}
										}else if(col == 14){
											if(tmpcollateral == ""){
												if(cell == null){
													tmpcollateral = "";
												}else{
													tmpcollateral = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpcollateral = tmpcollateral+"|";
												}else{
													tmpcollateral = tmpcollateral+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 15){
											if(tmprightSum == ""){
												if(cell == null){
													tmprightSum = "";
												}else{
													tmprightSum = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmprightSum = tmprightSum+"|";
												}else{
													tmprightSum = tmprightSum+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 16){
											if(tmpdebt == ""){
												if(cell == null){
													tmpdebt = "";
												}else{
													tmpdebt = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpdebt = tmpdebt+"|";
												}else{
													tmpdebt = tmpdebt+"|"+cell.getStringCellValue();
												}
												
											}
										}else{
											if(tmpnote == ""){
												if(cell == null){
													tmpnote = "";
												}else{
													tmpnote = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpnote = tmpnote+"|";
												}else{
													tmpnote = tmpnote+"|"+cell.getStringCellValue();
												}
											}
										}
										if(sheet.getLastRowNum() == chkCnt && titleMap.size()-1 == col){
											Realty realty = new Realty();
											realty.setRegisterId( user.getUserId() );
									        realty.setRegisterName( user.getUserName() );
									        realty.setUpdaterId( user.getUserId() );
									        realty.setUpdaterName( user.getUserName() );
									        realty.setCustomerId(tmpcustomerId);
									        realty.setCustomerName(tmpcustomerName);
									        realty.setNo(tmpno);  
									        realty.setDivision(tmpdivision); 
									        realty.setAddress(tmpaddress);  
									        realty.setOwner(tmpowner);    
									        realty.setRelation(tmprelation); 
									        realty.setPyeongSpace(tmppyeongSpace);   
									        realty.setPyeongBuilding(tmppyeongBuilding);
									        realty.setMeterSpace(tmpmeterSpace);    
									        realty.setMeterBuilding(tmpmeterBuilding); 
									        realty.setMeterSum(tmpmeterSum); 
									        realty.setTotalSum(tmptotalSum); 
									        realty.setRightDate(tmprightDate);
									        realty.setCollateral(tmpcollateral);    
									        realty.setRightSum(tmprightSum); 
									        realty.setDebt(tmpdebt);
									        realty.setNote(tmpnote);
									        
									        int realtyId = realtyService.createRealty(realty, user, null);
										}
									}else{
										Realty realty = new Realty();
										realty.setRegisterId( user.getUserId() );
								        realty.setRegisterName( user.getUserName() );
								        realty.setUpdaterId( user.getUserId() );
								        realty.setUpdaterName( user.getUserName() );
								        realty.setCustomerId(chkcustomerId);
								        realty.setCustomerName(chkcustomerName);
								        realty.setNo(tmpno);  
								        realty.setDivision(tmpdivision); 
								        realty.setAddress(tmpaddress);  
								        realty.setOwner(tmpowner);    
								        realty.setRelation(tmprelation); 
								        realty.setPyeongSpace(tmppyeongSpace);   
								        realty.setPyeongBuilding(tmppyeongBuilding);
								        realty.setMeterSpace(tmpmeterSpace);    
								        realty.setMeterBuilding(tmpmeterBuilding); 
								        realty.setMeterSum(tmpmeterSum); 
								        realty.setTotalSum(tmptotalSum); 
								        realty.setRightDate(tmprightDate);
								        realty.setCollateral(tmpcollateral);    
								        realty.setRightSum(tmprightSum); 
								        realty.setDebt(tmpdebt);
								        realty.setNote(tmpnote);
								        
								        int realtyId = realtyService.createRealty(realty, user, null);
								        
								        tmpno = "";  
								        tmpdivision = ""; 
								        tmpaddress = "";  
								        tmpowner = "";    
								        tmprelation = ""; 
								        tmppyeongSpace = "";   
								        tmppyeongBuilding = "";
								        tmpmeterSpace = "";    
								        tmpmeterBuilding = ""; 
								        tmpmeterSum = ""; 
								        tmptotalSum = ""; 
								        tmprightDate = "";
								        tmpcollateral = "";    
								        tmprightSum = ""; 
								        tmpdebt = "";
								        tmpnote = "";
								        chkcustomerId = tmpcustomerId;
										chkcustomerName = tmpcustomerName;
								        
								        if(col == 0){
											tmpcustomerName = cell.getStringCellValue();
										}
										else if(col == 1){
											tmpcustomerId = cell.getStringCellValue();
										}
										else if(col == 2){
											if(tmpno == ""){
												if(cell == null){
													tmpno = "";
												}else{
													tmpno = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpno = tmpno+"|";
												}else{
													tmpno = tmpno+"|"+cell.getStringCellValue();
												}
												
											}
										}else if(col == 3){
											if(tmpdivision == ""){
												if(cell == null){
													tmpdivision = "";
												}else{
													tmpdivision = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpdivision = tmpdivision+"|";
												}else{
													tmpdivision = tmpdivision+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 4){
											if(tmpaddress == ""){
												if(cell == null){
													tmpaddress = "";
												}else{
													tmpaddress = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpaddress = tmpaddress+"|";
												}else{
													tmpaddress = tmpaddress+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 5){
											if(tmpowner == ""){
												if(cell == null){
													tmpowner = "";
												}else{
													tmpowner = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpowner = tmpowner+"|";
												}else{
													tmpowner = tmpowner+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 6){
											if(tmprelation == ""){
												if(cell == null){
													tmprelation = "";
												}else{
													tmprelation = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmprelation = tmprelation+"|";
												}else{
													tmprelation = tmprelation+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 7){
											if(tmppyeongSpace == ""){
												if(cell == null){
													tmppyeongSpace = "";
												}else{
													tmppyeongSpace = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmppyeongSpace = tmppyeongSpace+"|";
												}else{
													tmppyeongSpace = tmppyeongSpace+"|"+cell.getStringCellValue();
												}
												
											}
										}else if(col == 8){
											if(tmppyeongBuilding == ""){
												if(cell == null){
													tmppyeongBuilding = "";
												}else{
													tmppyeongBuilding = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmppyeongBuilding = tmppyeongBuilding+"|";
												}else{
													tmppyeongBuilding = tmppyeongBuilding+"|"+cell.getStringCellValue();
												}
												
											}
										}else if(col == 9){
											if(tmpmeterSpace == ""){
												if(cell == null){
													tmpmeterSpace = "";
												}else{
													tmpmeterSpace = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpmeterSpace = tmpmeterSpace+"|";
												}else{
													tmpmeterSpace = tmpmeterSpace+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 10){
											if(tmpmeterBuilding == ""){
												if(cell == null){
													tmpmeterBuilding = "";
												}else{
													tmpmeterBuilding = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpmeterBuilding = tmpmeterBuilding+"|";
												}else{
													tmpmeterBuilding = tmpmeterBuilding+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 11){
											if(tmpmeterSum == ""){
												if(cell == null){
													tmpmeterSum = "";
												}else{
													tmpmeterSum = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpmeterSum = tmpmeterSum+"|";
												}else{
													tmpmeterSum = tmpmeterSum+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 12){
											if(tmptotalSum == ""){
												if(cell == null){
													tmptotalSum = "";
												}else{
													tmptotalSum = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmptotalSum = tmptotalSum+"|";
												}else{
													tmptotalSum = tmptotalSum+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 13){
											if(tmprightDate == ""){
												if(cell == null){
													tmprightDate = "";
												}else{
													tmprightDate = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmprightDate = tmprightDate+"|";
												}else{
													tmprightDate = tmprightDate+"|"+cell.getStringCellValue();
												}
												
											}
										}else if(col == 14){
											if(tmpcollateral == ""){
												if(cell == null){
													tmpcollateral = "";
												}else{
													tmpcollateral = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpcollateral = tmpcollateral+"|";
												}else{
													tmpcollateral = tmpcollateral+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 15){
											if(tmprightSum == ""){
												if(cell == null){
													tmprightSum = "";
												}else{
													tmprightSum = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmprightSum = tmprightSum+"|";
												}else{
													tmprightSum = tmprightSum+"|"+cell.getStringCellValue();
												}
											}
										}else if(col == 16){
											if(tmpdebt == ""){
												if(cell == null){
													tmpdebt = "";
												}else{
													tmpdebt = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpdebt = tmpdebt+"|";
												}else{
													tmpdebt = tmpdebt+"|"+cell.getStringCellValue();
												}
												
											}
										}else{
											if(tmpnote == ""){
												if(cell == null){
													tmpnote = "";
												}else{
													tmpnote = cell.getStringCellValue();
												}
											}else{
												if(cell == null){
													tmpnote = tmpnote+"|";
												}else{
													tmpnote = tmpnote+"|"+cell.getStringCellValue();
												}
											}
										}
									}
									
									
								}
							} catch (Exception e) {
								// ex.printStackTrace();
							}

						}

						// Excel에서 읽은 정보를, 해당 객체에 저장함
					}
					chkcustomerId = tmpcustomerId;
					chkcustomerName = tmpcustomerName;
					rownum++;

				} catch (Exception e) {
					// ex.printStackTrace();
				}
			}

			inp.close();

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		// 객체 리스트 반환
	}
    
    @RequestMapping("/realtyExcelUpload")
	public ModelAndView excelUpload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest req) {



		ModelAndView mav = new ModelAndView("support/customer/realtyExcelResult");

		try {
			// 더블 서밋방지 Token 체크
			if (HttpUtil.isValidToken(req)) {

				InputStream inp = file.getInputStream();

				User userSession = (User) getRequestAttribute( "ikep.user" );
				
				String className = "com.lgcns.ikep4.support.customer.model.Realty";

				readExcelMod(className, inp, 1, userSession);

				int successCount = 0;
				int failCount = 0;

				mav.addObject("successCount", successCount);
				mav.addObject("failCount", failCount);

				// Token 초기화
				String token = HttpUtil.setToken(req);
				mav.addObject("token", token);
			}

		} catch (Exception e) {
			mav.addObject("totalCount", 0);
			return mav;
		}

		return mav;
	}
    
    /**
     * 등록 프로세스 
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value="/createRealty.do")
    public String createRealty(Realty realty, HttpServletRequest request)
    {
        
        User user =(User)getRequestAttribute( "ikep.user");
        
        MultipartRequest multipartRequest = (MultipartRequest)request;
        List<MultipartFile> fileList = multipartRequest.getFiles( "file" );
        
        //realty.setCustomerId( customerId );
        //realty.setDeleteFlag( "N" );
        realty.setRegisterId( user.getUserId() );
        realty.setRegisterName( user.getUserName() );
        realty.setUpdaterId( user.getUserId() );
        realty.setUpdaterName( user.getUserName() );
        //realty.setLinereplyCount( 0 );
        
        int realtyId = realtyService.createRealty(realty, user, fileList);
        
        
        return "redirect:/support/customer/customerRealty/detailRealty.do?seqNum=" + realtyId;
    }
    
    
    /**
     * 고객별 상담내역 수정화면 불러오기 
     * @param seqNum
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value="/modifyRealtyView.do")
    public ModelAndView modifyRealtyView (@RequestParam("seqNum")int seqNum )throws JsonGenerationException,
    JsonMappingException, IOException{
        
        Realty realty = realtyService.getRealty( seqNum );
        
        
        ModelAndView mav = new ModelAndView().addObject( "realty", realty );
        
        
        return mav;
    }
    
    /**
     * 고객별 상담내역 수정 프로세스
     * @param seqNum
     * @param realty
     * @param request
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping(value="/modifyRealty.do")
    public String modifyRealty(@RequestParam("seqNum")int seqNum,
            Realty realty, HttpServletRequest request)throws JsonGenerationException,
            JsonMappingException,IOException{
        
        
        User user = (User)getRequestAttribute( "ikep.user" );
      
        
        MultipartRequest multipartRequest = (MultipartRequest)request;
        List<MultipartFile> fileList = multipartRequest.getFiles( "file" );
        
        //관리자 확인
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
       
        realtyService.updateRealty( realty, fileList, user, seqNum );        
        
        
        
        return "redirect:/support/customer/customerRealty/detailRealty.do?seqNum="+seqNum;
    }
    
    
    
    /**
     * 상담내역 삭제
     * @param seqNum
     * @param registerId
     * @return
     */
    @RequestMapping(value = "/deleteRealty.do")
    public String deleteRealty(@RequestParam("seqNum")int seqNum, @RequestParam("registerId")String registerId){
        
        User user = (User)getRequestAttribute( "ikep.user" );
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        Realty realty  = new Realty();
        realty.setSeqNum( seqNum );
        realty.setUpdaterId( user.getUserId() );
        realty.setUpdaterName( user.getUserName() );
        
        if(isAdmin || user.getUserId().equals( registerId )){
        
            realtyService.deleteRealty(realty);
        }
        
        
        return "redirect:/support/customer/customerRealty/realtyList.do";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
