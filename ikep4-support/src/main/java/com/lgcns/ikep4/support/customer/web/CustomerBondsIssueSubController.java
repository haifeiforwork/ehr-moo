
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
import com.lgcns.ikep4.support.customer.model.CustomerBondsIssueSub;
import com.lgcns.ikep4.support.customer.search.CustomerBondsIssueSubSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerBondsIssueService;
import com.lgcns.ikep4.support.customer.service.CustomerBondsIssueSubService;
import com.lgcns.ikep4.support.customer.service.CustomerCounselHistoryService;
import com.lgcns.ikep4.support.customer.service.CustomerService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

@Controller
@RequestMapping( value = "/support/customer/customerBondsIssueSub" )
public class CustomerBondsIssueSubController extends BaseController {

    @Autowired
    private CustomerBondsIssueSubService customerBondsIssueSubService;

    @Autowired
    private CustomerBondsIssueService customerBondsIssueService;

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private CustomerCounselHistoryService customerCounselHistoryService;
    

    public static final int LINEREPLY_INDENTATION_LIMITE = 5;

    /**
     * 댓글 생성 비동기 컨트롤 메서드
     * @param customerBondsIssueSub
     * @param result
     * @param status
     */
    @RequestMapping( value = "/createBondsIssueSub.do" )
    public @ResponseBody
    void createBondsIssueSub( CustomerBondsIssueSub customerBondsIssueSub, BindingResult result, SessionStatus status
            ,@RequestParam("itemType")String itemType) {

        try {
            if ( result.hasErrors() ) {
                throw new IKEP4AjaxException( "", null );
            }

            User user = (User)this.getRequestAttribute( "ikep.user" );

            ModelBeanUtil.bindRegisterInfo( customerBondsIssueSub, user.getUserId(), user.getUserName() );
            customerBondsIssueSub.setItemType( itemType );
            this.customerBondsIssueSubService.createBondsIssueSub( customerBondsIssueSub );

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
    @RequestMapping( value = "/listBondsIssueSubView.do" )
    public ModelAndView listBondsIssueSubView(CustomerBondsIssueSubSearchCondition searchCondition,
            BindingResult result, SessionStatus status ) {

        User user = (User)this.getRequestAttribute( "ikep.user" );
        Boolean isSystemAdmin = this.customerService.checkAdmin( user.getUserId() );
        Integer linereplyCount = 0;
        /*if(searchCondition.getItemType().equals( "BI" )){
            BondsIssueHistory bondsIssue = this.customerBondsIssueService.getBondsIssue( Integer
                .parseInt( searchCondition.getItemId() ) );
            linereplyCount = bondsIssue.getSubCount();
        }*/
        
        SearchResult<CustomerBondsIssueSub> searchResult = this.customerBondsIssueSubService
                .getCustomerBondsIssueSub( searchCondition );
        linereplyCount = searchResult.getEntity().size();

        return new ModelAndView().addObject( "searchResult", searchResult ).addObject( "user", user )
                .addObject( "linereplyCount", linereplyCount )
                .addObject( "searchCondition", searchResult.getSearchCondition() )
                .addObject( "isSystemAdmin", isSystemAdmin ).addObject( "itemType",searchCondition.getItemType() );

    }
    
    
    /**
     * 댓글의 댓글 생성 컨트롤 메서드
     * @param customerBondsIssueSub 생성대상 댓글의 댓글 모델 객체
     * @param result 바인딩결과
     * @param status 세션상태
     */
    @RequestMapping(value="/createReplyBondsIssueSub.do")
    public @ResponseBody void createReplyBondsIssueSub(CustomerBondsIssueSub customerBondsIssueSub, BindingResult result, SessionStatus status
            ,@RequestParam("itemTypeTemp")String itemType){
        
        try{
            
            if(result.hasErrors()){
                throw new IKEP4AjaxException( "", null );
            }
            User user = (User) this.getRequestAttribute( "ikep.user" );
            
            ModelBeanUtil.bindRegisterInfo( customerBondsIssueSub, user.getUserId(), user.getUserName() );
            customerBondsIssueSub.setItemType( itemType );
            this.customerBondsIssueSubService.createReplyBondsIssueSub(customerBondsIssueSub);
       
            
        }catch( Exception e){
            throw new IKEP4AjaxException( "code", e );
        }finally{
            status.setComplete();
        }
        
    }
    
    /**
     * 댓글 수정 비동기 컨트롤 메서드
     * @param customerBondsIssueSub
     * @param result
     * @param status
     */
    @RequestMapping(value="/updateBondsIssueSub.do")
    public @ResponseBody void updateBondsIssueSub (CustomerBondsIssueSub customerBondsIssueSub, BindingResult result, SessionStatus status){
        
        try {
            if (result.hasErrors()) {
                throw new IKEP4AjaxException("", null);
            }
            CustomerBondsIssueSub readCustomerBondsIssueSub = this.customerBondsIssueSubService.readCustomerBondsIssueSub(customerBondsIssueSub.getBondsIssueSubId());

            readCustomerBondsIssueSub.setContents(customerBondsIssueSub.getContents());
            readCustomerBondsIssueSub.setCounselDateSub(customerBondsIssueSub.getMcounselDateSub());
            readCustomerBondsIssueSub.setFactorySub(customerBondsIssueSub.getMfactorySub());
            readCustomerBondsIssueSub.setClaimGubunSubName(customerBondsIssueSub.getMclaimGubunSubName());
            readCustomerBondsIssueSub.setClaimGubunSub(customerBondsIssueSub.getMclaimGubunSub());
            readCustomerBondsIssueSub.setSubjectSub(customerBondsIssueSub.getSubjectSub());
            readCustomerBondsIssueSub.setCounselorSub(customerBondsIssueSub.getCounselorSub());

            User user = (User)this.getRequestAttribute("ikep.user");

            ModelBeanUtil.bindRegisterInfo(customerBondsIssueSub, user.getUserId(), user.getUserName());

            this.customerBondsIssueSubService.updateCustomerBondsIssueSub(readCustomerBondsIssueSub);
            
            if(readCustomerBondsIssueSub.getMasterSub().equals("Y")){
            	BondsIssueHistory bondsIssue = new BondsIssueHistory();
            	bondsIssue.setCounselDate(readCustomerBondsIssueSub.getCounselDateSub());
            	bondsIssue.setSubject(readCustomerBondsIssueSub.getSubjectSub());
            	bondsIssue.setContent(readCustomerBondsIssueSub.getContents());
            	bondsIssue.setFactory(readCustomerBondsIssueSub.getFactorySub());
            	bondsIssue.setClaimGubun(readCustomerBondsIssueSub.getClaimGubunSub());
            	bondsIssue.setClaimGubunName(readCustomerBondsIssueSub.getClaimGubunSubName());
            	bondsIssue.setCounselor(readCustomerBondsIssueSub.getCounselorSub());
            	customerBondsIssueService.updateBondsIssueMaster(bondsIssue,user,Integer.parseInt(readCustomerBondsIssueSub.getItemId()));
            }

        } catch(Exception exception) {
            throw new IKEP4AjaxException("code", exception);

        } finally {
            status.setComplete();
        }
        
    }
    
    /**
     * 댓글 작성자 모드 삭제 비동기 컨트롤 메서드
     * @param customerBondsIssueSub
     * @param result
     * @param status
     */
    @RequestMapping(value="/userDeleteBondsIssueSub.do")
    public @ResponseBody void userDeleteBondsIssueSub(CustomerBondsIssueSub customerBondsIssueSub, BindingResult result, SessionStatus status){
        try {
            if (result.hasErrors()) {
                throw new IKEP4AjaxException("", null);
            }
            User user = (User)this.getRequestAttribute("ikep.user");

            ModelBeanUtil.bindRegisterInfo(customerBondsIssueSub, user.getUserId(), user.getUserName());

            CustomerBondsIssueSub BondsIssueSubTmp = this.customerBondsIssueSubService.readCustomerBondsIssueSub( customerBondsIssueSub.getBondsIssueSubId() );
            
            
            // 사용자의 댓글인지 확인
            String registerId = BondsIssueSubTmp.getRegisterId();
            if(user.getUserId().equals(registerId)) {
                this.customerBondsIssueSubService.userDeleteBondsIssueSub(customerBondsIssueSub);                
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
    @RequestMapping(value="/adminDeleteBondsIssueSub.do")
    public @ResponseBody void adminDeleteBondsIssueSub (@RequestParam("itemId")String itemId,
            @RequestParam("linereplyId") String linereplyId,@RequestParam("itemType")String itemType){
        try {
            User user = (User)this.getRequestAttribute("ikep.user");
            Boolean isSystemAdmin = this.customerService.checkAdmin( user.getUserId() );
            if(isSystemAdmin) {
                this.customerBondsIssueSubService.adminDeleteBondsIssueSub(itemId, linereplyId, itemType);
            }

        } catch(Exception exception) {
            throw new IKEP4AjaxException("code", exception);

        }
    }
    

}
