//package com.mobilemall.general.controllers;
//
//import com.mobilemall.scrapper.conf.ShopsEnum;
//import com.mobilemall.scrapper.model.Category;
//import com.mobilemall.scrapper.model.Product;
//import com.mobilemall.scrapper.scrap_classes.Scrapable;
//import lombok.val;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.scheduler.Schedulers;
//
//import java.util.ArrayList;
//import java.util.Map;
//import java.util.Set;
//
//@RestController
//@RequestMapping(path = "/api")
//public class MallController {
//
//    private final Map<ShopsEnum, Scrapable> scrapperHandler;
//
//    @Autowired
//    public MallController(Map<ShopsEnum, Scrapable> scrapperHandler) {
//        this.scrapperHandler = scrapperHandler;
//    }
//
//    @GetMapping(path = "/categories", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    public Flux<Category> getCategories(@RequestParam Set<ShopsEnum> shopList) {
//        return Flux.fromIterable(shopList)
//                .subscribeOn(Schedulers.boundedElastic())
//                .flatMap(shop -> scrapperHandler.get(shop)
//                        .getScrappedCategories()
//                        .subscribeOn(Schedulers.boundedElastic()));
//    }
//
//    @GetMapping(path = "/products", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    public Flux<Product> getProducts() {
//        return scrapperHandler
//                .get(ShopsEnum.RESERVED)
//                .getProducts(Category.builder()
//                        .url("https://www.reserved.com/pl/pl/woman/clothes/blouses")
////                        .url("https://www.bershka.com/pl/kobieta/odzie%C5%BC/bluzy-c1010193222.html")
//                        .build());
//    }
//
//    @GetMapping(path = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
////    @ResponseStatus(HttpStatus.OK)
//    public Flux<String> getTest() {
//        val array = new ArrayList<String>();
//        for (int i = 0; i <= 1000; i++) {
//            array.add(String.valueOf(i));
//        }
//        return Flux.fromIterable(array);
//    }
//
//    //TODO handle the situation when scrapping from one shop would fail
//    //TODO Introduce React Java or sth else
//    //TODO Consider removing WebDriver as a bean and create seperate instance for every run and make calls in parallel
//}
