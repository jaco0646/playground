package playground

class Metaprogramming {

    static void main(String... args) {
        StackApi api = [
                declaredMethod: { Object[] arg -> println "declaredMethod!"},
        ].asType(StackApi)
        api.metaClass.undeclaredMethod = { Object[] arg -> println "undeclaredMethod!"}
        api.undeclaredMethod() // ok
        api.declaredMethod() // ok
    }

    static interface StackApi {
        void declaredMethod()
    }

}
