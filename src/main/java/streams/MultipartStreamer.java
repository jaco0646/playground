package streams;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.input.BoundedInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@UtilityClass
public class MultipartStreamer {
    static void main() {
        InputStream stream = new ByteArrayInputStream("Hello, World".getBytes());
        InputStream part1 = streamPart(stream);
        InputStream part2 = streamPart(stream);
        InputStream part3 = streamPart(stream);

        System.out.println(">>>");
        System.out.println(toString(part1) + toString(part2) + toString(part3));
        System.out.println("<<<");
    }

    private static InputStream streamPart(InputStream stream) {
        try {
            return BoundedInputStream.builder()
                    .setInputStream(stream)
                    .setMaxCount(6)
                    .get();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String toString(InputStream stream) {
        try {
            return new String(stream.readAllBytes(), UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
