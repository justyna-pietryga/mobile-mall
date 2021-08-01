package com.mobilemall.scrapper.scrap_classes;

import com.mobilemall.scrapper.conf.ShopsEnum;
import com.mobilemall.scrapper.model.Category;
import com.mobilemall.scrapper.model.Product;
import reactor.core.publisher.Flux;

public interface Scrapable {
     Flux<Category> getScrappedCategories();
     Flux<Product> getProducts(String url);
     ShopsEnum getShop();
}
