<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="preMessage" value="ui.approval.work.apprlist" />
 
<script type="text/javascript">
<!-- 
(function($){	 
	$(document).ready(function() { 
		//window.parent.topScroll(); 
		
		var oldFolderName = "";
		$jq("#apprUserDocLinkOfLeft").click();
		
		$("#adminUserDocTree").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
			$("#adminUserDocTree").jstree("open_all");
			 
			 //window.parent.resizeIframe();
		}).jstree({
		    plugins:["themes", "ui", "dnd", "crrm", "json_data", "contextmenu"],   
		    "json_data" : {   
		    	 "data" :[{  
	    		        "data" : "Category", 
	    				"state" : "closed",
	    				"attr" : {"id" : "treeRootItem", "dummy" : true, "boardId" : "", "boardRootId" : "0", boardType : 2},
		    	 		"children" : ${categoryTreeJson}
	    			  }]
		    },   
		    "contextmenu" : {  
				items : function($item){
					var menuItem = {
						"1-createFolder" : { label : "<ikep4j:message pre='${preMessage}' key='btnAdd'/>",  action : function(item) {
							$("#folderId").val("");
							$("#folderName").val("");
							$("#boardDetail").show();						
							$("#folderParentId").val($(item).attr("folderId"));
							$("#mode").val("create");
							
						}},
						"2-update" : { label : "<ikep4j:message pre='${preMessage}' key='btnEdit'/>",  action : function(item) { 
							$("#boardDetail").show();						
							$("#folderId").val($(item).attr("folderId"));
							oldFolderName = $(item).attr("folderName");
							$("#folderName").val($(item).attr("folderName"));
							$("#mode").val("update");
						}},
						"3-remove" : { label : "<ikep4j:message pre='${preMessage}' key='btnDelete'/>",  action : function(item) {   
							var tree = this;  
							 
							if(confirm("<ikep4j:message pre='${preMessage}' key='delete'/>")) {
								$.get("<c:url value='/approval/work/userdoc/deleteApprUserDocList.do?folderId='/>" + $(item).attr("folderId"))
								.success(function(data) {
									tree.remove(item); 
									$("#folderId").val("");
									$("#folderName").val("");
									$("#mode").val("");
									$("#boardDetail").hide();	
								})
								.error(function(event, request, settings) { alert("error"); }); 
							} 
							 
						}}, ccp : false/* 에디터 바꾸기 Context Menu 사용 안함 */, remove : false, create : false/* 생성 Context Menu 사용 안함 *//* 이름 바꾸기 Context Menu 사용 안함 */
					};
					 
					var folderId = $item.attr("folderId");
					var dummy   = $item.attr("dummy"); 
					var dept = $item.parents("li[class^=jstree]").length; 
					
					if(typeof($item.attr("folderId")) == "undefined") { 
						$.extend(menuItem, { 
							"2-update" : false,
							"3-remove" : false
						});
					}
					return menuItem;
				}
			}	        
		}).bind("open_node.jstree", function (event, data) {
			window.setTimeout("iKEP.iFrameContentResize()", 400);
			
		}).bind("close_node.jstree", function (event, data) {
			window.setTimeout("iKEP.iFrameContentResize()", 400);
			
		})/* 노드 이동 관련 콜백함수 바인드*/.bind("move_node.jstree", function(event, data){  
			var node          = data.rslt.o; 
		    var parentNode    = data.inst._get_parent(node);
		    var sortOrder     = data.rslt.cp;
		    var folderId       = $(node).attr("folderId");
		    var folderParentId = $(parentNode).attr("folderId");
		    
		    if(confirm("<ikep4j:message pre="${preMessage}" key="move" />")) { 
				$.post("<c:url value='/approval/work/userdoc/updateMoveBoard.do'/>",  {"folderId" : folderId, "folderParentId" : folderParentId, "sortOrder" : sortOrder})
				.success(function(data) {
					alert("<ikep4j:message pre='${preMessage}' key='move' post='complete'/>");  
					//parent.boardReload();
				})
				.error(function(event, request, settings) { 
					alert("error");
					$.jstree.rollback(data.rlbk); 	
				});  
			} else {
				$.jstree.rollback(data.rlbk); 
			}
		}); 
				
		/* 노드 클릭시 이벤트*/
	    $("#adminUserDocTree").delegate("a", "click", function () {
	    	if(typeof($(this).parent().attr("folderId")) != "undefined"){
	    		$("#boardDetail").show();						
				$("#folderId").val($(this).parent().attr("folderId"));
				$("#folderName").val($(this).parent().attr("folderName"));
	    	}
	     }); 
	    
		//폴더 저장
		$("#submitButton").click(function () {
			if(oldFolderName != $("folderName").val()) {
				 $jq.ajax({     
						url : '<c:url value="/approval/work/userdoc/existFolderName.do" />',     
						data :  $jq("#folderForm").serialize(),     
						async : false,
						success : function(result) {      
							if(!result) {
								$("#folderForm").attr("action","<c:url value='/approval/work/userdoc/createFolder.do'/>");
						    	$("#folderForm").submit();
							}else {
								alert("<ikep4j:message pre='${preMessage}' key='existFolder'/>");
								return;
							}
						},
						error : function(event, request, settings){
							 alert("error");
						}
				  });
			}
	    });
		
	    //iKEP.iFrameContentResize();
	}); 	 
	
	//parent.boardReload();

})(jQuery); 
//-->
</script>
<h1 class="none"></h1> 
<!--pageTitle Start-->
<div id="pageTitle"> 
	<h2>개인보관함 관리</h2> 
</div> 
<!--//pageTitle End-->		 
<!--blockLeft Start-->
<div class="blockLeft" style="width:25%;">	 
	<div class="leftTree" style="width:100%; padding:0;">
		<div id="adminUserDocTree"> 
		</div>
	</div>
</div>
<!--//blockLeft End-->

<!--blockRight Start-->
<div id="boardDetail" class="blockRight" style="width:73%;display:none;">
	<div class="blockDetail clear">
	<form id="folderForm" name="folderForm" method="post" action="">
	<spring:bind path="apprUserDocFolder.folderParentId">
		<input type="hidden" name="${status.expression}" id="${status.expression}" />
	</spring:bind>
	<spring:bind path="apprUserDocFolder.folderId">
		<input type="hidden" name="${status.expression}" id="${status.expression}" />
	</spring:bind>
	<input type="hidden" name="mode" id="mode" />
	<table summary="folder">
		<caption></caption>
		<colgroup>
			<col width="25%">
			<col width="75%">
		</colgroup>
		<tbody>
			<tr>
				<th scope="col">폴더명</th>
				<td class="testLeft">
					<spring:bind path="apprUserDocFolder.folderName">
						<input type="text" class="inputbox" name="${status.expression}" id="${status.expression}"/>
					</spring:bind>
				</td>
			</tr>
		</tbody>
	</table>
	</form>
	</div>
	<div class="blockButton"> 
		<ul>
			<li><a class="button" id="submitButton" href="#a"><span>저장</span></a></li>
		</ul>
	</div>	
			
</div>



