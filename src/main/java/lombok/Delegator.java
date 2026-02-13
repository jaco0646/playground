package lombok;

import lombok.experimental.Delegate;

public class Delegator {
    @Delegate(types = Included.class, excludes = Excluded.class)
    private MyDelegate delegate;

    public static void main(String... args) {
        new Delegator().include();
        new Delegator().include2();
//        new Delegator().extra();
    }

    private static class MyDelegate {
        public void include() {}
        public void include2() {}
        public void exclude() {}
        public void exclude2() {}
    }

    private interface Included {
        void include();
        void include2();
//        void extra();  // Not implemented: runtime error.
        void exclude();
    }

    private interface Excluded {
        void exclude();
    }
}
