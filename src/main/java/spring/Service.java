package spring;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import spring.cache.ICache;

import java.io.OutputStream;

public class Service implements ICache {

    @Value("${message}")
    private String msg;

    public Service() {
        System.out.println("instantiating Service");
    }

    @Cacheable("myCacheName")
    public String serve() {
        return msg;
    }

    @Override
    @Cacheable("myCacheName")
    public String foo() {
        return "foo";
    }

    @Override
    public void repopulateCache(ICache iCache) {
        if (iCache instanceof Service self) {
            self.serve();
        }
    }

    /** Does not work! @Lookup method must be in an annotated class. */
    @Lookup
    public PrototypeBean getPrototype() {
        throw new UnsupportedOperationException();
    }

    public void printSlow(OutputStream out, String msg) {
        try {
            out.write(msg.getBytes());
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
