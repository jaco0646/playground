package http;

import module jdk.httpserver;
import lombok.Builder;
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
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/** <a href="https://www.youtube.com/shorts/LNpLbSR1pzE">Java HttpServer</a> */
public class StaticResponseServer implements AutoCloseable {
    private final String host;
    private final int port;
    private final String path;
    private final URI url;
    private final HttpStatus status;
    private final String msg;
    private final HttpServer server;

    @Builder
    private StaticResponseServer(String host, int port, String path, HttpStatus status, String msg) {
        if (port < 1) {
            throw new IllegalArgumentException("Invalid port: " + port);
        }
        this.host = host.endsWith("/") ? host.substring(0, host.length() - 1) : host;
        this.port = port;
        this.path = path == null ? "/" : path.startsWith("/") ? path : "/" + path;
        this.url = URI.create(this.host + ":" + this.port + this.path);
        this.status = requireNonNull(status, "Invalid status: null");
        this.msg = msg == null ? jsonMsg(status) : msg;
        this.server = serve();
    }

    static void main() {
        StaticResponseServer localhost = StaticResponseServer.builder()
                .host("http://localhost/httpstatus/")
                .port(80)
                .status(HttpStatus.OK)
                .build();
        try (localhost) {
            System.out.println(">>>HTTP response: " + localhost.testRequest());
        }
    }

    private String testRequest() {
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response;
        try (HttpClient client = newHttpClient()) {
            response = client.send(request, BodyHandlers.ofString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }

    private HttpServer serve() {
        try {
            HttpServer server = build();
            server.start();
            return server;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private HttpServer build() throws IOException {
        InetSocketAddress port = new InetSocketAddress(this.port);
        HttpServer server = HttpServer.create(port, 0);
        server.createContext(path, handler());
        return server;
    }

    private HttpHandler handler() {
        return (HttpExchange xc) -> {
            IO.println(".");
            try(xc) {
                xc.getResponseHeaders().set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
                xc.sendResponseHeaders(status.value(), msg.length());
                xc.getResponseBody().write(msg.getBytes());
            }
        };
    }

    private String jsonMsg(HttpStatus status) {
        return "{" +
                '"' +
                "code" +
                '"' +
                ":" +
                status.value() +
                ',' +
                '"' +
                "description" +
                '"' +
                ":" +
                '"' +
                status.getReasonPhrase() +
                '"' +
                "}";
    }

    @Override
    public void close() {
        server.stop(0);
    }
}
