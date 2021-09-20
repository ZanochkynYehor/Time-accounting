package com.project.web.password;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

public class PasswordUtil {

	public static final int ITERATIONS = 65536;
	public static final int KEY_LENGTH = 512;
	private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
	private static final SecureRandom RAND = new SecureRandom();

	private static final Logger log = LogManager.getLogger(PasswordUtil.class);
	
	public static String generateSalt() {
		byte[] salt = new byte[16];
		RAND.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	public static String hashThePlainTextPassword(String plainTextPassword, String salt) {
		char[] password = plainTextPassword.toCharArray();
		PBEKeySpec spec = new PBEKeySpec(password, salt.getBytes(), ITERATIONS, KEY_LENGTH);
		Arrays.fill(password, Character.MIN_VALUE);
		try {
			SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
			byte[] securePassword = fac.generateSecret(spec).getEncoded();
			return Base64.getEncoder().encodeToString(securePassword);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
			log.error("NoSuchAlgorithmException | InvalidKeySpecException in PasswordUtil", ex);
			return null;
		} finally {
			spec.clearPassword();
		}
	}

	public static boolean verifyThePlainTextPassword(String plainTextPassword, String hashedPassword, String salt) {
		String password = hashThePlainTextPassword(plainTextPassword, salt);
		if (password == null) {
			return false;
		}
		return password.equals(hashedPassword);
	}
}