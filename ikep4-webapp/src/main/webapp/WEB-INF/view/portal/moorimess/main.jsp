<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 



<c:set var="user" value="${sessionScope['ikep.user']}" />


 <%-- 메시지 관련 Prefix 선언 End --%> 
<script type="text/javascript">
<!--   
(function($) {
	$jq(document).ready(function(){
		//alert("url:"+"${serverLinkUrl}zhr_es_001/default.htm"+"\n"+"id:"+"${sapId}"+"\n"+"pwd:"+"${sapPwd}");
		document.domain ="moorim.co.kr";
		var addParamStr ="";
		var programCode = "${whereLink}";
		
		if(programCode=="zhr_py_002_k2" || programCode=="zhr_pa_008"){
			addParamStr="?E_PERNR=${user.empNo}";
		}
		mooorim_ESS_link("${serverLinkUrl}${whereLink}/default.htm"+addParamStr ,"${sapId}" ,"${sapPwd}" );
		
	});

})(jQuery);
//-->
</script>
