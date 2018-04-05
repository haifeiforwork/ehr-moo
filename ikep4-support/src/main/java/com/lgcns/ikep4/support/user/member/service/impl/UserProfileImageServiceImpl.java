package com.lgcns.ikep4.support.user.member.service.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.timezone.service.TimeZoneSupportService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserProfileImageService;

import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.model.UserDetail;

@Service("userProfileImageService")
@Transactional
public class UserProfileImageServiceImpl extends GenericServiceImpl<UserDetail, String> implements UserProfileImageService{

	@Autowired
	private UserDao userDao;
	
	public void userProfileImagesSync(List<UserDetail> userList)
	{	
		List<User> userProfile = downloadUserProfileImages(userList);
		
        for (User user : userProfile) {
			
			try{
			   userDao.updateProfilePicture(user);
			}
			catch(Exception e)
			{
				continue;
			}
	    }		
	}
	
	
	/**
	 * 사용자 정보를 받아 SAP 이미지 패스에서 이미지를 다운 받아 저장한다 
	 * @param request
	 * @return
	 */
    public List<User> downloadUserProfileImages(List<UserDetail> userList)  {

    	Properties prop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
		
		String strImageRoot = prop.getProperty("ikep4.support.fileupload.upload_root_profile_image");
		String strImageFolder = prop.getProperty("ikep4.support.fileupload.upload_folder_profile_image");		
		
		//SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMMdd", Locale.KOREA );  

		//Date currentTime = new Date();  
		
        //String strImageName = mSimpleDateFormat.format ( currentTime ) + ".jpg"; 
		String strImageName = "userProfileImage.jpg"; 
        
        StringBuffer strImagePath = new StringBuffer();
        
        List<User> userImageList = new ArrayList<User>();

    	for (UserDetail userItem : userList) 
    	{
    		if("C".equals(userItem.getUserStatus()))
    		{
    			try
    			{
    				//이미지를 읽어온다.
    				URL url = new URL(userItem.getProfilePicturePath());
   					InputStream in = new BufferedInputStream(url.openStream());
    				ByteArrayOutputStream out = new ByteArrayOutputStream();
    				byte[] buf = new byte[1024];
    				int n = 0;
    				while (-1!=(n=in.read(buf)))
    				{
    					out.write(buf, 0, n);
    				}
    				out.close();
    				in.close();
    				byte[] response = out.toByteArray();     				
    				
    				//경로를 설정한다
    				strImagePath.setLength(0); 
    				strImagePath.append(strImageRoot).append(strImageFolder);
    				
    				String strImageSubFolder = getProfileImagesubfolder(strImagePath.toString(), userItem.getUserId());
    				
    				strImagePath.append(strImageSubFolder).append(strImageName);
    				
    				FileOutputStream fos = new FileOutputStream(strImagePath.toString());
    				fos.write(response);
    				fos.close();
    				
    				//저장이 완료되면 리턴 값을 설정한다
    				User user = new User();    				
    				user.setUserId(userItem.getUserId());
    				user.setProfilePicturePath(strImageFolder + strImageSubFolder + strImageName);    				
    				userImageList.add(user);
    	
    			} catch (IOException e) {
    				continue;
    			}   
    		}
    	}
    	
		return userImageList;
    }
    
    private String getProfileImagesubfolder(String root, String UserId)
    {
    	String imagePath = "";
    	boolean result = false;
    	try
		{
    	   char[] userIds = UserId.toCharArray();
		
		   for(int i= 0; i < userIds.length; i++)
		   {
			   imagePath += userIds[i] + "/";
		   }
		   
		   File file = new File(root + imagePath);
		   if(!file.exists())
		   {
			   file.mkdirs();
		   }

		} catch (Exception e) {
			imagePath = "";
		}  
		
		return imagePath;
    }
	
}
