package com.dxjr.portal.tzjinterface.util;

@SuppressWarnings("serial")
public class CryptException extends Exception {
	public final static int OK = 0;
	public final static int ValidateSignatureError = -40001;
	public final static int ParseXmlError = -40002;
	public final static int ComputeSignatureError = -40003;
	public final static int IllegalAesKey = -40004;
	public final static int ValidateAppidError = -40005;
	public final static int EncryptAESError = -40006;
	public final static int DecryptAESError = -40007;
	public final static int IllegalBuffer = -40008;
	public final static int MobileIsNull = -40009;
	public final static int UserIsNull = -40010;
	public final static int MobileIsExist = 1001;
	public final static int EmailIsExist = 1002;
	public final static int CardNoIsExist = 1003;
	public final static int ParamIsError = 201;

	private int code;

	public static String getMessage(int code) {
		switch (code) {
		case ValidateSignatureError:
			return "签名验证错误";
		case ParseXmlError:
			return "xml解析失败";
		case ComputeSignatureError:
			return "sha加密生成签名失败";
		case IllegalAesKey:
			return "SymmetricKey非法";
		case ValidateAppidError:
			return "appid校验失败";
		case EncryptAESError:
			return "aes加密失败";
		case DecryptAESError:
			return "aes解密失败";
		case IllegalBuffer:
			return "解密后得到的buffer非法";
		case MobileIsNull:
			return "手机号码不能为空";
		case UserIsNull:
			return "用户名不能为空";
		case MobileIsExist:
			return "手机号已占用";
		case EmailIsExist:
			return "邮箱已占用";
		case CardNoIsExist:
			return "身份证已占用";
		case ParamIsError:
			return "请求参数出错";
		default:
			return null; // cannot be
		}
	}

	public int getCode() {
		return code;
	}

	CryptException(int code) {
		super(getMessage(code));
		this.code = code;
	}
}
