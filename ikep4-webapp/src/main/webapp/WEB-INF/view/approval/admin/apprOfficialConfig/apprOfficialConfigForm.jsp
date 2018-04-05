<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.admin.apprOfficialConfig.header" />
<c:set var="preForm"  	value="ui.approval.admin.apprOfficialConfig" />
<c:set var="preValidation" value="ui.approval.admin.apprOfficialConfig.validation" />
<c:set var="preButton"  value="ui.approval.common.button" />
<c:set var="preMessage" value="ui.approval.common.message" />
<c:set var="preIcon"  	value="ui.approval.common.icon" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript">
	<!--
	
	
	(function($){
		
		/* var validOptions = {
		    rules : {
		    	companyName :    {
		            required : true,
		            maxlength : 100
		        },
		        companyZipCode :    {
		            required : true,
		            maxlength : 10
		        },
		        companyTel :    {
		            required : true,
		            maxlength : 30
		        },
		        companyFax :    {
		            required : true,
		            maxlength : 30
		        },
		        companyAddress :    {
		            required : true,
		            maxlength : 100
		        }
		    },
		    messages : {
		    	companyName : {
		            direction : "bottom",
		            required : "<ikep4j:message key="NotEmpty.companyName" />",
		            maxlength : "<ikep4j:message key="Size.companyName" />"
		        },
		        companyZipCode : {
		            direction : "bottom",
		            required : "<ikep4j:message key="NotEmpty.companyZipCode" />",
		            maxlength : "<ikep4j:message key="Size.companyZipCode" />",
		            zipcode : "<ikep4j:message key="Pattern.companyZipCode" />"
		        },
		        companyTel : {
		            direction : "bottom",
		            required : "<ikep4j:message key="NotEmpty.companyTel" />",
		            maxlength : "<ikep4j:message key="Size.companyTel" />",
		            phone : "<ikep4j:message key="Pattern.companyTel" />"
		        },
		        companyFax : {
		            direction : "bottom",
		            required : "<ikep4j:message key="NotEmpty.companyFax" />",
		            maxlength : "<ikep4j:message key="Size.companyFax" />",
		            phone : "<ikep4j:message key="Pattern.companyFax" />"
		        },
		        companyAddress : {
		            direction : "bottom",
		            required : "<ikep4j:message key="NotEmpty.companyAddress" />",
		            maxlength : "<ikep4j:message key="Size.companyAddress" />"
		        }
		    },
		    submitHandler : function(form) {
		    	saveForm();
		    }
		 }; */
		
		saveForm = function() {
			
			if(confirm("<ikep4j:message pre="${preMessage}" key="save" />")) {
				
				$jq.ajax({     
					url : '<c:url value="/approval/admin/apprOfficialConfig/apprOfficialConfigSave.do" />',     
					data :  $jq("#searchForm").serialize(),     
					type : "post",     
					success : function(result) {         
						alert("<ikep4j:message pre="${preMessage}" key="saveSuccess" />");
					},
					error : function(event, request, settings){
						 alert("error");
					}
				});
			}  
			
		};
		
		deleteImgFile = function(type) {
			
			if(confirm("<ikep4j:message pre="${preMessage}" key="delete" />")) {	
				$jq.ajax({     
					url : '<c:url value="/approval/admin/apprOfficialConfig/deleteImgFile.do" />',     
					data :  {configId:'${officialConfig.configId}',type:type},     
					type : "post",     
					success : function(result) {         
						alert("<ikep4j:message pre="${preMessage}" key="saveSuccess" />");
						if(type=='header') {
							$jq('#headerImg').attr('src', "") ;
						}
						else if(type=='footer') {
							$jq('#footerImg').attr('src', "") ;
						}
						else if(type=='seal') {
							$jq('#sealImg').attr('src', "") ;
						}
					},
					error : function(event, request, settings){
						 alert("error");
					}
				});
			}
		};
		
		afterHeaderImgUpload = function(status, fileId, fileName, message, targetId) {
			$jq("input[name=headerImgId]").val(fileId);
			$jq('#headerImg').attr('src', "<c:url value='/support/fileupload/downloadFile.do?fileId=' />"+fileId);
		};
		
		afterFooterImgUpload = function(status, fileId, fileName, message, targetId) {
			$jq("input[name=footerImgId]").val(fileId);
			$jq('#footerImg').attr('src', "<c:url value='/support/fileupload/downloadFile.do?fileId=' />"+fileId);
		};
		
		afterSealImgUpload = function(status, fileId, fileName, message, targetId) {
			$jq("input[name=sealId]").val(fileId);
			$jq('#sealImg').attr('src', "<c:url value='/support/fileupload/downloadFile.do?fileId=' />"+fileId);
		};
		
		setShowHideDiv = function() {
			
			if('${officialConfig.headerType}' == '1') {
				$("input[name=headerType]").filter("input[value=1]").attr("checked","checked");
				$jq("#headerTitleDiv").hide();
				$jq("#headerImgDiv").show();
			}
			else {
				$("input[name=headerType]").filter("input[value=0]").attr("checked","checked");
				$jq("#headerTitleDiv").show();
				$jq("#headerImgDiv").hide();
			}
			
			if('${officialConfig.footerType}' == '1') {
				$("input[name=footerType]").filter("input[value=1]").attr("checked","checked");
				$jq("#footerTitleDiv").hide();
				$jq("#footerImgDiv").show();
			}
			else {
				$("input[name=footerType]").filter("input[value=0]").attr("checked","checked");
				$jq("#footerTitleDiv").show();
				$jq("#footerImgDiv").hide();
			}
			
			if('${officialConfig.headerAlign}' == '') {
				$("input[name=headerAlign]").filter("input[value=1]").attr("checked","checked");
			}
			
			if('${officialConfig.footerAlign}' == '') {
				$("input[name=footerAlign]").filter("input[value=1]").attr("checked","checked");
			}
			
		};
		
		$(document).ready(function(){
			
			$jq("#apprOfficialConfigOfLeft").click();
			
			$jq("#saveBtn").click(function() {  
				//$jq("#searchForm").submit();
				saveForm();
			});  
			
			$jq("#headerImgBtn").click(function(event) { 
				iKEP.fileUpload(event.target.id, '0', '1', afterHeaderImgUpload);	
			}); 
			
			$jq("#footerImgBtn").click(function(event) { 
				iKEP.fileUpload(event.target.id, '0', '1', afterFooterImgUpload);	
			}); 
			
			$jq("#sealImgBtn").click(function(event) { 
				iKEP.fileUpload(event.target.id, '0', '1', afterSealImgUpload);	
			}); 
			
			$jq("#headerImgDeleteBtn").click(function(event) { 
				deleteImgFile('header');
			}); 
			
			$jq("#footerImgDeleteBtn").click(function(event) { 
				deleteImgFile('footer');
			}); 
			
			$jq("#sealImgDeleteBtn").click(function(event) { 
				deleteImgFile('seal');
			}); 
			
			$jq("input[name=headerType]").change(function(event) { 
				if($jq("input[name=headerType]:checked").val() == '0') {
					$jq("#headerTitleDiv").show();
					$jq("#headerImgDiv").hide();
				}
				else {
					$jq("#headerTitleDiv").hide();
					$jq("#headerImgDiv").show();
				}
			});
			
			$jq("input[name=footerType]").change(function(event) { 
				if($jq("input[name=footerType]:checked").val() == '0') {
					$jq("#footerTitleDiv").show();
					$jq("#footerImgDiv").hide();
				}
				else {
					$jq("#footerTitleDiv").hide();
					$jq("#footerImgDiv").show();
				}
			});
			
			setShowHideDiv();
			
			//new iKEP.Validator("#searchForm", validOptions);
			
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

<form id="searchForm" name="searchForm" method="post" action="" onsubmit="return false;">
	
	<input type="hidden" id="configId" name="configId" value="${officialConfig.configId}" title="configId"/>
	
	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${preForm}' key='summary'/>">
			<caption></caption>
			<colgroup>
				<col width="15%"/>
				<col width="35%"/>
				<col width="15%"/>
				<col width="35%"/>
			</colgroup>		
			<tbody>
			
				<tr>
					<th scope="row" ><ikep4j:message pre='${preForm}' key='headerTitle'/></th>
					<td colspan="3">
						<input 
							type="radio" 
							class="radio" 
							name="headerType" 
							title="<ikep4j:message pre='${preForm}' key='headerType'/>" 
							value="0" 
							<c:if test="${officialConfig.headerType eq 0}">checked="checked"</c:if>
						/>
						<ikep4j:message pre='${preForm}' key='txt'/>&nbsp;
						<input 
							type="radio" 
							class="radio" 
							name="headerType" 
							title="<ikep4j:message pre='${preForm}' key='headerType'/>" 
							value="1" 
							<c:if test="${officialConfig.headerType eq 1}">checked="checked"</c:if>
						/>
						<ikep4j:message pre='${preForm}' key='img'/>&nbsp;
						
						<div id="headerTitleDiv">
						<input 
							type="text" 
							class="inputbox" 
							id="headerTitle" 
							name="headerTitle" 
							title="<ikep4j:message pre='${preForm}' key='headerTitle'/>" 
							value="${officialConfig.headerTitle}" 
							size="100" 
						/>
						</div>
						
						<div id="headerImgDiv">
						<input type="hidden" id="headerImgId" name="headerImgId" value="${officialConfig.headerImgId}" title="headerImgId"/>
						<img id="headerImg" style="margin:0px 0px -6px 0px"
							src="<c:url value='/support/fileupload/downloadFile.do?fileId=${officialConfig.headerImgId}' />" 
							alt="<ikep4j:message pre='${preForm}' key='headerTitle' />" 
							onerror="this.src='<c:url value="/base/images/common/noimage_90x70.gif"/>'"
						/>
						<a id="headerImgBtn" href="#a" class="button_s"><span><ikep4j:message pre='${preButton}' key='imgupload'/></span></a>
						<a id="headerImgDeleteBtn" href="#a" class="button_s"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
						</div>
							
					</td>
				</tr>
				
				<tr>
					<th scope="row" ><ikep4j:message pre='${preForm}' key='headerAlign'/></th>
					<td>
						<input 
							type="radio" 
							class="radio" 
							name="headerAlign" 
							title="<ikep4j:message pre='${preForm}' key='headerAlign'/>" 
							value="0" 
							<c:if test="${officialConfig.headerAlign eq 0}">checked="checked"</c:if>
						/>
						<ikep4j:message pre='${preForm}' key='alignLeft'/>&nbsp;
						<input 
							type="radio" 
							class="radio" 
							name="headerAlign" 
							title="<ikep4j:message pre='${preForm}' key='headerAlign'/>" 
							value="1" 
							<c:if test="${officialConfig.headerAlign eq 1}">checked="checked"</c:if>
						/>
						<ikep4j:message pre='${preForm}' key='alignCenter'/>&nbsp;
						<input 
							type="radio" 
							class="radio" 
							name="headerAlign" 
							title="<ikep4j:message pre='${preForm}' key='headerAlign'/>" 
							value="2" 
							<c:if test="${officialConfig.headerAlign eq 2}">checked="checked"</c:if>
						/>
						<ikep4j:message pre='${preForm}' key='alignRight'/>&nbsp;
					</td>
					
					<th scope="row" ><ikep4j:message pre='${preForm}' key='headerHeight'/></th>
					<td>
						<input 
							type="text" 
							class="inputbox" 
							id="headerHeight" 
							name="headerHeight" 
							title="<ikep4j:message pre='${preForm}' key='headerHeight'/>" 
							value="${officialConfig.headerHeight}" 
						/>
						px
					</td>
				</tr>
				
				<tr>
					<th scope="row" ><ikep4j:message pre='${preForm}' key='footerTitle'/></th>
					<td colspan="3">
						<input 
							type="radio" 
							class="radio" 
							name="footerType" 
							title="<ikep4j:message pre='${preForm}' key='footerType'/>" 
							value="0" 
							<c:if test="${officialConfig.footerType eq 0}">checked="checked"</c:if>
						/>
						<ikep4j:message pre='${preForm}' key='txt'/>&nbsp;
						<input 
							type="radio" 
							class="radio" 
							name="footerType" 
							title="<ikep4j:message pre='${preForm}' key='footerType'/>" 
							value="1" 
							<c:if test="${officialConfig.footerType eq 1}">checked="checked"</c:if>
						/>
						<ikep4j:message pre='${preForm}' key='img'/>&nbsp;
						
						<div id="footerTitleDiv">
						<input 
							type="text" 
							class="inputbox" 
							id="footerTitle" 
							name="footerTitle" 
							title="<ikep4j:message pre='${preForm}' key='footerTitle'/>" 
							value="${officialConfig.footerTitle}" 
							size="100" 
						/>
						</div>
						
						<div id="footerImgDiv">
						<input type="hidden" id="footerImgId" name="footerImgId" value="${officialConfig.footerImgId}" title="footerImgId"/>
						<img id="footerImg" style="margin:0px 0px -6px 0px"
							src="<c:url value='/support/fileupload/downloadFile.do?fileId=${officialConfig.footerImgId}' />" 
							alt="<ikep4j:message pre='${preForm}' key='footerTitle' />" 
							onerror="this.src='<c:url value="/base/images/common/noimage_90x70.gif"/>'"
						/>
						<a id="footerImgBtn" href="#a" class="button_s"><span><ikep4j:message pre='${preButton}' key='imgupload'/></span></a>
						<a id="footerImgDeleteBtn" href="#a" class="button_s"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
						</div>
							
					</td>
				</tr>
				
				<tr>
					<th scope="row" ><ikep4j:message pre='${preForm}' key='footerAlign'/></th>
					<td>
						<input 
							type="radio" 
							class="radio" 
							name="footerAlign" 
							title="<ikep4j:message pre='${preForm}' key='footerAlign'/>" 
							value="0" 
							<c:if test="${officialConfig.footerAlign eq 0}">checked="checked"</c:if>
						/>
						<ikep4j:message pre='${preForm}' key='alignLeft'/>&nbsp;
						<input 
							type="radio" 
							class="radio" 
							name="footerAlign" 
							title="<ikep4j:message pre='${preForm}' key='footerAlign'/>" 
							value="1" 
							<c:if test="${officialConfig.footerAlign eq 1}">checked="checked"</c:if>
						/>
						<ikep4j:message pre='${preForm}' key='alignCenter'/>&nbsp;
						<input 
							type="radio" 
							class="radio" 
							name="footerAlign" 
							title="<ikep4j:message pre='${preForm}' key='footerAlign'/>" 
							value="2" 
							<c:if test="${officialConfig.footerAlign eq 2}">checked="checked"</c:if>
						/>
						<ikep4j:message pre='${preForm}' key='alignRight'/>&nbsp;
					</td>
					
					<th scope="row" ><ikep4j:message pre='${preForm}' key='footerHeight'/></th>
					<td>
						<input 
							type="text" 
							class="inputbox" 
							id="footerHeight" 
							name="footerHeight" 
							title="<ikep4j:message pre='${preForm}' key='footerHeight'/>" 
							value="${officialConfig.footerHeight}" 
						/>
						px
					</td>
				</tr>
				
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
							size="50" 
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
							size="100" 
						/>
					</td>
				</tr>
				
				<tr>
					<th scope="row" ><ikep4j:message pre='${preForm}' key='sealId'/></th>
					<td colspan="3">
						<input type="hidden" id="sealId" name="sealId" value="${officialConfig.sealId}" title="sealId"/>
						<img id="sealImg" style="margin:0px 0px -6px 0px"
							src="<c:url value='/support/fileupload/downloadFile.do?fileId=${officialConfig.sealId}' />" 
							alt="<ikep4j:message pre='${preForm}' key='sealId' />" 
							onerror="this.src='<c:url value="/base/images/common/noimage_90x70.gif"/>'"
						/>
						<a id="sealImgBtn" href="#a" class="button_s"><span><ikep4j:message pre='${preButton}' key='imgupload'/></span></a>
						<a id="sealImgDeleteBtn" href="#a" class="button_s"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
						
						<div class="clear" style="height:5px"></div>
						
						<div class="directive"> 
							<ul>
								<li><span><ikep4j:message pre="${preForm}" key="msg1" /></span></li>	
								<li><span><ikep4j:message pre="${preForm}" key="msg2" /></span></li>	
								<li><span><ikep4j:message pre="${preForm}" key="msg3" /></span></li>	
							</ul>
						</div>
						
					</td>
				</tr>
				
			</tbody>
		</table>
		
	</div>
	<!--//blockDetail End-->
	
	<div class="clear"></div>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="saveBtn"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
	
</form>			

<!--//mainContents End-->