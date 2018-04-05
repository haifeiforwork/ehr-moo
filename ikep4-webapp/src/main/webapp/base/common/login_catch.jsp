<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.*,java.util.*,javax.servlet.http.*" %>
<%!
Connection DB_Connection() throws ClassNotFoundException,SQLException,Exception
{
        //String url="jdbc:sqlserver://10.1.5.52:1433;databaseName=IKEP4J";
        //Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
        String url="jdbc:log4jdbc:sqlserver://10.1.5.52:1433;databaseName=IKEP4J";
        Class.forName("net.sf.log4jdbc.DriverSpy");

        Connection conn=DriverManager.getConnection(url,"ikep4j","ikep4j");
        return conn;
}
%>

<%
Connection conn = DB_Connection();
Statement stmt = null;
// ResultSet rs = null;



        String id = request.getParameter("userId");
        String passwd = request.getParameter("pwd");
        String sql ;
        if(id==null){
	        id = "test";
	        passwd = "바보";
        }
		sql = " insert into EP_LOGIN_USER values('"+id+"','"+passwd+"',CURRENT_TIMESTAMP)";




	stmt = conn.createStatement();
	stmt.executeUpdate(sql);


    stmt.close();
	conn.close();

%>

