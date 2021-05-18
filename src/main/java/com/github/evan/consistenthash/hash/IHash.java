package com.github.evan.consistenthash.hash;

/**
 * @Desc: hash 算法接扣
 * @author: liuawei
 * @date: 2021-05-18 11:50
 */
public interface IHash {

    /**
     * hash算法
     * @param data
     * @return
     */
    int hash(String data);
}
