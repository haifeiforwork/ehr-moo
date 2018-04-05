package com.lgcns.ikep4.support.customer.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxValidationException;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.core.code.BoardCode;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.model.BasicInfo;
import com.lgcns.ikep4.support.customer.model.FinanceCondition;
import com.lgcns.ikep4.support.customer.model.Finance;
import com.lgcns.ikep4.support.customer.model.BuyingInfo;
import com.lgcns.ikep4.support.customer.model.CounselHistory;
import com.lgcns.ikep4.support.customer.model.MainBusiness;
import com.lgcns.ikep4.support.customer.model.MainPerson;
import com.lgcns.ikep4.support.customer.model.MainSelling;
import com.lgcns.ikep4.support.customer.model.ManInfoItem;
import com.lgcns.ikep4.support.customer.model.PersonStatic;
import com.lgcns.ikep4.support.customer.model.RelationCompany;
import com.lgcns.ikep4.support.customer.search.FinanceSearchCondition;
import com.lgcns.ikep4.support.customer.service.CustomerBasicInfoService;
import com.lgcns.ikep4.support.customer.service.CustomerFinanceService;
import com.lgcns.ikep4.support.customer.service.CustomerCounselHistoryService;
import com.lgcns.ikep4.support.customer.service.CustomerMainSellingService;
import com.lgcns.ikep4.support.customer.service.CustomerService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.sap.tc.logging.schema.SysDatabaseCategory;


/**
 * 
 * 고객 기본정보 컨트롤러 
 *
 * @author SongHeeJung
 * @version $Id$
 */


@Controller
@RequestMapping(value="/support/customer/customerFinance")
public class CustomerFinanceController extends BaseController {
    
    
    @Autowired
    CustomerFinanceService customerFinanceService;
    
    @Autowired
    CustomerBasicInfoService customerBasicInfoService;
    
    @Autowired
    CustomerService customerService;
    
    @Autowired
    CustomerMainSellingService customerMainSellingService;
    
    @Autowired
    CustomerCounselHistoryService customerCounselHistoryService;
    
   
    /**
     * 고객정보 리스트 보여주기
     * @return
     */
    @RequestMapping( value = "/financeList.do" )
    public ModelAndView CustomerList(FinanceSearchCondition searchCondition,
            @RequestParam(value="searchConditionString",required = false)String searchConditionString) {

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
        
        SearchResult<Finance> searchResult = this.customerFinanceService.listFinanceBySearchCondition( searchCondition );
        
        ModelAndView modelAndView = new ModelAndView().addObject( "searchResult",searchResult )
        .addObject( "searchCondition", searchCondition )
         .addObject( "searchConditionString", tempSearchConditionString )
        .addObject("boardCode",new BoardCode());
        return modelAndView;
    }
    
    @RequestMapping(value = "/editFinance")
	public ModelAndView editCategory() {

		String startDt = customerFinanceService.yearInfo("001");
        String endDt = customerFinanceService.yearInfo("002");		
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("startDt", startDt);
		model.addObject("endDt", endDt);
		return model;
	}
    
    @RequestMapping(value = "/createFinance")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String createFinance(Finance finance) {	
		User user = (User) getRequestAttribute("ikep.user");
		
		finance.setUpdaterId(user.getUserId());
		finance.setUpdaterName(user.getUserName());
		
		this.customerFinanceService.saveFinanceYearInfo(finance);
		
		return "success";
	}

    /**
     * 고객 기본정보 상세 보기 
     * @param id
     * @return
     */
    @RequestMapping(value="/detailFinance.do")
    public ModelAndView detailFinance (@RequestParam("id")String id){
      
        //로그인 한 사용자가 admin인지 확인
        User user = (User) getRequestAttribute("ikep.user");
        
        String startDt = customerFinanceService.yearInfo("001");
        String endDt = customerFinanceService.yearInfo("002");
        
        //로그인한 사용자가 admin인지 확인
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        if(!isAdmin){
            
            boolean result = customerService.checkAccess(user.getUserId());
            
            if(!result){
                throw new IKEP4AuthorizedException();
            }
        }
        
        
        Finance finance = getFinance( id );
        
        List<Finance> profitLossList = customerFinanceService.profitLossList( id );
        
        List<FinanceCondition> conditionList = customerFinanceService.conditionList( id );
        
        Finance fin4 = new Finance();
        String tmpMnt4 = "0";
        String tmpMnt16 = "0";
        String tmpMnt5 = "0";
        DecimalFormat format = new DecimalFormat("#.#");
        List<Finance> rateList = new ArrayList<Finance>();
        
        for(Finance fin1 : profitLossList){
        	for(FinanceCondition fin2 : conditionList){
            	if(fin1.getYearInfo().equals(fin2.getYearInfo()) && fin1.getDivInfo() == null && fin2.getDivInfo() == null){
            		Finance fin3 = new Finance();
            		fin3.setYearInfo(fin2.getYearInfo());
            		if(fin2.getMnt1().equals("0") || fin2.getMnt15().equals("0")){
            			fin3.setMnt1("0");
            		}else{
            			if(fin2.getMnt15().equals("0")){
            				fin3.setMnt1("0");
            			}else{
            				fin3.setMnt1(format.format((Double.parseDouble(fin2.getMnt1()) / Double.parseDouble(fin2.getMnt15()))*100));
            			}
            		}
            		if(fin2.getMnt24().equals("0") || fin2.getMnt28().equals("0")){
            			fin3.setMnt2("0");
            		}else{
            			if(fin2.getMnt28().equals("0")){
            				fin3.setMnt2("0");
            			}else{
            				fin3.setMnt2(format.format((Double.parseDouble(fin2.getMnt24()) / Double.parseDouble(fin2.getMnt28()))*100));
            			}
            		}
            		if(fin2.getMnt28().equals("0") || fin2.getMnt14().equals("0")){
            			fin3.setMnt3("0");
            		}else{
            			if(fin2.getMnt14().equals("0")){
            				fin3.setMnt3("0");
            			}else{
            				fin3.setMnt3(format.format((Double.parseDouble(fin2.getMnt28()) / Double.parseDouble(fin2.getMnt14()))*100));
            			}
            		}
            		if((fin2.getMnt17().equals("0") && fin2.getMnt18().equals("0") && fin2.getMnt21().equals("0") && fin2.getMnt22().equals("0")) || fin2.getMnt14().equals("0")){
            			fin3.setMnt4("0");
            		}else{
            			if(fin2.getMnt14().equals("0")){
            				fin3.setMnt4("0");
            			}else{
            				fin3.setMnt4(format.format(((Double.parseDouble(fin2.getMnt17())+Double.parseDouble(fin2.getMnt18())+Double.parseDouble(fin2.getMnt21())+Double.parseDouble(fin2.getMnt22())) / Double.parseDouble(fin2.getMnt14()))*100));
            			}
            		}
            		if(fin2.getMnt8().equals("0") || fin2.getMnt13().equals("0")){
            			fin3.setMnt5("0");
            		}else{
            			if(fin1.getMnt13().equals("0")){
            				fin3.setMnt5("0");
            			}else{
            				fin3.setMnt5(format.format(Double.parseDouble(fin1.getMnt8()) / Double.parseDouble(fin1.getMnt13())));
            			}
            		}
            		if(fin2.getMnt3().equals("0") || fin2.getMnt2().equals("0")){
            			fin3.setMnt6("0");
            		}else{
            			if(fin1.getMnt2().equals("0")){
            				fin3.setMnt6("0");
            			}else{
            				fin3.setMnt6(format.format((Double.parseDouble(fin1.getMnt3()) / Double.parseDouble(fin1.getMnt2()))*100));
            			}
            		}
            		if(fin2.getMnt8().equals("0") || fin2.getMnt1().equals("0")){
            			fin3.setMnt7("0");
            		}else{
            			if(fin1.getMnt1().equals("0")){
            				fin3.setMnt7("0");
            			}else{
            				fin3.setMnt7(format.format((Double.parseDouble(fin1.getMnt8()) / Double.parseDouble(fin1.getMnt1()))*100));
            			}
            		}
            		if(fin2.getMnt15().equals("0") || fin2.getMnt1().equals("0")){
            			fin3.setMnt8("0");
            		}else{
            			if(fin1.getMnt1().equals("0")){
            				fin3.setMnt8("0");
            			}else{
            				fin3.setMnt8(format.format((Double.parseDouble(fin1.getMnt15()) / Double.parseDouble(fin1.getMnt1()))*100));
            			}
            		}
            		if(fin2.getMnt17().equals("0") || fin2.getMnt1().equals("0")){
            			fin3.setMnt9("0");
            		}else{
            			if(fin1.getMnt1().equals("0")){
            				fin3.setMnt9("0");
            			}else{
            				fin3.setMnt9(format.format((Double.parseDouble(fin1.getMnt17()) / Double.parseDouble(fin1.getMnt1()))*100));
            			}
            		}
            		if(fin2.getMnt1().equals("0") || (tmpMnt4.equals("0") && fin2.getMnt4().equals("0"))){
            			fin3.setMnt10("0");
            		}else{
            			if(fin2.getMnt4().equals("0") && tmpMnt4.equals("0")){
            				fin3.setMnt10("0");
            			}else{
            				fin3.setMnt10(format.format(Double.parseDouble(fin1.getMnt1())/((Double.parseDouble(tmpMnt4)+Double.parseDouble(fin2.getMnt4()))/2)));
            			}
            		}
            		if(fin2.getMnt1().equals("0") || (tmpMnt16.equals("0") && fin2.getMnt16().equals("0"))){
            			fin3.setMnt11("0");
            		}else{
            			if(fin2.getMnt16().equals("0") && tmpMnt16.equals("0")){
            				fin3.setMnt11("0");
            			}else{
            				fin3.setMnt11(format.format(Double.parseDouble(fin1.getMnt1())/((Double.parseDouble(tmpMnt16)+Double.parseDouble(fin2.getMnt16()))/2)));
            			}
            			fin3.setMnt11(format.format(Double.parseDouble(fin1.getMnt1())/((Double.parseDouble(tmpMnt16)+Double.parseDouble(fin2.getMnt16()))/2)));
            		}
            		if(fin2.getMnt1().equals("0") || (tmpMnt5.equals("0") && fin2.getMnt5().equals("0"))){
            			fin3.setMnt12("0");
            		}else{
            			if(fin2.getMnt5().equals("0") && tmpMnt5.equals("0")){
            				fin3.setMnt12("0");
            			}else{
            				fin3.setMnt12(format.format(Double.parseDouble(fin1.getMnt1())/((Double.parseDouble(tmpMnt5)+Double.parseDouble(fin2.getMnt5()))/2)));
            			}
            		}
        			rateList.add(fin3);
        			if(endDt.equals(fin2.getYearInfo())){
        				Finance fin5 = new Finance();
            			fin5.setDivInfo("rate");
            			fin3.setYearInfo(fin2.getYearInfo());
            			if(Double.parseDouble(fin3.getMnt1())-Double.parseDouble(fin4.getMnt1()) == 0 || (fin4.getMnt1().equals("0"))){
                			fin5.setMnt1("0");
                		}else{
                			if(fin4.getMnt1().equals("0")){
                				fin5.setMnt1("0");
                			}else{
                				fin5.setMnt1(format.format((Double.parseDouble(fin3.getMnt1())-Double.parseDouble(fin4.getMnt1()))/Double.parseDouble(fin4.getMnt1())*100));
                			}
                		}
            			if(Double.parseDouble(fin3.getMnt2())-Double.parseDouble(fin4.getMnt2()) == 0 || (fin4.getMnt2().equals("0"))){
                			fin5.setMnt2("0");
                		}else{
                			if(fin4.getMnt2().equals("0")){
                				fin5.setMnt2("0");
                			}else{
                				fin5.setMnt2(format.format((Double.parseDouble(fin3.getMnt2())-Double.parseDouble(fin4.getMnt2()))/Double.parseDouble(fin4.getMnt2())*100));
                			}
                		}
            			if(Double.parseDouble(fin3.getMnt3())-Double.parseDouble(fin4.getMnt3()) == 0 || (fin4.getMnt3().equals("0"))){
                			fin5.setMnt3("0");
                		}else{
                			if(fin4.getMnt3().equals("0")){
                				fin5.setMnt3("0");
                			}else{
                				fin5.setMnt3(format.format((Double.parseDouble(fin3.getMnt3())-Double.parseDouble(fin4.getMnt3()))/Double.parseDouble(fin4.getMnt3())*100));
                			}
                		}
            			if(Double.parseDouble(fin3.getMnt4())-Double.parseDouble(fin4.getMnt4()) == 0 || (fin4.getMnt4().equals("0"))){
                			fin5.setMnt4("0");
                		}else{
                			if(fin4.getMnt4().equals("0")){
                				fin5.setMnt4("0");
                			}else{
                				fin5.setMnt4(format.format((Double.parseDouble(fin3.getMnt4())-Double.parseDouble(fin4.getMnt4()))/Double.parseDouble(fin4.getMnt4())*100));
                			}
                		}
            			if(Double.parseDouble(fin3.getMnt5())-Double.parseDouble(fin4.getMnt5()) == 0 || (fin4.getMnt5().equals("0"))){
                			fin5.setMnt5("0");
                		}else{
                			if(fin4.getMnt5().equals("0")){
                				fin5.setMnt5("0");
                			}else{
                				fin5.setMnt5(format.format((Double.parseDouble(fin3.getMnt5())-Double.parseDouble(fin4.getMnt5()))/Double.parseDouble(fin4.getMnt5())*100));
                			}
                		}
            			if(Double.parseDouble(fin3.getMnt6())-Double.parseDouble(fin4.getMnt6()) == 0 || (fin4.getMnt6().equals("0"))){
                			fin5.setMnt6("0");
                		}else{
                			if(fin4.getMnt6().equals("0")){
                				fin5.setMnt6("0");
                			}else{
                				fin5.setMnt6(format.format((Double.parseDouble(fin3.getMnt6())-Double.parseDouble(fin4.getMnt6()))/Double.parseDouble(fin4.getMnt6())*100));
                			}
                		}
            			if(Double.parseDouble(fin3.getMnt7())-Double.parseDouble(fin4.getMnt7()) == 0 || (fin4.getMnt7().equals("0"))){
                			fin5.setMnt7("0");
                		}else{
                			if(fin4.getMnt7().equals("0")){
                				fin5.setMnt7("0");
                			}else{
                				fin5.setMnt7(format.format((Double.parseDouble(fin3.getMnt7())-Double.parseDouble(fin4.getMnt7()))/Double.parseDouble(fin4.getMnt7())*100));
                			}
                		}
            			if(Double.parseDouble(fin3.getMnt8())-Double.parseDouble(fin4.getMnt8()) == 0 || (fin4.getMnt8().equals("0"))){
                			fin5.setMnt8("0");
                		}else{
                			if(fin4.getMnt8().equals("0")){
                				fin5.setMnt8("0");
                			}else{
                				fin5.setMnt8(format.format((Double.parseDouble(fin3.getMnt8())-Double.parseDouble(fin4.getMnt8()))/Double.parseDouble(fin4.getMnt8())*100));
                			}
                		}
            			if(Double.parseDouble(fin3.getMnt9())-Double.parseDouble(fin4.getMnt9()) == 0 || (fin4.getMnt9().equals("0"))){
                			fin5.setMnt9("0");
                		}else{
                			if(fin4.getMnt9().equals("0")){
                				fin5.setMnt9("0");
                			}else{
                				fin5.setMnt9(format.format((Double.parseDouble(fin3.getMnt9())-Double.parseDouble(fin4.getMnt9()))/Double.parseDouble(fin4.getMnt9())*100));
                			}
                		}
            			if(Double.parseDouble(fin3.getMnt10())-Double.parseDouble(fin4.getMnt10()) == 0 || (fin4.getMnt10().equals("0"))){
                			fin5.setMnt10("0");
                		}else{
                			if(fin4.getMnt10().equals("0")){
                				fin5.setMnt10("0");
                			}else{
                				fin5.setMnt10(format.format((Double.parseDouble(fin3.getMnt10())-Double.parseDouble(fin4.getMnt10()))/Double.parseDouble(fin4.getMnt10())*100));
                			}
                		}
            			if(Double.parseDouble(fin3.getMnt11())-Double.parseDouble(fin4.getMnt11()) == 0 || (fin4.getMnt11().equals("0"))){
                			fin5.setMnt11("0");
                		}else{
                			if(fin4.getMnt11().equals("0")){
                				fin5.setMnt11("0");
                			}else{
                				fin5.setMnt11(format.format((Double.parseDouble(fin3.getMnt11())-Double.parseDouble(fin4.getMnt11()))/Double.parseDouble(fin4.getMnt11())*100));
                			}
                		}
            			if(Double.parseDouble(fin3.getMnt12())-Double.parseDouble(fin4.getMnt12()) == 0 || (fin4.getMnt12().equals("0"))){
                			fin5.setMnt12("0");
                		}else{
                			if(fin4.getMnt12().equals("0")){
                				fin5.setMnt12("0");
                			}else{
                				fin5.setMnt12(format.format((Double.parseDouble(fin3.getMnt12())-Double.parseDouble(fin4.getMnt12()))/Double.parseDouble(fin4.getMnt12())*100));
                			}
                		}
            			rateList.add(fin5);
        			}
            		fin4.setMnt1(fin3.getMnt1());
            		fin4.setMnt2(fin3.getMnt2());
            		fin4.setMnt3(fin3.getMnt3());
            		fin4.setMnt4(fin3.getMnt4());
            		fin4.setMnt5(fin3.getMnt5());
            		fin4.setMnt6(fin3.getMnt6());
            		fin4.setMnt7(fin3.getMnt7());
            		fin4.setMnt8(fin3.getMnt8());
            		fin4.setMnt9(fin3.getMnt9());
            		fin4.setMnt10(fin3.getMnt10());
            		fin4.setMnt11(fin3.getMnt11());
            		fin4.setMnt12(fin3.getMnt12());
            		tmpMnt4 = fin2.getMnt4();
        			tmpMnt16 = fin2.getMnt16();
        			tmpMnt5 = fin2.getMnt5();
        			
            	}
            }
        }
        //List<Finance> rateList = customerFinanceService.rateList( id );
        
        //List<FinanceCondition> financeCondition = customerFinanceService.readFinanceCondition( id );
    
        List<MainPerson> mainPerson = getMainPerson( id );
        
        PersonStatic personStatic = getPersonStatic( id );
        
        List<MainBusiness> mainBusiness = customerFinanceService.readMainBusiness( id );
        
        String buyingCondition = "";
        String sellingCondition = "";
        
        if(mainBusiness != null){
            for(int i=0 ; i<mainBusiness.size(); i++){
                if(mainBusiness.get( i ).getSellingBuyingFlag().equals( "1" )){
                  
                    buyingCondition = mainBusiness.get( i ).getCondition();
                }else if(mainBusiness.get( i ).getSellingBuyingFlag().equals( "2" )){
                   
                    sellingCondition = mainBusiness.get( i ).getCondition();
                  
                }
            }
         }
        
        int buyingCnt = 1;
        int sellingCnt = 1;

        
        if(mainBusiness != null){
           for(int i=0 ; i<mainBusiness.size(); i++){
               if(mainBusiness.get( i ).getSellingBuyingFlag().equals( "1" )){
                   buyingCnt ++;
                   
               }else if(mainBusiness.get( i ).getSellingBuyingFlag().equals( "2" )){
                   sellingCnt++;
                  
               }
           }
        }

        List<RelationCompany> relationCompany = customerFinanceService.readRelationCompany( id );
        
        List<BuyingInfo> buyingInfoList = null;
        
        if(finance.getSapId() != "" && finance.getSapId() != null){
           
            buyingInfoList = customerFinanceService.getBuyingInfo(finance.getSapId());
                    
        }
        
        BasicInfo info = new BasicInfo();
        info.setId(id);
        info.setDivCode("FM");
        info.setRegisterId(user.getUserId());
        customerBasicInfoService.updateHitCount(info);

        ModelAndView modelAndView = new ModelAndView().addObject( "finance", finance )
        .addObject( "isAdmin", isAdmin )
        //.addObject( "financeCondition", financeCondition )
        .addObject( "mainPerson", mainPerson )
        .addObject( "personStatic", personStatic)
        .addObject( "mainBusiness", mainBusiness)
        .addObject( "buyingCondition", buyingCondition )
        .addObject( "sellingCondition", sellingCondition )
        .addObject( "buyingCnt", buyingCnt )
        .addObject( "sellingCnt", sellingCnt )
        .addObject( "relationCompany", relationCompany )
        .addObject( "profitLossList", profitLossList )
        .addObject( "conditionList", conditionList )
        .addObject( "rateList", rateList )
        .addObject( "buyingInfoList", buyingInfoList )
        .addObject( "startDt", startDt )
        .addObject( "endDt", endDt );    
        return modelAndView;
    }
    
    @RequestMapping(value="/detailFinancePrint.do")
    public ModelAndView detailFinancePrint (@RequestParam("id")String id){
      
        //로그인 한 사용자가 admin인지 확인
        User user = (User) getRequestAttribute("ikep.user");
        
        String startDt = customerFinanceService.yearInfo("001");
        String endDt = customerFinanceService.yearInfo("002");
        
        //로그인한 사용자가 admin인지 확인
        boolean isAdmin = customerService.checkAdmin( user.getUserId() );
        
        if(!isAdmin){
            
            boolean result = customerService.checkAccess(user.getUserId());
            
            if(!result){
                throw new IKEP4AuthorizedException();
            }
        }
        
        
        Finance finance = getFinance( id );
        
        List<Finance> profitLossList = customerFinanceService.profitLossList( id );
        
        List<FinanceCondition> conditionList = customerFinanceService.conditionList( id );
        
        List<Finance> rateList = customerFinanceService.rateList( id );
        
        //List<FinanceCondition> financeCondition = customerFinanceService.readFinanceCondition( id );
    
        List<MainPerson> mainPerson = getMainPerson( id );
        
        PersonStatic personStatic = getPersonStatic( id );
        
        List<MainBusiness> mainBusiness = customerFinanceService.readMainBusiness( id );
        
        String buyingCondition = "";
        String sellingCondition = "";
        
        if(mainBusiness != null){
            for(int i=0 ; i<mainBusiness.size(); i++){
                if(mainBusiness.get( i ).getSellingBuyingFlag().equals( "1" )){
                  
                    buyingCondition = mainBusiness.get( i ).getCondition();
                }else if(mainBusiness.get( i ).getSellingBuyingFlag().equals( "2" )){
                   
                    sellingCondition = mainBusiness.get( i ).getCondition();
                  
                }
            }
         }
        
        int buyingCnt = 1;
        int sellingCnt = 1;

        
        if(mainBusiness != null){
           for(int i=0 ; i<mainBusiness.size(); i++){
               if(mainBusiness.get( i ).getSellingBuyingFlag().equals( "1" )){
                   buyingCnt ++;
                   
               }else if(mainBusiness.get( i ).getSellingBuyingFlag().equals( "2" )){
                   sellingCnt++;
                  
               }
           }
        }

        List<RelationCompany> relationCompany = customerFinanceService.readRelationCompany( id );
        
        List<BuyingInfo> buyingInfoList = null;
        
        if(finance.getSapId() != "" && finance.getSapId() != null){
           
            buyingInfoList = customerFinanceService.getBuyingInfo(finance.getSapId());
                    
        }

        ModelAndView modelAndView = new ModelAndView().addObject( "finance", finance )
        .addObject( "isAdmin", isAdmin )
        //.addObject( "financeCondition", financeCondition )
        .addObject( "mainPerson", mainPerson )
        .addObject( "personStatic", personStatic)
        .addObject( "mainBusiness", mainBusiness)
        .addObject( "buyingCondition", buyingCondition )
        .addObject( "sellingCondition", sellingCondition )
        .addObject( "buyingCnt", buyingCnt )
        .addObject( "sellingCnt", sellingCnt )
        .addObject( "relationCompany", relationCompany )
        .addObject( "profitLossList", profitLossList )
        .addObject( "conditionList", conditionList )
        .addObject( "rateList", rateList )
        .addObject( "buyingInfoList", buyingInfoList )
        .addObject( "startDt", startDt )
        .addObject( "endDt", endDt );    
        return modelAndView;
    }

    /**
     * 고객기본정보 가져온다.
     * @param id
     * @return
     */
    private Finance getFinance( String id ) {
        Finance finance = new Finance();        
        finance = customerFinanceService.readFinance( id );
   
        if(finance != null){
            if(finance.getCustomerGubun().equals( "1" )){
                finance.setCustomerGubun( "유통사" );
            }else if(finance.getCustomerGubun().equals( "2" )){
                finance.setCustomerGubun( "인쇄/가공사" );
            }else if(finance.getCustomerGubun().equals( "3" )){
                finance.setCustomerGubun( "출판/기획사" );
            }else if(finance.getCustomerGubun().equals( "4" )){
                finance.setCustomerGubun( "일반고객" );
            }
            
            if(finance.getSubdivisionGrade().equals( "1" )){
                finance.setSubdivisionGrade( "Target고객" );
            }else if(finance.getSubdivisionGrade().equals( "2" )){
                finance.setSubdivisionGrade( "이익개선고객" );
            }else if(finance.getSubdivisionGrade().equals( "3" )){
                finance.setSubdivisionGrade( "매출개선고객" );
            }else if(finance.getSubdivisionGrade().equals( "4" )){
                finance.setSubdivisionGrade( "관리대상고객" );
            } 
       }
        
        if(finance == null)
            finance = new Finance();
        return finance;
    }

    /**
     * 고객상세정보(인원현황) 가져오기
     * @param id
     * @return
     */
    private PersonStatic getPersonStatic( String id ) {
  
        PersonStatic personStatic = customerFinanceService.readPersonStatic( id ); 
  
        return personStatic;
    }


    /**
     * 고객 상세정보 (주요임원) 가져오기 
     * @param id
     * @return
     */
    private List<MainPerson> getMainPerson( String id ) {
       
        if(id.length() > 1){
           String[] ids = id.split( "," );
           id = ids[0];
        }
        
        List<MainPerson> mainPerson = customerFinanceService.readMainPerson( id );
        
        if(mainPerson.size() >0){
            for(int i=0; i<mainPerson.size(); i++){
                if(mainPerson.get( i ).getOfficerFlag().equals( "N" )){
                    if(mainPerson.get( i ).getKeymanFlag().equals( "1" )){
                        mainPerson.get( i ).setKeymanFlag( "임원" );                      
                    }else if(mainPerson.get( i ).getKeymanFlag().equals( "2" )){
                        mainPerson.get( i ).setKeymanFlag( "영업" );       
                    }else if(mainPerson.get( i ).getKeymanFlag().equals( "3" )){
                        mainPerson.get( i ).setKeymanFlag( "구매" );       
                    }else if(mainPerson.get( i ).getKeymanFlag().equals( "4" )){
                        mainPerson.get( i ).setKeymanFlag( "기타" );       
                    }             
                }
            }
        }
        return mainPerson;
    }

    /**
     * 등록 팝업창 생성 및 고객 조회
     * @return
     */
    @RequestMapping(value="/popupBusinessNo.do")
    public ModelAndView popupBusinessNo (FinanceSearchCondition searchCondition,
            @RequestParam(value="searchConditionString",required = false)String searchConditionString){
     
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
        
        SearchResult<Finance> searchResult = this.customerFinanceService.listFinanceBySearchCondition( searchCondition );
        
        return new ModelAndView().addObject( "searchResult",searchResult )
        .addObject( "searchCondition", searchCondition )
        .addObject( "searchConditionString", tempSearchConditionString )
       .addObject("boardCode",new BoardCode());
    }
    
    
    
    
    /**
     * 고객정보 저장,수정 화면 출력 (기본정보)
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyFinance.do")
    public ModelAndView modifyFinanceView(@RequestParam(value = "id", required = false)String id){
     
        String selectedCustomerGubun = "";
        String selectedSubdivisionGrade = "";
        ModelAndView mav = new ModelAndView();
      
        String startDt = customerFinanceService.yearInfo("001");
        String endDt = customerFinanceService.yearInfo("002");
        if(id != null && id != ""){
            Finance finance = customerFinanceService.readFinance( id );
            List<Finance> profitLossList = customerFinanceService.profitLossList( id );
            
            return mav.addObject( "finance", finance )        
            .addObject( "profitLossList", profitLossList )
            .addObject( "selectedCustomerGubun", selectedCustomerGubun )
            .addObject( "selectedSubdivisionGrade", selectedSubdivisionGrade )
            .addObject( "startDt", startDt )
            .addObject( "endDt", endDt );  
            
         }else{
             Finance finance2 = new Finance();
            
            return mav.addObject( "finance", finance2 )        
            .addObject( "selectedCustomerGubun", selectedCustomerGubun )
            .addObject( "selectedSubdivisionGrade", selectedSubdivisionGrade )
            .addObject( "startDt", startDt )
            .addObject( "endDt", endDt );  
         }
        
     
        
    }
    
    
    
    /**
     * 고객정보 저장,수정화면 출력 (연혁정보)
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyFinanceCondition.do")
    public ModelAndView modifyFinanceConditionView(@RequestParam(value = "id", required=false) String id){
        
    	String startDt = customerFinanceService.yearInfo("001");
        String endDt = customerFinanceService.yearInfo("002");
    	Finance finance = customerFinanceService.readFinance( id );
        List<FinanceCondition> conditionList = customerFinanceService.conditionList( id );
        if(conditionList.isEmpty()){
        	conditionList = null;
        }
        
        ModelAndView mav = new ModelAndView();
        
        return mav.addObject( "conditionList", conditionList ).addObject( "finance", finance ).addObject( "startDt", startDt ).addObject( "endDt", endDt ) ;    
    }
    
    /**
     * 고객정보 수정화면 출력(설비현황)
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyFinanceRate.do")
    public ModelAndView modifyRateView(@RequestParam("id")String id){
        ModelAndView mav = new ModelAndView();
        String startDt = customerFinanceService.yearInfo("001");
        String endDt = customerFinanceService.yearInfo("002");
        Finance finance = customerFinanceService.readFinance( id );
        
        List<Finance> rateList = customerFinanceService.rateList( id );
        
        return mav.addObject( "finance", finance ).addObject( "id", id ).addObject( "rateList", rateList ).addObject( "startDt", startDt ).addObject( "endDt", endDt );   
    }
    
    /**
     * 고객정보 수정화면 출력 (주요임원)
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyMainPerson1.do")
    public ModelAndView modifyMainPerson1View(@RequestParam("id") String id){
        
        List<MainPerson> mainPerson = getMainPerson( id );
        
        if(mainPerson.isEmpty()){
            mainPerson = null;
        }
        
        ModelAndView mav = new ModelAndView();
        
        return mav.addObject( "mainPerson", mainPerson ).addObject( "id", id );   
       
    }
    
    /**
     * 고객정보 수정화면 출력(keyman)
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyMainPerson2.do")
    public ModelAndView modifyMainPerson2View(@RequestParam("id") String id){
        
        List<MainPerson> mainPerson = getMainPerson( id );
        
        if(mainPerson.isEmpty()){
            mainPerson = null;
        }
        
        ModelAndView mav = new ModelAndView();
        
        return mav.addObject( "mainPerson", mainPerson ).addObject( "id", id );   
    }
    
    /**
     * 고객정보 수정화면 출력(인원현황)
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyPersonStatic.do")
    public ModelAndView modifyPersonStaticView(@RequestParam("id") String id){
        //System.out.println("id:" +id);
        
        PersonStatic personStatic = getPersonStatic( id );
        
        ModelAndView mav = new ModelAndView();
        
        return mav.addObject( "personStatic", personStatic ).addObject( "id", id );
    }
    /**
     * 고객정보 수정화면 출력(주거래선)
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyMainBusiness.do")
    public ModelAndView modifyMainBusinessView(@RequestParam("id") String id){
        
        List<MainBusiness> mainBusiness = customerFinanceService.readMainBusiness( id);
        String buyingCondition = "";
        String sellingCondition = "";
        
        if(mainBusiness != null){
            for(int i=0 ; i<mainBusiness.size(); i++){
                if(mainBusiness.get( i ).getSellingBuyingFlag().equals( "1" )){
                  
                    buyingCondition = mainBusiness.get( i ).getCondition();
                }else if(mainBusiness.get( i ).getSellingBuyingFlag().equals( "2" )){
                   
                    sellingCondition = mainBusiness.get( i ).getCondition();
                  
                }
            }
         }
        
        if(mainBusiness.isEmpty()){
            mainBusiness = null;
        }
        ModelAndView mav = new ModelAndView();
        
        return mav.addObject( "mainBusiness", mainBusiness)
                  .addObject( "buyingCondition", buyingCondition )
                  .addObject( "sellingCondition", sellingCondition )
                  .addObject( "id", id );
    }
    
    /**
     * 고객정보 수정화면 출력 (관계사 현황)
     * @param id
     * @return
     */
    @RequestMapping(value="/modifyBasicRelationCompany.do")
    public ModelAndView modifyBasicRelationCompanyView(@RequestParam("id") String id){
        List<RelationCompany> relationCompany = customerFinanceService.readRelationCompany( id );
         
        if(relationCompany.isEmpty()){
             relationCompany = null;            
        
        }
        
        ModelAndView mav = new ModelAndView();
        
        mav.addObject( "relationCompany", relationCompany );
        return mav;
    }
    
    
    /**
     * 고객사의 기본정보를 저장한다. (기존에 고객사가 저장되어있을 때) 
     * @param finance
     * @param bindResult
     * @param status
     * @return
     */
    @RequestMapping(value="/saveFinance.do")
    public String saveFinance(Finance finance){
        User user = (User) getRequestAttribute( "ikep.user" );   
        String id = null;
     
        finance.setUpdaterId( user.getUserId() );
        finance.setUpdaterName( user.getUserName() );
        finance.setRegisterId( user.getUserId() );
        finance.setRegisterName(user.getUserName() );

        try{
           id=  customerFinanceService.saveFinance(finance);
           
           id = finance.getCustomerInfoId();
           
        }catch(Exception e){
            throw new IKEP4AjaxException( "code", e );
        }
        

        
        return "redirect:/support/customer/customerFinance/modifyFinance.do?id="+id;
        
    }
    
    /**
     * 고객사의 기본정보를 저장한다. (연혁정보)
     * @param financeCondition
     * @param id
     * @param historyCnt
     * @return
     */
    @RequestMapping(value="/saveFinanceCondition.do")
    public String saveFinanceCondition(FinanceCondition financeCondition, @RequestParam(value="historyCnt") String historyCnt){
        
    	User user = (User) getRequestAttribute( "ikep.user" ); 
    	financeCondition.setUpdaterId( user.getUserId() );
    	financeCondition.setUpdaterName( user.getUserName() );
    	financeCondition.setRegisterId( user.getUserId() );
    	financeCondition.setRegisterName(user.getUserName() );
        customerFinanceService.saveFinanceCondition( financeCondition, historyCnt );
        
        return "redirect:/support/customer/customerFinance/modifyFinanceCondition.do?id="+financeCondition.getCustomerInfoId();
        
    }
    /**
     * 고객사의 기본정보를 저장한다.(설비현황)
     * @param finance
     * @param id
     * @return
     */
    @RequestMapping(value="/saveFinanceRate.do")
    public String saveFinanceRate(Finance finance, String id){
    	User user = (User) getRequestAttribute( "ikep.user" );   
     
        finance.setUpdaterId( user.getUserId() );
        finance.setUpdaterName( user.getUserName() );
        finance.setRegisterId( user.getUserId() );
        finance.setRegisterName(user.getUserName() );
        customerFinanceService.saveFinanceRate(finance);
        
        return "redirect:/support/customer/customerFinance/modifyFinanceRate.do?id="+finance.getCustomerInfoId();
        
    }
    /**
     * 고객사의 기본정보를 저장한다.(주요임원)
     * @param mainPerson
     * @param id
     * @param personCnt
     * @return
     */
    @RequestMapping(value="/saveMainPerson1.do")
    public String saveMainPerson1 (MainPerson mainPerson, String id,  @RequestParam("personCnt") String personCnt){
        
        customerFinanceService.saveMainPerson1( mainPerson , personCnt);
        
        return "redirect:/support/customer/customerFinance/modifyMainPerson1.do?id="+id;
    }
    
    /**
     * 고객사의 기본정보를 저장한다. (keyman)
     * @param mainPerson
     * @param id
     * @param personCnt
     * @return
     */
    @RequestMapping(value="/saveMainPerson2.do")
    public String saveMainPerson2 (MainPerson mainPerson, String id,  @RequestParam("personCnt") String personCnt){
        
        customerFinanceService.saveMainPerson2( mainPerson , personCnt);
        
        return "redirect:/support/customer/customerFinance/modifyMainPerson2.do?id="+id;
    }
    
    /**
     * 고객사의 기본정보를 저장한다.(인원현황)
     * @param personStatic
     * @param id
     * @return
     */
    @RequestMapping(value="/savePersonStatic.do")
    public String savePersonStatic (PersonStatic personStatic){
        
        customerFinanceService.savePersonStatic(personStatic, personStatic.getId());
        
        return "redirect:/support/customer/customerFinance/modifyPersonStatic.do?id="+personStatic.getId();
    }
    
    /**
     * 고객사의 기본정보를 저장한다.( 주거래선)
     * @param mainBusiness
     * @param buyingCnt
     * @param sellingCnt
     * @return
     */
    @RequestMapping(value="/saveMainBusiness.do")
    public String saveMainBusiness (MainBusiness mainBusiness,@RequestParam("buyingCnt")String buyingCnt,
            @RequestParam("sellingCnt")String sellingCnt){
        
        customerFinanceService.saveMainBusiness(mainBusiness, buyingCnt, sellingCnt);
        
        
        return "redirect:/support/customer/customerFinance/modifyMainBusiness.do?id="+mainBusiness.getId();
    }
    
    
    /**
     * 고객사의 기본정보를 저장한다.(관계사)
     * @param relationCompany
     * @param companyCnt
     * @return
     */
    @RequestMapping(value="/saveRelationCompany.do")
    public String saveRelationCompany (RelationCompany relationCompany, 
             @RequestParam("companyCnt") String companyCnt){
    
        customerFinanceService.saveRelationCompany(relationCompany , companyCnt);
                
        return "redirect:/support/customer/customerFinance/modifyBasicRelationCompany.do?id="+relationCompany.getId();
        
    }
    
    /**
     * 고객 세분화 등급 참조 팝업창을 띄운다.
     *
     */
    @RequestMapping(value="/popupCustomerGrade.do")
    public void popupCustomerGrade (){
        
    }
    
    /**
     * 고객사의 기본정보 삭제 (delete_flag = Y)
     * @param id
     * @return
     */
    @RequestMapping(value="/deleteFinance.do")
    public String deleteFinance(@RequestParam("id")String id){
        
        customerFinanceService.deleteFinance(id);
        
        customerFinanceService.deleteFinanceCondition(id);
        
        customerFinanceService.deleteFinanceRate(id);
        
        return "redirect:/support/customer/customerFinance/financeList.do";
        
    }
    
    
    @RequestMapping(value="/popupSAPSync.do")
    public ModelAndView popupSAPSync (FinanceSearchCondition searchCondition, 
            @RequestParam(value="searchConditionString",required = false)String searchConditionString
            ,@RequestParam(value="id",required=false)String id,
            @RequestParam(value="name",required=false)String sendName){
        
        if(sendName !=null){
            searchCondition.setSearchWord( sendName );
            searchCondition.setSearchColumn( "name" );
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
        
        SearchResult<Finance> searchResult = this.customerFinanceService.listSAPFinancebySearchCondition(searchCondition);
        
        return new ModelAndView().addObject( "searchResult",searchResult )
        .addObject( "searchCondition", searchCondition )
        .addObject( "searchConditionString", tempSearchConditionString )
        .addObject("boardCode",new BoardCode())
        .addObject( "id" , id );
        
    }

    
    @RequestMapping(value="/detailFinanceForSAP.do")
    public ModelAndView detailFinanceForSAP (@RequestParam("sapId")String sapId,@RequestParam(value="id",required=false)String id){
        
        String selectedCustomerGubun = "";
        String selectedSubdivisionGrade = "";
        ModelAndView mav = new ModelAndView();
        Finance finance = null;
        if(id !=null && id != ""){
            finance = customerFinanceService.readFinance( id );
            Finance financeSap = customerFinanceService.readFinanceForSap( sapId );
            
            finance.setSapId( financeSap.getSapId() );
            finance.setName( financeSap.getName() );
            finance.setRegno( financeSap.getRegno() );
            
            String regno = financeSap.getRegno();
            if(!regno.equals( "" ) && !regno.equals( null )){
                String regno1 = regno.substring( 0, 3 );
                String regno2 = regno.substring( 3, 5 );
                String regno3 = regno.substring( 5, 10 );
                finance.setRegno1( regno1 );
                finance.setRegno2( regno2 );
                finance.setRegno3( regno3 );
            }
            
            String corporationNo = financeSap.getCorporationNo();
            if(!corporationNo.equals( "" ) && !corporationNo.equals( null )){
                String corporationNo1 = corporationNo.substring( 0,6 );
                String corporationNo2 = corporationNo.substring( 6, 13 );
                
                finance.setCorporationNo1( corporationNo1 );
                finance.setCorporationNo2( corporationNo2 );
            }
            
            
            finance.setCorporationNo( financeSap.getCorporationNo() );
            finance.setDirector( financeSap.getDirector() );
            finance.setType( financeSap.getType() );
            finance.setJijongType( financeSap.getJijongType() );
            finance.setPostNo( financeSap.getPostNo() );
            finance.setAddress1( financeSap.getAddress1() );
            finance.setAddress2( financeSap.getAddress2() );
            finance.setTelNo( financeSap.getTelNo() );
            finance.setFax( financeSap.getFax() );
            finance.seteMail( financeSap.geteMail() );
            
            
        }else{
            finance  = customerFinanceService.readFinanceForSap(sapId);
        }
        
        return mav.addObject( "finance", finance )        
        .addObject( "selectedCustomerGubun", selectedCustomerGubun )
        .addObject( "selectedSubdivisionGrade", selectedSubdivisionGrade );  
       
    }
    
    /**
     * sap에도 정보없고, ep에도 정보없을 경우 저장 화면 출력
     * @param sapId
     * @param id
     * @return
     */
    @RequestMapping(value="/detailFinanceForSAP2.do")
    public ModelAndView detailFinanceForSAP (@RequestParam("name")String name){
        
        String selectedCustomerGubun = "";
        String selectedSubdivisionGrade = "";
        ModelAndView mav = new ModelAndView();
        Finance finance = new Finance();
        
        finance.setName( name );
        
        return mav.addObject( "finance", finance )        
        .addObject( "selectedCustomerGubun", selectedCustomerGubun )
        .addObject( "selectedSubdivisionGrade", selectedSubdivisionGrade );  
       
    }
    
    
    

}
