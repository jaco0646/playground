package spring.jpa;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
public final class JpaController {
    private final PlaygroundRepository playgroundRepo;

    JpaController(PlaygroundRepository playgroundRepo) {
        this.playgroundRepo = playgroundRepo;
    }

    @PutMapping("/jpa")
    public void save(@RequestBody KeyValue kv) {
        System.out.println(">>> /jpa ::" + kv);
        playgroundRepo.saveAndFlush(kv);
    }

    @GetMapping("/jpa")
    public Collection<KeyValue> findAll() {
        return playgroundRepo.findAll();
    }

    @GetMapping("/jpa/{key}")
    public KeyValue find(@PathVariable String key) {
        return playgroundRepo.findById(key).orElseThrow(() -> new NoSuchElementException(key));
    }
}
