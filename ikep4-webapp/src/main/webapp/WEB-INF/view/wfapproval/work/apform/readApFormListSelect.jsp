<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.wfapproval.work.apform.list.header" /> 
<c:set var="preList"    value="ui.wfapproval.work.apform.list" />
<c:set var="preView"    value="ui.wfapproval.work.apform.view.form" />
<c:set var="preButton"  value="ui.wfapproval.common.button" />
<c:set var="preMessage" value="ui.wfapproval.common.message" />
<c:set var="preSearch"  value="ui.wfapproval.common.searchCondition" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
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
			
			$("#searchApFormButton").click();
		};
		
		/**
		 * onload시 수행할 코드
		 */
		$(document).ready(function() {
			
			/**
			 * 검색버튼
			 */
			$("#searchApFormButton").click(function() {
				$("input[name=pageIndex]").val('1');
				$("#searchForm").submit(); 
				return false; 
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
			
			/**
		    * 버튼영역 실행
		    */
			$("#readApFormSelectListButton a").click(function(){
	            switch (this.id) {
	                case "listApFormButton":
						location.href='listApFormSelect.do?linkType=${linkType}';
						break;
	                default:
	                    break;
	            }
	        });
		});
		
	})(jQuery);  
//-->
</script>
			
<h1 class="none"><ikep4j:message pre="${preHeader}" key="title" /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.4depth" /></li>
		</ul>
	</div>
</div>
<!--//pageTitle End-->

<!--blockSearch Start-->
<form id="searchForm" method="post">
	
	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	<spring:bind path="searchCondition.formId">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 

<div class="blockSearch">
	<div class="corner_RoundBox03">
		<table summary="<ikep4j:message pre='${preList}' key='summary1'/>">
			<caption></caption>
			<tbody>
				<tr>
					<spring:bind path="searchCondition.formClassCd">
					<th scope="row" width="5%"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
					<td width="25%">
						<select name="${status.expression}" title="<ikep4j:message pre='${preView}' key='${status.expression}' />">
							<option value="">전체</option>
							<c:forEach var="apCode" items="${listFormClassCd}">
								<option value="${apCode.codeId}" <c:if test="${apCode.codeId eq status.value}">selected="selected"</c:if>>${apCode.krName}</option>
							</c:forEach>
						</select>
					</td>
					</spring:bind>
					<spring:bind path="searchCondition.formTypeCd">
					<th scope="row" width="5%"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
					<td width="25%">
						<select name="${status.expression}" title="<ikep4j:message pre='${preView}' key='${status.expression}' />">
							<option value="">전체</option>
							<c:forEach var="apCode" items="${listFormTypeCd}">
								<option value="${apCode.codeId}" <c:if test="${apCode.codeId eq status.value}">selected="selected"</c:if>>${apCode.krName}</option>
							</c:forEach>
						</select>
					</td>
					</spring:bind>
					<spring:bind path="searchCondition.formName">
					<th scope="row" width="5%"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
					<td width="35%">
						<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preView}' key='${status.expression}' />'  size="35" />
					</td>
					</spring:bind>
				</tr>								
			</tbody>
		</table>
		<div class="searchBtn">
			<a id="searchApFormButton" name="searchApFormButton" href="#a"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif' />" alt="검색" /></a>
		</div>
		
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>
	</div>
</div>	
<!--//blockSearch End-->

<!--blockListTable Start-->
<div class="blockListTable">

	<!--tableTop Start-->
	<div class="tableTop">
		<div class="listInfo">
			<spring:bind path="searchCondition.pagePerRecord">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="code" items="${commonCode.pageNumList}">
					<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
				</c:forEach> 
				</select> 
			</spring:bind>
			<div class="totalNum">${apFormListSelect.pageIndex}/ ${apFormListSelect.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${apFormListSelect.recordCount}</span>)</div>
		</div>			
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	
	
	<table summary="<ikep4j:message pre='${preList}' key='summary2'/>">
		<caption></caption>
		<col style="width: 10%;"/>
		<col style="width: 10%;"/>
		<col style="width: 25%;"/>
		<col/>
		<thead>
			<tr>
				<th scope="col">
					<a onclick="f_Sort('FORM_CLASS_CD', '<c:if test="${searchCondition.sortColumn eq 'FORM_CLASS_CD'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preView}' key='formClassCd' />
					</a>
				</th>
				<th scope="col">
					<a onclick="f_Sort('FORM_TYPE_CD', '<c:if test="${searchCondition.sortColumn eq 'FORM_TYPE_CD'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preView}' key='formTypeCd' />
					</a>
				</th>
				<th scope="col">
					<a onclick="f_Sort('FORM_NAME', '<c:if test="${searchCondition.sortColumn eq 'FORM_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preView}' key='formName' />
					</a>
				</th>
				<th scope="col">
					<a onclick="f_Sort('FORM_DESC', '<c:if test="${searchCondition.sortColumn eq 'FORM_DESC'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preView}' key='formDesc' />
					</a>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
			    <c:when test="${apFormListSelect.emptyRecord}">
					<tr>
						<td colspan="4" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
					</tr>
			    </c:when>
			    <c:otherwise>
					<c:forEach var="apform" items="${apFormListSelect.entity}" varStatus="apFormItem">
						<c:choose>
				 			<c:when test="${apFormItem.count % 2 == 0}">
				 				<c:set var="className" value="bgWhite"/>
				 			</c:when>
				 			<c:otherwise>
				 				<c:set var="className" value="bgSelected"/>
				 			</c:otherwise>
				 		</c:choose>
						<tr class="${className}">
							<td>${apform.formClassName}</td>
							<td>${apform.formTypeName}</td>
							<td class="textLeft">
								<a href="<c:url value='/wfapproval/work/apdoc/createApDoc.do?formId='/>${apform.formId}&linkType=${linkType}">${apform.formName}</a>
							</td>
							<td class="textLeft">${apform.formDesc}</td>
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

<!--blockButton Start-->
<div class="blockButton" id="readApFormSelectListButton"> 
	<ul> 
		<li><a id="listApFormButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->