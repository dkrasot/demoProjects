package bookmarks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@Configuration
@ComponentScan//(basePackages = "jpa")
@EnableAutoConfiguration
//TODO later try to change to
//@SpringBootApplication
//= @Configuration + @EnableAutoConfiguration + @EnableWebMvc (if we have spring-webmvc on the classpath) + @ComponentScan (finds GreetingController in package)
//@ComponentScan(basePackages = "jpa")
public class Application {

//    @Autowired
//    private final BookmarkRepository bookmarkRepository;
//
//    @Autowired
//    private final AccountRepository accountRepository;

    @Bean @Autowired
    CommandLineRunner init(AccountRepository accountRepository,
                           BookmarkRepository bookmarkRepository) {
        return (evt)-> Arrays.asList(
                "dkras,jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
                .forEach(
                        a -> {
                            Account account = accountRepository.save(new Account(a, "password"));
                            bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + a, "A description"));
                            bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + a, "A description"));
                        }
                );
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //run config : mvn spring-boot:run
    }

//    @PostConstruct
//    public void init(){
//        bookmarkRepository.save(new Bookmark(acc,uri,desc))
//    }
}



