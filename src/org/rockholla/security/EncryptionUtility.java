package org.rockholla.security;

import java.io.Serializable; 
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.rockholla.string.Base64;



/**
 * Util for encrypting and serailizing objects.
 * 
 * @author Patrick Force <patrickforce@gmail.com>
 * 
 */
public class EncryptionUtility
{

	public static String encryptAndSerialize(Serializable object, String password) 
	{
		try
		{
			SecretKey key = buildSecretKey(password);
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			SealedObject sealedObject = new SealedObject(object, cipher);
			
			return Base64.encodeObject(sealedObject);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static Object deserializeAndDecrypt(String cipherText, String password)
	{
		try
		{
			SecretKey key = buildSecretKey(password);
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key);
			
			SealedObject sealedObject = (SealedObject) Base64.decodeToObject(cipherText);
			
			return sealedObject.getObject(cipher);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static String getMd5(String toEncrypt) throws NoSuchAlgorithmException {

		MessageDigest digest = MessageDigest.getInstance("MD5");
	    digest.update(toEncrypt.getBytes());
	    byte[] hash = digest.digest();
	    
	    StringBuffer hexString = new StringBuffer();
		for (int i=0;i<hash.length;i++) {
			hexString.append(Integer.toHexString(0xFF & hash[i]));
		}

	    
	    return hexString.toString();
		
	}
	
	private static SecretKey buildSecretKey(String password) throws Exception
	{
		byte[] passwordBytes = password.getBytes();
		DESKeySpec keySpec = new DESKeySpec(passwordBytes);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		return keyFactory.generateSecret(keySpec);
	}
}