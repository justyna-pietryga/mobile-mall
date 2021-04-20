package com.mobilemall.scrapper.scrap_classes;

import com.mobilemall.scrapper.model.Category;
import com.mobilemall.scrapper.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ReservedScraper implements Scrapable {
    private static final String LI_TAG = "li";
    private final WebDriver webDriver;

    @Value("${reserved.url}")
    private String url;
    @Value("${reserved.element.li.xpath}")
    private String liElementXpath;

    @Autowired
    public ReservedScraper(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public List<Category> getScrappedCategories() {
        webDriver.get(url);

        List<WebElement> clothesCategoriesContainer = webDriver
                .findElement(By.xpath(liElementXpath))
                .findElements(By.tagName(LI_TAG));

        return clothesCategoriesContainer.stream()
                .map(this::getCategory)
                .collect(toList());
    }

    @Override
    public List<Product> getProducts(Category category) throws IOException {
        Document document = Jsoup.connect(category.getUrl()).get();
        Elements els = document.select("#categoryProducts article");
        return els.stream()
                .map(this::getProduct)
                .collect(toList());

    }

    private Category getCategory(WebElement categoryEl) {
        WebElement categoryLi = categoryEl.findElement(By.tagName("a"));
        return Category.builder()
                .name(categoryLi.getAttribute("innerText"))
                .url(categoryLi.getAttribute("href"))
                .build();
    }

    private Product getProduct(Element element) {
        Element figureElement = element.select("figure").first();
        Element productAHrefElement = figureElement.select("figcaption a").first();
        String imgUrl = figureElement.select("a img").first().attr("data-src");

        return Product.builder()
                .name(productAHrefElement.text())
                .url(productAHrefElement.attr("href"))
                .imgUrl(imgUrl)
                .build();
    }
}
