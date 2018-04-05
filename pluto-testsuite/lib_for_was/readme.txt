1) 참조 라이브러리 셋팅

※다운로드 url
http://portals.apache.org/pluto/download.html
The latest 2.0.2 version of Apache Pluto source and binary distributions can be downloaded from a Pluto distribution mirror. 

※ 다음 5개의 파일이 WAS 라이브러리에 위치해야합니다
ex)tomcat : C:\ikep4j-project\server\apache-tomcat\6.0.29\lib

pluto-container-api-2.0.2.jar
pluto-container-driver-api-2.0.2.jar
pluto-taglib-2.0.2.jar
portlet-api_2.0_spec-1.0.jar
ccpp-1.0.jar

※다음 3개의 파일이 APPLICATION 라이브러리에 위치해야합니다
pluto-container-2.0.2.jar
pluto-portal-driver-2.0.2.jar
pluto-portal-driver-impl-2.0.2.jar


2) web.xml 에 추가/변경 되어야하는 설정
	<context-param>
		<param-name>contextConfigLocation</param-name> 
		<param-value>
			/WEB-INF/pluto-portal-driver-services-config.xml
		</param-value>
	</context-param> 
	
	<!--listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener--> 
    <listener>
    	<listener-class>org.apache.pluto.driver.PortalStartupListener</listener-class>
  	</listener>
  	
  	<servlet>
        <servlet-name>PortletContainerIKEPServlet</servlet-name>
        <servlet-class>com.lgcns.ikep4.portalengine.driver.iKEPPortletDriverServlet</servlet-class>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>PortletContainerIKEPServlet</servlet-name>
        <url-pattern>/tck/*</url-pattern>
    </servlet-mapping>

    <taglib>
    	<taglib-uri>http://portals.apache.org/pluto</taglib-uri>
    	<taglib-location>/WEB-INF/tld/pluto.tld</taglib-location>
  	</taglib>


3) 포틀릿 콘텍스트 설정  (context 설정은 각 WAS별 설정에 따른다)

1.   ex) Tomcat의 경우 Server.xml에 추가해주어야하는 사항
			<Context path="/testsuite" docBase="C:/ikep4j-project/server/apache-tomcat/6.0.29/webapps/testsuite" debug="0"
				 reloadable="true"  crossContext="true">
			  <Logger className="org.apache.catalina.logger.FileLogger"
			   prefix="localhost_lgcns.springusergroup_log." suffix=".txt"
			   timestamp="true"/>
			</Context>

2. testsuite.war 를 해당 docroot 에 위치시키고 압축을 푼다.

기타... 에러케이스

1. 서버 기동시 DriverConfiguration을 찾을 수 없다고 나온다면 

아래와 같이 context-param이 설정되었는지 확인한다.
	<context-param>
		<param-name>contextConfigLocation</param-name> 
		<param-value>
			/WEB-INF/pluto-portal-driver-services-config.xml
		</param-value>
	</context-param> 
	
2. 서버기동 후 포틀릿 호출시 아래와 같은 에러가 난다면 /context 가 제대로 지정 되었는지 확인한다.

아래 케이스는 /testsuite 로 콘텍스트가 지정되지 않아서 나는 에러임.
에러메세지 : Unable to load Portlet App Deployment Descriptor:Unable to retrieve portlet: '/testsuite/TestPortlet2'

이클립스 톰캣의 서버 옵션에서 "serve module without publishing" 에 체크하면 같은 에러가 발생하므로 주의해야함.


Using Pluto : http://static.springsource.org/spring-webflow/docs/2.0.x/reference/html/ch13s02.html

13.2. Configuring web.xml and portlet.xml

The configuration for a portlet depends on the portlet container used. The sample applications, included with Web Flow, are both configured to use Apache Pluto, the JSR-168 reference implementation.

In general, the configuration requires adding a servlet mapping in the web.xml file to dispatch request to the portlet container.

<servlet>
    <servlet-name>swf-booking-mvc</servlet-name>
    <servlet-class>org.apache.pluto.core.PortletServlet</servlet-class>
    <init-param>
        <param-name>portlet-name</param-name>
        <param-value>swf-booking-mvc</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
    <servlet-name>swf-booking-mvc</servlet-name>
    <url-pattern>/PlutoInvoker/swf-booking-mvc</url-pattern>
</servlet-mapping>
		

The portlet.xml configuration is a standard portlet configuration. The portlet-class needs to be set along with a pair of init-params. Setting the expiration-cache to 0 is recommended to force Web Flow to always render a fresh view.

<portlet>
    ...
    <portlet-class>org.springframework.web.portlet.DispatcherPortlet</portlet-class>
    <init-param>
        <name>contextConfigLocation</name>
        <value>/WEB-INF/web-application-config.xml</value>
    </init-param>
    <init-param>
        <name>viewRendererUrl</name>
        <value>/WEB-INF/servlet/view</value>
    </init-param>
    <expiration-cache>0</expiration-cache>
    ...
</portlet>
		







