package design.patterns.visitor;

import design.patterns.visitor.node.ANode;
import design.patterns.visitor.node.BNode;

public class DtoNodeVisitor implements NodeVisitor<DTO> {
    @Override
    public DTO visit(ANode aNode) {
        return new DTO(aNode.foo());
    }
    @Override
    public DTO visit(BNode bNode) {
        return new DTO(bNode.bar());
    }
}