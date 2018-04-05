<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>

<script type="text/javascript" src="<c:url value='/base/js/jquery/jquery-1.6.2.min.js'/>"></script>
<script language="JAVAScript" src="<c:url value='/js/jquery.form.js'/>"></script>

<script type="text/javascript" language="javascript">

	function getReceiveNoteList()
	{
		$.post('getReceiveNoteList.do', 
				"", 
				function(data) {
					$("#listDiv").html(data);
		});
	}
	
	function getSendNoteList()
	{
		$.post('getSendNoteList.do', 
				"", 
				function(data) {
					$("#listDiv").html(data);
		});
	}
	
	function getNoteInfo(noteId)
	{
		$.post('getNoteInfo.do?noteId='+noteId, 
				"", 
				function(data) {
					$("#infoDiv").html(data);
		});
	}
	
	function newNote()
	{
		$.post('newNote.do', 
				"", 
				function(data) {
					$("#infoDiv").html(data);
		});
	}
	
	function sendNote()
	{
		$.post('sendNote.do', 
				$("#noteFrm").formSerialize(), 
				function(data) {
					
		});
	}
	
	function getNewNoteCount()
	{
		$.post('getNewNoteCount.json', 
				"", 
				function(data) {
					$("#cntDiv").html(data.cnt);
		});
	}
	
	getNewNoteCount();
	
	function sendNote2()
	{
		$("#noteFrm").submit();
	}
	
	$(document).ready(function() {

		$("#noteFrm").submit(function(){
    		var options = {
    			success:function(data){
    				alert('ok');
    			},
    			url:'sendNote.do',
    			type:'post'
    		};
    		
    		$(this).ajaxSubmit(options);
    		return false;
    	});
	});
	
</script>

</head>

<body>
	
	<a href="javascript:newNote()">::쪽지보내기</a><a href="javascript:getReceiveNoteList()">::받은쪽지함</a>(<span id="cntDiv"></span>)<a href="javascript:getSendNoteList()">::보낸쪽지함</a>
	
	<table width="1024">
	
		<tr>
			<td width="600" valign="top">
				<div id="listDiv"></div>
			</td>
			<td width="424" valign="top">
				<div id="infoDiv"></div>
			</td>
		</tr>
		
	</table>	
	
	
</body>
</html>
