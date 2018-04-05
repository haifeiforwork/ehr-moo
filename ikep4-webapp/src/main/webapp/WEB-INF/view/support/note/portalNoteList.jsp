<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="prefix" value="ui.support.note.portalNote"/>
<c:set var="preSearch"  value="ui.ikep4.common.searchCondition" /> 

<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript"> 
//<!--	
(function($) {
	$jq(document).ready(function() {

		$jq("select[name=sortWord]").change(function(event) {
			var sortInfo = $("#sortWord").val().split(":");
			$("#sortColumn").val(sortInfo[0]);
			$("#sortType").val(sortInfo[1]);

			getSearchNoteList();
        });  
		
		$("a.note").click(function() {		
			$jq("#noteList").hide();
			$jq("#noteDiv").html("");
			$jq("#noteDetail").show();
			
			getNoteView($(this).attr("value"));
		}); 
		
	});
})(jQuery)

function getNoteView(noteId) {		
	$jq.ajax({
		url : "<c:url value='/support/note/portalNoteView.do'/>",
		data : {noteId: noteId},
		type : "get",
		loadingElement : {container : $jq(".note_body")},
		dataType : "html",
		success : function(result) {
			$jq("#noteViewDiv").html(result);
		}
	});
}

//-->
</script>
					
<div class="search_box">
	<h2>
		<c:choose>
			<c:when test="${noteFolder.folderId eq 'A'}">
			<ikep4j:message pre="${prefix}" key="noteAllListView" />
			</c:when>
			<c:when test="${noteFolder.folderId eq 'I'}">
			<ikep4j:message pre="${prefix}" key="notePriorityListView" />
			</c:when>
			<c:when test="${noteFolder.folderId eq 'D'}">
			<ikep4j:message pre="${prefix}" key="noteTrashListView" />
			</c:when>
			<c:otherwise>
			${noteFolder.folderName}
			</c:otherwise> 
		</c:choose>
	</h2>
	<div class="tableSearch mr0">
		<select id="sortWord" name="sortWord">
			<option value="REGIST_DATE:DESC" <c:if test="${'REGIST_DATE:DESC' eq searchCondition.sortWord}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='registDate.desc' /></option>
		    <option value="REGIST_DATE:ASC" <c:if test="${'REGIST_DATE:ASC' eq searchCondition.sortWord}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='registDate.asc' /></option>
		    <option value="TITLE:ASC" <c:if test="${'TITLE:ASC' eq searchCondition.sortWord}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='title.asc' /></option>
			<option value="TITLE:DESC" <c:if test="${'TITLE:DESC' eq searchCondition.sortWord}">selected="selected"</c:if>><ikep4j:message pre='${preSearch}' key='title.desc' /></option>
		</select>	
	</div>
	<div class="clear"></div>
</div>

<div class="noteTable summary">
<c:choose>
	<c:when test="${empty portalNoteList}">
		<ul><span class="system_infoText" style="margin-left:10px; text-align:left;"><ikep4j:message pre='${prefix}' key='noSearch' /><span></span></ul>
	</c:when>
	<c:otherwise>
		<table>
			<caption></caption>
			<colgroup>
				<col style="width:80px;">
				<col style="width:*;">
			</colgroup>
			<c:forEach var="note" items="${portalNoteList}" varStatus="status">
			<tr class="">				
			<c:choose>
				<c:when test="${not empty note.imageFileId}">
				<td class="note_box_img">
					<img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${note.imageFileId}&amp;thumbnailYn=Y" alt="Image"/>
				</td>
				<td class="note_box_con">
					<div class="note_box_con_t">
						<div class="note_box_con_tl_layer">
				</c:when>
				<c:otherwise>
				<td colspan="2" class="note_box_con">    
					<div class="note_box_con_t">
						<div class="note_box_con_tl_layer_noImg">
				</c:otherwise>
			</c:choose>					
							<a class="note" value="${note.noteId}" href="#a">
								<h3>${note.title}</h3>
							</a>
							<p><ikep4j:extractText text="${note.contents}" length="100"/></p>
						</div>
					</div>
					<div class="note_box_con_info">
						<span class="note_box_con_date"><ikep4j:timezone date="${note.registDate}" pattern="yyyy.MM.dd"/></span>
					</div>
				</td>
			</tr>
			</c:forEach>
		</table>
	</c:otherwise>
</c:choose>
</div>