package com.mobilemall.scrapper.scrap_classes;

import com.mobilemall.scrapper.conf.ShopsEnum;
import com.mobilemall.scrapper.model.Category;
import com.mobilemall.scrapper.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;

import java.io.IOException;
import java.util.List;

public interface Scrapable {
     Flux<Category> getScrappedCategories();
     Flux<Product> getProducts(String url);
     ShopsEnum getShop();
}
