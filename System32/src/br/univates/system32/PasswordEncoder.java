package br.univates.system32;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordEncoder {
	
	private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
	private static final int ITERATIONS = 65536;
	private static final int SALT_SIZE = 32;
	private static final int HASH_SIZE = 128;

	private  PasswordEncoder() {
		
	}
	
	public static String encodePassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[32];
		random.nextBytes(salt);
		
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, HASH_SIZE);
		SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
		
		byte[] hash =  factory.generateSecret(spec).getEncoded();
		
		return ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
		
	}
	
	public static boolean comparePasswords(String password, String comparisonPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		
		String[] parts = comparisonPassword.split(":");
		
		int iterations = Integer.parseInt(parts[0]);
		byte[] salt = fromHex(parts[1]);
		byte[] hash = fromHex(parts[2]);
		
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, hash.length * 8);
		SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
		
		byte[] comparisonHash =  factory.generateSecret(spec).getEncoded();
		
		
		int diff = hash.length ^ comparisonHash.length;
		for(int i = 0; i < hash.length && i < comparisonHash.length; i++) {
			diff |= hash[i] ^ comparisonHash[i];
		}
		return diff == 0;
		
	}
	
	private static String toHex(byte[] byteArray) {
		
		BigInteger bi = new BigInteger(1, byteArray);
        String hex = bi.toString(16);
        int paddingLength = (byteArray.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
		
	}
	
	private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
	
	
}
