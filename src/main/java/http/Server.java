package http;

import module jdk.httpserver;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import static java.net.http.HttpClient.newHttpClient;

/** <a href="https://www.youtube.com/shorts/LNpLbSR1pzE">Java HttpServer</a> */
public class Server {
    public static final int PORT = 8080;
    public static final String PATH = "/http/status";
    public static final URI URL = URI.create("http://localhost:" + PORT + PATH);

    static void main() {
        HttpServer server = serving(HttpStatus.OK);
        server.start();
        System.out.println(">>>HTTP response: " + request());
        server.stop(0);
    }

    public static HttpServer serving(HttpStatus status) {
        try {
            return build(status);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String request() {
        HttpRequest request = HttpRequest.newBuilder().uri(URL).GET().build();
        HttpResponse<String> response;
        try (HttpClient client = newHttpClient()) {
            response = client.send(request, BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    private static HttpServer build(HttpStatus status) throws IOException {
        InetSocketAddress port = new InetSocketAddress(PORT);
        HttpServer server = HttpServer.create(port, 0);
        server.createContext(PATH, get(status));
        return server;
    }

    private static HttpHandler get(HttpStatus status) {
        String msg = status.value() + " " + status.getReasonPhrase();
        return (HttpExchange xc) -> {
            IO.println(".");
            try(xc) {
                xc.sendResponseHeaders(status.value(), msg.length());
                xc.getResponseBody().write(msg.getBytes());
            }
        };
    }
}
