package com.dxjr.portal.investment.vo;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * @User : xiaobaicai
 * @Date : 2016/1/4 14:48
 * @From : apiDemo
 */
public class PlatformVo implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String secret; //密钥
    private List<ProductVo> productList = Lists.newArrayList(); //产品列表

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public List<ProductVo> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductVo> productList) {
        this.productList = productList;
    }
}
