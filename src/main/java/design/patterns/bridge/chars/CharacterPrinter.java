package design.patterns.bridge.chars;

import design.patterns.bridge.words.WordPrinter;

/** Implementor
 * defines the interface for implementation classes.
 * This interface doesn't have to correspond exactly to Abstraction's interface; (i.e. {@link WordPrinter})
 * in fact the two interfaces can be quite different.
 * Typically the Implementor interface provides only primitive operations,
 * and Abstraction defines higher-level operations based on these primitives.
 */
public interface CharacterPrinter {
    void c();
    void l();
    void o();
    void r();
    void u();
}
