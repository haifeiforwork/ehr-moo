<%--
=====================================================
* 기능 설명 : item - multirow
* 작성자    : wonchu
=====================================================
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preIfm"         value="ui.approval.apprForm.iFM" />    
<c:set var="preView"        value="ui.approval.apprForm.view" />    
<c:set var="preList"        value="ui.approval.apprForm.list" />    
<c:set var="preButton"      value="ui.approval.common.button" />
<c:set var="preIfmMessage"  value="message.approval.apprForm.iFM" />
<c:set var="preViewMessage" value="message.approval.apprForm.view" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value="/base/js/units/approval/admin/apprForm.js"/>"></script>
<script  type="text/javascript">

<!--// 

	(function($) {
		
		var dialogWindow;    
		
		
		//- item JSON
        var item = {  
                      "title"      : "",
                      "count"      : "",
                      "width"      : ""
                    };
        
        
		//- onload시 수행할 코드
        $(document).ready(function() {   
            
            //- validation  
            var validOptions = {
                rules  : {
                    count : {
                        required : true,
                        digits   : true
                    }
                },
                messages : {
                    count : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>",
                        digits : "<ikep4j:message pre='${preIfmMessage}' key='digits'/>"
                    }
                },    
                notice : {
                },      
    	        submitHandler : function(form) {
    	            item.title    = $("#itemForm input[name=title]").val();
                    item.count    = $("#itemForm input[name=count]").val();
                    item.width    = $("#itemForm input[name=width]").val();
                    
                    dialogWindow.callback(item);
                    dialogWindow.close();
                }
    		};
    
           //- caller  
           fnCaller = function (params, dialog) {
				dialogWindow = dialog;
			    $("#itemForm input[name=title]").val(params.title);
			    $("#itemForm input[name=count]").val(params.count);
			    $("#itemForm input[name=width]").val(params.width);
			};
			
		    //- 버튼영역 실행  
			$("#itemFormButton a").click(function(){
			    switch (this.id) {
                    case "saveButton":
                        $("#itemForm").trigger("submit");
        			    break;
                    case "closeButton":
                        dialogWindow.close();
                        break;
	                default:
	                    break;
	            }   
            });
            
            new iKEP.Validator("#itemForm", validOptions);
        });
        
	})(jQuery);  

//-->
</script>

<h1 class="none">contnet area</h1>
<div id="guideConFrame">
	<div class="blockBlank_10px"></div>
	
	<!--subTitle_2 Start-->
	<div class="subTitle_2 noline">
		<h3><ikep4j:message pre='${preView}' key='basicInfo'/></h3>
	</div>
	<!--//subTitle_2 End-->
	
	<!--blockDetail Start-->
	<div class="Approval_1">
    	<form id="itemForm" name="itemForm" method="post">
    		<table summary="<ikep4j:message pre='${preView}' key='basicInfo'/>">
    			<caption></caption>
    			<colgroup>
					<col width="15%" />
					<col width="85%" />
				</colgroup>		
    			<tbody>
    				<tr>
    					<th width="15%" scope="row"><ikep4j:message pre='${preIfm}' key='title'/></th>
    					<td width="85%">
    						<input name="title" value="" type="text" class="inputbox w100" title="<ikep4j:message pre='${preIfm}' key='title'/>" />
    					</td>
    	            </tr>
    	            <tr>		
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='count'/></th>
    					<td>
    						<input type="text" name="count" class="inputbox" maxlength="2" size="13" value="1" title="<ikep4j:message pre='${preIfm}' key='count'/>">
    					</td>
    		        </tr>
    		        <tr>		
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='width'/></th>
    					<td>
    						<input type="text" name="width" class="inputbox" size="13" value="" title="<ikep4j:message pre='${preIfm}' key='width'/>">&nbsp;&nbsp;ex) <ikep4j:message pre='${preIfm}' key='widthEx'/> = 30,30,30,10
    					</td>
    		        </tr>
    			</tbody>
    		</table>
    	</form>
	</div>
	<!--//blockDetail End-->
	
	<!--blockButton Start-->
	<div id="itemFormButton" class="blockButton"> 
		<ul>
			<li><a id="saveButton"  class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			<li><a id="closeButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='close'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
</div>