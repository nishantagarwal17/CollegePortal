package com.nishant.collegeportal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

 class Encrypt {
     static String digest(String value) {
        MessageDigest digester = null;
        String digest = "";
        try {
            digester = MessageDigest.getInstance("SHA-256");
            byte[] stringBytes = value.getBytes();
            digester.update(stringBytes, 0, stringBytes.length);
            digest = String.format("%064x", new BigInteger(1, digester.digest()));
        } catch (NoSuchAlgorithmException | NullPointerException e) {
            e.printStackTrace();
        }
        return digest;
    }
}
