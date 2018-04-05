<%@ page import="javax.portlet.RenderRequest" %>
<%@ page import="com.liferay.portal.util.WebKeys" %>

pageContext.getOut().print(renderRequest.getAttribute(WebKeys.PORTLET_CONTENT));