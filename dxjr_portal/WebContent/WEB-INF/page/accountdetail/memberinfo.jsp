<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
	<!--左边列表开始-->       
 	 					<div class="memberinfo">
						<div class="user_info">
					<c:if test="${member.headimg!=null }">
		   				<img src="${basePath}${member.headimg }" width="123" height="132" />
		   			</c:if>
						</div>
						<div class="tblenght user_rinfo">
							<table>
								<tr>
									<td>用户名：${member.userNameSecret }</td>
									<td colspan="2">注册时间：${fn:substring(member.addtimeFmt, 0, 10)}</td>
								</tr>
								<tr>
									<td>会员级别： <c:if test="${userlevel == 0 }">
											<a href="javascript:toUserLevel();" title="普通会员"><img
												src="${basePath}/images/vip_0.gif" width="17"
												height="16" /></a>
										</c:if> <c:if test="${userlevel == 10 }">
											<a href="javascript:toUserLevel();" title="金牌会员"><img
												src="${basePath}/images/vip_10.gif" width="17"
												height="16" /></a>
										</c:if> <c:if test="${userlevel == 20 }">
											<a href="javascript:toUserLevel();" title="白金会员"><img
												src="${basePath}/images/vip_20.gif" width="17"
												height="16" /></a>
										</c:if> <c:if test="${userlevel == 30 }">
											<a href="javascript:toUserLevel();" title="钻石会员"><img
												src="${basePath}/images/vip_30.gif" width="17"
												height="16" /></a>
										</c:if> <c:if test="${userlevel == 40 }">
											<a href="javascript:toUserLevel();" title="皇冠会员"><img
												src="${basePath}/images/vip_40.gif" width="17"
												height="16" /></a>
										</c:if> <c:if test="${userlevel == 50 }">
											<a href="javascript:toUserLevel();" title="金皇冠会员"><img
												src="${basePath}/images/vip_50.gif" width="17"
												height="16" /></a>
										</c:if> <c:if test="${userlevel == 60 }">
											<a href="javascript:toUserLevel();" title="终身顶级会员"><img
												src="${basePath}/images/vip_60.gif" width="17"
												height="16" /></a>
										</c:if>
									</td>
									<td colspan="2">最后登录时间：${fn:substring(member.lastlogintimeFmt, 0, 10)}</td>
								</tr>

							</table>
						</div>
					</div>
<!--左边列表结束--> 
