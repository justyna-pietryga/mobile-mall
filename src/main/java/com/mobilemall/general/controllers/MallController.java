package com.mobilemall.general.controllers;

import com.mobilemall.scrapper.conf.ShopsEnum;
import com.mobilemall.scrapper.scrap_classes.Scrapable;
import com.mobilemall.scrapper.model.Category;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@RestController
@RequestMapping(path = "/api")
public class MallController {

    private final Map<ShopsEnum, Scrapable> scrapperHandler;

    @Autowired
    public MallController(Map<ShopsEnum, Scrapable> scrapperHandler) {
        this.scrapperHandler = scrapperHandler;
    }

    @GetMapping(path = "/categories")
    @ResponseStatus(HttpStatus.OK)
    public Map<ShopsEnum, List<Category>> getCategories(@RequestParam Set<ShopsEnum> shopList) {
        return shopList.parallelStream()
                .collect(Collectors.toMap(identity(), shop -> {
//                    List<Category> categories = List.of();
//                    ExecutorService es = Executors.newSingleThreadExecutor();
//                    Future<List<Category>> result =
//                            es.submit(() -> scrapperHandler.get(shop).getScrappedCategories());
//                    try {
//                        categories = result.get();
//                    } catch (Exception e) {
//                        System.out.println("Failed");
//                    }
//                    es.shutdown();
//                    return categories;
                    return scrapperHandler.get(shop).getScrappedCategories();
                }));
    }

    //TODO handle the situation when scrapping from one shop would fail
    //TODO Introduce React Java or sth else
    //TODO Consider removing WebDriver as a bean and create seperate instance for every run and make calls in parallel
}
