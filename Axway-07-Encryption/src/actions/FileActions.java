package actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Properties;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class FileActions {

	private static final String AES_ALGORITHM = "AES";
	private static final String RSA_ALGORITHM = "RSA";
	private static final String RSA_SHA1_PADDING = "RSA/ECB/OAEPWithSHA1AndMGF1Padding";
	private static final String UTF_8 = "UTF-8";

	FileInputStream input = null;

	private Scanner scan = new Scanner(System.in);
	private String key = generateRandomKey();

	public static String padTextToBeMultipleTo16(String text) {
		int textSize = text.getBytes().length;
		int leftover = textSize % 16;
		if (leftover > 0) {
			for (int i = 0; i < 16 - leftover; i++) {
				text = text + " ";
			}
		}
		return text;
	}

	public void fileEncrypt() {

		System.out.println("Enter the name of the file you want to encrypt: ");
		String fileName = scan.nextLine();
		File inputFile = null;
		while (true) {
			inputFile = new File(fileName);
			if (!inputFile.exists()) {
				System.out.println("There is no file with name " + fileName);
				System.out.println("Please enter a file name again.");
				fileName = scan.nextLine();
			} else {
				break;
			}
		}
			try {
				input = new FileInputStream(inputFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("Enter the name of the file to make it a container for the encrypted content.");
			String nameOfFile = scan.nextLine();
			File outputFile = new File(nameOfFile);

			doCrypto(inputFile, outputFile, nameOfFile, Cipher.ENCRYPT_MODE);
			encryptKey(key);
			String fileStoring = nameOfFile + "StoreKeyFilePair";
			storeKeyFilePair(nameOfFile, key, fileStoring);
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					System.out.println("Input stream cannot be closed.");
					e.printStackTrace();
				}
			}

		}

	public void fileDecrypt() {

		System.out.println("Enter the name of the file you want to decrypt: ");
		String fileName = scan.nextLine();
		while (true) {
			File inputFile = new File(fileName);
			if (!inputFile.exists()) {
				System.out.println("There is no file with name " + fileName);
				System.out.println("Please enter a file name again.");
				fileName = scan.nextLine();
			} else {
				System.out.println("Enter the name of the file to make it a container for the decrypt content.");
				String fileDecryptedName = scan.nextLine();
				File outputFile = new File(fileDecryptedName);
				String fileStoring = fileName + "StoreKeyFilePair";
				String keyToDecrypt = getKeyToDecrypt(fileName, key, fileStoring);

				doCrypto(inputFile, outputFile, keyToDecrypt, Cipher.DECRYPT_MODE);
			}
		}
	}

	public void doCrypto(File inputFile, File outputFile, String key, int cipherMode) {

		try {
			key = padTextToBeMultipleTo16(key);
			Key secretKey = new SecretKeySpec(key.getBytes(), AES_ALGORITHM);
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
			cipher.init(cipherMode, secretKey);

			FileInputStream input = new FileInputStream(inputFile);
			byte[] inputBytes = new byte[(int) inputFile.length()];
			input.read(inputBytes);

			byte[] outputBytes = cipher.doFinal(inputBytes);

			FileOutputStream output = new FileOutputStream(outputFile);
			output.write(outputBytes);

			input.close();
			output.close();

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		} catch (NoSuchPaddingException e) {

			e.printStackTrace();
		} catch (InvalidKeyException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {

			e.printStackTrace();
		} catch (BadPaddingException e) {
			System.out.println();
			e.printStackTrace();
		}
	}

	public byte[] encryptKey(String key, PublicKey encryptionKey) {
		Cipher cipher;
		byte[] encryptedKey = null;
		try {
			cipher = Cipher.getInstance(RSA_SHA1_PADDING);
			cipher.init(Cipher.ENCRYPT_MODE, encryptionKey);
			encryptedKey = cipher.doFinal(key.getBytes(UTF_8));
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		} catch (NoSuchPaddingException e) {

			e.printStackTrace();
		} catch (InvalidKeyException e) {

			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {

			e.printStackTrace();
		} catch (BadPaddingException e) {

			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		return encryptedKey;
	}

	public String decryptKey(byte[] encryptedContent, PrivateKey encryptionKey) {
		Cipher cipher;
		String decryptedKey = null;
		try {
			cipher = Cipher.getInstance(RSA_SHA1_PADDING);
			cipher.init(Cipher.DECRYPT_MODE, encryptionKey);
			decryptedKey = new String(cipher.doFinal(encryptedContent), UTF_8);
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		} catch (NoSuchPaddingException e) {

			e.printStackTrace();
		} catch (InvalidKeyException e) {

			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {

			e.printStackTrace();
		} catch (BadPaddingException e) {

			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

		return decryptedKey;
	}

	public void encryptKey(String key) {

		KeyPairGenerator keyGen = null;
		try {
			keyGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		KeyPair keyPair = keyGen.generateKeyPair();

		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();

		byte[] encryptedText = encryptKey(key, publicKey);
		System.out.println("Encrypted key - " + new String(encryptedText));

		Encoder encoder = Base64.getEncoder();
		System.out.println("Base64 encoded encrypted key for files - " + encoder.encodeToString(encryptedText));

		String decryptedString = decryptKey(encryptedText, privateKey);
		System.out.println("Decrypted key - " + decryptedString.trim());

	}

	public String generateRandomKey() {
		KeyGenerator keyGen = null;
		try {
			keyGen = KeyGenerator.getInstance(AES_ALGORITHM);

		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid algorithm.");
			e.printStackTrace();
		}
		keyGen.init(128, new SecureRandom());
		SecretKey key = keyGen.generateKey();
		String encKey = Base64.getEncoder().encodeToString(key.getEncoded());
		return encKey;

	}

	public String getKeyToDecrypt(String fileName, String theKey, String fileStoring) {
		Properties prop = new Properties();
		InputStream input;
		String key = null;

		try {
			input = new FileInputStream(fileName);
			prop.load(input);

			key = prop.getProperty(fileName);

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return key;
	}

	public void storeKeyFilePair(String fileName, String key, String fileStoring) {

		OutputStream output = null;
		File file = new File(fileName);
		try {
			if (!file.exists()) {

				file = new File(fileName);

				Properties properties = new Properties();
				properties.setProperty(fileName, key);
				output = new FileOutputStream(fileStoring);

				properties.store(output, null);
				if (output != null) {
					output.close();
				}
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
