package com.zirrom.shiro;


import java.security.Key;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Assert;
import org.junit.Test;


public class CryptographyUtil {
	
	private static final String SALT = "ziroom";
	
	//base64加密
	public static String base64Encode(String str){
		return Base64.encodeToString(str.getBytes());
	}
	
	//base64解密
	public static String base64Decode(String str){
		return Base64.decodeToString(str);
	}
	
	//16进制加密
	public static String hexEncode(String str){
		return Hex.encodeToString(str.getBytes());
	}
	
	//16进制解密
	public static String hexDecode(String str){
		return new String(Hex.decode(str));
	}
	
	//md5加密[不可逆]
	public static String md5Encode(String str){
		return new Md5Hash(str, SALT).toString();
	}
	
	//ase加解密[对称]
	public static void ascEncodeAndDecode(String str){
		AesCipherService aesCipherService = new AesCipherService();  
		aesCipherService.setKeySize(128); //设置key长度  
		//生成key  
		Key key = aesCipherService.generateNewKey();  
		String text = "hello";  
		//加密  
		String encrptText =   
		aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();  
		//解密  
		String text2 =  
		 new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());  
		  
		Assert.assertEquals(text, text2);   
	}
	
	
	@Test
	public void testCase(){
		//base64
		System.out.println(CryptographyUtil.base64Decode(CryptographyUtil.base64Encode("dfx")));
		//16
		System.out.println(CryptographyUtil.hexDecode(CryptographyUtil.hexEncode("java")));
		//md5
		System.out.println(CryptographyUtil.md5Encode("ziroom"));
	}
}
