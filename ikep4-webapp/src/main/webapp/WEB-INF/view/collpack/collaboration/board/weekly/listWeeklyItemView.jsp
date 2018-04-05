<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>  
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.board.weekly.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.collaboration.board.weekly.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.collaboration.board.weekly.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.collaboration.board.weekly.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.collaboration.board.weekly.listBoardView.alert" />
<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript">
var layerFlag = false;
(function($){	
	$(document).ready(function() {
		
		$("#datetext").datepicker({
			showOn: "button",
			buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			buttonImageOnly: false,
			showOtherMonths : true,
			selectOtherMonths: true,
			hoverWeek : true,
			onSelect: function(date) {
				$("#weeklyListForm input[name=weeklyTerm]").val(date);
				$("#weeklyListForm").submit();
			}
		});
		
		$jq("#urgeMessage, #urgeMessage1").click(function(){
			var chkMethod = $("#urgeForm input[name=urgeMethod]:radio:checked").val();
			chkMethod = "EMail";
			if(chkMethod!=null){
				if(chkMethod=="SMS"){
					sendSMS();
				}else if(chkMethod=="EMail"){
					if(confirm('<ikep4j:message pre='${preMessage}' key='confirmMAIL'/>')) {
						sendMail();
					} else {
						return false;
					}
				}
			}else{
				alert("<ikep4j:message pre='${preMessage}' key='emptySelectedMethod'/>.");
				return;
			}
		});
		
		$jq("#checkAllUrgeIds").click(function() { 
			$("input[name=urgeIds]").attr("checked", $(this).is(":checked"));  
		});
		
		
		$jq("#summaryWeeklyItemButton").click(function() {
			
			  if(${searchCondition.summaryCount}==1){
				  if(!confirm("<ikep4j:message pre="${preMessage}" key="checkConfirm1"/>\n<ikep4j:message pre="${preMessage}" key="checkConfirm2"/>")){
					  return;
				  }
			  }
			  createSummaryItem();
			  
		});
		iKEP.iFrameContentResize();
		
	}); //onLoad끝 
	
	createSummaryItem = function() { 
		$("#weeklyListForm input[name=isSummary]").val("1");
		$("#weeklyListForm").attr({
			action:"<c:url value='/collpack/collaboration/board/weekly/createWeeklyItemView.do'/>",
			method:'POST'
		}).submit();
		return false;
	};
	
	$jq("#urgeButton").click(function(){
		changeLayer();
	});
	
	
	
	resetAllCheck = function() { 
		$("input[name=urgeIds]").attr("checked",false);
		$("input[name=checkAllUrgeIds]").attr("checked",false); 
	};
	
	changeLayer = function(){
		if(layerFlag){
			$("#process_layer").attr("style","display:none");
			$("input[name=urgeMethod]").attr("checked",false);
			layerFlag = false;
		}else{
			$("#process_layer").attr("style","display:yes");
			layerFlag = true;
		}
	};
	
	sendSMS = function(){
		
		var countChkIds = $("#urgeForm input[name=urgeIds]:checkbox:checked").length;
		
		if(countChkIds>0)
		{
			$jq.ajax({    
				url : "<c:url value='/collpack/collaboration/board/weekly/createSms.do'/>?"+$jq("#urgeForm").serialize(),
				type : "get",     
				success : function(result) {        			
					alert("<ikep4j:message pre='${preMessage}' key='submitSMS'/>.");
					resetAllCheck();
				},
				error : function(e){
					iKEP.debug("error");
				}
			});
		}else{
			alert("<ikep4j:message pre='${preMessage}' key='emptySelected'/>.");
		}
		//changeLayer();
	}
	
	sendMail = function(){
		var countChkIds = $("#urgeForm input[name=urgeIds]:checkbox:checked").length;
		if(countChkIds>0)
		{
			$jq.ajax({    
				url : "<c:url value='/collpack/collaboration/board/weekly/createMail.do'/>?"+$jq("#urgeForm").serialize(),
				type : "get",     
				success : function(result) {
					if(result=="success"){
						alert("<ikep4j:message pre='${preMessage}' key='submitMAIL'/>.");
						resetAllCheck();	
					}else{
						iKEP.debug("error");
					}
				},
				error : function(e){
					iKEP.debug("error");
				}
			});
		}else{
			alert("<ikep4j:message pre='${preMessage}' key='emptySelected'/>.");
		}
		//changeLayer();
	}
	
	
})(jQuery);
</script>	<!--pageTitle Start-->
				<!--div id="pageTitle">
					<h2><ikep4j:message pre="${preHeader}" key="pageTitle"/></h2>
				</div-->
				<!--//pageTitle End-->
	<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle"/></h2>
	</div>
	</div>				
				<form name="weeklyListForm" id="weeklyListForm">
				
				<spring:bind path="searchCondition.sortColumn">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
					</spring:bind> 		
				<spring:bind path="searchCondition.sortType">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind> 
				<spring:bind path="searchCondition.workspaceId">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<spring:bind path="searchCondition.weeklyTerm">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<input name="isSummary" type="hidden" value="" title="<ikep4j:message pre='${preSearch}' key='isSummary'/>" />
				
				
				<div class="fc" id="calendar">
					<table class="fc-header"  style="width:100%;" summary="테이블상단">
						<caption></caption>
						<tbody>
							<tr>
								<td style="width:160px;"></td>
								<td class="fc-header-center">
									<div id="datepicker"></div>
									<span class="fc-button-prev"><a href="<c:url value='/collpack/collaboration/board/weekly/searchTermWeeklyItemView.do'/>?workspaceId=${workspaceId}&amp;weeklyTerm=${searchCondition.weeklyTerm}&amp;dayFlag=prev"><span class="none">이전</span></a></span>
									<div class="fc-header-title">
									<h2>${searchCondition.weeklyTerm}</h2>
									</div>
									<span class="fc-button-next"><a href="<c:url value='/collpack/collaboration/board/weekly/searchTermWeeklyItemView.do'/>?workspaceId=${workspaceId}&amp;weeklyTerm=${searchCondition.weeklyTerm}&amp;dayFlag=next"><span class="none">다음</span></a></span>&nbsp;
									<span style="position:absolute;">
											<span>
												<input type="hidden" id="datetext"/>
											</span>
									</span>
								</td>
								<td class="fc-header-right"></td>
							</tr>
						</tbody>
					</table>
				</div>
				
				<div class="blockLeft" style="width:76%;">
					<div class="blockListTable calTable">
						<table summary="주간보고">
							<caption></caption>
							<thead>
								<tr>
									<th scope="col" width="25%" class="borderLeft"></th>
									<th scope="col" width="*"><span><ikep4j:message pre="${preList}" key="weeklyStatus"/></span></th>
									<th scope="col" width="16%" class="borderRight"></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="weeklyItem" items="${searchResult.entity}" varStatus="idx">
								<tr <c:if test="${empty weeklyItem.title}">class="bgGray"</c:if>>
									<td class="borderLeft <c:if test='${idx.last}'>tdLast</c:if>">
										<c:choose>
											<c:when test="${user.localeCode == portal.defaultLocaleCode}">
												${weeklyItem.userName}&nbsp;${weeklyItem.jobTitleName}
											</c:when>
											<c:otherwise>
												${weeklyItem.userEnglishName}&nbsp;${weeklyItem.jobTitleEnglishName}
											</c:otherwise>
										</c:choose>	
									</td>
									<td class="textLeft <c:if test='${idx.last}'>tdLast</c:if>">
										<c:if test="${!empty weeklyItem.title}">
											<c:if test="${weeklyItem.isSummary eq 1}"> 
												<span class="cate_block_5"><span class="cate_tit_5"><ikep4j:message pre="${preList}" key="summary"/></span></span>&nbsp;
											</c:if>
											<c:choose>
												<c:when test="${weeklyPermission>0}">
													<a href="<c:url value='/collpack/collaboration/board/weekly/readWeeklyItemView.do?itemId=${weeklyItem.itemId}&amp;workspaceId=${workspaceId}'/>">${weeklyItem.title}</a>
												</c:when>
												<c:otherwise>
													${weeklyItem.title}
												</c:otherwise>
											</c:choose>
											<c:if test="${weeklyItem.isSummary eq 1}">
												</strong>
											</c:if>
										</c:if>
										<c:if test="${empty weeklyItem.title && weeklyItem.memberId==user.userId && weeklyPermission>0}">
							<a id="createWeeklyItemButton" class="button_s" href="<c:url value='/collpack/collaboration/board/weekly/createWeeklyItemView.do?workspaceId=${workspaceId}&amp;isSummary=0&amp;weeklyTerm=${searchCondition.weeklyTerm}'/>"><span><ikep4j:message pre='${preButton}' key='createWeeklyItem'/></span></a>
										</c:if>
									</td>
									<td class="borderRight <c:if test='${idx.last}'>tdLast</c:if>">
										<c:if test="${!empty weeklyItem.registDate}">
											<ikep4j:timezone pattern="yyyy.MM.dd" date="${weeklyItem.registDate}"/>
										</c:if>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<c:if test="${weeklyPermission>1}">
					<div class="blockButton"> 
						<a id="summaryWeeklyItemButton" class="button" href="#a">
							<span><ikep4j:message pre="${preList}" key="summary"/></span>
						</a>
					</div>
					</c:if>
				</div>
				</form>
				<form id="urgeForm">
				<spring:bind path="searchCondition.workspaceId">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<spring:bind path="searchCondition.weeklyTerm">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<div class="blockRight" style="width:22%;">
					<div class="calTable_2">		
						<table summary="">
							<caption></caption>
							<thead>
								<tr>
									<th scope="col" class="bgcolor_b borderRight" width="2%"><span class="colorPoint"><ikep4j:message pre="${preList}" key="imperfect"/></span></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="textLeft">
										<div class="allSelect">
											<c:if test="${weeklyPermission>1}">
												<input id="checkAllUrgeIds" name="checkAllUrgeIds" class="checkbox" title="checkbox" type="checkbox" value="" />
												<span><ikep4j:message pre="${preList}" key="selectAll"/></span>
											</c:if>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="noList">
											<ul>
												<c:set var="cntItem" value="1"/>
												<c:forEach var="urgeItem" items="${searchResult.entity}" varStatus="idx">
													<c:if test="${empty urgeItem.title && empty urgeItem.registDate}">
														<li>
															<c:if test="${weeklyPermission>1}">
																<input name="urgeIds" class="checkbox" title="checkbox" type="checkbox" value="${urgeItem.memberId}" />
															</c:if>
															${urgeItem.userName}
														</li>
														<c:set var="cntItem" value="2"/>
													</c:if>
												</c:forEach>
												<c:if test="${cntItem=='1'}">
														<li>
															<ikep4j:message pre="${preMessage}" key="empty"/>
														</li>
												</c:if>
											</ul>
											<div style="height:4px;"></div>
											<!--blockButton_2 Start-->
											<c:if test="${weeklyPermission>1}">
											<div class="blockButton_4"> 
												<a class="button_4" id="urgeMessage1" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_write.png'/>" alt="" /><ikep4j:message pre="${preList}" key="urge"/></span></a>				
											</div>
											<!--//blockButton_2 End-->
											<!--질문하기_layer는 공유 클릭시 생성-->
											<!--layer start-->
											<div id="process_layer" class="process_layer" style="top:435px; right:13px; z-index:99; width:200px; display:none;">
												<div class="process_layer_t">
													<ikep4j:message pre="${preList}" key="choiceUrge"/><a href="javascript:changeLayer();"><img src="<c:url value='/base/images/icon/ic_close_layer.gif'/>" alt="" /></a>
												</div>
												<div class="process_corporate4">
													<ul>
														<li><input type="radio" class="radio" title="SMS" name="urgeMethod" value="SMS"  /><ikep4j:message pre="${preList}" key="sms"/></li>
														<li><input type="radio" class="radio" title="E-Mail" name="urgeMethod" value="EMail"  /><ikep4j:message pre="${preList}" key="email"/></li>
														<li><a id="urgeMessage" class="button" href="#a"><span><ikep4j:message pre="${preList}" key="submit"/></span></a></li>
													</ul>
												</div>
											</div>
											</c:if>		
											<!--layer end-->
										</div>
									</td>
								</tr>
							</tbody>
						</table>	
					</div>
				</div>
				</form>