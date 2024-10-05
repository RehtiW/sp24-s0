import java.util.ArrayList;
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
    public T removeFirst() {
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
    public T removeLast() {
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
    public T get(int index) {
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

}
