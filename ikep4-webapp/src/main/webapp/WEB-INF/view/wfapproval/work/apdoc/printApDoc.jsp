<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.wfapproval.work.apdoc.create.header" /> 
<c:set var="preButton"  value="ui.wfapproval.work.apdoc.create.button" />
<c:set var="preMessage" value="ui.wfapproval.common.message" />
<c:set var="preSearch"  value="ui.wfapproval.common.searchCondition" />
 <%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>

<script type="text/javascript">
(function($) {
	setAddress = function(data) {

		var addStr="";

		$jq.each(data, function() {
			addStr = addStr + "\""+$jq.trim(this.name)+"\" "+$jq.trim(this.email)+",";
		})

		$jq("#addrDiv").html(addStr.substring(0,addStr.length-1));

	};

	

	
	
	$(document).ready(function() {		
		$("#apprDocData").ckeditor($.extend(fullCkeditorConfig, {"popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />",
		    width: '100%', 
		    height: 350,
		    toolbar: 
			    [ 
				  ['Preview'],
			      ['Print']			      
			    ]
		    //,
			//toolbarStartupExpanded: false,
			//toolbarCanCollapse: false,
			//contentEditable: false
			//disabled: true		  
 		}));  
	 	
		var editorCKE = CKEDITOR.instances.apprDocData;
		//alert("1");
		//editorCKE.disabled= true;
		editorCKE.on('key', function(keyEvent) {
			//alert("HERE");			
			//alert(keyEvent.getKey() );
			//var domEvent = keyEvent.data;
			//alert(domEvent.getKey());
			keyEvent.cancel();
		});
		
	});
})(jQuery);

</script>

			
<h1 class="none"><ikep4j:message pre="${preHeader}" key="title"/></h1>

<!--pageTitle Start-->
  
<div id="pageTitle" >
	<h2>품위서 조회</h2>
	
</div>
<!--//pageTitle End-->
<form:form name="apDoc" modelAttribute="apDoc" method="post">

<textarea 	id="apprDocData" name="apprDocData" cols="" rows="5" class="inputbox w100 fullEditor" title="인쇄 내용" >
<link rel="stylesheet" type="text/css" href="/ikep4-webapp/base/css/theme02/jquery_ui_custom.css" />
<link rel="stylesheet" type="text/css" href="/ikep4-webapp/base/css/common.css" />
<link rel="stylesheet" type="text/css" href="/ikep4-webapp/base/css/theme02/theme.css" />
<div class="blockLeft" >
				<div class="subTitle_2 noline">
					<h3>기안/승인</h3>
				</div>			
				<!--blockDetail Start-->					
				<div class="blockDetail">
					<table summary="기안/결재">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="15%" class="textCenter">구분</th>
								<th scope="col" width="15%" class="textCenter">이름</th>
								<th scope="col" width="30%" class="textCenter">부서/직위</th>
								<th scope="col" width="30%" class="textCenter">일자</th>
							</tr>
						</thead>
						<tbody id="test">
							<!--  <tr>
								<td class="textCenter">기안</td>
								<td class="textCenter">${user.userName}</td>
								<td class="textCenter">${user.teamName}/${user.jobPositionName}</td>
								<td class="textCenter">${today}</td>
							</tr>-->
			<c:forEach var="apProcess" items="${apProcess}" varStatus="apDocItem">
						<c:choose>
						<c:when test="${apProcess.apprOrder eq 0 }">
							<tr>
								<td class="textCenter">기안</td>							
								<td class="textCenter">${apProcess.userName}</td>
								<td class="textCenter">${apProcess.teamName}/${apProcess.jobPositionName}</td>
								<td class="textCenter">${apProcess.apprMessage}</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:if test="${apProcess.apprType eq 0 }">
							<tr>
								<td class="textCenter">결재</td>							
								<td class="textCenter">${apProcess.userName}</td>
								<td class="textCenter">${apProcess.teamName}/${apProcess.jobPositionName}</td>
								<td class="textCenter"></td>
							</tr>
							</c:if>
						</c:otherwise> 
			</c:choose>
			</c:forEach>
							
							
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
			</div><!--//blockLeft End-->
<div class="blockRight" >
				<div class="subTitle_2 noline">
					<h3>합의</h3>
				</div>			
				<!--blockDetail Start-->					
				<div class="blockDetail">
					<table summary="기안/결재">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="15%" class="textCenter">구분</th>
								<th scope="col" width="15%" class="textCenter">이름</th>
								<th scope="col" width="30%" class="textCenter">부서/직위</th>
								<th scope="col" width="30%" class="textCenter">일자</th>
							</tr>
						</thead>
						<tbody>
							<!--  <tr>
								<td class="textCenter">&nbsp;</td>
								<td class="textCenter">&nbsp;</td>
								<td class="textCenter">&nbsp;</td>
								<td class="textCenter">&nbsp;</td>
							</tr>-->
<c:forEach var="apProcess" items="${apProcess}" varStatus="apDocItem">
							
							<c:if test="${apProcess.apprType eq 1 }">
							<tr>
								<td class="textCenter">합의</td>							
								<td class="textCenter">${apProcess.userName}</td>
								<td class="textCenter">${apProcess.teamName}/${apProcess.jobPositionName}</td>
								<td class="textCenter">${apProcess.apprMessage}</td>
							</tr>
							</c:if>
							
			</c:forEach>							
							
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
			</div>	
			<!--//block Rigth End-->
			  <div class="clear"></div>
		<div class="blockDetail" >
					<table summary="상세정보 작성하기" width=700 >
						<caption></caption>
						<tbody>
							<tr>
								<th width="18%" scope="row">보안등급<!--: ${apFormTpl.apprDocCd},${apFormTpl.isApprDoc}--></th>
								<td width="32%">
									
									<!--${apDoc.apprSecurityCd}-->
									
									<c:forEach var="apCode" items="${listApprDocCd}">
										<c:if test="${apCode.codeId eq apDoc.apprSecurityCd}">
										<label>${apCode.codeName}
											<!--  <input type="radio" class="radio" title="${apCode.codeName}" name="apprSecurityCd"
													value="${apCode.codeId}" <c:if test="${apCode.codeId eq apDoc.apprSecurityCd}">checked</c:if>/>${apCode.codeName}-->
										</label>&nbsp;&nbsp;
										</c:if>
									</c:forEach>
									
									
									<c:if test="${apFormTpl.isApprDoc eq 'U'}">
									<c:forEach var="apCode" items="${listApprDocCd}">
										<label>
											<input type="radio" class="radio" title="${apCode.codeName}" name="apprSecurityCd"
													value="${apCode.codeId}" <c:if test="${apCode.codeId eq apFormTpl.apprDocCd}">checked</c:if>/>${apCode.codeName}
										</label>&nbsp;&nbsp;
									</c:forEach>										
									</c:if>
									<c:if test="${apFormTpl.isApprDoc eq 'A'}">
									<c:forEach var="apCode" items="${listApprDocCd}">
										<c:if test="${apCode.codeId eq apFormTpl.apprDocCd}">
									<label>
											<input type="radio" class="radio" title="${apCode.codeName}" name="apprSecurityCd"
													value="${apCode.codeId}" checked/>${apCode.codeName}
										</label>&nbsp;&nbsp;
										</c:if>
									</c:forEach>
									</c:if>
									
								</td>
								<th width="18%" scope="row">분류번호</th>
								<td width="32%">
									<input 	type="text" class="inputbox" title="분류번호" 
											name="apprDocNo" value="${apDoc.apprDocNo}" size="30" />

								</td>
							</tr>
							<tr>
								<th scope="row">기안부서</th>
								<td>
			<c:forEach var="apProcess" items="${apProcess}" varStatus="apDocItem">
						<c:choose><c:when test="${apProcess.apprOrder eq 0 }">
								${apProcess.teamName}
						</c:when></c:choose>
			</c:forEach><!--  ${user.teamName}-->
								</td>
								<th scope="row">기안자</th>
								<td>
									${apDoc.registUserId}
								</td>
							</tr>
							<tr>
								<th scope="row">기안일자</th>
								<td>
									<spring:eval expression="apDoc.apprReqDate"/>
								</td>
								<th scope="row">보존연한 <!--: ${apFormTpl.apprPeriodCd},${apFormTpl.isApprPeriod}--></th>
								<td>
								<!--${apDoc.apprPeriodCd}-->
									<c:forEach var="apCode" items="${listApprPeriodCd}">
										
										<c:if test="${apCode.codeId eq apDoc.apprPeriodCd}">
										<label>${apCode.codeName}
											<!--  <input type="radio" class="radio" title="${apCode.codeName}" name="apprPeriodCd"
													value="${apCode.codeId}" <c:if test="${apCode.codeId eq apDoc.apprPeriodCd}">checked</c:if>/>${apCode.codeName}-->
										</label>&nbsp;&nbsp;
										</c:if>
									</c:forEach>
									<c:if test="${apFormTpl.isApprPeriod eq 'U'}">
									<c:forEach var="apCode" items="${listApprPeriodCd}">
										<label>
											<input type="radio" class="radio" title="${apCode.codeName}" name="apprPeriodCd"
													value="${apCode.codeId}" <c:if test="${apCode.codeId eq apFormTpl.apprDocCd}">checked</c:if>/>${apCode.codeName}
										</label>&nbsp;&nbsp;
									</c:forEach>										
									</c:if>
									<c:if test="${apFormTpl.isApprPeriod eq 'A'}">
									<c:forEach var="apCode" items="${listApprPeriodCd}">
										<c:if test="${apCode.codeId eq apFormTpl.apprPeriodCd}">
									<label>
											<input type="radio" class="radio" title="${apCode.codeName}" name="apprPeriodCd"
													value="${apCode.codeId}" checked/>${apCode.codeName}
										</label>&nbsp;&nbsp;
										</c:if>
									</c:forEach>
									</c:if>
								</td>
							</tr>
							<tr>
								<th scope="row">문서종류<!--   : ${apFormTpl.apprTypeCd},${apFormTpl.isApprType}--></th>
								<td colspan=3>
								<!--${apDoc.apprTypeCd}-->
								
									<c:forEach var="apCode" items="${listFormTypeCd}">
										
										<c:if test="${apCode.codeId eq apDoc.apprTypeCd}">
										<label>${apCode.codeName}
											<!--  <input type="radio" class="radio" title="${apCode.codeName}" name="apprTypeCd"
													value="${apCode.codeId}" <c:if test="${apCode.codeId eq apDoc.apprTypeCd}">checked</c:if>/>${apCode.codeName}-->
										</label>&nbsp;&nbsp;
										</c:if>
										
									</c:forEach>
								
									<c:if test="${apFormTpl.isApprType eq 'U'}">
									
									<c:forEach var="apCode" items="${listFormTypeCd}">
										<label>
											<input type="radio" class="radio" title="${apCode.codeName}" name="apprTypeCd"
													value="${apCode.codeId}" <c:if test="${apCode.codeId eq apFormTpl.apprTypeCd}">checked</c:if>/>${apCode.codeName}
										</label>&nbsp;&nbsp;
									</c:forEach>										
									</c:if>
									<c:if test="${apFormTpl.isApprType eq 'A'}">
									<c:forEach var="apCode" items="${listFormTypeCd}">
										<c:if test="${apCode.codeId eq apFormTpl.apprTypeCd}">
									<label>
											<input type="radio" class="radio" title="${apCode.codeName}" name="apprTypeCd"
													value="${apCode.codeId}" checked/>${apCode.codeName}
										</label>&nbsp;&nbsp;
										</c:if>
									</c:forEach>
									</c:if>
								</td>
							</tr>
							<tr>
								<th scope="row">수신(참조)</th>
								<td >
									<select id="workers" name="addrGroupList" multiple="multiple" size="5" style="width:300px;height:50px;"></select>
								</td>
								<th scope="row">열람권한지정</th>
								<td >
									<select id="workers1" name="addrGroupList1" multiple="multiple" size="5" style="width:300px;height:50px;"></select>
								</td>
							</tr>
							<tr>
								<th scope="row">제목 <!--  : ${apFormTpl.isApprTitle}--></th>
								<td colspan=3>
									<input 	type="text" class="inputbox" title="제목" 
											name="apprTitle" value="${apDoc.apprTitle}" size="100" <c:if test="${apFormTpl.isApprTitle eq 'Y'}">readonly</c:if> onkeyDown="if(event.keyCode == 13 || event.keyCode == 8){event.returnValue=false;}" />
									<!--  onKeydown     backspace,enter 않되게 적용 -->
								</td>
							</tr>
						</tbody>
					</table>
					
				</div>
				
				
${apDoc.apprDocData}			 
             
</form:form>

</textarea>
		
		
		
		
</body>
</html>