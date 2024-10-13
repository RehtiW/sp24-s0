package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;
    private Double loadFactor;
    private int capacity;
    private int size;
    private Set<K> set;

    /** Constructors */
    public MyHashMap() {
        this(DEFAULT_CAPACITY);
    }

    public MyHashMap(int initialCapacity) {
        this(initialCapacity,LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        buckets=new Collection[initialCapacity];
        for(int i=0;i<initialCapacity;i++){
            buckets[i]=createBucket();
        }
        this.loadFactor=loadFactor;
        capacity=initialCapacity;
        set = new HashSet<>();
        size=0;
    }

    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    private int getIndex(K key){
        return Math.floorMod(key.hashCode(),capacity);
    }

    private void resize(){
        int newCapacity=capacity*2;
        Collection<Node>[]newBuckets=new Collection[newCapacity];
        for(int i=0;i<newCapacity;i++){
            newBuckets[i]=createBucket();
        }
        //rehash
        for(Collection<Node> whichBucket:buckets){
            for(Node node:whichBucket){
                int index=Math.floorMod(node.key.hashCode(),newCapacity);
                newBuckets[index].add(new Node(node.key,node.value));
            }
        }
        capacity=newCapacity;
        buckets=newBuckets;
    }
    @Override
    public void put(K key, V value) {
        if(key==null){
            throw new IllegalArgumentException("Null keys are not allowed");
        }
        int index=getIndex(key);
        Collection<Node>whichBucket=buckets[index];
        for(Node node:whichBucket){
            if(node.key.equals(key)){
                node.value=value;
                return;
            }
        }
        whichBucket.add(new Node(key,value));
        set.add(key);
        size++;
        if (size > loadFactor * capacity) {
            resize();
        }

    }

    @Override
    public V get(K key) {
        if(key == null){
            throw new IllegalArgumentException("key can not be null");
        }
        int index=getIndex(key);
        Collection<Node>whichBucket=buckets[index];
        for(Node node:whichBucket){
            if(node.key.equals(key)){
                return node.value;
            }
        }
        return null;

    }

    @Override
    public boolean containsKey(K key) {
        if(key == null){
            throw new IllegalArgumentException("key can not be null");
        }
        int index=getIndex(key);
        Collection<Node>whichBucket=buckets[index];
        for(Node node:whichBucket){
            if(node.key.equals(key)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for(Collection<Node> bucket:buckets){
            bucket.clear();
        }
        set.clear();
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        return set;
    }

    @Override
    public V remove(K key) {
        if(key == null){
            throw new IllegalArgumentException("key cannot be null");
        }
        int index=getIndex(key);
        Collection<Node>whichBucket=buckets[index];
        Iterator<Node>iterator=whichBucket.iterator();
        while(iterator.hasNext()){
            Node node=iterator.next();
            if(node.key.equals(key)){
                V returnVal=node.value;
                iterator.remove();
                set.remove(key);
                size -- ;
                return returnVal;
            }
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

}
