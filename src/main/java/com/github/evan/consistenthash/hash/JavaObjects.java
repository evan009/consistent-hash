package com.github.evan.consistenthash.hash;

import java.util.Objects;

/**
 * @Desc: java.util.Objects#hash(java.lang.Object...)
 * @author: liuawei
 * @date: 2021-05-18 14:00
 */
public class JavaObjects implements IHash{

    @Override
    public int hash(String data) {
        return Objects.hash(data);
    }
}
