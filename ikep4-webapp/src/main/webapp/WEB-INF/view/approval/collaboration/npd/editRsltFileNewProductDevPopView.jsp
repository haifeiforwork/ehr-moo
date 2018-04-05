<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 
<%@ include file="/base/common/DextfileUploadInit.jsp"%><%-- 파일업로드용 Import --%>
<%@ page import="destiny.link.slo.service.DestinySLO"%>

<%!
public void setSystemProperty( String key, String value) {
    System.setProperty( key, value);
}

public void setCookie( HttpServletResponse response, String key, String value) {
    Cookie cookie = new Cookie( key, value);
    cookie.setPath( "/");
    cookie.setMaxAge( -1);
    cookie.setVersion( 0);
    //cookie.setComment( "destiny slo test");
    cookie.setSecure( false);
    response.addCookie( cookie);
}
%>

<%
// ECM Server Address
String sloServerAddress = "http://ecm.moorim.co.kr";
String sloAPIKey = "0VbXsZYobdOnciJmv4GQ3h16EvOjAoF0icK5sHMSvX4=";

// GW Login User Account
String userAccount = ((com.lgcns.ikep4.support.user.member.model.User)session.getAttribute("ikep.user")).getUserId(); // 로그인 사용자ID

// GW Login User's GroupCode
String userGroupCode = ((com.lgcns.ikep4.support.user.member.model.User)session.getAttribute("ikep.user")).getGroupId(); // 로그인 사용자 부서코드

// ECM Settings
setSystemProperty( "common.SloAddrKey", sloServerAddress);
setSystemProperty( "common.SloAPIKey", sloAPIKey);
setCookie( response, "ACCOUNT", userAccount);
setCookie( response, "GROUP_CODE", userGroupCode);
%>

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preButton"  	value="ui.approval.common.button" />
<c:set var="preMessage"    	value="ui.approval.common.message" />
<c:set var="preDefault"     value="ui.approval.collaboration.default" />
<c:set var="preMessage2"    value="ui.approval.collaboration.message" />
<c:set var="preTitle"    	value="ui.approval.npd.view.title" />
<c:set var="preButton2"  	value="ui.approval.collaboration.button" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript">

	var fileCnt = 0;
	
	function CyberdigmpopupSelect( action) { 
		var iframe = document.getElementById( "select_dialog");
		var callbackFn = 'CyberdigmselectItem';
	
		//팝업 설정( 해당 다이얼로그에 맞도록 수정)
		var settings = "&settings=width:665,height:480,location:0,menubar:0,resizable:0,scrollbars:0,status:0,toolbar:0";
		iframe.src = "<c:url value='/base/common/destinySLO.jsp?TARGET_URL=popup&action='/>" + action + "&callBack=" + callbackFn + settings;
	}
	
	function CyberdigmselectItem( _p, type) {
		var data = eval( "(" + decodeURIComponent( _p) + ")");
	
		for( var i = 0; i < data.length; i++){
	
	    	//내부URL(에이전트 설치 시)
	        var fileUrl1 = "http://127.0.0.1:36482/viewFile?fileName=" + encodeURIComponent( data[i].fileName) + "&docID=" + data[i].targetOID + "&fileID_=" + data[i].OID + "&history=true&overWrite=true&recently=true&clientType=I";
	
	    	//외부URL(에이전트 미설치 시)
	
	    	var index = data[i].fileFullPath.indexOf( '?');
	
	    	var str = data[i].fileFullPath.substring( index + 1, data[i].fileFullPath.length);
	
	    	//개발서버
	        //var fileUrl2 = data[i].fileFullPath;
	
	    	//운영
	        var fileUrl2 =  "http://ecm.moorim.co.kr:80/servlet/blob?" + str;
	
	    	//모바일용 외부 URL
	    	var fileUrl3 = data[i].fileFullPath;
	
	        fileCnt++;
	        
	        $jq("#ecmTb").append(
					"<tr id=\"ecmTr_"+data[i].OID+"\">"+
					"<td><input name=\"ecmCheck\" type=\"checkbox\" class=\"checkbox\" value=\""+data[i].OID+"\" /></td>"+
						"<td><input name=\"ecmFileName_"+data[i].OID+"\" id=\"ecmFileName_"+data[i].OID+"\" type=\"text\" value=\""+data[i].fileName+"\" class=\"inputbox w100\" readonly=\"readonly\" /></td>"+
						"<td><input name=\"ecmFileUrl1_"+data[i].OID+"\" id=\"ecmFileUrl1_"+data[i].OID+"\" type=\"text\" value=\""+fileUrl1.replaceAll("'","")+"\" class=\"inputbox w100\" readonly=\"readonly\" /></td>"+
						"<input name=\"ecmFileUrl2_"+data[i].OID+"\" id=\"ecmFileUrl2_"+data[i].OID+"\" type=\"hidden\" value=\""+fileUrl2.replaceAll("'","")+"\" />"+
						"<input name=\"ecmFilePath_"+data[i].OID+"\" id=\"ecmFilePath_"+data[i].OID+"\" type=\"hidden\" value=\""+data[i].fileFullPath+"\" />"+
						"<input name=\"ecmFileId_"+data[i].OID+"\" id=\"ecmFileId_"+data[i].OID+"\" type=\"hidden\" value=\""+data[i].OID+"\" />"+
					"</tr>"
				);
	    }
	    
	}
	
	<!--
	var dialogWindow = null;
	var fnCaller;
	var validator;
	var ecmFlg = "N";
	(function($){
		var dblClick=false;
		
		window.parent.$('.ui-dialog-titlebar-close').hide();
		
		fnCaller = function (params, dialog) {
			if(params) {
			}

			dialogWindow = dialog;
			$(".cancelButton").click(function() {
				dialogWindow.callback("file");
				$("#normalFileTb").show();
				dialogWindow.close();
			});
		};
		ecmfileDelete = function(){
		    $("#editForm input[name=ecmCheck]:checked").each(function(index) { 
				$jq("#ecmTr_"+$(this).val()).remove();
			});

		};
		
		$(document).ready(function(){
			<c:if test="${ecmrole && user.essAuthCode != 'E9'}">
			$("#ecmBtn").show();
	     	$("#ecmTb").show();
			var xmlhttp; 
			  
		    if(window.XMLHttpRequest){ // code for IE7+, Firefox, Chrome, Opera, Safari 
		        xmlhttp=new XMLHttpRequest(); 
		    }else{ // code for IE6, IE5 
		        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); 
		    } 
		    xmlhttp.open("POST","http://127.0.0.1:36482/",true); 
		    //Request에 따라서 적절한 헤더 정보를 보내준다 
		    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded"); 
		    xmlhttp.send(); 
		  
		    xmlhttp.onreadystatechange=function(){ 
		    	if (xmlhttp.readyState==4 && xmlhttp.status==200){ 
		        	 //$("#normalFileTb").hide();
		          	 //$("#ecmBtn").show();
		          	 //$("#ecmTb").show();
		          	ecmFlg = "Y";
		        }else if(xmlhttp.status==12029){ 
		          	 $("#ecmBtn").hide();
		          	 $("#ecmTb").hide();
		          	 $("#normalFileTb").show();
				} 
		    } 
		    </c:if>
		    <c:if test="${!ecmrole || user.essAuthCode == 'E9'}">
		    	$("#normalFileTb").show();
		    </c:if>
			
			// 저장
			$("#saveButton").click(function() {
				var tempEcmFileId = "";  
				var tempEcmFileName = ""; 
				var tempEcmFilePath = "";  
				var tempEcmFileUrl1 = "";    
				var tempEcmFileUrl2 = ""; 
				var ecmFileCnt = 0;
				var ecmFileId = "";
				var ecmFileName = "";
				var ecmFilePath = "";
				var ecmFileUrl1 = "";
				var ecmFileUrl2 = "";
				
				
				$("#npdForm input[name=ecmCheck]").attr("checked", true);  

				$("#npdForm input[name=ecmCheck]").each(function(index) { 
					tempEcmFileId = $("#ecmFileId_"+$(this).val()).val();  
					tempEcmFileName = $("#ecmFileName_"+$(this).val()).val(); 
					tempEcmFilePath = $("#ecmFilePath_"+$(this).val()).val();   
					tempEcmFileUrl1 = $("#ecmFileUrl1_"+$(this).val()).val();     
					tempEcmFileUrl2 = $("#ecmFileUrl2_"+$(this).val()).val();  
					if(ecmFileCnt == 0){
						ecmFileId = tempEcmFileId;
						ecmFileName = tempEcmFileName;
						ecmFilePath = tempEcmFilePath;
						ecmFileUrl1 = tempEcmFileUrl1;
						ecmFileUrl2 = tempEcmFileUrl2;
					}else{
						ecmFileId = ecmFileId+"|"+tempEcmFileId;
						ecmFileName = ecmFileName+"|"+tempEcmFileName;
						ecmFilePath = ecmFilePath+"|"+tempEcmFilePath;
						ecmFileUrl1 = ecmFileUrl1+"|"+tempEcmFileUrl1;
						ecmFileUrl2 = ecmFileUrl2+"|"+tempEcmFileUrl2;
					}
					ecmFileCnt++;
				});
				
				$jq('input[name=ecmFileId]').val(ecmFileId);  
				$jq('input[name=ecmFileName]').val(ecmFileName); 
				$jq('input[name=ecmFilePath]').val(ecmFilePath);  
				$jq('input[name=ecmFileUrl1]').val(ecmFileUrl1);    
				$jq('input[name=ecmFileUrl2]').val(ecmFileUrl2);
				
				$("#editForm").submit();
				return false; 
			});
			
			// [validation] ============================================= Start			
			var validOptions = {
				
			    submitHandler : function(form) {

					if (!confirm("<ikep4j:message pre='${preMessage}' key='save'/>")) {
						return;
					}
					
					if(ecmFlg == "N"){
						
		    			if((oldSizes !=document.getElementById("FileUploadManager").Size)||(document.all["FileUploadManager"].Count>0)){//삭제한것이 있거나 추가한것이 있으면
			    			btnTransfer_Onclick("editForm");
			    		}else{
			    			//alert("파일업로드 없음");
			    			// oldFileSetting(oldFiles , form);
			    			writeSubmit(form);
			    		}
		    		} else {
		    			writeSubmit(form);
		    		}
				}
			  	
			};
			validator = new iKEP.Validator("#editForm", validOptions);
			
			writeSubmit = function(targetForm){
				
				$jq.ajax({
					url : iKEP.getContextRoot() + "/approval/collaboration/npd/ajaxRsltFileNewProductDev.do",
					type : "post",
					data : $("#editForm").serialize(),
					loadingElement : "#saveButton",
					success : function(data, textStatus, jqXHR) {
						alert("<ikep4j:message pre='${preMessage2}' key='suceess' />");
						$("#normalFileTb").show();
						dialogWindow.callback();
						dialogWindow.close();
						return false;
					},
					error : function(jqXHR, textStatus, errorThrown) {
						
						var arrException = $jq.parseJSON(jqXHR.responseText).exception;
						alert(arrException[0].defaultMessage);
					}
				});
			};
			
		    if(ecmFlg == "N"){
		    	<c:if test="${npdPermission.rsltFileBtnActive eq true}">
		    	dextFileUploadInit("${newProductDev.fileSize}" ,"${newProductDev.fileAttachOption}", "${newProductDev.allowType}");
					var oldFiles;
					var oldSizes;
					<c:if test="${not empty fileDataListJson}"> 
						oldFiles = ${fileDataListJson};
						$jq.each(oldFiles,function(index){
							var fileInfo = $.extend({index:index}, this);
							document.getElementById("FileUploadManager").AddUploadedFile(fileInfo.fileId, fileInfo.fileRealName, fileInfo.fileSize);
						});
				   
						dextFileUploadRefresh(); 
						oldSizes =document.getElementById("FileUploadManager").Size;
				   </c:if> 
				</c:if>
		    }
		    
	 	   	$(".rsltFileBtnActive").show();
		});
		
	})(jQuery);
	//-->
</script>
	<h1 class="none"><ikep4j:message pre='${preTitle}' key="popup.create" /></h1>
	
	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre="${preTitle}" key="rsltFile" /></h2> 
	</div>
	<!--//pageTitle End-->
	
	<!--blockDetail Start-->
	<div class="blockDetail" style="width: 98%;">
		<div>
			<form id="editForm" name="editForm" method="post" action="">
				<!-- ecmFile 변수 -->	
				<input type="hidden" name="ecmFileId" value="" /> 
				<input type="hidden" name="ecmFileName" value="" />
				<input type="hidden" name="ecmFilePath" value=""/>  
				<input type="hidden" name="ecmFileUrl1" value=""/> 
				<input type="hidden" name="ecmFileUrl2" value=""/>
				
				<input type="hidden" name="mgntNo" value="${newProductDev.mgntNo }"/>
				<table summary="<ikep4j:message pre='${preView}' key='summary'/>">
					<caption></caption>
					<colgroup>
						<col width="15%;">
						<col width="*">
					</colgroup>
					<tbody>
						<c:if test="${npdPermission.rsltFileBtnActive eq true}">
						<tr class="rsltFileBtnActive" style="display:none;">
							<th><ikep4j:message pre="${preTitle}" key="rsltFile" /></th>
							<td>
								<!--  File Area Start -->
								<table style="width:100%;display:none;" id="ecmTb">
									<tr><th style="width:3%;text-align:center;"></th><th style="width:17%;text-align:center;">파일명</th><th style="width:80%;text-align:center;">URL</th></tr>
									<c:forEach var="ecmFileData" items="${newProductDev.ecmFileDataList}" varStatus="ecmFileDataStatus">
										<tr id="ecmTr_${ecmFileData.fileName}">
										<td><input name="ecmCheck" type="checkbox" class="checkbox" value="${ecmFileData.fileName}" /></td>
											<td><input name="ecmFileName_${ecmFileData.fileName}" id="ecmFileName_${ecmFileData.fileName}" type="text" value="${ecmFileData.fileRealName}" class="inputbox w100" readonly="readonly" /></td>
											<td><input name="ecmFileUrl1_${ecmFileData.fileName}" id="ecmFileUrl1_${ecmFileData.fileName}" type="text" value="${ecmFileData.fileUrl1}" class="inputbox w100" readonly="readonly" /></td>
											<input name="ecmFileUrl2_${ecmFileData.fileName}" id="ecmFileUrl2_${ecmFileData.fileName}" type="hidden" value="${ecmFileData.fileUrl2}" />
											<input name="ecmFilePath_${ecmFileData.fileName}" id="ecmFilePath_${ecmFileData.fileName}" type="hidden" value="${ecmFileData.filePath}" />
											<input name="ecmFileId_${ecmFileData.fileName}" id="ecmFileId_${ecmFileData.fileName}" type="hidden" value="${ecmFileData.fileName}" />
										</tr>
									</c:forEach>
								</table>
								
								<table style="width:100%;display:none;" id="ecmBtn">
									<tr>
										<td style="border-left:none;border-color:#e5e5e5;background-color:#e8e8e8;text-align:right;">
										<img src="<c:url value="/base/images/dextupload/btn_fileplus_normal.gif"/>" id="imgBtn_fileAdd" name="Image2"  border="0" onclick="CyberdigmpopupSelect('selectAllFiles');" style="cursor:pointer;valign:absmiddle" />
										<img src="<c:url value="/base/images/dextupload/btn_listdelete_normal.gif"/>" name="Image4"  border="0" onclick="ecmfileDelete();" style="cursor:pointer;valign:absmiddle" />	
										</td>
									</tr>
								</table>
								<table style="width:100%;display:none;" id="normalFileTb">
									<tr>
										<td colspan="2" style="border-color:#e5e5e5">
											<OBJECT id="FileUploadManager" codeBase="<c:url value="/Bin/DEXTUploadX.cab#version=2,8,2,0"/>"
												height="140" width="100%" classid="CLSID:DF75BAFF-7DD5-4B83-AF5E-692067C90316" VIEWASTEXT>
												 <param name="ButtonVisible" value="FALSE" />
												 <param name="VisibleContextMenu" value="FALSE" />
												 <param name="StatusBarVisible" value="FALSE" />
												 <param name="VisibleListViewFrame" value="FALSE" />
											</OBJECT>
										</td>
									<tr>
									<tr>
							            <td style="border-right:none;border-color:#e5e5e5;background-color:#e8e8e8">전체 <span id="_StatusInfo_count"></span>개 <span id="_StatusInfo_size"></span><span id="_Total_size"></span></div>
										<td align="right" style="border-left:none;border-color:#e5e5e5;background-color:#e8e8e8;">
										<img src="<c:url value="/base/images/dextupload/btn_fileplus_normal.gif"/>" id="imgBtn_fileAdd" name="Image2"  border="0" onclick="btnAddFile_Onclick()" style="cursor:pointer;valign:absmiddle" />
										<img src="<c:url value="/base/images/dextupload/btn_listdelete_normal.gif"/>" name="Image4"  border="0" onclick="btnDeleteItem_Onclick()" style="cursor:pointer;valign:absmiddle" />	
										</td>
									</tr>
								</table> 
							</td>
						</tr>
						</c:if>
					</tbody>
				</table>
			</form>
		</div>
		
		<div class="clear"></div>
		
		<!--blockButton Start-->
		<div class="blockButton" style="margin-top: 10px;"> 
			<ul>
				<c:if test="${npdPermission.rsltFileBtnActive eq true}">
					<li><a id="saveButton" class="button" href="#a"><span><ikep4j:message pre='${preButton2}' key='save'/></span></a></li>
					<li><a class="button cancelButton" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
				</c:if>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
	<!--//blockDetail End-->
