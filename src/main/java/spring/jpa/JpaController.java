package spring.jpa;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
public final class JpaController {
    private final PlaygroundRepository playgroundRepo;

    JpaController(PlaygroundRepository playgroundRepo) {
        this.playgroundRepo = playgroundRepo;
    }

    @PutMapping("/jpa")
    public void save(KeyValue kv) {
        playgroundRepo.saveAndFlush(kv);
    }

    @RequestMapping("/jpa")
    public Collection<KeyValue> findAll() {
        return playgroundRepo.findAll();
    }

    @RequestMapping("/jpa/{key}")
    public KeyValue find(@PathVariable String key) {
        return playgroundRepo.findById(key).orElseThrow(() -> new NoSuchElementException(key));
    }
}
