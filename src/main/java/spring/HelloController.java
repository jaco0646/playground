package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.domain.Animal;

import javax.annotation.PostConstruct;

@RestController
public class HelloController {

    @Autowired
    private Service service;

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

    @PostMapping("/animal")
    public String postAnimal(@RequestBody Animal animal) {
        return animal.speak();
    }

}
