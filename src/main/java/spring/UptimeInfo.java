package spring;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class UptimeInfo implements InfoContributor {
    private final MetricsEndpoint metrics;

    @Override
    public void contribute(Info.Builder builder) {
        Double seconds = metrics.metric("process.uptime", null).getMeasurements().get(0).getValue();
        Duration time = Duration.ofSeconds(seconds.longValue());
        String uptime = String.format(
                "%d:%02d:%02d Days:Hours:Minutes", time.toDaysPart(), time.toHoursPart(), time.toMinutesPart());
        builder.withDetail("jvm.uptime", uptime);
    }
}
