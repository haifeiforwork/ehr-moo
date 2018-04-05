<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preManage" value="ui.socialpack.socialblog.management" />
<c:set var="preManageMenu" value="ui.socialpack.socialblog.management.menu" />
<c:set var="preManageDesign" value="ui.socialpack.socialblog.management.designsetting" />
<c:set var="preManageStat" value="ui.socialpack.socialblog.management.statistics" />
<c:set var="preValidMessage" value="message.socialpack.socialblog.management.menu.category" />

<c:set var="preCode"    value="ui.socialpack.socialblog.common.code" />
<c:set var="preButton"  value="ui.socialpack.socialblog.common.button" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/units/socialpack/socialblog.js"/>"></script>
<script type="text/javascript">
	
	var unselect;
	var enterFlag = true;
(function($){
	var validOptionsCategory = { 
			
			rules : {
				categoryName : {
					required : true,
					maxlength : 20
				}
			},
			messages : {
				categoryName : {
					direction : "bottom",
					required : "<ikep4j:message key='NotNull.socialCategory.categoryName' />",
					maxlength : "<ikep4j:message key='Size.socialCategory.categoryName' />"
				}
			},
			notice : {
				categoryName : {
					direction : "bottom",
					message : "<ikep4j:message key='Size.socialCategory.categoryName' />"
				}
		    },
			submitHandler : function(form) {
				
				var blogOwnerId = $("input[name=blogOwnerId]").val();
				var blogId = $("input[name=blogId]").val();
				var categoryName = $("input[name=categoryName]", "#divCategoryItemControll").val();
				var lastCnt = $(".orderBox li:last-child").text();
				lastCnt++;

				switch(categoryItemAction) {
					case "add" :
						//ajax : save
						$jq.ajax({    
							url : "createCategory.do",     
							data : {blogOwnerId:blogOwnerId,blogId:blogId,categoryName:categoryName},
							type : "post",     
							success : function(result) {    
								// ajax success callback process					
								var data = {id:lastCnt, name:categoryName, categoryId:result};
								$(".orderBox").append($.tmpl("tmplCategoryOrder",data));
								$("#categorys").append($.tmpl("tmplCategoryItem",data));
								//document.location.reload();		//왼쪽 메뉴 카테고리 때문에 리플래쉬 해야함.
							},
							error : function(event, request, settings){
							 	alert("error");
							}
						});

						break;
					case "modify" :
						//ajax : save
						$jq.ajax({    
							url : "createCategory.do",     
							data : {blogOwnerId:blogOwnerId,blogId:blogId,categoryName:categoryName, categoryId:currCategoryId},     
							type : "post",     
							success : function(result) {    
								// ajax success callback process					
								var data = {name:categoryName};
								$("#"+currCategoryId).empty();
								$("#"+currCategoryId).append($.tmpl("tmplCategoryName",data));	
								clearSelect();
								//document.location.reload();	//왼쪽 메뉴 카테고리 때문에 리플래쉬 해야함.
							},
							error : function(event, request, settings){
							 	alert("error");
							}
						});
						break;
				}
				$("#divCategoryItemControll").dialog("close");

			}
		}
	
	
		// 카테고리 저장
		saveCategory = function() {
			//$("#categoryForm").submit();
		};
		
		//sort완료 후 처리
		sortArray = function(){
			
			var blogOwnerId = $("input[name=blogOwnerId]").val();
			var blogId = $("input[name=blogId]").val();
			$("#categorys").sortable("refresh");
			var result = $("#categorys").sortable("toArray");
			
			var resultText =result.join(",");
			
			$jq.ajax({    
				url : "applyCategoryOrder.do",     
				data : {blogOwnerId:blogOwnerId,blogId:blogId,categoryIdes:resultText},     
				type : "post",     
				success : function(result) {    
					//alert("success");
					document.location.reload();	//왼쪽 메뉴 카테고리 때문에 리플래쉬 해야함.
				},
				error : function(event, request, settings){
				 	alert("error");
				}
			}); 
		}
		
		selectItem = function(item) {
			unselect();
			if(isToggle(item)){
				clearSelect();
			}else{
				$(item).addClass("ui-selected");
				currCategoryId = $(item).attr("id");
				showBtn();
			}
		}
		
		isToggle = function(item){		
			if(currCategoryId == null){
				return false;			
			}else{
				if(currCategoryId == $(item).attr("id")){
					return true;
				}else{
					return false;
				}
			}
		}
		
		showBtn = function(){
			$("#modifyLi").show();
			$("#deleteLi").show();
		}
		
		hideBtn = function(){
			$("#modifyLi").hide();
			$("#deleteLi").hide();
		}
		
		unselect = function() {		
			$("#categorys > li").removeClass("ui-selected");
		}
		
		addCatetory = function(){
			showCategoryItemDialog("add");
		}
		
		modifyCatetory = function(){
			showCategoryItemDialog("modify");
		}
		
		deleteCatetory = function(){
			//$("#"+currCategoryId).remove();
			//$(".orderBox li:last-child").remove();
			//sortArray();
			var blogOwnerId = $("input[name=blogOwnerId]").val();
			var blogId = $("input[name=blogId]").val();
			//alert("deleteCategory.do?blogOwnerId="+blogOwnerId+"&amp;blogId="+blogId+"&amp;categoryId="+currCategoryId);
			document.location.href="deleteCategory.do?blogOwnerId="+blogOwnerId+"&amp;blogId="+blogId+"&amp;categoryId="+currCategoryId;
			
		}
		
		clearSelect = function(){
			currCategoryId = null;
			unselect();
			hideBtn();
		}
		
		var currCategoryId = null;
		var dialog = null;
		var categoryItemAction = "";
		
		function showCategoryItemDialog(flag) {
			
			categoryItemAction = flag;
			var dialogTitle = categoryItemAction == "add" ? "<ikep4j:message pre='${preManageMenu}' key='list.addCategoryName' />" : "<ikep4j:message pre='${preManageMenu}' key='list.editCategoryName' />";
			
			if(dialog == null) {
				dialog = $("#divCategoryItemControll").dialog({
					title : dialogTitle,
					modal : true,
					resizable : false,
					width : 550,
					height : 130,
					open : function() {
						switch(categoryItemAction) {
							case "add" :
								$("input[name=categoryName]", this).val("");
								break;
							case "modify" :
								var categoryName = $("#"+currCategoryId+" span:first-child").attr("name");
								$("input[name=categoryName]", this).val(categoryName);
								break;
						}
					}
				});
			} else {
				if(dialog.dialog("isOpen")) dialog.dialog("close");
				
				dialog.dialog("option", "title", dialogTitle)
					.dialog("open");
				
			}
			
		}
		
		
		// 카테고리 관리 메뉴 조회
		getCategoryManagement = function(blogOwnerId) {
			document.location.href = "<c:url value='/socialpack/socialblog/setting/socialblogSettingHome.do?blogOwnerId=' />"+blogOwnerId ;
		};
		
		// 카테고리 관리 메뉴 조회
		getLayoutManagement = function(blogOwnerId) {
			document.location.href = "<c:url value='/socialpack/socialblog/setting/socialLayoutManage.do?blogOwnerId=' />"+blogOwnerId ;
		};
		
		// 카테고리 관리 메뉴 조회
		getBackgroundManagement = function(blogOwnerId) {
			document.location.href = "<c:url value='/socialpack/socialblog/setting/socialBackgroundManage.do?blogOwnerId=' />"+blogOwnerId ;
		};
		
		// 카테고리 관리 메뉴 조회
		getVisitorManagement = function(blogOwnerId) {
			document.location.href = "<c:url value='/socialpack/socialblog/setting/socialVisitorManage.do?blogOwnerId=' />"+blogOwnerId ;
		};
		

		$(document).ready(function() {
			
			new iKEP.Validator("#categoryForm", validOptionsCategory);
			
			$("#btnSave").click(function() {
				$("#categoryForm").trigger("submit");
			});
			
			
			$("#categorys").sortable({			
				start: function(e,ui){
					clearSelect();
	            },
				stop: function(e,ui){
					sortArray();
	            },
	            placeholder: "ui-state-highlight",
				revert:true
			});
			
			$("#categoryName").bind("keydown", function(event) {
				if(event.which == 13) {
					$("#btnSave").trigger("click");
				}
			});
			
			$("#categorys > li").live({
			    "click" : function(event){
			    	selectItem(event.target);
			    }
			});
			
			$( "#categorys" ).disableSelection();
			
			$("#btnAdd").click(function() {
				clearSelect();
				addCatetory();
			});
			
			$("#btnModify").click(function() {
				modifyCatetory();
			});
			
			$("#btnDelete").click(function() {
				deleteCatetory();
			});
			
			$("#btnCancel").click(function() {
				clearSelect();
				dialog.dialog("close");
			});
			
			$.template("tmplCategoryOrder", '<li class="ui-state-default">\${id}</li>');
			$.template("tmplCategoryItem", '<li class="ui-state-default" id="\${categoryId}"><span class="ui-icon ui-icon-arrowthick-2-n-s" name="\${name}"></span>\${name}</li>');
			$.template("tmplCategoryName", '<span class="ui-icon ui-icon-arrowthick-2-n-s" name="\${name}"></span>\${name}');

		});
		
	})(jQuery);


</script>

		<!-- Modal window Start -->
		<!-- 사용시class="none"으로 설정 -->
		<div class="" id="layer_p" title="<ikep4j:message pre='${preManage}' key='title' />">
		
			<div class="blog_layout">
				
				<!--leftMenu Start-->
				<div class="floatLeft blog_left">
					<h1 class="none"><ikep4j:message pre='${preManageMenu}' key='leftmenu' /></h1>
					<div class="blog_leftmenu">	
						<ul>
							<li><a onclick="getCategoryManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageMenu}' key='title' /></a>
								<ul>
									<li class="licurrent"><a onclick="getCategoryManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageMenu}' key='category.title' /></a></li>
								</ul>
							</li>
							<li><a onclick="getLayoutManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageDesign}' key='title' /></a>
								<ul>
									<li><a onclick="getLayoutManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageDesign}' key='layout.title' /></a></li>
									<li><a onclick="getBackgroundManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageDesign}' key='background.title' /></a></li>
								</ul>
							</li>
							<li><a onclick="getVisitorManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageStat}' key='title' /></a>
								<ul>
									<li><a onclick="getVisitorManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageStat}' key='visitor.title' /></a></li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
				<!--//leftMenu End-->
				
				<div class="floatRight" style="width:724px;">
					<div class="blog_layout_contents">
						
						<!--blog_backSelect Start-->
						<div class="blog_layout_Stitle">
							<ul class="floatLeft">
		
								<li><span><ikep4j:message pre='${preManageMenu}' key='category.title' /></span></li>
							</ul>
							<div class="floatRight"></div>
							<div class="clear"></div>
						</div>
						<!--//blog_backSelect End-->
						
						<input name="blogOwnerId" type="hidden" value="<c:out value='${socialBlog.ownerId}'/>" />
						<input name="blogId" type="hidden" value="<c:out value='${socialBlog.blogId}'/>" />
						
						<div id="divCategoryItemControll" style="display:none;">
							<form id="categoryForm" name="categoryForm" action="" method="post" onsubmit="return false;">
								<input type="hidden" name="categoryId" id="categoryId" value="" />
								<!--blockDetail Start-->
								<div class="blockDetail">
									<table summary="<ikep4j:message pre='${preManageMenu}' key='list.addCategoryName' />"> 
										<caption></caption>
										<tbody>
											<tr>
												<th scope="row" width="28%"><ikep4j:message pre='${preManageMenu}' key='list.categoryName' /></th>
												<td width="*">
													<input id="categoryName" name="categoryName" title="categoryName" class="inputbox w95" type="text" />
												</td>
											</tr>						
										</tbody>
									</table>
								</div>
								<!--//blockDetail End-->
								
								<!--blockButton Start-->
								<div class="blockButton"> 
									<ul>
										<li><a id="btnSave" class="button" href="#a" ><span><ikep4j:message pre='${preButton}' key='save' /></span></a></li>
										<li><a id="btnCancel" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel' /></span></a></li>
									</ul>
								</div>
								<!--//blockButton End-->
							</form>
						</div>
						
						<!--blockDetail Start-->	
						<div class="blockTableInfo"><ikep4j:message pre='${preManageMenu}' key='category.comment' /></div>				
						<div class="blockDetail clear">
							<table summary="<ikep4j:message pre='${preManageMenu}' key='category.title' />">
								<caption></caption>
								<tbody>
									<tr>
										<th scope="col" width="150px" class="textCenter"><ikep4j:message pre='${preManageMenu}' key='list.sortOrder' /></th>
										<th scope="col" width="*" class="textCenter"><ikep4j:message pre='${preManageMenu}' key='list.categoryName' /></th>
									</tr>
								</tbody>						
							</table>
							<div style="min-width:700px;width:100%;">
								<div style="float:left;width:162px;">
									<ul class="orderBox">
									<!-- <li class="ui-state-default">순번</li>  -->
									<c:forEach begin="1" end="${size}" varStatus="loopCount">
										<li class="ui-state-default">${loopCount.count}</li>
									</c:forEach>
									</ul>
								</div>
								<div style="float:left;width:532px;">
									<ul class="list-selectable" id="categorys">
									<!--<li class="ui-state-default ui-state-disabled">Category 명</li>-->
									<c:forEach var="category" items="${socialCategoryList}" varStatus="loopCount">
										<li class="ui-state-default" id="${category.categoryId}"><span class="ui-icon ui-icon-arrowthick-2-n-s" name="${category.categoryName}"></span>${category.categoryName}</li>
									</c:forEach>	
									</ul>
								</div>
								<div class="clear"></div>
							</div>
						</div>
						<!--//blockDetail End-->	
						
						<!--blockButton Start-->
						<div class="blockButton"> 
							<ul>
								<li><a id="btnAdd" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='create' /></span></a></li>
								<li id="modifyLi" style="display:none;"><a id="btnModify" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='update' /></span></a></li>
								<li id="deleteLi" style="display:none;"><a id="btnDelete" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></li>							
							</ul>
						</div>
						<!--//blockButton End-->
					</div>
				</div>
				
			</div>
			
		</div>	
		<!-- //Modal window End -->
