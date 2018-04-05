<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<%@ page import="com.lgcns.ikep4.collpack.forum.constants.ForumConstants" %>

<c:set var="preUi" 			value="ui.collpack.forum.forumSearch" />

<c:set var="user" value="${sessionScope['ikep.user']}" />


<!--pageTitle Start-->
<div id="pageTitle_main" class="border_none mb0">
	<h2><ikep4j:message pre='${preUi}' key='head'/></h2>
	<div class="search_con" style="width:320px;">
	<form id="searchForm" action="">
		<input type="hidden" name="searchColumn" value="title"/>
		<input type="hidden" name="pageIndex" value="1"/>
		<h3 class="none">search</h3>
		<div class="search_con_sel">
			<a class="sel_con" href="#a" onclick="sBoxView();" id="selectCol" title="<ikep4j:message pre='${preUi}' key='title'/>"><ikep4j:message pre='${preUi}' key='title'/><span>select</span></a>
			<div class="search_con_layer none" style="width:80px;" id="sBox">
				<ul>
					<li><a href="#a" onclick="sBoxSelect('<ikep4j:message pre='${preUi}' key='title'/>','title');" title="<ikep4j:message pre='${preUi}' key='title'/>"><ikep4j:message pre='${preUi}' key='title'/></a></li>
					<li><a href="#a" onclick="sBoxSelect('<ikep4j:message pre='${preUi}' key='regester'/>','registerName');" title="<ikep4j:message pre='${preUi}' key='regester'/>"><ikep4j:message pre='${preUi}' key='regester'/></a></li>
					<li><a href="#a" onclick="sBoxSelect('<ikep4j:message pre='${preUi}' key='tag'/>','tag');" title="<ikep4j:message pre='${preUi}' key='tag'/>"><ikep4j:message pre='${preUi}' key='tag'/></a></li>
				</ul>
			</div>
		</div>							
		<input name="searchWord" type="text" class="inputbox" title="search" style="width:200px;"/>
		<div class="search_con_btn"><a href="#a" onclick="goSearch();return false;" title="<ikep4j:message pre='${preUi}' key='search'/>"><img src="<c:url value="/base/images/icon/ic_search_con.gif"/>" alt="<ikep4j:message pre='${preUi}' key='search'/>" /></a></div>
	</form>
	</div>
</div>
<!--//pageTitle End-->


<script type="text/javascript">
//<![CDATA[
	$jq(document).ready(function() {
		
		 var searchValidOptions = {
				rules : {
					searchWord :	{
						required : true,
						maxlength  : 100
					}
				},
				messages : {
					searchWord : {
						
						required : "<ikep4j:message key='NotEmpty.forum.searchTitle'/>",
						maxlength : "<ikep4j:message key='Max.forum.searchTitle' arguments='100'/>",
						direction : "bottom"
					}
				},
				notice : {
					searchWord : {
				         message : "<ikep4j:message key='NotEmpty.forum.searchTitleNotice'/>",
				         direction : "bottom"
	
					}
				},
				submitHandler : function(form) {
					
					goSearchAction();
					
				}
	
			};
		    
		    new iKEP.Validator("#searchForm", searchValidOptions);
			    
		    var f = $jq('#searchForm');
			
			var searchColumn = f.find('input[name=searchColumn]').val('title');
			var searchWord = f.find('input[name=searchWord]').val('');
		
	});


function goSearch(){
	$jq('#searchForm').submit();
}

function goSearchAction(){
	
	var f = $jq('#searchForm');
	
	var searchColumn = f.find('input[name=searchColumn]').val();
	var searchWord = f.find('input[name=searchWord]').val();
	var pageIndex = f.find('input[name=pageIndex]').val();
	
	if(!searchWord){
		alert('<ikep4j:message key='NotEmpty.forum.searchTitle'/>');
		return;
	}
	
	$jq('#moreBtn').remove();

	$jq('#sBox').hide();

	$jq("#tagResult").ajaxLoadStart(); 
	
	if(searchColumn == "tag"){
		iKEP.tagging.tagSearchByName(searchWord, '<%=TagConstants.ITEM_TYPE_FORUM%>', '<%=ForumConstants.TAG_TYPE_ITEM%>');

		$jq("#tagResult").ajaxLoadComplete();
		
	} else {
		//f.submit();
		
		$jq.ajax({    
			url : "itemSearch.do",     
			data : {searchColumn:searchColumn, searchWord:searchWord,pageIndex:pageIndex, docIframe:'${param.docIframe}'},     
			type : "post",  
			dataType : "html",
			success : function(result) {  

				$jq("#tagResult").ajaxLoadComplete();
				
				$jq("#tagResult").html(result);  

			},
			error : function(event, request, settings){

			$jq("#tagResult").ajaxLoadComplete();
				
			 alert("error");
			}
		}); 
	}
	
}

function sBoxView(){
	$jq('#sBox').show();
	return false;
	
}

function sBoxSelect(text, val){
	$jq('#selectCol').text(text);
	$jq('#searchForm').find('input[name=searchColumn]').val(val);
	$jq('#sBox').hide();
	return false;
}

function listSearchMore(totalCount){
	
	var f = $jq('#searchForm');
	
	var searchColumn = f.find('input[name=searchColumn]').val();
	var searchWord = f.find('input[name=searchWord]').val();
	
	if(!searchWord){
		alert('<ikep4j:message key='NotEmpty.forum.searchTitle'/>');
		return;
	}
	
	listMore("itemSearch.do", totalCount, {searchColumn:searchColumn, searchWord:searchWord});
}
//]]>
</script>
