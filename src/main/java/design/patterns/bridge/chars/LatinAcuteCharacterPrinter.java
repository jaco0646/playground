package design.patterns.bridge.chars;

public class LatinAcuteCharacterPrinter implements CharacterPrinter {
    @Override public void c() { System.out.print('ć'); }
    @Override public void l() { System.out.print('ĺ'); }
    @Override public void o() { System.out.print('ó'); }
    @Override public void r() { System.out.print('ŕ'); }
    @Override public void u() { System.out.print('ú'); }
}
