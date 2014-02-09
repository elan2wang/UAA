package org.uaa.common;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class Crypto {
	
	private static final String password = "jsiuewiu6%&*&8fjkaklsalsd38%^fha";
	
	private static SecretKeySpec key = null;
	
	private static void generateKey()
	{
		try
		{
			KeyGenerator kgen = KeyGenerator.getInstance("AES");  
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  		       
            secureRandom.setSeed(password.getBytes());  
            kgen.init(128, secureRandom);
	        SecretKey secretKey = kgen.generateKey();  
	        byte[] enCodeFormat = secretKey.getEncoded();  
	        key = new SecretKeySpec(enCodeFormat, "AES"); 
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

    public static String encrypt(String content) {  
        try 
        { 
        	if (key == null)
        	{
        		generateKey();
        	}
           
        	Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("UTF-8");  
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(byteContent);  
            return parseByte2HexStr(result); 
                
        } catch (Exception e) {  
        	e.printStackTrace();
        }  
        return null;  
} 
    
    public static String decrypt(String content) {  
        try 
        {  
        	if (key == null)
        	{
        		generateKey();
        	}            
            
        	Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key); 
            byte[] result = cipher.doFinal(parseHexStr2Byte(content));  
            return new String(result); 
            
        } catch (Exception e) {
        	e.printStackTrace();
        } 
        return null;  
    }  
    
    private static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) {  
                        hex = '0' + hex;  
                }  
                sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
    }
    
    private static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1)  
                return null;  
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {  
                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
    } 
    
    public final static String MD5Encrypt(String s) { 
		char hexDigits[] = { '0', '1', '2', '3', '4', 
		                                 '5', '6', '7', '8', '9', 
		                                'A', 'B', 'C', 'D', 'E', 'F' }; 
		try 
		{ 
		    byte[] btInput = s.getBytes(); 
		    MessageDigest mdInst = MessageDigest.getInstance("MD5"); 
		    mdInst.update(password.getBytes());
		    mdInst.update(btInput); 
		    byte[] md = mdInst.digest(); 
		    int len = md.length; 
		    char str[] = new char[len * 2]; 
		    int j = 0;
		    for (int i = 0; i < len; i++) { 
		    	byte byte0 = md[i]; 
		    	str[j++] = hexDigits[byte0 >>> 4 & 0xf]; 
		    	str[j++] = hexDigits[byte0 & 0xf]; 
		    	} 
		    return new String(str); 
		} 
		catch (Exception e) { 
			e.printStackTrace();
			return null;
		} 
	}
}
