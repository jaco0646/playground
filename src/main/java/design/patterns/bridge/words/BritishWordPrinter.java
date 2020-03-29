package design.patterns.bridge.words;

import design.patterns.bridge.chars.CharacterPrinter;

public class BritishWordPrinter extends WordPrinter {
    public BritishWordPrinter(CharacterPrinter cp) {
        super(cp);
    }
    @Override
    public void hue() {
        cp.c();
        cp.o();
        cp.l();
        cp.o();
        cp.u();
        cp.r();
    }
}
