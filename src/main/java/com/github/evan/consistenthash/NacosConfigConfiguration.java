package com.github.evan.consistenthash;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.github.evan.consistenthash.algorithm.ConsistentHashByTreeMap;
import com.github.evan.consistenthash.node.ClusterNode;

/**
 * @Desc: macos配置类
 * @author: liuawei
 * @date: 2021-05-18 12:24
 */
@EnableAutoConfiguration
@Configuration
public class NacosConfigConfiguration {

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Value("${spring.profiles.active:test}")
    private String profileActive;

    @Value("${spring.application.name:consistent-hash}")
    private String applicationName;

    private ConsistentHashByTreeMap<ClusterNode> clusterNodeConsistentHash;

    private Set<ClusterNode> clusterNodeSet;

    private Logger log = LoggerFactory.getLogger(NacosConfigConfiguration.class);

    @PostConstruct
    public void systemInit() {
        initConsistenHash();
        addConfigLinster();
    }

    private void initConsistenHash() {
        // 构造节点
        Set<ClusterNode> nodeSet = new HashSet<>();
        nodeSet.add(new ClusterNode("192.168.0.1", "8081"));
        nodeSet.add(new ClusterNode("192.168.0.1", "8082"));
        nodeSet.add(new ClusterNode("192.168.0.1", "8083"));
        nodeSet.add(new ClusterNode("192.168.0.1", "8084"));
        nodeSet.add(new ClusterNode("192.168.0.2", "8081"));
        nodeSet.add(new ClusterNode("192.168.0.2", "8082"));
        nodeSet.add(new ClusterNode("192.168.0.2", "8083"));
        nodeSet.add(new ClusterNode("192.168.0.2", "8084"));
        clusterNodeConsistentHash = new ConsistentHashByTreeMap<>(nodeSet);
        clusterNodeSet = nodeSet;
    }

    public void addConfigLinster() {
        String dataId = applicationName + "-" + profileActive + ".properties";
        try {
            nacosConfigManager.getConfigService().addListener(dataId, "DEFAULT_GROUP", new Listener() {

                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("receiveConfigInfo:{}", configInfo);
                    Properties properties = new Properties();
                    try {
                        properties.load(new StringReader(configInfo));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String refreshNodeValue = properties.getProperty("refreshNode");
                    ;
                    List<ClusterNode> refreshNodeList =
                            JSONObject.parseArray(refreshNodeValue,ClusterNode.class);

                    // 处理内容
                    Set<ClusterNode> addNodeSet = new HashSet<>();
                    Set<ClusterNode> removeNodeSet = new HashSet<>();
                    for (ClusterNode clusterNode : refreshNodeList) {
                        String refreshNode = clusterNode.getRefreshNode();
                        clusterNode.setRefreshNode(null);
                        if ("addNode".equals(refreshNode)) {
                            // 防止添加重复节点
                            if (clusterNodeSet.contains(clusterNode)) {
                                log.info("add node exisit:{}", clusterNode);
                            } else {
                                addNodeSet.add(clusterNode);
                            }
                        }
                        if ("removeNode".equals(refreshNode)) {
                            // 防止删除没有的节点
                            if (clusterNodeSet.contains(clusterNode)) {
                                removeNodeSet.add(clusterNode);
                            } else {
                                log.info("remove node not exisit:{}", clusterNode);
                            }
                        }
                    }
                    clusterNodeConsistentHash.addNodeCollection(addNodeSet);
                    clusterNodeConsistentHash.removeNodeCollection(removeNodeSet);
                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

}
