package com.zirrom.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroUtil {
	
	public static Subject securityLogin(String userName, String password, String filePath){
		//shiro 工厂
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(filePath);
		//实例shiro
		SecurityManager manager = factory.getInstance();
		//初始化shiro
		SecurityUtils.setSecurityManager(manager);
		//设置TOKEN
		UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
		//获取当前登录用户
		Subject currentUser = SecurityUtils.getSubject();
		//TOKEN验证
		try {
			currentUser.login(token);
			System.out.println("验证通过");
		} catch (AuthenticationException e) {
			e.printStackTrace();
			System.out.println("验证失败");
		}
		
		return currentUser;
	}
}
