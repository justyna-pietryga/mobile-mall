package com.mobilemall.scrapper.categories;

import com.mobilemall.scrapper.model.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ReservedScrapCategories implements ScrapCategories {
//    @Value("${reserved.url}")
    private String url = "https://www.reserved.com/pl/pl/";

    private final WebDriver webDriver;

    @Autowired
    public ReservedScrapCategories(WebDriver webDriver) throws IOException {
        this.webDriver = webDriver;
    }

    @Override
    public Set<Category> getScrappedCategories() throws IOException {
        webDriver.get(url);

        Set<Category> categorySet = new HashSet<>();

        List<WebElement> clothesCategoriesContainer = webDriver
                .findElement(By.xpath("//*[@id=\"navigation-wrapper\"]/div/ul/li[3]/ul/li[1]/ul"))
                .findElements(By.tagName("li"));

        clothesCategoriesContainer.forEach(categoryEl -> {
            WebElement categoryLi = categoryEl.findElement(By.tagName("a"));
            categorySet.add(new Category(categoryLi.getAttribute("innerText"), categoryLi.getAttribute("href")));
        });

        return categorySet;


        //        Document document = Jsoup.connect(url).get();
//                .findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div[2]/button[2]"));
//        clothesCategoriesContainer.click();

//        System.out.println("test: " + clothesCategoriesContainer.toString());
//
//        Element clothesCategoriesContainer = document
//                .select(womanSelectorTemplate)
//                .get(entryNodeNum)
//                .select(womanCategoriesSelector)
//                .get(womanCategoriesClothesNodeNum);
//
//        Elements clothesCategories = clothesCategoriesContainer.select(womanCategoriesClothesCategoryEntrySelector);
//        clothesCategories.forEach(category -> {
//            String url = category.select(categoryEntryLinkSelector).attr("href");
//            String categoryName = category.select(categoryEntryNameSelector).text();
//
//            System.out.println(url);
//            System.out.println(categoryName);
//            categorySet.add(new Category(categoryName, url));
//        });
    }
}
