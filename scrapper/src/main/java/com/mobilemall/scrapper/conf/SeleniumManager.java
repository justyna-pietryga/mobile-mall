package com.mobilemall.scrapper.conf;

import lombok.val;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class SeleniumManager {

    public static <T> List<T> scrapData(Function<WebDriver, List<T>> scrapConsumer, String url){
        WebDriver driver = createWebDriver();
        driver.get(url);
        val result = scrapConsumer.apply(driver);
        driver.quit();
        return result;
    }

    public static WebDriver createWebDriver() {
        System.setProperty("webdriver.chrome.driver", "scrapper/src/main/resources/chromedriver.exe");
//        Proxy proxy = new Proxy();
//        proxy.setAutodetect(false);
//        proxy.setHttpProxy("http_proxy-url:port");
//        proxy.setSslProxy("https_proxy-url:port");
//        proxy.setNoProxy("no_proxy-var");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--diable--notifications");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1580,1280");

//        options.setCapability("proxy", proxy);

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);

        return driver;
    }
}
