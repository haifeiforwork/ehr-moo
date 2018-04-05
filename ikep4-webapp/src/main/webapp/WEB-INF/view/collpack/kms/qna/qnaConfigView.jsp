<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="preMessage" value="ui.support.fileupload.message" />
<c:set var="prefix" value="message.portal.portlet.managerMessage.configView"/>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.form.js"/>"></script>
<script type="text/javascript">
<!--   
$jq(document).ready(function() {
	var portletManagerMessageAddFormDiv = $jq("div[id=${portletConfigId}]");
	
	$jq("#managermessageConfigForm${portletConfigId}").submit(function(){
		var options = {
			success:function(){
				portletManagerMessageAddFormDiv.parent().parent().parent().trigger("click:reload");
				alert("<ikep4j:message pre="${prefix}" key="alert.saveMessage" />");
			},
			url:'<c:url value="/collpack/kms/qna/createQnaConfig.do"/>',
			type:'post',
			dataType : "html",
			error : function() {alert('error');}
		};
		
		$jq(this).ajaxSubmit(options);
		return false;
	});
	
	$jq("#managermessageAddButton", portletManagerMessageAddFormDiv).click(function() {
		var managermessageConfigForm = $jq("#managermessageConfigForm${portletConfigId}");
		
		if($jq("input[name=boardId]", managermessageConfigForm).val() == "") {
			alert("<ikep4j:message pre="${prefix}" key="alert.boardIdMessage" />");
			return;
		}
		
		if($jq("input[name=itemId]", managermessageConfigForm).val() == "") {
            alert("<ikep4j:message pre="${prefix}" key="alert.itemIdMessage" />");
            return;
        }
		
		var fileName = $jq("input[name=file]", managermessageConfigForm).val();
		
		if(fileName != "") {
			if(!fileName.match(/(.jpg|.JPG|.jpge|.JPGE|.gif|.GIF|.png|.PNG)$/)) {
				alert('<ikep4j:message pre='${preMessage}' key='file.image' />');
				return;
			}
		}
		
		$jq("#managermessageConfigForm${portletConfigId}").submit();
	});
});
//-->
</script>

<h4 class="guidetitle_han">지식Qna설정</h4>
<!--blockDetail Start-->
<div class="blockDetail" id="${portletConfigId}">
	<form name="managermessageConfigForm${portletConfigId}" id="managermessageConfigForm${portletConfigId}" method="post" enctype="multipart/form-data">
	<table summary="<ikep4j:message pre="${prefix}" key="summary" />" >
		<caption></caption>
		<tbody>
			<tr>
				<th scope="row" style="width: 80px;"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="write.subject" /></th>
				<td>
					<input name="subject" title="<ikep4j:message pre="${prefix}" key="write.subject" />" class="inputbox w50" type="text" value="${qnaconfig.subject}"/>
				</td>
			</tr>
			<tr>
				<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="write.summary" /></th>
				<td>
					<textarea name="summary" title="<ikep4j:message pre="${prefix}" key="write.summary" />" class="inputbox w90" rows="5" >${qnaconfig.summary}</textarea>
				</td>
			</tr>
			<%-- <tr>
                <th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="boardid" /></th>
                <td>
                    <input name="boardId" title="<ikep4j:message pre="${prefix}" key="boardid" />" class="inputbox w90" type="text" value="${qnaconfig.boardId}"/>
                </td>
            </tr> --%>
			<tr>
				<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="itemid" /></th>
				<td>
					<input name="itemId" title="<ikep4j:message pre="${prefix}" key="itemid" />" class="inputbox w20" type="text" value="${qnaconfig.itemId}"/>
				</td>
			</tr>
			<!-- tr>
				<th scope="row"><ikep4j:message pre="${prefix}" key="target" /></th>
				<td>
					<input type="radio" name="target" class="radio" value="WINDOW" <c:if test="${qnaconfig.target == 'WINDOW'}">checked="checked"</c:if> /><ikep4j:message pre="${prefix}" key="target.window" />
				</td>
			</tr-->
					<input type="hidden" name="target" class="radio" value="WINDOW" checked>
			<tr>
				<th scope="row"><ikep4j:message pre="${prefix}" key="image" /></th>
				<td>
					<input type="hidden" name="editorAttach" id="editorAttach" value="0"/>
					<input name="file" type="file" class="file w50" title="<ikep4j:message pre="${prefix}" key="image" />"/>
				</td>
			</tr>
			<%-- <tr>
				<th scope="row"><ikep4j:message pre="${prefix}" key="location" /></th>
				<td>
					<input type="radio" name="location" class="radio" value="left" <c:if test="${qnaconfig.location == 'left' or qnaconfig.location == null }">checked="checked"</c:if> /><ikep4j:message pre="${prefix}" key="location.left" />
					<input type="radio" name="location" class="radio" value="right" <c:if test="${qnaconfig.location == 'right'}">checked="checked"</c:if> /><ikep4j:message pre="${prefix}" key="location.right" />
					<input type="hidden" name="imgWidth" value="80" />
					<input type="hidden" name="imgHeight" value="80" />
			</tr>	 --%>		
			<!-- tr>
				<th scope="row"><ikep4j:message pre="${prefix}" key="img.size" /></th>
				<td>
					<ikep4j:message pre="${prefix}" key="img.width" /><input type="text" name="imgWidth" size="5" maxlength="3" class="radio" value="${qnaconfig.imgWidth}" />px
					<ikep4j:message pre="${prefix}" key="img.height" /><input type="text" name="imgHeight" size="5" maxlength="3" class="radio" value="${qnaconfig.imgHeight}" />px
				</td>
			</tr-->			
		</tbody>
	</table>
	<div class="blockButton" style="padding-top: 5px;"> 
		<ul>
			<li><a id="managermessageAddButton" class="button" href="#a" title="<ikep4j:message pre="${prefix}" key="button.save" />"><span><ikep4j:message pre="${prefix}" key="button.save" /></span></a></li>
		</ul>
	</div>
	</form>
</div>