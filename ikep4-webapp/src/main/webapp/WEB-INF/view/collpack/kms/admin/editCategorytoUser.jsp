<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preHeader"  value="ui.lightpack.board.boardAdmin.createBoardView" /> 
<c:set var="preDetail"  value="ui.lightpack.board.boardAdmin.createBoardView.detail" />
<c:set var="preMessage" value="message.lightpack.common.boardAdmin" />
<c:set var="preButton"  value="ui.lightpack.common.button" />   

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/approval/admin/apprForm.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/base/js/wceditor/css/editor.css'/>"/>
<script type="text/javascript" src="<c:url value='/base/js/units/approval/admin/apprForm.js'/>"></script>

<script  type="text/javascript">
<!--// 

	(function($) {
        
        $(document).ready(function() {   
		    
        	var $addHtml  = ""; 
        	var category = "";
        	var tmpCategoryId = "";
        	var tmpUserId = "";
        	var tmpCategoryuser = "";
			$jq("#saveButton").click(function() {  
				<c:forEach var="code" items="${categoryList}">
					tmpCategoryId = "${code.categoryId}";
					tmpUserId = $("#assessorId_"+tmpCategoryId).val();
					if($jq.trim(tmpUserId) == ""){
						alert("전문가를 설정하지 않았습니다.");
						return;
					}
					
					if(tmpCategoryuser == ""){
						tmpCategoryuser = tmpCategoryId+"_"+tmpUserId;
					}else{
						tmpCategoryuser = tmpCategoryuser+"^"+tmpCategoryId+"_"+tmpUserId;
					}
				</c:forEach>
				$("#categoryUsers").val(tmpCategoryuser);
				
				$.ajax({
					url : "<c:url value='/collpack/kms/admin/createCategoryUsers.do' />",
					data : $("#itemForm").serialize(),
					type : "post",
					dataType : "html",
					success : function(result) {
						if(result == 'success') {
							alert("<ikep4j:message pre='${preMessage}' key='sendMessage'/>");
							tmpCategoryuser = "";
						} else {
							alert('error');
						}
					},
					error : function() {alert('error');}
				});
	    	});  
			
			setUser = function(data) {
				$jq(data).each(function(index) {
					$("#assessor_"+category).val(data[index].userName +" " + data[index].jobTitleName + " " + data[index].teamName);
					$("#assessorId_"+category).val(data[index].id);
					$("#assessorName_"+category).val(data[index].userName);
				});	
			}
		    
			//$jq('#addPersonButton').live("click", function(event) {	
			addPersonButton = function(categoryid) {
				category = categoryid;
				
				//$addHtml = $(this).parent().parent();
				$addHtml = $(this).parent().parent().parent().parent();
				iKEP.searchUser("", "N", setUser);
			};
    		
        });
        
		
	})(jQuery);  

//-->
</script>

<!--popup Start-->

<div class="blockListTable">
	<!--tableTop Start-->  
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		<h2>전문가 관리</h2> 
		<div class="clear"></div>	
	</div>
	<!--//tableTop End-->
</div>
	<!--popup_contents Start-->
	
	<form id="itemForm" name="itemForm" method="post" action="" >	
		<input type="hidden" id="delIdList"      name="delIdList"/>
   		<input type="hidden" id="addNameList"    name="addNameList"/> 	
   		<input type="hidden" id="updateNameList" name="updateNameList"/> 	
   		<input type="hidden" id="updateIdList"   name="updateIdList"/> 	
   		<input type="hidden" id="alignList"   name="alignList"/> 	
   		<input type="hidden" id="categoryUsers" name="categoryUsers" value="" /> 
   		<!--blockDetail Start-->
		<div class="blockDetail">
			<table summary="Message" >
				<caption></caption>
				<colgroup>
					<col width="40%" />
					<col width="60%" />
				</colgroup>
				<tbody>   
					<c:forEach var="code" items="${categoryList}">
	    	      		<tr>
	    	      			<th style="text-align:center;">${code.categoryName}</th>
	    	      			<td>
	    	      				<input id="assessor_${code.categoryId}" name="assessor_${code.categoryId}" title="" class="inputbox w40" style="width:200px;" type="text" value="${code.userName} ${code.jobDutyName} ${code.teamName}" readonly/>
                               	<input id="assessorId_${code.categoryId}" name="assessorId_${code.categoryId}" value="${code.userId}" type="hidden"/>
                               	<input id="assessorName_${code.categoryId}" name="assessorName_${code.categoryId}" value="${code.userName}" type="hidden"/>
                                <a class="button_s" id="addButton" href="javascript:addPersonButton('${code.categoryId}');"><span>Search</span></a>
	    	      			</td>
	    	      		</tr>
    	      		</c:forEach>
    			</tbody>
			</table>			
			
		</div>
		<!--//blockDetail End-->	
																																			
		<div class="clear"></div>
		
		<!--blockButton Start-->
		<div id="itemFormButton"  class="blockButton"> 
		<ul>
			<li><a id="saveButton"  class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
		</ul>
		</div>
	</form>
	<!--//popup_contents End-->
	
			

<!--//popup End-->