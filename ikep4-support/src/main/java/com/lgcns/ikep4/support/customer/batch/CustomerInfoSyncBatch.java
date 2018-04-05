package com.lgcns.ikep4.support.customer.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.support.customer.service.CustomerBasicInfoService;
import com.lgcns.ikep4.support.customer.service.EpCustomerBuyingInfoRcvService;
import com.lgcns.ikep4.support.customer.service.EpCustomerRcvService;
import com.lgcns.ikep4.util.jco.EpCustomerBuyingInfoRcvRFC;
import com.lgcns.ikep4.util.jco.EpCustomerRcvRFC;


/**
 * 
 * SAP 고객 정보 배치 처리 클래스 
 *
 * @author SongHeeJung
 * @version $Id$
 */
public class CustomerInfoSyncBatch extends QuartzJobBean{

    private CustomerBasicInfoService customerBasicInfoService;
    private EpCustomerRcvService epCustomerRcvService;
    private EpCustomerBuyingInfoRcvService epCustomerBuyingInfoRcvService;
    
    
    /**
     * 고객정보 배치 처리
     */

    @Override
    protected void executeInternal( JobExecutionContext context ) throws JobExecutionException {
            Log log = LogFactory.getLog(this.getClass());
        
        try{
            
            log.debug( "==============CustomerInfoSyncBatch Start ===================" );
            
            SchedulerContext schedulerContext = context.getScheduler().getContext();
            ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");
           
            this.customerBasicInfoService = (CustomerBasicInfoService) appContext.getBean( "customerBasicInfoServiceImpl" );
            this.epCustomerRcvService = (EpCustomerRcvService) appContext.getBean( "epCustomerRcvServiceImpl" );
            this.epCustomerBuyingInfoRcvService = (EpCustomerBuyingInfoRcvService) appContext.getBean( "epCustomerBuyingInfoRcvServiceImpl" );
            
            // SAP 에서 데이터를 가져온다
            log.debug("=== CustomerInfoSyncBatch Receive Data from SAP ===");  
            
           // EpCustomerRcvService epCustomerRcvService = new EpCustomerRcvService();
            this.customerBasicInfoService.deleteSapCustomer();
            this.customerBasicInfoService.updateSapCustomer( this.epCustomerRcvService.EpCustomerRcv( null ) );
            //sap sync -- heejung
            this.customerBasicInfoService.basicInfoSAPSync( this.epCustomerRcvService.EpCustomerRcv( null ) );
            
           // EpCustomerBuyingInfoRcvRFC epCustomerBuyingInfoRcv  = new EpCustomerBuyingInfoRcvRFC();
            this.customerBasicInfoService.deleteSapCustomerBuyingInfo();
            this.customerBasicInfoService.updateSapCustomerBuyingInfo( this.epCustomerBuyingInfoRcvService.EpCustomerBuyingRcv( null ) );
               
            
        }catch(Exception e)
        {
            throw new IKEP4ApplicationException( "", e );
        }
        
        log.debug( "==========CustomerInfoSyncBatch end ==============" );
    }

}
