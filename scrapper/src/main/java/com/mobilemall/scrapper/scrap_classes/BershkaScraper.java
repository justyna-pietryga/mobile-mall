package com.mobilemall.scrapper.scrap_classes;

import com.mobilemall.scrapper.conf.SeleniumManager;
import com.mobilemall.scrapper.model.Category;
import com.mobilemall.scrapper.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class BershkaScraper extends Scraper implements Scrapable {
    @Value("${bershka.url}")
    private String url;
    @Value("${bershka.element.li.xpath}")
    private String liElementXpath;

    protected BershkaScraper(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public List<Category> getScrappedCategories() {
        return SeleniumManager.scrapData(this::scrapCategories, url + "/pl/");
    }

    private List<Category> scrapCategories(WebDriver driver) {
        return driver
                .findElement(By.xpath(liElementXpath))
                .findElements(By.tagName(LI_TAG))
                .stream()
                .map(this::getOptionalAtagCategoryElement)
                .filter(Optional::isPresent)
                .map(this::getCategory)
                .collect(toList());
    }

    @Override
    public List<Product> getProducts(Category category) throws IOException {
        return null;
    }

    private Category getCategory(Optional<?> categoryA) {
        WebElement category = (WebElement) categoryA.get();
        return Category.builder()
                .name(category.findElement(By.tagName("span")).getAttribute("innerHTML"))
                .url(category.getAttribute("href"))
                .build();
    }

    private Optional<?> getOptionalAtagCategoryElement(WebElement categoryEl) {
        try {
            return Optional.of(categoryEl.findElement(By.tagName("a")));
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            System.out.println("No category in this element: " + exception); //TODO make slf4j work and put log here
            return Optional.empty();
        }
    }
}
