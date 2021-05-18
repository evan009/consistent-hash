package com.github.evan.consistenthash.node;

import java.util.Objects;

/**
 * @Desc: 集群节点
 * @author: liuawei
 * @date: 2021-05-18 11:15
 */
public class ClusterNode {

    private String clusterName;

    private String ip;

    private String port;

    private String hostName;

    /**
     * addNode
     * removeNode
     * 固定值
     */
    private String refreshNode;

    public ClusterNode(){

    }


    public ClusterNode(String ip, String port) {
        this.clusterName = "default";
        this.ip = ip;
        this.port = port;
    }

    public ClusterNode(String clusterName, String ip, String hostName, String port) {
        this.clusterName = clusterName;
        this.ip = ip;
        this.hostName = hostName;
        this.port = port;
    }


    public ClusterNode(String clusterName, String ip, String port, String hostName, String refreshNode) {
        this.clusterName = clusterName;
        this.ip = ip;
        this.port = port;
        this.hostName = hostName;
        this.refreshNode = refreshNode;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getRefreshNode() {
        return refreshNode;
    }

    public void setRefreshNode(String refreshNode) {
        this.refreshNode = refreshNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClusterNode that = (ClusterNode) o;
        return Objects.equals(clusterName, that.clusterName) &&
                Objects.equals(ip, that.ip) &&
                Objects.equals(port, that.port) &&
                Objects.equals(hostName, that.hostName) &&
                Objects.equals(refreshNode, that.refreshNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clusterName, ip, port, hostName, refreshNode);
    }

    @Override
    public String toString() {
        return "ClusterNode{" +
                "clusterName='" + clusterName + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", hostName='" + hostName + '\'' +
                '}';
    }
}
