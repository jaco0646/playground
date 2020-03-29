package design.patterns.bridge;

import design.patterns.bridge.chars.LatinAcuteCharacterPrinter;
import design.patterns.bridge.chars.LatinCharacterPrinter;
import design.patterns.bridge.words.AmericanWordPrinter;
import design.patterns.bridge.words.BritishWordPrinter;
import design.patterns.bridge.words.WordPrinter;

public class MainBridge {
    public static void main(String... args) {
        WordPrinter american = new AmericanWordPrinter(new LatinAcuteCharacterPrinter());
        WordPrinter british = new BritishWordPrinter(new LatinCharacterPrinter());
        american.hue();
        System.out.println();
        british.hue();
    }
}
