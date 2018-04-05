<%@ include file="/base/common/taglibs.jsp"%>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.favorite.header" /> 
<c:set var="preList"    value="ui.support.favorite.list" />
<c:set var="preDetail"  value="ui.support.favorite.detail" />
<c:set var="preButton"  value="ui.support.favorite.button" /> 
<c:set var="preMessage" value="ui.support.favorite.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript" src="<c:url value="/base/js/units/support/favorite/favorite.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/support/favorite/listForm.js"/>"></script>
<script type="text/javascript">
(function($) {
	$(document).ready(function() {
		initialBody();
	});
})(jQuery);
</script>


<!--skipNavigation Start-->
<div id="skipNavigation">
</div>
<!--//skipNavigation End-->			
			
<!--wrapper Start-->
<div id="wrapper">
		
		<!--blockMain Start-->
		<div id="blockMain">

			<!--mainContents Start-->
			<div id="mainContents" class="conPPS">
			
				<h1><ikep4j:message pre='${preDetail}' key='recentDocument' /></h1>
				
				<form id="searchForm" method="post" action="<c:url value="/support/recent/getSubListForDocument.do" />" onsubmit="return false">  
				
				<spring:bind path="searchCondition.registerId">
					<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
				</spring:bind>	
				
				<spring:bind path="searchCondition.userLocaleCode">
					<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
				</spring:bind>	
				
				<spring:bind path="searchCondition.recordCount">
					<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
				</spring:bind>	
				
				<spring:bind path="searchCondition.currentCount">
					<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
				</spring:bind>	
				
				<spring:bind path="searchCondition.pageIndex">
					<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" />
				</spring:bind>
						
					<div class="listInfo">
						<div class="tab tab_v1 tab_v2">
							<ul>
								<li class="on">
									<a href="#"><ikep4j:message pre='${preDetail}' key='bbs' /></a>
									<input type="hidden" name="moduleCodeList"  value="BBS" />
								</li>
								<!-- li class="on">
									<a href="#"><ikep4j:message pre='${preDetail}' key='cafe' /></a>
									<input type="hidden" name="moduleCodeList"  value="Cafe" />
								</li-->
								<c:if test="${CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_FULL}">
									<li class="on">
										<a href="#"><ikep4j:message pre='${preDetail}' key='collaboration' /></a>
										<input type="hidden" name="moduleCodeList"  value="Workspace" />
									</li>
									<!--li class="on">
										<a href="#"><ikep4j:message pre='${preDetail}' key='blog' /></a>
										<input type="hidden" name="moduleCodeList"  value="Blog" />
									</li-->
									<!--li class="on">
										<a href="#"><ikep4j:message pre='${preDetail}' key='manual' /></a>
										<input type="hidden" name="moduleCodeList"  value="Manual" />
									</li-->
									<li class="on">
										<a href="#"><ikep4j:message pre='${preDetail}' key='qna' /></a>
										<input type="hidden" name="moduleCodeList"  value="QA" />
									</li>
									<!--li class="on">
										<a href="#"><ikep4j:message pre='${preDetail}' key='forum' /></a>
										<input type="hidden" name="moduleCodeList"  value="Forum" />
									</li-->
									<!--li class="on">
										<a href="#"><ikep4j:message pre='${preDetail}' key='voca' /></a>
										<input type="hidden" name="moduleCodeList"  value="Corporate Voca" />
									</li-->
									<li class="on">
										<a href="#"><ikep4j:message pre='${preDetail}' key='idea' /></a>
										<input type="hidden" name="moduleCodeList"  value="Idea" />
									</li>
									<li class="on">
                                        <a href="#"><ikep4j:message pre='${preDetail}' key='kms' /></a>
                                        <input type="hidden" name="moduleCodeList"  value="KMS" />
                                    </li>
								</c:if>
							</ul>
						</div>							
					</div>											
					
					<!--conSearch_2 Start-->
					<div class="conSearch_2">
						<spring:bind path="searchCondition.searchColumn">  
							<input name="${status.expression}" id="${status.expression}" type="hidden" title="search" value="title" />
						</spring:bind>
						<spring:bind path="searchCondition.searchWord"> 
							<input  name="${status.expression}" id="${status.expression}" type="text" title="search" value="" onfocus="this.value=''"/>
						</spring:bind>
						
						<a href="#a" onclick="javascript:getListAndClear()" class="sel_btn"><span>Search</span></a>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>	
					<!--//conSearch_2 End-->	
				
				<div class="blockBlank_20px"></div>
				
				<!--Contents Start-->
				<!--blockListTable Start-->
				
						<div class="MyContentsTable" >	
							
						<table summary="<ikep4j:message pre='${preDetail}' key='favoriteDocument' />">
							<caption></caption>
							<tbody id="listDiv">
							
							
								<tr><td></td></tr>
							
																																																								
							</tbody>
						</table>
						
						</div>
						
						
				<!--//blockListTable End-->	
				
				<!--blockButton_3 Start-->
				<div id="moreDiv" class="blockButton_3" onclick="getMore()"> 
					<a class="button_3" href="#a"><span><ikep4j:message pre='${preMessage}' key='list.more' /> <img src="<c:url value="/base/images/icon/ic_more_ar.gif" />" alt="" /></span></a>				
				</div>
				
				<div id="emptyDiv" class="blockButton_3" > 
					<a class="button_3" href="#a"><span><ikep4j:message pre='${preMessage}' key='list.empty' /> </span></a>				
				</div>
				<!--//blockButton_3 End-->	
				<!--//Contents End-->		
				
				</form>							

			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
			
		</div>
		<!--//blockMain End-->
	
	
</div>
<!--//wrapper End-->
