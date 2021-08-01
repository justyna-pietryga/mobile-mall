package com.mobilemall.scrapper.scrap_classes;

import com.mobilemall.scrapper.conf.SeleniumManager;
import com.mobilemall.scrapper.conf.ShopsEnum;
import com.mobilemall.scrapper.model.Category;
import com.mobilemall.scrapper.model.Product;
import lombok.val;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ReservedScraper extends Scraper implements Scrapable {

    @Value("${reserved.url}")
    private String url;
    @Value("${reserved.element.li.xpath}")
    private String liElementXpath;

    protected ReservedScraper(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public Flux<Category> getScrappedCategories() {
        return SeleniumManager.scrapData(this::scrapCategories, url);
    }

    @Override
    public ShopsEnum getShop() {
        return ShopsEnum.RESERVED;
    }

    private Flux<Category> scrapCategories(WebDriver driver) {
        val elements = driver
                .findElement(By.xpath(liElementXpath))
                .findElements(By.tagName(LI_TAG));

        return Flux.fromIterable(elements)
                .map(this::getCategory);
    }

    @Override
    public Flux<Product> getProducts(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements els = document.select("#categoryProducts article");

        return Flux.fromIterable(els)
                .map(this::getProduct);
    }

    private Category getCategory(WebElement categoryEl) {
        WebElement categoryLi = categoryEl.findElement(By.tagName("a"));
        return Category.builder()
                .name(categoryLi.getAttribute("innerText"))
                .url(categoryLi.getAttribute("href"))
                .shop(getShop())
                .build();
    }

    private Product getProduct(Element element) {
        Element figureElement = element.select("figure").first();
        Element productAHrefElement = figureElement.select("figcaption a").first();
        Element priceElement = figureElement.select("section p span").first();
        String imgUrl = figureElement.select("a img").first().attr("data-src");

        return Product.builder()
                .name(productAHrefElement.text())
                .url(productAHrefElement.attr("href"))
                .imgUrl(imgUrl)
                .price(priceElement.text())
                .build();
    }
}
