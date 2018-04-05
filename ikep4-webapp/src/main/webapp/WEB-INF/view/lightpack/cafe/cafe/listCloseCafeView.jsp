<%--
=====================================================
	* 기능설명	:	나의 Collaboration 목록
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.lightpack.cafe.cafe.listCloseCafe.header" />
<c:set var="preSearch"  value="message.lightpack.cafe.cafe.listCloseCafe.search" />
<c:set var="preList"    value="message.lightpack.cafe.cafe.listCloseCafe.list" />
<c:set var="preButton"  value="message.lightpack.cafe.cafe.listCloseCafe.button" />
<c:set var="preScript"  value="message.lightpack.cafe.cafe.listCloseCafe.script" />
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
	
	searchCategory = function(id) {
		$jq("input[name=pageIndex]").val('1');
		$jq("select[name=typeId]").val(id)
	    $jq("#searchButton").click();
	    
		return false; 
	};	
	// Collaboration 조회
	viewCafe = function(cafeId,status) {
		
		if(status=="O" || status=="WC")
		{
			parent.goCafe(cafeId);
		}
		else
		{
			$jq('#mainForm').attr({
				action:"<c:url value="/lightpack/cafe/cafe/readCafeView.do"/>"
			});
			$jq("input[name=cafeId]").val(cafeId);
			$jq("#mainForm").submit(); 			
		}
		
	
		return false;
	}; 
	
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		iKEP.iFrameContentResize();  
		
		
		$jq("#searchButton").click(function() {
			$jq("#mainForm").attr({method:'get'}).submit();		
			return false; 
		});
		$jq("select[name=pagePerRecord]").change(function() {
			//서브밋하는 코드 넣으시면 됩니다.
			//ex : $jq("#searchBoardItemButton").click(); 
			$jq("input[name=pageIndex]").val('1');
			$jq("#searchButton").click(); 
		});  
	
		$jq("#deleteCafeButton").click(function() {
			
			var countCheckBox	=	$jq("input[name=cafeIds]:checkbox:checked").length;

			if(countCheckBox>0)
			{
				if( confirm('<ikep4j:message pre='${preScript}' key='delete' />') ){
					$jq.ajax({
						url : '<c:url value='/lightpack/cafe/cafe/deleteCafeAjax.do' />?'+$jq("#mainForm").serialize(),
						type : "get",
						loadingElement : {container:"#pageLodingDiv"},
						success : function(result) {
							$jq("#searchButton").click(); 
						}
					});	
				}
			}
			else
			{
				alert('<ikep4j:message pre='${preScript}' key='checkDelete' />');
			}
			return false; 	
	
		}); 		
		
		$jq("#rejectColseButton").click(function() {
			
			var countCheckBox	=	$jq("input[name=cafeIds]:checkbox:checked").length;

			if(countCheckBox>0)
			{
				if( confirm('<ikep4j:message pre='${preScript}' key='closeRejectMessage' />') ){
					
					$jq("input[name=cafeStatus]").val("O");
					
					$jq.ajax({
						url : '<c:url value='/lightpack/cafe/cafe/updateCafeStatusAjax.do' />?'+$jq("#mainForm").serialize(),
						type : "get",
						loadingElement : {container:"#pageLodingDiv"},
						success : function(result) {
							$jq("#searchButton").click(); 
						}
					});	
				}
			}
			else
			{
				alert('<ikep4j:message pre='${preScript}' key='checkReject' />');
			}
			return false; 	
	
		}); 	
		
		$jq("#checkboxAll").click(function() { 
			$jq("input[name=cafeIds]").attr("checked", $jq(this).is(":checked"));  
		}); 
	   
	});
})(jQuery);  
//-->
</script>

<div id="pageLodingDiv">

<h1 class="none">Contents</h1>
<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="get" action="<c:url value="/lightpack/cafe/cafe/listCloseCafeView.do"/>">  
<spring:bind path="searchCondition.sortColumn">
<input name="${status.expression}" type="hidden" value="${status.value}" title="sortColumn" />
</spring:bind> 		
<spring:bind path="searchCondition.sortType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="sortType" />
</spring:bind> 
<spring:bind path="searchCondition.cafeId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="cafeId" />
</spring:bind> 
<spring:bind path="searchCondition.cafeStatus">
<input name="${status.expression}" type="hidden" value="${status.value}" title="cafeStatus" />
</spring:bind>
<spring:bind path="searchCondition.listType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="listType" />
</spring:bind>
<spring:bind path="searchCondition.listUrl">
<input name="${status.expression}" type="hidden" value="${status.value}" title="listUrl" />
</spring:bind>

		<!--tableTop Start -->
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
						<spring:bind path="searchCondition.searchColumn">  
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
								<option value="CAFE_NAME" <c:if test="${'CAFE_NAME' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='cafeName'/></option>
								<option value="SYSOP_NAME" <c:if test="${'SYSOP_NAME' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='sysopName'/></option>
							</select>		
						</spring:bind>		
						<spring:bind path="searchCondition.searchWord">  					
							<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
						</spring:bind>
						<a id="searchButton" name="searchButton" href="#a" class="ic_search"><span>Search</span></a>
					</div>
					<div class="clear"></div>
				</div>
				<!--//tableTop End-->
				<!--blockListTable Start -->
				<div class="blockListTable">
					<table summary="<ikep4j:message pre="${preHeader}" key="pageTitle" />">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="5%">
									<input id="checkboxAll" name="checkboxAll" class="checkbox" title="checkboxAll" type="checkbox" />
								</th>
								<th scope="col" width="5%"><ikep4j:message pre='${preList}' key='no' /></th>
								<th scope="col" width="*">
									<a onclick="sort('cafeName', '<c:if test="${searchCondition.sortColumn eq 'cafeName'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='cafeName' />
									</a>
									<c:if test="${searchCondition.sortColumn eq 'cafeName'}">
										<c:if test="${searchCondition.sortType eq 'ASC'}">
											<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
										</c:if>
										<c:if test="${searchCondition.sortType eq 'DESC'}">
											<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
										</c:if>
									</c:if>
								</th>
								<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}">
										<th scope="col" width="*">
											<a onclick="sort('fullPath', '<c:if test="${searchCondition.sortColumn eq 'fullPath'}">${searchCondition.sortType}</c:if>');"  href="#a">
												<ikep4j:message pre='${preList}' key='categoryPath' />
											</a>
											<c:if test="${searchCondition.sortColumn eq 'fullPath'}">
												<c:if test="${searchCondition.sortType eq 'ASC'}">
													<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
												</c:if>
												<c:if test="${searchCondition.sortType eq 'DESC'}">
													<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
												</c:if>
											</c:if>
										</th>
										<th scope="col" width="8%">
											<a onclick="sort('sysopName', '<c:if test="${searchCondition.sortColumn eq 'sysopName'}">${searchCondition.sortType}</c:if>');"  href="#a">
												<ikep4j:message pre='${preList}' key='sysopName' />
											</a>
											<c:if test="${searchCondition.sortColumn eq 'sysopName'}">
												<c:if test="${searchCondition.sortType eq 'ASC'}">
													<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
												</c:if>
												<c:if test="${searchCondition.sortType eq 'DESC'}">
													<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
												</c:if>
											</c:if>
										</th>
										<th scope="col" width="8%">
											<a onclick="sort('updaterName', '<c:if test="${searchCondition.sortColumn eq 'updaterName'}">${searchCondition.sortType}</c:if>');"  href="#a">
												<ikep4j:message pre='${preList}' key='closeName' />
											</a>
											<c:if test="${searchCondition.sortColumn eq 'updaterName'}">
												<c:if test="${searchCondition.sortType eq 'ASC'}">
													<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
												</c:if>
												<c:if test="${searchCondition.sortType eq 'DESC'}">
													<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
												</c:if>
											</c:if>
										</th>
									</c:when>
									<c:otherwise>
										<th scope="col" width="*">
											<a onclick="sort('fullEnglishPath', '<c:if test="${searchCondition.sortColumn eq 'fullEnglishPath'}">${searchCondition.sortType}</c:if>');"  href="#a">
												<ikep4j:message pre='${preList}' key='categoryPath' />
											</a>
											<c:if test="${searchCondition.sortColumn eq 'fullPath'}">
												<c:if test="${searchCondition.sortType eq 'ASC'}">
													<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
												</c:if>
												<c:if test="${searchCondition.sortType eq 'DESC'}">
													<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
												</c:if>
											</c:if>
										</th>
										<th scope="col" width="8%">
											<a onclick="sort('sysopEnglishName', '<c:if test="${searchCondition.sortColumn eq 'sysopEnglishName'}">${searchCondition.sortType}</c:if>');"  href="#a">
												<ikep4j:message pre='${preList}' key='sysopName' />
											</a>
											<c:if test="${searchCondition.sortColumn eq 'sysopEnglishName'}">
												<c:if test="${searchCondition.sortType eq 'ASC'}">
													<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
												</c:if>
												<c:if test="${searchCondition.sortType eq 'DESC'}">
													<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
												</c:if>
											</c:if>
										</th>
										<th scope="col" width="8%">
											<a onclick="sort('updaterEnglishName', '<c:if test="${searchCondition.sortColumn eq 'updaterEnglishName'}">${searchCondition.sortType}</c:if>');"  href="#a">
												<ikep4j:message pre='${preList}' key='updaterName' />
											</a>
											<c:if test="${searchCondition.sortColumn eq 'updaterEnglishName'}">
												<c:if test="${searchCondition.sortType eq 'ASC'}">
													<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
												</c:if>
												<c:if test="${searchCondition.sortType eq 'DESC'}">
													<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
												</c:if>
											</c:if>
										</th>
									</c:otherwise>
								</c:choose>	
								<th scope="col" width="12%">
									<a onclick="sort('closeDate', '<c:if test="${searchCondition.sortColumn eq 'closeDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='closeDate' />
									</a>
									<c:if test="${searchCondition.sortColumn eq 'closeDate'}">
										<c:if test="${searchCondition.sortType eq 'ASC'}">
											<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
										</c:if>
										<c:if test="${searchCondition.sortType eq 'DESC'}">
											<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
										</c:if>
									</c:if>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
							    <c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="7" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="cafeList" items="${searchResult.entity}" varStatus="status">
									<tr>
										<td><input name="cafeIds" class="checkbox" title="checkbox" type="checkbox" value="${cafeList.cafeId}" /></td>
										<td>${searchResult.recordCount - status.index - (searchCondition.pagePerRecord * ( searchResult.pageIndex - 1))}</td>
										<td class="textLeft">
											<a onclick="viewCafe('${cafeList.cafeId}','${cafeList.cafeStatus}');" href="#a" title="${cafeList.cafeName}">
												${cafeList.cafeName}
											</a>
										</td>
										<td class="textLeft">
											<c:choose>
												<c:when test="${user.localeCode == portal.defaultLocaleCode}">
													${cafeList.fullPath}
												</c:when>
												<c:otherwise>
													${cafeList.fullEnglishPath}
												</c:otherwise>
											</c:choose>
										</td>
										<td>
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
										</td>
										<td>
											<a href="#a" onclick="iKEP.showUserContextMenu(this, '${cafeList.updaterId}', 'bottom')" >
											<c:choose>
											<c:when test="${user.localeCode == portal.defaultLocaleCode}">
												${cafeList.updaterName}
											</c:when>
											<c:otherwise>
												${cafeList.updaterEnglishName}
											</c:otherwise>
											</c:choose>	
											</a>
										</td>
										<td>
											<c:if test="${not empty cafeList.closeDate}">
												<ikep4j:timezone pattern="yyyy.MM.dd" date="${cafeList.closeDate}"/>
											</c:if>
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
							
<!-- 				blockButton Start -->
				<div class="blockButton"> 	
					<ul>
						<li><a id="rejectColseButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='rejectClose' /></span></a></li>
						<li><a id="deleteCafeButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='removeCafe' /></span></a></li>
					</ul>
				</div>
<!-- 				//blockButton End -->

</form>


</div>
