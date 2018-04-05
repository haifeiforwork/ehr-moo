<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<c:set var="prefix" value="message.portal.portlet.campaignMessages.normalView"/>
<%-- Tab Lib End --%> 
<script type="text/javascript">
//<![CDATA[
(function($){
    
	$(document).ready(function() {   
		
		$("#CNW_${portletConfigId}").tabs(); 
		
		iKEP.showGallery($("a.image-gallery"));
		
		//more 클릭시
		$("#${portletConfigId} a.item").click(function() { 
			var url = $(this).attr("href")+ "&popupYn=true&portletYn=true";
			
			var options = {
				windowTitle : $(this).html(),
				documentTitle : "게시판 내용보기",
				width:720, height:500, modal:true, 
				callback : function(result) {
					 
				}
			};
			
			iKEP.portletPopupOpen(url, options);
	 
			return false;
		});
	}); 

})(jQuery);
//]]>
</script>
<div id="${portletConfigId}">
<div id="CNW_${portletConfigId}" class="iKEP_tab_s" style="margin-bottom: 0px;"> 
    <ul>        
        
        <!-- <li>
            <a href="#cnw-tabs-2" class="cnw tabItem">
                  <div class="ellipsis">
                      경영방침
                   </div>
            </a>
        </li> -->
        <li>
              <a href="#cnw-tabs-1" class="cnw tabItem">
                  <div class="ellipsis">
                      <ikep4j:message pre="${prefix}" key="title1" />                
                   </div>
              </a>
        </li>
    </ul>  
    
    <%-- <div id="cnw-tabs-2" style="padding-bottom: 0px;overflow: hidden;">
    <div class="more" style="float:right; padding-top: -4px;"><a href='<c:url value='/lightpack/board/boardCommon/boardView.do?boardId=${portletManagementPolicy.boardId}&portletYn=true' />'><img src='<c:url value='/base/images/common/btn_more.gif'/>' alt='more' /></a></div>
	      <table summary="" width="100%">
			    <caption></caption>
			    <tbody>
			        <tr>
			            <td width="440" style="text-align:center;">
			               <c:if test="${managementPolicyImageYn == 'Y'}">
                            <img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${portletManagementPolicy.imageFileId}" onmouseup="iKEP.iFrameContentResize();"/>
                        </c:if>
			            </td>
			        </tr>
			    </tbody>
			</table>
       </div> --%>
       <div id="cnw-tabs-1" style="padding-bottom: 0px;">
             <div class="more" style="float:right; padding-top: -4px;"><a href='<c:url value='/lightpack/board/boardCommon/boardView.do?boardId=${portletCampaignMessages.boardId}&portletYn=true' />'><img src='<c:url value='/base/images/common/btn_more.gif'/>' alt='more' /></a></div>
             <div id="${portletConfigId}">
                <h2 class="none"><ikep4j:message pre="${prefix}" key="title" /></h2>
                    <c:set var="img_td">
                    <td valign="top" style="padding-right:5px;width:103px;">
                        <c:if test="${campaignmessagesImageYn == 'Y'}">
                            <img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${portletCampaignMessages.imageFileId}" title="<ikep4j:message pre="${prefix}" key="img.campaignmessagesImage" />" width="100" height="107"/>
                        </c:if>
                        <c:if test="${campaignmessagesImageYn != 'Y'}">
                             <img src="<c:url value='/base/images/common/img_wisesaying5.gif'/>" title="<ikep4j:message pre="${prefix}" key="img.campaignmessagesImage" />" width="100" height="107"/>
                        </c:if>
                    </td>
                    </c:set>
                  <c:set var="text_td">
                <td valign="top">
            <div class="ellipsis">
                <b>${portletCampaignMessages.subject}</b>
            </div>
            <div class="ellipsis" style="margin-top: 2px; max-height:52px; white-space:normal; font: 100%/1.5em '맑은 고딕', Tahoma, 'Dotum'; BACKGROUND: #fff;">
                <a class="item" href="<c:url value='/lightpack/board/boardItem/readBoardItemLinkView.do?popupYn=true&amp;itemId=${portletCampaignMessages.itemId}'/>">
                    ${portletCampaignMessages.summary}
                </a>
            </div>
        </td>
    </c:set>
    <table width="100%" class="ex_me" style="table-layout: fixed;"> 
        <tbody>
            <tr>
                <c:if test="${portletCampaignMessages.location eq 'right'}">
                    ${text_td}
                    ${img_td}
                </c:if>
                <c:if test="${portletCampaignMessages.location eq 'left'}">
                    ${img_td}
                    ${text_td}
                </c:if>
            </tr>
        </tbody>
    </table>
</div>
</div> 
    </div>  
</div> 




