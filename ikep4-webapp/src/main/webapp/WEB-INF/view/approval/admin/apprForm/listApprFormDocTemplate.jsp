<%--
=====================================================
* 기능 설명 : 에디터 서식
* 작성자    : wonchu
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preSearch"      value="ui.approval.common.searchCondition" />
<c:set var="preList"        value="ui.approval.apprForm.list" />
<c:set var="preListMessage" value="message.approval.apprForm.list" />    
<c:set var="preViewMessage" value="message.approval.apprForm.view" />
<c:set var="preButton"      value="ui.approval.common.button" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript">
	<!--
	
	(function($){
		
		var dialogWindow;  
		
		//- onload시 수행할 코드
		$(document).ready(function(){
		    
		    
		    //- validation
    	    var validOptions = {
                rules  : {
                    templateName : {
                        required : true
                    }
                },
                messages : {
                    templateName : {
                        required : "<ikep4j:message pre='${preViewMessage}' key='required'/>"
                    }
                },    
                notice : {
                },      
    	        submitHandler : function(form) {
    	            form.submit();
                }
    		};	
			
			//- caller 
			fnCaller = function (params, dialog) {
				dialogWindow = dialog;
				$("#saveForm input[name=templateData]").val(params.editorData);
			};
			
			toggleButton("new");
	        //$("#saveForm input[name=templateName]").focus();
			
			//- create button 			
			$("#createButton").click(function(){
			    if($("#saveForm input[name=templateData]").val()==""){
			        alert("<ikep4j:message pre='${preListMessage}' key='notSave'/>");
			        return;
			    }
			    
			    if(!confirm("<ikep4j:message pre='${preListMessage}' key='save'/>")) return false;
			    $("#saveForm").attr("action", "createApprFormDocTemplate.do");
	            $("#saveForm").trigger("submit");
            });
            
            //- update button 
            $("#updateButton").click(function(){
                if($("#saveForm input[name=templateData]").val()==""){
			        alert("<ikep4j:message pre='${preListMessage}' key='notEdit'/>");
			        return;
			    }
			    
			    if(!confirm("<ikep4j:message pre='${preListMessage}' key='edit'/>")) return false;
                $("#saveForm").attr("action", "updateApprFormDocTemplate.do");
	            $("#saveForm").trigger("submit");
            });
            
            //- delete button 
            $("#deleteButton").click(function(){
                var templates = "";
                
                $.each($("#listForm input[name=checkItem]:checked"), function(){
                    if(templates!="") templates += ",";
                    templates += $(this).val(); 
                });        
                
                if(templates==""){
                    alert("<ikep4j:message pre='${preListMessage}' key='notDelete'/>");
                    return;
                }
                
                if(!confirm("<ikep4j:message pre='${preListMessage}' key='delete'/>")) return false;
                
                $("#saveForm input[name=templateName]").rules("remove");
                $("#saveForm input[name=templateId]").val(templates);
                $("#saveForm").attr("action", "deleteApprFormDocTemplate.do");
	            $("#saveForm").trigger("submit");
            });
            
            //- cancel button 
            $("#cancelButton").click(function(){
	            toggleButton("new");
            });
            
            //- close button  
            $("#closeButton").click(function(){
	           dialogWindow.close();
            });
            
            //- 전체 선택/해제 
            $("#listForm input[name=checkAll]").click(function(){
                var checked = $("#listForm input[name=checkAll]").attr("checked")=="checked"?true:false;
	            $("#listForm input[name=checkItem]").attr("checked", checked);
            });
            
            //- validator mapping 
            new iKEP.Validator("#saveForm", validOptions);
		});
		
		
        //- toggle
		toggleButton = function(mode){
		    if(mode=="new"){
		        $("#updateButton").hide();
		        $("#cancelButton").hide();
		        $("#createButton").show();
		    }else{
		        $("#updateButton").show();
		        $("#cancelButton").show();
		        $("#createButton").hide();
		    }
		};
		
		
        //- 템플릿 적용
		setTemplateData = function(index){
		    if(!confirm("<ikep4j:message pre='${preListMessage}' key='adjest'/>")) return;
		    var result = { "templateType" : $("#listForm input[name=templateType]").val(),
		                   "editorData"   : $("#listForm textarea[name=templateData" + index + "]").val()
		                 }
		    dialogWindow.callback(result);
		    dialogWindow.close();
		};
		
		
        //-템플릿 수정모드
		editTemplate = function(index){
		    toggleButton("edit");
		    $("#saveForm input[name=templateId]").val($("#listForm input[name=templateId" + index + "]").val());
		    $("#saveForm input[name=templateName]").val($("#listForm input[name=templateName" + index + "]").val());
		}
	})(jQuery);
	
	
	//-->
</script>

<!--mainContents Start-->
<h1 class="none">content area</h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2>&nbsp;</h2>
</div>
<!--//pageTitle End-->

	<!--blockSearch Start-->
	<div class="blockSearch">
		<div class="corner_RoundBox03">
		    <form id="saveForm" name="saveForm" method="post" />
			<table summary="form search">
				<caption></caption>
				<tbody>
					<tr>
						<th scope="row" width="5%">
						    <ikep4j:message pre='${preList}' key='templateName'/>
				        </th>
						
						<td width="95%">		
						<spring:bind path="searchCondition.templateType">
			                <input name="${status.expression}" type="hidden" value="${status.value}" />     	
			            </spring:bind>    
						<spring:bind path="apprFormDocTemplate.templateName">
						    <input name="${status.expression}" type="text" class="inputbox" title='' />     	
						</spring:bind>
						<spring:bind path="apprFormDocTemplate.templateData">
                            <input name="${status.expression}" type="hidden" />
	                    </spring:bind>
	                    <spring:bind path="apprFormDocTemplate.templateId">
		                    <input name="${status.expression}" type="hidden" />
	                    </spring:bind>
						<a id="createButton" class="button_ic valign_bottom" href="#a"><span><ikep4j:message pre='${preList}' key='saveTemplate'/></span></a>
						<a id="updateButton" class="button_ic valign_bottom" href="#a"><span><ikep4j:message pre='${preList}' key='editTemplate'/></span></a>
						<a id="cancelButton" class="button_ic valign_bottom" href="#a"><span><ikep4j:message pre='${preList}' key='cancel'/></span></a>
						</td>
					</tr>
				</tbody>
			</table>
		    </form>
			<div class="l_t_corner"></div>
			<div class="r_t_corner"></div>
			<div class="l_b_corner"></div>
			<div class="r_b_corner"></div>				
		</div>
	</div>	
	<!--//blockSearch End-->
    <form id="listForm" name="listForm" method="post" action="<c:url value='listMyEditorData.do'/>">
	<spring:bind path="searchCondition.templateType">
        <input name="${status.expression}" type="hidden" value="${status.value}" />     	
    </spring:bind>    
	<!--blockListTable Start-->
	<div class="blockListTable">
		<!--tableTop Start-->
		<div class="tableTop bgR">
		<div class="listInfo">
		<spring:bind path="searchCondition.pagePerRecord">  
			<select name="${status.expression}">
			<c:forEach var="code" items="${numList}">
				<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
			</c:forEach> 
			</select> 
		</spring:bind>
		<div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)</div>
	</div>				
		<div class="clear"></div>
	</div>
		<!--//tableTop End-->
	
		<table summary="">
			<caption></caption>
			<col style="width:10%;"/>
		    <col style="width:80%;"/>
		    <col style="width:10%;"/>
			<thead>
				<tr>
					<th scope="col"><input type="checkbox" class="checkbox" name="checkAll" /></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='templateName'/></th>
					<th scope="col"><ikep4j:message pre='${preList}' key='edit'/></th>
				</tr>
			</thead>
			<tbody>
		    <c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="3" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
					</tr>
			    </c:when>
			    <c:otherwise>
			        <c:forEach var="item" items="${searchResult.entity}" varStatus="i">
    				<tr>
    				    <td>
                            <input name="checkItem" class="checkbox" type="checkbox" value="${item.templateId}"" />
                            <input type="hidden" name="templateId${i.index}" value="${item.templateId}"/>
                            <input type="hidden" name="templateName${i.index}" value="${item.templateName}"/>
                            <textarea name="templateData${i.index}" style="display:none;">${item.templateData}</textarea>
    				    </td>
    				    <td class="textLeft">
    				    <a href="#a" onclick="setTemplateData(${i.index});">${item.templateName}</a>
    				    </td>
    				    <td><a class="button_ic valign_bottom" href="#a" onclick="editTemplate(${i.index});"><span><ikep4j:message pre='${preButton}' key='update'/></span></a></td>
    			    </tr>
    			    </c:forEach>				      
			    </c:otherwise> 
			</c:choose>  
			</tbody>
		</table>
		<!--Page Numbur Start--> 
	    <spring:bind path="searchCondition.pageIndex">
	    <ikep4j:pagination searchButtonId="${status.expression}" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
	    <input  id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" />
	    </spring:bind> 
	    <!--//Page Numbur End--> 
	</div>
	<!--//blockListTable End-->	
	
	<!--blockButton Start-->
    <div class="blockButton"> 
    	<ul> 
    	    <li><a id="deleteButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
    		<li><a id="closeButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='close'/></span></a></li>
    	</ul>
    </div>
    <!--blockButton End-->
    </form>
<!--//mainContents End-->