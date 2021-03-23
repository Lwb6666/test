package com.dxjr.portal.tzjinterface.util;

/*字符串 DESede(3DES) ECB加密*/
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.dxjr.portal.statics.ApplicationConstants;

public class DES3 {

	private static final String Algorithm = "DESede"; // 定义 加密算法,可用

	public static String encryptModeTzj(String src,String timestamp) {
		try {
			String key=ApplicationConstants.TZJ_KEY+timestamp;
			String keyStr=SHA1(key).substring(0,24);
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keyStr.getBytes(), Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			// 开始加密运算
			byte[] encryptedByteArray = c1.doFinal(src.getBytes());
			// 加密运算之后 将byte[]转化为base64的String
			BASE64Encoder enc = new BASE64Encoder();
			return enc.encode(encryptedByteArray);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区
	public static String decryptModeTzj(String src,String timestamp) {
		try {
			String key=ApplicationConstants.TZJ_KEY+timestamp;
			String keyStr=SHA1(key).substring(0,24);
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keyStr.getBytes(), Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			// 解密运算之前
			BASE64Decoder dec = new BASE64Decoder();
			byte[] encryptedByteArray = dec.decodeBuffer(src);
			// 解密运算 将base64的String转化为byte[]
			return new String(c1.doFinal(encryptedByteArray));
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// DES,DESede,Blowfish

	// keybyte为加密密钥，长度为24字节
	// src为被加密的数据缓冲区（源）
	public static String encryptMode(String src, String appKey) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(appKey.getBytes(), Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			// 开始加密运算
			byte[] encryptedByteArray = c1.doFinal(src.getBytes());
			// 加密运算之后 将byte[]转化为base64的String
			BASE64Encoder enc = new BASE64Encoder();
			return enc.encode(encryptedByteArray);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区
	public static String decryptMode(String src, String appKey) throws Exception {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(appKey.getBytes(), Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			// 解密运算之前
			BASE64Decoder dec = new BASE64Decoder();
			byte[] encryptedByteArray = dec.decodeBuffer(src);
			// 解密运算 将base64的String转化为byte[]
			return new String(c1.doFinal(encryptedByteArray), "utf-8");
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			throw e1;
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// 转换成十六进制字符串
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	public static void main(String[] args) throws Exception {

		//System.out.println(DES3.encryptModeTzj("tzj","1447895234"));
		System.out.println(DES3.decryptModeTzj("CJxQzd00JMg=","1448609065"));
		
		

	}
	
	/*
     * 根据字符串生成密钥字节数组 
     * @param keyStr 密钥字符串
     * @author WangQianJin 
     * @throws UnsupportedEncodingException
     */
    public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException{
        byte[] key = new byte[24];    //声明一个24位的字节数组，默认里面都是0
        byte[] temp = keyStr.getBytes("UTF-8");    //将字符串转成字节数组
        
        /*
         * 执行数组拷贝
         * System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
         */
        if(key.length > temp.length){
            //如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, temp.length);
        }else{
           //如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }
    
    /**
     * 使用SHA1进行加密
     * @author WangQianJin
     * @title @param decript
     * @title @return
     * @date 2015年11月19日
     */
    public static String SHA1(String decript) {
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

}
