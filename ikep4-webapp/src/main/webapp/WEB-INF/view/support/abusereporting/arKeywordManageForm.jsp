<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preDetail"  		value="ui.support.abusereporting.arKeyword.detail" />
<c:set var="preButton"  		value="ui.support.abusereporting.button" />
<c:set var="preLabel" 			value="ui.support.abusereporting.label" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
<!--

(function($) {

	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		// 등록 버튼 눌렀을 때
		$jq("#regiBtn").click(function() {
			//form을 submit하려고 시도한다.
			$jq('form[name=keywordForm]').submit();
		});

		// 취소 버튼 눌렀을 때
		$jq("#cancelBtn").click(function() {
			$jq('form[name=keywordForm]')[0].reset();
		});

		// 수정 버튼 눌렀을 때
		$jq("#updateBtn").click(function() {
			getRegForm('2','${keyword}');
		});

		// 삭제 버튼 눌렀을 때
		$jq("#delBtn").click(function() {
			//alert($jq('#keyword').val());
			delKeyword($jq('#keyword').val());
		});

		<c:if test="${2 == formType }">
			$jq('form[name=keywordForm]').find('input[name=keyword]').attr('readonly',true);
		</c:if>
		
		<c:if test="${3 == formType }">
			$jq('form[name=keywordForm]').find('input[name=keyword]').attr('readonly',true);
			$jq('form[name=keywordForm]').find('input[name=moduleCode]').attr("disabled",true);
		</c:if>
		
		// 정합성체크 옵션
		var validOptions = {
			rules : {
				keyword : {
					required : true
				}
			},
			messages : {
				keyword : { 
		    		direction : "bottom",
		        	required  : "<ikep4j:message pre='${preNotice}' key='keyword' />"
		    	}
			},
			submitHandler : function(form) {
				
				if(checkValid())
				{
					var keyword = $jq('#keyword').val();
					$.ajax({
						url : iKEP.getContextRoot() + "/support/abusereporting/createArKeyword.do",
						type : "post",
						data : $(form).originalSerialize(),
						success : function(data) {
							//alert(data);
							getRegForm('3',keyword);
							getKeywordList();
							
						},
						error : function(xhr, exMessage) {
							//alert("fail"); 
							var errorItems = $.parseJSON(xhr.responseText).exception;
							validator.showErrors(errorItems);
						}
					});
				}
			}
		};
		
		// form이 submit 될 때과 정합성옵션 연결된다.
		var validator = new iKEP.Validator("#keywordForm", validOptions);

	});
	// $(document).ready END

	checkValid = function(){
		//alert('checkValid!!!');

		// 금지어를 등록 안한 경우
		if("" == $jq('#keyword').val())
		{
			alert("<ikep4j:message pre='${preNotice}' key='keyword' />");
			
			return false;
		}
		// 선택을 안한 경우
		if(0 == $jq('input[name=moduleCode]:checkbox:checked').length)
		{
			alert("<ikep4j:message pre='${preMessage}' key='selectModule' />");
			return false;
		}
		return true;
	}

	// keyword 단건 삭제
	delKeyword = function(keyword){
		//iKEP.debug(keyword);
		$jq.get(iKEP.getContextRoot() + "/support/abusereporting/delArKeyword.do", 
				{'keywords':keyword}, 
				function(data) {
					//alert(data);
					keywordFormInit();
		});
	};

	// keywordForm 초기화
	keywordFormInit = function(){
		//alert('keywordFormInit!!');
		$jq('form[name=keywordForm]').find('input[name=keyword]').val('');
		$jq('form[name=keywordForm]').find('input[name=moduleCode]').attr("checked", true);

		getKeywordList();
	}

})(jQuery);

//-->
</script>

	<!--blockDetail Start-->
	<div class="blockDetail">
		<form id="keywordForm"  name="keywordForm" method="post" >
			<table summary="모듈정보">
			<caption></caption>
				<tbody>
					<tr>
						<th scope="row" width="18%"><ikep4j:message pre='${preDetail}' key='keyword' /></th>
						<td width="*%">
						    <c:if test="${1 == formType}">	
							<input type="text" class="inputbox w100" title="<ikep4j:message pre='${preDetail}' key='keyword' />" name="keyword" id="keyword" value="${keyword}" size="" />
							</c:if>
							<c:if test="${2 == formType || 3 == formType}">
							<input type="hidden" class="inputbox w100" title="<ikep4j:message pre='${preDetail}' key='keyword' />" name="keyword" id="keyword" value="${keyword}" size="" />
							<c:out value="${keyword}"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre='${preDetail}' key='module' /></th>
						<td>
							<ul>
								<c:forEach var="arModule" items="${arModulelist}" varStatus="loopStatus">
									<c:set var="checked" value=""/>
									<c:forEach var="linkedModule" items="${linkedModuleList}" varStatus="loopStatus">
										<c:if test="${arModule.moduleCode == linkedModule.moduleCode }">
											<c:set var="checked" value="checked"/>
										</c:if>
									</c:forEach>
									<c:if test="${1 == formType }"><c:set var="checked" value="checked"/></c:if>
									<li class="mb5"><input name="moduleCode" class="checkbox" title="checkbox" type="checkbox" value="${arModule.moduleCode}" ${checked} />${arModule.moduleName}</li>
								</c:forEach>
							</ul>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<!--//blockDetail End-->
	<ikep4j:message pre='${preLabel}' key='regAlready' />
	<div class="clear"></div>
	<!--blockButton Start-->
	<div class="blockButton">
		<ul>
			
			<c:if test="${true == isSystemAdmin}">	
				<c:if test="${1 == formType || 2 == formType}">	<!-- 등록/수정 -->
					<li><a class="button" href="#a" id="regiBtn"><span><ikep4j:message pre='${preButton}' key='save' /></span></a></li>
					<li><a class="button" href="#a" id="cancelBtn"><span><ikep4j:message pre='${preButton}' key='cancel' /></span></a></li>
				</c:if>
				<c:if test="${3 == formType }"> <!-- 조회 -->
					<li><a class="button" href="#a" id="updateBtn"><span><ikep4j:message pre='${preButton}' key='update' /></span></a></li>
					<li><a class="button" href="#a" id="delBtn"><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></li>
				</c:if>
			</c:if>
		</ul>
	</div>
	<!--//blockButton End-->
				