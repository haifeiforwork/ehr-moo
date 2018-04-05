<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%>

<c:set var="prefix">ui.collpack.workmanual.updateManualVersionView</c:set>
<c:set var="messagePrefix">ui.collpack.workmanual.message</c:set>
<c:set var="buttonPrefix">ui.collpack.workmanual.button</c:set>

<script type="text/javascript">
//<![CDATA[
(function($) {
	$jq(document).ready(function(){	
		//파일업로드 컨트롤로 생성	
		var uploaderOptions = {
			files : ${fileDataListJson}, //수정일때
			isUpdate : true,    // 등록 및 수정일때 true
			allowFileType : "all"
		};
		var fileController = new iKEP.FileController("#form", "#fileUploadArea", uploaderOptions);
		
		//저장 버튼 클릭
		$jq("#saveButton").click(function() {
			$("#form").trigger("submit");
		});
		
		//validation
		var validOptions = {
			rules : {
				versionTitle : {
					required : true, 
					rangelength : [1, 100]
				},
				versionContents : {
					required : true, 
					rangelength : [1, 1000]
				},
				updateReason : {
					required : true, 
					rangelength : [1, 100]
				}
			},
			messages : {
				versionTitle : {       
					required : "<ikep4j:message key='NotEmpty.workmanual.versionTitle' />",
					rangelength : "<ikep4j:message key='Size.workmanual.versionTitle' />"
				},
				versionContents : {       
					required : "<ikep4j:message key='NotEmpty.workmanual.versionContents' />",
					rangelength : "<ikep4j:message key='Size.workmanual.versionContents' />"
				},
				updateReason : {       
					required : "<ikep4j:message key='NotEmpty.workmanual.updateReason' />",
					rangelength : "<ikep4j:message key='Size.workmanual.updateReason' />"
				}
			},
			notice : {
				versionTitle : "<ikep4j:message key='Notice.workmanual.versionTitle' />",
				versionContents : "<ikep4j:message key='Notice.workmanual.versionContents' />",
				updateReason : "<ikep4j:message key='Notice.workmanual.updateReason' />"
			},
			submitHandler : function(form) {// 부가 검증 처리
				$jq("#form").attr("action", "<c:url value='/collpack/workmanual/updateManualVersion.do'/>");
				var isPublic = $jq("#form input[name=public]:checked").val();
				$jq("#form input[name=isPublic]").val(isPublic);
				
				//파일이 있을경우, 업로드를 먼저 실행함
				fileController.upload(function(isSuccess, files) {
					if(isSuccess == true) {
						form.submit();
					}
				});
			}

		};
		new iKEP.Validator("#form", validOptions);	
		
		//목록 버튼 클릭
		$jq("#listButton").click(function() {
			var pathStep2 = "${pathStep2}";
			if(pathStep2 == "A") {
				window.location.href = "<c:url value='/collpack/workmanual/readManualView.do'/>" + "?manualId=${manualVersion.manualId}&pathStep=${pathStep}";
				
			} else {
				window.location.href = "<c:url value='/collpack/workmanual/readManualVersionView.do'/>" + "?versionId=${manualVersion.versionId}&pathStep=${pathStep}";
			}
		});
		
	});

	
})(jQuery);
//]]>
</script>

<div id="tagResult">
				<h1 class="none"></h1>

				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${prefix}' key='main.title'/></h2>
				</div>
				<!--//pageTitle End-->
		
				<!--blockDetail Start-->
				<div class="blockDetail">
					<form id="form" method="post">
						<input type="hidden" name="manualId" value="${manualVersion.manualId}"/>
						<input type="hidden" name="versionId" value="${manualVersion.versionId}"/>
						<input type="hidden" name="pathStep" value="${pathStep}"/>
						<input type="hidden" name="pathStep2" value="${pathStep2}"/>
						<input type="hidden" name="approvalStatus" value="A"/>
						<input type="hidden" name="isPublic" value=""/>
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
						<caption></caption>
						<tbody>
							<tr>
								<th scope="row" width="18%">*<ikep4j:message pre='${prefix}' key='form.title'/></th>
								<td colspan="3" width="82%">
									<div><input name="versionTitle" value="${manualVersion.versionTitle}" title="<ikep4j:message pre='${prefix}' key='form.title'/>" class="inputbox w100" type="text" /></div>								
								</td>
							</tr>
							<tr>
								<th scope="row">*<ikep4j:message pre='${prefix}' key='form.contents'/></th>
								<td colspan="3"><div><textarea name="versionContents" class="w100" title="<ikep4j:message pre='${prefix}' key='form.contents'/>" cols="" rows="20">${manualVersion.versionContents}</textarea></div></td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre='${prefix}' key='form.attachFile'/></th>
								<td colspan="3"><div id="fileUploadArea"></td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre='${prefix}' key='form.tag'/></th>
								<td colspan="3">
									<input name="tag" value="${manualVersion.tag}" class="inputbox w80" type="text" title="input box"/>
									<div class="tdInstruction"><ikep4j:message pre='${prefix}' key='form.tag.notice'/></div>	
								</td>
							</tr>	
							<tr>
								<th scope="row"><ikep4j:message pre='${prefix}' key='form.isPublic'/></th>
								<td colspan="3">
									<input name="public" value="1" <c:if test="${manualVersion.isPublic == 1}">checked="checked"</c:if> <c:if test="${manual.version < 1}">disabled="disabled"</c:if> type="radio" class="radio" title="<ikep4j:message pre='${prefix}' key='form.isPublic.open'/>"/><ikep4j:message pre='${prefix}' key='form.isPublic.open'/>&nbsp;
									<input name="public" value="0" <c:if test="${manualVersion.isPublic == 0}">checked="checked"</c:if> <c:if test="${manual.version < 1}">disabled="disabled"</c:if> type="radio" class="radio" title="<ikep4j:message pre='${prefix}' key='form.isPublic.close'/>"/><ikep4j:message pre='${prefix}' key='form.isPublic.close'/>
								</td>
							</tr>	
							<tr>
								<th scope="row">*<ikep4j:message pre='${prefix}' key='form.updateReason'/></th>
								<td colspan="3">
									<div><input name="updateReason" value="" title="<ikep4j:message pre='${prefix}' key='form.updateReason'/>" class="inputbox w100" type="text" /></div>							
								</td>
							</tr>					
						</tbody>
					</table>
					</form>
				</div>
				<!--//blockDetail End-->
												
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><a class="button" href="#none" id="saveButton"><span><ikep4j:message pre='${buttonPrefix}' key='save'/></span></a></li>
						<li><a class="button" href="#none" id="listButton"><span><ikep4j:message pre='${buttonPrefix}' key='list'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->
</div>				