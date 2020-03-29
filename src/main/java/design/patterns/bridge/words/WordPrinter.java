package design.patterns.bridge.words;

import design.patterns.bridge.chars.CharacterPrinter;

/** Abstraction
 * <li> defines the abstraction's interface.
 * <li> maintains a reference to an object of type Implementor (i.e. {@link CharacterPrinter})
 */
public abstract class WordPrinter {
    protected CharacterPrinter cp;
    public WordPrinter(CharacterPrinter cp) {
        this.cp = cp;
    }
    public abstract void hue();
}
