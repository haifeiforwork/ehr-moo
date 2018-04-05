<%--
=====================================================
* 기능 설명 : item - textarea
* 작성자    : wonchu
=====================================================
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<c:set var="user" value="${sessionScope['ikep.user']}" />  
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preIfm"         value="ui.approval.apprForm.iFM" />    
<c:set var="preView"        value="ui.approval.apprForm.view" />    
<c:set var="preList"        value="ui.approval.apprForm.list" />    
<c:set var="preButton"      value="ui.approval.common.button" />
<c:set var="preIfmMessage"  value="message.approval.apprForm.iFM" />
<c:set var="preViewMessage" value="message.approval.apprForm.view" />
<%-- 메시지 관련 Prefix 선언 End --%>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/js/wceditor/css/editor.css"/>"/>
<script type="text/javascript" src="<c:url value="/base/js/wceditor/js/editor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/approval/admin/apprForm.js"/>"></script>
<script  type="text/javascript">
<!--// 

	(function($) {
		
		
		//- item JSON
        var item = {  "type"        : "textarea",
                      "name"        : "",
                      "linked"      : "",
                      "merged"      : "",
                      "title"       : "",
                      "hideTitle"   : "",
                      "required"    : "",
                      "width"       : "",
                      "unit"        : "",
                      "height"      : "",
                      "template"    : "",
                      "description" : ""
                    };
                    
        $(document).ready(function() {   
            
		    //- validation 
    	    var validOptions = {
                rules  : {
                    width : {
                        required : true,
                        digits : true
                    },
                    
                    height : {
                        required : true,
                        digits : true
                    }
                    
                },
                messages : {
                    width : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='require'/>",
                        digits : "<ikep4j:message pre='${preViewMessage}' key='digits'/>"
                    },
                    
                    height : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='require'/>",
                        digits : "<ikep4j:message pre='${preViewMessage}' key='digits'/>"
                    }
                },    
                notice : {
                },      
    	        submitHandler : function(form) {
    	            item.linked        = $("#itemForm input[name=linked]:checked").val();
    	            item.merged        = $("#itemForm input[name=merged]:checked").val();
    	            item.title         = $("#itemForm input[name=title]").val();
    	            item.hideTitle     = $("#itemForm input[name=hideTitle]").attr("checked")?"1":"0";
                    item.required      = $("#itemForm input[name=required]:checked").val();
                    item.width         = $("#itemForm input[name=width]").val();
                    item.unit          = $("#itemForm input[name=unit]:checked").val();
                    item.height        = $("#itemForm input[name=height]").val();
                    item.template      = $("#itemForm textarea[name=template]").val();
                    item.description   = iFU.convertWCData(WCEditor.getContent());
                    
                    parent.closeDialog(item);
                }
    		};	  
    		
    		//- 연계가능여부시  
    		if(parent.linked){
    		    $("#linkedTr").show();
    		}
    		
    		//- 병합가능여부시 
    		if(parent.merged){
    		    $("#mergedTr").show();
    		}
    		
		    //- edit item  
            if(parent.mode=="edit" && parent.json.type==item.type){
                
                item.name = parent.json.name;
                $("#itemForm input[name=title]").val(parent.json.title);
                $("#itemForm input[name=required]:input[value=" + parent.json.required + "]").attr("checked", true);
                $("#itemForm input[name=width]").val(parent.json.width);
                $("#itemForm input[name=height]").val(parent.json.height);
                $("#itemForm textarea[name=template]").val(parent.json.template);
                $("#itemForm textarea[name=description]").val(iFU.revokeWCData(parent.json.description));
                $("#saveButton > span").html("<ikep4j:message pre='${preList}' key='edit'/>");
                
                if(parent.json.hideTitle=="1") $("#itemForm input[name=hideTitle]").attr("checked", true);
                if(parent.json.unit) $("#itemForm input[name=unit]:input[value=" + parent.json.unit + "]").attr("checked", true);
                
                if(parent.merged && parent.json.merged) $("#itemForm input[name=merged]:input[value=" + parent.json.merged + "]").attr("checked", true);
                if(parent.linked && parent.json.linked) $("#itemForm input[name=linked]:input[value=" + parent.json.linked + "]").attr("checked", true);
            };

		    //- 버튼영역 실행  
			$("#itemFormButton a").click(function(){
			    switch (this.id) {
                    case "saveButton":
                        $("#itemForm").trigger("submit");
        			    break;
                    case "closeButton":
                        parent.closeDialog();
                        break;
	                default:
	                    break;
	            }   
            });

            //-  wonchu Edit Init 
            wceditorMiniToolbar.height="150px";
            wceditorMiniToolbar.miniMode = true;
            WCEditor.fixIfrmLeft("approval.form");
            $("#itemForm textarea[name=description]").wceditor(wceditorMiniToolbar);
	        
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
    			    <tr id="linkedTr" style="display:none;">
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='linked'/></th>
    					<td>
    						<input type="radio" class="radio" name="linked" value="1" title="<ikep4j:message pre='${preIfm}' key='yes'/>"> <ikep4j:message pre='${preIfm}' key='yes'/>
                            <input type="radio" class="radio" name="linked" value="0" checked="checked" title="<ikep4j:message pre='${preIfm}' key='no'/>"> <ikep4j:message pre='${preIfm}' key='no'/>
    					</td>
    			    </tr>
    			    <tr id="mergedTr" style="display:none;">
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='merged'/></th>
    					<td>
    						<input type="radio" class="radio" name="merged" value="1" title="<ikep4j:message pre='${preIfm}' key='yes'/>"> <ikep4j:message pre='${preIfm}' key='yes'/>
                            <input type="radio" class="radio" name="merged" value="0" checked="checked" title="<ikep4j:message pre='${preIfm}' key='no'/>"> <ikep4j:message pre='${preIfm}' key='no'/>
    					</td>
    			    </tr>
    				<tr>
    					<th width="15%" scope="row"><ikep4j:message pre='${preIfm}' key='title'/></th>
    					<td width="85%">
    						<input name="title" value="" type="text" class="inputbox w70" title="<ikep4j:message pre='${preIfm}' key='title'/>" />
    						<input type="checkbox" class="checkbox" name="hideTitle" title="<ikep4j:message pre='${preIfm}' key='hidden'/>" /> <ikep4j:message pre='${preIfm}' key='hidden'/>
    					</td>
    	            
    	            </tr>
    	            <tr>
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='required'/></th>
    					<td>
    						<input type="radio" class="radio" name="required" value="1" checked="checked" title="<ikep4j:message pre='${preIfm}' key='yes'/>"> <ikep4j:message pre='${preIfm}' key='yes'/>
                            <input type="radio" class="radio" name="required" value="0" title="<ikep4j:message pre='${preIfm}' key='no'/>"> <ikep4j:message pre='${preIfm}' key='no'/>
    					</td>
    			    </tr>
    			    <tr>		
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='width'/></th>
    					<td>
    						<input type="text" name="width" class="inputbox wdt20" maxlength="3" maxlength="5" value="100" title="<ikep4j:message pre='${preIfm}' key='width'/>">
    						<input type="radio" name="unit" value="0" class="radio" checked="checked"> pixel
    				        <input type="radio" name="unit" value="1" class="radio"> precent
    					</td>
    		        </tr>
    		        <tr>		
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='height'/></th>
    					<td>
    						<input type="text" name="height" class="inputbox wdt20" maxlength="3" maxlength="3" value="100" title="<ikep4j:message pre='${preIfm}' key='height'/>"> (px)
    					</td>
    		        </tr>
    		        <tr>		
    					<th scope="row">template</th>
    					<td>
    						 <textarea name="template" class="inputbox w100" rows="3" title="template"></textarea>
    					</td>
    		        </tr>
    		        <tr>		
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='description'/></th>
    					<td>
    						 <textarea name="description" class="inputbox w100" rows="3" title="<ikep4j:message pre='${preIfm}' key='description'/>"></textarea>
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
			<li><a id="saveButton"      class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			<li><a id="closeButton"     class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='close'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
</div>