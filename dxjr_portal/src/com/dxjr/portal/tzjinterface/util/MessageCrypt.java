package com.dxjr.portal.tzjinterface.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.dxjr.portal.tzjinterface.constant.TZJConstants;
import com.dxjr.portal.tzjinterface.entity.Message;
import com.dxjr.portal.tzjinterface.entity.ServiceData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Component
public class MessageCrypt {
	
	public static Logger logger = Logger.getLogger(MessageCrypt.class);
	
	private static Charset CHARSET = Charset.forName("utf-8");
	private static BASE64Encoder base64Encoder = new BASE64Encoder();
	private static BASE64Decoder base64Decoder = new BASE64Decoder();
	
	private static GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public Message encryptMsg(ServiceData sd) throws CryptException {
		String nonce = getRandomStr();
//		String nonce = "lDD5458v6F42Eafx";
		Message msg = new Message();
		msg.setTimestamp(System.currentTimeMillis() / 1000);
//		msg.setTimestamp(1460545399L);
		msg.setNonce(nonce);
		
//		Gson gson = new Gson();
		Gson gson = gsonBuilder.create();
		byte[] jsonBytes = gson.toJson(sd).getBytes(CHARSET);
		byte[] networkBytesOrder = getNetworkBytesOrder(jsonBytes.length);
		byte[] appIdBytes = TZJConstants.appId.getBytes(CHARSET);
		ByteGroup bg = new ByteGroup();
		bg.addBytes(nonce.getBytes(CHARSET));
		bg.addBytes(networkBytesOrder);
		bg.addBytes(jsonBytes);
		bg.addBytes(appIdBytes);
		byte[] padBytes = PKCS7Encoder.encode(bg.size());
		bg.addBytes(padBytes);
		// 获得最终的字节流, 未加密
		byte[] original = bg.toBytes();
		
		try {
			byte[] reqKey = getRequestKey(msg.getTimestamp());
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec key_spec = new SecretKeySpec(reqKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(reqKey, 0, 16));
			cipher.init(Cipher.ENCRYPT_MODE, key_spec, iv);
			byte[] encrypted = cipher.doFinal(original);
			String data = base64Encoder.encode(encrypted);
			//去掉换行
//			String data = "mj2w8e++VINuKqcXnnafVXSKlurXza1GlkhuqqJNAaHCTztlyS0yO5/cfaIJy1hmyH1WbZSPZ0rSWc4HrA8s/9RudhR16AMlkZhcSdNbUFqpBUmyQhoh+9H+yd8QLxK7c/GGWm/nhQk2GHqd5yRjytDCuFj5L7MDvLY37w4He5d/V143mnPrZEpbR+6p7hZUz7WoTNhLEnMEyLlMz84s1Ll0iR8Xp0c2sSGcmRFoLFSAAAm3puJUG/fqGo/84RsoTLeKYf12KnJimi1HVAT6d2qO4FBVr/rhJLN5JvG2aHKe+eZUIi+I8/FEn4kSenkxGs8QfkGkh+79ooPnx4fKMbGtcpe7SnfXo+okkxbFTxCFcdD7xih/leRXH/Vgk5ZaM0oxdM9yoau5xhJ7eCY0kb9uQo5zTI4N8ROTfXDg1GOUoN8QPXoILAWoc5ziD1EP5OlG13ZRsnn6mflFWYDLwtA2Fyu/7d6k5ZxQe5Lzi9EIYl1Dn6hYJUuDWtRr668yi9jhkqqxAwmIj1Td/m1mm1J8nNC+lF2z/2gGV95NV4yzW9TVi35IN96MQsouNeD/uaNrQlh7e47VjUfF09BM6wtgVA3MC6cww7udxgtf5JjbpLrs++Wqs01PqnLp/5jGPA78DVNCll0gxxsMJfONEGnt44jcwYka7qhJ4/dOVp4yaw74Imn42KLSRkgjJ2xk259Ef5rG8cqXBSDaJQms4HQvS1g4EdyR0onczDGqHMrrt2FYtHSGrE07QUSKe+uyTgAjqWF+An2T4UK4BSNQoOyAPJR23Gtl/VeVPhBdBh7BazYUZkqylyvmRGhe2vLcqY2yeiCaPkk1aYLGX1HAOqyMRvJwk/HXFjr9LNj64fJETv3xKkBF/Mip2f7Q4WPSglfiVn5dynUAaF/TVldjBHfErS6niSIcaDzbYnKI0ZP88nGChBZd49tRxfUcO8Jub8svJ24ED+Q11TQ1MCAEsQ==";
			//Windows下换行
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
			//Linux下换行
//			String data = "mj2w8e++VINuKqcXnnafVXSKlurXza1GlkhuqqJNAaHCTztlyS0yO5/cfaIJy1hmyH1WbZSPZ0rS\n"+
//"Wc4HrA8s/9RudhR16AMlkZhcSdNbUFqpBUmyQhoh+9H+yd8QLxK7c/GGWm/nhQk2GHqd5yRjytDC\n"+
//"uFj5L7MDvLY37w4He5d/V143mnPrZEpbR+6p7hZUz7WoTNhLEnMEyLlMz84s1Ll0iR8Xp0c2sSGc\n"+
//"mRFoLFSAAAm3puJUG/fqGo/84RsoTLeKYf12KnJimi1HVAT6d2qO4FBVr/rhJLN5JvG2aHKe+eZU\n"+
//"Ii+I8/FEn4kSenkxGs8QfkGkh+79ooPnx4fKMbGtcpe7SnfXo+okkxbFTxCFcdD7xih/leRXH/Vg\n"+
//"k5ZaM0oxdM9yoau5xhJ7eCY0kb9uQo5zTI4N8ROTfXDg1GOUoN8QPXoILAWoc5ziD1EP5OlG13ZR\n"+
//"snn6mflFWYDLwtA2Fyu/7d6k5ZxQe5Lzi9EIYl1Dn6hYJUuDWtRr668yi9jhkqqxAwmIj1Td/m1m\n"+
//"m1J8nNC+lF2z/2gGV95NV4yzW9TVi35IN96MQsouNeD/uaNrQlh7e47VjUfF09BM6wtgVA3MC6cw\n"+
//"w7udxgtf5JjbpLrs++Wqs01PqnLp/5jGPA78DVNCll0gxxsMJfONEGnt44jcwYka7qhJ4/dOVp4y\n"+
//"aw74Imn42KLSRkgjJ2xk259Ef5rG8cqXBSDaJQms4HQvS1g4EdyR0onczDGqHMrrt2FYtHSGrE07\n"+
//"QUSKe+uyTgAjqWF+An2T4UK4BSNQoOyAPJR23Gtl/VeVPhBdBh7BazYUZkqylyvmRGhe2vLcqY2y\n"+
//"eiCaPkk1aYLGX1HAOqyMRvJwk/HXFjr9LNj64fJETv3xKkBF/Mip2f7Q4WPSglfiVn5dynUAaF/T\n"+
//"VldjBHfErS6niSIcaDzbYnKI0ZP88nGChBZd49tRxfUcO8Jub8svJ24ED+Q11TQ1MCAEsQ==";
			msg.setData(data);
			String signature = SHA1.getSHA1(TZJConstants.token, msg);
			msg.setSignature(signature);
		} catch (Exception e) {
			throw new CryptException(CryptException.EncryptAESError);
		}
		
		return msg;
	}
	
	public ServiceData decryptMsg(Message msg) throws CryptException { 
		
		String signature = SHA1.getSHA1(TZJConstants.token, msg);
		logger.debug("decryptMsg====================signature("+signature+")");
		logger.debug("decryptMsg====================msg.getSignature()("+msg.getSignature()+")");
		if (!signature.equals(msg.getSignature())) {
			throw new CryptException(CryptException.ValidateSignatureError);
		}
		try {
			byte[] reqKey = getRequestKey(msg.getTimestamp());
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec key_spec = new SecretKeySpec(reqKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(reqKey, 0, 16));
			cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
			byte[] encrypted = base64Decoder.decodeBuffer(msg.getData());
			byte[] original = cipher.doFinal(encrypted);
			
			// 去除补位字符
			byte[] bytes = PKCS7Encoder.decode(original);
			// 分离16位随机字符串,网络字节序和AppId
			byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
			int dataLength = recoverNetworkBytesOrder(networkOrder);
			String json = new String(Arrays.copyOfRange(bytes, 20, 20 + dataLength), CHARSET);
			String appId = new String(Arrays.copyOfRange(bytes, 20 + dataLength, bytes.length),
					CHARSET);
			
			if (!appId.equals(TZJConstants.appId)) {
				throw new CryptException(CryptException.ValidateAppidError);
			}
			
			JsonParser parser = new JsonParser();
			JsonObject root = parser.parse(json).getAsJsonObject();
			String service = root.get("service").getAsString();
			return new ServiceData(service, root.get("body"));
		} catch(Exception e) {
			throw new CryptException(CryptException.DecryptAESError);
		}
	}
	
	private byte[] getRequestKey(long timestamp) throws CryptException {
		String a = String.format("%s%d", TZJConstants.aesKey, timestamp);
			MessageDigest md5;
			try {
				md5 = MessageDigest.getInstance("MD5");
				return md5.digest(a.getBytes(CHARSET));
			} catch (NoSuchAlgorithmException e) {
				throw new CryptException(CryptException.EncryptAESError);
			}
		
	}
	
	// 生成4个字节的网络字节序
	byte[] getNetworkBytesOrder(int sourceNumber) {
		byte[] orderBytes = new byte[4];
		orderBytes[3] = (byte) (sourceNumber & 0xFF);
		orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
		orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
		orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
		return orderBytes;
	}
	
	// 还原4个字节的网络字节序
	int recoverNetworkBytesOrder(byte[] orderBytes) {
		int sourceNumber = 0;
		for (int i = 0; i < 4; i++) {
			sourceNumber <<= 8;
			sourceNumber |= orderBytes[i] & 0xff;
		}
		return sourceNumber;
	}

	// 随机生成16位字符串
	String getRandomStr() {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	//测试
	public static void main(String[] args) {
		Message msg=new Message();
		msg.setData("wUQ7sbEtd//nEjK5vS0W2hxc58xiv7plHg6YsAkcWjhdr4HNLrL3Of+RM0r7Ddt0YYWJ2V+nTcDEAK7yKcMjFJMKzQynH0Gcdzh/Sqvt/YuFi0g9MPHBqybiaoXL8m6CbsoNyCeMo8qM0FoosrhRr8fcaLrnB+qjxTzZPVKUBsVP78iciMnQkR7c3W/QB23YYDmK/nvA7VeAGYq7gUqHLPA/gS2VG3uQZnmJx9SqCSL3VUCooYqIfW5IwyWMhWcc");
		msg.setNonce("R12sJMXMyWyppxwx");
		msg.setSignature("1dd1e849c557c92d17f7e69c444d05921aecfea7");
		msg.setTimestamp(Long.parseLong("1459838936"));
		MessageCrypt mes=new MessageCrypt();
		try {
			ServiceData req = mes.decryptMsg(msg);
			System.out.println("req===================="+req);
		} catch (CryptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
