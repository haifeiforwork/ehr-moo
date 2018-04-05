<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preHeader"      value="ui.approval.work.apprDisplay" />
<c:set var="preSearch"  	value="ui.approval.common.searchCondition" />
<c:set var="preDisplaySearch"  	value="ui.approval.work.apprDisplay.searchCondition" />
<c:set var="preButton"  	value="ui.approval.work.apprDisplay.button" />	

<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript">
	<!--
	var dialogWindow = null;
	var fnCaller;
	
	(function($){
		
		f_Sort = function(sortColumn, sortType) {
			$("input[name=sortColumn]").val(sortColumn);
			
			if(sortType == '') sortType = 'ASC';
			
			if( sortType == 'ASC') {
				$("input[name=sortType]").val('DESC');	
			} else {
				$("input[name=sortType]").val('ASC');
			}
			
			$("#searchListButton").click();
		};
		
		fnCaller = function (params, dialog) {
			if(params) {
			}

			dialogWindow = dialog;
			$("#cancelButton").click(function() {
				dialogWindow.close();
			});
		};
		
		displayCollect = function (displayConfirm) {
			
			var userIds = new Array();
			var displayMsg = "";
			
			$("#searchForm input[name=userId]:checked").each(function(index) { 
				userIds.push($(this).val()); 
			});		
			
			if(displayConfirm == "all") {
				displayMsg = "<ikep4j:message pre='${preHeader}' key='confirmAllDisplay' />";
			}else if(displayConfirm == "part") {
				if(userIds == "") { 
					alert("<ikep4j:message pre='${preHeader}' key='checkDisplay' />");
					return;
				}
				displayMsg = "<ikep4j:message pre='${preHeader}' key='selectDisplay' />";
			}

			if(confirm(displayMsg)) {
				
				$.post("<c:url value='/approval/work/apprDisplay/deleteApprDisplayList.do'/>", {"userIds" : userIds.toString(),"displayConfirm":displayConfirm,"apprId":$("#searchForm input[name=apprId]:hidden").val()}) 
				.success(function(result) {
					if(result == "OK") {
						alert("<ikep4j:message pre='${preHeader}' key='returnDisplay' />");
						//location.href="<c:url value='/approval/work/apprDisplay/listApprDisplay.do'/>";
						location.reload();
					}
				});
			} 
		};
		
		$(document).ready(function(){
			
			$("#searchListButton").click(function() {
				$("input[name=pageIndex]").val('1');
				$("#searchForm").submit(); 
				return false; 
			});
			
			$("#allCheck").click(function() { 
				$("#searchForm input[name=userId]").attr("checked", $(this).is(":checked"));  
			});  
			
			/**
			 * 페이징 버튼
			 */
			$("#pageIndex").click(function() {
				$("#searchForm").submit(); 
				return false; 
			});
			
			$("select[name=pagePerRecord]").change(function(){
	            $("#pageIndex").click();
	        });
	 
			$("#listApButton").click(function() {   
				location.href='listApTempSearch.do';
			});
			
			$("#createApButton").click(function() {   
				location.href='createApForm.do';
			});
			
		});
		
		
	})(jQuery);
	//-->
</script>
<h1 class="none"><ikep4j:message pre='${preHeader}' key='title' /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="title" /></h2> 
	<%-- <div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.4depth" /></li>
		</ul>
	</div> --%>
</div>
<!--//pageTitle End-->

<form id="searchForm" name="searchForm" method="post" action="<c:url value='/approval/work/apprDisplay/listApprDisplayPop.do'/>" >

	<input name="userIds" type="hidden" value="" title="hidden" />
	<spring:bind path="searchCondition.apprId">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	

	<div class="blockSearch">
		<div class="corner_RoundBox03">
			<table summary="<ikep4j:message pre="${preHeader}" key="title.search" />">
				<caption></caption>
				<tbody>
					<tr>
						<spring:bind path="searchCondition.searchUserId">
						<th scope="row" width="5%"><ikep4j:message pre='${preDisplaySearch}' key='${status.expression}' /></th>
						<td width="25%">
							<input type="text" class="inputbox" title="<ikep4j:message pre='${preDisplaySearch}' key='${status.expression}' />" name="${status.expression}" value="${status.value}" size="35" />
						</td>
						</spring:bind>
						<spring:bind path="searchCondition.searchUserStatus">
						<th scope="row" width="5%"><ikep4j:message pre='${preDisplaySearch}' key='${status.expression}' /></th>
						<td width="25%">
							<select name="${status.expression}" title='<ikep4j:message pre='${preDisplaySearch}' key='${status.expression}' />'>
								<c:forEach var="userStatus" items="${userStatusList}">
									<option value="${userStatus.key}" <c:if test="${userStatus.key eq status.value}">selected="selected"</c:if>><ikep4j:message key='${userStatus.value}' /></option>
								</c:forEach> 
							</select>	
						</td>
						</spring:bind>
					</tr>
				</tbody>
			</table>
			<div class="searchBtn">
				<a id="searchListButton" name="searchListButton" href="#a"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif' />" alt="검색" /></a>
			</div>
			
			<div class="l_t_corner"></div>
			<div class="r_t_corner"></div>
			<div class="l_b_corner"></div>
			<div class="r_b_corner"></div>
		</div>
	</div>	
	<!--//blockSearch End-->
		
	<!--blockDetail Start-->
	<div class="blockListTable">
		<div class="tableTop">
			<div class="listInfo">
				<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
				<div align="right">
				</div>						
			</div>			
			<div class="clear"></div>
		</div>
		<table summary="<ikep4j:message pre='${preHeader}' key='title' />">
		<caption></caption>
		<colgroup>
		<col width="5%"/>
		<col width="5%"/>
		<col width="20%"/>
		<col width="25%"/>
		<col width="20%"/>
		<col width="25%"/>
		</colgroup>
		<tbody>
			<tr>
				<th scope="col">
					<input name="allCheck" id="allCheck" class="checkbox" title="checkbox" type="checkbox" value="" />
				</th>
				<th scope="col"><ikep4j:message pre='${preDisplaySearch}' key='number' /></th>
				<th scope="col"><ikep4j:message pre='${preDisplaySearch}' key='userId' /></th>
				<th scope="col"><ikep4j:message pre='${preDisplaySearch}' key='teamName' /></th>
				<th scope="col"><ikep4j:message pre='${preDisplaySearch}' key='userStatus' /></th>
				<th scope="col">
					<a onclick="f_Sort('confirmDate', '<c:if test="${searchCondition.sortColumn eq 'confirmDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preDisplaySearch}' key='confirmDate' />&nbsp;&nbsp;
					</a>
					<c:choose>
					    <c:when test="${searchCondition.sortColumn eq 'confirmDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					    <c:when test="${searchCondition.sortColumn eq 'confirmDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
				    </c:choose>
				</th>
			</tr>
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
					</tr>
			    </c:when>
			    <c:otherwise>
					<c:forEach var="apdisplay" items="${searchResult.entity}" varStatus="i">
						<c:choose>
				 			<c:when test="${i.count % 2 == 0}">
				 				<c:set var="className" value="bgWhite"/>
				 			</c:when>
				 			<c:otherwise>
				 				<c:set var="className" value="bgSelected"/>
				 			</c:otherwise>
				 		</c:choose>  
						<tr class="${className}">
							<td>
								<input type="checkbox" name="userId" id="userId" value="${apdisplay.userId}|${apdisplay.displayId}" title="checkbox">
							</td>
							<td>${(searchCondition.recordCount-(searchCondition.pagePerRecord*(searchCondition.pageIndex-1))-i.count)+1}</td>
							<td>
								<span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${apdisplay.userId}', 'bottom')">${apdisplay.userName}</a></span>&nbsp;${apdisplay.jobTitleName}
							</td>
							<td>${apdisplay.teamName}</td>
							<td>
								<c:if test="${apdisplay.userStatus eq '0'}"><ikep4j:message pre='${preHeader}' key='confirm.no' /></c:if>
								<c:if test="${apdisplay.userStatus eq '1'}"><ikep4j:message pre='${preHeader}' key='confirm.yes' /></c:if>
							</td>
							<td><ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${apdisplay.confirmDate}"/>&nbsp;</td>
						</tr>
					</c:forEach>				      
			    </c:otherwise> 
			</c:choose>  
		</tbody>
	</table>
	<!--Page Numbur Start--> 
	<spring:bind path="searchCondition.pageIndex">
	<ikep4j:pagination searchButtonId="${status.expression}" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
	<input  id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<!--//Page Numbur End-->
	
</div>
</form>
<!--//blockListTable End-->	
<div class="clear"></div>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="allCollect" class="button" href="#a" onclick="displayCollect('all');"><span><ikep4j:message pre='${preButton}' key='collect.all' /></span></a></li>
			<li><a id="partCollect" class="button" href="#a" onclick="displayCollect('part');"><span><ikep4j:message pre='${preButton}' key='collect.part' /></span></a></li>
			<li><a id="cancelButton"   class="button" href="#a"><span><ikep4j:message key='ui.approval.common.button.close'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->	