package design.patterns.bridge.chars;

public class LatinCharacterPrinter implements CharacterPrinter {
    @Override public void c() { System.out.print('c'); }
    @Override public void l() { System.out.print('l'); }
    @Override public void o() { System.out.print('o'); }
    @Override public void r() { System.out.print('r'); }
    @Override public void u() { System.out.print('u'); }
}
