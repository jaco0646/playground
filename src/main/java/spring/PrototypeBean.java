package spring;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class PrototypeBean {
    private static final Random RANDOM = new Random();

    public final int randomInt = RANDOM.nextInt();
}
