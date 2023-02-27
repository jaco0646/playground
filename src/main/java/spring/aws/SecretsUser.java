package spring.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
aws --endpoint-url=http://localhost:4566 secretsmanager create-secret --name playground --secret-string "{\"secret-name\":\"admin\"}"
aws --endpoint-url=http://localhost:4566 secretsmanager put-secret-value --secret-id playground --secret-string "{\"secret-name\":\"admin\"}"
 */
@Component
public class SecretsUser {
    public SecretsUser(@Value("${playground.name}") String name, @Value("${playground.password}") String pass) {
        if (!name.isEmpty()) {
            System.out.println(">>> Found secret-name!");
        }
        if (!pass.isEmpty()) {
            System.out.println(">>> Found secret-password!");
        }
    }
}
