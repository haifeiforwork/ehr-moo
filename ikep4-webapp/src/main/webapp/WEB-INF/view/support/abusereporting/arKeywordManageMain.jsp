<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preDetail"  	value="ui.support.abusereporting.arKeyword.detail" />
<c:set var="preAbuseSearch"	value="ui.support.abusereporting.arAbuseSearchCondition.detail" />
<c:set var="preLabel" 		value="ui.support.abusereporting.label" />
<c:set var="preButton"  	value="ui.support.abusereporting.button" />
<c:set var="preMenu"  		value="ui.support.abusereporting.menu" />
<c:set var="preMessage"		value="ui.support.abusereporting.message" /> 
<c:set var="preNotice"		value="Notice.ArKeyword" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
<!--   

(function($) {

	// onload시 수행할 코드
	$jq(document).ready(function() { 
		// left menu setting
		 //iKEP.setLeftMenu();
		
		// 리스트 하단 등록 버튼 눌렀을 때
		$jq("#getRegFormBtn").click(function() {
			getRegForm('1');
		});

		// 리스트 하단 등록 버튼 눌렀을 때
		$jq("#delKeywordListBtn").click(function() {
			delKeywordList();
		});

		$jq("#checkedAll").click(function() {

	 		if ($jq("#checkedAll").is(":checked")) {
	 			$jq('input[name=keywords]:not(checked)').attr("checked", true);
			}else{
		   		$jq('input[name=keywords]:checked').attr("checked", false);
		    }

	    });

		$jq('#keyword').keypress(function(event) { 
			if(event.which == '13') {
				getKeywordList();
			}
		}); 

		getKeywordList();

	});
	// $(document).ready END
	
	// 우측 등록 페이지 조회
	// 			등록/수정/조회(1/2/3),	금지어
	getRegForm = function(formType, keyword){
		//alert(formType);
		//alert(keyword);
		$jq.get(iKEP.getContextRoot() + "/support/abusereporting/getArKeywordManageForm.do", 
				{'formType':formType, 'keyword':keyword}, 
				function(data) {
					//alert(data);
					$jq("#arKeywordManageFormArea").empty().append(data);
		});
	};

	// List에서 keyword 삭제
	delKeywordList = function(){
		$jq.post(iKEP.getContextRoot() + "/support/abusereporting/delArKeyword.do", 
				$jq('form[name=keywordListForm]').serialize(), 
				function(data) {
					//alert(data);
					// 체크된 부분 삭제.
					//$("input:checked").parents("tr").remove();
					getKeywordList();
		});
	};

	// Keyword List 조회
	getKeywordList = function(){
		$jq.get(iKEP.getContextRoot() + "/support/abusereporting/getKeywordList.do", 
				$jq('form[name=searchForm]').serialize(), 
				function(data) {
					//alert(data);
					$jq("#arKeywordlistArea").empty().append(data);
		});
	};

})(jQuery);

//-->
</script>


	<!--pageTitle Start-->
	<div id="pageTitle">
		<h2><ikep4j:message pre='${preDetail}' key='title'  /></h2>
	</div>
	<!--//pageTitle End-->
	
	<form name='searchForm' id="searchForm" method="post"> 
		<!--blockSearch Start-->
		<div class="blockSearch">
			<div class="corner_RoundBox03">
				<table summary="Abuse 리포트 테이블">
					<caption></caption>
					<tbody>
						<tr>
							<th scope="row" width="5%"><ikep4j:message pre='${preLabel}' key='module' /></th>
							<td colspan="3">		
								<spring:bind path="searchCondition.moduleCode">  
									<select name="${status.expression}" title='<ikep4j:message pre='${preAbuseSearch}' key='${status.expression}' />'>
										<option value="ALL"><ikep4j:message pre='${preLabel}' key='allModule' /></option>
										<c:forEach var="arModule" items="${arModulelist}">
											<option value="${arModule.moduleCode}" <c:if test="${arModule.moduleCode eq status.value}">selected="selected"</c:if>>${arModule.moduleName}</option>
										</c:forEach> 											
									</select>
								</spring:bind>
							</td>									
						</tr>
						<tr>
							<th scope="row"><ikep4j:message pre='${preLabel}' key='searchWord' /></th>
							<td colspan="3">			
								<spring:bind path="searchCondition.searchWord">  					
									<input name="${status.expression}" id="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preAbuseSearch}' key='${status.expression}' />'  size="40" />
								</spring:bind>
							</td>								
						</tr>														
					</tbody>
				</table>
				<div class="searchBtn"><a id="searchBoardItemButton" name="searchBoardItemButton" href="#a" onclick="javascript:getKeywordList()" ><span><ikep4j:message pre='${preButton}' key='search' /></span></a></div>
				<div class="l_t_corner"></div>
				<div class="r_t_corner"></div>
				<div class="l_b_corner"></div>
				<div class="r_b_corner"></div>				
			</div>
			<div class="clear"></div>
		</div>	
		<!--//blockSearch End-->
	</form>
	
	<div class="blockLeft" style="width:48%;">
		<div class="proBox_l">
			<div class="sbox">
				<!--blockDetail Start-->					
				<div class="blockDetail">
					<form name="keywordListForm" method="post" >
						<table summary="금지어 목록 게시판">
							<caption></caption>
							<thead>
								<tr>
									<th scope="col" width="25" class="textCenter"><input id="checkedAll" name="" class="checkbox" title="checkbox" type="checkbox" value="" /></th>
									<th scope="col" width="*" class="textCenter"><ikep4j:message pre='${preDetail}' key='keyword'  /></th>
								</tr>
							</thead>
							<tbody id="arKeywordlistArea">

							</tbody>
						</table>
					</form>
				</div>
				<!--//blockDetail End-->
			</div>
		</div>
		<div class="clear"></div>
		<!--blockButton Start-->
		<div class="blockButton sch_btn">
			<ul>
				<c:if test="${true == isSystemAdmin}">	
					<li><a class="button" href="#a" id="getRegFormBtn"><span><ikep4j:message pre='${preButton}' key='regist' /></span></a></li>
					<li><a class="button" href="#a" id="delKeywordListBtn"><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></li>
				</c:if>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
	
	<div class="blockRight" style="width:50%;" id="arKeywordManageFormArea">	
	
	</div>
				