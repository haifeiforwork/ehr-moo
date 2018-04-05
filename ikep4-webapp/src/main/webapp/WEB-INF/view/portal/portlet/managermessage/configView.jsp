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
			url:'<c:url value="/portal/portlet/managermessage/createPortletManagerMessage.do"/>',
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

<h4 class="guidetitle_han"><ikep4j:message pre="${prefix}" key="title" /></h4>
<!--blockDetail Start-->
<div class="blockDetail" id="${portletConfigId}">
	<form name="managermessageConfigForm${portletConfigId}" id="managermessageConfigForm${portletConfigId}" method="post" enctype="multipart/form-data">
	<table summary="<ikep4j:message pre="${prefix}" key="summary" />">
		<caption></caption>
		<tbody>
			<tr>
				<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="write.subject" /></th>
				<td>
					<input name="subject" title="<ikep4j:message pre="${prefix}" key="write.subject" />" class="inputbox w90" type="text" value="${portletManagerMessage.subject}"/>
				</td>
			</tr>
			<tr>
				<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="write.summary" /></th>
				<td>
					<textarea name="summary" title="<ikep4j:message pre="${prefix}" key="write.summary" />" class="inputbox w90">${portletManagerMessage.summary}</textarea>
				</td>
			</tr>
			<tr>
                <th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="boardid" /></th>
                <td>
                    <input name="boardId" title="<ikep4j:message pre="${prefix}" key="boardid" />" class="inputbox w90" type="text" value="${portletManagerMessage.boardId}"/>
                </td>
            </tr>
			<tr>
				<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="itemid" /></th>
				<td>
					<input name="itemId" title="<ikep4j:message pre="${prefix}" key="itemid" />" class="inputbox w90" type="text" value="${portletManagerMessage.itemId}"/>
				</td>
			</tr>
			<!-- tr>
				<th scope="row"><ikep4j:message pre="${prefix}" key="target" /></th>
				<td>
					<input type="radio" name="target" class="radio" value="WINDOW" <c:if test="${portletManagerMessage.target == 'WINDOW'}">checked="checked"</c:if> /><ikep4j:message pre="${prefix}" key="target.window" />
				</td>
			</tr-->
					<input type="hidden" name="target" class="radio" value="WINDOW" checked>
			<tr>
				<th scope="row">인물 <ikep4j:message pre="${prefix}" key="image" /></th>
				<td>
					<input name="file" type="file" class="file w100" title="<ikep4j:message pre="${prefix}" key="image" />"/>
				</td>
			</tr>
			<tr>
				<th scope="row">헤더<ikep4j:message pre="${prefix}" key="image" /></th>
				<td>
					<input type="hidden" name="editorAttach" id="editorAttach" value="0"/>
					<input name="file" type="file" class="file w100" title="<ikep4j:message pre="${prefix}" key="image" />"/>
				</td>
			</tr>
			<tr>
				<th scope="row"><ikep4j:message pre="${prefix}" key="location" /></th>
				<td>
					<input type="radio" name="location" class="radio" value="left" <c:if test="${portletManagerMessage.location == 'left' or portletManagerMessage.location == null }">checked="checked"</c:if> /><ikep4j:message pre="${prefix}" key="location.left" />
					<input type="radio" name="location" class="radio" value="right" <c:if test="${portletManagerMessage.location == 'right'}">checked="checked"</c:if> /><ikep4j:message pre="${prefix}" key="location.right" />
					<input type="hidden" name="imgWidth" value="80" />
					<input type="hidden" name="imgHeight" value="80" />
			</tr>			
			<!-- tr>
				<th scope="row"><ikep4j:message pre="${prefix}" key="img.size" /></th>
				<td>
					<ikep4j:message pre="${prefix}" key="img.width" /><input type="text" name="imgWidth" size="5" maxlength="3" class="radio" value="${portletManagerMessage.imgWidth}" />px
					<ikep4j:message pre="${prefix}" key="img.height" /><input type="text" name="imgHeight" size="5" maxlength="3" class="radio" value="${portletManagerMessage.imgHeight}" />px
				</td>
			</tr-->			
		</tbody>
	</table>
	<div class="blockButton" style="padding-top: 5px;"> 
		<ul>
			<li><a id="managermessageAddButton" class="button_s" href="#a" title="<ikep4j:message pre="${prefix}" key="button.save" />"><span><ikep4j:message pre="${prefix}" key="button.save" /></span></a></li>
		</ul>
	</div>
	</form>
</div>