package com.mobilemall.general.categories;

import com.mobilemall.general.model.Category;
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
    @Value("${reserved.url}")
    private String url;
    @Value("${reserved.woman.selectortemplate}")
    private String womanSelectorTemplate;
    @Value("${reserved.woman.entryNum}")
    private int entryNodeNum;
    @Value("${reserved.woman.categories.selector}")
    private String womanCategoriesSelector;
    @Value("${reserved.woman.categories.clothes.nodeNum}")
    private int womanCategoriesClothesNodeNum;
    @Value("${reserved.woman.categories.clothes.categoryentry.selector}")
    private String womanCategoriesClothesCategoryEntrySelector;

    @Value("${reserved.woman.categories.clothes.categoryentry.link.selector}")
    private String categoryEntryLinkSelector;
    @Value("${reserved.woman.categories.clothes.categoryentry.name.selector}")
    private String categoryEntryNameSelector;

    private final WebDriver webDriver;

    @Autowired
    public ReservedScrapCategories(WebDriver webDriver) throws IOException {
        this.webDriver = webDriver;
    }

    @Override
    public Set<Category> getScrappedCategories() throws IOException {
//        Document document = Jsoup.connect(url).get();
        System.out.println("jazda");
        webDriver.get(url);

        Set<Category> categorySet = new HashSet<>();

        List<WebElement> clothesCategoriesContainer = webDriver
                .findElement(By.xpath("//*[@id=\"navigation-wrapper\"]/div/ul/li[3]/ul/li[1]/ul"))
                .findElements(By.tagName("li"));

        WebElement li = clothesCategoriesContainer.get(4).findElement(By.tagName("a"));

//*[@id="navigation-wrapper"]/div/ul/li[3]/ul/li[1]/ul
        //#navigation-wrapper > div > ul > li:nth-child(3) > ul > li.sc-cEvuZC.eqaJBs.menu-submenu.type-default.level-1 > ul
        System.out.println(li.getAttribute("href"));
        System.out.println(li.getAttribute("innerText"));
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

        return categorySet;
    }
}
