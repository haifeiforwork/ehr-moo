<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  		value="message.collpack.kms.admin.winner.regist" /> 
<c:set var="preButton"  		value="message.collpack.kms.admin.winner.button" />
<c:set var="preList"  			value="message.collpack.kms.admin.winner.list" />
<c:set var="preMessage"  		value="message.collpack.kms.admin.winner.message" />
<c:set var="preCondition"    	value="message.collpack.kms.admin.winner.condition" />



 
<%-- 메시지 관련 Prefix 선언 End --%>  

<c:set var="user" value="${sessionScope['ikep.user']}" /> 
 
<script type="text/javascript" src="<c:url value='/base/js/zeroclipboard/ZeroClipboard.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/jquery/jquery.layout-latest.js'/>"></script>
<script type="text/javascript">
<!--   
var bbsIframe;  	 // 부모 iframe
var bbsIsIframe = 0; // iframe 존재 여부
var isLayout; // 레이아웃 보기 여부
var bbsLayout = null;
var layoutType = "n"; // n:없음, v:가로보기, h:세로보기


(function($){	 
	/* window risize 시 Contaner 높이 조절 */
	resizeContanerHeight = function(){
		var docHeight = 0;
		var adjustHeight = 20;		
		var $lefMenu;
		var $Container	= $('#container');
		
		if(isLayout){
			// iframe 안에서 호출된 경우
			if(bbsIsIframe>0){
				docHeight = $(parent).height();
				
				$lefMenu = $("#leftMenu", parent.document);
				var leftMenuPostion = $lefMenu.offset();
				if($lefMenu.length > 0) {
					$lefMenu.height(docHeight - leftMenuPostion.top);
				}
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( docHeight - $Container.offset().top - adjustHeight );
				// 부모 iframe 높이 조절 - 부모 윈도우 높이로 조절
				bbsIframe.height(docHeight-17);				
			}else{
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( $(window).height() - $Container.offset().top - adjustHeight );
			}	
		}
		
		if(bbsLayout != null){
			bbsLayout.resizeAll();	
		} 
	}
	
	/* Contaner & iframe 높이 조절 */
	setContanerHeight = function() {
		var docHeight = 0;
		var adjustHeight = 20;
		var $lefMenu;
		var $Container	= $('#container');
		
		// layout 존재하므로 layout destroy 함수 호출시
		if(isLayout){
			// iframe 안에서 호출된 경우
			if(bbsIsIframe>0){
				docHeight = $(parent).height();
				$lefMenu = $("#leftMenu", parent.document);
				var leftMenuPostion = $lefMenu.offset();
				if($lefMenu.length > 0) {
					$lefMenu.height(docHeight - leftMenuPostion.top)
					.css({overflowY:"auto",overflowX:"hidden"});
				}
				
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( docHeight - $Container.offset().top - adjustHeight );
				// 부모 iframe 높이 조절 - 부모 윈도우 높이로 조절
				bbsIframe.height(docHeight-19);
			}else{
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( $(window).height() - $Container.offset().top - adjustHeight );
			}			
			
		}else{ // layout 없으므로 layout 함수 호출시
			// iframe 안에서 호출된 경우
			if(bbsIsIframe>0){
				$lefMenu = $("#leftMenu", parent.document);
				$lefMenu.css({overflowY:"",overflowX:""});
				bbsIframe.height($(document).height());
			}
		
		}
		
		if(bbsLayout != null){
			bbsLayout.resizeAll();	
		}
		
	}
	
	
	$(document).ready(function() { 
		
		
		/* iframe 구성여부 확인 */
		bbsIframe = $(parent.document).find("iframe[name=contentIframe]");
		bbsIsIframe = bbsIframe.length;
		
		/* 기본 layout 설정 여부 */ 
		isLayout = false;	
		layoutType = "n";		
		
		/* 윈도우 resize 이벤트 */
		$(window).bind("resize", resizeContanerHeight);		
		
		$("#displayButton").click(function() { 
			
			$.post('<c:url value="/collpack/kms/admin/saveCompulsionTime.do"/>', $("#searchUserPermForm").serialize()) 
		    .success(function(data) {
		    	alert('저장되었습니다.');
		    })
		    .error(function(event, request, settings) { 
		    	alert('error');
		    	
		    });  
			 
		});
		
	});
	
	
	
})(jQuery);
//-->
</script>

<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="searchUserPermForm" method="post" onsubmit="return false" action="<c:url value='/collpack/kms/admin/saveCompulsionTime.do'/>">
<!--mainContents Start-->	 
<!--pageTitle Start-->
	
                <!--tableTop Start-->
				<div class="tableTop">
					<h2>조회 시간 관리</h2>
					<div class="tableTop_bgR"></div>
				</div>
				<!--//tableTop End-->		
				<div class="blockSearch">
                	<div class="corner_RoundBox03">
						<table summary="tableSearch">
						<tbody>
							<tr>
								<th scope="row" width="15%">조회 시간 선택</th>
		                         <td width="85%">	
		                         	<input type="checkbox" name="mon" value="1" <c:if test="${compulsionTime.mon eq '1'}">checked</c:if> style="vertical-align: middle;"/>월
		                         	<input type="checkbox" name="tue" value="1" <c:if test="${compulsionTime.tue eq '1'}">checked</c:if> style="vertical-align: middle;"/>화
		                         	<input type="checkbox" name="wed" value="1" <c:if test="${compulsionTime.wed eq '1'}">checked</c:if> style="vertical-align: middle;"/>수
		                         	<input type="checkbox" name="thu" value="1" <c:if test="${compulsionTime.thu eq '1'}">checked</c:if> style="vertical-align: middle;"/>목
		                         	<input type="checkbox" name="fri" value="1" <c:if test="${compulsionTime.fri eq '1'}">checked</c:if> style="vertical-align: middle;"/>금
		                         	&nbsp;
		                         	<select id="startHour" name="startHour">
										<option value="00" <c:if test="${compulsionTime.startHour eq '00'}">selected="selected"</c:if>>00</option>
										<option value="01" <c:if test="${compulsionTime.startHour eq '01'}">selected="selected"</c:if>>01</option>
										<option value="02" <c:if test="${compulsionTime.startHour eq '02'}">selected="selected"</c:if>>02</option>
										<option value="03" <c:if test="${compulsionTime.startHour eq '03'}">selected="selected"</c:if>>03</option>
										<option value="04" <c:if test="${compulsionTime.startHour eq '04'}">selected="selected"</c:if>>04</option>
										<option value="05" <c:if test="${compulsionTime.startHour eq '05'}">selected="selected"</c:if>>05</option>
										<option value="06" <c:if test="${compulsionTime.startHour eq '06'}">selected="selected"</c:if>>06</option>
										<option value="07" <c:if test="${compulsionTime.startHour eq '07'}">selected="selected"</c:if>>07</option>
										<option value="08" <c:if test="${compulsionTime.startHour eq '08'}">selected="selected"</c:if>>08</option>
										<option value="09" <c:if test="${compulsionTime.startHour eq '09'}">selected="selected"</c:if>>09</option>
										<option value="10" <c:if test="${compulsionTime.startHour eq '10'}">selected="selected"</c:if>>10</option>
										<option value="11" <c:if test="${compulsionTime.startHour eq '11'}">selected="selected"</c:if>>11</option>
										<option value="12" <c:if test="${compulsionTime.startHour eq '12'}">selected="selected"</c:if>>12</option>
										<option value="13" <c:if test="${compulsionTime.startHour eq '13'}">selected="selected"</c:if>>13</option>
										<option value="14" <c:if test="${compulsionTime.startHour eq '14'}">selected="selected"</c:if>>14</option>
										<option value="15" <c:if test="${compulsionTime.startHour eq '15'}">selected="selected"</c:if>>15</option>
										<option value="16" <c:if test="${compulsionTime.startHour eq '16'}">selected="selected"</c:if>>16</option>
										<option value="17" <c:if test="${compulsionTime.startHour eq '17'}">selected="selected"</c:if>>17</option>
										<option value="18" <c:if test="${compulsionTime.startHour eq '18'}">selected="selected"</c:if>>18</option>
										<option value="19" <c:if test="${compulsionTime.startHour eq '19'}">selected="selected"</c:if>>19</option>
										<option value="20" <c:if test="${compulsionTime.startHour eq '20'}">selected="selected"</c:if>>20</option>
										<option value="21" <c:if test="${compulsionTime.startHour eq '21'}">selected="selected"</c:if>>21</option>
										<option value="22" <c:if test="${compulsionTime.startHour eq '22'}">selected="selected"</c:if>>22</option>
										<option value="23" <c:if test="${compulsionTime.startHour eq '23'}">selected="selected"</c:if>>23</option>
									</select> 시
									<select id="startMin" name="startMin">
										<option value="00" <c:if test="${compulsionTime.startMin eq '00'}">selected="selected"</c:if>>00</option>
										<option value="05" <c:if test="${compulsionTime.startMin eq '05'}">selected="selected"</c:if>>05</option>
										<option value="10" <c:if test="${compulsionTime.startMin eq '10'}">selected="selected"</c:if>>10</option>
										<option value="15" <c:if test="${compulsionTime.startMin eq '15'}">selected="selected"</c:if>>15</option>
										<option value="20" <c:if test="${compulsionTime.startMin eq '20'}">selected="selected"</c:if>>20</option>
										<option value="25" <c:if test="${compulsionTime.startMin eq '25'}">selected="selected"</c:if>>25</option>
										<option value="30" <c:if test="${compulsionTime.startMin eq '30'}">selected="selected"</c:if>>30</option>
										<option value="35" <c:if test="${compulsionTime.startMin eq '35'}">selected="selected"</c:if>>35</option>
										<option value="40" <c:if test="${compulsionTime.startMin eq '40'}">selected="selected"</c:if>>40</option>
										<option value="45" <c:if test="${compulsionTime.startMin eq '45'}">selected="selected"</c:if>>45</option>
										<option value="50" <c:if test="${compulsionTime.startMin eq '50'}">selected="selected"</c:if>>50</option>
										<option value="55" <c:if test="${compulsionTime.startMin eq '55'}">selected="selected"</c:if>>55</option>
									</select> 분	~
						<select id="endHour" name="endHour">
										<option value="00" <c:if test="${compulsionTime.endHour eq '00'}">selected="selected"</c:if>>00</option>
										<option value="01" <c:if test="${compulsionTime.endHour eq '01'}">selected="selected"</c:if>>01</option>
										<option value="02" <c:if test="${compulsionTime.endHour eq '02'}">selected="selected"</c:if>>02</option>
										<option value="03" <c:if test="${compulsionTime.endHour eq '03'}">selected="selected"</c:if>>03</option>
										<option value="04" <c:if test="${compulsionTime.endHour eq '04'}">selected="selected"</c:if>>04</option>
										<option value="05" <c:if test="${compulsionTime.endHour eq '05'}">selected="selected"</c:if>>05</option>
										<option value="06" <c:if test="${compulsionTime.endHour eq '06'}">selected="selected"</c:if>>06</option>
										<option value="07" <c:if test="${compulsionTime.endHour eq '07'}">selected="selected"</c:if>>07</option>
										<option value="08" <c:if test="${compulsionTime.endHour eq '08'}">selected="selected"</c:if>>08</option>
										<option value="09" <c:if test="${compulsionTime.endHour eq '09'}">selected="selected"</c:if>>09</option>
										<option value="10" <c:if test="${compulsionTime.endHour eq '10'}">selected="selected"</c:if>>10</option>
										<option value="11" <c:if test="${compulsionTime.endHour eq '11'}">selected="selected"</c:if>>11</option>
										<option value="12" <c:if test="${compulsionTime.endHour eq '12'}">selected="selected"</c:if>>12</option>
										<option value="13" <c:if test="${compulsionTime.endHour eq '13'}">selected="selected"</c:if>>13</option>
										<option value="14" <c:if test="${compulsionTime.endHour eq '14'}">selected="selected"</c:if>>14</option>
										<option value="15" <c:if test="${compulsionTime.endHour eq '15'}">selected="selected"</c:if>>15</option>
										<option value="16" <c:if test="${compulsionTime.endHour eq '16'}">selected="selected"</c:if>>16</option>
										<option value="17" <c:if test="${compulsionTime.endHour eq '17'}">selected="selected"</c:if>>17</option>
										<option value="18" <c:if test="${compulsionTime.endHour eq '18'}">selected="selected"</c:if>>18</option>
										<option value="19" <c:if test="${compulsionTime.endHour eq '19'}">selected="selected"</c:if>>19</option>
										<option value="20" <c:if test="${compulsionTime.endHour eq '20'}">selected="selected"</c:if>>20</option>
										<option value="21" <c:if test="${compulsionTime.endHour eq '21'}">selected="selected"</c:if>>21</option>
										<option value="22" <c:if test="${compulsionTime.endHour eq '22'}">selected="selected"</c:if>>22</option>
										<option value="23" <c:if test="${compulsionTime.endHour eq '23'}">selected="selected"</c:if>>23</option>
										<option value="24" <c:if test="${compulsionTime.endHour eq '24'}">selected="selected"</c:if>>24</option>
									</select> 시
									<select id="endMin" name="endMin">
										<option value="00" <c:if test="${compulsionTime.endMin eq '00'}">selected="selected"</c:if>>00</option>
										<option value="05" <c:if test="${compulsionTime.endMin eq '05'}">selected="selected"</c:if>>05</option>
										<option value="10" <c:if test="${compulsionTime.endMin eq '10'}">selected="selected"</c:if>>10</option>
										<option value="15" <c:if test="${compulsionTime.endMin eq '15'}">selected="selected"</c:if>>15</option>
										<option value="20" <c:if test="${compulsionTime.endMin eq '20'}">selected="selected"</c:if>>20</option>
										<option value="25" <c:if test="${compulsionTime.endMin eq '25'}">selected="selected"</c:if>>25</option>
										<option value="30" <c:if test="${compulsionTime.endMin eq '30'}">selected="selected"</c:if>>30</option>
										<option value="35" <c:if test="${compulsionTime.endMin eq '35'}">selected="selected"</c:if>>35</option>
										<option value="40" <c:if test="${compulsionTime.endMin eq '40'}">selected="selected"</c:if>>40</option>
										<option value="45" <c:if test="${compulsionTime.endMin eq '45'}">selected="selected"</c:if>>45</option>
										<option value="50" <c:if test="${compulsionTime.endMin eq '50'}">selected="selected"</c:if>>50</option>
										<option value="55" <c:if test="${compulsionTime.endMin eq '55'}">selected="selected"</c:if>>55</option>
									</select> 분
						</select>
		                         
		                         </td>				
		                     </tr>														
		                 </tbody>
		             	</table>
		             	<div class="displayBtn"><a id="displayButton" name="displayButton" href="#a"><span>Display</span></a></div> 
		             </div>
				</div>     	
				   
             </div>	
             
				<!--//splitterBox End-->
			</div>
				<!--//mainContents End-->
			<div class="clear"></div>
		</div>

</form>