package com.gome.im.platform.utils;

import com.gome.im.platform.global.Global;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangshikai on 2016/3/30.
 */
public class CuratorClient {
    private static Logger log = LoggerFactory.getLogger(CuratorClient.class);

    private static CuratorFramework zkClient;

    private static ConcurrentSkipListSet watchers = new ConcurrentSkipListSet();

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    private static final String namespace = "";
    private static final String path = Global.ZK_PATH;
    private static final String ip_port = Global.ZK_IP_PORT;

    private static volatile List<String> zkGateKeeperPaths;

    private CuratorClient() {}

    public static List<String> getZkGateKeeperPaths() {
        if (zkGateKeeperPaths == null) {
            getChildrenData();
        }
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    getChildrenData();
                }catch (Exception e){
                    log.info("异步获取zookeeper gatekeeper地址数据错误.error:{}",e.toString());
                }
            }
        });
        return zkGateKeeperPaths;
    }

    public static CuratorFramework init() {
        zkClient = CuratorFrameworkFactory
                .builder()
                .connectString(ip_port)
                        //.namespace(namespace)
                .retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 5000))
                .build();
        zkClient.start();

        //初始化zkPaths
        if(zkClient.getState() == CuratorFrameworkState.STARTED){
            CuratorClient.getZkGateKeeperPaths();
        }
        return zkClient;
    }

    private static synchronized void getChildrenData() {
        try {
            final CuratorWatcher watcher = new CuratorWatcher() {
                @Override
                public void process(WatchedEvent event) throws Exception {
                    log.info("eventType:" + event.getType());
                    if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged) {
                        zkGateKeeperPaths = zkClient.getChildren().forPath(path);
                        log.info("zookpeer gatekeeper地址节点变化,gatekeeper地址节值变化为:{}",zkGateKeeperPaths);
                    }
                }
            };
            zkGateKeeperPaths = zkClient.getChildren().usingWatcher(watcher).forPath(path);
            boolean isContains = true;
            //添加session过期的监控
            if (!watchers.contains(isContains)) {
                zkClient.getConnectionStateListenable().addListener(new ConnectionStateListener() {
                    @Override
                    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                        try {
                            log.info("zookpeer 连接状态变化......");
                            zkGateKeeperPaths = zkClient.getChildren().usingWatcher(watcher).forPath(path);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                watchers.add(isContains);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error:" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            init();
            CuratorClient.getChildrenData();
            System.out.println("result:"+zkGateKeeperPaths.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
