
package com.lgcns.ikep4.portal.util.spring;

import org.apache.log4j.Logger;

/**
 * <PRE>
 * Spring의 BeanFactory 및 Bean을 제공하는 Util이다.
 * </PRE>
 */
public final class SpringFactoryUtil {
    
    private static final Logger log = Logger.getLogger(SpringFactoryUtil.class);
    
    private static SpringFactoryUtil sInstance;
    
    /**
     * Singleton을  구성된 getInstance() method
     */
    public synchronized static SpringFactoryUtil getInstance() { 
        if (sInstance == null) {
            sInstance = new SpringFactoryUtil();
        }
        return sInstance;
    }

    
    /**
     * 생성된 Factory에서 bean을 return해주는 method
     */
    public static Object getBean(String beanName) {
    	try{
            if(log.isDebugEnabled()) { log.debug("[getBean]" + beanName); }
            Object rtnBean = SpringFactoryLoader.getContext().getBean(beanName);
            return rtnBean;
    	} catch(Exception e) {	
    		return null;
    	}
    }
}
