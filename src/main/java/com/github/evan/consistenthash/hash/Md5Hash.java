package com.github.evan.consistenthash.hash;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Desc: md5 hash 算法 常见 效率低
 * @author: evan
 * @date: 2021-05-18 10:48
 */
public class Md5Hash implements IHash {

    @Override
    public int hash(String data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] messageDigest = md.digest(data.getBytes(Charset.defaultCharset()));
        BigInteger no = new BigInteger(messageDigest);
        return no.intValue();
    }

}
