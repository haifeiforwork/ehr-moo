<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/${user.userTheme.cssPath}/jquery_ui_custom.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/${user.userTheme.cssPath}/theme.css"/>" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-ui-1.8.13.min.without.datepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.ui.datepicker.customize.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.1.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/plugins.pack.js"/>"></script>
<script type="text/javascript">var contextRoot = "${pageContext.request.contextPath}";</script>
<script type="text/javascript" src="<c:url value="/base/js/etc.plugins.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/langResource/${empty user.localeCode ? 'en' : user.localeCode}.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/common.pack.js"/>"></script>

<script type="text/javascript"> 

	function addTab(){
		
		var keyword = document.getElementById("tagNm").value;		
		
		if(keyword == ""){
			alert("태그를 입력해주세요");
            return;
		}	 
        parent.document.getElementById("ifrmTab").contentWindow.addTab(keyword);           
    }
 
</script>
<form id="f1" name="f1" method="post" onsubmit="return false;"></form> 
<div class="qna_search">
  <div class="qna_tc">
      <img src="<c:url value='/base/images/common/kms_tap.gif'/>" alt="" />
      <div class="qna_search_form_2">
          <div class="qna_search_input_2">							
              <div class="qna_search_inputbox" >
                  <table summary="" width="80%">
                      <caption></caption>
                      <tbody>
                          <tr>
                              <td width="*">
                                  <input name="tagNm" id="tagNm" class="inputbox" type="text" size="50" value="" />
                              </td>
                          </tr>
                      </tbody>
                  </table>
              </div>
              <span><a class="qna_btn_1" id="addBtn" href="javascript:addTab();"><span><ikep4j:message key="message.collpack.kms.main.tab.button.add" /></span></a></span>	
            </div>							
        </div>	
    </div>                                
</div>	