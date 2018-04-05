<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<script type="text/javascript">
<!-- 
var dialogWindow = null;
var fnCaller;

(function($){	 
	
	fnCaller = function (params, dialog) {
		if(params) {
		}

		dialogWindow = dialog;
		$("#cancelButton").click(function() {
			dialogWindow.close();
		});
	};
	
	$(document).ready(function() { 
		//window.parent.topScroll(); 
		$jq("#userDocTreeOfLeft").click();
		
		$("#adminUserDocTree").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
			$("#adminUserDocTree").jstree("open_all");
			 
			 //window.parent.resizeIframe();
		}).jstree({
		    plugins:["themes", "ui", "dnd", "crrm", "json_data"],   
		    "json_data" : {   
		    	 "data" : ${categoryTreeJson}
		    }       
		}).bind("open_node.jstree", function (event, data) {
			
		}).bind("close_node.jstree", function (event, data) {
			
		}); 
				
		/* 노드 클릭시 이벤트*/
	    $("#adminUserDocTree").delegate("a", "click", function () {
	    	<c:if test="${usePop eq 'Y'}">
	    	$("#folderId").val($(this).parent().attr("folderId"));
			$("#folderName").val($(this).parent().attr("folderName"));
	    	</c:if>
	    	
	    	<c:if test="${usePop ne 'Y'}">
	    	if(typeof($(this).parent().attr("folderId")) != "undefined"){
	    		$.ajax({    
	    			url : "<c:url value="/approval/work/userdoc/listApprUserDoc.do"/>?folderId="+$(this).parent().attr("folderId"),     
	    			data : {},     
	    			success : function(result) {
	    				$jq("#mainContents").empty();
	    				$jq("#mainContents").append(result);
	    			},
	    			error : function(event, request, settings){
	    			 	alert("error");
	    			}
	    		}) ;
	    	}
	    	</c:if>
	     }); 
		
	    $("#applyBtn").click(function() {   
			$("#folderForm").submit(); 
			return false; 
		});
	
		//입력값 확인
		var folderValidOptions = {
			submitHandler	: function(form) {
					$jq.ajax({     
						url : '<c:url value="/approval/work/userdoc/checkExist.do" />',     
						data :  $jq("#folderForm").serialize(),     
						async : false,
						success : function(result) {      
							if(!result) {
								if (!confirm($jq("#folderName").val()+' <ikep4j:message key="ui.approval.work.apprlist.confirmFolder" />')) return false;
				                  $jq.ajax({     
										url : '<c:url value="/approval/work/userdoc/createApprUserDoc.do" />',     
										data :  $jq("#folderForm").serialize(),     
										async : false,
										success : function(result) {      
											if(result == "OK") {
												 dialogWindow.callback(result);
								                 dialogWindow.close();
											}
										},
										error : function(event, request, settings){
											 alert("error");
										}
								  });
							}else {
								alert("<ikep4j:message key='ui.approval.work.apprlist.existDoc' />");
								return;
							}
						},
						error : function(event, request, settings){
							 alert("error");
						}
				  });
				
            }
		}; 
	    
		new iKEP.Validator("#folderForm", folderValidOptions);
		
	    
	}); 	 
	
	//parent.boardReload();

})(jQuery); 
//-->
</script>

<div id="adminUserDocTree"> 
</div>
<c:if test="${usePop eq 'Y'}">
<div class="blockButton"> 
	<ul>
		<li><a id="applyBtn"   class="button" href="#a"><span><ikep4j:message key='ui.approval.common.button.apply' /></span></a></li>
	</ul>
</div>
<form id="folderForm" method="post" action="">
	<input type="hidden" name="folderId" id="folderId" />
	<input type="hidden" name="folderName" id="folderName" />
	<input type="hidden" name="apprId" id="apprId" value="${apprId}"/>
</form>
</c:if>
<!--//blockLeft End-->



