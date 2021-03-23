package com.dxjr.portal.tzjinterface.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import com.dxjr.portal.tzjinterface.entity.Message;

/**
 * 
 * @author WQJ
 * SHA1加密类
 */
public class EncryptSHA1 {
	
	
	private static EncryptSHA1 instance;
	
    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    
    /**
     * 构造函数
     * @author WangQianJin
     * @date 2015年12月23日
     */
    private EncryptSHA1(){
            
    }
    
    public static EncryptSHA1 getInstance(){
            if(null == instance)
                    return new EncryptSHA1();
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
		
		/*构造MD5加密*/
		MessageDigest alg;
        String enStr = "";
        try {
            // MD5 is 32 bit message digest
            alg = MessageDigest.getInstance("SHA-1");
//            Base64.decodeBase64(alg.digest(inputStr.getBytes()));
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
                    alg = MessageDigest.getInstance("SHA-1");
                    enStr = byteArrayToHexString(alg.digest(src.getBytes("utf-8")));
            } catch (Exception e) {
                    return null;
            } 
            return enStr;
    }
    
    public static String MD5(String txt) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] btInput = txt.getBytes("utf-8");
            MessageDigest mdInst = MessageDigest.getInstance("SHA-1");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static byte[] getFeatureSHAbyte(String key) throws Exception { 
        //hashType = "SHA1";  
        //hashType = "SHA-256";  
        //hashType = "SHA-384";  
        //hashType = "SHA-512";  
          
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");  
        messageDigest.update(key.getBytes());  
        byte[] B = key.getBytes();  
         System.out.print("原二进制数:");  
         for(int i : B){  
             System.out.print(i+",");  
          }   
          
         System.out.println(Base64.decodeBase64(messageDigest.digest()));  
        return B;  
    }
    
    public static String sha1(String text) {
    	MessageDigest md = null;
    	String outStr = null;
    	try {
    	md = MessageDigest.getInstance("SHA-1");
    	byte[] digest = md.digest(text.getBytes());
    	outStr = byteToString(digest);
    	} catch (NoSuchAlgorithmException e) {
    	throw new RuntimeException(e);
    	}
    	return outStr;
    }
    
    private static String byteToString(byte[] digest) {
    	StringBuilder buf = new StringBuilder();
    	for (int i = 0; i < digest.length; i++) {
    	String tempStr = Integer.toHexString(digest[i] & 0xff);
    	if (tempStr.length() == 1) {
    	buf.append("0").append(tempStr);
    	} else {
    	buf.append(tempStr);
    	}
    	}
    	return buf.toString().toLowerCase();
    }
    
    public static void main(String[] args) {        
		try {			
			Message msg = new Message();
//			String data = "mj2w8e++VINuKqcXnnafVXSKlurXza1GlkhuqqJNAaHCTztlyS0yO5/cfaIJy1hmyH1WbZSPZ0rS\r\n"+
//"Wc4HrA8s/9RudhR16AMlkZhcSdNbUFqpBUmyQhoh+9H+yd8QLxK7c/GGWm/nhQk2GHqd5yRjytDC\r\n"+
//"uFj5L7MDvLY37w4He5d/V143mnPrZEpbR+6p7hZUz7WoTNhLEnMEyLlMz84s1Ll0iR8Xp0c2sSGc\r\n"+
//"mRFoLFSAAAm3puJUG/fqGo/84RsoTLeKYf12KnJimi1HVAT6d2qO4FBVr/rhJLN5JvG2aHKe+eZU\r\n"+
//"Ii+I8/FEn4kSenkxGs8QfkGkh+79ooPnx4fKMbGtcpe7SnfXo+okkxbFTxCFcdD7xih/leRXH/Vg\r\n"+
//"k5ZaM0oxdM9yoau5xhJ7eCY0kb9uQo5zTI4N8ROTfXDg1GOUoN8QPXoILAWoc5ziD1EP5OlG13ZR\r\n"+
//"snn6mflFWYDLwtA2Fyu/7d6k5ZxQe5Lzi9EIYl1Dn6hYJUuDWtRr668yi9jhkqqxAwmIj1Td/m1m\r\n"+
//"m1J8nNC+lF2z/2gGV95NV4yzW9TVi35IN96MQsouNeD/uaNrQlh7e47VjUfF09BM6wtgVA3MC6cw\r\n"+
//"w7udxgtf5JjbpLrs++Wqs01PqnLp/5jGPA78DVNCll0gxxsMJfONEGnt44jcwYka7qhJ4/dOVp4y\r\n"+
//"aw74Imn42KLSRkgjJ2xk259Ef5rG8cqXBSDaJQms4HQvS1g4EdyR0onczDGqHMrrt2FYtHSGrE07\r\n"+
//"QUSKe+uyTgAjqWF+An2T4UK4BSNQoOyAPJR23Gtl/VeVPhBdBh7BazYUZkqylyvmRGhe2vLcqY2y\r\n"+
//"eiCaPkk1aYLGX1HAOqyMRvJwk/HXFjr9LNj64fJETv3xKkBF/Mip2f7Q4WPSglfiVn5dynUAaF/T\r\n"+
//"VldjBHfErS6niSIcaDzbYnKI0ZP88nGChBZd49tRxfUcO8Jub8svJ24ED+Q11TQ1MCAEsQ==";	
			msg.setTimestamp(1460545399L);
			msg.setNonce("lDD5458v6F42Eafx");
			String data = "mj2w8e++VINuKqcXnnafVXSKlurXza1GlkhuqqJNAaHCTztlyS0yO5/cfaIJy1hmyH1WbZSPZ0rS\r\n"+
	"Wc4HrA8s/9RudhR16AMlkZhcSdNbUFqpBUmyQhoh+9H+yd8QLxK7c/GGWm/nhQk2GHqd5yRjytDC\r\n"+
	"uFj5L7MDvLY37w4He5d/V143mnPrZEpbR+6p7hZUz7WoTNhLEnMEyLlMz84s1Ll0iR8Xp0c2sSGc\r\n"+
	"mRFoLFSAAAm3puJUG/fqGo/84RsoTLeKYf12KnJimi1HVAT6d2qO4FBVr/rhJLN5JvG2aHKe+eZU\r\n"+
	"Ii+I8/FEn4kSenkxGs8QfkGkh+79ooPnx4fKMbGtcpe7SnfXo+okkxbFTxCFcdD7xih/leRXH/Vg\r\n"+
	"k5ZaM0oxdM9yoau5xhJ7eCY0kb9uQo5zTI4N8ROTfXDg1GOUoN8QPXoILAWoc5ziD1EP5OlG13ZR\r\n"+
	"snn6mflFWYDLwtA2Fyu/7d6k5ZxQe5Lzi9EIYl1Dn6hYJUuDWtRr668yi9jhkqqxAwmIj1Td/m1m\r\n"+
	"m1J8nNC+lF2z/2gGV95NV4yzW9TVi35IN96MQsouNeD/uaNrQlh7e47VjUfF09BM6wtgVA3MC6cw\r\n"+
	"w7udxgtf5JjbpLrs++Wqs01PqnLp/5jGPA78DVNCll0gxxsMJfONEGnt44jcwYka7qhJ4/dOVp4y\r\n"+
	"aw74Imn42KLSRkgjJ2xk259Ef5rG8cqXBSDaJQms4HQvS1g4EdyR0onczDGqHMrrt2FYtHSGrE07\r\n"+
	"QUSKe+uyTgAjqWF+An2T4UK4BSNQoOyAPJR23Gtl/VeVPhBdBh7BazYUZkqylyvmRGhe2vLcqY2y\r\n"+
	"eiCaPkk1aYLGX1HAOqyMRvJwk/HXFjr9LNj64fJETv3xKkBF/Mip2f7Q4WPSglfiVn5dynUAaF/T\r\n"+
	"VldjBHfErS6niSIcaDzbYnKI0ZP88nGChBZd49tRxfUcO8Jub8svJ24ED+Q11TQ1MCAEsQ==";
			msg.setData(data);
			String token="guochengjinrong";
			String sign=SHA1.getSHA1(token, msg);
			System.out.println(sign);
		} catch (Exception e) {			
			e.printStackTrace();
		}        
    }

}
