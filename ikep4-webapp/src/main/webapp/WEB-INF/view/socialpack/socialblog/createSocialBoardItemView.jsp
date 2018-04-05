<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<!-- <c:set var="preHeader"  value="ui.socialpack.socialblog.socialBoardItem.common.header" /> -->
<c:set var="preDetail"  value="ui.socialpack.socialblog.socialBoardItem.createBoardView.detail" />
<c:set var="preCode"    value="ui.socialpack.socialblog.common.code" />
<c:set var="preButton"  value="ui.socialpack.socialblog.common.button" /> 
<c:set var="preMessage" value="message.socialpack.socialblog.common.socialBoardItem" />
<c:set var="preCreateMessage" value="message.socialpack.socialblog.socialBoardItem.createBoardView" />
<c:set var="preSearch"  value="ui.socialpack.socialblog.common.searchCondition" /> 

<body <c:if test="${socialBlog.isPrivateImage == 1}"> style="background-image:Url('<c:url value="/support/fileupload/downloadFile.do?fileId=${socialBlog.imageFileId}" />');" </c:if> <c:if test="${socialBlog.isPrivateImage == 0}"> class="${socialBlog.imageUrl}" </c:if>>	

<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/units/socialpack/socialblog.js"/>"></script>

<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript">
<!--   
var useActXEditor = "${useActiveX}";
(function($){	
	$(document).ready(function() {    
		
		// editor 초기화
		initEditorSet();
		
		$(".divPortletLoad").trigger("click");
		
		$("#listSocialBoardItemButton").click(function() {  
			SocialBlog.getPostingList('${socialBlog.ownerId}');
		});
	    
		$("#saveSocialBoardItemButton").click(function() {  			
			$("#socialBoardItemForm").trigger("submit"); 
			return false; 			
		});
				
		new iKEP.Validator("#socialBoardItemForm", {   
			rules  : {
				title     : {
						required : true, 
						rangelength : [1, 150] 
				},
				tag       : {
						required : true 
				}
			},
			messages : {
				title     : {
						direction : "bottom",    
						required : "<ikep4j:message key='NotNull.socialBoardItem.title' />", 
						rangelength : "<ikep4j:message key='Size.socialBoardItem.title' />"
				},
				tag       : {
						direction : "bottom", 
						required : "<ikep4j:message key='NotBlank.socialBoardItem.tag' />"
				}
			},
			notice : {
				title     : { 
						direction : "bottom",
						message : "<ikep4j:message key='Size.socialBoardItem.title' />"
				},
				tag       : {
						direction : "bottom",
						message : "<ikep4j:message key='NotBlank.socialBoardItem.tag' />"
				}
			},
		    submitHandler : function(form) {  
		    	// ActiveX Editor 사용 여부가 Y인 경우
		    	if(useActXEditor == "Y"){
			    	if ($.browser.msie) {
			    		//태그프리 Mime 데이타 세팅
			    		var tweBody = document.twe.MimeValue();
			    		$('input[name="contents"]').val(tweBody);
			    	}else{
			    		//ekeditor 데이타 업데이트
			    		var editor = $("#contents").ckeditorGet(); 
						editor.updateElement();
						//에디터 내 이미지 파일 링크 정보 세팅
						createEditorFileLink("socialBoardItemForm");
			    	}
		    	}else{
		    		//ekeditor 데이타 업데이트
		    		var editor = $("#contents").ckeditorGet(); 
					editor.updateElement();
					//에디터 내 이미지 파일 링크 정보 세팅
					createEditorFileLink("socialBoardItemForm");
		    	}

				var resultTitle = iKEP.checkProhibitWord('SB',$("#title").val());
				var resultContents = iKEP.checkProhibitWord('SB',$("#contents").val());
				if( "" != resultTitle ){
					alert('<ikep4j:message pre="${preCreateMessage}" key="checkProhibitWord.title" />('+resultTitle.replace(/[|]/g, ',')+")");
					return false;
				}else if( "" != resultContents){
					alert('<ikep4j:message pre="${preCreateMessage}" key="checkProhibitWord.contents" />('+resultContents.replace(/[|]/g, ',')+")");
					return false;
				}else{
			    	if(confirm('<ikep4j:message pre="${preMessage}" key="createItem" />')) {
						fileController.upload(function(isSuccess, files) {
							if(isSuccess === true) {
								//에디터 감추기
								if(useActXEditor == "Y"){
							    	if ($.browser.msie) {
							    		$("#twe").css("visibility","hidden");
							    	}
								}
								
								$("body").ajaxLoadStart("button");
								form.submit();
							}
						});
					}
				}
		    }
		}); 
		
	    var uploaderOptions = {
		 	files : [],
			allowFileType : "allowAll",
	    	isUpdate : true 
	    }; 

		//파일업로드 컨트롤로 생성 : formId는 조회화면일 경우 null로 지정할 수 있습니다.
		var fileController = new iKEP.FileController("#socialBoardItemForm", "#fileUploadArea", uploaderOptions);
	   	 
	});
	
	/* editor 초기화  */
	initEditorSet = function() {
		// ActiveX Editor 사용 여부가 Y인 경우
	    if(useActXEditor == "Y"){
			// 브라우저가 ie인 경우
			if ($.browser.msie) {
				// div 높이, 넓이 세팅
				var cssObj = {
			      'height' : '450px',
			      'width' : '100%'
			    };
				$('#editorDiv').css(cssObj);

				// hidden 필드 추가(contents)
				iKEP.createActiveXEditor("#editorDiv","${user.localeCode}","#contents",0);
				// ie 세팅
				$('input[name="msie"]').val('1');
			}else{
				// ckeditor 초기화.
				$("#contents").ckeditor($.extend(cafeCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
				// ie 이외 브라우저 값 세팅
				$('input[name="msie"]').val('0');
			}
	    }else{
	    	// ckeditor 초기화.
			$("#contents").ckeditor($.extend(cafeCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
			// ie 이외 브라우저 값 세팅
			$('input[name="msie"]').val('0');
	    }
	};
	
	
	// 블로그 로딩시 해당 Portlet 메뉴를 로딩 하기 위한 메서드
	getSocialBlogPortLayout = function(el,portletUrl) {
		$.ajax({
		    url : iKEP.getContextRoot() + portletUrl,
		    data : {'blogOwnerId':'${socialBlog.ownerId}'},
		    type : "get",
		    success : function(result) {
		    	$(el).html(result);
		    	$(el).removeAttr( "onclick" );
		    }
		});	
	};

	
	// 블로그의 Follower List 에서 호출 하는 Following User 의 TimeLine
	// 내가 팔로워 하고 있는 사용자들이 작성한 Microblog List
	getMicroBlogFollowingTimeLine = function() {
		$.ajax({
		    url : iKEP.getContextRoot() + "/socialpack/microblogging/followingTimelineHome.do",
		    data : {'userId':'${socialBlog.ownerId}'},
		    type : "get",
		    success : function(result) {
		    	
		    	$(document).scrollTop(0);
		    	$("#blockContentsArea").addClass("corner_RoundBox07");
		    	$("#blockContentsArea").html(result);
		    }
		});	
	};
	
	//날자별 검색을 통해 
	getSearchDateBlogPosting = function(searchDate) {
		
		$.ajax({
		    url : '<c:url value="/socialpack/socialblog/listSocialBoardItemView.do"/>',
		    data : {'blogOwnerId':'${socialBlog.ownerId}','searchDate':searchDate},
		    type : "get",
		    success : function(result) {
		    	
		    	$(document).scrollTop(0);
		    	$("#blockContentsArea").removeClass("corner_RoundBox07");
		    	$("#blockContentsArea").html(result);
		    }
		});
	};
	
	
	// 검색 키워드  통해 
	getSearchBlogPosting = function(searchType,searchKeyword,pagePerRecord) {
		
		$.ajax({
		    url : '<c:url value="/socialpack/socialblog/listSocialBoardItemView.do"/>',
		    data : {'blogOwnerId':'${socialBlog.ownerId}','searchColumn':searchType,'searchWord':searchKeyword,'pagePerRecord':pagePerRecord},
		    type : "get",
		    success : function(result) {
		    	
		    	$(document).scrollTop(0);
		    	$("#blockContentsArea").removeClass("corner_RoundBox07");
		    	$("#blockContentsArea").html(result);
		    }
		});
		
	};
	
	
	// 카테고리 별 검색 
	getCategoryBlogPosting = function(categoryId) {
		
		$.ajax({
		    url : '<c:url value="/socialpack/socialblog/listSocialBoardItemView.do"/>',
		    data : {'blogOwnerId':'${socialBlog.ownerId}','categoryId':categoryId},
		    type : "get",
		    success : function(result) {
		    	
		    	$(document).scrollTop(0);
		    	$("#blockContentsArea").removeClass("corner_RoundBox07");
		    	$("#blockContentsArea").html(result);
		    }
		});
		
	};
	
	// 최근 코멘트가 달린 포스팅을 검색 
	getRecentCmtBlogPosting = function(itemId) {
		
		$.ajax({
		    url : '<c:url value="/socialpack/socialblog/listSocialBoardItemView.do"/>',
		    data : {'blogOwnerId':'${socialBlog.ownerId}','itemId':itemId},
		    type : "get",
		    success : function(result) {
		    	
		    	$(document).scrollTop(0);
		    	$("#blockContentsArea").removeClass("corner_RoundBox07");
		    	$("#blockContentsArea").html(result);
		    }
		});
		
	};
	
	
})(jQuery);  
//-->
</script>
<script language="JScript" FOR="twe" EVENT="OnKeyDown(event)">
	/* 태그프리 에디터 줄바꿈 태그 P => br 로 변경하는 메소드 */
	if (!event.shiftKey && event.keyCode == 13){
		twe.InsertHtml("<br>");
		event.returnValue = true; <!-- Active Designer에서의 처리를 막음 -->
	}
	if (event.shiftKey && event.keyCode == 13){
		twe.InsertHtml("<p>");
		event.returnValue = true; <!-- Active Designer에서의 처리를 막음 -->
	}		
</script>

		<!--blockMain Start-->
		<div id="blockMain">
				<form id="editorFileUploadParameter" name="editorFileUploadParameter" action="" > 
				<input name="blogIdId"  value="${socialBlog.blogId}" type="hidden" /> 
				<input name="interceptorKey"  value="socialpack.socialblog" type="hidden" /> 
				</form>
			<!--mainContents Start-->
			<div id="mainContents" class="conPadding_2 fixedContents">
				<h1 class="none"><ikep4j:message pre='${preMain}' key='content' /></h1>
				
				
				<c:if test="${mySocialBlogLayout != null}">
					<c:choose>
						<c:when test="${mySocialBlogLayout.colCount == 2}">
							<c:forEach var="layoutListColumn" items="${mySocialBlogLayout.socialBlogLayoutColumnList}" varStatus="colStatus">
								<c:choose>
									<c:when test="${layoutListColumn.isFixed == 0}">
										<c:if test="${colStatus.index == 0}">
											<!--blockLeft_fixed Start 좌 -->
											<div class="blockLeft_fixed">
												<!--corner_RoundBox07 Start-->
												<div class="corner_RoundBox07">
													<c:if test="${mySocialBlogLayout != null}">
													<c:forEach var="portletLayout" items="${socialBlogPortletLayoutList}" varStatus="colStatus">
														<c:if test="${ portletLayout.colIndex == 1 }">
															<div class="divPortletLoad" onclick="getSocialBlogPortLayout(this,'${portletLayout.socialBlogPortlet.viewUrl}')"></div>
														</c:if>
													</c:forEach>
													</c:if>
												</div>
												<!--corner_RoundBox07 End-->
											</div>
											<!--//blockLeft_fixed End-->
										</c:if>
										<c:if test="${colStatus.index == 1}">
											<!--blockRight_fixed Start 우 -->	
											<div class="blockRight_fixed">
												<!--corner_RoundBox07 Start-->
												<div class="corner_RoundBox07">
													<c:if test="${mySocialBlogLayout != null}">
													<c:forEach var="portletLayout" items="${socialBlogPortletLayoutList}" varStatus="colStatus">
														<c:if test="${ portletLayout.colIndex == 2 }">
															<div class="divPortletLoad" onclick="getSocialBlogPortLayout(this,'${portletLayout.socialBlogPortlet.viewUrl}')"></div>
														</c:if>
													</c:forEach>
													</c:if>
												</div>
												<!--corner_RoundBox07 End-->
											</div>	
											<!--//blockRight_fixed End-->
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if test="${colStatus.index == 0}">
											<!-- 고정영역 항상 보여 지는 내용 영역  -->
											<!--blockCenter Start 우 -->
											<div class="blockCenter">
												<div class="blockCenter_con_2" id="blockContentsArea">
												<%@ include file="/WEB-INF/view/socialpack/socialblog/includePostEditor.jsp"%>	
												</div>
											</div>
											<!--//blockCenter End-->
										</c:if>
										<c:if test="${colStatus.index == 1}">
											<!-- 고정영역 항상 보여 지는 내용 영역  -->
											<!--blockCenter Start 좌-->
											<div class="blockCenter">
												<div class="blockCenter_con_3" id="blockContentsArea">
												<%@ include file="/WEB-INF/view/socialpack/socialblog/includePostEditor.jsp"%>	
												</div>
											</div>
											<!--//blockCenter End-->
										</c:if>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<!--blockLeft_fixed Start-->
							<div class="blockLeft_fixed">
								<!--corner_RoundBox07 Start-->
								<div class="corner_RoundBox07">
									<c:if test="${mySocialBlogLayout != null}">
										<c:forEach var="portletLayout" items="${socialBlogPortletLayoutList}" varStatus="colStatus">
											<c:if test="${ portletLayout.colIndex == 1 }">
												<div class="divPortletLoad" onclick="getSocialBlogPortLayout(this,'${portletLayout.socialBlogPortlet.viewUrl}')"></div>
											</c:if>
										</c:forEach>
									</c:if>
								</div>
								<!--corner_RoundBox07 End-->
							</div>
							<!--//blockLeft_fixed End-->
							
							<!-- 고정영역 항상 보여 지는 내용 영역  -->
							<!--blockCenter Start-->
							<div class="blockCenter">
								<div class="blockCenter_con" id="blockContentsArea">	
								<%@ include file="/WEB-INF/view/socialpack/socialblog/includePostEditor.jsp"%>				
								</div>
							</div>
							<!--//blockCenter End-->
							
							<!--blockRight_fixed Start-->	
							<div class="blockRight_fixed">
								<!--corner_RoundBox07 Start-->
								<div class="corner_RoundBox07">
									<c:if test="${mySocialBlogLayout != null }">
										<c:forEach var="portletLayout" items="${socialBlogPortletLayoutList}" varStatus="colStatus">
											<c:if test="${ portletLayout.colIndex == 2 }">
												<div class="divPortletLoad" onclick="getSocialBlogPortLayout(this,'${portletLayout.socialBlogPortlet.viewUrl}')"></div>
											</c:if>
										</c:forEach>
									</c:if>
								</div>
								<!--corner_RoundBox07 End-->						
							</div>	
							<!--//blockRight_fixed End-->
						</c:otherwise>
					</c:choose>
				</c:if>
				
			</div>

			<!--//mainContents_3 End-->

		</div>
		<!--//blockMain End-->

</body>
