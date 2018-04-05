<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="preTagCloudMenu" value="ui.socialpack.socialblog.management.designsetting.layout.portlet" />

						<!--sMenu Start-->
						<div class="sMenu">
							<h3><ikep4j:message pre='${preTagCloudMenu}' key='tagCloud'/></h3>
							<script type="text/javascript">
							<!--   
							(function($){	
  								iKEP.createTagEmbedObject("#socialBlogTagList", "<c:url value='/socialpack/socialblog/getSocialBlogTagXml.do?blogOwnerId=${blogOwnerId}'/>", "blockContentsArea");
							})(jQuery);  
							//-->
							</script> 
							<div id="socialBlogTagList">
							</div>
						</div>
						<!--//sMenu End-->


