<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 
<c:set var="prefix" value="message.portal.portlet.rss"/>
<c:set var="portletList" value="ui.portal.portlet.rssNews"/>

<script type="text/javascript">
<!--   
(function($) {
	
	showDatail_${portletConfigId} = function(url, itemId, subId, title) {

		window.open(url, "", "width=800,height=500,status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes");
	};
	
	$(document).ready(function(event) {
		var portletManager = PortletManager.getInstance();
		var thisPortlet = portletManager.getPortletByConfigId(${portletConfigId});
		
		thisPortlet.box.container.unbind("click:reload")
			.bind("click:reload", function(event) {
				
			    if (confirm("<ikep4j:message pre='${prefix}' key='reload.ConfirmMessage' />")){	

						// reload
						if(thisPortlet.options.reload && thisPortlet.options.reload.callback) {
							thisPortlet.options.reload.callback();
						} else {								                
								$.ajax({
				                    url : iKEP.getContextRoot() + "/support/rss/channel/readChannel.do",
				                    data : {channelId:"",categoryId:""} ,
				                    type : "post",
				                    loadingElement : {container : thisPortlet.box.container},
				                    success : function() {
				                    	thisPortlet.loadContent();
				                    },
				                    error : function() {
				                    	alert("<ikep4j:message pre='${prefix}' key='reload.updateError' />");
				                    }
				                });
						}
			    }				
				
			});
	});
	
})(jQuery); 
//-->
</script>

<div id="${portletConfigId}" class="tableList_1">	
<c:choose>
    <c:when test="${searchResult.emptyRecord}">
        <ikep4j:message pre='${portletList}' key='list.empty' />                                            
    </c:when>
    <c:otherwise>
        <c:forEach var="category" items="${searchResult.entity}"  varStatus="roop">
                 <img src="<c:url value='/base/images/common/ic_title_01.gif'/>" title="title_bar" style="padding-right:4px; padding-bottom:2px; vertical-align:middle;"><strong>${category.categoryName}</strong>
                 <c:if test="${roop.index == 0}">
                     <div class="more" style="text-align:right;float: right;align:left;"><a href="<c:url value='/support/rss/channelitem/getList.do'/>"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more"></a></div>
                 </c:if>
                 <div class="line_1" style="margin-bottom:5px; padding-top:5px;"></div>
              <ul>
                 <div id="rssItemBoard${portletConfigId}" class="tableList_1">
                    <table summary="<ikep4j:message pre="${portletList}" key="summary" />">
                            <caption></caption>
                            <colgroup>
                                <col width="*"/>
                                <col width="10%"/>                                      
                            </colgroup>
                        <tbody>  
                            <c:choose>
                                <c:when test="${empty category.channelItem}">
                                    <tr>
                                       <td colspan="2" class="emptyRecord"><ikep4j:message pre='${portletList}' key='list.empty' /></td> 
                                    </tr>                   
                                </c:when>
                            <c:otherwise>                       
                            <c:forEach var="channelItem" items="${category.channelItem}" varStatus="status">
                                 <tr>
                                     <td class="t_po1" width="*" scope="row">
                                         <div class="ellipsis"><a href="#a" onclick="showDatail_${portletConfigId}('${channelItem.itemUrl}', '${fn:replace(fn:replace(channelItem.itemTitle,"\"",""),"'","")}')">
                                            ${channelItem.itemTitle}</a></div>
                                     </td> 
                                     <td class="textRight"><span class="date"><ikep4j:timezone pattern="MM.dd" date="${channelItem.itemPublishDate}"/></span></td>
                                  </tr>
                            </c:forEach>    
                            </c:otherwise> 
                            </c:choose> 
                        </tbody>
                    </table>
                    </div>
                    <div class="dotline_3" style="margin-bottom:0px;"></div>
                    </ul>
                <input type="hidden" id="${category.categoryId}_hidden" value=""/> 
            <br>            
        </c:forEach>                    
    </c:otherwise>  
</c:choose>         
</div>