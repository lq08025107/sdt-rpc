package com.sdt.rpc.registry.zookeeper;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

public class ZookeeperAPI implements Watcher {
    private static CountDownLatch connect = new CountDownLatch(1);

    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws Exception{
        String path = "/zk-book";
        zooKeeper = new ZooKeeper("localhost:2181", 5000, new ZookeeperAPI());
        connect.await();

        zooKeeper.exists(path, true);

        zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        zooKeeper.setData(path, "123".getBytes(), -1);

        zooKeeper.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        zooKeeper.delete(path + "/c1", -1);

        zooKeeper.delete(path, -1);

        Thread.sleep(Integer.MAX_VALUE);
    }
    @Override
    public void process(WatchedEvent watchedEvent) {
        try{
            if (Event.KeeperState.SyncConnected == watchedEvent.getState()){
                if(Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()){
                    connect.countDown();
                } else if(Event.EventType.NodeCreated == watchedEvent.getType()){
                    System.out.println("Node " + watchedEvent.getPath() + " created");
                    zooKeeper.exists(watchedEvent.getPath(), true);
                } else if(Event.EventType.NodeDeleted == watchedEvent.getType()){
                    System.out.println("Node " + watchedEvent.getPath() + " deleted");
                    zooKeeper.exists(watchedEvent.getPath(), true);
                } else if(Event.EventType.NodeDataChanged == watchedEvent.getType()){
                    System.out.println("Node " + watchedEvent.getPath() + " data changed");
                    zooKeeper.exists(watchedEvent.getPath(), true);
                }
            }
        } catch(Exception e){}
    }
}
