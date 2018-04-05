/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lgcns.ikep4.portalengine.driver;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pluto.container.PortletContainer;
import org.apache.pluto.container.PortletContainerException;
import org.apache.pluto.driver.AttributeKeys;
import org.apache.pluto.driver.PortalDriverServlet;
import org.apache.pluto.driver.core.PortalRequestContext;
import org.apache.pluto.driver.core.PortletWindowImpl;
import org.apache.pluto.driver.services.portal.PageConfig;
import org.apache.pluto.driver.services.portal.PortletWindowConfig;
import org.apache.pluto.driver.url.PortalURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TCK Driver Servlet.
 *
 * @version 1.0
 * @since Dec 11, 2005
 */
public class IKEPPortletDriverServlet extends PortalDriverServlet {
	
	/** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(IKEPPortletDriverServlet.class);
    
    public static final String DEFAULT_PAGE_URI =
		"/WEB-INF/themes/ikep4-default-theme.jsp";

    
    public String getServletInfo() {
        return "Pluto TCK Driver Servlet";
    }

    public void init() {
        super.init();
        ServletContext servletContext = getServletContext();
        container = (PortletContainer) servletContext.getAttribute(
                AttributeKeys.PORTLET_CONTAINER);
     }
    
    /**
     * Overwrites <code>super.doGet(..)</code>. If <code>portletName</code>
     * (multiple occurrences) parameter is received, the driver is attempting
     * to create a new page. This page must be setup and then redirected to the
     * actual page. Otherwise, the driver calls <code>super.doGet(..)</code>
     * to continue as normal.
     * @param request  the incoming servlet request.
     * @param response  the incoming servlet response.
     * @throws IOException
     * @throws ServletException
     */
    
    /*
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        String[] portletNames = request.getParameterValues("portletName");
        if (portletNames != null && portletNames.length > 0) {
        	debugWithName("Initializing new TCK page...");
            doSetup(request, response);
        } else {
        	debugWithName("No portlet names specified. Continue as normal.");
        	super.doGet(request, response);
        }
    }
    */
    
    /**
     * Handle all requests. All POST requests are passed to this method.
     * @param request  the incoming HttpServletRequest.
     * @param response  the incoming HttpServletResponse.
     * @throws ServletException  if an internal error occurs.
     * @throws IOException  if an error occurs writing to the response.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        if (LOG.isDebugEnabled()) {
        	LOG.debug("Start of PortalDriverServlet.doGet() to process portlet request . . .");
        }
        
        String portletName = request.getParameter("portletName");

        PortalRequestContext portalRequestContext =
            new PortalRequestContext(getServletContext(), request, response);

        PortalURL portalURL = null;
        
        try {
        	portalURL = portalRequestContext.getRequestedPortalURL();
        } catch(Exception ex) {
        	String msg = "Cannot handle request for portal URL. Problem: "  + ex.getMessage();
        	LOG.error(msg, ex);
        	throw new ServletException(msg, ex);
        }
        
        String actionWindowId = portalURL.getActionWindow();
        String resourceWindowId = portalURL.getResourceWindow();
        
        PortletWindowConfig actionWindowConfig = null;
        PortletWindowConfig resourceWindowConfig = null;
        
		if (resourceWindowId != null){
			resourceWindowConfig = PortletWindowConfig.fromId(resourceWindowId);
		} else if(actionWindowId != null){
			 actionWindowConfig = PortletWindowConfig.fromId(actionWindowId);
		}

        // Action window config will only exist if there is an action request.
        if (actionWindowConfig != null) {
            PortletWindowImpl portletWindow = new PortletWindowImpl(container,
            		actionWindowConfig, portalURL);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Processing action request for window: "
                		+ portletWindow.getId().getStringId());
            }
            try {
                container.doAction(portletWindow, request, response);
            } catch (PortletContainerException ex) {
            	LOG.error(ex.getMessage(), ex);
                throw new ServletException(ex);
            } catch (PortletException ex) {
            	LOG.error(ex.getMessage(), ex);
                throw new ServletException(ex);
            }
            if (LOG.isDebugEnabled()) {
            	LOG.debug("Action request processed.\n\n");
            }
        }
        //Resource request
        else if (resourceWindowConfig != null) {
            PortletWindowImpl portletWindow = new PortletWindowImpl(container,
                               resourceWindowConfig, portalURL);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Processing resource Serving request for window: "
                               + portletWindow.getId().getStringId());
            }
            try {
                container.doServeResource(portletWindow, request, response);
            } catch (PortletContainerException ex) {
            	LOG.error(ex.getMessage(), ex);
                throw new ServletException(ex);
            } catch (PortletException ex) {
            	LOG.error(ex.getMessage(), ex);
                throw new ServletException(ex);
            }
            if (LOG.isDebugEnabled()) {
               LOG.debug("Resource serving request processed.\n\n");
            }
        }
        // Otherwise (actionWindowConfig == null), handle the render request.
        else {
        	if (LOG.isDebugEnabled()) {
        		LOG.debug("Processing render request.");
        	}
            PageConfig pageConfig = portalURL.getPageConfig(super.getServletContext());
            if (pageConfig == null)
            {
            	String renderPath = (portalURL == null ? "" : portalURL.getRenderPath());
                String msg = "PageConfig for render path [" + renderPath + "] could not be found.";
                LOG.error(msg);
                throw new ServletException(msg);
            }
            if(portletName == null) {
            	portletName = portalURL.getPortletModes().keySet().iterator().next();
            }
            
            request.setAttribute(AttributeKeys.CURRENT_PAGE, pageConfig);
            String uri = (pageConfig.getUri() != null)
            		? pageConfig.getUri() : DEFAULT_PAGE_URI;
            if (LOG.isDebugEnabled()) {
            	LOG.debug("Dispatching to: " + uri);
            }
            
            request.setAttribute("portletId", portletName);
            request.setAttribute("portletAreaId", portletName.replace(".","_"));
            
            RequestDispatcher dispatcher = request.getRequestDispatcher(uri);
            dispatcher.forward(request, response);
            if (LOG.isDebugEnabled()) {
            	LOG.debug("Render request processed.\n\n");
            }
        }
    }

    

    public void doPost(HttpServletRequest req, HttpServletResponse response)
    throws IOException, ServletException {
        super.doGet(req, response);
    }
    
    
    // Private Methods ---------------------------------------------------------
    
    /*
    private void doSetup(HttpServletRequest request,
                         HttpServletResponse response)
    throws IOException, ServletException {
        String[] portletNames = request.getParameterValues("portletName");
        String pageName = request.getParameter("pageName");
        if (pageName != null) {
        	debugWithName("Retrieved page name from request: " + pageName);
        } else {
        	debugWithName("Creating page name...");
        	AdminConfiguration adminConfig = (AdminConfiguration)
            		getServletContext()
            		.getAttribute(AttributeKeys.DRIVER_ADMIN_CONFIG);
            if (adminConfig == null) {
                throw new ServletException("Invalid configuration: "
                		+ "an AdminConfiguration must be specified "
                		+ "to run the TCK.");
            }
            
            pageName = (new DecimalFormat("TCK00000")).format(pageCounter++);
            PageConfig pageConfig = new PageConfig();
            pageConfig.setName(pageName);
            pageConfig.setUri(DEFAULT_PAGE_URI);
            for (int i = 0; i < portletNames.length; i++) {
            	debugWithName("Processing portlet name: " + portletNames[i]);
                int index = portletNames[i].indexOf('/');
                String contextPath = "/" + portletNames[i].substring(0, index);
                String portletName = portletNames[i].substring(index + 1);
                pageConfig.addPortlet(contextPath, portletName);
//                adminConfig.getPortletRegistryAdminService()
//                		.addPortletApplication(contextPath);
            }

            adminConfig.getRenderConfigAdminService().addPage(pageConfig);
            debugWithName("Created TCK Page: " + pageName);
        }

        // The other possibility would be to redirect to the actual portal.
        //   I'm not sure which is better at this point.
        StringBuffer buffer = new StringBuffer();
        buffer.append(request.getRequestURL().toString());
        if (!request.getRequestURL().toString().endsWith("/")) {
        	buffer.append("/");
        }
        buffer.append(pageName);
        debugWithName("Sending redirect to: " + buffer.toString());
        response.sendRedirect(buffer.toString());
    }
    
    */
    
    /**
     * Prints debug message with a <code>[Pluto TCK Driver]</code> prefix.
     * @param message  message to debug.
     */
    /*
    private void debugWithName(String message) {
    	if (LOG.isDebugEnabled()) {
    		LOG.debug("[Pluto TCK Driver] " + message);
    	}
    }
    */
    
}
