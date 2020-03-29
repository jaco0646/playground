package design.patterns.visitor;

import design.patterns.visitor.node.ANode;
import design.patterns.visitor.node.BNode;

public interface NodeVisitor<T> {
    T visit(ANode aNode);
    T visit(BNode bNode);
}