package com.project.web.password;

import org.junit.jupiter.api.Test;

public class PasswordUtilTest {

	@Test
	public void test() {
		String salt = PasswordUtil.generateSalt();
		System.out.println("salt ==> " + salt);
		String password = PasswordUtil.hashThePlainTextPassword("asdf1G", salt);
		System.out.println("password ==> " + password);
		boolean res = PasswordUtil.verifyThePlainTextPassword("asdf1G", password, salt);
		System.out.println("result ==> " + res);
	}
}
