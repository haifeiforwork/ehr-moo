<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%@ include file="/base/common/fileUploadControll.jsp"%>

<c:set var="prefix">ui.collpack.workmanual.createManualView</c:set>
<c:set var="messagePrefix">ui.collpack.workmanual.message</c:set>
<c:set var="buttonPrefix">ui.collpack.workmanual.button</c:set>

<script type="text/javascript">
//<![CDATA[
(function($) {
	$jq(document).ready(function(){
		//파일업로드 컨트롤로 생성	
		var uploaderOptions = {
			isUpdate : true,    // 등록 및 수정일때 true
			allowFileType : "all"
		};
		var fileController = new iKEP.FileController("#form", "#fileUploadArea", uploaderOptions);
		
		//저장 클릭
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
				}
			},
			notice : {
				versionTitle : "<ikep4j:message key='Notice.workmanual.versionTitle' />",
				versionContents : "<ikep4j:message key='Notice.workmanual.versionContents' />"
			},
			submitHandler : function(form) {// 부가 검증 처리
				//파일이 있을경우, 업로드를 먼저 실행함
				fileController.upload(function(isSuccess, files) {
					if(isSuccess == true) {
						form.submit();
					}
				});
			}

		};
		new iKEP.Validator("#form", validOptions);
		
	});

	
})(jQuery);
//]]>
</script>

				<h1 class="none"></h1>

				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${prefix}' key='main.title'/></h2>
				</div>
				<!--//pageTitle End-->
		
				<!--blockDetail Start-->
				<div class="blockDetail">
					<form id="form" action="<c:url value='/collpack/workmanual/saveNewManual.do'/>" method="post">
					<input type="hidden" name="categoryId" value="${categoryId}"/>
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
						<caption></caption>
						<tbody>
							<tr>
								<th scope="row" width="18%">*<ikep4j:message pre='${prefix}' key='form.title'/></th>
								<td colspan="3" width="82%">
									<div><input name="versionTitle" title="<ikep4j:message pre='${prefix}' key='form.title'/>" class="inputbox w100" type="text" /></div>							
								</td>
							</tr>
							<tr>
								<th scope="row">*<ikep4j:message pre='${prefix}' key='form.contents'/></th>
								<td colspan="3"><div><textarea name="versionContents" class="w100" title="<ikep4j:message pre='${prefix}' key='form.contents'/>" cols="" rows="20"></textarea></div></td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre='${prefix}' key='form.attachFile'/></th>
								<td colspan="3"><div id="fileUploadArea"></div></td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre='${prefix}' key='form.tag'/></th>
								<td colspan="3">
									<input name="tags" title="<ikep4j:message pre='${prefix}' key='form.tag'/>" class="inputbox w80" type="text" />
									<div class="tdInstruction"><ikep4j:message pre='${prefix}' key='form.tag.notice'/></div>								
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
						<li><a class="button" href="<c:url value='/collpack/workmanual/listCategoryManualView.do?categoryId=${categoryId}'/>"><span><ikep4j:message pre='${buttonPrefix}' key='list'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->