package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
//methods return domain objects instead of views ( like @Controller + @ResponseBody )
// http://localhost:8080/greeting -> Response with JSON {"id":1,"content":"Hello, World!"}
// http://localhost:8080/greeting?name=Ukraine -> {"id":2,"content":"Hello, Ukraine!"}
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name){
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
        //this object will be converted to JSON automatically because Jackson2 lib in classpath
    }

}
