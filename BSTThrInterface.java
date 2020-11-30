import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.StringTokenizer;

//BY KENNETH TANG AND NATHAN TRAN

public class BSTThrInterface {
    public static void main(String args[]) {

        BSTThr<String, Long> st = new BSTThr<>();

        In in = new In(args[0]);
        while (in.hasNextLine()) {
            String line = in.readLine();
            StringTokenizer sToken = new StringTokenizer(line);
            String key = sToken.nextToken();
            long val = Long.parseLong(sToken.nextToken());
            st.put(key, val);
        }

        Stopwatch time1 = new Stopwatch();
        Iterable pairs1 = st.pairs("einstein", "eisenhower");
        double dtime1 = time1.elapsedTime();

        Stopwatch time2 = new Stopwatch();
        Iterable pairs2 = st.keys("einstein", "eisenhower");
        double dtime2 = time2.elapsedTime();

        Stopwatch time3 = new Stopwatch();
        Iterable pairs3 = st.pairs();
        double dtime3 = time3.elapsedTime();

        Stopwatch time4 = new Stopwatch();
        Iterable pairs4 = st.keys();
        double dtime4 = time4.elapsedTime();

        StdOut.println("Timing for einstein to eisenhower");
        StdOut.println("pairs(): " + dtime1 + "\nkeys(): " + dtime2);
        StdOut.println("Words with frequency between einstein and eisenhower");
        for (Object x : pairs1)
            StdOut.println(x);
        StdOut.println("Timing for entire dictionary");
        StdOut.println("pairs(): " + dtime3 + "\nkeys(): " + dtime4);
    }
}
