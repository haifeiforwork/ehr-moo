<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.board.boardItem.updateBoardView.header" /> 
<c:set var="preDetail"  value="message.collpack.collaboration.board.boardItem.updateBoardView.detail" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" />
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.boardItem" />
<c:set var="preSearch"  value="message.collpack.collaboration.common.searchCondition" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%> 
 <%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script> 
<%@ include file="/base/common/DextfileUploadInit.jsp"%><%-- 파일업로드용 Import --%> 

<c:set var="user"   value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />	

<script type="text/javascript">
<!--  
var useActXEditor = "${useActiveX}";
(function($){	 
	$(document).ready(function() { 
		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		} 
		
		if(window.parent.menuMark != null) { 
			window.parent.menuMark("${boardItem.boardId}");
		}
		
		$("input[name=startDate]").attr("readonly", true).datepicker({
			onSelect : function(){
				var form = $(this)[0].form;
				var validator = $(form).validate();
				validator.element(this);
				validator.element("input[name=endDate]", form);
			},
		    showOn: "button",
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true,
		    buttonText : "<ikep4j:message pre='${preDetail}' key='startDate' />"
		});   
		
		$("input[name=endDate]").attr("readonly", true).datepicker({
			onSelect : function(){
				var form = $(this)[0].form;
				var validator = $(form).validate();
				validator.element(this);
				validator.element("input[name=startDate]", form);
			},
		    showOn: "button",
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true,
		    buttonText : "<ikep4j:message pre='${preDetail}' key='endDate' />"
		});    
		$jq("input[name=followBoardPermission]").click(function() {
			if($jq("input[name=followBoardPermission]:checked").val()==0)
			{
				$jq('#readPermissionDiv').show();
				iKEP.iFrameContentResize();
			}
			else{
				$('#readPermissionDiv').hide();
				$jq('#readPermissionList').empty();
				$jq('input[name=searchSubFlag]:hidden').val("0") ;
				iKEP.iFrameContentResize();
			}
		});
		
		$jq("input[name=readPermission]").click(function() {
			if($jq("input[name=readPermission]:checked").val()==4)
			{
				$jq('#readPermissionListDiv').show();
				iKEP.iFrameContentResize();
				
			}
			else{
				$('#readPermissionListDiv').hide();
				$jq('#readPermissionList').empty();
				$jq('input[name=searchSubFlag]:hidden').val("0") ;
				iKEP.iFrameContentResize();
			}
		});
		
		$jq("#deleteReadPermissionButton").click(function(event) {
			event.preventDefault();
			var $rPermissionList=$jq('#readPermissionList');
			$jq('option:selected',$rPermissionList).remove();
		});			
		
		$jq("#addReadPermissionButton").click(function() {
			// 조직도 팝업 테스트
			var dbItems = [];
			
			<c:if test="${!empty boardItem.aclReadPermissionList.groupDetailList}">
			<c:forEach var="item" items="${boardItem.aclReadPermissionList.groupDetailList}">
			dbItems.push({// 공통필수
				type:"group",				
				code:"${item.groupId }",
				name:"${item.groupName}",
				parent:"${item.parentGroupId }",
				groupTypeId:"${item.groupTypeId}",
				hasChild:"${item.childGroupCount}",
				searchSubFlag:<c:if test="${boardItem.searchSubFlag==1}">true</c:if><c:if test="${boardItem.searchSubFlag!=1}">false</c:if>
			});			
			</c:forEach>
			</c:if>
			
			<c:if test="${!empty boardItem.aclReadPermissionList.assignUserDetailList}">

			<c:forEach var="item" items="${boardItem.aclReadPermissionList.assignUserDetailList}">
			dbItems.push({// 공통필수
				type:"user",				
				id:"${item.userId }",
				jobTitle:"${item.jobTitleName }",
				name:"${item.userName }",
				searchSubFlag:false,
				teamName:"${item.teamName }"
			});	
			</c:forEach>
			</c:if>

		
			var items = [];
			var $sel = $jq("#boardItemForm").find("[name=readPermissionList]");
			
			$sel.children().each(function(idx) {
				//iKEP.debug(this);
				//iKEP.debug(dbItems[idx]);
				$jq.data(this, "data", dbItems[idx]);
			});
			
			$jq.each($sel.children(), function() {
				items.push($jq.data(this, "data"));
			});

			$controlType = $jq('input[name=controlType]:hidden').val() ;
			$controlTabType = $jq('input[name=controlTabType]:hidden').val() ;
			$selectType = $jq('input[name=selectType]:hidden').val() ;
			$selectMaxCnt = $jq('input[name=selectMaxCnt]:hidden').val() ;
			$searchSubFlag = $jq('input[name=searchSubFlag]:hidden').val() ;

			var url = "<c:url value='/support/addressbook/addresbookPopup.do'/>"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=" + $selectMaxCnt + "&searchSubFlag=" + $searchSubFlag;
			var callback = function(result){
				$sel.empty();
				$jq.each(result, function() {

					var tpl = "";
					
					switch(this.type) {
						case "group" : tpl = "addrBookItemGroup"; break;
						case "user" : tpl = "addrBookItemUser"; break;
					}
					
					if(this.type=="group")
						this.code="G:"+this.code;
					else
						this.id ="U:"+this.id;
					
					var $option = $jq.tmpl(tpl, this).appendTo($sel);

					$jq.data($option[0], "data", this);

					if( this.searchSubFlag == true ){
						$jq('input[name=searchSubFlag]:hidden').val("1") ;
					}
		
				})
			};
			iKEP.showAddressBook(callback, items, {selectType:"all", selectElement:$sel, isAppend:false, tabs:{common:1}});
			

			
		});	
				
		$("a.saveBoardItemButton").click(function() {
			$("#boardItemForm").trigger("submit"); 
			return false;
		}); 
		

		// editor 초기화
		initEditorSet();
		
		
		new iKEP.Validator("#boardItemForm", {   
			rules  : {
				title     		: {required : true, rangelength : [1, 1000] },
				targetSource	: {required : true},
				tag       		: {tagDuplicate : true}
			},
			messages : {
				title     		: {direction : "top",    required : "<ikep4j:message key="message.collpack.collaboration.NotNull.boardItem.title" />", rangelength : "<ikep4j:message key="message.collpack.collaboration.Size.boardItem.title" />"},
				targetSource	: {direction : "top",    required : "출처는 필수 입력항목입니다."},
				tag       		: {direction : "top", 	 tagDuplicate : "<ikep4j:message key="message.collpack.collaboration.TagDuplicate.boardItem.tag" />"} 
			},  
			
		    submitHandler : function(form) {  
	    			    	
		    	// ActiveX Editor 사용 여부가 Y인 경우
		    	if(useActXEditor == "Y"){
			    	if (!$.browser.msie) {
			    		//ekeditor 데이타 업데이트
			    		var editor = $("#contents").ckeditorGet(); 
						editor.updateElement();
						//에디터 내 이미지 파일 링크 정보 세팅
						createEditorFileLink("boardItemForm");
			    	} 			    	
		    	}else{
		    		//ekeditor 데이타 업데이트
		    		var editor = $("#contents").ckeditorGet(); 
					editor.updateElement();
					//에디터 내 이미지 파일 링크 정보 세팅
					createEditorFileLink("boardItemForm");
		    	}
				if($jq('input[name=followBoardPermission]:checked').val()==1){
					$jq('input[name=readPermission]').val('${board.readPermission}') ;
				}
			
				$jq("#readPermissionList>option").attr("selected",true);
						    	
		    	if(confirm("<ikep4j:message pre="${preMessage}" key="updateItem" />")) {
		    		
		    		if((oldSizes !=document.getElementById("FileUploadManager").Size)||(document.all["FileUploadManager"].Count>0)){//삭제한것이 있거나 추가한것이 있으면
		    			btnTransfer_Onclick("boardItemForm");
		    		}else{
		    			//alert("파일업로드 없음");
		    			
		    			oldFileSetting(oldFiles , form);
		    			writeSubmit(form);
		    		}
		    		
		    		



				}
		    }
		});    
		/*		
	    var uploaderOptions = {
	 		<c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if> 
			<c:if test="${board.fileAttachOption eq 0 and not empty board.allowType}">allowExt : "${board.allowType}",</c:if>
			<c:if test="${board.fileAttachOption eq 1 and not empty board.allowType}">allowFileType : "${board.allowType}",</c:if>
		    isUpdate : true,
	    	onLoad : function() {
	    		iKEP.iFrameContentResize();
	    	}
		};  
        
	    var fileController = new iKEP.FileController("#boardItemForm", "#fileUploadArea", uploaderOptions); 
	    */
		   dextFileUploadInit("${board.fileSize}" ,"${board.fileAttachOption}", "${board.allowType}");
		   
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
		if($("input[name=itemDisplayDummy]").is(":checked")) {
			$("input[name=itemDisplay]").val("1");
		} else {
			$("input[name=itemDisplay]").val("0");
		}
	
	
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
	    		var tweBodyObj = document.twe.GetBody();
	    		//에디터 내 이미지 파일 링크 정보 세팅
	    		createActiveXEditorFileLink(tweBodyObj,"boardItemForm");
	    		
	    		$("#twe").css("visibility","hidden");
	    	}
		}
		$("body").ajaxLoadStart();
		targetForm.submit();
	
	};
	
	/* editor 초기화  */
	initEditorSet = function() {
		// ActiveX Editor 사용 여부가 Y인 경우
	    if(useActXEditor == "Y"){
			// 브라우저가 ie인 경우
			if ($.browser.msie) {
				// div 높이, 넓이 세팅
				var cssObj = {
			      'height' : '450px',
			      'width' : '650px'
			    };
				$('#editorDiv').css(cssObj);
				// hidden 필드 추가(contents) - 수정모드
				iKEP.createActiveXEditor("#editorDiv","${user.localeCode}","#contents",1);
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
	
	var $addHtml  = ""; 
	var $addFullHtml = "";	
	//var rows = $("#infoWinner_").length+1;
	var rows = ${fn:length(boardItemList.boardItemList)};
	
	if(rows == 0){
		rows++
	}
	
	  
	$("#delButton").live("click", function(){
		
		if(rows > 1){
			var clickedRow = $(this).parent().parent().parent().parent().parent();
			clickedRow.remove();
			rows--;
		}else{
			$jq("input[name=refTitle]").val("");
			$jq("input[name=refItemId]").val("");
			$jq("input[name=refUserInfo]").val("");
			$jq("input[name=refRegisterDate]").val("");
			$jq("input[name=refTargetSource]").val("");
		}
			
	});
	
	$("#moveBoardItemButton1").live("click", function(){ 
		
		var isKnowhow = $("input[name=isKnowhow1]:checked").attr("value");
		var url = iKEP.getContextRoot() + "/collpack/kms/board/boardItem/viewBoardTreePopup.do?isKnowhow="+isKnowhow+"&orgBoardId=0";
		//var pageURL = "<c:url value='/collpack/kms/board/boardItem/viewBoardTreePopup.do' />?isKnowhow=0&orgBoardId=0";
		iKEP.popupOpen( url , {width:400, height:730 , callback:function(result){moveBoardItem1(result);}}, "MoveBoardItem");
	});

	moveBoardItem1 = function(result) {
		$('input[name="boardId"]').val(result);
		$.get("<c:url value='/collpack/kms/board/boardItem/getKmsMapName.do?boardId='/>" +result)
		.success(function(data) { 
			$("#kmsMapName").text(data);	
		})
		.error(function(event, request, settings) { alert("error"); });  
	};
	
	$("#addButton").live("click", function() { 
		
		var addRefItemId = "";
					  
		if($addFullHtml == ""){
			addRefItemId = $("#boardItemForm").find('input[name=refItemId]').val();
			
		}else{				
			addRefItemId = $addFullHtml.find('input[name=refItemId]:last').val();
		}
		
		if(addRefItemId == ""){
			//alert("<ikep4j:message pre='${preMessage}' key='required' />");
			alert("데이터를 선택해주세요.");
			return false;
		}			
		
		++rows;			
		if(rows > 6){
			//alert("<ikep4j:message pre='${preMessage}' key='maxRegist' />");
			alert("최대 등록 갯수는 6개입니다. ");
			return false;
		}
		
		$addFullHtml = $("#infoStart").append					
			("<div class=\"blockDetail\" id='infoWinner_'" + rows+">"
           +"		<table summary=\"게시판등록\">"
           +"			<caption></caption>"
           +"			<colgroup>"
           +"				<col width=\"12%\" />"
           +"				<col width=\"20%\" />"
           +"				<col width=\"12%\" />"
           +"				<col width=\"20%\" />"
           +"               <col width=\"12%\" />"
           +"				<col width=\"20%\" />"
           +"				<col width=\"4%\" />"
           +"			</colgroup>"
           +"			<tbody>"
           +" 		  		<tr>"
           +"					<th scope=\"row\">제목</th>"
           +"					<td colspan=\"5\">"
           +"                      	<input name=\"refTitle\" title=\"제목\" class=\"inputbox w70\" type=\"text\"  readonly/>"
           +"                      	<input name=\"refItemId\" value=\"\" type=\"hidden\"/>"
           +"                          <a class=\"button_s\"  id=\"addRefButton\" href=\"#a\"><span>검색</span></a>"
           +"                   </td>"
           +"                   <td class=\"textCenter\"><a href=\"#a\" id=\"addButton\"><img src=\"<c:url value='/base/images/icon/ic_btn_plus.gif'/>\" alt=\"\" /></a></td>"
           +"				</tr>"
           +"				<tr>"
           +"                   <th scope=\"row\">등록자</th>"
           +"					<td><input name=\"refUserInfo\" value=\"\" class=\"inputbox w30\" type=\"text\" readonly/></td>"
           +"					<th scope=\"row\">등록일</th>"
           +"					<td><input name=\"refRegisterDate\" value=\"\" class=\"inputbox w30\" type=\"text\" readonly/></td>"
           +"                   <th scope=\"row\">출처</th>"
           +"					<td><input name=\"refTargetSource\" value=\"\" class=\"inputbox w30\" type=\"text\" readonly/></td>"
           +"					<td class=\"textCenter\"><a href=\"#a\" id=\"delButton\"><img src=\"<c:url value='/base/images/icon/ic_btn_minus.gif'/>\" alt=\"\" /></a></td>"
           +"				</tr>"
           +"           </tbody>"
           +"		</table>"
           +"</div>");		
		
		iKEP.iFrameContentResize();
	});
	

	setRef = function(data) {
		
		$jq(data).each(function(index) {

            $addHtml.find('input[name=refTitle]').val(data[index].title);
            $addHtml.find('input[name=refItemId]').val(data[index].itemId);
            $addHtml.find('input[name=refUserInfo]').val(data[index].registerName);
            $addHtml.find('input[name=refRegisterDate]').val(data[index].registDate);
            $addHtml.find('input[name=refTargetSource]').val(data[index].targetSource);
		});	
	}
	
	$jq('#addRefButton').live("click", function(event) {			
		event.which = "13";			
		//$addHtml = $(this).parent().parent();
		$addHtml = $(this).parent().parent().parent().parent();
		
		//alert(setRef);
		iKEP.searchRef(event, "N", setRef);
	});	
	
	// 사용자 검색 팝업
	iKEP.popupRef = function(name, column, isMulti, callback) {
		var url = iKEP.getContextRoot() + '/collpack/kms/board/boardItem/popupRef.do?isMulti='+isMulti;
		
		iKEP.popupOpen(url, {width:800, height:560, callback:callback, argument:{name:name,column:column,isMulti:isMulti}}, "BoardItemSearch");
	};


	// 사용자 검색 : AJAX
	iKEP.searchRef = function(event, isMulti, callback, column) {

		if (event.which == '13' || event.which === undefined) {
			var $el = jQuery(event.target);
			var name = $el.val();
			
			if(column == undefined) {
				column= "title";
			}
			
			if(!name) {
				iKEP.popupRef(name, column, isMulti, callback);
			} else {
				jQuery.post(iKEP.getContextRoot()+"/support/popup/getUser.do", {name:name, column:column})
				.success(function(data) {
					
					switch(data.userName) {
						case "isMany" : iKEP.popupUser(name, column, isMulti, callback); break;
						case "isEmpty" : callback([]); break;
						default :
							callback([{
								type:"user",
								id:data.userId,
								empNo:data.empNo,
								userName:data.userName,
								jobTitleName:data.jobTitleName,
								group:data.groupId,
								teamName:data.teamName,
								email:data.mail,
								mobile:data.mobile
							}]);
					}
				})
				.error(function(event, request, settings) { alert("error"); });
			}
		}
	};
	
	
	
})(jQuery); 
//-->
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
	var form = document.boardItemForm;
	document.twe.HtmlValue = $jq("input[name=contents]").val();//.replaceAll("src=\""+iKEP.getContextRoot(), "src=\""+iKEP.getWebAppPath());
	$jq("input[name=title]").focus();
</script>

<c:if test="${popupYn}"><div class="contentIframe"></c:if>
<form id="editorFileUploadParameter" action="null"> 
	<input name="boardId"  value="${board.boardId}" type="hidden"/> 
	<input name="workspaceId"  value="${board.workspaceId}" type="hidden"/>		
	<input name="interceptorKey"  value="collpack.collaboration.board" type="hidden"/> 
</form>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!--pageTitle Start-->
<div id="pageTitle" class="btnline"> 
	<h2>${board.boardName}</h2> 
	<div class="blockButton"> 
		<ul>
			<c:if test="${permission.isSystemAdmin or (user.userId eq boardItem.registerId and boardItem.status < 3)}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
				<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
			</c:if>
			
			<c:if test="${pageGubun eq 'boardItem'}"> 
				<li><a class="button" href="<c:url value='/collpack/kms/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
				<c:if test="${not popupYn}">	
					<li><a class="button" href="<c:url value='/collpack/kms/board/boardItem/listBoardItemView.do?boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
				</c:if> 
			</c:if>
			
			<c:if test="${pageGubun eq 'latestItem'}"> 
				<li><a class="button" href="<c:url value='/collpack/kms/board/boardItem/readLatestItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}&amp;isKnowhow=${isKnowhow}&amp;pageGubun=${pageGubun}'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
				<c:if test="${not popupYn}">	
					<li><a class="button" href="<c:url value='/collpack/kms/board/boardItem/listLatestItemView.do?boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}&amp;isKnowhow=${isKnowhow}&amp;pageGubun=${pageGubun}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
				</c:if> 
			</c:if>
			
			<c:if test="${pageGubun eq 'searchItem'}"> 
				<li><a class="button" href="<c:url value='/collpack/kms/board/boardItem/readSearchItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}&amp;isKnowhow=${isKnowhow}&amp;pageGubun=${pageGubun}'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
				<c:if test="${not popupYn}">	
					<li><a class="button" href="<c:url value='/collpack/kms/board/boardItem/listSearchItemView.do?boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}&amp;isKnowhow=${isKnowhow}&amp;pageGubun=${pageGubun}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
				</c:if> 
			</c:if>			
		</ul>
	</div>	
</div> 
<!--//pageTitle End--> 
<div class="blockDetail"> 

<form id="boardItemForm" method="post" action="<c:url value='/collpack/kms/board/boardItem/updateBoardItem.do'/>">
	<input name="controlTabType" title="" type="hidden" value="0:1:0:0" />
	<input name="controlType" title="" type="hidden" value="ORG" />
	<input name="selectType" title="" type="hidden" value="ALL" />
	<input name="selectMaxCnt" title="" type="hidden" value="100" />
	<input name="searchSubFlag" title="" type="hidden" value="" />	
	
	<input name="searchConditionString" type="hidden" value="${searchConditionString}" /> 		
	<input name="popupYn"               type="hidden" value="${popupYn}" /> 		
	<input name="boardId"               type="hidden" value="${boardItem.boardId}" /> 		
	<input name="itemId"                type="hidden" value="${boardItem.itemId}" /> 		
	<input name="itemDisplay"           type="hidden" value="${boardItem.itemDisplay}" />
	<input name="itemType" type="hidden" value="0"/>
	<input name="msie"        			type="hidden" value="0" />
	
	<input name="pageGubun" type="hidden" value="${pageGubun}"/>
	<input name="status" type="hidden" value="${boardItem.status}"/>
		
	<table summary="<ikep4j:message pre="${preDetail}" key="summary" />">
		<caption></caption>
		<col style="width: 15%"/>
		<col style="width: 35%"/>
		<col style="width: 10%"/>
		<col style="width: 40%"/>
		<tbody> 
		<tr> 
			<spring:bind path="boardItem.title">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3"> 
				<div>
					<input 
					name="${status.expression}" 
					type="text" 
					class="inputbox" style="width: 80%;" 
					value="${status.value}" 
					size="1000" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/> 
					<c:forEach items="${status.errorMessages}" var="error">
					    <label for="${status.expression}" class="serverError"> ${error}</label>
					</c:forEach> 				
				</spring:bind>
				</div>		
			</td> 
		</tr>				
		<tr>  
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registerName' /></th>
			<td>
				${boardItem.registerName} ${boardItem.groupName}
			</td>
			<th scope="row">출처</th>
			<td>
			<spring:bind path="boardItem.targetSource">
				<input name="targetSource" title="출처" class="inputbox w30" type="text" value="${status.value}"/>
			</spring:bind>
			</td>   	 
		</tr>
        <tr>
			<th scope="row">지식분류</th>
			<td>
				<label>
				<input name="isKnowhow1" type="radio" class="radio" value="1" title="지식분류" <c:if test="${boardItem.isKnowhow eq '1'}">checked="checked"</c:if>/> 
				일반정보
				</label>
			 
				<label>
				<input name="isKnowhow1" type="radio" class="radio" value="0" title="지식분류" <c:if test="${boardItem.isKnowhow eq '0'}">checked="checked"</c:if>/> 
				업무노하우
				</label>
			</td>
			<th scope="row">지식맵</th>
			<td>
				<span id="kmsMapName">${kmsMapName}</span>	
				<a id="moveBoardItemButton1" class="button_s"><span>지식맵</span></a>
			</td>
		</tr>			
		<tr>   
			<spring:bind path="boardItem.tag">
			<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
			<td colspan="3">
				<div>
				<input 
				name="${status.expression}" 
				type="text" 
				class="inputbox w40" 
				value="${status.value}" 
				size="40" 
				title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"/>
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
				<c:forEach items="${status.errorMessages}" var="error">
				    <label for="${status.expression}" class="serverError"> ${error}</label>
				</c:forEach>
				</div>		
			</td>		
			</spring:bind>   
		</tr>			
		<tr>  
			<spring:bind path="boardItem.contents">
			<td colspan="4" class="ckeditor">
				<div id="editorDiv"">
					<textarea 
					id="contents"
					name="${status.expression}" 
					class="inputbox w100 fullEditor"
					cols="" 
					rows="5" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />">${status.value}</textarea>
				</div> 				
			</td> 
			</spring:bind> 
		</tr>
		</tbody> 
	</table>
	
                <!--blockDetail Start-->
                <div id="pageTitle">
					<h2>활용지식</h2>
				</div>	
                <div class="mb5"></div>
                <div id="infoStart">


				<c:choose>
					<c:when test="${not empty boardItemList}">
				<spring:bind path="boardItemList.boardItemList">					
						<c:forEach var="refItemList" varStatus="varStatus" items="${status.value}" >
                <div class="blockDetail" id="infoWinner_${varStatus.index}">						
					<table summary="게시판등록">
						<caption></caption>
						<colgroup>
							<col width="12%" />
							<col width="20%" />
							<col width="12%" />
							<col width="20%" />
                            <col width="12%" />
							<col width="20%" />
							<col width="4%" />
						</colgroup>
						<tbody>						
               		  		<tr>
								<th scope="row">제목</th>
								<td colspan="5">
                                	<input name="refTitle" title="제목" class="inputbox w70" type="text" value="${refItemList.title}" readonly/>
                                	<input name="refItemId" value="${refItemList.itemId}" type="hidden"/>
                                    <a class="button_s" id="addRefButton" href="#a"><span>검색</span></a>
                                </td>
                                <td class="textCenter"><a href="#a" id="addButton"><img src="<c:url value='/base/images/icon/ic_btn_plus.gif'/>" alt="" /></a></td>
							</tr>
							<tr>
                                <th scope="row">등록자</th>
								<td><input name="refUserInfo" class="inputbox w30" type="text" value="${refItemList.registerName}" readonly/></td>
								<th scope="row">등록일</th>
								<td><input name="refRegisterDate" class="inputbox w30" type="text" value="<ikep4j:timezone date="${refItemList.registDate}" pattern="yyyy.MM.dd HH:mm:ss" />" readonly/></td>
                                <th scope="row">출처</th>
								<td><input name="refTargetSource" class="inputbox w30" type="text" value="${refItemList.targetSource}" readonly/></td>
								<td class="textCenter"><a href="#a" id="delButton"><img src="<c:url value='/base/images/icon/ic_btn_minus.gif'/>" alt="" /></a></td>
							</tr>
                        </tbody>
					</table>	
				</div>											
						</c:forEach>  
				</spring:bind>              		  		
					</c:when>
					<c:otherwise>

					</c:otherwise>
				</c:choose>
				
				<c:if test="${fn:length(boardItemList.boardItemList) == 0}">
                <div class="blockDetail" id="infoWinner_0">					
					<table summary="게시판등록">
						<caption></caption>
						<colgroup>
							<col width="12%" />
							<col width="20%" />
							<col width="12%" />
							<col width="20%" />
                            <col width="12%" />
							<col width="20%" />
							<col width="4%" />
						</colgroup>
						<tbody>					
               		  		<tr>
								<th scope="row">제목</th>
								<td colspan="5">
                                	<input name="refTitle" title="제목" class="inputbox w70" type="text"  readonly/>
                                	<input name="refItemId" value="" type="hidden"/>
                                    <a class="button_s" id="addRefButton" href="#a"><span>검색</span></a>
                                </td>
                                <td class="textCenter"><a href="#a" id="addButton"><img src="<c:url value='/base/images/icon/ic_btn_plus.gif'/>" alt="" /></a></td>
							</tr>
							<tr>
                                <th scope="row">등록자</th>
								<td><input name="refUserInfo" value="" class="inputbox w30" type="text" readonly/></td>
								<th scope="row">등록일</th>
								<td><input name="refRegisterDate" value="" class="inputbox w30" type="text" readonly/></td>
                                <th scope="row">출처</th>
								<td><input name="refTargetSource" value="" class="inputbox w30" type="text" readonly/></td>
								<td class="textCenter"><a href="#a" id="delButton"><img src="<c:url value='/base/images/icon/ic_btn_minus.gif'/>" alt="" /></a></td>
							</tr>
                        </tbody>
					</table>							
				</div>
				</c:if>									


				</div>
                <!--//blockDetail End-->
	
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
	</form> 
</div> 
<!--//blockDetail End-->			
							
<!--blockButton Start-->
<div class="blockButton"> 
	<ul> 
		<c:if test="${permission.isSystemAdmin or (user.userId eq boardItem.registerId and boardItem.status < 3)}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
			<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
		</c:if>  
			
		<c:if test="${pageGubun eq 'boardItem'}"> 
			<li><a class="button" href="<c:url value='/collpack/kms/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
			<c:if test="${not popupYn}">	
				<li><a class="button" href="<c:url value='/collpack/kms/board/boardItem/listBoardItemView.do?boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
			</c:if> 
		</c:if>
		
		<c:if test="${pageGubun eq 'latestItem'}"> 
			<li><a class="button" href="<c:url value='/collpack/kms/board/boardItem/readLatestItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
			<c:if test="${not popupYn}">	
				<li><a class="button" href="<c:url value='/collpack/kms/board/boardItem/listLatestItemView.do?boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
			</c:if> 
		</c:if>
		
		<c:if test="${pageGubun eq 'searchItem'}"> 
			<li><a class="button" href="<c:url value='/collpack/kms/board/boardItem/readSearchItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
			<c:if test="${not popupYn}">	
				<li><a class="button" href="<c:url value='/collpack/kms/board/boardItem/listSearchItemView.do?boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
			</c:if> 
		</c:if>	
	 </ul>
</div>
<c:if test="${popupYn}"></div></c:if>
<!--//blockButton End-->