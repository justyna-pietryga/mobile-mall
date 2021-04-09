package com.mobilemall.scrapper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;

import java.util.concurrent.TimeUnit;

@Configuration
@SpringBootApplication
@PropertySource("classpath:scrapper.properties")
public class ScraperConfiguration {
    @Bean
    public WebDriver webDriver(){
        System.setProperty("webdriver.chrome.driver", "scrapper/src/main/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--diable--notifications");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1580,1280");

        WebDriver driver = new ChromeDriver(options);
//        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

        return driver;
    }
}
