<%--
=====================================================
	* 기능설명	:	개설관리목록
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.lightpack.cafe.cafe.listCafeLog.header" />
<c:set var="preSearch"  value="message.lightpack.cafe.cafe.listCafeLog.search" />
<c:set var="preList"    value="message.lightpack.cafe.cafe.listCafeLog.list" />
<c:set var="preButton"  value="message.lightpack.cafe.cafe.listCafeLog.button" />
<c:set var="preScript"  value="message.lightpack.cafe.cafe.listCafeLog.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>



<script type="text/javascript">
<!-- 
(function($) {

	// 주의 : 글로벌 함수는 아래와 같이 글로벌 변수에 무명 함수를 assign해서 생성한다. 
	//정렬 조건 변경 함수
	sort = function(sortColumn, sortType) {

		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}
		$jq("#searchButton").click();
	};
	
	search = function() {
		$jq("input[name=pageIndex]").val('1');
		$jq("#searchButton").click();
	};	
	
	searchStatus = function(status){
		var countCheckBox	=	$jq("input[name=openStatus]:checkbox:checked").length;

		if(countCheckBox==1)
		{
			$jq("input[name=cafeStatus]").val(status);
			$jq("input[name=pageIndex]").val('1');
			$jq("#searchButton").click();
		}
		else
		{
			$jq("input[name=pageIndex]").val('1');
			$jq("#searchButton").click();
		}
	};

	// Collaboration 조회
	viewCafe = function(cafeId,status) {
		if(status=="O" || status=="WC")
		{
			parent.goCafe(cafeId);
			/**$jq('form[name=mainForm]').attr({
				action:"<c:url value="/lightpack/cafe/main/Cafe.do"/>",
				method:"GET"
			});**/				
		}
		else
		{
			$jq('form[name=mainForm]').attr({
				action:"<c:url value="/lightpack/cafe/cafe/readCafeView.do"/>"
			});
			$jq("input[name=cafeId]").val(cafeId);
			$jq("#mainForm").attr({method:'get'}).submit(); 			
		}

		
		return false;

	}; 
	

	// onload시 수행할 코드
	$jq(document).ready(function() { 
	
		iKEP.iFrameContentResize();  
		
		
		$jq("#searchButton").click(function() {
			$jq("#mainForm").submit();
			return false; 
		});

		$jq("select[name=pagePerRecord]").change(function() {
			$jq("input[name=pageIndex]").val('1');
			$jq("#searchButton").click(); 
		});
		
		$jq("#checkboxAll").click(function() { 
			$jq("input[name=cafeIds]").attr("checked", $jq(this).is(":checked"));  
		});
		
		$jq("#startDate").datepicker().next().eq(0).click(function() { 
			$jq(this).prev().eq(0).datepicker("show"); 
		});
		
		$jq("#endDate").datepicker().next().eq(0).click(function() { 
			$jq(this).prev().eq(0).datepicker("show"); 
		});
	   
	});
})(jQuery);  
//-->
</script>


<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="get" action="<c:url value="/lightpack/cafe/cafe/listCafeLogView.do"/>">  
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
<spring:bind path="searchCondition.listUrl">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.searchColumn">  					
	<input name="${status.expression}" value="CAFE_NAME" type="hidden" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
</spring:bind>

<h1 class="none"><ikep4j:message pre="${preList}" key="title" /></h1>
								
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2>
					<div class="listInfo">  
						<spring:bind path="searchCondition.pagePerRecord">  
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
							<c:forEach var="code" items="${cafeCode.pageNumList}">
								<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
							</c:forEach> 
							</select> 
						</spring:bind>
						<div class="totalNum"> <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
					</div>				
					<div class="tableSearch">
						<div class="subInfo">
							<spring:bind path="searchCondition.startDate">
								<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="날짜시작" value="${status.value}" size="10"/> <img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="달력" /> ~
							</spring:bind>
							<spring:bind path="searchCondition.endDate">
								<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="날짜끝" value="${status.value}" size="10"  /> <img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="달력" />
							</spring:bind>
						</div>				
						<spring:bind path="searchCondition.searchWord">  					
							<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
						</spring:bind>
						<a id="searchButton" name="searchButton" href="#a" class="ic_search"><span>Search</span></a>
					</div>			
					<div class="clear"></div>
				</div>
				
				<!--//tableTop End-->				
				<!--blockListTable Start-->
				<div class="blockListTable summaryView">
					<table summary="<ikep4j:message pre="${preList}" key="title" />">
						<caption></caption>						
						<tbody>
							<c:choose>
							    <c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="cafeList" items="${searchResult.entity}" varStatus="status">
										<tr>
											<td>
												<div class="tag_summaryViewTitle">
													<span class="c_ty1 colorBlue">
														<c:choose>
															<c:when test="${cafeList.cafeStatus eq 'O' }">
																[<ikep4j:message pre="${preList}" key="createCafe" />]
															</c:when>
															<c:when test="${cafeList.cafeStatus eq 'C' }">
																[<ikep4j:message pre="${preList}" key="closeCafe" />]
															</c:when>
															<c:when test="${cafeList.cafeStatus eq 'WC' }">
																[<ikep4j:message pre="${preList}" key="waitCafe" />]
															</c:when>
															<c:otherwise>
															</c:otherwise>
														</c:choose>
													</span>
													<a onclick="viewCafe('${cafeList.cafeId}','${cafeList.cafeStatus}')" href="#a">${cafeList.cafeName}</a>
												</div>
												<div class="summaryViewInfo">
													<span class="summaryViewInfo_name">
														<ikep4j:message pre="${preList}" key="sysopt" /> : 
														<a href="#a" onclick="iKEP.showUserContextMenu(this, '${cafeList.sysopId}', 'bottom')" >
														<c:choose>
															<c:when test="${user.localeCode == portal.defaultLocaleCode}">
																${cafeList.sysopName} 
															</c:when>
															<c:otherwise>
																${cafeList.sysopEnglishName} 
															</c:otherwise>
														</c:choose>
														</a>
													</span>
													<span>
														<c:choose>
															<c:when test="${user.localeCode == portal.defaultLocaleCode}">
																${cafeList.fullPath}
															</c:when>
															<c:otherwise>
																${cafeList.fullEnglishPath}
															</c:otherwise>
														</c:choose>
													</span>
													<span>
														<c:choose>
															<c:when test="${cafeList.cafeStatus eq 'C' }">
																[<ikep4j:timezone pattern="yyyy.MM.dd" date="${cafeList.closeDate}"/>]
															</c:when>
															<c:when test="${cafeList.cafeStatus eq 'ED' }">
																[<ikep4j:timezone pattern="yyyy.MM.dd" date="${cafeList.closeDate}"/>]
															</c:when>
															<c:otherwise>
																[<ikep4j:timezone pattern="yyyy.MM.dd" date="${cafeList.openDate}"/>]
															</c:otherwise>
														</c:choose>
													</span>										
												</div>
												<div class="summaryViewCon">
													<img src="<c:url value='/base/images/icon/ic_reply_ar.gif'/>" alt="" /> 
													<a href="#a">${cafeList.description}</a>
												</div>
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>				
					<!--Page Numbur Start--> 
					<spring:bind path="searchCondition.pageIndex">
					<ikep4j:pagination searchButtonId="searchButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind> 
					<!--//Page Numbur End--> 					

				</div>
				<!--//blockListTable End-->								
													
</form>