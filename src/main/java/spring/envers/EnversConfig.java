package spring.envers;

import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class EnversConfig {
    private final EntityManagerFactory entityManagerFactory;

    @PostConstruct
    private void disableAuditingInsertEvents() {
        entityManagerFactory
                .unwrap(SessionFactoryImplementor.class)
                .getServiceRegistry()
                .getService(EventListenerRegistry.class)
                .getEventListenerGroup(EventType.POST_INSERT)
                .clearListeners();
    }
}
