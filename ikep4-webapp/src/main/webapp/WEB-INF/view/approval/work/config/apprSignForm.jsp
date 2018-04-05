<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.work.apprsign.form.header" />
<c:set var="preForm"  	value="ui.approval.work.apprsign.form" />
<c:set var="preValidation" value="ui.approval.work.apprsign.validation" />
<c:set var="preButton"  value="ui.approval.common.button" />
<c:set var="preMessage" value="ui.approval.common.message" />
<c:set var="preIcon"  	value="ui.approval.common.icon" />
<c:set var="preMessage2" value="ui.support.fileupload.message" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>

<script type="text/javascript">
	<!--
	
	(function($){
		
		clearDefault = function() { 
			
			if(!confirm('<ikep4j:message pre='${preMessage}' key='release' />')) {
				return;
			}
				  
			$jq.ajax({     
				url : '<c:url value="/approval/work/config/clearApprSignDefault.do" />',     
				data :  {},     
				type : "post",     
				success : function(result) {         
					getHistoryList();
					$jq('#defaultSignImage').attr('src', "") ;
				},
				error : function(event, request, settings){
					 alert("error");
				}
			});
		};
		
		/* changeDefault = function(signId) { 
			
			if(!confirm('<ikep4j:message pre='${preForm}' key='confirm.changeDefault' />')) {
				return;
			}
				  
			$jq.ajax({     
				url : '<c:url value="/approval/work/config/changeApprSignDefault.do" />',     
				data :  {signId:signId},     
				type : "post",     
				success : function(result) {         
					//getList();
					$jq('#defaultSignImage').attr('src', "<c:url value='/support/fileupload/downloadFile.do?fileId=${defaultSignFileId}' />") ;
				},
				error : function(event, request, settings){
					 alert("error");
				}
			});
		}; */
		
		
		/* getList = function() { 
			
			$jq.ajax({     
				url : '<c:url value="/approval/work/config/selectApprSign.do" />',     
				data :  {},     
				type : "post",     
				success : function(result) {         
					$jq("#listDiv").html(result);
				},
				error : function(event, request, settings){
					 alert("error");
				}
			});
		}; */
		
		fileUploadForSign = function(userId, targetId, imgSrc, callback) { 

			var options = {
					title : "Sign Image Upload",
					url : iKEP.getContextRoot() + '/support/fileupload/uploadFormForSign.do?userId='+userId+'&targetId='+targetId+"&editorAttachYn=0&imageYn=1",
					width : 550,
					height : 170,
					modal : true,
					resizable : false,
					scroll : "no",
					params : { imgSrc : imgSrc },
					callback: function(result) {
						callback(result.status, result.fileId, result.fileName, result.messgage, result.gubun);
					}
				};
			iKEP.showDialog(options);
		};
		
		function afterFileUpload(status, fileId, fileName, message, gubun){
			
			$jq.ajax({     
				url : '<c:url value="/approval/work/config/saveApprSign.do" />',     
				data :  {fileId:fileId},     
				type : "post",     
				success : function(result) {         
					getHistoryList();
					$jq('#defaultSignImage').attr('src', "<c:url value='/support/fileupload/downloadFile.do?fileId=' />"+fileId) ;
				},
				error : function(event, request, settings){
					 alert("error");
				}
			});
		};
		
		getHistoryList = function() {
			
			$jq.ajax({     
				url : "<c:url value='/approval/work/config/apprSignList.do'/>",     
				data :  $jq("#historyForm").serialize(),     
				type : "post",     
				success : function(result) {         
					$jq("#historyListDiv").html(result);
				} 
			});       
			
		};
		
		$(document).ready(function(){
			
			$jq("#btnAdd").click(function(){
				
				fileUploadForSign('','', '', afterFileUpload);
				
			});
			
			$jq("#btnDelete").click(function() {  
				
				clearDefault();
				
				/* var itemIds = new Array();
				
				$("#searchForm input[name=chkSignId]:checked").each(function(index) { 
					itemIds.push($(this).val()); 
				});			 
				
				if(itemIds.length > 0) {
					if(confirm("<ikep4j:message pre="${preMessage}" key="delete" />")) {
						
						$jq.ajax({     
							url : '<c:url value="/approval/work/config/deleteApprSign.do" />',     
							data :  {signIds:itemIds.toString()},     
							type : "post",     
							success : function(result) {         
								getList();
							},
							error : function(event, request, settings){
								 alert("error");
							}
						});
					}  
				} */
			});   
			
			//getList();
			getHistoryList();
		
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

	
	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${preForm}' key='summary'/>">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="18%"><ikep4j:message pre='${preForm}' key='signImage' /></th>
					<td>
						<div class="prPhoto2_Pop_Img">
							<img id="defaultSignImage" src="<c:url value='/support/fileupload/downloadFile.do?fileId=${sign.signFileId}'/>"  width="100%" alt="<ikep4j:message pre='${preForm}' key='signImage' />" onerror="this.src='<c:url value="/base/images/common/noimage_50x50.gif"/>'" />
						</div>
						
						<div class="clear" style="height:5px"></div>
						
						<div class="directive"> 
							<ul>
								<li><span><ikep4j:message pre="${preMessage2}" key="file.sign.msg1" /></span></li>	
								<li><span><ikep4j:message pre="${preMessage2}" key="file.sign.msg2" /></span></li>	
								<li><span><ikep4j:message pre="${preMessage2}" key="file.sign.msg3" /></span></li>	
								<li><span><ikep4j:message pre="${preMessage2}" key="file.sign.msg4" /></span></li>	
							</ul>
						</div>
						
					</td>
				</tr>
			</tbody>
		</table>
		
	</div>
	<!--//blockDetail End-->
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="btnAdd" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='create' /></span></a></li>
			<li><a id="btnDelete" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='release' /></span></a></li>							
		</ul>
	</div>
	<!--//blockButton End-->
	
	<div class="clear"></div>
	
	
	<div id="historyListDiv"></div>
	
	
	<div class="clear"></div>
		

