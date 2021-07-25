package com.mobilemall.core.controllers;

import com.mobilemall.persistence.model.Shop;
import com.mobilemall.persistence.repository.ShopRepository;
import com.mobilemall.scrapper.conf.ShopsEnum;
import com.mobilemall.scrapper.model.Category;
import com.mobilemall.scrapper.model.Product;
import com.mobilemall.scrapper.scrap_classes.Scrapable;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "/api")
public class MallController {

    private final Map<ShopsEnum, Scrapable> scrapperHandler;
    private final ShopRepository shopRepository;

    @Autowired
    public MallController(Map<ShopsEnum, Scrapable> scrapperHandler, ShopRepository shopRepository) {
        this.scrapperHandler = scrapperHandler;
        this.shopRepository = shopRepository;
    }

    @GetMapping(path = "/categories", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Category> getCategories(@RequestParam Set<ShopsEnum> shopList) {
        shopRepository.save(new Shop("BERSHKA", List.of()));
        return Flux.fromIterable(shopList)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(shop -> scrapperHandler.get(shop)
                        .getScrappedCategories()
                        .subscribeOn(Schedulers.boundedElastic()));
    }

    @GetMapping(path = "/products", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<Product> getProducts() {
        return scrapperHandler
                .get(ShopsEnum.RESERVED)
                .getProducts(Category.builder()
                        .url("https://www.reserved.com/pl/pl/woman/clothes/blouses")
//                        .url("https://www.bershka.com/pl/kobieta/odzie%C5%BC/bluzy-c1010193222.html")
                        .build());
    }

    @GetMapping(path = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    @ResponseStatus(HttpStatus.OK)
    public Flux<String> getTest() {
        val array = new ArrayList<String>();
        for (int i = 0; i <= 1000; i++) {
            array.add(String.valueOf(i));
        }
        return Flux.fromIterable(array);
    }

    //TODO handle the situation when scrapping from one shop would fail
    //TODO Introduce React Java or sth else
    //TODO Consider removing WebDriver as a bean and create seperate instance for every run and make calls in parallel
}
