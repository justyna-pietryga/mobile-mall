package com.mobilemall.scrapper.scrap_classes;

import com.mobilemall.scrapper.conf.SeleniumManager;
import com.mobilemall.scrapper.conf.ShopsEnum;
import com.mobilemall.scrapper.model.Category;
import com.mobilemall.scrapper.model.Product;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

@Service
public class BershkaScraper extends Scraper implements Scrapable {
    @Value("${bershka.url}")
    private String url;
    @Value("${bershka.element.li.xpath}")
    private String liElementXpath;
    @Value("${bershka.product.ul.path}")
    private String productUlPath;

    protected BershkaScraper(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public ShopsEnum getShop() {
        return ShopsEnum.BERSHKA;
    }

    @Override
    public Flux<Category> getScrappedCategories() {
        return SeleniumManager.scrapData(this::scrapCategories, url + "/pl/");
    }

    private Flux<Category> scrapCategories(WebDriver driver) {
        val elements = driver
                .findElement(By.xpath(liElementXpath))
                .findElements(By.tagName(LI_TAG));

        return Flux.fromIterable(elements)
                .map(this::getOptionalAtagCategoryElement)
                .filter(Optional::isPresent)
                .map(this::getCategory);
    }

    @Override
    public Flux<Product> getProducts(Category category) {
        return SeleniumManager.scrapData(this::scrapProducts, category.getUrl());
    }


    private Flux<Product> scrapProducts(WebDriver webDriver) {
        val webElements = webDriver
                .findElement(By.xpath(productUlPath))
                .findElements(By.tagName(LI_TAG));

        return Flux.fromIterable(webElements)
                .doOnNext(el -> System.out.println("First element tag should be li: " + el.getTagName()))
                .subscribeOn(Schedulers.boundedElastic())
                .map(this::getProduct)
                .doOnNext(el -> System.out.println("Mapped to product: " + el.getName()))
                .filter(product -> product.getUrl() != null)
                .doOnNext(el -> System.out.println("Filtered"))
                .doOnComplete(() -> System.out.println("completed"));
    }

    private Product getProduct(WebElement webElement) {
        if (getOptionalTagProductElement(webElement, "div").isPresent()) {
            System.out.println("getOptional div");
            WebElement productDivHref = webElement.findElement(By.tagName("div"));
            if (getOptionalTagProductElement(productDivHref, "a").isPresent()) {
                WebElement productAHref = productDivHref.findElement(By.tagName("a"));
                System.out.println("getOptional a");
                WebElement pElement = productAHref
                        .findElement(By.className("product-content"))
                        .findElement(By.tagName("div"))
                        .findElement(By.className("product-text"))
                        .findElement(By.tagName("p"));

                Optional<WebElement> imgElement = Optional.empty();
                val imgEl1 = getOptionalClassProductElement(productAHref, "product-image");
                if (imgEl1.isPresent()) {
                    val imgEl2 = getOptionalClassProductElement(imgEl1.get(), "product-image-wrapper");
                    if (imgEl2.isPresent()) {
                        val imgEl3 = getOptionalClassProductElement(imgEl2.get(), "image-item-wrapper");
                        if (imgEl3.isPresent()) {
                            imgElement = getOptionalClassProductElement(imgEl3.get(), "image-item");
                        }
                    }
                }

                return Product.builder()
                        .url(productAHref.getAttribute("href"))
                        .name(pElement.getText())
                        .imgUrl(imgElement.isPresent() ? imgElement.get().getAttribute("data-original") : "")
                        .build();
            }
        }
        return Product.builder().build();
    }

    private Category getCategory(Optional<?> categoryA) {
        WebElement category = (WebElement) categoryA.get();
        return Category.builder()
                .name(category.findElement(By.tagName("span")).getAttribute("innerHTML"))
                .url(category.getAttribute("href"))
                .shop(getShop())
                .build();
    }

    private Optional<WebElement> getOptionalAtagCategoryElement(WebElement categoryEl) {
        try {
            return Optional.of(categoryEl.findElement(By.tagName("a")));
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            System.out.println("No category in this element: " + exception); //TODO make slf4j work and put log here
            return Optional.empty();
        }
    }

    private Optional<WebElement> getOptionalTagProductElement(WebElement categoryEl, String tag) {
        try {
            return Optional.of(categoryEl.findElement(By.tagName(tag)));
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            System.out.println("No product in this element: " + exception); //TODO make slf4j work and put log here
            return Optional.empty();
        }
    }

    private Optional<WebElement> getOptionalClassProductElement(WebElement categoryEl, String className) {
        try {
            return Optional.of(categoryEl.findElement(By.className(className)));
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            System.out.println("No product in this element: " + exception); //TODO make slf4j work and put log here
            return Optional.empty();
        }
    }

    private WebElement getByXPath(WebElement webElement, String xpath) {
        try {
            return webElement.findElement(By.xpath(xpath));
        } catch (org.openqa.selenium.NoSuchElementException exception) {
            System.out.println("No product p in this element: " + exception); //TODO make slf4j work and put log here
            return null;
        }
    }
}
