package com.github.evan.consistenthash.hash;

/**
 * @Desc: MurmurHash 算法 离散
 * @author: evan
 * @date: 2021-05-18 10:45
 */
public class MurmurHash3 implements IHash {

    @Override
    public int hash(String data) {
        return org.apache.commons.codec.digest.MurmurHash3.hash32(data);
    }

}
