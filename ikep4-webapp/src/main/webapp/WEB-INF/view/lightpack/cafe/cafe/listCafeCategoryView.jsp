<%--
=====================================================
	* 기능설명	:	Collaboration Category 별 목록(조직/TFT/Cop/Informal)
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.lightpack.cafe.cafe.listCafeCategory.header" />
<c:set var="preSearch"  value="message.lightpack.cafe.cafe.listCafeCategory.search" />
<c:set var="preList"    value="message.lightpack.cafe.cafe.listCafeCategory.list" />
<c:set var="preButton"  value="message.lightpack.cafe.cafe.listCafeCategory.button" />
<c:set var="preScript"  value="message.lightpack.cafe.cafe.listCafeCategory.script" />
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
	sort = function(sortColumn, sortCategory) {

		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortCategory == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}
		
		$jq("#searchButton").click();
	};
	

	
	searchCategory = function(id) {

		$jq("select[name=categoryId]").val(id)
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
			$jq('form[name=mainForm]').attr({
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
			
			$jq("#mainForm").submit();		
			return false; 
		});
		
		$jq("#defaultCategory").click(function() {
			
			$jq("#mainForm").submit();		
			return false; 
		});
		
		$jq("select[name=pagePerRecord]").change(function() {
			$jq("#searchButton").click(); 
		}); 
		$jq("#checkboxAll").click(function() { 
			$jq("input[name=cafeIds]").attr("checked", $jq(this).is(":checked"));  
		}); 
		$jq("select[name=lowRankCategory]").change(function() {
			$jq("input[name=categoryId]").val($jq("select[name=lowRankCategory]").val());
			$jq("#searchButton").click(); 
		}); 
		changeCategory=function(categoryId){
			$jq("input[name=categoryId]").val(categoryId);
			$jq("#searchButton").click();
			return false;
		};
		
		//ORG WORKSPACE 폐쇄
		$jq("#closeCafeButton").click(function() {
			
			var countCheckBox	=	$("input[name=cafeIds]:checkbox:checked").length;
			
			if(countCheckBox>0)
			{
				if( confirm('<ikep4j:message pre='${preScript}' key='closeMessage' />') ){

					$jq.ajax({
						url : '<c:url value="/lightpack/cafe/cafe/updateCafeCloseStatus.do" />?'+$jq("#mainForm").serialize(),
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
				alert('<ikep4j:message pre='${preScript}' key='checkboxClose' />');
			}
			return false; 		
			
		});
	});
	
})(jQuery);  
//-->
</script>

<div id="pageLodingDiv">

<h1 class="none">Contents</h1>
				
				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${preList}' key='title'/></h2>
				</div>
				<!--//pageTitle End-->
				
				<!--blockbox Start-->
				<div class="Box_type_01">
					<table summary="category">
						<caption></caption>
						<tbody>
							<tr>
								<c:forEach var="default" items="${defaultCateogryList}" varStatus='index'>
									<td width="25%"><a href="javascript:changeCategory('${default.categoryId}')" <c:if test="${searchCondition.categoryId==default.categoryId}">class="selected"</c:if>>${default.categoryName}<span class="colorPoint">(${default.cafeCount})</span></a></td>
									<c:if test="${index.count/4 == 1}">
										</tr>
										<c:if test="${!index.last}"><tr></c:if>
									</c:if>
								</c:forEach>
						</tbody>
					</table>
				</div>	
				<!--//blockbox End-->		
				<!--tableTop Start-->
				<form id="mainForm" name="mainForm" method="get" action="<c:url value='/lightpack/cafe/cafe/listCafeCategoryView.do' />">
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
					<spring:bind path="searchCondition.categoryId">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="categoryId" />
					</spring:bind>			
					<div class="tableTop">
						<div class="tableTop_bgR"></div>
						<div class="listInfo">
							<spring:bind path="searchCondition.pagePerRecord"> 
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
							<c:forEach var="code" items="${cafeCode.pageNumList}">
								<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
							</c:forEach> 
							</select> 
						</spring:bind>
						<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageTotal' /> <span> ${searchResult.recordCount}</span></div>
					</div>	
								
					<select name="lowRankCategory" title="lowRankCategory">
						<option value=""><ikep4j:message pre='${preList}' key='selectAll'/></option>
						<c:forEach var="lowRankCategory" items="${lowRankCategoryList}" varStatus="index">
							<option <c:if test="${searchCondition.categoryId==lowRankCategory.categoryId}">selected="selected"</c:if> value="${lowRankCategory.categoryId}">
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									${lowRankCategory.categoryName}
								</c:when>
								<c:otherwise>
									${lowRankCategory.categoryEnglishName}
								</c:otherwise>
								</c:choose>		
							</option>
						</c:forEach>
					</select>
					
					<div class="tableSearch">
						<spring:bind path="searchCondition.searchColumn">  
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
								<option value="CAFE_NAME" <c:if test="${'CAFE_NAME' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='cafeName'/></option>
								<option value="REGISTER_NAME" <c:if test="${'REGISTER_NAME' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='registerName'/></option>
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
				<!--blockListTable Start-->
				<div class="blockListTable">
					<table summary="자유게시판">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="5%">
									<input id="checkboxAll" name="checkboxAll" class="checkbox" title="checkboxAll" type="checkbox" />
								</th>
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
								<th scope="col" width="13%">
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
									<a onclick="sort('memberCount', '<c:if test="${searchCondition.sortColumn eq 'memberCount'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='memberCount' />
									</a>
									<c:if test="${searchCondition.sortColumn eq 'memberCount'}">
										<c:if test="${searchCondition.sortType eq 'ASC'}">
											<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
										</c:if>
										<c:if test="${searchCondition.sortType eq 'DESC'}">
											<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
										</c:if>
									</c:if>
								</th>
								<th scope="col" width="8%">
									<a onclick="sort('boardItemCount', '<c:if test="${searchCondition.sortColumn eq 'boardItemCount'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='itemCount' />
									</a>
									<c:if test="${searchCondition.sortColumn eq 'boardItemCount'}">
										<c:if test="${searchCondition.sortType eq 'ASC'}">
											<img src="<c:url value="/base/images/icon/ic_tablesort_up.gif" />" alt="up" />
										</c:if>
										<c:if test="${searchCondition.sortType eq 'DESC'}">
											<img src="<c:url value="/base/images/icon/ic_tablesort_down.gif" />" alt="down" />
										</c:if>
									</c:if>
								</th>
								<th scope="col" width="12%">
									<a onclick="sort('openDate', '<c:if test="${searchCondition.sortColumn eq 'openDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
										<ikep4j:message pre='${preList}' key='registDate' />
									</a>
									<c:if test="${searchCondition.sortColumn eq 'openDate'}">
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
									<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<c:forEach var="cafeList" items="${searchResult.entity}">
									<tr>
										<td><input  name="cafeIds" class="checkbox" title="checkbox" type="checkbox" value="${cafeList.cafeId}" /></td>
										<td class="textLeft">
											<a onclick="viewCafe('${cafeList.cafeId}','${cafeList.cafeStatus}');" href="#a" title="${cafeList.cafeName}">
												${cafeList.cafeName}
											</a>
										</td>
										<td class="textCenter">
											<a href="#a" onclick="iKEP.showUserContextMenu(this, '${cafeList.sysopId}', 'bottom')">
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
										<td class="textRight">${cafeList.memberCount}</td>
										<td class="textRight">
											${cafeList.boardItemCount}
										</td>
										<td>
											<c:if test="${!empty cafeList.openDate}">
											<ikep4j:timezone pattern="yyyy.MM.dd" date="${cafeList.openDate}"/>	
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
				<!--//blockListTable End-->	
							
				<!--blockButton Start-->
				<div class="blockButton"> 	
					<ul>
						<li><a id="closeCafeButton"class="button" href="#a"><span><ikep4j:message pre='${preList}' key='close'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->
			 
</form>

</div>