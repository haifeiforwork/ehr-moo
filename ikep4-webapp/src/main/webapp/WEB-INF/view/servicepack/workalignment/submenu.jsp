<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preUi" 	value="ui.servicepack.workalignment.left" />

<script type="text/javascript">

   (function($){
       $(document).ready(function () {
           // Left Menu setting
    	   iKEP.setLeftMenu();
       });
   })(jQuery);

</script>

 
<!--leftMenu Start-->
 <h1 class="none">레프트메뉴</h1>
 <h2><a href="<c:url value="/servicepack/workalignment/workAlignment.do"/>"><ikep4j:message pre='${preUi}' key='myWork'/></a></h2>
 <div class="left_fixed">
  <ul>
   <li class="liFirst no_child"><a href="<c:url value="/portal/main/body.do?pageId=100000731452"/>"><ikep4j:message pre='${preUi}' key='dashboard'/></a></li>
   <li class="opened"><a href="#a"><ikep4j:message pre='${preUi}' key='paymentManage'/></a>
    <ul>
      <li class="no_child"><a href="<c:url value='/approval/work/apprlist/listApprTodo.do?linkType=mywork'/>"><ikep4j:message pre='${preUi}' key='accept'/></a></li>
      <li class="no_child liLast"><a href="<c:url value='/approval/work/apprlist/listApprMyRequest.do?linkType=mywork'/>"><ikep4j:message pre='${preUi}' key='request'/></a></li>
    </ul>
   </li>
   <li class="opened"><a href="#a"><ikep4j:message pre='${preUi}' key='workManage'/></a>
    <ul>
      <li class="no_child"><a href="subMain.do?type=todo&amp;link=1"><ikep4j:message pre='${preUi}' key='attendWork'/></a></li>
      <li class="no_child liLast"><a href="subMain.do?type=todo&amp;link=2"><ikep4j:message pre='${preUi}' key='direction'/></a></li>
    </ul>
   </li>
   <li class="opened"><a href="#a"><ikep4j:message pre='${preUi}' key='planManage'/></a>
    <ul>
      <li class="no_child"><a href="subMain.do?type=plan&amp;link=1"><ikep4j:message pre='${preUi}' key='myPlan'/></a></li>
      <li class="no_child liLast"><a href="subMain.do?type=plan&amp;link=2"><ikep4j:message pre='${preUi}' key='planMandate'/></a></li>
    </ul>
   </li>
   <li class="opened"><a href="#a"><ikep4j:message pre='${preUi}' key='workRequest'/></a>
    <ul>
      <li class="no_child"><a href="<c:url value='/approval/work/apprWorkForm/listApprForm.do?linkType=mywork'/>"><ikep4j:message pre='${preUi}' key='draftWork'/></a></li>
      <li class="no_child liLast"><a href="subMain.do?type=todo&amp;link=3"><ikep4j:message pre='${preUi}' key='workDirection'/></a></li>
    </ul>
   </li>
   </ul>
 </div>
<!--//leftMenu End-->
