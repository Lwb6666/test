package com.dxjr.portal.tzjinterface.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.dxjr.portal.tzjinterface.entity.Message;

public class SHA1 {
	
	public static Logger logger = Logger.getLogger(SHA1.class); 
	
	public static String getSHA1(String token, Message msg) throws CryptException {
		logger.debug("getSHA1====================token("+token+")");
		logger.debug("getSHA1====================getTimestamp("+String.valueOf(msg.getTimestamp())+")");
		logger.debug("getSHA1====================getNonce("+msg.getNonce()+")");
		logger.debug("getSHA1====================getData("+msg.getData()+")");
		try {
			String[] array = new String[] { token, String.valueOf(msg.getTimestamp()), msg.getNonce(), msg.getData() };
			StringBuffer sb = new StringBuffer();
			// 字符串排序
			Arrays.sort(array);
			for (int i = 0; i < 4; i++) {
				sb.append(array[i]);
			}
			String str = sb.toString();
			System.out.println("getSHA1====================beforeStr("+str+")");
			logger.debug("getSHA1====================beforeStr("+str+")");
			str=str.replaceAll("\r", "");
			str=str.replaceAll("\n", "");
			System.out.println("getSHA1====================afterStr("+str+")");
			logger.debug("getSHA1====================afterStr("+str+")");
			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();

			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CryptException(CryptException.ComputeSignatureError);
		}
	}
	
	
	/**
     * 使用SHA1进行加密
     * @author WangQianJin
     * @title @param decript
     * @title @return
     * @date 2015年11月19日
     */
    public static String getSHA1byStr(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");                    
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    /** 
     * 返回16进制sha-1加密后信息
     * @param btInput 
     * @return 
     */  
    public static String sha1Hex(byte[] btInput){  
        final char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a', 'b', 'c', 'd', 'e', 'f'};  
        try {  
            MessageDigest mdInst = MessageDigest.getInstance("SHA-1", "SUN");  
            mdInst.update(btInput);  
            byte[] md = mdInst.digest();  
            // 把密文转换成十六进制的字符串形式  
               int j = md.length;  
               char str[] = new char[j * 2];  
                 
               for (int i=0,k=0; i < j; i++) {  
                   byte byte0 = md[i];  
                   str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
                   str[k++] = hexDigits[byte0 & 0xf];  
               }  
                 
               return new String(str);              
        } catch (Exception e) {  
            e.printStackTrace();  
            return "";  
        }  
    }  

}
