package design.patterns.lod;

class TypeThree {
    TypeFour instanceVariableFour;
    TypeFour getTypeFour() {
        return instanceVariableFour;
    }

    TypeThree aMethodReturningTypeThree() {
        return this;
    }

    TypeThree anotherMethodReturningTypeThree() {
        return this;
    }
}
