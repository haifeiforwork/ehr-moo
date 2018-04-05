<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%@ page contentType="text/html; charset=UTF-8"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.rss.channel.header" /> 
<c:set var="preList"    value="ui.support.rss.channel.list" />
<c:set var="preDetail"  value="ui.support.rss.channel.detail" />
<c:set var="preButton"  value="ui.support.rss.button" /> 
<c:set var="preMessage" value="ui.support.rss.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<%-- 메시지 관련 Prefix 선언 End --%>



<script type="text/javascript" language="javascript">

(function($) {
		
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		$jq('#sendMaildBtn').click(function(event) { 
			iKEP.sendMailPop();	
		});
		
		$jq('#sendMaildBtn2').click(function(event) { 

			var url = iKEP.getWebAppPath() + "/lightpack/board/boardItem/readBoardItemView.do?itemId=BITEM200000";

			var title = $jq('#titleDiv').html();
			var content = "<a href='"+url+"'>" + $jq('#titleDiv').html() + "</a>";
			
			iKEP.sendMailPop("", "", title, content, "", "");	
		});
		
		$jq('#sendMaildBtn3').click(function(event) { 
			
			var nameList = ['유승목'];
			var emailList = ['handul32@hanmail.net'];
			var title = $jq('#titleDiv').html();
			var content = $jq('#contentDiv').html();
			var fileIdList = ['111','222'];
			var fileNameList = ['111.txt','222.txt'];
			
			iKEP.sendMailPop(nameList, emailList, title, content, fileIdList, fileNameList);	
		});
		
	});
	
})(jQuery);  

	
</script>

<br/><br/>

<a class="button" href="#" >
	<span name="sendMailBtn" id="sendMaildBtn"><ikep4j:message pre='ui.support.mail.detail.main' key='send' /></span>
</a>

<br/><br/>

<a class="button" href="#" >
	<span name="sendMailBtn2" id="sendMaildBtn2"><ikep4j:message pre='ui.support.mail.detail.main' key='send' /> (Attach Link)</span>
</a>

<br/><br/>

<a class="button" href="#" >
	<span name="sendMailBtn3" id="sendMaildBtn3"><ikep4j:message pre='ui.support.mail.detail.main' key='send' /> (Attach Content)</span>
</a>

<br/><br/>
<br/><br/>





<!--blockListTable Start-->
<!--blockListTable Start-->
<div class="blockTableRead"  >
	<div class="blockTableRead_t">
		
		<p id="titleDiv"> 
		 
		<김연아-아사다 내달 모스크바서 만난다>
		</p> 
		 
		<div class="summaryViewInfo"> 
			<span class="summaryViewInfo_name"> 
				
					  
					
						<a href="#a">
							구매팀 
							사용자1 
							사원II 
						</a>   
					 
				  		
			</span> 
			<img src="/ikep4-webapp/base/images/theme/theme01/basic/bar_pageNum.gif" alt="" /> 
			<span class="blockCommentInfo_name">
				 
					<span>2011-00-28</span> 
				 
		    </span>
			<img src="/ikep4-webapp/base/images/theme/theme01/basic/bar_pageNum.gif" alt="" />
			
				<span>게시물 조회수 <strong>1</strong></span>  
			  
			<img src="/ikep4-webapp/base/images/theme/theme01/basic/bar_pageNum.gif" alt="" /> 
			
				<span id="recommendCount">게시물 추천수 <strong>0</strong></span>
			 
			<img src="/ikep4-webapp/base/images/theme/theme01/basic/bar_pageNum.gif" alt="" />
			<span>
			게시물기간 
			2011-00-01 ~ 
			2011-00-31
			</span>			 
		</div>
		
			<div class="recommend">
				<a id="updateRecommendCountButton" class="button_rec" href="#a"><span><img src="/ikep4-webapp/base/images/icon/ic_recommend.gif" alt="" />추천</span></a>	
			</div> 	
		 
		
	</div> 
	<div class="blockTableRead_c">
		
		<p id="contentDiv">4월24일 개막 세계선수권대회서 1년 만에 대결
 
(서울=연합뉴스) 고동욱 기자 = 다소 긴장감이 떨어진 채로 진행된 올 시즌 피겨스케이팅이 마지막 대회에서 '흥행 카드'가 성사됐다.
 
   '피겨 여왕' 김연아(21&middot;고려대)와 일본의 간판스타 아사다 마오(21)가 내달 24일부터 러시아 모스크바에서 열리는 2011 국제빙상경기연맹(ISU) 세계선수권대회에서 1년 만에 만나 대결하게 된 것이다.
 
   2008년부터 2010년까지 세 차례 세계선수권대회의 정상에 번갈아 오른 김연아와 아사다는 지난해 밴쿠버 동계올림픽에서도 서로 최고 점수를 따며 금&middot;은메달을 나눠 가졌다.
 
   해가 갈수록 완성도 높은 연기를 펼친 두 선수의 대결은 지난해 정점을 찍었지만, 올 시즌 김연아가 그랑프리 시리즈에 불참하면서 여자 피겨 판도는 한순간에 김이 빠졌다.
 
   일찌감치 2014년 소치 올림픽 도전을 목표로 내건 아사다는 장기인 트리플 악셀의 균형이 무너지면서 올 시즌 한 차례도 국제 대회에서 우승하지 못했다.
 
   어린 선수들도 위협적인 실력을 보여주지 못해 다소 실망스럽게 진행된 올 시즌 마지막 무대인 세계선수권대회는 그래서 큰 기대를 모았다.
 
   지난해 대회 이후 1년 만에 김연아와 아사다가 동시에 출전하는 만큼 수준 높은 경기를 기대해볼 만했다.
 
   김연아는 지난해 10월부터 피터 오피가드(미국) 코치와 함께 세계선수권대회에 초점을 맞추고 '지젤'과 '오마주 투 코리아' 등 새 프로그램을 다듬어 왔다.
 
   아사다 역시 지난해 12월 일본선수권대회와 올해 2월 4대륙 선수권대회에서 연달아 준우승하며 어느 정도 자신감을 찾았고, 이번 대회에서 2연패에 도전할 예정이었다.
 
   둘의 대결은 자칫 이뤄지지 못할 뻔했다.
 
   지난 11월 일본을 덮친 대지진으로 원래 21일부터 도쿄에서 열릴 예정이었던 대회가 무산됐기 때문이다.
 
   다행히 모스크바에서 개최하는 쪽으로 결론이 났지만, 최고의 경기를 보여줄 수 있을지는 미지수다.
 
   물거품이 된 도쿄 대회에 맞춰 놓았던 신체 리듬과 긴장감이 풀린 상황에서 한 달 동안 다시 끌어올리기는 쉽지 않다.
 
   김연아는 &quot;모든 선수는 3월 도쿄 대회에 맞춰서 컨디션 조절을 했을 것&quot;이라며 &quot;예정된 일정에서 조금이라도 벗어나면 선수에게 심리적으로 큰 타격이 있다&quot;고 설명한 바 있다.
 
   최고의 컨디션을 유지하기 어렵더라도 두 선수 모두 좋은 성적을 내고자 최선을 다할 전망이다.
 
   김연아로서는 밴쿠버 동계올림픽에서의 감동을 이어가면서 5월부터 본격적으로 시작할 평창의 동계올림픽 유치 활동에 좋은 이미지를 만들어 두는 게 중요하다.
 
   아사다는 이번 대회를 통해 대지진으로 실의에 빠진 일본 국민에게 희망을 전하겠다는 각오로 훈련하고 있다.
 
   sncwook@yna.co.kr
 
</p>
		 
		<div class="tableTag"><img src="/ikep4-webapp/base/images/theme/theme01/basic/ic_tag.gif" alt="태그" /> 
			<a href="#a">아이패드</a>, <a href="#a">애플</a>, <a href="#a">아이폰</a>, <a href="#a">태블릿</a>, <a href="#a">PC</a>
		</div>						
	</div>  
</div>
<!--//blockListTable End-->	



 


	