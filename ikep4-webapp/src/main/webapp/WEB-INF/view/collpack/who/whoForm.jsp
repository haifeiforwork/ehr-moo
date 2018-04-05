<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>


<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%pageContext.setAttribute("crlf", "\r\n"); %>

<c:set var="preUi" 			value="ui.collpack.who.whoForm" />
<c:set var="preMessage" 	value="message.collpack.who" />
<c:set var="preValidate" 	value="validate.collpack.who" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
//<![CDATA[
(function($) { 
	var chkDuplicate = false;
	var isDuplicate = false;
	afterFileUpload = function(status, fileId, fileName, message, gubun) {
		var imgsrc ="<c:url value="/support/fileupload/downloadFile.do"/>?fileId="+fileId+"&amp;profileYn=Y";
		var img ="<img id='viewImageDiv' src='"+imgsrc+"' width=100 height=100 style='vertical-align:bottom' />";
		$jq("#viewDiv").html(img);
		$jq("input[name=fileId]").val(fileId);
		$jq("input[name=mode]").val("imgChange");
	};	
	$(document).ready(function() {
		//사진등록
		$jq('#fileuploadBtn').click(function(event) {
			iKEP.fileUpload(event.target.id,'0','1');

		});		
		//저장
		$jq("#insertButton").click(function() {
			var mail = $jq("input[name=mail]").val();
			if ( "${who.mail}" == mail ) {
				chkDuplicate = true;
			}

			var f = $jq('#searchForm');			
			var tagNames = f.find('input[name=tag]').val();

			$jq("#searchForm").submit();	
		});
		var checkExists = false;
		//이메일 중복체크
		$jq("#checkExistsButton").click(function() {
			$jq("#searchForm").validate().element("input[name=mail]");
			var mail = $jq("input[name=mail]").val();	
			if ( mail != "" ) {
				
				checkExists = true;	
				$jq.ajax({
					url : '<c:url value="checkAlreadyMailExists.do" />',
					data : {mail:mail},
					type : "post",
					success : function(result) {
						chkDuplicate = true;
						$("input[name=mail]", "#searchForm").trigger("focusout");
						if ( result == 0 ) {
							isDuplicate = false;	
							alert("<ikep4j:message pre='${preMessage}' key='mailAvailable'/>")
						} else {
							isDuplicate = true;
							alert("<ikep4j:message pre='${preMessage}' key='alreadyInserted'/>")
						}				
					},
					complete : function() {
						//chkDuplicate = true;
						//$("input[name=mail]", "#searchForm").trigger("focusout");	
					}				
					,error:function(){
						alert("<ikep4j:message pre='${preMessage}' key='doubleCheckFail'/>");
					}
				});			
			}
		});		
	
		
	    //validation
	    var validOptions = {
			rules : {
				name :	{
					required : true,
					maxlength  : 100
				}
	    		,teamName :	{
					maxlength  : 100
				}
				,companyName :	{
					required : true,
					maxlength  : 100
				}
	    		,jobRankName :	{
					maxlength  : 100
				}				
				,mail : { 
					required : true,
					email : true,
					chkDuplicate:function() { if(!chkDuplicate) return true; }
					//duplicate:function() { if(isDuplicate) return true }
				}
				,mobile : "phone"
				,officePhoneno : "phone"
	    		,companyAddress :	{
					maxlength  : 250
				}				
	    		,memo :	{
					maxlength  : 2000
				}
				<c:if test="${who.profileId != null}">
				,updateReason : { 
					required : true,
					maxlength  : 2000
				}
				</c:if>
				,tag :	{
					required : true
					,maxlength  : 100
					,tagCount :10
					,tagDuplicate: true
					,tagWord :true
				}
			},
			messages : {
				name : {
					required : "<ikep4j:message key='NotEmpty.who.name'/>",
					maxlength : "<ikep4j:message pre='${preValidate}' key='MaxLengthFormName' arguments='100'/>",
					direction:"top"
				}
				,teamName : {
					maxlength : "<ikep4j:message key='Max.who.teamName' arguments='100'/>"
				}			
				,companyName : {
					required : "<ikep4j:message key='NotEmpty.who.companyName'/>",
					maxlength : "<ikep4j:message pre='${preValidate}' key='MaxLengthFormCompanyName' arguments='100'/>",
					direction:"top"
				}
				,jobRankName : {
					maxlength : "<ikep4j:message key='Max.who.jobRankName' arguments='100'/>"
				}				
				,mail : {
					required : "<ikep4j:message key='NotEmpty.who.mail'/>",
					email : "<ikep4j:message pre='${preMessage}' key='NotValidMail'/>",
					chkDuplicate : "<ikep4j:message pre='${preMessage}' key='doubleCheck'/>",
					direction:"top"
				}
				,mobile : { phone : "<ikep4j:message pre='${preMessage}' key='NotValidPhone'/>", direction:"top" }
				,officePhoneno : { phone : "<ikep4j:message pre='${preMessage}' key='NotValidPhone'/>", direction:"top" }
				,companyAddress : {
					maxlength : "<ikep4j:message key='Max.who.companyAddress' arguments='250'/>"
				}				
				,memo : {
					maxlength : "<ikep4j:message key='Max.who.memo' arguments='2000'/>"
				}					
				<c:if test="${who.profileId != null}">
				,updateReason : {
					required : "<ikep4j:message pre='${preValidate}' key='NotEmptyFormUpdateReason'/>",
					maxlength : "<ikep4j:message key='Max.who.updateReason' arguments='2000'/>",
					direction:"top"
				}
				</c:if>
				,tag : {
					required : "<ikep4j:message key='NotEmpty.frDiscussion.tagName'/>"
					,maxlength : "<ikep4j:message key='Size.frDiscussion.tagName' arguments='100'/>"
					,tagCount :"<ikep4j:message key='MaxCount.collpack.forum.tag' arguments='10'/>"
					,tagDuplicate :"<ikep4j:message key='Duplicate.qna.tag'/>"	
					,tagWord :"<ikep4j:message key='Check.qna.tagWord'/>"
				}
			},
			notice : {
				/*name : "<ikep4j:message key='NotEmpty.who.name'/>"
				,companyName : "<ikep4j:message key='NotEmpty.who.companyName'/>"
				,mail : "<ikep4j:message pre='${preMessage}' key='NotValidMail'/>"
				,tag : "<ikep4j:message key='NotEmpty.frDiscussion.tagNameNotice'/>"*/
			}			
		};	  
		$("input[name=mail]", "#searchForm").change(function() {
			chkDuplicate = false;
		});	    
	    new iKEP.Validator("#searchForm", validOptions);
	    
	});

	
})(jQuery); 

//]]>
</script> 

				<form id="editorFileUploadParameter" action="">
				    <input name="interceptorKey"  value="ikep4.system"    type="hidden"/>
				</form>
				
				<form id="searchForm" method="post" action="<c:url value='/collpack/who/createWho.do'/>">
				<input type="hidden" name="mode" value="imgNoneChange"/>
				<input type="hidden" name="hitCount" value="${!empty(who.hitCount) ? who.hitCount:0 }"/>
				<input type="hidden" name="searchColumn" value="wordName"/>
				<h1 class="none">contents area</h1>

				<!--pageTitle Start-->
				<div id="pageTitle">
			 		<c:choose>
			 			<c:when test="${who.profileId != null}">
							<h2><ikep4j:message pre='${preUi}' key='pageLocationTitle'/></h2>			 				
		 				</c:when>
		 				<c:otherwise>
		 					<h2><ikep4j:message pre='${preUi}' key='pageLocationTitle'/></h2>	
			 			</c:otherwise>		 			
			 		</c:choose>				

					<div id="pageLocation" class="none">
						<ul>
							<li class="liFirst"><ikep4j:message pre='${preUi}' key='pageLocation1depth'/></li>
							<li><ikep4j:message pre='${preUi}' key='pageLocation2depth'/></li>
							<li class="liLast"><ikep4j:message pre='${preUi}' key='pageLocation3depth'/></li>							
						</ul>
					</div>
				</div>
				<!--//pageTitle End-->
				
				<!--blockDetail Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${preUi}' key='formSummary'/>">
						<caption></caption>
						<tbody>
							<tr>
								<th colspan="2" width="18%"><span class="colorPoint">*</span><label for="name"><ikep4j:message pre='${preUi}' key='formName'/></label></th>
								<td width="32%">
									<div>
									<input name="name" id="name" type="text" class="inputbox w95" <c:if test="${who.profileId != null}">readonly="readonly"</c:if> title="<ikep4j:message pre='${preUi}' key='formName'/>" value="${who.name}"/>
									</div>
								</td>
								<th width="18%"><ikep4j:message pre='${preUi}' key='formTeamName'/></th>
								<td width="32%">
									<input name="teamName" type="text" class="inputbox w95" title="<ikep4j:message pre='${preUi}' key='formTeamName'/>" value="${who.teamName}"/>
								</td>
							</tr>
							<tr>
								<th colspan="2" width="18%"><span class="colorPoint">*</span><label for="companyName"><ikep4j:message pre='${preUi}' key='formCompanyName'/></label></th>
								<td width="32%">
									<div>
									<input name="companyName" id="companyName" type="text" class="inputbox w95" title="<ikep4j:message pre='${preUi}' key='formCompanyName'/>" value="${who.companyName}"/>
									</div>
								</td>
								<th width="18%"><ikep4j:message pre='${preUi}' key='formJobRankName'/></th>
								<td width="32%">
									<input name="jobRankName" type="text" class="inputbox w95" title="<ikep4j:message pre='${preUi}' key='formJobRankName'/>" value="${who.jobRankName}"/>
								</td>
							</tr>													
							<tr>
								<th colspan="2" width="10%"><span class="colorPoint">*</span><ikep4j:message pre='${preUi}' key='formMail'/></th>
								<td colspan="3" width="90%">
									<div>
									<input name="mail" type="text" class="inputbox w50" title="<ikep4j:message pre='${preUi}' key='formMail'/>" value="${who.mail}"/>
									<a class="button_s" href="#a" id="checkExistsButton"><span><ikep4j:message pre='${preUi}' key='formDoubleCheck'/></span></a>
									</div>
								</td>
							</tr>
							<tr>
								<th colspan="2" width="18%"><ikep4j:message pre='${preUi}' key='formOfficePhoneno'/></th>
								<td width="32%">
									<input name="officePhoneno" type="text" class="inputbox w95" title="<ikep4j:message pre='${preUi}' key='formOfficePhoneno'/>" value="${who.officePhoneno}" maxlength="13"/>
								</td>
								<th width="18%"><ikep4j:message pre='${preUi}' key='formMobile'/></th>
								<td width="32%">
									<input name="mobile" type="text" class="inputbox w95" title="<ikep4j:message pre='${preUi}' key='formMobile'/>" value="${who.mobile}" maxlength="13"/>
								</td>
							</tr>							
							<tr>
								<th colspan="2"><ikep4j:message pre='${preUi}' key='formCompanyAddress'/></th>
								<td colspan="3">
								<input name="companyAddress" type="text" class="inputbox w50" title="<ikep4j:message pre='${preUi}' key='formCompanyAddress'/>" value="${who.companyAddress}" />															
								</td>
							</tr>
							<tr>
								<th colspan="2"><ikep4j:message pre='${preUi}' key='formPicture'/></th>
								<td colspan="3">
									<input name="fileId" type="hidden" value="${who.fileId}" title="fileId" />
									<span id="viewDiv">
							 		<c:choose>
							 			<c:when test="${who.profileId != null}">
											<img id="profilePictureImage" src="<c:url value='/support/fileupload/downloadFile.do?fileId=${who.fileId}&amp;profileYn=Y' />" width="100" height="100" style="vertical-align:bottom" alt="<ikep4j:message pre='${preUi}' key='formPicture'/>" />			 				
						 				</c:when>
						 				<c:otherwise>
						 					<img id="profilePictureImage" src="<c:url value='/base/images/common/photo_170x170.gif' />" width="100" height="100" style="vertical-align:bottom" alt="<ikep4j:message pre='${preUi}' key='formPicture'/>" />	
							 			</c:otherwise>		 			
							 		</c:choose>									
									

									</span>
									&nbsp;
									<a class="button_s" href="#a" ><span id="fileuploadBtn" style="vertical-align:top"><ikep4j:message pre='${preUi}' key='formPictureUpload'/></span></a>
																		
								</td>
								
							</tr>
							<tr>
								<th colspan="2"><ikep4j:message pre='${preUi}' key='formMemo'/></th>
								<td colspan="3">
								<textarea name="memo" id="memo" title="<ikep4j:message pre='${preUi}' key='formMemo'/>" 
												class="inputbox w100 fullEditor" 
												cols="" rows="8">${who.memo}</textarea>
								</td>
							</tr>
							<c:if test="${who.profileId != null}">
							<tr>
								<th colspan="2"><span class="colorPoint">*</span><ikep4j:message pre='${preUi}' key='formUpdateReason'/></th>
								<td colspan="3">
								<div>
								<textarea name="updateReason" id="updateReason" title="<ikep4j:message pre='${preUi}' key='formUpdateReason'/>" 
												class="inputbox w100 fullEditor" 
												cols="" rows="3"></textarea>
								</div>
								</td>
							</tr>							
							</c:if>
							<tr>
								<th colspan="2"><span class="colorPoint">*</span><ikep4j:message pre='${preUi}' key='formTag'/></th>
								<td colspan="3">
									<div>
									<input name="tag" title="<ikep4j:message pre='${preUi}' key='formTag'/>" class="inputbox w100" type="text" value="<c:forEach var="tag" items="${tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">,</c:if>${tag.tagName}</c:forEach>"/>
									</div>
									<div class="tdInstruction">※ <ikep4j:message pre='${preUi}' key='formTagText'/></div>
								</td>
							</tr>					
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><a class="button" href="#a" id="insertButton"><span><ikep4j:message pre='${preUi}' key='formCreateBtn'/></span></a></li>
						<li><a class="button" href="<c:url value='/collpack/who/main.do'/>"><span><ikep4j:message pre='${preUi}' key='formListBtn'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->

				</form>