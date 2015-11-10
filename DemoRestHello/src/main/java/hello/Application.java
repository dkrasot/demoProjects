package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//TODO later try to change to
@SpringBootApplication
//= @Configuration + @EnableAutoConfiguration + @EnableWebMvc (if we have spring-webmvc on the classpath) + @ComponentScan (finds GreetingController in package)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //run config : mvn spring-boot:run
    }
}