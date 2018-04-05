<%--
=====================================================
	* 기능설명	:	 등록
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.lightpack.cafe.cafe.createCafe.header" />
<c:set var="preSearch"  value="message.lightpack.cafe.cafe.createCafe.search" />
<c:set var="preDetail"  value="message.lightpack.cafe.cafe.createCafe.detail" />
<c:set var="preButton"  value="message.lightpack.cafe.cafe.createCafe.button" />
<c:set var="preScript"  value="message.lightpack.cafe.cafe.createCafe.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript" src="<c:url value='/base/js/jquery/plugins.pack.js'/>" ></script>

<script type="text/javascript">
<!--
(function($) {
	var dialogWindow = null;
	var orgCafeName = null;
	var checkName = false;
		

	//Type category list
	changeType = function(typeId) {
		
        $jq.get('<c:url value="/collpack/collaboration/admin/category/listCafeCategory.do"/>',
			{typeId:typeId},
			function(data) {
				$jq("#categoryId").empty();
				
				for(var i=0 ; i<data.length ; i++) {
					$jq("<option/>").attr("value",data[i].categoryId).text(data[i].categoryName).appendTo("#categoryId");
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
		if(cafeName !=""){
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
		}else{
			alert("<ikep4j:message pre='${preScript}' key='emptyCafeName' />");
		}
		return false;
	};

    //업로드완료후 fileId 리턴
	afterFileUpload = function(status, fileId, fileName, message, gubun) {
		//리턴받은 fileId를 Hidden값으로 저장함
		//폼을 최종 저장할때 filId를 가지고, fileLink정보를 생성함
		var imgsrc ="<c:url value="/support/fileupload/downloadFile.do"/>?fileId="+fileId;
		var img ="<img id='viewImageDiv' src='"+imgsrc+"' width='110' height='90'/> ";
		
		$jq("#viewImageSpan").html(img);
		$jq("input[name=fileId]").val(fileId);
		 
	};

	selectCategory = function(result) {
		var categoryId = result.id,
			categoryName = result.name;
		$jq("input[name=categoryNavi]").val(categoryName);
		$jq("input[name=categoryId]").val(categoryId);
		
		$("#categoryNavi").trigger("focusout");
		
		/* $("#searchBoardItemForm input[name=checkboxBoardItem]:checked").each(function(index) { 
			itemIds.push($(this).val()); 
		}); */
		
		/* $.get("<c:url value='/lightpack/cafe/board/boardItem/moveBoardItem.do'/>?orgBoardId="+orgBoardId +"&targetBoardId="+targetBoardId+"&itemIds="+itemIds)
		.success(function(data) {
			location.href="<c:url value='/lightpack/cafe/board/boardItem/listBoardItemView.do?cafeId=${board.cafeId}&boardId=${board.boardId}'/>";
		})
		.error(function(event, request, settings) { alert("error"); }); */ 		
	};
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		fnCaller = function(param, dialog){
			dialogWindow = dialog;
		}
		
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
			
			$jq("#viewImageSpan").html(img);
			$jq("input[name=fileId]").val("");
		});
		
		
		$jq('#cancelCafeButton').click(function(event) {
			dialogWindow.close();
		});
		
		$jq("#createCafeButton").click(function() {
			$jq("#mainForm").submit();
		});
		
		$jq("#dupCafeButton").click(function() {
			checkCafeName(); 
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

			},
			notice : {
				cafeName : {
					direction	:	"bottom",
					message		:	"<ikep4j:message key="Notice.cafe.cafeName" />"
				}
			},
			submitHandler : function(form) {
				var cafeName = $jq("input[name=cafeName]").val();
				
				if(orgCafeName != cafeName)
				{
					alert('<ikep4j:message pre='${preScript}' key='checkCafeName' />');
					return false;					
				}
				// 부가 검증 처리
				// Ws Name Dup Check
				if(!checkName)
				{
					alert('<ikep4j:message pre='${preScript}' key='checkCafeName' />');
					return false;
				}
				// tag Check
				var tagNames = $jq("#mainForm").find('input[name=tag]').val();
				
				if(iKEP.tagging.lengthCheck(tagNames)){
					alert('<ikep4j:message pre='${preScript}' key='tagLength' />');
					return false;
				}
				
				if(!confirm('<ikep4j:message pre='${preScript}' key='confirmCafe' />')) {
					return false;
				}
				
				$.ajax({
					url : "<c:url value='/lightpack/cafe/cafe/createCafe.do' />",
					type : "post",
					data : $(form).serialize(),
					loadingElement : {container:"#pageLodingDiv"},
					success : function(cafeId) {
						alert("<ikep4j:message pre='${preScript}' key='createCafe' />");
						dialogWindow.callback(cafeId);
						dialogWindow.close();
					},
					error : function(xhr, exMessage) {						
					}
				});		
			}
			
		};


		var validator = new iKEP.Validator("#mainForm", validOptions);

		/**
		 * Validation Logic End
		 */

	});
	
	

	// 주의 위에 code 가 가이드 입력시 오류로 입력되지 않아서 부득이하게 공백으로 입력
	// 추후 사용시 공백 제거후 사용 해주세요
	
	
	function show_layer(){
		$jq("#layer_p").dialog({width: 520, height:160, modal:true, resizable: false});
	}
	
})(jQuery);  
//-->
</script>

<div id="pageLodingDiv">

<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<!--pageTitle Start-->

<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
</div> 

<!-- Modal window Start -->
<!-- 사용시class="none"으로 설정 -->
<div class="" id="layer_p" title="Category">
	
<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="post" action="<c:url value='/lightpack/cafe/cafe/createCafe.do' />">  

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

	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="Category">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="18%"><ikep4j:message pre='${preDetail}' key='cafeName' /></th>
					<td width="82%">
						<div>
						<spring:bind path="cafe.cafeName">
							<input id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preDetail}' key='cafeName'/>" class="inputbox w80" type="text" /> <a  id="dupCafeButton" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='dupCafe'/></span></a><br />
						<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
						</spring:bind>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row" width="18%"><ikep4j:message pre='${preDetail}' key='categoryName' /></th>
					<td width="82%">
						<div>
						<input name="categoryNavi" id="categoryNavi" type="text" class="inputbox w80" title="<ikep4j:message pre='${preDetail}' key='categoryName' />" value="" readonly /> <a id="categoryTreeButton" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='category' /></span></a>
						<input name="categoryId" id="categoryId" type="hidden" value=""/>
						<input name="categoryName" id="categoryName" type="hidden" value=""/> 
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preDetail}' key='description' /></th>
					<td>
						<div>
							<spring:bind path="cafe.description">
							<textarea id="${status.expression}" name="${status.expression}" class="w100" title="<ikep4j:message pre='${preDetail}' key='description' />" cols="" rows="5"></textarea>
							<span class="totalNum_s"></span> 
							<label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
							</spring:bind>
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preDetail}' key='introImage' /></th>
					<td>
						<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='fileMessage' /></span>
						<div>
							<input name="fileId" type="hidden" value="" title="fileId" />
							<span id="viewImageSpan" class="cafe_img_bd">
								<img id="viewImageDiv" src="<c:url value='/base/images/common/photo_110x90.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='introImage' />" />
							</span>
							<a id="fileuploadBtn" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='update' /></span></a>
							<a id="filedeleteBtn"class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='delete' /></span></a>
						</div>								
					</td>
				</tr>
				
				<tr>
					<th scope="row"><ikep4j:message pre='${preDetail}' key='tag' /></th>
					<td>
						<input name="tag" id="tag" title="<ikep4j:message pre='${preDetail}' key='tag' />" class="inputbox w100" type="text" />
						<div class="tdInstruction"><ikep4j:message pre='${preDetail}' key='tagMessage' /></div>
					</td>
				</tr>							
			</tbody>
		</table>
	</div>
	<!--//blockDetail End-->
							
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="createCafeButton"  class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='open' /></span></a></li>
			<li><a id="cancelCafeButton"  class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel' /></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->	
</form>			
</div>	
<!-- //Modal window End -->

</div>
