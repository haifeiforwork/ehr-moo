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
var dialogWindow = null;
var fnCaller;

(function($) {
	
	var	apprType	=	new Array("<ikep4j:message pre='${preCommon}' key='appr' />",
									"<ikep4j:message pre='${preCommon}' key='agree' />",
									"<ikep4j:message pre='${preCommon}' key='choiceAgree' />",
									"<ikep4j:message pre='${preCommon}' key='receive' />");
	// 결재선 정보
	var	items		=	[];
	
	fnCaller = function (params, dialog) {
		if(params) {
		}

		dialogWindow = dialog;
		$("#cancelButton").click(function() {
			dialogWindow.close();
		});
	};
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {

		$("#divTab").tabs(); 
	});
})(jQuery);  
//-->
</script>
			
<!--tab Start-->		
<div id="divTab" class="iKEP_tab_poll">
	<ul>
		<li><a href="#tabs-1"><ikep4j:message pre='${preView}' key='line' /></a></li>
		<c:if test="${apprDoc.apprDocType=='1'}">
		<li><a href="#tabs-2"><ikep4j:message pre='${preView}' key='receive' /></a></li>
		</c:if>
		<li><a href="#tabs-3"><ikep4j:message pre='${preView}' key='apprMessage' /></a></li>
	</ul>	
	<div class="tab_conbox">
		<div id="tabs-1">
			<!--blockListTable Start-->
			<div class="blockDetail">
				<table summary="<ikep4j:message pre='${preView}' key='summary' />">
					<caption></caption>
					<col style="width: 10%;"/>
					<col />
					<col style="width: 12%;"/>
					<col style="width: 12%;"/>
					<col style="width: 17%;"/>
					<col style="width: 17%;"/>
					<thead>
						<tr>
							<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='line.no' /></th>
							<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='line.approver' /></th>
							<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='line.apprType' /></th>
							<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='line.status' /></th>
							<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='line.viewDate' /></th>
							<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='line.apprDate' /></th>										
						</tr>
					</thead>
					<tbody>
	
						<c:if test="${!empty apprLineList}">
						<c:forEach var="item" items="${apprLineList}">
						<c:if test="${item.apprType!=3}">
						<tr>
						<td class="textCenter">${item.apprOrder}</td>
						<td class="textCenter">
						
						<c:if test="${item.approverType==0}">
							${item.approverName} ${item.approverJobTitle} ${item.approverGroupName} 
							<c:if test="${!empty item.entrustUserId}">
								<br />
								<ikep4j:message pre='${preView}' key='entrust' /> ${item.entrustUserName} ${item.entrustJobTitle} ${item.entrustGroupName}
							</c:if>
						</c:if>
						
						<c:if test="${item.approverType==1 && item.apprLineList ==null}">
						${item.approverGroupName}
						</c:if>	
														
						<c:if test="${item.approverType==1  && item.apprLineList !=null}">
						<c:forEach var="itemSub" items="${item.apprLineList}" varStatus='varStatus'>
							<c:if test="${itemSub.apprDate !=''}">
								${itemSub.approverGroupName} ( ${itemSub.approverName} ${itemSub.approverJobTitle} )
								<c:if test="${!empty itemSub.entrustUserId}">
									<br />
									<ikep4j:message pre='${preView}' key='entrust' /> ${itemSub.approverGroupName} ( ${itemSub.entrustUserName} ${itemSub.entrustJobTitle} )
								</c:if>
							</c:if>
						</c:forEach> 
						</c:if>	
						
						</td>
						<td class="textCenter">	
							<c:if test="${receiveEnd}">
							<ikep4j:message pre='${preView}' key='apprMessage.receiveEnd' />
							</c:if>
								
							<c:if test="${!receiveEnd}">
							<c:forEach var="code" items="${commonCode.apprTypeList}"> 
								<c:if test="${code.key eq item.apprType}"><ikep4j:message key='${code.value}'/></c:if>
							</c:forEach>
							</c:if>													
					
						</td>							
						<td class="textCenter">
						<c:forEach var="code" items="${commonCode.apprStatusList}">
							<c:if test="${code.key eq item.apprStatus}"><ikep4j:message key='${code.value}'/></c:if>
						</c:forEach>	
						</td>
						<td class="textCenter"><ikep4j:timezone date="${item.viewDate}" pattern="yyyy.MM.dd HH:mm:ss"/></td>
						<td class="textCenter"><ikep4j:timezone date="${item.apprDate}" pattern="yyyy.MM.dd HH:mm:ss"/></td>
						</tr>
						</c:if>	
						</c:forEach>
						</c:if>	
		
					</tbody>
				</table>
			</div>					
		</div>
		<c:if test="${apprDoc.apprDocType=='1'}">
		<div id="tabs-2">
			<!--blockListTable Start-->
			<div class="blockDetail">
				<table summary="<ikep4j:message pre='${preView}' key='summaryRcv' />">
					<caption></caption>
					<col style="width: 25%;"/>
					<col style="width: 12%;"/>
					<col style="width: 9%;"/>
					<col style="width: 15%;"/>
					<col />
				<thead>
				<tr>
					<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='receive.receiveDept' /></th>
					<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='receive.approver' /></th>
					<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='receive.status' /></th>
					<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='receive.apprDate' /></th>
					<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='receive.message' /></th>
				</tr>
				</thead>
				<tbody>

					<c:if test="${!empty receiveSubList}">
					<c:forEach var="subList" items="${receiveSubList}" varStatus="status">
					<tr>
					<td class="textCenter" rowspan="${subList.lineCount+2 }">
					<c:if test="${!empty subList.approverId}">
					${subList.approverName} ${subList.approverJobTitle} ( ${subList.apprGroupName} )
					</c:if>	
					<c:if test="${empty subList.approverId}">
					${subList.apprGroupName}
					</c:if>	
					</td></tr>
					
					<!-- 접수자 정보 -->
					<c:if test="${subList.lineCount>0 && subList.apprDocStatus!='6'}">
					<tr>				
					<td class="textCenter">${subList.registerName} ${subList.registerJobTitle}</td>				
					<td class="textCenter"><ikep4j:message pre='${preView}' key='receive.receive' /></td>
					<td class="textCenter"><ikep4j:timezone date="${subList.registDate}" pattern="yyyy.MM.dd HH:mm:ss"/></td>
					<td class="textLeft">${subList.registerMessage }</td>
					</tr>
					</c:if>	
					
					<!-- 접수자 정보 -->
					<c:if test="${subList.lineCount>0 && subList.apprDocStatus=='6'}">
					<tr>				
					<td class="textCenter"></td>				
					<td class="textCenter"><ikep4j:message pre='${preView}' key='receive.noneReceive' /></td>
					<td class="textCenter"></td>
					<td class="textCenter"></td>
					</tr>
					</c:if>	
					
					<c:if test="${subList.lineCount==0}">
					<tr>				
					<td class="textCenter"></td>				
					<td class="textCenter"><ikep4j:message pre='${preView}' key='receive.noneReceive' /></td>
					<td class="textCenter"></td>
					<td class="textCenter"></td>
					</tr>
					</c:if>	
					
					<tr>
					<c:if test="${!empty receiveApprLineList}">
					<c:forEach var="item" items="${receiveApprLineList}">
					
					<c:if test="${subList.apprId==item.apprId && subList.apprDocStatus!='6'}">						
					<td class="textCenter">
						${item.approverName} ${item.approverJobTitle}
						<c:if test="${!empty item.entrustUserId}">
						<br />
						<ikep4j:message pre='${preView}' key='entrust' /> ${item.entrustUserName} ${item.entrustJobTitle}
						</c:if>					
					</td>
							
					<td class="textCenter">
						<c:if test="${receiveEnd2}">
						<ikep4j:message pre='${preView}' key='apprMessage.receiveEnd' />
						</c:if>
								
						<c:if test="${!receiveEnd2}">
						<c:forEach var="code" items="${commonCode.apprStatusList}"> 
							<c:if test="${code.key eq item.apprStatus}"><ikep4j:message key='${code.value}'/></c:if>
						</c:forEach>
						</c:if>						
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
		</div>
		</c:if>
		
		<div id="tabs-3">
			<!--blockListTable Start-->
			<div class="blockDetail">
				<table summary="<ikep4j:message pre='${preView}' key='summaryMsg' />">
					<caption></caption>
					<col style="width: 10%;"/>
					<col style="width: 30%;"/>
					<col />
					<thead>
						<tr>
							<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='apprMessage.result' /></th>
							<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='apprMessage.approver' /></th>
							<th class="textCenter" scope="col"><ikep4j:message pre='${preView}' key='apprMessage.message' /></th>								
						</tr>
					</thead>
					<tbody>

						<!-- 기안자의 결재요청 메시지 -->
						<c:if test="${!receiveEnd}">
						<tr>
						<td class="textCenter">
						<c:if test="${empty apprDoc.parentApprId}">
						<ikep4j:message pre='${preView}' key='apprMessage.register' />
						</c:if>
						<c:if test="${!empty apprDoc.parentApprId}">
						<ikep4j:message pre='${preView}' key='apprMessage.receiver' />
						</c:if>			
						</td>
						<td class="textCenter">${apprDoc.registerName} ${apprDoc.registerJobTitle} ${apprDoc.apprGroupName}</td>
						<td class="textLeft">
							<% pageContext.setAttribute("newLineChar", "\n"); %> 
							${fn:replace(apprDoc.registerMessage, newLineChar, '<br/>')}								
						</td>
						</tr>
						</c:if>	
												
						<c:if test="${!empty apprLineList}">
						<c:forEach var="item" items="${apprLineList}">
						<c:if test="${item.apprType!=3 && !empty item.apprMessage}">
						<tr>
						<td class="textCenter">
								<c:if test="${receiveEnd}">
								<ikep4j:message pre='${preView}' key='apprMessage.receiveEnd' />
								</c:if>
								
								<c:if test="${!receiveEnd}">
								<c:forEach var="code" items="${commonCode.apprStatusList}"> 
									<c:if test="${code.key eq item.apprStatus}"><ikep4j:message key='${code.value}'/></c:if>
								</c:forEach>
								</c:if>	
						</td>							
						</td>
						<td class="textCenter">
						
						
						<c:if test="${item.approverType==0}">
							${item.approverName} ${item.approverJobTitle} ${item.approverGroupName} 
							<c:if test="${!empty item.entrustUserId}">
								<br />
								<ikep4j:message pre='${preView}' key='entrust' /> ${item.entrustUserName} ${item.entrustJobTitle} ${item.entrustGroupName}
							</c:if>
						</c:if>
												
						<c:if test="${item.approverType==1 && item.apprLineList ==null}">
							${item.approverName} ${item.approverJobTitle}
						</c:if>
	
						<c:if test="${item.approverType==1  && item.apprLineList !=null}">
						<c:forEach var="itemSub" items="${item.apprLineList}" varStatus='varStatus'>	
							<c:if test="${itemSub.apprDate !=''}">
								${itemSub.approverGroupName} ( ${itemSub.approverName} ${itemSub.approverJobTitle} )
								<c:if test="${!empty itemSub.entrustUserId}">
									<br />
									<ikep4j:message pre='${preView}' key='entrust' /> ${itemSub.approverGroupName} ( ${itemSub.entrustUserName} ${itemSub.entrustJobTitle} )
								</c:if>
							</c:if>
						</c:forEach> 
						</c:if>			

						</td>
						<td class="textLeft">
							<% pageContext.setAttribute("newLineChar", "\n"); %> 
							${fn:replace(item.apprMessage, newLineChar, '<br/>')}								
						</td>
						</tr>
						</c:if>	
						</c:forEach>
						</c:if>	
		
							
					</tbody>
				</table>
			</div>
		</div>
	</div>		
</div>		
<!--//tab End-->
<div class="clear"></div>

<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a id="cancelButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='close'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->		
