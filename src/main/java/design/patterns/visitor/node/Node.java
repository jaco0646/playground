package design.patterns.visitor.node;

import design.patterns.visitor.NodeVisitor;

public interface Node {
    <T> T accept(NodeVisitor<T> visitor);
}