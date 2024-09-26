import java.util.ArrayList;
import java.util.List;

public class JavaExercises {

    /** Returns an array [1, 2, 3, 4, 5, 6] */
    public static int[] makeDice() {
        // TODO: Fill in this function.
        int []dice={1,2,3,4,5,6};
        return dice;
    }

    /** Returns the order depending on the customer.
     *  If the customer is Ergun, return ["beyti", "pizza", "hamburger", "tea"].
     *  If the customer is Erik, return ["sushi", "pasta", "avocado", "coffee"].
     *  In any other case, return an empty String[] of size 3. */
    public static String[] takeOrder(String customer) {
        // TODO: Fill in this function.
        String []commodity;
        if(customer=="Ergun"){
            commodity=new String[]{"beyti", "pizza", "hamburger", "tea"};
        }else if(customer=="Erik"){
            commodity=new String[]{"sushi", "pasta", "avocado", "coffee"};
        }else{
            commodity=new String[3];
        }
        return commodity;
    }

    /** Returns the positive difference between the maximum element and minimum element of the given array.
     *  Assumes array is nonempty. */
    public static int findMinMax(int[] array) {
        // TODO: Fill in this function.
        int maxElement,minElement;
        maxElement=minElement=array[0];
        for(int i=1;i< array.length;i++){
            if(maxElement<array[i]){
                maxElement=array[i];
            }
            if(minElement>array[i]){
                minElement=array[i];
            }
        }
        return maxElement-minElement;

    }

    /**
      * Uses recursion to compute the hailstone sequence as a list of integers starting from an input number n.
      * Hailstone sequence is described as:
      *    - Pick a positive integer n as the start
      *        - If n is even, divide n by 2
      *        - If n is odd, multiply n by 3 and add 1
      *    - Continue this process until n is 1
      */
    public static List<Integer> hailstone(int n) {
        List<Integer> list=new ArrayList<>();
        list.add(n);
        return hailstoneHelper(n,list);
    }

    private static List<Integer> hailstoneHelper(int x, List<Integer> list) {
        // TODO: Fill in this function.

        if(x==1){
            return list;
        }else if(x%2!=0){
            list.add(x*3+1);
            return hailstoneHelper(x*3+1,list);
        }else if(x%2==0){
            list.add(x/2);
            return hailstoneHelper(x/2,list);
        }
        return list;
    }

}
