package com.mobilemall.scrapper.conf;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class SeleniumManager {

    public static <T> Flux<T> scrapData(Function<WebDriver, Flux<T>> scrapConsumer, String url) {
        WebDriver driver = createWebDriver();
        driver.get(url);
        synchronized (driver) {
            webDriverDelayAtStart(driver);
        }
        return scrapConsumer.apply(driver)
                .doOnTerminate(driver::quit);
    }

    private static void webDriverDelayAtStart(WebDriver driver) {
        try {
            driver.wait(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static WebDriver createWebDriver() {
        System.setProperty("webdriver.chrome.driver", "scrapper/src/main/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--diable--notifications");
//        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1580,1280");

        WebDriver driver = new ChromeDriver(options);
//        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        return driver;
    }
}
