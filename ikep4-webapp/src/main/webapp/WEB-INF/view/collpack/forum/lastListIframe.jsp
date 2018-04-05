<%--팝업 템플릿으로 적용하기 위해 jsp분리 --%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript">
//<![CDATA[

function viewPopUpProfile(targetUserId){
	var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />" + "?targetUserId=" + targetUserId;
	iKEP.popupOpen( pageURL , {width:800, height:280 , callback:function(result) {} });
}


var pageIndex = 1;		//페이징 수
function listMore(url, totalCount, param){
	
	//페이지수만큼 불렀음 호출 더이상 안함.
	var viewCount = $jq('.lineRow').size();
	
	if(totalCount <= viewCount){
		return;	
	}
	
	if(param && param.pageIndex){
		pageIndex = param.pageIndex;
	} else {
		pageIndex++;
	}
	
	ajaxListAction(url, totalCount, param);
}	



function ajaxListAction(url, totalCount, param){
	
	param = $jq.extend(param, {pageIndex:pageIndex,docIframe:'${param.docIframe}'});
	
	$jq.ajax({    
		url : url,     
		data : param,     
		type : "post",  
		dataType : "html",
		success : function(result) {   
			
			var frame = $jq("#itemFrame"); 
			
			frame.append(result); 
			var viewCount = $jq('.lineRow').size();
			
			if($jq("#itemFrame").data('totalCount')){
				$jq("#moreBtn").show();
			}
			
			if(totalCount <= viewCount){
				$jq('#moreText').text('<ikep4j:message  key='message.collpack.forum.notMore'/>');
			}

			iKEP.iFrameContentResize();
			
		},
		error : function(event, request, settings){
		 alert("error");
		}
	}); 
	
}



//참가자 리스트
var partyPageindex = 1;
function partyList(discussionId, totalCount){
	
	var viewCount = $jq('#partyFrame_'+discussionId+' li').size();
	
	if(totalCount <= viewCount){
		return;	
	}
	partyPageindex++;
	
	$jq.ajax({    
		url : "participantListMore.do",     
		data : {discussionId:discussionId, pageIndex:partyPageindex},     
		type : "post",  
		dataType : "html",
		success : function(result) {   

    		if( partyPageindex == 1){
    			$jq('#partyFrame_'+discussionId).html(result);
			} else {
				$jq('#partyFrame_'+discussionId).append(result);
    		}
			
			viewCount = $jq('#partyFrame_'+discussionId+' li').size();

			if(totalCount <= viewCount){
				$jq('#partyMoreBtn_'+discussionId).hide();
			}

			var partCount =  $jq('#partyFrame_'+discussionId+' li:last').attr("class").replace("participant_","");

				if(partCount > 5 && partyPageindex == 1){
					$jq("#partyMoreBtn_"+discussionId).show();
				}
			
		},
		error : function(event, request, settings){
		 alert("error");
		}
	}); 
}


function goViewItem(itemId){

	location.href="getView.do?itemId="+itemId+"&pageIndex="+pageIndex+"#a";
 }

//]]>
</script>

<c:import url="/WEB-INF/view/collpack/forum/lastList.jsp" charEncoding="UTF-8" />