package lombok;

public class MainLombok {

    public static void main(String... args) {
        Parent p = new Parent("foo", true);
        Parent c1 = new Child(p, "baz");
        Parent c2 = new Child(p, "qux");

        System.out.println(p);
        System.out.println(c1);

        System.out.println( p.equals(c1) );
        System.out.println( c1.equals(p) );
        System.out.println( c1.equals(c2) );
    }

}
