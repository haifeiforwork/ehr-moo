<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preForm" 		value="ui.support.rss.categoryList.categoryForm" />
<c:set var="preLocation" 	value="ui.support.rss.categoryList.pageLocation" />
<c:set var="preList" 		value="ui.support.rss.categoryList.list" />

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
				required : "<ikep4j:message key='NotEmpty.rss.categoryName'/>"
				,maxlength : "<ikep4j:message key='Size.rss.categoryName'/>"
				,direction : "bottom"
					
			}
		},
		notice : {
			//categoryName : "<ikep4j:message key='NotEmpty.rss.categoryNameNotice'/>"
			//,direction : "bottom"
		},
		submitHandler : function(form) {
			
			submitAction();
			
		}
		
	};
       	
	var unselect;
	var currCategoryId = null;
	var dialog = null;
	var categoryItemAction = "";
	
	(function($){
		
		$(document).ready(function() {

			 new iKEP.Validator('#categoryForm', validOptions);
			//alert("뭐니");
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
			
			var resultText =(result.join(",")).split("c_").join("");
			
			$jq.ajax({    
				url : '<c:url value="/support/rss/channelCategory/applyCategoryOrder.do" />',     
				data : {categoryIdes:resultText},     
				type : "post",     
				success : function(result) {    
					//alert("success");
					//document.location.reload();	
					getChannelSummary();
				},
				error : function(event, request, settings){
				 	alert("error");
				}
			}); 
		}
		
		selectItem = function(item) {
			unselect();
			if($(item).attr("id")){
				
			}else{
				item =$(item).parent();
			}
			if(isToggle(item)){
				clearSelect();
			}else{
				$(item).addClass("ui-selected");
				currCategoryId = ($(item).attr("id")).split("c_").join("");
				showBtn();
			}
		}
		
		isToggle = function(item){	
		
			if(currCategoryId == null){
				return false;			
			}else{
				
				if(currCategoryId == ($(item).attr("id")).split("c_").join("")){
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
			
			$jq.ajax({    
                url : '<c:url value="/support/rss/channelCategory/deleteCategory.do"/>',     
                data : {categoryId:currCategoryId},     
                type : "post",     
                success : function(result) { 
                	 $(".orderBox li:last-child").remove();
                	 $("#c_"+currCategoryId).remove();
                	 clearSelect();
                     getChannelSummary();
                },
                error : function(event, request, settings){
                	alert("error");
                }
            });
			
			//document.location.href='<c:url value="/support/rss/channelCategory/deleteCategory.do?categoryId=" />' + currCategoryId;   	
		}
		
		clearSelect = function(){
			currCategoryId = null;
			unselect();
			hideBtn();
		}
		submitAction = function(){

			//alert($("#categoryName").val());
			var lastCnt = $(".orderBox li:last-child").text();
			lastCnt++;
			switch(categoryItemAction) {
				case "add" :
					//ajax : save
					$jq.ajax({    
						url : '<c:url value="/support/rss/channelCategory/createCategory.do" />',     
						data : {categoryName:$("#categoryName").val()},     
						type : "post",     
						success : function(result) {    
							// ajax success callback process					
							var data = {id:lastCnt, name:$("#categoryName").val(), categoryId:result};
							$(".orderBox").append($.tmpl("tmplCategoryOrder",data));
							$("#categorys").append($.tmpl("tmplCategoryItem",data));
							clearSelect();
							getChannelSummary();	
							$("#categoryName").val("");
							$("#categoryId").val("");
							dialog.dialog("close");
							dialog=null;

						},
						error : function(event, request, settings){
						 	alert("error");
						}
					});
					
									
					break;
				case "modify" :
					//ajax : save
					$jq.ajax({    
						url : '<c:url value="/support/rss/channelCategory/createCategory.do" />',     
						data : {categoryName:$("#categoryName").val(), categoryId:currCategoryId},     
						type : "post",     
						success : function(result) {    
							// ajax success callback process					
							var data = {name:$("#categoryName").val()};
							$("#c_"+currCategoryId).empty();
							$("#c_"+currCategoryId).append($.tmpl("tmplCategoryName",data));	
							clearSelect();
							getChannelSummary();
							$("#categoryName").val("");
							$("#categoryId").val("");
							dialog.dialog("close");
							dialog=null;

							
						},
						error : function(event, request, settings){
						 	alert("error");
						}
					});
					break;
			}

		}
		

		function showCategoryItemDialog(flag) {
			dialog = null;
			categoryItemAction = flag;
			var dialogTitle = categoryItemAction == "add" ? "<ikep4j:message pre='${preForm}' key='summary'/>" : "<ikep4j:message pre='${preForm}' key='summaryUpdate'/>";
			
			dialog = $("#divCategoryItemControll").dialog({
				title : dialogTitle,
				modal : true,
				resizable : false,
				width : 400,
				height : 130,
				open : function() {
					switch(categoryItemAction) {
						case "add" :
							$("#categoryName").val("");
							break;
						case "modify" :
							$("#categoryName").val($("#c_"+currCategoryId).text());
							break;
					}
				}
			});
				
		}
		
	})(jQuery);


	String.prototype.replaceAll = function( searchStr, replaceStr ){
		return this.split( searchStr ).join( replaceStr );
	}

	//]]>
</script>
<div id="divCategoryItemControll" style="display:none;">
	<form id="categoryForm" method="post" onsubmit="return false;" action="">
	<input type="hidden" name="categoryId" id="categoryId" value="" />
		<!--blockDetail Start-->
		<div class="blockDetail">
			<table summary="<ikep4j:message pre='${preForm}' key='summary'/>">
				<caption></caption>
				<tbody>
					<tr>
						<th scope="row" width="28%"><label for="categoryName"><ikep4j:message pre='${preForm}' key='categoryName'/></label></th>
						<td width="*"><div>
							<input name="categoryName" id="categoryName" class="inputbox w95" type="text" />
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
				<li><a id="btnSave" class="button" href="#a" title="<ikep4j:message pre='${preForm}' key='save'/>"><span><ikep4j:message pre='${preForm}' key='save'/></span></a></li>
				<li><a id="btnCancel" class="button" href="#a" title="<ikep4j:message pre='${preForm}' key='cancel'/>"><span><ikep4j:message pre='${preForm}' key='cancel'/></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</form>
</div>

<h1 class="none">contents Area</h1>
 
<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preLocation}' key='title'/></h2>
</div>
<!--//pageTitle End-->	

<!--blockDetail Start-->	
			
<div class="blockDetail clear">
	<table summary="<ikep4j:message pre='${preList}' key='summary'/>">
		<caption></caption>
		<tbody>
			<tr>
				<th scope="col" width="17%" class="textCenter"><ikep4j:message pre='${preList}' key='order'/></th>
				<th scope="col" width="*" class="textCenter"><ikep4j:message pre='${preList}' key='categoryName'/></th>
			</tr>
		</tbody>						
	</table>
	<div style="min-width:700px;width:100%;">
		<div style="float:left;width:17%;">
			<ul class="orderBox">
			<c:forEach begin="1" end="${size}" varStatus="loopCount">
				<li class="ui-state-default">${loopCount.count}</li>
			</c:forEach>
			</ul>
		</div>
		<div style="float:left;width:83%;">
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
		<li><a id="btnAdd" class="button" href="#a" title="<ikep4j:message pre='${preList}' key='create'/>"><span><ikep4j:message pre='${preList}' key='create'/></span></a></li>
		<li id="modifyLi" style="display:none;"><a id="btnModify" class="button" href="#a" title="<ikep4j:message pre='${preList}' key='update'/>"><span><ikep4j:message pre='${preList}' key='update'/></span></a></li>
		<li id="deleteLi" style="display:none;"><a id="btnDelete" class="button" href="#a" title="<ikep4j:message pre='${preList}' key='delete'/>"><span><ikep4j:message pre='${preList}' key='delete'/></span></a></li>							
	</ul>
</div>
<!--//blockButton End-->


<!--directive Start-->
<div class="directive"> 
	<ul>
		<li><span><ikep4j:message pre='${preList}' key='info'/></span></li>																		
	</ul>
</div>
<!--//directive End-->	
