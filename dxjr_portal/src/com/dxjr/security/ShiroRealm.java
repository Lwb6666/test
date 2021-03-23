package com.dxjr.security;

import static com.dxjr.security.ShiroConstants.permissions;
import static com.dxjr.security.ShiroConstants.roles;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.dxjr.portal.member.mapper.MemberMapper;
import com.dxjr.portal.member.vo.MemberVo;

public class ShiroRealm extends AuthorizingRealm {
	
//	private static final String[] roles = { "borrow", "invest" };
//	private static final Map<String, String[]> permissions = new HashMap<String, String[]>(2);
//	static {
//		permissions.put("borrow", new String[] { "1", "2" });
//		permissions.put("invest", new String[] { "3", "4" });
//	}
	
	@Autowired
	private MemberMapper memberMapper;
	
	public ShiroRealm() {
		setAuthenticationTokenClass(com.dxjr.security.UsernamePasswordToken.class);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
		com.dxjr.security.UsernamePasswordToken usernamePasswordToken = (com.dxjr.security.UsernamePasswordToken) authenticationToken;

		MemberVo memberVo = memberMapper.queryMemberByloginname(usernamePasswordToken.getUsername());
		if (memberVo == null) {
			throw new UnknownAccountException("Unknown username [" + usernamePasswordToken.getUsername() + "]");
		}
		
		if (!memberVo.getStatus().equals(0)) {
			throw new DisabledAccountException("Disabled account [" + usernamePasswordToken.getUsername() + "]");
		}

		ShiroUser user = new ShiroUser();
		user.setUserId(memberVo.getId());
		user.setUserIdMD5(memberVo.getUseridmd5());
		user.setUserName(memberVo.getUsername());
		user.setHeadImg(memberVo.getHeadimg());
		user.setIsFinancialUser(memberVo.getIsFinancialUser());
		user.setPlatform(usernamePasswordToken.getPlatform());
		
		return new SimpleAuthenticationInfo(user, memberVo.getLogpassword(), getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		info.addRole(roles[shiroUser.getIsFinancialUser()]);
		info.addStringPermissions(permissions.get(roles[shiroUser.getIsFinancialUser()]));
		return info;
	}

}
