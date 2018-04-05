<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%>  
<c:set var="preList"    		value="message.collpack.kms.admin.winner.assess" />
<c:set var="preMessage"    		value="message.collpack.kms.admin.winner.message" />

 
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
		
		if("${param.gubun}" == "Y"){
			alert("<ikep4j:message key='message.collpack.kms.admin.permission.user.popup.myCnt.modify' />");
		}
		
		$("#markRate").keyup( function(){
			sumRate($("#markRate").val());
		});
		
		$("#regCntRate").keyup( function(){
			sumRate($("#regCntRate").val());
		});
		
		$("#viewCntRate").keyup( function(){			
			sumRate($("#viewCntRate").val());
		});
		
		$("#recommendCntRate").keyup( function(){			
			sumRate($("#recommendCntRate").val());
		});
		
		$("#lineReplyCntRate").keyup( function(){			
			sumRate($("#lineReplyCntRate").val());
		});
		
		$("#usageCntRate").keyup( function(){			
			sumRate($("#usageCntRate").val());
		});		
		
		
		sumRate = function(rate, init){
			
			var total = checkValue($("#total").val());		
			var markRate = checkValue($("#markRate").val());
			var regCntRate = checkValue($("#regCntRate").val());
			var viewCntRate = checkValue($("#viewCntRate").val());
			var recommendCntRate = checkValue($("#recommendCntRate").val());
			var lineReplyCntRate = checkValue($("#lineReplyCntRate").val());
			var usageCntRate = checkValue($("#usageCntRate").val());
			
			//alert("sumRate");
			if( !isNaN(parseFloat(rate)) && isFinite(rate) ){				
				total = parseFloat(markRate) + parseFloat(regCntRate) + parseFloat(viewCntRate) + parseFloat(recommendCntRate) +  parseFloat(lineReplyCntRate) + parseFloat(usageCntRate);
				
				$("#totalRate").val(total);
			}
		}
		
		checkValue = function(data){
			return (data == null ? 0 : data);
			
		}
		
		//입력값 확인
		$("#searchUserPermForm").validate({ 
			submitHandler: function(form) {                  
                  form.submit();
                  return true;
            },
            rules : {
    			markRate : {
    				required : true,
    				range : [0, 100],
    				digits : true
    			},
    			
    			regCntRate : {
    				required : true,
    				range : [0, 100],
    				digits : true
    			},
    			
    			viewCntRate : {
    				required : true,
    				range : [0, 100],
    				digits : true
    			},
    			
    			recommendCntRate : {
    				required : true,
    				range : [0, 100],
    				digits : true
    			},
    			
    			lineReplyCntRate : {
    				required : true,
    				range : [0, 100],
    				digits : true
    			},
    			
    			usageCntRate : {
    				required : true,
    				range : [0, 100],
    				digits : true
    			}, 
    			
    			totalRate : {
    				required : true,
    				min : 100,
    				max : 100
    			}
    			
    		},
    		
    		messages : {
    			
    			markRate : {
    				required : "<ikep4j:message pre='${preMessage}' key='required' />",
    				range : "<ikep4j:message pre='${preMessage}' key='min' />",
    				digits : "<ikep4j:message pre='${preMessage}' key='digit' />"
    			},
    			regCntRate : {
    				required : "<ikep4j:message pre='${preMessage}' key='required' />",
    				range : "<ikep4j:message pre='${preMessage}' key='min' />",
    				digits : "<ikep4j:message pre='${preMessage}' key='digit' />"
    			},
    			viewCntRate : {
    				required : "<ikep4j:message pre='${preMessage}' key='required' />",
    				range : "<ikep4j:message pre='${preMessage}' key='min' />",
    				digits : "<ikep4j:message pre='${preMessage}' key='digit' />"
    			},
    			recommendCntRate : {
    				required : "<ikep4j:message pre='${preMessage}' key='required' />",
    				range : "<ikep4j:message pre='${preMessage}' key='min' />",
    				digits : "<ikep4j:message pre='${preMessage}' key='digit' />"
    			},
    			lineReplyCntRate : {
    				required : "<ikep4j:message pre='${preMessage}' key='required' />",
    				range : "<ikep4j:message pre='${preMessage}' key='min' />",
    				digits : "<ikep4j:message pre='${preMessage}' key='digit' />"
    			},
    			usageCntRate : {
    				required : "<ikep4j:message pre='${preMessage}' key='required' />",
    				range : "<ikep4j:message pre='${preMessage}' key='min' />",
    				digits : "<ikep4j:message pre='${preMessage}' key='digit' />"
    			},
    			totalRate : {
    				required : "<ikep4j:message pre='${preMessage}' key='required' />",
    				min : "<ikep4j:message pre='${preMessage}' key='total' />",
    				max : "<ikep4j:message pre='${preMessage}' key='total' />"
    			}
    		}
		}); 
		
		
		$("#saveAssessButton").click(function() {   
			$("#searchUserPermForm").submit(); 
			return false; 
		});
		
	});
	
	
})(jQuery);
//-->
</script>
<div id="tagResult">
<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="searchUserPermForm" method="post" action="<c:url value='/collpack/kms/admin/winner/saveAssessStandard.do' />">
<input name="isKnowHow" id="isKnowHow" type="hidden" value="${assess.isKnowHow}" />
<!--mainContents Start-->
	
        <!--tableTop Start-->
		<div class="tableTop">
			<div class="tableTop_bgR"></div>
			<h2><ikep4j:message pre="${preList}" key="title" /></h2>			
		</div>
		<!--//tableTop End-->
              
              <!--directive Start-->
				<div class="directive"> 
					<ul>
						<li><span><ikep4j:message pre="${preList}" key="comment1" /> = <br /><ikep4j:message pre="${preList}" key="comment2" /></span></li>												
					</ul>
				</div>
                <div class="mb10"></div>
				<!--//directive End-->
              
              <!--blockDetail Start-->
              <div class="blockDetail">
                  <table summary="new group">
                      <tbody>
                          <tr>
                              <th scope="col" class="textCenter" width="14%"><ikep4j:message pre="${preList}" key="markRate" /></th>
                              <th scope="col" class="textCenter" width="14%"><ikep4j:message pre="${preList}" key="regCntRate" /></th>
                              <th scope="col" class="textCenter" width="14%"><ikep4j:message pre="${preList}" key="viewCntRate" /></th>
                              <th scope="col" class="textCenter" width="14%"><ikep4j:message pre="${preList}" key="recommendCntRate" /></th>
                              <th scope="col" class="textCenter" width="14%"><ikep4j:message pre="${preList}" key="lineReplyCntRate" /></th>
                              <th scope="col" class="textCenter" width="14%"><ikep4j:message pre="${preList}" key="usageCntRate" /></th>
                              <th scope="col" class="textCenter" width="16%"><ikep4j:message pre="${preList}" key="total" /></th>
                          </tr>
                          <tr>
                              <td class="textCenter"><input name="markRate"  id="markRate" class="inputbox" type="text" size="10" value="${assess.markRate}"  maxlength="3" /> %</td>
                              <td class="textCenter"><input name="regCntRate"  id="regCntRate" class="inputbox" type="text" size="10" value="${assess.regCntRate}" maxlength="3" /> %</td>
                              <td class="textCenter"><input name="viewCntRate" id="viewCntRate" class="inputbox" type="text" size="10" value="${assess.viewCntRate}" maxlength="3" /> %</td>
                              <td class="textCenter"><input name="recommendCntRate"  id="recommendCntRate" class="inputbox" type="text" size="10" value="${assess.recommendCntRate}" maxlength="3" /> %</td>
                              <td class="textCenter"><input name="lineReplyCntRate"  id="lineReplyCntRate" class="inputbox" type="text" size="10" value="${assess.lineReplyCntRate}" maxlength="3" /> %</td>
                              <td class="textCenter"><input name="usageCntRate" id="usageCntRate"  class="inputbox" type="text" size="10" value="${assess.usageCntRate}" maxlength="3" /> %</td>
                              <td class="textCenter"><input name="totalRate" id="totalRate" class="inputbox" type="text" size="10" value="${assess.totalRate}" maxlength="3" readonly/> %</td>
                          </tr>
                      </tbody>
                  </table>
      		</div>
              <!--//blockDetail End-->

                                          
    			<!--blockButton Start-->
              <div class="blockButton"> 
                  <ul>
                      <li><a class="button" id="saveAssessButton" href="#a"><span><ikep4j:message key="ui.support.popup.button.modify" /></span></a></li>
                  </ul>
              </div>
              <!--//blockButton End-->
			
  </div>			
		<!--//splitterBox End-->						
	
</form>
</div>

	