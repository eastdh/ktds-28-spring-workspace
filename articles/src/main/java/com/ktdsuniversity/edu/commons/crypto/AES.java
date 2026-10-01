package com.ktdsuniversity.edu.commons.crypto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class AES {

  /**
   * 원본을 암호화 함
   * 
   * @param key 암호화 키 (32byte 길이의 텍스트)
   * @param plainText 원본
   * @return 암호화된 결과
   */
  public static String encode(String key, String plainText) {
    if (plainText == null) {
      return null;
    }
    SecretKeySpec secretKey = null;
    try {
      secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e.getMessage(), e);
    }

    IvParameterSpec IV = new IvParameterSpec(key.substring(0, 16).getBytes());

    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      throw new RuntimeException(e.getMessage(), e);
    }

    try {
      cipher.init(Cipher.ENCRYPT_MODE, secretKey, IV);
    } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
      throw new RuntimeException(e.getMessage(), e);
    }

    byte[] encryptionByte = null;
    try {
      encryptionByte = cipher.doFinal(plainText.getBytes("UTF-8"));
    } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
      throw new RuntimeException(e.getMessage(), e);
    }

    return Hex.encodeHexString(encryptionByte);
  }

  /**
   * 암호화된 텍스트를 복호화 함
   * 
   * @param key 복호화 키 (32byte 길이의 텍스트)
   * @param encodedText 암호화된 텍스트
   * @return 복호화된 결과
   */
  public static String decode(String key, String encodedText) {
    if (encodedText == null) {
      return null;
    }
    SecretKeySpec secretKey = null;
    try {
      secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e.getMessage(), e);
    }

    IvParameterSpec IV = new IvParameterSpec(key.substring(0, 16).getBytes());

    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      throw new RuntimeException(e.getMessage(), e);
    }

    try {
      cipher.init(Cipher.DECRYPT_MODE, secretKey, IV);
    } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
      throw new RuntimeException(e.getMessage(), e);
    }

    byte[] decodeByte = null;
    try {
      decodeByte = Hex.decodeHex(encodedText.toCharArray());
    } catch (DecoderException e) {
      throw new RuntimeException(e.getMessage(), e);
    }

    try {
      return new String(cipher.doFinal(decodeByte), "UTF-8");
    } catch (UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
