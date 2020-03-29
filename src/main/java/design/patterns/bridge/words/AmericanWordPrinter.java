package design.patterns.bridge.words;

import design.patterns.bridge.chars.CharacterPrinter;

public class AmericanWordPrinter extends WordPrinter {
    public AmericanWordPrinter(CharacterPrinter cp) {
        super(cp);
    }
    @Override
    public void hue() {
        cp.c();
        cp.o();
        cp.l();
        cp.o();
        cp.r();
    }
}
