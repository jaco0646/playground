package design.patterns.visitor.node;

import design.patterns.visitor.NodeVisitor;

public class BNode implements Node {
    @Override
    public <T> T accept(NodeVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String bar() {
        return "bar";
    }
}