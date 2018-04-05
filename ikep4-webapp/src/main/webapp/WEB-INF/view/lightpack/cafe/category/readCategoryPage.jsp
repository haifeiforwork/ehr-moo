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
		$jq("#updateButton").click(function(){
			update("${category.categoryId}");			
		});
	});
	
})(jQuery);
</script>

						<!--blockDetail Start-->					
						<div class="blockDetail clear">
							<table summary="<ikep4j:message pre='${preDetail}' key='category' />">
								<caption></caption>
								<thead>
									<tr>
										<th scope="col" width="25%"><ikep4j:message pre='${preDetail}' key='categoryLocation' /></th>
										<td class="textLeft">
											${category.categoryLocation}
										</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<th width="25%" scope="col"><ikep4j:message pre='${preDetail}' key='categoryName' /></th>
										<td>
<!-- 										<input name="input" title="<ikep4j:message pre='${preDetail}' key='categoryName' />" class="inputbox w95" type="text" size="20" /> -->
											${category.categoryName}
										</td>
									</tr>
									<tr>
										<th width="25%" scope="col"><ikep4j:message pre='${preDetail}' key='categoryDescription' /></th>
										<td>
											<textarea name="textarea" class="w100" title="<ikep4j:message pre='${preDetail}' key='categoryDescription' />" readOnly cols="" rows="10">${category.description}</textarea>
											<span class="totalNum_s"></span>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<!--//blockDetail End-->	
						
						<!--blockButton Start-->
						<div class="blockButton"> 
							<ul>
								<li><a id="updateButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='update' /></span></a></li>
							</ul>
						</div>
						<!--//blockButton End-->
