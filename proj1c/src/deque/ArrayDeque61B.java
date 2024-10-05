package deque;

import org.apache.commons.collections.iterators.ArrayIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B <T> implements Deque61B<T>{
    private T [] items;
    private int back;
    private int front;
    private int size;

    public ArrayDeque61B() {
        items = (T[]) (new Object[8]);
        front = 0;
        back = 0;
        size = 0;
    }
    // 环形数组下标处理，确保数组循环使用
    private int nextIndex(int index) {
        return (index + 1) % items.length;
    }

    private int prevIndex(int index) {
        return (index - 1 + items.length) % items.length;
    }

    // 数组扩容，当数组满时进行扩容
    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[(front + i) % items.length];  // 正确处理环形索引
        }
        front = 0;
        back = size;
        items = newItems;
    }

    @Override
    public void addFirst(T x) {
        if(size == items.length){
            resize(size*2);
        }
        front=prevIndex(front);
        items[front]=x;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);  // 扩容为原来的两倍
        }
        items[back] = x;
        back = nextIndex(back);  // 先插入，再更新back
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {      //constant
        if (isEmpty()) {
            return null;
        }
        T removeItem=items[front];
        items[front]=null;
        front=nextIndex(front);
        size--;
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);  // 缩小数组
        }
        return removeItem;
    }

    @Override
    public T removeLast() {       //constant
        if (isEmpty()) {
            return null;
        }
        back=prevIndex(back);
        T removedItem=items[back];
        items[back]=null;
        size--;
        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);  // 缩小数组
        }
        return removedItem;
    }

    @Override
    public T get(int index) {     //constant
        if (index < 0 || index >= size) {
            return null;
        }
        return items[(front+index) % items.length];  // 环形数组获取元素
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for(int i=0 ; i<size ; i++){
            returnList.add(items[(front+i)% items.length]);
        }
        return returnList;
    }

    private class ArrayDequeIterator<T> implements Iterator<T>{
        private int pos;

        public ArrayDequeIterator(){
            pos=0;
        }
        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public T next() {
            T returnItem= (T) items[front+pos];
            pos+=1;
            return returnItem;
        }
    }
    @Override
    public Iterator<T> iterator() {  //获取迭代器
        return new ArrayDequeIterator<>();
    }

    @Override
    public boolean equals(Object other) {
        if(this==other){
            return true;
        }
        if (other instanceof ArrayDeque61B<?> otherList) {
            if (this.size != otherList.size()) {
                return false;
            }
            int i = 0;
            for (T item : this) {
                if (!item.equals(otherList.get(i))) {
                    return false;
                }
                i += 1;
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        Iterator<T> iterator = this.iterator(); // 获取迭代器

        if (iterator.hasNext()) {
            sb.append(iterator.next()); // 添加第一个元素
        }

        while (iterator.hasNext()) {
            sb.append(", ").append(iterator.next()); // 添加后续元素，前面加逗号
        }

        sb.append("]"); // 关闭括号
        return sb.toString(); // 返回生成的字符串
    }


}

