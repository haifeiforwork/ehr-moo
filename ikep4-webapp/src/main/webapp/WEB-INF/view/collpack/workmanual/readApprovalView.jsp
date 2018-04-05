<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.collpack.workmanual.readApprovalView</c:set>
<c:set var="messagePrefix">ui.collpack.workmanual.message</c:set>
<c:set var="buttonPrefix">ui.collpack.workmanual.button</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
//<![CDATA[
(function($) { 
	$jq(document).ready(function(){
		loadApprovalLineList();
		
		//승인 버튼 클릭
		$jq("#acceptButton").click(function() {
			$jq("input[name='approvalResult']").attr("value", "C");
			$jq("#form").submit();	
		});   
		//반려 버튼 클릭
		$jq("#rejectButton").click(function() {
			$jq("input[name='approvalResult']").attr("value", "D");
			$jq("#form").submit();	
		});
		
		//파일 다운로드
		var uploaderOptions = {
		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 
	    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions); 
	});
	
	//탭 선택
	$jq(function() {
		$jq("#divTab").tabs({
			selected : 0,
		});
	});	
	
	//결재자 조회
	loadApprovalLineList = function() {
		$("#approvalLineDiv").load('<c:url value="/collpack/workmanual/addApprovalLinePage.do?approvalId=${approval.approvalId}"/>', $("#Form").serialize())
		.success(function(data) {
			iKEP.iFrameContentResize();
			})
		.error(function(event, request, settings) { alert("error"); 
		});
		
	};
	
})(jQuery);
//]]>
</script>

				<h1 class="none"></h1>
				
				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre="${prefix}" key="main.title"/></h2>
					<!--  
					<div id="pageLocation">
						<ul>
							<li class="liFirst"><ikep4j:message pre="${prefix}" key="pageLocation.1depth"/></li>
							<li><ikep4j:message pre="${prefix}" key="pageLocation.2depth"/></li>
							<li><ikep4j:message pre="${prefix}" key="pageLocation.3depth"/></li>
							<li class="liLast"><ikep4j:message pre="${prefix}" key="pageLocation.4depth"/></li>
						</ul>
					</div>
					-->
				</div>
				<!--//pageTitle End-->
				
				
				<!--tab Start-->		
				<div id="divTab" class="iKEP_tab">
					<ul>
						<li><a href="#tabs-1"><ikep4j:message pre='${prefix}' key='tab1.title'/></a></li>
						<li><a href="#tabs-2"><ikep4j:message pre='${prefix}' key='tab2.title'/></a></li>
					</ul>
					
					<div>
						<!--TAB 1 Start-->
						<div id="tabs-1">
							<!--blockDetail Start-->
							<div class="blockDetail">
								<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
									<caption></caption>
									<tbody>
										<tr>
											<th scope="row" width="18%"><ikep4j:message pre="${prefix}" key="tab1.table1.column1"/></th>
											<td width="32%"><span>${manualVersion.versionTitle}</span></td>
											<th scope="row" width="18%"><ikep4j:message pre="${prefix}" key="tab1.table1.column2"/></th>
											<td width="32%"><span><c:if test="${approval.manualType == 'A'}"><ikep4j:message pre='${messagePrefix}' key='manual.submit'/></c:if>
																	<c:if test="${approval.manualType == 'B'}"><ikep4j:message pre='${messagePrefix}' key='manual.submit.revision'/></c:if>
																	<c:if test="${approval.manualType == 'C'}"><ikep4j:message pre='${messagePrefix}' key='manual.disuse'/></c:if></span>
											</td>
										</tr>
										<tr>
											<th scope="row" width="18%"><ikep4j:message pre="${prefix}" key="tab1.table1.column3"/></th>
											<td width="32%">
												<span><c:choose>
													<c:when test="${user.localeCode == portal.defaultLocaleCode}">${approval.registerName}</c:when>
													<c:otherwise>${approval.registerEnglishName}</c:otherwise>
												</c:choose></span>
											</td>
											<th scope="row" width="18%"><ikep4j:message pre="${prefix}" key="tab1.table1.column4"/></th>
											<td width="32%"><span><ikep4j:timezone date="${approval.registDate}" pattern="yyyy.MM.dd HH:mm"/></span></td>
										</tr>
										<tr>
											<th scope="row"><ikep4j:message pre="${prefix}" key="tab1.table1.column5"/></th>
											<td colspan="3"><textarea class="w100" readonly="true" title="<ikep4j:message pre="${prefix}" key="tab1.table1.column5"/>" cols="" rows="5">${approval.requestContents}</textarea></td>
										</tr>
									</tbody>
								</table>
							</div>
							<!--//blockDetail End-->
			 				<div class="blockBlank_10px"></div>
			 				
							<!--subTitle_2 Start-->
							<div class="subTitle_2 noline">
								<h3><ikep4j:message pre="${prefix}" key="tab1.table2.title"/></h3>
							</div>
							<!--//subTitle_2 End-->
					
							<!--blockDetail Start-->
							<div class="blockDetail" id="approvalLineDiv">
							</div>
							<!--//blockDetail End-->
									
							<c:forEach var="item" items="${approvalLineList}">
								<c:if test="${item.approvalUserId == user.userId && item.approvalResult=='B'}">
									<!--blockDetail Start-->
									<div class="blockDetail">
										<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
											<caption></caption>
											<tbody>
												<tr>
													<th scope="row" colspan="2" width="18%"><ikep4j:message pre="${prefix}" key="tab1.table3.column1"/></th>
													<td colspan="3" width="82%">
														<form id="form" action="<c:url value='/collpack/workmanual/updateApprovalLine.do'/>" method="post">
															<input type="hidden" name="approvalId" value="${item.approvalId}"/>
															<input type="hidden" name="approvalLine" value="${item.approvalLine}"/>
															<input type="hidden" name="approvalUserId" value="${item.approvalUserId}"/>
															<input type="hidden" name="approvalResult" value=""/>	
															<textarea name="approvalComment" class="w100" title="<ikep4j:message pre="${prefix}" key="tab1.table3.column1"/>" cols="" rows="5"></textarea>
														</form>
													</td>
												</tr>
											</tbody>
										</table>
									</div>
									<!--//blockDetail End-->
									
									<!--blockButton Start-->
									<div class="blockButton"> 
										<ul>
											<li><a class="button" href="#a" id="acceptButton"><span><ikep4j:message pre='${buttonPrefix}' key='accept'/></span></a></li>
											<li><a class="button" href="#a" id="rejectButton"><span><ikep4j:message pre='${buttonPrefix}' key='reject'/></span></a></li>
											<li><a class="button" href="<c:url value='/collpack/workmanual/listApprovalView.do'/>"><span><ikep4j:message pre='${buttonPrefix}' key='list'/></span></a></li>
										</ul>
									</div>
									<!--//blockButton End-->
								</c:if>
							</c:forEach>
							<c:if test="${approvalResult != 'B'}">
									<!--blockButton Start-->
									<div class="blockButton"> 
										<ul>
											<li><a class="button" href="<c:url value='/collpack/workmanual/listApprovalView.do'/>"><span><ikep4j:message pre='${buttonPrefix}' key='list'/></span></a></li>
										</ul>
									</div>
									<!--//blockButton End-->
							</c:if>
						</div>
						<!--TAB 1 End-->
						
						<!--TAB 2 Start-->
						<div id="tabs-2">
							<!--blockListTable Start-->
							<div class="blockTableRead">
								<div class="blockTableRead_t">
									<p>${manualVersion.versionTitle}(<ikep4j:message pre="${prefix}" key="tab2.version"/>:${manualVersion.version})</p>
									<div class="summaryViewInfo">
										<span class="summaryViewInfo_name">
											<c:choose>
												<c:when test="${user.localeCode == portal.defaultLocaleCode}">${manualVersion.registerName}&nbsp;${registerUser.jobTitleName}</c:when>
												<c:otherwise>${manualVersion.registerEnglishName}&nbsp;${registerUser.jobTitleEnglishName}</c:otherwise>
											</c:choose>
										</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span><ikep4j:timezone date="${manualVersion.registDate}" pattern="yyyy.MM.dd HH:mm"/></span>
									</div>
									<c:if test="${fn:length(manualVersion.fileDataList) > 0}"><div id="fileDownload"></div></c:if>
								</div>
								
								<div class="blockTableRead_c">
									<p>${manualVersion.versionContents}</p>
									<div class="tableTag"><img src="<c:url value='/base/images/theme/theme01/basic/ic_tag.gif'/>" alt="tag"/> 
										${manualVersion.tag}
									</div>						
								</div>
								
							</div>
							<!--//blockListTable End-->
						</div>
						<!--TAB 2 End-->
					</div>			
				</div>		
				<!--//tab End-->