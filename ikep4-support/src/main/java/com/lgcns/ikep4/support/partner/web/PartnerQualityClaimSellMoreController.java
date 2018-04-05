
package com.lgcns.ikep4.support.partner.web;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.partner.model.PartnerClaimSellHistory;
import com.lgcns.ikep4.support.partner.model.PartnerQualitySub;
import com.lgcns.ikep4.support.partner.search.PartnerQualityClaimSellMoreSearchCondition;
import com.lgcns.ikep4.support.partner.service.PartnerQualityClaimSellMoreService;
import com.lgcns.ikep4.support.partner.service.PartnerQualityClaimSellService;
import com.lgcns.ikep4.support.partner.service.PartnerService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

@Controller
@RequestMapping( value = "/support/partner/partnerQualityClaimSellMore" )
public class PartnerQualityClaimSellMoreController extends BaseController {

    @Autowired
    private PartnerQualityClaimSellMoreService partnerQualityClaimSellMoreService;

    @Autowired
    private PartnerQualityClaimSellService partnerQualityClaimSellService;

    @Autowired
    private PartnerService partnerService;
    

    private Properties prop = null;		
	private String serverIp = null;
	private String serverPort = null;

    
    @Autowired
    private UserDao userDao;

    public static final int LINEREPLY_INDENTATION_LIMITE = 5;

    /**
     * 댓글 생성 비동기 컨트롤 메서드
     * @param partnerQualityClaimSellMore
     * @param result
     * @param status
     */
    @RequestMapping( value = "/createQualityClaimSellMore.do" )
    public @ResponseBody
    void createQualityClaimSellMore( PartnerQualitySub partnerQualitySub, BindingResult result, SessionStatus status
            ,@RequestParam("itemType")String itemType) {

        try {
            if ( result.hasErrors() ) {
                throw new IKEP4AjaxException( "", null );
            }

            User user = (User)this.getRequestAttribute( "ikep.user" );

            ModelBeanUtil.bindRegisterInfo( partnerQualitySub, user.getUserId(), user.getUserName() );
            partnerQualitySub.setItemType( itemType );
            partnerQualitySub.setPartnerName(partnerQualitySub.getSubPartnerName());
            this.partnerQualityClaimSellMoreService.createQualityClaimSellMore( partnerQualitySub );
            
            partnerQualityClaimSellMoreService.sendMail(user, partnerQualitySub);

        } catch ( Exception exception ) {
            throw new IKEP4AjaxException( "code", exception );
        } finally {
            status.setComplete();
        }

    }

    /**
     * 댓글 목록 조회 화면 컨트롤 메서드
     * @param itemType  고객정보 메뉴타입
     * @param itemId    게시물 아이디
     * @param result    바인딩결과
     * @param status    세션상태
     * @return ModelAndView
     */
    @RequestMapping( value = "/listQualityClaimSellMoreView.do" )
    public ModelAndView listQualityClaimSellMoreView(PartnerQualityClaimSellMoreSearchCondition searchCondition,
            BindingResult result, SessionStatus status 
            ,@RequestParam(value = "directSubId", required = false) String directSubId) {

        User user = (User)this.getRequestAttribute( "ikep.user" );
        Boolean isSystemAdmin = this.partnerService.checkAdmin( user.getUserId() );
        Integer linereplyCount = 0;
        String subPartnerName = "";
        /*if(searchCondition.getItemType().equals( "CL" )){
            QualityClaimSellHistory qualityClaimSell = this.partnerQualityClaimSellService.getQualityClaimSell( Integer
                .parseInt( searchCondition.getItemId() ) );
            linereplyCount = qualityClaimSell.getSubCount();
        }*/
        
        SearchResult<PartnerQualitySub> searchResult = this.partnerQualityClaimSellMoreService.getPartnerQualityClaimSellMore( searchCondition );
        if(searchResult.getEntity() != null){
        	linereplyCount = searchResult.getEntity().size();
        	
        }
        
        PartnerClaimSellHistory qualityClaimSellHistory = partnerQualityClaimSellService.getQualityClaimSell(Integer.parseInt(searchCondition.getItemId()));
        subPartnerName = qualityClaimSellHistory.getPartnerName();
       /* boolean clrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "ACL12");
		int claimRole = userDao.getUserRoleCheck(map);
		if(claimRole > 0){
			clrole = true;
		}*/
        
        return new ModelAndView().addObject( "searchResult", searchResult ).addObject( "user", user ).addObject( "linereplyCount", linereplyCount ).addObject( "searchCondition", searchResult.getSearchCondition() ).addObject( "isSystemAdmin", isSystemAdmin ).addObject( "itemType",searchCondition.getItemType() ).addObject( "directSubId", directSubId ).addObject( "subPartnerName", subPartnerName );

    }
    
    
    /**
     * 댓글의 댓글 생성 컨트롤 메서드
     * @param partnerQualityClaimSellMore 생성대상 댓글의 댓글 모델 객체
     * @param result 바인딩결과
     * @param status 세션상태
     */
    @RequestMapping(value="/createReplyQualityClaimSellMore.do")
    public @ResponseBody void createReplyQualityClaimSellMore(PartnerQualitySub partnerQualitySub, BindingResult result, SessionStatus status
            ,@RequestParam("itemTypeTemp")String itemType){
        
        try{
            
            if(result.hasErrors()){
                throw new IKEP4AjaxException( "", null );
            }
            User user = (User) this.getRequestAttribute( "ikep.user" );
            
            ModelBeanUtil.bindRegisterInfo( partnerQualitySub, user.getUserId(), user.getUserName() );
            partnerQualitySub.setItemType( itemType );
            this.partnerQualityClaimSellMoreService.createReplyQualityClaimSellMore(partnerQualitySub);
       
            //partnerQualityClaimSellMoreService.sendMail(user, partnerQualitySub);
        }catch( Exception e){
            throw new IKEP4AjaxException( "code", e );
        }finally{
            status.setComplete();
        }
        
    }
    
    /**
     * 댓글 수정 비동기 컨트롤 메서드
     * @param partnerQualityClaimSellMore
     * @param result
     * @param status
     */
    @RequestMapping(value="/updateQualityClaimSellMore.do")
    public @ResponseBody void updateQualityClaimSellMore (PartnerQualitySub partnerQualitySub, BindingResult result, SessionStatus status){
        
        try {
            if (result.hasErrors()) {
                throw new IKEP4AjaxException("", null);
            }
            /*PartnerQualitySub readPartnerQualityClaimSellMore = this.partnerQualityClaimSellMoreService.readPartnerQualityClaimSellMore(partnerQualitySub.getQualityClaimSellMoreId());

            readPartnerQualityClaimSellMore.setContents(partnerQualitySub.getContents());
            readPartnerQualityClaimSellMore.setCounselDateSub(partnerQualitySub.getMcounselDateSub());
            readPartnerQualityClaimSellMore.setCounselorSub(partnerQualitySub.getCounselorSub());
            readPartnerQualityClaimSellMore.setJijongSub(partnerQualitySub.getJijongSub());
            readPartnerQualityClaimSellMore.setFactorySub(partnerQualitySub.getMfactorySub());
            readPartnerQualityClaimSellMore.setFactorySubName(partnerQualitySub.getMfactorySubName());
            readPartnerQualityClaimSellMore.setClaimGubunSubName(partnerQualitySub.getMclaimGubunSubName());
            readPartnerQualityClaimSellMore.setClaimGubunSub(partnerQualitySub.getMclaimGubunSub());
            readPartnerQualityClaimSellMore.setWeightSub(partnerQualitySub.getWeightSub());
            readPartnerQualityClaimSellMore.setSubjectSub(partnerQualitySub.getSubjectSub());*/

            User user = (User)this.getRequestAttribute("ikep.user");

            ModelBeanUtil.bindRegisterInfo(partnerQualitySub, user.getUserId(), user.getUserName());
            partnerQualitySub.setCounselDate(partnerQualitySub.getMcounselDateSub());
            this.partnerQualityClaimSellMoreService.updatePartnerQualityClaimSellMore(partnerQualitySub);
            
            
            /*if(readPartnerQualityClaimSellMore.getMasterSub().equals("Y")){
            	PartnerClaimSellHistory qualityClaimSellHistory = new PartnerClaimSellHistory();
            	qualityClaimSellHistory.setContent(readPartnerQualityClaimSellMore.getContents());
            	qualityClaimSellHistory.setCounselDate(readPartnerQualityClaimSellMore.getCounselDateSub());
            	qualityClaimSellHistory.setCounselor(readPartnerQualityClaimSellMore.getCounselorSub());
            	qualityClaimSellHistory.setJijong(readPartnerQualityClaimSellMore.getJijongSub());
            	qualityClaimSellHistory.setFactory(readPartnerQualityClaimSellMore.getFactorySub());
            	qualityClaimSellHistory.setFactoryName(readPartnerQualityClaimSellMore.getFactorySubName());
            	qualityClaimSellHistory.setClaimGubunName(readPartnerQualityClaimSellMore.getClaimGubunSubName());
            	qualityClaimSellHistory.setClaimGubun(readPartnerQualityClaimSellMore.getClaimGubunSub());
            	qualityClaimSellHistory.setWeight(readPartnerQualityClaimSellMore.getWeightSub());
            	qualityClaimSellHistory.setSubject(readPartnerQualityClaimSellMore.getSubjectSub());
            	partnerQualityClaimSellService.updateQualityClaimSellMaster(qualityClaimSellHistory,user,Integer.parseInt(readPartnerQualityClaimSellMore.getItemId()));
            }*/
            
            
            /*Properties commonprop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
            String baseUrl =commonprop.getProperty("ikep4.baseUrl");
			String url = "";
			url = baseUrl+"/support/partner/partnerQualityClaimSell/directDetailQualityClaimSell.do?seqNum="+readPartnerQualityClaimSellMore.getItemId()+"&linereplyId="+readPartnerQualityClaimSellMore.getLinereplyId()+"&suserId=";
			
			prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
			serverIp = prop.getProperty("messenger.server.ip");
			serverPort = prop.getProperty("messenger.server.port");
			AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));
			
			String title 	= " 협력사 상담내역이 등록되었습니다.";
			String contents = "[제목: "+readPartnerQualityClaimSellMore.getSubjectSub()+"]<br>[상담자 : "+readPartnerQualityClaimSellMore.getCounselorSub()+"]<br>[내용 : "+readPartnerQualityClaimSellMore.getContents()+"]";
			url += "cnssys";//하드코딩!!!!!!!!!!!!
			//메시지 보내기
			atmc.addMessage("cnssys", user.getUserName(), contents.toString(), url, title);//하드코딩!!!!!!!!!!!!
			atmc.send();*/
        } catch(Exception exception) {
            throw new IKEP4AjaxException("code", exception);

        } finally {
            status.setComplete();
        }
        
    }
    
    
    @RequestMapping(value="/tlcommentQualityClaimSellMore.do")
    public @ResponseBody void tlcommentQualityClaimSellMore (PartnerQualitySub partnerQualitySub, BindingResult result, SessionStatus status){
        
        try {
            if (result.hasErrors()) {
                throw new IKEP4AjaxException("", null);
            }
            User user = (User) this.getRequestAttribute( "ikep.user" );
            PartnerQualitySub readPartnerQualityClaimSellMore = new PartnerQualitySub();
            readPartnerQualityClaimSellMore.setLinereplyId(partnerQualitySub.getLinereplyId());
            readPartnerQualityClaimSellMore.setComment1(partnerQualitySub.getComment1());
            readPartnerQualityClaimSellMore.setCommentRegisterId1(user.getUserId());

            this.partnerQualityClaimSellMoreService.updatePartnerTlComment(readPartnerQualityClaimSellMore);
            
            partnerQualityClaimSellMoreService.sendCommentMail(user, partnerQualitySub);
            
            
        } catch(Exception exception) {
            throw new IKEP4AjaxException("code", exception);

        } finally {
            status.setComplete();
        }
        
    }
    
    @RequestMapping(value="/ofcommentQualityClaimSellMore.do")
    public @ResponseBody void ofcommentQualityClaimSellMore (PartnerQualitySub partnerQualitySub, BindingResult result, SessionStatus status){
        
        try {
            if (result.hasErrors()) {
                throw new IKEP4AjaxException("", null);
            }
            PartnerQualitySub readPartnerQualityClaimSellMore = new PartnerQualitySub();
            User user = (User)this.getRequestAttribute("ikep.user");
            readPartnerQualityClaimSellMore.setLinereplyId(partnerQualitySub.getLinereplyId());
            readPartnerQualityClaimSellMore.setComment2(partnerQualitySub.getComment2());
            readPartnerQualityClaimSellMore.setCommentRegisterId2(user.getUserId());

            ModelBeanUtil.bindRegisterInfo(partnerQualitySub, user.getUserId(), user.getUserName());

            this.partnerQualityClaimSellMoreService.updatePartnerOfComment(readPartnerQualityClaimSellMore);
            
            partnerQualityClaimSellMoreService.sendCommentMail(user, partnerQualitySub);
            
            partnerQualityClaimSellMoreService.sendCommentMailSub(user, partnerQualitySub);
            
            
        } catch(Exception exception) {
            throw new IKEP4AjaxException("code", exception);

        } finally {
            status.setComplete();
        }
        
    }
    
    /**
     * 댓글 작성자 모드 삭제 비동기 컨트롤 메서드
     * @param partnerQualityClaimSellMore
     * @param result
     * @param status
     */
    @RequestMapping(value="/userDeleteQualityClaimSellMore.do")
    public @ResponseBody void userDeleteQualityClaimSellMore(PartnerQualitySub partnerQualitySub, BindingResult result, SessionStatus status){
        try {
            if (result.hasErrors()) {
                throw new IKEP4AjaxException("", null);
            }
            User user = (User)this.getRequestAttribute("ikep.user");

            ModelBeanUtil.bindRegisterInfo(partnerQualitySub, user.getUserId(), user.getUserName());

            PartnerQualitySub QualityClaimSellMoreTmp = this.partnerQualityClaimSellMoreService.readPartnerQualityClaimSellMore( partnerQualitySub.getQualityClaimSellMoreId() );
            
            
            // 사용자의 댓글인지 확인
            String registerId = QualityClaimSellMoreTmp.getRegisterId();
            if(user.getUserId().equals(registerId)) {
                this.partnerQualityClaimSellMoreService.userDeleteQualityClaimSellMore(partnerQualitySub);                
            }

        } catch(Exception exception) {
            throw new IKEP4AjaxException("code", exception);

        }
        
    }
    /**
     * 관리자 댓글 삭제 프로세스 
     * @param itemId
     * @param linereplyId
     * @param itemType
     */
    @RequestMapping(value="/adminDeleteQualityClaimSellMore.do")
    public @ResponseBody void adminDeleteQualityClaimSellMore (@RequestParam("itemId")String itemId,
            @RequestParam("linereplyId") String linereplyId,@RequestParam("itemType")String itemType){
        try {
            User user = (User)this.getRequestAttribute("ikep.user");
            Boolean isSystemAdmin = this.partnerService.checkAdmin( user.getUserId() );
            if(isSystemAdmin) {
                this.partnerQualityClaimSellMoreService.adminDeleteQualityClaimSellMore(itemId, linereplyId, itemType);
            }

        } catch(Exception exception) {
            throw new IKEP4AjaxException("code", exception);

        }
    }
    

}
