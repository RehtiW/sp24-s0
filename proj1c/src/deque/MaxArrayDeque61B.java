package deque;
import java.util.Comparator;

public class MaxArrayDeque61B <T> extends ArrayDeque61B <T>{
    private Comparator<T> c;
    public MaxArrayDeque61B(Comparator<T> c){
        this.c=c;
    }


    public T max(){
        if(isEmpty()){
            return null;
        }
        int compResult=0;
        int maxIndex=0;
        for(int i=0;i<size()-1;i++){
            compResult=c.compare(this.get(i+1),this.get(maxIndex));
            if(compResult>0){
                maxIndex = i + 1;
            }
        }
        return get(maxIndex);
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        int compareNum = 0;
        int maxIndex = 0;
        for (int i = 0; i < size() - 1; i++) {
            compareNum = c.compare(this.get(i + 1),this.get(maxIndex));
            if (compareNum > 0) {
                maxIndex = i + 1;
            }
        }
        return this.get(maxIndex);
    }

    public static void main(String[] args) {
        MaxArrayDeque61B<Integer> L = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        L.addLast(1);
        L.addLast(99);
        L.addLast(21);
        L.addLast(51);
        System.out.println(L.max());
        AlphabeticalComparator ac = new AlphabeticalComparator();
        MaxArrayDeque61B<String> L2 = new MaxArrayDeque61B<String>(ac);
        L2.addLast("act");
        L2.addLast("zelot");
        L2.addLast("fury");
        L2.addLast("breezeeeee");
        L2.addLast("zebrb");
        System.out.println(L2.max());
    }
}
