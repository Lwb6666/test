/*
 * Alipay.com Inc.
 * Copyright (c) 2004-2007 All Rights Reserved.
 */
package com.dxjr.utils.custody.https;

import java.io.StringReader;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.dxjr.security.custody.KeyReader;
import com.dxjr.security.custody.SignManagerImpl;
import com.dxjr.utils.custody.XML;

public class HttpClientMessageSender {
    private HttpConnectionManager connectionManager;
    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
    	int Count = 100000;
    	while(Count > 0) {
    		Date date = new Date();
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        	SimpleDateFormat sdf2= new SimpleDateFormat("yyyyMMdd");
        	String seqOrderNo = "KFSH";
        	
    		String reqMessage="<Finance><Message id=\"2015110316450021308\"><RCReq id=\"RCReq\"><version>1.0.1</version><instId>PAY_RB</instId><certId>0001</certId><date>"
    						+ sdf.format(date) + "</date><p2pId>P2P0000001</p2pId><depositSerialNo>CST0000001</depositSerialNo><OrderNo>"
    						+ seqOrderNo + Count + "</OrderNo><Amount>"
    						+ Count + "</Amount><Fee>10</Fee><settleDate>"
    						+ sdf2.format(date) + "</settleDate><extension></extension></RCReq></Message></Finance>";
    		
    		PrivateKey privateKey = null;
    		
    		String clientCertFile = "D:\\workspace\\HttpsClientWebDemo\\WebContent\\configFiles\\aliapy.pfx";
    		KeyReader clientCert = new KeyReader();
    		privateKey=clientCert.readPrivateKeyfromPKCS12StoredFile(clientCertFile, "a121212");
    		
    		SignManagerImpl si = new SignManagerImpl();
    		XML xml=XML.readFrom(reqMessage);
    		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setNamespaceAware(true);
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc=docBuilder.parse(new InputSource(new StringReader(xml.getXmlStr())));
    		reqMessage =si.sign(doc, privateKey, "RCReq");
    		System.out.println("������=["+reqMessage+"]");	    
            HttpClientMessageSender messageSender = new HttpClientMessageSender();
            String rep= messageSender.send(reqMessage, "http://10.0.247.93:9081/P2PDEPOSIT/P2PReq.srv");
            System.out.println("��Ӧ����=["+rep+"]");
            Count --;
    	}
    }

    public String send(String reqXml, String postUrl) {

        HttpClient httpClient = new HttpClient(connectionManager);

        PostMethod method = new PostMethod(postUrl);

        method.addRequestHeader("Content-Type", "text/xml; charset=utf-8");
        try {
            method.setRequestEntity(new StringRequestEntity(reqXml, null, "utf-8"));

            httpClient.executeMethod(method);

            String resXml = method.getResponseBodyAsString();
            return resXml;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return null;
    }

    public HttpClientMessageSender() {
        super();

        connectionManager = new MultiThreadedHttpConnectionManager();

        HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setConnectionTimeout(10000);
        params.setSoTimeout(30000);
        params.setDefaultMaxConnectionsPerHost(10);
        params.setMaxTotalConnections(200);

        connectionManager.setParams(params);
    }

}
