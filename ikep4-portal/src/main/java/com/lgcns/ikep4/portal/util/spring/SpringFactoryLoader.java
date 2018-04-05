package com.lgcns.ikep4.portal.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * <PRE>
 * Spring의 BeanFactory 를 위해서 Context를 제공하는 Loader.
 * </PRE>
 */
@SuppressWarnings("static-access")
@Service
public final class SpringFactoryLoader implements ApplicationContextAware{
    private static ApplicationContext context;
    public static ApplicationContext getContext() {
    	return context;
    }
	
	//@Override
	public void setApplicationContext(ApplicationContext paramApplicationContext) throws BeansException {
		this.context = paramApplicationContext;
	}
}
