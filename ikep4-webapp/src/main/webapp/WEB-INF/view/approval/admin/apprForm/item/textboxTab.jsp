<%--
=====================================================
* 기능 설명 : item - textbox 
* 작성자    : wonchu
=====================================================
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<c:set var="user" value="${sessionScope['ikep.user']}" />  
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
var aaa = "textbox";
<!--// 

    iFC.bindData.userId.text         = "<ikep4j:message pre='${preIfm}' key='bindData.userId'/>";
    iFC.bindData.userName.text       = "<ikep4j:message pre='${preIfm}' key='bindData.userName'/>";
    iFC.bindData.jobTitleName.text   = "<ikep4j:message pre='${preIfm}' key='bindData.jobTitleName'/>";
    iFC.bindData.teamName.text       = "<ikep4j:message pre='${preIfm}' key='bindData.teamName'/>";
    
	(function($) {
		
		
        //- item JSON
        var item = {  "type"        : "textbox",
                      "name"        : "",
                      "linked"      : "",
                      "merged"      : "",
                      "header"      : "",
                      "title"       : "",
                      "hideTitle"   : "",
                      "html"        : ""
                    };
        
        $(document).ready(function() {   

		    //- validation  
    	    var validOptions = {
                rules  : {
                },
                messages : {
                },    
                notice : {
                },      
    	        submitHandler : function(form) {
    	            item.linked    = $("#itemForm input[name=linked]:checked").val();
    	            item.merged    = $("#itemForm input[name=merged]:checked").val();
    	            item.header    = $("#itemForm input[name=header]:checked").val();
    	            item.title     = $("#itemForm input[name=title]").val();
    	            item.hideTitle = $("#itemForm input[name=hideTitle]").attr("checked")?"1":"0";
                    item.html      = iFU.convertWCData(WCEditor.getContent());
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
                $("#itemForm input[name=header]:input[value=" + parent.json.header + "]").attr("checked", true);
                $("#itemForm input[name=title]").val(parent.json.title);
                $("#itemForm textarea[name=html]").val(iFU.revokeWCData(parent.json.html));
                
                $("#saveButton > span").html("<ikep4j:message pre='${preList}' key='edit'/>");
                
                if(parent.json.hideTitle=="1") $("#itemForm input[name=hideTitle]").attr("checked", true);
                if(parent.merged && parent.json.merged) $("#itemForm input[name=merged]:input[value=" + parent.json.merged + "]").attr("checked", true);
                if(parent.linked && parent.json.linked) $("#itemForm input[name=linked]:input[value=" + parent.json.linked + "]").attr("checked", true);
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
            
            //- kind 옵션추가  
            $.each(iFC.bindData, function(key){
               $("#textboxKindDiv").append($jq.tmpl(iFC.template.textboxKind, {"name" : key, "text" : this.text}));
            });
            
            //-  wonchu Edit Init 
            wceditorSimpleToolbar.height="200px";
            wceditorSimpleToolbar.miniMode = true;
            WCEditor.fixIfrmLeft("approval.textbox");
            $("#itemForm textarea[name=html]").wceditor(wceditorSimpleToolbar);
            
            new iKEP.Validator("#itemForm", validOptions);
        });
        
        
		//- editor에 kind추가
        addCkHtml = function(v){
            WCEditor.saveRange();
            WCEditor.setHtml(v);
        }
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
    					<th scope="row"><ikep4j:message pre='${preView}' key='isLinkUrl'/></th>
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
    			    <tr id="hearderTr">
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='header'/></th>
    					<td>
    						<input type="radio" class="radio" name="header" value="1" title="<ikep4j:message pre='${preIfm}' key='yes'/>"> <ikep4j:message pre='${preIfm}' key='yes'/>
                            <input type="radio" class="radio" name="header" value="0" checked="checked" title="<ikep4j:message pre='${preIfm}' key='yes'/>"> <ikep4j:message pre='${preIfm}' key='no'/>
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
    					<th width="15%" scope="row"><ikep4j:message pre='${preIfm}' key='tag'/></th>
    					<td width="85%">
    						<div id="textboxKindDiv"></div>
    					</td>
    	            </tr>
    		        <tr height="150">		
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='contents'/></th>
    					<td>
    						 <textarea name="html"></textarea>
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