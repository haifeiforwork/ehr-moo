<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="companyCodeUiPrefix" value="ui.portal.admin.code.companyCode"/>
<c:set var="companyCodeListPrefix" value="message.portal.admin.code.companyCode.companyCodeList"/>

                                <script type="text/javascript" language="javascript">
                                //<!--
                                    $jq(document).ready(function() {
                                        $jq("#companyCode :input:visible:enabled:first").focus().select();
                                        
                                        // ID 중복을 체크함
                                        $jq("#checkCodeButton").click(function() {
                                            checkCode();
                                        });
                                        
                                        $jq("input[name=companyCode]").change(function() {
                                            $jq("#codeCheck").val('changed');
                                        });
                                        
                                        // 백스페이스 방지(input, select 제외)
                                        $jq(document).keydown(function(e) {
                                            var element = e.target.nodeName.toLowerCase();
                                            if (element != 'input') {
                                                if (e.keyCode === 8) {
                                                    return false;
                                                }
                                            }
                                        });
                                        
                                        var validOptions = {
                                                rules : {
                                                    companyCode : {
                                                        required : true,
                                                        rangelength : [0, 20]
                                                    },
                                                    companyCodeName : {
                                                        required : true,
                                                        rangelength : [0, 20]
                                                    },
                                                    companyCodeEnglishName : {
                                                        required : true,
                                                        companyEnglishName : true,
                                                        rangelength : [0, 100]                                      
                                                    }
                                                },
                                                messages : {
                                                    companyCode : {
                                                        required : "<ikep4j:message key="NotNull.companyCode.companyCode" />",
                                                        rangelength : "<ikep4j:message key="Size.companyCode.companyCode" />"
                                                    },
                                                    companyCodeName : {
                                                        required : "<ikep4j:message key="NotNull.companyCode.companyCodeName" />",
                                                        rangelength : "<ikep4j:message key="Size.companyCode.companyCodeName" />"
                                                    },
                                                    companyCodeEnglishName : {
                                                        required : "<ikep4j:message key="NotNull.companyCode.companyCodeEnglishName" />",
                                                        companyEnglishName : "<ikep4j:message key="EnglishName.companyCode.companyCodeEnglishName" />",
                                                        rangelength : "<ikep4j:message key="Size.companyCode.companyCodeEnglishName" />"
                                                    }
                                                },
                                                submitHandler : function(company) {
                                                    
                                                    // 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
                                                    if (($jq("#codeCheck").val() == 'success') || 
                                                            ($jq("#codeCheck").val() == 'modify')) {
                                                        $jq.ajax({
                                                            url : '<c:url value="createCompanyCode.do" />',
                                                            data : $jq("#company").serialize(),
                                                            type : "post",
                                                            success : function(result) {
                                                                alert("<ikep4j:message pre="${companyCodeListPrefix}" key="alert.saveCompleted" />");
                                                                $jq("#tempCode").val(result);
                                                                getList();
                                                            },
                                                            error : function(xhr, exMessage) {
                                                            var errorItems = $.parseJSON(xhr.responseText).exception;
                                                            validator.showErrors(errorItems);
                                                            }
                                                        });
                                                    } else {
                                                        alert("<ikep4j:message pre="${companyCodeListPrefix}" key="alert.checkDuplicaed" />");
                                                        return;
                                                    }
                                                }

                                             };

                                        var validator = new iKEP.Validator("#company", validOptions);
                                    });
                                //-->
                                </script>
                                
                                <form id="company" name="company" method="post" onsubmit="return false;" action="">
                                
                                    <input type="hidden" id="codeCheck" name="codeCheck" value="${companyCode.codeCheck}"/>
                                    <table summary="<ikep4j:message pre="${companyCodeUiPrefix}" key="tableSummary" />">
                                    <caption></caption>
                                        <colgroup>
                                            <col width="15%"/>
                                            <col width="10%"/>
                                            <col width="75%"/>
                                        </colgroup>                             
                                    <tbody>
                                        <tr>
                                            <th scope="row" colspan="2">
                                                <span class="colorPoint">*</span> <ikep4j:message pre="${companyCodeUiPrefix}" key="companyCode" />
                                            </th>
                                            <td>
                                                <div>
                                                    <input type="text" name="companyCode" id="companyCode" value="${companyCode.companyCode}" size="50" class="inputbox" />
                                                    &nbsp;<a class="button_ic" id="checkCodeButton" href="#a"><span><ikep4j:message pre="${companyCodeUiPrefix}" key="checkDuplicated" /></span></a>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th scope="row" rowspan="2"><span class="colorPoint">*</span> <ikep4j:message pre="${companyCodeUiPrefix}" key="companyCodeName" /></th>
                                            <th><span class="colorPoint">*</span> <ikep4j:message pre="${companyCodeUiPrefix}" key="companyCodeName" /></th>
                                            <td>
                                                <div>
                                                    <input type="text" name="companyCodeName" id="companyCodeName" value="${companyCode.companyCodeName}" size="50" class="inputbox" />
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th><span class="colorPoint">*</span> <ikep4j:message pre="${companyCodeUiPrefix}" key="companyCodeEnglishName" /></th>
                                            <td>
                                                <div>
                                                    <input type="text" name="companyCodeEnglishName" id="companyCodeEnglishName" value="${companyCode.companyCodeEnglishName}" size="50" class="inputbox" />
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                    
                                    </table>
                                    
                                    <input type="hidden" id="sortOrder" name="sortOrder" value="${companyCode.sortOrder}"/> 
                                    <input type="hidden" id="oldSortOrder" name="oldSortOrder" value="${oldSortOrder}"/>
                                    
                                </form>