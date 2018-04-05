
package com.lgcns.ikep4.portal.moorimess.web;

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
import com.lgcns.ikep4.portal.moorimess.model.OldRecordHistory;
import com.lgcns.ikep4.portal.moorimess.search.OldRecordSearchCondition;
import com.lgcns.ikep4.portal.moorimess.service.OldRecordService;
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
@RequestMapping( value = "/portal/moorimess/oldRecord" )
public class OldRecordController extends BaseController {

    @Autowired
    private OldRecordService oldRecordService;
    
    @Autowired
    private UserDao userDao;

    /**
     * 채권 관련 이슈 상담내역 리스트 불러오기 
     * @return
     */
    @RequestMapping( value = "/oldRecordList.do" )
    public ModelAndView oldRecordList( OldRecordSearchCondition searchCondition,
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

        SearchResult<OldRecordHistory> searchResult = this.oldRecordService
                .listOldRecordBySearchCondition( searchCondition );
        
        boolean accrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "ACL13");
		int accRole = userDao.getUserRoleCheck(map);
		if(accRole > 0){
			accrole = true;
		}

		
        ModelAndView modelAndView = new ModelAndView().addObject( "searchResult", searchResult )
                .addObject( "searchCondition", searchResult.getSearchCondition() )
                .addObject( "searchConditionString", tempSearchConditionString )
                .addObject( "boardCode", new BoardCode() )
                .addObject( "accrole", accrole );

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
    @RequestMapping( value = "/detailOldRecord.do" )
    public ModelAndView detailOldRecord( @RequestParam( "seqNum" )
    int itemId ) throws JsonGenerationException, JsonMappingException, IOException {

        User user = (User) getRequestAttribute("ikep.user");
        
        
        OldRecordHistory oldRecordHistory = oldRecordService.getOldRecord( itemId );
        
        
        

        //첨부파일 가져오기
        ObjectMapper mapper = new ObjectMapper();
        String fileDataListJson = mapper.writeValueAsString(oldRecordHistory.getFileDataList());
        
        boolean accrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "ACL13");
		int accRole = userDao.getUserRoleCheck(map);
		if(accRole > 0){
			accrole = true;
		}
        
        ModelAndView modelAndView = new ModelAndView().addObject( "oldRecordHistory", oldRecordHistory )
        .addObject("fileDataListJson", fileDataListJson)
        .addObject( "accrole", accrole );
        
        return modelAndView;
    }
    

    /**
     * 채권 관련 이슈 등록 화면 불러오기
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @RequestMapping( value = "/createOldRecordView.do" )
    public ModelAndView createOldRecordView() throws JsonGenerationException, JsonMappingException, IOException {
    	
        OldRecordHistory oldRecord = new OldRecordHistory();
        

        return this.bindResult( new ModelAndView().addObject( "oldRecord", oldRecord ) );

    }

    /**
     * 채권 관련 이슈 등록 프로세스 
     * @param oldRecord
     * @param request
     * @return
     */
    @RequestMapping( value = "/createOldRecord.do" )
    public String createOldRecord( OldRecordHistory oldRecord, HttpServletRequest request ) {

        User user = (User)getRequestAttribute( "ikep.user" );

        MultipartRequest multipartRequest = (MultipartRequest)request;
        List<MultipartFile> fileList = multipartRequest.getFiles( "file" );

        oldRecord.setDeleteFlag( "N" );
        oldRecord.setRegisterId( user.getUserId() );
        oldRecord.setUpdaterId( user.getUserId() );

        int oldRecordId = oldRecordService.createOldRecord( oldRecord, user, fileList );

        return "redirect:/portal/moorimess/oldRecord/detailOldRecord.do?seqNum=" + oldRecordId;
    }
    
    
    /**
     * 채권 관련 이슈 수정화면 불러오기 
     * @return
     */
    @RequestMapping( value = "/modifyOldRecordView.do")
    public ModelAndView  modifyOldRecordView (@RequestParam( "seqNum" )int seqNum)throws JsonGenerationException,
            JsonMappingException, IOException{
        
        OldRecordHistory oldRecord = oldRecordService.getOldRecord( seqNum );
        
        
        //첨부파일 가져오기
        ObjectMapper mapper = new ObjectMapper();
        String fileDataListJson = mapper.writeValueAsString(oldRecord.getFileDataList());
        
        
        ModelAndView mav = new ModelAndView().addObject( "oldRecord", oldRecord );
        mav.addObject("fileDataListJson", fileDataListJson);
        
        return mav;
    }
    
    /**
     * 채권 관련 이슈 수정 처리 프로세스
     * @return
     */
    @RequestMapping( value = "/modifyOldRecord.do")
    public String modifyOldRecord (@RequestParam("seqNum")int seqNum,
            OldRecordHistory oldRecord, HttpServletRequest request)throws JsonGenerationException, JsonMappingException, IOException{
        
        User user = (User)getRequestAttribute( "ikep.user" );
        
        MultipartRequest multipartRequest = (MultipartRequest)request;
        List<MultipartFile> fileList = multipartRequest.getFiles( "file" );
        
        
        
      
       oldRecordService.updateOldRecord(oldRecord,fileList,user,seqNum);
       
        return "redirect:/portal/moorimess/oldRecord/detailOldRecord.do?seqNum="+seqNum;
    }
    
    /**
     * 채권 관련 이슈 삭제
     *
     */
    @RequestMapping(value = "/deleteOldRecord.do")
    public String deleteOldRecord(@RequestParam("seqNum")int seqNum, @RequestParam("registerId")String registerId){
        
        User user = (User)getRequestAttribute( "ikep.user" );
        
        OldRecordHistory oldRecord = new OldRecordHistory();
        oldRecord.setSeqNum( seqNum );
        oldRecord.setUpdaterId( user.getUserId() );
        oldRecord.setUpdaterName( user.getUserName() );
        
        
        if(user.getUserId().equals( registerId) ){
            oldRecordService.deleteOldRecord(oldRecord);
        }
        
        return "redirect:/portal/moorimess/oldRecord/oldRecordList.do";
        
        
    }
    

}
