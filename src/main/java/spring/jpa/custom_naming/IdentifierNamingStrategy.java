package spring.jpa.custom_naming;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategySnakeCaseImpl;
import org.springframework.context.annotation.Configuration;

import static org.hibernate.boot.model.naming.Identifier.toIdentifier;

/** Handle JPA entity logical names ending with capital ID the same as names ending with camel Id,
 * so that both are preceded by an underscore in physical column names. */
@Configuration
class IdentifierNamingStrategy extends PhysicalNamingStrategySnakeCaseImpl {
    @Override
    protected Identifier unquotedIdentifier(Identifier name) {
        return super.unquotedIdentifier(name.getText().endsWith("ID") ? toId(name) : name);
    }

    private Identifier toId(Identifier name) {
        return toIdentifier(name.getText().substring(0, name.getText().length() - 1) + 'd');
    }
}
