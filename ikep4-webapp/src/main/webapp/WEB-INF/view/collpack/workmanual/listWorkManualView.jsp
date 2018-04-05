<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
 
<%-- 메시지 관련 Prefix 선언 Start <%@ include file="/base/common/iframelibs.jsp"%>  --%> 
<c:set var="prefix">ui.collpack.workmanual.listWorkManualView</c:set>
<c:set var="messagePrefix">ui.collpack.workmanual.message</c:set>
<c:set var="buttonPrefix">ui.collpack.workmanual.button</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
//<![CDATA[
(function($) { 
	$jq(document).ready(function(){
		// 더보기 Click
		$jq("#showMoreButton").click(function() {
			$jq.get('<c:url value="/collpack/workmanual/addManualPage.do?endRowNum="/>' + $jq('#showMoreForm>input[name=endRowNum]').val())
			.success(function(data) {
				$jq("#addPage").append(data);
				isShowMoreButton();
			})
			.error(function(event, request, settings) { alert("addPage error"); });
		});

		isShowMoreButton();
	});
	
	//더보기 버튼 제어
	isShowMoreButton = function() {
		var maxNum = $jq("#showMoreForm>input[name=maxRowNum]").val();
		var endNum = $jq("#showMoreForm>input[name=endRowNum]").val();
		//alert(maxNum + "-" + endNum) ;
		if (eval(maxNum) <= eval(endNum)) {
			$jq('#showMoreButton').css("display","none");
		}
	}
	
	//태그 검색
	tagSearch = function() {
		var tag = $jq("#tagText").val();
		if ("" != tag) {
			iKEP.tagging.tagSearchByName(tag);
		}
	}
	
})(jQuery);
//]]>
</script>

<div id="tagResult">
				<h1 class="none"></h1>

				<!--pageTitle Start-->
				<div id="pageTitle_main" style="padding-top:8px;">
					<h2><ikep4j:message pre="${prefix}" key="search.title"/></h2>
					<div class="search_con">
						<input id="tagText" class="inputbox" title="search" type="text" />
						<div class="search_con_btn"><a href="#a" onclick="tagSearch()"><img src="<c:url value="/base/images/icon/ic_search_con.gif"/>" alt="" /></a></div>
					</div>
					<div class="search_tag">
						<img src="<c:url value="/base/images/icon/ic_favorite_2.gif"/>" alt="" />
						<c:forEach var="tag" items="${popularTagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
					</div>					
				</div>
				<!--//pageTitle End-->
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre="${prefix}" key="main.title"/></h3>
				</div>
				<!--//subTitle_2 End-->
				
				<!--blockListTable Start-->
				<div class="blockListTable summaryView">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
						<caption></caption>						
						<tbody id="addPage">
							<c:choose>
							    <c:when test="${fn:length(manualList) == 0}">
							    	<tr><td><ikep4j:message pre='${prefix}' key='nothing.manual'/></td></tr>
							    </c:when>
							    <c:otherwise>
									<c:forEach var="item" items="${manualList}">
										<tr>
											<td>
												<div class="summaryViewTitle">
													<c:if test="${item.manualType == 'A'}">[<ikep4j:message pre='${messagePrefix}' key='manual.submit'/>]</c:if>
													<c:if test="${item.manualType == 'B'}">[<ikep4j:message pre='${messagePrefix}' key='manual.submit.revision'/>]</c:if>
													<c:if test="${item.manualType == 'C'}">[<ikep4j:message pre='${messagePrefix}' key='manual.disuse'/>]</c:if>
													 &nbsp;<a href="<c:url value='/collpack/workmanual/readManualView.do?manualId=${item.manualId}&amp;pathStep=A'/>">${item.title}</a>&nbsp;(<ikep4j:message pre='${prefix}' key='table.version'/>:${item.version})
												</div>
												<div class="summaryViewInfo">
													<span class="summaryViewInfo_name">
														<c:choose>
														    <c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.updaterName}&nbsp;${item.jobTitleName}</c:when>
														    <c:otherwise>${item.updaterEnglishName}&nbsp;${item.jobTitleEnglishName}</c:otherwise>
													    </c:choose> 
													</span>
													<img src="<c:url value="/base/images/common/bar_info.gif"/>" alt="" />
													<span><ikep4j:timezone date="${item.updateDate}" pattern="yyyy.MM.dd HH:mm"/></span>
													<img src="<c:url value="/base/images/common/bar_info.gif"/>" alt="" />
													<span><ikep4j:message pre="${prefix}" key="table.search"/> <strong>${item.hitCount}</strong></span>
												</div>
												<div class="summaryViewCon">${item.contents}</div>
												<div class="summaryViewTag">
												  <span class="ic_tag"><span><ikep4j:message pre="${prefix}" key="table.tag"/></span></span>
												  <c:forEach var="tag" items="${item.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
												</div>
											</td>
										</tr>
									</c:forEach>
							    </c:otherwise>
						    </c:choose> 																																															
						</tbody>
					</table>				
				</div>
				<!--//blockListTable End-->
				
				<!--blockButton_3 Start-->
				<div class="blockButton_3">		
					<a class="button_3" id="showMoreButton" href="#a"><span><ikep4j:message pre='${buttonPrefix}' key='moreShow'/><img src="<c:url value="/base/images/icon/ic_more_ar.gif"/>" alt="<ikep4j:message pre='${buttonPrefix}' key='moreShow'/>" /></span></a>
					<form id="showMoreForm" action="">
						<input type="hidden" name="maxRowNum" value="${maxRowNum}"/>
						<input type="hidden" name="endRowNum" value="${endRowNum}"/>
					</form>	
				</div>
				<!--//blockButton_3 End-->
</div>				