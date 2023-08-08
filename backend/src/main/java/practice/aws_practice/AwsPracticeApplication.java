package practice.aws_practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class AwsPracticeApplication {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(AwsPracticeApplication.class);

        app.run(args);
    }

}
