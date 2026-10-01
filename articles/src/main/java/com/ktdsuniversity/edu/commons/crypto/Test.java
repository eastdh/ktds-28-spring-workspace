package com.ktdsuniversity.edu.commons.crypto;

import com.ktdsuniversity.edu.commons.crypto.encrypt.hash.SHA;

public class Test {

  private static final String AES_SECRET_KEY = "abcde12345abcde12345abcde12345zz"; // 32 글자

  public static void testSHA() {
    String rawPassword = "Password123!@#";

    // SHA를 이용한 이중 암호화
    // 1. 이중 암호화를 위한 SALT 발급
    String salt = SHA.generateSalt();

    System.out.println(salt);

    // 2. rawPassword와 SALT를 이용한 암호화
    String encryptedPassword = SHA.getEncrypt(rawPassword, salt);
    System.out.println(encryptedPassword);
  }

  public static void testAESEnc() {
    // AES 암호화
    String name = "유동혁";
    String encryptedName = AES.encode(AES_SECRET_KEY, name);
    System.out.println(encryptedName);
  }

  public static void testAESDec() {
    // AES 복호화
    String encryptedName = "f019f55335bf98ed062e051362987cda"; // 암호화 결과
    String rawName = AES.decode(AES_SECRET_KEY, encryptedName);
    System.out.println(rawName);
  }

  public static void main(String[] args) {
    testSHA();
    testAESEnc();
    testAESDec();
  }
}
