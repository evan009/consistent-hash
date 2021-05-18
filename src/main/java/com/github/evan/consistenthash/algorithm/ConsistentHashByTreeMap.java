package com.github.evan.consistenthash.algorithm;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.github.evan.consistenthash.hash.IHash;

/**
 * @Desc: 一致性hash算法通过TreeMap实现
 * @author: liuawei
 * @date: 2021-05-18 11:28
 */
public class ConsistentHashByTreeMap<T> {

    /**
     * 节点的副本
     */
    private Integer nodeReplicas;
    /**
     * 虚拟节点环
     */
    private SortedMap<Integer, T> virtualCircle = new TreeMap<>();

    /**
     * hash算法实现
     */
    private IHash hash;

    /**
     * 构建虚拟节点环
     * @param nodes
     */
    public ConsistentHashByTreeMap(Collection<T> nodes) {
        // 默认的节点副本16
        this.nodeReplicas = 16;
        this.hash = new com.github.evan.consistenthash.hash.MurmurHash3();
        for (T node : nodes) {
            addNode(node);
        }
    }

    public ConsistentHashByTreeMap(Integer nodeReplicas, Collection<T> nodes, IHash hash) {
        this.nodeReplicas = nodeReplicas;
        this.hash = hash;
        for (T node : nodes) {
            addNode(node);
        }
    }

    /**
     * 新增节点
     * 循环遍历新增虚拟节点
     * @param node
     */
    public void addNode(T node) {
        for (int i = 0; i < nodeReplicas; i++) {
            virtualCircle.put(hash.hash(node.toString() + i), node);
        }
    }

    /**
     * 移除节点
     * 循环遍历移除虚拟节点
     * @param node
     */
    public void removeNode(T node) {
        for (int i = 0; i < nodeReplicas; i++) {
            virtualCircle.remove(hash.hash(node.toString() + i), node);
        }
    }


    /**
     * 新增节点列表
     * @param nodeList
     */
    public void addNodeCollection(Collection<T> nodeList) {
        for (T t : nodeList) {
            addNode(t);
        }
    }

    /**
     * 移除节点列表
     * @param nodeList
     */
    public void removeNodeCollection(Collection<T> nodeList) {
        for (T t : nodeList) {
            removeNode(t);
        }
    }

    /**
     * 获取节点
     * @param key
     * @return
     */
    public T getKey(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        int hashKey = hash.hash(key);
        if (!virtualCircle.containsKey(hashKey)) {
            // 获取距离这个hash最近的节点  移除节点取附近节点
            SortedMap<Integer, T> tailMap = virtualCircle.tailMap(hashKey);
            hashKey = tailMap.isEmpty() ? virtualCircle.firstKey() : tailMap.firstKey();
        }
        return virtualCircle.get(hashKey);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, T> entry : virtualCircle.entrySet()) {
            sb.append(entry + "\n");
        }
        return sb.toString();
    }

}
