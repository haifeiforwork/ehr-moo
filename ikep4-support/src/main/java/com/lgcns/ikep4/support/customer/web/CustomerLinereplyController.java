
package com.lgcns.ikep4.support.customer.web;

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
import com.lgcns.ikep4.support.customer.model.BondsIssueHistory;
import com.lgcns.ikep4.support.customer.model.CounselHistory;
import com.lgcns.ikep4.support.customer.model.CustomerLinereply;
import com.lgcns.ikep4.support.customer.model.QualityClaimHistory;
import com.lgcns.ikep4.support.customer.model.QualityClaimSellHistory;
import com.lgcns.ikep4.support.customer.search.CustomerLinereplySearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerBondsIssueService;
import com.lgcns.ikep4.support.customer.service.CustomerCounselHistoryService;
import com.lgcns.ikep4.support.customer.service.CustomerLinereplyService;
import com.lgcns.ikep4.support.customer.service.CustomerQualityClaimSellService;
import com.lgcns.ikep4.support.customer.service.CustomerQualityClaimService;
import com.lgcns.ikep4.support.customer.service.CustomerService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

@Controller
@RequestMapping( value = "/support/customer/customerLinereply" )
public class CustomerLinereplyController extends BaseController {

    @Autowired
    private CustomerLinereplyService customerLinereplyService;

    @Autowired
    private CustomerQualityClaimService customerQualityClaimService;
    
    @Autowired
    private CustomerQualityClaimSellService customerQualityClaimSellService;

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private CustomerCounselHistoryService customerCounselHistoryService;
    
    @Autowired
    private CustomerBondsIssueService customerBondsIssueService;

    public static final int LINEREPLY_INDENTATION_LIMITE = 5;

    /**
     * 댓글 생성 비동기 컨트롤 메서드
     * @param customerLinereply
     * @param result
     * @param status
     */
    @RequestMapping( value = "/createLinereply.do" )
    public @ResponseBody
    void createLinereply( CustomerLinereply customerLinereply, BindingResult result, SessionStatus status
            ,@RequestParam("itemType")String itemType) {

        try {
            if ( result.hasErrors() ) {
                throw new IKEP4AjaxException( "", null );
            }

            User user = (User)this.getRequestAttribute( "ikep.user" );

            ModelBeanUtil.bindRegisterInfo( customerLinereply, user.getUserId(), user.getUserName() );
            customerLinereply.setItemType( itemType );
            this.customerLinereplyService.createLinereply( customerLinereply );

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
    @RequestMapping( value = "/listLinereplyView.do" )
    public ModelAndView listLinereplyView(CustomerLinereplySearchCondition searchCondition,
            BindingResult result, SessionStatus status ) {

        User user = (User)this.getRequestAttribute( "ikep.user" );
        Boolean isSystemAdmin = this.customerService.checkAdmin( user.getUserId() );
        Integer linereplyCount = 0;
        if(searchCondition.getItemType().equals( "QC" )){
            QualityClaimHistory qualityClaim = this.customerQualityClaimService.getQualityClaim( Integer.parseInt( searchCondition.getItemId() ) );
            linereplyCount = qualityClaim.getLinereplyCount();
        }else if(searchCondition.getItemType().equals( "CH" )){
            CounselHistory counselHistory = this.customerCounselHistoryService.getCounselHistory( Integer.parseInt( searchCondition.getItemId() ) );
            linereplyCount = counselHistory.getLinereplyCount();
        }else if(searchCondition.getItemType().equals( "BI" )){
            BondsIssueHistory bondsIssue = this.customerBondsIssueService.getBondsIssue( Integer.parseInt( searchCondition.getItemId() ) );
            linereplyCount = bondsIssue.getLinereplyCount();
        }else if(searchCondition.getItemType().equals( "CL" )){
        	QualityClaimSellHistory qualityClaimSell = this.customerQualityClaimSellService.getQualityClaimSell( Integer.parseInt( searchCondition.getItemId() ) );
            linereplyCount = qualityClaimSell.getLinereplyCount();
        }
        
        SearchResult<CustomerLinereply> searchResult = this.customerLinereplyService
                .getCustomerLinereply( searchCondition );
        

        return new ModelAndView().addObject( "searchResult", searchResult ).addObject( "user", user )
                .addObject( "linereplyCount", linereplyCount )
                .addObject( "searchCondition", searchResult.getSearchCondition() )
                .addObject( "isSystemAdmin", isSystemAdmin ).addObject( "itemType",searchCondition.getItemType() );

    }
    
    
    /**
     * 댓글의 댓글 생성 컨트롤 메서드
     * @param customerLinereply 생성대상 댓글의 댓글 모델 객체
     * @param result 바인딩결과
     * @param status 세션상태
     */
    @RequestMapping(value="/createReplyLinereply.do")
    public @ResponseBody void createReplyLinereply(CustomerLinereply customerLinereply, BindingResult result, SessionStatus status
            ,@RequestParam("itemTypeTemp")String itemType){
        
        try{
            
            if(result.hasErrors()){
                throw new IKEP4AjaxException( "", null );
            }
            User user = (User) this.getRequestAttribute( "ikep.user" );
            
            ModelBeanUtil.bindRegisterInfo( customerLinereply, user.getUserId(), user.getUserName() );
            customerLinereply.setItemType( itemType );
            this.customerLinereplyService.createReplyLinereply(customerLinereply);
       
            
        }catch( Exception e){
            throw new IKEP4AjaxException( "code", e );
        }finally{
            status.setComplete();
        }
        
    }
    
    /**
     * 댓글 수정 비동기 컨트롤 메서드
     * @param customerLinereply
     * @param result
     * @param status
     */
    @RequestMapping(value="/updateLinereply.do")
    public @ResponseBody void updateLinereply (CustomerLinereply customerLinereply, BindingResult result, SessionStatus status){
        
        try {
            if (result.hasErrors()) {
                throw new IKEP4AjaxException("", null);
            }
            CustomerLinereply readCustomerLinereply = this.customerLinereplyService.readCustomerLinereply(customerLinereply.getLinereplyId());

            readCustomerLinereply.setContents(customerLinereply.getContents());

            User user = (User)this.getRequestAttribute("ikep.user");

            ModelBeanUtil.bindRegisterInfo(customerLinereply, user.getUserId(), user.getUserName());

            this.customerLinereplyService.updateCustomerLinereply(readCustomerLinereply);

        } catch(Exception exception) {
            throw new IKEP4AjaxException("code", exception);

        } finally {
            status.setComplete();
        }
        
    }
    
    /**
     * 댓글 작성자 모드 삭제 비동기 컨트롤 메서드
     * @param customerLinereply
     * @param result
     * @param status
     */
    @RequestMapping(value="/userDeleteLinereply.do")
    public @ResponseBody void userDeleteLinereply(CustomerLinereply customerLinereply, BindingResult result, SessionStatus status){
        try {
            if (result.hasErrors()) {
                throw new IKEP4AjaxException("", null);
            }
            User user = (User)this.getRequestAttribute("ikep.user");

            ModelBeanUtil.bindRegisterInfo(customerLinereply, user.getUserId(), user.getUserName());

            CustomerLinereply LinereplyTmp = this.customerLinereplyService.readCustomerLinereply( customerLinereply.getLinereplyId() );
            
            
            // 사용자의 댓글인지 확인
            String registerId = LinereplyTmp.getRegisterId();
            if(user.getUserId().equals(registerId)) {
                this.customerLinereplyService.userDeleteLinereply(customerLinereply);                
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
    @RequestMapping(value="/adminDeleteLinereply.do")
    public @ResponseBody void adminDeleteLinereply (@RequestParam("itemId")String itemId,
            @RequestParam("linereplyId") String linereplyId,@RequestParam("itemType")String itemType){
        try {
            User user = (User)this.getRequestAttribute("ikep.user");
            Boolean isSystemAdmin = this.customerService.checkAdmin( user.getUserId() );
            if(isSystemAdmin) {
                this.customerLinereplyService.adminDeleteLinereply(itemId, linereplyId, itemType);
            }

        } catch(Exception exception) {
            throw new IKEP4AjaxException("code", exception);

        }
    }
    

}
