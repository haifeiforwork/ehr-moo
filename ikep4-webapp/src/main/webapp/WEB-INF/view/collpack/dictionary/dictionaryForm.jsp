<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>


<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%pageContext.setAttribute("crlf", "\r\n"); %>

<c:set var="preView"    	value="ui.collpack.dictionary.dictionaryView" />
<c:set var="preMenu"    	value="ui.collpack.dictionary.dictionaryMenu" />
<c:set var="preForm"    	value="ui.collpack.dictionary.dictionaryForm" />
<c:set var="preMessage" 	value="message.collpack.dictionary" />
<c:set var="preValidate" 	value="validate.collpack.dictionary" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
<!--
(function($) { 
	$(document).ready(function() { 
		var chkDuplicate = false;
		<c:if test="${dictionary.wordId != null}">
		chkDuplicate = true;
		</c:if>
		var isDuplicate = false;		
		/**
		 * CK Edit Init
		 */
		$("#contents").ckeditor($.extend(fullCkeditorConfig, {"language" : "${user.localeCode}","popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
			
		$jq("#insertButton").click(function() {

			var f = $jq('#searchForm');
			var wordName = $jq("input[name=wordName]").val();
			if ( "${dictionary.wordName}" == wordName ) {
				chkDuplicate = true;
			}			

			createEditorFileLink("searchForm");
			 var editor = $jq("#contents").ckeditorGet();
		     editor.updateElement();			
			
			var tagNames = f.find('input[name=tag]').val();

			$jq("#searchForm").submit();	
		});

		$jq("#wordLinkButton").click(function() {
			var dictionaryId = $jq("select[name=dictionaryId]").val();	
			iKEP.showDialog({
				title : "<ikep4j:message pre='${preForm}' key='formLink' />",
				url : "<c:url value='/collpack/dictionary/linkDictionary.do?dictionaryId="+dictionaryId+"'/>",
				modal : true,
				width : 400,
				height : 462,
				callback : addWordLink
			});			

		});
		addWordLink = function(result) {
			var wordId = result.wordId,
				wordName = result.wordName,
				targetId = result.targetId;
			
			addDictionaryLink(wordId,wordName,targetId);
		};
		var checkExists = false;
		$jq("#checkExistsButton").click(function() {
			$jq("#searchForm").validate().element("input[name=wordName]");
			checkExists = true;
			var dictionaryId = $jq("select[name=dictionaryId]").val();
			var wordName = $jq("input[name=wordName]").val();
			var searchColumn = $jq("input[name=searchColumn]").val();
			if ( wordName != "" ) {
				$jq.ajax({
					url : '<c:url value="checkAlreadyExists.do" />',
					data : {dictionaryId:dictionaryId,wordName:wordName,searchColumn:searchColumn,searchWord:wordName,dictionarySortIndex:-1},
					type : "post",
					success : function(result) {
						
						//$("input[name=wordName]", "#searchForm").trigger("focusout");
						if ( result == false ) {
							isDuplicate = false;	
							alert("<ikep4j:message pre='${preMessage}' key='wordAvailable'/>");
							chkDuplicate = true;
							$jq("label.valid-error").hide();
						} else {
							isDuplicate = true;
							alert("<ikep4j:message pre='${preMessage}' key='alreadyInserted'/>");
							chkDuplicate = false;
						}
					},
					error:function(){
						alert("<ikep4j:message pre='${preMessage}' key='doubleCheckFail'/>");
					}
				});						
			}
		});		
        //validation   
        var validOptions = {
			rules : {
				wordName :	{
					required : true,
					maxlength  : 100,
					chkDuplicate:function() { if(!chkDuplicate) return true; }
				},
				wordEnglishName :	{
					englishName : true
				},
				<c:if test="${dictionary.wordId != null}">
				updateReason : { 
					required : true,
					maxlength  : 2000
				},
				</c:if>
				tag :	{
					required : true
					,maxlength  : 100
					,tagCount :10
					,tagDuplicate: true
					,tagWord :true
				}
			},
			messages : {
				wordName : {
					required : "<ikep4j:message key='NotEmpty.dictionary.wordName'/>",
					maxlength : "<ikep4j:message pre='${preValidate}' key='MaxLengthFormWordName' arguments='100'/>",
					chkDuplicate : "<ikep4j:message pre='${preMessage}' key='doubleCheck'/>",
					direction:"top"
				},
				wordEnglishName :	{
					englishName : "<ikep4j:message key='Alert.dictionary.wordEnglishName'/>"
				},				
				<c:if test="${dictionary.wordId != null}">
				updateReason : {
					required : "<ikep4j:message key='NotEmpty.dictionary.updateReason'/>",
					maxlength : "<ikep4j:message key='Max.dictionary.updateReason' arguments='2000'/>",
					direction:"top"
				},
				</c:if>
				tag : {
					required : "<ikep4j:message key='NotEmpty.frDiscussion.tagName'/>"
					,maxlength : "<ikep4j:message key='Size.frDiscussion.tagName' arguments='100'/>"
					,tagCount :"<ikep4j:message key='MaxCount.collpack.forum.tag' arguments='10'/>"
					,tagDuplicate :"<ikep4j:message key='Duplicate.qna.tag'/>"	
					,tagWord :"<ikep4j:message key='Check.qna.tagWord'/>"
				}
			}			
		};  
		
		$("input[name=wordName]", "#searchForm").change(function() {
			chkDuplicate = false;
		});	        
 
		new iKEP.Validator("#searchForm", validOptions);
	});

	
})(jQuery); 
//-->
</script> 

				<form id="editorFileUploadParameter" action="">
				    <input name="interceptorKey"  value="ikep4.system"    type="hidden"/>
				</form>
				
				<form id="searchForm" method="post" action="<c:url value='/collpack/dictionary/createDictionary.do'/>">
				<input type="hidden" name="hitCount" value="${!empty(dictionary.hitCount) ? dictionary.hitCount:0 }"/>
				<input type="hidden" name="searchColumn" value="wordName"/>
				<h1 class="none">contents area</h1>

				<!--pageTitle Start-->
				<div id="pageTitle">
			 		<c:choose>
			 			<c:when test="${dictionary.dictionaryId != null}">
							<h2><ikep4j:message pre='${preMenu}' key='menuModify' /></h2>
			 			</c:when>
			 			<c:otherwise>
			 				<h2><ikep4j:message pre='${preMenu}' key='menuInput' /></h2>
			 			</c:otherwise>		 			
			 		</c:choose>					

					<div id="pageLocation" class="none">
						<ul>
							<li class="liFirst"><ikep4j:message pre='${preForm}' key='pageLocation1depth' /></li>
							<li><ikep4j:message pre='${preForm}' key='pageLocation2depth' /></li>							
							<li class="liLast"><ikep4j:message pre='${preForm}' key='pageLocationTitle' /></li>
						</ul>
					</div>
				</div>
				<!--//pageTitle End-->
				
				<!--blockDetail Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${preForm}' key='pageLocationTitle' />">
						<caption></caption>
						<tbody>
							<tr>
								<th colspan="2" scope="row" width="10%"><ikep4j:message pre='${preForm}' key='formDictionaryName' /></th>
								<td colspan="3" width="90%">
							 		<c:choose>
							 			<c:when test="${dictionary.wordId != null}">
											<c:set var="curDictionaryId" value="${dictionary.dictionaryId}"/>
							 			</c:when>
							 			<c:otherwise>
							 				<c:set var="curDictionaryId" value="${viewDictionaryId}"/>
							 			</c:otherwise>		 			
							 		</c:choose>									
									
									<select title="<ikep4j:message pre='${preForm}' key='formDictionaryName' />" name="dictionaryId">
									<c:forEach var="dictionaryItem" items="${dictionaryAdminList}" varStatus="dictionaryItemLoopCount">								
										<option value="${dictionaryItem.dictionaryId}" <c:if test="${dictionaryItem.dictionaryId eq curDictionaryId}">selected="selected"</c:if> >${dictionaryItem.dictionaryName}</option>
									</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th colspan="2" scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preForm}' key='formWordName' /></th>
								<td colspan="3">
								<div>
							 		<c:choose>
							 			<c:when test="${dictionary.wordId != null}">
											${dictionary.wordName}
							 			</c:when>
							 			<c:otherwise>
							 				<input name="wordName" type="text" class="inputbox w50" title="<ikep4j:message pre='${preForm}' key='formWordName' />" value="${dictionary.wordName}"/>
							 			</c:otherwise>		 			
							 		</c:choose>									
									
									<c:if test="${dictionary.wordId == null}">
										<a class="button_s" href="#a" id="checkExistsButton"><span><ikep4j:message pre='${preForm}' key='formDoubleCheck' /></span></a>
									</c:if>								
								</div>							
								</td>
							</tr>
							<tr>
								<th colspan="2" scope="row"><ikep4j:message pre='${preForm}' key='formWordEnglishName' /></th>
								<td colspan="3">
								<div>
									<input name="wordEnglishName" type="text" class="inputbox w50" title="<ikep4j:message pre='${preForm}' key='formWordEnglishName' />" value="${dictionary.wordEnglishName}" />
									<a class="button_s" href="#a" id="wordLinkButton"><span><ikep4j:message pre='${preForm}' key='formLink' /></span></a>
								</div>
								</td>								
							</tr>
							<tr>
								<td colspan="5">
								<div>
									<textarea name="contents" id="contents" title="<ikep4j:message pre='${preForm}' key='formContents' />" 
													class="tabletext" 
													cols="" rows="20">${dictionary.contents}</textarea>
								</div>
								</td>
							</tr>
							<c:if test="${dictionary.wordId != null}">
							<tr>
								<th colspan="2" scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preView}' key='updateReason'/></th>
								<td colspan="3">
								<div>
								<textarea name="updateReason" id="updateReason" title="<ikep4j:message pre='${preView}' key='updateReason'/>" 
												class="inputbox w100 fullEditor" 
												cols="" rows="3"></textarea>
								</div>
								</td>
							</tr>							
							</c:if>							
							<tr>
								<th colspan="2" scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preForm}' key='formTag' /></th>
								<td colspan="3">
									<div>
										<input name="tag" title="<ikep4j:message pre='${preForm}' key='formTag' />" class="inputbox w100" type="text" value="<c:forEach var="tag" items="${tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">,</c:if>${tag.tagName}</c:forEach>"/>
									</div>
									<div class="tdInstruction">※ <ikep4j:message pre='${preForm}' key='formTagText' /></div>
								</td>
							</tr>					
						</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><a class="button" href="#a" id="insertButton"><span><ikep4j:message pre='${preForm}' key='formCreateBtn' /></span></a></li>
						<li><a class="button" href="<c:url value='/collpack/dictionary/main.do?dictionaryId=${viewDictionaryId }'/>"><span><ikep4j:message pre='${preForm}' key='formListBtn' /></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->

				</form>