package spring;

import org.springframework.boot.devtools.restart.Restarter;
import org.springframework.http.ContentDisposition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@RestController
public class HelloController {

    private final Service service;

    HelloController(Service service) {
        this.service = service;
    }

    @PostConstruct
    void testLookupMethod() {
        try {
            System.out.println(" >>> Random int == " + service.getPrototype().randomInt);
            System.out.println(" >>> Random int == " + service.getPrototype().randomInt);
        } catch (UnsupportedOperationException ignored) {
            System.out.println(">>> @Lookup method not implemented.");
        }
    }

    @GetMapping("/")
    public String index() throws InterruptedException {
        return service.serve();
    }

    @GetMapping("/foo")
    public String foo() throws InterruptedException {
        return service.foo();
    }

    @PostMapping("/restart")
    public void restart() {
//        Application.restart();
        Restarter.getInstance().restart();
    }

/**
    The {@code @RequestBody} annotation is a local alternative to the
        global {@code setUrlEncodedBody} bean in SwaggerConfig.
    It also requires the params to be individually hidden (e.g. {@code @Parameter(hidden = true)})
        so they are not automatically documented as query params.
    <pre>{@code
        @RequestBody(content = @Content(
            mediaType = APPLICATION_FORM_URLENCODED_VALUE,
            schema = @Schema(type = "object", requiredProperties = {
                    "required1", "requiredNameOverride", "requiredWithDefault"}),
            schemaProperties = {
                @SchemaProperty(name = "required1", schema = @Schema(type = "string")),
                @SchemaProperty(name = "requiredNameOverride", schema = @Schema(type = "string")),
                @SchemaProperty(name = "requiredWithDefault", schema = @Schema(type = "string", defaultValue = "default_value")),
                @SchemaProperty(name = "optional", schema = @Schema(type = "string"))
            }
        ))
    }</pre>
 */
    @PostMapping(path = "/form", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    public String form(
            @RequestParam String required1,
            @RequestParam(name = "requiredNameOverride") String required2,
            @RequestParam(defaultValue = "default_value") String requiredWithDefault,
            @RequestParam(required = false) String optional) {
        return APPLICATION_FORM_URLENCODED_VALUE
                + "\n" + required1
                + "\n" + required2
                + "\n" + requiredWithDefault
                + "\n" + optional;
    }

    @GetMapping(path = "/csv")
    public void csv(HttpServletResponse response) throws IOException {
        String fileName = "csv_" + LocalDate.now() + ".csv";
        response.setHeader(CONTENT_DISPOSITION, ContentDisposition.attachment().filename(fileName).build().toString());
        response.setContentType("text/csv");
        try (PrintWriter outputWriter = response.getWriter()) {
            Stream.of("foo,bar", "baz,qux").forEach(outputWriter::println);
        }
    }
}
