package playground.plugin.bytebuddy


import net.bytebuddy.build.Plugin
import net.bytebuddy.description.annotation.AnnotationDescription
import net.bytebuddy.description.type.TypeDescription
import net.bytebuddy.dynamic.ClassFileLocator
import net.bytebuddy.dynamic.DynamicType
import org.hibernate.annotations.Immutable

import jakarta.persistence.Entity
import jakarta.persistence.Table

class ImmutableEntityPlugin implements Plugin {
    private static final AnnotationDescription IMMUTABLE = AnnotationDescription.Builder.ofType(Immutable).build()
    private static final AnnotationDescription ENTITY = AnnotationDescription.Builder.ofType(Entity).build()
/*
    By the time Byte Buddy can add these annotations, Lombok has already run. It's too late.

    private static final AnnotationDescription VALUE = AnnotationDescription.Builder.ofType(Value).build()
    private static final AnnotationDescription ALL_ARGS = AnnotationDescription.Builder.ofType(AllArgsConstructor)
            .define('access', AccessLevel.PACKAGE).build()
    private static final AnnotationDescription NO_ARGS = AnnotationDescription.Builder.ofType(NoArgsConstructor)
            .define('access', AccessLevel.PACKAGE)
            .define('force', true).build()
*/
    @Override
    boolean matches(TypeDescription target) {
        return getImmutableEntityAnnotation(target)
    }

    @Override
    DynamicType.Builder<?> apply(DynamicType.Builder<?> builder, TypeDescription annotated, ClassFileLocator locator) {
        def annotation = getImmutableEntityAnnotation(annotated)
        def tableName = annotation.getValue('tableName').resolve(String)
        def table = AnnotationDescription.Builder.ofType(Table).define('name', tableName).build()
        return builder.annotateType(
                IMMUTABLE,
                ENTITY,
                table
        )
    }

    private static AnnotationDescription getImmutableEntityAnnotation(TypeDescription target) {
        return target.getDeclaredAnnotations().find {
            it.getAnnotationType().getTypeName() == 'spring.jpa.ImmutableEntity' }
    }

    @Override
    void close() {}
}
