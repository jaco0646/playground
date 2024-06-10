package spring.enums;

import org.springframework.web.bind.annotation.*;

@RestController
public class EnumsController {
    enum MyEnum {
        FOO,
        BAR,
        BAZ,
        QUX
    }

    /** Uses Spring converters. */
    @GetMapping("/enum")
    MyEnum deserializeParam(@RequestParam MyEnum me) {
        return me;
    }

    /** Uses Jackson ObjectMapper. */
    @PutMapping("/enum/body")
    MyEnum deserializeBody(@RequestBody MyEnum me) {
        return me;
    }
}
