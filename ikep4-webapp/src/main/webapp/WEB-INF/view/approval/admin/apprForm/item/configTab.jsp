<%--
=====================================================
* 기능 설명 : item - config
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
        var item = {  "thColspan"   : "",
                      "required"    : ""
                    };
        
        
	    //- onload시 수행할 코드
        $(document).ready(function() {   
           
           //- caller  
           fnCaller = function (params, dialog) {
				dialogWindow = dialog;
        		if(params.thColspan) $("#itemForm input[name=thColspan]").val(params.thColspan);
        		if(params.required)  $("#itemForm input[name=required]:input[value=" + params.required + "]").attr("checked", true);
			};
			
    		//- save button  
            $("#saveButton").click(function(){
                
                var _thColspan = parseInt($("#itemForm input[name=thColspan]").val());
                if(_thColspan=="") _thColspan="4";
                item.thColspan = _thColspan;
                item.required  = $("#itemForm input[name=required]:checked").val();
                 
                dialogWindow.callback(item);
                dialogWindow.close();
            });
            
            //- close button  
            $("#closeButton").click(function(){
                dialogWindow.close();
            });
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
					<col width="25%" />
					<col width="75%" />
				</colgroup>		
    			<tbody>
    				<tr>
    					<th width="25%" scope="row"><ikep4j:message pre='${preIfm}' key='thColspan'/></th>
    					<td width="75%">
    						<input name="thColspan" value="4" type="text" class="inputbox w20" title="colspan" /> <ikep4j:message pre='${preIfm}' key='defaultValue'/>(4)
    					</td>
    	            </tr>
    	            <tr>
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='showRequired'/></th>
    					<td>
    						<input type="radio" class="radio" name="required" value="1" checked="checked" title="<ikep4j:message pre='${preIfm}' key='yes'/>"> <ikep4j:message pre='${preIfm}' key='yes'/>
                            <input type="radio" class="radio" name="required" value="0" title="<ikep4j:message pre='${preIfm}' key='no'/>"> <ikep4j:message pre='${preIfm}' key='no'/>
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