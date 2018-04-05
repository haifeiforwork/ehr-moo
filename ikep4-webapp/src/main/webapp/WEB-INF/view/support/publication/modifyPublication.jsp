<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />



<script type="text/javascript">
var fileController = "";		//파일등록폼
(function($) {

	
	$(document).ready( function() {
		

		$("a.createPublication").click(function(){

			$("#publicationForm").trigger("submit"); 
			return false;
			
		});
		



	
		
		 
		new iKEP.Validator("#publicationForm", {   
		 	rules  : {
				//companyName     : {required : true},
				//name : {required : true},
				//zip1No : {required : true,digits:true,rangelength:[3,3]},
				//zip2No : {required : true,digits:true,rangelength:[3,3]},
				address : {required : true},
				count :{digits:true}
				
			},
			messages : {
				//companyName     : {direction : "top",    required : "고객사는 필수입력값입니다."},
				//name : {direction : "top", required : "고객명은 필수입력값입니다."},
				//zip1No   : {direction : "top", required : "우편번호를 입력해주세요.",digits: "숫자형식만 입력 가능합니다.",rangelength:"3자를 정확하게 입력해주세요."}, 
				//zip2No    : {direction : "top",  required: "우편번호를 입력해주세요.",digits: "숫자형식만 입력 가능합니다.",rangelength:"3자를 정확하게 입력해주세요."},
				address      : {direction : "bottom", required : "주소는 필수 입력값입니다."},
				count : {digits: "숫자형식만 입력 가능합니다."}
			},     
			
		    submitHandler : function(form) { 
			
				
		     	if(confirm("저장하시겠습니까?")) {
	
					$("body").ajaxLoadStart("button");
					form.submit();

				} 
		    	
		    	
		    	
		    }
		}); 
		
	});
	
	
	
})(jQuery);


function deletePublication(id){
	if(confirm("삭제하시겠습니까?")){
		
		location.href = "<c:url value='/support/publication/publication/deletePublication.do?id='/>"+id;
	}
}


function open_pop(){
	
	window.open( "<c:url value='/support/customer/zipcode/main.do'/>" ,"우편번호검색","resizable=yes,scrollbars=yes,width=600,height=450");
	
}

</script>




<h1 class="none">컨텐츠영역</h1>

<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>	
	    <c:choose>
		<c:when test="${publication.type eq 1 }">종이를 만드는 사람들</c:when>
		<c:when test="${publication.type eq 2 }">좋은종이</c:when>
		</c:choose></h2>
	<div class="clear"></div>
</div>
<!--//tableTop End-->

<form id="publicationForm" method="post" action="<c:url value='/support/publication/publication/modifyPublication.do'/>" >
	<input type="hidden" name="id" value="${publication.id}" />
	<div class="subTitle_2 noline">
		<h3>간행물 주소록 수정</h3>
	</div>
	<!--//subTitle_1 End-->	
             
	<!--blockDetail Start-->
             <div class="blockDetail">
		<table summary="상담이력 상세조회">
			<caption></caption>
	  			  <colgroup>
                      <col width="20%"/>
                      <col width="*%"/>
                  </colgroup>
			<tbody>
				
				<tr>
					<th scope="row">간행물 종류</th>
					<td>
						<select title="간행물 종류" name="type" id="type">	                       
	                        <option value="1" <c:if test="${publication.type eq '1'}">selected="selected"</c:if>>아이엠 무림</option>
	                        <option value="2" <c:if test="${publication.type eq '2'}">selected="selected"</c:if>>좋은종이</option>
	                    </select> 
					</td>
				</tr>

			   <tr>
					<th scope="row">그룹</th>
					<td>
						<select title="간행물 그룹" name="groupId" id="groupId">	                       
	                        <option value="1" <c:if test="${publication.groupId eq '1'}">selected="selected"</c:if>>제지/지류유통</option>
	                        <option value="2" <c:if test="${publication.groupId eq '2'}">selected="selected"</c:if>>학교/오피니언</option>
	                        <option value="3" <c:if test="${publication.groupId eq '3'}">selected="selected"</c:if>>디자인/개인</option>
	                        <option value="4" <c:if test="${publication.groupId eq '4'}">selected="selected"</c:if>>퇴직사우</option>
	                    </select> 
					</td>
				</tr>
            	<tr>
					<th scope="row">고객사</th>
					<td><input name="companyName" title="고객사" value="${publication.companyName}"  class="inputbox w100" maxlength="250" type="text" /></td>
				</tr>
				<tr>
					<th scope="row">고객명</th>
					<td><input name="name" title="고객명" value="${publication.name}"  class="inputbox w100"  maxlength="100" type="text" /></td>
				</tr>
				<tr>
                    <th scope="row">우편번호</th>
					<td>
					<input name="zip1No" title="우편번호" value="${publication.zip1No}"  class="inputbox" type="text" />
					<input name="zip2No" id="zip2No" value=""  type="hidden" />
					<%-- -
					<input name="zip2No" title="우편번호" value="${publication.zip2No}"  class="inputbox" type="text" size="3" maxlength="3" /> --%>
					<a class="button_s" onclick="javascript:open_pop();" href="#a"><span>우편번호 찾기</span></a>
					</td>				
				</tr>
                <tr>
                    <th scope="row">주소</th>
					<td><input name="address" title="주소" value="${publication.address}"  class="inputbox w100" maxlength="250" type="text" /></td>
				</tr>
				<tr>
					<th scope="row">전화번호</th>
					<td><input name="telNo" title="전화번호" value="${publication.telNo}"  class="inputbox w100" type="text" /></td>
				</tr>
				<tr>
					<th scope="row">이메일</th>
					<td><input name="eMail" title="이메일" value="${publication.eMail}"  class="inputbox w100" type="text" /></td>
				</tr>
                <tr>
					<th scope="row">부수</th>
						<td><input name="count" title="이메일" value="${fn:trim(publication.count)}"  class="inputbox w100" type="text" /></td>
				</tr>
                <tr>
					<th scope="row">등록일</th>
					<td><ikep4j:timezone date='${publication.registDate}'/></td>
				</tr>
				<tr>
					<th scope="row">수정일</th>
					<td><ikep4j:timezone date='${publication.updateDate}'/></td>
				</tr>                        
            </tbody>
		</table>
	</div>
             <!--//blockDetail End-->
                            
             <!--blockButton Start-->
             <div class="blockButton"> 
                 <ul>
                     <li><a class="button createPublication" href="#a"><span>등록</span></a></li>
                     <li><a class="button" href="javascript:deletePublication('${publication.id}');"><span>삭제</span></a></li>
                     <li><a class="button" href="javascript:history.back();"><span>목록</span></a></li>
                 </ul>
             </div>
             <!--//blockButton End-->
             
             <div class="blockBlank_10px"></div>		
                
                
</form>        