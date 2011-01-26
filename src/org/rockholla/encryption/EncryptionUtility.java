/*
 *	This is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *	 
 */

package org.rockholla.encryption;

import java.io.Serializable; 
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
 * Util for encrypting and serailizing objects.
 * 
 * @author rockholla
 * 
 */
public class EncryptionUtility
{

	/**
	 * Builds and returns an encrypted, password-protected representation of an object
	 * 
	 * @param object	the object to encrypt
	 * @param password	the password "key" to unlocking/deserializing the object
	 * @return			the encrypted/locked object
	 */
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
	
	/**
	 * Decrypts an encrypted/locked string representation of an object
	 * 
	 * @param cipherText	the encrypted/locked object, a string representation
	 * @param password		the password or "key" to unlocking the object
	 * @return				the decrypted object
	 */
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
	
	/**
	 * Encrypts a string using MD5 encrypting
	 * 
	 * @param toEncrypt	the string to encrypt
	 * @return			the encrypted string
	 * @throws NoSuchAlgorithmException
	 */
	public static String getMd5(String toEncrypt) throws NoSuchAlgorithmException 
	{

		MessageDigest digest = MessageDigest.getInstance("MD5");
	    digest.update(toEncrypt.getBytes());
	    byte[] hash = digest.digest();
	    
	    StringBuffer hexString = new StringBuffer();
		for (int i=0;i<hash.length;i++) {
			hexString.append(Integer.toHexString(0xFF & hash[i]));
		}

	    
	    return hexString.toString();
		
	}
	
	/**
	 * Builds and returns a secret key based on a password string
	 * 
	 * @param password	the unprotected password string
	 * @return			a secret key representation of the password
	 * @throws Exception
	 */
	private static SecretKey buildSecretKey(String password) throws Exception
	{
		byte[] passwordBytes = password.getBytes();
		DESKeySpec keySpec = new DESKeySpec(passwordBytes);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		return keyFactory.generateSecret(keySpec);
	}
}