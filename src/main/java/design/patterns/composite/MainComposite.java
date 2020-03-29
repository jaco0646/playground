package design.patterns.composite;

public class MainComposite {
    public static void main(String... args) {
        Arithmetic fivePlusTwo = new Addition(5,2);
        Arithmetic sixMinusThree = new Subtraction(6,3);
        Arithmetic sevenPlusThree = new CompositeAddition(fivePlusTwo, sixMinusThree);
        System.out.println(sevenPlusThree.compute());
    }
}
