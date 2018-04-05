<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="sessionPortal" value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preTap"    	value="ui.socialpack.microblogging.tap" />
<c:set var="preButton"  value="ui.socialpack.microblogging.button" />
<c:set var="preLink"  	value="ui.socialpack.microblogging.link" /> 
<c:set var="preLabel" 	value="ui.socialpack.microblogging.label" />
<c:set var="preMessage"	value="ui.socialpack.microblogging.message" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/units/socialpack/microblogging.js"/>"></script>
<script type="text/javascript">
<!--	
	// 현재 탭과 그 탭안에서의 항목
	var nowDiv = "tabs-1";

// 스크롤로 자동조회 진행 여부
var windowScrollFlag = false;

(function($) {
	var dropdownTabs;
	$(document).ready(function() {
	
		// 탭 안의 각종 리스트를 클릭했을 때
		$("ul[name=microblog_list] > li").live("click", function(event) {
			//클릭한 객체의 이름에 따라 작동 구분.
			var tg = $(event.target).attr("name");  
			if(tg=="hashTag")
			{
				// 해시테그로 검색을 한다.
				//getHashTag(event.target);				
			}
			else if(tg== "addonLink" || tg == "strongLink"  || tg == "strongButtons")
			{
				// 원래 스크립트만 적용된다.
			}
			else
			{
				//alert("else");
				// 우측 상세보기 레이어의 위치를 정하고 내용을 넣게 된다. (트윗 상세내역과 사용자 정보의 경우)
			    var mblogId = $(this).attr("id").replace('timelineMblogId_','');
				
				//$(this).siblings().removeClass("selected").end().addClass("selected");
			    
			    // mblogId가 없으면 아무것도 하지 않는다.
			    if('' == mblogId){
			    	
			    }//  userInfo 정보 조회 일 때는 블로그 홈을 보여준다.
			    else if(tg =="userInfo"){					
					var userId = $(event.target).attr("id");
					//alert(userId);
					document.location.href = "<c:url value='/socialpack/microblogging/privateHome.do?ownerId='/>"+userId;
				} // 맨션 일 때는 상세 페이지에 사용자상세정보 페이지를 보여준다.
				else if(tg =="mention"){					
					var objectId = $(event.target).attr("id");
					
					// 아이디 타입별로 다르게 보여준다.
					getObjectIdInfo(objectId, $div);
				}else{
					detailsForTweetPopup(mblogId);
				}
			}
		});

		$("ul[name=microblog_list] > li").live("mouseover", 
			  function () {			    
			    $(this).addClass("hover");
				//$("span.microblog_icon", this).show();
				//$("div.ic_micro_ar", this).show();
			  }
			 		
		);
		
		$("ul[name=microblog_list] > li").live("mouseout", 
			function () {
				$(this).removeClass("hover");
				//$("span.microblog_icon", this).hide();
				//$("div.ic_micro_ar", this).hide();
		  	}			
		);

		var firstDraw =  true;
		
		//iKEP.debug("firstDraw1:"+firstDraw);
			//최초에 타임라인 불러오기
			getTimeline('pre');

		// 스크롤이 맨 하단에 위치했을 때 해당하는 탭의 다음 내용을 자동조회한다.
		$(window).scroll(function(){
			if  ($(window).scrollTop() == $(document).height() - $(window).height())
			{
				if(window.scrollFlag) return;
				window.scrollFlag = true; 
				
				//alert(${maxFeedCount});
				//alert($jq("li", "#"+nowDiv).length);

				//Tab 별로 maxFeedCount보다 데이터가 더 많으면 더이상 조회하지 않게 한다. 예외를 할 탭은 예외 DIV 설정 로직을 추가한다.
				var overflow  = false;
				var maxFeedCount = ${maxFeedCount};
				if(maxFeedCount <= $jq("li", "#"+nowDiv).length){
					overflow = true;
					alert("<ikep4j:message pre='${preMessage}' key='feedtoomuch' />");
				}
									
				//iKEP.debug("firstDraw2:"+firstDraw);
				if(!firstDraw && !overflow){
					//alert(nowDiv);
					
					getTimeline('pre');

				}
				window.scrollFlag = false; 
			}
			firstDraw =  false;
			//iKEP.debug("firstDraw3:"+firstDraw);
		});

	});
	// $(document).ready END
	
		
	// 타임라인 조회 (회해온 데이터를 앞/뒤에 붙일건인지)
	getTimeline = function( standardType){
		//alert(registerOnly);
		//alert(standardType);
		//alert(nowDiv);

		// 다음 리스트 조회시 기준이 되는 값들. 맨마지막이나 맨처음의 id.
		var standardMblogId = '';
		var standardAddonCode = '';
		
		try{
			if("pre" == standardType){
				standardMblogId = $jq("li", "#"+nowDiv).last().attr("id").replace('timelineMblogId_','');
			}else{
				standardMblogId = $jq("li", "#"+nowDiv).first().attr("id").replace('timelineMblogId_','');
			}
		}catch(e){}
		
		//alert('${workspaceId}');
		//alert(standardMblogId);
		//alert('${ownerId}');
		
		// 로딩 이미지 표현할 Div 영역 설정
		addTempLoadingDiv();
		
		$jq.get("<c:url value='/socialpack/microblogging/followingTimelineList.do'/>", 
				// 기준 userId					   ,조회해온 데이터를 앞/뒤에 붙일건지  , 조회기준 MblogId	
				{'ownerId':'${userId}', 'standardType':standardType, 'standardMblogId':standardMblogId}, 
				function(data) {
					//alert(data);
					//alert($jq("#"+nowDiv).html());
					if("pre" == standardType){
						$jq("ul", "#"+nowDiv).append(data);
					}else{
						$jq("ul", "#"+nowDiv).prepend(data);
					}
					
					// 로딩이미지 표시한 임시영역 제거
					removeTempLoadingDiv();
		});
	};


})(jQuery);
//-->
</script>

<!--microblog_layer Start-->
<div class="microblog_layer none" style="background-color:white;" id="microblog_layer">
	<div class="microblog_layer_close">
		<a href="#a"><img src="<c:url value='/base/images/common/btn_close.gif'/>" alt="close" id="close_img"/></a>
	</div>
	<div class="microblog_layer_con" id="microblog_layer_content"></div>
</div>
<!--//microblog_layer End-->
						
<div id="tabs-1">
	<ul class="microblog_li" name="microblog_list"></ul>	
</div>