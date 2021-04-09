package com.mobilemall.general;

import com.mobilemall.scrapper.categories.ReservedScraper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "com.mobilemall.scrapper")
@Slf4j
public class ScrapperApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrapperApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ReservedScraper reservedScraper) {
        return (args) -> {
            log.debug("Scrapping categories");
            val categories = reservedScraper.getScrappedCategories();

            //TODO fix logging
            log.debug("Categories scrapped from reserved: {}", categories.toString());
            System.out.println(categories.toString());
            log.debug("Products from first category: {}", reservedScraper.getProducts(categories.get(0)));
            System.out.println(reservedScraper.getProducts(categories.get(0)));
        };
    }
}
