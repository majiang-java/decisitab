package com.ifre.util.encrypte;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Protect class file from decompile.
 * 
 * @author Yan Sun
 * @version 1.0
 */
public class ClassGuarder {
	private String strKeyAlg = "AES";
	private int nKeySize = 128;
	private String strCipherAlg = "AES/CBC/PKCS5Padding";
	private static final String fileSeparator = "/"; 
	private SecretKey key = null;
	private byte[] baIv = new byte[128];

	private static ClassGuarder classGuarder = null;

	public static synchronized ClassGuarder getInstance() {
		if (classGuarder == null) {
			classGuarder = new ClassGuarder();
		}
		return classGuarder;
	}

	public static synchronized ClassGuarder getInstance(String seedName) {
		if (classGuarder == null) {
			classGuarder = new ClassGuarder(seedName);
		}
		return classGuarder;
	}

	private ClassGuarder(String seedName) {

		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(seedName);
			inputStream.read(baIv);
			byte[] baKey = new byte[nKeySize / 8];
			baKey = Arrays.copyOf(baIv, nKeySize / 8);
			key = new SecretKeySpec(baKey, strKeyAlg);

			inputStream.close();

		}  catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}
	
	private ClassGuarder() {
		// Put the seed file in the working directory.
		String path = fileSeparator+"com"+fileSeparator+"ifre"+fileSeparator+"util"+fileSeparator+"encrypte"+fileSeparator+"IfreClassLoader.class";
		try {
			BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path));
			inputStream.read(baIv);
			byte[] baKey = new byte[nKeySize / 8];
			baKey = Arrays.copyOf(baIv, nKeySize / 8);
			key = new SecretKeySpec(baKey, strKeyAlg);

			inputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

	private InputStream loadClassData(String name) {
		// Opening the file
		InputStream stream = getClass().getClassLoader().getResourceAsStream(name);
/*		int size = stream.available();
		byte buff[] = new byte[size];
		DataInputStream in = new DataInputStream(stream);
		// Reading the binary data
		in.readFully(buff);
		in.close();
		return buff;*/
		return stream;
	}


	private void initKey(BufferedInputStream inputStream) {
		try {
			inputStream.read(baIv);
			byte[] baKey = new byte[nKeySize / 8];
			baKey = Arrays.copyOf(baIv, nKeySize / 8);
			key = new SecretKeySpec(baKey, strKeyAlg);

			inputStream.close();

		}  catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		
	}
	/**
	 * Encrypt a file.
	 * 
	 * @param classFile
	 * @return encrypted bytes
	 */
	public byte[] encryptClass(File classFile) {
		byte[] encoded = null;
		try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(classFile))) {
			encoded = doCrypto(key, baIv, inputStream, Cipher.ENCRYPT_MODE, strCipherAlg);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return encoded;
	}

	/**
	 * Decrypt a byte array
	 * 
	 * @param encodedClass
	 * @return decrypted bytes.
	 */
	public byte[] decryptClass(byte[] encodedClass) {
		byte[] decodedByte = null;

		ByteArrayInputStream bai = new ByteArrayInputStream(encodedClass);

		try {
			decodedByte = doCrypto(key, baIv, bai, Cipher.DECRYPT_MODE, strCipherAlg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decodedByte;
	}

	/**
	 * Performs the cryptographic operation on the specified IO Streams. This
	 * function is private and is not meant to be called directly.
	 *
	 * @param key
	 *            The secret key used in the crypto operation.
	 * @param baIv
	 *            The initialization vector used in the crypto operation.
	 * @param in
	 *            InputStream to from which data is read.
	 * @param nMode
	 *            Operation mode; either Cipher.DECRYPT_MODE or
	 *            Cipher.ENCRYPT_MODE
	 * @param strCipherAlg
	 *            The encryption algorithm; this must correspond to something
	 *            that makes sense with the specified secret key
	 * @throws Exception
	 */
	private byte[] doCrypto(SecretKey key, byte[] baIv, InputStream in, int nMode, String strCipherAlg)
			throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Cipher cipher = Cipher.getInstance(strCipherAlg);
		cipher.init(nMode, key, new IvParameterSpec(baIv, 0, cipher.getBlockSize()));

		CipherInputStream cis = new CipherInputStream(in, cipher);

		byte[] baBuff = new byte[1024];
		int nBytesRead = -1;

		while ((nBytesRead = cis.read(baBuff)) != -1) {
			out.write(baBuff, 0, nBytesRead);
		}

		byte[] result = out.toByteArray();
		cis.close();
		in.close();

		out.flush();
		out.close();
		return result;
	}

	/**
	 * Decrypt a byte array with base64 algorithm.
	 * 
	 * @param encodedClass
	 * @return decrypted bytes.
	 */
	private byte[] base64Decrypt(byte[] encodedClass) {
		byte[] decodedByte = null;

		BASE64Decoder decoder = new BASE64Decoder();
		ByteArrayInputStream bai = new ByteArrayInputStream(encodedClass);
		try {
			decodedByte = decoder.decodeBuffer(bai);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return decodedByte;

	}

	/**
	 * Encrypt a file with base64 algorithm.
	 * 
	 * @param classFile
	 * @return encrypted bytes
	 */
	private byte[] base64Encrypt(File classFile) {
		byte[] encoded = null;
		try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(classFile))) {
			// System.out.println("Available bytes from the file
			// :"+inputStream.available());

			byte toBeEncoded[] = new byte[inputStream.available()];
			inputStream.read(toBeEncoded);

			BASE64Encoder base64Encoder = new BASE64Encoder();
			encoded = base64Encoder.encode(toBeEncoded).getBytes();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return encoded;

	}
	
}
