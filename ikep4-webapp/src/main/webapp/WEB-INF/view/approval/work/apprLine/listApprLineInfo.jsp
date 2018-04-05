<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preView"	value="ui.approval.work.apprLine.listApprLineView.view"/>
<%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript">
<!-- 
(function($) {	
 			
	apprDocStatus = function(apprId){			
		dialog = new iKEP.showDialog({     
			title 		: "<ikep4j:message pre='${preView}' key='progress' />",
			url 		: "<c:url value='/approval/work/apprLine/listApprLineForm.do'/>?apprId="+apprId,
			modal 		: true,
			width 		: 800,
			height 		: 430,
			params 		: {apprId:apprId},
			callback : function(result) {					
			}
		});
	};

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
					<table summary="<ikep4j:message pre='${preView}' key='line' />">
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
													
							<c:if test="${item.approverType==1 && item.apprLineList==null}">
							${item.approverGroupName}
							</c:if>		
												
							<c:if test="${item.approverType==1 && item.apprLineList!=null}">
							<c:forEach var="itemSub" items="${item.apprLineList}" varStatus='varStatus'>
							<c:if test="${itemSub.apprDate !=''}">
							<c:if test="${apprConfig.isMessageOpen=='0'}">
							${itemSub.approverGroupName} ( ${itemSub.approverName} ${itemSub.approverJobTitle} )
							</c:if>
							<c:if test="${apprConfig.isMessageOpen=='1'}">
							<a href="#a" onclick="apprDocStatus('${itemSub.apprId}');">${itemSub.approverGroupName} ( ${itemSub.approverName} ${itemSub.approverJobTitle} )</a>
							</c:if>							
								<c:if test="${!empty itemSub.entrustUserId}">
									<br />
									<ikep4j:message pre='${preView}' key='entrust' /> ${itemSub.approverGroupName} ( ${itemSub.entrustUserName} ${itemSub.entrustJobTitle} )
								</c:if>						
							</c:if>
							</c:forEach> 
							</c:if>	
													
							</td>
							
							<td class="textCenter">							
							<c:forEach var="code" items="${commonCode.apprTypeList}">
								<c:if test="${code.key eq item.apprType}"><ikep4j:message key='${code.value}'/></c:if>
							</c:forEach>
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
				<table summary="<ikep4j:message pre='${preView}' key='receive' />">
					<caption></caption>
					<col style="width: 20%;"/>
					<col style="width: 12%;"/>
					<col style="width: 10%;"/>
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
					
					<%--tr>
					<td class="textCenter" rowspan="${subList.lineCount+2 }">
					<c:if test="${!empty subList.approverId}">
					${subList.approverName} ${subList.approverJobTitle} ( ${subList.apprGroupName} )
					</c:if>	
					<c:if test="${empty subList.approverId}">
					${subList.apprGroupName}
					</c:if>						
					</td></tr--%>
					
					<!-- 접수자 정보 -->
					<c:if test="${subList.lineCount>1 && subList.apprDocStatus!='6'}">
					<tr>
					<td class="textCenter" rowspan="${subList.lineCount+1 }">
					<c:if test="${!empty subList.approverId}">
					${subList.approverName} ${subList.approverJobTitle} ( ${subList.apprGroupName} )
					</c:if>	
					<c:if test="${empty subList.approverId}">
					${subList.apprGroupName}
					</c:if>						
					</td>				
					<td class="textCenter">${subList.registerName} ${subList.registerJobTitle}</td>				
					<td class="textCenter"><ikep4j:message pre='${preView}' key='receive.receive' /></td>
					<td class="textCenter"><ikep4j:timezone date="${subList.registDate}" pattern="yyyy.MM.dd HH:mm:ss"/></td>
					<td class="textLeft">${subList.registerMessage }</td>
					</tr>
					</c:if>	
					
					<!-- 접수자 정보 -->
					<c:if test="${subList.lineCount>1 && subList.apprDocStatus=='6'}">
					<tr>
					<td class="textCenter" rowspan="${subList.lineCount+1 }">
					<c:if test="${!empty subList.approverId}">
					${subList.approverName} ${subList.approverJobTitle} ( ${subList.apprGroupName} )
					</c:if>	
					<c:if test="${empty subList.approverId}">
					${subList.apprGroupName}
					</c:if>						
					</td>				
					<td class="textCenter"></td>				
					<td class="textCenter"><ikep4j:message pre='${preView}' key='receive.noneReceive' /></td>
					<td class="textCenter"></td>
					<td class="textCenter"></td>
					</tr>
					</c:if>	
										
					<c:if test="${subList.lineCount==0}">
					<tr>		
					<td class="textCenter" rowspan="${subList.lineCount+1 }">
					<c:if test="${!empty subList.approverId}">
					${subList.approverName} ${subList.approverJobTitle} ( ${subList.apprGroupName} )
					</c:if>	
					<c:if test="${empty subList.approverId}">
					${subList.apprGroupName}
					</c:if>						
					</td>			
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
						<c:if test="${subList.lineCount!=1 && subList.registerId!=item.approverId}">
						<tr>	
						</c:if>
						<c:if test="${subList.lineCount==1 && subList.registerId==item.approverId}">
						<td class="textCenter" rowspan="${subList.lineCount }">${subList.apprGroupName}</td>	
						</c:if>											
					<td class="textCenter">
						${item.approverName} ${item.approverJobTitle}
						<c:if test="${!empty item.entrustUserId}">
						<br />
						<ikep4j:message pre='${preView}' key='entrust' /> ${item.entrustUserName} ${item.entrustJobTitle}
						</c:if>							
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
			</div>
			</c:if>
			
			
			<div id="tabs-3">
				<!--blockListTable Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${preView}' key='apprMessage' />">
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
							<td class="textCenter">
							
							<c:if test="${item.approverType==0}">
								${item.approverName} ${item.approverJobTitle} ${item.approverGroupName} 
								<c:if test="${!empty item.entrustUserId}">
								<br />
								<ikep4j:message pre='${preView}' key='entrust' /> ${item.entrustUserName} ${item.entrustJobTitle} ${item.entrustGroupName}
								</c:if>
							</c:if>

							
							
							<c:if test="${item.approverType==1 && item.apprLineList==null}">
							${item.approverGroupName} 
							</c:if>

							<c:if test="${item.approverType==1  && item.apprLineList !=null}">
							<c:forEach var="itemSub" items="${item.apprLineList}" varStatus='varStatus'>
								<c:if test="${itemSub.apprDate !=''}">
									<c:if test="${apprConfig.isMessageOpen=='0'}">
									${itemSub.approverGroupName} ( ${itemSub.approverName} ${itemSub.approverJobTitle} )
									</c:if>
									<c:if test="${apprConfig.isMessageOpen=='1'}">
									<a href="#a" onclick="apprDocStatus('${itemSub.apprId}');">${item.approverGroupName} ( ${itemSub.approverName} ${itemSub.approverJobTitle} )</a>
									</c:if>									
								
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
			
