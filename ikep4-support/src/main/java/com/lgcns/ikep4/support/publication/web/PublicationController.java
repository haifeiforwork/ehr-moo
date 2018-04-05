package com.lgcns.ikep4.support.publication.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.publication.model.Publication;
import com.lgcns.ikep4.support.publication.search.PublicationSearchCondition;
import com.lgcns.ikep4.support.publication.service.PublicationService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.excel.ExcelModelConstants;
import com.lgcns.ikep4.util.excel.ExcelUtil;
import com.lgcns.ikep4.util.excel.ExcelViewModel;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;



/**
 * 
 * 간행물 주소록 컨트롤러
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Controller
@RequestMapping(value="/support/publication/publication")
public class PublicationController extends BaseController {

    @Autowired
    PublicationService publicationService;
    
    

    /**
     * 고객정보 메뉴화면 불러오기 
     * @return
     */
    @RequestMapping( value = "/menuView.do" )
    public String menuView() {

        return null;
    }
    
    
    
    @RequestMapping(value="/publicationList.do")
    public ModelAndView PublicationList(PublicationSearchCondition searchCondition,
            @RequestParam(value="searchConditionString",required = false)String searchConditionString){
        
        //화면이 넘어갈때 마다 searchCondition 조건을 String으로 가져가기 위함
        String tempSearchConditionString = null;
        if ( StringUtils.isEmpty( searchConditionString ) ) {
            tempSearchConditionString = ModelBeanUtil.makeSearchConditionString( searchCondition,
                    "type","groupId","pageIndex",
                    "searchColumn", "searchWord", "sortColumn", "sortType" );
        } else {
            ModelBeanUtil.makeSearchCondition( searchConditionString, searchCondition );
            tempSearchConditionString = searchConditionString;
        }
        
        //페이징할 row수 설정 (초기 로딩시에는 기본값으로 설정한다)

        if ( searchCondition.getPagePerRecord().equals( "" ) ) {
            searchCondition.setPagePerRecord( searchCondition.getDefaultPagePerRecord() );

        }
        
        
        SearchResult<Publication> searchResult = this.publicationService.listPublicationBySearchCondition(searchCondition);
        
        
        ModelAndView modelAndView = new ModelAndView().addObject( "searchResult",searchResult )
        .addObject( "searchCondition", searchCondition )
        .addObject( "searchConditionString", tempSearchConditionString )
        .addObject("boardCode",new BoardCode());
        return modelAndView;
    }
    
    /**
     * 간행물 주소록 상세보기 및 등록, 수정 화면 출력
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyPublicationView.do")
    public ModelAndView ModifyPublicationView (@RequestParam(value = "id",defaultValue="false")String id){
        Publication publication = new Publication();
        
        if(id != null){
         publication = publicationService.getPublicationItem(id);
        }
        
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject( "publication", publication );
        return modelAndView;
    }
    
    
    
    @RequestMapping(value="/modifyPublication.do")
    public String ModifyPublication(Publication publication,HttpServletRequest request )throws JsonGenerationException ,
    JsonMappingException,IOException{
        
        User user = (User) getRequestAttribute("ikep.user");
        
        
        String tmpGroupId = publication.getGroupId();
        if(tmpGroupId.equals( "1" )){
            publication.setGroupName( "제지/지류유통" );            
        }else if(tmpGroupId.equals( "2" )){
            publication.setGroupName( "학교/오피니언" );
        }else if(tmpGroupId.equals( "3" )){
            publication.setGroupName( "디자인/개인" );
        }else if(tmpGroupId.equals( "4" )){
            publication.setGroupName( "퇴직사우" );
        }
        
        
        //새로 등록할 때
        if(publication.getId().equals( "" )){
            
            publication.setRegisterId( user.getUserId() );
            publication.setRegisterName( user.getUserName() );
            publication.setUpdaterId( user.getUserId() );
            publication.setUpdaterName( user.getUserName() );
            
            
            String id = publicationService.createPublication(publication);
            return "redirect:/support/publication/publication/modifyPublicationView.do?id="+id;
            
        }else{ // 수정 시
            
            publication.setUpdaterId( user.getUserId() );
            publication.setUpdaterName( user.getUserName() );
            
            publicationService.modifyPublication(publication);
            return "redirect:/support/publication/publication/modifyPublicationView.do?id="+publication.getId(); 
        }
        
    }
    
    /**
     * 간행물 주소록 삭제 프로세스
     * @param id
     * @return
     */
    @RequestMapping(value="/deletePublication.do") 
    public String  DeletePublication(@RequestParam(value = "id")String id){
        
        publicationService.delete( id );
        
        
        return "redirect:/support/publication/publication/publicationList.do";
        
    }
    
    /**
     * 엑셀 다운로드 
     * @param searchCondition
     * @param response
     */
    @RequestMapping(value="/downloadExcel.do")
    public ModelAndView downloadExcel(PublicationSearchCondition searchCondition,  HttpServletResponse response  ){
      
        ExcelViewModel excel = new ExcelViewModel();  
        
        if (searchCondition.getPagePerRecord() == null) {
            searchCondition.setPagePerRecord(10);
        }
        
        searchCondition.setPageIndex(1);
        
        List<Publication> listAllPublication = publicationService.allListPublication(searchCondition);

        
        String fileName = "PublicationAddr_" + DateUtil.getTodayDateTime( "yyyyMMdd" )+".xls";
        
        if( listAllPublication.size() > 0 ) {
            
            excel.setFileName( fileName );
            excel.setSheetName( "sheet" );
            excel.setTitle( "간행물 주소록_"+  DateUtil.getTodayDateTime( "yyyyMMdd" ));

            excel.setMaxRowLimitNewSheet(ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT); //ExcelModelConstants.EXCEL_ROW_MAX_LINE_LIMIT 기본은 이값
                    
            excel.addColumn( "번호","rowNum" ,20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT );
            excel.addColumn( "종류","type" ,20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT );
            excel.addColumn( "그룹","groupName" ,20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT );
            excel.addColumn( "고객사","companyName" ,20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT );
            excel.addColumn( "성명","name" ,20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT );
            excel.addColumn( "우편번호","zip1No" ,20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT );
            excel.addColumn( "주소","address" ,100, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT );
            excel.addColumn( "부수","count" ,20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT );
            excel.addColumn( "등록자","registerName" ,20, ExcelModelConstants.EXCEL_COLUMN_TYPE_STRING,"",ExcelModelConstants.ALIGN_LEFT );
            excel.addColumn( "등록일","registDate" ,20, ExcelModelConstants.EXCEL_COLUMN_TYPE_DATETIME,"",ExcelModelConstants.ALIGN_LEFT );

            excel.setDataList(listAllPublication);       
          
        }
        
        return new ModelAndView("defaultExcelView", ExcelModelConstants.EXCEL_VIEW_MODEL, excel);
    }
    
    
    
    
}
