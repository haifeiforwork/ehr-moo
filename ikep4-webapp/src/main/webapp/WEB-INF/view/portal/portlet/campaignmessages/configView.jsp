<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="preMessage" value="ui.support.fileupload.message" />
<c:set var="prefix" value="message.portal.portlet.campaignMessages.configView"/>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.form.js"/>"></script>
<script type="text/javascript">
<!--   
$jq(document).ready(function() {
    var portletCampaignMessagesAddFormDiv = $jq("div[id=${portletConfigId}]");
    
    var portletManagementPolicyAddFormDiv = $jq("div[id=managementPolicyDiv]");
    
    $jq("#campaignmessagesConfigForm${portletConfigId}").submit(function(){
        var options = {
            success:function(){
                portletCampaignMessagesAddFormDiv.parent().parent().parent().trigger("click:reload");
                alert("<ikep4j:message pre="${prefix}" key="alert.saveMessage" />");
            },
            url:'<c:url value="/portal/portlet/campaignmessages/createPortletCampaignMessages.do"/>',
            type:'post',
            dataType : "html",
            error : function() {alert('error');}
        };
        
        $jq(this).ajaxSubmit(options);
        return false;
    });
    
    $jq("#managementPolicyConfigForm").submit(function(){
        var options = {
            success:function(){
                portletManagementPolicyAddFormDiv.parent().parent().parent().trigger("click:reload");
                alert("<ikep4j:message pre="${prefix}" key="alert.saveMessage" />");
            },
            url:'<c:url value="/portal/portlet/campaignmessages/createPortletManagementPolicy.do"/>',
            type:'post',
            dataType : "html",
            error : function() {alert('error');}
        };
        
        $jq(this).ajaxSubmit(options);
        return false;
    });
    
    $jq("#campaignmessagesAddButton", portletCampaignMessagesAddFormDiv).click(function() {
        var campaignmessagesConfigForm = $jq("#campaignmessagesConfigForm${portletConfigId}");
        
        if($jq("input[name=boardId]", campaignmessagesConfigForm).val() == "") {
            alert("<ikep4j:message pre="${prefix}" key="alert.boardIdMessage" />");
            return;
        }
        
        if($jq("input[name=itemId]", campaignmessagesConfigForm).val() == "") {
            alert("<ikep4j:message pre="${prefix}" key="alert.itemIdMessage" />");
            return;
        }
        
        var fileName = $jq("input[name=file]", campaignmessagesConfigForm).val();
        
        if(fileName != "") {
            if(!fileName.match(/(.jpg|.JPG|.jpge|.JPGE|.gif|.GIF|.png|.PNG)$/)) {
                alert('<ikep4j:message pre='${preMessage}' key='file.image' />');
                return;
            }
        }
        
        $jq("#campaignmessagesConfigForm${portletConfigId}").submit();
    });
    
    $jq("#managementPolicyAddButton", portletManagementPolicyAddFormDiv).click(function() {
        var managementPolicyConfigForm = $jq("#managementPolicyConfigForm");
        
        if($jq("input[name=boardId]", managementPolicyConfigForm).val() == "") {
            alert("<ikep4j:message pre="${prefix}" key="alert.boardIdMessage" />");
            return;
        }
        
        if($jq("input[name=itemId]", managementPolicyConfigForm).val() == "") {
            alert("<ikep4j:message pre="${prefix}" key="alert.itemIdMessage" />");
            return;
        }
        
        var fileName = $jq("input[name=file]", managementPolicyConfigForm).val();
        if(fileName != "") {
            if(!fileName.match(/(.jpg|.JPG|.jpge|.JPGE|.gif|.GIF|.png|.PNG)$/)) {
                alert('<ikep4j:message pre='${preMessage}' key='file.image' />');
                return;
            }
        }
        
        $jq("#managementPolicyConfigForm").submit();
    });
    
    
    wiseSayingEdit = function() {
        var url = "<c:url value='/portal/portlet/wiseSaying/popupPortletWiseSayingList.do' />?portletConfigId=${portletConfigId}";
        iKEP.popupOpen(url, {width:900, height:510}, "PortletWiseSayingEdit");
    }
});
//-->
</script>
<div id="CNWConfig_${portletConfigId}" class="iKEP_tab_s"> 
 <ul>        
        <li>
              <a href="#cnwconfig-tabs-1" class="cnw tabItem">
                  <div class="ellipsis">
                      <ikep4j:message pre="${prefix}" key="title1" />                
                   </div>
              </a>
        </li>
        <li>
            <a href="#cnwconfig-tabs-2" class="cnw tabItem">
                  <div class="ellipsis">
                      경영방침
                   </div>
            </a>
        </li>
    </ul>
<!--blockDetail Start-->
<div id="cnwconfig-tabs-1">
<div class="blockDetail" id="${portletConfigId}">
    <form name="campaignmessagesConfigForm${portletConfigId}" id="campaignmessagesConfigForm${portletConfigId}" method="post" enctype="multipart/form-data">
    <table summary="<ikep4j:message pre="${prefix}" key="summary" />">
        <caption></caption>
        <tbody>
            <tr>
                <th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="write.subject" /></th>
                <td>
                    <input name="subject" title="<ikep4j:message pre="${prefix}" key="write.subject" />" class="inputbox w90" type="text" value="${portletCampaignMessages.subject}"/>
                </td>
            </tr>
            <tr>
                <th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="write.summary" /></th>
                <td>
                    <textarea name="summary" title="<ikep4j:message pre="${prefix}" key="write.summary" />" class="inputbox w90">${portletCampaignMessages.summary}</textarea>
                </td>
            </tr>
            <tr>
                <th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="boardid" /></th>
                <td>
                    <input name="boardId" title="<ikep4j:message pre="${prefix}" key="boardid" />" class="inputbox w90" type="text" value="${portletCampaignMessages.boardId}"/>
                </td>
            </tr>
            <tr>
                <th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="itemid" /></th>
                <td>
                    <input name="itemId" title="<ikep4j:message pre="${prefix}" key="itemid" />" class="inputbox w90" type="text" value="${portletCampaignMessages.itemId}"/>
                </td>
            </tr>
            <!-- tr>
                <th scope="row"><ikep4j:message pre="${prefix}" key="target" /></th>
                <td>
                    <input type="radio" name="target" class="radio" value="WINDOW" <c:if test="${portletCampaignMessages.target == 'WINDOW'}">checked="checked"</c:if> /><ikep4j:message pre="${prefix}" key="target.window" />
                </td>
            </tr-->
                    <input type="hidden" name="target" class="radio" value="WINDOW" checked>
            <tr>
                <th scope="row"><ikep4j:message pre="${prefix}" key="image" /></th>
                <td>
                    <input type="hidden" name="editorAttach" id="editorAttach" value="0"/>
                    <input name="file" type="file" class="file w100" title="<ikep4j:message pre="${prefix}" key="image" />"/>
                </td>
            </tr>
            <tr>
                <th scope="row"><ikep4j:message pre="${prefix}" key="location" /></th>
                <td>
                    <input type="radio" name="location" class="radio" value="left" <c:if test="${portletCampaignMessages.location == 'left' or portletCampaignMessages.location == null }">checked="checked"</c:if> /><ikep4j:message pre="${prefix}" key="location.left" />
                    <input type="radio" name="location" class="radio" value="right" <c:if test="${portletCampaignMessages.location == 'right'}">checked="checked"</c:if> /><ikep4j:message pre="${prefix}" key="location.right" />
                    <input type="hidden" name="imgWidth" value="80" />
                    <input type="hidden" name="imgHeight" value="80" />
            </tr>           
            <!-- tr>
                <th scope="row"><ikep4j:message pre="${prefix}" key="img.size" /></th>
                <td>
                    <ikep4j:message pre="${prefix}" key="img.width" /><input type="text" name="imgWidth" size="5" maxlength="3" class="radio" value="${portletCampaignMessages.imgWidth}" />px
                    <ikep4j:message pre="${prefix}" key="img.height" /><input type="text" name="imgHeight" size="5" maxlength="3" class="radio" value="${portletCampaignMessages.imgHeight}" />px
                </td>
            </tr-->         
        </tbody>
    </table>
    <div class="blockButton" style="padding-top: 5px;"> 
        <ul>
            <li><a id="campaignmessagesAddButton" class="button_s" href="#a" title="<ikep4j:message pre="${prefix}" key="button.save" />"><span><ikep4j:message pre="${prefix}" key="button.save" /></span></a></li>
        </ul>
    </div>
    </form>
</div>
</div>
<div id="cnwconfig-tabs-2">
<div class="blockDetail" id="managementPolicyDiv">
    <form name="managementPolicyConfigForm" id="managementPolicyConfigForm" method="post" enctype="multipart/form-data">
    <table>
        <caption></caption>
        <tbody>
            <tr>
                <th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="write.subject" /></th>
                <td>
                    <input name="subject" title="<ikep4j:message pre="${prefix}" key="write.subject" />" class="inputbox w90" type="text" value="${portletManagementPolicy.subject}"/>
                </td>
            </tr>
            <tr>
                <th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="write.summary" /></th>
                <td>
                    <textarea name="summary" title="<ikep4j:message pre="${prefix}" key="write.summary" />" class="inputbox w90">${portletManagementPolicy.summary}</textarea>
                </td>
            </tr>
            <tr>
                <th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="boardid" /></th>
                <td>
                    <input name="boardId" title="<ikep4j:message pre="${prefix}" key="boardid" />" class="inputbox w90" type="text" value="${portletManagementPolicy.boardId}"/>
                </td>
            </tr>
            <tr>
                <th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="itemid" /></th>
                <td>
                    <input name="itemId" title="<ikep4j:message pre="${prefix}" key="itemid" />" class="inputbox w90" type="text" value="${portletManagementPolicy.itemId}"/>
                </td>
            </tr>
            <!-- tr>
                <th scope="row"><ikep4j:message pre="${prefix}" key="target" /></th>
                <td>
                    <input type="radio" name="target" class="radio" value="WINDOW" <c:if test="${portletCampaignMessages.target == 'WINDOW'}">checked="checked"</c:if> /><ikep4j:message pre="${prefix}" key="target.window" />
                </td>
            </tr-->
                    <input type="hidden" name="target" class="radio" value="WINDOW" checked>
            <tr>
                <th scope="row"><ikep4j:message pre="${prefix}" key="image" /></th>
                <td>
                    <input type="hidden" name="editorAttach" id="editorAttach" value="0"/>
                    <input name="file" type="file" class="file w100" title="<ikep4j:message pre="${prefix}" key="image" />"/>
                </td>
            </tr>
            <tr>
                <th scope="row"><ikep4j:message pre="${prefix}" key="location" /></th>
                <td>
                    <input type="radio" name="location" class="radio" value="left" <c:if test="${portletManagementPolicy.location == 'left' or portletManagementPolicy.location == null }">checked="checked"</c:if> /><ikep4j:message pre="${prefix}" key="location.left" />
                    <input type="radio" name="location" class="radio" value="right" <c:if test="${portletManagementPolicy.location == 'right'}">checked="checked"</c:if> /><ikep4j:message pre="${prefix}" key="location.right" />
                    <input type="hidden" name="imgWidth" value="80" />
                    <input type="hidden" name="imgHeight" value="80" />
            </tr>           
            <!-- tr>
                <th scope="row"><ikep4j:message pre="${prefix}" key="img.size" /></th>
                <td>
                    <ikep4j:message pre="${prefix}" key="img.width" /><input type="text" name="imgWidth" size="5" maxlength="3" class="radio" value="${portletCampaignMessages.imgWidth}" />px
                    <ikep4j:message pre="${prefix}" key="img.height" /><input type="text" name="imgHeight" size="5" maxlength="3" class="radio" value="${portletCampaignMessages.imgHeight}" />px
                </td>
            </tr-->         
        </tbody>
    </table>
    <div class="blockButton" style="padding-top: 5px;"> 
        <ul>
            <li><a id="managementPolicyAddButton" class="button_s" href="#a" title="<ikep4j:message pre="${prefix}" key="button.save" />"><span><ikep4j:message pre="${prefix}" key="button.save" /></span></a></li>
        </ul>
    </div>
    </form>
</div>

</div>
</div>
  
 <script>$jq("#CNWConfig_${portletConfigId}").tabs();</script>  