﻿<%--
=====================================================
* 기능 설명 : 양식 목록 
* 작성자    : approval
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preList"    value="ui.approval.apprForm.list" />
<c:set var="preCode"    value="ui.approval.common.code" />
<c:set var="preSearch"  value="ui.approval.common.searchCondition" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:if test="${searchCondition.templateType ne 'main'}">

<!--blockSearch Start-->
<div class="blockSearch">
	<div class="corner_RoundBox03">
		<table summary="form search">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="10%">
					<ikep4j:message pre='${preSearch}' key='searchFormName'/> 
			        </th>
					<td width="90%">		
					<spring:bind path="searchCondition.searchWord">
        			    <input name="${status.expression}" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='searchFormName'/>" size="35" type="text" class="inputbox" title='' onkeypress="checkKeyevent(event);" />     	
        			</spring:bind>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="searchBtn">
			<a href="#" onclick="readFormList();return false;">
				<img src="<c:url value="/base/images/theme/theme01/basic/btn_search.gif"/>" alt="search" />
			</a>
		</div>
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>				
	</div>
</div>	
<!--//blockSearch End-->
</c:if>

<!--blockListTable Start-->
<div class="blockListTable">
	<!--tableTop Start-->
	<div class="tableTop bgR">
		<div class="listInfo">
		<spring:bind path="searchCondition.pagePerRecord">  
			<select name="${status.expression}" onchange="readFormList();">
			<c:forEach var="code" items="${numList}">
				<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
			</c:forEach> 
			</select> 
		</spring:bind>
		<div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)</div>				
		<div class="clear"></div>
	</div>
	<div style="margin-top:20px;text-align:right;"><ikep4j:message pre="${preList}" key="favoriteOption" /> <input type="checkbox" onclick="sortFavorite(this);" <c:if test="${searchCondition.sortColumn eq 'FAVORITE_ID'}"> checked="checked"</c:if>></div>
	<!--//tableTop End-->

	<table summary="">
		<caption></caption>
		<col style="width:5%;"/>
		<col style="width:10%;"/>
		<col style="width:15%;"/>
	    <col style="width:45%;"/>
	    <col style="width:15%;"/>
	    <col style="width:10%;"/>
		<thead>
			<tr>
			    <th scope="col">
					NO
				</th>
				<th scope="col">
				    <a onclick="sort('APPR_DOC_TYPE', '<c:if test="${searchCondition.sortColumn eq 'APPR_DOC_TYPE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='apprDocType'/>
				    </a>
				    <c:choose>
					    <c:when test="${searchCondition.sortColumn eq 'APPR_DOC_TYPE' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					    <c:when test="${searchCondition.sortColumn eq 'APPR_DOC_TYPE' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				    </c:choose>		
				</th>
				<th scope="col">
				    <a onclick="sort('FORM_PARENT_ID', '<c:if test="${searchCondition.sortColumn eq 'FORM_PARENT_ID'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='apprCategory'/>
				    </a>
				    <c:choose>
					    <c:when test="${searchCondition.sortColumn eq 'FORM_PARENT_ID' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					    <c:when test="${searchCondition.sortColumn eq 'FORM_PARENT_ID' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				    </c:choose>		
				</th>
				<th scope="col">
				    <a onclick="sort('FORM_NAME', '<c:if test="${searchCondition.sortColumn eq 'FORM_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='formName'/>
				    </a>
				    <c:choose>
					    <c:when test="${searchCondition.sortColumn eq 'FORM_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					    <c:when test="${searchCondition.sortColumn eq 'FORM_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				    </c:choose>		
				</th>
				<th scope="col">
				    <a onclick="sort('REGIST_DATE', '<c:if test="${searchCondition.sortColumn eq 'REGIST_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
					<ikep4j:message pre='${preList}' key='registDate'/>
				    </a>
				    <c:choose>
					    <c:when test="${searchCondition.sortColumn eq 'REGIST_DATE' and searchCondition.sortType eq 'DESC'}"><img alt="desc" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
					    <c:when test="${searchCondition.sortColumn eq 'REGIST_DATE' and searchCondition.sortType eq 'ASC'}"><img alt="asc" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when> 
				    </c:choose>		
				</th>
				<th scope="col">
					<ikep4j:message pre="${preList}" key="favorite" />
				</th>
			</tr>
		</thead>
		<tbody>
	    <c:choose>
		    <c:when test="${searchResult.emptyRecord}">
				<tr>
					<td colspan="99" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
				</tr>
		    </c:when>
		    <c:otherwise>
		        <c:forEach var="item" items="${searchResult.entity}" varStatus="i">
				<tr>
				    <td>${(searchCondition.recordCount-(searchCondition.pagePerRecord*(searchCondition.pageIndex-1))-i.count)+1}</td>
				    <td>
				        <c:if test="${item.apprDocType eq 0}"><ikep4j:message pre='${preList}' key='apprDocType0'/></c:if>
						<c:if test="${item.apprDocType eq 1}"><ikep4j:message pre='${preList}' key='apprDocType1'/></c:if>    
				    </td>
				    <td>${item.formParentName}</td>
				    <td class="textLeft">
				    <div class="ellipsis" title="${item.formName}"><a href="#a" onclick="createApprDocForm('${item.formId}');">${item.formName}</a></div>
				    </td>
				    <td><ikep4j:timezone pattern="yyyy.MM.dd" date="${item.registDate}"/></td>
				    <td><a class="ic_rt_favorite<c:if test="${not empty item.favoriteId}"> select</c:if>" href="#" title="<ikep4j:message pre="${preList}" key="favorite" />" onclick="addFavorite(this, '${item.formId}');return false;"><span><ikep4j:message pre="${preList}" key="favorite" /></span></a></td>
			    </tr>
			    </c:forEach>				      
		    </c:otherwise> 
		</c:choose>  
		</tbody>
	</table>
	<!--Page Numbur Start--> 
    <spring:bind path="searchCondition.pageIndex">
    <ikep4j:pagination searchButtonId="${status.expression}" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
        <input  id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" />
    </spring:bind> 
    <!--//Page Numbur End--> 
</div>
<!--//blockListTable End-->	