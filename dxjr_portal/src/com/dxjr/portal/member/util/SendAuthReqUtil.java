package com.dxjr.portal.member.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.dxjr.portal.util.JsonUtils;


public class SendAuthReqUtil {

    public static CloseableHttpClient httpclient = null;

    public static final String USER_ID = bankConstants.bank_user_id; // 商户编号

    public static final String M_KEY = bankConstants.bank_mKey; // md5密钥

    public static final String AES_KEY =bankConstants.bank_aesKey;// aes密钥

    public static final String URL_STR = bankConstants.bank_id_url; // 访问地址

    public static String sendHttpRequest(String url, Map<String, String> cloneParamMap) throws Exception {
        List<NameValuePair> nvp = new ArrayList<NameValuePair>();
        HttpResponse response = null;
        InputStream input = null;
        HttpEntity entity = null;
        String postResp = null;
        httpclient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000)
                .setConnectionRequestTimeout(15000).build();
        HttpPost httpost = new HttpPost(url);
        httpost.setConfig(requestConfig);
        httpost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpost.addHeader("Accept-Language", "zh-cn");
        httpost.addHeader("Accept-Encoding", "gzip, deflate");
        try {

            nvp = getNameValuePairArr(cloneParamMap);
            httpost.setEntity(new UrlEncodedFormEntity(nvp, "UTF-8"));
            response = httpclient.execute(httpost);
            entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() != 200) {
                httpost.abort();
                return null;
            }
            input = entity.getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            String tempBf = null;
            StringBuffer xmlStr = new StringBuffer();
            while ((tempBf = reader.readLine()) != null) {
                xmlStr.append(tempBf);
            }
            postResp = xmlStr.toString();
            if (entity != null)
                entity.consumeContent();
        } catch (Exception e) {
            httpost.abort();
            throw new Exception();
        } finally {
            httpclient.getConnectionManager().closeExpiredConnections();
            httpclient.getConnectionManager().closeIdleConnections(5, TimeUnit.SECONDS);
        }
        return postResp;
    }

    public static List<NameValuePair> getNameValuePairArr(Map<String, String> parasMap) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Entry<String, String> parasEntry : parasMap.entrySet()) {
            String parasName = parasEntry.getKey();
            String parasValue = parasEntry.getValue();
            nvps.add(new BasicNameValuePair(parasName, parasValue));
        }
        return nvps;
    }

    /**
     * 
     * <p>
     * Description:银行卡四要素验证<br />
     * </p>
     * 
     * @author liutao
     * @version 0.1 2016年5月4日
     * @param name
     * @param idCode
     * @param bankCode
     * @param phoneNum
     * @return
     * @throws Exception
     *             String
     */
    public static Map<?, ?> sendAuthBankCodeValid(String name, String idCode, String bankCode, String phoneNum)
            throws Exception {
        String urlStr = URL_STR;
        String userId = USER_ID;// 用户编号
        String ts = System.currentTimeMillis() + "";
        String mKey = M_KEY;// MD5密钥
        String dateString = "";
        String aesKey = AES_KEY;// AES密钥
        try {
            name = URLEncoder.encode(name, "UTF-8");// 姓名UTF-8编码
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateString = formatter.format(currentTime);
            dateString = URLEncoder.encode(dateString, "UTF-8");// 日期UTF-8编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 姓名AES加密
        String nameDes = AESUtil.encrypt(name, aesKey);
        // 身份证号AES加密
        String idCodeDes = AESUtil.encrypt(idCode, aesKey);
        String bankCodeDes = AESUtil.encrypt(bankCode, aesKey);
        String phoneNumDes = AESUtil.encrypt(phoneNum, aesKey);

        String md5Str = "userId=" + userId + "realName=" + name + "idCode=" + idCode + "bankCardNo=" + bankCode
                + "phoneNum=" + phoneNum + "ts=" + ts + mKey;

        md5Str = Md5Util.md5Sign(md5Str);
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        params.put("realName", nameDes);
        params.put("idCode", idCodeDes);
        params.put("reqDate", dateString);
        params.put("ts", ts);
        params.put("sign", md5Str);
        params.put("authType", "14");// 01：实名认证，02：人证合一，03：银行卡认证
        params.put("uImage", "");
        params.put("bankCardNo", bankCodeDes);
        params.put("phoneNum", phoneNumDes);

        String resultJson = sendHttpRequest(urlStr, params);
        System.out.println(resultJson);
        return JsonUtils.json2Map(resultJson);
    }

    /**
     * 
     * <p>
     * Description:余额查询<br />
     * </p>
     * 
     * @author liutao
     * @version 0.1 2016年5月4日
     * @param name
     * @param idCode
     * @param bankCode
     * @param phoneNum
     * @return
     * @throws Exception
     *             String
     */
    public static Map<?, ?> queryBalance() throws Exception {
        String urlStr = "http://139.196.195.95:8080/api/queryUserBalance.htm";
        String userId = USER_ID;// 用户编号
        String mKey = M_KEY;// MD5密钥

        String md5Str = userId + mKey;

        md5Str = Md5Util.md5Sign(md5Str);
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        params.put("sign", md5Str);
        //String resultJson = sendHttpRequest(urlStr, params);
        String resultJson = "";
        System.out.println(resultJson);
        return JsonUtils.json2Map(resultJson);
    }

    public static void main(String[] args) {
        String urlStr = "http://139.196.195.95:8080/api/hdAuthenCenter.htm";
        String userId = "";// 用户编号
        String ts = System.currentTimeMillis() + "";
        String mKey = "";// MD5密钥
        String dateString = "";
        String name = "杨仕金";// 姓名
        String idCode = "360427198811172018";// 身份证号
        String aesKey = "";// AES密钥
        String bankCode = "6217001210035664745";
        String phoneNum = "18516609764";
        try {
            name = URLEncoder.encode(name, "UTF-8");// 姓名UTF-8编码
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateString = formatter.format(currentTime);
            dateString = URLEncoder.encode(dateString, "UTF-8");// 日期UTF-8编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 姓名AES加密
        String nameDes = AESUtil.encrypt(name, aesKey);
        // 身份证号AES加密
        String idCodeDes = AESUtil.encrypt(idCode, aesKey);
        String bankCodeDes = AESUtil.encrypt(bankCode, aesKey);
        String phoneNumDes = AESUtil.encrypt(phoneNum, aesKey);

        String md5Str = "userId=" + userId + "realName=" + name + "idCode=" + idCode + "bankCardNo=" + bankCode
                + "phoneNum=" + phoneNum + "ts=" + ts + mKey;

        md5Str = Md5Util.md5Sign(md5Str);
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);
        params.put("realName", nameDes);
        params.put("idCode", idCodeDes);
        params.put("reqDate", dateString);
        params.put("ts", ts);
        params.put("sign", md5Str);
        params.put("authType", "05");// 01：实名认证，02：人证合一，03：银行卡认证
        params.put("uImage", "");
        params.put("bankCardNo", bankCodeDes);
        params.put("phoneNum", phoneNumDes);

        try {
            System.out.println(urlStr + "?userId=" + userId + "&auName=" + nameDes + "&auId=" + idCodeDes
                    + "&bankCardNo=&phoneNum=&reqDate=" + dateString + "&ts=" + ts + "&sign=" + md5Str);

            //System.out.println(sendHttpRequest(urlStr, params));
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
    }
}
