package spring.health;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class OptionalHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return Health.status("UNAVAILABLE")
                .withDetail("required", "false")
                .build();
    }
}
