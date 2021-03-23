package com.dxjr.portal.sinapay.model;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Iterator;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.Validate;

import com.dxjr.portal.sinapay.protocol.SinaPayRequest;
import com.dxjr.portal.sinapay.protocol.SinaQueryRequest;
import com.dxjr.portal.sinapay.sign.MD5;
import com.dxjr.portal.sinapay.sign.SignAndVerify;

/**
 * 
 * 
 * 根据参数生成表单元素.
 * 
 * @author daisy
 */
public class FormGenerator {
	/**
	 * Form表单页面元素的html id
	 */
	private String formElementId;
	/**
	 * 密钥,当使用pki时，是base64编码的pkcs8密钥格式
	 */
	private String key;
	/**
	 * Form表单提交的地址
	 */
	private String actionUrl;
	/**
	 * 签名类型，参数值见规范，目前支持1，4两种
	 */
	private String signType;

	private transient PrivateKey privateKey;

	public void init() {
		Validate.notBlank(formElementId, "formElementId is blank");
		Validate.notBlank(key, "key is blank");
		Validate.notBlank(actionUrl, "actionUrl is blank");
		Validate.notBlank(signType, "signType is blank");

		if (signType.equals("4")) {
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key));
			KeyFactory fact;
			try {
				fact = KeyFactory.getInstance("RSA");
				privateKey = fact.generatePrivate(spec);
			} catch (NoSuchAlgorithmException e) {
				// NOT SUPPOSED TO HAPPENED
				throw new UnsupportedOperationException(e);
			} catch (InvalidKeySpecException e) {
				throw new RuntimeException("invalid key", e);
			}
		}
	}

	/**
	 * 
	 * @param request signType字段将被强制替换
	 * @return
	 */
	public String generateForm(SinaPayRequest request) {
		Validate.notNull(request, "request is null");
		request.setSignType(signType);
		String signContent = request.generateSignContent();
		if (signType.equals("1")) {
			String toMD5 = signContent + "&" + request.md5KeyProperty() + "=" + key;
			String signMsg = MD5.getMD5(toMD5.getBytes(Charsets.UTF_8));
			request.setSignMsg(signMsg);
		} else if (signType.equals("4")) {
			try {
				String signMsg = SignAndVerify.sign("SHA1withRSA", request.generateSignContent(), privateKey);
				request.setSignMsg(signMsg);
			} catch (InvalidKeyException e) {
				throw new RuntimeException("invalid key", e);
			} catch (SignatureException e) {
				throw new RuntimeException(e);
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("Algorithm not supported", e);
			}
		} else {
			throw new UnsupportedOperationException("Currently we only support md5(1) and rsa certification (4). ");
		}
		BeanMap params = new BeanMap(request);
		StringBuilder formBuilder = new StringBuilder();
		formBuilder.append("<form id=\"" + formElementId + "\" action=\"" + actionUrl + "\" method=\"post\">\r\n");
		Iterator keyIterator = params.keyIterator();
		while (keyIterator.hasNext()) {
			String propertyName = (String) keyIterator.next();
			Object value = params.get(propertyName);
			if (value != null && value instanceof String) {
				formBuilder.append("<input type=\"hidden\" name=\"").append(propertyName).append("\" value=\"");
				formBuilder.append(StringEscapeUtils.escapeHtml4((String) value)).append("\" />\r\n");
			}
		}
		formBuilder.append("</form>");
		return formBuilder.toString();
	}

	/**
	 * 
	 * @param request 查询接口 signType字段将被强制替换
	 * @return
	 */
	public SinaQueryRequest generateSignMsg(SinaQueryRequest request) {
		Validate.notNull(request, "request is null");
		request.setSignType(signType);
		String signContent = request.generateSignContent();
		if (signType.equals("1")) {
			String toMD5 = signContent + "&" + request.md5KeyProperty() + "=" + key;
			String signMsg = MD5.getMD5(toMD5.getBytes(Charsets.UTF_8));
			request.setSignMsg(signMsg);
		} else if (signType.equals("4")) {
			try {
				String signMsg = SignAndVerify.sign("SHA1withRSA", request.generateSignContent(), privateKey);
				request.setSignMsg(signMsg);
			} catch (InvalidKeyException e) {
				throw new RuntimeException("invalid key", e);
			} catch (SignatureException e) {
				throw new RuntimeException(e);
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("Algorithm not supported", e);
			}
		} else {
			throw new UnsupportedOperationException("Currently we only support md5(1) and rsa certification (4). ");
		}
		return request;
	}

	public void setFormElementId(String formElementId) {
		this.formElementId = formElementId;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

}
