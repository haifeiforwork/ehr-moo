<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="prefix" value="message.portal.portlet.rss.configView"/>
<c:set var="portletList" value="ui.portal.portlet.rssNews.configView"/>


<script type="text/javascript">
<!--   

(function($) {
				
	// onload시 수행할 코드
	$jq(document).ready(function() { 

		$jq("#save_${portletConfigId}").click(function() {
			
			if($("input.categoryId:checked").length == 0){
                alert('<ikep4j:message pre='${portletList}' key='selectBoard' />');
                return;
            }
            $.post('<c:url value="/portal/portlet/rssNews/updatePortlet.do"/>', $("#configForm").serialize()) 
            .success(function(data) {            	
            	   var reloadUrl = '<c:url value="/portal/portlet/rssNews/normalView.do"/>?portletId=${portletId}';
                   var listSize = $jq("#listCount_${portletConfigId}").val();
                   //portletConfigId, 프로퍼티네임(최대 10Byte), 프로퍼티 값(최대 50Byte),설정 후 이동할 url : 없으면 이동하지 않음.
                   PortletSimple.setListCount('${portletConfigId}', listSize, reloadUrl, '${portletId}');                   
                //alert('<ikep4j:message pre='${preList}' key='updateSuccess' />');
               // $("#portlet-lightpack-publicboard").parent().parent().parent().parent().trigger("click:reload"); 
            })
            .error(function(event, request, settings) { 
                alert('<ikep4j:message pre='${portletList}' key='updateError' />');                
            });
		}); 		
	});
	
})(jQuery);  

//-->
</script>
<form id="configForm">
<input name="portletConfigId" type="hidden" value="${portletConfigId}">

<div id="${portletConfigId}" class="tableList_1">

	<div class="poEdit">
		<div>
		<ikep4j:message pre='${portletList}' key='list.Count' /> :
		<select title="<ikep4j:message pre='${portletList}' key='list.Count' />" id="listCount_${portletConfigId}">
			<option value="10" <c:if test="${listSize eq '10'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='list.Count10' /></option>
			<option value="5" <c:if test="${listSize eq '5'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='list.Count5' /></option>
			<option value="3" <c:if test="${listSize eq '3'}">selected="selected"</c:if>><ikep4j:message pre='${portletList}' key='list.Count3' /></option>
		</select>
		
		</div>
		
		<div class="textRight">
			<a id="save_${portletConfigId}" class="button_s" href="#a"><span><ikep4j:message pre='${portletList}' key='button.create' /></span></a></div>
	</div>
	
	<div class="pTitle_1">
            <ikep4j:message pre='${prefix}' key='title' />
        </div>
        <div>
            <table summary="<ikep4j:message pre="${preList}" key="summary" />">
                <caption></caption>
                <c:forEach var="category" items="${categoryList}" varStatus="status">
                    <tr>
                        <td><input class="categoryId" name="categoryId" type="checkbox"
                            class="checkbox" value="${category.categoryId}"
                            <c:if test="${category.portlet gt 0}">checked="checked"</c:if> />
                            ${category.categoryName}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
	
</div>

</form>
							
							