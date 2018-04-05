package devpia.dextwebeditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.mail.internet.MimeUtility;
import javax.mail.MessagingException;
import java.net.*;
import java.io.*;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;


public class DEXTWebEditor {

	class MimePart
	{
		private String bodypart;
		private String contentID;
		private String encode;
		private String name;
		private String charset;
		private String replacecid;
	}
	
	/**
	 * 파일 서비스
	 */
	private FileService fileService;
	protected Vector uploadFiles;

	private String boundary;
	private Vector decodePart;
	protected List<FileLink> fileLinkList;
	protected String serverPort;
	protected String serverDomain;

	private String uploadpath;
	private String uploadurl;
	private	String devpia = "DEXTWebEditor MIME Control";
	private String body_header;
	private String body_body;
	private String htmldata;
	private String replaceCidName;

	private String tempctype = "";
	private	String tempencode = "";
	private	String tempcid = "";
	private	String tempname = "";
	private	String tempcharset = "";
	private String body_part[];
	private int html_found;
	
	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
		
	public String getServerDomain() {
		return serverDomain;
	}

	public void setServerDomain(String serverDomain) {
		this.serverDomain = serverDomain;
	}
	
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
	
	public FileService getFileService() {
		return this.fileService;
	}
	
	public void setUploadFiles(Vector uploadFiles) {
		uploadFiles = uploadFiles;
	}

	public Vector getUploadFiles() {
		return uploadFiles;
	}

	public List getFileLinkList() {

		fileLinkList = new ArrayList<FileLink>();
		
		for (int i = 0; i < uploadFiles.size(); i++) {
			FileData file = (FileData) uploadFiles.get(i);
			FileLink fileLink = new FileLink();
			fileLink.setFileId(file.getFileId());
			fileLink.setFlag("add");
			fileLinkList.add(fileLink);
		}

		return fileLinkList;
	}


	public String DEXTMimeDecode(String dextcont) throws IOException,FileNotFoundException, UnsupportedEncodingException
	{
		if(dextcont.equals(""))
		{
			htmldata = "디코딩할 내용이 없습니다";
			return htmldata;
		}
		

		MimePart part;

		decodePart = null;
		decodePart = new Vector();
		
		uploadFiles = new Vector();

		// main function call
		checkMimeData(dextcont);
		htmlBodyDecode();
		saveDecodeFile();

		return htmldata;

	}

	protected void checkMimeData(String dextcont) throws IOException,FileNotFoundException, UnsupportedEncodingException
	{
		String mimedata = dextcont;
		{
			int devpiaPosition = mimedata.indexOf(devpia);
			if( devpiaPosition >= 0)
			{
				devpia = "devpia";
			}
			else
			{
				devpia = "other";
			}
		}

		boundary = "boundary=\"";
		{
			int boundaryPosition = mimedata.indexOf("boundary=\"");

			if(-1 == boundaryPosition)
			{
				htmldata = "정상적인 MIME 데이터가 아닙니다.";
				return;
			}
			else
			{
				int boundaryEndPos = mimedata.indexOf("\"", boundaryPosition+boundary.length());
				boundary = mimedata.substring(boundaryPosition + boundary.length(), boundaryEndPos);
				//System.out.println("boundary = " + boundary);
			}
		}
		//=====================================
		//Boundary 단위로 split 하여 분석 한다
		//=====================================
		body_part = mimedata.split("--" + boundary);
		//System.out.println("body_part length = " + body_part.length);

		String rootbody;
		html_found = 0;

		for (int  i=0; i<body_part.length; i++)
		{
			rootbody = body_part[i];
			//System.out.println("rootbody(" + i + ") = " + rootbody);
			bodyAnalysis(rootbody);
		}
	}

	protected void bodyAnalysis(String rootbody)  throws IOException,FileNotFoundException, UnsupportedEncodingException
	{
		//---------------------------------------------------------------------------------
		// HTML본문과 저장할 이미지파일을 분리해낸다
		// header  정보를 추출하여 part 에 저장한다
		//---------------------------------------------------------------------------------
		// 재사용 변수 초기화
		tempctype = "";
		tempencode = "";
		tempcid = "";
		tempname = "";
		tempcharset = "";
		body_header = "";
		body_body = "";

		//part
		MimePart part = new MimePart();

		rootbody = rootbody.trim();
		//System.out.println("rootbody trim = " + rootbody);

		int pos = rootbody.indexOf("\r\n\r\n");
		if(pos != -1)
		{
			body_header = rootbody.substring(0, pos).trim();
			body_body = rootbody.substring(pos+1).trim();
			//System.out.println("body_header = " + body_header);
			//System.out.println("body_body = " + body_body);
		}
		else
			return;

		String compare = "";
		compare = body_header.toLowerCase();
		//====================================================================
		pos = compare.indexOf("content-type:");
		if(pos != -1)
		{
			pos += 13;
			while( pos < body_header.length() )
			{
				if( body_header.charAt(pos) == 10 || body_header.charAt(pos) == 13 ) break;
				else if( body_header.charAt(pos) != 32 && body_header.charAt(pos) != 34 && body_header.charAt(pos) != 59 )
								tempctype = tempctype + body_header.charAt(pos);
				pos++;
			}
			//System.out.println("content-type = " + tempctype);
		}

		//====================================================================
		pos = compare.indexOf("charset=");
		if(pos != -1)
		{
			pos += 8;
			while( pos < body_header.length() )
			{
				if( body_header.charAt(pos) == 10 || body_header.charAt(pos) == 13 ) break;
				else if( body_header.charAt(pos) != 32 && body_header.charAt(pos) != 34 && body_header.charAt(pos) != 59 )
								tempcharset = tempcharset + body_header.charAt(pos);
				pos++;
			}
			//System.out.println("charset = " + tempcharset);
		}

		//====================================================================
		pos = compare.indexOf("content-transfer-encoding:");
		if(pos != -1)
		{
			pos += 26;
			while( pos < body_header.length() )
			{
				if( body_header.charAt(pos) == 10 || body_header.charAt(pos) == 13 ) break;
				else if( body_header.charAt(pos) != 32 && body_header.charAt(pos) != 34 && body_header.charAt(pos) != 59 )
								tempencode = tempencode + body_header.charAt(pos);
				pos++;
			}
			//System.out.println("content-transfer-encoding = " + tempencode);
		}

		//====================================================================
		pos = compare.indexOf("content-id:");
		if(pos != -1)
		{
			pos += 11;
			while( pos < body_header.length() )
			{
				if( body_header.charAt(pos) == 10 || body_header.charAt(pos) == 13 ) break;
				else if( body_header.charAt(pos) != 32 && body_header.charAt(pos) != 34 &&
								body_header.charAt(pos) != 60 && body_header.charAt(pos) != 62 &&	body_header.charAt(pos) != 59 )
							tempcid = tempcid + body_header.charAt(pos);
				pos++;
			}
			//System.out.println("content-id = " + tempcid);
		}

		//====================================================================
		pos = compare.indexOf("name=\"");
		if(pos != -1)
		{
			pos += 6;
			String workname = "";
			while( pos < body_header.length() )
			{
				if( body_header.charAt(pos) == 34 )	break;
				else if( body_header.charAt(pos) != 32 && body_header.charAt(pos) != 34 && body_header.charAt(pos) != 59 )
								workname = workname + body_header.charAt(pos);
				pos++;
			}
			//System.out.println("workname = " + workname);

			String workarr[] = workname.split("\r\n");
			//System.out.println("workarr length = " + workarr.length);

			String wmode = "";
			String wname = "";
			String wcset = "";
			String w_split[];

			for (int  i=0; i<workarr.length; i++)
			{
				w_split = workarr[i].split("[\u003F]");	//split question mark
				if( w_split.length  == 5 )
				{
					wcset = w_split[1].toLowerCase();
					wmode = w_split[2].toLowerCase();
					wname += w_split[3].trim();
				}
				else
					wname += w_split[0];
			}

			if( wmode == "" || wcset == "" )
				tempname = wname;
			else
			{

				if(devpia.equals("devpia"))
				{
					if( wcset.equals("iso-8859-1") )
					{
						wcset = "ks_c_5601-1987";
					}
				}
				if( wmode.equals("q") )	wmode = "quoted-printable";
				if( wmode.equals("b") )	wmode = "base64";

				tempname = new String( stringDecode(wname, wmode), wcset);
			}
		}

		if(tempctype.equals("text/html") && html_found == 0)
		{
			part.bodypart = body_body;
			part.name = "##body##";
			part.encode = tempencode;
			part.contentID = tempcid;
			part.charset = tempcharset;
			decodePart.addElement(part);
			html_found = 1;
		}

		if( tempname != "" )
		{
			part.bodypart = body_body;
			part.encode = tempencode;
			part.contentID = tempcid;
			tempname = replace(tempname, " ", "_");
			part.name = tempname;
			part.replacecid = URLEncoder.encode(tempname, "utf-8");
			decodePart.addElement(part);
		}
	}

	protected byte[] stringDecode(String Source, String DecodeType)
	{
		byte[] bytearr = null;
		try
		{
			InputStream dtext = MimeUtility.decode( new StringBufferInputStream(Source), DecodeType);

			int ContentSize= 0;
			while(-1 != dtext.read())
			{
				ContentSize++;
			}

			dtext.close();

			bytearr = new byte[ContentSize];

			dtext = MimeUtility.decode( new StringBufferInputStream(Source), DecodeType);

			dtext.read(bytearr);
			dtext.close();

		}
		catch(Exception ex)
		{

		}

		return bytearr;
	}

	public void htmlBodyDecode()
	{
		MimePart part;
		String convert ="";
		byte [] decodeByte = null;
		InputStream is;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int intRead = 0;

		for(int i = 0; i < decodePart.size(); i++)
		{
			part = (MimePart)decodePart.elementAt(i);
			if(part.name.equals("##body##"))
			{
				convert = part.bodypart;

				if(part.encode == "")
					htmldata = convert;
				else
				{
					try
					{
						is = new ByteArrayInputStream(part.bodypart.getBytes(part.charset));
						try
						{
								is = MimeUtility.decode(is, part.encode);

						}
						catch(MessagingException me)
						{
							System.out.println("ERROR : Decoding faild");
						}
						decodeByte = new byte[is.available() + 1];
						intRead = is.read(decodeByte);
						out.write(decodeByte, 0, intRead);
						htmldata = out.toString(part.charset);
					}
					catch(IOException ioe)
					{
						System.out.println("ERROR : input stream faild");
					}
				}
				htmldata = htmldata.intern();
				htmldata = htmlReplace(htmldata).toString();
			}
		}
	}

	protected void saveDecodeFile()
	{
		File outFile;
		OutputStream os;
		InputStream is;
		MimePart part;
		String fileName;
		byte [] fileContent;
		int i;
		String tmpCid;
		String fileInfo;

		for(i = 0; i < decodePart.size(); i++)
		{
			part = (MimePart)decodePart.elementAt(i);

			//if not HTML
			if(!part.name.equals("##body##"))
			{
					outFile = null;
					os = null;
					is = null;
					fileContent = null;

					try
					{
						is = new ByteArrayInputStream(part.bodypart.getBytes("iso-8859-1"));
						try
						{
							is = MimeUtility.decode(is, part.encode);
						}
						catch(MessagingException me)
						{
							System.out.println("ERROR : Decoding faild");
						}
						
						// 파일 업로드 처리
						FileData fileData = fileService.uploadFile(part.name, is, "1");
						uploadFiles.add(fileData);
						
						tmpCid = part.contentID;
						tmpCid = replaceHtml(tmpCid, "&lt", "cid:");
						tmpCid = tmpCid.substring(0, tmpCid.indexOf("&gt"));
						
						fileInfo = "http://" + serverDomain + ":" + serverPort + fileData.getDownloadLinkUrl() + "\" name=\"editorImage\" id=\""+ fileData.getFileId();
						htmldata = replaceHtml(htmldata, tmpCid, fileInfo);

						/*						
						fileContent = new byte [is.available() + 1];
						is.read(fileContent);

						fileName = part.name;
						System.out.println("saved file = " + uploadpath + part.name);
						outFile = new File(uploadpath + part.name);
						os = new FileOutputStream(outFile);
						os.write(fileContent);
						os.close();						
						*/
					}
					catch(IOException ioe)
					{
						System.out.println("ERROR : file write faild");
					}
			}
		}
	}

	protected boolean isMatchedCid(String compare)
	{
		MimePart part;

		int i;

		for(i = 0; i < decodePart.size(); i++)
		{
			part = (MimePart)decodePart.elementAt(i);

			if(compare.equals("cid:" + part.contentID))
			{
				replaceCidName = part.replacecid;
				return true;
			}
		}
		return false;
	}

	protected StringBuffer htmlReplace(String str)
	{
		String compare = "cid:";
		StringBuffer temphtml = new StringBuffer();
		String tempcid;

		int i = 0;
		int icnt = 0;
		int start = 0;
		int dcode=0;

		while (true)
		{
			tempcid="";
			icnt = str.indexOf(compare, start);

			if(icnt == -1)
				break;
			dcode = str.charAt(icnt-1);
			temphtml.append(str.substring(start, icnt));

			start = icnt;
			for(i=icnt; i<str.length(); i++)
			{
				if(dcode==39 || dcode==34)
				{
					if(str.charAt(i) == dcode)
						break;
				}
				else
				{
					if(str.charAt(i) == 32)
						break;
				}
				if(str.charAt(i) != 10 && str.charAt(i) != 13)
				{
					tempcid= tempcid + str.charAt(i);
				}
			}

			tempcid = tempcid.trim();
			if(isMatchedCid(tempcid))
			{
				temphtml.append(uploadurl + replaceCidName);
				start = start + tempcid.length();
			}
			else
			{
				temphtml.append(tempcid);
				start = start + tempcid.length();
			}
		}
		temphtml.append(str.substring(start));
		return temphtml;
	}

	protected String replace(String strsrc, String strfrom ,String strto)
	{
		int s = 0;
		int e = 0;

		if( strsrc.length() == 0 )
			return "";

		StringBuffer result = new StringBuffer();

		while( (e = strsrc.indexOf(strfrom, s)) >= 0 )
		{
			result.append(strsrc.substring(s, e));
			result.append(strto);
			s = e+strto.length();
		}
		result.append(strsrc.substring(s));
		return result.toString();
	}

	public String DecodeFileName()
	{

		MimePart part;
		String fileName="";
		int i;

		for(i = 0; i < decodePart.size(); i++)
		{
			part = (MimePart)decodePart.elementAt(i);

			if(!part.name.equals("##body##"))
			{
				if( fileName == "" )
					fileName = part.name;
				else
					fileName = fileName + "|" + part.name;
			}
		}
		return fileName;
	}

	public String DecodedCharSet()
	{

		MimePart part;
		String charsetName="";
		int i;

		for(i = 0; i < decodePart.size(); i++)
		{
			part = (MimePart)decodePart.elementAt(i);
			if(part.name.equals("##body##"))	charsetName = part.charset;
		}
		return charsetName;
	}
	
	
	/**
	 * str에 지정된 문자열에서 find에 해당하는 모든 문자열을 찾아 replace 문자열로 대체한다.
	 * 
	 * @param str 대상 문자열
	 * @param find 찾을 문자열
	 * @param replace 대체 문자열
	 * @return String 변환된 문자열
	 */
	public static String replaceHtml(String str, String find, String replace) {
		int findLength = find.length();
		int replaceLength = replace.length();
		int start = 0;
		int strLength = 0;
		int index = 0;
		StringBuffer sb = new StringBuffer();

		do {
			strLength = str.length();
			index = str.indexOf(find, start);

			if (index == -1)
				break;

			sb.setLength(0);
			sb.append(str.substring(0, index));
			sb.append(replace);
			sb.append(str.substring(index + findLength, strLength));
			str = sb.toString();

			start = index + replaceLength;
		} while (true);

		return str;
	}
	
	
}
