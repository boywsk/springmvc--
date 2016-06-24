package cn.com.gome.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wangshikai on 2015/12/22.
 */
public class LRUCache<K,V> {
    private Logger log = LoggerFactory.getLogger(LRUCache.class);
    private LinkedHashMap<K,V> hashMap;
    private int MAX_CACHE_SIZE;
    private final float DEFAULT_LOAD_FACTOR = 0.75f;
    private final ReentrantLock lock = new ReentrantLock();

    public LRUCache(int cacheSize){
        this.MAX_CACHE_SIZE = cacheSize;
        //根据cacheSize和加载因子计算hashmap的capactiy，+1确保当达到cacheSize上限时不会触发hashmap的扩容
        int capacity = (int) Math.ceil(MAX_CACHE_SIZE / DEFAULT_LOAD_FACTOR) + 1;
        hashMap = new LinkedHashMap<K,V>(capacity,DEFAULT_LOAD_FACTOR,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };
    }

    public void put(K key, V value) {
        try{
            this.lock.lock();
            this.hashMap.put(key, value);
        }catch (Exception e){
            log.error("LRUCache put error:"+e);
        }finally {
            this.lock.unlock();
        }
    }

    public V get(K key) {
        V value = null;
        try{
            this.lock.lock();
            value = this.hashMap.get(key);
        }catch (Exception e){
            log.error("LRUCache get error:"+e);
        }finally {
            this.lock.unlock();
        }
        return value;
    }

    public void remove(K key) {
        try{
            this.lock.lock();
            this.hashMap.remove(key);
        }catch (Exception e){
            log.error("LRUCache remove error:" + e);
        }finally {
            this.lock.unlock();
        }
    }

    public int size() {
        int size = 0;
        try{
            this.lock.lock();
            size = this.hashMap.size();
        }catch (Exception e){
            log.error("LRUCache size error:"+e);
        }finally {
            this.lock.unlock();
        }
        return size;
    }

    public void clear() {
        try{
            this.lock.lock();
            this.hashMap.clear();
        }catch (Exception e){
            log.error("LRUCache clear error:" + e);
        }finally {
            this.lock.unlock();
        }
    }

    public Set<Map.Entry<K, V>> getAll() {
        Set<Map.Entry<K,V>> set = null;
        try{
            this.lock.lock();
            set = this.hashMap.entrySet();
        }catch (Exception e){
            log.error("LRUCache getAll error:" + e);
        }finally {
            this.lock.unlock();
        }
        return set;
    }

    @Override
    public String toString() {
        String str = null;
        try{
            this.lock.lock();
            StringBuilder sb = new StringBuilder();
            for (Map.Entry entry : this.getAll()) {
                sb.append(String.format("%s:%s ", entry.getKey(), entry.getValue()));
            }
            str = sb.toString();
        }catch (Exception e){
            log.error("LRUCache toString error:" + e);
        }finally {
            this.lock.unlock();
        }
        return str;
    }

    public static void main(String[] args) {
        final LRUCache<String,Integer> map = new LRUCache<>(5);
        map.put("1",1);
        map.put("2", 2);
        map.put("3", 3);
        map.put("4", 4);
        map.put("5", 5);
        System.out.println(map.toString());
        map.put("6", 6);
        System.out.println(map.toString());
        map.put("7", 7);
        System.out.println(map.toString());
        map.put("8", 8);
        System.out.println(map.toString());
    }

}
