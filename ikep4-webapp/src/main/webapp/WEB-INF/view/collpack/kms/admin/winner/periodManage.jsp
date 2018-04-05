<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  		value="message.collpack.kms.admin.winner.regist" /> 
<c:set var="preButton"  		value="message.collpack.kms.admin.winner.button" />
<c:set var="preList"  			value="message.collpack.kms.admin.winner.list" />
<c:set var="preMessage"  		value="message.collpack.kms.admin.winner.message" />
<c:set var="preCondition"    	value="message.collpack.kms.admin.winner.condition" />



 
<%-- 메시지 관련 Prefix 선언 End --%>  

<c:set var="user" value="${sessionScope['ikep.user']}" /> 
 
<script type="text/javascript" src="<c:url value='/base/js/zeroclipboard/ZeroClipboard.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/jquery/jquery.layout-latest.js'/>"></script>
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
	            $addHtml.find('input[name=winnerId]').val(data[index].id);
	            
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
			var addMark = "";
			var addUserName = "";
			var addConversionMark = "";
			
						  
			if($addFullHtml == ""){
				addSort = $("#searchUserPermForm").find('input[name=sort]').val();
				addMark = $("#searchUserPermForm").find('input[name=mark]').val();
				addUserName = $("#searchUserPermForm").find('input[name=userName]').val();
				addConversionMark = $("#searchUserPermForm").find('input[name=conversionMark]').val();
				
				
			}else{				
				addSort = $addFullHtml.find('input[name=sort]:last').val();
				addMark = $addFullHtml.find('input[name=mark]:last').val();
				addUserName = $addFullHtml.find('input[name=userName]:last').val();
				addConversionMark = $addFullHtml.find('input[name=conversionMark]:last').val();				
			}
			
			
			if(addSort == "" ||addMark == "" || addMark == "" || addMark == ""){
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
               +"<table>"
                   + "<caption></caption>"
                   + "<colgroup>"
					+ "<col width=\"8%\"/>		"
                    + "<col width=\"22%\"/>     "
                    + "<col width=\"8%\"/>      "
                    + "<col width=\"14%\"/>     "
                    + "<col width=\"8%\"/>      "
                    + "<col width=\"14%\"/>     "
                    + "<col width=\"8%\"/>      "
                    + "<col width=\"14%\"/>     "
                    + "<col width=\"5%\"/>      "
                   	+ "</colgroup>"
               		+ "	<tbody>																																																		"
                   + "       <tr>                                                                                                                                                                                                  "
                   + "           <th scope=\"row\"><ikep4j:message pre='${preList}' key='targetTime' /></th>                                                                                                                       "
                   + "           <td>                                                                                                                                                                                              "
                   + "                                                                                                                                                                                                             "
                   + "           <spring:bind path='adminWinner.winYear'>                                                                                                                                                          "
                   + "           	<select title='${status.expression}' name='${status.expression}'>                                                                                                                               "
               	+ "                	<c:forEach var='year' begin='2012' end='${nowYear}' step='1'>                                                                                                                                     "
               	+ "                        <option value='${year}' <c:if test='${year eq nowYear}'>selected=\"selected\"</c:if>>${year}</option>                                                                                                                                             "
               	+ "                    </c:forEach>                                                                                                                                                                             "
                   + "               </select>                                                                                                                                                                                     "
                   + "           </spring:bind>                                                                                                                                                                                    "
                   + "                                                                                                                                                                                                             "
                   + "           <spring:bind path='adminWinner.winGb'>                                                                                                                                                            "
                   + "               <select title='${status.expression}' name='${status.expression}'>                                                                                                                             "
               	+ "                    <c:forEach var='month' begin='1' end='12' step='1'>                                                                                                                                      "
               	+ "                        <option value='${month}' <c:if test='${month eq nowMonth}'>selected=\"selected\"</c:if>>${month}</option>                                                                                                                                           "
               	+ "                    </c:forEach>                                                                                                                                                                             "
                   + "               </select>                                                                                                                                                                                     "
                   + "           </spring:bind>                                                                                                                                                                                    "
                   + "                                                                                                                                                                                                             "
                   + "           </td>                                                                                                                                                                                             "
                   + "           <th scope=\"row\"><ikep4j:message pre='${preList}' key='isMonth' /></th>                                                                                                                          "
                   + "           <td>                                                                                                                                                                                              "
                   + "           	<spring:bind path='adminWinner.isMonth'>                                                                                                                                                        "
               	+ "                	<select title=${status.expression} name='${status.expression}'>                                                                                                                             "
               	+ "                        <option value=\"0\"><ikep4j:message key='message.collpack.kms.admin.winner.condition.month' /></option>                                                                              "
               	+ "                        <option value=\"1\"><ikep4j:message key='message.collpack.kms.admin.winner.condition.quarter' /></option>                                                                            "
               	+ "                        <option value=\"2\"><ikep4j:message key='message.collpack.kms.admin.winner.condition.year' /></option>                                                                               "
               	+ "                    </select>                                                                                                                                                                                "
                   + "   				                </spring:bind>                                                                                                                                                              "
                   + "                                             </td>                                                                                                                                                           "
                   + "                                             <th scope=\"row\"><ikep4j:message pre='${preList}' key='sort' /></th>                                                                                           "
                   + "                                             <td>                                                                                                                                                            "
					+ "													<spring:bind path='adminWinner.sort'>											"
                    + "														<select title='${status.expression}' name='${status.expression}'>           "
                    + "														<c:forEach var='index' begin='1' end='10' step='1'>                     "
                    + "															<option value='${index}'>${index}</option>                          "
                    + "														</c:forEach>                                                            "
                    + "														</select>                                                                   "
                    + "													</spring:bind>                                                                  "
                   + "                                             </td>                                                                                                                                                           "
                   + "                                             <th scope=\"row\"><ikep4j:message pre='${preList}' key='mark' /></th>                                                                                           "
                   + "                                             <td>                                                                                                                                                            "
                   + "												<spring:bind path='adminWinner.mark'> "                   
                   + "                                             	<input id=${status.expression} name=${status.expression} title=\"mark\" class=\"inputbox\" type=\"text\" size=\"10\" maxlength=\"2\"/>                                                             "
                   + "												</spring:bind>"
                   + "                                             </td>                                                                                                                                                           "
                   + "                                             <td class=\"textCenter\"><a href=\"#a\" id=\"addButton\"><img src=\"<c:url value='/base/images/icon/ic_btn_plus.gif'/>\" alt=\"\" /></a></td>                   "
                   + "                                         </tr>                                                                                                                                                               "
                   + "                                         <tr>                                                                                                                                                                "
                   + "                                         	<th scope=\"row\"><ikep4j:message pre='${preList}' key='name' /></th>                                                                                           "
                   + "                                             <td>                                                                                                                                                            "
                   + "                                             	<input name=\"userName\" title=\"\" class=\"inputbox\" type=\"text\" size=\"8\" readonly/>                                                                          "
                   + "                                                 <a class=\"button_s\" id=\"addPersonButton\" href=\"#a\"><span>Search</span></a>                                                                                                     "
                   + "                                             </td>                                                                                                                                                           "
                   + "                                             <th scope=\"row\"><ikep4j:message pre='${preList}' key='teamName' /></th>                                                                                             "
                   + "                                             <td colspan=\"3\">                                                                                                                                                            "
                   + "                                             	<input name=\"userInfo\" title=\"\" class=\"inputbox\" type=\"text\" size=\"30\" readonly/>                                                                                 "
                   + "                                             	<input name=\"winnerId\" title=\"\" class=\"inputbox\" type=\"hidden\" value=\"\"/>                                                                                 "
                   + "                                             </td>                                                                                                                                                           "
                   + "                                             <th scope=\"row\"><ikep4j:message pre='${preList}' key='conversionMark' /></th>                                                                                 "
                   + "                                             <td>                                                                                                                                                            "
                   + "												<spring:bind path='adminWinner.conversionMark'> "					
                   + "                                             	<input id=${status.expression} name=${status.expression} class=\"inputbox\" type=\"text\" size=\"10\" maxlength=\"4\"/>                                                                     "
                   + "												</spring:bind>		"
                   + "                                             </td>                                                                                                                                                           "
                   + "                                             <td class=\"textCenter\"><a href=\"#a\" id=\"delButton\"><img src=\"<c:url value='/base/images/icon/ic_btn_minus.gif'/>\" alt=\"\" /></a></td>                  "
                   + "                                         </tr>                                                                                                                                                               "
                   + "                                     </tbody>                                                                                                                                                                "
                   + "     </table>                                                                                                                                 																"
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
<form id="searchUserPermForm" method="post" onsubmit="return false" action="<c:url value='/collpack/kms/admin/winner/saveWinners.do'/>">
<!--mainContents Start-->	 
<!--pageTitle Start-->
	
                <!--tableTop Start-->
				<div class="tableTop">
					<h2><ikep4j:message pre="${preCondition}" key="maintitle" /></h2>
					<div class="tableTop_bgR"></div>
				</div>
				<!--//tableTop End-->		
				<div class="blockSearch">
                	<div class="corner_RoundBox03">
						<table summary="tableSearch">
						<tbody>
							<tr>
								<th scope="row" width="15%"><ikep4j:message pre="${preCondition}" key="title" /></th>
		                         <td width="85%">	
		                         	<label><input type="radio" name="isMonthDisplay" value="0" <c:if test="${isMonthDisplay eq '0'}">checked</c:if>/><ikep4j:message pre='${preCondition}' key='month' /></label>
		                         	<label><input type="radio" name="isMonthDisplay" value="1" <c:if test="${isMonthDisplay eq '1'}">checked</c:if>/><ikep4j:message pre='${preCondition}' key='quarter' /></label>
		                         	<label><input type="radio" name="isMonthDisplay" value="2" <c:if test="${isMonthDisplay eq '2'}">checked</c:if>/><ikep4j:message pre='${preCondition}' key='year' /></label>
		                         </td>				
		                     </tr>														
		                 </tbody>
		             	</table>
		             	<div class="displayBtn"><a id="displayButton" name="displayButton" href="#a"><span>Display</span></a></div> 
		             </div>
				</div>     	
				   
             </div>	
             
				<!--//splitterBox End-->
			</div>
				<!--//mainContents End-->
			<div class="clear"></div>
		</div>

</form>