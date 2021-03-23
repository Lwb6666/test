package com.dxjr.portal.liumi.utils;

import junit.framework.TestCase;  
import org.apache.commons.httpclient.HostConfiguration;  
import org.apache.commons.httpclient.HttpClient;  
import org.apache.commons.httpclient.methods.PostMethod;  
import org.apache.commons.httpclient.params.HttpMethodParams; 
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;
  
import org.apache.commons.httpclient.protocol.Protocol;  
  
import java.io.BufferedReader;  
import java.io.InputStream;  
import java.io.InputStreamReader; 


public class TrustAnyTrust {

	/*public static void main(String[] args) {  
        try {  
            HttpClient client = new HttpClient();  
  
            Protocol easyHttps1 = new Protocol("https", new EasySSLProtocolSocketFactory(), 443);  
            Protocol.registerProtocol("https", easyHttps1);  
  
            HostConfiguration hc = new HostConfiguration();  
            hc.setHost("127.0.0.1", 80, easyHttps1);  
  
            String url = "https://localhost:8080/";  
            PostMethod method = new PostMethod(url);  
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");  
            int t = client.executeMethod(hc, method);  
            InputStream is = method.getResponseBodyAsStream();  
            BufferedReader br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), "utf-8"));  
            StringBuffer response = new StringBuffer();  
            String readLine;  
            while (((readLine = br.readLine()) != null)) {  
                response.append(readLine);  
            }  
            System.out.print(response);  
            System.out.print(t);  
        } catch (Exception e) {  
            System.out.print(e);  
        }  
    }  */
	
}
