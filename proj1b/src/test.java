import java.util.List;
public class test {
    public static void main(String[] args) {
        Deque61B<Integer>tests=new ArrayDeque61B<>();
        tests.addFirst(1); // [1]
        tests.addFirst(5); // [5, 1]
        tests.addLast(3); // [5, 1, 3]
        tests.addLast(9); // [5, 1, 3, 9]
        //test.removeFirst();
        List <Integer> show =tests.toList();
        int last= tests.removeLast();
        System.out.print(last);



    }
}
