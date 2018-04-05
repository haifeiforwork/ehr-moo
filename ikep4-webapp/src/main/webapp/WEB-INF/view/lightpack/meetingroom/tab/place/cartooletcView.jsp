<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />

<script type="text/javascript" language="javascript">
(function($) {
	$jq(document).ready(function() { 
		iKEP.setContentImageRendering("#tdRoomImage");
	});
})(jQuery);  
</script>

<h1 class="none"><ikep4j:message pre="${preHeader}" key="cartooletc" /></h1>

<!--blockDetail Start-->
<div class="blockDetail">
					
	<table summary="<ikep4j:message pre='${preDetail}' key='main.title' />">
		<caption></caption>	
		<colgroup>
				<col width="15%">
				<col width="35%">
				<col width="15%">
				<col width="35%">
		</colgroup>
		<tbody>
			
			<spring:bind path="cartooletc.cartooletcName">
			<tr> 
				<th scope="row" width="15%"><ikep4j:message pre='${preDetail}' key='cartooletcName' /></th>
				<td width="85%" class="textLeft" colspan="3">
					${status.value}
					&nbsp;
					<span id="id_favorite">
						<a class="ic_rt_favorite valign_middle ${checkFavorite eq 'Y' ? 'select' : ''}"
							<c:choose>
							    <c:when test="${checkFavorite != 'Y'}">
									title="<ikep4j:message pre='${preDetail}' key='addFavorite' />"
								</c:when>
								<c:otherwise>
									title="<ikep4j:message pre='${preDetail}' key='delFavorite' />"
								</c:otherwise>
							</c:choose>
							title="<ikep4j:message pre='${preDetail}' key='delFavorite' />"
						href="#${cartooletc.cartooletcId}"><span>favorite</span></a>	
					</span>
				</td> 
			</tr>				
			</spring:bind>
			
			<spring:bind path="cartooletc.categoryName">
			<tr> 
				<th scope="row" width="15%"><ikep4j:message pre='${preDetail}' key='category' /></th>
				<td class="textLeft">
					${status.value}
				</td> 
			</spring:bind>
			
			<spring:bind path="cartooletc.regionName">
				<th scope="row"><ikep4j:message pre='${preDetail}' key='region' /></th>
				<td class="textLeft">
					${status.value}
				</td> 
			</tr>				
			</spring:bind>
			
			<spring:bind path="cartooletc.managerName">
			<tr> 
				<th scope="row" width="15%"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td class="textLeft">
					${status.value}
				</td> 
			</spring:bind>
			
			<spring:bind path="cartooletc.phone">
				<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td class="textLeft">
					${status.value}
				</td> 
			</tr>				
			</spring:bind>

			<spring:bind path="cartooletc.description">
			<tr> 
				<th scope="row"><ikep4j:message pre='${preDetail}' key='description2' /></th>
				<td class="textLeft" colspan="3">
					${status.value}
				</td> 
			</tr>				
			</spring:bind>
			
			<spring:bind path="cartooletc.fileId">
			<tr> 
				<th scope="row" width="15%"><ikep4j:message pre='${preDetail}' key='fileId2' /></th>
				<td class="textCenter" colspan="3" id="tdRoomImage">
					<img id="fileImage" src="<c:url value='/support/fileupload/downloadFile.do?fileId=${status.value}' />" onerror="this.src='<c:url value="/base/images/common/noimage_170x170.gif"/>'" />
				</td> 
			</tr>				
			</spring:bind>
						
		</tbody>					
	</table>
</div>
<!--blockDetail End-->				