<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preResource" 	value="ui.collpack.forum.categoryList" />

<script type="text/javascript">
//<![CDATA[

	var validOptions = {
		rules : {
			categoryName : {
				required : true,
				maxlength  : 100
			}
		},
		messages : {
			categoryName : {
				required : "<ikep4j:message key='NotEmpty.qna.categoryName'/>"
				,maxlength : "<ikep4j:message key='Size.qna.categoryName'/>"
				,direction : "bottom"
					
			}
		},
		notice : {
			//categoryName : "<ikep4j:message key='NotEmpty.qna.categoryNameNotice'/>"
			//,direction : "bottom"
		},
		submitHandler : function(form) {
			
			submitAction();
			
		}
		
	};
	
	var unselect;
	(function($){
		
		$(document).ready(function() {
		
			new iKEP.Validator('#categoryForm', validOptions);
			
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
			$.template("tmplCategoryItem", '<li class="ui-state-default" id="c_\${categoryId}"><span class="ui-icon ui-icon-arrowthick-2-n-s" name="\${name}"></span>\${name}</li>');
			$.template("tmplCategoryName", '<span class="ui-icon ui-icon-arrowthick-2-n-s" ></span>\${name}');
			
			$("#btnSave").click(function() {
				$("#categoryForm").submit();
			});
			
		});
			
		//sort완료 후 처리
		sortArray = function(){		
			$("#categorys").sortable("refresh");
			//iKEP.debug($("#categorys").sortable("serialize"));
			var result = $("#categorys").sortable("toArray");
			
			var resultText =result.join(",").replaceAll("c_","");
			
			$jq.ajax({    
				url : "applyCategoryOrder.do",     
				data : {categoryIdes:resultText},     
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
				currCategoryId = $(item).attr("id").replaceAll("c_","");
				showBtn();
			}
		}
		
		isToggle = function(item){		
			if(currCategoryId == null){
				return false;			
			}else{
				if(currCategoryId == $(item).attr("id").replaceAll("c_","")){
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
			
			document.location.href="deleteCategory.do?categoryId="+currCategoryId;
			
		}
		
		clearSelect = function(){
			currCategoryId = null;
			unselect();
			hideBtn();
		}
		
		submitAction = function(){
			var categoryName = $("input[name=categoryName]", "#divCategoryItemControll").val();
			var lastCnt = $(".orderBox li:last-child").text();
			lastCnt++;
			switch(categoryItemAction) {
				case "add" :
					//ajax : save
					$jq.ajax({    
						url : "createCategory.do",     
						data : {categoryName:categoryName},     
						type : "post",     
						success : function(result) {    
							// ajax success callback process					
							var data = {id:lastCnt, name:categoryName, categoryId:result};
							$(".orderBox").append($.tmpl("tmplCategoryOrder",data));
							$("#categorys").append($.tmpl("tmplCategoryItem",data));
							document.location.reload();		
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
						data : {categoryName:categoryName, categoryId:currCategoryId},     
						type : "post",     
						success : function(result) {    
							// ajax success callback process					
							var data = {name:categoryName};
							$("#"+currCategoryId).empty();
							$("#"+currCategoryId).append($.tmpl("tmplCategoryName",data));	
							clearSelect();
							document.location.reload();	
						},
						error : function(event, request, settings){
						 	alert("error");
						}
					});
					break;
			}
			
			$("#divCategoryItemControll").dialog("close");
		}
		
		var currCategoryId = null;
		var dialog = null;
		var categoryItemAction = "";
		function showCategoryItemDialog(flag) {
			categoryItemAction = flag;
			var dialogTitle = categoryItemAction == "add" ? "<ikep4j:message pre='${preResource}' key='categoryForm.summary'/>" : "<ikep4j:message pre='${preResource}' key='categoryForm.summaryUpdate'/>";
			
			if(dialog == null) {
				dialog = $("#divCategoryItemControll").dialog({
					title : dialogTitle,
					modal : true,
					resizable : false,
					width : 400,
					height : 130,
					open : function() {
						switch(categoryItemAction) {
							case "add" :
								$("input[name=categoryName]", this).val("");
								break;
							case "modify" :
								var categoryName = $("#c_"+currCategoryId).text();
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
		
	})(jQuery);
	
	String.prototype.replaceAll = function( searchStr, replaceStr ){
		return this.split( searchStr ).join( replaceStr );
	}

//]]>
</script>
<div id="divCategoryItemControll" style="display:none;">
	<form id="categoryForm" method="post" onsubmit="return false;" action="">
	<input type="hidden" name="categoryId" id="categoryId" value=""/>
		<!--blockDetail Start-->
		<div class="blockDetail">
			<table summary="Category">
				<caption></caption>
				<tbody>
					<tr>
						<th scope="row" width="28%"><label for="categoryName"><ikep4j:message pre='${preResource}' key='categoryForm.categoryName'/></label></th>
						<td width="*"><div>
							<input name="categoryName" id="categoryName"  class="inputbox w95" type="text" />
							</div>
						</td>
					</tr>						
				</tbody>
			</table>
		</div>
		<!--//blockDetail End-->
		
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a id="btnSave" class="button" href="#a" title="<ikep4j:message pre='${preResource}' key='categoryForm.save'/>"><span><ikep4j:message pre='${preResource}' key='categoryForm.save'/></span></a></li>
				<li><a id="btnCancel" class="button" href="#a" title="<ikep4j:message pre='${preResource}' key='categoryForm.cancel'/>"><span><ikep4j:message pre='${preResource}' key='categoryForm.cancel'/></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</form>
</div>

<h1 class="none">Contents Area</h1>
 
<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3><ikep4j:message pre='${preResource}' key='pageLocation.title'/></h3>
</div>
<!--//subTitle_2 End-->


<!--blockDetail Start-->	
<div class="blockDetail clear">
	<table summary="category Table">
		<caption></caption>
		<tbody>
			<tr>
				<th scope="col" width="17%" class="textCenter"><ikep4j:message pre='${preResource}' key='list.order'/></th>
				<th scope="col" width="*" class="textCenter"><ikep4j:message pre='${preResource}' key='categoryForm.categoryName'/></th>
			</tr>
		</tbody>						
	</table>
	<div style="min-width:700px;width:100%;">
		<div style="float:left;width:18%;">
			<ul class="orderBox">
			<c:forEach begin="1" end="${size}" varStatus="loopCount">
				<li class="ui-state-default">${loopCount.count}</li>
			</c:forEach>
			</ul>
		</div>
		<div style="float:left;width:82%;">
			<ul class="list-selectable" id="categorys">
			<c:forEach var="category" items="${categoryList}" varStatus="loopCount">
				<li class="ui-state-default" id="c_${category.categoryId}"><span class="ui-icon ui-icon-arrowthick-2-n-s" ></span>${category.categoryName}</li>
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
		<li><a id="btnAdd" class="button" href="#a" title="<ikep4j:message pre='${preResource}' key='list.create'/>"><span><ikep4j:message pre='${preResource}' key='list.create'/></span></a></li>
		<li id="modifyLi" style="display:none;"><a id="btnModify" class="button" href="#a" title="<ikep4j:message pre='${preResource}' key='list.update'/>"><span><ikep4j:message pre='${preResource}' key='list.update'/></span></a></li>
		<li id="deleteLi" style="display:none;"><a id="btnDelete" class="button" href="#a" title="<ikep4j:message pre='${preResource}' key='list.delete'/>"><span><ikep4j:message pre='${preResource}' key='list.delete'/></span></a></li>							
	</ul>
</div>
<!--//blockButton End-->

<!--directive Start-->
<div class="directive"> 
	<ul>
		<li><span><ikep4j:message pre='${preResource}' key='list.info'/></span></li>																		
	</ul>
</div>
<!--//directive End-->
