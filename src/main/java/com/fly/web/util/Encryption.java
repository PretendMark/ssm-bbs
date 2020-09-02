package com.fly.web.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class Encryption {
	/**
	 *
	 * @param encryptionMode		加密方式？MD5  SHA1
	 * @param salt					Salt
	 * @param password			明文密码
	 * @param encryptionCount	加密次数
	 * @return						加密结果
	 */
	public static Object encryption( String encryptionMode, Object salt, String password, int encryptionCount )
	{
		Object result = new SimpleHash( encryptionMode, password, salt, encryptionCount );
		return(result);
	}


	/**
	 * 生成公钥私钥
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static Map<Integer, String> getRSA() throws NoSuchAlgorithmException
	{
		Map<Integer, String> keyMap = new HashMap<Integer, String>();           /* 用于封装随机产生的公钥与私钥 */
		/* KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象 */
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance( "RSA" );
		/* 初始化密钥对生成器，密钥大小为96-1024位 */
		keyPairGen.initialize( 1024, new SecureRandom() );
		/* 生成一个密钥对，保存在keyPair中 */
		KeyPair		keyPair		= keyPairGen.generateKeyPair();
		RSAPrivateKey	privateKey	= (RSAPrivateKey) keyPair.getPrivate(); /* 得到私钥 */
		RSAPublicKey	publicKey	= (RSAPublicKey) keyPair.getPublic();   /* 得到公钥 */
		String		publicKeyString = new String( Base64.encodeBase64( publicKey.getEncoded() ) );
		/* 得到私钥字符串 */
		String privateKeyString = new String( Base64.encodeBase64( (privateKey.getEncoded() ) ) );
		/* 将公钥和私钥保存到Map */
		keyMap.put( 0, publicKeyString );                                       /* 0表示公钥 */
		keyMap.put( 1, privateKeyString );                                      /* 1表示私钥 */
		return(keyMap);
	}


	/**
	 * 私钥解密
	 * @param str				公钥加密后的字符串
	 * @param privateKey		服务端生成的私钥
	 * @return
	 * @throws Exception		可能的异常
	 */
	public static String decrypt( String str, String privateKey ) throws Exception
	{
		/* 64位解码加密后的字符串 */
		byte[] inputByte = Base64.decodeBase64( str.getBytes( "UTF-8" ) );
		/* base64编码的私钥 */
		byte[] decoded = Base64.decodeBase64( privateKey );
		RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance( "RSA" ).generatePrivate( new PKCS8EncodedKeySpec( decoded ) );
		/* RSA解密 */
		Cipher cipher = Cipher.getInstance( "RSA" );
		cipher.init( Cipher.DECRYPT_MODE, priKey );
		String outStr = new String( cipher.doFinal( inputByte ) );
		return(outStr);
	}


	/**
	 * 公钥加密
	 * @param str				被加密字符串
	 * @param publicKey		公钥
	 * @return
	 * @throws Exception
	 */
	public static String encrypt( String str, String publicKey ) throws Exception
	{
		/* base64编码的公钥 */
		byte[] decoded = Base64.decodeBase64( publicKey );
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance( "RSA" ).generatePublic( new X509EncodedKeySpec( decoded ) );
		/* RSA加密 */
		Cipher cipher = Cipher.getInstance( "RSA" );
		cipher.init( Cipher.ENCRYPT_MODE, pubKey );
		String outStr = Base64.encodeBase64String( cipher.doFinal( str.getBytes( "UTF-8" ) ) );
		return(outStr);
	}
}
