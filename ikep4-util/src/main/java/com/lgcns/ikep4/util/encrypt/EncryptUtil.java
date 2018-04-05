package com.lgcns.ikep4.util.encrypt;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import sun.misc.BASE64Encoder;


/**
 * 암호화, 복호화 처리 클래스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: EncryptUtil.java 16247 2011-08-18 04:54:29Z giljae $
 */
@SuppressWarnings("unchecked")
public final class EncryptUtil {

	public static final String ENCRYPT_ALGORITHM = "AES";

	public static final String ENCRYPT_KEY = "ThisIsIkepSecurityKey";
	
	
	public static final String MOORIM_3DES_SECRET_KEY = "anflapaper1114moorim2011";
	
	private EncryptUtil() {
		throw new AssertionError();
	}

	/**
	 * 대칭키 암호화
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String encryptText(String text) {
		String encrypted;

		try {
			SecretKeySpec ks = new SecretKeySpec(generateKey(ENCRYPT_KEY), ENCRYPT_ALGORITHM);
			Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, ks);
			byte[] encryptedBytes = cipher.doFinal(text.getBytes());
			encrypted = new String(Base64Coder.encode(encryptedBytes));
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return encrypted;
	}

	/**
	 * 대칭키 복호화
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String decryptText(String text) {
		String decrypted;

		try {
			SecretKeySpec ks = new SecretKeySpec(generateKey(ENCRYPT_KEY), ENCRYPT_ALGORITHM);
			Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, ks);
			byte[] decryptedBytes = cipher.doFinal(Base64Coder.decode(text));
			decrypted = new String(decryptedBytes);
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return decrypted;
	}

	/**
	 * 대칭키 생성
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static byte[] generateKey(String key) {
		byte[] desKey = new byte[16];
		byte[] bkey = key.getBytes();

		if (bkey.length < desKey.length) {
			System.arraycopy(bkey, 0, desKey, 0, bkey.length);

			for (int i = bkey.length; i < desKey.length; i++) {
				desKey[i] = 0;
			}
		} else {
			System.arraycopy(bkey, 0, desKey, 0, desKey.length);
		}

		return desKey;
	}

	/**
	 * 비대칭키 SHA 암호화 (복호화 되지 않음)
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String encryptSha(String text) {
		String encrypted;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(text.getBytes("UTF-8"));

			byte[] digested = md.digest();
			encrypted = new String(Base64Coder.encode(digested));
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return encrypted;
	}

	/**
	 * 비대칭키 MD5 암호화 (복호화 되지 않음)
	 * 
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String encryptMd5(String text) {
		String encrypted;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes("UTF-8"));

			byte[] digested = md.digest();
			encrypted = new String(Base64Coder.encode(digested));
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return encrypted;
	}

	
	

	public static String encryptTripleDES(String cleartext) {
		
		SecretKey desedeKey = null;
		IvParameterSpec ivSpec = null;
		String secretKey =MOORIM_3DES_SECRET_KEY;
		
		try {
			byte[] keyBytes = secretKey.getBytes("ISO-8859-1");
			DESedeKeySpec desEdeSpec = new DESedeKeySpec(keyBytes);
			SecretKeyFactory desEdeFactory = SecretKeyFactory.getInstance("DESede", "SunJCE");
			desedeKey = desEdeFactory.generateSecret(desEdeSpec);
			// init IV
			byte[] specByte = new byte[8];
			for ( int i=0; i< keyBytes.length && i<specByte.length; i++ ) {
				specByte[i] = keyBytes [i];
			}
			ivSpec = new IvParameterSpec(specByte);
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
		
		if ( desedeKey==null ) {
			return null;
		}
		
		String cipherString = null;
		
		byte[] ciphertext = new byte[0];
		
		String returnStr ="";
		
		try {
			// For 3DES
			// TRANSFORMATION may be
			// DESede/ECB/PKCS5Padding or DESede/CBC/PKCS5Padding
			Cipher desCipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			// Initialize the cipher for decryption with key
			desCipher.init(Cipher.ENCRYPT_MODE, desedeKey);
			// If Initialization Vector is needed…
			// desCipher.init(Cipher.ENCRYPT_MODE, desedeKey, ivSpec);
			// Encrypt cleartext
			ciphertext = desCipher.doFinal(cleartext.getBytes("ISO-8859-1"));

			cipherString = new sun.misc.BASE64Encoder().encode(ciphertext);
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^2:"+cipherString);
			int i=0;

			while(i<cipherString.length()){
				if(i!=0){
				returnStr = returnStr+"-";
				}
				returnStr= returnStr+URLEncoder.encode(cipherString.substring(i, i+4), "ISO-8859-1");
				i=i+4;
			}
			
			cipherString = URLEncoder.encode(cipherString, "ISO-8859-1");
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^3:"+cipherString);
			//System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^4:"+returnStr);
		} catch ( NoSuchPaddingException ex ) {
			ex.printStackTrace();
		} catch ( NoSuchAlgorithmException ex ) {
			ex.printStackTrace();
		} catch ( InvalidKeyException ex ) {
			ex.printStackTrace();
		} catch ( IllegalBlockSizeException ex ) {
			ex.printStackTrace();
		} catch ( BadPaddingException ex ) {
			ex.printStackTrace();
		} catch ( UnsupportedEncodingException ex ) {
			ex.printStackTrace();
		}
		return returnStr;
	}
}
