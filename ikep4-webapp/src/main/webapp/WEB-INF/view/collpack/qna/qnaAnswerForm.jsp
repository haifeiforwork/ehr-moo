<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%>
<%@ include file="/base/common/DextfileUploadInit.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>

<c:set var="preUi" 			value="ui.collpack.qna.qnaAnswerForm" />
<c:set var="preResource" 	value="message.collpack.qna" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
//<![CDATA[
		var fileController = ""	;	

		//activeX editor 사용여부
		var useActXEditor = "${useActiveX}";
	
		function submitQna(){
			var f = $jq('#qnaForm');
			
			
		  // ActiveX Editor 사용 여부가 Y인 경우
	    	if(useActXEditor == "Y"){
		    	if (!$jq.browser.msie) {
		    		//ekeditor 데이타 업데이트
		    		var editor = $jq("#contents").ckeditorGet(); 
					editor.updateElement();
					//에디터 내 이미지 파일 링크 정보 세팅
					createEditorFileLink("qnaForm");
		    	}
	    	}else{
	    		//ekeditor 데이타 업데이트
	    		var editor = $jq("#contents").ckeditorGet(); 
				editor.updateElement();
				//에디터 내 이미지 파일 링크 정보 세팅
				createEditorFileLink("qnaForm");
	    	}
		  
		    //먼저 submit을 하여 validation check로 넘어간다.
		    // iKEP.Validator는 check후 submitHandler를 호출하게 된다.
		    
			f.submit();
		}
		
		writeSubmit = function(targetForm){
			//에디터 감추기
            if(useActXEditor == "Y"){
                if ($jq.browser.msie) {
                    //태그프리 선택 탭을 디자인으로 변경 후 저장한다.
                    var tweTab = document.twe.ActiveTab;
                    if ( tweTab != 1 ) {
                        document.twe.ActiveTab = 1;
                    }
                    //태그프리 Mime 데이타 세팅
                    var tweBody = document.twe.MimeValue();
                    $jq('input[name="contents"]').val(tweBody);
                    $jq("#twe").css("visibility","hidden");
                }
            }
		    targetForm.submit();
		};
		

	function goList(val){
		var param = '<c:forEach var="ram" items="${param}" ><c:if test="${ram.value != ''}">${ram.key}=${ram.value}&</c:if></c:forEach>';
		

		var url = 'list'+val+'Qna.do?'+param;
		
		if(val == "Main"){
			url = 'main.do?'+param;
		}

		location.href=url;
	}
	
	var oldFiles;
	var oldSizes;
	
	$jq(document).ready(function() {
			
		//validation
		var validOptions = {
			rules : {
				title :	{
					required : true
					,maxlength  : 500
				}
				//,contents : "required"
				/*
				,tag :	{
					required : true
					,maxlength  : 100
					,tagCount :10
					,tagDuplicate: true
					,tagWord :true
				}
				*/
			},
			messages : {
				title : {
					required : "<ikep4j:message key='NotEmpty.qna.title'/>",
					maxlength : "<ikep4j:message key='Max.qna.title' arguments='500'/>"
				}
				,contents : {
					required : "<ikep4j:message key='NotEmpty.qna.contents'/>"
				}
				/*
				,tag : {
					///required : "<ikep4j:message key='NotEmpty.qna.tag'/>",
					maxlength : "<ikep4j:message key='Max.qna.tagName' arguments='100'/>"
					,tagCount :"<ikep4j:message key='MaxCount.qna.tag' arguments='10'/>"
					,tagDuplicate :"<ikep4j:message key='Duplicate.qna.tag'/>"	
					,tagWord :"<ikep4j:message key='Check.qna.tagWord'/>"	
				}
				*/
			},
			notice : {
				title : "<ikep4j:message key='NotEmpty.qna.title'/>",
				contents : "<ikep4j:message key='NotEmpty.qna.contents'/>"
				//tag: "<ikep4j:message key='NotEmpty.qna.tag'/>"
			}
			,
	        submitHandler : function(form){
	              if((oldSizes !=document.getElementById("FileUploadManager").Size)||(document.all["FileUploadManager"].Count>0)){//삭제한것이 있거나 추가한것이 있으면
	                  btnTransfer_Onclick("qnaForm");
	              }else{
	                  //alert("파일업로드 없음");            
	                  oldFileSetting(oldFiles , form);
	                  writeSubmit(form);
	              }
	        }
		};

	        
       // $jq("#qnaForm").validate(validOptions); 
       new iKEP.Validator("#qnaForm", validOptions);
       
	  /*var uploaderOptions = {// 사용할 수 있는 옵션 항목
				<c:if test="${!empty(fileDataListJson)}">
					files  :  ${fileDataListJson}, //파일 목록
				</c:if>
			    // isUpdate : true,    // 등록 및 수정일때 true
		        //uploadUrl : "",    // 파일 업로드 경로가 다를때 설정 : 사용하지 않음
		        maxTotalSize : 20*1024*1024,    // 최대 업로드 가능 용량(개별 파일 사이즈임)
		        allowFileType  : "all"    
		    };
			
			 
		//파일업로드 컨트롤로 생성   
		 fileController = new iKEP.FileController("#qnaForm", "#fileUploadArea", uploaderOptions);*/
		 dextFileUploadInit(""+20*1024*1024 ,"1", "all");
	     
	        <c:if test="${not empty fileDataListJson}"> 
	        oldFiles = ${fileDataListJson};
	        $jq.each(oldFiles,function(index){
	            var fileInfo = $.extend({index:index}, this);
	            document.getElementById("FileUploadManager").AddUploadedFile(fileInfo.fileId, fileInfo.fileRealName, fileInfo.fileSize);
	         });
	        
	        dextFileUploadRefresh(); 
	        oldSizes =document.getElementById("FileUploadManager").Size;
	        </c:if> 

		 <c:if test="${fn:length(parent.fileDataListJson) > 3}">
			new iKEP.FileController(null, "#fileUploadArea_${parent.qnaId}", {files : ${parent.fileDataListJson}, isUpdate : false});
		</c:if>

		// editor 초기화
		initEditorSet();
	    
	});

	/* editor 초기화  */
	function initEditorSet() {
		// ActiveX Editor 사용 여부가 Y인 경우
	    if(useActXEditor == "Y"){
			// 브라우저가 ie인 경우
			if ($jq.browser.msie) {
				// div 높이, 넓이 세팅
				var cssObj = {
			      'height' : '450px',
			      'width' : '100%'
			    };
				$jq('#editorDiv').css(cssObj);

				var editVal = 0;
				<c:if test="${!empty(qnaForm)}">
					editVal = 1;
				</c:if>
	
				// hidden 필드 추가(contents)
				iKEP.createActiveXEditor("#editorDiv","${user.localeCode}","#contents",editVal);

				// ie 세팅
				$jq('input[name="msie"]').val('1');
			}else{
				// ckeditor 초기화.
				$jq("#contents").ckeditor($jq.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
				// ie 이외 브라우저 값 세팅
				$jq('input[name="msie"]').val('0');
			}
	    }else{
	    	// ckeditor 초기화.
			$jq("#contents").ckeditor($jq.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
			// ie 이외 브라우저 값 세팅
			$jq('input[name="msie"]').val('0');
	    }
	}
	
	//]]>	
</script>

<script language="JScript" FOR="twe" EVENT="OnKeyDown(event)">
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
<script language="JScript" for="twe" event="OnControlInit()">	
	document.twe.HtmlValue = $jq("input[name=contents]").val().replaceAll("src=\""+iKEP.getContextRoot(), "src=\""+iKEP.getWebAppPath());
	$jq("input[name=title]").focus();
</script>

<!--guideWrapper Start-->
	<div id="tagResult">
	<h4 class="guidetitle_han">
		<c:choose>
			<c:when test="${empty(param.qnaId)}">
				<ikep4j:message pre='${preUi}' key='pageLocationTitle'/>
			</c:when>
			<c:otherwise>
				<ikep4j:message pre='${preUi}' key='pageLocationTitle.update'/>
			</c:otherwise>
		</c:choose>
	</h4>
	
	<!--qna_question Start-->
		<div class="blockTableRead qna_question">
			<h3 class="none"><ikep4j:message pre='${preUi}' key='subTile'/></h3>
			<div class="qnaimg">
				<span class="ic_qna_q_2"><span><ikep4j:message pre='${preUi}' key='subTile'/></span></span>
			</div>	
			<div class="blockTableRead_t">
			<c:if test="${parent.bestFlag  == 1}">
				<div class="qna_answer_best"><img src="<c:url value="/base/images/common/img_best.png"/>" alt="best" /></div>
			</c:if>	
				<p>
				<c:if test="${parent.urgent == 1}">
 					[<ikep4j:message pre='${preUi}' key='tableUrgent'/>] 
 				</c:if>
					${parent.title}
				</p>
				<div class="summaryViewInfo">
					<span class="summaryViewInfo_name"><a href="#a" onclick="viewPopUpProfile('${parent.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${parent.registerName} ${parent.jobTitleName}</c:when><c:otherwise>${parent.userEnglishName} ${parent.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
					<img src="<c:url value="/base/images/theme/theme01/basic/bar_pageNum.gif"/>" alt="bar" />
					<span><ikep4j:timezone date="${parent.registDate}" pattern="message.collpack.qna.timezone.dateformat.type2" keyString="true"/></span>
					<img src="<c:url value="/base/images/theme/theme01/basic/bar_pageNum.gif"/>" alt="bar" />
					<span><ikep4j:message pre='${preUi}' key='tableHit'/> <strong>${parent.hitCount}</strong></span>
					<img src="<c:url value="/base/images/theme/theme01/basic/bar_pageNum.gif"/>" alt="bar" />
					<span><ikep4j:message pre='${preUi}' key='tableAnswer'/> <strong>${parent.replyCount}</strong></span>
					<img src="<c:url value="/base/images/theme/theme01/basic/bar_pageNum.gif"/>" alt="bar" />							
					<span><ikep4j:message pre='${preUi}' key='tableRecommend'/> <strong>${parent.recommendCount}</strong></span>
				</div>
			</div>
			
			<div id="fileUploadArea_${parent.qnaId}"></div>
			
			<div class="blockTableRead_c">
				<p>
					<spring:htmlEscape defaultHtmlEscape="false"> 
					${parent.contents}
					</spring:htmlEscape>
				</p>
				
				<c:choose>
					<c:when test="${parent.qnaType == 0}">
						<c:set var="tagSubType" value="Q"/>	
					</c:when>
					<c:otherwise>
						<c:set var="tagSubType" value="A"/>	
					</c:otherwise>
				</c:choose>
				
				
				<!--tag list-->
					<div class="tableTag" id="tagForm_${parent.qnaId}">
						<input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_QNA %>"/>
						<input type="hidden" name="tagItemSubType" value="${tagSubType}"/>
						<input type="hidden" name="tagItemName" value="${parent.title}"/>
						<input type="hidden" name="tagItemContents" value="${fn:escapeXml(parent.contents)}"/>
						<input type="hidden" name="tagItemUrl" value="/collpack/qna/getQna.do?docPopup=true&amp;qnaId=${parent.qnaGroupId}"/>
						
						<div>
							<span class="ic_tag"><span><ikep4j:message pre='${preUi}' key='tableTag'/></span></span>
								<c:forEach var="tag" items="${parent.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" title="tag" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
							<span class="rebtn">
								<c:if test="${user.userId == parent.registerId}">
								<a href="#a" title="<ikep4j:message pre='${preUi}' key='tableTagUpdate'/>" onclick="iKEP.tagging.tagFormView('${parent.qnaId}');return false;" class="ic_modify"><span><ikep4j:message pre='${preUi}' key='tableTagUpdate'/></span></a>
								</c:if>
							</span>
						</div>
					</div>
					<!--//tag list-->
			</div>	
			
		</div>
		<!--//qna_question End-->	
		
	<form id="editorFileUploadParameter" action="">
	    <input name="interceptorKey"  value="ikep4.system"    type="hidden"/>
	</form>
	
	
	<form id="qnaForm" method="post" action="<c:url value='createQna.do'/>">
		<spring:bind path="qnaForm.portalId">
			<input name="${status.expression}" type="hidden" value="${status.value}" />
		</spring:bind> 
		<spring:bind path="qnaForm.listType">
			<input name="${status.expression}" type="hidden" value="${status.value}" />
		</spring:bind> 
		<spring:bind path="qnaForm.urgent">
			<input id="urgent" name="${status.expression}" type="hidden" value="${status.value}" />
		</spring:bind> 
		<spring:bind path="qnaForm.listTab">
			<input name="${status.expression}" type="hidden" value="${status.value}" />
		</spring:bind> 
		<spring:bind path="qnaForm.qnaType">
			<input name="${status.expression}" type="hidden" value="${status.value}" />
		</spring:bind> 
		<spring:bind path="qnaForm.qnaId">
			<input name="${status.expression}" type="hidden" value="${status.value}" />
		</spring:bind>
		<input name="qnaGroupId" type="hidden" value="${parent.qnaId}" />
		<input name="msie"        			type="hidden" value="0" />
	
	<!--blockDetail Start-->
	<div class="blockDetail">
		
		<table summary="form">
			<caption></caption>
			<tbody>
			
				<spring:bind path="qnaForm.title">
					<c:if test="${empty(qnaForm.title)}">
						<c:set var="tmpTitle" value="[Re] ${parent.title }"/>
					</c:if>
					<c:if test="${!empty(qnaForm.title)}">
						<c:set var="tmpTitle" value="${qnaForm.title }"/>
					</c:if>
					<tr> 
						<th scope="row"><label for="${status.expression}"><ikep4j:message pre='${preUi}' key='formTitle'/></label></th>
						<td colspan="3">
							<div>
							<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w100" value="${tmpTitle }" size="40" />
							<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
							</div>
						</td> 
					</tr>				
				</spring:bind>
				
			
				<spring:bind path="qnaForm.contents">
					<tr> 
						<td colspan="4">
							<div id="editorDiv">
							<textarea id="contents" title="content" name="${status.expression}" class="tabletext" cols="" rows="20" >${status.value}</textarea>
							<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
							</div>
						</td> 
					</tr>				
				</spring:bind> 
				
				<tr>
					<td colspan="4">
					    <table style="width:100%;">
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
				
				<tr>
					<th scope="row"><label for="tag"><ikep4j:message pre='${preUi}' key='formTag'/></label></th>
					<td colspan="3">
						<c:if test="${empty(tagList)}">
							<c:set var="tagList" value="${parent.tagList }"/>
						</c:if>
						<div>
						<input name="tag" id="tag" title="<ikep4j:message pre='${preUi}' key='formTag'/>" class="inputbox w100" type="text" value="<c:forEach var="tag" items="${tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">,</c:if>${tag.tagName}</c:forEach>"/>
						<div class="tdInstruction">※ <ikep4j:message pre='${preUi}' key='formTagText'/></div>
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
			<li><a class="button" href="#a" title="<ikep4j:message pre='${preUi}' key='formCreateBtn'/>" onclick="submitQna();return false;"><span><ikep4j:message pre='${preUi}' key='formCreateBtn'/></span></a></li>
			<li><a class="button" href="#a" title="<ikep4j:message pre='${preUi}' key='formListBtn'/>" onclick="goList('${qnaForm.listType}');return false;"><span><ikep4j:message pre='${preUi}' key='formListBtn'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
	
	</form>
	
<!--guideWrapper End -->
</div>