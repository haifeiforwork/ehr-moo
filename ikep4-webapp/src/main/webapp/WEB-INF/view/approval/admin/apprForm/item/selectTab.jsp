<%--
=====================================================
* 기능 설명 : item - select 
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
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/approval/admin/apprForm.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/js/wceditor/css/editor.css"/>"/>
<script type="text/javascript" src="<c:url value="/base/js/wceditor/js/editor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/approval/admin/apprForm.js"/>"></script>     
<script  type="text/javascript">
<!--// 

	(function($) {
	    
		
		//- item JSON
        var item = {  "type"            : "select",
                      "name"            : "",
                      "merged"          : "",
                      "linked"          : "",
                      "title"           : "",
                      "hideTitle"       : "",
                      "dataType"        : "",
                      "required"        : "",
                      "defaultValue"    : "",
                      "lastSeq"         : 0,
                      "codeId"          : "",
                      "options"         : [],
                      "description"     : ""
                    };
        
        var editIndex = 0; //수정시 인덱스값
        $(document).ready(function() {   

		    //- validation   
    	    var validOptions = {
                rules  : {
                    options : { 
                         minSize : 1
                    },
                    
                    codeId : { 
                        required : true
                    }
                },
                messages : {
                    options : { 
                         minSize : "<ikep4j:message pre='${preViewMessage}' key='minSize'/>"
                    },
                    
                    codeId : { 
                        required : "<ikep4j:message pre='${preViewMessage}' key='selectCode'/>"
                    }
                },    
                notice : {
                },      
    	        submitHandler : function(form) {
    	            item.linked         = $("#itemForm input[name=linked]:checked").val();
    	            item.merged         = $("#itemForm input[name=merged]:checked").val();
    	            item.title          = $("#itemForm input[name=title]").val();
    	            item.hideTitle      = $("#itemForm input[name=hideTitle]").attr("checked")?"1":"0";
    	            item.dataType       = $("#itemForm input[name=dataType]:checked").val();
    	            item.required       = $("#itemForm input[name=required]:checked").val();
                    
                    if(item.dataType=="1"){
                        if($("#itemForm select[name=options] option.selectedOption").val()){
                            item.defaultValue   = $("#itemForm select[name=options] option.selectedOption").val();
                        }
                        
                        item.options = [];
                        $("#itemForm select[name=options] option").each(function() {
                            item.options.push({"text" : $(this).text(), "value" : $(this).val()});
                        });
                    } else{
                        item.lastSeq = 0;
                        item.codeId = $("#itemForm select[name=codeId]").val();
                    }
                    
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
   
		    //- 데이터 클릭시   
			$("#itemForm input[name=dataType]").click(function() {
                if($(this).val()=="1"){
                    $("#optionValueTr").show();
                    $("#codeIdTr").hide();
                    
                    $("#itemForm select[name=codeId]").rules("remove");
                    $("#itemForm select[name=options]").rules("add", {"minSize" : 1});
                }else{
                    $("#optionValueTr").hide();
                    $("#codeIdTr").show();
                    
                    $("#itemForm select[name=options]").rules("remove");
                    $("#itemForm select[name=codeId]").rules("add", {"required" : true});
                }
            });   
  
    		//- edit item  
            if(parent.mode=="edit" && parent.json.type==item.type){
                item.name = parent.json.name;
                $("#itemForm input[name=title]").val(parent.json.title);
                $("#itemForm input[name=dataType]:input[value=" + parent.json.dataType + "]").attr("checked", true);
                $("#itemForm input[name=required]:input[value=" + parent.json.required + "]").attr("checked", true);
                $("#itemForm textarea[name=description]").val(iFU.revokeWCData(parent.json.description));
                
                if(parent.json.dataType=="1"){
                    item.lastSeq = parent.json.lastSeq;
                    var selected = ""
                    $.each(parent.json.options, function(){
                        selected = parent.json.defaultValue == this.value?" class=\"selectedOption\"":"";
                        $("#itemForm select[name=options]").append("<option value=\"" + this.value + "\"" + selected + ">" + this.text + "</option>");
                    });
                }else{
                    $("#itemForm select[name=codeId] option[value="+parent.json.codeId+"]").attr("selected", true);
                }
                $("#saveButton > span").html("<ikep4j:message pre='${preList}' key='edit'/>");
                
                if(parent.json.hideTitle=="1") $("#itemForm input[name=hideTitle]").attr("checked", true);
                if(parent.merged && parent.json.merged) $("#itemForm input[name=merged]:input[value=" + parent.json.merged + "]").attr("checked", true);
                if(parent.linked && parent.json.linked) $("#itemForm input[name=linked]:input[value=" + parent.json.linked + "]").attr("checked", true);
            }
            
            $("#updateOptionButton").hide();
            $("#initOptionButton").hide();
 
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
  
    		//- add option   
            $("#addOptionButton").click(function() {
                var optionText  = $.trim($("#itemForm input[name=optionText]").val());
                if(optionText=="") return;
                
                item.lastSeq++;
                var optionValue = "S" + item.lastSeq;
                var checked     = $("#itemForm input[name=defaultValue]").attr("checked")?" class=\"selectedOption\"":"";    
            	
                if(checked!="") $("#itemForm select[name=options] option.selectedOption").removeClass("selectedOption");  		
        		$("#itemForm select[name=options]").append("<option value=\""+optionValue+"\"" + checked + ">"+optionText+"</option>");
        		setOptionForm("", "", false);
            	
            });
            
       		//- update option 
            $("#updateOptionButton").click(function() {
                var optionText  = $.trim($("#itemForm input[name=optionText]").val());
                if(optionText=="") return;
                
                var optionValue = $("#itemForm input[name=optionValue]").val();
                var checked     = $("#itemForm input[name=defaultValue]").attr("checked")?" class=\"selectedOption\"":"";
    
                if(checked!="") $("#itemForm select[name=options] option.selectedOption").removeClass("selectedOption");  		
        		
        		$("#itemForm select[name=options] option:eq(" + editIndex + ")").replaceWith("<option value=\""+optionValue+"\"" + checked + ">"+optionText+"</option>");
        		setOptionForm("", "", false);
        		$("#addOptionButton").show();
                $("#updateOptionButton").hide();
                $("#initOptionButton").hide();
    
            });
            
    		//- init option 
            $("#initOptionButton").click(function() {
                setOptionForm("", "", false);
                $("#addOptionButton").show();
                $("#updateOptionButton").hide();
                $("#initOptionButton").hide();
            });
            
    		//- edit option 
            $("#editOptionButton").click(function() {
                editIndex = $("#itemForm select[name=options] option").index($("#itemForm select[name=options] option:selected"));
                if(editIndex!=-1){
                    setOptionForm($("#itemForm select[name=options] option:selected").text(), $("#itemForm select[name=options] option:selected").val(), $("#itemForm select[name=options] option:selected").hasClass("selectedOption"));
                    $("#addOptionButton").hide();
                    $("#updateOptionButton").show();
                    $("#initOptionButton").show();
                }     
            });   

    		//- remove option 
            $("#removeOptionButton").click(function() {
                var selIndex = $("#itemForm select[name=options] option").index($("#itemForm select[name=options] option:selected"));
               $("#itemForm select[name=options] option:selected").remove();
               if(selIndex>0) $("#itemForm select[name=options] option:eq(" + (selIndex-1) + ")").attr("selected", "selected");
            });   

    		//-  move up option 
            $("#moveUpButton").click(function() {
                if($("#itemForm select[name=options] option:selected").prevAll().size()>0){
                    $("#itemForm select[name=options] option:selected").insertBefore($("#itemForm select[name=options] option:selected").first().prev());
                }
            });   

    		//-  move down option 
            $("#moveDownButton").click(function() {
               if($("#itemForm select[name=options] option:selected").nextAll().size()>0){
                    $("#itemForm select[name=options] option:selected").insertAfter($("#itemForm select[name=options] option:selected").last().next());
                }
            });   

    		//-  option text event 
            $("#itemForm input[name=optionText]").keypress(function(event){
                if(event.which == 13) {
                    if($("#addOptionButton").css("display")=="none")
                	    $("#updateOptionButton").click();
                    else
                        $("#addOptionButton").click();
                }
            });

            //-  wonchu Edit Init 
            wceditorMiniToolbar.height="125px";
            wceditorMiniToolbar.miniMode = true;
            WCEditor.fixIfrmLeft("approval.form");
            $("#itemForm textarea[name=description]").wceditor(wceditorMiniToolbar);
            
            new iKEP.Validator("#itemForm", validOptions);  
            
            $("#itemForm input[name=dataType]:input[value=" + $("#itemForm input[name=dataType]:checked").val() + "]").click();
        });
    
        
		//- option setting
        setOptionForm = function(txt, val, b){
            $("#itemForm input[name=optionText]").val(txt);
            $("#itemForm input[name=optionValue]").val(val);
            $("#itemForm input[name=defaultValue]").attr("checked", b);
            $("#itemForm input[name=optionText]").select();
        };
        
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
    						<input type="radio" class="radio" name="required" value="1" title="<ikep4j:message pre='${preIfm}' key='yes'/>"> <ikep4j:message pre='${preIfm}' key='yes'/>
                            <input type="radio" class="radio" name="required" value="0" checked="checked" title="<ikep4j:message pre='${preIfm}' key='no'/>"> <ikep4j:message pre='${preIfm}' key='no'/> <span id="requireSpan">(<ikep4j:message pre='${preIfm}' key='notFirsetSelect'/>)</span>
    					</td>
    	            </tr>
    	            <tr>
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='data'/></th>
    					<td>
    						<input type="radio" class="radio" name="dataType" value="1" checked="checked" title="<ikep4j:message pre='${preIfm}' key='user'/>"> <ikep4j:message pre='${preIfm}' key='user'/>
                            <input type="radio" class="radio" name="dataType" value="0" title="<ikep4j:message pre='${preIfm}' key='code'/>"> <ikep4j:message pre='${preIfm}' key='code'/>
    					</td>
    	            
    	            </tr>
    	            <tr id="optionValueTr">
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='option'/></th>
    					<td style="padding-top:7px;">
    						<input type="hidden" name="optionValue" />
                            <ikep4j:message pre='${preIfm}' key='defaultValue'/> : <input type="checkbox" class="checkbox" name="defaultValue" title="<ikep4j:message pre='${preIfm}' key='defalutValue'/>" />
                            <input type="text" name="optionText" class="inputbox w20" style="width:137px !important;" />
                            <a id="addOptionButton" class="button_ic valign_bottom mr3" href="#a"><span><ikep4j:message pre='${preList}' key='add'/></span></a>
                            <a id="updateOptionButton" class="button_ic valign_bottom mr3" href="#a"><span><ikep4j:message pre='${preList}' key='edit'/></span></a>
                            <a id="initOptionButton" class="button_ic valign_bottom" href="#a"><span><ikep4j:message pre='${preList}' key='cancel'/></span></a><br>                            
                            
							<table border="0" style="width:515px;">
							  <tr>
							    <td class="border_none padding0"><select name="options" class="inputbox mt5" size="5" style="width:259px; width:256px\9; height:69px;"></select></td>
							    <td class="border_none">
							    	<ul>
								    	<li>
			                            	<a id="moveUpButton" class="button_ic valign_bottom" href="#a" ><span>▲</span></a>
			                            </li>
			                            <li>
			                            	<a id="moveDownButton" class="button_ic valign_bottom mt5" href="#a"><span>▼</span></a>
			                            </li>
		                            </ul>
							    </td>
							  </tr>
							  <tr>
							    <td class="border_none pt5" colspan="2" style="padding-left:175px; padding-left:176px\9;">
		                            <a id="editOptionButton" class="button_ic valign_bottom mr3" href="#a"><span><ikep4j:message pre='${preList}' key='edit'/></span></a>
		                            <a id="removeOptionButton" class="button_ic valign_bottom" href="#a"><span><ikep4j:message pre='${preList}' key='delete'/></span></a>
							    </td>
							  </tr>
							</table>                            
							
    					</td>
    			    </tr>
    			    <tr id="codeIdTr">
    					<th scope="row"><ikep4j:message pre='${preIfm}' key='option'/></th>
    					<td>
                            <select name="codeId" title="<ikep4j:message pre='${preIfm}' key='option'/>">
                                <option value=""><ikep4j:message pre='${preView}' key='select'/></option>
							    <c:forEach var="item" items="${apprCodeList}">
							    <option value="${item.codeId}">${item.codeName}</option>    
							    </c:forEach>
                            </select>
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