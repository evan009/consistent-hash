package com.github.evan.consistenthash.algorithm;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.evan.consistenthash.node.ClusterNode;

class ConsistentHashByTreeMapTest {

    public static void main(String[] args) {
        // 构造节点
        Set<ClusterNode> nodeSet = new HashSet<>();
        nodeSet.add(new ClusterNode("192.168.0.1","8081"));
        nodeSet.add(new ClusterNode("192.168.0.1","8082"));
        nodeSet.add(new ClusterNode("192.168.0.1","8083"));
        nodeSet.add(new ClusterNode("192.168.0.1","8084"));
        nodeSet.add(new ClusterNode("192.168.0.2","8081"));
        nodeSet.add(new ClusterNode("192.168.0.2","8082"));
        nodeSet.add(new ClusterNode("192.168.0.2","8083"));
        nodeSet.add(new ClusterNode("192.168.0.2","8084"));
        // 构建虚拟环
        ConsistentHashByTreeMap<ClusterNode> consistentHashByTreeMap = new ConsistentHashByTreeMap<>(nodeSet);
        // 构建随机数
        Set<String > dataSet = Stream.of("92c3a2f3-8a52-43f4-ab58-8744d5111bb5",
                "cc3de1de-2f3c-40f8-b3c8-1dd1f2e3c021",
                "02679a4d-1e4f-4943-af80-4365159ec270",
                "ee7a152c-48c0-4e2e-94e1-904635e5792a",
                "1f116a5c-76bb-4795-978e-ee33f9c29560",
                "8c9951f1-6cea-4175-ba14-86b3ec14ee3e",
                "e40418da-3deb-41ca-8cb3-6a53c2d009d0",
                "66b68e07-3100-40f3-8441-43b333956f82",
                "0afedc7d-2610-4f7d-86e4-3daa5ec79e12",
                "4f3eae1a-9204-4b12-aa16-a41028751a93",
                "5a50fdcd-b41d-4527-84c0-a53080e8ef7e",
                "bbc5d591-39bc-417e-a572-15362a231557",
                "f405637c-c2dd-455f-89fb-32f118fcb166",
                "d9d3686a-3a09-44c8-9e39-ce137000e972",
                "5d106b6e-2383-45d4-8c52-db137a710484",
                "5dd3b820-b9c2-4703-a3d9-27f21209c3b1",
                "df93d1ef-ee28-4f83-9773-756f8863db6d",
                "18d21bfd-def5-447e-987b-1c32cb4ff601",
                "08072f7a-05e7-4c38-9b0a-0143d646e224",
                "f7ec22d5-133e-4f84-9886-ee7a5f1ff60f",
                "49730e09-c76d-4b5d-934d-9739cdaf9ada",
                "b3715e1e-d044-4692-ab91-9b20e11bf70f",
                "3c455529-91d4-4b17-9e3d-686f636b40cd",
                "29d46b6f-5006-4b4f-95f1-fb244d684a59",
                "722513fb-849c-4f00-9dbc-8c13f95580f6",
                "387341b2-ccc0-4924-88e9-8c64e8602e4b",
                "6c1333aa-0642-4efb-ad66-1205bb1011b7",
                "422ba2e9-9d5c-4d6c-bcab-2df3a9925d83",
                "17ce2500-da3a-4bf3-a423-9222fe20dff5",
                "8bd1addd-9907-4140-987b-43e95bcc738a").collect(Collectors.toSet());

        for (String s : dataSet) {
            System.out.println(consistentHashByTreeMap.getKey(s));
        }
        System.out.println("增加节点");
        consistentHashByTreeMap.addNode(new ClusterNode("192.168.0.3","8081"));
        consistentHashByTreeMap.addNode(new ClusterNode("192.168.0.3","8082"));
        for (String s : dataSet) {
            System.out.println(consistentHashByTreeMap.getKey(s));
        }
        System.out.println("减少节点");
        consistentHashByTreeMap.removeNode(new ClusterNode("192.168.0.3","8081"));
        for (String s : dataSet) {
            System.out.println(consistentHashByTreeMap.getKey(s));
        }
    }
}