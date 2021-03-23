package com.dxjr.portal.investment.util;

import org.springframework.web.client.RestTemplate;


public class RequestUtil {
	private static RequestUtil requestUtil;

    private RequestUtil() {
    }

    public static RequestUtil getInstance() {
        if(requestUtil == null) {
            create();
        }
        return requestUtil;
    }

    private synchronized static void create() {
        if(requestUtil == null) {
            requestUtil = new RequestUtil();
        }
    }

    private final static RestTemplate REST = new RestTemplate();

    /**
     * post请求
     * @param url
     * @param obj
     * @param clz
     * @param <T>
     * @return
     */
    public <T> T post(String url,Object obj,Class<T> clz) {

        String params = "?params=" + Tripledes.getInstance().encrypt(obj);
        T t = REST.postForObject(url + params,null,clz);
        return t;

        //T t = Tripledes.getInstance().decrypt(result.getParams(),clz);
        //return t;

    }

}
