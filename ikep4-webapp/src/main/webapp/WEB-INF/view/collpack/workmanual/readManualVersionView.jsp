<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.collpack.workmanual.readManualVersionView</c:set>
<c:set var="messagePrefix">ui.collpack.workmanual.message</c:set>
<c:set var="buttonPrefix">ui.collpack.workmanual.button</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
//<![CDATA[
(function($) { 
	$jq(document).ready(function(){
		$jq("#layerApproval").hide();
		
		//목록 버튼 클릭
		$jq("#listButton").click(function() {
			var pathStep = "${pathStep}";
			if(pathStep == "C")
				$jq("#form").attr("action", "<c:url value='/collpack/workmanual/listMyManualView.do'/>");
			else
				$jq("#form").attr("action", "<c:url value='/collpack/workmanual/listHistoryView.do'/>");
			$jq("#form").submit();	
		});
		//삭제 버튼 클릭
		$jq("#deleteButton").click(function() {
			if(confirm("<ikep4j:message pre='${messagePrefix}' key='comfirm.delete' />")) {
				$jq("#form").attr("action", "<c:url value='/collpack/workmanual/deleteManualVersion.do'/>");
				$jq("#form").submit();	
			}	
		});
		//수정 버튼 클릭
		$jq("#modiyfyButton").click(function() {
			$jq("#form").attr("action", "<c:url value='/collpack/workmanual/updateManualVersionView.do'/>");
			$jq("#form").submit();
		});
		//제정 버튼 클릭
		var dlg;
		$jq("#submitButton").click(function() {
			$jq("#manualType").attr("innerHTML", "<ikep4j:message pre='${messagePrefix}' key='manual.submit'/>");
			$jq("#form>input[name=manualType]").attr("value", "A");
			dlg = $jq("#layerApproval").dialog({width: 600, height:360, modal:false, resizable: false});
		});
		//개정 버튼 클릭
		$jq("#revisionButton").click(function() {
			$jq("#manualType").attr("innerHTML", "<ikep4j:message pre='${messagePrefix}' key='manual.submit.revision'/>");
			$jq("#form>input[name=manualType]").attr("value", "B");
			dlg = $jq("#layerApproval").dialog({width: 600, height:360, modal:false, resizable: false});
		});
		//폐기 버튼 클릭
		$jq("#disuseButton").click(function() {
			$jq("#manualType").attr("innerHTML", "<ikep4j:message pre='${messagePrefix}' key='manual.disuse'/>");
			$jq("#form>input[name=manualType]").attr("value", "C");
			dlg = $jq("#layerApproval").dialog({width: 600, height:360, modal:false, resizable: false});
		});
		//팝업 취소 버튼 클릭
		$jq("#cancelButton").click(function() {
			dlg.dialog("close"); 
		});
		//승인요청 버튼 클릭
		$jq("#approvalButton").click(function() {
			if($jq("#requestContents").val() == "") {
				alert("<ikep4j:message key='NotEmpty.workmanual.requestContents' />");
				return;
			}
			$jq("#layerApproval").ajaxLoadStart(); 
			$jq("#form>input[name=requestContents]").attr("value", $jq("#requestContents").val());
			$jq("#form").attr("action", "<c:url value='/collpack/workmanual/createApproval.do'/>");
			$jq("#form").submit();
		});
		//폐기 취소 버튼 클릭
		$jq("#disuseCancelButton").click(function() {
			$jq("#form>input[name=manualType]").attr("value", "C");
			$jq("#form").attr("action", "<c:url value='/collpack/workmanual/cancelApproval.do'/>");
			$jq("#form").submit();
		});
		//개정 취소 버튼 클릭
		$jq("#revisionCancelButton").click(function() {
			$jq("#form>input[name=manualType]").attr("value", "B");
			$jq("#form").attr("action", "<c:url value='/collpack/workmanual/cancelApproval.do'/>");
			$jq("#form").submit();
		});
		//제정 취소 버튼 클릭
		$jq("#submitCancelButton").click(function() {
			$jq("#form>input[name=manualType]").attr("value", "A");
			$jq("#form").attr("action", "<c:url value='/collpack/workmanual/cancelApproval.do'/>");
			$jq("#form").submit();
		});
		
		//파일 다운로드
		var uploaderOptions = {
		   <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if>  
		   isUpdate : false 
		}; 
	    var fileController = new iKEP.FileController(null, "#fileDownload", uploaderOptions); 

	});

	
})(jQuery);
//]]>
</script>

<div id="tagResult">
				<h1 class="none"></h1>
				
				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2></h2>
				</div>
				<!--//pageTitle End-->

				<!--blockTableReadWrap Start-->
                <div class="blockTableReadWrap">
					<!--blockListTable Start-->
					<div class="blockTableRead">
						<div class="blockTableRead_t">
							<p><c:choose> 
								  <c:when test="${manualVersion.approvalStatus == 'A'}"> 
								    [<ikep4j:message pre='${messagePrefix}' key='manual.status.save'/>]
								  </c:when> 
								  <c:when test="${manualVersion.approvalStatus == 'C'}"> 
								    [<ikep4j:message pre='${messagePrefix}' key='manual.approval.accept'/>]
								  </c:when> 
								  <c:when test="$manualVersion.approvalStatus == 'D'}"> 
								    [<ikep4j:message pre='${messagePrefix}' key='manual.approval.reject'/>]
								  </c:when> 
								  <c:when test="${manualVersion.version == 1}"> 
								    [<ikep4j:message pre='${messagePrefix}' key='manual.submit'/>]
								  </c:when> 
								  <c:when test="${manualVersion.version < 1 && manualVersion.approvalStatus == 'B'}"> 
								    [<ikep4j:message pre='${messagePrefix}' key='manual.submit.apply'/>]
								  </c:when> 
								  <c:when test="${manualVersion.version > 1 && manualVersion.approvalStatus == 'B'}"> 
								    [<ikep4j:message pre='${messagePrefix}' key='manual.submit.revision.apply'/>]
								  </c:when> 
								  <c:otherwise> 
								    [<ikep4j:message pre='${messagePrefix}' key='manual.submit.revision'/>]
								  </c:otherwise> 
								</c:choose>&nbsp;${manualVersion.versionTitle}(<ikep4j:message pre="${prefix}" key="body.version"/>:${manualVersion.version})</p>
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
						</div>
						<c:if test="${fn:length(manualVersion.fileDataList) > 0}"><div id="fileDownload"></div></c:if>
						<div class="blockTableRead_c">
							<p>${manualVersion.versionContents}</p>
							<div class="tableTag">
								<img src="<c:url value='/base/images/theme/theme01/basic/ic_tag.gif'/>" alt="tag"/>${manualVersion.tag}
							</div>						
						</div>
					</div>
					<!--//blockListTable End-->	
								
					<!--blockButton Start-->
					<form id="form" action="" method="post">
						<input type="hidden" name="manualId" value="${manualVersion.manualId}"/>
						<input type="hidden" name="versionId" value="${manualVersion.versionId}"/>
						<input type="hidden" name="pathStep" value="${pathStep}"/>
						<input type="hidden" name="pathStep2" value="B"/>
						
						<input type="hidden" name="manualType" value=""/>
						<input type="hidden" name="requestContents" value=""/>
					</form>
					<div class="blockButton"> 
						<ul>
							<c:if test="${manual.manualType != 'C' && (writeAuthorityYn == 'Y' || adminYn == 'Y')}"><!-- 폐기아니며 문서 권한자 -->
								<c:if test="${manualVersion.approvalStatus == 'B'}"> <!-- 상신중 -->
									<c:if test="${manualVersion.isAbolition == 1}">
										<li><a class="button" href="#a" id="disuseCancelButton"><span><ikep4j:message pre='${buttonPrefix}' key='disuse.cancel'/></span></a></li>
									</c:if>
									<c:if test="${manualVersion.isAbolition == 0 && manual.version >= 1}">
										<li><a class="button" href="#a" id="revisionCancelButton"><span><ikep4j:message pre='${buttonPrefix}' key='submit.revision.cancel'/></span></a></li>
									</c:if>
									<c:if test="${manualVersion.isAbolition == 0 && manual.version < 1}">
										<li><a class="button" href="#a" id="submitCancelButton"><span><ikep4j:message pre='${buttonPrefix}' key='submit.cancel'/></span></a></li>
									</c:if>
								</c:if>
								<c:if test="${countSubmitting == 0}"> <!-- 비상신중 -->
									<c:choose>
										<c:when test="${manualVersion.approvalStatus == 'C' || manualVersion.approvalStatus == 'D' || isReleaseVersion}"> <!-- 결재문서 또는 릴리즈문서--> 
											<c:if test="${manualVersion.version >= standardVersion && manualVersion.version < standardVersion + 1}">
												<li><a class="button" href="#a" id="modiyfyButton"><span><ikep4j:message pre='${buttonPrefix}' key='modify'/></span></a></li>
											</c:if>
										</c:when>
										<c:otherwise>  
											<c:if test="${manualVersion.version >= 1 && manualVersion.version >= manual.version}">
												<li><a class="button" href="#a" id="disuseButton"><span><ikep4j:message pre='${buttonPrefix}' key='disuse'/></span></a></li>
												<c:if test="${!isReleaseVersion}">
													<li><a class="button" href="#a" id="revisionButton"><span><ikep4j:message pre='${buttonPrefix}' key='submit.revision'/></span></a></li>
												</c:if>
											</c:if>
											<c:if test="${manualVersion.version < 1 && manual.version < 1}">
												<li><a class="button" href="#a" id="submitButton"><span><ikep4j:message pre='${buttonPrefix}' key='submit'/></span></a></li>
											</c:if>
											<c:if test="${manualVersion.version >= standardVersion && manualVersion.version < standardVersion + 1}">
												<li><a class="button" href="#a" id="modiyfyButton"><span><ikep4j:message pre='${buttonPrefix}' key='modify'/></span></a></li>
											</c:if>
											<c:if test="${manualVersion.version < 1}">
												<li><a class="button" href="#a" id="deleteButton"><span><ikep4j:message pre='${buttonPrefix}' key='delete'/></span></a></li>
											</c:if>
										</c:otherwise>
									</c:choose>	
								</c:if>
							</c:if>
							<li><a class="button" href="#none" id="listButton"><span><ikep4j:message pre='${buttonPrefix}' key='list'/></span></a></li> 
						</ul>
					</div>
					<!--//blockButton End-->
				</div>
	            <!--//blockTableReadWrap End-->
				
				<c:if test="${manualVersion.approvalStatus != 'A'}">
					<!--subTitle_2 Start-->
					<div class="subTitle_2 noline">
						<h3><ikep4j:message pre="${prefix}" key="table.title"/></h3>
					</div>
					<!--//subTitle_2 End-->
					
					<!--blockDetail Start-->
					<div class="blockDetail">
						<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
							<caption></caption>
							<thead>
								<tr>
									<th class="textCenter" scope="col" width="5%"><ikep4j:message pre="${prefix}" key="table.column1"/></th>
									<th class="textCenter" scope="col" width="15%"><ikep4j:message pre="${prefix}" key="table.column2"/></th>
									<th class="textCenter" scope="col" width="10%"><ikep4j:message pre="${prefix}" key="table.column3"/></th>
									<th class="textCenter" scope="col" width="55%"><ikep4j:message pre="${prefix}" key="table.column5"/></th>
									<th class="textCenter" scope="col" width="15%"><ikep4j:message pre="${prefix}" key="table.column4"/></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${approvalLineList}"> 
								<tr>
									<td class="textCenter">${item.approvalLine}</td>
									<td class="textLeft">
										<c:choose>
											<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.approvalUserName}&nbsp;${item.approvalUserJobTitleName}</c:when>
											<c:otherwise>${item.approvalUserEnglishName}&nbsp;${item.approvalUserJobTitleEnglishName}</c:otherwise>
										</c:choose>
									</td>
									<td class="textCenter"><c:if test="${item.approvalResult == 'A'}"><ikep4j:message pre='${messagePrefix}' key='approvalResult.prepare'/></c:if>
															<c:if test="${item.approvalResult == 'B'}"><ikep4j:message pre='${messagePrefix}' key='approvalResult.holding'/></c:if>
															<c:if test="${item.approvalResult == 'C'}"><ikep4j:message pre='${messagePrefix}' key='approvalResult.accept'/></c:if>
															<c:if test="${item.approvalResult == 'D'}"><ikep4j:message pre='${messagePrefix}' key='approvalResult.reject'/></c:if>
									</td>
									<td class="textLeft" width="55%"><div class="ellipsis">${item.approvalComment}</div></td>
									<td class="textCenter"><ikep4j:timezone date="${item.approvalDate}" pattern="yyyy.MM.dd HH:mm"/></td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!--//blockDetail End-->
				</c:if>		
				
				
				<!-- Modal window Start -->
				<div class="" id="layerApproval" title="<ikep4j:message pre="${prefix}" key="popup.title"/>">
					<!--blockDetail Start-->
					<div class="blockDetail">
						<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
							<caption></caption>
							<tbody>
								<tr>
									<th scope="row" width="18%"><ikep4j:message pre="${prefix}" key="popup.subject"/></th>
									<td width="32%"><span>${manualVersion.versionTitle}</span></td>
									<th scope="row" width="18%"><ikep4j:message pre="${prefix}" key="popup.type"/></th>
									<td width="32%"><span id="manualType"></span></td>
								</tr>
								<tr>
									<th scope="row">*<ikep4j:message pre="${prefix}" key="popup.contents"/></th>
									<td colspan="3"><textarea id="requestContents" class="w100" title="<ikep4j:message pre="${prefix}" key="popup.contents"/>" cols="" rows="5"></textarea></td>
								</tr>
							</tbody>
						</table>
					</div>
					<!--//blockDetail End-->
					
					<!--subTitle_2 Start-->
					<div class="subTitle_2 noline">
						<h3><ikep4j:message pre="${prefix}" key="popup.table.title"/></h3>
					</div>
					<!--//subTitle_2 End-->
					
					<!--blockDetail Start-->
					<div class="blockDetail">
						<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
							<caption></caption>
							<thead>
								<tr>
									<th class="textCenter" scope="col" width="18%"><ikep4j:message pre="${prefix}" key="popup.table.column1"/></th>
									<th colspan="2" class="textCenter" scope="col" width="82%"><ikep4j:message pre="${prefix}" key="popup.table.column2"/></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${approvalUserList}"> 
								<tr>
									<td class="textCenter">${item.approvalLine}</td>
									<td colspan="2" class="textCenter">
										<c:choose>
											<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.approvalUserName}&nbsp;${item.approvalUserJobTitleName}</c:when>
											<c:otherwise>${item.approvalUserEnglishName}&nbsp;${item.approvalUserJobTitleEnglishName}</c:otherwise>
										</c:choose>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!--//blockDetail End-->
				
						
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<c:if test="${fn:length(approvalUserList) > 0}">
							<li><a class="button" href="#a" id="approvalButton"><span><ikep4j:message pre='${buttonPrefix}' key='submit.approval'/></span></a></li>
							</c:if>
							<li><a class="button" href="#a" id="cancelButton"><span><ikep4j:message pre='${buttonPrefix}' key='cancel'/></span></a></li>
						</ul>
					</div>
					<!--//blockButton End-->
					
				</div>	
				<!-- //Modal window End -->	
</div>										