<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.board.boardItem.updateBoardView.header" /> 
<c:set var="preDetail"  value="message.collpack.collaboration.board.boardItem.updateBoardView.detail" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" />
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.boardItem" />
<c:set var="preSearch"  value="message.collpack.collaboration.common.searchCondition" /> 
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
			
			$("#boardItemForm select[name=searchWorkPlaceName]").change(function() { 
				var workPlaceName = $("#searchForm select[name=searchWorkPlaceName]").val();
				$.post("<c:url value='/collpack/kms/admin/listTeamCodes.do'/>", {"workPlaceName" : workPlaceName}) 
				.success(function(data) {
					$("#searchGroupId").empty();
					$("#searchGroupId").append(data);
				})	
			}); 
			
		});	
		
		$jq("input[name=info]").click(function() {
			infoSum();
		}); 
		
		$jq("input[name=info1]").click(function() {
			$("input[name='info1']:checkbox").not($(this)).attr("checked",false);
			infoSum();
		});
		
		$jq("input[name=info2]").click(function() {
			$("input[name='info2']:checkbox").not($(this)).attr("checked",false);
			infoSum();
		});
		
		infoSum = function() {
			var mark = 0;
			var i = 0;	
			
			$("#boardItemForm input[name=info]").each(
				function(){
					
					if(this.checked){
						mark += Number(this.value);
						$("#boardItemForm input[name=itemDisplays_"+(i+1)+"]").val("1");
					}else{
						$("#boardItemForm input[name=itemDisplays_"+(i+1)+"]").val("0");
					}
					i++;
				}		
			)
			
			$("#boardItemForm input[name=info1]").each(
				function(){
					
					if(this.checked){
						mark += Number(this.value);
						$("#boardItemForm input[name=itemDisplays_"+(i+1)+"]").val("1");
					}else{
						$("#boardItemForm input[name=itemDisplays_"+(i+1)+"]").val("0");
					}
					i++;
				}		
			)
			
			$("#boardItemForm input[name=info2]").each(
				function(){
				
					if(this.checked){
						mark += Number(this.value);
						$("#boardItemForm input[name=itemDisplays_"+(i+1)+"]").val("1");
					}else{
						$("#boardItemForm input[name=itemDisplays_"+(i+1)+"]").val("0");
					}
					i++;
				}		
			)
			
			$jq('input[name=mark]').val(mark) ;
		};
		
				
		$("a.saveBoardItemButton").click(function() {
			//평가기준선택여부
			var i = 0;
			$("#boardItemForm input[name=info]").each(
				function(){
					if(this.checked){
						i++;
					}
				}
			)
			$("#boardItemForm input[name=info1]").each(
				function(){
					if(this.checked){
						i++;
					}
				}
			)
			$("#boardItemForm input[name=info2]").each(
				function(){
					if(this.checked){
						i++;
					}
				}
			)
			if(i == 0){
				alert("평가기준을 선택 해주세요.");
				return false;
			}
			//지식확인선택여부
			var $sel1 = $jq("input[name=checkStatus1]:checked");
			var $sel2 = $jq("input[name=checkStatus2]:checked");
			if($sel1.length < 1) {
				alert("지식 확인을 선택해주세요.");
				return;	
			}

			if($sel1.length > 0) {
				$("#boardItemForm input[name=status]").val("3"); //지식확인
				
				if($sel2.length > 0) {
					$("#boardItemForm input[name=status]").val("5"); //결재확인
				}
			}
			
			//alert($("#boardItemForm input[name=status]").val());
			$("#boardItemForm").trigger("submit"); 
			return false;
		}); 
		
		$("a.assessBoardItemButton").click(function() {

			var assessorIdStr  = $("#boardItemForm input[name=assessorId]").val();
			var assessorNameStr = $("#boardItemForm input[name=assessorName]").val();
			if(assessorIdStr == "" ){
				alert("전문가를 설정해주세요.");
				return false;
			}
			
			//alert("<c:url value='/collpack/kms/board/boardItem/assessMove.do?itemId=${boardItem.itemId}&status=2&assessorId="+assessorId+"&assessorName="+assessorName+"'/>")
			if(confirm("전문가에게 평가를 위임하시겠습니까?")) {
				
				$jq("#boardItemForm input[name=status]").val("2");
				//alert(assessorIdStr + " : " + assessorNameStr);
				$.get("<c:url value='/collpack/kms/board/boardItem/assessMove.do'/>" , {itemId :"${boardItem.itemId}",status:"2",assessorId:assessorIdStr, assessorName:assessorNameStr})
				.success(function(data) {
					location.href="<c:url value='/collpack/kms/board/boardItem/updateAssessItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}&amp;gubun=${gubun}'/>";
				})
				.error(function(event, request, settings) { alert("error"); }); 	
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
		
		var $addHtml  = ""; 
		var $addFullHtml = "";	
		
		setUser = function(data) {
			$jq(data).each(function(index) {
			    $addHtml.find('input[name=assessor]').val(data[index].userName +" " + data[index].jobTitleName + " " + data[index].teamName);
	            $addHtml.find('input[name=assessorId]').val(data[index].id);
	            $addHtml.find('input[name=assessorName]').val(data[index].userName);
			});	
		}
		
		$jq('#addPersonButton').live("click", function(event) {			
			event.which = "13";			
			//$addHtml = $(this).parent().parent();
			$addHtml = $(this).parent().parent().parent().parent();
			iKEP.searchUser(event, "N", setUser);
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


	
	/////////////////////////////////////////////////////////////////////
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
		
		$("body").ajaxLoadStart("button");
	
	
		targetForm.submit();
	
	};
	/////////////////////////////////////////////////////////////////////////
	
	
	
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
			<c:if test="${permission.isSystemAdmin and empty boardItem.expertId and boardItem.status < 3}"><%-- 관리자일경우 버튼 활성화 --%>
				<li><a class="button assessBoardItemButton" href="#a"><span>전문가평가위임</span></a></li>
			</c:if>		
			<c:if test="${permission.isSystemAdmin or user.userId eq boardItem.expertId}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
				<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			</c:if>  
			<!-- c:if test="${not popupYn}"-->
			<li><a class="button" href="<c:url value='/collpack/kms/board/boardItem/readEinfogradeItemView.do?itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&gubun=${gubun}'/>"><span>취소</span></a></li>
			<!-- /c:if-->  
		</ul>
	</div>	
</div> 
<!--//pageTitle End--> 
<div class="blockDetail"> 

<form id="boardItemForm" method="post" action="<c:url value='/collpack/kms/board/boardItem/updateEinfogradeItem.do'/>">
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
	<input name="status" 				type="hidden" value="${boardItem.status}"/>
	<input name="oriStatus" 			type="hidden" value="${boardItem.status}"/>
	
	<input name="msie"        			type="hidden" value="0" />
	<input name="hopeGrade"        		type="hidden" value="${boardItem.hopeGrade}" />
	<input name="oriRegistDate"        	type="hidden" value="<ikep4j:timezone pattern="yyyy-MM-dd" date="${boardItem.registDate}"/>" />
	<input name="registerId"        	type="hidden" value="${boardItem.registerId}" />
	<input name="registerName"        	type="hidden" value="${boardItem.registerName}" />
	
	<input name="itemDisplays_1"	type="hidden" value="${boardItem.itemDisplays[0]}" />
	<input name="itemDisplays_2"	type="hidden" value="${boardItem.itemDisplays[1]}" />
	<input name="itemDisplays_3"	type="hidden" value="${boardItem.itemDisplays[2]}" />
	<input name="itemDisplays_4"	type="hidden" value="${boardItem.itemDisplays[3]}" />
	<input name="itemDisplays_5"	type="hidden" value="${boardItem.itemDisplays[4]}" />
	<input name="itemDisplays_6"	type="hidden" value="${boardItem.itemDisplays[5]}" />
	<input name="itemDisplays_7"	type="hidden" value="${boardItem.itemDisplays[6]}" />
	<input name="itemDisplays_8"	type="hidden" value="${boardItem.itemDisplays[7]}" />
	<input name="itemDisplays_9"	type="hidden" value="${boardItem.itemDisplays[8]}" />
	<input name="itemDisplays_10"	type="hidden" value="${boardItem.itemDisplays[9]}" />
	<input name="itemDisplays_11"	type="hidden" value="${boardItem.itemDisplays[10]}" />
	
	<input name="itemDisplays_12"	type="hidden" value="${boardItem.itemDisplays[11]}" />
	<input name="itemDisplays_13"	type="hidden" value="${boardItem.itemDisplays[12]}" />
	<input name="itemDisplays_14"	type="hidden" value="${boardItem.itemDisplays[13]}" />
	
	<input name="itemDisplays_15"	type="hidden" value="${boardItem.itemDisplays[14]}" />
	<input name="itemDisplays_16"	type="hidden" value="${boardItem.itemDisplays[15]}" />
	<input name="itemDisplays_17"	type="hidden" value="${boardItem.itemDisplays[16]}" />

	<input name="gubun"	type="hidden" value="${gubun}" />
	<input type="hidden" name="ecmFileId" value="" /> 
	<input type="hidden" name="ecmFileName" value="" />
	<input type="hidden" name="ecmFilePath" value=""/>  
	<input type="hidden" name="ecmFileUrl1" value=""/> 
	<input type="hidden" name="ecmFileUrl2" value=""/>  
	
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
			<th scope="row">정보등급요청</th>
			<td colspan="3">
               <select title="정보등급요청" name="" disabled>
	               <option value="A" <c:if test="${boardItem.hopeGrade eq 'A'}">selected</c:if>>A</option>
	               <option value="B" <c:if test="${boardItem.hopeGrade eq 'B'}">selected</c:if>>B</option>
	               <option value="C" <c:if test="${boardItem.hopeGrade eq 'C'|| boardItem.hopeGrade eq '' || empty boardItem.hopeGrade}">selected</c:if>>C</option>
               </select> <span class="tdInstruction"> ※ A : 팀장이상 공개, B : 파트장이상 공개, C : 전사공개</span>
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
                <div class="blockDetail">
					<table summary="게시판등록">
						<caption></caption>
						<colgroup>
							<col width="12%" />
							<col width="21%" />
							<col width="12%" />
							<col width="22%" />
                            <col width="12%" />
							<col width="21%" />
						</colgroup>
						<tbody>
				
				<c:choose>
					<c:when test="${not empty boardItemList}">
				<spring:bind path="boardItemList.boardItemList">					
						<c:forEach var="refItemList" varStatus="varStatus" items="${status.value}">
               		  		<tr>
								<th scope="row">제목</th>
								<td colspan="5">
                                	<input name="refTitle" title="제목" class="inputbox w70" type="text" value="${refItemList.title}" readonly/>
                                	<input name="refItemId" value="${refItemList.itemId}" type="hidden"/>
                                    <!-- a class="button_s" href="#a"><span>검색</span></a -->
                                </td>
							</tr>
							<tr>
                                <th scope="row">등록자</th>
								<td><input name="refUserInfo" class="inputbox w30" type="text" value="${refItemList.registerName}" readonly/></td>
								<th scope="row">등록일</th>
								<td><input name="refRegisterDate" class="inputbox w30" type="text" value="<ikep4j:timezone date="${refItemList.registDate}" pattern="yyyy.MM.dd HH:mm:ss" />" readonly/></td>
                                <th scope="row">출처</th>
								<td><input name="refSource" class="inputbox w30" type="text" value="${refItemList.targetSource}" readonly/></td>
							</tr>
						</c:forEach>  
				</spring:bind>              		  		
					</c:when>
					<c:otherwise>
               		  		<tr>
								<th scope="row">제목</th>
								<td colspan="5">
                                	<input name="refTitle" title="제목" class="inputbox w70" type="text"  readonly/>
                                	<input name="refItemId" value="" type="hidden"/>
                                    <!-- a class="button_s" href="#a"><span>검색</span></a -->
                                </td>
							</tr>
							<tr>
                                <th scope="row">등록자</th>
								<td><input name="refUserInfo" value="" class="inputbox w30" type="text" readonly/></td>
								<th scope="row">등록일</th>
								<td><input name="refRegisterDate" value="" class="inputbox w30" type="text" readonly/></td>
                                <th scope="row">출처</th>
								<td><input name="refSource" value="" class="inputbox w30" type="text" readonly/></td>
							</tr>
					</c:otherwise>
				</c:choose>		
                            <tr>
								<th scope="row">평가기준</th>
								<td colspan="5">
                                <div class="blockDetail_3">
                                    <table summary="평가기준">
                                        <caption></caption>
                                        <colgroup>
                                        	<col width="12%"/>
                                            <col width="15%"/>
                                            <col width="40%"/>
                                            <col width="33%"/>
                                        </colgroup>
                                        <tbody>
                                            <tr>
                                            	<th rowspan="3" class="textRight" scope="row">일반정보/원문 게시판</th>
                                                <th scope="row" class="textRight">지식특성</th>
                                                <td colspan="2"><label><input id="checkboxInfo" name="info" class="checkbox" title="checkbox" type="checkbox" value="1" <c:if test="${boardItem.itemDisplays[0] == 1}">checked</c:if>/> ${boardItem.assessNames[0]} (+1)</label></td>
                                            </tr>
                                            <tr>
                                                <th class="textRight">지식내용 및 구성</th>
                                                <td colspan="2">
                                                <label><input id="checkboxInfo" name="info" class="checkbox" title="checkbox" type="checkbox" value="1" checked/> ${boardItem.assessNames[1]} (+1)</label></br>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="textRight">지식활용</th>
                                                <td colspan="2"><label><input id="checkboxInfo" name="info" class="checkbox" title="checkbox" type="checkbox" value="1" <c:if test="${boardItem.itemDisplays[2] == 1}">checked</c:if>/> ${boardItem.assessNames[2]} (+1)</label></td>
                                            </tr>
                                            <tr>
                                              <th rowspan="2" class="textRight" scope="row">업무노하우</th>
                                              <th scope="row" class="textRight">중요도</th>
                                              <td colspan="2">
                                              	<label><input id="checkboxInfo" name="info1" class="checkbox" title="checkbox" type="checkbox" value="3" <c:if test="${boardItem.itemDisplays[3] == 1}">checked</c:if>/> ${boardItem.assessNames[3]} (3점)</label> &nbsp;
                                                <label><input id="checkboxInfo" name="info1" class="checkbox" title="checkbox" type="checkbox" value="2" <c:if test="${boardItem.itemDisplays[4] == 1}">checked</c:if>/> ${boardItem.assessNames[4]} (2점)</label> &nbsp;
                                                <label><input id="checkboxInfo" name="info1" class="checkbox" title="checkbox" type="checkbox" value="1" <c:if test="${boardItem.itemDisplays[5] == 1}">checked</c:if>/> ${boardItem.assessNames[5]} (1점)</label>
                                              </td>
                                            </tr>
                                            <tr>
                                              <th scope="row" class="textRight">활용도</th>
                                              <td colspan="2">
                                              	<label><input id="checkboxInfo" name="info2" class="checkbox" title="checkbox" type="checkbox" value="3" <c:if test="${boardItem.itemDisplays[6] == 1}">checked</c:if>/> ${boardItem.assessNames[6]} (3점)</label> &nbsp;
                                                <label><input id="checkboxInfo" name="info2" class="checkbox" title="checkbox" type="checkbox" value="2" <c:if test="${boardItem.itemDisplays[7] == 1}">checked</c:if>/> ${boardItem.assessNames[7]} (2점)</label> &nbsp;
                                                <label><input id="checkboxInfo" name="info2" class="checkbox" title="checkbox" type="checkbox" value="1" <c:if test="${boardItem.itemDisplays[8] == 1}">checked</c:if>/> ${boardItem.assessNames[8]} (1점)</label>
                                              </td>
                                            </tr>
                                            <tr>
                                                <th colspan="2" scope="row" class="textRight">평가점수</th>
                                                <td colspan="2"><input type="text" class="inputbox" title="inputbox" name="mark" value="${boardItem.mark}" size="20" readonly/></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                </td>
							</tr>
                            <tr>
                                <th scope="row">지식등급</th>
								<td>
                                <select title="지식등급" name="infoGrade">
                                    <option value="B" <c:if test="${boardItem.infoGrade eq 'B' || (boardItem.hopeGrade eq 'B' && (boardItem.infoGrade eq '' || empty boardItem.infoGrade))}">selected</c:if>>보안</option>
                                    <option value="C" <c:if test="${boardItem.infoGrade eq 'C'|| boardItem.infoGrade eq 'D' || (boardItem.hopeGrade eq 'C' && (boardItem.infoGrade eq '' || empty boardItem.infoGrade))}">selected</c:if>>공유</option>
                                    <option value="E" <c:if test="${boardItem.infoGrade eq 'E'}">selected</c:if>>미공유</option>
                                </select>
                                </td>
								<th scope="row">지식확인</th>
								<td><label><input name="checkStatus1" class="checkbox" title="checkbox" type="checkbox" value="3" <c:if test="${boardItem.status > 2}">checked disabled="true"</c:if>/> 확인</label></td>
                                <th scope="row">결재확인</th>
								<td><label><input name="checkStatus2" class="checkbox" title="checkbox" type="checkbox" value="5" <c:if test="${user.userId eq boardItem.expertId}">disabled="true"</c:if><c:if test="${boardItem.status eq 5}">checked</c:if>/> 확인</label></td>
							</tr>
               		  		<tr>
								<th scope="row">전문가</th>
								<td colspan="5">
                                	<input name="assessor" title="전문가" class="inputbox w20" type="text" value="${boardItem.expertName}" readonly/>
                                	<input name="assessorId" value="${boardItem.expertId}" type="hidden"/>
                                	<input name="assessorName" value="${boardItem.expertName}" type="hidden"/>
                                    <c:if test="${permission.isSystemAdmin and empty boardItem.expertId}"><%-- 관리자일경우 버튼 활성화 --%>
                                    <a class="button_s" id="addPersonButton" href="#a"><span>Search</span></a>
                                    </c:if>
                                </td>
							</tr>									
                        </tbody>
					</table>
				</div>
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
		<c:if test="${permission.isSystemAdmin and empty boardItem.expertId and boardItem.status < 3}"><%-- 관리자일경우 버튼 활성화 --%>
			<li><a class="button assessBoardItemButton" href="#a"><span>전문가평가위임</span></a></li>
		</c:if>
		<c:if test="${permission.isSystemAdmin or user.userId eq boardItem.expertId}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
			<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
		</c:if>  
		<!-- c:if test="${not popupYn}"-->
		<li><a class="button" href="<c:url value='/collpack/kms/board/boardItem/readEinfogradeItemView.do?itemId=${boardItem.itemId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}&gubun=${gubun}'/>"><span>취소</span></a></li>
		<!-- /c:if--> 
	 </ul>
</div>
<c:if test="${popupYn}"></div></c:if>
<!--//blockButton End-->