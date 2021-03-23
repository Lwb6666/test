package com.dxjr.security;


import static com.dxjr.security.ShiroConstants.permissions;
import static com.dxjr.security.ShiroConstants.roles;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.dxjr.base.entity.Member;
import com.dxjr.base.mapper.BaseMemberMapper;
import com.dxjr.portal.member.service.MemberService;
import com.dxjr.portal.member.vo.MemberCnd;
import com.dxjr.portal.member.vo.MemberVo;

public class SsoRealm extends AuthorizingRealm {
	
	@Autowired
	private BaseMemberMapper baseMemberMapper;
	
	@Autowired
	private MemberService memberService;

	public SsoRealm() {
		setAuthenticationTokenClass(SsoToken.class);
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		SsoToken ssoToken = (SsoToken) token;
		if (token == null) {
			return null;
		}

		String ticket = (String) ssoToken.getCredentials();
		if (!StringUtils.hasText(ticket)) {
			return null;
		}

		try {
			String userIdMD5 = ssoToken.getUserIdMD5();
			MemberCnd memberCnd = new MemberCnd();
			memberCnd.setUserIdMD5(userIdMD5);
			MemberVo member = memberService.queryMemberByCnd(memberCnd);
			
			if (member == null) {
				throw new UnknownAccountException("Unknown user for MD5 [" + userIdMD5 + "]");
			}
			
			if (!member.getStatus().equals(0)) {
				throw new DisabledAccountException("Disabled account for MD5 [" + userIdMD5 + "]");
			}

			ShiroUser user = new ShiroUser();
			user.setUserId(member.getId());
			user.setUserIdMD5(member.getUseridmd5());
			user.setUserName(member.getUsername());
			user.setHeadImg(member.getHeadimg());
			user.setIsFinancialUser(member.getIsFinancialUser());
			user.setPlatform(ssoToken.getPlatform());

			List<Object> principals = CollectionUtils.asList(new Object[] { user });
			PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, getName());
			
			return new SimpleAuthenticationInfo(principalCollection, ticket);
		} catch (Exception e) {
			throw new AuthenticationException("Unable to validate ticket [" + ticket + "]", e);
		}
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		
		SimplePrincipalCollection simplePrincipalCollection = (SimplePrincipalCollection) principalCollection;
		ShiroUser shiroUser = (ShiroUser) simplePrincipalCollection.getPrimaryPrincipal();
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRole(roles[shiroUser.getIsFinancialUser()]);
		info.addStringPermissions(permissions.get(roles[shiroUser.getIsFinancialUser()]));
		return info;
	}
}