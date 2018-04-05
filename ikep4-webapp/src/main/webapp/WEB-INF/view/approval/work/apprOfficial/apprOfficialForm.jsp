<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.admin.apprOfficial.header" />
<c:set var="preForm"  	value="ui.approval.admin.apprOfficial" />
<c:set var="preValidation" value="ui.approval.admin.apprOfficial.validation" />
<c:set var="preButton"  value="ui.approval.common.button" />
<c:set var="preMessage" value="ui.approval.common.message" />
<c:set var="preIcon"  	value="ui.approval.common.icon" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript" src="<c:url value="/base/js/units/approval/work/apprDoc.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>   

<script type="text/javascript">
	<!--
	
	
	(function($){
		
		//validation
	    var validOptions = {
            rules  : {
            },
            messages : {
            },    
            notice : {
            },      
	        submitHandler : function(form) {
	        	
	        	if(confirm("<ikep4j:message pre="${preMessage}" key="save" />")) {
		        	//- formData 생성
	                var json = "";
	               	if($("#officialForm textarea[name=formLayoutData]").val()!=""){
	                   json = iFU.itemToFormData(form, $(form).find("input:[name=formId]").val(), $(form).find("input:[name=apprTitle]").val());
	                   if(json=="false") return false;
	               	}
		            
		        	//- formLayout 초기화(layout form 객체 삭제)
	                <c:if test="${not empty apprForm.formLayoutData}">
	               	 	$("#formLayoutDiv").empty();
	               	 </c:if>
	                
	              	//- 에디터 사용시
	              	<c:if test="${apprForm.isApprEditor eq 1}">
						var editor = $("#formEditorData").ckeditorGet();
	                	editor.updateElement();
	                
	                	//에디터 내 이미지 파일 링크 정보 세팅
		            	createEditorFileLink($(form).attr("id"));
	                </c:if> 
	                
	                $("#mainContents").ajaxLoadStart();
	                
	                if($("#officialForm textarea[name=formLayoutData]").val()!=""){
	                    $(form).find("textarea[name=formData]").val(JSON.stringify(json));
	                }
	                
	    	        form.submit();
	        	}
            }
		};
		
		$(document).ready(function(){
			
			<c:if test="${not empty apprDoc.formLayoutData}">
				iFU.setUserInfo("${user.userId}", "${user.localeCode}", "${apprDoc.registerId}");

		    	iFC.object.formLayoutData =  $.parseJSON($("#officialForm textarea[name=formLayoutData]").val());
	            iFC.object.formData       =  $.parseJSON($("#officialForm textarea[name=formData]").val());
	            iFC.mode = "edit";
	            
	            iFU.printForm();
	            
	          	//- rules 셋팅  
	            if(iFC.object.rules!=""){
	                validOptions.rules = $.parseJSON("{" + iFC.object.rules + "}");
	            }
	            
	            //- messages 셋팅  
	            if(iFC.object.messages!=""){
	                validOptions.messages = $.parseJSON("{" + iFC.object.messages + "}");
	            }
	        </c:if>
		    
	        <c:if test="${apprForm.isApprEditor eq 1}">
			    fullCkeditorConfig.height="250";
			    $("#officialForm textarea[name=formEditorData]").ckeditor($jq.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "editor" }));
		    </c:if>
		    
		    validOptions.rules.campanyName = {"required" : true};
		    validOptions.messages.campanyName = {"required" : "<ikep4j:message key='NotEmpty.companyName'/>"};
		    validOptions.rules.companyZipCode = {"required" : true};
		    validOptions.messages.companyZipCode = {"required" : "<ikep4j:message key='NotEmpty.companyZipCode'/>"};
		    validOptions.rules.companyTel = {"required" : true};
		    validOptions.messages.companyTel = {"required" : "<ikep4j:message key='NotEmpty.companyTel'/>"};
		    validOptions.rules.companyFax = {"required" : true};
		    validOptions.messages.companyFax = {"required" : "<ikep4j:message key='NotEmpty.companyFax'/>"};
		    validOptions.rules.companyAddress = {"required" : true};
		    validOptions.messages.companyAddress = {"required" : "<ikep4j:message key='NotEmpty.companyAddress'/>"};
		    validOptions.rules.officialDocNo = {"required" : true};
		    validOptions.messages.officialDocNo = {"required" : "<ikep4j:message key='NotEmpty.officialDocNo'/>"};
		    validOptions.rules.receiver = {"required" : true};
		    validOptions.messages.receiver = {"required" : "<ikep4j:message key='NotEmpty.receiver'/>"};
		    validOptions.rules.reference = {"required" : true};
		    validOptions.messages.reference = {"required" : "<ikep4j:message key='NotEmpty.reference'/>"};
		    validOptions.rules.title = {"required" : true};
		    validOptions.messages.title = {"required" : "<ikep4j:message key='NotEmpty.title'/>"};
		    
            new iKEP.Validator("#officialForm", validOptions);
			
			$jq("#saveBtn").click(function() {  
				$("#officialForm").trigger("submit");
			});  
			
			$jq("#cancelBtn").click(function() {  
				history.back();
			}); 
			
		});
	})(jQuery);
	//-->
</script>

<h1 class="none"><ikep4j:message pre="${preHeader}" key="title" /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
	<%-- <div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.4depth" /></li>
		</ul>
	</div> --%>
</div>
<!--//pageTitle End-->

<form id="officialForm" name="officialForm" method="post" action="<c:url value="/approval/work/apprOfficial/saveApprOfficial.do"/>">

    <spring:bind path="apprDoc.apprId">
      	<input type="hidden" name="${status.expression}" value="${status.value}">
    </spring:bind>
   	<spring:bind path="apprDoc.mode">
      	<input type="hidden" name="${status.expression}" value="${status.value}">
    </spring:bind>
    <spring:bind path="apprDoc.formId">
      	<input type="hidden" name="${status.expression}" value="${status.value}">
    </spring:bind>
   	<spring:bind path="apprDoc.formData">
      	<textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
    </spring:bind>
    <spring:bind path="apprDoc.formLayoutData">
      	<textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
    </spring:bind>
	
	<spring:bind path="officialConfig.configId">
      	<textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
    </spring:bind>
    <spring:bind path="officialConfig.headerTitle">
      	<textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
    </spring:bind>
    <spring:bind path="officialConfig.headerHeight">
      	<textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
    </spring:bind>
    <spring:bind path="officialConfig.headerType">
      	<textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
    </spring:bind>
    <spring:bind path="officialConfig.headerImgId">
      	<textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
    </spring:bind>
    <spring:bind path="officialConfig.headerAlign">
      	<textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
    </spring:bind>
    <spring:bind path="officialConfig.footerTitle">
      	<textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
    </spring:bind>
    <spring:bind path="officialConfig.footerHeight">
      	<textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
    </spring:bind>
    <spring:bind path="officialConfig.footerType">
      	<textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
    </spring:bind>
    <spring:bind path="officialConfig.footerImgId">
      	<textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
    </spring:bind>
    <spring:bind path="officialConfig.footerAlign">
      	<textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
    </spring:bind>
    <spring:bind path="officialConfig.sealId">
      	<textarea name="${status.expression}" style="display:none;">${status.value}</textarea>
    </spring:bind>
		
	
	<span><ikep4j:message pre='${preForm}' key='companyInfo'/></span>
		
	<!--blockDetail Start-->
	<div class="blockDetail">
	
		<table summary="<ikep4j:message pre='${preForm}' key='companyInfo'/>">
			<caption></caption>
			<colgroup>
				<col width="15%"/>
				<col width="35%"/>
				<col width="15%"/>
				<col width="35%"/>
			</colgroup>		
			<tbody>
			
				
				<tr>
					<th scope="row" ><ikep4j:message pre='${preForm}' key='companyName'/></th>
					<td>
						<input 
							type="text" 
							class="inputbox" 
							id="companyName" 
							name="companyName" 
							title="<ikep4j:message pre='${preForm}' key='companyName'/>" 
							value="${officialConfig.companyName}" 
							size="40" 
						/>
					</td>
					
					<th scope="row" ><ikep4j:message pre='${preForm}' key='companyZipCode'/></th>
					<td>
						<input 
							type="text" 
							class="inputbox" 
							id="companyZipCode" 
							name="companyZipCode" 
							title="<ikep4j:message pre='${preForm}' key='companyZipCode'/>" 
							value="${officialConfig.companyZipCode}" 
						/>
					</td>
				</tr>
				
				<tr>
					<th scope="row" ><ikep4j:message pre='${preForm}' key='companyTel'/></th>
					<td>
						<input 
							type="text" 
							class="inputbox" 
							id="companyTel" 
							name="companyTel" 
							title="<ikep4j:message pre='${preForm}' key='companyTel'/>" 
							value="${officialConfig.companyTel}" 
						/>
					</td>
					
					<th scope="row" ><ikep4j:message pre='${preForm}' key='companyFax'/></th>
					<td>
						<input 
							type="text" 
							class="inputbox" 
							id="companyFax" 
							name="companyFax" 
							title="<ikep4j:message pre='${preForm}' key='companyFax'/>" 
							value="${officialConfig.companyFax}" 
						/>
					</td>
				</tr>
				
				<tr>
					<th scope="row" ><ikep4j:message pre='${preForm}' key='companyAddress'/></th>
					<td colspan="3">
						<input 
							type="text" 
							class="inputbox" 
							id="companyAddress" 
							name="companyAddress" 
							title="<ikep4j:message pre='${preForm}' key='companyAddress'/>" 
							value="${officialConfig.companyAddress}" 
							size="80" 
						/>
					</td>
				</tr>
				
				<tr>
					<th scope="row" ><ikep4j:message pre='${preForm}' key='companyMail'/></th>
					<td>
						<input 
							type="text" 
							class="inputbox" 
							id="companyMail" 
							name="companyMail" 
							title="<ikep4j:message pre='${preForm}' key='companyMail'/>" 
							value="" 
						/>
						&nbsp;
						<input 
							type="checkbox" 
							class="checkbox" 
							name="isMail" 
							title="<ikep4j:message pre='${preForm}' key='use'/>" 
							value="1" 
						/>
						<ikep4j:message pre='${preForm}' key='use'/>
					</td>
					
					<th scope="row" ><ikep4j:message pre='${preForm}' key='inCharge'/></th>
					<td>
						<input 
							type="text" 
							class="inputbox" 
							id="inCharge" 
							name="inCharge" 
							title="<ikep4j:message pre='${preForm}' key='inCharge'/>" 
							value="" 
						/>
						&nbsp;
						<input 
							type="checkbox" 
							class="checkbox" 
							name="isInCharge" 
							title="<ikep4j:message pre='${preForm}' key='use'/>" 
							value="1" 
						/>
						<ikep4j:message pre='${preForm}' key='use'/>
					</td>
				</tr>
				
			</tbody>
		</table>
	
	</div>
	<!--//blockDetail End-->
	
	<span><ikep4j:message pre='${preForm}' key='apprInfo'/></span>
		
	<!--blockDetail Start-->
	<div class="blockDetail">
		
		<table summary="<ikep4j:message pre='${preForm}' key='apprInfo'/>">
			<caption></caption>
			<colgroup>
				<col width="15%"/>
				<col width="35%"/>
				<col width="15%"/>
				<col width="35%"/>
			</colgroup>		
			<tbody>
			
				
				<tr>
					<th scope="row" ><ikep4j:message pre='${preForm}' key='officialDocNo'/></th>
					<td>
						<input 
							type="text" 
							class="inputbox" 
							id="officialDocNo" 
							name="officialDocNo" 
							title="<ikep4j:message pre='${preForm}' key='officialDocNo'/>" 
							value="" 
						/>
					</td>
					
					<th scope="row" ><ikep4j:message pre='${preForm}' key='isSeal'/></th>
					<td>
						<input 
							type="radio" 
							class="radio" 
							name="isSeal" 
							title="<ikep4j:message pre='${preForm}' key='visible'/>" 
							value="1" 
							checked="checked"
						/>
						<ikep4j:message pre='${preForm}' key='visible'/>&nbsp;
						<input 
							type="radio" 
							class="radio" 
							name="isSeal" 
							title="<ikep4j:message pre='${preForm}' key='invisible'/>" 
							value="0" 
						/>
						<ikep4j:message pre='${preForm}' key='invisible'/>&nbsp;
					</td>
				</tr>
				
				<tr>
					<th scope="row" ><ikep4j:message pre='${preForm}' key='receiver'/></th>
					<td colspan="3">
						<input 
							type="text" 
							class="inputbox" 
							id="receiver" 
							name="receiver" 
							title="<ikep4j:message pre='${preForm}' key='receiver'/>" 
							value="" 
							size="40" 
						/>
					</td>
				</tr>
				
				<tr>
					<th scope="row" ><ikep4j:message pre='${preForm}' key='reference'/></th>
					<td colspan="3">
						<input 
							type="text" 
							class="inputbox" 
							id="reference" 
							name="reference" 
							title="<ikep4j:message pre='${preForm}' key='reference'/>" 
							value="" 
							size="40" 
						/>
					</td>
				</tr>
				
				<tr>
					<th scope="row" ><ikep4j:message pre='${preForm}' key='title'/></th>
					<td colspan="3">
						<input 
							type="text" 
							class="inputbox" 
							id="title" 
							name="title" 
							title="<ikep4j:message pre='${preForm}' key='title'/>" 
							value="" 
							size="80" 
						/>
					</td>
				</tr>
				
				
			</tbody>
		</table>
		
	</div>
	<!--//blockDetail End-->
	
	<c:if test="${not empty apprForm.formLayoutData}">
	<div class="blockDetail" id="layoutTableDiv" style="border-top:1px solid #e0e0e0;">
		<table id="layoutTable" summary="기본정보">
			<caption></caption>
			<tbody>
			</tbody>
			<tfoot>
            </tfoot>
		</table>
	</div>
	</c:if>
	<c:if test="${apprForm.isApprEditor eq 1}">
	<div class="blockDetail">
		<table id="editorTable" summary="기본정보">
			<caption></caption>
			<tbody>
			    <tr>
					<td colspan="2">
					    <spring:bind path="apprDoc.formEditorData">
					    <textarea name="${status.expression}"  id="${status.expression}" class="inputbox w100 fullEditor">${status.value}</textarea>
					    </spring:bind>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	</c:if>
	
</form>
<form id="editorFileUploadParameter" action="null" method="post"> 
    <spring:bind path="apprDoc.apprId">
    <input type="hidden" name="${status.expression}" value="${status.value}"/>
    </spring:bind>
    <input name="interceptorKey"  value="ikep4.system" type="hidden"/> 
</form>	    
	
	<div class="clear"></div>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="saveBtn"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			<li><a id="cancelBtn"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
	
	

<!--//mainContents End-->