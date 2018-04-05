<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 
<c:set var="prefix" value="message.portal.portlet.rss.normalView"/>
<c:set var="portletList" value="ui.portal.portlet.wiseSaying.popupPortletWiseSayingList"/>
<script type="text/javascript">
<!--

function open_pop( url ){	
	window.open( url ,"","resizable=yes,scrollbars=yes,width=800,height=600");
}
(function($) {

   $(document).ready(function() {
        
        $(".recentMailitem").click( function() {              
            var url = $(this).attr("link_url")+ "&popupYn=true";
            
            var options = {
                windowTitle : $(this).html(),
                documentTitle : "메일 읽기" ,
                width:720, 
                height:500, 
                modal:true 
            };
            
            iKEP.portletPopupOpen(url, options);  
            
            return false;
        });
        

    });  
	
})(jQuery);		 
//-->
</script>

<div id="${portletConfigId}" class="tableList_1">
	<table summary="" >
		<caption></caption>
        <colgroup>
            <col width="*"/>
            <col width="15%"/>
            <col width="10%"/>
        </colgroup>
		<tbody>		
			<c:choose>
			    <c:when test="${empty mailList}">
					<tr>
						<td width="100%" colspan="3" class="emptyRecord"><ikep4j:message pre='${portletList}' key='list.empty' /></td> 
					</tr>				        
			    </c:when>
			    <c:otherwise>
					<c:forEach var="mail" items="${mailList}" varStatus="status">
						<c:if test="${status.count <= listSize}">
						<tr>
							<td width="*" scope="row" class="t_po1">
								<div class="ellipsis">									
									<a href="#" onclick="javascript:open_pop('${mailServer}/dynamic/mail/readMessage.do?readType=pop&uid=${mail.mailId}&folder=${mail.folderName}');">
									${mail.title}
									</a>
                                </div>    								
							</td>	
							<td class="textCenter"><div class="ellipsis">${mail.fromName}</div></td>
							<td width="30" class="textRight"><span class="date"><ikep4j:timezone pattern="MM.dd" date="${mail.sendDate}"/></span></td>
						</tr>
						</c:if>
					</c:forEach>
				</c:otherwise>	
			</c:choose>
		</tbody>
	</table>
	<div class="none" id="popLayer" title="Message">
		<div id="popMessageView" > </div>
	</div>
</div>