package com.lgcns.ikep4.util.htmlcoverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.lgcns.ikep4.util.PropertyLoader;

public class HtmlConverter {

	private String htmlConverter = null; 
	
	public HtmlConverter(String htmlConverter) throws Exception {
		this.htmlConverter = htmlConverter;
		File f = new File(this.htmlConverter);
		if (!f.exists()) throw new Exception("file not found (" + this.htmlConverter + ")");
	}
	
	public String fileToHtml(String templatePath, String modulePath, String inputFile, String outputPath) {
		
		String resultUrl = null;
		String[] wholeFileName = null;
		String fileName = null;
		
		try {
			
			//input파일의 경로로부터 파일이름을 구한다.
			wholeFileName = inputFile.split("\\\\");
			fileName = wholeFileName[wholeFileName.length-1];
			Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
			
			String cmd = String.format("%s -t %s -mod_path %s %s %s", this.htmlConverter, templatePath, modulePath, inputFile, outputPath);
			Process proc = Runtime.getRuntime().exec(cmd);
			InputStream in = proc.getInputStream();
			InputStream err = proc.getErrorStream();
			printInputStream( in );
			printInputStream(err);
			int exitValue = proc.waitFor();
			System.out.println("return: " + exitValue);
			
			//client로 보낼 url주소
			resultUrl = prop.getProperty("ikep4.support.synap.resultUrl")+fileName+".htm";
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return resultUrl;
	}
	
	public void printInputStream(InputStream in ) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader( in ));
		StringBuffer buffer = new StringBuffer();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line + "n");
		}
		System.out.println(buffer);
	}
	
//	public static void usage() {
//		String usageStr[] = {
//		"usage) java HtmlConvSample {sn3hcv.exe or sn3hcv_path} {input-file}, {output-path }", ""
//		};
//		for (String s: usageStr) {
//		System.out.println(s);
//		}
//	}
	
//	public static void main(String args[]) {
//		/*
//		if (args.length != 3) {
//			usage();
//			System.exit(1);
//		}
//		*/
//		String converter = "C:\\ikep4j-project\\tools\\synap-converter\\sn3hcv.exe";
//		String templatePath = "C:\\ikep4j-project\\tools\\synap-converter\\template";
//		String modulePath = "C:\\ikep4j-project\\tools\\synap-converter";
//		String inputFile =  "\"C:\\ikep4j-project\\tools\\synap-converter\\sample\\123 123 123.xlsx\"";
//		String outputPath = "C:\\ikep4j-project\\tools\\synap-converter\\sample\\";
//		
//		
//		HtmlConverter hmlConvertor = null;
//		
//		try {
//			hmlConvertor = new HtmlConverter(converter);
//			hmlConvertor.fileToHtml(templatePath, modulePath, inputFile, outputPath);
//		} catch (Exception e) {
//		e.printStackTrace();
//		}
//	}
}