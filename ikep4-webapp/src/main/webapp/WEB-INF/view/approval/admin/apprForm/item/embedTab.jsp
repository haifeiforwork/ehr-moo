<%--
=====================================================
* 기능 설명 : item - embed
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
    
    iFC.embedKind[0].text   = "<ikep4j:message pre='${preIfm}' key='embedKind.user'/>";
    iFC.embedKind[1].text   = "<ikep4j:message pre='${preIfm}' key='embedKind.group'/>";
    iFC.embedKind[2].text   = "<ikep4j:message pre='${preIfm}' key='embedKind.date'/>";

	(function($) {
		
		//- item JSON
        var item = {  "type"        : "embed",
                      "name"        : "",
                      "merged"      : "",
                      "title"       : "",
                      "hideTitle"   : "",
                      "required"    : "",
                      "kind"        : "",
                      "description" : ""
                    };
            
        $(document).ready(function() {   
            
		    //- validation  
    	    var validOptions = {
                rules  : {
                    kind : {
                        required : true
                    }
                },
                messages : {
                    kind : { 
                        required : "항목을 선택해주세요"
                    },
                    
                    maxCount : { 
                        required  : "<ikep4j:message pre='${preViewMessage}' key='required'/>",
                        digits    : "<ikep4j:message pre='${preIfmMessage}' key='digits'/>",
                        min       : "<ikep4j:message pre='${preIfmMessage}' key='min'/>"
                    }
                },    
                notice : {
                },      
    	        submitHandler : function(form) {
    	            item.merged        = $("#itemForm input[name=merged]:checked").val();
    	            item.title         = $("#itemForm input[name=title]").val();
    	            item.hideTitle     = $("#itemForm input[name=hideTitle]").attr("checked")?"1":"0";
    	            item.required      = $("#itemForm input[name=required]:checked").val();
                    item.kind          = $("#itemForm select[name=kind]").val();
                    if(item.kind=="user" || item.kind=="group"){
                        item.maxCount  = $("#itemForm input[name=maxCount]").val();
                    }
                    item.description   = iFU.convertWCData(WCEditor.getContent());
                    
                    parent.closeDialog(item);
                }
    		};	   
    		
    		//- 병합가능여부시  
    		if(parent.merged){
    		    $("#mergedTr").show();
    		}  

		    //- kind 옵션추가  
    		iFU.jsonToSelectOption($("#itemForm select[name=kind]"), iFC.embedKind);
			
			//- kind 변경시  
			$("#itemForm select[name=kind]").change(function(){
                if($(this).val()=="") return;
			    if($(this).val()=="user" || $(this).val()=="group") {
                    $("#maxCountTr").show();
                    $("#itemForm input[name=maxCount]").rules("add", {"required" : true, "digits" : true, min : 1});
                }else{
                
                    $("#maxCountTr").hide();
                    $("#itemForm input[name=maxCount]").rules("remove");
                }
    	    });

    		//- edit item  
            if(parent.mode=="edit" && parent.json.type==item.type){
                item.name = parent.json.name;
                $("#itemForm input[name=title]").val(parent.json.title);
                $("#itemForm input[name=required]:input[value=" + parent.json.required + "]").attr("checked", true);
                $("#itemForm select[name=kind] option[value="+parent.json.kind+"]").attr("selected", true);
                
                if(parent.json.kind=="user" || parent.json.kind=="group"){
                    $("#itemForm input[name=maxCount]").val(parent.json.maxCount);
                }
                
                $("#itemForm textarea[name=description]").val(iFU.revokeWCData(parent.json.description));
                $("#saveButton > span").html("<ikep4j:message pre='${preList}' key='edit'/>");
                
                if(parent.json.hideTitle=="1") $("#itemForm input[name=hideTitle]").attr("checked", true);
                if(parent.merged && parent.json.merged) $("#itemForm input[name=merged]:input[value=" + parent.json.merged + "]").attr("checked", true);
            }

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
            if(parent.mode=="edit" && parent.json.type==item.type){
                $("#itemForm select[name=kind]").change();
            }     
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
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='option'/></th>
    					<td>
    						<select name="kind" title="<ikep4j:message pre='${preIfm}' key='option'/>">
                                <option value=""><ikep4j:message pre='${preView}' key='select'/></option>
                           </select>
    					</td>
    			    </tr>
    			    <tr id="maxCountTr" style="display:none;">		
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='max'/></th>
    					<td>
    						<input type="text" name="maxCount" class="inputbox wdt20" maxlength="3" value="1" title="<ikep4j:message pre='${preIfm}' key='max'/>"> (<ikep4j:message pre='${preIfm}' key='length'/>)
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
			<li><a id="saveButton"  class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			<li><a id="closeButton"     class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='close'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
</div>