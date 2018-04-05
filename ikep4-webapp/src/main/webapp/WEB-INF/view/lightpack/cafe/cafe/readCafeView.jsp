<%--
=====================================================
	* 기능설명	:	cafe 내용 조회
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"	value="message.lightpack.cafe.cafe.viewCafe.header" />
<c:set var="preSearch"	value="message.lightpack.cafe.cafe.viewCafe.search" />
<c:set var="preDetail"	value="message.lightpack.cafe.cafe.viewCafe.detail" />
<c:set var="preButton"	value="message.lightpack.cafe.cafe.viewCafe.button" />
<c:set var="preScript"	value="message.lightpack.cafe.cafe.viewCafe.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript">
<!-- 
(function($) {
	
	updateCafeView = function() {
		$jq('form[name=mainForm]').attr({
			action:"<c:url value="/lightpack/cafe/cafe/updateCafeView.do"/>",
			method:"GET"
		});
		$jq("input[name=cafeStatus]").val(status);
		$jq("#mainForm").submit(); 
	}
	updateStatus = function(status) {
		$jq('form[name=mainForm]').attr({
			action:"<c:url value="/lightpack/cafe/cafe/updateCafeStatus.do"/>",
			method:"POST"
		});
		$jq("input[name=cafeStatus]").val(status);
		$jq("#mainForm").submit(); 
	}
	updateCloseRejectStatus = function(status) {
		$jq('form[name=mainForm]').attr({
			action:"<c:url value="/lightpack/cafe/cafe/updateCafeCloseRejectStatus.do"/>",
			method:"POST"
		});
		$jq("input[name=cafeStatus]").val(status);
		$jq("#mainForm").submit(); 
	}	
	deleteCafe = function() {
		$jq('form[name=mainForm]').attr({
			action:"<c:url value="/lightpack/cafe/cafe/deleteCafe.do"/>",
			method:"POST"
		});
		$jq("#mainForm").submit(); 		
	}		
	listCafe = function() {
		$jq('form[name=mainForm]').attr({
			action:"<c:url value="/lightpack/cafe/cafe/${searchCondition.listUrl}"/>",
			method:"GET"
		});
		$jq("#mainForm").submit(); 
	}
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 

	   iKEP.iFrameContentResize();  
	});
})(jQuery);  
//-->
</script>


<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<!--pageTitle Start-->

<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
</div> 

<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="post" action="<c:url value='/lightpack/cafe/cafe/readCafeView.do' />">  
<spring:bind path="searchCondition.sortColumn">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 		
<spring:bind path="searchCondition.sortType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.cafeId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.cafeStatus">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.listType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.listUrl">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>

<input name="cafeIds" id="cafeIds" type="hidden" value="${cafe.cafeId}" />


<div class="blockDetail">

	<table summary="<ikep4j:message pre="${preList}" key="summary" />">
		<caption></caption>
		<col style="width: 18%;"/>
		<col style="width: 32%;"/>
		<col style="width: 18%;"/>
		<col style=""/>	
		<tbody>
	
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registerName' /></th>
			<td>
			<c:choose>
			<c:when test="${user.localeCode == portal.defaultLocaleCode}">
				${cafe.registerName}
			</c:when>
			<c:otherwise>
				${cafe.registerEnglishName}
			</c:otherwise>
			</c:choose>
			</td>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registDate' /></th>
			<td><ikep4j:timezone pattern="yyyy.MM.dd" date="${cafe.registDate}"/></td>
		</tr>
		
		<tr>
			
			<th scope="row"><span id="span_category_text"></span><ikep4j:message pre='${preDetail}' key='categoryName' /></th>
			<td colspan="3">
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${cafe.categoryName}
				</c:when>
				<c:otherwise>
					${cafe.categoryEnglishName}
				</c:otherwise>
				</c:choose>	
			</td>			
		</tr>
					
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='cafeName' /></th>
			<td colspan="3">${cafe.cafeName}</td>
		</tr>
		
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='introImage' /></th>
			<td colspan="3">
			<input name="fileId" type="hidden" value="" title="fileId" />
			<span id="viewDiv">
			<c:forEach var="fileDataList" items="${cafe.fileDataList}" varStatus="fileLoop">
			<img id='viewImageDiv' src='<c:url value="/support/fileupload/downloadFile.do"/>?fileId=${fileDataList.fileId}' width='110' height='90'/>
			</c:forEach>
			</span>	
			</td>
		</tr>	
		

		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='cafeStatus' /></th>
			<td>
				<c:choose>
		    		<c:when test="${cafe.cafeStatus=='O'}"> 
						<ikep4j:message pre='${preDetail}' key='O' />
					</c:when>
		    		<c:when test="${cafe.cafeStatus=='WO'}"> 
						<ikep4j:message pre='${preDetail}' key='WO' />
					</c:when>
					<c:when test="${cafe.cafeStatus=='WC'}"> 
						<ikep4j:message pre='${preDetail}' key='WC' />
					</c:when>
		    		<c:when test="${cafe.cafeStatus=='C'}"> 
						<ikep4j:message pre='${preDetail}' key='C' />
					</c:when>
					<c:when test="${cafe.cafeStatus=='WR'}"> 
						<ikep4j:message pre='${preDetail}' key='WR' />
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>			
			</td>
			<th scope="row"><span id="span_category_text"></span><ikep4j:message pre='${preDetail}' key='sysopName' /></th>
			<td>
				<a href="#a" onclick="iKEP.showUserContextMenu(this, '${cafe.sysopId}', 'bottom')" >
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${cafe.sysopName}
				</c:when>
				<c:otherwise>
					${cafe.sysopEnglishName}
				</c:otherwise>
				</c:choose>
				</a>				
			</td>			
		</tr>
		
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='member' /></th>
			<td colspan="3">
			<c:forEach var="memberList" items="${cafe.memberList}">
				<c:choose>
				<c:when test="${user.localeCode == portal.defaultLocaleCode}">
					${memberList.memberName } - ${memberList.teamName }- ${memberList.jobTitleName }<br/>
				</c:when>
				<c:otherwise>
					${memberList.memberEnglishName } - ${memberList.teamEnglishName }- ${memberList.jobTitleEnglishName }<br/>
				</c:otherwise>
				</c:choose>	
			</c:forEach>
			</select></td>
		</tr>

		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='description' /></th>
			<td colspan="3">${cafe.description}</td>
		</tr>

		<tr>
			<th scope="row">&nbsp;<ikep4j:message pre='${preDetail}' key='tag' /></th>
			<td colspan="3">
			<c:forEach var="tag" items="${cafe.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if>${tag.tagName}</c:forEach>
			</td>
		</tr>
		</tbody>
	</table>

	
	
</div>
</form>
<!--//blockListTable End--> 
	 
<!--blockButton Start-->
<div class="blockButton">
<ul>
	<c:choose>
		<c:when test="${cafe.cafeStatus=='WO'}"> 
			<c:if test="${isSystemAdmin}">
			<li><a class="button" href="#a" onclick="updateStatus('O')" title="<ikep4j:message pre='${preButton}' key='approveCafe'/>"><span><ikep4j:message pre='${preButton}' key='approveCafe'/></span></a></li>
			<li><a class="button" href="#a" onclick="updateStatus('WR')" title="<ikep4j:message pre='${preButton}' key='rejectCafe'/>"><span><ikep4j:message pre='${preButton}' key='rejectCafe'/></span></a></li>
			</c:if>
			<c:if test="${isSystemAdmin or cafe.sysopId==user.userId}">
			<li><a class="button" href="#a" onclick="updateCafeView()" title="<ikep4j:message pre='${preButton}' key='update'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a></li>
			</c:if>
		</c:when>
		<c:when test="${cafe.cafeStatus=='WC' && isSystemAdmin}"> 
			<li><a class="button" href="#a" onclick="updateStatus('C')" title="<ikep4j:message pre='${preButton}' key='closeCafe'/>"><span><ikep4j:message pre='${preButton}' key='closeCafe'/></span></a></li>
			<li><a class="button" href="#a" onclick="updateCloseRejectStatus('O')" title="<ikep4j:message pre='${preButton}' key='closeRejectCafe'/>"><span><ikep4j:message pre='${preButton}' key='closeRejectCafe'/></span></a></li>
		</c:when>
		<c:when test="${cafe.cafeStatus=='C' && isSystemAdmin && searchCondition.listUrl == 'listCloseCafeView.do'}">
			<li><a class="button" href="#a" onclick="updateCloseRejectStatus('O')" title="<ikep4j:message pre='${preButton}' key='closeRejectCafe'/>"><span><ikep4j:message pre='${preButton}' key='closeRejectCafe'/></span></a></li>
			<li><a class="button" href="#a" onclick="deleteCafe()" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
		</c:when>
	</c:choose>
	
	<li><a class="button" href="#" onclick="listCafe()" title="<ikep4j:message pre='${preButton}' key='list'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
</ul>
</div>
<!--//blockButton End-->		


