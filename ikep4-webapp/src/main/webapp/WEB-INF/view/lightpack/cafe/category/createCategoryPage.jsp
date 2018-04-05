<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preDetail"  value="message.lightpack.cafe.category.detail" />
<c:set var="preButton"  value="message.lightpack.cafe.category.button" />
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script language="JavaScript">
(function($) {
	$jq(document).ready(function(){
		//저장 여부 클릭
		$jq("#saveButton").click(function() {
			
			save();
		});
	});
	
})(jQuery);
</script>

<!--blockDetail Start-->	
<form id="categoryForm" method="post" action="<c:url value='/lightpack/cafe/category/saveCategory.do'/>">	
					<spring:bind path="category.parentId">
						<input type="hidden" title="parentId" name="${status.expression}"  value="${status.value}"/>
					</spring:bind>
					<spring:bind path="category.categoryId">
						<input type="hidden" name="categoryId" value='${status.value}'/>
					</spring:bind>
					<input type="hidden" name="mode" value="${mode}"/>
			
						<div class="blockDetail clear">
							<table summary="<ikep4j:message pre='${preDetail}' key='category' />">
								<caption></caption>
								<thead>
									<c:if test="${mode eq 'U'}">
									<tr>
										<th scope="col" width="25%"><ikep4j:message pre='${preDetail}' key='categoryLocation' /></th>
										<td class="textLeft">
											${category.categoryLocation}
										</td>
									</tr>
									</c:if>
								</thead>
								<tbody>
									<tr>
										<th width="25%" scope="col"><ikep4j:message pre='${preDetail}' key='categoryName' /></th>
										<td>
											<spring:bind path="category.categoryName">
												<input name="${status.expression}" title="<ikep4j:message pre='${preDetail}' key='categoryName' />" class="inputbox w95" type="text" size="20" value='${status.value}'/>
											</spring:bind>
										</td>
									</tr>
									<tr>
										<th width="25%" scope="col"><ikep4j:message pre='${preDetail}' key='categoryEnglishName' /></th>
										<td>
											<spring:bind path="category.categoryEnglishName">
												<input name="${status.expression}" title="<ikep4j:message pre='${preDetail}' key='categoryEnglishName' />" class="inputbox w95" type="text" size="20" value='${status.value}'/>
											</spring:bind>
										</td>
									</tr>
									<tr>
										<th width="25%" scope="col"><ikep4j:message pre='${preDetail}' key='categoryDescription' /></th>
										<td>
											<spring:bind path="category.description">
												<textarea name="${status.expression}" class="w100" title="<ikep4j:message pre='${preDetail}' key='categoryDescription' />" cols="" rows="10">${status.value}</textarea>
												<span class="totalNum_s"></span>
											</spring:bind>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<!--//blockDetail End-->	
						
						<!--blockButton Start-->
						<div class="blockButton"> 
							<ul>
								<li><a id="saveButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save' /></span></a></li>
							</ul>
						</div>
						<!--//blockButton End-->
</form>