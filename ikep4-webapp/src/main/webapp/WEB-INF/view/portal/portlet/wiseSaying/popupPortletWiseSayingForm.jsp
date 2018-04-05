<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preWiseSaying" value="ui.portal.portlet.wiseSaying" />

<c:set var="preAlert" value="message.portal.portlet.wiseSaying.popupPortletWiseSayingForm.alert"/>
<c:set var="preForm" value="${preWiseSaying}.popupPortletWiseSayingForm.form"/>
<c:set var="preButton" value="${preWiseSaying}.popupPortletWiseSayingForm.button"/>

<script type="text/javascript">
//<![CDATA[
(function($) {
	
	$(document).ready(function() {
		
		$jq("#portletWiseSayingForm:input:visible:enabled:first").focus();
		
		/** 파일 이미지 선택으로 변경함으로 주석처리
		iKEP.showGallery($jq("a.image-gallery"));		
		$jq("#fileuploadBtn").click(function(event) { 			
			iKEP.fileUpload(event.target.id, '0', '1');				
		});
		**/
		
		$jq("#newPopupWiseSayingButton").click(function() { 
			getList();			
		});
		
		$jq("#updatePopupWiseSayingButton").click(function() {
			
			/** 파일 이미지 선택으로 변경함으로 주석처리
			<c:if test="${!empty portletWiseSaying.fileData}">
				if($jq("#fileChk").is(":checked") && ($jq("#imageFileId").val() != $jq("#oldImageFileId").val())) {
					$jq("#imageFileId").val($jq("#oldImageFileId").val());
				}
			</c:if>
			if($jq("#fileChk").is(":checked")){
				$jq("#attachCheck").val("1");
			}
			**/
			
			$jq("#portletWiseSayingForm").trigger("submit");
	
		});
		
		$jq("#deletePopupWiseSayingButton").click(function() {  			
			if(confirm("<ikep4j:message pre='${preAlert}' key='confirmDelete' />")) {
				$jq.ajax({
					url : "<c:url value='popupPortletWiseSayingDelete.do' />",
					data : $jq("#portletWiseSayingForm").serialize(),
					type : "post",
					success : function(result) {						
						alert("<ikep4j:message pre='${preAlert}' key='deleteSuccess' />");						
						$jq("#tempId").val(result);						
						reloadWiseSaying();
						getList();						
					},
					error : function(event, request, settings) { 						
						alert("<ikep4j:message pre='${preAlert}' key='deleteFail' />");						
					}
				});
			} else {
				return;
			}			
		});
		
		$jq("#closePopupWiseSayingButton").click(function() {			
			iKEP.closePop();
		});
		
		/**
		 * Validation Logic Start
		 */
		var validOptions = {
			rules : {
				"writerName" : {
					required : true,
					maxlength : 100
				},
				"writerEnglishName" : {
					maxlength : 100
				},
				"wiseSayingContents" : {
					maxlength : 1000
				},
				"wiseSayingEnglishContents" : {
					maxlength : 1000
				},
				"usage" : "required"
			},
			messages : {
				"writerName" : {
					required : "<ikep4j:message key='NotEmpty.portletWiseSaying.writerName' />",
					maxlength : "<ikep4j:message key='Size.portletWiseSaying.writerName' />"
				},
				"writerEnglishName" : {
					maxlength : "<ikep4j:message key='Size.portletWiseSaying.writerName' />"
				},
				"wiseSayingContents" : {
					maxlength : "<ikep4j:message key='Size.portletWiseSaying.wiseSayingContents' />"
				},
				"wiseSayingEnglishContents" : {
					maxlength : "<ikep4j:message key='Size.portletWiseSaying.wiseSayingContents' />"
				},
				"usage" : "<ikep4j:message key='message.portal.portlet.wiseSaying.usage'/>"
			},
			submitHandler : function() {
				$jq.ajax({
					url : "<c:url value='popupPortletWiseSayingUpdate.do' />",
					data : $jq("#portletWiseSayingForm").serialize(),
					type : "post",
					success : function(result) {
						alert("<ikep4j:message pre='${preAlert}' key='saveSuccess' />");						
						$jq("#tempId").val(result);						
						reloadWiseSaying();
						getList();
					},
					error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
			}
		};
		
		var validator = new iKEP.Validator("#portletWiseSayingForm", validOptions);

		/**
		 * Validation Logic End
		 */
		
	});
})(jQuery);
//]]>
</script>

<form id="portletWiseSayingForm" name="portletWiseSayingForm" action="" method="post">  
<input type="hidden" id="wiseSayingId" name="wiseSayingId" value="${portletWiseSaying.wiseSayingId}"/>
<!--  
<input type="hidden" id="oldImageFileId" name="oldImageFileId" value="${portletWiseSaying.imageFileId}"/>
<input type="hidden" id="imageFileId" name="imageFileId" value="${portletWiseSaying.imageFileId}"/>
<input type="hidden" id="attachCheck" name="attachCheck" value="0"/>
-->
	
	<table summary="<ikep4j:message pre='${preForm}' key='tableSummary' />">
		<caption></caption>
		<col width="10%"/>
		<col width="8%"/>
		<col width="*"/>
		<tbody>
			<!--writerName Start-->		
			<tr>
				<th scope="row" rowspan="2">
					<span class="colorPoint">*</span>
					<ikep4j:message pre="${preForm}" key="writerName" />
				</th>
				<th scope="row">
					<span class="colorPoint">*</span>
					<ikep4j:message pre="${preForm}" key="writerName" />
				</th>
				<td>
					<div>
						<spring:bind path="portletWiseSaying.writerName">
						<input type="text" id="writerName" name="writerName" value="${portletWiseSaying.writerName}" class="inputbox w100" />
						</spring:bind>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row">
					<ikep4j:message pre="${preForm}" key="writerEnglishName" />
				</th>
				<td>
					<div>
						<spring:bind path="portletWiseSaying.writerEnglishName">
						<input type="text" id="writerEnglishName" name="writerEnglishName" value="${portletWiseSaying.writerEnglishName}" class="inputbox w100" />
						</spring:bind>
					</div>
				</td>
			</tr>
			<!--//writerName End-->
				
			<!--wiseSayingContents Start-->		
			<tr>
				<th scope="row" rowspan="2">
					<ikep4j:message pre="${preForm}" key="wiseSayingContents" />
				</th>
				<th scope="row">
					<ikep4j:message pre="${preForm}" key="wiseSayingContents" />
				</th>
				<td>
					<div>
						<spring:bind path="portletWiseSaying.wiseSayingContents">
						<textarea id="wiseSayingContents" name="wiseSayingContents" class="inputbox w100" cols="" rows="5" title="국문내용"><c:out value="${portletWiseSaying.wiseSayingContents}" /></textarea>
						</spring:bind>	
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row">
					<ikep4j:message pre="${preForm}" key="wiseSayingEnglishContents" />
				</th>
				<td>
					<div>
						<spring:bind path="portletWiseSaying.wiseSayingEnglishContents">
						<textarea id="wiseSayingEnglishContents" name="wiseSayingEnglishContents" class="inputbox w100" cols="" rows="5" title="영문내용"><c:out value="${portletWiseSaying.wiseSayingEnglishContents}" /></textarea>
						</spring:bind>
					</div>
				</td>
			</tr>
			<!--//wiseSayingContents End-->	
			
			<tr>
				<th scope="row" colspan="2">
					<ikep4j:message pre="${preForm}" key="imageAttach" />
				</th>
				<td>
					<ul style="list-style:none;">
						<li style="float:left;width:120px;text-align:center;">
							<img src="<c:url value='/base/images/common/img_wisesaying.gif'/>" alt="wise image01"/><br/>
							<input type="radio" name="imageFileId" value="1" <c:if test="${portletWiseSaying.imageFileId == 1}">checked="true"</c:if> <c:if test="${empty portletWiseSaying.imageFileId}">checked="true"</c:if> />
						</li>
						<li style="float:left;width:120px;text-align:center;">
							<img src="<c:url value='/base/images/common/img_wisesaying2.gif'/>" alt="wise image02"/><br/>
							<input type="radio" name="imageFileId" value="2" <c:if test="${portletWiseSaying.imageFileId == 2}">checked="true"</c:if> />
						</li>
						<li style="float:left;width:120px;text-align:center;">
							<img src="<c:url value='/base/images/common/img_wisesaying3.gif'/>" alt="wise image03"/><br/>
							<input type="radio" name="imageFileId" value="3" <c:if test="${portletWiseSaying.imageFileId == 3}">checked="true"</c:if> />
						</li>
						<li style="float:left;width:120px;text-align:center;">
							<img src="<c:url value='/base/images/common/img_wisesaying4.gif'/>" alt="wise image04"/><br/>
							<input type="radio" name="imageFileId" value="4" <c:if test="${portletWiseSaying.imageFileId == 4}">checked="true"</c:if> />
						</li>
						<li style="float:left;width:120px;text-align:center;">
							<img src="<c:url value='/base/images/common/img_wisesaying5.gif'/>" alt="wise image05"/><br/>
							<input type="radio" name="imageFileId" value="5" <c:if test="${portletWiseSaying.imageFileId == 5}">checked="true"</c:if> />
						</li>
					</ul>
					<!--  파일 이미지 선택으로 변경함으로 주석처리
						<c:if test="${!empty portletWiseSaying.fileData}">
						<input type="checkbox" id="fileChk" name="fileChk" value="1" checked="checked" onclick="checkAttachFile();" />
						<a class="image-gallery" href="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${portletWiseSaying.fileData.fileId}" title="${portletWiseSaying.fileData.fileRealName}">
							<c:out value="${portletWiseSaying.fileData.fileRealName}"/>
						</a>
						</c:if>
						
						<div id="fileDiv" <c:if test="${!empty portletWiseSaying.fileData}">style="display:none;"</c:if>>
							<span id="fileNameSpan"></span>
							<a class="button_s" href="#" onclick="return false;">
								<span id="fileuploadBtn"><ikep4j:message pre="${preButton}" key="attachFile" /></span>
							</a>
						</div>
					 -->
				</td>
			</tr>
			<tr>
				<th scope="row" colspan="2">
					<ikep4j:message pre="${preWiseSaying}" key="usage" />
				</th>
				<td>
					<div>
						<label><input name="usage" type="radio" value="1" <c:if test="${portletWiseSaying.usage == 1}">checked</c:if> /><ikep4j:message pre="${preWiseSaying}" key="usage.use"/></label>&nbsp;&nbsp;&nbsp;
						<label><input name="usage" type="radio" value="0" <c:if test="${portletWiseSaying.usage != 1}">checked</c:if> /><ikep4j:message pre="${preWiseSaying}" key="usage.disabled"/></label>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	
</form>

<div class="blockBlank_10px"></div>
				
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li>
			<a href="#" class="button" id="newPopupWiseSayingButton" onclick="return false;" title="<ikep4j:message pre='${preButton}' key='new' />">
				<span><ikep4j:message pre="${preButton}" key="new" /></span>
			</a>
		</li>
		<li>
			<a href="#" class="button" id="updatePopupWiseSayingButton" onclick="return false;" title="<ikep4j:message pre='${preButton}' key='update' />">
				<span><ikep4j:message pre="${preButton}" key="update" /></span>
			</a>
		</li>
		<li>
			<a href="#" class="button" id="deletePopupWiseSayingButton" onclick="return false;" title="<ikep4j:message pre='${preButton}' key='delete' />">
				<span><ikep4j:message pre="${preButton}" key="delete" /></span>
			</a>
		</li>
		
		<li>
			<a href="#" class="button" id="closePopupWiseSayingButton" onclick="return false;" title="<ikep4j:message pre='${preButton}' key='close' />">
				<span><ikep4j:message pre="${preButton}" key="close" /></span>
			</a>
		</li>
	</ul>
</div>
<!--//blockButton End-->