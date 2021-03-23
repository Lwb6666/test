package com.dxjr.portal.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CharacterEncoder {
	public static String encodeURL(String input, String enc) {
		if (input == null) {
			return null;
		}
		try {
			return URLEncoder.encode(input, enc);
		} catch (UnsupportedEncodingException e) {
			// ignore
		}
		return input;
	}
	
	public static String decodeURL(String input, String enc) {
		if (input == null) {
			return null;
		}
		try {
			return URLDecoder.decode(input, enc);
		} catch (UnsupportedEncodingException e) {
			// ignore
		}
		return input;
	}

}
