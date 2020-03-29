package design.patterns.visitor;

import design.patterns.visitor.node.ANode;
import design.patterns.visitor.node.BNode;
import design.patterns.visitor.node.Node;

public class MainVisitor {
    public static void main(String... args) {
        Node a = new ANode();
        Node b = new BNode();
        NodeVisitor<DTO> dtoVisitor = new DtoNodeVisitor();
        DTO dtoA = a.accept(dtoVisitor);
        DTO dtoB = b.accept(dtoVisitor);
        System.out.println(dtoA.getValue());
        System.out.println(dtoB.getValue());
    }
}
