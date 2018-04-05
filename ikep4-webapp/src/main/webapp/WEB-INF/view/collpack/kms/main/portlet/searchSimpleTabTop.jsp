<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/kms.css'/>" />


<script type="text/javascript">

	var cnt = ${fn:length(keywordList)};	
	var maxCnt = 7;
	var sel = "";
	
	
	function goNew() { 
		
		//alert("goNew");
		
		parent.document.getElementById("ifrmNewTab").contentWindow.location.href="<c:url value='/search/kmsSearchTabAdd.jsp?type=addSeachTab'/>";
		//document.getElementById("liNew").className = "selected";		
		
		for(i=0; i<cnt; i++){
			document.getElementById("liTag" + i).className = "";
		}
	}

	

	function addTag(v){ 
		document.location.reload(); 
	}
	
	
	function addTab(s){
		if(cnt >= maxCnt){
			alert("최대 7개까지 추가할수 있습니다");
			return;
		}

		/*
		var f = document.getElementById("tagForm");
		var tagNm = "";
		for(var i=0;i<cnt;i++){
			if(tagNm!=""){
				tagNm += ",";
			}
			tagNm += document.getElementById("liTag" + i).title;
		}
		f.tagNm.value = tagNm + "," + s;
		*/
		var f = document.getElementById("tagForm");
		f.keyword.value = s;
		
		//alert("addTab:" + s);
		//f.target = "ok";
		f.submit();
		//parent.location.reload();
	//	document.location.reload();
		
	}
	
	function showSearchTag(n, s){
		
		for(i=0; i<cnt ; i++){
			if(i != n){
				document.getElementById("liTag" + i).className = "";	
			}			
		}		
		
		document.getElementById("liTag" + n).className = "selected";
		if( cnt < 7 ){
			//document.getElementById("liNew").className = "";	
		}
		
		sel = n;		
		
		var keyword = document.getElementById("liTag" + n).title;
		parent.selUserKeyword(keyword);
	}
	
	function showSearchKeyword(){
		parent.selUserKeyword(document.getElementById("searchKeyword").value);
	}
	
	function deleteTab(n, item_seq){
		var f = document.getElementById("tagForm");		
		f.itemSeq.value = item_seq;
		f.mode.value = "del";
		//f.target = "_parent";
		//f.target = "ok";		
		f.submit();
	}

	window.onload = function(){		
				
		if(cnt > 0){
			//alert("showSearchTag:" + (cnt-1));
			//showSearchTag(cnt-1);	
		}else{
			//goNew();
		}
		
	}	
	
</script>
</head>
<body>
<form id="tagForm" name="tagForm" method="post" action="<c:url value='/collpack/kms/main/addKeyword.do'/>" onsubmit="return false;">
<input type="hidden" name="keyword" value=""/>	
<input type="hidden" name="userId" value="${user.userId}"/>					
<input type="hidden" name="itemSeq" value=""/>
<input type="hidden" name="mode" value=""/>
	<c:if test="${!empty keywordList}">
		<select id="searchKeyword" name="searchKeyword" onchange="javascript: showSearchKeyword();">
		<c:forEach var="keyword" items="${keywordList}" varStatus="status">
		<option value="${keyword.KEYWORD}">${keyword.KEYWORD}</option>
		</c:forEach>
	</select>
	</c:if>
	<c:if test="${empty keywordList}">
	</c:if>
	
	<div class="more" style="float:right; padding-top: 7px;"><a href='<c:url value='/collpack/kms/main/knowledgeShare.do' />'target="mainFrame" > <img src='<c:url value='/base/images/common/btn_more.gif'/>' alt='more' /></a></div>
</form>
<iframe frameborder="0" width="0" height="0" name="ok"></iframe>
</body>
</html>