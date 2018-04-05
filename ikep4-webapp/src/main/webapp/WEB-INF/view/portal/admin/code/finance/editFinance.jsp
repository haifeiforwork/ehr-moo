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
		    //- 버튼영역 실행  
		    
			$jq("#saveButton").click(function() {   
	    		
	    		$.ajax({
					url : "<c:url value='/portal/admin/code/finance/createFinance.do' />",
					data : $("#itemForm").serialize(),
					type : "post",
					dataType : "html",
					success : function(result) {
						if(result == 'success') {
							alert("<ikep4j:message pre='${preMessage}' key='sendMessage'/>");
						} else {
							alert('error');
						}
					},
					error : function() {alert('error');}
				});
	    	});  
		    
            
        });
        
        
		
	})(jQuery);  

//-->
</script>

<!--popup Start-->

<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>재무/손익 정보 기간 관리</h2>
	<div class="clear"></div>
</div>
	<!--popup_contents Start-->
	
	<form id="itemForm" name="itemForm" method="post" action="" onsubmit="return false;">	
   		<!--blockDetail Start-->
		<div class="blockDetail">
			<table>
				<caption></caption>
				<colgroup>
					<col width="15%" />
					<col width="85%" />
				</colgroup>
				<tbody>   
    	      		<tr>
    					<th>년도</th>
    					<td>
    						<input type="text" name="startYear" value="${startDt}" class="inputbox" />
    						~
    						<input type="text" name="endYear" value="${endDt}" class="inputbox" />
    					</td>
    			    </tr>
    			</tbody>
			</table>			
			
		</div>
		<!--//blockDetail End-->	
																																			
		
		
		<!--blockButton Start-->
		
	</form>
	<!--//popup_contents End-->
		<div class="blockButton"> 
		<ul>
			<li><a id="saveButton"  class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
		</ul>
		</div>

<!--//popup End-->