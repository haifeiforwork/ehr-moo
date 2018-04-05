<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/fileUploadControll.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<%@ page import="com.lgcns.ikep4.collpack.ideation.constants.IdeationConstants" %>

<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>

<c:set var="preUi" 			value="ui.collpack.ideation.view" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="preMail"  value="message.collpack.idea.expert.mail" />
<c:set var="refererUrl"><%=request.getHeader("referer") %></c:set> <%--전페이지주소--%>

<c:choose>
	<c:when test="${fn:contains(refererUrl,'lastList.do') || fn:contains(refererUrl,'keyIssueList.do') || fn:contains(refererUrl,'activityList.do') || fn:contains(refererUrl,'businessList.do')}">
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
	//validation
	var da = (document.all) ? 1 : 0
    var pr = (window.print) ? 1 : 0
    var mac = (navigator.userAgent.indexOf("Mac") != -1);
	
	var validOptions = {
		rules : {
			examinationComment :	{
				required : true,
				maxlength  : 2000
			}
		},
		messages : {
			examinationComment : {
				required : "<ikep4j:message key='NotEmpty.ideation.exam'/>",
				maxlength : "<ikep4j:message key='Size.ideation.exam' />"
			}
		},
		notice : {
			examinationComment : "<ikep4j:message key='NotEmpty.linereply.examNotice'/>"
		}
	
	};
	
	var lineValid;
	
	var lineValidOptions = {
			rules : {
				contents : {
					required : true,
					maxlength  : 1500
				}
			},
			messages : {
				contents : {
					required : "<ikep4j:message key='NotEmpty.idLinereply.contents'/>"
					,maxlength : "<ikep4j:message key='Size.idLinereply.contents' />"
				}
			},
			notice : {
				contents : "<ikep4j:message key='NotEmpty.idLinereply.contentsNotice'/>"
			},
			submitHandler : function(form) {
				addReplyAction(form.tmpId.value);
				
			}
			
		};


	var lineValid;
	
	$jq(document).ready(function() {
		
		lineValid = new iKEP.Validator('#linereplyForm_${param.itemId}', lineValidOptions);
		
		//즐겨찾기
		<c:choose>
			<c:when test="${isFavorite == 'true'}">
				toggleFavorteImg('del');
			</c:when>
			<c:otherwise>
				toggleFavorteImg('add');
			</c:otherwise>
		</c:choose>
		
		
			var uploaderOptions = {// 사용할 수 있는 옵션 항목
				<c:if test="${!empty(fileDataListJson)}">
					files : /* 파일 목록 */ ${fileDataListJson}, 
				</c:if>
			    isUpdate : false,    // 등록 및 수정일때 true
		        //uploadUrl : "",    // 파일 업로드 경로가 다를때 설정 : 사용하지 않음
		        allowFileType  : "allowAll"    // 허용가능한 파일 확장자 설정, default = "img,comp,doc" (정책에 따라 변경될 수 있습니다.)
		    };
			
		
		 
		//파일업로드 컨트롤로 생성   
		fileController = new iKEP.FileController(null, "#fileUploadArea", uploaderOptions);
		
			$jq("#adoptDesc").hover(function(event) {
				var info = $jq("#helpContent").html();
				iKEP.showTooltipInfo(1, event.target, info );
			},
			function(event) {
				iKEP.showTooltipInfo(0);
			}
		);
		
		//메일 보내기 이벤트
        $jq("#btnMailSend")
        .mouseover(function(event){ event.stopPropagation(); $jq('#dropDown').show();})
        .mouseout(function(event){ event.stopPropagation(); $jq('#dropDown').hide();});	
		
		
	});

	
	function addReplyAction(val){
		
		var f = $jq('#linereplyForm_'+val);
		
		var linereplyId = f.find(':input[name=linereplyId]').val();
		var linereplyParentId = f.find(':input[name=linereplyParentId]').val();
		
		var contents = f.find(':input[name=contents]').val();
		var itemId = f.find(':input[name=itemId]').val();
		
		var adoptLinereply = 0;
		f.find(":radio").each(function(){
			if(this.checked == true){
				adoptLinereply = this.value;
			}
		});

		f.parents(".blockComment").ajaxLoadStart(); 
		
		 $jq.ajax({    
			url : "createLinereply.do",     
			data : {itemId:itemId, contents:contents, linereplyId:linereplyId, linereplyParentId:linereplyParentId, adoptLinereply:adoptLinereply},     
			type : "post",     
			success : function(result) { 

				f.parents(".blockComment").ajaxLoadComplete();
				
				showLinereplyList(itemId, result);

				f.find(':input[name=contents]').val('');

				if(adoptLinereply == 1){
					var count = Number($jq("#ideaAdoptCount").text());
					$jq("#ideaAdoptCount").text(count+1);
				}
				
				//window.location.reload();
				
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
	function addReply(val){
		
		$jq('#linereplyForm_'+val).submit();
		
	}
	
	
	function showLinereplyAddForm(linereplyParentId, itemId, type){
		
		$jq('.lineFrame').remove();
		 $jq("#linereplyContents_"+tmplineId).show();

		 tmplineId = linereplyParentId;
		
		 var  html = '<div class="blockComment_rewrite lineFrame" id="blockComment_rewrite_'+linereplyParentId+'">';
		 html += '<div class="reply_ar"><img src="<c:url value="/base/images/icon/ic_reply_ar_b.gif"/>" alt="reply" /></div>';
		 html += '<form method="post" id="linereplyForm_'+linereplyParentId+'" action="">';
		 html += '<input type="hidden" name="itemId"  value="'+itemId+'"/>';
		 html += '<input type="hidden" name="linereplyId"  value=""/><input type="hidden" name="tmpId"  value="'+linereplyParentId+'"/>';
		 html += '<input type="hidden" name="linereplyParentId" value="'+linereplyParentId+'"/>';
		 html += '<table summary="<ikep4j:message pre='${preUi}' key='viewLinereply'/>"><caption></caption><tr><td><div>';
		 html += '<input name="contents" title="<ikep4j:message pre='${preUi}' key='viewLinereply'/>"  class="inputbox" type="text" value=""/>';
		 html += '</div></td><td width="95" class="textRight"><ul><li><a class="button_s" href="#a" onclick="addReply('+linereplyParentId+');return false;" title="<ikep4j:message pre='${preUi}' key='formLineSave'/>"><span><ikep4j:message pre='${preUi}' key='formLineSave'/></span></a></li>';
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
			html += '<input type="hidden" name="itemId"  value="'+itemId+'"/>';
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
		
		$jq("#blockComment_rewrite_"+linereplyId + ' form').find(':input[name=contents]').val( lineContents);
		$jq("#blockComment_rewrite_"+linereplyId + ' form').find(':input[name=linereplyId]').val(linereplyId);
		 
		lineValid = new iKEP.Validator('#linereplyForm_'+linereplyId, lineValidOptions);

		$jq("#linereplyForm_"+linereplyId).find(':input[name=contents]').focus();

		$jq("#linereplyContents_"+linereplyId).hide();
		
	}
	
	
	function linereplyReplyUpdateForm(linereplyId, itemId){
		
		$jq(".lineFrame").remove();
		
		showLinereplyAddForm(linereplyId, itemId, "update");
		
		$jq("#blockComment_rewrite_"+linereplyId + ' form').find(':input[name=contents]').val( $jq.trim($jq("#linereplyContents_"+linereplyId).text()));
		$jq("#blockComment_rewrite_"+linereplyId + ' form').find(':input[name=linereplyId]').val(linereplyId);
		$jq("#blockComment_rewrite_"+linereplyId + ' form').find(':input[name=linereplyParentId]').val('');
		
	}
	
	
	String.prototype.replaceAll = function( searchStr, replaceStr ){
		return this.split( searchStr ).join( replaceStr );
	}

	
	function linereplyDel(linereplyId, itemId, adoptLinereply){

		if(confirm("<ikep4j:message key='message.collpack.ideation.deleteConfirm'/>")){
			
			$jq.ajax({    
				url : "deleteItemReply.do",     
				data : {linereplyId:linereplyId, itemId:itemId},     
				type : "post",     
				success : function(result) {    
	
					showLinereplyList(itemId, result);
	
					if(adoptLinereply == 1){
						var count = Number($jq("#ideaAdoptCount").text());
						$jq("#ideaAdoptCount").text(count-1);
					}
				},
				error : function(event, request, settings){
				 	alert("error");
				}
			}); 

		}
	}
	
	function linereplyAlive(replyNo, itemId){
		
		$jq.ajax({    
				url : "aliveItemReply.do",     
				data : {replyNo:replyNo, itemId:itemId},     
				type : "post",     
				success : function(result) {    
					showLinereplyList(itemId, result);
				},
				error : function(event, request, settings){
				 	alert("error");
				}
			}); 
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
		
		var resultSize = $jq(result).find('div').size();
		
		if(resultSize > 0){
			$jq("#lineReply_"+val).html(result);  
			$jq("#linereplyCount_"+val).text("("+$jq('#lineReply_${param.itemId}').data("totalCount")+")");
			
			$jq("#lineReply_"+val).parent().attr('class','blockComment');

			$jq("#ideaLinereplyCount").text($jq('#lineReply_${param.itemId}').data("totalCount"));
		}

		iKEP.iFrameContentResize();  
		
	}
	
	function goLinePage(val){
			var itemId = val.substring(val.indexOf("itemId")+7,val.length);
			var f = $jq('#lineForm_'+itemId);
			
			replyList(f.find(":input[name=itemId]").val(), f.find(":input[name=pageIndex]").val(), true);
			
		}
			
			
	function toggleLinereply(val){
		
		$jq('#guestbook_write_'+val+' textarea').val('');
		$jq('#guestbook_write_'+val).toggle();
		
	}

	function recommItem(){
		
		$jq.ajax({    
			url : "addRecommend.do",     
			data : {itemId:'${idea.itemId}'},     
			type : "post",  
			success : function(result) {   
				
				if(result == 'success'){
					alert('<ikep4j:message key='message.collpack.ideation.alertRecommend'/>'); 

					var count = Number($jq(".button_rec_num .num").text());
					$jq(".button_rec_num .num").text(count+1);
					
				} else if(result == 'exists'){
					alert('<ikep4j:message key='message.collpack.ideation.alreadyRecommend'/>'); 
				} else {
					alert('<ikep4j:message key='message.collpack.ideation.alertNotRecommend'/>'); 
				}
				
			},
			error : function(event, request, settings){
			 	alert("error");
			}
		}); 
	}
	
	
	function recomm(replyId){

		$jq.ajax({    
			url : "addLineRecommend.do",     
			data : {linereplyId:replyId},     
			type : "post",  
			success : function(result) {   
				
				if(result == 'success'){
					alert('<ikep4j:message key='message.collpack.ideation.alertRecommend'/>'); 

					var count = $jq("#linereplyContents_"+replyId).parent().find(".blockCommentInfo span:eq(3) b").text();
					$jq("#linereplyContents_"+replyId).parent().find(".blockCommentInfo span:eq(3) b").text(Number(count)+1)

				} else if(result == 'exists'){
					alert('<ikep4j:message key='message.collpack.ideation.alreadyRecommend'/>'); 
				} else {
					alert('<ikep4j:message key='message.collpack.ideation.alertNotRecommend'/>'); 
				}
				
			},
			error : function(event, request, settings){
			 	alert("error");
			}
		}); 
	}
	
	
	function addFavorite(title){
		
		iKEP.addFavorite('CONTENTS','${IKepConstant.ITEM_TYPE_CODE_IDEATION}','${idea.itemId}', '${fn:escapeXml(idea.title)}', function(data){
			
			if(data.status == "success"){
				
				$jq.ajax({    
					url : "addFavorite.do",     
					data : {itemId:'${param.itemId}'},     
					type : "post",  
					success : function(result) {   
						
						alert('<ikep4j:message key='message.collpack.ideation.alertFavorite'/>'); 
						toggleFavorteImg('del');

						var count = $jq("#ideaFavoriteCount").text();
						$jq("#ideaFavoriteCount").text(Number(count)+1);
						
					},
					error : function(event, request, settings){
					 	alert("error");
					}
				}); 
				
			} else {
				alert("<ikep4j:message key='message.collpack.ideation.alreadyFavorite'/>");
			}
		});
		
		return false;
	}
	
	
	function delFavorite(){
		
		iKEP.delFavorite('','${idea.itemId}', function(data){
			
			if(data.status == "success"){
				$jq.ajax({    
					url : "delFavorite.do",     
					data : {itemId:'${param.itemId}'},     
					type : "post",  
					success : function(result) {   
						
						alert("<ikep4j:message key='message.collpack.ideation.alertNotFavorite'/>");
						 
						toggleFavorteImg('add');

						var count = $jq("#ideaFavoriteCount").text();
						$jq("#ideaFavoriteCount").text(Number(count)-1);
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
	
	function toggleFavorteImg(type){
		if(type == "add"){
			$jq('#linkFavorite').unbind('click');
			$jq('#linkFavorite').attr("class","ic_rt_favorite valign_middle").click(function(){addFavorite();});
		} else {
			$jq('#linkFavorite').unbind('click');
			$jq('#linkFavorite').attr("class","ic_rt_favorite valign_middle select").click(function(){delFavorite();});
		}
		
	}

	
	
	//마이크로블로그 팝업
	function twittingPop(){

		var content = "${idea.title}"; 
		
		var url = iKEP.getWebAppPath() + "/collpack/ideation/getView.do?itemId=${idea.itemId}";
		
		iKEP.showTwittingPage(content,url);
		
	};

	//블로그 성공적으로 보내고 콜백함수
	function callbackMBlog(){
		$jq.ajax({    
			url : "updateMblogCount.do",      //  업데이트 URL은 작성 모듈에 맞게 변경
			data : {itemId:'${param.itemId}'},  
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
		 
		var url = iKEP.getWebAppPath() + "/collpack/ideation/getView.do?itemId=${param.itemId}";
		//var link = "window.open('"+url+"', 'ideation', 'width=800px, height=670px, scrollbars=yes');return false;";
		
		var content = "<a href='"+url+"' target='_blank'>${idea.title}</a>" ;
		
		 // 단순 작성 팝업 열기
		 var url = "<c:url value='/support/message/messageNew.do'/>?contents="+content;
		 
		 iKEP.popupOpen(url ,{width:800, height:670} );
	}
	
	//메일 팝업
	function mailPop() {

		var url = iKEP.getWebAppPath() + "/collpack/ideation/getView.do?itemId=${param.itemId}";
		
		var title = "${idea.title}";
		var content = "<a href='"+url+"' target='_blank'>"+title+"</a>" ;

	    iKEP.sendTerraceMailPop("", title, content);  		
	}
	
	function mailContentsPop() {

        var url = iKEP.getWebAppPath() + "/collpack/ideation/getView.do?itemId=${param.itemId}";
        
        var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;";
       
        var title = "${idea.title}";
        var content = $jq("#ideaContents2").clone();

        iKEP.sendMailPop("","", title, $jq(content).html(),"",""); 
        
    	$jq(".icboxLayer").hide(); 
    	return false;
    }
	
	function printPage()
	{
		if (pr) // NS4, IE5
            window.print();
        else if (da && !mac) // IE4 (Windows)
            vbPrintPage();
        else // 다른 부라우저
            alert("죄송합니다. 이 브라우저는 이 기능을 지원하지 않습니다!"); 
	}

	//메일 성공적으로 보냈을시 콜백함수
	function callbackMail(){
		$jq.ajax({    
			url : "updateMailCount.do",     
			data : {itemId:'${param.itemId}'},          
			type : "post",  
			dataType : "html",
			success : function(result) {   
			},
			error : function(event, request, settings){
			 alert("error");
			}
		}); 
	}


	function createExam(){
		
		$jq('#examinationForm').submit();
	}
	
	function viewExamForm(val){
		  
	   $jq("#examination").show();

	   if(val){

		   var businessItem = (val == 'adopt')? 1: 3;
		   var labelText = (val == 'adopt')? "<ikep4j:message pre='${preUi}' key='businessAdopt'/> <ikep4j:message pre='${preUi}' key='comment'/>": "<ikep4j:message pre='${preUi}' key='businessCancel'/> <ikep4j:message pre='${preUi}' key='comment'/>";
	
		   var f = $jq("#examinationForm");
		   
		   f.find(":input[name=businessItem]").val(businessItem);
		   
		   f.find("label").text(labelText);

	   }
		
	   new iKEP.Validator("#examinationForm", validOptions);

	   iKEP.iFrameContentResize();  
	   
	}
	
	function hideExamForm(){
		
		var f = $jq("#examinationForm");
		
		//f.find(":input[name=examinationComment]").val('');
		
		$jq("#examination").hide();

		iKEP.iFrameContentResize();  
	}


	function updateBusiCom(){
		
		$jq.ajax({    
			url : "updateBusinessComplete.do",     
			data : {itemId:'${idea.itemId}'},     
			type : "post",  
			success : function(result) {   
				
				if(result == 'success'){
					alert('<ikep4j:message key='message.collpack.ideation.busniss.complete'/>'); 
					window.location.reload();
				} else {
					alert('<ikep4j:message key='message.collpack.ideation.busniss.notComplete'/>'); 
				}
				
			},
			error : function(event, request, settings){
			 	alert("error");
			}
		}); 
	}
	
	
	function updateBusiCel(){
		
		$jq.ajax({    
			url : "updateBusinessCancel.do",     
			data : {itemId:'${idea.itemId}'},     
			type : "post",  
			success : function(result) {   
				
				if(result == 'success'){
					alert('<ikep4j:message key='message.collpack.ideation.busniss.adoptCancel'/>'); 
					window.location.reload();
				} else {
					alert('<ikep4j:message key='message.collpack.ideation.busniss.adoptError'/>'); 
				}
				
			},
			error : function(event, request, settings){
			 	alert("error");
			}
		}); 
	}

	//파라미터
	var param = '<c:forEach var="ram" items="${param}" ><c:if test="${ram.value != '' && ram.key != 'itemId'}">&${ram.key}=${ram.value}</c:if></c:forEach>';
	

	function itemDel(itemId){

		if(confirm("<ikep4j:message key='message.collpack.ideation.deleteConfirm'/>")){

			$jq("#tagResult").ajaxLoadStart(); 

			location.href="deleteIdea.do?itemId="+itemId+param;
		}
	}

	//]]>
</script>


<h1 class="none">Contents Area</h1>
				
<div id="tagResult">

<c:if test="${param.docPopup != 'true' || !empty(param.docIframe)}">
	<div class="icgroup">
		<div class="btn_icbox">
			<a id="linkFavorite" class="ic_rt_favorite" href="#a" title="<ikep4j:message pre='${preUi}' key='viewFavoriteAdd'/>"><span><ikep4j:message pre='${preUi}' key='viewFavoriteAdd'/></span></a> <!--선택된 경우 <a class="ic_rt_favorite select" href="#a"><span>즐겨찾기 선택됨</span></a>-->
		</div>		
		<div class="btn_icbox_sel" id="btnMailSend">
                <a href="#a" ><img class="ic_mail" src="<c:url value="/base/images/icon/ic_mail_2.gif"/>" alt="mail" /></a>
                <div class="icboxLayer none" id="dropDown">
                    <ul>
                        <li><a href="#a" onclick="mailContentsPop();return false;" title="<ikep4j:message pre='${preUi}' key='emailSend.content'/>"><ikep4j:message pre='${preUi}' key='emailSend.content'/></a></li>
                        <li><a href="#a"  onclick="mailPop();return false;" title="<ikep4j:message pre='${preUi}' key='emailSend.url'/>"><ikep4j:message pre='${preUi}' key='emailSend.url'/></a></li>
                    </ul>
                </div>
        </div>
        <div class="btn_icbox"> 
            <a id="createPrintButton"  onclick="printPage();return false;"  class="ic_note" href="#a" title="<ikep4j:message pre='${preUi}' key='print'/>" ><img src="<c:url value='/base/images/icon/ic_print_02.gif'/>" alt="<ikep4j:message pre='${preUi}' key='print'/>" /></a> 
        </div>     	
	</div>
</c:if>	


<!--blockTableReadWrap Start-->
<div class="blockTableReadWrap">

<!--blockListTable Start-->
<div class="blockTableRead">
	<div class="blockTableRead_t">
		<p>
		<c:if test="${idea.bestItem == 1 }">
			<span class="cate_block_1"><span class="cate_tit_1"><ikep4j:message pre='${preUi}' key='best'/></span></span>
		</c:if>
		<c:if test="${idea.adoptItem == 1 }">
			<span class="cate_block_4"><span class="cate_tit_4"><ikep4j:message pre='${preUi}' key='adopt' /></span></span>
		</c:if>
		<c:if test="${idea.businessItem == 2}">
			<span class="cate_block_3"><span class="cate_tit_3"><ikep4j:message pre='${preUi}' key='businessCom'/></span></span>
		</c:if>
		<c:if test="${idea.businessItem == 1}">
			<span class="cate_block_5"><span class="cate_tit_5"><ikep4j:message pre='${preUi}' key='businessAdopt'/></span></span>
		</c:if>
		<c:if test="${idea.businessItem == 3}">
			<span class="cate_block_5"><span class="cate_tit_2"><ikep4j:message pre='${preUi}' key='viewCancel'/></span></span>
		</c:if>
			&nbsp;${idea.title }				
		</p>
		<div class="summaryViewInfo">
			<span class="summaryViewInfo_name"><a href="#a" onclick="viewPopUpProfile('${idea.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${idea.registerName} ${idea.jobTitleName}</c:when><c:otherwise>${idea.userEnglishName} ${idea.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
			<span class="summaryViewInfo_name"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${idea.teamName}</c:when><c:otherwise>${idea.teamEnglishName}</c:otherwise></c:choose></span>
			<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
			<span><ikep4j:timezone date="${idea.registDate}" pattern="message.collpack.ideation.timezone.dateformat.type2" keyString="true"/></span>
			<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
			<span><ikep4j:message pre='${preUi}' key='viewHit'/> <strong>${idea.hitCount}</strong></span>
			<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
			<span><ikep4j:message pre='${preUi}' key='viewFavorite'/>  <strong id="ideaFavoriteCount">${idea.favoriteCount}</strong></span>
			<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
			<span><ikep4j:message pre='${preUi}' key='adopt'/> <strong id="ideaAdoptCount">${idea.adoptCount}</strong></span>
			<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
			<span><ikep4j:message pre='${preUi}' key='viewLinereply'/> <strong id="ideaLinereplyCount">${idea.linereplyCount}</strong></span>
		</div>
		<c:if test="${!empty(idea.examinationComment)}">
			<div class="tdInstruction">※ <ikep4j:message pre='${preUi}' key='comment'/> 
				<c:if test="${isAdmin == true}">
					<span class="rebtn">
						<a class="ic_modify" href="#" onclick="viewExamForm(); return false;" title="<ikep4j:message pre='${preUi}' key='viewUpdate'/>"><span><ikep4j:message pre='${preUi}' key='viewUpdate'/></span></a>
					</span>
				</c:if>
				<br/> ${idea.examinationComment }
			</div>
		</c:if>
		<div class="recommend">
		 <a class="button_rec_num" href="#a" 
		<c:if test="${(user.userId != idea.registerId) }">            
			  onclick="recommItem();return false;"  
		</c:if>
		title="<ikep4j:message pre='${preUi}' key='viewRecommend'/>"><span class="num">${idea.recommendCount}</span><span class="doc"><img src="<c:url value="/base/images/icon/ic_recommend.gif"/>" alt="<ikep4j:message pre='${preUi}' key='viewRecommend'/>" /><ikep4j:message pre='${preUi}' key='viewRecommend'/></span></a>
		</div>
		
	</div>
	<div id="fileUploadArea"></div>
	<div class="none" id="ideaContents2"><ikep4j:extractTextBody text="${idea.contents}"/></div>
	<div class="blockTableRead_c">
		<p id="ideaContents">
			${idea.contents}
		</p>
		
		<!--tag list-->
		<div class="tableTag" id="tagForm_${idea.itemId}" >
			<input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_IDEATION %>"/>
			<input type="hidden" name="tagItemName" value="${idea.title}"/>
			<input type="hidden" name="tagItemContents" value="${fn:escapeXml(idea.contents)}"/>
			<input type="hidden" name="tagItemUrl" value="/collpack/ideation/getView.do?docPopup=true&amp;itemId=${idea.itemId}"/>
			<div>
				<span class="ic_tag"><span><ikep4j:message pre='${preUi}' key='viewTag'/></span></span>
					<c:forEach var="tag" items="${tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
				<span class="rebtn">
					<c:if test="${user.userId == idea.registerId}">
					<a  href="#a" <c:if test="${param.docPopup != 'true' || !empty(param.docIframe)}"> onclick="iKEP.tagging.tagFormView('${idea.itemId}');return false;"</c:if> title="<ikep4j:message pre='${preUi}' key='viewUpdate'/>" class="ic_modify"><span><ikep4j:message pre='${preUi}' key='viewUpdate'/></span></a>
					</c:if>
				</span>
			</div>
		</div>
		<!--//tag list-->						
	</div>	

</div>
<!--//blockListTable End-->	
					
	<c:if test="${param.docPopup != 'true' || !empty(param.docIframe)}">		
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
		    <c:if test="${(user.userId == idea.registerId || isAdmin)  && (param.docPopup != 'true' || !empty(param.docIframe)) }">
                <c:if test="${!(idea.businessItem == 1 || idea.businessItem == 2 || idea.businessItem == 3)}">
                  <li><a class="button" href="ideaForm.do?itemId=${idea.itemId}<c:if test="${!empty(param.docIframe) }">&amp;docIframe=${param.docIframe}</c:if>" title="<ikep4j:message pre='${preUi}' key='viewUpdate'/>"><span><ikep4j:message pre='${preUi}' key='viewUpdate'/></span></a></li>
                </c:if>
                <c:if test="${!(idea.businessItem == 1 || idea.businessItem == 2 )}">
                  <li><a class="button" href="#a" onclick="itemDel('${idea.itemId}');" title="<ikep4j:message pre='${preUi}' key='viewDelete'/>"><span><ikep4j:message pre='${preUi}' key='viewDelete'/></span></a></li>
                </c:if>
            </c:if>
			<c:if test="${isAdmin == true}">
				<!--
				<c:if test="${idea.businessItem == 0}">
				<li><a class="button" href="#a" onclick="viewExamForm('cancel');return false;" title="<ikep4j:message pre='${preUi}' key='businessCancel'/>"><span><ikep4j:message pre='${preUi}' key='businessCancel'/></span></a></li>	
				<li><a class="button" href="#a" onclick="viewExamForm('adopt');return false;" title="<ikep4j:message pre='${preUi}' key='businessAdopt'/>"><span><ikep4j:message pre='${preUi}' key='businessAdopt'/></span></a></li>	
				</c:if>
				<c:if test="${idea.businessItem == 1}">
					<li><a class="button" href="#a" onclick="updateBusiCel();return false;" title="<ikep4j:message pre='${preUi}' key='businessAdoptCancel'/>"><span><ikep4j:message pre='${preUi}' key='businessAdoptCancel'/></span></a></li>	
					<li><a class="button" href="#a" onclick="updateBusiCom();return false;" title="<ikep4j:message pre='${preUi}' key='businessCom'/>"><span><ikep4j:message pre='${preUi}' key='businessCom'/></span></a></li>	
				</c:if>	
				  -->
				<c:if test="${idea.businessItem == 0}">
					<li><a class="button" href="#a" onclick="viewExamForm('adopt');return false;" title="<ikep4j:message pre='${preUi}' key='businessAdopt'/>"><span><ikep4j:message pre='${preUi}' key='businessAdopt'/></span></a></li>	
				</c:if>
				<c:if test="${idea.businessItem == 1}">
					<li><a class="button" href="#a" onclick="updateBusiCel();return false;" title="<ikep4j:message pre='${preUi}' key='businessAdoptCancel'/>"><span><ikep4j:message pre='${preUi}' key='businessAdoptCancel'/></span></a></li>	
					<!-- 					
					<li><a class="button" href="#a" onclick="updateBusiCom();return false;" title="<ikep4j:message pre='${preUi}' key='businessCom'/>"><span><ikep4j:message pre='${preUi}' key='businessCom'/></span></a></li>	
					-->
				</c:if>	
			</c:if>
			<li><a class="button" href="${listUrl}" title="<ikep4j:message pre='${preUi}' key='viewList'/>"><span><ikep4j:message pre='${preUi}' key='viewList'/></span></a></li>					
		</ul>
	</div>
	<!--//blockButton End-->
	</c:if>			
</div>
<!--//blockTableReadWrap End-->
					
<!-- Modal window Start -->
<!-- 사용시class="none"으로 설정 -->
<div id="examination" style="display:none;">
	<!--blockDetail Start-->
	<form id="examinationForm" action="createComment.do" method="post">
	<input type="hidden" name="docIframe" value="${param.docIframe }"/>
	<input type="hidden" name="itemId" value="${idea.itemId }"/>
	<input type="hidden" name="businessItem" value="${idea.businessItem}"/>
	<div class="blockDetail">
		<table summary="new group">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="18%"><label for="examinationComment"><ikep4j:message pre='${preUi}' key='comment'/></label></th>
					<td width="82%"><div><textarea id="examinationComment" name="examinationComment" class="w95" cols="" rows="4">${fn:replace(idea.examinationComment,'<br>','') }</textarea></div></td>
				</tr>
			</tbody>
		</table>
	</div>
	</form>	
	<!--//blockDetail End-->
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a class="button" href="#a" onclick="createExam();return false;" title="<ikep4j:message pre='${preUi}' key='formLineSave'/>"><span><ikep4j:message pre='${preUi}' key='formLineSave'/></span></a></li>
			<li><a class="button" href="#a" onclick="hideExamForm();return false;" title="<ikep4j:message pre='${preUi}' key='formLineCancel'/>"><span><ikep4j:message pre='${preUi}' key='formLineCancel'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->	
</div>	
<!-- //Modal window End -->
			
				
<!--blockComment Start-->
<div class="blockComment">
	<div class="blockComment_t">
		<ikep4j:message pre='${preUi}' key='viewLinereply'/> <span class="comment_num" id="linereplyCount_${idea.itemId}">(${idea.linereplyCount})</span>
	</div>	
	<div class="guestbook_write" id="guestbook_write_${idea.itemId}" >
		<form method="post" id="linereplyForm_${idea.itemId}" action="">
		<input type="hidden" name="itemId"  value="${idea.itemId}"/>
		<input type="hidden" name="tmpId"  value="${idea.itemId}"/>
			<table summary="<ikep4j:message pre='${preUi}' key='formLineSummary'/>">
				<caption></caption>
				<tr>
					<td>
						<label><input type="radio" class="radio valign_middle" title="<ikep4j:message pre='${preUi}' key='ideaLinereply'/>" name="adoptLinereply" value="0"  checked="checked"/><ikep4j:message pre='${preUi}' key='ideaLinereply'/></label>&nbsp;
						<c:if test="${user.userId != idea.registerId}">
							<span id="adoptDesc">
								<label><input type="radio" class="radio valign_middle" title="<ikep4j:message pre='${preUi}' key='ideaAdopt'/>" name="adoptLinereply" value="1"  /><ikep4j:message pre='${preUi}' key='ideaAdopt'/></label>										
							</span>
							<img class="valign_top" src="<c:url value="/base/images/icon/ic_idea_rec.gif"/>" alt="<ikep4j:message pre='${preList}' key='ideaAdopt'/>" />
						</c:if>
					</td>
				</tr>
				<tr>
					<td><div>
						<textarea name="contents" title="contents" cols="" rows="3" ></textarea>
						</div>
					</td>
					<td width="74" class="textRight">
						<a class="button_re" href="#a" onclick="addReply('${idea.itemId}');return false;" title="<ikep4j:message pre='${preUi}' key='viewSave'/>"><span><ikep4j:message pre='${preUi}' key='viewSave'/></span></a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="lineReply_${idea.itemId}" >
	<c:if test="${!empty(linereplyList) }">
		<c:import url="/WEB-INF/view/collpack/ideation/linereplyListMore.jsp" charEncoding="UTF-8" />
	</c:if>
	</div>		
</div>
<!--//blockComment End-->
</div>				
<div style="display:none;" id="helpContent">	
	<ul>
		<li><span><ikep4j:message pre='${preUi}' key='adopt.help01'/></span></li>	
		<li><span><ikep4j:message pre='${preUi}' key='adopt.help02'/></span></li>																
	</ul>
</div>	