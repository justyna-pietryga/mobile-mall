package com.mobilemall.general;

import com.mobilemall.scrapper.ScraperService;
import com.mobilemall.scrapper.categories.ReservedScrapCategories;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.mobilemall.scrapper")
public class ScrapperApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScrapperApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        ScrapCategories scrapCategories = new ReservedScrapCategories();
//        scrapCategories.getScrappedCategories();
//        System.out.println("Hello World from Application Runner");
    }
    @Bean
    public CommandLineRunner demo(ReservedScrapCategories reservedScrapCategories) {
        return (args) -> {
            System.out.println("zaczynamy");
            System.out.println(reservedScrapCategories.getScrappedCategories());
        };
    }
}
