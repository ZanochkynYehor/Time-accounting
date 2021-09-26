package com.project.web.password;

import org.junit.Assert;
import org.junit.Test;

public class PasswordUtilTest {

	@Test
	public void testHashingPassword() {
		String salt = PasswordUtil.generateSalt();
		String password = PasswordUtil.hashThePlainTextPassword("test", salt);
		boolean res = PasswordUtil.verifyThePlainTextPassword("test", password, salt);
		Assert.assertTrue(res);
		res = PasswordUtil.verifyThePlainTextPassword("wrongPass", password, salt);
		Assert.assertFalse(res);
	}
}