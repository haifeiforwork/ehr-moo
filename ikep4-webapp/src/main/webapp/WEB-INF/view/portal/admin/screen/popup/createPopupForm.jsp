<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="prefix" value="ui.portal.admin.screen.popup.createPopupForm"/>
<c:set var="prefixMessage" value="message.portal.admin.screen.popup.createPopupForm"/>
 
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>

<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>
<script type="text/javascript" src="/base/js/plupload/browserplus-min.js"></script>
<script type="text/javascript">
<!--
//activeX editor 사용여부
var useActXEditor = "${useActiveX}";

(function($) {
    $jq(document).ready(function() {
    	
		// editor 초기화
		initEditorSet();

		/*
    	$("input[name=popupStartDate]").attr("readonly", true).datepicker({
    		isActiveX : (useActXEditor == "Y" && $.browser.msie) ? true : false,
			onSelect : function(){
				var form = $(this)[0].form;
				var validator = $(form).validate();
				validator.element(this);
				validator.element("input[name=popupEndDate]", form);
			},
		    showOn: "button",
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true,
		    buttonText : "<ikep4j:message pre='${prefix}' key='popupStartDate'/>"
		});
    	
		$("input[name=popupEndDate]").attr("readonly", true).datepicker({
			isActiveX : (useActXEditor == "Y" && $.browser.msie) ? true : false,
			onSelect : function(){
				var form = $(this)[0].form;
				var validator = $(form).validate();
				validator.element(this);
				validator.element("input[name=popupStartDate]", form);
			},
		    showOn: "button",
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true,
		    buttonText : "<ikep4j:message pre='${prefix}' key='popupEndDate'/>"
		});
		*/
		
		
		var datepickerOptions = {
			dateFormat: "yy.mm.dd",
			showOn: "both",
			buttonImageOnly: true,
			buttonImage: iKEP.getContextRoot() + "/base/images/icon/ic_cal.gif"
		};
		
		//시작 기간 
		$jq("#popupStartDate").attr("readonly", true)
			.datepicker($jq.extend({},datepickerOptions, {
				onSelect: function(dateText, inst) {
					$jq("#popupEndDate").datepicker("option", "minDate", $jq(this).val());
				}
			}))
			//.datepicker("setDate", "")
			.next("img").addClass("dateicon").attr('title','<ikep4j:message pre="${prefix}" key="popupStartDate"/>');
		 
		 //마지막 기간
		 $jq("#popupEndDate").attr("readonly", true)
			.datepicker($jq.extend({},datepickerOptions, {
				minDate: $jq("#popupStartDate").val(),
				onSelect: function() {
				}
			}))
			//.datepicker("setDate", "2012.01.01")
			.next("img").addClass("dateicon").attr('title','<ikep4j:message pre="${prefix}" key="popupEndDate"/>');
		
		 
		
		
		$jq("#saveButton").click(function() {
			$("#createPopupForm").trigger("submit"); 
		});
		
		/**
		 * Validation Logic Start
		 */
		
		var validOptions = {
			rules  : {
				popupName      : {required : true, maxlength : 500},
				//popupUrl       : {required : true, maxlength : 500},
				popupWidth     : {required : true, maxlength : 4, digits:true}, 
				popupHeight    : {required : true, maxlength : 4, digits:true}
				//,
				//popupStartDate : {required : true, dateGTE : "toDate"},
				//popupEndDate   : {required : true, dateGTE : "popupStartDate"}
			},
			messages : {
				popupName    : {
					required  : "<ikep4j:message key="NotEmpty.portalPopup.popupName" />",
					maxlength : "<ikep4j:message key="" />"
				},
				/*
				popupUrl     : {
					required  : "<ikep4j:message key="NotEmpty.portalPopup.locale" />",
					maxlength : "<ikep4j:message key="Size.portalPopup.description100" />"
				},
				*/
				popupWidth   : {
					required  : "<ikep4j:message key="NotEmpty.portalPopup.popupWidth" />",
					maxlength : "<ikep4j:message key="Size.portalPopup.description4" />",
					digits    : "<ikep4j:message key="Digits.portalPopup.onlyNum" />"
				}, 
				popupHeight  : {
					required  : "<ikep4j:message key="NotEmpty.portalPopup.popupHeight" />",
					maxlength : "<ikep4j:message key="Size.portalPopup.description4" />",
					digits    : "<ikep4j:message key="Digits.portalPopup.onlyNum" />"
				}
				/*
				,
				popupStartDate : {
					direction : "bottom", 
					required : "<ikep4j:message key="NotEmpty.portalPopup.locale" />",
					dateGTE : "<ikep4j:message pre='${prefixMessage}' key='alert.dateBetween'/>"
				},
				popupEndDate   : {
					direction : "bottom-right", 
					required : "<ikep4j:message key="NotEmpty.portalPopup.locale" />",
					dateGTE : "<ikep4j:message pre='${prefixMessage}' key='alert.dateBetween'/>"
				}*/
			}, 	
			
			submitHandler : function(form) {
				
				if(confirm("<ikep4j:message pre='${prefixMessage}' key='alert.saveConfirm'/>"))  {
					if(useActXEditor != "Y" || !$jq.browser.msie){
			    		//ekeditor 데이타 업데이트
			    		var editor = $jq('#createPopupForm :input[name=contents]').ckeditorGet(); 
						editor.updateElement();
						//에디터 내 이미지 파일 링크 정보 세팅
						createEditorFileLink("createPopupForm");
			    	}
					
					fileController.upload(function(isSuccess, files) {
						$.ajax({
							url : "<c:url value='/portal/admin/screen/popup/createPopup.do'/>",
							type : "post",
							data : $(form).serialize(),
							success : function(result) {
								alert("<ikep4j:message pre='${prefixMessage}' key='alert.saveMessage'/>");
								location.href= "<c:url value='readPopup.do'/>?popupId=" + result; //조회화면으로 포워딩
							},
							error : function(xhr, exMessage) {
								var errorItems = $.parseJSON(xhr.responseText).exception;
								validator.showErrors(errorItems);
							}
						});
					});
				}
			}
		};


		var validator = new iKEP.Validator("#createPopupForm", validOptions);
	    
		/**
		 * Validation Logic End
		 */

	    var uploaderOptions = {
		 	<c:if test="${empty fileDataListJson}">files : [],</c:if> 
		 	<c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if> 
			<c:if test="${board.fileAttachOption eq 0 and not empty board.allowType}">allowExt : "${board.allowType}",</c:if>
			<c:if test="${board.fileAttachOption eq 1 and not empty board.allowType}">allowFileType : "${board.allowType}",</c:if>
	    	isUpdate : true ,
	    	onLoad : function() {
	    		iKEP.iFrameContentResize();
	    	}
		}; 
		
		var fileController = new iKEP.FileController("#createPopupForm", "#fileUploadArea", uploaderOptions);
		    
	 	// ActiveX Editor 사용 여부가 Y인 경우
	    if(useActXEditor == "Y"){
		    if (!$.browser.msie) {
		    	//에디터 로딩 후 현재 페이지 높이 세팅 - iframe 높이 조절
		    	var editor = $("#contents").ckeditorGet();
				editor.on("instanceReady",function() {
					iKEP.iFrameContentResize();
				});
				$("input[name=title]").focus();
	    	}
	    }else{
	    	//에디터 로딩 후 현재 페이지 높이 세팅 - iframe 높이 조절
	    	var editor = $("#contents").ckeditorGet();
			editor.on("instanceReady",function() {
				iKEP.iFrameContentResize();
			});
			$("input[name=title]").focus();
	    }
		
	    /* fileUpload start */
	    <%--
		$jq('#fileuploadBtn').click(function(event) { 
			iKEP.fileUpload(event.target.id, '0', '1');	
		}); 
		$jq('#pf_picture_edit').click(function(){
			uploadFormForMemberProfile('admin','picture', $jq("#profilePictureImage").attr("src"), afterFileUpload);
		});					
		--%>
		/* fileUpload end */
		
		//className,resourceName,operationName,"권한설정 옵션"
		iKEP.loadSecurity("Portal-Popup","","READ","User,Group,Role,Job,Duty,ExpUser", 25);
    });
    

	/* editor 초기화  */
	initEditorSet = function() {
		// ActiveX Editor 사용 여부가 Y인 경우
	    if(useActXEditor == "Y"){
			// 브라우저가 ie인 경우
			if ($.browser.msie) {
				// div 높이, 넓이 세팅
				var cssObj = {
			      'height' : '150px',
			      'width' : '50%'
			    };
				$('#editorDiv').css(cssObj);

				// hidden 필드 추가(contents)
				iKEP.createActiveXEditor("#editorDiv","${user.localeCode}","#contents",0);
				// ie 세팅
				$('input[name="msie"]').val('1');
			}else{
				// ckeditor 초기화.
				$("#contents").ckeditor($.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
				// ie 이외 브라우저 값 세팅
				$('input[name="msie"]').val('0');
			}
	    }else{
	    	// ckeditor 초기화.
			$("#contents").ckeditor($.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
			// ie 이외 브라우저 값 세팅
			$('input[name="msie"]').val('0');
	    }
	};
	
	<%--
	afterFileUpload = function(status, fileId, fileName, message, targetId) {
		$jq("#viewDiv").html(fileId);
		$jq("input[name=fileId]").val(fileId);
		
	};
	--%>
	
})(jQuery);

function popupTypeChange(obj) {
	
	if(obj.value == 'C') {
		$jq("#popupUrlTr").hide();
		$jq("#popupContentTr").show();
	} else if(obj.value == 'U') {
		$jq("#popupUrlTr").show();
		$jq("#popupContentTr").hide();
	}
}

//-->
</script>
<script language="JScript" FOR="twe" EVENT="OnKeyDown(event)" type="text/javascript" >
	/* 태그프리 에디터 줄바꿈 태그 P => br 로 변경하는 메소드 */
	if (!event.shiftKey && event.keyCode == 13){
		twe.InsertHtml("<br>");
		event.returnValue = true; <!-- Active Designer에서의 처리를 막음 -->
	}
	if (event.shiftKey && event.keyCode == 13){
		twe.InsertHtml("<p>");
		event.returnValue = true; <!-- Active Designer에서의 처리를 막음 -->
	}		
</script>
<script language="JScript" for="twe" event="OnControlInit()" type="text/javascript" >	
	$jq("input[name=title]").focus();
</script>
<form id="editorFileUploadParameter" action="#a">  
	<input name="interceptorKey"  value="ikep4.system" type="hidden" title="interceptorKey" /> 
</form> 

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${prefix}" key="pageTitle" /></h2>
</div>
<!--//pageTitle End-->

<form id="createPopupForm" method="post" enctype="multipart/form-data" action="#a">
<input name="msie" type="hidden" value="0" title="msie" />
<input type="hidden" id="imageYn" name="imageYn" title="imageYn" />
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${prefix}' key='summary'/>">
			<caption></caption>
			<tbody>
				<tr>
					<th width="18%" scope="row"><span class="colorPoint">*</span> <ikep4j:message pre='${prefix}' key='popupName'/></th>
					<td width="82%">
						<div>
							<input type="text" name="popupName" class="inputbox w90" value="" title="<ikep4j:message pre='${prefix}' key='popupName'/>" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row"><span class="colorPoint">*</span> <ikep4j:message pre='${prefix}' key='popupWidth'/></th>
					<td>
						<div>
							<input type="text" name="popupWidth" class="inputbox w90" value="" title="<ikep4j:message pre='${prefix}' key='popupWidth'/>" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row"><span class="colorPoint">*</span> <ikep4j:message pre='${prefix}' key='popupHeight'/></th>
					<td>
						<div>
							<input type="text" name="popupHeight" class="inputbox w90" value="" title="<ikep4j:message pre='${prefix}' key='popupHeight'/>" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row"><span class="colorPoint">*</span> <ikep4j:message pre='${prefix}' key='useDate'/></th>
					<td>
						<div>
							<input id="popupStartDate" name="popupStartDate" type="text" class="inputbox date-pick" value="${today}" size="10" title="<ikep4j:message pre='${prefix}' key='popupStartDate'/>" /> ~   
							<input id="popupEndDate" name="popupEndDate" type="text" class="inputbox date-pick" value="${today}" size="10" title="<ikep4j:message pre='${prefix}' key='popupEndDate'/>"  />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='isUse'/></th>
					<td>
						<label><input type="radio" class="radio" name="popupActive" value="1" checked="checked" title="popupActive" /><ikep4j:message pre='${prefix}' key='use'/></label>&nbsp;
						<label><input type="radio" class="radio" name="popupActive" value="0" title="popupActive" /><ikep4j:message pre='${prefix}' key='noUse'/></label>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='howtopopup'/></th>
					<td>
						<label><input type="radio" class="radio" name="windowYn" value="Y" checked="checked" title="windowYn" /><ikep4j:message pre='${prefix}' key='newWindow'/></label>&nbsp;
						<label><input type="radio" class="radio" name="windowYn" value="N" title="windowYn" /><ikep4j:message pre='${prefix}' key='layer'/></label>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='bgImage'/></th>
					<td>
						<label><input type="radio" class="radio" name="popupTheme" value="N" checked="checked" title="popupTheme" /><ikep4j:message pre='${prefix}' key='noUse'/></label>&nbsp;
						<label><input type="radio" class="radio" name="popupTheme" value="1" title="popupTheme" /><img width="30" height="30" alt="Popup Theme Image1" src="<c:url value='/base/images/common/skinDesign_05.gif'/>" /></label>&nbsp;
						<label><input type="radio" class="radio" name="popupTheme" value="2" title="popupTheme" /><img width="30" height="30" alt="Popup Theme Image2" src="<c:url value='/base/images/common/skinDesign_07.gif'/>" /></label>&nbsp;
						<label><input type="radio" class="radio" name="popupTheme" value="3" title="popupTheme" /><img width="30" height="30" alt="Popup Theme Image3" src="<c:url value='/base/images/common/skinDesign_04.gif'/>" /></label>&nbsp;
						<label><input type="radio" class="radio" name="popupTheme" value="4" title="popupTheme" /><img width="30" height="30" alt="Popup Theme Image4" src="<c:url value='/base/images/common/skinDesign_03.gif'/>" /></label>&nbsp;
					</td>
				</tr>				
				<tr>
					<th scope="row"><ikep4j:message pre='${prefix}' key='popupType'/></th>
					<td>
						<label><input type="radio" class="radio" name="popupType" value="C" onclick="popupTypeChange(this);" checked="checked" title="popupType" /><ikep4j:message pre='${prefix}' key='popupType.contents'/></label>&nbsp;
						<label><input type="radio" class="radio" name="popupType" value="U" onclick="popupTypeChange(this);" title="popupType" /><ikep4j:message pre='${prefix}' key='popupType.url'/></label>
					</td>
				</tr>
				<tr id="popupUrlTr" style="display: none;">
					<th scope="row"><ikep4j:message pre='${prefix}' key='popupUrl'/></th>
					<td>
						<div>
							<input type="text" name="popupUrl" class="inputbox w90" value="" title="<ikep4j:message pre='${prefix}' key='popupUrl'/>" />
						</div>
					</td>
				</tr>
				<tr id="popupContentTr">
					<th scope="row"><ikep4j:message pre='${prefix}' key='contents'/></th>
					<td class="ckeditor">
						<div id="editorDiv">			
							<textarea id="contents"
							name="contents" 
							class="inputbox w100 fullEditor"
							cols="" 
							rows="5" 
							title="<ikep4j:message pre='${prefix}' key='contents'/>"></textarea>					
						</div>
					</td>
				</tr>
			</tbody>
		</table>
		<div id="securityArea"></div>
	</div>
	<!--//blockDetail End-->
	
	<!--tableBottom Start-->
	<div class="tableBottom">										
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a id="saveButton" class="button" href="#a"><span><ikep4j:message pre='${prefix}' key='button.save'/></span></a></li>
				<li><a class="button" href="<c:url value='listPopup.do'/>"><span><ikep4j:message pre='${prefix}' key='button.list'/></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
</form>
