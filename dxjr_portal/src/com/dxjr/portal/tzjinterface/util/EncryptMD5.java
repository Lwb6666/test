package com.dxjr.portal.tzjinterface.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author 胡建盼
 * MD5加密类
 */
public class EncryptMD5 {
	
	
	private static EncryptMD5 instance;
	
    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    
    /**
     * 构造函数
     * @author WangQianJin
     * @date 2015年12月23日
     */
    private EncryptMD5(){
            
    }
    
    public static EncryptMD5 getInstance(){
            if(null == instance)
                    return new EncryptMD5();
            return instance;
    }
    
	
	/**
	 * 
	 * <p>
	 * Description:生成MD5值<br />
	 * </p>
	 * @author 胡建盼
	 * @version 0.1 2014年10月22日
	 * @param inputStr
	 * @return
	 * @throws NoSuchAlgorithmException
	 * String
	 */
	public static String toMD5(String inputStr) throws NoSuchAlgorithmException {
//	    String md5Str = inputStr;
//	    if(inputStr != null) {
//	        MessageDigest md = MessageDigest.getInstance("MD5");
//	        md.update(inputStr.getBytes());
//	        BigInteger hash = new BigInteger(1, md.digest());
//	        md5Str = hash.toString(16);
//	        if((md5Str.length() % 2) != 0) {
//	            md5Str = "0" + md5Str;
//	        }
//	    }
//	    return md5Str;
		
		/*构造MD5加密*/
		MessageDigest alg;
        String enStr = "";
        try {
            // MD5 is 32 bit message digest
            alg = MessageDigest.getInstance("MD5");
            enStr = byteArrayToHexString(alg.digest(inputStr.getBytes("utf-8")));
        } catch (Exception e) {
                return null;
        } 
        return enStr;
	}

    
    /**
     * 转换字节数组 ?6进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    private static String byteArrayToHexString(byte[] b) {
            StringBuffer resultSb = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                    resultSb.append(byteToHexString(b[i]));
            }
            return resultSb.toString();
    }

    /**
     * 转换字节数组为高位字符串
     * @param b 字节数组
     * @return
     */
    private static String byteToHexString(byte b) {
            int n = b;
            if (n < 0)
                    n = 256 + n;
            int d1 = n / 16;
            int d2 = n % 16;
            return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5 摘要计算(byte[]).
     * @param src byte[]
     * @throws Exception
     * @return String
     * @throws UnsupportedEncodingException 
     */
    public String md5Digest(String src) {
            MessageDigest alg;
            String enStr = "";
            try {
                    // MD5 is 32 bit message digest
                    alg = MessageDigest.getInstance("MD5");
                    enStr = byteArrayToHexString(alg.digest(src.getBytes("utf-8")));
            } catch (Exception e) {
                    return null;
            } 
            return enStr;
    }
    
    public static void main(String[] args) {
        String md5Digest;
		try {
			md5Digest = EncryptMD5.toMD5("cardNo=440126194104105720&email=mrhuang009@126.com&from=touzhijia&realName=郭凤娥&service=registerUser&telephone=13662371461&username=gfe002");
			System.out.println(md5Digest);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
    }

}
