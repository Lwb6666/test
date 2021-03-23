package com.dxjr.portal.investment.util;

import com.alibaba.fastjson.JSON;
import com.dxjr.portal.investment.vo.ChannelBindingInvestVo;
import com.dxjr.portal.investment.vo.UserInfo;
import com.taobao.api.internal.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;


public class Tripledes {

	private final static Logger logger = LoggerFactory.getLogger(Tripledes.class);
	
	 // 算法名称
    public static final String KEY_ALGORITHM = "TripleDES";
    // 算法名称/加密模式/填充方式
    public static final String CIPHER_ALGORITHM_CBC = "DESede/CBC/PKCS5Padding";

    private String key = "NmY1MWJmMGItMDU4Zi00MDgy";
    private String lv = "Y2IwNjBj";

    private Cipher cipher;
    private SecretKey secretKey;

    private static Tripledes tripledes;

    public static Tripledes getInstance() {
        if(tripledes == null) {
            create();
        }
        return tripledes;
    }

    private synchronized static void create() {
        if(tripledes == null) {
            tripledes = new Tripledes();
        }
    }

    private Tripledes() {
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);

            DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
            secretKey = SecretKeyFactory.getInstance(KEY_ALGORITHM).generateSecret(spec);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * 加密
     * @param t
     * @param <T>
     * @return
     */
    public <T> String encrypt(T t) {
        try {

            String json = JSON.toJSONString(t);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(lv.getBytes()));
            byte[] encryptData = cipher.doFinal(json.getBytes("utf-8"));

            String base64JSON = Base64.encodeToString(encryptData,true);
            //String urlJSON = URLDecoder.decode(base64JSON,"utf-8");
            base64JSON = base64JSON.replace("+","-").replace("/","_");
            base64JSON = base64JSON.replaceAll("\r","").replaceAll("\n","");
            return base64JSON;
        }catch (Exception e) {
            return null;
        }
    }
   
    /**
     * 解密
     * @param str
     * @param <T>
     * @return
     */
    public <T> T decrypt(String str,Class<T> clz) {

        try {
            str = str.replace("-","+").replace("_","/").replaceAll("\r","").replaceAll("\n","");
            //String urlJSON = new String(URLCodec.decodeUrl(str.getBytes("utf-8")));
            byte[] base64JSON  = Base64.decode(str);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(lv.getBytes()));
            byte[] encryptData = cipher.doFinal(base64JSON);
            String json = new String(encryptData,"utf-8");
            logger.debug("Tripledes decrypt json : {}" , json);
            return (T)JSON.parseObject(json,clz);
        }catch (Exception e) {
            logger.error("Tripledes decrypt error : " ,e);
            return null;
        }
    }

}
