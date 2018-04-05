<%--
=====================================================
* 기능 설명 : 양식 item메인 
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
<c:set var="preListMessage" value="message.approval.apprForm.list" />     
<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value="/base/js/units/approval/admin/apprForm.js"/>"></script>
<script  type="text/javascript">
<!--// 
    
    iFC.item.text.name              = "<ikep4j:message pre='${preIfm}' key='item.text'/>";
    iFC.item.textarea.name          = "<ikep4j:message pre='${preIfm}' key='item.textarea'/>";
    iFC.item.select.name            = "<ikep4j:message pre='${preIfm}' key='item.select'/>";
    iFC.item.radio.name             = "<ikep4j:message pre='${preIfm}' key='item.radio'/>";
    iFC.item.checkbox.name          = "<ikep4j:message pre='${preIfm}' key='item.checkbox'/>";
    iFC.item.textbox.name           = "<ikep4j:message pre='${preIfm}' key='item.textbox'/>";
    iFC.item.embed.name             = "<ikep4j:message pre='${preIfm}' key='item.embed'/>";
    iFC.item.line.name              = "<ikep4j:message pre='${preIfm}' key='item.line'/>";
    
    var mode, merged, json, multi, linked;
    var _height = 470;

    (function($){
    
        var dialogWindow;    
        var preIndex =0;
        
        $(document).ready(function() {
            
            //- caller
            fnCaller = function (params, dialog) {
				dialogWindow = dialog;
				merged  = params.merged;
				json    = params.json;
				mode    = params.mode;
				multi   = params.multi;
				linked  = params.linked;
				
				if(merged) _height += 26;
                if(linked) _height += 26;
				
				//- item object 생성
		        var tabId=0, src="";
    		    $.each(iFC.item, function(key){
    		        if(this.tabId!="-1"){
    		            if(!multi || (multi && iFU.hasArrayValue(iFC.allowMultiItem, key))){
                            this.tabId = String(tabId);
                            if(json.type && key==json.type){
                                src = "viewApprFormItem.do?itemId=" + key;
                            }else if(!json.type && key=="text"){ // 신규시 default를 text
                                src = "viewApprFormItem.do?itemId=" + key;
                            }else{
                                src ="";
                            }
                            
                            $("#searchTab ul").append("<li><a href=\"#tabs-" + tabId + "\">" +this.name+"</a></li>");
                            $("#searchTab .tab_con").append("<div id=\"tabs-" + tabId + "\"><iframe width=\"99%\" src=\"" + src + "\" height=\"" + _height + "\" scrolling=\"auto\" frameborder=\"0\" />");
                            tabId++;
                        }                            
                    }
                });
				
				//-신규 또는 수정 모드 판별 및 처리
                if(mode=="new"){
    		        $("#searchTab").tabs({selected : 0, select : function(event, ui){checkItem(ui.index)}});
    		        $("#tabs-0 iframe").load( function(){
                        $(this).contents().find("#itemForm input[name=title]").focus() // 텝클릭시 모든 item title에 focus를 주려 했지만 ie에서 해당 iframe이 보여지기 전에 포커스를 주기 때문에 GG함
                    });
                }else{
                    $("#searchTab").tabs({selected : iFC.item[json.type].tabId, select : function(event, ui){checkItem(ui.index)}});
                }
    	    };
			
        });
        
        // index 로 type을 구함
        getItemTypeByIndex = function(index){
            var src="";
            $.each(iFC.item, function(key){
                if(this.tabId==String(index)) {
                    src = key;
                    return false;
                }
            });
            return src;
        };
        
        //- 신규 작성시 기 작성된 item 항목여부를 판단하고 처리
        checkItem = function(index){
            if($("#tabs-" + index + " iframe").attr("src")==""){
                $("#tabs-" + index + " iframe").attr("src", "viewApprFormItem.do?itemId=" + getItemTypeByIndex(index));
            }
            
            var title = $("#tabs-" + preIndex + " iframe").contents().find("#itemForm input[name=title]").val();
            if(mode=="new" && title!=undefined && title!=""){
                if(confirm("<ikep4j:message pre='${preListMessage}' key='checkItem'/>")){
                    $("#tabs-" + preIndex + " iframe").contents().find("#itemForm")[0].reset();
                }
            }
            
            preIndex = index;
        },
        
        //- close or save close
        closeDialog = function(json){
            if(typeof(json)!="undefined"){
                
                if(mode=="edit"){
                    if(!confirm("<ikep4j:message pre='${preListMessage}' key='edit'/>")) return;
                }
                
                dialogWindow.callback(json);
            }
            
            dialogWindow.close();
        }   
            
    })(jQuery);

//-->
</script>
<h1 class="none">contnet area</h1>
<div id="guideConFrame">
    <div class="blockBlank_10px"></div>
	<!--tab Start-->		
	<div id="searchTab" class="iKEP_tab">
	    <ul></ul>
	    <div class="tab_con"></div>                
	</div>
	<!--//tab End-->
</div>