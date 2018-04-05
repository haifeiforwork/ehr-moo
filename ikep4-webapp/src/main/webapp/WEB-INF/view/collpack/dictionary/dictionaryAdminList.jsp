<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%pageContext.setAttribute("crlf", "\r\n"); %>

<c:set var="preMessage"    	value="message.collpack.dictionary" />
<c:set var="preForm"    	value="ui.collpack.dictionary.dictionaryForm" />
<c:set var="preView"    	value="ui.collpack.dictionary.dictionaryView" />
<c:set var="preMenu"    	value="ui.collpack.dictionary.dictionaryMenu" />
<c:set var="preAdmin"    	value="ui.collpack.dictionary.dictionaryAdminList" />

<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
<!--
(function($) { 
	$(document).ready(function() { 
		$("#dictionarys").sortable({			
			start: function(e,ui){
				clearSelect();
            },
			stop: function(e,ui){
				sortArray();
            },
            placeholder: "ui-state-highlight",
			revert:true
		});		
		$("#dictionarys > li").live({
		    "click" : function(event){
		    	selectItem(event.target);
		    }
		});
		$( "#dictionarys" ).disableSelection();
		
		$("#insertButton").click(function() {
			clearSelect();
			addCatetory();
		});	
		$("#modifyButton").click(function() {
			modifyCatetory();
		});		
		$("#deleteButton").click(function() {
			deleteCatetory();
		});		
		$("#btnCancel").click(function() {
			clearSelect();
			dialog.dialog("close");
		});	
		
		$.template("tmplDictionaryOrder", '<li class="ui-state-default">\${id}</li>');
		$.template("tmplDictionaryItem", '<li class="ui-state-default" id="dictionary_\${dictionaryId}"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>\${dictionaryInfo}</li>');
		$.template("tmplDictionaryName", '<span class="ui-icon ui-icon-arrowthick-2-n-s" name="\${name}"></span>\${name}');
		
		$("#btnSave").click(function() {
			$("#categoryForm").submit();
		});
		

		sortArray = function(){		
			$("#dictionarys").sortable("refresh");
			var result = $("#dictionarys").sortable("toArray");
			
			var resultText =result.join(",");
			
			$jq.ajax({    
				url : "applyDictionaryOrder.do",     
				data : {dictionaryIdes:resultText.replaceAll("dictionary_","")},     
				type : "post",     
				success : function(result) {    

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
				currDictionaryId = $(item).attr("id").replaceAll("dictionary_","");
				showBtn();
			}
		}
		
		isToggle = function(item){		
			if(currDictionaryId == null){
				return false;			
			}else{
				if(currDictionaryId == $(item).attr("id").replaceAll("dictionary_","")){
					return true;
				}else{
					return false;
				}
			}
		}
		unselect = function() {		
			$("#dictionarys > li").removeClass("ui-selected");
		}
		
		addCatetory = function(){
			showDictionaryItemDialog("add");
		}
		
		modifyCatetory = function(){
			showDictionaryItemDialog("modify");
		}	
		deleteCatetory = function(){
			if(confirm("<ikep4j:message pre='${preMessage}' key='confirmDeleteDictionary' />") == true){
				document.location.href="deleteDictionaryItem.do?dictionaryId="+currDictionaryId;
			}
		}		
		showBtn = function(){
			$("#modifyButton").show();
			$("#deleteButton").show();
		}
		
		hideBtn = function(){
			$("#modifyButton").hide();
			$("#deleteButton").hide();
		}
		
		clearSelect = function(){
			currDictionaryId = null;
			unselect();
			hideBtn();
		}
		
		var currDictionaryId = null;
		var dialog = null;
		var categoryItemAction = "";
		function showDictionaryItemDialog(flag) {
			categoryItemAction = flag;
			var dialogTitle = categoryItemAction == "add" ? "<ikep4j:message pre='${preAdmin}' key='titleAdd' />" : "<ikep4j:message pre='${preAdmin}' key='titleModify' />";
			
			if(dialog == null) {
				dialog = $("#divDictionaryItemControll").dialog({
					title : dialogTitle,
					modal : true,
					resizable : false,
					width : 450,
					height : 130,
					open : function() {
						switch(categoryItemAction) {
							case "add" :
								$("input[name=dictionaryName]", this).val("");
								break;
							case "modify" :
								var title = $("#dictionary_"+currDictionaryId).text();
								title = title.substring(0,title.indexOf("("));
								$("input[name=dictionaryName]", this).val(title);
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

        //validation   
        var validOptions = {
			rules : {
				dictionaryName :	{
					required : true,
					maxlength  : 10
				}
			},
			messages : {
				dictionaryName : {
					required : "<ikep4j:message key='NotEmpty.dictionary.dictionaryName'/>",
					maxlength : "<ikep4j:message key='Max.dictionary.dictionaryName' arguments='10'/>",
					direction:"bottom"
				}
			},
			submitHandler : function() {
				var dictionaryName = $("input[name=dictionaryName]", "#divDictionaryItemControll").val();
				var dictionaryInfo = dictionaryName+"(0)"; 
				var lastCnt = $(".orderBox li:last-child").text();
				lastCnt++;
				switch(categoryItemAction) {
					case "add" :
						//ajax : save
						$jq.ajax({    
							url : "createDictionaryItem.do",     
							data : {dictionaryName:dictionaryName, sortOrder:lastCnt},     
							type : "post",     
							success : function(result) {    
								// ajax success callback process					
								var data = {id:lastCnt, name:dictionaryName, dictionaryInfo:dictionaryInfo, dictionaryId:result};
								$(".orderBox").append($.tmpl("tmplDictionaryOrder",data));
								$("#dictionarys").append($.tmpl("tmplDictionaryItem",data));
							},
							error : function(event, request, settings){
							 	alert("error");
							}
						});
						
										
						break;
					case "modify" :
						//ajax : save
						$jq.ajax({    
							url : "createDictionaryItem.do",     
							data : {dictionaryName:dictionaryName, dictionaryId:currDictionaryId},     
							type : "post",     
							success : function(result) {    
								// ajax success callback process					
								var data = {name:dictionaryName};
								$("#"+currDictionaryId).empty();
								$("#"+currDictionaryId).append($.tmpl("tmplDictionaryName",data));	
								clearSelect();
								document.location.reload();	
							},
							error : function(event, request, settings){
							 	alert("error");
							}
						});
						break;
				}
				
				$("#divDictionaryItemControll").dialog("close");
			}
		};  
		new iKEP.Validator("#categoryForm", validOptions);		
		
	});

	
})(jQuery); 


String.prototype.replaceAll = function( searchStr, replaceStr ){
	return this.split( searchStr ).join( replaceStr );
}

//-->
</script> 
<div id="divDictionaryItemControll" style="display:none;">
	<form id="categoryForm" method="post" action="">
	<input type="hidden" name="dictionaryId" id="dictionaryId" value=""/>
		<!--blockDetail Start-->
		<div class="blockDetail">
			<table summary="<ikep4j:message pre='${preAdmin}' key='titleAdd' />">
				<caption></caption>
				<tbody>
					<tr>
						<th scope="row" width="28%"><ikep4j:message pre='${preForm}' key='formDictionaryName' /></th>
						<td width="*">
							<input name="dictionaryName" title="dictionaryName" class="inputbox w95" type="text" />
						</td>
					</tr>						
				</tbody>
			</table>
		</div>
		<!--//blockDetail End-->

		
		<!--blockButton Start-->
		<div class="blockButton" style="padding-top:20px;"> 
			<ul>
				<li><a id="btnSave" class="button" href="#a" ><span><ikep4j:message pre='${preView}' key='viewSave' /></span></a></li>
				<li><a id="btnCancel" class="button" href="#a"><span><ikep4j:message pre='${preView}' key='viewCancel' /></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->
	</form>
</div>	
				<h1 class="none">contents area</h1>

				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${preMenu}' key='menuAdmin' /></h2>					
				</div>
				<!--//pageTitle End-->	
								
				<!--blockListTable Start-->
				<div class="blockTableInfo"><ikep4j:message pre='${preAdmin}' key='help' /></div>
				<div class="blockDetail clear">
					<table summary="<ikep4j:message pre='${preMenu}' key='menuAdmin' />">
						<caption></caption>
						<tbody>
							<tr>
								<th scope="col" width="18%" class="textCenter"><ikep4j:message pre='${preAdmin}' key='num' /></th>
								<th scope="col" width="*" class="textCenter"><ikep4j:message pre='${preForm}' key='formDictionaryName' /></th>
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
							<ul class="list-selectable" id="dictionarys">
							<c:forEach var="dictionaryAdminItem" items="${dictionaryAdminList}" varStatus="dictionaryAdminItemLoopCount">
								<li class="ui-state-default" id="dictionary_${dictionaryAdminItem.dictionaryId}"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>${dictionaryAdminItem.dictionaryName}(${dictionaryAdminItem.wordCount})</li>
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
						<li><a class="button" href="#a" id="insertButton"><span><ikep4j:message pre='${preForm}' key='formCreateBtn' /></span></a></li>
						<li><a class="button" href="#a" id="modifyButton" style="display:none;"><span><ikep4j:message pre='${preView}' key='viewModify' /></span></a></li>
						<li><a class="button" href="#a" id="deleteButton" style="display:none;"><span><ikep4j:message pre='${preView}' key='viewDelete' /></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->
		