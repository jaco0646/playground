package spring;

import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static java.util.stream.Collectors.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.web.bind.annotation.ValueConstants.DEFAULT_NONE;

@Configuration
public class SwaggerConfig {
    @Bean
    public OperationCustomizer setUrlEncodedBody() {
        return (operation, handlerMethod) -> {
            if (isPostMappingForUrlEncodedBodyParams(handlerMethod)) {
                operation.setParameters(List.of());
                operation.setRequestBody(buildRequestBody(handlerMethod));
            }
            return operation;
        };
    }

    private boolean isPostMappingForUrlEncodedBodyParams(HandlerMethod handlerMethod) {
        if (handlerMethod.hasMethodAnnotation(PostMapping.class)) {
            String[] consumes = handlerMethod.getMethodAnnotation(PostMapping.class).consumes();
            if (Arrays.asList(consumes).contains(APPLICATION_FORM_URLENCODED_VALUE)) {
                return Arrays.stream(handlerMethod.getMethodParameters())
                        .allMatch(it -> it.hasParameterAnnotation(RequestParam.class));
            }
        }
        return false;
    }

    private RequestBody buildRequestBody(HandlerMethod handlerMethod) {
        Schema<?> schema = buildSchema(handlerMethod);
        return new RequestBody().content(new Content()
                .addMediaType(APPLICATION_FORM_URLENCODED_VALUE, new MediaType().schema(schema)));
    }

    private Schema<?> buildSchema(HandlerMethod handlerMethod) {
        Map<String, RequestParam> requestParams = getRequestParams(handlerMethod);
        Schema<?> schema = new Schema<>().type("object");
        schema.setProperties(requestParams
                .entrySet().stream()
                .collect(toMap(Entry::getKey, e -> buildSchema(e.getValue()))));
        schema.setRequired(requestParams
                .entrySet().stream()
                .filter(e -> e.getValue().required())
                .map(Entry::getKey)
                .collect(toList()));
        return schema;
    }

    private Map<String, RequestParam> getRequestParams(HandlerMethod handlerMethod) {
        Map<String, RequestParam> requestParams = new HashMap<>();
        for (Parameter p : handlerMethod.getMethod().getParameters()) {
            RequestParam annotation = p.getAnnotation(RequestParam.class);
            String name = annotation.name().isEmpty() ? p.getName() : annotation.name();
            requestParams.put(name, annotation);
        }
        return requestParams;
    }

    private Schema<?> buildSchema(RequestParam requestParam) {
        Schema<?> schema = new Schema<>().type("string");
        if (!requestParam.defaultValue().equals(DEFAULT_NONE)) {
            schema.setDefault(requestParam.defaultValue());
            schema.description("default: " + requestParam.defaultValue());
        }
        return schema;
    }
}
