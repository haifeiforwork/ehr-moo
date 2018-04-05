<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<!--blockListTable Start-->
<script type="text/javascript">
<!--
	function popupDetail_${portletConfigId}(apprId, instanceId, insLogId) {
		var top; 
		var left; 
		var width = 850;
		var height = 700;
		
		left = (screen.width - width) / 2; 
		top = (screen.height - height) / 2;		
		var param = "apprId="+apprId+"&instanceId="+instanceId+"&insLogId="+insLogId+"&linkType=mywork";
		var contextUrl	= "<c:url value='/wfapproval/work/apdoc/readApDoc.do'/>?"+param;
		window.open(contextUrl, "", "top=" + top + ", left=" + left +", width="+ width + ", height="+height+", resizable=yes,scrollbars=yes");
	}
//-->
</script>
<div id="${portletConfigId}"> 
	<div class="blockListTable msgTable">
		<table summary="My To-Do 목록">
			<caption></caption>
			<thead>
				<tr>
					<th scope="col" width="*">제목</th>
					<th scope="col" width="60">기안자</th>
					<th class="tdLast" scope="col" width="70">배정일</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${!empty apList.entity}">
						<c:forEach var="apListItem" items="${apList.entity}" varStatus="status">
							<tr onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
								<td class="textLeft">
									<div class="ellipsis">
										<a href="javascript:popupDetail_${portletConfigId}('${apListItem.appKey01}','${apListItem.instanceId}','${apListItem.insLogId}')" title="${apListItem.title}">
											${apListItem.title}
										</a>
									</div>
								</td>					
								<td><div class="ellipsis" title="${apListItem.instanceAuthorName}">${apListItem.instanceAuthorName}</div></td>
								<td class="tdLast"><ikep4j:timezone date="${apListItem.createDate}" pattern="yyyy.MM.dd"/></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="3" class="emptyRecord"><ikep4j:message pre='${prefixCommon}' key='emptyRecord' /></td>
						</tr>
					</c:otherwise>	
				</c:choose>		
			</tbody>
		</table>
	</div>
</div>
