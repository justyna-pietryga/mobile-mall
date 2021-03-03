package com.mobilemall.general;

import com.mobilemall.general.categories.ReservedScrapCategories;
import com.mobilemall.general.categories.ScrapCategories;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.concurrent.TimeUnit;

@Service
public class ScraperService {

    private final ScrapCategories scrapCategories;

    @Autowired
    public ScraperService(ReservedScrapCategories reservedScrapCategories) {
        scrapCategories = reservedScrapCategories;
    }

    public void test2(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--diable--notifications");
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
//        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("https://www.reserved.com/pl/pl/");
    }

    public void test() throws IOException {
        scrapCategories.getScrappedCategories();
    }

    public void getProduct(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        LinkedHashSet<String> links = new LinkedHashSet<>();

        Elements elements = doc.select("#categoryProducts article");
        elements.forEach(child -> links.add(child.select("a").attr("href")));
    }
}
