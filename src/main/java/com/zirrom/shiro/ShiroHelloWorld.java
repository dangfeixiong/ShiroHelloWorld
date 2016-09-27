package com.zirrom.shiro;

import java.util.Arrays;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class ShiroHelloWorld {
	
	public static void main(String[] args) {
		//shiro 工厂
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro01.ini");
		//实例shiro
		SecurityManager manager = factory.getInstance();
		//初始化shiro
		SecurityUtils.setSecurityManager(manager);
		//设置TOKEN
		UsernamePasswordToken token = new UsernamePasswordToken("dfx", "123456");
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
		//退出登录
		currentUser.logout();
	}
	
	@Test
	//数据库验证用户
	public void SecurityUserByMysql(){
		Subject currentUser = ShiroUtil.securityLogin("dfx", "123456", "classpath:shiro_jdbc_mysl.ini");
		currentUser.logout();
	}
	
	@Test
	//校验角色
	public void SecurityRole(){
		Subject currentUser = ShiroUtil.securityLogin("dfx", "123456", "classpath:shiro01.ini");
		//1.校验角色[有返回值]
		boolean hasRole = currentUser.hasRole("role1");
		System.out.println("hasRole ==> " + hasRole);
		boolean[] hasRoles = currentUser.hasRoles(Arrays.asList("role1","role2"));
		System.out.println("hasRoles ==> " + toString(hasRoles));
		//2.校验角色[无返回值]
		//currentUser.hasAllRoles()
	}
	
	//将数组转换
	public String toString(boolean[] b){
		StringBuilder sb = new StringBuilder();
		if(b == null || b.length <= 0){
			return sb.toString();
		}
		for (boolean bool : b) {
			sb.append(bool).append(" | ");
		}
		return sb.toString();
	}

}
