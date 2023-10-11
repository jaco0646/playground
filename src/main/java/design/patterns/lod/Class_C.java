package design.patterns.lod;

import java.util.ArrayList;
import java.util.List;

    class Class_C {
        TypeOne instanceVariable;

        void method_M(TypeThree argument) {
            // Allowed to call methods on argument variable types.
            // "1. The argument classes of M..."
            TypeFour typeFour = argument.getTypeFour();

            // Allowed to call methods on own (or inherited) types.
            // "1. The argument classes of M (including C)."
            allowed();

            // Allowed to call methods on own (or inherited) instance variable types.
            // "2. The instance variable classes of C."
            TypeTwo typeTwo = instanceVariable.getTypeTwo();

            // Allowed to create Objects and/or call constructor methods.
            // "Objects created by M or by functions or methods which M calls...
            //   are considered as arguments of M."
            String newString = "Hello, Law of Demeter!";
            List<String> newList = new ArrayList<>();

            // Allowed to call methods on global variables,
            //   e.g. println() on the 'out' variable of System.
            // "Global variables are considered as arguments of M."
            System.out.println(newString);

            // NOT allowed to call methods on other classes' instance variable types.
            // "The Law _prohibits_ the nesting of generic accessor function calls..."
            //   One accessor call was made to retrieve each of these variable types,
            //     so a second call would be inappropriate nesting.
            typeTwo.notAllowed();
            typeFour.notAllowed();

            // Allowed to call methods on newly-constructed objects.
            // Remember, these "are considered as arguments of M."
            // "[The Law] _allows_ the nesting of constructor function calls."
            //   One constructor call followed by a second call is appropriate nesting.
            newList.add(newString);

            // Note: nested method calls on the _same_ variable type are allowed,
            //   because this does not change the implications of rules 1 and 2.
            argument.aMethodReturningTypeThree().anotherMethodReturningTypeThree();
            instanceVariable.aMethodReturningTypeOne().anotherMethodReturningTypeOne();
        }

        void allowed() {}
    }
