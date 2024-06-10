package spring.enums;


import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EnumConfig implements WebMvcConfigurer {
    /** !! IMPORTANT !!
     * Spring converters apply ONLY to individual path/query params and NOT to JSON payloads.
     * JSON is deserialized by Jackson; so for JSON, the same logic needs to be configured on the ObjectMapper.
     */
    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        // In particular, this method applies the LenientStringToEnumConverterFactory.
        // ApplicationConversionService.addApplicationConverters(registry);
        registry.addConverterFactory(new StringToEnumConverterFactory());
        System.out.println(">>> Enums config");
    }

    /** <a href="https://docs.spring.io/spring-framework/reference/core/validation/convert.html#core-convert-ConverterFactory-SPI">ConverterFactory</a> */
    static final class StringToEnumConverterFactory implements ConverterFactory<String, Enum<?>> {
        @NonNull
        @Override
        public <T extends Enum<?>> Converter<String, T> getConverter(@NonNull Class<T> targetType) {
            return new StringToEnumConverter<>(targetType);
        }

        /**
         * Convert to an Enum by either its case-insensitive name() or case-insensitive toString() value.
         */
        static final class StringToEnumConverter<T extends Enum<?>> implements Converter<String, T> {
            private final Map<String, T> lookup = new HashMap<>();
            private final Class<T> enumType;

            StringToEnumConverter(Class<T> enumType) {
                this.enumType = enumType;
                for (T e : enumType.getEnumConstants()) {
                    lookup.put(e.name().toUpperCase(), e);
                    lookup.put(e.toString().toUpperCase(), e);
                }
            }

            @Override
            public T convert(String source) {
                return lookup.computeIfAbsent(
                    source.toUpperCase(),
                    key -> { throw new IllegalArgumentException("No instance of " + enumType + " matches " + source); });
            }
        }
    }
}
