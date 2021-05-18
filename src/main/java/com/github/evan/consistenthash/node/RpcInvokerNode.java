package com.github.evan.consistenthash.node;

import java.util.Collection;
import java.util.Objects;

/**
 * @Desc: 远程调用服务接口
 * @author: evan
 * @date: 2021-05-18 11:21
 */
public class RpcInvokerNode {

    private String uri;

    private String protocal;

    private Collection<String> paramList;

    public RpcInvokerNode(String uri, String protocal) {
        this.uri = uri;
        this.protocal = protocal;
    }

    public RpcInvokerNode(String uri, String protocal, Collection<String> paramList) {
        this.uri = uri;
        this.protocal = protocal;
        this.paramList = paramList;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getProtocal() {
        return protocal;
    }

    public void setProtocal(String protocal) {
        this.protocal = protocal;
    }

    public Collection<String> getParamList() {
        return paramList;
    }

    public void setParamList(Collection<String> paramList) {
        this.paramList = paramList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RpcInvokerNode that = (RpcInvokerNode) o;
        return Objects.equals(uri, that.uri) &&
                Objects.equals(protocal, that.protocal) &&
                Objects.equals(paramList, that.paramList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, protocal, paramList);
    }

    @Override
    public String toString() {
        StringBuilder paramStr = new StringBuilder();
        for (String s : paramList) {
            paramStr.append(s);
        }
        return uri + protocal + paramStr;
    }
}
