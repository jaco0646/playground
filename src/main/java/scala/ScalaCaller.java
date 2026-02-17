package scala;

import playground.matcher.ScalaMatcher;
import com.external.ScalaMatcher2;

public class ScalaCaller {
    public static void main(String... args) {
        ScalaMatcher matcher = new ScalaMatcher("foo bar baz qux");
        String msg = matcher.scalaMatch("oo");
        System.out.println(msg);

        ScalaMatcher2 matcher2 = new ScalaMatcher2("foo bar baz qux");
        String msg2 = matcher2.scalaMatch("ux");
        System.out.println(msg2);
    }
}
