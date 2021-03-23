package com.dxjr.security;

public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	private Integer platform;

	public UsernamePasswordToken(String username, String password, Integer platform) {
		super(username, password);
		this.platform = platform;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}
}
