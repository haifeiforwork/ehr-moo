<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>
<%@ include file="/base/common/fileUploadControll.jsp"%>

<jsp:useBean id="IKepConstant" class="com.lgcns.ikep4.framework.constant.JSTLConstant"/>

<c:set var="preUi" 			value="ui.collpack.qna.qnaView" />
<c:set var="preResource" 	value="message.collpack.qna" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="preMail"  value="message.collpack.qna.expert.mail" />
<c:set var="refererUrl"><%=request.getHeader("referer") %></c:set> <%--전페이지주소--%>

<script type="text/javascript">
//<![CDATA[
    var da = (document.all) ? 1 : 0
    var pr = (window.print) ? 1 : 0
    var mac = (navigator.userAgent.indexOf("Mac") != -1);
    
	var validOptions = {
		rules : {
			contents : {
				required : true,
				maxlength  : 300
			}
		},
		messages : {
			contents : {
				required : "<ikep4j:message key='NotEmpty.qnaLinereply.contents'/>"
				,maxlength : "<ikep4j:message key='Size.qnaLinereply.contents'/>"
			}
		},
		notice : {
			contents : "<ikep4j:message key='NotEmpty.qnaLinereply.contents'/>"
		},
		submitHandler : function(form) {
			
			addReplyAction(form.tmpId.value);
			
		}
		
	};

	var lineValid = new Array();

	$jq(document).ready(function() {
		
		<%-- 등록하고 접근 하였으면 --%>
		var referer = "${refererUrl}";
		var mblogChannel = "${param.mblogChannel}";
		
		if((referer.indexOf('formQna.do')>0 || referer.indexOf('formExpertQna.do')>0) && $jq.cookie('isMblog') != 1 && mblogChannel == 1){
			$jq.post("<c:url value='/socialpack/microblogging/createMblog.do'/>", 
					$jq('form[name=mblogForm]').serialize(), 
					function(data) {
						$jq.cookie('isMblog', '1');
					});
		}	
		
		
		
		<c:forEach var="qna" items="${qnaGroupList}" >
			replyList('${qna.qnaId}');
			
			lineValid["line_${qna.qnaId}"] =  new iKEP.Validator('#linereplyForm_${qna.qnaId}', validOptions);

			
			//파일업로드 컨트롤로 생성   
			<c:if test="${fn:length(qna.fileDataListJson) > 3}">
				new iKEP.FileController(null, "#fileUploadArea_${qna.qnaId}", {files : ${qna.fileDataListJson}, isUpdate : false});
			</c:if>
		       
		</c:forEach>

		
		//즐겨찾기
		<c:choose>
			<c:when test="${isFavorite == 'true'}">
				toggleFavorteImg('del');
			</c:when>
			<c:otherwise>
				toggleFavorteImg('add');
			</c:otherwise>
		</c:choose>

		//메일 보내기 이벤트
		$jq("#btnMailSend")
		.mouseover(function(event){ event.stopPropagation(); $jq('#dropDown').show();})
		.mouseout(function(event){ event.stopPropagation(); $jq('#dropDown').hide();});
		
	});
	

	//파라미터
	var param = '<c:forEach var="ram" items="${param}" ><c:if test="${ram.value != '' && ram.key != \'qnaId\' }">${ram.key}=${ram.value}&</c:if></c:forEach>';

	function qnaUpdate(qnaId, qnaType, qnaGroupId){

		var url = "";
		if(qnaType == 0){
			url = 'formQna.do?qnaId='+qnaId + '&' +param;;
		} else {
			url = 'formAnswer.do?qnaGroupId='+qnaGroupId+'&qnaId='+qnaId + '&' +param;
		}
		
		location.href=url;
	}
	
	
	function qnaDel(id){

		if(confirm("<ikep4j:message pre='${preResource}' key='deleteConfirm'/>")){

			location.href='deleteItemQna.do?qnaId='+id+'&'+ param;
		}
		
	}
	
	
	function qnaAnswer(id){
		
		location.href='formAnswer.do?qnaGroupId='+id+'&'+param;
	}
	

	function goList(val){

		var url = 'list'+val+'Qna.do?'+param;
		
		if(val == "Main"){
			url = 'main.do?'+param;
		}

		location.href=url;
	}
	
	
	function addReplyAction(val){
		
		var f = '';
		var linereplyId ='';
		var linereplyParentId = '';
		
		f = $jq('#linereplyForm_'+val);
		
		linereplyId = f.find(':input[name=linereplyId]').val();
		linereplyParentId = f.find(':input[name=linereplyParentId]').val();
		
		var contents = f.find(':input[name=contents]').val();
		var qnaId = f.find(':input[name=qnaId]').val();

		f.parents(".blockComment").ajaxLoadStart(); 
		
		 $jq.ajax({    
			url : "createReply.do",     
			data : {qnaId:qnaId, contents:contents, linereplyId:linereplyId, linereplyParentId:linereplyParentId},     
			type : "post",     
			success : function(result) {    

				f.parents(".blockComment").ajaxLoadComplete();
				    
				showLinereplyList(qnaId, result);
				//$jq('#guestbook_write_'+val).hide();
				f.find(':input[name=contents]').val('');
				
			},
			error : function(xhr, exMessage, httpStatus) {
				
				var errorItems = $jq.parseJSON(xhr.responseText).exception;

				if(linereplyId){
					lineValid['lineUp_'+linereplyId].showErrors(errorItems);
				} else if(linereplyParentId) {
					lineValid['lineRe_'+linereplyParentId].showErrors(errorItems);
				} else {
					lineValid['line_'+qnaId].showErrors(errorItems);
				}

				f.parents(".blockComment").ajaxLoadComplete();
					
	        }

		}); 
		
		$jq("#lineReplyContents_"+qnaId).val('');
		$jq("#linereplyId_"+qnaId).val(''); 
		
		
	}
	function addReply(val){
		
		$jq('#linereplyForm_'+val).submit();
		
	}
	
	
	function showLinereplyAddForm(linereplyParentId, qnaId, type){
		
		 $jq('.lineFrame').remove();
		 $jq("#linereplyContents_"+tmplineId).show();

		 tmplineId = linereplyParentId;
		
		 var  html = '<div class="blockComment_rewrite lineFrame" id="blockComment_rewrite_'+linereplyParentId+'">';
		 html += '<div class="reply_ar"><img src="<c:url value="/base/images/icon/ic_reply_ar_b.gif"/>" alt="reply" /></div>';
		 html += '<form method="post" id="linereplyForm_'+linereplyParentId+'">';
		 html += '<input type="hidden" name="qnaId"  value="'+qnaId+'"/>';
		 html += '<input type="hidden" name="linereplyId"  value=""/><input type="hidden" name="tmpId"  value="'+linereplyParentId+'"/>';
		 html += '<input type="hidden" name="linereplyParentId" value="'+linereplyParentId+'"/>';
		 html += '<table summary="<ikep4j:message pre='${preUi}' key='viewLinereply'/>"><caption></caption><tr><td><div>';
		 html += '<input name="contents" title="<ikep4j:message pre='${preUi}' key='viewLinereply'/>"  class="inputbox" type="text" value=""/>';
		 html += '</div></td><td width="95" class="textRight"><ul><li><a class="button_ic" href="#a" onclick="addReply('+linereplyParentId+');return false;" title="<ikep4j:message pre='${preUi}' key='formLineSave'/>"><span><ikep4j:message pre='${preUi}' key='formLineSave'/></span></a></li>';
		 html += '<li><a class="button_ic" href="#a" onclick="hideLinereplyForm('+linereplyParentId+');return false;" title="<ikep4j:message pre='${preUi}' key='formLineCancel'/>"><span><ikep4j:message pre='${preUi}' key='formLineCancel'/></span></a></li></ul></td></tr></table></form></div>';


		 $jq("#linereplyContents_"+linereplyParentId).after(html);
		 
		 
		 lineValid['lineRe_'+linereplyParentId] = new iKEP.Validator('#linereplyForm_'+linereplyParentId, validOptions);

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
	
	var tmplineId;		//리플 아이디 임시 저정
	function linereplyUpdateForm(linereplyId, qnaId){
		
		$jq(".lineFrame").remove();
		$jq("#linereplyContents_"+tmplineId).show();

		tmplineId = linereplyId;
		
		var html = '<div class="guestbook_write lineFrame" id="blockComment_rewrite_'+linereplyId+'" >';
			html += '<form method="post" id="linereplyForm_'+linereplyId+'">';
			html += '<input type="hidden" name="qnaId"  value="'+qnaId+'"/>';
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
		 
		lineValid['lineUp_'+linereplyId] =  new iKEP.Validator('#linereplyForm_'+linereplyId, validOptions);

		$jq("#linereplyForm_"+linereplyId).find(':input[name=contents]').focus();

		$jq("#linereplyContents_"+linereplyId).hide();
		
	}
	
	
	function linereplyReplyUpdateForm(linereplyId, qnaId){
		
		$jq(".lineFrame").remove();
		
		showLinereplyAddForm(linereplyId, qnaId, "update");
		
		$jq("#blockComment_rewrite_"+linereplyId + ' form').find(':input[name=contents]').val( $jq.trim($jq("#linereplyContents_"+linereplyId).text()));
		$jq("#blockComment_rewrite_"+linereplyId + ' form').find(':input[name=linereplyId]').val(linereplyId);
		$jq("#blockComment_rewrite_"+linereplyId + ' form').find(':input[name=linereplyParentId]').val('');
		
	}
	
	
	String.prototype.replaceAll = function( searchStr, replaceStr ){
		return this.split( searchStr ).join( replaceStr );
	}

	
	function linereplyDel(replyNo, qnaId){

		if(confirm("<ikep4j:message pre='${preResource}' key='deleteConfirm'/>")){

			$jq.ajax({    
					url : "deleteItemReply.do",     
					data : {replyNo:replyNo, qnaId:qnaId},     
					type : "post",     
					success : function(result) {    
	
						showLinereplyList(qnaId, result);
					},
					error : function(event, request, settings){
					 	alert("error");
					}
				}); 

		}
	}
	
	function linereplyAlive(replyNo, qnaId){
		
		$jq.ajax({    
				url : "aliveItemReply.do",     
				data : {replyNo:replyNo, qnaId:qnaId},     
				type : "post",     
				success : function(result) {    
					showLinereplyList(qnaId, result);
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
			data : {qnaId:val, pageIndex:pageIndex},     
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

		var count = $jq("#lineReply_"+val+" p:first").attr('count');
		if(!count || count == 0 ) { 
			count = "";
		} else {
			count = "("+count+")";
		}
		
		$jq("#linereplyCount_"+val).text(count);
		
		$jq("#lineReply_"+val).parent().attr('class','blockComment');
		
	}
		
	function recomm(val, userId){

		//본인 자료 체크
		if('${user.userId}' == userId){

			alert('<ikep4j:message pre='${preResource}' key='recommendSelf'/>');
			return;
			
		}

		if(confirm("<ikep4j:message pre='${preResource}' key='confirmRecommend'/>")){
			$jq.ajax({    
				url : "createRecommend.do",     
				data : {qnaId:val},     
				type : "post",  
				success : function(result) {   
					
					if(result.stat == 'success'){
						alert('<ikep4j:message pre='${preResource}' key='alertRecommend'/>');  
						document.location.reload();
					} else {
						if(result.message == 'exist'){
							alert('<ikep4j:message pre='${preResource}' key='alreadyRecommend'/>'); 
						} else {
							alert('<ikep4j:message pre='${preResource}' key='alertNotRecommend'/>'); 
						}
					}
					
				},
				error : function(event, request, settings){
				 	alert("error");
				}
			}); 

		}
	}
	
	
	function recommDel(qnaId){
		
		$jq.ajax({    
			url : "deleteRecommend.do",     
			data : {qnaId:qnaId},     
			type : "post",     
			success : function(result) {        
				
				$jq("#lineReply_"+qnaId).html(result);       
			},
			error : function(event, request, settings){
			 	alert("error");
			}
		}); 
	}			
	
	function adoptOk(qnaId, groupId){
		
		$jq.ajax({    
			url : "adoptOk.do",     
			data : {qnaId:qnaId, groupId:groupId},     
			type : "post",     
			success : function(result) {        
				
				alert('<ikep4j:message pre='${preResource}' key='alertAdot'/>');  
				document.location.reload();
			},
			error : function(event, request, settings){
			 	alert("error");
			}
		}); 
	
	}
	
	function adoptNo(qnaId, groupId){
		
		$jq.ajax({    
			url : "adoptNo.do",     
			data : {qnaId:qnaId, groupId:groupId},     
			type : "post",     
			success : function(result) {        
				
				alert('<ikep4j:message pre='${preResource}' key='alertNotAdot'/>');  
				document.location.reload();
			},
			error : function(event, request, settings){
			 	alert("error");
			}
		}); 
	
	}
	
	
	function addFavorite(title){
		
			iKEP.addFavorite('CONTENTS','${IKepConstant.ITEM_TYPE_CODE_QA}','${param.qnaId}', '${fn:escapeXml(qnaGroupList[0].title)}', function(data){
				
				if(data.status == "success"){
					
					$jq.ajax({    
						url : "addFavorite.do",     
						data : {qnaGroupNo:'${param.qnaId}'},     
						type : "post",  
						success : function(result) {   
							
							alert('<ikep4j:message pre='${preResource}' key='alertFavorite'/>'); 
							toggleFavorteImg('del');
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
	
	
	
	
	function delFavorite(){
		
		iKEP.delFavorite('','${param.qnaId}', function(data){
			
			if(data.status == "success"){
				$jq.ajax({    
					url : "delFavorite.do",     
					data : {qnaGroupNo:'${param.qnaId}'},     
					type : "post",  
					success : function(result) {   
						
						alert("<ikep4j:message pre='${preResource}' key='alertNotFavorite'/>");
						 
						toggleFavorteImg('add');
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

	
	function goLinePage(val){
		
		var qnaId = val.substring(val.indexOf("qnaId")+6,val.length);
		var f = $jq('#lineForm_'+qnaId);
		
		replyList(f.find(":input[name=qnaId]").val(), f.find(":input[name=pageIndex]").val(), true);
		
	}
	
	function toggleLinereply(val){
		
		$jq('#guestbook_write_'+val+' textarea').val('');
		$jq('#guestbook_write_'+val).toggle();
		
	}
	
	function fileDown(val){
		
		location.href='<c:url value="/support/fileupload/downloadFile.do"/>?fileId='+val+'&thumbnailYn=N';
		
	}
	
	//마이크로블로그 팝업
	function twittingPop(){

		var content = $jq.trim($jq('.blockTableRead_t:first p:first').text()); 
		
		var url = iKEP.getWebAppPath() + "/collpack/qna/getQna.do?qnaId=${param.qnaId}";

		iKEP.showTwittingPage(content,url);
		
		
	};

	//블로그 성공적으로 보내고 콜백함수
	function callbackMBlog(){
		$jq.ajax({    
			url : "updateMblogCount.do",      //  업데이트 URL은 작성 모듈에 맞게 변경
			data : {qnaId:'${param.qnaId}'},  
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
		 
		var url = iKEP.getWebAppPath() + "/collpack/qna/getQna.do?qnaId=${param.qnaId}";
		//var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;";
		
		var content = "<a href='"+url+"' target='_blank'>"+$jq.trim($jq('.blockTableRead_t:first p:first').text())+"</a>" ;
		
		 // 단순 작성 팝업 열기
		 var url = "<c:url value='/support/message/messageNew.do'/>?contents="+content;
		 
		 iKEP.popupOpen(url ,{width:800, height:670} );
	}
	
	//링크 보내기 팝업
	function mailPop() {

		var url = iKEP.getWebAppPath() + "/collpack/qna/getQna.do?qnaId=${param.qnaId}";
		//var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;";

		var title = $jq.trim($jq('.blockTableRead_t:first p:first').text());
		var content = "<a href=\""+url+"\" target='_blank'>"+$jq.trim($jq('.blockTableRead_t:first p:first').text())+"</a>" ;

		iKEP.sendTerraceMailPop("", title, content);        
		
	}

	
/* 	
	//메일내용 보내기 
	function mailContentsPop() {

		var url = iKEP.getWebAppPath() + "/collpack/qna/getQna.do?qnaId=${param.qnaId}";
		//var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;";

		var title = $jq.trim($jq('.blockTableRead_t:first p:first').text());
		
		var contentHtml = $jq('#tagResult').html();

		$jq('#tagResult .guestbook_write').each(function(){
			contentHtml = contentHtml.replace($jq(this).html(),"");
		});

		$jq('#tagResult .icgroup').each(function(){
			contentHtml = contentHtml.replace($jq(this).html(),"");
		});

		$jq('#tagResult .blockButton').each(function(){
			contentHtml = contentHtml.replace($jq(this).html(),"");
		});


		$jq('#tagResult a').each(function(){

			var aHtml = $jq(this).html();

			if(this.className == "button_rec_num" || this.className == "btn_page_first" || this.className == "btn_page_pre" || this.className == "btn_page_next" || this.className == "btn_page_last"){
				aHtml= "<a class='"+this.className+"'>"+aHtml+"</a>";
			}
			
			contentHtml = contentHtml.replace($jq(this).clone().wrapAll("<div/>").parent().html(),aHtml);
		});

		var fileIdList = [<c:forEach var="fileData" items="${qnaGroupList[0].fileDataList}" varStatus="loop"><c:if test="${loop.count != 1}">, </c:if>'${fileData.fileId}'</c:forEach>];
		var fileNameList = [<c:forEach var="fileData" items="${qnaGroupList[0].fileDataList}" varStatus="loop"><c:if test="${loop.count != 1}">, </c:if>'${fileData.fileRealName}'</c:forEach>];

		 iKEP.sendTerraceMailPop("", title, contentHtml);        
		
	} */	
	
	//메일 내용 보내기 (무림 신규)
	function mailContentsPop() {
		
		
		var url = iKEP.getWebAppPath() + "/collpack/qna/getQna.do?qnaId=${param.qnaId}";
		
		var link = "window.open('"+url+"', 'qna', 'width=800px, height=670px, scrollbars=yes');return false;";
		var contentClone = $jq("#qnaContent2").clone();
		var qnaTitle = $jq("#qnaTitle").clone();
		iKEP.sendMailPop("","", $jq(qnaTitle).html(), $jq(contentClone).html(), "", ""); 
		
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
			data : {qnaId:'${param.qnaId}'},     
			type : "post",  
			dataType : "html",
			success : function(result) {   
			},
			error : function(event, request, settings){
			 alert("error");
			}
		}); 
	}
	
//]]>	
</script>

<%-- 등록하고 접근 하였으면 --%>
<c:if test="${fn:contains(refererUrl,'formQna.do') || fn:contains(refererUrl,'formExpertQna.do')}">
	<c:set var="mblogcontents">
		<c:forEach var="expert" items="${expertList}" >@${expert.expertId} </c:forEach>${user.userId}<ikep4j:message pre='${preMail}' key='contents' /><c:if test="${qnaGroupList[0].urgent == 1}"><ikep4j:message pre='${preMail}' key='urgent' /></c:if><a href="<c:url value="/collpack/qna/getQna.do?qnaId=${qnaGroupList[0].qnaId}"/>" target='_blank'><ikep4j:extractText text="${qnaGroupList[0].title}" length="50"/><c:if test="${fn:length(qnaGroupList[0].title) > 50 }">...</c:if></a>
	</c:set>
	<form name="mblogForm" method="post" action="">
		<input type ="hidden" name="mblogType" value = "0" />
		<input type ="hidden" name="contents" value = "${fn:escapeXml(mblogcontents)}" />
	</form>
</c:if>	

<!--guideWrapper Start-->
<h1 class="none">Contents Area</h1>

<c:choose>
	<c:when test="${qnaGroupList[0].itemDelete == 1 && !isAdmin}">
		<ikep4j:message key='ui.collpack.qna.qnaMoreList.listDelText'/>
	</c:when>
	<c:otherwise>
	
<div id="tagResult">

	<c:if test="${param.docPopup != 'true' }">
		<div class="icgroup">
			<div class="btn_icbox">
				<a id="linkFavorite" class="ic_rt_favorite" href="#a" title="<ikep4j:message pre='${preUi}' key='viewFavoriteAdd'/>"><span><ikep4j:message pre='${preUi}' key='viewFavoriteAdd'/></span></a> <!--선택된 경우 <a class="ic_rt_favorite select" href="#a"><span>즐겨찾기 선택됨</span></a>-->
			</div>
			<div class="btn_icbox_sel" id="btnMailSend">
				<a href="#a" ><img class="ic_mail" src="<c:url value="/base/images/icon/ic_mail_2.gif"/>" alt="mail" /></a>
				<div class="icboxLayer none" id="dropDown">
					<ul>
						<li><a href="#a" onclick="mailContentsPop();return false;" title="<ikep4j:message pre='${preUi}' key='viewSendMailContents'/>"><ikep4j:message pre='${preUi}' key='viewSendMailContents'/></a></li>
						<li><a href="#a"  onclick="mailPop();return false;" title="<ikep4j:message pre='${preUi}' key='viewSendMailLink'/>"><ikep4j:message pre='${preUi}' key='viewSendMailLink'/></a></li>
					</ul>
				</div>
			</div>
			<div class="btn_icbox"> 
            <a id="createPrintButton"  onclick="printPage();return false;"  class="ic_note" href="#a" title="<ikep4j:message pre='${preUi}' key='print'/>" ><img src="<c:url value='/base/images/icon/ic_print_02.gif'/>" alt="<ikep4j:message pre='${preUi}' key='print'/>" /></a> 
            </div> 	
			<!-- div class="btn_icbox">
				<a class="ic_note" href="#a" onclick="messgaePop();return false;" title="<ikep4j:message pre='${preUi}' key='viewSendMsg'/>"><img src="<c:url value="/base/images/icon/ic_note.gif"/>" alt="<ikep4j:message pre='${preUi}' key='viewSendMsg'/>" /></a>
			</div>
			<div class="btn_icbox">
				<a class="ic_microblog" href="#a" onclick="twittingPop();return false;" title="<ikep4j:message pre='${preUi}' key='viewSendBlog'/>"><img src="<c:url value="/base/images/icon/ic_microblog.gif"/>" alt="<ikep4j:message pre='${preUi}' key='viewSendBlog'/>" /></a>
			</div-->
			
			<div id="qaCategoryLocation">
				<ul>
					<li class="liFirst"><ikep4j:message pre='${preUi}' key='category'/></li>
			 		<li class="liLast">
						<c:choose>
							<c:when test="${!empty(categoryName)}">${categoryName}</c:when>
							<c:otherwise><ikep4j:message pre='${preUi}' key='cetera'/></c:otherwise>
						</c:choose>
					</li>
				</ul>
			</div>			
		</div>
	</c:if>	
	
	<c:forEach var="qna" items="${qnaGroupList}" varStatus="qnaLoopCount">
		
		<c:choose>
 			<c:when test="${qna.qnaType == 0}">
 				<c:set var="className" value="blockTableRead qna_question"/>
 				<c:set var="typeName" value="Question"/>
 				<c:set var="imgName" value="q"/>
 			</c:when>
 			<c:otherwise>
 				<c:set var="className" value="blockTableRead qna_answer"/>
 				<c:set var="typeName" value="Answer"/>
 				<c:set var="imgName" value="a"/>
 			</c:otherwise>
 		</c:choose>
	
		<!--blockTableReadWrap Start-->
		<div class="blockTableReadWrap">
		
		<!--qna_question Start-->
		<div class="${className}">
			<h3 class="none">${typeName}</h3>
			<div class="qnaimg">
				<span class="ic_qna_${imgName}_2"><span>Question</span></span>
			</div>	
			
		<c:if test="${qna.qnaType == 1}">
			<div class="qna_answer_bg"></div>	
		</c:if>	
		
			<div class="blockTableRead_t">
			
				<c:choose> <%--질문 답변 best class 변경--%>
					<c:when test="${qna.qnaType == 0}">
						<c:set var="qnaTypeBestType" value="qna_question_best" />
					</c:when>
					<c:otherwise>
						<c:set var="qnaTypeBestType" value="qna_answer_best" />
					</c:otherwise>
				</c:choose>
				<div class="${qnaTypeBestType}">
					<c:if test="${qna.answerAdopt == 1 && qna.qnaType == 1}">
					<img src="<c:url value="/base/images/common/img_select.png"/>" alt="select" />
					</c:if>
					<c:if test="${qna.bestFlag  == 1}">
						<c:choose>
							<c:when test="${qna.qnaType == 1}"><img src="<c:url value="/base/images/common/img_best.png"/>" alt="best" /></c:when>
							<c:otherwise><img src="<c:url value="/base/images/common/img_best1.png"/>" alt="best" /></c:otherwise>
						</c:choose>
					
					</c:if>	
				</div>
				<p>
				<c:choose>
		 			<c:when test="${qna.itemDelete  == 1 && qna.qnaType == 0}">
		 				<span class="deletedItem"><ikep4j:message key='ui.collpack.qna.qnaMoreList.listDelText'/></span>
		 			</c:when>
		 			<c:otherwise>	
		 				<c:if test="${qna.urgent == 1}">[<ikep4j:message pre='${preUi}' key='viewUrgent'/>]</c:if> ${qna.title}
		 			</c:otherwise>
		 		</c:choose>				
				</p>
				<div class="summaryViewInfo">
					<span class="summaryViewInfo_name"><a href="#a" onclick="viewPopUpProfile('${qna.registerId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${qna.registerName} ${qna.jobTitleName}</c:when><c:otherwise>${qna.userEnglishName} ${qna.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					<span><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${qna.teamName}</c:when><c:otherwise>${qna.teamEnglishName}</c:otherwise></c:choose></span>
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					<span><ikep4j:timezone date="${qna.registDate}" pattern="message.collpack.qna.timezone.dateformat.type2" keyString="true"/></span>
				<c:if test="${qna.qnaType == 0}">
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					<span><ikep4j:message pre='${preUi}' key='viewHit'/> <strong>${qna.hitCount}</strong></span>
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					<span><ikep4j:message pre='${preUi}' key='viewReply'/> <strong>${qna.replyCount}</strong></span>
					<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
					<span><ikep4j:message pre='${preUi}' key='viewRecommend'/> <strong>${qna.recommendCount}</strong></span>
				</c:if>	
				</div>
				<c:if test="${qna.qnaType == 1}">
				<div class="recommend">
					<a name="${qna.qnaId }" class="button_rec_num" href="#a"  onclick="recomm('${qna.qnaId}', '${qna.registerId }');return false;" title="<ikep4j:message pre='${preUi}' key='viewRecommend'/>">
						<span class="num">${qna.recommendCount}</span><span class="doc"><img src="<c:url value="/base/images/icon/ic_recommend.gif"/>" alt="<ikep4j:message pre='${preUi}' key='viewRecommend'/>" /><ikep4j:message pre='${preUi}' key='viewRecommend'/></span>
					</a>
				</div>
				</c:if>
			</div>
			
			<div id="fileUploadArea_${qna.qnaId }"></div>
			<div class="none" id="qnaContent2"><ikep4j:extractTextBody text="${qna.contents}"/></div>
			<div class="none" id="qnaTitle"><ikep4j:extractTextBody text="${qna.title}"/></div>
			<div class="blockTableRead_c">
				<p>
					<spring:htmlEscape defaultHtmlEscape="false"> 
					${qna.contents}
					</spring:htmlEscape>
				</p>
				
				<c:choose>
					<c:when test="${qna.qnaType == 0}">
						<c:set var="tagSubType" value="Q"/>	
					</c:when>
					<c:otherwise>
						<c:set var="tagSubType" value="A"/>	
					</c:otherwise>
				</c:choose>
				
					<!--tag list-->
					<div class="tableTag" id="tagForm_${qna.qnaId}">
						<input type="hidden" name="tagItemType" value="<%=TagConstants.ITEM_TYPE_QNA %>"/>
						<input type="hidden" name="tagItemSubType" value="${tagSubType}"/>
						<input type="hidden" name="tagItemName" value="${qna.title}"/>
						<input type="hidden" name="tagItemContents" value="${fn:escapeXml(qna.contents)}"/>
						<input type="hidden" name="tagItemUrl" value="/collpack/qna/getQna.do?docPopup=true&amp;qnaId=${qna.qnaGroupId}"/>
						<div>
							<span class="ic_tag"><span><ikep4j:message pre='${preUi}' key='viewTag'/></span></span>
								<c:forEach var="tag" items="${qna.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if><a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a></c:forEach>
							<span class="rebtn">
								<c:if test="${user.userId == qna.registerId}">
								<a href="#a" <c:if test="${param.docPopup != 'true' }"> onclick="iKEP.tagging.tagFormView('${qna.qnaId}');return false;"</c:if> title="<ikep4j:message pre='${preUi}' key='viewUpdate'/>" class="ic_modify"><span><ikep4j:message pre='${preUi}' key='viewUpdate'/></span></a>
								</c:if>
							</span>
						</div>
					</div>
					<!--//tag list-->
							
			</div>	
			
		<c:if test="${qna.qnaType == 0 && !empty(expertList)}"> 
			<div class="expert_reply">
				<h4><ikep4j:message pre='${preUi}' key='viewReplyExpert'/> <span class="colorPoint">${expertListCount}<ikep4j:message pre='${preUi}' key='viewReplyExpertCount'/></span></h4>					
				<table summary="<ikep4j:message pre='${preUi}' key='viewReplyExpert'/>">
					<caption></caption>
					<tbody>
						
						<c:forEach var="expert" items="${expertList}" varStatus="loopCount">
							<tr>
							<td>

								<p class="qna_best_name"><a href="#a" onclick="viewPopUpProfile('${expert.expertId}');return false;" title="profile"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${expert.userName} ${expert.jobTitleName}</c:when><c:otherwise>${expert.userEnglishName} ${expert.jobTitleEnglishName}</c:otherwise></c:choose></a>
								<c:if test="${!empty expert.expertId}"><img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" /></c:if>
								<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${expert.teamName}</c:when><c:otherwise>${expert.teamEnglishName}</c:otherwise></c:choose></p>	

							</td>
							</tr>
						</c:forEach>
					
					</tbody>
				</table>
			</div>
		</c:if>
		
		<!--blockComment Start-->
		<div class="blockComment noLine">
		
			<div class="blockComment_t">
				<ikep4j:message pre='${preUi}' key='viewLinereply'/> <span class="comment_num" id="linereplyCount_${qna.qnaId}"><c:if test="${!empty(qna.linereplyCount)}">(${qna.linereplyCount })</c:if></span>
			</div>
			
			<c:if test="${qna.itemDelete  != 1}">
			<div class="guestbook_write" id="guestbook_write_${qna.qnaId}" >
				<form method="post" id="linereplyForm_${qna.qnaId}" action="">
				<input type="hidden" name="qnaId"  value="${qna.qnaId}"/>
				<input type="hidden" name="tmpId"  value="${qna.qnaId}"/>
					<table summary="<ikep4j:message pre='${preUi}' key='formLineSummary'/>">
						<caption></caption>
						<tr>
							<td><div>
								<textarea id="replyContents_${qna.qnaId}" name="contents" title="contents" cols="" rows="3" ></textarea>
								 <label for="replyContents_${qna.qnaId}" class="serverError"> </label>
								</div>
							</td>
							<td width="74" class="textRight">
								<a class="button_re" href="#a" onclick="addReply('${qna.qnaId}');return false;" title="<ikep4j:message pre='${preUi}' key='viewSave'/>"><span><ikep4j:message pre='${preUi}' key='viewSave'/></span></a>
							</td>
						</tr>
					</table>
				</form>
			</div>
			</c:if>
			
			<div id="lineReply_${qna.qnaId}" ></div>
		
		</div>
		<!--//blockComment End-->
			
		</div>
		<!--//qna_question End-->	
 
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
			
			
			<c:if test="${qna.qnaType == 0 && param.docPopup != 'true'}">
				<c:set var="groupRegisterId" value="${qna.registerId}"/>
				<c:set var="groupId" value="${qna.qnaId}"/>
				<c:if test="${(user.userId == qna.registerId || isAdmin) && param.docPopup != 'true'}">
                   <li><a class="button" href="#a" onclick="qnaUpdate('${qna.qnaId}', '${qna.qnaType}', '${qna.qnaGroupId}');return false;" title="<ikep4j:message pre='${preUi}' key='viewUpdate'/>"><span><ikep4j:message pre='${preUi}' key='viewUpdate'/></span></a></li>
                   <li><a class="button" href="#a" onclick="qnaDel('${qna.qnaId}');return false;" title="<ikep4j:message pre='${preUi}' key='viewDelete'/>"><span><ikep4j:message pre='${preUi}' key='viewDelete'/></span></a></li>
                </c:if>				
				<c:if test="${qna.itemDelete  != 1}">
					<li><a class="button" href="#a" onclick="qnaAnswer('${qna.qnaId}');return false;" title="<ikep4j:message pre='${preUi}' key='viewReply'/>"><span><ikep4j:message pre='${preUi}' key='viewReply'/></span></a></li>
				</c:if>
					<li><a class="button" href="#a" onclick="goList('${param.listType}');return false;" title="<ikep4j:message pre='${preUi}' key='viewList'/>"><span><ikep4j:message pre='${preUi}' key='viewList'/></span></a></li>
			</c:if>
			
			<c:if test="${qna.qnaType == 1 && param.docPopup != 'true'}">
				<c:if test="${user.userId == groupRegisterId || isAdmin}">
					<c:choose>
			 			<c:when test="${qna.answerAdopt == 1}">
			 				<li><a class="button" href="#a" onclick="adoptNo('${qna.qnaId}','${qna.qnaGroupId}');return false;" title="<ikep4j:message pre='${preUi}' key='viewAdoptNo'/>"><span><ikep4j:message pre='${preUi}' key='viewAdoptNo'/></span></a></li>
			 			</c:when>
			 			<c:otherwise>
			 				<li><a class="button" href="#a" onclick="adoptOk('${qna.qnaId}','${qna.qnaGroupId}');return false;" title="<ikep4j:message pre='${preUi}' key='viewAdoptOk'/>"><span><ikep4j:message pre='${preUi}' key='viewAdoptOk'/></span></a></li>
			 			</c:otherwise>
		 			</c:choose>
	 			</c:if>
			</c:if>
			<li></li>
			</ul>
		</div>
		<!--//blockButton End-->
		
	<!--//blockTableReadWrap End-->
	</div>
		
	</c:forEach>	
<!--guideWrapper End -->


<c:if test="${!empty(qnaRelationList)}">

	<!--blockRelated Start-->
	<div class="blockRelated">
		<div class="blockRelated_t">
			<ikep4j:message pre='${preUi}' key='viewRelation'/><span class="comment_num">(${relationListCount })</span>
			<span class="more">
			<c:if test="${relationListCount > 5 && param.docPopup != 'true' }">
			<a href="listRelationQna.do?qnaGroupId=${param.qnaId}"><img src='<c:url value="/base/images/common/btn_more.gif"></c:url>' alt="more" /></a>
			</c:if>
			</span>
		</div>
		<ul>
		<c:forEach var="qna" items="${qnaRelationList}" varStatus="qnaLoopCount">
		<c:if test="${qnaLoopCount.count <= 5 }">
			<li>
				<div class="common_related_post_div" style="width:39%;">
					<div class="ellipsis">
						<c:choose>
				 			<c:when test="${qna.status  == 0}">
				 				<span class="status_None"><ikep4j:message pre='${preUi}' key='listStatusN'/></span>
				 			</c:when>
				 			<c:when test="${qna.status  == 1}">
				 				<span class="status_Ing"><ikep4j:message pre='${preUi}' key='listStatusA'/></span>
				 			</c:when>
				 			<c:when test="${qna.status  == 2}">
				 				<span class="status_Done"><ikep4j:message pre='${preUi}' key='listStatusSolve'/></span>
				 			</c:when>
				 		</c:choose>
		 		 		<c:if test="${qna.urgent == 1}">
							<img class="imgtable valign_middle" src="<c:url value="/base/images/icon/ic_emer.gif"/>" alt="<ikep4j:message pre='${preUi}' key='listStatusUrgent'/>" />
						</c:if>
						
						<c:if test="${param.docPopup != 'true' }">
						 	<a href="getQna.do?qnaId=${qna.qnaGroupId}&amp;listType=My" title="${qna.title}">${qna.title}</a>
						</c:if>
						<c:if test="${param.docPopup == 'true' }">
							${qna.title}
						</c:if>
						<c:if test="${qna.bestFlag == 1}">
							<img src="<c:url value="/base/images/icon/ic_best.gif"/>" alt="best" class="imgtable valign_middle" />
						</c:if>
					</div>
				</div>
				<div class=common_related_summary_info style="width:59%;">
					<div class="ellipsis">
						<span class="summaryViewInfo_name"><a href="#a" onclick="viewPopUpProfile('${qna.registerId}');return false;" title="profile" ><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${qna.registerName} ${qna.jobTitleName}</c:when><c:otherwise>${qna.userEnglishName} ${qna.jobTitleEnglishName}</c:otherwise></c:choose></a></span>
						<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
						<span class="summaryViewInfo_name"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${qna.teamName}</c:when><c:otherwise>${qna.teamEnglishName}</c:otherwise></c:choose></span>
						<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
						<span><ikep4j:timezone date="${qna.registDate}" pattern="message.collpack.qna.timezone.dateformat.type2" keyString="true"/></span>
						<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
						<span><ikep4j:message pre='${preUi}' key='viewHit'/> <strong>${qna.hitCount}</strong></span>
						<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
						<span><ikep4j:message pre='${preUi}' key='viewRecommend'/> <strong>${qna.recommendCount}</strong></span>
					</div>					
				</div>
				<!--  
				<div class="summaryViewInfo" style="width:19%;float:left;clear: none;padding:2px 0 0 2px;display:inline-block;">
					<div class="ellipsis">
					
					</div>
				</div>
				-->
				<div class="clear"></div>
			</li>
		</c:if>
		</c:forEach>	
		</ul>					
	</div>
	<!--//blockRelated End-->									
</c:if>
</div>
</c:otherwise>
</c:choose>