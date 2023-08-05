package practice.aws_practice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final TestRepository testRepository;

    @GetMapping("/test")
    public String test() {
        return "Hello World!";
    }

    @GetMapping("/test/{name}")
    public String testSave(@PathVariable String name) {

        Test test = new Test();

        test.setName(name);

        Test save = testRepository.save(test);
        return save.getName();

    }

}
