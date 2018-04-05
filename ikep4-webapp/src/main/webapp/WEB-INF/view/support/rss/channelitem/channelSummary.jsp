<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.rss.channelitem.header" /> 
<c:set var="preList"    value="ui.support.rss.channelitem.list" />
<c:set var="preDetail"  value="ui.support.rss.channelitem.detail" />
<c:set var="preButton"  value="ui.support.rss.button" /> 
<c:set var="preMessage" value="ui.support.rss.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
//<!--
(function($) {
	$(document).ready(function() {	
		// left menu setting
		iKEP.setLeftMenu();		
	});
})(jQuery);
//-->
</script>
				<a href="#a" ><ikep4j:message pre='ui.support.rss.channel.list' key='main.title' /></a>
				<ul>						
						<c:choose>
						    <c:when test="${searchResult.emptyRecord}">
						       <ikep4j:message pre='${preMessage}' key='list.empty'/>
						       <script type="text/javascript">$jq("input[name=catCount]").val(0)</script>
					        </c:when>
						    <c:otherwise>
						       <script type="text/javascript">$jq("input[name=catCount]").val(1)</script>
						        <li class="no_child" style="padding-bottom: 7px;"><a href="javascript:getListByChannel('')"><ikep4j:message pre='${preList}' key='all' /></a></li>
								<c:forEach var="category" items="${searchResult.entity}">
									<li class="no_child" ><a href="#a" onclick="javascript:getListByCategory('${category.categoryId}')">${category.categoryName}</a>
									<ul class="qnalist_sub">
									  <c:forEach var="channel" items="${category.channel}">
                                         <li class="no_child" ><a href="#a" onclick="javascript:getListByChannel('${channel.channelId}')">${channel.channelTitle}</a></li>
                                       </c:forEach>     
                                    <ul>
                                    </li>
								</c:forEach>				      
						    </c:otherwise> 
					   </c:choose>  
				</ul>					

			
	
				
				
