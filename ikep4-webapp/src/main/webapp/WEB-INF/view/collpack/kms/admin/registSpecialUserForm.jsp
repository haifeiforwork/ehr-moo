<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  		value="message.collpack.kms.admin.winner.regist.people" /> 
<c:set var="preButton"  		value="message.collpack.kms.admin.winner.button" />
<c:set var="preList"  			value="message.collpack.kms.admin.winner.list" />
<c:set var="preMessage"  		value="message.collpack.kms.admin.winner.message" />
<c:set var="preCondition"    	value="message.collpack.kms.admin.winner.condition" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/kms/kms.css"/>" />


 
<%-- 메시지 관련 Prefix 선언 End --%>  

<c:set var="user" value="${sessionScope['ikep.user']}" /> 
 
<script type="text/javascript" src="<c:url value='/base/js/zeroclipboard/ZeroClipboard.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/jquery/jquery.layout-latest.js'/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/collpack/kms/color_edit.js"/>"></script>
<script type="text/javascript">
<!--   
var bbsIframe;  	 // 부모 iframe
var bbsIsIframe = 0; // iframe 존재 여부
var isLayout; // 레이아웃 보기 여부
var bbsLayout = null;
var layoutType = "n"; // n:없음, v:가로보기, h:세로보기


(function($){	 
	/* window risize 시 Contaner 높이 조절 */
	resizeContanerHeight = function(){
		var docHeight = 0;
		var adjustHeight = 20;		
		var $lefMenu;
		var $Container	= $('#container');
		
		if(isLayout){
			// iframe 안에서 호출된 경우
			if(bbsIsIframe>0){
				docHeight = $(parent).height();
				
				$lefMenu = $("#leftMenu", parent.document);
				var leftMenuPostion = $lefMenu.offset();
				if($lefMenu.length > 0) {
					$lefMenu.height(docHeight - leftMenuPostion.top);
				}
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( docHeight - $Container.offset().top - adjustHeight );
				// 부모 iframe 높이 조절 - 부모 윈도우 높이로 조절
				bbsIframe.height(docHeight-17);				
			}else{
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( $(window).height() - $Container.offset().top - adjustHeight );
			}	
		}
		
		if(bbsLayout != null){
			bbsLayout.resizeAll();	
		} 
	}
	
	/* Contaner & iframe 높이 조절 */
	setContanerHeight = function() {
		var docHeight = 0;
		var adjustHeight = 20;
		var $lefMenu;
		var $Container	= $('#container');
		
		// layout 존재하므로 layout destroy 함수 호출시
		if(isLayout){
			// iframe 안에서 호출된 경우
			if(bbsIsIframe>0){
				docHeight = $(parent).height();
				$lefMenu = $("#leftMenu", parent.document);
				var leftMenuPostion = $lefMenu.offset();
				if($lefMenu.length > 0) {
					$lefMenu.height(docHeight - leftMenuPostion.top)
					.css({overflowY:"auto",overflowX:"hidden"});
				}
				
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( docHeight - $Container.offset().top - adjustHeight );
				// 부모 iframe 높이 조절 - 부모 윈도우 높이로 조절
				bbsIframe.height(docHeight-19);
			}else{
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( $(window).height() - $Container.offset().top - adjustHeight );
			}			
			
		}else{ // layout 없으므로 layout 함수 호출시
			// iframe 안에서 호출된 경우
			if(bbsIsIframe>0){
				$lefMenu = $("#leftMenu", parent.document);
				$lefMenu.css({overflowY:"",overflowX:""});
				bbsIframe.height($(document).height());
			}
		
		}
		
		if(bbsLayout != null){
			bbsLayout.resizeAll();	
		}
		
	}
	
	
	$(document).ready(function() { 
		
		if("${param.error}" == "Y"){
			
			alert("<ikep4j:message pre='${preMessage}' key='saveError' />");
		}
		
		var $addHtml  = ""; 
		var $addFullHtml = "";		
		
		setUser = function(data) {
			
			$jq(data).each(function(index) {

	            $addHtml.find('input[name=userName]').val(data[index].userName);
	            $addHtml.find('input[name=userInfo]').val(data[index].id +" " + data[index].teamName + " " + data[index].jobTitleName);
	            $addHtml.find('input[name=userId]').val(data[index].id);
	            
			});	
		}
		
		
		$jq('#addPersonButton').live("click", function(event) {		
			event.which = "13";			
			//$addHtml = $(this).parent().parent();
			$addHtml = $(this).parent().parent().parent().parent();
			iKEP.searchUser(event, "N", setUser);
		});		
		
				
		//입력값 확인
		$("#searchUserPermForm").validate({ 
			submitHandler: function(form) {                  
                  form.submit();
                  return true;
            },
            rules : {
    			sort : {
    				required : true,
    				min : 1,
    				digits : true
    			},
    			
    			mark : {
    				required : true,
    				min : 1,
    				number : true
    			},
    			
    			userName : {
    				required : true
    			},
    			
    			conversionMark : {
    				required : true,
    				min : 1,
    				number : true
    			},
    			
    			regCnt : {
    				required : true,
    				min : 1,
    				number : true
    			}
    		},
    		
    		messages : {
    			
    			sort : {
    				required : "<ikep4j:message pre='${preMessage}' key='required' />",
    				min : "<ikep4j:message pre='${preMessage}' key='min' />",
    				digits : "<ikep4j:message pre='${preMessage}' key='digit' />"
    			},
    			
    			mark : {
    				required : "<ikep4j:message pre='${preMessage}' key='required' />",
    				min : "<ikep4j:message pre='${preMessage}' key='min' />",
    				number : "<ikep4j:message pre='${preMessage}' key='digit' />"
    			},
    			
    			userName : {
    				required : "<ikep4j:message pre='${preMessage}' key='required' />"
    			},
    			
    			conversionMark : {
    				required : "<ikep4j:message pre='${preMessage}' key='required' />",
    				min : "<ikep4j:message pre='${preMessage}' key='min' />",
    				number : "<ikep4j:message pre='${preMessage}' key='digit' />"
    			},
    			
    			regCnt : {
    				required : "<ikep4j:message pre='${preMessage}' key='required' />",
    				min : "<ikep4j:message pre='${preMessage}' key='min' />",
    				number : "<ikep4j:message pre='${preMessage}' key='digit' />"
    			}
    		}
		}); 
		
		
		$("#saveButton").click(function() {   
			$("#searchUserPermForm").submit(); 
			return false; 
		});
		
		
		var rows = $("#infoWinner_").length+1;	
		
		  
		$("#delButton").live("click", function(){
			
			if(rows > 1){
				var clickedRow = $(this).parent().parent().parent().parent().parent();
				clickedRow.remove();
				rows--;
			}
				
		});
		
		$("#addButton").live("click", function() { 
			
			
			var addSort = "";
			var addUserName = "";
			
						  
			if($addFullHtml == ""){
				addSort = $("#searchUserPermForm").find('input[name=sort]').val();
				addUserName = $("#searchUserPermForm").find('input[name=userName]').val();
				
				
			}else{				
				addSort = $addFullHtml.find('input[name=sort]:last').val();
				addUserName = $addFullHtml.find('input[name=userName]:last').val();
			}
			
			
			if(addSort == ""){
				alert("<ikep4j:message pre='${preMessage}' key='required' />");
				return false;
			}			
			
			++rows;			
			if(rows > 6){
				alert("<ikep4j:message pre='${preMessage}' key='maxRegist' />");
				return false;
			}
			
			$addFullHtml = $("#infoStart").append					
			("<div class=\"blockDetail\" id='infoWinner_'" + rows+">"
                   + " </div>                                                                                                                                                                       								"
			
			);			
		});
		
		
		
		$("#displayButton").click(function() { 
			
			$("#searchUserPermForm").ajaxLoadStart();
			
			$.post("<c:url value='/collpack/kms/admin/winner/displayWinner.do'/>", {"isMonthDisplay" : $("#searchUserPermForm input:radio[name=isMonthDisplay]:checked").val()}) 
			.success(function(data) {  
				alert("<ikep4j:message pre="message.collpack.kms.admin.permission.user.popup.myCnt" key="modify" />");
				$("#searchUserPermForm").ajaxLoadComplete();
				//$("#searchUserPermButton").click();
			})		
			 
		});
		
		$("#searchUserPermForm select[name=pagePerRecord]").change(function() { 
			$("#searchUserPermForm input[name=actionType]").val("pagePerRecord"); 
			$("#searchUserPermForm").submit(); 
			return false;
		});  
		
		/* iframe 구성여부 확인 */
		bbsIframe = $(parent.document).find("iframe[name=contentIframe]");
		bbsIsIframe = bbsIframe.length;
		
		/* 기본 layout 설정 여부 */ 
		isLayout = false;	
		layoutType = "n";		
		
		/* 윈도우 resize 이벤트 */
		$(window).bind("resize", resizeContanerHeight);			
	});
	
	
})(jQuery);
//-->
</script>

<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="searchUserPermForm" method="post" onsubmit="return false" action="<c:url value='/collpack/kms/admin/registSpecialUser.do'/>">
<!--mainContents Start-->	 
<!--pageTitle Start-->
<input id="categoryColor" type="hidden" value="${adminPermission.color}"/>	
                <!--tableTop Start-->
				<div class="tableTop">
					<h2><ikep4j:message pre='${preList}' key='specialUserTitle' /></h2>
					<div class="tableTop_bgR"></div>
				</div>
				   
				<!--blockDetail Start-->
				<div id="infoStart">
                <div class="blockDetail">
                    <table id="infoWinner">
                        <caption></caption>
                        <colgroup>
                        	<col width="8%"/>
                            <col width="17%"/>
                            <col width="8%"/>
                            <col width="15%"/>
                            <col width="8%"/>
                            <col width="20%"/>
                            <col width="8%"/>
                            <col width="*"/>
                        </colgroup>
                        <tbody>
                            <tr>
                            	<th scope="row"><ikep4j:message pre='${preList}' key='name' /></th>
                                <td>
                                	<input name="userName" id="userName" class="inputbox" type="text" size="8" value="${adminPermission.userName}" readonly/>
                                    <a class="button_s" id="addPersonButton" href="#a"><span>Search</span></a>
                                </td>
                                <th scope="row"><ikep4j:message pre='${preList}' key='teamName' /></th>
                                <td>
                                <input name="userInfo" value="${adminPermission.teamName} ${adminPermission.jobDutyName}" size="10" readonly/>
                                <input name="userId" value="${adminPermission.userId}" type="hidden"/>
                                </td>
                                <th scope="row">color</th>
                                <td>
                                <select id="color_pane" name="color" value="${adminPermission.color}"></select>
								<span id="sample_color" class="cal_color_box_sample1"></span>
                                </td>
                                 <th scope="row">category</th>
                                 <td>
                                 <select id="categoryId" name="categoryId" value="${adminPermission.categoryId}">
                                 	<option value="1"><ikep4j:message pre='${preList}' key='category1' /></option>
                                 	<option value="2"><ikep4j:message pre='${preList}' key='category2' /></option>
                                 </select>
                                 </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                </div>
                <!--//blockDetail End-->
		
		 		<!--blockButton Start-->
                <div class="blockButton"> 
                    <ul>
                        <li><a class="button" id="saveButton" href="#a"><span><ikep4j:message pre='${preButton}' key='apply' /></span></a></li>
                        <li><a id="listTeamButton" class="button" href="<c:url value='/collpack/kms/admin/listSpecialUser.do'/>"><span><ikep4j:message key='message.collpack.kms.admin.permission.button.list' /></span></a></li>
                    </ul>
                </div>
                		
             </div>	
             
				<!--//splitterBox End-->
			</div>
				<!--//mainContents End-->
			<div class="clear"></div>
		</div>

</form>