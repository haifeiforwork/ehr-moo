<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/DextfileUploadInit.jsp"%>

<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>

<c:set var="preUi" 			value="ui.collpack.ideation.ideaForm" />
<c:set var="preMenu" 			value="ui.collpack.qna.qnaMenu" />
<c:set var="preUiQna" 			value="ui.collpack.qna.qnaForm" />
<c:set var="refererUrl"><%=request.getHeader("referer") %></c:set> <%--전페이지주소--%>

<c:choose>
	<c:when test="${fn:contains(refererUrl,'lastList.do') || fn:contains(refererUrl,'keyIssueList.do') || fn:contains(refererUrl,'activityList.do') || fn:contains(refererUrl,'businessList.do') }">
		<c:set var="listUrl" value="${refererUrl }"/>
	</c:when>
	<c:otherwise>
		<c:set var="listUrl" value="main.do"/>
	</c:otherwise>
</c:choose>

<script type="text/javascript">

//activeX editor 사용여부
var useActXEditor = "${useActiveX}";

(function($){


		/* editor 초기화  */
	initEditorSet = function() {
		// ActiveX Editor 사용 여부가 Y인 경우
	    if(useActXEditor == "Y"){
			// 브라우저가 ie인 경우
			if ($.browser.msie) {
				// div 높이, 넓이 세팅
				var cssObj = {
			      'height' : '450px',
			      'width' : '100%'
			    };
				$('#editorDiv').css(cssObj);

	<c:choose>
		<c:when test="${empty(param.itemId)}">
				// hidden 필드 추가(contents)
				iKEP.createActiveXEditor("#editorDiv","${user.localeCode}","#contents",0);
		</c:when>
		<c:otherwise>
				// hidden 필드 추가(contents)
				iKEP.createActiveXEditor("#editorDiv","${user.localeCode}","#contents",1);
		</c:otherwise>
	</c:choose>

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


	var oldFiles;
	var oldSizes;
	
	$jq(document).ready(function() {
		

		// editor 초기화
		initEditorSet(); 	
		
		$("#saveButton").click(function() { 
			$("#ideaForm").trigger("submit"); 
			return false;  
		});

		$("#saveButton2").click(function() { 
			$("#ideaForm").trigger("submit"); 
			return false;  
		});

	    new iKEP.Validator("#ideaForm",  {
			rules : {
				title :	{
					required : true,
					maxlength  : 500
				}
				//,contents : "required"
				
						
			},
			messages : {
				title : {
					required : "<ikep4j:message key='NotEmpty.idIdea.title'/>",
					maxlength : "<ikep4j:message key='Size.idIdea.title'/>"
				}
				,contents : {
					required : "<ikep4j:message key='NotEmpty.idIdea.contents'/>"
				}
			
			},
			notice : {
				title : "<ikep4j:message key='NotEmpty.idIdea.titleNotice'/>",
				contents : "<ikep4j:message key='NotEmpty.idIdea.contentsNotice'/>"
				
			},
		    submitHandler : function(form) { 
		    	// ActiveX Editor 사용 여부가 Y인 경우
		    	if(useActXEditor == "Y"){
			    	if (!$.browser.msie) {
			    		//ekeditor 데이타 업데이트
			    		var editor = $("#contents").ckeditorGet(); 
						editor.updateElement();
						//에디터 내 이미지 파일 링크 정보 세팅
						createEditorFileLink("ideaForm");
			    	}
		    	}else{
		    		//ekeditor 데이타 업데이트
		    		var editor = $("#contents").ckeditorGet(); 
					editor.updateElement();
					//에디터 내 이미지 파일 링크 정보 세팅
					createEditorFileLink("ideaForm");
		    	}
		    	
		    	if((oldSizes !=document.getElementById("FileUploadManager").Size)||(document.all["FileUploadManager"].Count>0)){//삭제한것이 있거나 추가한것이 있으면
	                  btnTransfer_Onclick("ideaForm");
	              }else{
	                  //alert("파일업로드 없음");            
	                  oldFileSetting(oldFiles , form);
	                  writeSubmit(form);
	              }
		    }
		}); 

		/*var uploaderOptions = {     // 사용할 수 있는 옵션 항목
				<c:if test="${!empty(fileDataListJson)}">
					files : ${fileDataListJson}, 
				</c:if>
		        allowFileType  :"all"  ,
				isUpdate : true ,
				onLoad : function() {
					iKEP.iFrameContentResize();
				}
		    };
		//파일업로드 컨트롤로 생성   
	    var fileController = new iKEP.FileController("#ideaForm", "#fileUploadArea", uploaderOptions);*/
	    
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


   		
	});
	
    writeSubmit = function(targetForm){
    	//에디터 감추기
        if(useActXEditor == "Y"){
            if ($.browser.msie) {
                //태그프리 선택 탭을 디자인으로 변경 후 저장한다.
                var tweTab = document.twe.ActiveTab;
                if ( tweTab != 1 ) {
                    document.twe.ActiveTab = 1;
                }
                //태그프리 Mime 데이타 세팅
                var tweBody = document.twe.MimeValue();
                $('input[name="contents"]').val(tweBody);
                $("#twe").css("visibility","hidden");
            }
        }
        
        $("body").ajaxLoadStart("button");
        targetForm.submit();
    };
	

	
	
})(jQuery);  





	function goList(){
		var param = '<c:forEach var="ram" items="${param}" ><c:if test="${ram.value != ''}">${ram.key}=${ram.value}&</c:if></c:forEach>';
		
		location.href='${idea.listType}List.do?'+param;
	}
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
	var form = document.ideaForm;
	document.twe.HtmlValue = $jq("input[name=contents]").val().replaceAll("src=\""+iKEP.getContextRoot(), "src=\""+iKEP.getWebAppPath());
	$jq("input[name=title]").focus();
</script>

<form id="editorFileUploadParameter" action="">
    <input type="hidden" name="interceptorKey"  value="ikep4.system"/>
</form>

<h1 class="none">Contents Area</h1>
	
<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3>
	<c:choose>
		<c:when test="${empty(param.itemId)}">
			<ikep4j:message pre='${preUi}' key='subTitle'/>
		</c:when>
		<c:otherwise>
			<ikep4j:message pre='${preUi}' key='subTitle.update'/>
		</c:otherwise>
	</c:choose>
	</h3>
</div>
<!--//subTitle_2 End-->

<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button" href="#a" id="saveButton" title="<ikep4j:message pre='${preUi}' key='save'/>"><span><ikep4j:message pre='${preUi}' key='save'/></span></a></li>
		<li><a class="button" href="${listUrl }"  title="<ikep4j:message pre='${preUi}' key='list'/>"><span><ikep4j:message pre='${preUi}' key='list'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->

<!--blockDetail Start-->
<div class="blockDetail">
	<form id="ideaForm" method="post" action="<c:url value='createIdea.do'/>" >
		<spring:bind path="idea.listType">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="" />
		</spring:bind> 
		<spring:bind path="idea.itemId">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="" />
		</spring:bind>
		<input name="msie"        			type="hidden" value="0" />
		<input name="docIframe" type="hidden" value="${param.docIframe}"  />
		
	<table summary="create">
		<caption></caption>
		<tbody>
			<spring:bind path="idea.title">
			<tr>
				<th width="18%" scope="row"><span class="colorPoint">*</span><label for="${status.expression}"><ikep4j:message pre='${preUi}' key='title'/></label></th>
				<td><div>
					<input name="${status.expression}" id="${status.expression}" class="inputbox w100" type="text" value="${status.value}"/>
					<c:forEach items="${status.errorMessages}" var="error">
			       		<label for="${status.expression}" class="serverError"> ${error}</label>
			        </c:forEach>
					</div>
				</td>
			</tr>
			</spring:bind>
			<spring:bind path="idea.categoryId">
			<tr id="categoryDiv" style="display:"> 
				<th scope="row"><label for="${status.expression}"><ikep4j:message pre='${preUiQna}' key='formCategory'/></label></th>
				<td>
					<select id="${status.expression}" name="${status.expression}">
							<option value="" <c:if test="${empty status.value}">selected="selected"</c:if>><ikep4j:message pre='${preMenu}' key='cetera'/></option>
						<c:forEach var="category" items="${categoryList}">
							<option value="${category.categoryId}" <c:if test="${category.categoryId eq status.value}">selected="selected"</c:if>>
								${category.categoryName}
							</option>
						</c:forEach>
					</select>
				</td> 
			</tr>
			</spring:bind>
			<spring:bind path="idea.contents">
			<tr>
			<td colspan="2" class="ckeditor"> 
				<div id="editorDiv">					
					<textarea id="contents"
					name="${status.expression}" 
					class="inputbox w100 fullEditor"
					cols="" 
					rows="5" 
					title="<ikep4j:message pre='${preUi}' key='contents'/>">${status.value}</textarea>					
				</div> 				
			</td>
			</tr>
			</spring:bind> 


			<spring:bind path="idea.tag">
			<tr>
				<th scope="row"><label for="tag"><ikep4j:message pre='${preUi}' key='tag'/></label></th>
				<td><div>
					<c:choose>
						<c:when test="${!empty(status.value)}">
							<c:set var="tagText" value="${status.value}"/>
						</c:when>
						<c:otherwise>
							<c:set var="tagText" ><c:forEach var="tag" items="${tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">,</c:if>${tag.tagName}</c:forEach></c:set>
						</c:otherwise>
					</c:choose>
					<input id="tag" name="tag" class="inputbox w100" type="text" value="${tagText}"/>
					<div class="tdInstruction">※ <ikep4j:message pre='${preUi}' key='tagContents'/></div>
					<c:forEach items="${status.errorMessages}" var="error">
			       		<label for="${status.expression}" class="serverError"> ${error}</label>
			        </c:forEach>
					</div>
				</td>
			</tr>	
			</spring:bind>
<!-- 			<tr>
				<td colspan="2"><div id="fileUploadArea"></div></td>
			</tr>						 -->
		</tbody>
	</table>
<BR>
		<div class="fileAttachArea">

							<div class="td">
								<div class="content">
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
								</div>
							</div>

		</div>


	</form>
</div>
<!--//blockDetail End-->
										
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button" href="#a" id="saveButton2" title="<ikep4j:message pre='${preUi}' key='save'/>"><span><ikep4j:message pre='${preUi}' key='save'/></span></a></li>
		<li><a class="button" href="${listUrl}"  title="<ikep4j:message pre='${preUi}' key='list'/>"><span><ikep4j:message pre='${preUi}' key='list'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
 
					
