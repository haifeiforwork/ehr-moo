<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" /> 
<c:set var="prePrivate"    value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />

<c:set var="preAddrMessage" value="message.support.addressbook.addrgroup" />
<c:set var="prePerMessage" value="message.support.addressbook.person" />
<c:set var="prePerButton"  value="ui.support.addressbook.person.button" /> 
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
                               
<%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript">
<!--

//-->
</script>


<c:forEach var="addrgroup" items="${addrgroupList.entity}" varStatus="status">
<li>${addrgroup.addrgroupName}</li>
<input type="hidden" value="${addrgroup.addrgroupId}"/>
</c:forEach>	
