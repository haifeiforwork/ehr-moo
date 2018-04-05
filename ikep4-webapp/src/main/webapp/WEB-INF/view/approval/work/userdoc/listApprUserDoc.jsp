<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preApCommList"    		value="ui.approval.work.apprlist" />
<c:set var="preButton"  			value="ui.approval.common.button" />
<c:set var="preSearch"  			value="ui.approval.common.searchCondition" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>
<c:choose>
	<c:when test="${searchCondition.listType eq 'listApprUserDoc'}">
		<c:set var="formActUrl"     value="/approval/work/userdoc/listApprUserDoc.do" />
	</c:when>
</c:choose>	
			 
<script type="text/javascript">
<!-- 

(function($) {

	//주의 : 글로벌 함수는 아래와 같이 글로벌 변수에 무명 함수를 assign해서 생성한다.
	
	/**
	 * 정렬 조건 변경 함수
	 * 
	 * @param {Object} sortColumn
	 * @param {Object} sortType
	 */
	 
	 f_Sort = function(sortColumn, sortType) {
		$("input[name=sortColumn]").val(sortColumn);
		
		if(sortType == '') sortType = 'ASC';
		
		if( sortType == 'ASC') {
			$("input[name=sortType]").val('DESC');	
		} else {
			$("input[name=sortType]").val('ASC');
		}
		
		$("#searchApListButton").click();
	};
	
	//문서 상세 정보
	getApprDetail = function(apprId,entrustUserId) {
		$("#searchForm input[name=apprId]:hidden").val(apprId);
		$("#searchForm input[name=entrustUserId]:hidden").val(entrustUserId);
		$("#searchForm").attr("action","<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>");
		$("#searchForm").submit(); 
	};
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {
		
		//left 메뉴
		$jq("#userDocTreeOfLeft").click();
		
		//전체 선택
		$("#allCheck").click(function() { 
			$("#searchForm input[name=chk_apprId]").attr("checked", $(this).is(":checked"));  
		});  
		
		//선택한 문서 결재 일괄결재
		$("#allDelete").click(function() {  
			var apprIds = new Array();

			$("#searchForm input[name=chk_apprId]").each(function(index) { 
				if($(this).is(":checked")) {
					apprIds.push($(this).val());
				}
			});		

			if(apprIds == "") {
				alert("<ikep4j:message pre='${preApCommList}' key='checkDelete' />");
				return;
			}
			
			if(confirm("<ikep4j:message pre='${preApCommList}' key='confirmDelete' />")) {
				
				$.post("<c:url value='/approval/work/userdoc/deleteApprUserDocList.do'/>", {"apprIds" : apprIds.toString()}) 
				.success(function(result) {
					if(result == "OK") {
						alert("<ikep4j:message pre='${preApCommList}' key='completeDelete' />");
						$.ajax({    
			    			url : "<c:url value="/approval/work/userdoc/listApprUserDoc.do"/>?folderId=${apprUserDocFolder.folderId}",     
			    			data : {},     
			    			success : function(result) {
			    				$jq("#mainContents").empty();
			    				$jq("#mainContents").append(result);
			    			},
			    			error : function(event, request, settings){
			    			 	alert("error");
			    			}
			    		}) ;
					}
				})
			} 
			
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
		
	});
})(jQuery);  
//-->
</script>
			
<h1 class="none"><ikep4j:message pre='${preApCommList}.pageTitle' key='${searchCondition.listType}' /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2>${apprUserDocFolder.folderName}</h2>
</div>
<!--//pageTitle End-->

<!--blockSearch Start-->
<form id="searchForm" method="post" action="<c:url value='${formActUrl}' />">
	<spring:bind path="searchCondition.apprId">
		<input name="${status.expression}" type="hidden" value="" title="hidden" />
	</spring:bind>
	<spring:bind path="searchCondition.listType">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="hidden" />
	</spring:bind>
	<spring:bind path="searchCondition.linkType">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="hidden" />
	</spring:bind>
	<spring:bind path="searchCondition.entrustUserId">
		<input name="${status.expression}" type="hidden" value="${status.value}" title="hidden" />
	</spring:bind>
	<input name="apprIds" type="hidden" value="" title="hidden" />
	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<spring:bind path="searchCondition.folderId">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="folderId" value="${apprUserDocFolder.folderId}"/>
	</spring:bind> 

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
		<col width="13%"/>
		<col width="8%"/>
		<col width="8%"/>
		<col width="29%"/>
		<col width="9%"/>
		<col width="14%"/>
		<col width="14%"/>
		</colgroup>
		<thead>
			<tr>
				<th scope="col">
					<input name="allCheck" id="allCheck" class="checkbox" title="checkbox" type="checkbox" value="" />
				</th>
				<th scope="col"><ikep4j:message pre='${preSearch}' key='searchApprDocNo'/></th>
				<th scope="col"><ikep4j:message pre='${preSearch}' key='apprStatus' /></th>
				<th scope="col"><ikep4j:message pre='${preSearch}' key='codeName' /></th>
				<th scope="col">
					<a onclick="f_Sort('apprTitle', '<c:if test="${searchCondition.sortColumn eq 'apprTitle'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preSearch}' key='apprTitle' />&nbsp;&nbsp;
					</a>
					<c:choose>
					    <c:when test="${searchCondition.sortColumn eq 'apprTitle' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					    <c:when test="${searchCondition.sortColumn eq 'apprTitle' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
				    </c:choose>
				</th>
				<th scope="col"><ikep4j:message pre='${preSearch}' key='searchUserId' /></th>
				<th scope="col">
				    <a onclick="f_Sort('apprReqDate', '<c:if test="${searchCondition.sortColumn eq 'apprReqDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preSearch}' key='apprReqDate' />&nbsp;&nbsp;
					</a>
					<c:choose>
					    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					    <c:when test="${searchCondition.sortColumn eq 'apprReqDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
				    </c:choose>				
				</th>
				<th scope="col">
					<a onclick="f_Sort('apprEndDate', '<c:if test="${searchCondition.sortColumn eq 'apprEndDate'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preSearch}' key='apprEndDate' />&nbsp;&nbsp;
					</a>
					<c:choose>
					    <c:when test="${searchCondition.sortColumn eq 'apprEndDate' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					    <c:when test="${searchCondition.sortColumn eq 'apprEndDate' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
				    </c:choose>
				</th>
			</tr>
		</thead> 
		<tbody>
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="8" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
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
							<td>
								<input type="checkbox" name="chk_apprId" id="chk_apprId" value="${aplist.apprId}" title="checkbox">
							</td>
							<td><div class="ellipsis" title="${aplist.apprDocNo}" style="cursor:default;">${aplist.apprDocNo}</div></td>
							<td>
								<c:choose>
									<c:when test="${aplist.mListType eq 'APPR_AD' or searchCondition.searchListType eq 'appr_ad'}">
										<ikep4j:message pre='${preSearch}' key='apprAd' />
									</c:when>
									<c:when test="${aplist.mListType eq 'APPR_AL' or searchCondition.searchListType eq 'appr_al'}">
										<ikep4j:message pre='${preSearch}' key='apprAl' />
									</c:when>
									<c:when test="${aplist.mListType eq 'APPR_AL2' or searchCondition.searchListType eq 'appr_al2'}">
										<ikep4j:message pre='${preSearch}' key='apprAl2' />
									</c:when>
									<c:when test="${aplist.mListType eq 'APPR_AR' or searchCondition.searchListType eq 'appr_ar'}">
										<ikep4j:message pre='${preSearch}' key='apprAr' />
									</c:when>
									<c:when test="${aplist.mListType eq 'APPR_ARE' or searchCondition.searchListType eq 'appr_are'}">
										<ikep4j:message pre='${preSearch}' key='apprAre' />
									</c:when>
									<c:when test="${aplist.mListType eq 'APPR_DEPT' or searchCondition.searchListType eq 'appr_dept'}">
										<ikep4j:message pre='${preSearch}' key='apprDept' />
									</c:when>
								</c:choose>
							</td>
							<td>${aplist.codeName}</td>
							<td class="textLeft">
								<div class="ellipsis"><a href="#a" onclick="getApprDetail('${aplist.apprId}');" title="${aplist.apprTitle}">${aplist.apprTitle}</a></div>
							</td>
							<td><span class="name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${aplist.registerId}', 'bottom')">${aplist.registerName}</a></span></td>
							<td>
								<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprReqDate}"/>
							</td>
							<td>
								<ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprEndDate}"/>	
							</td>						
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
			<li><a id="allDelete"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->	
