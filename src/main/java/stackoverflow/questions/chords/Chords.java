package stackoverflow.questions.chords;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Chords {
    static final Pattern CHORD = Pattern.compile("\\[(.+?)]");

    static String formatChords(String input) {
        String[] lines = input.split("\\R+");
        return Arrays.stream(lines)
                .map(Chords::formatLine)
                .collect(Collectors.joining("\n"));
    }

    static String formatLine(String input) {
        return formatLineChords(input) + "\n" + formatLineVerse(input);
    }

    static String formatLineChords(String input) {
        StringBuilder output = new StringBuilder();
        int chordsLength = 0;
        Matcher matcher = CHORD.matcher(input);
        while (matcher.find()) {
            String pad = pad(output.length(), chordsLength, matcher.start());
            output.append(pad).append(matcher.group(1));
            chordsLength += matcher.group().length();
        }
        return output.toString();
    }

    static String formatLineVerse(String input) {
        return CHORD.matcher(input).replaceAll("").trim();
    }

    static String pad(int currentIndex, int chordsLength, int nextIndex) {
        int size = nextIndex - currentIndex - chordsLength;
        return (size <= 1) ? " " : " ".repeat(size);
    }
}
