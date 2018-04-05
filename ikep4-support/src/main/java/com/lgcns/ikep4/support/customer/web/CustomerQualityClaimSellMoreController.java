
package com.lgcns.ikep4.support.customer.web;

import java.util.HashMap;
import java.util.Map;

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
import com.lgcns.ikep4.support.customer.model.CustomerQualitySub;
import com.lgcns.ikep4.support.customer.model.QualityClaimSellHistory;
import com.lgcns.ikep4.support.customer.search.CustomerQualityClaimSellMoreSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerBondsIssueService;
import com.lgcns.ikep4.support.customer.service.CustomerCounselHistoryService;
import com.lgcns.ikep4.support.customer.service.CustomerQualityClaimSellMoreService;
import com.lgcns.ikep4.support.customer.service.CustomerQualityClaimSellService;
import com.lgcns.ikep4.support.customer.service.CustomerService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

@Controller
@RequestMapping( value = "/support/customer/customerQualityClaimSellMore" )
public class CustomerQualityClaimSellMoreController extends BaseController {

    @Autowired
    private CustomerQualityClaimSellMoreService customerQualityClaimSellMoreService;

    @Autowired
    private CustomerQualityClaimSellService customerQualityClaimSellService;

    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private CustomerCounselHistoryService customerCounselHistoryService;
    
    @Autowired
    private CustomerBondsIssueService customerBondsIssueService;
    
    @Autowired
    private UserDao userDao;

    public static final int LINEREPLY_INDENTATION_LIMITE = 5;

    /**
     * 댓글 생성 비동기 컨트롤 메서드
     * @param customerQualityClaimSellMore
     * @param result
     * @param status
     */
    @RequestMapping( value = "/createQualityClaimSellMore.do" )
    public @ResponseBody
    void createQualityClaimSellMore( CustomerQualitySub customerQualitySub, BindingResult result, SessionStatus status
            ,@RequestParam("itemType")String itemType) {

        try {
            if ( result.hasErrors() ) {
                throw new IKEP4AjaxException( "", null );
            }

            User user = (User)this.getRequestAttribute( "ikep.user" );

            ModelBeanUtil.bindRegisterInfo( customerQualitySub, user.getUserId(), user.getUserName() );
            customerQualitySub.setItemType( itemType );
            this.customerQualityClaimSellMoreService.createQualityClaimSellMore( customerQualitySub );

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
    public ModelAndView listQualityClaimSellMoreView(CustomerQualityClaimSellMoreSearchCondition searchCondition,
            BindingResult result, SessionStatus status ) {

        User user = (User)this.getRequestAttribute( "ikep.user" );
        Boolean isSystemAdmin = this.customerService.checkAdmin( user.getUserId() );
        Integer linereplyCount = 0;
        /*if(searchCondition.getItemType().equals( "CL" )){
            QualityClaimSellHistory qualityClaimSell = this.customerQualityClaimSellService.getQualityClaimSell( Integer
                .parseInt( searchCondition.getItemId() ) );
            linereplyCount = qualityClaimSell.getSubCount();
        }*/
        
        SearchResult<CustomerQualitySub> searchResult = this.customerQualityClaimSellMoreService.getCustomerQualityClaimSellMore( searchCondition );
        linereplyCount = searchResult.getEntity().size();
        
        boolean clrole = false;
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", user.getUserId());
		map.put("roleName", "ACL12");
		int claimRole = userDao.getUserRoleCheck(map);
		if(claimRole > 0){
			clrole = true;
		}
        
        return new ModelAndView().addObject( "searchResult", searchResult ).addObject( "user", user ).addObject( "linereplyCount", linereplyCount ).addObject( "searchCondition", searchResult.getSearchCondition() ).addObject( "isSystemAdmin", isSystemAdmin ).addObject( "itemType",searchCondition.getItemType() ).addObject( "clrole", clrole );

    }
    
    
    /**
     * 댓글의 댓글 생성 컨트롤 메서드
     * @param customerQualityClaimSellMore 생성대상 댓글의 댓글 모델 객체
     * @param result 바인딩결과
     * @param status 세션상태
     */
    @RequestMapping(value="/createReplyQualityClaimSellMore.do")
    public @ResponseBody void createReplyQualityClaimSellMore(CustomerQualitySub customerQualitySub, BindingResult result, SessionStatus status
            ,@RequestParam("itemTypeTemp")String itemType){
        
        try{
            
            if(result.hasErrors()){
                throw new IKEP4AjaxException( "", null );
            }
            User user = (User) this.getRequestAttribute( "ikep.user" );
            
            ModelBeanUtil.bindRegisterInfo( customerQualitySub, user.getUserId(), user.getUserName() );
            customerQualitySub.setItemType( itemType );
            this.customerQualityClaimSellMoreService.createReplyQualityClaimSellMore(customerQualitySub);
       
            
        }catch( Exception e){
            throw new IKEP4AjaxException( "code", e );
        }finally{
            status.setComplete();
        }
        
    }
    
    /**
     * 댓글 수정 비동기 컨트롤 메서드
     * @param customerQualityClaimSellMore
     * @param result
     * @param status
     */
    @RequestMapping(value="/updateQualityClaimSellMore.do")
    public @ResponseBody void updateQualityClaimSellMore (CustomerQualitySub customerQualitySub, BindingResult result, SessionStatus status){
        
        try {
            if (result.hasErrors()) {
                throw new IKEP4AjaxException("", null);
            }
            CustomerQualitySub readCustomerQualityClaimSellMore = this.customerQualityClaimSellMoreService.readCustomerQualityClaimSellMore(customerQualitySub.getQualityClaimSellMoreId());

            readCustomerQualityClaimSellMore.setContents(customerQualitySub.getContents());
            readCustomerQualityClaimSellMore.setCounselDateSub(customerQualitySub.getMcounselDateSub());
            readCustomerQualityClaimSellMore.setCounselorSub(customerQualitySub.getCounselorSub());
            readCustomerQualityClaimSellMore.setJijongSub(customerQualitySub.getJijongSub());
            readCustomerQualityClaimSellMore.setFactorySub(customerQualitySub.getMfactorySub());
            readCustomerQualityClaimSellMore.setFactorySubName(customerQualitySub.getMfactorySubName());
            readCustomerQualityClaimSellMore.setClaimGubunSubName(customerQualitySub.getMclaimGubunSubName());
            readCustomerQualityClaimSellMore.setClaimGubunSub(customerQualitySub.getMclaimGubunSub());
            readCustomerQualityClaimSellMore.setWeightSub(customerQualitySub.getWeightSub());
            readCustomerQualityClaimSellMore.setSubjectSub(customerQualitySub.getSubjectSub());

            User user = (User)this.getRequestAttribute("ikep.user");

            ModelBeanUtil.bindRegisterInfo(customerQualitySub, user.getUserId(), user.getUserName());

            this.customerQualityClaimSellMoreService.updateCustomerQualityClaimSellMore(readCustomerQualityClaimSellMore);
            
            
            if(readCustomerQualityClaimSellMore.getMasterSub().equals("Y")){
            	QualityClaimSellHistory qualityClaimSellHistory = new QualityClaimSellHistory();
            	qualityClaimSellHistory.setContent(readCustomerQualityClaimSellMore.getContents());
            	qualityClaimSellHistory.setCounselDate(readCustomerQualityClaimSellMore.getCounselDateSub());
            	qualityClaimSellHistory.setCounselor(readCustomerQualityClaimSellMore.getCounselorSub());
            	qualityClaimSellHistory.setJijong(readCustomerQualityClaimSellMore.getJijongSub());
            	qualityClaimSellHistory.setFactory(readCustomerQualityClaimSellMore.getFactorySub());
            	qualityClaimSellHistory.setFactoryName(readCustomerQualityClaimSellMore.getFactorySubName());
            	qualityClaimSellHistory.setClaimGubunName(readCustomerQualityClaimSellMore.getClaimGubunSubName());
            	qualityClaimSellHistory.setClaimGubun(readCustomerQualityClaimSellMore.getClaimGubunSub());
            	qualityClaimSellHistory.setWeight(readCustomerQualityClaimSellMore.getWeightSub());
            	qualityClaimSellHistory.setSubject(readCustomerQualityClaimSellMore.getSubjectSub());
            	customerQualityClaimSellService.updateQualityClaimSellMaster(qualityClaimSellHistory,user,Integer.parseInt(readCustomerQualityClaimSellMore.getItemId()));
            }
        } catch(Exception exception) {
            throw new IKEP4AjaxException("code", exception);

        } finally {
            status.setComplete();
        }
        
    }
    
    /**
     * 댓글 작성자 모드 삭제 비동기 컨트롤 메서드
     * @param customerQualityClaimSellMore
     * @param result
     * @param status
     */
    @RequestMapping(value="/userDeleteQualityClaimSellMore.do")
    public @ResponseBody void userDeleteQualityClaimSellMore(CustomerQualitySub customerQualitySub, BindingResult result, SessionStatus status){
        try {
            if (result.hasErrors()) {
                throw new IKEP4AjaxException("", null);
            }
            User user = (User)this.getRequestAttribute("ikep.user");

            ModelBeanUtil.bindRegisterInfo(customerQualitySub, user.getUserId(), user.getUserName());

            CustomerQualitySub QualityClaimSellMoreTmp = this.customerQualityClaimSellMoreService.readCustomerQualityClaimSellMore( customerQualitySub.getQualityClaimSellMoreId() );
            
            
            // 사용자의 댓글인지 확인
            String registerId = QualityClaimSellMoreTmp.getRegisterId();
            if(user.getUserId().equals(registerId)) {
                this.customerQualityClaimSellMoreService.userDeleteQualityClaimSellMore(customerQualitySub);                
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
            Boolean isSystemAdmin = this.customerService.checkAdmin( user.getUserId() );
            if(isSystemAdmin) {
                this.customerQualityClaimSellMoreService.adminDeleteQualityClaimSellMore(itemId, linereplyId, itemType);
            }

        } catch(Exception exception) {
            throw new IKEP4AjaxException("code", exception);

        }
    }
    

}
