<%--
=====================================================
* 기능 설명 : 양식목록 레이아웃
* 작성자    : approval
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.apprForm.header" /> 
<c:set var="preButton"  value="ui.approval.common.button" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript">
	<!--
	
	(function($){
		
		/**
		 * onload시 수행할 코드
		 */
		$(document).ready(function(){
		    	
			$jq("#apprFormLinkOfLeft").click();
			
		   /**
    		* category tree 생성
    		*/
			createTree();
			
		    /**
    		* category tree 생성
    		*/
		    readFormList();
		});
		
		createTree = function () {
		    $("#categoryTreeDiv").bind("loaded.jstree", function (event, datet) {
    			$(this).jstree("hide_icons");
    			$(this).jstree("open_all");
    		}).jstree({
    		    plugins     : ["themes", "ui", "json_data"],  
    		    ui			: { select_limit : 1},
    		    json_data   : {"data" :  ${categoryTreeJson}}
    		}).bind("select_node.jstree", function (event, data) {
    		    readFormList($(data.rslt.obj).attr("codeid"), $(data.rslt.obj).attr("codename"));
    		});
		}
		
		createApprDocForm = function(formId){
		    $("body").ajaxLoadStart("button");
		    $("#listForm input[name=formId]").val(formId);
		    $("#listForm").attr("action", "<c:url value="/approval/work/apprWorkDoc/createApprDocForm.do"/>");
		    document.listForm.submit();
		};
		
		readFormList = function(formParentId, formParentName){
		    if(typeof(formParentId)!="undefined") {
		        $("#listForm input[name=formParentId]").val(formParentId);
		        $("#categoryNameSpan").html(formParentName);
		    }
		    
		    $.ajax({
				url : "<c:url value="/approval/work/apprWorkForm/ajaxListApprForm.do"/>",
				type : "post",
				loadingElement : {container:"body"},
				data : $("#listForm").serialize(),
				success : function(result) {
					$('#formListDiv').html(result);
				    pageEvent();
				},
				error : function(xhr, exMessage) {
				
					var errorItems = $.parseJSON(xhr.responseText).exception;
					validator.showErrors(errorItems);
				}
			});		
		};
		
		addFavorite = function(t, formId){
			var mode = $(t).hasClass("select")?"remove":"add";
			
			$.post(iKEP.getContextRoot()+"/approval/work/apprWorkForm/ajaxSetApprFavorite.do", {"formId" : formId, "mode" : mode})
			.success(function(data) {
				if(mode=="remove"){
					$(t).removeClass("select");
				}else{
					$(t).addClass("select");
				}
			})
			.error(function(event, request, settings) { alert("error"); });	
		};
		
		pageEvent = function(){
		    $("#pageIndex").click(function() {
				readFormList();
			});
		};
		
		sort = function(sortColumn, sortType) { 
			$("#listForm input[name=actionType]").val("sort");
			$("#listForm input[name=sortColumn]").val(sortColumn); 
			$("#listForm input[name=sortType]").val(sortType == "ASC" ? "DESC" : "ASC");	 
			readFormList();
		}; 
		
		sortFavorite = function(t){
			if(t.checked){
				sort("FAVORITE_ID", "DESC");
			}else{
				sort("FORM_PARENT_ID", "DESC");
			}
		};
		
		checkKeyevent = function(event){
            if (event.keyCode == '13'){
                readFormList();
            }
        };
		
	})(jQuery);
	
	
	//-->
</script>

<!--mainContents Start-->
<h1 class="none">content area</h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preHeader}' key='title.list'/></h2>
</div>
<!--//pageTitle End-->

<!--blockLeft Start-->
<div class="blockLeft" style="width:20%;" summary="">	
    <h3>&nbsp;</h3>
	<div class="leftTree treeBox">	
		<div><img src="/ikep4-webapp/base/images/common/img_title_cate.gif" alt="category" /></div>
		<div id="categoryTreeDiv"></div>
	</div>
</div>
<!--//blockLeft End-->

<!--blockRight Start-->
<div class="blockRight" style="width:78%">
<form id="listForm" name="listForm" method="post" onsubmit="return false;">
    <spring:bind path="searchCondition.topFormParentId">
    <input type="hidden" name="${status.expression}" value="${status.value}" />
    </spring:bind>
    <spring:bind path="searchCondition.formParentId">
    <input type="hidden" name="${status.expression}" value="${status.value}" />
    </spring:bind>
    <spring:bind path="searchCondition.formId">
    <input type="hidden" name="${status.expression}" value="" />
    </spring:bind>
    <spring:bind path="searchCondition.actionType">
    <input type="hidden" name="${status.expression}" value="${status.value}" />
    </spring:bind>
    <spring:bind path="searchCondition.sortColumn">
    <input type="hidden" name="${status.expression}" value="${status.value}" />
    </spring:bind>
    <spring:bind path="searchCondition.sortType">
    <input type="hidden" name="${status.expression}" value="${status.value}" />
    </spring:bind>
    <spring:bind path="searchCondition.linkType">
    <input type="hidden" name="${status.expression}" value="${status.value}" />
    </spring:bind>
	<!--subTitle_2 Start-->
	<div class="subTitle_2">
		<h3><span id="categoryNameSpan">${searchCondition.formParentName}</span> <ikep4j:message pre='${preHeader}' key='title.list.sub'/></h3>
	</div>
	<!--//subTitle_2 End-->	
    <div id="formListDiv">
    <spring:bind path="searchCondition.searchWord">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="searchCondition.pageIndex">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <spring:bind path="searchCondition.pagePerRecord">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    </div>
</form>
</div>

<!--//blockLight End-->
