package com.mobilemall.scrapper.scrap_classes;

import com.mobilemall.scrapper.conf.ShopsEnum;
import com.mobilemall.scrapper.model.Category;
import com.mobilemall.scrapper.model.Product;
import reactor.core.publisher.Flux;

import java.util.List;

public interface Scrapable {
     Flux<Category> getScrappedCategories();
     Flux<Product> getProducts(String url, List<String> sizes);
     ShopsEnum getShop();
     String addSizeToUrl(List<String> sizes);
}
