package spring.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.util.StringValueResolver;

import java.lang.reflect.AnnotatedElement;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

@Configuration
@EnableFeignClients
class FeignConfiguration implements EmbeddedValueResolverAware {
    private StringValueResolver stringValueResolver;

    @Bean
    RequestInterceptor headerAnnotationInterceptor() {
        return requestTemplate -> {
            setHeadersFromAnnotations(requestTemplate, requestTemplate.feignTarget().type());
            setHeadersFromAnnotations(requestTemplate, requestTemplate.methodMetadata().method());
        };
    }

    private void setHeadersFromAnnotations(RequestTemplate requestTemplate, AnnotatedElement annotatedElement) {
        stream(annotatedElement.getAnnotationsByType(FeignHeader.class))
                .forEach(feignHeader -> requestTemplate.header(
                        resolve(feignHeader.name()),
                        resolve(feignHeader.value())
                ));
    }

    private String resolve(String placeholder) {
        return requireNonNull(stringValueResolver.resolveStringValue(placeholder),
                "Placeholder must not resolve to null: " + placeholder);
    }

    @Override
    public void setEmbeddedValueResolver(@NonNull StringValueResolver resolver) {
        this.stringValueResolver = resolver;
    }
}
