package spring.animal;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import org.springframework.stereotype.Component;
import spring.animal.domain.Animal;

import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Resolve subtypes of {@code Animal} by their json name.
 * {@link ObjectMapper#registerSubtypes(Class[])} is a simpler mechanism of accomplishing the same goal.
 */
@Component
public class AnimalTypeResolver implements TypeIdResolver {
    private final Map<String, Class<? extends Animal>> typeLookup = new ConcurrentHashMap<>();
    private final Map<Class<? extends Animal>, String> idLookup = new ConcurrentHashMap<>();
    private JavaType superClass;

    public void registerAnimal(Class<? extends Animal> animalClass) {
        String animalName = animalClass.getAnnotation(JsonTypeName.class).value();
        typeLookup.put(animalName, animalClass);
        idLookup.put(animalClass, animalName);
    }

    @Override
    public void init(JavaType baseType) {
        System.out.println("Initializing AnimalTypeResolver: " + baseType);
        superClass = baseType;
    }

    @Override
    public String idFromValue(Object value) {
        return idFromValueAndType(value, value.getClass());
    }

    @Override
    public String idFromValueAndType(Object value, Class<?> suggestedType) {
        if (idLookup.containsKey(suggestedType)) {
            return idLookup.get(suggestedType);
        }
        throw new IllegalArgumentException("Unregistered animal type: " + suggestedType);
    }

    @Override
    public String idFromBaseType() {
        return idFromValueAndType(null, Animal.class);
    }

    @Override
    public JavaType typeFromId(DatabindContext context, String id) {
        if (typeLookup.containsKey(id)) {
            return context.constructSpecializedType(superClass, typeLookup.get(id));
        }
        throw new IllegalArgumentException("Unregistered animal name: " + id);
    }

    @Override
    public String getDescForKnownTypeIds() {
        return new TreeSet<>(typeLookup.keySet()).toString();
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.NAME;
    }
}
