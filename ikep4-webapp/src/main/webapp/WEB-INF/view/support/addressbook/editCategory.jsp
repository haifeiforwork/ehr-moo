<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" /> 
<c:set var="prePrivate"    value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />

<c:set var="preAddrList"  value="ui.support.addressbook.addrgroup.list" />
<c:set var="preAddrMessage" value="message.support.addressbook.addrgroup" />
<c:set var="preAddrButton"  value="ui.support.addressbook.addrgroup.button" />

<c:set var="preSumMessage"  value="message.support.addressbook.summary" />

<c:set var="preDetail"  value="ui.lightpack.board.boardAdmin.createBoardView.detail" />
<c:set var="preMessage" value="message.lightpack.common.boardAdmin" />
<c:set var="preButton"  value="ui.lightpack.common.button" />   
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/approval/admin/apprForm.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/base/js/wceditor/css/editor.css'/>"/>		
<script type="text/javascript" src="<c:url value="/base/js/units/support/addressbook/addressbook.js"/>"></script>
<script type="text/javascript">
<!--
(function($) {
	
	var delIdList        = [];
	var updateNameList   = [];
	var updateIdList     = [];
    var alignList =[];
    
    selectedAll = function (selectName){
		var $sel = $jq("select[name="+selectName+"]");
		if($sel) {
			$jq.each($sel.children(), function() {
				this.selected = true;
			});
		}
	};
	
	$jq(document).ready(function() {
		// 화면 로딩시 각각 페이지 호출 시작
		//Addressbook.getLeftMenuView();
		Addressbook.getPrivateAddrgroupView('${addrgroupId}');
		Addressbook.getPrivatePaddrgroupView('${addrgroupId}');
		Addressbook.getPublicAddrgroupView('${addrgroupId}');
		Addressbook.getTeamAddrgroupView('${addrgroupId}');
		iKEP.setLeftMenu();
		// 화면 로딩시 각각 페이지 호출 종료
		
		$("#itemFormButton a").click(function(){
		    switch (this.id) {
                //case "saveButton":
                    //$("#itemForm").trigger("submit");
                   // $("#itemForm").attr("action", "<c:url value='/lightpack/board/boardAdmin/createCategoryBoard.do'/>");
    			   // break;
                case "closeButton":
                	window.close();
                    break;
                default:
                    break;
            }   
        });
	    
		$jq("#saveButton").click(function() {   
    		var addNameList = [];
    		
    		$jq.each($jq("#options > option"),function(index){
    			if($jq(this).val() == '') {
    				addNameList.push($jq(this).text());
    			}
    			//alert($jq(this).text());
    			alignList.push($jq(this).text());
    		});

    		$jq("#addNameList").val(addNameList);
    		$jq("#delIdList").val(delIdList);
    		$jq("#updateIdList").val(updateIdList);
    		$jq("#updateNameList").val(updateNameList);
    		$jq("#alignList").val(alignList);
    		
    		$.ajax({
				url : "<c:url value='/portal/admin/code/category/createCategoryBoard.do?boardId=100000000001' />",
				data : $("#itemForm").serialize(),
				type : "post",
				dataType : "html",
				success : function(result) {
					if(result == 'success') {
						alert("<ikep4j:message pre='${preMessage}' key='sendMessage'/>");
						
						// 부모 함수 호출
						if(opener) {
							if(opener.changeCategoryList) {
								//var optionIdList   = [];
								var optionTextList = [];
								/*
								$jq.each($jq("#options > option"),function(index){
									optionIdList.push($jq(this).val());
					    		});
								*/		
								$jq.each($jq("#options > option"),function(index){
									optionTextList.push($jq(this).text());
					    		});
								
								opener.changeCategoryList(optionTextList);
							}
						}
						
						window.close();
					} else {
						alert('error');
					}
				},
				error : function() {alert('error');}
			});
    	});  
	    
	    $("#updateOptionButton").hide();
        $("#initOptionButton").hide();

		//- add option  
        $("#addOptionButton").click(function() {
        	
            var optionText  = $.trim($("#itemForm input[name=optionText]").val());
            //iKEP.debug(optionText);
            if(optionText=="알림"){//[공지] 대신 [알림]으로 변경
            	alert("<ikep4j:message pre='${preMessage}' key='noticeMessage'/>");
            	return;
            }
            
            if(optionText=="") return;
    		$("#itemForm select[name=options]").append("<option value=''>"+optionText+"</option>");
    		setOptionForm("", "", false);
        });

		//- remove option  
        $("#removeOptionButton").click(function() {
        	$jq.each($("#itemForm select[name=options] option:selected"),function(index){
        		if($jq(this).val() != '') {
        			delIdList.push($jq(this).val());
        		}
        		$jq(this).remove();
    		});
        });   
		
      //- update option  
        $("#updateOptionButton").click(function() { 	
            var optionText  = $.trim($("#itemForm input[name=optionText]").val());
		    		
    		$jq.each($("#itemForm select[name=options] option:selected"),function(index){
        		if($jq(this).text() != '') {			
        			//iKEP.debug("3333333333333333333");
            		//iKEP.debug(optionText);              		
            		//iKEP.debug("44444444444444444");
            		//iKEP.debug($jq(this).val());      		
        			updateNameList.push(optionText);
        			updateIdList.push($jq(this).val());
        		}
        		
    		});
                  
            if(optionText=="") return;
            
            var optionValue = $("#itemForm input[name=optionValue]").val();
            var checked     = $("#itemForm input[name=defaultValue]").attr("checked")?" class=\"selectedOption\"":"";

            if(checked!="") $("#itemForm select[name=options] option.selectedOption").removeClass("selectedOption");  		
		
    		$("#itemForm select[name=options] option:eq(" + editIndex + ")").replaceWith("<option value=\""+optionValue+"\"" + checked + ">"+optionText+"</option>");
    		setOptionForm("", "", false);
    		$("#addOptionButton").show();
            $("#updateOptionButton").hide();
            $("#initOptionButton").hide();

        });

		//- init option  
        $("#initOptionButton").click(function() {
            setOptionForm("", "", false);
            $("#addOptionButton").show();
            $("#updateOptionButton").hide();
            $("#initOptionButton").hide();
        });
        
		//- edit option  
        $("#editOptionButton").click(function() {
            editIndex = $("#itemForm select[name=options] option").index($("#itemForm select[name=options] option:selected"));
            if(editIndex!=-1){
                setOptionForm($("#itemForm select[name=options] option:selected").text(), $("#itemForm select[name=options] option:selected").val(), $("#itemForm select[name=options] option:selected").hasClass("selectedOption"));
                $("#addOptionButton").hide();
                $("#updateOptionButton").show();
                $("#initOptionButton").show();
            }     
        });   

		//- move up option 
        $("#moveUpButton").click(function() {
            if($("#itemForm select[name=options] option:selected").prevAll().size()>0){
                $("#itemForm select[name=options] option:selected").insertBefore($("#itemForm select[name=options] option:selected").first().prev());
            }
        });   

		//- move down option 
        $("#moveDownButton").click(function() {
           if($("#itemForm select[name=options] option:selected").nextAll().size()>0){
                $("#itemForm select[name=options] option:selected").insertAfter($("#itemForm select[name=options] option:selected").last().next());
            }
        });   
		
      	//- option text event   
        $("#itemForm input[name=optionText]").keypress(function(event){
            if(event.which == 13) {
                if($("#addOptionButton").css("display")=="none")
            	    $("#updateOptionButton").click();
                else
                    $("#addOptionButton").click();
            }
        });
		
	});
	
	goAddrbookMain = function() {
		document.location.href = "<c:url value='/support/addressbook/addressbookHome.do'/>" ;
	};
	
	setOptionForm = function(txt, val, b){
        $("#itemForm input[name=optionText]").val(txt);
        $("#itemForm input[name=optionValue]").val(val);
        $("#itemForm input[name=defaultValue]").attr("checked", b);
        $("#itemForm input[name=optionText]").select();
    };
	
	
})(jQuery);  
//-->
</script>

		<!--blockMain Start-->
		<div id="blockMain">

			<!--leftMenu Start-->
			<div id="leftMenu">
				<h1 class="none"><ikep4j:message pre='${preHeader}' key='leftmenu'/></h1>
				<h2><a href="#a" onclick="goAddrbookMain()"><ikep4j:message pre='${preHeader}' key='english.title'/></a></h2>
				<div class="blockButton_2 mb10"> 
					<a class="button_2" onclick="Addressbook.editPerson('','P','');" href="#a"><span><img src="<c:url value='/base/images/icon/ic_addressbook.gif'/>" alt="" /><ikep4j:message pre='${prePrivate}' key='newPerson.title'/></span></a>				
				</div>		
				<div class="left_fixed">
					<ul>
						<li class="liFirst opened licurrent"><a href="#a"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${user.userName}</c:when><c:otherwise>${user.userEnglishName}</c:otherwise></c:choose><ikep4j:message pre='${prePrivate}' key='title'/></a>
							<ul class="boxList_child private_group">
								<li>&nbsp;</li>
							</ul>
							<div class="addr_setting">
								<a href="#a" title="setting" onclick="Addressbook.getAddrgroupList('P')"><ikep4j:message pre='${prePrivate}' key='setting.title'/></a> 
							</div>
							<div class="planner_leftbtn">
								<span class="btn_planner_import"><a href="#a" onclick="Addressbook.getAddrbookImport('P');"><ikep4j:message key='ui.support.addressbook.import.button.import'/></a></span>
								<span class="btn_planner_export"><a href="#a" onclick="Addressbook.getAddrbookExport('P');"><ikep4j:message key='ui.support.addressbook.export.button.export'/></a></span>
							</div>	
						</li>	
						<li class="liFirst opened licurrent"><a href="#a"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${user.userName}</c:when><c:otherwise>${user.userEnglishName}</c:otherwise></c:choose><ikep4j:message pre='${prePrivate}' key='grouptitle'/></a>
							<ul class="boxList_child prip_group">
								<li>&nbsp;</li>
							</ul>
							<div class="addr_setting">
								<a href="#a" title="setting" onclick="Addressbook.getAddrgroupList('G')"><ikep4j:message pre='${prePrivate}' key='setting.title'/></a> 
							</div>
							<div class="planner_leftbtn">
								<span class="btn_planner_import"><a href="#a" onclick="Addressbook.getAddrbookImport('G');"><ikep4j:message key='ui.support.addressbook.import.button.import'/></a></span>
								<span class="btn_planner_export"><a href="#a" onclick="Addressbook.getAddrbookExport('G');"><ikep4j:message key='ui.support.addressbook.export.button.export'/></a></span>
							</div>
						</li>		
						<li class="liFirst no_child"><a href="#a" onclick="Addressbook.getAddrHome('${user.groupId}','T','${user.teamName}');">${user.teamName}<ikep4j:message pre='${prePrivate}' key='title'/></a></li>	
						<li class="opened"><a href="#a"><ikep4j:message pre='${prePublic}' key='title'/></a>
							<ul class="boxList_child public_group">
								<li>&nbsp;</li>
							</ul>				
						</li>
						
						<c:if test="${publicManageFlag == 'true'}">
						<li class="opened"><a href="#a"><ikep4j:message pre='${prePublic}' key='administrator.title'/></a>
							<ul>
								<li class="no_child liLast"><a href="#a" onclick="Addressbook.getAddrgroupList('O');"><ikep4j:message pre='${prePublic}' key='management.title'/></a></li>
								<li class="no_child liLast"><a href="#a" onclick="Addressbook.editCategory();">공용그룹 카테고리 관리</a></li>
							</ul>					
						</li>
						</c:if>											
					</ul>							
				</div>
			</div>	
			<!--//leftMenu End-->
				
			<!--mainContents Start-->
			<div id="mainContents" >
						
				<h1 class="none"><ikep4j:message pre='${preHeader}' key='contents'/></h1>
				
				<!--subTitle_2 Start-->
				<div id="pageTitle">
					<h2>
					공용그룹 카테고리 관리
					</h2>
				</div>
				<!--//subTitle_2 End-->
				
				<form id="itemForm" name="itemForm" method="post" action="" onsubmit="return false;">	
		<input type="hidden" id="delIdList"      name="delIdList"/>
   		<input type="hidden" id="addNameList"    name="addNameList"/> 	
   		<input type="hidden" id="updateNameList" name="updateNameList"/> 	
   		<input type="hidden" id="updateIdList"   name="updateIdList"/> 	
   		<input type="hidden" id="alignList"   name="alignList"/> 	
   		<!--blockDetail Start-->
		<div class="blockDetail">
			<table summary="Message">
				<caption></caption>
				<colgroup>
					<col width="15%" />
					<col width="85%" />
				</colgroup>
				<tbody>   
    	      		<tr id="optionValueTr">
    					<th scope="row"><ikep4j:message pre='${preDetail}' key='option'/></th>
    					<td style="padding-top:7px;">
    						<input type="hidden" name="optionValue" />
                            <ikep4j:message pre='${preDetail}' key='name'/> : 
                            <input type="text" name="optionText" class="inputbox w20" style="width:137px !important;" />
                            <a id="addOptionButton" class="button_ic valign_bottom mr3" href="#a"><span><ikep4j:message pre='${preDetail}' key='add'/></span></a>
  							<a id="updateOptionButton" class="button_ic valign_bottom mr3" href="#a"><span><ikep4j:message pre='${preDetail}' key='edit'/></span></a>
                            <a id="initOptionButton" class="button_ic valign_bottom" href="#a"><span><ikep4j:message pre='${preDetail}' key='cancel'/></span></a><br>
							<table border="0" style="width:500px;">
							  <tr>
							    <td class="border_none padding0">
								    <select name="options" id="options" multiple="multiple" class="inputbox mt5" size="5" style="width:259px; width:256px\9; height:69px;">
								     	<c:forEach var="code" items="${categoryList}">
											<option value="${code.categoryId}">${code.categoryName}</option>
										</c:forEach>
								    </select>
							    </td>
							    <td class="border_none">
							    	<ul>
								    	<li>
			                            	<a id="moveUpButton" class="button_ic valign_bottom" href="#a" ><span>▲</span></a>
			                            </li>
			                            <li>
			                            	<a id="moveDownButton" class="button_ic valign_bottom mt5" href="#a"><span>▼</span></a>
			                            </li>
		                            </ul>
							    </td>
							  </tr>
							  <tr>
							    <td class="border_none pt5" colspan="2" style="padding-left:175px; padding-left:176px\9;">
							    	<a id="editOptionButton" class="button_ic valign_bottom mr3" href="#a"><span><ikep4j:message pre='${preDetail}' key='edit'/></span></a>
		                            <a id="removeOptionButton" class="button_ic valign_bottom" href="#a"><span><ikep4j:message pre='${preDetail}' key='delete'/></span></a>
							    </td>
							  </tr>
							</table>                                  

    					</td>
    			    </tr>
    			   
    			</tbody>
			</table>			
			
		</div>
		<!--//blockDetail End-->	
																																			
		<div class="clear"></div>
		
		<!--blockButton Start-->
		<div id="itemFormButton"  class="blockButton"> 
		<ul>
			<li><a id="saveButton"  class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
		</ul>
		</div>
	</form>
				
			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
		</div>
		<!--//blockMain End-->				