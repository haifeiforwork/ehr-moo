<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>


<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%pageContext.setAttribute("crlf", "\r\n"); %>

<c:set var="preSub"    value="ui.support.poll.sub" />
<c:set var="preList"    value="ui.support.poll.list" />
<c:set var="preAdmin"    value="ui.support.poll.admin" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />



<script type="text/javascript">
<!--
(function($) { 
	$(document).ready(function() {
		$("#searchPollItemButton").click(function() {
			$("input[name=pageIndex]").val('1');
			$("#searchForm").submit(); 
			return false; 
		});		
		$jq("select[name=pagePerRecord]").change(function() {
			$("input[name=pageIndex]").val("1");
			$("#searchForm").submit(); 
        }); 		
 		/**
 		 * 체크박스 ALL 
 		 */
		$("#checkboxAllPoll").click(function() { 
			$("input[name=checkboxPollItem]").attr("checked", $(this).is(":checked"));  
		}); 
		
	   /**
	    * 버튼영역 실행
	    */
		$("#pollAdminListButton a").click(function(){
            switch (this.id) {
                case "createPollButton":
                    location.href='formPoll.do?mode=admin';
                    break;
                case "deletePollButton":					
					if($jq("input[name=checkboxPollItem]:checked").length == 0){
						alert('삭제할 항목을 선택하세요.');
						return false;
					}												
					if(confirm("삭제하시겠습니까?")) { 
	                	$jq('#searchForm').attr('action', '<c:url value="/support/poll/deleteMultiPoll.do"/>');
	    				$jq('#searchForm').submit();
					}
                    break;
                default:
                    break;
            }
        });
		
	});	
})(jQuery); 
function sort(sortColumn, sortType) {
	$jq("input[name=sortColumn]").val(sortColumn);
	
	if( sortType == 'ASC') {
		$jq("input[name=sortType]").val('DESC');	
	} else {
		$jq("input[name=sortType]").val('ASC');
	}	
	getList();
};
function getList() {
	$jq("input[name=pageIndex]").val("1");
	$jq("#searchForm").submit(); 
	/*$jq.ajax({
		url : '<c:url value="/support/poll/pollAdminList.do" />',
		data : $jq("#searchForm").serialize(),
		type : "post",
		success : function(result) {
			$jq("#listDiv").html(result);
		}
	});*/
}
function openPollWin(pollId,mode) {
	//window.open (pageURL, '', 'top=0,width=630,height=350,resizable=no, status=no,scrollbars=no, toolbar=no, menubar=no');
	//var pageURL = "<c:url value='/support/poll/viewPoll.do' />" + "?pollId=" + pollId + "&viewMode=" + mode + "&docPopup=true";
	//iKEP.popupOpen(pageURL, {width:580, height:350}, "pollPopup")
	location.href="viewPoll.do"+"?pollId=" + pollId + "&viewMode=" + mode;
}
//-->
</script> 
				<form id="searchForm" method="post" action="<c:url value='/support/poll/pollAdminList.do'/>" > 
					<spring:bind path="searchCondition.sortColumn">
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind>
					<spring:bind path="searchCondition.sortType">
						<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind>				
				<h1 class="none">contents</h1>			

				<!--tableTop Start-->
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2><ikep4j:message pre='${preSub}' key='adminPoll' /></h2>
					<div class="listInfo">
						<spring:bind path="searchCondition.pagePerRecord">  
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
							<c:forEach var="code" items="${boardCode.pageNumList}">
								<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
							</c:forEach> 
							</select> 
						</spring:bind>
						<div class="totalNum"><ikep4j:message pre='${preList}' key='all' /><span>${searchResult.recordCount}</span></div>
					</div>			
					<div class="tableSearch">
						<spring:bind path="searchCondition.searchColumn">  
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
								<option value="question" <c:if test="${'question' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preAdmin}' key='question' /></option>
								<option value="registerName" <c:if test="${'registerName' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preAdmin}' key='registerName' /></option>
							</select>	
						</spring:bind>		
						<spring:bind path="searchCondition.searchWord">  					
							<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
						</spring:bind>																	
						<a id="searchPollItemButton" name="searchPollItemButton" href="javascript:getList()" class="ic_search"><span>Search</span></a>	
					</div>	
					<div class="clear"></div>
				</div>
				<!--//tableTop End-->
				
				<!--blockListTable Start-->
				<div class="blockListTable">
					<table summary="board">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="5%"><input id="checkboxAllPoll" class="checkbox" title="checkbox" type="checkbox" value="" /></th>
								<th scope="col" width="8%">
									<a onclick="javascript:sort('PERMISSION_TYPE', '<c:if test="${searchCondition.sortColumn eq 'PERMISSION_TYPE'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preAdmin}' key='permissionType' />
									</a>
								</th>
								<th scope="col" width="*"><ikep4j:message pre='${preAdmin}' key='question' /><a href="#a"></a></th>
								<th scope="col" width="8%"><ikep4j:message pre='${preAdmin}' key='registerName' /></th>
								<th scope="col" width="20%"><ikep4j:message pre='${preAdmin}' key='date' /><a href="#a"></a></th>
								<th scope="col" width="8%">
									<a onclick="javascript:sort('STATUS', '<c:if test="${searchCondition.sortColumn eq 'STATUS'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preAdmin}' key='status' />
									</a>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="poll" items="${searchResult.entity}" varStatus="pollLoopCount">
							<tr>
								<td><input id="checkboxPollItem${pollLoopCount.count}" name="checkboxPollItem" class="checkbox" title="checkbox" type="checkbox" value="${poll.pollId}" /></td>
								<td>
									<c:choose>
							 			<c:when test="${poll.permissionType == 0}">[<ikep4j:message pre='${preAdmin}' key='permissionType01' />]</c:when>
							 			<c:when test="${poll.permissionType == 1}">[<ikep4j:message pre='${preAdmin}' key='permissionType02' />]</c:when>
							 		</c:choose>								
								</td>
								<td class="textLeft"><span class="notice"><a href="#a" onclick="openPollWin('${poll.pollId}','admin')">${poll.question}</a></span></td>
								<td>
								     <c:choose>
								      <c:when test="$(user.localeCode ne portal.defaultLocaleCode)">
								        ${poll.registerEnglishName}
								      </c:when>
								      <c:otherwise>
								        ${poll.registerName}
								      </c:otherwise>
								     </c:choose>															
								</td>
								<td><ikep4j:timezone date="${poll.startDate}" pattern="message.support.poll.timezone.dateformat.type" keyString="true"/>~<ikep4j:timezone date="${poll.endDate}" pattern="message.support.poll.timezone.dateformat.type" keyString="true"/></td>
								<td>
									<c:choose>
							 			<c:when test="${poll.status == 0}"><ikep4j:message pre='${preAdmin}' key='status01' /></c:when>
							 			<c:when test="${poll.status == 1}"><ikep4j:message pre='${preAdmin}' key='status02' /></c:when>
							 			<c:when test="${poll.status == 2}"><ikep4j:message pre='${preAdmin}' key='status03' /></c:when>
							 		</c:choose>									
								</td>
							</tr>
							</c:forEach>																																		
						</tbody>
					</table>
					<!--Page Numbur Start--> 
					<spring:bind path="searchCondition.pageIndex">
					<ikep4j:pagination searchFormId="searchForm" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind>  
					<!--//Page Numbur End--> 						
				</div>
				<!--//blockListTable End-->	
							
				<!--blockButton Start-->
				<div class="blockButton" id="pollAdminListButton"> 	
					<ul>
						<li><a id="createPollButton" class="button" href="#a"><span><ikep4j:message pre='${preAdmin}' key='add' /></span></a></li>
						<li><a id="deletePollButton" class="button" href="#a"><span><ikep4j:message pre='${preAdmin}' key='del' /></span></a></li>						
					</ul>
				</div>
				<!--//blockButton End-->	
													
		
			</form>