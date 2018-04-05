package com.lgcns.ikep4.util.messenger;

import java.net.Socket;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Vector;
import java.util.Calendar;

import com.lgcns.ikep4.util.PropertyLoader;

public class AtMessengerCommunicator extends Thread
{
	String ip;
	int port;
	
	char DOM = 15;
	
	Vector <String> uidAr;
	Vector <String> cntAr;
	Vector <String> urlAr;
	
	public AtMessengerCommunicator(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
		
		uidAr = new Vector<String>();
		cntAr = new Vector<String>();
		urlAr = new Vector<String>();
	}
	
	public void addMessage(String _recipientUId, String _content, String _url)
	{
		char DOMMI = 15;
		
		//_content = _content.replaceAll("\n", "<br>");
		
		Calendar cal = Calendar.getInstance();
		String indt = cal.getTimeInMillis() + "";
		if ( indt.length() > 10 ) indt = indt.substring(0,10);
		
		StringBuffer msg = new StringBuffer();
		msg.append(_content);
		msg.append(DOMMI);
		msg.append("0");
		msg.append(DOMMI);
		msg.append("0");
		msg.append(DOMMI);
		
		uidAr.add(_recipientUId);
		cntAr.add(msg.toString());
		urlAr.add(_url);
	}
	
	public void addMessage(String _recipientUId, String _content, String _url, int _width, int _height)
	{
		char DOMMI = 15;
		
		//_content = _content.replaceAll("\n", "<br>");
		
		Calendar cal = Calendar.getInstance();
		String indt = cal.getTimeInMillis() + "";
		if ( indt.length() > 10 ) indt = indt.substring(0,10);
		
		StringBuffer msg = new StringBuffer();
		msg.append(_content);
		msg.append(DOMMI);
		msg.append(_width);
		msg.append(DOMMI);
		msg.append(_height);
		
		uidAr.add(_recipientUId);
		cntAr.add(msg.toString());
		urlAr.add(_url);
	}

	public void addMessage(String _recipientUId, String sender, String _content, String _url, String _shortMessage)
	{
		char DOMMI = 15;

		Calendar cal = Calendar.getInstance();
		String sendedTime = cal.getTimeInMillis() + "";
		if (sendedTime.length() > 10) sendedTime = sendedTime.substring(0, 10);

		StringBuffer msg = new StringBuffer();
		msg.append("0");
		msg.append(DOMMI);
		msg.append("1");
		msg.append(DOMMI);
		msg.append("ALERTMSG");
		msg.append(DOMMI);
		msg.append(_url);
		msg.append(DOMMI);
		msg.append("Reserved");
		msg.append(DOMMI);
		msg.append(_recipientUId);	
		msg.append(DOMMI);
		msg.append(sender);		
		msg.append(DOMMI);
		msg.append("Reserved");
		msg.append(DOMMI);
		msg.append("Reserved");
		msg.append(DOMMI);
		msg.append("Reserved");
		msg.append(DOMMI);
		msg.append("2");
		msg.append(DOMMI);
		msg.append(sendedTime);
		msg.append(DOMMI);
		msg.append("0");
		msg.append(DOMMI);
		msg.append("Reserved");
		msg.append(DOMMI);
		msg.append(_content);		
		msg.append(DOMMI);
		msg.append(_shortMessage);	

		uidAr.add(_recipientUId);
		cntAr.add(msg.toString());
		urlAr.add("SDS");
	}
	
	public void addMessage(String _recipientUId, String sender, String _content, String _url, String _showMessage, String _Type) {

        char DOMMI = '\017';

        Calendar cal = Calendar.getInstance();
        String sendedTime = cal.getTimeInMillis() + "";
        if (sendedTime.length() > 10)
            sendedTime = sendedTime.substring(0, 10);

        StringBuffer msg = new StringBuffer();
        msg.append("0");
        msg.append(DOMMI);
        msg.append("1");
        msg.append(DOMMI);
        msg.append(_Type);
        msg.append(DOMMI);
        msg.append(_url);
        msg.append(DOMMI);
        msg.append("Reserved");
        msg.append(DOMMI);
        msg.append(_recipientUId);
        msg.append(DOMMI);
        msg.append(sender);
        msg.append(DOMMI);
        msg.append("Reserved");
        msg.append(DOMMI);
        msg.append("Reserved");
        msg.append(DOMMI);
        msg.append("Reserved");
        msg.append(DOMMI);
        msg.append("2");
        msg.append(DOMMI);
        msg.append(sendedTime);
        msg.append(DOMMI);
        msg.append("0");
        msg.append(DOMMI);
        msg.append("Reserved");
        msg.append(DOMMI);
        msg.append(_content);
        msg.append(DOMMI);
        msg.append(_showMessage);
        msg.append(DOMMI);
        msg.append("0");

        this.uidAr.add(_recipientUId);
        this.cntAr.add(msg.toString());
        this.urlAr.add("SDS");
    }
	
	// Window Size
	public void addMessage(String _recipientUId, String sender, String _content, String _url, String _shortMessage, int _width, int _height)
	{
		char DOMMI = 15;

		Calendar cal = Calendar.getInstance();
		String sendedTime = cal.getTimeInMillis() + "";
		if (sendedTime.length() > 10) sendedTime = sendedTime.substring(0, 10);

		StringBuffer msg = new StringBuffer();
		msg.append("0");
		msg.append(DOMMI);
		msg.append("1");
		msg.append(DOMMI);
		msg.append("ALERTMSG");
		msg.append(DOMMI);
		msg.append(_url);
		msg.append(DOMMI);
		msg.append("Reserved");
		msg.append(DOMMI);
		msg.append(_recipientUId);	
		msg.append(DOMMI);
		msg.append(sender);		
		msg.append(DOMMI);
		msg.append("Reserved");
		msg.append(DOMMI);
		msg.append("Reserved");
		msg.append(DOMMI);
		msg.append("Reserved");
		msg.append(DOMMI);
		msg.append("2");
		msg.append(DOMMI);
		msg.append(sendedTime);
		msg.append(DOMMI);
		msg.append("0");
		msg.append(DOMMI);
		msg.append("Reserved");
		msg.append(DOMMI);
		msg.append(_content);		
		msg.append(DOMMI);
		msg.append(_shortMessage);		
		msg.append(DOMMI);
		msg.append(_width);
		msg.append(DOMMI);
		msg.append(_height);

		uidAr.add(_recipientUId);
		cntAr.add(msg.toString());
		urlAr.add("SDS");
	}
	
	public void send()
	{
		this.start();
	}
	
	public void run()
	{
		Socket sc = null;
		InputStreamReader ir = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		
		try
		{
			sc = new Socket(ip, port);
			ir = new InputStreamReader(sc.getInputStream());
			br = new BufferedReader(ir);
			pw = new PrintWriter(sc.getOutputStream(), true);
			
			
			for ( int i = 0 ; i < uidAr.size() ; i++ )
			{
				StringBuffer sndMsg = new StringBuffer();
				
				sndMsg.append("SYSMSG");
				sndMsg.append("\t");
				sndMsg.append((String)uidAr.elementAt(i));				
				sndMsg.append("\t");
				sndMsg.append((String)cntAr.elementAt(i));
				sndMsg.append("\t");
				sndMsg.append((String)urlAr.elementAt(i));
				sndMsg.append("\f");
				

				//System.out.println("port: " + port);
				System.out.println("sndMsg: [" + sndMsg.toString() + "]");

				 //pw.println(sndMsg.toString());
                 sc.getOutputStream().write((sndMsg.toString() + "\n").getBytes("EUC-KR"));
				
				if ( !getMessage(br, "ok") ) throw new Exception("Cannot send message");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try { ir.close(); ir = null; } catch(Exception ee) {}
			try { br.close(); br = null; } catch(Exception ee) {}
			try { pw.close(); pw = null; } catch(Exception ee) {}
			try { sc.close(); sc = null; } catch(Exception ee) {}
		}
	}
	
	private boolean getMessage(BufferedReader br, String targetStr) throws Exception
	{
		char buf[] = new char[1024];
		int rcv = 0;
		
		StringBuffer str = new StringBuffer();
		
		while ( ( rcv = br.read(buf, 0, 1024) ) >= 0 )
		{
			str.append(new String(buf, 0, rcv));

			//System.out.println("str: [" + str.toString() + "]");
			
			if ( str.indexOf(targetStr) >= 0 )
				return true;
		}
		
		return false;
	}
	
	public static void main(String[] args)
	{
		
		Properties prop = PropertyLoader.loadProperties("/configuration/messenger.properties");
		String serverIp = prop.getProperty("messenger.server.ip");
		String serverPort = prop.getProperty("messenger.server.port");
		
		AtMessengerCommunicator atmc = new AtMessengerCommunicator(serverIp, Integer.parseInt(serverPort));
			
		atmc.addMessage("cnskcm", "관리자", "전자우편 제목이나 메일 내용<br><br>두줄띄운다", "http://www.naver.com", "[게시물] 알림이 도착하였습니다.");
		atmc.send();
	}
}

