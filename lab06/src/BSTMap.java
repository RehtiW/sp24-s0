import java.rmi.UnexpectedException;
import java.util.*;

public class BSTMap<K extends Comparable<K>,V> implements Map61B<K, V>{
    private int size;
    private Node root;

    private class Node{
        K key;
        V val;
        Node left;
        Node right;
        public Node(K k,V v){
            key = k;
            val = v;
            left = right = null;
        }

    }

    public BSTMap(){
        root = null;
        size = 0;
    }

    @Override
    public void put(K key, V value) {
        root = put(key,value,root);
    }

    private Node put(K key,V value,Node node){
        if(node==null){
            size+=1;
            return new Node(key,value);
        }

        int cmp=key.compareTo(node.key);
        if(cmp<0){
            node.left=put(key,value,node.left);
        }else if(cmp>0){
            node.right=put(key,value,node.right);
        }else{
            node.val=value;
        }

        return node; // 父节点不改变

    }

    @Override
    public V get(K key) {
        V returnVal = get(key,root);
        return returnVal;
    }

    private V get (K key,Node node) { //获得当前子树正确的返回值
        if(node == null){
            return null;
        }
        int cmp=key.compareTo(node.key);
        if(cmp < 0){
            return get(key,node.left);
        }else if(cmp > 0){
            return get(key,node.right);
        }else{
            return node.val;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return containsKet(key,root);
    }

    private boolean containsKet(K key,Node node){
        if(node == null){
            return false;
        }
        int cmp=key.compareTo(node.key);
        if(cmp < 0){
            return containsKet(key,node.left);
        }else if(cmp > 0){
            return containsKet(key,node.right);
        }else{
            return true;
        }
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K>{
        private Stack<Node> stack = new Stack<>();
//利用栈模拟中序遍历递归
        public BSTMapIterator(){
            pushLeft(root);
        }

        private void pushLeft(Node node){
            while(node != null){
                stack.push(node);
                node=node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public K next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            Node node=stack.pop();
            pushLeft(node.right);
            return node.key;
        }
    }
}
