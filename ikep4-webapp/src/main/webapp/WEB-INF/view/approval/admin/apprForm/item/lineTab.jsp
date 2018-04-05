<%--
=====================================================
* 기능 설명 : item - line
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
        var item = {  "type"        : "line",
                      "name"        : "",
                      "title"       : "",
                      "description" : "",
                      "height"      : "",
                    };
        
        
	    //- onload시 수행할 코드
        $(document).ready(function() {   
           
           //- caller  
           fnCaller = function (params, dialog) {
				dialogWindow = dialog;

        		//- edit item  
    			if(params.mode=="edit"){
    		        $("#itemForm input[name=title]").val(params.json.title);
    		        $("#itemForm input[name=description]").val(params.json.description);
    		        $("#itemForm input[name=height]").val(params.json.height);
    			}  
			};
			
    		//- save button  
            $("#saveButton").click(function(){
                
                var _height = parseInt($("#itemForm input[name=height]").val());
                if(_height=="") _height="30";
                
                item.title  = $("#itemForm input[name=title]").val();
                item.description  = $("#itemForm input[name=description]").val();
                item.height = _height;
                 
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
    					<th width="15%" scope="row"><ikep4j:message pre='${preIfm}' key='description'/></th>
    					<td width="85%">
    						<input name="description" value="" type="text" class="inputbox w100" title="<ikep4j:message pre='${preIfm}' key='description'/>" />
    					</td>
    	            </tr>   
    	            <tr>		
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='height'/></th>
    					<td>
    						<input type="text" name="height" class="inputbox wdt20" maxlength="3" maxlength="3" value="30" title="<ikep4j:message pre='${preIfm}' key='height'/>"> (px)
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