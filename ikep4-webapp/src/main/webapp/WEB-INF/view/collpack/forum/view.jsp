<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<%@ page import="com.lgcns.ikep4.collpack.forum.constants.ForumConstants" %>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>

<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>

<c:set var="preUi" 			value="ui.collpack.forum.view" />
<c:set var="preResource" 	value="message.collpack.forum" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="preMail"  value="message.collpack.qna.expert.mail" />
<c:set var="refererUrl"><%=request.getHeader("referer") %></c:set> <%--전페이지주소--%>

<c:choose>
	<c:when test="${fn:contains(refererUrl,'myActivity.do') || fn:contains(refererUrl,'popularList.do') || fn:contains(refererUrl,'bestList.do') || fn:contains(refererUrl,'popularList.do') || fn:contains(refererUrl,'lastList.do')}">
		<c:set var="listUrl" value="${refererUrl }"/>
	</c:when>
	<c:otherwise>
		<c:set var="listUrl" value="main.do"/>
	</c:otherwise>
</c:choose>

<c:if test="${param.docIframe == 'true' }">
	<c:set var="listUrl" value="lastList.do?docIframe=true"/>
</c:if>

<script type="text/javascript">
//<![CDATA[
           
	//activeX editor 사용여부
	var useActXEditor = "${useActiveX}";

	//validation
	var itemValidOptions = {
		rules : {
			title :	{
				required : true,
				maxlength  : 500
			}
			//,contents : "required"
			,tag :	{
				required : true,
				maxlength  : 100
				,tagCount :10
				,tagDuplicate: true
				,tagWord : true
			}
		},
		messages : {
			title : {
				required : "<ikep4j:message key='NotEmpty.frItem.title' />",
				maxlength : "<ikep4j:message key='Size.frItem.title' />"
			}
			,contents : {
				required : "<ikep4j:message key='NotEmpty.frItem.contents'/>"
			}
			,tag : {
				direction : "bottom", 
				required : "<ikep4j:message key='NotEmpty.frItem.tagName'/>",
				maxlength : "<ikep4j:message key='Size.frDiscussion.tagName' arguments='100'/>"
				,tagCount :"<ikep4j:message key='MaxCount.collpack.forum.tag' arguments='10'/>"
				,tagDuplicate :"<ikep4j:message key='Duplicate.collpack.forum.tag'/>"	
				,tagWord :"<ikep4j:message key='Check.forum.tagWord'/>"
			}
		},
		notice : {
			title : "<ikep4j:message key='NotEmpty.frItem.titleNotice' />",
			contents : "<ikep4j:message key='NotEmpty.frItem.contentsNotice' />",
			tag: "<ikep4j:message key='NotEmpty.frItem.tagNameNotice'/>"
		},
		submitHandler : function(form) {
			addItemAction();
			
		}

	};
	
	
	var lineValidOptions = {
			rules : {
				contents : {
					required : true,
					maxlength  : 1500
				}
			},
			messages : {
				contents : {
					required : "<ikep4j:message key='NotEmpty.frLinereply.contents'/>"
					,maxlength : "<ikep4j:message key='Size.frLinereply.contents'/>"
				}
			},
			notice : {
				contents : "<ikep4j:message key='NotEmpty.frLinereply.contentsNotice'/>"
			},
			submitHandler : function(form) {
				addReplyAction(form.tmpId.value);
				
			}
			
		};

	var lineValid;
	var itemValid;

	$jq(document).ready(function() {
		
		lineValid = new iKEP.Validator('#linereplyForm_${frItem.itemId}', lineValidOptions);
		
		//즐겨찾기
		<c:choose>
			<c:when test="${isFavorite == 'true'}">
				toggleFavorteImg('del', '${frItem.itemId}');
			</c:when>
			<c:otherwise>
				toggleFavorteImg('add', '${frItem.itemId}');
			</c:otherwise>
		</c:choose>

	});

	
	//파라미터
	var param = '<c:forEach var="ram" items="${param}" ><c:if test="${ram.value != '' && ram.key != \'itemId\' && ram.key != \'qnaParentId\'}">${ram.key}=${ram.value}&</c:if></c:forEach>';


	function addItemAction(){
		
		var f = $jq('#itemForm');
		
		var discussionId = f.find(':input[name=discussionId]').val();
		var itemId = f.find(':input[name=itemId]').val();
		
		var title = f.find(':input[name=title]').val();
		var contents = f.find(':input[name=contents]').val();
		var tag = f.find(':input[name=tag]').val();
		var msie = f.find(':input[name=msie]').val();

		 // ActiveX Editor 사용 여부가 Y인 경우
    	if(useActXEditor != "Y" || !$jq.browser.msie){
    		//ekeditor 데이타 업데이트
    		var editor = $jq("#itemContents").ckeditorGet(); 
			editor.updateElement();
			//에디터 내 이미지 파일 링크 정보 세팅
			createEditorFileLink("itemForm");
    	}
    	
		//에디터 감추기
		if(useActXEditor == "Y" && $jq.browser.msie){

    		//태그프리 선택 탭을 디자인으로 변경 후 저장한다.
    		var tweTab = document.twe.ActiveTab;
    		if ( tweTab != 1 ) {
    			document.twe.ActiveTab = 1;
    		}
    		//태그프리 Mime 데이타 세팅
    		var tweBody = document.twe.MimeValue();
    		
    		contents = tweBody;
    		
		}
		
		//$jq("#itemFormFrame").ajaxLoadStart(); 
		
		 $jq.ajax({    
			url : "createItem.do",     
			data : {title:title, contents:contents, discussionId:discussionId, tag:tag, itemId:itemId, msie:msie},     
			type : "post",     
			success : function(result) {        

				//$jq("#itemFormFrame").ajaxLoadComplete();
				
				$jq("#twe").css("visibility","hidden");
				
				location.href = "getView.do?itemId="+result+"&docPopup=${param.docPopup}&docIframe=${param.docIframe}";
				
			},
			error : function(xhr, exMessage, httpStatus) {

				//$jq("#itemFormFrame").ajaxLoadComplete();
				var errorItems = $jq.parseJSON(xhr.responseText).exception;
				itemValid.showErrors(errorItems);

	        }

		}); 
		
	}
	
	
	function addReplyAction(val){
		
		var f = $jq('#linereplyForm_'+val);
		
		var linereplyId = f.find(':input[name=linereplyId]').val();
		var linereplyParentId = f.find(':input[name=linereplyParentId]').val();
		
		var contents = f.find(':input[name=contents]').val();
		var itemId = f.find(':input[name=itemId]').val();
		var discussionId = f.find(':input[name=discussionId]').val();

		f.parents(".blockComment").ajaxLoadStart(); 
		
		 $jq.ajax({    
			url : "createLinereply.do",     
			data : {itemId:itemId, contents:contents, discussionId:discussionId, linereplyId:linereplyId, linereplyParentId:linereplyParentId},     
			type : "post",     
			success : function(result) {        

				f.parents(".blockComment").ajaxLoadComplete();
				
				showLinereplyList(itemId, result);

				f.find(':input[name=contents]').val('');


			},
			error : function(xhr, exMessage, httpStatus) {

				var errorItems = $jq.parseJSON(xhr.responseText).exception;
				lineValid.showErrors(errorItems);

				f.parents(".blockComment").ajaxLoadComplete();

	        }

		}); 
		
		$jq("#lineReplyContents_"+itemId).val('');
		$jq("#linereplyId_"+itemId).val(''); 
		
	}
	
	function showLinereplyAddForm(linereplyParentId, itemId, type){
		
		 $jq('.lineFrame').remove();
		 $jq("#linereplyContents_"+tmplineId).show();

		 tmplineId = linereplyParentId;
		
		 var  html = '<div class="blockComment_rewrite lineFrame" id="blockComment_rewrite_'+linereplyParentId+'">';
		 html += '<div class="reply_ar"><img src="<c:url value="/base/images/icon/ic_reply_ar_b.gif"/>" alt="" /></div>';
		 html += '<form method="post" id="linereplyForm_'+linereplyParentId+'" action="">';
		 html += '<input type="hidden" name="itemId"  value="'+itemId+'"/><input type="hidden" name="tmpId"  value="'+linereplyParentId+'"/>';
		 html += '<input type="hidden" name="linereplyId"  value=""/><input type="hidden" name="discussionId" value="${frItem.discussionId}"/>';
		 html += '<input type="hidden" name="linereplyParentId" value="'+linereplyParentId+'"/>';
		 html += '<table summary="table"><caption></caption><tr><td>';
		 html += '<input name="contents" title="<ikep4j:message pre='${preUi}' key='formLineInput'/>"  class="inputbox" type="text" value=""/>';
		 html += '</td><td width="95" class="textRight"><ul><li><a class="button_s" href="#a" onclick="addReply('+linereplyParentId+');return false;" title="<ikep4j:message pre='${preUi}' key='formLineSave'/>"><span><ikep4j:message pre='${preUi}' key='formLineSave'/></span></a></li>';
		 html += '<li><a class="button_s" href="#a" onclick="hideLinereplyForm('+linereplyParentId+');return false;" title="<ikep4j:message pre='${preUi}' key='formLineCancel'/>"><span><ikep4j:message pre='${preUi}' key='formLineCancel'/></span></a></li></ul></td></tr></table></form></div>';


		 $jq("#linereplyContents_"+linereplyParentId).after(html);
		 
		 lineValid = new iKEP.Validator('#linereplyForm_'+linereplyParentId, lineValidOptions);
		 
		 $jq("#linereplyForm_"+linereplyParentId).find(':input[name=contents]').focus();
		 
		 if(type == "update"){
			 $jq("#linereplyContents_"+linereplyParentId).hide();
			 $jq('.reply_ar').hide();
	     }
		 
	}
	
	function hideLinereplyForm(linereplyParentId){
		 $jq("#blockComment_rewrite_"+linereplyParentId).remove();
		 $jq("#linereplyContents_"+linereplyParentId).show();
	}
	
	var tmplineId;	//리플 아이디 임시 저정
	function linereplyUpdateForm(linereplyId, itemId){
		
		$jq(".lineFrame").remove();
		$jq("#linereplyContents_"+tmplineId).show();

		tmplineId = linereplyId;
		
		var html = '<div class="guestbook_write lineFrame" id="blockComment_rewrite_'+linereplyId+'" >';
			html += '<form method="post" id="linereplyForm_'+linereplyId+'" action="">';
			html += '<input type="hidden" name="itemId"  value="'+itemId+'"/><input type="hidden" name="discussionId" value="${frItem.discussionId}"/>';
			html += '<input type="hidden" name="tmpId"  value="'+linereplyId+'"/><input type="hidden" name="linereplyId"  value="'+linereplyId+'"/>';
			html += '<table summary="<ikep4j:message pre='${preUi}' key='formLineSummary'/>">';
			html += '<caption></caption><tr><td><div>';
			html += '<textarea name="contents" title="contents" cols="" rows="3" ></textarea>';
			html += '</div></td><td width="74" class="textRight">';
			html += '<a class="button_re" href="#a" onclick="addReply(\''+linereplyId+'\');return false;" title="<ikep4j:message pre='${preUi}' key='viewSave'/>"><span><ikep4j:message pre='${preUi}' key='viewSave'/></span></a>';
			html += '</td></tr></table></form></div>';
		
		$jq("#linereplyContents_"+linereplyId).after(html);
		
		var lineContents = $jq.trim($jq("#linereplyContents_"+linereplyId).html());
		lineContents = lineContents.replaceAll("<BR>","\n").replaceAll("<br>","\n");
		
		$jq("#linereplyForm_"+linereplyId).find(':input[name=contents]').val( lineContents);
		$jq("#linereplyForm_"+linereplyId).find(':input[name=linereplyId]').val(linereplyId);
		 
		lineValid = new iKEP.Validator('#linereplyForm_'+linereplyId, lineValidOptions);

		$jq("#linereplyForm_"+linereplyId).find(':input[name=contents]').focus();

		$jq("#linereplyContents_"+linereplyId).hide();
		
	}
	
	
	function linereplyReplyUpdateForm(linereplyId, itemId){
		
		$jq(".lineFrame").remove();
		
		showLinereplyAddForm(linereplyId, itemId, "update");
		
		$jq("#linereplyForm_"+linereplyId).find(':input[name=contents]').val( $jq.trim($jq("#linereplyContents_"+linereplyId).text()));
		$jq("#linereplyForm_"+linereplyId).find(':input[name=linereplyId]').val(linereplyId);
		$jq("#linereplyForm_"+linereplyId).find(':input[name=linereplyParentId]').val('');
		
	}
	
	function linereplyDel(linereplyId, itemId){

		if(confirm("<ikep4j:message key='message.collpack.forum.deleteConfirm'/>")){
			
			$jq.ajax({    
					url : "deleteItemReply.do",     
					data : {linereplyId:linereplyId, itemId:itemId},     
					type : "post",     
					success : function(result) {    
	
						showLinereplyList(itemId, result);
	
						var count = $jq("#disLinereplyCount").text();
						$jq("#disLinereplyCount").text(Number(count)-1);
	
					},
					error : function(event, request, settings){
					 	alert("error");
					}
				}); 

		}
	}
	
	
	//댓글리스트 보기
	function replyList(val, pageIndex, isDisply){
		
		//리스트 보고있으면 숨기기
		var replyHtml = $jq('#lineReply_'+val).html();
		if(!isDisply && replyHtml){
			$jq('#lineReply_'+val).html('');
			$jq("#lineReply_"+val).parent().attr('class','blockComment noLine');
			return;
		} 

		$jq.ajax({    
			url : "listReply.do",     
			data : {itemId:val, pageIndex:pageIndex},     
			type : "post",  
			dataType : "html",
			success : function(result) {   
				showLinereplyList(val, result);
			},
			error : function(event, request, settings){
			 alert("error");
			}
		}); 
	}
	
	function showLinereplyList(val, result){
		
		$jq("#lineReply_"+val).html(result);  
		
		$jq("#lineReply_"+val).parent().attr('class','blockComment');

		$jq("#itemLinereplyCount_"+val).text($jq('#lineReply_'+val).data("totalCount"));
		$jq("#item2LinereplyCount").text("("+$jq('#lineReply_'+val).data("totalCount")+")");
		$jq("#itemLinereplyCount").text($jq('#lineReply_'+val).data("totalCount"));

		var count = $jq("#disLinereplyCount").text();
		$jq("#disLinereplyCount").text(Number(count)+1);

		initPartyList();//참가자 리스트 init

		iKEP.iFrameContentResize();  
		
	}
		
	function recomm(replyId){
		
		$jq.ajax({    
			url : "addRecommend.do",     
			data : {linereplyId:replyId, discussionId:'${discussionId}'},    
			type : "post",  
			success : function(result) {   
				
				if(result == 'success'){
					alert('<ikep4j:message pre='${preResource}' key='alertRecommend'/>'); 
					window.location.reload();
				} else if(result == 'exists'){
					alert('<ikep4j:message pre='${preResource}' key='alreadyRecommend'/>'); 
				} else {
					alert('<ikep4j:message pre='${preResource}' key='alertNotRecommend'/>'); 
				}
				
			},
			error : function(event, request, settings){
			 	alert("error");
			}
		}); 
	}
	
	
		
	function agree(itemId, discussionId){
		
		$jq.ajax({    
			url : "addAgreement.do",     
			data : {itemId:itemId, discussionId:discussionId},     
			type : "post",  
			success : function(result) {   
				
				if(result == 'success'){
					alert('<ikep4j:message pre='${preResource}' key='agreeOk'/>'); 


					var count = $jq("#itemAgreementCount").text();
					$jq("#itemAgreementCount").text(Number(count)+1);

					var countItem = $jq("#itemAgreementCount_${frItem.itemId}").text();
					$jq("#itemAgreementCount_${frItem.itemId}").text(Number(countItem)+1);

					initPartyList();//참가자 리스트 init
					
				} else if(result == 'exists'){
					alert('<ikep4j:message pre='${preResource}' key='agreeAlready'/>'); 
				} else {
					alert('<ikep4j:message pre='${preResource}' key='agreeNo'/>'); 
				}
				
			},
			error : function(event, request, settings){
			 	alert("error");
			}
		}); 
	}
	
	function opposite(itemId, discussionId){
		
		$jq.ajax({    
			url : "addOpposition.do",     
			data : {itemId:itemId, discussionId:discussionId},     
			type : "post",  
			success : function(result) {   
				
				if(result == 'success'){
					alert('<ikep4j:message pre='${preResource}' key='oppOk'/>');  

					var count = $jq("#itemOppositionCount").text();
					$jq("#itemOppositionCount").text(Number(count)+1);

					var countItem = $jq("#itemOppositionCount_${frItem.itemId}").text();
					$jq("#itemOppositionCount_${frItem.itemId}").text(Number(countItem)+1);

					 initPartyList();//참가자 리스트 init
					
				} else if(result == 'exists'){
					alert('<ikep4j:message pre='${preResource}' key='oppAlready'/>'); 
				} else {
					alert('<ikep4j:message pre='${preResource}' key='oppNo'/>'); 
				}
				
			},
			error : function(event, request, settings){
			 	alert("error");
			}
		}); 
	}
	
	
	function goPage(val){
		var pageIndex = $jq('#pageIndex_'+val).val();
		
		replyList(val, pageIndex)
		
	}
	
	function toggleLinereply(val){
		
		$jq('#guestbook_write_'+val+' textarea').val('');
		$jq('#guestbook_write_'+val).toggle();
		
	}

	var detailLink = "";

	<c:if test="${empty(param.itemId)}">
		detailLink = iKEP.getWebAppPath() + "/collpack/forum/getView.do?discussionId=${frDiscussion.discussionId}";
	</c:if>
	<c:if test="${!empty(param.itemId)}">
		detailLink = iKEP.getWebAppPath() + "/collpack/forum/getView.do?itemId=${frItem.itemId}";
	</c:if>
	
	//마이크로블로그 팝업
	function twittingPop(){

		var content = "[${frDiscussion.categoryName}]${frDiscussion.title}" ;
		
		iKEP.showTwittingPage(content,detailLink);
		
		
	};

	//블로그 성공적으로 보내고 콜백함수
	function callbackMBlog(){
		$jq.ajax({    
			url : "updateMblogCount.do",      //  업데이트 URL은 작성 모듈에 맞게 변경
			data : {discussionId:'${frDiscussion.discussionId}'},
			type : "post",  
			dataType : "html",
			success : function(result) {   
			},
			error : function(event, request, settings){
			 alert("error");
			}
		}); 
	}
	
	
	//쪽지 팝업
	function messgaePop() { 

		//var link = "window.open('"+detailLink+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;";
		var content = "<a href='"+detailLink+"'  target='_blank'>" +"[${frDiscussion.categoryName}]${frDiscussion.title}"+ "</a>" ;
		
		 // 단순 작성 팝업 열기
		 var url = "<c:url value='/support/message/messageNew.do'/>?contents="+content;
		 
		 iKEP.popupOpen(url ,{width:800, height:670} );
	}
	
	//메일 팝업
	function mailPop() {

		var link = "window.open('"+detailLink+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;";

		var title = "[${frDiscussion.categoryName}]${frDiscussion.title}";
		var content = "<a href=\""+detailLink+"\"  target='_blank'>"+title+"</a>";

		iKEP.sendMailPop("","", title, content, "", "");
		
	}


	//메일 성공적으로 보냈을시 콜백함수
	function callbackMail(){
		$jq.ajax({    
			url : "updateMailCount.do",     
			data : {discussionId:'${frDiscussion.discussionId}'},         
			type : "post",  
			dataType : "html",
			success : function(result) {   
			},
			error : function(event, request, settings){
			 alert("error");
			}
		}); 
	}

	function createItem(){

		$jq('#itemForm').submit();
	}


	function addReply(val){
		
		var val = $jq('#linereplyForm_'+val).submit();

	}
	
	var itemHtmlVal = "";
	
	function updateItemForm(itemId){
		
		$jq.ajax({    
			url : "getItem.do",     
			data : {itemId:itemId},     
			type : "post",  
			dataType : "json",
			success : function(result) {   
				
				var f = $jq("#itemForm");
				f.find(":input[name=itemId]").val(result.itemId);
				f.find(":input[name=title]").val(result.title);
				f.find(":input[name=contents]").val(result.contents);
				itemHtmlVal = result.contents;
				
				var tags = "";
				if(result.tagList.length > 0){
					$jq.each(result.tagList, function(i){
						if(i != 0){tags +=",";}
						tags += this.tagName;
					});
				} 
				f.find(":input[name=tag]").val(tags);

				
				viewItemForm();
			},
			error : function(event, request, settings){
			 alert("error");
			}
		}); 
	}
	
	function viewItemForm(){
		  
		itemValid = new iKEP.Validator("#itemForm", itemValidOptions);

		$jq("#itemFormFrame").show();

		$jq("#itemViewFrame").hide();

		setTimeout(function(){iKEP.iFrameContentResize();},1000);

		// editor 초기화
		initEditorSet();
		
	}



	/* editor 초기화  */
	function initEditorSet() {
		// ActiveX Editor 사용 여부가 Y인 경우 이며 브라우저가 ie인 경우
	    if(useActXEditor == "Y" && $jq.browser.msie){
			
				// div 높이, 넓이 세팅
				var cssObj = {
			      'height' : '450px',
			      'width' : '100%'
			    };
				$jq('#editorDiv').css(cssObj);

				var editVal = 0;

				if(itemHtmlVal != null){
					editVal = 1;
				}

				// hidden 필드 추가(contents)
				iKEP.createActiveXEditor("#editorDiv","${user.localeCode}","#itemContents",editVal);

				// ie 세팅
				$jq('input[name="msie"]').val('1');

				$jq("#itemForm input[name=title]").focus();

			}else{
				// ckeditor 초기화.
				$jq("#itemContents").ckeditor($jq.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
				// ie 이외 브라우저 값 세팅
				$jq('input[name="msie"]').val('0');
			}
	}
	
	function hideItemForm(){
		
		var f = $jq("#itemForm");
		
		f.find(":input[name=itemId]").val('');
		f.find(":input[name=title]").val('');
		f.find(":input[name=contents]").val('');
		f.find(":input[name=tag]").val('');
		
		$jq("#itemFormFrame").hide();

		$jq("#itemViewFrame").show();

		$jq("#editorDiv").empty();
		itemHtmlVal = "";

		setTimeout(function(){iKEP.iFrameContentResize();},1000);
	}
	
	var g_orderType = "";
	function moreList(orderType, pageIndex, totalCount){
		
		if(pageIndex == 1){
			//$jq('#itemFrame').empty();
			
			$jq('#moreText').text('<ikep4j:message pre='${preUi}' key='more10'/>');
			$jq('#moreBtn').hide();
			
		}
		
	 	if(orderType){
			g_orderType = orderType;
			
			$jq('#orBtn span').removeClass('current');
			$jq('#orBtn span img').each(function(){
				this.src = this.src.replace("ic_cafesort_down_on", "ic_cafesort_down");
			});
			
			if(orderType == "hit"){
				$jq('#orBtn span:eq(1)').addClass('current');
				var imgTmpSrc = $jq('#orBtn span:eq(1) img').attr("src");
				$jq('#orBtn span:eq(1) img').attr("src", imgTmpSrc.replace('ic_cafesort_down', "ic_cafesort_down_on"))
			} else if(orderType == "best"){
				$jq('#orBtn span:eq(2)').addClass('current');
				var imgTmpSrc = $jq('#orBtn span:eq(2) img').attr("src");
				$jq('#orBtn span:eq(2) img').attr("src", imgTmpSrc.replace('ic_cafesort_down', "ic_cafesort_down_on"))
			} else {
				$jq('#orBtn span:eq(0)').addClass('current');
				var imgTmpSrc = $jq('#orBtn span:eq(0) img').attr("src");
				$jq('#orBtn span:eq(0) img').attr("src", imgTmpSrc.replace('ic_cafesort_down', "ic_cafesort_down_on"))
			}
		} 
		
		
		listMore('itemListMore.do', totalCount,{discussionId:${frDiscussion.discussionId}, orderType:g_orderType, pageIndex:pageIndex});
	
	}
	
	
	function goLinePage(val){
		
		var itemId = val.substring(val.indexOf("itemId")+7,val.length);
		var f = $jq('#lineForm_'+itemId);
		
		replyList(f.find(":input[name=itemId]").val(), f.find(":input[name=pageIndex]").val(), true);
		
	}
	
	
	function addFavorite(itemId){

		var title = $jq.trim($jq("#itemViewFrame").find(".blockTableRead_t p").text());
		
		iKEP.addFavorite('CONTENTS','${IKepConstant.ITEM_TYPE_CODE_FORUM}',itemId, title, function(data){
			
			if(data.status == "success"){
				
				$jq.ajax({    
					url : "addFavorite.do",     
					data : {itemId:itemId},     
					type : "post",  
					success : function(result) {   
						
						alert('<ikep4j:message pre='${preResource}' key='alertFavorite'/>'); 
						toggleFavorteImg('del', itemId);

						var count = $jq("#itemFavoriteCount").text();
						$jq("#itemFavoriteCount").text(Number(count)+1);

						var countItem = $jq("#itemFavoriteCount_"+itemId).text();
						$jq("#itemFavoriteCount_"+itemId).text(Number(countItem)+1);

						
					},
					error : function(event, request, settings){
					 	alert("error");
					}
				}); 
				
			} else {
				alert("<ikep4j:message pre='${preResource}' key='alreadyFavorite'/>");
			}
		});
		
		return false;
	}
	
	
	
	function delFavorite(itemId){
		
		iKEP.delFavorite('',itemId, function(data){
			
			if(data.status == "success"){
				$jq.ajax({    
					url : "delFavorite.do",     
					data : {itemId:itemId},     
					type : "post",  
					success : function(result) {   
						
						alert("<ikep4j:message pre='${preResource}' key='alertNotFavorite'/>");
						toggleFavorteImg('add', itemId);

						var count = $jq("#itemFavoriteCount").text();
						$jq("#itemFavoriteCount").text(Number(count)-1);

						var countItem = $jq("#itemFavoriteCount_"+itemId).text();
						$jq("#itemFavoriteCount_"+itemId).text(Number(countItem)-1);
						
					},
					error : function(event, request, settings){
					 	alert("error");
					}
				}); 
			} else {
				alert("error");
			}
			
		});
		return false;
	}	
	
	function toggleFavorteImg(type, itemId){

		if(type == "add"){
			$jq('#linkFavorite').unbind('click');
			$jq('#linkFavorite').attr("class","ic_rt_favorite valign_middle").click(function(){addFavorite(itemId);});
		} else {
			$jq('#linkFavorite').unbind('click');
			$jq('#linkFavorite').attr("class","ic_rt_favorite valign_middle select").click(function(){delFavorite(itemId);});
		}
		
	}

	//토론 주제 삭제
	function delDiscussion(discussionId){

		if(confirm("<ikep4j:message key='message.collpack.forum.deleteConfirm'/>")){
			
			location.href='deleteDiscussion.do?discussionId='+discussionId+"&docPopup=${param.docPopup}<c:if test="${!empty(param.docIframe) }">&docIframe=${param.docIframe}</c:if>";
			
		}
	}

	//토론글 삭제
	function delItem(itemId){
		
		if(confirm("<ikep4j:message key='message.collpack.forum.deleteConfirm'/>")){
			
			location.href='deleteItem.do?itemId='+itemId+"&docPopup=${param.docPopup}&docIframe=${param.docIframe}";
			
		}
	}

	//토론글 조회
	function getViewItem(itemId){

		$jq("#tagResult").ajaxLoadStart(); 
		
		$jq.ajax({    
			url : "getViewItem.do",     
			data : {itemId:itemId},     
			type : "post",  
			dataType : "html",
			success : function(result) {   

				$jq("#tagResult").ajaxLoadComplete();
				
				$jq("#itemViewFrame").html(result);
				
				lineValid = new iKEP.Validator('#linereplyForm_'+itemId, lineValidOptions);

				var itemHitCount = $jq("#itemHitCount").text();
				var listItemHitCount =  $jq("#itemHitCount_"+itemId).text();
				var disHitCount = $jq("#disHitCount").text();

				//조회수가 증가했으면 뷰업데이트
				if(itemHitCount != listItemHitCount){
					 $jq("#itemHitCount_"+itemId).text(itemHitCount);
					 $jq("#disHitCount").text(Number(disHitCount)+1);
				}

				//선택한 토론글리스트 클래스 변경
				$jq("#itemFrame li").removeClass("selected");

				$jq("#itemFrame li").each(function(){
					$this = $jq(this);
					if($this.hasClass("item_"+itemId)){
						$this.addClass("selected");
					}
				});

				$jq("#itemFormFrame").hide();

				$jq("#itemViewFrame").show();

				iKEP.iFrameContentResize();  
				
			},
			error : function(event, request, settings){

				$jq("#tagResult").ajaxLoadComplete();
				
				alert("error");
			}
		}); 

	}

	function initPartyList(){

		partyPageindex = 0;
		
		partyList('${frDiscussion.discussionId}', 100);

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
	document.twe.HtmlValue = itemHtmlVal.replaceAll("src=\""+iKEP.getContextRoot(), "src=\""+iKEP.getWebAppPath());
	$jq("input[name=title]").focus();
</script>

<h1 class="none">Contents Area</h1>

<div id="tagResult">
<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3><ikep4j:message pre='${preUi}' key='disTitle'/></h3>
</div>
<!--//subTitle_2 End-->

<c:if test="${param.docPopup != 'true' || !empty(param.docIframe) }">
	<div class="icgroup">
		<div class="btn_icbox">
			<a class="ic_mail" href="#a" onclick="mailPop();return false;" title="<ikep4j:message pre='${preUi}' key='viewSendMail'/>"><img src="<c:url value="/base/images/icon/ic_mail_2.gif"/>" alt="<ikep4j:message pre='${preUi}' key='viewSendMail'/>" /></a>
		</div>										
		<div class="btn_icbox">
			<a class="ic_note" href="#a" onclick="messgaePop();return false;" title="<ikep4j:message pre='${preUi}' key='viewSendMsg'/>"><img src="<c:url value="/base/images/icon/ic_note.gif"/>" alt="<ikep4j:message pre='${preUi}' key='viewSendMsg'/>" /></a>
		</div>
		<div class="btn_icbox">
			<a class="ic_microblog" href="#a" onclick="twittingPop();return false;" title="<ikep4j:message pre='${preUi}' key='viewSendBlog'/>"><img src="<c:url value="/base/images/icon/ic_microblog.gif"/>" alt="<ikep4j:message pre='${preUi}' key='viewSendBlog'/>" /></a>
		</div>				
	</div>
</c:if>	

<!--forum_topBox Start-->
<div class="forum_topBox">
	<div class="forum_topBox_t">
		<c:if test="${!empty(frDiscussion.categoryName) }"><span class="cate_block_1"><span class="cate_tit_2">${frDiscussion.categoryName}</span></span></c:if>
		<p>${frDiscussion.title}</p>
		
		<c:if test="${(user.userId == frDiscussion.registerId || isAdmin) && (param.docPopup != 'true' || !empty(param.docIframe)) }">
			<span class="rebtn">
				<a class="ic_modify" href="discussionForm.do?discussionId=${frDiscussion.discussionId}<c:if test="${!empty(param.docIframe) }">&amp;docIframe=${param.docIframe}</c:if>"  title="<ikep4j:message pre='${preUi}' key='viewUpdate'/>"><span><ikep4j:message pre='${preUi}' key='viewUpdate'/></span></a>
				<a class="ic_delete" href="#a" onclick="delDiscussion('${frDiscussion.discussionId}');return false;" title="<ikep4j:message pre='${preUi}' key='viewDelete'/>"><span><ikep4j:message pre='${preUi}' key='viewDelete'/></span></a>
			</span>
		</c:if>
		
		<div class="forum_list_info">
			<span class="forum_name"><a href="#a" onclick="viewPopUpProfile('${frDiscussion.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${frDiscussion.registerName} ${frDiscussion.jobTitleName}</c:when><c:otherwise>${frDiscussion.userEnglishName} ${frDiscussion.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
			<span class="forum_team"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${frDiscussion.teamName}</c:when><c:otherwise>${frDiscussion.teamEnglishName}</c:otherwise></c:choose></span>
			<span class="forum_date"><ikep4j:timezone date="${frDiscussion.registDate}" pattern="message.collpack.forum.timezone.dateformat.type2" keyString="true"/></span>
			<span class="forum_etc"><ikep4j:message pre='${preUi}' key='hit'/>  <strong id="disHitCount">${frDiscussion.hitCount}</strong></span>
			<span class="forum_etc"><ikep4j:message pre='${preUi}' key='item'/>  <strong>${frDiscussion.itemCount}</strong></span>
			<span class="forum_etc"><ikep4j:message pre='${preUi}' key='line'/>  <strong id="disLinereplyCount">${frDiscussion.linereplyCount}</strong></span>
			<span class="forum_etc"><ikep4j:message pre='${preUi}' key='pary'/>  <strong>${frDiscussion.participationCount}</strong></span>
		</div>
		<div class="forum_list_img" style="float:left;">
		<c:if test="${fn:length(participantList) > 0 }">
			<ul id="partyFrame_${frDiscussion.discussionId}">
				<!--participant start-->
				<c:import url="/WEB-INF/view/collpack/forum/participaniListMore.jsp" charEncoding="UTF-8" />
				<!--participant end-->
			</ul>
		</c:if>
		</div>
			<div style="padding: 5px 0 0 5px; <c:if test="${participantCount < 6}">display:none;</c:if>" id="partyMoreBtn_${frDiscussion.discussionId}"><a href="#a" class="next" onclick="partyList('${frDiscussion.discussionId}', '${participantCount }');return false;" title="next"><img src="<c:url value="/base/images/icon/ic_ar_next.gif"/>" alt="next" /></a></div>
		<div style="clear: both;"></div>
	</div>
	
	<div class="forum_topBox_c">
		<div class="forum_topBox_img">
			<img src="<c:url value="/support/fileupload/downloadFile.do"/>?fileId=${frDiscussion.imageId }&amp;thumbnailYn=Y" width="150" height="150" alt="image"/>
		</div>
		<div class="forum_topBox_con">
			${frDiscussion.contents }
		</div>	
		<!--tag list-->
		<div class="corporateViewTag mt10" id="tagForm_${frDiscussion.discussionId}">
		      <input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_FORUM %>"/>      <!--게시물 type TagConstants에서 맞는 타입 골라서 사용하면 됨.-team coll은 type ID를 넣으셔야 합니다.-->
		      <input type="hidden" name="tagItemSubType" value="<%=ForumConstants.TAG_TYPE_DISCUSSION%>"/>                      <!--서브 파입이 있는 경우만 -->
		      <input type="hidden" name="tagItemName" value="${frDiscussion.title}"/>                          <!--게시물 제목-->
		      <input type="hidden" name="tagItemContents" value="${fn:escapeXml(frDiscussion.contents)}"/>                   <!--게시물 내용 - html특수 기호 처리-->
		      <input type="hidden" name="tagItemUrl" value="/collpack/forum/getView.do?docPopup=true&amp;discussionId=${frDiscussion.discussionId}"/> 
		      
		     <div>  <!--코딩파일엔 div가 없는데 꼭 넣어주어야 함.-->
		        <span class="ic_tag"><span><ikep4j:message pre='${preUi}' key='viewTag'/></span></span> <!--tagList--> 
		           <c:forEach var="tag" items="${discussionTagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
		        <span class="rebtn">
		         <c:if test="${user.userId == frDiscussion.registerId}">  <!--권한 체크 등록자랑 세션userID랑 같으면 태그 수정 가능-->
		           <a href="#a" <c:if test="${param.docPopup != 'true' || !empty(param.docIframe) }"> onclick="iKEP.tagging.tagFormView('${frDiscussion.discussionId}');return false;" </c:if> title="<ikep4j:message pre='${preUi}' key='viewUpdate'/>" class="ic_modify"><span><ikep4j:message pre='${preUi}' key='viewUpdate'/></span></a>
		         </c:if>                                          <!--게시물 id--> 
		       </span>
		     </div>
		</div>		
		<!--//tag list-->																		
	</div>
	<c:if test="${param.docPopup != 'true' || !empty(param.docIframe) }">
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a class="button" href="#a" onclick="viewItemForm();return false;" title="<ikep4j:message pre='${preUi}' key='doDis'/>"><span><ikep4j:message pre='${preUi}' key='doDis'/></span></a></li>
				<li><a class="button" href="${listUrl }" title="<ikep4j:message pre='${preUi}' key='viewList'/>"><span><ikep4j:message pre='${preUi}' key='viewList'/></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->	
	</c:if>				
</div>
<!--//forum_topBox End-->	

<form id="editorFileUploadParameter" action="">
    <input type="hidden" name="interceptorKey"  value="ikep4.system"/>
</form>

<!--blockDetail Start-->
<div  id="itemFormFrame" style="display:none;">
	<form id="itemForm" action="createItem.do" method="post">
		<input type="hidden" name="discussionId" value="${frDiscussion.discussionId}"/>
		<input type="hidden" name="itemId" />
		<input type="hidden" name="msie" value="0" />
	<div class="blockDetail" >
		<table summary="itemForm table">
			<caption></caption>
			<tbody>
				<tr> 
					<th width="18%" scope="row"><label for="itemTitle"><ikep4j:message pre='${preUi}' key='title'/></label></th>
					<td ><div>
						<input type="text" id="itemTitle" name="title" class="inputbox w100" />
						<label for="itemTitle" class="serverError"> </label>
						</div>
					</td> 
				</tr>	
				<tr> 
					<td colspan="2"><div id="editorDiv">
						<textarea id="itemContents" name="contents" class="w100" cols="" rows="20" title="<ikep4j:message pre='${preUi}' key='contens'/>">${status.value}</textarea>
						<label for="itemContents" class="serverError"> </label>
						</div>
					</td> 
				</tr>				
				<tr>
					<th scope="row"><label for="itemTag"><ikep4j:message pre='${preUi}' key='viewTag'/></label></th>
					<td><div>
						<input type="text" name="tag" class="inputbox w100" id="itemTag"  />
				        <div class="tdInstruction">※ <ikep4j:message pre='${preUi}' key='tagContents'/></div>
				        <label for="itemTag" class="serverError"> </label>
				        </div>
					</td>
				</tr>							
			</tbody>
		</table>
	</div>
	</form>
	<!--//blockDetail End-->
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a class="button" href="#" onclick="createItem();return false;" title="<ikep4j:message pre='${preUi}' key='formLineSummary'/>"><span><ikep4j:message pre='${preUi}' key='formLineSummary'/></span></a></li>
			<li><a class="button" href="#" onclick="hideItemForm();return false;" title="<ikep4j:message pre='${preUi}' key='formLineCancel'/>"><span><ikep4j:message pre='${preUi}' key='formLineCancel'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
</div>

<c:if test="${!empty(itemId)}">
	<!--subTitle_2 Start-->
	<div class="cafeSubTitle border_b1 mb15">
		<h3><a name="a"><ikep4j:message pre='${preUi}' key='itemContents'/></a></h3>
	</div>
	<!--//subTitle_2 End-->

	<!--blockTableReadWrap Start-->
	<div class="blockTableReadWrap" id="itemViewFrame"> 
		<c:import url="/WEB-INF/view/collpack/forum/itemViewMore.jsp" charEncoding="UTF-8" />	
	</div>
	<!--//blockTableReadWrap End-->
</c:if>


<!--subTitle_2 Start-->
<div class="cafeSubTitle border_b1 mb15">
	<h3><ikep4j:message pre='${preUi}' key='itemList'/></h3>
	
	<div class="cafe_sort">
		<span class="cafe_sort_smenu" id="orBtn">
			<span class="current" ><a href="#a" onclick="moreList('date', 1, '${totalCount}');return false;" title="<ikep4j:message pre='${preUi}' key='orderLast'/>"><ikep4j:message pre='${preUi}' key='orderLast'/> <img src="<c:url value="/base//images/icon/ic_cafesort_down_on.gif"/>" alt="<ikep4j:message pre='${preUi}' key='orderLast'/>" /></a></span>
			<img src="<c:url value="/base/images/common/bar_info.gif"/>" alt="bar" />
			<span ><a href="#a" onclick="moreList('hit', 1, '${totalCount}');return false;" title="<ikep4j:message pre='${preUi}' key='orderHit'/>"><ikep4j:message pre='${preUi}' key='orderHit'/> <img src="<c:url value="/base/images/icon/ic_cafesort_down.gif"/>" alt="<ikep4j:message pre='${preUi}' key='orderHit'/>" /></a></span>
			<img src="<c:url value="/base/images/common/bar_info.gif"/>" alt="bar" />
			<span ><a href="#a" onclick="moreList('best', 1, '${totalCount}');return false;" title="<ikep4j:message pre='${preUi}' key='orderAgree'/>"><ikep4j:message pre='${preUi}' key='orderAgree'/> <img src="<c:url value="/base/images/icon/ic_cafesort_down.gif"/>" alt="<ikep4j:message pre='${preUi}' key='orderAgree'/>" /></a></span>
		</span>					
	</div>	
								
</div>
<!--//subTitle_2 End-->
	
<ul class="forum_list mb15" id="itemFrame">
	<c:import url="/WEB-INF/view/collpack/forum/itemListMore.jsp" charEncoding="UTF-8" />																										
</ul>
			
			
<c:if test="${totalCount > 10}">		
	<!--blockButton_3 Start-->
	<div class="blockButton_3" id="moreBtn"> 
		<a class="button_3" href="#a" onclick="moreList('','','${totalCount}');return false;" title="<ikep4j:message pre='${preUi}' key='more10'/>"><span id="moreText"><ikep4j:message pre='${preUi}' key='more10'/> <img src="<c:url value="/base/images/icon/ic_more_ar.gif"/>" alt="<ikep4j:message pre='${preUi}' key='more10'/>" /></span></a>				
	</div>
	<!--//blockButton_3 End-->	
</c:if>

</div>