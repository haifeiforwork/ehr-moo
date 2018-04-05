<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<body>
<!-- <iframe width="100" height="100" src="http://10.1.5.53/common/sso.do?d=4&ssoParam=mailUid=kangdw,domain=eptest.co.kr"></iframe> -->
<script type="text/javascript">
// 개발, 운영 메일서버 URL 구분 
		var mailHost ="http://webmail.moorim.co.kr";
		var mailDomain ="moorim.co.kr";
		var mailD="4";
		var regurl=/eptest.moorim.co.kr/g;
		
		if (regurl.test(location.href)) { 
			mailHost="http://mailtest.moorim.co.kr";
			mailDomain="eptest.co.kr";
			mailD="2";
		}
//alert(mailHost+"/common/sso.do?d=4&ssoParam=mailUid=${mailUserId},domain="+mailDomain);
location.href=mailHost+"/common/sso.do?d="+mailD+"&ssoParam=mailUid=${mailUserId},domain="+mailDomain;
</script>
</body>
</html>
