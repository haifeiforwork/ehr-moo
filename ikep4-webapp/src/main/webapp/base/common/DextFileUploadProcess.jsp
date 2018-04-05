<%@page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="java.io.File"%>
<%@page import="java.util.UUID"%>
<%@page import="devpia.dextupload.*"%>
<%@page import="java.util.Properties"%>
<%@page import="com.lgcns.ikep4.util.*"%>
<%@page import="com.lgcns.ikep4.support.fileupload.*"%>
<%!
	public String getFilePath(String path) {

		String date = com.lgcns.ikep4.util.lang.DateUtil.getToday("yyyy-MM-dd");
		String yyyy = date.substring(0, 4);
		String mm = date.substring(5, 7);

		return path + yyyy + "/" + mm + "/" + date;
	}
%>
<%


	FileUpload fileUpload = new FileUpload(request, response, "UTF-8");
	
	String ResponseData ="";

	try {
		
			String strPath = request.getRealPath("/");
			
			// 정품 혹은 평가판의 만료일을 판단하기 위한 라이센스 파일의 위치를 지정합니다.
			fileUpload.setLicenseFilePath(strPath + File.separator + "dextuploadj.config");

			//다국어 처리
			fileUpload.setCharacterEncoding("UTF-8");
			
			fileUpload.UploadStart(strPath);
		
			// getFileItemValues(\"DEXTUploadX\") 를 사용해서 중복된 모든 파일 아이템을 가져온다.
			// DEXTUploadX를 연동할 경우 파일 아이템은 모두 "DEXTUploadX" 이름으로 전송된다. 
			FileItem[] value = fileUpload.getFileItemValues("DEXTUploadX");
			String[] UploadedItem = fileUpload.getParameterValues("DEXTUploadX_Uploaded");  
			if(null != UploadedItem)
			{ 
				for(int i=0; i<UploadedItem.length; i++)
				{
					if(ResponseData.equals("")){
						ResponseData = UploadedItem[i]+"|"+UploadedItem[i]+"|"+UploadedItem[i]+"|"+UploadedItem[i]+"|"+UploadedItem[i]+"|"+"use";
					}else{
						ResponseData = ResponseData+"|"+UploadedItem[i]+"|"+UploadedItem[i]+"|"+UploadedItem[i]+"|"+UploadedItem[i]+"|"+UploadedItem[i]+"|"+"use";;
					}
					//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$use:"+ResponseData); 
				}
			} 
			String[] DeleteItem = fileUpload.getParameterValues("DEXTUploadX_Deleted_Uploaded");	
			if(null != DeleteItem)
			{ 
				for(int i=0; i<DeleteItem.length; i++)
				{
					
					if(ResponseData.equals("")){
						ResponseData = DeleteItem[i]+"|"+DeleteItem[i]+"|"+DeleteItem[i]+"|"+DeleteItem[i]+"|"+DeleteItem[i]+"|"+"del";
					}else{
						ResponseData = ResponseData+"|"+DeleteItem[i]+"|"+DeleteItem[i]+"|"+DeleteItem[i]+"|"+DeleteItem[i]+"|"+DeleteItem[i]+"|"+"del";;
					}
					//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$del:"+ResponseData); 
				}

			}
			Properties prop = PropertyLoader
			.loadProperties("/configuration/fileupload-conf.properties");

			String uploadRoot = "";
			String uploadFolder = "";
			String uploadRootForFile = prop
					.getProperty("ikep4.support.fileupload.upload_root");
			String uploadRootForImage = prop
					.getProperty("ikep4.support.fileupload.upload_root_image");
			String uploadFolderForFile = getFilePath(prop
					.getProperty("ikep4.support.fileupload.upload_folder"));
			String uploadFolderForImage = getFilePath(prop
					.getProperty("ikep4.support.fileupload.upload_folder_image"));
			String downloadUrl = prop
					.getProperty("ikep4.support.fileupload.downloadurl");
			String keywordList = prop
			.getProperty("ikep4.support.fileupload.image_file");
			
			if(null != value){
				for (int i = 0; i < value.length; i++) 
				{
					if(value[i] != null)
					{
						if(value[i].IsUploaded()){
							
							String fileStr = value[i].getFileName();
							String fileExt = com.lgcns.ikep4.util.lang.StringUtil.getFileExtension(fileStr);//fileStr.substring(fileStr.lastIndexOf(".")+1,fileStr.length());
							if(keywordList.indexOf(fileExt)>0){//이미지 파일.
								uploadRoot = uploadRootForImage;
								uploadFolder = uploadFolderForImage;
							} else {
								uploadRoot = uploadRootForFile;
								uploadFolder = uploadFolderForFile;
							}
							
							File folder = new File(uploadRoot + uploadFolder);
							if (!folder.exists()) {
								folder.mkdirs();
							}
	
	
							File saveFile = new File(folder, UUID.randomUUID()
									.toString().replace("-", "")
									+ "." + fileExt);
							
							value[i].SaveAs(saveFile.getAbsolutePath(), true);
							
							
				
							// Save() 메소드 등에 의해서 마지막으로 저장된 파일의 경로를 구할 수 있다. 
							if(ResponseData.equals("")){
								ResponseData = uploadFolder+"|"+value[i].getLastSavedFileName()+"|"+fileStr+"|"+value[i].length()+"|"+fileExt+"|"+"add";
							}else{
								ResponseData = ResponseData+"|"+uploadFolder+"|"+value[i].getLastSavedFileName()+"|"+fileStr+"|"+value[i].length()+"|"+fileExt+"|"+"add";
							}
							
	
						}else{
							// 파일이 업로드 되지 않았을 때의 처리를 한다. 				
						}
					}
					//System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$add:"+ResponseData); 
				}
			}
			out.print(ResponseData);
	}
	catch (DEXTUploadException ex) {
		// 예외에 대한 처리를 수행한다. 
		System.out.println("DEXTUploadException : "+ex.getMessage());
	} 
	catch (Exception ex) {
		// 예외에 대한 처리를 수행한다. 
		System.out.println("Exception :"+ex.getMessage());
	} 
	finally {
		// 종료 시에 반드시 자원을 해제해야 한다.
		// 그렇지 않으면 임시 파일이 삭제되지 않고 남을 수 있다.
		fileUpload.dispose();
	}
%>
