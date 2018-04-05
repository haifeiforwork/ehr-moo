<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preApCommList"    		value="ui.approval.work.apprlist" />
<c:set var="preSearch"  			value="ui.approval.common.searchCondition" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>
<c:choose>
	<c:when test="${searchCondition.listType eq 'listApprSignUser'}">
		<c:set var="formActUrl"     value="/approval/work/config/listApprSignUser.do" />
	</c:when>
</c:choose>	
			 
<script type="text/javascript">
<!-- 

(function($) {

	getApprDetail = function(entrustId,entrustType) {
		
		var url = iKEP.getContextRoot() + "/approval/work/apprlist/listApprEntrust.do?entrustId="+entrustId+"&entrustType="+entrustType;
	    var options = {
    		windowTitle : "결재내역",
    		documentTitle : "결재내역",
    		width:850, height:600, modal:true,
    		callback : function(result) {
    			iKEP.debug(result);
    		}
    	};
	    iKEP.popupOpen(url, options, "popupApprViewer");
	};
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {
		
		$jq("#signUserOfLeft").click();
		
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
		
	});
})(jQuery);  
//-->
</script>
			
<h1 class="none"><ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' /></h2>
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

<!--blockSearch Start-->
<form id="searchForm" method="post" action="<c:url value='${formActUrl}' />">
	
<div class="blockSearch">
</div>	
<!--//blockSearch End-->

<h1 class="none">
  <ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' />
</h1>

<!--blockListTable Start-->
<div class="blockListTable">

	<!--tableTop Start-->
	<div class="tableTop">
	<div class="tableTop_bgR"></div> 
		<div class="listInfo">
			<spring:bind path="searchCondition.pagePerRecord">
			<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="commonCode" items="${commonCode}">
					<option value="${commonCode.key}" <c:if test="${commonCode.key eq status.value}">selected="selected"</c:if>>${commonCode.value}</option>
				</c:forEach> 
			</select> 
			</spring:bind>
			<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
			<div align="right">
				
			</div>			
		</div>		
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	<table summary="<ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' />" id="listTable">
		<caption></caption>
		<colgroup>
		<col width="5%"/>
		<col width="25%"/>
		<col width="10%"/>
		<col width="50%"/>
		<col width="10%"/>
		</colgroup>
		<thead>
			<tr>
				<th scope="col">No</th>
				<th scope="col">위임기간</th>
				<th scope="col">위임자</th>
				<th scope="col">위임사유</th>
				<th scope="col">결재내역</th>
			</tr>
		</thead> 
		<tbody>	
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="5" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
					</tr>
			    </c:when>
			    <c:otherwise>
					<c:forEach var="aplist" items="${searchResult.entity}" varStatus="i">
						<c:choose>
				 			<c:when test="${i.count % 2 == 0}">
				 				<c:set var="className" value="bgWhite"/>
				 			</c:when>
				 			<c:otherwise>
				 				<c:set var="className" value="bgSelected"/>
				 			</c:otherwise>
				 		</c:choose>  
						<tr class="${className}">
							<td>${(searchCondition.recordCount-(searchCondition.pagePerRecord*(searchCondition.pageIndex-1))-i.count)+1}</td>
							<td><ikep4j:timezone pattern="yyyy.MM.dd" date="${aplist.startDate}"/> ~ 
								<ikep4j:timezone pattern="yyyy.MM.dd" date="${aplist.endDate}"/>							
							</td>
							<td><span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${aplist.userId}', 'bottom')">${aplist.userName}</a></span></td>
							<td class="textLeft">
								<div class="ellipsis">${aplist.reason}</div>
							</td>
							<td><a class="button_ic valign_bottom" href="#a" onclick="getApprDetail('${aplist.entrustId}','S');"><span><img src="/ikep4-webapp/base/images/icon/ic_btn_search.gif" alt="" />View</span></a></td>
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
