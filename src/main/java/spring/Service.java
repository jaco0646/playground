package spring;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;

public class Service {

    @Value("${message}")
    private String msg;

    public Service() {
        System.out.println("instantiating Service");
    }

    @Cacheable("myCacheName")
    public String serve() throws InterruptedException {
        Thread.sleep(3000);
        return msg;
    }

    @Cacheable("myCacheName")
    public String foo() throws InterruptedException {
        Thread.sleep(3000);
        return "foo";
    }

    /** Does not work! @Lookup method must be in an annotated class. */
    @Lookup
    public PrototypeBean getPrototype() {
        throw new UnsupportedOperationException();
    }

}
