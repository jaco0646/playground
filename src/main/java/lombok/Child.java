package lombok;

import lombok.experimental.Delegate;

//@ToString
public class Child extends Parent {
    @Delegate
    private final Parent delegate;
    @Getter
    private final String baz;

    public Child(Parent parent, String baz) {
        this.delegate = parent.toBuilder().build();
        this.baz = baz;
    }
}
