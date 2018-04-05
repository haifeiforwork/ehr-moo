<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preMessage"	value="message.approval.work.apprLine.listApprLineView"/>					
<c:set var="preHeader"	value="ui.approval.work.apprLine.listApprLineView.header"/>
<c:set var="preList"	value="ui.approval.work.apprLine.listApprLineView.list"/>
<c:set var="preView"	value="ui.approval.work.apprLine.listApprLineView.view"/>
<c:set var="preSearch"	value="ui.approval.work.apprLine.listApprLineView.search"/>
<c:set var="preButton"	value="ui.approval.work.apprLine.listApprLineView.button"/>	
<c:set var="preCommon"	value="ui.approval.common.apprDefLine.code"/>

<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal"	value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript">
<!-- 
(function($) {
	
	var	apprType	=	new Array("<ikep4j:message pre='${preCommon}' key='appr' />",
		"<ikep4j:message pre='${preCommon}' key='agree' />",
		"<ikep4j:message pre='${preCommon}' key='choiceAgree' />",
		"<ikep4j:message pre='${preCommon}' key='receive' />");
			
	// 결재선 정보
	var	items		=	[];

	apprDocStatus = function(apprId){
			
		dialog = new iKEP.showDialog({     
			title 		: "<ikep4j:message pre='${preView}' key='subLine.popTitle' />",
			url 		: "<c:url value='/approval/work/apprLine/listApprLineForm.do'/>?apprId="+apprId,
			modal 		: true,
			width 		: 800,
			height 		: 430,
			params 		: {apprId:apprId},
			callback : function(result) {
					
			}
		});
	};

	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {

		//$("#divTab").tabs(); 
		
	});
})(jQuery);  
//-->
</script>
			
<!--popup_contents Start-->
<div id="popup_contents_2">
	<div class="blockBlank_10px"></div>
	
	<!--subTitle_2 Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre='${preView}' key='subLine.pageTitle' /></h2>
	</div>
	<!--//subTitle_2 End-->		
	<c:if test="${apprType!='3'}">

		<!--blockListTable Start-->
		<div class="blockDetail">
		
			<table summary="<ikep4j:message pre='${preView}' key='subLine.summary' />">
				<caption></caption>
				<col style="width: 15%;"/>
				<col style="width: 15%;"/>
				<col style="width: 12%;"/>
				<col style="width: 17%;"/>
				<col />
				
				<thead>
					<tr>				
						<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='subLine.agreeDept' /></th>
						<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='subLine.approver' /></th>
						<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='subLine.status' /></th>
						<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='subLine.apprDate' /></th>
						<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='subLine.message' /></th>
					</tr>
				</thead>
				
				<tbody>
					<c:if test="${!empty subList}">
					<c:forEach var="subList" items="${subList}" varStatus="status">
						
						<tr>
						
						
						<!-- 접수자 정보 -->
						<c:if test="${subList.lineCount>1}">
						<td class="textCenter" rowspan="${subList.lineCount+1 }">${subList.apprGroupName}</td>
						<td class="textCenter">${subList.registerName} ${subList.registerJobTitle}</td>				
						<td class="textCenter"><ikep4j:message pre='${preView}' key='subLine.receive' /></td>
						<td class="textCenter"><ikep4j:timezone date="${subList.registDate}" pattern="yyyy.MM.dd HH:mm:ss"/></td>
						<td class="textLeft">${subList.registerMessage }</td>
						</tr>
						</c:if>	
						
						<c:if test="${subList.lineCount==0}">
						<td class="textCenter" rowspan="${subList.lineCount+1 }">${subList.apprGroupName}</td>			
						<td class="textCenter"></td>				
						<td class="textCenter">
						<c:if test="${subList.apprDocStatus==3}">
							<c:forEach var="code" items="${commonCode.apprStatusList}">
								<c:if test="${code.key eq subList.apprDocStatus}"><ikep4j:message key='${code.value}'/></c:if>
							</c:forEach>
						</c:if>
						<c:if test="${subList.apprDocStatus!=3}">
							<ikep4j:message pre='${preView}' key='subLine.noneReceive' />
						</c:if>
						</td>
						<td class="textCenter"></td>
						<td class="textCenter"></td>
						</tr>
						</c:if>	
						
						
						<c:if test="${!empty apprLineList}">						
							<c:forEach var="item" items="${apprLineList}">						
								<c:if test="${subList.apprId==item.apprId}">
								<c:if test="${subList.lineCount!=1 && subList.registerId!=item.approverId}">
								<tr>	
								</c:if>
								<c:if test="${subList.lineCount==1 && subList.registerId==item.approverId}">
								<td class="textCenter" rowspan="${subList.lineCount }">${subList.apprGroupName}</td>	
								</c:if>	
								<c:if test="${subList.lineCount==1 && subList.registerId!=item.approverId}">
								<td class="textCenter" rowspan="${subList.lineCount }">${subList.apprGroupName}</td>	
								</c:if>									
								<td class="textCenter">
								${item.approverName} ${item.approverJobTitle}
								</td>
										
								<td class="textCenter">
									<c:forEach var="code" items="${commonCode.apprStatusList}">
										<c:if test="${code.key eq item.apprStatus}"><ikep4j:message key='${code.value}'/></c:if>
									</c:forEach>	
								</td>
								<td class="textCenter"><ikep4j:timezone date="${item.viewDate}" pattern="yyyy.MM.dd HH:mm:ss"/></td>
								<td class="textLeft">${item.apprMessage }</td>
								</tr>
								</c:if>	
							</c:forEach>
						</c:if>
					</c:forEach>
					</c:if>										
				</tbody>
			</table>				
		
		</div>
	</c:if>
	<%--
	<c:if test="${apprType=='3'}">

		<!--blockListTable Start-->
		<div class="blockDetail">
		
			<table summary="<ikep4j:message pre='${prePreView}' key='summary' />">
				<caption></caption>
				<col style="width: 10%;"/>
				<col style="width: 12%;"/>
				<col style="width: 12%;"/>
				<col style="width: 12%;"/>
				<col style="width: 17%;"/>
				<col />
				<thead>
					<tr>				
						<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='subLine.receiveDept' /></th>
						<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='subLine.approver' /></th>
						<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='subLine.status' /></th>
						<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='subLine.apprDate' /></th>
						<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='subLine.message' /></th>					
					</tr>
				</thead>
				<tbody>
					<c:if test="${!empty subList}">
					<c:forEach var="subList" items="${subList}" varStatus="status">
						<tr><td class="textCenter" rowspan="${subList.lineCount+1 }">${subList.apprGroupName}</td>
						
						<!-- 접수자 정보 -->
						<c:if test="${subList.lineCount>0}">				
						<td class="textCenter">${subList.registerName} ${subList.registerJobTitle}</td>				
						<td class="textCenter"><ikep4j:message pre='${preView}' key='subLine.receive' /></td>
						<td class="textCenter"><ikep4j:timezone date="${subList.registDate}" pattern="yyyy.MM.dd HH:mm:ss"/></td>
						<td class="textCenter">${subList.registerMessage }</td>
						</tr>
						</c:if>	

						<c:if test="${subList.lineCount==0}">			
						<td class="textCenter"></td>				
						<td class="textCenter"><ikep4j:message pre='${preView}' key='subLine.noneReceive' /></td>
						<td class="textCenter"></td>
						<td class="textCenter"></td>
						</tr>
						</c:if>
						
						
						<c:if test="${!empty apprLineList}">
							<c:forEach var="item" items="${apprLineList}">
								<c:if test="${subList.apprId==item.apprId}">
								<tr>
								<td class="textCenter"> 
								${item.approverName} ${item.approverJobTitle}
								</td>
								
								<td class="textCenter">
									<c:forEach var="code" items="${commonCode.apprStatusList}">
										<c:if test="${code.key eq item.apprStatus}"><ikep4j:message key='${code.value}'/></c:if>
									</c:forEach>	
								</td>
								<td class="textCenter"><ikep4j:timezone date="${item.viewDate}" pattern="yyyy.MM.dd HH:mm:ss"/></td>
								<td class="textCenter">${item.apprMessage }</td>
								</tr>
								</c:if>	
							</c:forEach>
						</c:if>
					</c:forEach>
					</c:if>										
				</tbody>
			</table>
		</div>
	</c:if>
	--%>
</div>
