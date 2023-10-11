package design.patterns.lod;

class TypeOne {
    TypeTwo instanceVariableTwo;
    TypeTwo getTypeTwo() {
        return instanceVariableTwo;
    }

    TypeOne aMethodReturningTypeOne() {
        return this;
    }

    TypeOne anotherMethodReturningTypeOne() {
        return this;
    }
}
