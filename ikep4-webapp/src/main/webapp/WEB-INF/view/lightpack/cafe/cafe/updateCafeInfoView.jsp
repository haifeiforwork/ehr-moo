<%--
=====================================================
	* 기능설명	:	cafe 내용 수정(회원정보수정 제외)화면
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"	value="message.lightpack.cafe.cafe.updateCafe.header" />
<c:set var="preSearch"	value="message.lightpack.cafe.cafe.updateCafe.search" />
<c:set var="preDetail"	value="message.lightpack.cafe.cafe.updateCafe.detail" />
<c:set var="preButton"	value="message.lightpack.cafe.cafe.updateCafe.button" />
<c:set var="preScript"	value="message.lightpack.cafe.cafe.updateCafe.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript" src="<c:url value='/base/js/jquery/plugins.pack.js'/>" ></script>

<script type="text/javascript">
<!-- 
var $teamStatus;
(function($) {
	var orgCafeName = null;
	var checkName = false;
	//Type category list
	changeType = function(typeId) {
		
        $jq.get('<c:url value="/lightpack/cafe/admin/category/listCafeCategory.do"/>',
			{typeId:typeId},
			function(data) {
				$jq("#categoryId").empty();
				
				for(var i=0 ; i<data.length ; i++) {
					$jq("<option/>").attr("value",data[i].categoryId).text(data[i].categoryName).appendTo("#categoryId");
					if('${cafe.categoryId}'==data[i].categoryId )
						$("#categoryId").val("${cafe.categoryId}");
				}
				if(data.length == 0) {
					$jq('<option/>').attr('value','').text('<ikep4j:message pre="${preScript}" key="noCategory" />').appendTo('#categoryId');
				}
			}

		)
		return false; 
	};

	// duplication Cafe Name
	checkCafeName = function() {
		
		var cafeName = $jq("input[name=cafeName]").val();
		if(cafeName == '${cafe.cafeName}')
		{
			alert('<ikep4j:message pre='${preScript}' key='sameCafeName' />');
			return;
		}		
		$jq.get('<c:url value="/lightpack/cafe/cafe/checkCafeName.do"/>',
			{cafeName:cafeName},
			function(data){
				if(data){
					checkName = true;
					alert('<ikep4j:message pre='${preScript}' key='successCafeName' />');
					orgCafeName=cafeName;						
				}else{
					$jq("input[name=cafeName]").val('');
					alert('<ikep4j:message pre='${preScript}' key='dupCafeName' />');
				}
			}
		)
		return false;
	};	
	
	//업로드완료후 fileId 리턴
	afterFileUpload = function(status, fileId, fileName, message, gubun) {
		//리턴받은 fileId를 Hidden값으로 저장함
		//폼을 최종 저장할때 filId를 가지고, fileLink정보를 생성함
		var imgsrc ="<c:url value="/support/fileupload/downloadFile.do"/>?fileId="+fileId;
		var img ="<img id='viewImageDiv' src='"+imgsrc+"' width='110' height='90'/>";
		//$jq("#viewDiv").html(fileId);
		$jq("#viewDiv").html(img);
		$jq("input[name=fileId]").val(fileId);
		
		setTimeout(function() {
			iKEP.iFrameContentResize(); 
		}, 100); 
	};
	
	//검색되거나 선택된 값이 있을경우 실행되는 콜백 메소드
	//result: 검색되거나 선택된 값을 배열형태로 리턴함
	setAddress = function(data) {
		var addStr="";
		$jq.each(data, function() {
			//addStr = addStr + "\""+$jq.trim(this.name)+"\" "+$jq.trim(this.email)+",";
			addStr = $jq.trim(this.name);
			teamId = $jq.trim(this.code);
		});
		
		$teamStatus = checkCafeTeam(teamId);

		if($teamStatus=="WO"){
			alert('<ikep4j:message pre='${preScript}' key='waitingOpen' />');
			return false;
		}
		else if($teamStatus=="WC"){
			alert('<ikep4j:message pre='${preScript}' key='waitingClose' />');
			return false;
		}
		else if($teamStatus=="O") {
			alert('<ikep4j:message pre='${preScript}' key='open' />');
			return false;
		}
				
		$jq("#teamName").val(addStr);
		$jq("#teamId").val(teamId);
	};
	
	checkCafeTeam = function(teamId) {

		$jq.get('<c:url value="/lightpack/cafe/cafe/checkCafeTeam.do"/>',
			{teamId : teamId},
			function(data){
				$teamStatus = data;
				//return data;
			}
		)
		return $teamStatus;
	};	
	
	selectCategory = function(result) {
		var categoryId = result.id,
			categoryName = result.name;
		
		$jq("input[name=categoryNavi]").val(categoryName);
		$jq("input[name=categoryId]").val(categoryId);	

		$("#categoryNavi").trigger("focusout");
	};
	
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		iKEP.iFrameContentResize();  
		
		//changeType('${cafe.typeId}');
		
		$jq("#initCafeButton").click(function() {
			$jq("#mainForm")[0].reset(); 	
			return false; 	
		});
		//파일업로드 버튼 이벤트 바인딩
		$jq('#fileuploadBtn').click(function(event) {
			//파일업로드 팝업창
			//파라미터(이벤트객체이름,에디터에서사용여부(0:일반,1:에디터에서),이미지여부(0:모든파일,1:이미지파일만 가능))
			iKEP.fileUpload(event.target.id,'0','1');

		});
		//파일삭제 버튼 이벤트 바인딩
		$jq('#filedeleteBtn').click(function(event) {
			//물리적인 삭제처리가 아닌 View 페이지 정보 없앰
			//추후에 배치를 통해 파일정보 자동삭제됨
			var img = '<img id="viewImageDiv" src="<c:url value='/base/images/common/photo_110x90.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='introImage' />" />';
			$jq("#viewDiv").html(img);
			$jq("input[name=fileId]").val("");
		});

		$jq("#saveCafeButton").click(function() {
			
			$jq("#mainForm").submit(); 	
		});

		$jq("#dupCafeButton").click(function() {
			checkCafeName(); 
		});
		
		//주소록 버튼에 이벤트 바인딩
		$jq('#addrBtn').click( function() {
			//파라미터(콜백함수, 전송파라미터, 팝업옵션)
			iKEP.showAddressBook(setAddress, "", {selectType:"group", isAppend:false, selectMaxCnt:1, tabs:{common:1}});
		});
				    	
		$jq("input[name=teamName]").click( function() {
			iKEP.showAddressBook(setAddress, "", {selectType:"group", isAppend:false, selectMaxCnt:1, tabs:{common:1}});
		});		
		
		
		$("#categoryTreeButton").click(function() {  
			iKEP.showDialog({
				title: "<ikep4j:message pre='${preDetail}' key='selectCategory' />",
				url: "<c:url value='/lightpack/cafe/category/viewCategoryTree.do'/>",
				modal: true,
				width: 300,
				height: 250,
				scroll: "no",
				callback : selectCategory
			});
		});
		
		/**
		 * Validation Logic Start
		 */
		
		var validOptions = {
			rules : {
				cafeName :	{
					required : true,
					maxlength : 200
				},
				categoryNavi :	{
					required : true
				},
				description :	{
					required : true,
					maxlength : 500
				}
			},
			messages : {
				cafeName : {
					direction	:	"bottom",
					required	:	"<ikep4j:message key="NotEmpty.cafe.cafeName" />",
					maxlength	:	"<ikep4j:message key="Size.cafe.cafeName" />"
					
				},
				categoryNavi : {
					direction	:	"bottom",
					required	:	"<ikep4j:message key="NotEmpty.cafe.categoryName" />",
					
				},
				description : {
					direction	:	"bottom",
					required	:	"<ikep4j:message key="NotEmpty.cafe.description" />",
					maxlength	:	"<ikep4j:message key="Size.cafe.description" />"
				}

			}
			,
			notice : {
				cafeName : {
					direction	:	"bottom",
					message		:	"<ikep4j:message key="Notice.cafe.changeCafeName" />"
				}
			},
			submitHandler : function(form) {
			
				var cafeName = $jq("input[name=cafeName]").val();

				if(orgCafeName != cafeName && cafeName!= '${cafe.cafeName}')
				{
					alert('<ikep4j:message pre='${preScript}' key='checkCafeName' />');
					return false;					
				}
		
				// 부가 검증 처리
				// Ws Name Dup Check
				if(!checkName && $jq("input[name=cafeName]").val() != '${cafe.cafeName}')
				{
					alert('sss')
					alert('<ikep4j:message pre='${preScript}' key='checkCafeName' />');
					return false;
				}
				// tag Check
				var tagNames = $jq("#mainForm").find('input[name=tag]').val();
				
				if(iKEP.tagging.lengthCheck(tagNames)){
					alert('<ikep4j:message pre='${preScript}' key='tagLength' />');
					return false;
				}
				
				if(!confirm("<ikep4j:message pre='${preScript}' key='script.save' />")) {
					return;
				}
				
				$.ajax({
					url : "<c:url value='/lightpack/cafe/cafe/updateCafeInfo.do' />",
					type : "post",
					loadingElement : {container:"#pageLodingDiv"},
					data : $(form).serialize(),
					success : function(result) {
						location.href= "<c:url value='/lightpack/cafe/cafe/updateCafeInfoView.do'/>?cafeId="+result+"&amp;listType=${searchCondition.listType}"; //조회화면으로 포워딩
					},
					error : function(xhr, exMessage) {
						
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});		
			}
		};


		var validator = new iKEP.Validator("#mainForm", validOptions);

		/**
		 * Validation Logic End
		 */		
	});
	
})(jQuery);  
//-->
</script>

<div id="pageLodingDiv">

<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<!--pageTitle Start-->

<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitleInfo" /></h2> 

</div> 

<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="post" action="">  
<spring:bind path="searchCondition.sortColumn">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 		
<spring:bind path="searchCondition.sortType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.cafeId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.cafeStatus">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.listType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.listUrl">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<input name="cafeIds" id="cafeIds" type="hidden" value="${cafe.cafeId}"/>
<%-- <input name="teamId" id="teamId" type="hidden" value="${cafe.teamId}"/> --%>

<div class="blockDetail">

	<table summary="<ikep4j:message pre="${preDetail}" key="summary" />">
		<caption></caption>
		<col style="width: 18%;"/>
		<col style="width: 32%;"/>
		<col style="width: 18%;"/>
		<col style=""/>		
		<tbody>
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='cafeName' /></th>
			<td colspan="3">
				<div>
				<spring:bind path="cafe.cafeName">			
				<input name="${status.expression}" id="${status.expression}"	class="inputbox w80" type="text" value="${status.value}"/>
				<a id="dupCafeButton" class="button_s" href="#a" title="<ikep4j:message pre='${preButton}' key='dupCafe'/>"><span><ikep4j:message pre='${preButton}' key='dupCafe'/></span></a>
				<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
				</spring:bind>
				</div>			
			</td>
		</tr>
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='categoryName' /></th>
			<td colspan="3">
				<div>
				<input name="categoryNavi" type="text" class="inputbox w80" title="" value="${cafe.categoryName}" /> <a id="categoryTreeButton" class="button_s" href="#a"><span>카테고리 선택</span></a>
				<spring:bind path="cafe.categoryId">
					<input type="hidden" name="${status.expression}" value="${status.value}"/>
				</spring:bind>
				</div>
			</td>
		</tr>
		
		<tr>
			<th scope="row"><font color='#990000'>*</font>&nbsp;<ikep4j:message pre='${preDetail}' key='description' /></th>
			<td colspan="3">
			<div>
			<spring:bind path="cafe.description">		
			<textarea id="${status.expression}" name="${status.expression}" class="w100"	title="<ikep4j:message pre='${preDetail}' key='description' />" cols="" rows="5">${status.value}</textarea>
			<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
			</spring:bind>
			</div>				
			</td>
		</tr>
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='introImage' /></th>
			<td colspan="3">
			
			<input name="imageIdPre" type="hidden" value="${cafe.imageId}" title="imageIdPre" />
			<input name="fileId" type="hidden" value="${cafe.imageId}" title="fileId" />
				<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='fileMessage' /></span>
				<div>
					<span id="viewDiv" class="cafe_img_bd">
							<c:if test="${empty cafe.fileDataList}">
								<img src="<c:url value='/base/images/common/photo_110x90.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='introImage' />" width="110" height="90" />
							</c:if>
							<c:if test="${!empty cafe.fileDataList}">
								<c:forEach var="fileDataList" items="${cafe.fileDataList}" varStatus="tagLoop">
									<img id='viewImageDiv' src='<c:url value="/support/fileupload/downloadFile.do"/>?fileId=${fileDataList.fileId}' alt="<ikep4j:message pre='${preDetail}' key='introImage' />" width="110" height="90" onerror="this.src='<c:url value='/base/images/common/photo_130x95.gif'/>'" />
								</c:forEach>
							</c:if>
					</span>
					<a name="fileuploadBtn" id="fileuploadBtn" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='update' /></span></a>
					<a id="filedeleteBtn"class="button_s" href="#a">
								<span><ikep4j:message pre='${preButton}' key='delete' /></span>
							</a>
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row">&nbsp;<ikep4j:message pre='${preDetail}' key='tag' /></th>
			<td colspan="3"><input type="text" id="tag" name="tag" class="inputbox w100" title="<ikep4j:message pre='${preDetail}' key='tag' />" value="<c:forEach var="tag" items="${cafe.tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">, </c:if>${tag.tagName}</c:forEach>"/>
			<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='tagMessage' /></div>
			</td>
		</tr>
		</tbody>
	</table>

	
	
</div>
<input name="controlTabType" title="" type="hidden" value="0:0:0:0" />
<input name="controlType" title="" type="hidden" value="ORG" />
<input name="selectType" title="" type="hidden" value="GROUP" />
<input name="selectMaxCnt" title="" type="hidden" value="1" />
<input name="searchSubFlag" title="" type="hidden" value="" />
</form>
<!--//blockListTable End--> 
	 
<!--blockButton Start-->
<div class="blockButton">
<ul>
	<li><a id="saveCafeButton" class="button" href="#" title="<ikep4j:message pre='${preButton}' key='save'/>"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
	<li><a id="initCafeButton" class="button" href="#" title="<ikep4j:message pre='${preButton}' key='init'/>"><span><ikep4j:message pre='${preButton}' key='init'/></span></a></li>
</ul>
</div>
<!--//blockButton End-->	
	
</div>

