<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/base/common/taglibs.jsp"%>  

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="preMsg"   value="ui.approval.common.portlet" />
<c:set var="preList"   value="ui.approval.common.portlet.request" />

<script type="text/javascript">
//<![CDATA[
(function($){	 
	
	//문서 결재 정보 조회
	getViewApprLine_${portletConfigId} = function(apprId) {
		
		var $divContext = $jq("#"+apprId+"_${user.userId}"),
			$trRow = $jq("#apprLineTr"+apprId+"_${user.userId}");
		
		if($divContext.is(":hidden")) {
			
			$jq("#listTable").ajaxLoadStart("button");
			
			$jq("tr[id^=apprLineTr]").each(function() { 
				var rowApprid = $(this).find("div").attr("id");
				
				$jq("#"+rowApprid).empty();
				$jq("#apprLineTr"+rowApprid).css("display","block");
				$jq("#"+rowApprid).hide();
				$jq("#apprLineTr"+rowApprid).hide();
			});
			$.post("<c:url value='/approval/work/apprLine/listApprLineInfo.do'/>", {apprId	:	apprId})
				.success(function(result) {
					$divContext.append(result);
					$trRow.css("display","");
					$divContext.show();
					$jq("#listTable").ajaxLoadComplete();
				})
				.error(function(event, request, settings) { alert("error"); });
		}else {
			$divContext.empty();
			$trRow.css("display","block");
			$divContext.hide();
			$trRow.hide();
		}
		
	};
	
	//문서 상세 정보
	getApprDetail_${portletConfigId} = function(apprId) {
		var listType = "myRequestList";
		location.href="<c:url value='/approval/work/apprWorkDoc/viewApprDoc.do'/>?apprId="+apprId+"&listType="+listType;
	};
	
	$(document).ready(function() {
		
	}); 

})(jQuery);	
//]]>
</script> 	
<!--blockListTable Start-->
<div class="poEdit">
	<div class="textRight">
		<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_01.png'/>" alt="<ikep4j:message pre='${preMsg}' key='progress'/>" /><ikep4j:message pre='${preMsg}' key='progress'/>
		<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_02.png'/>" alt="<ikep4j:message pre='${preMsg}' key='complete'/>" /><ikep4j:message pre='${preMsg}' key='complete'/>
		<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_03.png'/>" alt="<ikep4j:message pre='${preMsg}' key='reject'/>" /><ikep4j:message pre='${preMsg}' key='reject'/>
		<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_04.png'/>" alt="<ikep4j:message pre='${preMsg}' key='return'/>" /><ikep4j:message pre='${preMsg}' key='return'/>
		<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_07.png'/>" alt="<ikep4j:message pre='${preMsg}' key='return1'/>" /><ikep4j:message pre='${preMsg}' key='return1'/>
		<img style="vertical-align:text-top;" src="<c:url value='/base/images/icon/signal_06.png'/>" alt="<ikep4j:message pre='${preMsg}' key='wait'/>" /><ikep4j:message pre='${preMsg}' key='wait'/>
	</div>
</div>
<div id="${portletConfigId}"> 
	<div id="portletlightpackRecentBoard" class="tableList_1"> 
		<table summary="<ikep4j:message pre="${preList}" key="summary" />" id="listTable">
			<caption></caption>
			<colgroup>
				<col width="10%"/>
				<col width="10%"/>
				<col width="*"/>
				<col width="15%"/>
				<col width="10%"/>
			</colgroup> 
			<tbody>
				<c:choose>
				    <c:when test="${searchResult.emptyRecord}">
						<tr class="bgWhite">
							<td colspan="5" class="emptyRecord"><ikep4j:message pre='${preMsg}' key='emptyRecord' /></td> 
						</tr>				        
				    </c:when>
				    <c:otherwise>
						<c:forEach var="aplist" items="${searchResult.entity}"> 
						<tr>  
							<td class="textCenter">
								<c:if test="${aplist.apprDocType eq '0'}"><ikep4j:message pre='${preList}' key='apprDocType0' /></c:if>
								<c:if test="${aplist.apprDocType eq '1'}"><ikep4j:message pre='${preList}' key='apprDocType1' /></c:if>
							</td>
							<td class="textCenter">${aplist.codeName}</td>
							<td class="textLeft">
								<div class="ellipsis"><a href="#a" onclick="getApprDetail_${portletConfigId}('${aplist.apprId}');" title="${aplist.apprTitle}">${aplist.apprTitle}</a></div>
							</td>
							<td class="textRight"><span class="date"><ikep4j:timezone pattern="yyyy.MM.dd HH:mm" date="${aplist.apprReqDate}"/></span></td>
							<td class="textCenter">
								<c:choose>
									<c:when test="${aplist.apprDocStatus == '1'}">
										<a href="#a" onclick="getViewApprLine_${portletConfigId}('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_01.png'/>" alt="<ikep4j:message pre='${preMsg}' key='progress'/>" /></a>
									</c:when>
									<c:when test="${aplist.apprDocStatus == '2'}">
										<a href="#a" onclick="getViewApprLine_${portletConfigId}('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_02.png'/>" alt="<ikep4j:message pre='${preMsg}' key='complete'/>" /></a>
									</c:when>
									<c:when test="${aplist.apprDocStatus == '3'}">
										<a href="#a" onclick="getViewApprLine_${portletConfigId}('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_03.png'/>" alt="<ikep4j:message pre='${preMsg}' key='reject'/>" /></a>
									</c:when>
									<c:when test="${aplist.apprDocStatus == '4'}">
										<a href="#a" onclick="getViewApprLine_${portletConfigId}('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_04.png'/>" alt="<ikep4j:message pre='${preMsg}' key='return'/>" /></a>
									</c:when>
									<c:when test="${aplist.apprDocStatus == '6'}">
										<img src="<c:url value='/base/images/icon/signal_06.png'/>" alt="<ikep4j:message pre='${preMsg}' key='wait'/>" />
									</c:when>
									<c:when test="${aplist.apprDocStatus == '7'}">
										<a href="#a" onclick="getViewApprLine_${portletConfigId}('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_07.png'/>" alt="<ikep4j:message pre='${preMsg}' key='return1'/>" /></a>
									</c:when>
									<c:when test="${aplist.apprDocStatus == '0'}">
										<a href="#a" onclick="getViewApprLine_${portletConfigId}('${aplist.apprId}');"><img src="<c:url value='/base/images/icon/signal_00.png'/>" alt="<ikep4j:message pre='${preMsg}' key='temp'/>" /></a>
									</c:when>
								</c:choose>
							</td>
						</tr>
						<tr style="display:none;" id="apprLineTr${aplist.apprId}_${user.userId}">
							<td colspan="5">
								<div id="${aplist.apprId}_${user.userId}" style="display:none;">
								</div>					
							</td>
						</tr>  
						</c:forEach>				      
				    </c:otherwise> 
				</c:choose>  
			</tbody> 
		</table> 
	</div> 
</div>