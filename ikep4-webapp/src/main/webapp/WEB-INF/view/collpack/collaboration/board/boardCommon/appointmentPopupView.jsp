<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>  
<script type="text/javascript">
<!--   

	$jq(document).ready(function() { 
		iKEP.iFrameContentResize();
		
		$jq("#cancelBtn").click(function() {
			iKEP.closePop();
		});
		
		$jq("#appointmentDay").datepicker().next().eq(0).click(function() { 
			$jq(this).prev().eq(0).datepicker("show"); 
		});
		
		$jq("#submitBtn").click(function() {
			$jq.ajax({
				url : "<c:url value='/collpack/collaboration/board/boardCommon/consultationRequest.do' />",
				data : $jq("#appointmentForm").serialize(),
				type : "post",
				dataType : "html",
				success : function(result) {
					if(result == 'success') {
						alert("상담신청이 완료되었습니다.");
						iKEP.closePop();
					} else {
						alert('error');
					}
				},
				error : function() {alert('error');}
			});
		});
	});   
	
//-->
</script>

<!--popup Start-->
		<div id="popup">
		
				<!--popup_title Start-->
				<div id="popup_title_2">
                    <div class="popup_bgTitle_l"></div>
					<h1>상담 예약</h1>
					<a href="javascript:iKEP.closePop()"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
				</div>
				<!--//popup_title End-->
				
				
				<!--popup_contents Start-->
				<div id="popup_contents">
				
							
					<!--blockDetail Start-->
					<div class="blockDetail">
					
						<form id="appointmentForm" name="appointmentForm" method="post" action="" >
							
							<table>
							<caption></caption>
								<colgroup>
									<col width="20%"/>
									<col width="80%"/>
								</colgroup>
					
							<tbody>
							
								<tr>
									<th>상담일</th>
									<td>
										<input id="appointmentDay" name="appointmentDay" type="text" class="inputbox datepicker" value="" size="10" title="<ikep4j:message pre='${preDetail}' key='date' />" readonly="readonly" /> 
										<img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='calendar' />" />
									</td>
								</tr>
								<tr>
									<th>상담시간1</th>
									<td>
										<select  name="appointmentHour1">     
											<option value="" >선택</option>       
											<option value="00" <c:if test="${'00' eq nowHour}">selected="selected"</c:if>>00</option>                   		
									        <option value="01" <c:if test="${'01' eq nowHour}">selected="selected"</c:if>>01</option>
									        <option value="02" <c:if test="${'02' eq nowHour}">selected="selected"</c:if>>02</option>
									        <option value="03" <c:if test="${'03' eq nowHour}">selected="selected"</c:if>>03</option>
									        <option value="04" <c:if test="${'04' eq nowHour}">selected="selected"</c:if>>04</option>
									        <option value="05" <c:if test="${'05' eq nowHour}">selected="selected"</c:if>>05</option>
									        <option value="06" <c:if test="${'06' eq nowHour}">selected="selected"</c:if>>06</option>
									        <option value="07" <c:if test="${'07' eq nowHour}">selected="selected"</c:if>>07</option>
									        <option value="08" <c:if test="${'08' eq nowHour}">selected="selected"</c:if>>08</option>
									        <option value="09" <c:if test="${'09' eq nowHour}">selected="selected"</c:if>>09</option>
									        <option value="10" <c:if test="${'10' eq nowHour}">selected="selected"</c:if>>10</option>
									        <option value="11" <c:if test="${'11' eq nowHour}">selected="selected"</c:if>>11</option>
									        <option value="12" <c:if test="${'12' eq nowHour}">selected="selected"</c:if>>12</option>
									        <option value="13" <c:if test="${'13' eq nowHour}">selected="selected"</c:if>>13</option>
									        <option value="14" <c:if test="${'14' eq nowHour}">selected="selected"</c:if>>14</option>
									        <option value="15" <c:if test="${'15' eq nowHour}">selected="selected"</c:if>>15</option>
									        <option value="16" <c:if test="${'16' eq nowHour}">selected="selected"</c:if>>16</option>
									        <option value="17" <c:if test="${'17' eq nowHour}">selected="selected"</c:if>>17</option>
									        <option value="18" <c:if test="${'18' eq nowHour}">selected="selected"</c:if>>18</option>
									        <option value="19" <c:if test="${'19' eq nowHour}">selected="selected"</c:if>>19</option>
									        <option value="20" <c:if test="${'20' eq nowHour}">selected="selected"</c:if>>20</option>
									        <option value="21" <c:if test="${'21' eq nowHour}">selected="selected"</c:if>>21</option>
									        <option value="22" <c:if test="${'22' eq nowHour}">selected="selected"</c:if>>22</option>
									        <option value="23" <c:if test="${'23' eq nowHour}">selected="selected"</c:if>>23</option>
								        </select>
								        시
								        <select  name="appointmentMinute1">   
								        	<option value="" >선택</option>         
											<option value="00" >00</option>                   		
									        <option value="05" >05</option>
									        <option value="10" >10</option>
									        <option value="15" >15</option>
									        <option value="20" >20</option>
									        <option value="25" >25</option>
									        <option value="30" >30</option>
									        <option value="35" >35</option>
									        <option value="40" >40</option>
									        <option value="45" >45</option>
									        <option value="50" >50</option>
									        <option value="55" >55</option>
								        </select>
								        분
									</td>
								</tr>	
								<tr>
									<th>상담시간2</th>
									<td>
										<select  name="appointmentHour2">     
											<option value="" >선택</option>       
											<option value="00" >00</option>                   		
											<option value="01" >01</option>
											<option value="02" >02</option>
											<option value="03" >03</option>
											<option value="04" >04</option>
											<option value="05" >05</option>
											<option value="06" >06</option>
											<option value="07" >07</option>
											<option value="08" >08</option>
											<option value="09" >09</option>
											<option value="10" >10</option>
											<option value="11" >11</option>
											<option value="12" >12</option>
											<option value="13" >13</option>
											<option value="14" >14</option>
											<option value="15" >15</option>
											<option value="16" >16</option>
											<option value="17" >17</option>
											<option value="18" >18</option>
											<option value="19" >19</option>
											<option value="20" >20</option>
											<option value="21" >21</option>
											<option value="22" >22</option>
											<option value="23" >23</option>
								        </select>
								        시
								        <select  name="appointmentMinute2">  
								        	<option value="" >선택</option>          
											<option value="00" >00</option>                   		
									        <option value="05" >05</option>
									        <option value="10" >10</option>
									        <option value="15" >15</option>
									        <option value="20" >20</option>
									        <option value="25" >25</option>
									        <option value="30" >30</option>
									        <option value="35" >35</option>
									        <option value="40" >40</option>
									        <option value="45" >45</option>
									        <option value="50" >50</option>
									        <option value="55" >55</option>
								        </select>
								        분
									</td>
								</tr>
								<tr>
									<th>상담시간3</th>
									<td>
										<select  name="appointmentHour3">       
											<option value="" >선택</option>     
											<option value="00" >00</option>                   		
											<option value="01" >01</option>
											<option value="02" >02</option>
											<option value="03" >03</option>
											<option value="04" >04</option>
											<option value="05" >05</option>
											<option value="06" >06</option>
											<option value="07" >07</option>
											<option value="08" >08</option>
											<option value="09" >09</option>
											<option value="10" >10</option>
											<option value="11" >11</option>
											<option value="12" >12</option>
											<option value="13" >13</option>
											<option value="14" >14</option>
											<option value="15" >15</option>
											<option value="16" >16</option>
											<option value="17" >17</option>
											<option value="18" >18</option>
											<option value="19" >19</option>
											<option value="20" >20</option>
											<option value="21" >21</option>
											<option value="22" >22</option>
											<option value="23" >23</option>
								        </select>
								        시
								        <select  name="appointmentMinute3">    
								        	<option value="" >선택</option>        
											<option value="00" >00</option>                   		
									        <option value="05" >05</option>
									        <option value="10" >10</option>
									        <option value="15" >15</option>
									        <option value="20" >20</option>
									        <option value="25" >25</option>
									        <option value="30" >30</option>
									        <option value="35" >35</option>
									        <option value="40" >40</option>
									        <option value="45" >45</option>
									        <option value="50" >50</option>
									        <option value="55" >55</option>
								        </select>
								        분
									</td>
								</tr>		
							</tbody>					
							
							</table>
						</form>
							
							
					</div>
					<!--//blockDetail End-->
															
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<li><a class="button" id="submitBtn" href="#a"><span>확인</span></a></li>
							<li><a class="button" id="cancelBtn" href="#a"><span>취소</span></a></li>
						</ul>
					</div>
					<!--//blockButton End-->
					
				
			       
				
				</div>
				<!--//popup_contents End-->
			 
				<!--popup_footer Start-->
				<div id="popup_footer"></div>
				<!--//popup_footer End-->
				
					
		
		</div>
		<!--//popup End-->
