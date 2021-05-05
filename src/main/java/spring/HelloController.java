package spring;

import org.springframework.boot.devtools.restart.Restarter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class HelloController {

    private final Service service;

    HelloController(Service service) {
        this.service = service;
    }

    @PostConstruct
    void testLookupMethod() {
        try {
            System.out.println(" >>> Random int == " + service.getPrototype().randomInt);
            System.out.println(" >>> Random int == " + service.getPrototype().randomInt);
        } catch (UnsupportedOperationException ignored) {
            System.out.println(">>> @Lookup method not implemented.");
        }
    }

    @RequestMapping("/")
    public String index() throws InterruptedException {
        return service.serve();
    }

    @RequestMapping("/foo")
    public String foo() throws InterruptedException {
        return service.foo();
    }

    @PostMapping("/restart")
    public void restart() {
//        Application.restart();
        Restarter.getInstance().restart();
    }
}
