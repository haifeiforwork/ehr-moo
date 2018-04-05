<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="preMessage" value="ui.support.fileupload.message" />
<c:set var="prefix" value="message.portal.portlet.innovation.configView"/>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.form.js"/>"></script>
<script type="text/javascript">
<!--   


checkAttachFile = function () {
	
	if(!$jq("#fileChk").is(":checked")) {
		
		if(confirm("<ikep4j:message pre='${prefix}' key='alert.checkAttach' />")) {
			$jq("#fileChk").attr("checked", false);
			$jq("#attachCheck").val("0");
			$jq("input[name=imageFileId]").val('');
			$jq("#fileDiv").show();
		} else {
			$jq("#fileChk").attr("checked", true);
			$jq("#attachCheck").val("1");
			
			return;
		}
	} else {
		$jq("#fileChk").attr("checked", true);
		$jq("#attachCheck").val("1");
		$jq("#fileDiv").hide();
	}
	
};

afterFileUpload = function (status, fileId, fileName, message, targetId) {
	
	var htmlText = "<a class='image-gallery' href='<c:url value='/support/fileupload/downloadFile.do'/>?fileId="+fileId+"' title='"+fileName+"'>"+fileName+"</a>";
	
	$jq("#fileNameSpan").html(htmlText);
	$jq("input[name=file]").val(fileName);
	$jq("input[name=imageFileId]").val(fileId);
	$jq("#attachCheck").val("1");
	
};


$jq(document).ready(function() {
	var portletInnovationAddFormDiv = $jq("div[id=${portletInnovation.portletConfigId}]");
	
	$jq("#fileuploadBtn").click(function(event) { 
		iKEP.fileUpload(event.target.id, '0', '1');	
	});
	
	
	$jq("#innovationConfigForm${portletInnovation.portletConfigId}").submit(function(){
		var options = {
			success:function(){
				portletInnovationAddFormDiv.parent().parent().parent().trigger("click:reload");
				alert("<ikep4j:message pre="${prefix}" key="alert.saveMessage" />");
			},
			url:'<c:url value="/portal/portlet/innovation/createPortletInnovation.do"/>',
			type:'post',
			dataType : "html",
			error : function() {alert('error');}
		};
		
		$jq(this).ajaxSubmit(options);
		return false;
	});
	
	$jq("#innovationAddButton", portletInnovationAddFormDiv).click(function() {
		var innovationConfigForm = $jq("#innovationConfigForm${portletInnovation.portletConfigId}");
		
		
		<c:if test="${!empty portletInnovation.fileData}">
		
		if($jq("#fileChk").is(":checked") && ($jq("#imageFileId").val() != $jq("#oldImageFileId").val())) {
			$jq("#imageFileId").val($jq("#oldImageFileId").val());
		}
		</c:if>

		if($jq("#fileChk").is(":checked")){
			$jq("#attachCheck").val("1");
		}
		
		if($jq("input[name=url]", innovationConfigForm).val() == "") {
			alert("<ikep4j:message pre="${prefix}" key="alert.urlMessage" />");
			return;
		}
		/* 
		var fileName = $jq("input[name=file]", innovationConfigForm).val();
		
	
		if(fileName == "") {
			alert('<ikep4j:message pre='${preMessage}' key='file.select' />');
			return;
		}
	
		
		if(fileName != "") {
			if(!fileName.match(/(.jpg|.JPG|.jpge|.JPGE|.gif|.GIF|.png|.PNG)$/)) {
				alert('<ikep4j:message pre='${preMessage}' key='file.image' />');
				return;
			}
		} */
		
		$jq("#innovationConfigForm${portletInnovation.portletConfigId}").submit();
	});
});
//-->
</script>

<h4 class="guidetitle_han"><ikep4j:message pre="${prefix}" key="title" /></h4>
<!--blockDetail Start-->
<div class="blockDetail" id="${portletInnovation.portletConfigId}">
	<form name="innovationConfigForm${portletInnovation.portletConfigId}" id="innovationConfigForm${portletInnovation.portletConfigId}" method="post" enctype="multipart/form-data">
	<table summary="<ikep4j:message pre="${prefix}" key="summary" />">
		<caption></caption>
		<tbody>
			<tr>
				<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="url" /></th>
				<td>
					<input name="url" title="<ikep4j:message pre="${prefix}" key="url" />" class="inputbox w90" type="text" value="${portletInnovation.url}"/>
				</td>
			</tr>
			<tr>
				<th scope="row"><ikep4j:message pre="${prefix}" key="target" /></th>
				<td>
					<input type="radio" name="target" class="radio" value="WINDOW" <c:if test="${portletInnovation.target == 'WINDOW'}">checked="checked"</c:if> /><ikep4j:message pre="${prefix}" key="target.window" />
					<input type="radio" name="target" class="radio" value="INNER" <c:if test="${empty portletInnovation.target || portletInnovation.target == 'INNER'}">checked="checked"</c:if>/><ikep4j:message pre="${prefix}" key="target.inner" />
				</td>
			</tr>
			<tr>
				<th scope="row"><ikep4j:message pre="${prefix}" key="image" /></th>
				<td>
					<c:if test="${!empty portletInnovation.fileData}">
					<input type="checkbox" id="fileChk" name="fileChk" value="1" checked="checked" onclick="checkAttachFile();" />
					<a class="image-gallery" href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${portletInnovation.fileData.fileId}" title="${portletInnovation.fileData.fileRealName}">
						<c:out value="${portletInnovation.fileData.fileRealName}"/>
					</a>
					</c:if>
					
					<div id="fileDiv" <c:if test="${!empty portletInnovation.fileData}">style="display:none;"</c:if>>
						<span id="fileNameSpan"></span>
						<a class="button_s" href="#" onclick="return false;">
							<span id="fileuploadBtn"><ikep4j:message pre="${prefix}" key="button.attachFile" /></span>
						</a>
					</div>
					<input type="hidden" name="portletConfigId" id="portletConfigId" value="${portletInnovation.portletConfigId}"/>
					<input type="hidden" name="attachCheck" id="attachCheck" value="0"/>
					<input type="hidden" name="editorAttach" id="editorAttach" value="0"/>
					<input type="hidden" id="oldImageFileId" name="oldImageFileId" value="${portletInnovation.imageFileId}"/>
					<input type="hidden" id="imageFileId" name="imageFileId" value="${portletInnovation.imageFileId}"/>
					<input type="hidden" id="file" name="file" value=""/>
				</td>
				<%-- <td>
					<input type="hidden" name="portletConfigId" id="portletConfigId" value="${portletInnovation.portletConfigId}"/>
					<input type="hidden" name="editorAttach" id="editorAttach" value="0"/>
					<input name="file" type="file" class="file w100" title="<ikep4j:message pre="${prefix}" key="image" />"/>
				</td> --%>
			</tr>
		</tbody>
	</table>
	<div class="blockButton" style="padding-top: 5px;"> 
		<ul>
			<li><a id="innovationAddButton" class="button_s" href="#a" title="<ikep4j:message pre="${prefix}" key="button.save" />"><span><ikep4j:message pre="${prefix}" key="button.save" /></span></a></li>
		</ul>
	</div>
	</form>
</div>