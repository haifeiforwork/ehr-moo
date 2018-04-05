<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.socialpack.socialanalyzer.updateSocialDistanceView</c:set>
<c:set var="messagePrefix">ui.socialpack.socialanalyzer.message</c:set>
<c:set var="buttonPrefix">ui.socialpack.socialanalyzer.button</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
(function($) {
	$jq(document).ready(function(){
		//저장 클릭
		$jq("#saveButton").click(function() {
			$("#form").trigger("submit");
		});

		//validation
		var validOptions = {
			rules : {
				typeWeightD : {required:true, range:[0, 100], digits:true},
				typeWeightC : {required:true, range:[0, 100], digits:true},
				typeWeightF : {required:true, range:[0, 100], digits:true},
				typeWeightE : {required:true, range:[0, 100], digits:true},
				validMonthC : {required:true, range:[1, 12], digits:true},
				validMonthF : {required:true, range:[1, 12], digits:true},
				dTeamMemberWeight     : {required:true, range:[0, 1000], digits:true},
				dTeamLeaderWeight     : {required:true, range:[0, 1000], digits:true},
				dFavoriteWeight       : {required:true, range:[0, 1000], digits:true},
				dFollowingWeight      : {required:true, range:[0, 1000], digits:true},
				dFollowerWeight       : {required:true, range:[0, 1000], digits:true},
				dBothFollowingWeight  : {required:true, range:[0, 1000], digits:true},
				cMailWeight           : {required:true, range:[0, 1000], digits:true},
				cMailMax              : {required:true, range:[0, 1000], digits:true},
				cMessageMax           : {required:true, range:[0, 1000], digits:true},
				cMessageWeight        : {required:true, range:[0, 1000], digits:true},
				cSmsMax               : {required:true, range:[0, 1000], digits:true},
				cSmsWeight            : {required:true, range:[0, 1000], digits:true},
				cGuestbookMax         : {required:true, range:[0, 1000], digits:true},
				cGuestbookWeight      : {required:true, range:[0, 1000], digits:true},
				fFollowerWeight       : {required:true, range:[0, 1000], digits:true},
				fFollowingWeight      : {required:true, range:[0, 1000], digits:true},
				fBothFollowingWeight  : {required:true, range:[0, 1000], digits:true},
				fMyLinereplyMax       : {required:true, range:[0, 1000], digits:true},
				fMyLinereplyWeight    : {required:true, range:[0, 1000], digits:true},
				fMyMentionMax         : {required:true, range:[0, 1000], digits:true},
				fMyMentionWeight      : {required:true, range:[0, 1000], digits:true},
				fYourLinereplyMax     : {required:true, range:[0, 1000], digits:true},
				fYourLinereplyWeight  : {required:true, range:[0, 1000], digits:true},
				fYourMentionMax       : {required:true, range:[0, 1000], digits:true},
				fYourMentionWeight    : {required:true, range:[0, 1000], digits:true},
				fBothMbloggroupMax    : {required:true, range:[0, 1000], digits:true},
				fBothMbloggroupWeight : {required:true, range:[0, 1000], digits:true},
				eExpertTagMax         : {required:true, range:[0, 1000], digits:true},
				eExpertTagWeight      : {required:true, range:[0, 1000], digits:true},
				eInterestTagMax       : {required:true, range:[0, 1000], digits:true},
				eInterestTagWeight    : {required:true, range:[0, 1000], digits:true},
				eDocTagMax            : {required:true, range:[0, 1000], digits:true},
				eDocTagWeight         : {required:true, range:[0, 1000], digits:true},
				eBothAnswerMax        : {required:true, range:[0, 1000], digits:true},
				eBothAnswerWeight     : {required:true, range:[0, 1000], digits:true}
			},
			messages : {
				typeWeightD : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_100'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>"},
				typeWeightC : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_100'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>"},
				typeWeightF : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_100'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>"},
				typeWeightE : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_100'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>", direction:"left"},
				validMonthC : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.1_12'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>", direction:"left"},
				validMonthF : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.1_12'/>", digits:"<ikep4j:message key='Digits.socialRanking'/>", direction:"left"},
				dTeamMemberWeight     : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				dTeamLeaderWeight     : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				dFavoriteWeight       : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				dFollowingWeight      : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				dFollowerWeight       : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				dBothFollowingWeight  : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", direction:"left"},
				cMailWeight           : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				cMailMax              : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				cMessageMax           : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				cMessageWeight        : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				cSmsMax               : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				cSmsWeight            : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				cGuestbookMax         : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", direction:"left"},
				cGuestbookWeight      : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", direction:"left"},
				fFollowerWeight       : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				fFollowingWeight      : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				fBothFollowingWeight  : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				fMyLinereplyMax       : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				fMyLinereplyWeight    : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				fMyMentionMax         : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				fMyMentionWeight      : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				fYourLinereplyMax     : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				fYourLinereplyWeight  : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				fYourMentionMax       : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				fYourMentionWeight    : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				fBothMbloggroupMax    : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", direction:"left"},
				fBothMbloggroupWeight : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", direction:"left"},
				eExpertTagMax         : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				eExpertTagWeight      : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				eInterestTagMax       : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				eInterestTagWeight    : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				eDocTagMax            : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				eDocTagWeight         : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>"},
				eBothAnswerMax        : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", direction:"left"},
				eBothAnswerWeight     : {required:"<ikep4j:message key='NotEmpty.socialRanking'/>", range:"<ikep4j:message key='Range.socialRanking.0_1000'/>", direction:"left"}						
			},
			notice : {
				typeWeightD : {message:"<ikep4j:message key='Notice.socialityType.typeWeight'/>"},
				typeWeightC : {message:"<ikep4j:message key='Notice.socialityType.typeWeight'/>"},
				typeWeightF : {message:"<ikep4j:message key='Notice.socialityType.typeWeight'/>"},
				typeWeightE : {message:"<ikep4j:message key='Notice.socialityType.typeWeight'/>", direction:"left"},
				validMonthC : {message:"<ikep4j:message key='Notice.socialityType.validMonth'/>", direction:"left"},
				validMonthF : {message:"<ikep4j:message key='Notice.socialityType.validMonth'/>", direction:"left"},
				dTeamMemberWeight     : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				dTeamLeaderWeight     : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				dFavoriteWeight       : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				dFollowingWeight      : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				dFollowerWeight       : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				dBothFollowingWeight  : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>", direction:"left"},
				cMailMax              : {message:"<ikep4j:message key='Notice.socialityPolicy.max'/>"},
				cMailWeight           : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				cMessageMax           : {message:"<ikep4j:message key='Notice.socialityPolicy.max'/>"},
				cMessageWeight        : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				cSmsMax               : {message:"<ikep4j:message key='Notice.socialityPolicy.max'/>"},
				cSmsWeight            : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				cGuestbookMax         : {message:"<ikep4j:message key='Notice.socialityPolicy.max'/>", direction:"left"},
				cGuestbookWeight      : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>", direction:"left"},
				fFollowerWeight       : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				fFollowingWeight      : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				fBothFollowingWeight  : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				fMyLinereplyMax       : {message:"<ikep4j:message key='Notice.socialityPolicy.max'/>"},
				fMyLinereplyWeight    : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				fMyMentionMax         : {message:"<ikep4j:message key='Notice.socialityPolicy.max'/>"},
				fMyMentionWeight      : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				fYourLinereplyMax     : {message:"<ikep4j:message key='Notice.socialityPolicy.max'/>"},
				fYourLinereplyWeight  : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				fYourMentionMax       : {message:"<ikep4j:message key='Notice.socialityPolicy.max'/>"},
				fYourMentionWeight    : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				fBothMbloggroupMax    : {message:"<ikep4j:message key='Notice.socialityPolicy.max'/>", direction:"left"},
				fBothMbloggroupWeight : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>", direction:"left"},
				eExpertTagMax         : {message:"<ikep4j:message key='Notice.socialityPolicy.max'/>"},
				eExpertTagWeight      : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				eInterestTagMax       : {message:"<ikep4j:message key='Notice.socialityPolicy.max'/>"},
				eInterestTagWeight    : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				eDocTagMax            : {message:"<ikep4j:message key='Notice.socialityPolicy.max'/>"},
				eDocTagWeight         : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>"},
				eBothAnswerMax        : {message:"<ikep4j:message key='Notice.socialityPolicy.max'/>", direction:"left"},
				eBothAnswerWeight     : {message:"<ikep4j:message key='Notice.socialityPolicy.compensator'/>", direction:"left"}				
			},
			submitHandler : function(form) {// 부가 검증 처리
				var sum = eval($("input[name=typeWeightD]").val()) + eval($("input[name=typeWeightC]").val()) + eval($("input[name=typeWeightF]").val()) + eval($("input[name=typeWeightE]").val());
				if(sum != 100) {
					alert("<ikep4j:message key='Notice.socialityType.typeWeight.sum'/>");  
					return;
				}
				
				form.submit();	
			}
		};
		new iKEP.Validator("#form", validOptions);

	});

	blurTypeWeightD = function() {$jq('#spanTypeWeightD').attr("innerHTML", eval($jq('input[name=typeWeightD]').val())/100);}
	blurTypeWeightC = function() {$jq('#spanTypeWeightC').attr("innerHTML", eval($jq('input[name=typeWeightC]').val())/100);}
	blurTypeWeightF = function() {$jq('#spanTypeWeightF').attr("innerHTML", eval($jq('input[name=typeWeightF]').val())/100);}
	blurTypeWeightE = function() {$jq('#spanTypeWeightE').attr("innerHTML", eval($jq('input[name=typeWeightE]').val())/100);}	
	
	blurDTeamMemberWeight    = function() {$jq('#spanDTeamMemberWeight').attr("innerHTML", $jq('input[name=dTeamMemberWeight]').val());}
	blurDTeamLeaderWeight    = function() {$jq('#spanDTeamLeaderWeight').attr("innerHTML", $jq('input[name=dTeamLeaderWeight]').val());}
	blurDFavoriteWeight      = function() {$jq('#spanDFavoriteWeight').attr("innerHTML", $jq('input[name=dFavoriteWeight]').val());}
	blurDFollowingWeight     = function() {$jq('#spanDFollowingWeight').attr("innerHTML", $jq('input[name=dFollowingWeight]').val());}
	blurDFollowerWeight      = function() {$jq('#spanDFollowerWeight').attr("innerHTML", $jq('input[name=dFollowerWeight]').val());}
	blurDBothFollowingWeight = function() {$jq('#spanDBothFollowingWeight').attr("innerHTML", $jq('input[name=dBothFollowingWeight]').val());}
	
	blurCMailMax             = function() {$jq('#span1CMailMax').attr("innerHTML", $jq('input[name=cMailMax]').val()); $jq('#span2CMailMax').attr("innerHTML", $jq('input[name=cMailMax]').val());}
	blurCMessageMax          = function() {$jq('#span1CMessageMax').attr("innerHTML", $jq('input[name=cMessageMax]').val()); $jq('#span2CMessageMax').attr("innerHTML", $jq('input[name=cMessageMax]').val());}
	blurCSmsMax              = function() {$jq('#span1CSmsMax').attr("innerHTML", $jq('input[name=cSmsMax]').val()); $jq('#span2CSmsMax').attr("innerHTML", $jq('input[name=cSmsMax]').val());}
	blurCGuestbookMax        = function() {$jq('#span1CGuestbookMax').attr("innerHTML", $jq('input[name=cGuestbookMax]').val()); $jq('#span2CGuestbookMax').attr("innerHTML", $jq('input[name=cGuestbookMax]').val());}
	
	blurCMailWeight          = function() {$jq('#spanCMailWeight').attr("innerHTML", $jq('input[name=cMailWeight]').val());}
	blurCMessageWeight       = function() {$jq('#spanCMessageWeight').attr("innerHTML", $jq('input[name=cMessageWeight]').val());}
	blurCSmsWeight           = function() {$jq('#spanCSmsWeight').attr("innerHTML", $jq('input[name=cSmsWeight]').val());}
	blurCGuestbookWeight     = function() {$jq('#spanCGuestbookWeight').attr("innerHTML", $jq('input[name=cGuestbookWeight]').val());}	

	blurFMyLinereplyMax       = function() {$jq('#span1FMyLinereplyMax').attr("innerHTML", $jq('input[name=fMyLinereplyMax]').val()); $jq('#span2FMyLinereplyMax').attr("innerHTML", $jq('input[name=fMyLinereplyMax]').val());}
	blurFMyMentionMax         = function() {$jq('#span1FMyMentionMax').attr("innerHTML", $jq('input[name=fMyMentionMax]').val()); $jq('#span2FMyMentionMax').attr("innerHTML", $jq('input[name=fMyMentionMax]').val());}
	blurFYourLinereplyMax     = function() {$jq('#span1FYourLinereplyMax').attr("innerHTML", $jq('input[name=fYourLinereplyMax]').val()); $jq('#span2FYourLinereplyMax').attr("innerHTML", $jq('input[name=fYourLinereplyMax]').val());}
	blurFYourMentionMax       = function() {$jq('#span1FYourMentionMax').attr("innerHTML", $jq('input[name=fYourMentionMax]').val()); $jq('#span2FYourMentionMax').attr("innerHTML", $jq('input[name=fYourMentionMax]').val());}
	blurFBothMbloggroupMax    = function() {$jq('#span1FBothMbloggroupMax').attr("innerHTML", $jq('input[name=fBothMbloggroupMax]').val()); $jq('#span2FBothMbloggroupMax').attr("innerHTML", $jq('input[name=fBothMbloggroupMax]').val());}  
	
	blurFFollowerWeight       = function() {$jq('#spanFFollowerWeight      ').attr("innerHTML", $jq('input[name=fFollowerWeight]').val());}
	blurFFollowingWeight      = function() {$jq('#spanFFollowingWeight     ').attr("innerHTML", $jq('input[name=fFollowingWeight]').val());}
	blurFBothFollowingWeight  = function() {$jq('#spanFBothFollowingWeight ').attr("innerHTML", $jq('input[name=fBothFollowingWeight]').val());}
	blurFMyLinereplyWeight    = function() {$jq('#spanFMyLinereplyWeight   ').attr("innerHTML", $jq('input[name=fMyLinereplyWeight]').val());}
	blurFMyMentionWeight      = function() {$jq('#spanFMyMentionWeight     ').attr("innerHTML", $jq('input[name=fMyMentionWeight]').val());}
	blurFYourLinereplyWeight  = function() {$jq('#spanFYourLinereplyWeight ').attr("innerHTML", $jq('input[name=fYourLinereplyWeight]').val());}
	blurFYourMentionWeight    = function() {$jq('#spanFYourMentionWeight   ').attr("innerHTML", $jq('input[name=fYourMentionWeight]').val());}
	blurFBothMbloggroupWeight = function() {$jq('#spanFBothMbloggroupWeight').attr("innerHTML", $jq('input[name=fBothMbloggroupWeight]').val());}
	
	blurEExpertTagMax      = function() {$jq('#span1EExpertTagMax').attr("innerHTML", $jq('input[name=eExpertTagMax]').val()); $jq('#span2EExpertTagMax').attr("innerHTML", $jq('input[name=eExpertTagMax]').val());}
	blurEInterestTagMax    = function() {$jq('#span1EInterestTagMax').attr("innerHTML", $jq('input[name=eInterestTagMax]').val()); $jq('#span2EInterestTagMax').attr("innerHTML", $jq('input[name=eInterestTagMax]').val());}
	blurEDocTagMax         = function() {$jq('#span1EDocTagMax').attr("innerHTML", $jq('input[name=eDocTagMax]').val()); $jq('#span2EDocTagMax').attr("innerHTML", $jq('input[name=eDocTagMax]').val());}
	blurEBothAnswerMax     = function() {$jq('#span1EBothAnswerMax').attr("innerHTML", $jq('input[name=eBothAnswerMax]').val()); $jq('#span2EBothAnswerMax').attr("innerHTML", $jq('input[name=eBothAnswerMax]').val());}
                                                                                                                          
	blurEExpertTagWeight   = function() {$jq('#spanEExpertTagWeight').attr("innerHTML", $jq('input[name=eExpertTagWeight]').val());}
	blurEInterestTagWeight = function() {$jq('#spanEInterestTagWeight').attr("innerHTML", $jq('input[name=eInterestTagWeight]').val());}
	blurEDocTagWeight      = function() {$jq('#spanEDocTagWeight').attr("innerHTML", $jq('input[name=eDocTagWeight]').val());}
	blurEBothAnswerWeight  = function() {$jq('#spanEBothAnswerWeight').attr("innerHTML", $jq('input[name=eBothAnswerWeight]').val());}	
})(jQuery);
</script>

				<h1 class="none"></h1>
				
				<!--subTitle_2 Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${prefix}' key='main.title'/></h2>
				</div>
				<!--//subTitle_2 End-->
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${prefix}' key='table1.title'/></h3>
				</div>
				<!--//subTitle_2 End-->				
				
				<form id="form" action="<c:url value="/socialpack/socialanalyzer/saveSocialDistance.do"/>" method="post">
				<!--blockDetail Start-->					
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary1'/>">
						<caption></caption>
						<tr>
							<th class="textCenter" scope="col" width="12%"><ikep4j:message pre='${prefix}' key='table1.column1'/></th>
							<th class="textCenter" scope="col" width="22%"><ikep4j:message pre='${prefix}' key='table1.column2'/> (0 &lt; = x &lt; =1)</th>
							<th class="textCenter" scope="col" width="22%"><ikep4j:message pre='${prefix}' key='table1.column3'/> (0 &lt; = x &lt; =1)</th>
							<th class="textCenter" scope="col" width="22%"><ikep4j:message pre='${prefix}' key='table1.column4'/> (0 &lt; = x &lt; =1)</th>
							<th class="textCenter" scope="col" width="22%"><ikep4j:message pre='${prefix}' key='table1.column5'/> (0 &lt; = x &lt; =1)</th>
						</tr>
						<tr>
							<th scope="col" class="textCenter"><ikep4j:message pre='${prefix}' key='table1.weight'/></th>
							<td scope="col" class="textCenter"><div><input name="typeWeightD" value="${relationTypeD.typeWeight}" class="inputbox" size="3" maxlength="3" type="text" title="input box" onblur="blurTypeWeightD()"/>%</div></td>
							<td scope="col" class="textCenter"><div><input name="typeWeightC" value="${relationTypeC.typeWeight}" class="inputbox" size="3" maxlength="3" type="text" title="input box" onblur="blurTypeWeightC()"/>%</div></td>
							<td scope="col" class="textCenter"><div><input name="typeWeightF" value="${relationTypeF.typeWeight}" class="inputbox" size="3" maxlength="3" type="text" title="input box" onblur="blurTypeWeightF()"/>%</div></td>
							<td scope="col" class="textCenter"><div><input name="typeWeightE" value="${relationTypeE.typeWeight}" class="inputbox" size="3" maxlength="3" type="text" title="input box" onblur="blurTypeWeightE()"/>%</div></td>
						</tr>
					</table>
				</div>
				<!--//blockDetail End-->
				
				<div class="blockBlank_10px"></div>
				
				<!--directive Start-->
				<div class="directive"> 
					<ul>
						<li><span><ikep4j:message pre='${prefix}' key='table1.comment'/></span></li>
					</ul>
				</div>
				<!--//directive End-->
				
				<!--debate_point Start-->
				<div class="debate_point">
					<h3><ikep4j:message pre='${prefix}' key='table1.title'/> =</h3>
					
					<div class="point_box">
						<span>(<ikep4j:message pre='${prefix}' key='table1.column2'/> × <span id="spanTypeWeightD">${relationTypeD.typeWeight/100}</span>) + (<ikep4j:message pre='${prefix}' key='table1.column3'/> × <span id="spanTypeWeightC">${relationTypeC.typeWeight/100}</span>) + (<ikep4j:message pre='${prefix}' key='table1.column4'/> × <span id="spanTypeWeightF">${relationTypeF.typeWeight/100}</span>) + (<ikep4j:message pre='${prefix}' key='table1.column5'/> × <span id="spanTypeWeightE">${relationTypeE.typeWeight/100}</span>)</span>
					</div>
				</div>
				<!--//debate_point End-->
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${prefix}' key='table2.title'/></h3>
				</div>
				<!--//subTitle_2 End-->				
				
				<!--blockDetail Start-->					
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary2'/>">
						<caption></caption>
						<thead>
							<tr>
								<th class="textCenter" scope="col" width="14%"><ikep4j:message pre='${prefix}' key='table2.column1'/></th>
								<th class="textCenter" scope="col" width="15%"><ikep4j:message pre='${prefix}' key='table2.column2'/> 0  or 1</th>
								<th class="textCenter" scope="col" width="15%"><ikep4j:message pre='${prefix}' key='table2.column3'/> 0  or 1</th>
								<th class="textCenter" scope="col" width="15%"><ikep4j:message pre='${prefix}' key='table2.column4'/> 0  or 1</th>
								<th class="textCenter" scope="col" width="14%"><ikep4j:message pre='${prefix}' key='table2.column5'/> 0  or 1</th>
								<th class="textCenter" scope="col" width="13%"><ikep4j:message pre='${prefix}' key='table2.column6'/> 0  or 1</th>
								<th class="textCenter" scope="col" width="14%"><ikep4j:message pre='${prefix}' key='table2.column7'/> 0  or 1</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th scope="col" class="textCenter"><ikep4j:message pre='${prefix}' key='table2.compensator'/></th>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.dTeamMemberWeight">   <input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurDTeamMemberWeight   ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.dTeamLeaderWeight">   <input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurDTeamLeaderWeight   ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.dFavoriteWeight">     <input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurDFavoriteWeight     ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.dFollowingWeight">    <input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurDFollowingWeight    ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.dFollowerWeight">     <input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurDFollowerWeight     ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.dBothFollowingWeight"><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurDBothFollowingWeight()"/></spring:bind></div></td>
							</tr>
						</tbody>
					</table>
					<div class="floatRight"><span class="colorPoint">※<ikep4j:message pre='${prefix}' key='table2.comment'/></span></div>
					<div class="clear"></div>
				</div>
				<!--//blockDetail End-->
								
				<!--debate_point Start-->
				<div class="debate_point">
					<h3><ikep4j:message pre='${prefix}' key='table2.title'/> =</h3>
					
					<div class="point_box">
						<span>(<ikep4j:message pre='${prefix}' key='table2.column2'/>?<span id="spanDTeamMemberWeight">${relationPolicy.dTeamMemberWeight}</span>:0) + (<ikep4j:message pre='${prefix}' key='table2.column3'/>?<span id="spanDTeamLeaderWeight">${relationPolicy.dTeamLeaderWeight}</span>:0) + (<ikep4j:message pre='${prefix}' key='table2.column4'/>?<span id="spanDFavoriteWeight">${relationPolicy.dFavoriteWeight}</span>:0) + (<ikep4j:message pre='${prefix}' key='table2.column5'/>?<span id="spanDFollowingWeight">${relationPolicy.dFollowingWeight}</span>:0) + (<ikep4j:message pre='${prefix}' key='table2.column6'/>?<span id="spanDFollowerWeight">${relationPolicy.dFollowerWeight}</span>:0) + (Both?<span id="spanDBothFollowingWeight">${relationPolicy.dBothFollowingWeight}</span>:0) / ∑<ikep4j:message pre='${prefix}' key='table2.compensator'/>;</span>
					</div>
				</div>
				<!--//debate_point End-->
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${prefix}' key='table3.title'/></h3>
					<div class="subTitle_2_input02"><span>※<ikep4j:message pre='${prefix}' key='table3.comment'/></span>&nbsp;&nbsp;<ikep4j:message pre='${prefix}' key='table3.search.recent'/> <input name="validMonthC" title="<ikep4j:message pre='${prefix}' key='table3.search.title'/>" class="inputbox" size="2" maxlength="2" type="text" value="${relationTypeC.validMonth}"/><ikep4j:message pre='${prefix}' key='table3.search.month'/></div>
				</div>
				<!--//subTitle_2 End-->
				
				<div class="blockBlank_5px"></div>
				
				<!--blockDetail Start-->					
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary3'/>">
						<caption></caption>
						<thead>
							<tr>
								<th class="textCenter" scope="col" width="12%"><ikep4j:message pre='${prefix}' key='table3.column1'/></th>
								<th class="textCenter" scope="col" width="22%"><ikep4j:message pre='${prefix}' key='table3.column2'/> (0 &lt; = x &lt; =1)</th>
								<th class="textCenter" scope="col" width="22%"><ikep4j:message pre='${prefix}' key='table3.column3'/> (0 &lt; = x &lt; =1)</th>
								<th class="textCenter" scope="col" width="22%"><ikep4j:message pre='${prefix}' key='table3.column4'/> (0 &lt; = x &lt; =1)</th>
								<th class="textCenter" scope="col" width="22%"><ikep4j:message pre='${prefix}' key='table3.column5'/> (0 &lt; = x &lt; =1)</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th scope="col" class="textCenter"><ikep4j:message pre='${prefix}' key='table3.maximum'/></th>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.cMailMax"             ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurCMailMax     ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.cMessageMax"          ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurCMessageMax  ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.cSmsMax"              ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurCSmsMax      ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.cGuestbookMax"        ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurCGuestbookMax()"/></spring:bind></div></td>
							</tr>
							<tr>
								<th scope="col" class="textCenter"><ikep4j:message pre='${prefix}' key='table3.compensator'/></th>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.cMailWeight"          ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurCMailWeight      ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.cMessageWeight"       ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurCMessageWeight   ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.cSmsWeight"           ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurCSmsWeight       ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.cGuestbookWeight"     ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurCGuestbookWeight ()"/></spring:bind></div></td>
							</tr>
						</tbody>
					</table>
					<div class="floatRight"><span class="colorPoint">※<ikep4j:message pre='${prefix}' key='table3.compensator.text'/></span></div>
					<div class="clear"></div>
				</div>
				<!--//blockDetail End-->
				
				<!--debate_point Start-->
				<div class="debate_point">
					<h3><ikep4j:message pre='${prefix}' key='table3.title'/> =</h3>
					
					<div class="point_box02">
						<span>(<ikep4j:message pre='${prefix}' key='table3.column2'/>&gt;=<span id="span1CMailMax">${relationPolicy.cMailMax}</span>?1:<ikep4j:message pre='${prefix}' key='table3.column2'/>/<span id="span2CMailMax">${relationPolicy.cMailMax}</span>)×<span id="spanCMailWeight">${relationPolicy.cMailWeight}</span> + (<ikep4j:message pre='${prefix}' key='table3.column3'/>&gt;=<span id="span1CMessageMax">${relationPolicy.cMessageMax}</span>?1:<ikep4j:message pre='${prefix}' key='table3.column3'/>/<span id="span2CMessageMax">${relationPolicy.cMessageMax}</span>)×<span id="spanCMessageWeight">${relationPolicy.cMessageWeight}</span> + (<ikep4j:message pre='${prefix}' key='table3.column4'/>&gt;=<span id="span1CSmsMax">${relationPolicy.cSmsMax}</span>?1: <ikep4j:message pre='${prefix}' key='table3.column4'/>/<span id="span2CSmsMax">${relationPolicy.cSmsMax}</span>)×<span id="spanCSmsWeight">${relationPolicy.cSmsWeight}</span> + (<ikep4j:message pre='${prefix}' key='table3.column5'/>&gt;=<span id="span1CGuestbookMax">${relationPolicy.cGuestbookMax}</span>?1:<ikep4j:message pre='${prefix}' key='table3.column5'/>/<span id="span2CGuestbookMax">${relationPolicy.cGuestbookMax}</span>)×<span id="spanCGuestbookWeight">${relationPolicy.cGuestbookWeight}</span> / ∑<ikep4j:message pre='${prefix}' key='table3.compensator'/>;</span>
					</div>
					<div class="point_box_txt">※<ikep4j:message pre='${prefix}' key='table3.comment.post'/></div>
				</div>
				<!--//debate_point End-->
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${prefix}' key='table4.title'/></h3>
					<div class="subTitle_2_input02"><span>※<ikep4j:message pre='${prefix}' key='table4.comment'/></span>&nbsp;&nbsp;<ikep4j:message pre='${prefix}' key='table4.search.recent'/> <input name="validMonthF" title="<ikep4j:message pre='${prefix}' key='table4.search.title'/>" class="inputbox" size="2" maxlength="2" type="text" value="${relationTypeF.validMonth}"/><ikep4j:message pre='${prefix}' key='table4.search.month'/></div>
				</div>
				<!--//subTitle_2 End-->
				
				<div class="blockBlank_5px"></div>			
				
				<!--blockDetail Start-->					
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary4'/>">
						<caption></caption>
						<thead>
							<tr>
								<th class="textCenter" scope="col" width="12%"><ikep4j:message pre='${prefix}' key='table4.column1'/></th>
								<th class="textCenter" scope="col" width="8%"><ikep4j:message pre='${prefix}' key='table4.column2'/> 0  or 1</th>
								<th class="textCenter" scope="col" width="9%"><ikep4j:message pre='${prefix}' key='table4.column3'/> 0  or 1</th>
								<th class="textCenter" scope="col" width="9%"><ikep4j:message pre='${prefix}' key='table4.column4'/> 0  or 1</th>
								<th class="textCenter" scope="col" width="11%"><ikep4j:message pre='${prefix}' key='table4.column5'/> 0&lt;=x&lt;=1</th>
								<th class="textCenter" scope="col" width="11%"><ikep4j:message pre='${prefix}' key='table4.column6'/> 0&lt;=x&lt;=1</th>
								<th class="textCenter" scope="col" width="12%"><ikep4j:message pre='${prefix}' key='table4.column7'/> 0&lt;=x&lt;=1</th>
								<th class="textCenter" scope="col" width="12%"><ikep4j:message pre='${prefix}' key='table4.column8'/> 0&lt;=x&lt;=1</th>
								<th class="textCenter" scope="col" width="16%"><ikep4j:message pre='${prefix}' key='table4.column9'/> 0&lt;=x&lt;=1</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th scope="col" class="textCenter"><ikep4j:message pre='${prefix}' key='table4.maximum'/></th>
								<td scope="col" class="textCenter"></td>
								<td scope="col" class="textCenter"></td>
								<td scope="col" class="textCenter"></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.fMyLinereplyMax"      ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFMyLinereplyMax   ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.fMyMentionMax"        ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFMyMentionMax     ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.fYourLinereplyMax"    ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFYourLinereplyMax ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.fYourMentionMax"      ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFYourMentionMax   ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.fBothMbloggroupMax"   ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFBothMbloggroupMax()"/></spring:bind></div></td>
							</tr>
							<tr>
								<th scope="col" class="textCenter"><ikep4j:message pre='${prefix}' key='table4.compensator'/></th>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.fFollowerWeight"      ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFFollowerWeight      ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.fFollowingWeight"     ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFFollowingWeight     ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.fBothFollowingWeight" ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFBothFollowingWeight ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.fMyLinereplyWeight"   ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFMyLinereplyWeight   ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.fMyMentionWeight"     ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFMyMentionWeight     ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.fYourLinereplyWeight" ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFYourLinereplyWeight ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.fYourMentionWeight"   ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFYourMentionWeight   ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.fBothMbloggroupWeight"><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurFBothMbloggroupWeight()"/></spring:bind></div></td>
							</tr>
						</tbody>
					</table>
					<div class="floatRight"><span class="colorPoint">※<ikep4j:message pre='${prefix}' key='table4.comment.post'/></span></div>
					<div class="clear"></div>
				</div>
				<!--//blockDetail End-->
				
				<!--debate_point Start-->
				<div class="debate_point">
					<h3><ikep4j:message pre='${prefix}' key='table4.title'/> =</h3>
					
					<div class="point_box">
						<span>(<ikep4j:message pre='${prefix}' key='table4.column2'/>?<span id="spanFFollowerWeight">${relationPolicy.fFollowerWeight}</span>:0) + (<ikep4j:message pre='${prefix}' key='table4.column3'/>?<span id="spanFFollowingWeight">${relationPolicy.fFollowingWeight}</span>:0) + (<ikep4j:message pre='${prefix}' key='table4.column4'/>?<span id="spanFBothFollowingWeight">${relationPolicy.fBothFollowingWeight}</span>:0) + (<ikep4j:message pre='${prefix}' key='table4.column5'/>&gt;=<span id="span1FMyLinereplyMax">${relationPolicy.fMyLinereplyMax}</span>?1:<ikep4j:message pre='${prefix}' key='table4.column5'/>/<span id="span2FMyLinereplyMax">${relationPolicy.fMyLinereplyMax}</span>)×<span id="spanFMyLinereplyWeight">${relationPolicy.fMyLinereplyWeight}</span> + (<ikep4j:message pre='${prefix}' key='table4.column6'/> &gt;=<span id="span1FMyMentionMax">${relationPolicy.fMyMentionMax}</span>?1:<ikep4j:message pre='${prefix}' key='table4.column6'/>/<span id="span2FMyMentionMax">${relationPolicy.fMyMentionMax}</span>)×<span id="spanFMyMentionWeight">${relationPolicy.fMyMentionWeight}</span> + (<ikep4j:message pre='${prefix}' key='table4.column7'/> &gt;=<span id="span1FYourLinereplyMax">${relationPolicy.fYourLinereplyMax}</span>?1: <ikep4j:message pre='${prefix}' key='table4.column7'/>/<span id="span2FYourLinereplyMax">${relationPolicy.fYourLinereplyMax}</span>)×<span id="spanFYourLinereplyWeight">${relationPolicy.fYourLinereplyWeight}</span> + (<ikep4j:message pre='${prefix}' key='table4.column8'/> &gt;=<span id="span1FYourMentionMax">${relationPolicy.fYourMentionMax}</span>?1:<ikep4j:message pre='${prefix}' key='table4.column8'/>/<span id="span2FYourMentionMax">${relationPolicy.fYourMentionMax}</span>)×<span id="spanFYourMentionWeight">${relationPolicy.fYourMentionWeight}</span> + (<ikep4j:message pre='${prefix}' key='table4.column9'/>&gt;=<span id="span1FBothMbloggroupMax">${relationPolicy.fBothMbloggroupMax}</span>?1:<ikep4j:message pre='${prefix}' key='table4.column9'/>/<span id="span2FBothMbloggroupMax">${relationPolicy.fBothMbloggroupMax}</span>)×<span id="spanFBothMbloggroupWeight">${relationPolicy.fBothMbloggroupWeight}</span> / ∑<ikep4j:message pre='${prefix}' key='table4.compensator'/>;</span>
					</div>
				</div>
				<!--//debate_point End-->
				
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3><ikep4j:message pre='${prefix}' key='table5.title'/></h3>
				</div>
				<!--//subTitle_2 End-->
				
				<!--blockDetail Start-->
				<div class="blockDetail">
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary5'/>">
						<caption></caption>
						<thead>
							<tr>
								<th class="textCenter" scope="col" width="12%"><ikep4j:message pre='${prefix}' key='table5.column1'/></th>
								<th class="textCenter" scope="col" width="22%"><ikep4j:message pre='${prefix}' key='table5.column2'/> 0&lt;=x&lt;=1</th>
								<th class="textCenter" scope="col" width="22%"><ikep4j:message pre='${prefix}' key='table5.column3'/> 0&lt;=x&lt;=1</th>
								<th class="textCenter" scope="col" width="22%"><ikep4j:message pre='${prefix}' key='table5.column4'/> 0&lt;=x&lt;=1</th>
								<th class="textCenter" scope="col" width="22%"><ikep4j:message pre='${prefix}' key='table5.column5'/> 0&lt;=x&lt;=1</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th scope="col" class="textCenter"><ikep4j:message pre='${prefix}' key='table5.maximum'/></th>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.eExpertTagMax"        ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurEExpertTagMax  ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.eInterestTagMax"      ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurEInterestTagMax()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.eDocTagMax"           ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurEDocTagMax     ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.eBothAnswerMax"       ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurEBothAnswerMax ()"/></spring:bind></div></td>
							</tr>
							<tr>
								<th scope="col" class="textCenter"><ikep4j:message pre='${prefix}' key='table5.compensator'/></th>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.eExpertTagWeight"     ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurEExpertTagWeight  ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.eInterestTagWeight"   ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurEInterestTagWeight()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.eDocTagWeight"        ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurEDocTagWeight     ()"/></spring:bind></div></td>
								<td scope="col" class="textCenter"><div><spring:bind path="relationPolicy.eBothAnswerWeight"    ><input name="${status.expression}" value="${status.value}" class="inputbox" size="4" maxlength="4" type="text" title="input box" onblur="blurEBothAnswerWeight ()"/></spring:bind></div></td>
							</tr>
						</tbody>
					</table>
					<div class="floatRight"><span class="colorPoint">※<ikep4j:message pre='${prefix}' key='table5.comment1'/></span></div>
					<div class="clear"></div>
				</div>
				<!--//blockDetail End-->
				</form>
				
				<!--debate_point Start-->
				<div class="debate_point">
					<h3><ikep4j:message pre='${prefix}' key='table5.title'/> =</h3>
					
					<div class="point_box02">
						<span>(<ikep4j:message pre='${prefix}' key='table5.column2'/> &gt;=<span id="span1EExpertTagMax">${relationPolicy.eExpertTagMax}</span>?1:<ikep4j:message pre='${prefix}' key='table5.column2'/>/<span id="span2EExpertTagMax">${relationPolicy.eExpertTagMax}</span>)×<span id="spanEExpertTagWeight">${relationPolicy.eExpertTagWeight}</span> + (<ikep4j:message pre='${prefix}' key='table5.column3'/> &gt;=<span id="span1EInterestTagMax">${relationPolicy.eInterestTagMax}</span>?1:<ikep4j:message pre='${prefix}' key='table5.column3'/>/<span id="span2EInterestTagMax">${relationPolicy.eInterestTagMax}</span>)×<span id="spanEInterestTagWeight">${relationPolicy.eInterestTagWeight}</span> + (<ikep4j:message pre='${prefix}' key='table5.column4'/> &gt;=<span id="span1EDocTagMax">${relationPolicy.eDocTagMax}</span>?1: <ikep4j:message pre='${prefix}' key='table5.column4'/>/<span id="span2EDocTagMax">${relationPolicy.eDocTagMax}</span>)×<span id="spanEDocTagWeight">${relationPolicy.eDocTagWeight}</span> + (<ikep4j:message pre='${prefix}' key='table5.column5'/> &gt;=<span id="span1EBothAnswerMax">${relationPolicy.eBothAnswerMax}</span>?1: <ikep4j:message pre='${prefix}' key='table5.column5'/>/<span id="span2EBothAnswerMax">${relationPolicy.eBothAnswerMax}</span>)×<span id="spanEBothAnswerWeight">${relationPolicy.eBothAnswerWeight}</span> / ∑<ikep4j:message pre='${prefix}' key='table5.compensator'/>;</span>
					</div>
					<div class="point_box_txt">※<ikep4j:message pre='${prefix}' key='table5.comment2'/></div>
				</div
				><!--//debate_point End-->
				
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>
						<li><a class="button" href="#a" id="saveButton"><span><ikep4j:message pre='${buttonPrefix}' key='modify'/></span></a></li>
					</ul>
				</div>
				<!--//blockButton End-->

