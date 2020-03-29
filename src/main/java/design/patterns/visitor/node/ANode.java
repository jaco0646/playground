package design.patterns.visitor.node;

import design.patterns.visitor.NodeVisitor;

public class ANode implements Node {
    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String foo() {
        return "foo";
    }
}