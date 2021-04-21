package com.mobilemall.scrapper.scrap_classes;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

@Service
public abstract class Scraper implements Scrapable{
    public static final String LI_TAG = "li";
    @Getter
    private final WebDriver webDriver;

    protected Scraper(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
}
