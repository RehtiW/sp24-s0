import deque.ArrayDeque61B;
import edu.princeton.cs.algs4.StdAudio;
import gh2.GuitarString;
import org.junit.jupiter.api.Test;
import deque.Deque61B;
import deque.LinkedListDeque61B;

import static com.google.common.truth.Truth.assertThat;

public class SimpleTest {
    @Test
    public void testEquals() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }

}
