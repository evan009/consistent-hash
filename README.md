## 项目说明

Java实现一致性Hash，通过nacos配置中心动态刷新节点。


### 1 Hash算法

com.github.evan.consistenthash.hash实现了多种hash算法。

- MD5
- MurmurHash3
- JavaObjects

### 2 一致性Hash算法

com.github.evan.consistenthash.algorithm实现了一致性hash的算法

#### 2.1 ConsistentHashByTreeMap 

基于TreeMap 生成虚拟节点


利于java.util.SortedMap.tailMap获取距离最近key的Map对象。

### 3 Nacos属性监听

com.github.evan.consistenthash.NacosConfigConfiguration添加Nacos 配置中心监听，当属性配置文件变化的时候，解析数据，处理数据。

```Json
eg：
DATA-ID consistent-hash-test.properties
refreshNode=[{"ip": "192.168.0.7","port": "8081","refreshNode": "addNode"},{ "ip": "192.168.0.2","port": "8081","refreshNode": "removeNode"}]
```

### 4 测试

测试类 com.github.evan.consistenthash.algorithm.ConsistentHashByTreeMapTest


