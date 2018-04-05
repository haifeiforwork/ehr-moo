<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>  



<script type="text/javascript">
var fileController = "";		//파일등록폼
(function($) {


	 saveButton =function(surveyId) {			


		$.post("<c:url value='/servicepack/survey/modifyEndDate.do' />",  $("#saveForm").serialize())
		.success(function(result) {
			alert("저장 완료되었습니다.");
			self.close();
			window.opener.location.reload();
		})
		.error(function(){
			alert("저장 실패했습니다. 관리자에게 문의하세요.");

		});



	};

	$(document).ready( function() {
				 
		//달력
		$("input[name=newEndDate]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });

	
	});
	
	
	
})(jQuery);




</script>


<!--popup Start-->
<form id="saveForm" method="post" action="<c:url value='/servicepack/survey/modifyEndDate.do' />">

<div id="popup_2">

	<!--popup_title Start-->
	<div id="popup_title_2">
		<h1>설문종료일 변경</h1>
		<a href="javascript:iKEP.closePop()"></a>
		<div class="popup_bgTitle_l"></div>
	</div>
	<!--//popup_title End-->		
	
	<!--popup_contents Start-->
	<div id="popup_contents_2">
	
		<!--blockDetail Start-->
	    <div class="blockDetail">
	        <table summary="new group">
	            <colgroup>
	                <col width="30%"/>
	                <col width="70%"/>
	            </colgroup>
	            <tbody>
	                <tr>
	                    <th scope="row">설문 종료일</th>
	                    <td><%-- <input type="text" name="endDate" id="endDate" size="10" value="${endDate}"/> --%>
	                    
	                    <input id="newEndDate" name="newEndDate" title="상담일" value="${endDate}"  class="inputbox datepicker" type="text" value="<ikep4j:timezone date='${endDate}'/>" size="15" /> 
                        <img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>   
	                    <input name="surveyId" type="hidden" value="${surveyId}" />
	                    </td>
	                </tr>        
	            </tbody>
	        </table>
	    </div>

	    <!--//blockDetail End-->	
	    <div class="blockButton"> 			
			<ul>	            
			    <li><a class="button" href="javascript:saveButton('${surveyId}')" id="saveBtn" name="saveBtn"><span>저장</span></a></li>
	            <li><a class="button" href="javascript:iKEP.closePop()" id="cancelBtn" name="cancelBtn"><span>닫기</span></a></li>&nbsp;
	        </ul>
		</div>		
		
		<!--//blockButton End-->
		<div class="blockBlank_5px"></div>	
	
	<!--//popup_contents End-->
</div>
<!--//popup End-->
</form>